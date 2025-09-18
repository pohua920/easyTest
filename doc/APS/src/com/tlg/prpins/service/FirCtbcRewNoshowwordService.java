package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewNoshowword;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirCtbcRewNoshowwordService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRewNoshowwordByParams(Map params) throws SystemException, Exception;
	
	public Result findFirCtbcRewNoshowwordByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateFirCtbcRewNoshowword(FirCtbcRewNoshowword firCtbcRewNoshowword) throws SystemException, Exception;

	public Result insertFirCtbcRewNoshowword(FirCtbcRewNoshowword firCtbcRewNoshowword) throws SystemException, Exception;

	public Result removeFirCtbcRewNoshowword(BigDecimal oid) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public int countByParams(Map params) throws SystemException, Exception;
	
	//mantis：FIR0220，處理人員：BJ085，需求單編號：FIR0220 中信代理投保通知處理
	public Result findFirCtbcRewNoshowwordByUK(Map params) throws SystemException, Exception;
}
