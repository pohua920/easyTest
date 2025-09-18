package com.tlg.aps.bs.firBatchSendmailService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;
import com.tlg.util.UserInfo;

public interface BatchSendmailService {

	public Result batchSendmail(UserInfo userInfo) throws SystemException,Exception;
	
}
