package com.tlg.aps.bs.firUbNewPolicyService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/** mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程  **/
public interface FirUbBackFileService {
	
	public Result runToReceiveData(String userId, Date excuteTime, String programId, String type) throws SystemException, Exception;
	
	public Map<String,String> generateFile(String batchNo, String userId, String programId, String type) throws SystemException, Exception;
}
