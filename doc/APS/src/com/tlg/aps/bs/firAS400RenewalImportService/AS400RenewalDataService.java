package com.tlg.aps.bs.firAS400RenewalImportService;

import java.math.BigDecimal;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.entity.FirAgtrnAs400DataErr;
import com.tlg.prpins.entity.FirAgtrnAs400DataUplist;
import com.tlg.util.Result;

/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
public interface AS400RenewalDataService {
	public Result insertFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) throws SystemException, Exception;
	
	public Result updateFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) throws SystemException, Exception;
	
	public Result insertFirAgtrnAs400Data(FirAgtrnAs400Data firAgtrnAs400Data) throws SystemException, Exception;
	
	public Result insertFirAgtrnAs400DataErr(FirAgtrnAs400DataErr firAgtrnAs400DataErr) throws SystemException, Exception;
	
	public Result insertAndUpdateData(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist, FirAgtrnAs400Data firAgtrnAs400Data, FirAgtrnAs400DataErr firAgtrnAs400DataErr) throws SystemException, Exception;

	public Result removeFirAgtrnAs400Data(BigDecimal oid) throws SystemException, Exception;
	
	public Result removeFirAgtrnAs400DataErr(BigDecimal oid) throws SystemException, Exception;
}
