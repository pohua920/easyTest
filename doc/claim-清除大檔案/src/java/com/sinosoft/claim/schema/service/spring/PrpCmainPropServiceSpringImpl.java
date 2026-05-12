package com.sinosoft.claim.schema.service.spring;
/**
 * 财产险保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainProp;
import com.sinosoft.claim.schema.service.facade.PrpCmainPropService;

public class PrpCmainPropServiceSpringImpl extends
GenericDaoHibernate<PrpCmainProp, String> implements PrpCmainPropService{

	@Override
	public void save(PrpCmainProp PrpCmainProp) throws Exception {
		logger.info("保存财产险保单信息信息");
		super.save(PrpCmainProp);
		
	}

	@Override
	public void save(List<PrpCmainProp> list) throws Exception {
		logger.info("保存财产险保单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除财产险保单信息编号为" + claimNo + "的财产险保单信息");
		super.deleteByPK(PrpCmainProp.class, claimNo);
	}

	@Override
	public PrpCmainProp findPrpCmainProp(String claimNo) throws Exception {
		logger.info("查询财产险保单信息编号为" + claimNo + "的财产险保单信息");
		return super.get(PrpCmainProp.class,claimNo);
	}
    
	@Override
	public Page findPrpCmainProp(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取财产险保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpCmainProp> findPrpCmainProp(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
