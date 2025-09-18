package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpduserService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpduserByParams(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result selectForAjax005(Map params) throws SystemException, Exception;

}
