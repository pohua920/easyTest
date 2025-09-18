package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate7;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate7Service {

	public int countPbPremiumRate7(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate7ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate7ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate7ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate7(PbPremiumRate7 pbPremiumRate7) throws SystemException, Exception;

	public Result insertPbPremiumRate7(PbPremiumRate7 pbPremiumRate7) throws SystemException, Exception;

	public Result removePbPremiumRate7(BigDecimal oid) throws SystemException, Exception;
	

}
