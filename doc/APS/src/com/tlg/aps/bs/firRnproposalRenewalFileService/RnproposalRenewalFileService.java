package com.tlg.aps.bs.firRnproposalRenewalFileService;

import java.util.Date;
import java.util.List;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirCtbcRnproposalDtl;
import com.tlg.prpins.entity.FirCtbcRnproposalMain;
import com.tlg.util.Result;

/* mantis：FIR0458，處理人員：CC009，需求單編號：FIR0458 住火-APS中信續件要保書-資料接收排程 */
public interface RnproposalRenewalFileService {
	
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;
	
	public void insertFirCtbcRnproposalMainData(String batchNo, List<String> downloadFileList, String userId, Date executeTime) throws SystemException, Exception;
	
	public Result updateFirCtbcRnproposalMain(FirCtbcRnproposalMain firCtbcRnproposalMain) throws  SystemException, Exception;
	
	public Integer insertFirCtbcRnproposalDtlList(String batchNo, String batchSeq, String fileName, String userId, List<String> fileDataList) throws SystemException, Exception;
	
	public Result updateFirCtbcRnproposalDtl(FirCtbcRnproposalDtl firCtbcRnproposalDtl) throws SystemException, Exception;
}
