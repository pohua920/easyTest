package com.sinosoft.claim.schema.service.spring;
/**
 * 立案基本信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainCasualty;
import com.sinosoft.claim.schema.service.facade.PrpCmainCasualtyService;

public class PrpCmainCasualtyServiceSpringImpl extends
GenericDaoHibernate<PrpCmainCasualty, String> implements PrpCmainCasualtyService{

	@Override
	public void save(PrpCmainCasualty PrpCmainCasualty) throws Exception {
		logger.info("保存立案基本信息信息");
		super.save(PrpCmainCasualty);
		
	}

	@Override
	public void save(List<PrpCmainCasualty> list) throws Exception {
		logger.info("保存立案基本信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除立案基本信息编号为" + claimNo + "的立案基本信息");
		super.deleteByPK(PrpCmainCasualty.class, claimNo);
	}

	@Override
	public PrpCmainCasualty findPrpCmainCasualty(String claimNo) throws Exception {
		logger.info("查询立案基本信息编号为" + claimNo + "的立案基本信息");
		return super.get(PrpCmainCasualty.class,claimNo);
	}
    
	@Override
	public Page findPrpCmainCasualty(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取立案基本信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpCmainCasualty> findPrpCmainCasualty(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
