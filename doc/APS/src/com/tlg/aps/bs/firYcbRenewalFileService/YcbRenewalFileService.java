package com.tlg.aps.bs.firYcbRenewalFileService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps060YcbDetailVo;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;

/** mantis：FIR0667，處理人員：DP0706，需求單編號：FIR0667 住火_元大續保作業_續件資料產生排程  **/
public interface YcbRenewalFileService {
	
	public Result insertFirBatchLog (Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;

	public void updateFirAgtrnBatchMain(String batchNo, String userId, Map<String,String> params) throws Exception;

	public void updateFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl, String batchNo, String userId, int batchSeq) throws Exception;

	public void updateFirAgtTocore(FirAgtTocoreMain firAgtTocoreMain,List<FirAgtTocoreInsured> firAgtTocoreInsuredToUpdateList) throws Exception;
	
	/** mantis：FIR0668，處理人員：DP0706，需求單編號：FIR0668_住火_元大續保作業_續件資料處理作業 */
	public void updateFirAgtTocoreAndAgtrnBatchDtl(Aps060YcbDetailVo ycbDetailVo, String userId) throws Exception;
}
