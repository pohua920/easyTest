package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcSnn;
import com.tlg.util.Result;

public interface FirCtbcSnnService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcSnnByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcSnn(FirCtbcSnn firCtbcSnn) throws SystemException, Exception;

	public Result insertFirCtbcSnn(FirCtbcSnn firCtbcSnn) throws SystemException, Exception;

	public Result removeFirCtbcSnn(BigDecimal oid) throws SystemException, Exception;
	

}
