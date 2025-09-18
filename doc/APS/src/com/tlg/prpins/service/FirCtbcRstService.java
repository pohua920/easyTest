package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRst;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirCtbcRstService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRstByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcRst(FirCtbcRst firCtbcRst) throws SystemException, Exception;

	public Result insertFirCtbcRst(FirCtbcRst firCtbcRst) throws SystemException, Exception;

	public Result removeFirCtbcRst(BigDecimal oid) throws SystemException, Exception;
	
	public Result findFirCtbcRstByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	public Result findForAps003Detail(PageInfo pageInfo) throws SystemException, Exception;
	
	//mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)
	@SuppressWarnings("rawtypes")
	public int countForAps003Detail(Map params) throws SystemException, Exception;
}
