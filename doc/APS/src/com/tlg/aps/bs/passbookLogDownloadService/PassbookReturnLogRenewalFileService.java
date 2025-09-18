package com.tlg.aps.bs.passbookLogDownloadService;

import java.util.List;

/* mantis：OTH0138，處理人員：CC009，需求單編號：OTH0138 保發中心_保單存摺回饋檔回存資料庫規格 */
public interface PassbookReturnLogRenewalFileService {
	
	public void insertOthBatchPassbookReturnLogList(String fileName, String userId, List<String> fileDataList) throws Exception;
}
