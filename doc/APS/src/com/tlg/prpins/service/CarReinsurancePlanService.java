package com.tlg.prpins.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tlg.exception.SystemException;
import com.tlg.prpins.entity.CarReinsurancePlan;
import com.tlg.util.PageInfo;
import com.tlg.util.Result;

/** mantis：CAR0553，處理人員：DP0706，需求單編號：CAR0553.APS-車險再保註記設定維護功能 */
public interface CarReinsurancePlanService {
	
	public Result removeCarReinsurancePlan(BigDecimal oid) throws SystemException, Exception;
	
	public Result insertCarReinsurancePlan(CarReinsurancePlan entity) throws SystemException, Exception;
	
	public Result findCarReinsurancePlanByPageInfo(PageInfo pageInfo) throws SystemException, Exception;
	
	public Result updateCarReinsurancePlan(CarReinsurancePlan entity) throws SystemException, Exception;
	
	public Result findCarReinsurancePlanByOid(BigDecimal oid) throws SystemException, Exception;
	
	public Result findCarReinsurancePlanByParams(Map params) throws SystemException, Exception;
}
