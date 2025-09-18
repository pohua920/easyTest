package com.tlg.prpins.service;

import java.util.Map;

import com.tlg.exception.SystemException;

public interface UwblacklistService {

	@SuppressWarnings("rawtypes")
	public int countUwblacklist(Map params) throws SystemException, Exception;
	

}
