package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewFix180;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirCtbcRewFix180Service {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRewFix180ByParams(Map params) throws SystemException, Exception;
	
	public Result findFirCtbcRewFix180ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirCtbcRewFix180(FirCtbcRewFix180 firCtbcRewFix180) throws SystemException, Exception;

	public Result insertFirCtbcRewFix180(FirCtbcRewFix180 firCtbcRewFix180) throws SystemException, Exception;

	public Result removeFirCtbcRewFix180(BigDecimal oid) throws SystemException, Exception;
	
	//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
	public Result findFirCtbcRewFix180ByPK(Map params) throws SystemException, Exception;
}
