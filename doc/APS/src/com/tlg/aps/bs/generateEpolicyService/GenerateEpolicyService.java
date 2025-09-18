package com.tlg.aps.bs.generateEpolicyService;

import java.util.Map;

import com.tlg.exception.SystemException;

/** HAS0094 ，處理人員：BI086，需求單編號：HAS0094提供外部呼叫以產生PA電子保單  **/
public interface GenerateEpolicyService {

	
	public Map<String, Object> queryToGeneratePAEpolicyService(String executeApp,String executeName) throws SystemException, Exception;
	
	public Map<String, Object> queryToGenerateTAEpolicyService(String executeApp,String executeName) throws SystemException, Exception;
	
	
	/**
	 * 
	 * 產生PA電子保單
	 * 
	 * @param policyNo
	 * @param executeApp
	 * @param executeName
	 * @return 0→成功，1→檔案已存在，-1→失敗
	 * @throws SystemException
	 * @throws Exception
	 */
	public String generatePAEpolicyService(String policyNo, String executeApp, String executeName, String riskCode) throws SystemException, Exception;

}
