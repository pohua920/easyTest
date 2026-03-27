package com.sinosoft.claim.schema.service.spring;

/**
 * 信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpCitemKind;
import com.sinosoft.claim.schema.model.PrpCmain;
import com.sinosoft.claim.schema.model.PrpDriskRate;
import com.sinosoft.claim.schema.model.PrpDriskRateId;
import com.sinosoft.claim.schema.service.facade.PrpDriskRateService;

public class PrpDriskRateServiceSpringImpl extends GenericDaoHibernate<PrpDriskRate, PrpDriskRateId> implements PrpDriskRateService {
	@Override
	public void save(PrpDriskRate prpDriskRate) throws Exception {
		logger.info("保存PrpDriskRate信息");
		super.save(prpDriskRate);
	}

	@Override
	public void save(List<PrpDriskRate> list) throws Exception {
		logger.info("保存PrpDriskRate信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpDriskRateId prpDriskRateId) throws Exception {
		super.deleteByPK(PrpDriskRate.class, prpDriskRateId);
	}

	@Override
	public PrpDriskRate findPrpDriskRate(PrpDriskRateId prpDriskRateId) throws Exception {
		return super.get(PrpDriskRate.class, prpDriskRateId);
	}

	@Override
	public Page findPrpDriskRate(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpDriskRate> findPrpDriskRate(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 查询险别的日额
	 * @return
	 * @throws Exception
	 */
	public double findDayAmount(PrpCmain prpCmain,PrpCitemKind prpCitemKind)throws Exception{
		double dayAmount = 0D;
		String tcol1 = "10";
		//是否直接业务
		if(prpCmain.getChannelType() != null){
			//40是11-12-13并勾选直接业务
			if("1".equals(prpCmain.getDirectBusiness())&&"11-12-13".indexOf(prpCmain.getChannelType())>-1){
				tcol1 = "40";
			}else{
				tcol1 = prpCmain.getChannelType().substring(0,1)+"0";
			}
		}
		String sql = "select rate from prpdriskrate where  riskCode = '"+prpCitemKind.getRiskCode()+"' and kindCode = '"+prpCitemKind.getKindCode()+"' and tcol1 = '"+tcol1+"' and rownum = 1 ";
		String sqlAdd = sql + " and tcol2 in ( select dutyLevel from prpCinsuredNature where policyNo = '"+prpCmain.getPolicyNo()+"' and serialNo = "+prpCitemKind.getFamilyNo()+")";
		List<Object> list = (List<Object>) HibernateUtils.findbySql(super.getSession(), sqlAdd);
		Object obj = null;
		if(list.size()>0){
			obj = list.get(0);
		}else{
			//没有查询到，取第一条
			list = (List<Object>) HibernateUtils.findbySql(super.getSession(), sql);
			if(list.size()>0){
				obj = list.get(0);
			}
		}
		if(obj!=null){
			dayAmount = ((Number)obj).doubleValue();
		}
		return dayAmount;
	}

}
