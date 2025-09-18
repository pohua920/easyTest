package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRptCtbcCtf;
import com.tlg.util.Result;

public interface FirRptCtbcCtfService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirRptCtbcCtfByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirRptCtbcCtf(FirRptCtbcCtf firRptCtbcCtf) throws SystemException, Exception;

	public Result insertFirRptCtbcCtf(FirRptCtbcCtf firRptCtbcCtf) throws SystemException, Exception;

	public Result removeFirRptCtbcCtf(BigDecimal oid) throws SystemException, Exception;
	
	/* mantis：FIR0499，處理人員：BJ085，需求單編號：FIR0499_住火-中信保代網投_進件查詢作業 */
	public Result findFirRptCtbcCtfByOid(BigDecimal oid) throws SystemException,Exception;

}
