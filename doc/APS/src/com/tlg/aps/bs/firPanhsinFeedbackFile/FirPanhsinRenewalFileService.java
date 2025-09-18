package com.tlg.aps.bs.firPanhsinFeedbackFile;

import java.util.Date;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface FirPanhsinRenewalFileService {
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程 start*/
	
	public Result runToReceiveData(String userId,Date excuteTime,String programId) throws SystemException, Exception;
}
