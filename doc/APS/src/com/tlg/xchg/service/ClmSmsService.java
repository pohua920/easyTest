package com.tlg.xchg.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;
import com.tlg.xchg.entity.ClmSms;

public interface ClmSmsService {

	public int countClmSms(Map params) throws SystemException, Exception;
	
	public Result findClmSmsByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findClmSmsByParams(Map params) throws SystemException, Exception;

	public Result findClmSmsByUK(String transactionId) throws SystemException, Exception;

	public Result updateClmSms(ClmSms clmSms) throws SystemException, Exception;

	public Result insertClmSms(ClmSms clmSms) throws SystemException, Exception;

	public Result removeClmSms(String transactionId) throws SystemException, Exception;

	
}
