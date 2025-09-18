package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpcitemkindService {
	
	@SuppressWarnings("rawtypes")
	public Result findPrpcitemkindByParams(Map params) throws SystemException, Exception;

}
