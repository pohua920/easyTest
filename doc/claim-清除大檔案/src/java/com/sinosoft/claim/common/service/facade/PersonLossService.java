package com.sinosoft.claim.common.service.facade;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLpersonLoss;
/**
 * 初始化人伤信息
 * @Description 由com.sinosoft.claim.bl.action.custom.BLPersonLossAction迁移
 * @author 中科软
 */
public interface PersonLossService {
	
	public void initPersonLoss(String configCode,String riskCode,List<PrpLpersonLoss> personLossList,double medicalLimit,double deathLimit)throws Exception;

}
