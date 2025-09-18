package com.tlg.aps.bs.firTiiDataServerce.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firTiiDataServerce.TiiDataProcessService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirBatchTii;
import com.tlg.prpins.entity.FirBatchTiiList;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.prpins.service.FirBatchTiiListService;
import com.tlg.prpins.service.FirBatchTiiService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class TiiDataProcessServiceImpl implements TiiDataProcessService {

	private FirBatchLogService firBatchLogService;
	private FirBatchTiiService firBatchTiiService;
	private FirBatchTiiListService firBatchTiiListService;

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
	public Result insertFirBatchTii(String batchNo, String userId) throws SystemException, Exception {
		FirBatchTii firBatchTii = new FirBatchTii();
		firBatchTii.setBatchNo(batchNo);
		firBatchTii.setStatus("0");
		firBatchTii.setIcreate(userId);
		firBatchTii.setDcreate(new Date());
		return firBatchTiiService.insertFirBatchTii(firBatchTii);
	}
	
	@Override
	public void updateFirBatchTii(FirBatchTii firBatchTii) throws SystemException,Exception {
		firBatchTiiService.updateFirBatchTii(firBatchTii);
	}
	
	@Override
	public void updateFirBatchTiiList(FirBatchTiiList firBatchTiiList) throws SystemException,Exception {
		firBatchTiiListService.updateFirBatchTiiList(firBatchTiiList);
	}
	
	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public FirBatchTiiService getFirBatchTiiService() {
		return firBatchTiiService;
	}

	public void setFirBatchTiiService(FirBatchTiiService firBatchTiiService) {
		this.firBatchTiiService = firBatchTiiService;
	}

	public FirBatchTiiListService getFirBatchTiiListService() {
		return firBatchTiiListService;
	}

	public void setFirBatchTiiListService(FirBatchTiiListService firBatchTiiListService) {
		this.firBatchTiiListService = firBatchTiiListService;
	}
}
