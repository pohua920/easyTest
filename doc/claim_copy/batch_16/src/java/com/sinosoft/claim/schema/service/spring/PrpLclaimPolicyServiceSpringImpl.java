package com.sinosoft.claim.schema.service.spring;
/**
 * 立案保单清单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLclaimPolicy;
import com.sinosoft.claim.schema.model.PrpLclaimPolicyId;
import com.sinosoft.claim.schema.service.facade.PrpLclaimPolicyService;

public class PrpLclaimPolicyServiceSpringImpl extends
GenericDaoHibernate<PrpLclaimPolicy, PrpLclaimPolicyId> implements PrpLclaimPolicyService{

	@Override
	public void save(PrpLclaimPolicy prpLclaimPolicy) throws Exception {
		logger.info("保存立案保单清单信息");
		super.save(prpLclaimPolicy);
		
	}

	@Override
	public void save(List<PrpLclaimPolicy> list) throws Exception {
		logger.info("保存立案保单清单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLclaimPolicyId prpLclaimPolicyId) throws Exception {
		logger.info("删除立案保单清单信息编号为" + prpLclaimPolicyId + "的立案保单清单信息");
		super.deleteByPK(PrpLclaimPolicy.class, prpLclaimPolicyId);
	}

	@Override
	public PrpLclaimPolicy findPrpLclaimPolicy(PrpLclaimPolicyId prpLclaimPolicyId) throws Exception {
		logger.info("查询立案保单清单信息编号为" + prpLclaimPolicyId + "的立案保单清单信息");
		return super.get(PrpLclaimPolicy.class, prpLclaimPolicyId);
	}

	@Override
	public Page findPrpLclaimPolicy(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取立案保单清单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLclaimPolicy> findPrpLclaimPolicy(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据立案保单清单编号查询出立案保单清单信息
	 * @param certiNo ：传入的立案保单清单编号
	 * @return 返回立案保单清单
	 */
	public PrpLclaimPolicy findPrpLclaimPolicy(String certiNo) throws Exception{
		PrpLclaimPolicy prpLclaimPolicy = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLclaimPolicy> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLclaimPolicy = resultList.get(0);
		}
		return prpLclaimPolicy;
	}

}
