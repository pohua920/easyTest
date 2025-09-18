package com.tlg.aps.bs.firPanhsinBackFileService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
public interface FirPanhsinBackFileService {
	
	public Result runToGenerateFile(String type, Date excuteTime, String userId, String programId) throws SystemException, Exception;
	
	public Map<String,String> generateFile(String batchNo, String userId, String programId, String type) throws SystemException, Exception;
}
