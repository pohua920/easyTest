package com.tlg.aps.bs.smsService.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.smsService.UsagelogService;
import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.UsageLog;
import com.tlg.msSqlSms.service.UsageLogService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "msSqlSmsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class UsagelogServiceImpl implements UsagelogService {

	private UsageLogService usageLogService;

	@Override
	public Result insertUsageLog(UsageLog usageLog) throws SystemException,
			Exception {

		if (usageLog == null) {
			throw new SystemException(
					Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = usageLogService.insertUsageLog(usageLog);
		if (result.getResObject() != null) {
			result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
			result.setResObject(usageLog);
			return result;
		}
		return result;
	}
	
	@Override
	public Result findUsageLogByParams(Map params) throws SystemException,
			Exception {
		return usageLogService.findUsageLogByParams(params);
	}
	
	@Override
	public int countUsageLog(Map params) throws SystemException, Exception {
		return usageLogService.countUsageLog(params);
	}
	
	@Override
	public Result updateUsageLogResp(UsageLog usageLog) throws SystemException,
			Exception {
		return usageLogService.updateUsageLogResp(usageLog);
	}
	
	@Override
	public Result findUsageLogBySubmitHour(Map params) throws SystemException,
			Exception {
		return usageLogService.findUsageLogBySubmitHour(params);
	}

	public UsageLogService getUsageLogService() {
		return usageLogService;
	}

	public void setUsageLogService(UsageLogService usageLogService) {
		this.usageLogService = usageLogService;
	}

}
