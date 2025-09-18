package com.tlg.msSqlMob.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.msSqlMob.entity.FetMobileEpolicy;
import com.tlg.util.Result;

public interface FetMobileEpolicyService {

	public int countFetMobileEpolicy(Map params) throws SystemException, Exception;

	public Result findFetMobileEpolicyByParams(Map params) throws SystemException, Exception;

	public Result findFetMobileEpolicyByUK(String oid) throws SystemException, Exception;

	public Result updateFetMobileEpolicy(FetMobileEpolicy fetMobileEpolicy) throws SystemException, Exception;

	public Result insertFetMobileEpolicy(FetMobileEpolicy fetMobileEpolicy) throws SystemException, Exception;

	public Result removeFetMobileEpolicy(String oid) throws SystemException, Exception;
	
}
