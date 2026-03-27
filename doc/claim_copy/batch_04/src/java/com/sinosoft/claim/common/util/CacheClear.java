package com.sinosoft.claim.common.util;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.ServiceFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.sinosoft.claim.workflow.vo.WorkFlowDto;
import com.sinosoft.one.bpm.cache.ProcessInstanceBOCache;
import com.sinosoft.one.bpm.support.BpmServiceSupport;

/***
 * 用於緩存清理與重置
 * @author 中科軟
 */
public class CacheClear {

	/***
	 * 重置工作流
	 */
	public static void resetWorkFlowSwitch(String status) {
		if ("1".equals(status)) {// 開啟JBPM
			WorkFlowDto.setWorkFlowSwitch(true);
		} else if ("0".equals(status)) {
			WorkFlowDto.setWorkFlowSwitch(false);
		}
	}

	/***
	 * 清理用戶的機構處理權限
	 * @throws Exception
	 */
	public static void resetUserComcodePower(String userCode) throws Exception {
		try {
			Field field = com.sinosoft.platform.bl.action.custom.BLPowerAction.class.getDeclaredField("cacheMap");
			field.setAccessible(true);
			@SuppressWarnings("unchecked")
			Map<String, Object> cacheMap = (Map<String, Object>) field.get(null);
			List<String> keys = new ArrayList<String>();
			keys.addAll(cacheMap.keySet());
			for (String key : keys) {
				if (key.indexOf("^" + userCode + "^") > 0) {
					cacheMap.remove(key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	/***
	 * 清理用戶的險種處理權限
	 * @throws Exception
	 */
	public static void resetUserRiskPower(String userCode) throws Exception {
		try {
			CacheService cacheManager = CacheManager.getInstance("PowerServiceSpringImpl");
//			String key = cacheManager.generateCacheKey("addRiskPower",userCode,comCode,"","SwfLog","claim");
//			if(cacheManager.containsKey(key)){
				cacheManager.clearCache("addRiskPower",userCode);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/***
	 * 清理險種使用模板配置相關緩存
	 */
	public static void resetWorkFlowSwfModelUseModelNo(String riskCode){
		try {
			CacheService cacheManager = CacheManager.getInstance("WorkFlow");
			cacheManager.clearCache("WorkFlowSwfModelUseModelNo",riskCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/***
	 * 清理險工作流模板配置相關緩存
	 */
	public static void resetWorkFlow(String keyType, String modelNoStr) {
		try {
			//註銷拒賠節點訊息 、工作流模板配置的第一個節點訊息、自動節點流轉的條件、流程路線流轉的條件、流轉路線、配置節點訊息
			String[] keyTypes = new String[] { "WorkFlowCancelSwfNode", "WorkFlowFirstSwfNode", "WorkFlowSwfConditionForAutoTask", "WorkFlowSwfConditionForPath", "WorkFlowSwfPath", "WorkFlowSwfNode" };
			List<String> keyTypesList = Arrays.asList(keyTypes);
			if (keyTypesList.contains(keyTypes)) {
				CacheService cacheManager = CacheManager.getInstance("WorkFlow");
				int modelNo = Integer.parseInt(modelNoStr);
				if (modelNo == 0) {
					cacheManager.clearCache(keyType);
				} else {
					cacheManager.clearCache(keyType, modelNo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/***
	 * 清理用戶的機構處理權限
	 * @param keyId  processId + "_" + businessId
	 * @throws Exception
	 */
	public static void resetProcessInstanceIdCache(String keyId) throws Exception {
		try {
			BpmServiceSupport bpmServiceSupport = (BpmServiceSupport)ServiceFactory.getService("bpmServiceSupport");
			Field field = BpmServiceSupport.class.getDeclaredField("processInstanceBOCache");
			field.setAccessible(true);
			ProcessInstanceBOCache processInstanceBOCache = (ProcessInstanceBOCache) field.get(bpmServiceSupport);
			if(!CommonUtils.isEmpty(keyId)){
				String keys[] = keyId.split(",");
				if (keys.length == 2) {
					processInstanceBOCache.removeInstanceIdFromCache(keys[0], keys[1]);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
