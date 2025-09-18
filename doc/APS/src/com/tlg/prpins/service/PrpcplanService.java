package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpcplanService {
	
	@SuppressWarnings("rawtypes")
	public int countPrpcplan(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcplanByParams(Map params) throws SystemException, Exception;
	

}
