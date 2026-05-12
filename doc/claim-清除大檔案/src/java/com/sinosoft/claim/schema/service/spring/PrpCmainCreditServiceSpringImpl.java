package com.sinosoft.claim.schema.service.spring;
/**
 * 信用险保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainCredit;
import com.sinosoft.claim.schema.service.facade.PrpCmainCreditService;

public class PrpCmainCreditServiceSpringImpl extends
GenericDaoHibernate<PrpCmainCredit, String> implements PrpCmainCreditService{

	@Override
	public void save(PrpCmainCredit PrpCmainCredit) throws Exception {
		logger.info("保存信用险保单信息信息");
		super.save(PrpCmainCredit);
		
	}

	@Override
	public void save(List<PrpCmainCredit> list) throws Exception {
		logger.info("保存信用险保单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除信用险保单信息编号为" + claimNo + "的信用险保单信息");
		super.deleteByPK(PrpCmainCredit.class, claimNo);
	}

	@Override
	public PrpCmainCredit findPrpCmainCredit(String claimNo) throws Exception {
		logger.info("查询信用险保单信息编号为" + claimNo + "的信用险保单信息");
		return super.get(PrpCmainCredit.class,claimNo);
	}
    
	@Override
	public Page findPrpCmainCredit(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取信用险保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpCmainCredit> findPrpCmainCredit(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
