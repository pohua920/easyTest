package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardClaimInsData;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ReinsInwardClaimInsDataService {

	public int countReinsInwardClaimInsData(Map params) throws SystemException, Exception;

	public Result findReinsInwardClaimInsDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findReinsInwardClaimInsDataByParams(Map params) throws SystemException, Exception;

	public Result findReinsInwardClaimInsDataByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateReinsInwardClaimInsData(ReinsInwardClaimInsData reinsInwardClaimInsData) throws SystemException, Exception;

	public Result insertReinsInwardClaimInsData(ReinsInwardClaimInsData reinsInwardClaimInsData) throws SystemException, Exception;

	public Result removeReinsInwardClaimInsData(BigDecimal oid) throws SystemException, Exception;
	

}
