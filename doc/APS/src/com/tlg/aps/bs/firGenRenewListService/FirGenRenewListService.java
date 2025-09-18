package com.tlg.aps.bs.firGenRenewListService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/* mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
public interface FirGenRenewListService {
	
	public Result runToGenerateList(String rnYymm, Date excuteTime, String userId, String programId) throws SystemException, Exception;
	
	public Map<String, String> updateRenewList(String tmpBatchNo, String userId) throws SystemException, Exception;
}
