package com.tlg.aps.bs.firBotFileService;

import java.util.Date;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/* mantis：FIR0314，處理人員：BJ085，需求單編號：FIR0314 台銀保經-APS新件要保檔產生排程
   mantis：FIR0315，處理人員：BJ085，需求單編號：FIR0315 台銀保經-APS保單檔產生排程 */
public interface GenerateBotInsuredFileService {
	
	public Result RunToGenerateFiles(String userId,Date excuteTime,String programId) throws SystemException, Exception;
	
	public Map<String,String> generateFileAndUpload(String batchNo,String userId,String programId) throws Exception;

}
