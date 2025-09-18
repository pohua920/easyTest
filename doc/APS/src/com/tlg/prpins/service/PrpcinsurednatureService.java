package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpcinsurednatureService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcinsurednatureByParams(Map params) throws SystemException, Exception;

}
