package com.tlg.aps.bs.firGenRenewListService.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firGenRenewListService.GenRenewListService;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirRenewList;
import com.tlg.prpins.entity.FirRenewListDtl;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.prpins.service.FirRenewListDtlService;
import com.tlg.prpins.service.FirRenewListService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/* mantis：FIR0570，處理人員：BJ085，需求單編號：FIR0570 住火_APS每月應續件產生排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class GenRenewListServiceImpl implements GenRenewListService{

	private FirBatchLogService firBatchLogService;
	private FirRenewListDtlService firRenewListDtlService;
	//mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格
	private FirRenewListService firRenewListService;
	
	@Override
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId, String status, String remark,
			String batchNo) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setBatchNo(batchNo);
		firBatchLog.setPrgId(programId);
		firBatchLog.setStatus(status);
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
	public void updateFirRenewListDtl(FirRenewListDtl dtl, String userId) throws Exception {
		dtl.setIupdate(userId);
		dtl.setDupdate(new Date());
		firRenewListDtlService.updateFirRenewListDtl(dtl);
	}
	
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 */
	@Override
	public void updateFirRenewList(FirRenewList firRenewList) throws Exception {
		firRenewListService.updateFirRenewList(firRenewList);
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public FirRenewListDtlService getFirRenewListDtlService() {
		return firRenewListDtlService;
	}

	public void setFirRenewListDtlService(FirRenewListDtlService firRenewListDtlService) {
		this.firRenewListDtlService = firRenewListDtlService;
	}

	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 start*/
	public FirRenewListService getFirRenewListService() {
		return firRenewListService;
	}

	public void setFirRenewListService(FirRenewListService firRenewListService) {
		this.firRenewListService = firRenewListService;
	}
	/*mantis：FIR0571，處理人員：BJ085，需求單編號：FIR0571 住火_APS到期通知處理作業-新增印刷廠產檔作業規格 end*/
	
}
