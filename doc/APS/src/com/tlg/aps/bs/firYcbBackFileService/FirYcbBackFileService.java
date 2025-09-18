package com.tlg.aps.bs.firYcbBackFileService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/**
 * mantis：FIR0680，處理人員：DP0714，住火_元大回饋檔產生排程規格
 */
public interface FirYcbBackFileService {
	
	public Result runToGenerateFile(Date excuteTime, String userId, String programId) throws SystemException, Exception;
	
	public Map<String,String> generateFile(String batchNo, String userId, String programId, String type) throws SystemException, Exception;
}
