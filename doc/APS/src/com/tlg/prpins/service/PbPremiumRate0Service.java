package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate0;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate0Service {

	public int countPbPremiumRate0(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate0ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate0ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate0ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate0(PbPremiumRate0 pbPremiumRate0) throws SystemException, Exception;

	public Result insertPbPremiumRate0(PbPremiumRate0 pbPremiumRate0) throws SystemException, Exception;

	public Result removePbPremiumRate0(BigDecimal oid) throws SystemException, Exception;
	

}
