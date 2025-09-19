package com.tlg.msSqlSms.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.UsageLog;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface UsageLogService {

	public int countUsageLog(Map params) throws SystemException, Exception;
	
	public Result findUsageLogByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findUsageLogByParams(Map params) throws SystemException, Exception;
	
	public Result findUsageLogBySubmitHour(Map params) throws SystemException, Exception;

	public Result findUsageLogByUK(String serial) throws SystemException, Exception;

	public Result updateUsageLog(UsageLog usageLog) throws SystemException, Exception;
	
	public Result updateUsageLogResp(UsageLog usageLog) throws SystemException, Exception;

	public Result insertUsageLog(UsageLog usageLog) throws SystemException, Exception;
	
	public Result removeUsageLog(String serial) throws SystemException, Exception;

}
