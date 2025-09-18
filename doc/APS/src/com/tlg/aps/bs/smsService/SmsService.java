package com.tlg.aps.bs.smsService;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface SmsService {

	public Result batchSendMessage() throws SystemException,Exception;
	
	public void batchHandleExceMessage() throws SystemException,Exception;
	
	public Result queryDeliverReport() throws SystemException,Exception;
}
