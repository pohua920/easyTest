package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate8;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate8Service {

	public int countPbPremiumRate8(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate8ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate8ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate8ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate8(PbPremiumRate8 pbPremiumRate8) throws SystemException, Exception;

	public Result insertPbPremiumRate8(PbPremiumRate8 pbPremiumRate8) throws SystemException, Exception;

	public Result removePbPremiumRate8(BigDecimal oid) throws SystemException, Exception;
	

}
