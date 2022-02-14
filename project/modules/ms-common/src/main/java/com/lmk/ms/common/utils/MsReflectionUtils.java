package com.lmk.ms.common.utils;

import java.lang.reflect.Field;
import org.apache.commons.lang3.StringUtils;
import io.swagger.annotations.ApiModelProperty;

/**
 * 反射工具类
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/09/16
 */
public class MsReflectionUtils {

	/**
	 * 指定类型指定字段名称获取Field
	 * @param type
	 * @param name
	 * @return
	 */
	public static Field findField(Class<?> type, String name) {
		try {
			return org.springframework.data.util.ReflectionUtils.findRequiredField(type, name);
		}catch (Exception e) {
		}
		return null;
	}


	/**
	 * 指定类型指定字段名称获取ApiModelProperty标记的描述
	 * 优先获取name，如果为空则获取value
	 * 如果没有ApiModelProperty，或name、value未设置则返回字段名称
	 * @param type
	 * @param name
	 * @return
	 */
	public static String getApiModelProperty(Class<?> type, String name) {
		Field field = findField(type, name);
		if(field != null) {
			ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
			if(property != null) {
				//优先获取name
				String value = StringUtils.defaultIfBlank(property.name(), property.value());
				name = StringUtils.defaultIfBlank(value, name);
			}
		}
		return name;
	}
}
