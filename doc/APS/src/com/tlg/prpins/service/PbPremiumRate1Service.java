package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate1;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate1Service {

	public int countPbPremiumRate1(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate1ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate1ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate1ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate1(PbPremiumRate1 pbPremiumRate1) throws SystemException, Exception;

	public Result insertPbPremiumRate1(PbPremiumRate1 pbPremiumRate1) throws SystemException, Exception;

	public Result removePbPremiumRate1(BigDecimal oid) throws SystemException, Exception;

}
