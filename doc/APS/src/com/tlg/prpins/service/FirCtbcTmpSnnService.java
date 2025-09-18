package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcTmpSnn;
import com.tlg.util.Result;

public interface FirCtbcTmpSnnService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcTmpSnnByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcTmpSnn(FirCtbcTmpSnn firCtbcTmpSnn) throws SystemException, Exception;

	public Result insertFirCtbcTmpSnn(FirCtbcTmpSnn firCtbcTmpSnn) throws SystemException, Exception;

	public Result removeFirCtbcTmpSnn(BigDecimal oid) throws SystemException, Exception;
	

}
