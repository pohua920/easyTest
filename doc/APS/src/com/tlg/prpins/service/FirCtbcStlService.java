package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirCtbcStl;
import com.tlg.util.Result;

public interface FirCtbcStlService {
	
	@SuppressWarnings("rawtypes")
	public Result findFirCtbcStlByParams(Map params) throws SystemException, Exception;
	
	public Result updateFirCtbcStl(FirCtbcStl firCtbcStl) throws SystemException, Exception;

	public Result insertFirCtbcStl(FirCtbcStl firCtbcStl) throws SystemException, Exception;

	public Result removeFirCtbcStl(BigDecimal oid) throws SystemException, Exception;
	

}
