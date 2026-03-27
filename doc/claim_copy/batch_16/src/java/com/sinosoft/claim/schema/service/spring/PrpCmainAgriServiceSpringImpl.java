package com.sinosoft.claim.schema.service.spring;
/**
 * 农业险保单信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCmainAgri;
import com.sinosoft.claim.schema.service.facade.PrpCmainAgriService;

public class PrpCmainAgriServiceSpringImpl extends
GenericDaoHibernate<PrpCmainAgri, String> implements PrpCmainAgriService{

	@Override
	public void save(PrpCmainAgri PrpCmainAgri) throws Exception {
		logger.info("保存农业险保单信息信息");
		super.save(PrpCmainAgri);
		
	}

	@Override
	public void save(List<PrpCmainAgri> list) throws Exception {
		logger.info("保存农业险保单信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(String claimNo) throws Exception {
		logger.info("删除农业险保单信息编号为" + claimNo + "的农业险保单信息");
		super.deleteByPK(PrpCmainAgri.class, claimNo);
	}

	@Override
	public PrpCmainAgri findPrpCmainAgri(String claimNo) throws Exception {
		logger.info("查询农业险保单信息编号为" + claimNo + "的农业险保单信息");
		return super.get(PrpCmainAgri.class,claimNo);
	}
    
	@Override
	public Page findPrpCmainAgri(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取农业险保单信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
		
	}

	@Override
	public List<PrpCmainAgri> findPrpCmainAgri(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

}
