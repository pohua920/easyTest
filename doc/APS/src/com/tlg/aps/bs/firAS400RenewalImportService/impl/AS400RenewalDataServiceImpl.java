package com.tlg.aps.bs.firAS400RenewalImportService.impl;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firAS400RenewalImportService.AS400RenewalDataService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.entity.FirAgtrnAs400DataErr;
import com.tlg.prpins.entity.FirAgtrnAs400DataUplist;
import com.tlg.prpins.service.FirAgtrnAs400DataErrService;
import com.tlg.prpins.service.FirAgtrnAs400DataService;
import com.tlg.prpins.service.FirAgtrnAs400DataUplistService;
import com.tlg.util.Result;
/**mantis：FIR0388，處理人員：BJ085，需求單編號：FIR0388 AS400續保資料匯入 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class AS400RenewalDataServiceImpl implements AS400RenewalDataService {
	
	private FirAgtrnAs400DataUplistService firAgtrnAs400DataUplistService;
	private FirAgtrnAs400DataService firAgtrnAs400DataService;
	private FirAgtrnAs400DataErrService firAgtrnAs400DataErrService;

	@Override
	public Result insertFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) throws SystemException, Exception{
		return this.firAgtrnAs400DataUplistService.insertFirAgtrnAs400DataUplist(firAgtrnAs400DataUplist);
	}

	@Override
	public Result updateFirAgtrnAs400DataUplist(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist) throws SystemException, Exception{
		return this.firAgtrnAs400DataUplistService.updateFirAgtrnAs400DataUplist(firAgtrnAs400DataUplist);
	}
	
	@Override
	public Result insertFirAgtrnAs400Data(FirAgtrnAs400Data firAgtrnAs400Data) throws SystemException, Exception {
		firAgtrnAs400DataService.insertFirAgtrnAs400Data(firAgtrnAs400Data);
		return null;
	}

	@Override
	public Result insertFirAgtrnAs400DataErr(FirAgtrnAs400DataErr firAgtrnAs400DataErr)
			throws SystemException, Exception {
		firAgtrnAs400DataErrService.insertFirAgtrnAs400DataErr(firAgtrnAs400DataErr);
		return null;
	}
	
	@Override
	public Result insertAndUpdateData(FirAgtrnAs400DataUplist firAgtrnAs400DataUplist,
			FirAgtrnAs400Data firAgtrnAs400Data, FirAgtrnAs400DataErr firAgtrnAs400DataErr)
			throws SystemException, Exception {
		
		return null;
	}
	
	@Override
	public Result removeFirAgtrnAs400Data(BigDecimal oid) throws SystemException, Exception {
		firAgtrnAs400DataService.removeFirAgtrnAs400Data(oid);
		return null;
	}

	@Override
	public Result removeFirAgtrnAs400DataErr(BigDecimal oid)
			throws SystemException, Exception {
		firAgtrnAs400DataErrService.removeFirAgtrnAs400DataErr(oid);
		return null;
	}

	public FirAgtrnAs400DataUplistService getFirAgtrnAs400DataUplistService() {
		return firAgtrnAs400DataUplistService;
	}

	public void setFirAgtrnAs400DataUplistService(FirAgtrnAs400DataUplistService firAgtrnAs400DataUplistService) {
		this.firAgtrnAs400DataUplistService = firAgtrnAs400DataUplistService;
	}

	public FirAgtrnAs400DataService getFirAgtrnAs400DataService() {
		return firAgtrnAs400DataService;
	}

	public void setFirAgtrnAs400DataService(FirAgtrnAs400DataService firAgtrnAs400DataService) {
		this.firAgtrnAs400DataService = firAgtrnAs400DataService;
	}

	public FirAgtrnAs400DataErrService getFirAgtrnAs400DataErrService() {
		return firAgtrnAs400DataErrService;
	}

	public void setFirAgtrnAs400DataErrService(FirAgtrnAs400DataErrService firAgtrnAs400DataErrService) {
		this.firAgtrnAs400DataErrService = firAgtrnAs400DataErrService;
	}

}
