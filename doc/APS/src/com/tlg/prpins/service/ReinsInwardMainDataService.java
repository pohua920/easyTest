package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardMainData;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ReinsInwardMainDataService {

	public int countReinsInwardMainData(Map params) throws SystemException, Exception;

	public Result findReinsInwardMainDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findReinsInwardMainDataByParams(Map params) throws SystemException, Exception;

	public Result findReinsInwardMainDataByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateReinsInwardMainData(ReinsInwardMainData reinsInwardMainData) throws SystemException, Exception;

	public Result insertReinsInwardMainData(ReinsInwardMainData reinsInwardMainData) throws SystemException, Exception;

	public Result removeReinsInwardMainData(BigDecimal oid) throws SystemException, Exception;
	
	public int queryCurrentEndorseNo(Map params) throws SystemException, Exception;
	

}
