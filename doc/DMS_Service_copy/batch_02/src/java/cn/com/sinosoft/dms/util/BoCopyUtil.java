package cn.com.sinosoft.dms.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/***
 * 将BoCopyService的方法放入Util中使用
 * <p>
 * convert：C、P、Copy之间表数据的互拷的方法<br>
 * setValueforSpecificFiled：为C、P、Copy表中的相同名称的输入域赋值
 * </p>
 * 
 * @author liufei
 * 
 */
public class BoCopyUtil {
	@SuppressWarnings("unchecked")
	private static final Map<Class, String> supportTypeMap = new HashMap<Class, String>();
	// 避免在方法内多次新建数组而设立的两个基础数组 add by wangzhifu 2010-12-06
	private static final Object[] EMPTY_ARRAY = new Object[0];
	// private static Object[] SINGLE_ELEM_ARRAY = new Object[1];
	/** 初始缓存实例 */

	static {
		supportTypeMap.put(Integer.class, "");
		supportTypeMap.put(Long.class, "");
		supportTypeMap.put(Double.class, "");
		supportTypeMap.put(BigDecimal.class, "");
		supportTypeMap.put(String.class, "");
		supportTypeMap.put(Date.class, "");
		supportTypeMap.put(Boolean.class, "");
		supportTypeMap.put(byte[].class, "");
	}

