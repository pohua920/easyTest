package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewSnn;
import com.tlg.util.Result;

public interface FirCtbcRewSnnService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRewSnnByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcRewSnn(FirCtbcRewSnn firCtbcRewSnn) throws SystemException, Exception;

	public Result insertFirCtbcRewSnn(FirCtbcRewSnn firCtbcRewSnn) throws SystemException, Exception;

	public Result removeFirCtbcRewSnn(BigDecimal oid) throws SystemException, Exception;
	
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 start*/
	public Result findCtbcRewSnnForSameId(Map params) throws SystemException, Exception;
	
	public Result findCtbcRewSnnForSameName(Map params) throws SystemException, Exception;
	/*mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理 end*/
}
