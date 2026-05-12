package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;


import com.sinosoft.claim.schema.model.PrpLuser;
import com.sinosoft.claim.schema.model.VehicleClaimApiLog;
import com.sinosoft.claim.schema.service.facade.VehicleClaimApiLogService;

/**
 * mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
 */
public class VehicleClaimApiLogServiceSpringImpl extends GenericDaoHibernate<VehicleClaimApiLog, String> implements VehicleClaimApiLogService {
	
	@Override
	public void save(VehicleClaimApiLog vehicleClaimApiLog) throws Exception {
		logger.info("保存VehicleClaimApiLog信息");
		super.save(vehicleClaimApiLog);
		
	}

	@Override
	public void save(List<VehicleClaimApiLog> list) throws Exception {
		logger.info("保存VehicleClaimApiLog信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除PrpLuser信息编号为" + Id + "的PrpLuser信息");
		super.deleteByPK(VehicleClaimApiLog.class, Id);
	}

	@Override
	public VehicleClaimApiLog findVehicleClaimApiLog(String Id) throws Exception {
		logger.info("查询VehicleClaimApiLog信息编号为" + Id + "的VehicleClaimApiLog信息");
		return super.get(VehicleClaimApiLog.class, Id);
	}

	@Override
	public Page findVehicleClaimApiLog(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取VehicleClaimApiLog信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<VehicleClaimApiLog> findVehicleClaimApiLog(QueryRule queryRule)
			throws Exception {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

}
