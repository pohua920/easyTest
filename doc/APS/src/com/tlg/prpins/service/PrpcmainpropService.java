package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpcmainpropService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcmainpropByParams(Map params) throws SystemException, Exception;

}
