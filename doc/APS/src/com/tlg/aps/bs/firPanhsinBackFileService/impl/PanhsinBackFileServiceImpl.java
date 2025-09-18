package com.tlg.aps.bs.firPanhsinBackFileService.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firPanhsinBackFileService.PanhsinBackFileService;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class PanhsinBackFileServiceImpl implements PanhsinBackFileService{

	private FirBatchLogService firBatchLogService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	
	@Override
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId, String status, String remark,
			String batchNo) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setStatus(status);
		firBatchLog.setBatchNo(batchNo);
		firBatchLog.setPrgId(programId);
		firBatchLog.setRemark(remark);
		firBatchLog.setIcreate(userId);
		firBatchLog.setDcreate(excuteTime);
		Result result = firBatchLogService.insertFirBatchLog(firBatchLog);
		result.setResObject(firBatchLog);
		
		return result;
	}
	
	@Override
	public void updateFirBatchLog(String status, String remark, String userId, FirBatchLog firBatchLog) throws Exception {
		firBatchLog.setStatus(status);
		if(!StringUtil.isSpace(remark)) {
			firBatchLog.setRemark(remark.length()>300?remark.substring(0, 300):remark);
		}
		firBatchLog.setIupdate(userId);
		firBatchLog.setDupdate(new Date());
		firBatchLogService.updateFirBatchLog(firBatchLog);
	}

	@Override
	public void updateFirAgtBatch(FirAgtBatchMain main, Map<String,Object> params) throws Exception {
		firAgtBatchMainService.updateFirAgtBatchMain(main);
		if(params != null) {
			firAgtBatchDtlService.updateFirAgtBatchDtlByParams(params);
		}
	}

	@Override
	public void updateFirAgtBatchMain(FirAgtBatchMain main, String remark, String userId) throws Exception {
		if(!StringUtil.isSpace(remark) && remark.length()>300) {
			remark = remark.substring(remark.length()-300 <0?0:remark.length()-300, remark.length());
		}
		main.setRemark(remark);
		main.setIupdate(userId);
		main.setDupdate(new Date());
		firAgtBatchMainService.updateFirAgtBatchMain(main);
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public FirAgtBatchMainService getFirAgtBatchMainService() {
		return firAgtBatchMainService;
	}

	public void setFirAgtBatchMainService(FirAgtBatchMainService firAgtBatchMainService) {
		this.firAgtBatchMainService = firAgtBatchMainService;
	}

	public FirAgtBatchDtlService getFirAgtBatchDtlService() {
		return firAgtBatchDtlService;
	}

	public void setFirAgtBatchDtlService(FirAgtBatchDtlService firAgtBatchDtlService) {
		this.firAgtBatchDtlService = firAgtBatchDtlService;
	}
}
