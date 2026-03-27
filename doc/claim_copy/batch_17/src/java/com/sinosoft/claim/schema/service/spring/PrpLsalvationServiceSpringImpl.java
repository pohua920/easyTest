package com.sinosoft.claim.schema.service.spring;
/**
 * 特约救助信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLsalvation;
import com.sinosoft.claim.schema.model.PrpLsalvationId;
import com.sinosoft.claim.schema.service.facade.PrpLsalvationService;

public class PrpLsalvationServiceSpringImpl extends
GenericDaoHibernate<PrpLsalvation, PrpLsalvationId> implements PrpLsalvationService{

	@Override
	public void save(PrpLsalvation prpLsalvation) throws Exception {
		logger.info("保存特约救助信息");
		super.save(prpLsalvation);
		
	}

	@Override
	public void save(List<PrpLsalvation> list) throws Exception {
		logger.info("保存特约救助信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLsalvationId prpLsalvationId) throws Exception {
		logger.info("删除特约救助信息编号为" + prpLsalvationId + "的特约救助信息");
		super.deleteByPK(PrpLsalvation.class, prpLsalvationId);
	}

	@Override
	public PrpLsalvation findPrpLsalvation(PrpLsalvationId prpLsalvationId) throws Exception {
		logger.info("查询特约救助信息编号为" + prpLsalvationId + "的特约救助信息");
		return super.get(PrpLsalvation.class, prpLsalvationId);
	}

	@Override
	public Page findPrpLsalvation(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取特约救助信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLsalvation> findPrpLsalvation(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据特约救助编号查询出特约救助信息
	 * @param certiNo ：传入的特约救助编号
	 * @return 返回特约救助
	 */
	public PrpLsalvation findPrpLsalvation(String certiNo) throws Exception{
		PrpLsalvation prpLsalvation = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLsalvation> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLsalvation = resultList.get(0);
		}
		return prpLsalvation;
	}

}
