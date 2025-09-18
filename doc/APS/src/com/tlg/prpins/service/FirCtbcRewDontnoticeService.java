package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcRewDontnotice;
import com.tlg.util.Result;

public interface FirCtbcRewDontnoticeService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcRewDontnoticeByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcRewDontnotice(FirCtbcRewDontnotice firCtbcRewDontnotice) throws SystemException, Exception;

	public Result insertFirCtbcRewDontnotice(FirCtbcRewDontnotice firCtbcRewDontnotice) throws SystemException, Exception;

	public Result removeFirCtbcRewDontnotice(BigDecimal oid) throws SystemException, Exception;
	

}
