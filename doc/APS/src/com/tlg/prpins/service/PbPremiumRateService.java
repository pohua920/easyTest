package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRateService {

	public int countPbPremiumRate(Map params) throws SystemException, Exception;

	public Result findPbPremiumRateByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRateByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRateByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate(PbPremiumRate pbPremiumRate) throws SystemException, Exception;

	public Result insertPbPremiumRate(PbPremiumRate pbPremiumRate) throws SystemException, Exception;

	public Result removePbPremiumRate(BigDecimal oid) throws SystemException, Exception;

}
