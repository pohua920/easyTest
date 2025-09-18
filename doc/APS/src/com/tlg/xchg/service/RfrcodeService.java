package com.tlg.xchg.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface RfrcodeService {
	
	@SuppressWarnings("rawtypes")
	public Result findRfrcodeByParams(Map params) throws SystemException, Exception;
	

}
