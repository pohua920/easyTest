package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetMobilePolicySales;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FetMobilePolicySalesService {

	public int countFetMobilePolicySales(Map params) throws SystemException, Exception;
	
	public Result findFetMobilePolicySalesByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetMobilePolicySalesByParams(Map params) throws SystemException, Exception;

	public Result findFetMobilePolicySalesByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetMobilePolicySales(FetMobilePolicySales fetMobilePolicySales) throws SystemException, Exception;

	public Result insertFetMobilePolicySales(FetMobilePolicySales fetMobilePolicySales) throws SystemException, Exception;

	public Result removeFetMobilePolicySales(String transactionId) throws SystemException, Exception;
	
	public boolean isUnique(FetMobilePolicySales fetMobilePolicySales) throws SystemException, Exception;
}
