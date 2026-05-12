package com.sinosoft.claim.common.util;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

//import com.sinosoft.common.schema.model.PrpCinsuredIdvList;
import com.sinosoft.common.schema.model.PrpCitemKind;
//import com.sinosoft.common.schema.model.PrpCitemKindSub;
import com.sinosoft.common.schema.model.PrpCmain;
import com.sinosoft.common.schema.model.PrpCplan;
import com.sinosoft.common.schema.model.PrpCration;

import org.apache.commons.lang.StringUtils;


/**
 * mantis：CLM0226，處理人員：DP0713，需求單編號：新核心-立案修改功能修改出險地點調整
 * 來源  核心prpins
 */

/** 
 * 將BoCopyService的方法放入Util中使用
 * @author Sinosoft
 */
public class BoCopyUtil {
	@SuppressWarnings("unchecked")
	private static final Map<Class, String> supportTypeMap = new HashMap<Class, String>();
	// 避免在方法內多次新建數組而設立的兩個基礎數組 add by wangzhifu 2010-12-06
	private static final Object[] EMPTY_ARRAY = new Object[0];
	// private static Object[] SINGLE_ELEM_ARRAY = new Object[1];
	/** 初始緩存實例 */
	private static CacheService cacheManager = CacheManager.getInstance("BoCopyUtil");

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

	//BoCopyUtil.convert(UtiDecisionTableOld, UtiDecisionTableNew, UtiDecisionTable.class, null, null);
    //BoCopyUtil.setValueforSpecificField(UtiDecisionTableNew, "RiskCode", riskCodeNew);
	
