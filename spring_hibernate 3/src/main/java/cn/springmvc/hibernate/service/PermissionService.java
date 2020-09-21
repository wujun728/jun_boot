package cn.springmvc.hibernate.service;

import java.util.Collection;

import cn.springmvc.hibernate.model.Permission;
import cn.springmvc.hibernate.model.User;

/**
 * @author Vincent.wang
 *
 */
public interface PermissionService {

	/**
	 * 查询用户所能访问的所有菜单
	 *
	 * @param user
	 *            用户
	 * @return permissions 菜单
	 */
	public Collection<Permission> getPermissions(User user);

	/**
	 * 添加 菜单
	 *
	 * @param permission
	 *            菜单项
	 */
	public void addPermission(Permission permission);
}
