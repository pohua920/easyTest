package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremiumRate;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirPremiumRateService {

	public int countFirPremiumRate(Map params) throws SystemException, Exception;

	public Result findFirPremiumRateByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirPremiumRateByParams(Map params) throws SystemException, Exception;

	public Result findFirPremiumRateByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirPremiumRate(FirPremiumRate firPremiumRate) throws SystemException, Exception;

	public Result insertFirPremiumRate(FirPremiumRate firPremiumRate) throws SystemException, Exception;

	public Result removeFirPremiumRate(BigDecimal oid) throws SystemException, Exception;
	

}
