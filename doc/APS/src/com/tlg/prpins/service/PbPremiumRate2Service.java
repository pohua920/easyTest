package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate2;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate2Service {

	public int countPbPremiumRate2(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate2ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate2ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate2ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate2(PbPremiumRate2 pbPremiumRate2) throws SystemException, Exception;

	public Result insertPbPremiumRate2(PbPremiumRate2 pbPremiumRate2) throws SystemException, Exception;

	public Result removePbPremiumRate2(BigDecimal oid) throws SystemException, Exception;

}