	/**
	 * @category 大對象轉換方法
	 * @param source  源對象
	 * @param dest 目標對象
	 * @param caller 調用者
	 * @param mappingRule 源對象與目標對象子對象的名稱映射方法
	 * @param intercepteMethod 字段特殊處理
	 * @exception Exception
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void convert(Object source, Object dest, Object caller,
			Method mappingRule, Method intercepteMethod) throws Exception {
		Object[] SINGLE_ELEM_ARRAY = new Object[1];
		// 獲取所有getter,setter 方法
		if(null != source) {//modified by yuyiqiang 20130310 暫時這么處理
			
			Class sourceClass = source.getClass();
			Class destClass = dest.getClass();
			// 獲取所有getter,setter 方法
	//		List<Method> srcGetMethods = getGetter(sourceClass);
	//		List<Method> destSetMethods = getSetter(destClass);
			// 獲取所有getter,setter 方法
			List<Method> srcGetMethods = null;
			List<Method> destSetMethods = null;
			
			String sourceKey = cacheManager.generateCacheKey("convert", sourceClass.getName(),"getter");
			String destKey = cacheManager.generateCacheKey("convert", destClass.getName(),"setter");
			
			//System.out.println( "convert:  "+ sourceKey +"|"+ destKey);
			
			Object sourceResult = cacheManager.getCache(sourceKey);
			if(sourceResult!=null){
				srcGetMethods = (List<Method>)sourceResult;
			}else{
				srcGetMethods = getGetter(sourceClass);
				cacheManager.putCache(sourceKey, srcGetMethods);
			}
			
			Object destResult = cacheManager.getCache(destKey);
			if(destResult!=null){
				destSetMethods = (List<Method>)destResult;
			}else{
				destSetMethods = getSetter(destClass);
				cacheManager.putCache(destKey, destSetMethods);
			}
			Map<String, Method> srcMethodMap = new HashMap<String, Method>();
			for (Method method : srcGetMethods) {
				srcMethodMap.put(method.getName().toUpperCase(), method);
			}
			String currentFieldName = null;
			Method targetGetter = null;
			int outerListSize = destSetMethods.size();
			for (int index = 0; index < outerListSize; index++) {
				Method target = destSetMethods.get(index);
				// 處理思路延用原SuperBeantools的simpleCopy方法
				currentFieldName = target.getName().substring(3);
				targetGetter = destClass.getMethod("get" + currentFieldName);
				// 如果返回類型為List，則說明此字段為子表信息
				if (targetGetter.getReturnType() == List.class) {
					// 默認方法名稱均為get,set.調用相應方法對子表名稱轉換
					String srcMethodName = "get" + currentFieldName;
					if (mappingRule != null) {
						SINGLE_ELEM_ARRAY[0] = currentFieldName;
						srcMethodName = (String) mappingRule.invoke(caller,
								SINGLE_ELEM_ARRAY);
						// System.out.println(target.getName() + " <--- " +
						// srcMethodName);
					}
					Method srcMethod = null;
					// 沒有此方法退出本次循環
					try {
						srcMethod = sourceClass.getMethod(srcMethodName);
					} catch (NoSuchMethodException e) {
						// System.out.println("----Copy ERROR (沒有此方法): " +
						// sourceClass.getSimpleName() + "." + srcMethodName);
						continue;
					}
					// 獲取源List對象
					List<Object> fieldsListSrc = (List<Object>) srcMethod.invoke(
							source, EMPTY_ARRAY);
					// 有可能源代List對象為空，此時繼續循環即可
					if (fieldsListSrc == null) {
						continue;
					}
					// 獲目標List對象
					List<Object> fieldsListDest = (List<Object>) targetGetter
							.invoke(dest, EMPTY_ARRAY);
					// 如果目標LIST對象為空，則創建所LIST
					if (fieldsListDest == null) {
						// System.out.println("----Copy ERROR (列表為空): " +
						// sourceClass.getSimpleName() + "." + srcMethod.getName() +
						// " ---> " +
						// destClass.getSimpleName() + "." + target.getName());
						fieldsListDest = new ArrayList<Object>();
					}
					// 獲取泛型
					Type genericType = targetGetter.getGenericReturnType();
					ParameterizedType paramType = (ParameterizedType) genericType;
					// 創建實例
					Class genericClazz = (Class) paramType.getActualTypeArguments()[0];
					int innerListSize = fieldsListSrc.size();
					// 循環遍歷LIST
					for (int i = 0; i < innerListSize; i++) {
						Object destObject = genericClazz.newInstance();
						// 對單個對象進行拷貝
						convert(fieldsListSrc.get(i), destObject, caller,
								mappingRule, intercepteMethod);
						// 對拷后將對象存儲
						fieldsListDest.add(destObject);
					}
					SINGLE_ELEM_ARRAY[0] = fieldsListDest;
					// 目標LIST賦值
					target.invoke(dest, SINGLE_ELEM_ARRAY);
					// 聯合主鍵處理
				} else if (currentFieldName.equals("Id")) {
					// "get" + "Id";
					String strIDname = "get" + target.getName().substring(3);
					// 獲取源ID的getter方法
					Method srcIDMethod = sourceClass.getMethod(strIDname);
					// 獲取目標ID的getter方法
					Method destIDMethod = destClass.getMethod(strIDname);
					// 獲取源ID對象
					Object srcID = srcIDMethod.invoke(source, EMPTY_ARRAY);
					// 獲取目標ID對象
					Object destID = destIDMethod.invoke(dest, EMPTY_ARRAY);
					// 目標對象可能存在為空情況
					if (destID == null) {
						// 獲取ID對象類型，并實例化
						Class clazz = destIDMethod.getReturnType();
						destID = clazz.newInstance();
						// 進行對象復制
						convert(srcID, destID, caller, mappingRule,
								intercepteMethod);
						SINGLE_ELEM_ARRAY[0] = destID;
						// 對目標對象進行賦值
						target.invoke(dest, SINGLE_ELEM_ARRAY);
					}
				}
				// 普通字段值進行值拷貝
				Method srcMethod = (Method) srcMethodMap.get("GET"
						+ currentFieldName.toUpperCase());
				if (srcMethod == null) {
					srcMethod = (Method) srcMethodMap.get("IS"
							+ currentFieldName.toUpperCase());
				}
				// 找到相應方法調用
				if (srcMethod != null
						&& supportTypeMap.containsKey(srcMethod.getReturnType())) {
					try {
						Object value = srcMethod.invoke(source, EMPTY_ARRAY);
						SINGLE_ELEM_ARRAY[0] = value;
						target.invoke(dest, SINGLE_ELEM_ARRAY);
					} catch (IllegalArgumentException e) {
						// 如果是類型不匹配，則打印不匹配的屬性
						// System.out.println("----Copy ERROR (類型不匹配): " +
						// source.getClass().getSimpleName() + "." +
						// srcMethod.getName() + " ---> " +
						// dest.getClass().getSimpleName() + "." +
						// target.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 字段特殊處理
				if (intercepteMethod != null) {
					intercepteMethod.invoke(caller, new Object[] { dest, target,
							source });
				}
			}
		}
	}
	
	/**
	 * 去除大對象轉換后的List屬性中的原對象（專用于批單追回）所謂原對象即主鍵—proposalNo或聯合主鍵(ID)相同的第一個對象
	 * @param object  原大對象
	 * @param proposalNo 要保單號
	 * @return Object 去除后的大對象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object removeObjOld(Object object,String proposalNo) throws Exception {
		
		Class objectClass = object.getClass();
	
		// 獲取所有Getter方法
		Map<String,Method> getterMethods = new HashMap<String,Method>();
		
		String objKey = cacheManager.generateCacheKey("removeObjOld", objectClass.getName(),"getter");		
		Object objResult = cacheManager.getCache(objKey);
		if(objResult!=null) {
			getterMethods = (Map<String,Method>)objResult;
		} else {
			getterMethods = getGetterMap(objectClass);
			cacheManager.putCache(objKey, getterMethods);
		}
		// 遍歷獲取返回類型為List的Getter方法
		Set objSet = getterMethods.keySet();
		Iterator it = objSet.iterator();
		while(it.hasNext()) {
			Object strKey = it.next();
			Method getterMethod = getterMethods.get(strKey);			
			if (getterMethod.getReturnType() == List.class) {
				List<Object> subObjects = (List<Object>) getterMethod.invoke(object,
						EMPTY_ARRAY);
				if (subObjects == null) {
					continue;
				}
				// 存儲所有子對象<id/applyNo，索引>
				Map<String,Integer> objMap = new HashMap<String,Integer>();
				List<Integer> index = new ArrayList<Integer>();//存儲需要移除的對象索引
				
				for(int i=0; i<subObjects.size(); i++) {
					Object subObj = subObjects.get(i);
					
					//遞歸處理子對象的List
					removeObjOld(subObj,proposalNo);			
					
					Class subObjClass = subObj.getClass();
					// 獲取子對象的所有getter方法
					Map<String,Method> subGetterM = null;					
					objKey = cacheManager.generateCacheKey("removeObjOld", subObjClass.getName(),"getter");		
					objResult = cacheManager.getCache(objKey);
					if(objResult!=null) {
						subGetterM = (Map<String,Method>)objResult;
					} else {
						subGetterM = getGetterMap(subObjClass);
						cacheManager.putCache(objKey, subGetterM);
					}					
					// 聯合主鍵
					if(subGetterM.containsKey("getId")) {
						// 獲取id對象及其所有Getter方法
						Object idObj = subGetterM.get("getId").invoke(subObj, EMPTY_ARRAY);
						Class idObjClass = idObj.getClass();						
						// 存放id對象的所有屬性值的組合值
						StringBuilder keyValue = new StringBuilder();
						// 使用TreeMap的自動排序功能
						TreeMap<String,Method> idGetters = new TreeMap<String,Method>();
						objKey = cacheManager.generateCacheKey("removeObjOld", idObjClass.getName(),"getter");		
						objResult = cacheManager.getCache(objKey);
						if(objResult!=null) {
							idGetters = (TreeMap<String,Method>)objResult;
						} else {
							idGetters = getGetterTreeMap(idObjClass);
							cacheManager.putCache(objKey, idGetters);
						}
						
						String proNo = (String)idGetters.get("getProposalNo").invoke(idObj, EMPTY_ARRAY);
						if(proNo == null || proNo.equals("")) {
							// 為從要追回的批單追加的數據添加投保單號
							Method setProposalNo = idObjClass.getDeclaredMethod("setProposalNo", 
																		new Class[]{String.class});
							setProposalNo.invoke(idObj,(Object[])new String[]{proposalNo});
						}
						
						Set idSet = idGetters.keySet();
						Iterator iter = idSet.iterator();
						while(iter.hasNext()) {
							Object idKey = iter.next();
							Object objValue = idGetters.get(idKey).invoke(idObj, EMPTY_ARRAY);
							// 將每一個id對象的屬性值按順序組合在一起，便于比較
							keyValue.append(objValue.toString());
						}
						
						// 將以前出現過id的子對象的索引值放入index
						if(objMap.containsKey(keyValue.toString())){
							index.add(objMap.get(keyValue.toString()));
						}
						// 以id屬性值的組合值為key，子對象索引為value存入map
						objMap.put(keyValue.toString(), new Integer(i));
						
					} else {// 主鍵為proposalNo
						String proNo = (String)subGetterM.get("getProposalNo").invoke(subObj, EMPTY_ARRAY);
						if(proNo == null || proNo.equals("")) {
							// 為新增數據添加投保單號
							Method setProposalNo = subObjClass.getDeclaredMethod("setProposalNo", 
																		new Class[]{String.class});
							setProposalNo.invoke(subObj,(Object[])new String[]{proposalNo});
						}
						
						// 將以前出現過proposalNo值的子對象的索引值放入index
						if(objMap.containsKey(proNo)){
							index.add(objMap.get(proNo));
						}
						// 以endorseNo的值為key，子對象索引為value存入map
						objMap.put(proNo, new Integer(i));
					}
					// 新增加的記錄，p表后加的記錄也應該刪除
					String flag = (String)subGetterM.get("getFlag").invoke(subObj, EMPTY_ARRAY);
					//modify by sunjiuhua 20111117
					if(StringUtils.isNotBlank(flag) && "I".equals(flag.substring(0, 1))) {
						// 此處只設標志位而不真刪
						Method setFlag = subObjClass.getDeclaredMethod("setFlag", String.class);
						if(subObjClass == PrpCplan.class) {
							setFlag.invoke(subObj,"");
//						} else if(subObjClass == PrpCitemKind.class 
//								|| subObjClass == PrpCitemKindSub.class) {
//							setFlag.invoke(subObj,"D");
//							
//							Method setPremium = subObjClass.getDeclaredMethod("setPremium", 
//									BigDecimal.class);
//							setPremium.invoke(subObj,BigDecimal.ZERO);
//							Method setAmount = subObjClass.getDeclaredMethod("setAmount", 
//									BigDecimal.class);
//							setAmount.invoke(subObj,BigDecimal.ZERO);
//						} else if(subObjClass == PrpCinsuredIdvList.class) {
//							setFlag.invoke(subObj,"D");
//							
//							Method setPremium = subObjClass.getDeclaredMethod("setPremium", 
//									BigDecimal.class);
//							setPremium.invoke(subObj,BigDecimal.ZERO);
						} else if(subObjClass == PrpCration.class) {
							setFlag.invoke(subObj,"D");
							
							Method setPremium = subObjClass.getDeclaredMethod("setQuantity", 
									Integer.class);
							setPremium.invoke(subObj,0);
						} else {
							setFlag.invoke(subObj,"D");
						}
						
					//B的不變
					} else if(StringUtils.isNotBlank(flag) && "B".equals(flag.substring(0, 1))) {
					// U的還是為“U"
					} else if(StringUtils.isNotBlank(flag)) {
						Method setFlag = subObjClass.getDeclaredMethod("setFlag", 
								new Class[]{String.class});
						setFlag.invoke(subObj,"U");
					}
				}
				// 倒敘移除對象，防止移除對象時索引變化造成錯誤
				int size = index.size();
				// add by sunjiuhua 20110804 原因：排序
				Collections.sort(index);
				for(int i=(size-1); i>=0; i--) {
					subObjects.remove(index.get(i).intValue());
				}
			}			
		}
		return object;
	}

	/**
	 * 為大對象的某特定字段賦值
	 * @param object 大對象
	 * @param fieldName 字段名稱（以大寫字母開頭）
	 * @param value 要設置的值
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void setValueforSpecificField(Object object,
			String fieldName, Object value) throws Exception {
		Object[] SINGLE_ELEM_ARRAY = new Object[1];
		//modefied by zhangruofei 20130305
		if(null!=object) {
			Class objectClass = object.getClass();
			// 獲取所有getter方法
			// 獲取所有getter方法
			//性能調優 20110401 liyu mod start by 緩存getGetter；
	//		List<Method> getterMethods = getGetter(objectClass);
			List<Method> getterMethods=new ArrayList();
			String objKey = cacheManager.generateCacheKey("BoCopyUtil", objectClass.getName(),"getter");
			Object objResult = cacheManager.getCache(objKey);
			if(objResult!=null){
				getterMethods = (List<Method>)objResult;
			}else{
				getterMethods = getGetter(objectClass);
				cacheManager.putCache(objKey, getterMethods);
			}
			//性能調優 20110401 liyu mod end by 緩存getGetter；
			// 獲取方法實例
			Class paramType = null;
			String currentFieldName;
			// 獲取所有字表
			for (Method method : getterMethods) {
				currentFieldName = method.getName().substring(3);
				// 一對多子表處理
				if (method.getReturnType() == List.class) {
					List<Object> subObjects = (List<Object>) method.invoke(object,
							EMPTY_ARRAY);
					if (subObjects == null) {
						continue;
					}
					// 循環遍歷子對象
					for (Object obj : subObjects) {
						setValueforSpecificField(obj, fieldName, value);
					}
					// 聯合主鍵處理
				} else if (currentFieldName.equals("Id")) {
					Object objectID = method.invoke(object, EMPTY_ARRAY);
					setValueforSpecificField(objectID, fieldName, value);
				}
				//應該單獨判斷 modify by sunjiuhua 20110707
				if (currentFieldName.equals(fieldName)) {
					// 找到了要賦值的屬性，執行賦值（1、加if是為了不重復獲取，2、在這里獲取是避免NoSuchMethodException）
					if (paramType == null) {
						// 首先得到getter方法（用于獲取返回值的類型）
						Method getterMethod = objectClass.getMethod("get"
								+ fieldName);
						// 獲取其返回值
						paramType = getterMethod.getReturnType();
					}
					// 得到其setter方法
					Method setterMethod = objectClass.getMethod("set" + fieldName,
							paramType);
	
					SINGLE_ELEM_ARRAY[0] = value;
					setterMethod.invoke(object, SINGLE_ELEM_ARRAY);
				}
		  }
		}
	}
	
	/**
	 * 獲取對象的相應屬性的值
	 * @param object 對象
	 * @param String 要獲取值的屬性
	 * @return Object 返回值 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object getValueforSpecificField(Object object,
			String fieldName) throws Exception {
		Class objectClass = object.getClass();
		// 獲取所有getter方法
		List<Method> getterMethods=new ArrayList();
		String objKey = cacheManager.generateCacheKey("BoCopyUtil", objectClass.getName(),"getter");
		Object objResult = cacheManager.getCache(objKey);
		if(objResult!=null){
			getterMethods = (List<Method>)objResult;
		}else{
			getterMethods = getGetter(objectClass);
			cacheManager.putCache(objKey, getterMethods);
		}
		Object fieldValue = null;
		String currentFieldName;
		// 獲取所有字表
		for (Method method : getterMethods) {
			currentFieldName = method.getName().substring(3);
			// 一對多子表不處理
			if (method.getReturnType() == List.class) {
				
				continue;
			} else if (currentFieldName.equals("Id")) {// 聯合主鍵處理
				Object objectID = method.invoke(object, EMPTY_ARRAY);
				fieldValue = getValueforSpecificField(objectID, fieldName);
			}
			if (!currentFieldName.equalsIgnoreCase(fieldName)) {
				continue;
			}
			fieldValue = method.invoke(object, EMPTY_ARRAY);
			break;
		}
		return fieldValue;
	}
	
	/**
	 * 獲取類及其父類的所有set方法（jar包中方法的效率提升的版本）
	 * @param cl  待獲取的類
	 * @return List Method對象集合
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
		// 遞歸獲取父類的set方法
		if (cl != Object.class) {
			list.addAll(getSetter(cl));
		}
		return list;
	}

	/**
	 * 獲取類及其父類的所有get方法（jar包中方法的效率提升的版本）
	 * @param cl 待獲取的類
	 * @return List Method對象集合
	 */
	private static List<Method> getGetter(Class cl) {
		List list = new ArrayList();
		Method[] methods = cl.getDeclaredMethods();
		int lgn=methods.length;
		for (int i = 0; i < lgn; ++i) {
			Method method = methods[i];
			String methodName = method.getName();
			// 以set或is開頭的方法
			if (methodName.startsWith("get") || methodName.startsWith("is")) {
				list.add(method);
			}
		}
		cl = cl.getSuperclass();
		// 遞歸獲取父類的get方法
		if (cl != Object.class) {
			list.addAll(getGetter(cl));
		}
		return list;
	}
	
