package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLemailConfig;
import com.sinosoft.claim.schema.service.facade.PrpLemailConfigService;

public class PrpLemailConfigServiceSpringImpl extends GenericDaoHibernate<PrpLemailConfig, String> implements PrpLemailConfigService {

	@Override
	public void save(PrpLemailConfig prpLemailConfig) throws Exception {
		logger.info("保存PrpLemailConfig信息");
		super.save(prpLemailConfig);
		
	}

	@Override
	public void save(List<PrpLemailConfig> list) throws Exception {
		logger.info("保存PrpLemailConfig信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String Id) throws Exception {
		logger.info("删除PrpLemailConfig信息编号为" + Id + "的PrpLemailConfig信息");
		super.deleteByPK(PrpLemailConfig.class, Id);
	}

	@Override
	public PrpLemailConfig findPrpLemailConfig(String Id) throws Exception {
		logger.info("查询PrpLemailConfig信息编号为" + Id + "的PrpLemailConfig信息");
		return super.get(PrpLemailConfig.class, Id);
	}

	@Override
	public Page findPrpLemailConfig(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PrpLemailConfig信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLemailConfig> findPrpLemailConfig(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

}
