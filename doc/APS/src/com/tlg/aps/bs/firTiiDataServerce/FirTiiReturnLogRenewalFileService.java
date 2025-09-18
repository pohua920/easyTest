package com.tlg.aps.bs.firTiiDataServerce;

import java.util.List;

/* mantis：FIR0580，處理人員：BJ085，需求單編號：FIR0580 保發中心-住火保批資料回饋檔回存資料庫 */
public interface FirTiiReturnLogRenewalFileService {
	
	public void insertReturnLogList(String fileName, String userId, List<String> fileDataList) throws Exception;
}