	/**
	 * 獲取類及其父類的所有get方法（jar包中方法的效率提升的版本）
	 * @param cl 待獲取的類
	 * @return Map<methodName,Method> Map對象
	 */
	private static Map<String,Method> getGetterMap(Class cl) {
		Map<String,Method> getterMap = new HashMap<String,Method>();
		Method[] methods = cl.getDeclaredMethods();
		int lgn=methods.length;
		for (int i = 0; i < lgn; ++i) {
			Method method = methods[i];
			String methodName = method.getName();			
			// 以get或is開頭的方法
			if (methodName.startsWith("get") || methodName.startsWith("is")) {
				
				getterMap.put(methodName, method);
			}
		}
		cl = cl.getSuperclass();
		// 遞歸獲取父類的get方法
		if (cl != Object.class) {
			getterMap.putAll(getGetterMap(cl));
		}
		return getterMap;
	}
	
	/**
	 * 獲取類及其父類的所有get方法（jar包中方法的效率提升的版本）
	 * @param cl 待獲取的類
	 * @return TreeMap<methodName,Method> 為使用其自動排序功能
	 */
	private static TreeMap<String,Method> getGetterTreeMap(Class cl) {
		TreeMap<String,Method> getterMap = new TreeMap<String,Method>();
		Method[] methods = cl.getDeclaredMethods();
		int lgn=methods.length;
		for (int i = 0; i < lgn; ++i) {
			Method method = methods[i];
			String methodName = method.getName();			
			// 以set或is開頭的方法
			if (methodName.startsWith("get") || methodName.startsWith("is")) {
				
				getterMap.put(methodName, method);
			}
		}
		cl = cl.getSuperclass();
		// 遞歸獲取父類的get方法
		if (cl != Object.class) {
			getterMap.putAll(getGetterMap(cl));
		}
		return getterMap;
	}

	public static void main(String[] args) throws Exception {

		PrpCmain prpCmain = new PrpCmain();
		long start = System.currentTimeMillis();
		BoCopyUtil.convert(prpCmain, new PrpCmain(), new BoCopyUtil(), null,
				null);
		long end = System.currentTimeMillis();
		// System.out.println(end - start);

		long start2 = System.currentTimeMillis();
		BoCopyUtil
				.setValueforSpecificField(prpCmain, "ProposalNo", "321321321");
		long end2 = System.currentTimeMillis();
		// System.out.println(end2 - start2);
	}
}
