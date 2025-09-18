package com.tlg.aps.bs.othBatchPassbookServerce;

import java.util.Date;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.OthBatchPassbookNcData;
import com.tlg.util.Result;

/** mantis：OTH0131，處理人員：BJ085，需求單編號：OTH0131 保發中心-保單存摺各險寫入中介Table作業 */
public interface PassbookDataInsertService {
	
	public Result insertFirBatchLog (Date excuteTime, String userId, String programId,String status, String remark, String batchNo) throws Exception;
	
	public void updateFirBatchLog(String status, String remark, String userId,FirBatchLog firBatchLog) throws Exception;
	
	public Result insertOthBatchPassbookNcData(String batchNo, String riskcode, String produceType, String filename, String userId ) throws SystemException, Exception;
	
	public void updateOthBatchPassbookNcData(OthBatchPassbookNcData othBatchPassbookNcData) throws SystemException, Exception;

}
