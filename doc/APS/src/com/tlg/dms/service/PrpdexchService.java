package com.tlg.dms.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.dms.entity.Prpdexch;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdexchService {
	
	@SuppressWarnings("rawtypes")
	public int countPrpdexch(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findPrpdexchByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpdexchByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updatePrpdexch(Prpdexch prpdexch) throws SystemException, Exception;

	public Result insertPrpdexch(Prpdexch prpdexch) throws SystemException, Exception;

	public Result removePrpdexch(Prpdexch prpdexch) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findPrpdexchByUK(Map params) throws SystemException, Exception;
	
}
