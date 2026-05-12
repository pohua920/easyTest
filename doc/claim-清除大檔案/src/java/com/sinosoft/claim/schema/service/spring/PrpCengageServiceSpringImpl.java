package com.sinosoft.claim.schema.service.spring;
/**
 * PRPCENGAGE信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCengage;
import com.sinosoft.claim.schema.model.PrpCengageId;
import com.sinosoft.claim.schema.service.facade.PrpCengageService;

public class PrpCengageServiceSpringImpl extends
GenericDaoHibernate<PrpCengage, PrpCengageId> implements PrpCengageService{

	@Override
	public void save(PrpCengage prpCengage) throws Exception {
		logger.info("保存PRPCENGAGE信息");
		super.save(prpCengage);
		
	}

	@Override
	public void save(List<PrpCengage> list) throws Exception {
		logger.info("保存PRPCENGAGE信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCengageId prpCengageId) throws Exception {
		logger.info("删除PRPCENGAGE信息编号为" + prpCengageId + "的PRPCENGAGE信息");
		super.deleteByPK(PrpCengage.class, prpCengageId);
	}

	@Override
	public PrpCengage findPrpCengage(PrpCengageId prpCengageId) throws Exception {
		logger.info("查询PRPCENGAGE信息编号为" + prpCengageId + "的PRPCENGAGE信息");
		return super.get(PrpCengage.class, prpCengageId);
	}

	@Override
	public Page findPrpCengage(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取PRPCENGAGE信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCengage> findPrpCengage(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据PRPCENGAGE编号查询出PRPCENGAGE信息
	 * @param certiNo ：传入的PRPCENGAGE编号
	 * @return 返回PRPCENGAGE
	 */
	public PrpCengage findPrpCengage(String certiNo) throws Exception{
		PrpCengage prpCengage = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCengage> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpCengage = resultList.get(0);
		}
		return prpCengage;
	}

}
