package com.tlg.aps.bs.firFubonRenewalService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps034FbDetailVo;
import com.tlg.prpins.entity.FirAgtrnAs400Data;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;

/** mantis：FIR0454，處理人員：BJ085，需求單編號：FIR0454 住火-APS富邦續件資料接收排程 
 *  mantis：FIR0455，處理人員：BJ085，需求單編號：FIR0455 住火-APS富邦續件處理作業 **/
public interface FubonRenewalFileService {
	
	public void insertBatchMain(String batchNo, String filename, String userId) throws Exception;
	
	public void insertFirAgtrnTmpFbList(String batchNo, String filename, String userId, List<String> fileDataList) throws Exception;
	
	public Result insertFirBatchLog (Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;

	public void updateFirAgtrnBatchMain(String batchNo, String userId, Map<String,String> params) throws Exception;
	
	public void insertFirAgtrnBatchDtl(String batchNo, Integer batchSeq, String userId) throws Exception;
	
	public void updateFirAgtrnBatchDtl(FirAgtrnBatchDtl firAgtrnBatchDtl, String batchNo, String userId, int batchSeq) throws Exception;

	public void insertFirAgtTocore(String batchNo, int batchSeq, String userId, Map<String,String> tmpdatas, String handler1code, String comcode, FirAgtrnAs400Data as400Data) throws Exception; 
	
	public void updateFirAgtTocoreAndAgtrnBatchDtl(Aps034FbDetailVo fbDetailVo, String userId) throws Exception;
}
