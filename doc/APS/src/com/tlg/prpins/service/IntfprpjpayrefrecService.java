package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface IntfprpjpayrefrecService {
	
	@SuppressWarnings("rawtypes")
	public int countIntfprpjpayrefrec(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findIntfprpjpayrefrecByParams(Map params) throws SystemException, Exception;
	

}
