package com.tlg.aps.bs.firUbNewPolicyService;

import java.util.Date;
import java.util.Map;

import com.tlg.prpins.entity.FirAgtBatchDtl;
import com.tlg.prpins.entity.FirAgtBatchMain;
import com.tlg.prpins.entity.FirAgtUb01;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;

/**mantis：FIR0545，處理人員：BJ085，需求單編號：FIR0545 火險_聯邦新件_APS要保資料接收排程*/
public interface UbNewPolicyProcessService {
	
	public void insertFirAgtBatchMain(String batchNo, String fileName, String userId) throws Exception;
	
	public Result insertFirBatchLog (Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;

	public void updateFirAgtBatchMain(FirAgtBatchMain firAgtBatchMain,String remark, String userId) throws Exception;
	
	public void updateFirAgtBatchDtl(FirAgtBatchDtl firAgtBatchDtl, String userId) throws Exception;
	
	public void insertUb01AndDtl(FirAgtUb01 firAgtUb01,FirAgtBatchDtl firAgtBatchDtl) throws Exception;
	
	//mantis：FIR0546，處理人員：BJ085，需求單編號：FIR0546 火險_聯邦新件_APS回饋檔產生排程
	public void updateFirAgtBatch(FirAgtBatchMain main, Map<String,Object> params) throws Exception;
	
}