	/**
	 * @author liufei
	 * @category 大对象转换方法
	 * @param source
	 *            源对象
	 * @param dest
	 *            目标对象
	 * @param caller
	 *            调用者
	 * @param mappingRule
	 *            源对象与目标对象子对象的名称映射方法
	 * @param intercepteMethod
	 *            字段特殊处理
	 * @exception Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void convert(Object source, Object dest, Object caller,
			Method mappingRule, Method intercepteMethod) throws Exception {
		Object[] SINGLE_ELEM_ARRAY = new Object[1];
		// 获取所有getter,setter 方法
		Class sourceClass = source.getClass();
		Class destClass = dest.getClass();
		// 获取所有getter,setter 方法
//		List<Method> srcGetMethods = getGetter(sourceClass);
//		List<Method> destSetMethods = getSetter(destClass);
		// 获取所有getter,setter 方法
		List<Method> srcGetMethods = null;
		List<Method> destSetMethods = null;
		
		//System.out.println( "convert:  "+ sourceKey +"|"+ destKey);
		
		srcGetMethods = getGetter(sourceClass);
		destSetMethods = getSetter(destClass);
		
		Map<String, Method> srcMethodMap = new HashMap<String, Method>();
		for (Method method : srcGetMethods) {
			srcMethodMap.put(method.getName().toUpperCase(), method);
		}
		String currentFieldName = null;
		Method targetGetter = null;
		int outerListSize = destSetMethods.size();
		for (int index = 0; index < outerListSize; index++) {
			Method target = destSetMethods.get(index);
			// 处理思路延用原SuperBeantools的simpleCopy方法
			currentFieldName = target.getName().substring(3);
			targetGetter = destClass.getMethod("get" + currentFieldName);
			// 如果返回类型为List，则说明此字段为子表信息
			if (targetGetter.getReturnType() == List.class) {
				// 默认方法名称均为get,set.调用相应方法对子表名称转换
				String srcMethodName = "get" + currentFieldName;
				if (mappingRule != null) {
					SINGLE_ELEM_ARRAY[0] = currentFieldName;
					srcMethodName = (String) mappingRule.invoke(caller,
							SINGLE_ELEM_ARRAY);
					// System.out.println(target.getName() + " <--- " +
					// srcMethodName);
				}
				Method srcMethod = null;
				// 没有此方法退出本次循环
				try {
					srcMethod = sourceClass.getMethod(srcMethodName);
				} catch (NoSuchMethodException e) {
					// System.out.println("----Copy ERROR (没有此方法): " +
					// sourceClass.getSimpleName() + "." + srcMethodName);
					continue;
				}
				// 获取源List对象
				List<Object> fieldsListSrc = (List<Object>) srcMethod.invoke(
						source, EMPTY_ARRAY);
				// 有可能源代List对象为空，此时继续循环即可
				if (fieldsListSrc == null) {
					continue;
				}
				// 获目标List对象
				List<Object> fieldsListDest = (List<Object>) targetGetter
						.invoke(dest, EMPTY_ARRAY);
				// 如果目标LIST对象为空，则创建所LIST
				if (fieldsListDest == null) {
					// System.out.println("----Copy ERROR (列表为空): " +
					// sourceClass.getSimpleName() + "." + srcMethod.getName() +
					// " ---> " +
					// destClass.getSimpleName() + "." + target.getName());
					fieldsListDest = new ArrayList<Object>();
				}
				// 获取泛型
				Type genericType = targetGetter.getGenericReturnType();
				ParameterizedType paramType = (ParameterizedType) genericType;
				// 创建实例
				Class genericClazz = (Class) paramType.getActualTypeArguments()[0];
				int innerListSize = fieldsListSrc.size();
				// 循环遍历LIST
				for (int i = 0; i < innerListSize; i++) {
					Object destObject = genericClazz.newInstance();
					// 对单个对象进行拷贝
					convert(fieldsListSrc.get(i), destObject, caller,
							mappingRule, intercepteMethod);
					// 对拷后将对象存储
					fieldsListDest.add(destObject);
				}
				SINGLE_ELEM_ARRAY[0] = fieldsListDest;
				// 目标LIST赋值
				target.invoke(dest, SINGLE_ELEM_ARRAY);
				// 联合主键处理
			} else if (currentFieldName.equals("Id")) {
				// "get" + "Id";
				String strIDname = "get" + target.getName().substring(3);
				// 获取源ID的getter方法
				Method srcIDMethod = sourceClass.getMethod(strIDname);
				// 获取目标ID的getter方法
				Method destIDMethod = destClass.getMethod(strIDname);
				// 获取源ID对象
				Object srcID = srcIDMethod.invoke(source, EMPTY_ARRAY);
				// 获取目标ID对象
				Object destID = destIDMethod.invoke(dest, EMPTY_ARRAY);
				// 目标对象可能存在为空情况
				if (destID == null) {
					// 获取ID对象类型，并实例化
					Class clazz = destIDMethod.getReturnType();
					destID = clazz.newInstance();
					// 进行对象复制
					convert(srcID, destID, caller, mappingRule,
							intercepteMethod);
					SINGLE_ELEM_ARRAY[0] = destID;
					// 对目标对象进行赋值
					target.invoke(dest, SINGLE_ELEM_ARRAY);
				}
			}
			// 普通字段值进行值拷贝
			Method srcMethod = (Method) srcMethodMap.get("GET"
					+ currentFieldName.toUpperCase());
			if (srcMethod == null) {
				srcMethod = (Method) srcMethodMap.get("IS"
						+ currentFieldName.toUpperCase());
			}
			// 找到相应方法调用
			if (srcMethod != null
					&& supportTypeMap.containsKey(srcMethod.getReturnType())) {
				try {
					Object value = srcMethod.invoke(source, EMPTY_ARRAY);
					SINGLE_ELEM_ARRAY[0] = value;
					target.invoke(dest, SINGLE_ELEM_ARRAY);
				} catch (IllegalArgumentException e) {
					// 如果是类型不匹配，则打印不匹配的属性
					// System.out.println("----Copy ERROR (类型不匹配): " +
					// source.getClass().getSimpleName() + "." +
					// srcMethod.getName() + " ---> " +
					// dest.getClass().getSimpleName() + "." +
					// target.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 字段特殊处理
			if (intercepteMethod != null) {
				intercepteMethod.invoke(caller, new Object[] { dest, target,
						source });
			}
		}
	}
	
	
	/**
	 * 获取类及其父类的所有set方法（jar包中方法的效率提升的版本）
	 * 
	 * @param cl
	 *            待获取的类
	 * @return 所有set方法
	 * 
	 *         liufei
	 */
	private static List<Method> getSetter(Class cl) {
		List list = new ArrayList();
		Method[] methods = cl.getDeclaredMethods();
		for (int i = 0; i < methods.length; ++i) {
			Method method = methods[i];
			String methodName = method.getName();
			if (methodName.startsWith("set")) {
				list.add(method);
			}
		}
		cl = cl.getSuperclass();
		// 递归获取父类的set方法
		if (cl != Object.class) {
			list.addAll(getSetter(cl));
		}
		return list;
	}

	/**
	 * 获取类及其父类的所有get方法（jar包中方法的效率提升的版本）
	 * 
	 * @param cl
	 *            待获取的类
	 * @return 所有get方法
	 * 
	 *         liufei
	 */
	private static List<Method> getGetter(Class cl) {
		List list = new ArrayList();
		Method[] methods = cl.getDeclaredMethods();
		int lgn=methods.length;
		for (int i = 0; i < lgn; ++i) {
			Method method = methods[i];
			String methodName = method.getName();
			// 以set或is开头的方法
			if (methodName.startsWith("get") || methodName.startsWith("is")) {
				list.add(method);
			}
		}
		cl = cl.getSuperclass();
		// 递归获取父类的get方法
		if (cl != Object.class) {
			list.addAll(getGetter(cl));
		}
		return list;
	}
	
	
}
