package com.sinosoft.claim.schema.service.spring;
/**
 * 地震基金
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLearthquakeFund;
import com.sinosoft.claim.schema.model.PrpLearthquakeFundId;
import com.sinosoft.claim.schema.service.facade.PrpLearthquakeFundService;

public class PrpLearthquakeFundServiceSpringImpl extends GenericDaoHibernate<PrpLearthquakeFund, String> implements PrpLearthquakeFundService{

	public void save(PrpLearthquakeFund prpLearthquakeFund) throws Exception {
		logger.info("保存地震基金信息");
		super.save(prpLearthquakeFund);
		
	}

	public void save(List<PrpLearthquakeFund> list) throws Exception {
		logger.info("保存地震基金信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	public void delete(PrpLearthquakeFundId prpLearthquakeFundId) throws Exception {
		logger.info("删除地震基金信息编号为" + prpLearthquakeFundId + "的地震基金信息");
		super.deleteByPK(PrpLearthquakeFund.class, prpLearthquakeFundId);
	}

	public PrpLearthquakeFund findPrpLearthquakeFund(PrpLearthquakeFundId prpLearthquakeFundId) throws Exception {
		logger.info("查询地震基金信息编号为" + prpLearthquakeFundId + "的地震基金信息");
		return super.get(PrpLearthquakeFund.class,prpLearthquakeFundId);
	}

	public Page findPrpLearthquakeFund(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取地震基金信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	public List<PrpLearthquakeFund> findPrpLearthquakeFund(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}

}
