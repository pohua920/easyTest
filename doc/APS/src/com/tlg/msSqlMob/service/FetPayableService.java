package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetPayable;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FetPayableService {

	public int countFetPayable(Map params) throws SystemException, Exception;
	
	public Result findFetPayableByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetPayableByParams(Map params) throws SystemException, Exception;

	public Result findFetPayableByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetPayable(FetPayable entity) throws SystemException, Exception;

	public Result insertFetPayable(FetPayable entity) throws SystemException, Exception;

	public Result removeFetPayable(String transactionId) throws SystemException, Exception;
	
}
