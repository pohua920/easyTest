package com.sinosoft.claim.common.util;

import ins.framework.utils.BeanUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import java.text.SimpleDateFormat;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import java.util.ArrayList;
import java.util.Calendar;//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/***
 * 数据处理工具类
 * @author 中科软
 */
@SuppressWarnings("unchecked")
public final class DataUtils {
	private static final Log logger = LogFactory.getLog(DataUtils.class);
	private static final Object[] ZERO_OBJECT_ARRAY = new Object[0];
	private static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("###0");
	private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("###0.00");
	private static final String[] TRUE_ARRAY = { "y", "yes", "true", "t", "是", "1" };
	private static final String[] FALSE_ARRAY = { "n", "no", "false", "f", "否", "0" };

	private static Map<Object, String> supportTypeMap = new HashMap<Object, String>();

	public static void addSupportType(Object clazz) {
		supportTypeMap.put(clazz, "");
	}

	public static String zeroToEmpty(int value) {
		return value == 0 ? "" : String.valueOf(value);
	}

	public static String zeroToEmpty(double value) {
		return value == 0.0D ? "" : String.valueOf(value);
	}

	public static String nullToEmpty(String str) {
		return str == null ? "" : str;
	}

	public static String emptyToNull(String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return null;
		}
		return str;
	}

	public static String dbNullToEmpty(String str) {
		if ((str == null) || (str.equalsIgnoreCase("null"))) {
			return "";
		}
		return str;
	}

	public static String nullToZero(String str) {
		if ((str == null) || (str.trim().length() == 0)) {
			return "0";
		}
		return str;
	}

	public static String getBooleanDescribe(String str) {
		if (str == null) {
			throw new IllegalArgumentException("argument is null");
		}
		String retValue = null;
		if (str.trim().equals("")) {
			retValue = "";
		}
		for (int i = 0; i < TRUE_ARRAY.length; i++) {
			if (str.equalsIgnoreCase(TRUE_ARRAY[i])) {
				retValue = "是";
				break;
			}
		}
		for (int i = 0; i < FALSE_ARRAY.length; i++) {
			if (str.equalsIgnoreCase(FALSE_ARRAY[i])) {
				retValue = "否";
				break;
			}
		}
		if (retValue == null) {
			throw new IllegalArgumentException("argument not in ('y','n','yes','no','true','false','t','f','是','否','1','0','')");
		}

		return retValue;
	}

	public static boolean getBoolean(String str) {
		if (str == null) {
			throw new IllegalArgumentException("argument is null");
		}
		for (int i = 0; i < TRUE_ARRAY.length; i++) {
			if (str.equalsIgnoreCase(TRUE_ARRAY[i])) {
				return true;
			}
		}
		for (int i = 0; i < FALSE_ARRAY.length; i++) {
			if (str.equalsIgnoreCase(FALSE_ARRAY[i])) {
				return false;
			}
		}
		if (str.trim().equals("")) {
			return false;
		}
		throw new IllegalArgumentException("argument not in ('y','n','yes','no','true','false','t','f','是','否','1','0','')");
	}

	public static String getBooleanDescribe(boolean value) {
		if (value) {
			return getBooleanDescribe("true");
		}
		return getBooleanDescribe("false");
	}

	public static int compareByValue(String str1, String str2) {
		return new BigDecimal(str1).compareTo(new BigDecimal(str2));
	}

	public static double round(double value, int scale) {
		BigDecimal obj = new BigDecimal(Double.toString(value));
		return obj.divide(BigDecimal.ONE, scale, 4).doubleValue();
	}

	/** @deprecated */
	public static void copySimpleObject(Object target, Object source) {
		copySimpleObjectToTargetFromSource(target, source, true);
	}

	public static void copySimpleObjectToTargetFromSource(Object target, Object source) {
		copySimpleObjectToTargetFromSource(target, source, true);
	}

	/** @deprecated */
	public static void copySimpleObject(Object target, Object source, boolean isCopyNull) {
		copySimpleObjectToTargetFromSource(target, source, isCopyNull);
	}

	public static void copySimpleObjectToTargetFromSource(Object target, Object source, boolean isCopyNull) {
		if ((target == null) || (source == null)) {
			return;
		}
		List targetMethodList = BeanUtils.getSetter(target.getClass());
		List sourceMethodList = BeanUtils.getGetter(source.getClass());
		Map map = new HashMap();
		for (Iterator iter = sourceMethodList.iterator(); iter.hasNext();) {
			Method method = (Method) iter.next();
			map.put(method.getName(), method);
		}
		Object value = null;
		Object[] objArray = new Object[1];
		String methodName = null;
		for (Iterator iter = targetMethodList.iterator(); iter.hasNext();) {
			Method method = (Method) iter.next();
			String fieldName = method.getName().substring(3);
			try {
				methodName = "get" + fieldName;
				Method sourceMethod = null;
				if (map.containsKey(methodName)) {
					sourceMethod = (Method) map.get(methodName);
				} else {
					methodName = "is" + fieldName;
					if (map.containsKey(methodName)) {
						sourceMethod = (Method) map.get(methodName);
					}
				}
				if ((sourceMethod == null) || (!supportTypeMap.containsKey(sourceMethod.getReturnType()))) {
					continue;
				}
				value = sourceMethod.invoke(source, ZERO_OBJECT_ARRAY);
				objArray[0] = value;
				if (isCopyNull) {
					method.invoke(target, objArray);
				} else if (value != null)
					method.invoke(target, objArray);
			} catch (Exception e) {
				if (logger.isDebugEnabled())
					logger.debug(e);
			}
		}
	}

	public static List generateListFromJdbcResult(List jdbcResultList, Class clazz) {
		List objectList = new ArrayList();
		Object[] objArray = new Object[1];
		try {
			List methodList = BeanUtils.getSetter(clazz);
			for (int i = 0; i < jdbcResultList.size(); i++) {
				Map rowMap = (Map) jdbcResultList.get(i);
				Object[] rowKeys = rowMap.keySet().toArray();
				Object object = clazz.newInstance();
				for (int j = 0; j < rowKeys.length; j++) {
					String column = (String) rowKeys[j];
					for (int k = 0; k < methodList.size(); k++) {
						Method method = (Method) methodList.get(k);
						String upperMethodName = method.getName().toUpperCase();
						if (upperMethodName.equals("SET" + column.toUpperCase())) {
							Class type = method.getParameterTypes()[0];
							Object value = rowMap.get(column);
							if (value != null) {
								if (type == Integer.class)
									value = Integer.valueOf(value.toString());
								else if (type == Double.class)
									value = Double.valueOf(value.toString());
								else if (type == Long.class) {
									value = Long.valueOf(value.toString());
								}
							}
							objArray[0] = value;
							method.invoke(object, objArray);
							break;
						}
					}
				}
				objectList.add(object);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return objectList;
	}

	public static Integer getInteger(Object object) {
		Integer value = null;
		if (object != null) {
			value = Integer.valueOf(object.toString());
		}
		return value;
	}

	public static Long getLong(Object object) {
		Long value = null;
		if (object != null) {
			value = Long.valueOf(object.toString());
		}
		return value;
	}

	public static Double getDouble(Object object) {
		Double _double = null;
		if (object != null) {
			_double = new Double(object.toString());
		}
		return _double;
	}

	public static String getString(Object object) {
		String string = null;
		if (object != null) {
			string = object.toString();
		}
		return string;
	}

	public static String join(Object[] arguments) {
		return StringUtils.concat(arguments);
	}

	public static String getPlainNumber(Integer value) {
		if (value == null) {
			return "";
		}
		return NUMBER_FORMAT.format(value);
	}

	public static String getPlainNumber(Long value) {
		if (value == null) {
			return "";
		}
		return NUMBER_FORMAT.format(value);
	}

	public static String getPlainNumber(Double value) {
		if (value == null) {
			return "";
		}
		return DOUBLE_FORMAT.format(value);
	}
	public static String getPointNumber(Double value) {
		if (value == null) {
			return "";
		}
		return NUMBER_FORMAT.format(value);
	}

	static {
		supportTypeMap.put(Integer.TYPE, "");
		supportTypeMap.put(Long.TYPE, "");
		supportTypeMap.put(Double.TYPE, "");
		supportTypeMap.put(Boolean.TYPE, "");
		supportTypeMap.put(Integer.class, "");
		supportTypeMap.put(Long.class, "");
		supportTypeMap.put(Double.class, "");
		supportTypeMap.put(BigDecimal.class, "");
		supportTypeMap.put(String.class, "");
		supportTypeMap.put(Date.class, "");
		supportTypeMap.put(Boolean.class, "");
		// supportTypeMap.put([B.class, "");
	}
	
	/**
	 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
	 * @param birthdayStr
	 * @return
	 */
    public static int calculateAgeWithDateUtils(String birthdayStr) {
        try {
            // 處理日期格式，將"-"替換為"/"
            String formattedBirthday = birthdayStr.replace("-", "/");
            
            // 解析生日日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date birthday = sdf.parse(formattedBirthday);
            
            // 獲取當前日期
            Date now = new Date();
            
            // 使用DateUtils的方法來計算年齡
            Calendar birthdayCalendar = Calendar.getInstance();
            birthdayCalendar.setTime(birthday);
            
            Calendar nowCalendar = Calendar.getInstance();
            nowCalendar.setTime(now);
            
            // 計算年份差
            int age = nowCalendar.get(Calendar.YEAR) - birthdayCalendar.get(Calendar.YEAR);
            
            // 檢查是否已過生日
            if (nowCalendar.get(Calendar.DAY_OF_YEAR) < birthdayCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            
            return Math.max(0, age);
            
        } catch (ParseException e) {
            System.err.println("日期解析錯誤: " + e.getMessage());
            return 0;
        }
    }
}