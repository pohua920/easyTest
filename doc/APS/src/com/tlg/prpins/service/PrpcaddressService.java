package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpcaddressService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcaddressByParams(Map params) throws SystemException, Exception;

}
