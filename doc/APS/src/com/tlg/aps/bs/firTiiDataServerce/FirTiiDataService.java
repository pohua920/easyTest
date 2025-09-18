package com.tlg.aps.bs.firTiiDataServerce;

import java.util.Date;

import com.tlg.util.Result;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public interface FirTiiDataService {

	public Result callSpToGenData(String type, String businessNo, Date undate, 
			String userId, String programId) throws Exception;
	
	public Result generateFileAndUpload(String userId, String batchNo, 
			String type, String programId) throws Exception;
	
}
