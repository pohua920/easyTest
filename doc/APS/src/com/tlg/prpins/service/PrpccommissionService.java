package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpccommissionService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpccommissionByParams(Map params) throws SystemException, Exception;
	
}
