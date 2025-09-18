package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchPins01Detail;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public interface FirBatchPins01DetailService {
	
	@SuppressWarnings("rawtypes")
	public int countFirBatchPins01Detail(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchPins01DetailByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchPins01DetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirBatchPins01Detail(FirBatchPins01Detail firBatchPins01Detail) throws SystemException, Exception;

	public Result insertFirBatchPins01Detail(FirBatchPins01Detail firBatchPins01Detail) throws SystemException, Exception;

	public Result findFirBatchPins01DetailByUK(Map params) throws SystemException, Exception;
	
}
