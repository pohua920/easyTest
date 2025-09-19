package com.tlg.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 取得指定Class Field 
 * FIR0690、FIR0693	 CF048住火	住火_APS_元大續保作業Ph2_N+1_產生保單副本&N+1產生出單明細檔 
 * @author CF048
 *
 */
public class FieldAccessor {
	private static Map<Class<?> , Map<String, Method>> methodCache = new HashMap<Class<?> , Map<String, Method>> ();

	/**
	 * 取得指定Class Field 
	 * @param bean
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldValue(Object bean, String fieldName){
		
		if (bean==null || fieldName==null ) return null;
		Class<?> clazz = bean.getClass();
		
		try{
			Map<String, Method> classCache = methodCache.get(clazz);
			if (classCache==null){
				classCache = new HashMap<String, Method>();
				for (Field field : clazz.getDeclaredFields()){
					String fName = field.getName();
					String getterName = "get" + Character.toUpperCase(fName.charAt(0)) + fName.substring(1) ;
					Method method = clazz.getMethod(getterName);
					classCache.put(fName, method);
				}
				methodCache.put(clazz, classCache);
			}
			
			Method getter = classCache.get(fieldName);
			if(getter!=null){
				return getter.invoke(bean);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 泛型版本、支援自動轉型
	 * 取得指定Class Field 
	 * @param bean
	 * @param fieldName
	 * @param targetType
	 * @return
	 */
	public static <T> T getFieldValue(Object bean, String fieldName, Class<T> targetType ){
		Object value = getFieldValue(bean, fieldName);
		
		if (value ==null ) return null; 
		
		try{
			if (targetType.isAssignableFrom(value.getClass())){
				return (T) value ; //型別已相容
			}
			
			String stringValue = value.toString();
			if (targetType== Integer.class){
				return (T)Integer.valueOf(stringValue);
			} else if (targetType== Long.class){
				return (T)Long.valueOf(stringValue);
			}else if (targetType== Double.class){
				return (T)Double.valueOf(stringValue);
			}else if (targetType== Boolean.class){
				return (T)Boolean.valueOf(stringValue);
			}else if (targetType== java.util.Date.class){
				SimpleDateFormat sdf = stringValue.length() > 10 ?
						new SimpleDateFormat("yyyy-mm-dd HH:mm:ss"): 
							new SimpleDateFormat("yyyy-mm-dd");
				return (T) sdf.parse(stringValue);
			}else if (targetType== String.class){
				return (T)String.valueOf(stringValue);
			} 
			 
		}catch(Exception e){
			//轉型失敗
			e.printStackTrace();
		}
		
		return null;
	}

}
