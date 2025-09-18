package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PrplClaim;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PrplClaimService {

	public int countPrplClaim(Map params) throws SystemException, Exception;
	
	public Result findFirClaim(Map params) throws SystemException, Exception;

	public Result findPrplClaimByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPrplClaimByParams(Map params) throws SystemException, Exception;

	public Result findPrplClaimByOid(BigDecimal oid) throws SystemException, Exception;

}
