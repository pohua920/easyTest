package com.sinosoft.claim.schema.service.spring;
/**
 * 损余回收信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLreclaim;
import com.sinosoft.claim.schema.model.PrpLreclaimId;
import com.sinosoft.claim.schema.service.facade.PrpLreclaimService;

public class PrpLreclaimServiceSpringImpl extends
GenericDaoHibernate<PrpLreclaim, PrpLreclaimId> implements PrpLreclaimService{

	@Override
	public void save(PrpLreclaim prpLreclaim) throws Exception {
		logger.info("保存损余回收信息信息");
		super.save(prpLreclaim);
		
	}

	@Override
	public void save(List<PrpLreclaim> list) throws Exception {
		logger.info("保存损余回收信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLreclaimId prpLreclaimId) throws Exception {
		logger.info("删除损余回收信息编号为" + prpLreclaimId + "的损余回收信息");
		super.deleteByPK(PrpLreclaim.class, prpLreclaimId);
	}

	@Override
	public PrpLreclaim findPrpLreclaim(PrpLreclaimId prpLreclaimId) throws Exception {
		logger.info("查询损余回收信息编号为" + prpLreclaimId + "的损余回收信息");
		return super.get(PrpLreclaim.class, prpLreclaimId);
	}

	@Override
	public Page findPrpLreclaim(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取损余回收信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLreclaim> findPrpLreclaim(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据损余回收信息编号查询出损余回收信息
	 * @param certiNo ：传入的损余回收信息编号
	 * @return 返回损余回收信息
	 */
	public PrpLreclaim findPrpLreclaim(String certiNo) throws Exception{
		PrpLreclaim prpLreclaim = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLreclaim> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLreclaim = resultList.get(0);
		}
		return prpLreclaim;
	}

}
