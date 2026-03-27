package com.sinosoft.claim.schema.service.spring;
/**
 * 车船税接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCcarShipTax;
import com.sinosoft.claim.schema.model.PrpCcarShipTaxId;
import com.sinosoft.claim.schema.service.facade.PrpCcarShipTaxService;

public class PrpCcarShipTaxServiceSpringImpl extends
GenericDaoHibernate<PrpCcarShipTax, PrpCcarShipTaxId> implements PrpCcarShipTaxService{

	@Override
	public void save(PrpCcarShipTax PrpCcarShipTax) throws Exception {
		logger.info("保存车船税信息");
		super.save(PrpCcarShipTax);
		
	}

	@Override
	public void save(List<PrpCcarShipTax> list) throws Exception {
		logger.info("保存车船税");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCcarShipTaxId PrpCcarShipTaxId) throws Exception {
		logger.info("删除车船税编号为" + PrpCcarShipTaxId + "的车船税");
		super.deleteByPK(PrpCcarShipTax.class, PrpCcarShipTaxId);
	}

	@Override
	public PrpCcarShipTax findPrpCcarShipTax(PrpCcarShipTaxId PrpCcarShipTaxId) throws Exception {
		logger.info("查询车船税编号为" + PrpCcarShipTaxId + "的车船税");
		return super.get(PrpCcarShipTax.class, PrpCcarShipTaxId);
	}

	@Override
	public Page findPrpCcarShipTax(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取车船税列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCcarShipTax> findPrpCcarShipTax(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出车船税
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCcarShipTax findPrpCcarShipTax(String certiNo) throws Exception{
		PrpCcarShipTax PrpCcarShipTax = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCcarShipTax> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCcarShipTax = resultList.get(0);
		}
		return PrpCcarShipTax;
	}

}
