package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchPins01SpMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public interface FirBatchPins01SpMainService {
	
	@SuppressWarnings("rawtypes")
	public int countFirBatchPins01SpMain(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchPins01SpMainByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchPins01SpMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirBatchPins01SpMain(FirBatchPins01SpMain firBatchPins01SpMain) throws SystemException, Exception;

	public Result insertFirBatchPins01SpMain(FirBatchPins01SpMain firBatchPins01SpMain) throws SystemException, Exception;

	public Result findFirBatchPins01SpMainByUK(Map params) throws SystemException, Exception;
	
	public Result removeFirBatchPins01SpMain(BigDecimal oid) throws SystemException, Exception;
	
	public Result findForAps031ExcelByParams(Map params) throws SystemException, Exception;
	
	public void truncateFirBatchPins01SpMain() throws SystemException, Exception;
	
	public int countForAps031Excel(Map params) throws SystemException, Exception;
}
