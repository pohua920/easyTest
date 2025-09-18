package com.tlg.aps.bs.passbookLogDownloadService;

import com.tlg.util.Result;

/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 */
public interface PassbookReturnLogDownloadService {
	
	public Result runToReceiveData() throws Exception;
}
