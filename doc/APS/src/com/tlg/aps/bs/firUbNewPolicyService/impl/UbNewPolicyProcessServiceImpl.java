package com.tlg.aps.bs.firUbNewPolicyService.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firUbNewPolicyService.UbNewPolicyProcessService;
import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtUb01;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirAgtBatchDtlService;
import com.tlg.prpins.service.FirAgtBatchMainService;
import com.tlg.prpins.service.FirAgtUb01Service;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/**mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class UbNewPolicyProcessServiceImpl implements UbNewPolicyProcessService {

	private FirBatchLogService firBatchLogService;
	private FirAgtBatchMainService firAgtBatchMainService;
	private FirAgtBatchDtlService firAgtBatchDtlService;
	private FirAgtUb01Service firAgtUb01Service;

	@Override
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId, String status, String remark,
			String batchNo) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setStatus(status);
		firBatchLog.setBatchNo(batchNo);
		firBatchLog.setPrgId(programId);
		firBatchLog.setRemark(remark);
		firBatchLog.setIcreate(userId);
		firBatchLog.setDcreate(new Date());
		Result result = firBatchLogService.insertFirBatchLog(firBatchLog);
		result.setResObject(firBatchLog);
		return result;
	}

	@Override
	public void insertFirAgtBatchMain(String batchNo, String fileName, String userId) throws Exception {
		FirAgtBatchMain firAgtBatchMain = new FirAgtBatchMain();
		firAgtBatchMain.setBatchNo(batchNo);
		firAgtBatchMain.setBusinessnature("I99080");
		firAgtBatchMain.setBatchType("01");
		firAgtBatchMain.setFileName(fileName);
		firAgtBatchMain.setFileStatus("N");
		firAgtBatchMain.setDeleteFlag("N");
		firAgtBatchMain.setIcreate(userId);
		firAgtBatchMain.setDcreate(new Date());
		firAgtBatchMainService.insertFirAgtBatchMain(firAgtBatchMain);
	}

	@Override
	public void updateFirAgtBatchMain(FirAgtBatchMain firAgtBatchMain,String remark, String userId) throws Exception {
		firAgtBatchMain.setIupdate(userId);
		firAgtBatchMain.setDupdate(new Date());
		firAgtBatchMainService.updateFirAgtBatchMain(firAgtBatchMain);
	}

	@Override
	public void updateFirBatchLog(String status, String remark, String userId, FirBatchLog firBatchLog)
			throws Exception {
		firBatchLog.setStatus(status);
		if (!StringUtil.isSpace(remark)) {
			firBatchLog.setRemark(remark.length() > 300 ? remark.substring(0, 300) : remark);
		}
		firBatchLog.setIupdate(userId);
		firBatchLog.setDupdate(new Date());
		firBatchLogService.updateFirBatchLog(firBatchLog);
	}

	@Override
	public void updateFirAgtBatchDtl(FirAgtBatchDtl firAgtBatchDtl, String userId) throws Exception {
		firAgtBatchDtl.setIupdate(userId);
		firAgtBatchDtl.setDupdate(new Date());
		firAgtBatchDtlService.updateFirAgtBatchDtl(firAgtBatchDtl);
	}
	
	@Override
	public void insertUb01AndDtl(FirAgtUb01 firAgtUb01, FirAgtBatchDtl firAgtBatchDtl) throws Exception {
		firAgtUb01Service.insertFirAgtUb01(firAgtUb01);
		firAgtBatchDtlService.insertFirAgtBatchDtl(firAgtBatchDtl);
	}
	
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 start*/
	@Override
	public void updateFirAgtBatch(FirAgtBatchMain main, Map<String,Object> params) throws Exception {
		firAgtBatchMainService.updateFirAgtBatchMain(main);
		if(params != null) {
			firAgtBatchDtlService.updateFirAgtBatchDtlByParams(params);
		}
	}
	/*mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程 end*/
	
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
	
	public FirAgtUb01Service getFirAgtUb01Service() {
		return firAgtUb01Service;
	}
	
	public void setFirAgtUb01Service(FirAgtUb01Service firAgtUb01Service) {
		this.firAgtUb01Service = firAgtUb01Service;
	}
}
