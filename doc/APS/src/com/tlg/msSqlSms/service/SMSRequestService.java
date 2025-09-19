package com.tlg.msSqlSms.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.SMSRequest;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface SMSRequestService {

	public int countSMSRequest(Map params) throws SystemException, Exception;
	
	public Result findSMSRequestByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findSMSRequestByParams(Map params) throws SystemException, Exception;
	
	public Result findTopSMSRequestByParams(Map params) throws SystemException, Exception;

	public Result findSMSRequestByUK(String serial) throws SystemException, Exception;

	public Result updateSMSRequest(SMSRequest smsRequest) throws SystemException, Exception;

	public Result insertSMSRequest(SMSRequest smsRequest) throws SystemException, Exception;
	
	public Result removeSMSRequest(String serial) throws SystemException, Exception;
	
	public void removeSMSRequestNullData() throws SystemException, Exception;

}
