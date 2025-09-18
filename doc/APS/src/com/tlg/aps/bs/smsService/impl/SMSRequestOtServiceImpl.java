package com.tlg.aps.bs.smsService.impl;

import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.smsService.SMSRequestOtService;
import com.tlg.exception.SystemException;
import com.tlg.msSqlSms.entity.SMSRequest;
import com.tlg.msSqlSms.service.SMSRequestService;
import com.tlg.util.Constants;
import com.tlg.util.Message;
import com.tlg.util.Result;

@Transactional(value = "msSqlSmsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class SMSRequestOtServiceImpl implements SMSRequestOtService {

	private SMSRequestService smsRequestService;

	@Override
	public Result insertSMSRequest(SMSRequest smsRequest) throws SystemException,
			Exception {

		if (smsRequest == null) {
			throw new SystemException(
					Message.getMessage(Constants.DATA_NOT_EXIST));
		}
		Result result = smsRequestService.insertSMSRequest(smsRequest);
		if (result.getResObject() != null) {
			result.setMessage(Message.getMessage(Constants.SAVE_DATA_SUCCESS));
			result.setResObject(smsRequest);
			return result;
		}
		return result;
	}
	
	@Override
	public Result removeSMSRequest(String serial) throws SystemException, Exception {

		return smsRequestService.removeSMSRequest(serial);
	}
	
	@Override
	public Result findTopSMSRequestByParams(Map params) throws SystemException, Exception {
		return smsRequestService.findTopSMSRequestByParams(params);
	}
	
	@Override
	public Result updateSMSRequest(SMSRequest smsRequest) throws SystemException, Exception {
		return smsRequestService.updateSMSRequest(smsRequest);
	}
	
	@Override
	public void removeSMSRequestNullData() throws SystemException, Exception {
		smsRequestService.removeSMSRequestNullData();
	}

	public com.tlg.msSqlSms.service.SMSRequestService getSmsRequestService() {
		return smsRequestService;
	}

	public void setSmsRequestService(com.tlg.msSqlSms.service.SMSRequestService smsRequestService) {
		this.smsRequestService = smsRequestService;
	}



}
