package com.sinosoft.claim.schema.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import java.util.Date;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLuser;
import com.sinosoft.claim.schema.model.VehicleClaimApiLog;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public interface VehicleClaimApiLogService {
	/**
	 * 
	 */
	public void save(VehicleClaimApiLog vehicleClaimApiLog) throws Exception;
	
	/**
	 * @throws Exceptionuan
	 */
	public void save(List<VehicleClaimApiLog> list) throws Exception;
	
	/**
	 */
	public void delete(String id) throws Exception;

	/**
	 */
	public void update(VehicleClaimApiLog vehicleClaimApiLog) throws Exception;

	/**
	 */
	public VehicleClaimApiLog findVehicleClaimApiLog(String id) throws Exception;
	
	/**
	 */
	public Page findVehicleClaimApiLog(QueryRule queryRule, int pageNo, int pageSize) throws Exception;
	/**
	 */
	public List<VehicleClaimApiLog> findVehicleClaimApiLog(QueryRule queryRule) throws Exception;
	
}
