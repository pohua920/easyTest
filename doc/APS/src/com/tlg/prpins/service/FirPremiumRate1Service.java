package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremiumRate1;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirPremiumRate1Service {

	public int countFirPremiumRate1(Map params) throws SystemException, Exception;

	public Result findFirPremiumRate1ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirPremiumRate1ByParams(Map params) throws SystemException, Exception;

	public Result findFirPremiumRate1ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirPremiumRate1(FirPremiumRate1 firPremiumRate1) throws SystemException, Exception;

	public Result insertFirPremiumRate1(FirPremiumRate1 firPremiumRate1) throws SystemException, Exception;

	public Result removeFirPremiumRate1(BigDecimal oid) throws SystemException, Exception;
	

}
