package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetMobilePolicyDevice;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FetMobilePolicyDeviceService {

	public int countFetMobilePolicyDevice(Map params) throws SystemException, Exception;
	
	public Result findFetMobilePolicyDeviceByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFetMobilePolicyDeviceByParams(Map params) throws SystemException, Exception;

	public Result findFetMobilePolicyDeviceByUK(String transactionId) throws SystemException, Exception;

	public Result updateFetMobilePolicyDevice(FetMobilePolicyDevice fetMobilePolicyDevice) throws SystemException, Exception;

	public Result insertFetMobilePolicyDevice(FetMobilePolicyDevice fetMobilePolicyDevice) throws SystemException, Exception;

	public Result removeFetMobilePolicyDevice(String transactionId) throws SystemException, Exception;
	
}
