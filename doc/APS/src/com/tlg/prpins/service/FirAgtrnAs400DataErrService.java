package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnAs400DataErr;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
public interface FirAgtrnAs400DataErrService {
	public Result findFirAgtrnAs400DataErrByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnAs400DataErrByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnAs400DataErr(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnAs400DataErr(FirAgtrnAs400DataErr firAgtrnAs400DataErr) throws SystemException, Exception;
	
	public Result updateFirAgtrnAs400DataErr(FirAgtrnAs400DataErr firAgtrnAs400DataErr) throws SystemException, Exception;

	public Result removeFirAgtrnAs400DataErr(BigDecimal oid) throws Exception;
}
