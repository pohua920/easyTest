package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcBatchMain;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirCtbcBatchMainService {
	
	@SuppressWarnings("rawtypes")
	public int countFirCtbcBatchMain(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcBatchMainByParams(Map params) throws SystemException, Exception;
	
	public Result findFirCtbcBatchMainByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirCtbcBatchMain(FirCtbcBatchMain firCtbcBatchMain) throws SystemException, Exception;

	public Result insertFirCtbcBatchMain(FirCtbcBatchMain firCtbcBatchMain) throws SystemException, Exception;

	public Result removeFirCtbcBatchMain(BigDecimal oid) throws SystemException, Exception;
	

}
