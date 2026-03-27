package com.sinosoft.claim.schema.service.spring;
/**
 * 立案基本信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainInvest;
import com.sinosoft.claim.schema.service.facade.PrpCmainInvestService;

public class PrpCmainInvestServiceSpringImpl extends
GenericDaoHibernate<PrpCmainInvest, String> implements PrpCmainInvestService{

	@Override
	public void save(PrpCmainInvest PrpCmainInvest) throws Exception {
		logger.info("保存立案基本信息信息");
		super.save(PrpCmainInvest);
		
	}

	@Override
	public void save(List<PrpCmainInvest> list) throws Exception {
		logger.info("保存立案基本信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除立案基本信息编号为" + claimNo + "的立案基本信息");
		super.deleteByPK(PrpCmainInvest.class, claimNo);
	}

	@Override
	public PrpCmainInvest findPrpCmainInvest(String claimNo) throws Exception {
		logger.info("查询立案基本信息编号为" + claimNo + "的立案基本信息");
		return super.get(PrpCmainInvest.class,claimNo);
	}
    
	@Override
	public Page findPrpCmainInvest(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取立案基本信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpCmainInvest> findPrpCmainInvest(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
