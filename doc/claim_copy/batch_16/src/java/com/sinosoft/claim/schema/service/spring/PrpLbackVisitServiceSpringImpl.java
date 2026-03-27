package com.sinosoft.claim.schema.service.spring;
/**
 * PRPLBACKVISIT信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLbackVisit;
import com.sinosoft.claim.schema.model.PrpLbackVisitId;
import com.sinosoft.claim.schema.service.facade.PrpLbackVisitService;

public class PrpLbackVisitServiceSpringImpl extends
GenericDaoHibernate<PrpLbackVisit, PrpLbackVisitId> implements PrpLbackVisitService{

	@Override
	public void save(PrpLbackVisit prpLbackVisit) throws Exception {
		logger.info("保存PRPLBACKVISIT信息");
		super.save(prpLbackVisit);
		
	}

	@Override
	public void save(List<PrpLbackVisit> list) throws Exception {
		logger.info("保存PRPLBACKVISIT信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLbackVisitId prpLbackVisitId) throws Exception {
		logger.info("删除PRPLBACKVISIT信息编号为" + prpLbackVisitId + "的PRPLBACKVISIT信息");
		super.deleteByPK(PrpLbackVisit.class, prpLbackVisitId);
	}

	@Override
	public PrpLbackVisit findPrpLbackVisit(PrpLbackVisitId prpLbackVisitId) throws Exception {
		logger.info("查询PRPLBACKVISIT信息编号为" + prpLbackVisitId + "的PRPLBACKVISIT信息");
		return super.get(PrpLbackVisit.class, prpLbackVisitId);
	}

	@Override
	public Page findPrpLbackVisit(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPLBACKVISIT信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLbackVisit> findPrpLbackVisit(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPLBACKVISIT编号查询出PRPLBACKVISIT信息
	 * @param certiNo ：传入的PRPLBACKVISIT编号
	 * @return 返回PRPLBACKVISIT
	 */
	public PrpLbackVisit findPrpLbackVisit(String certiNo) throws Exception{
		PrpLbackVisit prpLbackVisit = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLbackVisit> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLbackVisit = resultList.get(0);
		}
		return prpLbackVisit;
	}

}
