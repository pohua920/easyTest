package com.sinosoft.one.bpm.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public final class BpmCommonUtils {
	private BpmCommonUtils() {
	}

	// public static String parseAttributeValue(Object bean, String
	// attributeName) throws Exception {
	// String value = "";
	// if (BeanUtils.isSimpleProperty(bean.getClass())) {
	// value = bean.toString();
	// } else {
	// if(StringUtils.isBlank(attributeName)) {
	// throw new IllegalArgumentException("the attribute value [" +
	// attributeName + "] is invalid.");
	// }
	// value = PropertyUtils.getProperty(bean, attributeName)
	// .toString();
	// }
	// return value;
	// }

	public static Object parseAttributeValue(Object bean, String attributeName) {
		Object value = null;
		if (BeanUtils.isSimpleProperty(bean.getClass())) {
			value = bean.toString();
		} else {
			if (bean instanceof Collection || bean instanceof Map || bean instanceof String[]) {
				return bean;
			}
			if (StringUtils.isBlank(attributeName)) {
				throw new IllegalArgumentException("the attribute value [" + attributeName + "] is invalid.");
			}
			try {
				value = PropertyUtils.getProperty(bean, attributeName);
			} catch (Exception e) {
				throw new IllegalArgumentException("the attribute value [" + attributeName + "] is invalid.");
			}
		}
		return value;
	}

	public static Object parseVariableValue(Object[] args, int attributeOffset, String attributeName) throws Exception {
		return parseAttributeValue(args[attributeOffset], attributeName);
	}

	public static Object parseAttributeValue(Object[] args, int attributeOffset, String attributeName) throws Exception {
		return parseAttributeValue(args[attributeOffset], attributeName);
	}

	public static void setAttributeValue(Object bean, String attributeName, Object value) {
		if (!BeanUtils.isSimpleProperty(bean.getClass())) {
			if (bean instanceof Collection) {
				Collection collection = (Collection) bean;
				collection.add(value);
				return;
			}
			if (bean instanceof Map || bean instanceof String[]) {
				Map map = (Map) bean;
				map.put(attributeName, value);
				return;
			}
			if (bean instanceof String[]) {
				String[] map = (String[]) bean;
				map[0] = value.toString();
				return;
			}
			if (StringUtils.isBlank(attributeName)) {
				throw new IllegalArgumentException("the attribute value [" + attributeName + "] is invalid.");
			}
			try {
				PropertyUtils.setProperty(bean, attributeName, value);
			} catch (Exception e) {
				throw new IllegalArgumentException("the attribute value [" + attributeName + "] is invalid.");
			}
		}
	}

	public static void setVariableValue(Object[] args, int attributeOffset, String attributeName, Object value) throws Exception {
		setAttributeValue(args[attributeOffset], attributeName, value);
	}

	public static void setAttributeValue(Object[] args, int attributeOffset, String attributeName, Object value) throws Exception {
		setAttributeValue(args[attributeOffset], attributeName, value);
	}

	public static boolean hasText(CharSequence cs, String errorMessage) {
		if (StringUtils.isBlank(cs)) {
			throw new IllegalArgumentException(errorMessage);
		}
		return Boolean.TRUE.booleanValue();
	}
}
