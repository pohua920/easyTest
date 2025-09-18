package com.tlg.xchg.service;

import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.xchg.entity.CtbcPetWs10704;
import com.tlg.util.Result;

public interface CtbcPetWs10704Service {
	
	@SuppressWarnings("rawtypes")
	public int countCtbcPetWs10704(Map params) throws SystemException, Exception;
	
	@SuppressWarnings("rawtypes")
	public Result findCtbcPetWs10704ByParams(Map params) throws SystemException, Exception;
	
	public Result updateCtbcPetWs10704(CtbcPetWs10704 ctbcPetWs10704) throws SystemException, Exception;

}
