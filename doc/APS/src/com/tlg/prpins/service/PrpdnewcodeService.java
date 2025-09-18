package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.Prpdnewcode;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdnewcodeService {
	
	@SuppressWarnings("rawtypes")
	public int countPrpdnewcode(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findPrpdnewcodeByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpdnewcodeByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
//	public Result updatePrpdnewcode(Prpdnewcode prpdnewcode) throws SystemException, Exception;
//
//	public Result insertPrpdnewcode(Prpdnewcode prpdnewcode) throws SystemException, Exception;
//
//	public Result removePrpdnewcode(BigDecimal oid) throws SystemException, Exception;
	
}
