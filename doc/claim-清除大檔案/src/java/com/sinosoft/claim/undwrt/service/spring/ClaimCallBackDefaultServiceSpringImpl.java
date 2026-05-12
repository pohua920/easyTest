package com.sinosoft.claim.undwrt.service.spring;

import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLcompensate;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLcompensateService;
import com.sinosoft.claim.undwrt.service.facade.ClaimCallBackService;

/***
 * 核赔回调处理函数实现类
 * @author 中科软
 * @version 1.0
 *
 */
public class ClaimCallBackDefaultServiceSpringImpl implements ClaimCallBackService{
	private PrpLcompensateService prpLcompensateService;
	private PrpLclaimService prpLclaimService;
	
	/**
	 * 理赔核赔回调处理函数
	 * @param businessType 业务类型
	 * @param businessNo 业务号码
	 */
	public void callBack(String businessType, String businessNo) throws Exception {
		if("C".equals(businessType)){
//			PrpLcompensateDto prpLcompensateDto = new BLPrpLcompensateAction().findByPrimaryKey(dbManager, businessNo);
			PrpLcompensate prpLcompensat = prpLcompensateService.findPrpLcompensate(businessNo);
			String strClaimNo = prpLcompensat.getClaimNo();
//			PrpLclaimDto prpLclaimDto = new BLPrpLclaimAction().findByPrimaryKey(dbManager,strClaimNo);
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(strClaimNo);
			//计算书可出多张，回写立案表时要为多次赔付金额之和
			double dbSumPaid = prpLclaim.getSumPaid();
			dbSumPaid = dbSumPaid + prpLcompensat.getSumPaid();
			prpLclaim.setSumPaid(dbSumPaid);
			prpLclaim.setCurrency(prpLcompensat.getCurrency());
			prpLclaimService.update(prpLclaim);
//			new BLPrpLclaimAction().update(dbManager, prpLclaimDto);			
		}
		
	}
	public PrpLcompensateService getPrpLcompensateService() {
		return prpLcompensateService;
	}
	public void setPrpLcompensateService(PrpLcompensateService prpLcompensateService) {
		this.prpLcompensateService = prpLcompensateService;
	}
	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}
	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
}
