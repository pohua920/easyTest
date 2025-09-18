package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcBatchDtl;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirCtbcBatchDtlService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcBatchDtlByParams(Map params) throws SystemException, Exception;
	
	public Result findFirCtbcBatchDtlByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirCtbcBatchDtl(FirCtbcBatchDtl firCtbcBatchDtl) throws SystemException, Exception;

	public Result insertFirCtbcBatchDtl(FirCtbcBatchDtl firCtbcBatchDtl) throws SystemException, Exception;

	public Result removeFirCtbcBatchDtl(BigDecimal oid) throws SystemException, Exception;
	
	public Result findFirCtbcBatchDtlForFeedback(String batchNo) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findForAps002Detail(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countFirCtbcBatchDtl(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countFirCtbcBatchDtlJoinStl(Map params) throws SystemException, Exception;
	public Result findFirCtbcBatchDtlByPageInfoJoinStl(PageInfo pageInfo) throws SystemException, Exception;
}
