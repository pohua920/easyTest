package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewOriginal180;
import com.tlg.util.Result;

public interface FirCtbcRewOriginal180Service {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRewOriginal180ByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcRewOriginal180(FirCtbcRewOriginal180 firCtbcRewOriginal180) throws SystemException, Exception;

	public Result insertFirCtbcRewOriginal180(FirCtbcRewOriginal180 firCtbcRewOriginal180) throws SystemException, Exception;

	public Result removeFirCtbcRewOriginal180(BigDecimal oid) throws SystemException, Exception;
	

}
