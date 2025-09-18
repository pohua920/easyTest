package com.tlg.aps.bs.firProcFpolicyRerunService;

import java.util.Date;

import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;

/** mantis：FIR0635，處理人員：CD094，需求單編號：FIR0635_住火_新核心保單轉入中介檔異常處理排程  **/
public interface FpolicyRerunService {
	
	public Result insertFirBatchLog (Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;
	
}
