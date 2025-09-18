package com.tlg.aps.bs.firPanhsinBackFileService;

import java.util.Date;
import java.util.Map;

import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;

/* mantis：FIR0494，處理人員：CC009，需求單編號：FIR0494_板信回饋檔產生排程規格_新版 */
public interface PanhsinBackFileService {
	
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId, FirBatchLog firBatchLog) throws Exception;
	
	public void updateFirAgtBatch(FirAgtBatchMain main,Map<String,Object> params) throws Exception;
	
	public void updateFirAgtBatchMain(FirAgtBatchMain main, String remark, String userId) throws Exception;
}
