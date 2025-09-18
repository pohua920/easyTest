package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
public interface FirAgtrnAs400DataService {
	public Result findFirAgtrnAs400DataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	@SuppressWarnings("rawtypes")
	public Result findFirAgtrnAs400DataByParams(Map params) throws SystemException, Exception;
	
	public int countFirAgtrnAs400Data(Map params) throws SystemException, Exception;
	
	public Result insertFirAgtrnAs400Data(FirAgtrnAs400Data firAgtrnAs400Data) throws SystemException, Exception;
	
	public Result updateFirAgtrnAs400Data(FirAgtrnAs400Data firAgtrnAs400Data) throws SystemException, Exception;

	public Result removeFirAgtrnAs400Data(BigDecimal oid) throws Exception;
}
