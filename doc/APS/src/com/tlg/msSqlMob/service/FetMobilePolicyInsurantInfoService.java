package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetMobilePolicyInsurantInfo;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FetMobilePolicyInsurantInfoService {

	public int countFetMobilePolicyInsurantInfo(Map params) throws SystemException, Exception;
	
	public Result findFetMobilePolicyInsurantInfoByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetMobilePolicyInsurantInfoByParams(Map params) throws SystemException, Exception;

	public Result findFetMobilePolicyInsurantInfoByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetMobilePolicyInsurantInfo(FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo) throws SystemException, Exception;

	public Result insertFetMobilePolicyInsurantInfo(FetMobilePolicyInsurantInfo fetMobilePolicyInsurantInfo) throws SystemException, Exception;

	public Result removeFetMobilePolicyInsurantInfo(String transactionId) throws SystemException, Exception;
	
}
