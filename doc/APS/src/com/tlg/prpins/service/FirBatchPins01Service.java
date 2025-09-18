package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchPins01;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0427，處理人員：BJ085，需求單編號：FIR0427 個人險-保單通訊資料產生及下載作業 */
public interface FirBatchPins01Service {
	
	@SuppressWarnings("rawtypes")
	public int countFirBatchPins01(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchPins01ByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchPins01ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirBatchPins01(FirBatchPins01 firBatchPins01) throws SystemException, Exception;

	public Result insertFirBatchPins01(FirBatchPins01 firBatchPins01) throws SystemException, Exception;

	public Result findFirBatchPins01ByUK(Map params) throws SystemException, Exception;
	
}
