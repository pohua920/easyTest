package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetMobilePolicy;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FetMobilePolicyService {

	public int countFetMobilePolicy(Map params) throws SystemException, Exception;
	
	public int countPolicyByStatusWait() throws SystemException, Exception;
	
	public Result findFetMobilePolicyByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetMobilePolicyByParams(Map params) throws SystemException, Exception;

	public Result findFetMobilePolicyByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetMobilePolicy(FetMobilePolicy fetMobilePolicy) throws SystemException, Exception;

	public Result insertFetMobilePolicy(FetMobilePolicy fetMobilePolicy) throws SystemException, Exception;

	public Result removeFetMobilePolicy(String transactionId) throws SystemException, Exception;
	
	public Result findPolicyNoByWait() throws SystemException, Exception;
	
}
