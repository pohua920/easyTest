package com.tlg.aps.bs.generatePolicyPassbookService.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.generatePolicyPassbookService.PolicyPassbookService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.OthBatchPassbook;
import com.tlg.prpins.entity.OthBatchPassbookList;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.prpins.service.OthBatchPassbookListService;
import com.tlg.prpins.service.OthBatchPassbookService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/*  mantis：OTH0127，處理人員：CC009，需求單編號：OTH0127 保發中心_保單存摺產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class PolicyPassbookServiceImpl implements PolicyPassbookService{

	private FirBatchLogService firBatchLogService;
	private OthBatchPassbookService othBatchPassbookService;
	private OthBatchPassbookListService othBatchPassbookListService;
	
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
	public void updateFirBatchLog(String status, String remark, String userId, FirBatchLog firBatchLog)
			throws Exception {
		firBatchLog.setStatus(status);
		if(!StringUtil.isSpace(remark)) {
			firBatchLog.setRemark(remark.length()>300?remark.substring(0, 300):remark);
		}
		firBatchLog.setIupdate(userId);
		firBatchLog.setDupdate(new Date());
		firBatchLogService.updateFirBatchLog(firBatchLog);
	}
	
	@Override
	public Result insertOthBatchPassbook(String batchNo, String status, String userId, Date executeTime)
			throws SystemException, Exception {
		OthBatchPassbook entity = new OthBatchPassbook();
		entity.setBatchNo(batchNo);
		entity.setStatus(status);
		entity.setIcreate(userId);
		entity.setDcreate(executeTime);
		return othBatchPassbookService.insertOthBatchPassbook(entity);
	}
	
	@Override
	public Result updateOthBatchPassbook(OthBatchPassbook othBatchPassbook) throws SystemException, Exception {
		return othBatchPassbookService.updateOthBatchPassbook(othBatchPassbook);
		
	}
	
	@Override
	public Result updateOthBatchPassbookList(OthBatchPassbookList othBatchPassbookList)
			throws SystemException, Exception {
		return othBatchPassbookListService.updateOthBatchPassbookList(othBatchPassbookList);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Result updateOthBatchPassbookListByTmpBno(Map params) throws SystemException, Exception {
		return othBatchPassbookListService.updateBatchNoByTmpBno(params);
	}
	
	public OthBatchPassbookService getOthBatchPassbookService() {
		return othBatchPassbookService;
	}

	public void setOthBatchPassbookService(OthBatchPassbookService othBatchPassbookService) {
		this.othBatchPassbookService = othBatchPassbookService;
	}

	public OthBatchPassbookListService getOthBatchPassbookListService() {
		return othBatchPassbookListService;
	}

	public void setOthBatchPassbookListService(OthBatchPassbookListService othBatchPassbookListService) {
		this.othBatchPassbookListService = othBatchPassbookListService;
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

}
