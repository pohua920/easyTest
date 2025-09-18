package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetPaid;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FetPaidService {

	public int countFetPaid(Map params) throws SystemException, Exception;
	
	public Result findFetPaidByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetPaidByParams(Map params) throws SystemException, Exception;

	public Result findFetPaidByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetPaid(FetPaid entity) throws SystemException, Exception;

	public Result insertFetPaid(FetPaid entity) throws SystemException, Exception;

	public Result removeFetPaid(String transactionId) throws SystemException, Exception;
	
	public Result findFetPaidForCheckAccount(String contractId) throws SystemException, Exception;
	
}
