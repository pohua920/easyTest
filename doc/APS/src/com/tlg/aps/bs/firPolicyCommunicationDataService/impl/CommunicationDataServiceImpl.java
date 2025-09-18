package com.tlg.aps.bs.firPolicyCommunicationDataService.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firPolicyCommunicationDataService.CommunicationDataService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirBatchPins01;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.prpins.service.FirBatchPins01Service;
import com.tlg.util.Result;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class CommunicationDataServiceImpl implements CommunicationDataService {
	
	private FirBatchLogService firBatchLogService;
	private FirBatchPins01Service firBatchPins01Service;

	@Override
	public Result insertFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception{
		return this.firBatchLogService.insertFirBatchLog(firBatchLog);
	}

	@Override
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception{
		return this.firBatchLogService.updateFirBatchLog(firBatchLog);
	}
	
	@Override
	public Result insertFirBatchPins01(FirBatchPins01 firBatchPins01) throws SystemException, Exception{
		return this.firBatchPins01Service.insertFirBatchPins01(firBatchPins01);
	}
	
	@Override
	public Result updateFirBatchPins01(FirBatchPins01 firBatchPins01) throws SystemException, Exception {
		return this.firBatchPins01Service.updateFirBatchPins01(firBatchPins01);
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public FirBatchPins01Service getFirBatchPins01Service() {
		return firBatchPins01Service;
	}

	public void setFirBatchPins01Service(FirBatchPins01Service firBatchPins01Service) {
		this.firBatchPins01Service = firBatchPins01Service;
	}
}
