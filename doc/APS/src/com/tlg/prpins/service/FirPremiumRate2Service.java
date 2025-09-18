package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremiumRate2;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirPremiumRate2Service {

	public int countFirPremiumRate2(Map params) throws SystemException, Exception;

	public Result findFirPremiumRate2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirPremiumRate2ByParams(Map params) throws SystemException, Exception;

	public Result findFirPremiumRate2ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirPremiumRate2(FirPremiumRate2 firPremiumRate2) throws SystemException, Exception;

	public Result insertFirPremiumRate2(FirPremiumRate2 firPremiumRate2) throws SystemException, Exception;

	public Result removeFirPremiumRate2(BigDecimal oid) throws SystemException, Exception;
	

}
