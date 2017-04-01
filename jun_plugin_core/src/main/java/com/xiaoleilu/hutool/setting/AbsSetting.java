package com.xiaoleilu.hutool.setting;

import com.xiaoleilu.hutool.convert.Convert;
import com.xiaoleilu.hutool.getter.OptNullBasicTypeFromObjectGetter;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.StaticLog;
import com.xiaoleilu.hutool.util.BeanUtil;
import com.xiaoleilu.hutool.util.BeanUtil.ValueProvider;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 * Setting抽象类
 * 
 * @author Looly
 *
 */
public abstract class AbsSetting extends OptNullBasicTypeFromObjectGetter<String> {
	private final static Log log = StaticLog.get();

	/** 数组类型值默认分隔符 */
	public final static String DEFAULT_DELIMITER = ",";

	@Override
	public abstract Object getObj(String key, Object defaultValue);

	/**
	 * 获得字符串类型值
	 * 
	 * @param key KEY
	 * @param group 分组
	 * @param defaultValue 默认值
	 * @return 值或默认值
	 */
	public String getStr(String key, String group, String defaultValue) {
		final String value = getByGroup(key, group);
		if (StrUtil.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * 获得指定分组的键对应值
	 * 
	 * @param key 键
	 * @param group 分组
	 * @return 值
	 */
	public String getByGroup(String key, String group) {
		return getStr(keyWithGroup(key, group));
	}

	// --------------------------------------------------------------- Get
	/**
	 * 带有日志提示的get，如果没有定义指定的KEY，则打印debug日志
	 * 
	 * @param key 键
	 * @return 值
	 */
	public String getWithLog(String key) {
		final String value = getStr(key);
		if (value == null) {
			log.debug("No key define for [{}]!", key);
		}
		return value;
	}

	/**
	 * 带有日志提示的get，如果没有定义指定的KEY，则打印debug日志
	 * 
	 * @param key 键
	 * @return 值
	 */
	public String getByGroupWithLog(String key, String group) {
		final String value = getByGroup(key, group);
		if (value == null) {
			log.debug("No key define for [{}] of group [{}] !", key, group);
		}
		return value;
	}

	// --------------------------------------------------------------- Get string array
	/**
	 * 获得数组型
	 * 
	 * @param key 属性名
	 * @return 属性值
	 */
	public String[] getStrings(String key) {
		return getStrings(key, null);
	}

	/**
	 * 获得数组型
	 * 
	 * @param key 属性名
	 * @param defaultValue 默认的值
	 * @return 属性值
	 */
	public String[] getStringsWithDefault(String key, String[] defaultValue) {
		String[] value = getStrings(key, null);
		if (null == value) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * 获得数组型
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public String[] getStrings(String key, String group) {
		return getStrings(key, group, DEFAULT_DELIMITER);
	}

	/**
	 * 获得数组型
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @param delimiter 分隔符
	 * @return 属性值
	 */
	public String[] getStrings(String key, String group, String delimiter) {
		final String value = getByGroup(key, group);
		if (StrUtil.isBlank(value)) {
			return null;
		}
		return StrUtil.split(value, delimiter);
	}

	// --------------------------------------------------------------- Get int
	/**
	 * 获取数字型型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public Integer getInt(String key, String group) {
		return getInt(key, group, null);
	}

	/**
	 * 获取数字型型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @param defaultValue 默认值
	 * @return 属性值
	 */
	public Integer getInt(String key, String group, Integer defaultValue) {
		return Convert.toInt(getByGroup(key, group), defaultValue);
	}

	// --------------------------------------------------------------- Get bool
	/**
	 * 获取波尔型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public Boolean getBool(String key, String group) {
		return getBool(key, group, null);
	}

	/**
	 * 获取波尔型型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @param defaultValue 默认值
	 * @return 属性值
	 */
	public Boolean getBool(String key, String group, Boolean defaultValue) {
		return Convert.toBool(getByGroup(key, group), defaultValue);
	}

	// --------------------------------------------------------------- Get long
	/**
	 * 获取long类型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public Long getLong(String key, String group) {
		return getLong(key, group, null);
	}

	/**
	 * 获取long类型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @param defaultValue 默认值
	 * @return 属性值
	 */
	public Long getLong(String key, String group, Long defaultValue) {
		return Convert.toLong(getByGroup(key, group), defaultValue);
	}

	// --------------------------------------------------------------- Get char
	/**
	 * 获取char类型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public Character getChar(String key, String group) {
		final String value = getByGroup(key, group);
		if (StrUtil.isBlank(value)) {
			return null;
		}
		return value.charAt(0);
	}

	// --------------------------------------------------------------- Get double
	/**
	 * 获取double类型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @return 属性值
	 */
	public Double getDouble(String key, String group) {
		return getDouble(key, group, null);
	}

	/**
	 * 获取double类型属性值
	 * 
	 * @param key 属性名
	 * @param group 分组名
	 * @param defaultValue 默认值
	 * @return 属性值
	 */
	public Double getDouble(String key, String group, Double defaultValue) {
		return Convert.toDouble(getByGroup(key, group), defaultValue);
	}

	/**
	 * 将setting中的键值关系映射到对象中，原理是调用对象对应的set方法<br/>
	 * 只支持基本类型的转换
	 * 
	 * @param group 分组
	 * @param bean Bean对象
	 * @return Bean
	 */
	public Object toBean(final String group, Object bean) {
		return BeanUtil.fillBean(bean, new ValueProvider(){

			@Override
			public Object value(String name) {
				final String value = getByGroup(name, group);
				if (null != value) {
					log.debug("Parse setting to object field [{}={}]", name, value);
				}
				return value;
			}
		});
	}

	/**
	 * 将setting中的键值关系映射到对象中，原理是调用对象对应的set方法<br/>
	 * 只支持基本类型的转换
	 * 
	 * @param bean Bean
	 * @return Bean
	 */
	public Object toBean(Object bean) {
		return toBean(null, bean);
	}

	/*--------------------------Private Method start-------------------------------*/
	/**
	 * 组合Key和Group，组合后为group.key
	 * 
	 * @param key
	 * @param group
	 * @return 组合后的KEY
	 */
	private static String keyWithGroup(String key, String group) {
		String keyWithGroup = key;
		if (!StrUtil.isBlank(group)) {
			keyWithGroup = group.concat(StrUtil.DOT).concat(key);
		}
		return keyWithGroup;
	}

	/*--------------------------Private Method end-------------------------------*/
}
