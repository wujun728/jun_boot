package me.kafeitu.activiti.web.chapter6;

import me.kafeitu.activiti.chapter6.util.UserUtil;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 流程定义相关功能：读取动态表单字段、读取外置表单内容
 *
 * @author henryyan
 */
@Controller
@RequestMapping(value = "/chapter6")
public class ProcessDefinitionController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    FormService formService;

    @Autowired
    IdentityService identityService;

    /**
     * 读取启动流程的表单字段
     */
    @RequestMapping(value = "getform/start/{processDefinitionId}")
    public ModelAndView readStartForm(@PathVariable("processDefinitionId") String processDefinitionId,
                                      HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();

        User user = UserUtil.getUserFromSession(request.getSession());
        List<Group> groupList = (List<Group>) request.getSession().getAttribute("groups");

        // 权限拦截
        boolean startable = false;
      //根据流程定义查询全部的 IdentityLink（ACT_RU_IDENTITYLINK任务参与者数据表） 数据
        List<IdentityLink> identityLinks = repositoryService.getIdentityLinksForProcessDefinition(processDefinition.getId());
        if (identityLinks == null || identityLinks.isEmpty()) {//identityLinks为空，任何人都有权限
            startable = true;
        } else {
            for (IdentityLink identityLink : identityLinks) {
                if (StringUtils.isNotBlank(identityLink.getUserId()) && identityLink.getUserId().equals(user.getId())) {//identityLinks不为空，且当前用户和identityLink里面的用户一致
                    startable = true;
                    break;
                }

                if (StringUtils.isNotBlank(identityLink.getGroupId())) {
                    for (Group group : groupList) {
                        if (group.getName().equals(identityLink.getGroupId())) {
                            startable = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!startable) {
            redirectAttributes.addFlashAttribute("error", "您无权启动【" + processDefinition.getName() + "】流程！");
            return new ModelAndView("redirect:/chapter5/process-list-view");
        }

        //业务流程定义数据表ACT_RE_PROCDEF(把所有的流程概要信息放在这张表)
        boolean hasStartFormKey = processDefinition.hasStartFormKey();//leave-start.form
        System.out.println(processDefinition.getDescription());

        // 根据是否有formkey属性判断使用哪个展示层
        String viewName = "chapter6/start-process-form";
        ModelAndView mav = new ModelAndView(viewName);

        // 判断是否有formkey属性
        if (hasStartFormKey) {
            Object renderedStartForm = formService.getRenderedStartForm(processDefinitionId);//根据processDefinitionId（leave-formkey:1:27）来获取渲染表单leave-start.form字符串
            /**
             * <div class="control-group">
				<label class="control-label" for="startDate">开始时间：</label>
				<div class="controls">
					<input type="text" id="startDate" name="startDate" class="datepicker" data-date-format="yyyy-mm-dd" required />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="endDate">结束时间：</label>
				<div class="controls">
					<input type="text" id="endDate" name="endDate" class="datepicker" data-date-format="yyyy-mm-dd" required />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="reason">请假原因：</label>
				<div class="controls">
					<textarea id="reason" name="reason" required></textarea>
				</div>
			</div>
             */
            mav.addObject("startFormData", renderedStartForm);
            mav.addObject("processDefinition", processDefinition);
        } else { // 动态表单字段
            StartFormData startFormData = formService.getStartFormData(processDefinitionId);
            mav.addObject("startFormData", startFormData);
        }
        mav.addObject("hasStartFormKey", hasStartFormKey);
        mav.addObject("processDefinitionId", processDefinitionId);
        return mav;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "process-instance/start/{processDefinitionId}")
    public String startProcessInstance(@PathVariable("processDefinitionId") String pdid, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).singleResult();
        boolean hasStartFormKey = processDefinition.hasStartFormKey();

        Map<String, String> formValues = new HashMap<String, String>();

        if (hasStartFormKey) { // formkey外置表单
            Map<String, String[]> parameterMap = request.getParameterMap();
            Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
            for (Entry<String, String[]> entry : entrySet) {
                String key = entry.getKey();
                formValues.put(key, entry.getValue()[0]);
            }
        } else { // 动态表单
            // 先读取表单字段在根据表单字段的ID读取请求参数值
            StartFormData formData = formService.getStartFormData(pdid);

            // 从请求中获取表单字段的值
            List<FormProperty> formProperties = formData.getFormProperties();
            for (FormProperty formProperty : formProperties) {
                String value = request.getParameter(formProperty.getId());
                formValues.put(formProperty.getId(), value);
            }
        }

        // 获取当前登录的用户
        User user = UserUtil.getUserFromSession(request.getSession());
        // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (user == null || StringUtils.isBlank(user.getId())) {
            return "redirect:/login?timeout=true";
        }
        identityService.setAuthenticatedUserId(user.getId());

        // 提交表单字段并启动一个新的流程实例
        ProcessInstance processInstance = formService.submitStartFormData(pdid, formValues);//pdid：leave-formkey:1:27   formValues：{startDate=2017-12-15, reason=111111111, endDate=2017-12-21}
        logger.debug("start a processinstance: {}", processInstance);
        redirectAttributes.addFlashAttribute("message", "流程已启动，实例ID：" + processInstance.getId());
        return "redirect:/chapter5/process-list-view";
    }

}
