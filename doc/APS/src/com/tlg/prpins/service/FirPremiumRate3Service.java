package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.FirPremiumRate3;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface FirPremiumRate3Service {

	public int countFirPremiumRate3(Map params) throws SystemException, Exception;

	public double findFirPremiumRate3ForDeduction(Map params) throws SystemException, Exception;
	
	public Result findFirPremiumRate3ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findFirPremiumRate3ByParams(Map params) throws SystemException, Exception;

	public Result findFirPremiumRate3ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateFirPremiumRate3(FirPremiumRate3 firPremiumRate3) throws SystemException, Exception;

	public Result insertFirPremiumRate3(FirPremiumRate3 firPremiumRate3) throws SystemException, Exception;

	public Result removeFirPremiumRate3(BigDecimal oid) throws SystemException, Exception;
	

}
