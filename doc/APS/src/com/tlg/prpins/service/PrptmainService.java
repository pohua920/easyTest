package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrptmainService {
	
	@SuppressWarnings("rawtypes")
	public int countPrptmain(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findPrptmainByParams(Map params) throws SystemException, Exception;
	

}
