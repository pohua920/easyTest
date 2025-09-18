package com.tlg.aps.bs.firPolicyCommunicationDataService;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchLog;
import com.tlg.prpins.entity.FirBatchPins01;
import com.tlg.util.Result;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public interface CommunicationDataService {
	
	public Result insertFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception;
	
	public Result updateFirBatchLog(FirBatchLog firBatchLog) throws SystemException, Exception;
	
	public Result insertFirBatchPins01(FirBatchPins01 firBatchPins01) throws SystemException, Exception;
	
	public Result updateFirBatchPins01(FirBatchPins01 firBatchPins01) throws SystemException, Exception;
	
}
