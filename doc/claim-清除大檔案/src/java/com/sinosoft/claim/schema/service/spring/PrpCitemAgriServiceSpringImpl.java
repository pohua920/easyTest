package com.sinosoft.claim.schema.service.spring;
/**
 * 农业险承保标的信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemAgri;
import com.sinosoft.claim.schema.model.PrpCitemAgriId;
import com.sinosoft.claim.schema.service.facade.PrpCitemAgriService;

public class PrpCitemAgriServiceSpringImpl extends
GenericDaoHibernate<PrpCitemAgri, PrpCitemAgriId> implements PrpCitemAgriService{

	@Override
	public void save(PrpCitemAgri PrpCitemAgri) throws Exception {
		logger.info("保存农业险承保标的信息信息");
		super.save(PrpCitemAgri);
		
	}

	@Override
	public void save(List<PrpCitemAgri> list) throws Exception {
		logger.info("保存农业险承保标的信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCitemAgriId PrpCitemAgriId) throws Exception {
		logger.info("删除农业险承保标的信息编号为" + PrpCitemAgriId + "的农业险承保标的信息");
		super.deleteByPK(PrpCitemAgri.class, PrpCitemAgriId);
	}

	@Override
	public PrpCitemAgri findPrpCitemAgri(PrpCitemAgriId PrpCitemAgriId) throws Exception {
		logger.info("查询农业险承保标的信息编号为" + PrpCitemAgriId + "的农业险承保标的信息");
		return super.get(PrpCitemAgri.class, PrpCitemAgriId);
	}

	@Override
	public Page findPrpCitemAgri(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取农业险承保标的信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCitemAgri> findPrpCitemAgri(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出农业险承保标的信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCitemAgri findPrpCitemAgri(String certiNo) throws Exception{
		PrpCitemAgri PrpCitemAgri = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCitemAgri> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCitemAgri = resultList.get(0);
		}
		return PrpCitemAgri;
	}

}
