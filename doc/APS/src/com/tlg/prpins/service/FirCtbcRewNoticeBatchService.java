package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewNoticeBatch;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirCtbcRewNoticeBatchService {
	
	@SuppressWarnings("rawtypes")
	public int countFirCtbcRewNoticeBatch(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRewNoticeBatchByParams(Map params) throws SystemException, Exception;
	
	public Result findFirCtbcRewNoticeBatchByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirCtbcRewNoticeBatch(FirCtbcRewNoticeBatch firCtbcRewNoticeBatch) throws SystemException, Exception;

	public Result insertFirCtbcRewNoticeBatch(FirCtbcRewNoticeBatch firCtbcRewNoticeBatch) throws SystemException, Exception;

	public Result removeFirCtbcRewNoticeBatch(BigDecimal oid) throws SystemException, Exception;
	

}
