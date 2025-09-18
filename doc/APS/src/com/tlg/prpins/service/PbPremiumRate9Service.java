package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate9;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate9Service {

	public int countPbPremiumRate9(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate9ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate9ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate9ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate9(PbPremiumRate9 pbPremiumRate9) throws SystemException, Exception;

	public Result insertPbPremiumRate9(PbPremiumRate9 pbPremiumRate9) throws SystemException, Exception;

	public Result removePbPremiumRate9(BigDecimal oid) throws SystemException, Exception;
	

}
