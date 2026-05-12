package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLPERSONLIMIT信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLPersonLimit;
import com.sinosoft.claim.schema.model.PrpLPersonLimitId;
import com.sinosoft.claim.schema.service.facade.PrpLPersonLimitService;

public class PrpLPersonLimitServiceSpringImpl extends
GenericDaoHibernate<PrpLPersonLimit, PrpLPersonLimitId> implements PrpLPersonLimitService{

	@Override
	public void save(PrpLPersonLimit prpLPersonLimit) throws Exception {
		logger.info("保存PRPLPERSONLIMIT信息");
		super.save(prpLPersonLimit);
		
	}

	@Override
	public void save(List<PrpLPersonLimit> list) throws Exception {
		logger.info("保存PRPLPERSONLIMIT信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLPersonLimitId prpLPersonLimitId) throws Exception {
		logger.info("删除PRPLPERSONLIMIT信息编号为" + prpLPersonLimitId + "的PRPLPERSONLIMIT信息");
		super.deleteByPK(PrpLPersonLimit.class, prpLPersonLimitId);
	}

	@Override
	public PrpLPersonLimit findPrpLPersonLimit(PrpLPersonLimitId prpLPersonLimitId) throws Exception {
		logger.info("查询PRPLPERSONLIMIT信息编号为" + prpLPersonLimitId + "的PRPLPERSONLIMIT信息");
		return super.get(PrpLPersonLimit.class, prpLPersonLimitId);
	}

	@Override
	public Page findPrpLPersonLimit(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLPERSONLIMIT信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLPersonLimit> findPrpLPersonLimit(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPLPERSONLIMIT编号查询出PRPLPERSONLIMIT信息
	 * @param certiNo ：传入的PRPLPERSONLIMIT编号
	 * @return 返回PRPLPERSONLIMIT
	 */
	public PrpLPersonLimit findPrpLPersonLimit(String certiNo) throws Exception{
		PrpLPersonLimit prpLPersonLimit = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLPersonLimit> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLPersonLimit = resultList.get(0);
		}
		return prpLPersonLimit;
	}

}
