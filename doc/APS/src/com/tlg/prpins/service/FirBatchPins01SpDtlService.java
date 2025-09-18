package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirBatchPins01SpDtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/* mantis：FIR0436，處理人員：BJ085，需求單編號：FIR0436 住火-APS保單通訊資料產生及下載作業批次作業 */
public interface FirBatchPins01SpDtlService {
	
	@SuppressWarnings("rawtypes")
	public int countFirBatchPins01SpDtl(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirBatchPins01SpDtlByParams(Map params) throws SystemException, Exception;
	
	public Result findFirBatchPins01SpDtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirBatchPins01SpDtl(FirBatchPins01SpDtl firBatchPins01SpDtl) throws SystemException, Exception;

	public Result insertFirBatchPins01SpDtl(FirBatchPins01SpDtl firBatchPins01SpDtl) throws SystemException, Exception;

	public Result findFirBatchPins01SpDtlByUK(Map params) throws SystemException, Exception;
	
	public Result removeFirBatchPins01SpDtl(BigDecimal oid) throws SystemException, Exception;
	
	public void truncateFirBatchPins01SpDtl() throws SystemException, Exception;
}
