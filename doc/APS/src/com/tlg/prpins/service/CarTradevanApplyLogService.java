package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.CarTradevanApplyLog;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

public interface CarTradevanApplyLogService {

	public int countCarTradevanApplyLog(Map params) throws SystemException, Exception;

	public Result findCarTradevanApplyLogByPageInfo(PageInfo pageInfo) throws SystemException, Exception;

	public Result findCarTradevanApplyLogByParams(Map params) throws SystemException, Exception;

	public Result findCarTradevanApplyLogByOid(BigDecimal oid) throws SystemException, Exception;

	public Result updateCarTradevanApplyLog(CarTradevanApplyLog carTradevanApplyLog) throws SystemException, Exception;

	public Result insertCarTradevanApplyLog(CarTradevanApplyLog carTradevanApplyLog) throws SystemException, Exception;

	public Result removeCarTradevanApplyLog(BigDecimal oid) throws SystemException, Exception;
	

}
