package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.PbQueryDetail;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface PbQueryDetailService {

	public int countPbQueryDetail(Map params) throws SystemException, Exception;

	public Result findPbQueryDetailByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findPbQueryDetailByParams(Map params) throws SystemException, Exception;

	public Result findPbQueryDetailByOid(BigDecimal oid) throws SystemException, Exception;
	
	public Result findPbQueryDetailForScore(Map params) throws SystemException, Exception;
	
	public BigDecimal findPbQueryDetailForResultScore(Map params) throws SystemException, Exception;
	
	public Result findPbQueryDetailForPbScore(Map params) throws SystemException, Exception;
	
	public BigDecimal findPbQueryDetailForPbResultScore(Map params) throws SystemException, Exception;

	public Result updatePbQueryDetail(PbQueryDetail pbQueryDetail) throws SystemException, Exception;

	public Result insertPbQueryDetail(PbQueryDetail pbQueryDetail) throws SystemException, Exception;

	public Result removePbQueryDetail(BigDecimal oid) throws SystemException, Exception;
	

}
