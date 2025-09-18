package com.tlg.aps.bs.othBatchPassbookServerce.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.othBatchPassbookServerce.PassbookDataInsertService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.OthBatchPassbookNcData;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.prpins.service.OthBatchPassbookNcDataService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class PassbookDataInsertServiceImpl implements PassbookDataInsertService {

	private FirBatchLogService firBatchLogService;
	private OthBatchPassbookNcDataService othBatchPassbookNcDataService;

	@Override
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId, String status, String remark, String batchNo) throws Exception {
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
	public void updateFirBatchLog(String status, String remark, String userId, FirBatchLog firBatchLog) throws Exception {
		firBatchLog.setStatus(status);
		if (!StringUtil.isSpace(remark)) {
			firBatchLog.setRemark(remark.length() > 300 ? remark.substring(0, 300) : remark);
		}
		firBatchLog.setIupdate(userId);
		firBatchLog.setDupdate(new Date());
		firBatchLogService.updateFirBatchLog(firBatchLog);
	}
	
	@Override
	public Result insertOthBatchPassbookNcData(String batchNo, String riskcode, String produceType, String filename, String userId ) throws SystemException, Exception {
		OthBatchPassbookNcData othBatchPassbookNcData = new OthBatchPassbookNcData();
		othBatchPassbookNcData.setBatchNo(batchNo);
		othBatchPassbookNcData.setBatchSerial(batchNo + riskcode + produceType);
		othBatchPassbookNcData.setFilename(filename);
		othBatchPassbookNcData.setRiskcode(riskcode);
		othBatchPassbookNcData.setProcType(transProduceType(produceType));
		othBatchPassbookNcData.setIcreate(userId);
		othBatchPassbookNcData.setDcreate(new Date());
		return othBatchPassbookNcDataService.insertOthBatchPassbookNcData(othBatchPassbookNcData);
	}

	@Override
	public void updateOthBatchPassbookNcData(OthBatchPassbookNcData othBatchPassbookNcData)
			throws SystemException, Exception {
		othBatchPassbookNcDataService.updateOthBatchPassbookNcData(othBatchPassbookNcData);
	}
	
	private String transProduceType(String produceType) {
		if("P".equals(produceType)) {
			produceType = "1";
		}
		if("E".equals(produceType)) {
			produceType = "2";
		}
		if("A".equals(produceType)) {
			produceType = "3";
		}
		return produceType;
	}
	
	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public OthBatchPassbookNcDataService getOthBatchPassbookNcDataService() {
		return othBatchPassbookNcDataService;
	}

	public void setOthBatchPassbookNcDataService(OthBatchPassbookNcDataService othBatchPassbookNcDataService) {
		this.othBatchPassbookNcDataService = othBatchPassbookNcDataService;
	}

}
