package com.tlg.aps.bs.mobclaimService;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/** mantis：MOB0018，處理人員：BJ085，需求單編號：MOB0018 理賠資料提交及列印作業 */
public interface MobSubmitAndPrintService {
	
	public Result submitClaimData(String userId, String wda00) throws Exception;
	
	public Result genClaimListFile(Map params) throws SystemException, Exception;
}
