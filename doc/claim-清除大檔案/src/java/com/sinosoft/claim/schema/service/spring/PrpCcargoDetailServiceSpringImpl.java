package com.sinosoft.claim.schema.service.spring;
/**
 * 货运险货运明细信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcargoDetail;
import com.sinosoft.claim.schema.model.PrpCcargoDetailId;
import com.sinosoft.claim.schema.service.facade.PrpCcargoDetailService;

public class PrpCcargoDetailServiceSpringImpl extends
GenericDaoHibernate<PrpCcargoDetail, PrpCcargoDetailId> implements PrpCcargoDetailService{

	@Override
	public void save(PrpCcargoDetail PrpCcargoDetail) throws Exception {
		logger.info("保存货运险货运明细信息信息");
		super.save(PrpCcargoDetail);
		
	}

	@Override
	public void save(List<PrpCcargoDetail> list) throws Exception {
		logger.info("保存货运险货运明细信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCcargoDetailId PrpCcargoDetailId) throws Exception {
		logger.info("删除货运险货运明细信息编号为" + PrpCcargoDetailId + "的货运险货运明细信息");
		super.deleteByPK(PrpCcargoDetail.class, PrpCcargoDetailId);
	}

	@Override
	public PrpCcargoDetail findPrpCcargoDetail(PrpCcargoDetailId PrpCcargoDetailId) throws Exception {
		logger.info("查询货运险货运明细信息编号为" + PrpCcargoDetailId + "的货运险货运明细信息");
		return super.get(PrpCcargoDetail.class, PrpCcargoDetailId);
	}

	@Override
	public Page findPrpCcargoDetail(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取货运险货运明细信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCcargoDetail> findPrpCcargoDetail(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出货运险货运明细信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCcargoDetail findPrpCcargoDetail(String certiNo) throws Exception{
		PrpCcargoDetail PrpCcargoDetail = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCcargoDetail> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCcargoDetail = resultList.get(0);
		}
		return PrpCcargoDetail;
	}

}
