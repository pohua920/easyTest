package com.tlg.msSqlAs400.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlAs400.entity.CtbcClmsmsRm;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface CtbcClmsmsRmService {

	public int countCtbcClmsmsRm(Map params) throws SystemException, Exception;
	
	public Result findCtbcClmsmsRmByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCtbcClmsmsRmByParams(Map params) throws SystemException, Exception;

	public Result findCtbcClmsmsRmByUK(String transactionId) throws SystemException, Exception;

	public Result updateCtbcClmsmsRm(CtbcClmsmsRm ctbcClmsmsRm) throws SystemException, Exception;

	public Result insertCtbcClmsmsRm(CtbcClmsmsRm ctbcClmsmsRm) throws SystemException, Exception;

	public Result removeCtbcClmsmsRm(String transactionId) throws SystemException, Exception;
	
}
