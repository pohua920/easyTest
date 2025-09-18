package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpcopycommissionService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcopycommissionByParams(Map params) throws SystemException, Exception;
	
}
