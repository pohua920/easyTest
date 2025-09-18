package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewMatchname;
import com.tlg.util.Result;

public interface FirCtbcRewMatchnameService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRewMatchnameByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcRewMatchname(FirCtbcRewMatchname firCtbcRewMatchname) throws SystemException, Exception;

	public Result insertFirCtbcRewMatchname(FirCtbcRewMatchname firCtbcRewMatchname) throws SystemException, Exception;

	public Result removeFirCtbcRewMatchname(BigDecimal oid) throws SystemException, Exception;

}
