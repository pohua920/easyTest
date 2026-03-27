package com.sinosoft.claim.schema.service.spring;
/**
 * 代理的详细信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCagentDetail;
import com.sinosoft.claim.schema.model.PrpCagentDetailId;
import com.sinosoft.claim.schema.service.facade.PrpCagentDetailService;

public class PrpCagentDetailServiceSpringImpl extends
GenericDaoHibernate<PrpCagentDetail, PrpCagentDetailId> implements PrpCagentDetailService{

	@Override
	public void save(PrpCagentDetail PrpCagentDetail) throws Exception {
		logger.info("保存代理的详细信息信息");
		super.save(PrpCagentDetail);
		
	}

	@Override
	public void save(List<PrpCagentDetail> list) throws Exception {
		logger.info("保存代理的详细信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCagentDetailId PrpCagentDetailId) throws Exception {
		logger.info("删除代理的详细信息编号为" + PrpCagentDetailId + "的代理的详细信息");
		super.deleteByPK(PrpCagentDetail.class, PrpCagentDetailId);
	}

	@Override
	public PrpCagentDetail findPrpCagentDetail(PrpCagentDetailId PrpCagentDetailId) throws Exception {
		logger.info("查询代理的详细信息编号为" + PrpCagentDetailId + "的代理的详细信息");
		return super.get(PrpCagentDetail.class, PrpCagentDetailId);
	}

	@Override
	public Page findPrpCagentDetail(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取代理的详细信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCagentDetail> findPrpCagentDetail(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出代理的详细信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCagentDetail findPrpCagentDetail(String certiNo) throws Exception{
		PrpCagentDetail PrpCagentDetail = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCagentDetail> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCagentDetail = resultList.get(0);
		}
		return PrpCagentDetail;
	}

}
