package com.tlg.aps.bs.firBatchSendmailService.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firBatchSendmailService.WriteForBatchSendmailService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchSendmail;
import com.tlg.prpins.entity.FirBatchSendmailDetail;
import com.tlg.prpins.service.FirBatchSendmailDetailService;
import com.tlg.prpins.service.FirBatchSendmailService;

/*mantis：CAR0507，處理人員：BJ085，需求單編號：CAR0507 承保系統新增電子保單產生追蹤機制(車險&旅平險)*/
@Transactional(value="prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class WriteForBatchSendmailServiceImpl implements  WriteForBatchSendmailService {
	
	private FirBatchSendmailService firBatchSendmailService;
	private FirBatchSendmailDetailService firBatchSendmailDetailService;
	
	@Override
	public void insertFirBatchSendmail(FirBatchSendmail firBatchSendmail) throws SystemException, Exception {
		firBatchSendmailService.insertFirBatchSendmail(firBatchSendmail);
	}

	@Override
	public void insertFirBatchSendmailDetail(FirBatchSendmailDetail firBatchSendmailDetail) throws SystemException, Exception {
		firBatchSendmailDetailService.insertFirBatchSendmailDetail(firBatchSendmailDetail);
	}

	public FirBatchSendmailService getFirBatchSendmailService() {
		return firBatchSendmailService;
	}
	public void setFirBatchSendmailService(FirBatchSendmailService firBatchSendmailService) {
		this.firBatchSendmailService = firBatchSendmailService;
	}
	public FirBatchSendmailDetailService getFirBatchSendmailDetailService() {
		return firBatchSendmailDetailService;
	}
	public void setFirBatchSendmailDetailService(FirBatchSendmailDetailService firBatchSendmailDetailService) {
		this.firBatchSendmailDetailService = firBatchSendmailDetailService;
	}
}
