package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcTmpPbl;
import com.tlg.util.Result;

public interface FirCtbcTmpPblService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcTmpPblByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcTmpPbl(FirCtbcTmpPbl firCtbcTmpPbl) throws SystemException, Exception;

	public Result insertFirCtbcTmpPbl(FirCtbcTmpPbl firCtbcTmpPbl) throws SystemException, Exception;

	public Result removeFirCtbcTmpPbl(BigDecimal oid) throws SystemException, Exception;
	

}
