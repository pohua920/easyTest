package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardClaimData;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ReinsInwardClaimDataService {

	public int countReinsInwardClaimData(Map params) throws SystemException, Exception;

	public Result findReinsInwardClaimDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findReinsInwardClaimDataByParams(Map params) throws SystemException, Exception;

	public Result findReinsInwardClaimDataByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateReinsInwardClaimData(ReinsInwardClaimData reinsInwardClaimData) throws SystemException, Exception;

	public Result insertReinsInwardClaimData(ReinsInwardClaimData reinsInwardClaimData) throws SystemException, Exception;

	public Result removeReinsInwardClaimData(BigDecimal oid) throws SystemException, Exception;
	
	

}
