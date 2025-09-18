package com.tlg.aps.bs.firRepeatpolicyImportService.impl;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firRepeatpolicyImportService.RepeatpolicyImportService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirRepeatpolicyBatchDtl;
import com.tlg.prpins.entity.FirRepeatpolicyBatchMain;
import com.tlg.prpins.service.FirRepeatpolicyBatchDtlService;
import com.tlg.prpins.service.FirRepeatpolicyBatchMainService;
import com.tlg.util.Result;

/* mantis：FIR0565，處理人員：CC009，需求單編號：FIR0565 住火_複保險通知轉檔作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class RepeatpolicyImportServiceImpl implements RepeatpolicyImportService {
	private static final Logger logger = Logger.getLogger(RepeatpolicyImportServiceImpl.class);
	
	private FirRepeatpolicyBatchMainService firRepeatpolicyBatchMainService;
	private FirRepeatpolicyBatchDtlService firRepeatpolicyBatchDtlService;
	
	@Override
	public Result insertFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain firRepeatpolicyBatchMain) throws SystemException,Exception {
		return this.firRepeatpolicyBatchMainService.insertFirRepeatpolicyBatchMain(firRepeatpolicyBatchMain);
	}
	
	@Override
	public Result updateFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException, Exception {
		return this.firRepeatpolicyBatchMainService.updateFirRepeatpolicyBatchMain(entity);
	}
	
	@Override
	public Result removeFirRepeatpolicyBatchMain(FirRepeatpolicyBatchMain entity) throws SystemException, Exception {
		return this.firRepeatpolicyBatchMainService.removeFirRepeatpolicyBatchMain(entity);
	}
	
	@Override
	public Result removeFirRepeatpolicyBatchDtl(FirRepeatpolicyBatchDtl entity) throws SystemException, Exception {
		return this.firRepeatpolicyBatchDtlService.removeFirRepeatpolicyBatchDtl(entity);
	}

	public FirRepeatpolicyBatchMainService getFirRepeatpolicyBatchMainService() {
		return firRepeatpolicyBatchMainService;
	}
	public void setFirRepeatpolicyBatchMainService(FirRepeatpolicyBatchMainService firRepeatpolicyBatchMainService) {
		this.firRepeatpolicyBatchMainService = firRepeatpolicyBatchMainService;
	}
	public FirRepeatpolicyBatchDtlService getFirRepeatpolicyBatchDtlService() {
		return firRepeatpolicyBatchDtlService;
	}
	public void setFirRepeatpolicyBatchDtlService(FirRepeatpolicyBatchDtlService firRepeatpolicyBatchDtlService) {
		this.firRepeatpolicyBatchDtlService = firRepeatpolicyBatchDtlService;
	}
}
