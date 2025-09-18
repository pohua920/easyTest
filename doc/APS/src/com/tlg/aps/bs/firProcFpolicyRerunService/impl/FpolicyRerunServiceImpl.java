package com.tlg.aps.bs.firProcFpolicyRerunService.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firProcFpolicyRerunService.FpolicyRerunService;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class FpolicyRerunServiceImpl implements FpolicyRerunService {

	private FirBatchLogService firBatchLogService;

	@Override
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId, String status, String remark,
			String batchNo) throws Exception {
		FirBatchLog firBatchLog = new FirBatchLog();
		firBatchLog.setBatchNo(batchNo);
		firBatchLog.setPrgId(programId);
		firBatchLog.setStatus(status);
		firBatchLog.setRemark(remark);
		firBatchLog.setIcreate(userId);
		firBatchLog.setDcreate(new Date());
		Result result = firBatchLogService.insertFirBatchLog(firBatchLog);
		result.setResObject(firBatchLog);

		return result;
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

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}
}
