package com.sinosoft.claim.schema.service.spring;

/**
 * 保险地址信息接口实现类
 * @author 中科软
 */
import ins.framework.common.DateTime;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.EndorseViewHelper;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpLclaim;
import com.sinosoft.claim.schema.model.PrpLdisabilityLimit;
import com.sinosoft.claim.schema.model.PrpLdisabilityLimitId;
import com.sinosoft.claim.schema.service.facade.PrpLclaimService;
import com.sinosoft.claim.schema.service.facade.PrpLdisabilityLimitService;

public class PrpLdisabilityLimitServiceSpringImpl extends GenericDaoHibernate<PrpLdisabilityLimit, PrpLdisabilityLimitId> implements PrpLdisabilityLimitService {

	private EndorseViewHelper endorseViewHelper;
	private PrpLclaimService prpLclaimService;
	public void save(PrpLdisabilityLimit prpLdisabilityLimit) throws Exception {
		super.save(prpLdisabilityLimit);

	}

	public void save(List<PrpLdisabilityLimit> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	public void delete(PrpLdisabilityLimitId prpLdisabilityLimitId) throws Exception {
		super.deleteByPK(PrpLdisabilityLimit.class, prpLdisabilityLimitId);
	}

	public PrpLdisabilityLimit findPrpLdisabilityLimit(PrpLdisabilityLimitId prpLdisabilityLimitId) throws Exception {
		return super.get(PrpLdisabilityLimit.class, prpLdisabilityLimitId);
	}

	public Page findPrpLdisabilityLimit(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpLdisabilityLimit> findPrpLdisabilityLimit(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * @param claimNo
	 * @param ratingCode
	 * @return
	 * @throws Exception
	 * 根据立案号和伤残等级查询赔付限额
	 */
	public double getPrpLdisabilityLimitFee(String claimNo,String ratingCode)throws Exception{
		double limitFee = 0;
		if(claimNo!=null&&!"".equals(claimNo)){
			PrpLclaim prpLclaim = prpLclaimService.findPrpLclaim(claimNo);
			DateTime dateTime = new DateTime(prpLclaim.getDamageStartDate(),DateTime.YEAR_TO_DAY);
			String policyNo = prpLclaim.getPolicyNo();
			String damageDate = new DateTime(prpLclaim.getDamageStartDate()).toString();
			String damageHour = prpLclaim.getDamageStartHour();
			PrpCmain prpCmain = this.endorseViewHelper.findPrpCmain(policyNo, damageDate , damageHour);
			List<PrpCitemKind> prpCitemKindList = this.endorseViewHelper.findPrpCitemKind(policyNo, damageDate, damageHour, prpCmain.getRiskCode(), null);
			StringBuffer kindCodes = new StringBuffer();
			QueryRule queryRule = QueryRule.getInstance();
			queryRule.addEqual("id.riskCode", prpLclaim.getRiskCode());
			queryRule.addEqual("id.ratingCode", ratingCode);
			queryRule.addEqual("status","1");
			for(int i=0;i<prpCitemKindList.size();i++){
				PrpCitemKind prpCitemKind = prpCitemKindList.get(i);
				kindCodes.append("'"+prpCitemKind.getKindCode()+"',");
			}
			if(kindCodes.length()>0){
				String sql = "kindCode in("+kindCodes.substring(0, kindCodes.length()-1)+")"
				+" and startTime <= to_date('"+dateTime.toString()+"','yyyy-MM-dd') and (endTime >= to_date('"+dateTime.toString()+"','yyyy-MM-dd') or endTime is null)";
				queryRule.addSql(sql);
				List<PrpLdisabilityLimit> list = this.find(queryRule);
				if(list.size()>0){
					limitFee = list.get(0).getLimitFee();
				}
			}
		}
		return limitFee;
	}

	public EndorseViewHelper getEndorseViewHelper() {
		return endorseViewHelper;
	}

	public void setEndorseViewHelper(EndorseViewHelper endorseViewHelper) {
		this.endorseViewHelper = endorseViewHelper;
	}

	public PrpLclaimService getPrpLclaimService() {
		return prpLclaimService;
	}

	public void setPrpLclaimService(PrpLclaimService prpLclaimService) {
		this.prpLclaimService = prpLclaimService;
	}
	
}
