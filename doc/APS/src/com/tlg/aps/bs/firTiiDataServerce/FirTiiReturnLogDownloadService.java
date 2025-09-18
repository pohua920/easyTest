package com.tlg.aps.bs.firTiiDataServerce;

import com.tlg.util.Result;

/* mantis：FIR0580，處理人員：BJ085，需求單編號：FIR0580 保發中心-住火保批資料回饋檔回存資料庫 */
public interface FirTiiReturnLogDownloadService {
	
	public Result runToReceiveData() throws Exception;
}
