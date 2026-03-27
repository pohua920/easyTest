package com.sinosoft.claim.schema.service.spring;
/**
 * 担保信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCguaranty;
import com.sinosoft.claim.schema.model.PrpCguarantyId;
import com.sinosoft.claim.schema.service.facade.PrpCguarantyService;

public class PrpCguarantyServiceSpringImpl extends
GenericDaoHibernate<PrpCguaranty, PrpCguarantyId> implements PrpCguarantyService{

	@Override
	public void save(PrpCguaranty PrpCguaranty) throws Exception {
		logger.info("保存担保信息信息");
		super.save(PrpCguaranty);
		
	}

	@Override
	public void save(List<PrpCguaranty> list) throws Exception {
		logger.info("保存担保信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCguarantyId PrpCguarantyId) throws Exception {
		logger.info("删除担保信息编号为" + PrpCguarantyId + "的担保信息");
		super.deleteByPK(PrpCguaranty.class, PrpCguarantyId);
	}

	@Override
	public PrpCguaranty findPrpCguaranty(PrpCguarantyId PrpCguarantyId) throws Exception {
		logger.info("查询担保信息编号为" + PrpCguarantyId + "的担保信息");
		return super.get(PrpCguaranty.class, PrpCguarantyId);
	}

	@Override
	public Page findPrpCguaranty(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取担保信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCguaranty> findPrpCguaranty(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出担保信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCguaranty findPrpCguaranty(String certiNo) throws Exception{
		PrpCguaranty PrpCguaranty = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCguaranty> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCguaranty = resultList.get(0);
		}
		return PrpCguaranty;
	}

}
