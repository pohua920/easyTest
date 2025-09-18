package com.tlg.aps.bs.firIssueCheckService.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firIssueCheckService.IssueCheckService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirTmpDatacheck;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.prpins.service.FirTmpDatacheckService;
import com.tlg.util.Result;

@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class IssueCheckServiceImpl implements IssueCheckService {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	
	private FirTmpDatacheckService firTmpDatacheckService;
	private FirBatchLogService firBatchLogService;

	@Override
	public Result insertFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception{
		return this.firBatchLogService.insertFirBatchLog(firBatchLog);
	}
	
	@Override
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception{
		return this.firBatchLogService.updateFirBatchLog(firBatchLog);
	}
	
	@Override
	public Result updateFirTmpDatacheckByOid(FirTmpDatacheck firTmpDatacheck) throws SystemException, Exception {
		return this.firTmpDatacheckService.updateFirTmpDatacheckByOid(firTmpDatacheck);
	}

	public FirTmpDatacheckService getFirTmpDatacheckService() {
		return firTmpDatacheckService;
	}

	public void setFirTmpDatacheckService(FirTmpDatacheckService firTmpDatacheckService) {
		this.firTmpDatacheckService = firTmpDatacheckService;
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}
}
