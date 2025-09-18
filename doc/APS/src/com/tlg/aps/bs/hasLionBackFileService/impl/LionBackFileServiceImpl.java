package com.tlg.aps.bs.hasLionBackFileService.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.hasLionBackFileService.LionBackFileService;
import com.tlg.prpins.entity.HasAgtBatchMain;
import com.tlg.prpins.entity.HasBatchLog;
import com.tlg.prpins.service.HasAgtBatchDtlService;
import com.tlg.prpins.service.HasAgtBatchMainService;
import com.tlg.prpins.service.HasBatchLogService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/** mantis：HAS0215，處理人員：CD094，需求單編號：HAS0215 TA雄獅回饋檔*/
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class LionBackFileServiceImpl implements LionBackFileService{

	private HasBatchLogService hasBatchLogService;
	private HasAgtBatchMainService hasAgtBatchMainService;
	private HasAgtBatchDtlService hasAgtBatchDtlService;
	
	@Override
	public Result insertHasBatchLog(Date excuteTime, String userId, String programId, String status, String remark,
			String batchNo) throws Exception {
		HasBatchLog hasBatchLog = new HasBatchLog();
		hasBatchLog.setBatchNo(batchNo);
		hasBatchLog.setPrgId(programId);
		hasBatchLog.setStatus(status);
		hasBatchLog.setRemark(remark);
		hasBatchLog.setIcreate(userId);
		hasBatchLog.setDcreate(excuteTime);
		return  hasBatchLogService.insertHasBatchLog(hasBatchLog);
		
	}
	
	@Override
	public void updateHasBatchLog(String status, String remark, String userId, HasBatchLog hasBatchLog) throws Exception {
		hasBatchLog.setStatus(status);
		if(!StringUtil.isSpace(remark)) {
			hasBatchLog.setRemark(remark.length()>300?remark.substring(0, 300):remark);
		}
		hasBatchLog.setIupdate(userId);
		hasBatchLog.setDupdate(new Date());
		hasBatchLogService.updateHasBatchLog(hasBatchLog);
	}

	@Override
	public void updateHasAgtBatch(HasAgtBatchMain main, Map<String,Object> params) throws Exception {
		hasAgtBatchMainService.updateHasAgtBatchMain(main);
		if(params != null) {
			hasAgtBatchDtlService.updateHasAgtBatchDtlByParams(params);
		}
	}

	@Override
	public void updateHasAgtBatchMain(HasAgtBatchMain main, String remark, String userId) throws Exception {
		if(!StringUtil.isSpace(remark) && remark.length()>300) {
			remark = remark.substring(remark.length()-300 <0?0:remark.length()-300, remark.length());
		}
		main.setRemark(remark);
		main.setIupdate(userId);
		main.setDupdate(new Date());
		hasAgtBatchMainService.updateHasAgtBatchMain(main);
	}

	public HasBatchLogService getHasBatchLogService() {
		return hasBatchLogService;
	}

	public void setHasBatchLogService(HasBatchLogService hasBatchLogService) {
		this.hasBatchLogService = hasBatchLogService;
	}

	public HasAgtBatchMainService getHasAgtBatchMainService() {
		return hasAgtBatchMainService;
	}

	public void setHasAgtBatchMainService(HasAgtBatchMainService hasAgtBatchMainService) {
		this.hasAgtBatchMainService = hasAgtBatchMainService;
	}

	public HasAgtBatchDtlService getHasAgtBatchDtlService() {
		return hasAgtBatchDtlService;
	}

	public void setHasAgtBatchDtlService(HasAgtBatchDtlService hasAgtBatchDtlService) {
		this.hasAgtBatchDtlService = hasAgtBatchDtlService;
	}

	
}
