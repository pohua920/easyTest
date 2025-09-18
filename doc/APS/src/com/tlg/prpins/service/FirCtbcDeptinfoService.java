package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcDeptinfo;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirCtbcDeptinfoService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcDeptinfoByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcDeptinfo(FirCtbcDeptinfo firCtbcDeptinfo) throws SystemException, Exception;

	public Result insertFirCtbcDeptinfo(FirCtbcDeptinfo firCtbcDeptinfo) throws SystemException, Exception;

	public Result removeFirCtbcDeptinfo(BigDecimal oid) throws SystemException, Exception;
	/* mantis：FIR0182，處理人員：BJ085，需求單編號：FIR0182 中信新件服務人員維護作業 start */
	public Result findFirCtbcDeptinfoByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirCtbcDeptinfoByOid(BigDecimal oid) throws SystemException,Exception;	
	/* mantis：FIR0182，處理人員：BJ085，需求單編號：FIR0182 中信新件服務人員維護作業 end */
}
