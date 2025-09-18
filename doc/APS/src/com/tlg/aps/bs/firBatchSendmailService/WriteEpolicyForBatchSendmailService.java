package com.tlg.aps.bs.firBatchSendmailService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
public interface WriteEpolicyForBatchSendmailService {

	public Result writeEpolicyForBatchSendmail() throws SystemException,Exception;
	
}
