package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrpdcodeService {
	
	@SuppressWarnings("rawtypes")
	public int countPrpdcode(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findPrpdcodeByParams(Map params) throws SystemException, Exception;
	
	public Result findPrpdcodeByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

}
