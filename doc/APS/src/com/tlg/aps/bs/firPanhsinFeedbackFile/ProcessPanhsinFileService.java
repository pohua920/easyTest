package com.tlg.aps.bs.firPanhsinFeedbackFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tlg.aps.vo.Aps016DetailVo;
import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirAgtTocoreInsured;
import com.tlg.prpins.entity.FirAgtTocoreMain;
import com.tlg.prpins.entity.FirAgtrnBatchDtl;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.util.Result;

public interface ProcessPanhsinFileService {
	/* mantis：FIR0265，處理人員：BJ085，需求單編號：FIR0265 板信受理檔產生排程
	   mantis：FIR0266，處理人員：BJ085，需求單編號：FIR0266 板信資料交換處理作業 
	   mantis：FIR0271，處理人員：BJ085，需求單編號：FIR0271 板信保單檔產生作業-排程作業  start */
	
	public Result insertFirBatchLog(Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;

	public void insertBatchGenFileAndUpdateMain(String batchNo, String userId, String fileName);
	
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程  start */
	public void insertFirAgtrnBatchMain(String batchNo,String userId,String filename) throws SystemException, Exception;
	
	public void updateFirAgtrnBatchMain(String batchNo, String userId, Map<String,String> params) throws  SystemException, Exception;
	
	public void insertFirAgtrnTmpBopList(String batchNo, String fileName, String userId, List<String> fileDataList) throws SystemException, Exception;
	
	/* mantis：FIR0294，處理人員：BJ085，需求單編號：FIR0294 外銀板信續件移回新核心-資料接收排程  end */
	
	//mantis：FIR0295，處理人員：BJ085，需求單編號：FIR0295 外銀板信續件移回新核心-維護作業
	public void updateFirAgtTocoreAndAgtrnBatchDtl(Aps016DetailVo aps016DetailVo, String userId) throws SystemException, Exception;
}
