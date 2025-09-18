package com.tlg.aps.bs.firBotRenewalFileService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
public interface FirBotGenFileService {
	
	public Result genReFile(String batchNo, String userId) throws Exception;
	
	public Result genEnFile(String batchNo, String userId) throws SystemException, Exception;
	
	public Result genRnproFile(String batchNo, String userId, String rnYyyymm) throws Exception;
}
