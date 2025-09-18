package com.tlg.aps.bs.firTiiDataServerce;

import java.util.Date;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirBatchTii;
import com.tlg.prpins.entity.FirBatchTiiList;
import com.tlg.util.Result;

/** mantis：FIR0579，處理人員：BJ085，需求單編號：FIR0579 保發中心-住火保批資料產生排程規格 */
public interface TiiDataProcessService {
	
	public Result insertFirBatchLog (Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;
	
	public Result insertFirBatchTii(String batchNo, String userId) throws SystemException, Exception;
	
	public void updateFirBatchTii(FirBatchTii firBatchTii) throws SystemException,Exception;

	public void updateFirBatchTiiList(FirBatchTiiList firBatchTiiList) throws SystemException,Exception;
}
