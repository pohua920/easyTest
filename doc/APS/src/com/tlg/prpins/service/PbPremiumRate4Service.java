package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate4;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate4Service {

	public int countPbPremiumRate4(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate4ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate4ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate4ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate4(PbPremiumRate4 pbPremiumRate4) throws SystemException, Exception;

	public Result insertPbPremiumRate4(PbPremiumRate4 pbPremiumRate4) throws SystemException, Exception;

	public Result removePbPremiumRate4(BigDecimal oid) throws SystemException, Exception;

}
