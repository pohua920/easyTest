package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbPremiumRate10;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbPremiumRate10Service {

	public int countPbPremiumRate10(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate10ByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbPremiumRate10ByParams(Map params) throws SystemException, Exception;

	public Result findPbPremiumRate10ByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updatePbPremiumRate10(PbPremiumRate10 pbPremiumRate10) throws SystemException, Exception;

	public Result insertPbPremiumRate10(PbPremiumRate10 pbPremiumRate10) throws SystemException, Exception;

	public Result removePbPremiumRate10(BigDecimal oid) throws SystemException, Exception;
	

}
