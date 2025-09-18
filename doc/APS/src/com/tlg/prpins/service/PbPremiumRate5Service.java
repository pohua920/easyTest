package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate5;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate5Service {

	public int countPbPremiumRate5(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate5ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate5ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate5ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate5(PbPremiumRate5 pbPremiumRate5) throws SystemException, Exception;

	public Result insertPbPremiumRate5(PbPremiumRate5 pbPremiumRate5) throws SystemException, Exception;

	public Result removePbPremiumRate5(BigDecimal oid) throws SystemException, Exception;
	

}
