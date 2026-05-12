package com.sinosoft.claim.schema.service.spring;
/**
 * 保单全貌信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpallPolicy;
import com.sinosoft.claim.schema.service.facade.PrpallPolicyService;

public class PrpallPolicyServiceSpringImpl extends
GenericDaoHibernate<PrpallPolicy, String> implements PrpallPolicyService{

	@Override
	public void save(PrpallPolicy prpallPolicy) throws Exception {
		logger.info("保存保单全貌信息");
		super.save(prpallPolicy);
		
	}

	@Override
	public void save(List<PrpallPolicy> list) throws Exception {
		logger.info("保存保单全貌信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String policyNo) throws Exception {
		logger.info("删除保单全貌信息编号为" + policyNo + "的保单全貌信息");
		super.deleteByPK(PrpallPolicy.class, policyNo);
	}

	@Override
	public PrpallPolicy findPrpallPolicy(String policyNo) throws Exception {
		logger.info("查询保单全貌信息编号为" + policyNo + "的保单全貌信息");
		return super.get(PrpallPolicy.class,policyNo);
	}

	@Override
	public Page findPrpallPolicy(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取保单全貌信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpallPolicy> findPrpallPolicy(QueryRule queryRule)
			throws Exception {
		
		return super.find(queryRule);
	}

}
