package com.tlg.aps.bs.firPanhsinFeedbackFile;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface FirProcessPanhsinFileService {
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程
	   mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 
	   mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業 start */
	
	public Result RunToGenerateFiles(String userId,Date excuteTime,String programId) throws SystemException, Exception;
	
	public Result generateFile(Map<String,String> params, String userId) throws SystemException, Exception;

}
