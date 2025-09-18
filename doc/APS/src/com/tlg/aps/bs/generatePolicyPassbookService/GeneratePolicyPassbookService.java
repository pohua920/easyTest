package com.tlg.aps.bs.generatePolicyPassbookService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/* mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
public interface GeneratePolicyPassbookService {
	
	public Result runToGeneratePolicyPassbook(String userId, String programId) throws SystemException, Exception;
	
	public String generatePolicyPassbook(String userId, String batchNo, String type) throws SystemException, Exception;
}
