package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.util.Result;

public interface PrpdrationclausekindService {
	
	@SuppressWarnings("rawtypes")
	public Result selectForEpolicy(Map params) throws SystemException, Exception;

}
