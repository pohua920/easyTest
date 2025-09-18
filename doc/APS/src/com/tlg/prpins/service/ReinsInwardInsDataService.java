package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.ReinsInwardInsData;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface ReinsInwardInsDataService {

	public int countReinsInwardInsData(Map params) throws SystemException, Exception;

	public Result findReinsInwardInsDataByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findReinsInwardInsDataByParams(Map params) throws SystemException, Exception;

	public Result findReinsInwardInsDataByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateReinsInwardInsData(ReinsInwardInsData reinsInwardInsData) throws SystemException, Exception;

	public Result insertReinsInwardInsData(ReinsInwardInsData reinsInwardInsData) throws SystemException, Exception;

	public Result removeReinsInwardInsData(BigDecimal oid) throws SystemException, Exception;
	

}
