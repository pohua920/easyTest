package com.tlg.aps.bs.firRnproposalRenewalFileService.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tlg.aps.bs.firRnproposalRenewalFileService.RnproposalRenewalFileService;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirCtbcRnproposalDtl;
import com.tlg.prpins.entity.FirCtbcRnproposalMain;
import com.tlg.prpins.service.FirBatchLogService;
import com.tlg.prpins.service.FirCtbcRnproposalDtlService;
import com.tlg.prpins.service.FirCtbcRnproposalMainService;
import com.tlg.util.Result;
import com.tlg.util.StringUtil;

/* mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
@Transactional(value = "prpinsTransactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = Exception.class)
public class RnproposalRenewalFileServiceImpl implements RnproposalRenewalFileService{

	private FirBatchLogService firBatchLogService;
	private FirCtbcRnproposalMainService firCtbcRnproposalMainService;
	private FirCtbcRnproposalDtlService firCtbcRnproposalDtlService;
	
	
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
	public void insertFirCtbcRnproposalMainData(String batchNo, List<String> downloadFileList, String userId, Date executeTime)
			throws SystemException, Exception {
		int iSeq = 1;
		for(String fileName : downloadFileList) {
			FirCtbcRnproposalMain main = new FirCtbcRnproposalMain();
			main.setBatchNo(batchNo);
			main.setBatchSeq(String.format("%03d", iSeq));
			main.setFilenameZip(fileName);
			main.setDeleteFlag("N");
			main.setIcreate(userId);
			main.setDcreate(executeTime);
			firCtbcRnproposalMainService.insertFirCtbcRnproposalMain(main);
			iSeq += 1;
		}
	}
	
	@Override
	public Integer insertFirCtbcRnproposalDtlList(String batchNo, String batchSeq, String fileName, String userId,
			List<String> fileDataList) throws SystemException, Exception {
		int dataQty = 0;
		for(String fileData:fileDataList) {
			FirCtbcRnproposalDtl firCtbcRnproposalDtl = new FirCtbcRnproposalDtl();
			firCtbcRnproposalDtl.setBatchNo(batchNo);
			firCtbcRnproposalDtl.setBatchSeq(batchSeq);
			firCtbcRnproposalDtl.setFilename(fileName);
			firCtbcRnproposalDtl.setRawdata(fileData);
			firCtbcRnproposalDtl.setpStatus("N");
			firCtbcRnproposalDtl.setIcreate(userId);
			firCtbcRnproposalDtl.setDcreate(new Date());
			firCtbcRnproposalDtlService.insertFirCtbcRnproposalDtl(firCtbcRnproposalDtl);
			dataQty ++;
		}
		return Integer.valueOf(dataQty);
	}

	@Override
	public Result updateFirCtbcRnproposalMain(FirCtbcRnproposalMain firCtbcRnproposalMain) throws SystemException, Exception {
		return firCtbcRnproposalMainService.updateFirCtbcRnproposalMain(firCtbcRnproposalMain);
		
	}
	
	@Override
	public Result updateFirCtbcRnproposalDtl(FirCtbcRnproposalDtl firCtbcRnproposalDtl) throws SystemException, Exception {
		return firCtbcRnproposalDtlService.updateFirCtbcRnproposalDtl(firCtbcRnproposalDtl);
	}

	public FirBatchLogService getFirBatchLogService() {
		return firBatchLogService;
	}

	public void setFirBatchLogService(FirBatchLogService firBatchLogService) {
		this.firBatchLogService = firBatchLogService;
	}

	public FirCtbcRnproposalMainService getFirCtbcRnproposalMainService() {
		return firCtbcRnproposalMainService;
	}

	public void setFirCtbcRnproposalMainService(FirCtbcRnproposalMainService firCtbcRnproposalMainService) {
		this.firCtbcRnproposalMainService = firCtbcRnproposalMainService;
	}

	public FirCtbcRnproposalDtlService getFirCtbcRnproposalDtlService() {
		return firCtbcRnproposalDtlService;
	}

	public void setFirCtbcRnproposalDtlService(FirCtbcRnproposalDtlService firCtbcRnproposalDtlService) {
		this.firCtbcRnproposalDtlService = firCtbcRnproposalDtlService;
	}
	
}
