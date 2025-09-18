package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcSig;
import com.tlg.util.Result;

public interface FirCtbcSigService {
	
	@SuppressWarnings("rawtypes")
	public int countFirCtbcSig(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcSigByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcSig(FirCtbcSig firCtbcSig) throws SystemException, Exception;

	public Result insertFirCtbcSig(FirCtbcSig firCtbcSig) throws SystemException, Exception;

	public Result removeFirCtbcSig(BigDecimal oid) throws SystemException, Exception;
	

}
