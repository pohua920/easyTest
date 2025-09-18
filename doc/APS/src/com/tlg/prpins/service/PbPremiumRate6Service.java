package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate6;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate6Service {

	public int countPbPremiumRate6(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate6ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate6ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate6ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate6(PbPremiumRate6 pbPremiumRate6) throws SystemException, Exception;

	public Result insertPbPremiumRate6(PbPremiumRate6 pbPremiumRate6) throws SystemException, Exception;

	public Result removePbPremiumRate6(BigDecimal oid) throws SystemException, Exception;
	

}
