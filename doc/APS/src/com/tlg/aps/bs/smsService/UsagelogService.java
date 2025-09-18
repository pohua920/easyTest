package com.tlg.aps.bs.smsService;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.UsageLog;
import com.tlg.util.Result;

public interface UsagelogService {

	public Result insertUsageLog(UsageLog usageLog) throws SystemException, Exception;
	
	public int countUsageLog(Map params) throws SystemException, Exception;
	
	public Result findUsageLogByParams(Map params) throws SystemException, Exception;
	
	public Result updateUsageLogResp(UsageLog usageLog) throws SystemException, Exception;
	
	public Result findUsageLogBySubmitHour(Map params) throws SystemException, Exception;
}
