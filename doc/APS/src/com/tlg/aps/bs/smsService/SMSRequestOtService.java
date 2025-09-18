package com.tlg.aps.bs.smsService;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.SMSRequest;
import com.tlg.util.Result;

public interface SMSRequestOtService {
	
	public Result findTopSMSRequestByParams(Map params) throws SystemException, Exception;

	public Result insertSMSRequest(SMSRequest smsRequest) throws SystemException, Exception;
	
	public Result removeSMSRequest(String serial) throws SystemException, Exception;
	
	public Result updateSMSRequest(SMSRequest smsRequest) throws SystemException, Exception;
	
	public void removeSMSRequestNullData() throws SystemException, Exception;
}
