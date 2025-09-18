package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcTmpStl;
import com.tlg.util.Result;

public interface FirCtbcTmpStlService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcTmpStlByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcTmpStl(FirCtbcTmpStl firCtbcTmpStl) throws SystemException, Exception;

	public Result insertFirCtbcTmpStl(FirCtbcTmpStl firCtbcTmpStl) throws SystemException, Exception;

	public Result removeFirCtbcTmpStl(BigDecimal oid) throws SystemException, Exception;
	

}
