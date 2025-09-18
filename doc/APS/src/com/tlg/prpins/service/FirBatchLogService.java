package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;

public interface FirBatchLogService {
	
	@SuppressWarnings("rawtypes")
	public int countFirBatchLog(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchLogByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception;

	public Result insertFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception;

	public Result removeFirBatchLog(BigDecimal oid) throws SystemException, Exception;
	
	public String getLastBatchNo() throws SystemException, Exception;
	

}
