package com.sinosoft.claim.schema.service.spring;
/**
 * 代理赔保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclaimAgent;
import com.sinosoft.claim.schema.service.facade.PrpLclaimAgentService;

public class PrpLclaimAgentServiceSpringImpl extends
GenericDaoHibernate<PrpLclaimAgent, String> implements PrpLclaimAgentService{

	@Override
	public void save(PrpLclaimAgent prpLclaimAgent) throws Exception {
		logger.info("保存代理赔保单信息");
		super.save(prpLclaimAgent);
		
	}

	@Override
	public void save(List<PrpLclaimAgent> list) throws Exception {
		logger.info("保存代理赔保单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除代理赔保单信息编号为" + claimNo + "的代理赔保单信息");
		super.deleteByPK(PrpLclaimAgent.class, claimNo);
	}

	@Override
	public PrpLclaimAgent findPrpLclaimAgent(String claimNo) throws Exception {
		logger.info("查询代理赔保单信息编号为" + claimNo + "的代理赔保单信息");
		return super.get(PrpLclaimAgent.class,claimNo);
	}
    
	@Override
	public Page findPrpLclaimAgent(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取代理赔保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpLclaimAgent> findPrpLclaimAgent(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
