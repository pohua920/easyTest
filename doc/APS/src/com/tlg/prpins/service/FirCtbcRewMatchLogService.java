package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewMatchLog;
import com.tlg.util.Result;

public interface FirCtbcRewMatchLogService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRewMatchLogByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcRewMatchLog(FirCtbcRewMatchLog firCtbcRewMatchLog) throws SystemException, Exception;

	public Result insertFirCtbcRewMatchLog(FirCtbcRewMatchLog firCtbcRewMatchLog) throws SystemException, Exception;

	public Result removeFirCtbcRewMatchLog(BigDecimal oid) throws SystemException, Exception;
	
	public BigDecimal getOid() throws SystemException, Exception;

}
