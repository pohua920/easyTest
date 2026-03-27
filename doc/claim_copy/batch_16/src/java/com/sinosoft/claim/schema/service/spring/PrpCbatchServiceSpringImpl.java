package com.sinosoft.claim.schema.service.spring;
/**
 * 批量保单接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCbatch;
import com.sinosoft.claim.schema.model.PrpCbatchId;
import com.sinosoft.claim.schema.service.facade.PrpCbatchService;

public class PrpCbatchServiceSpringImpl extends
GenericDaoHibernate<PrpCbatch, PrpCbatchId> implements PrpCbatchService{

	@Override
	public void save(PrpCbatch PrpCbatch) throws Exception {
		logger.info("保存批量保单信息");
		super.save(PrpCbatch);
		
	}

	@Override
	public void save(List<PrpCbatch> list) throws Exception {
		logger.info("保存批量保单");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCbatchId PrpCbatchId) throws Exception {
		logger.info("删除批量保单编号为" + PrpCbatchId + "的批量保单");
		super.deleteByPK(PrpCbatch.class, PrpCbatchId);
	}

	@Override
	public PrpCbatch findPrpCbatch(PrpCbatchId PrpCbatchId) throws Exception {
		logger.info("查询批量保单编号为" + PrpCbatchId + "的批量保单");
		return super.get(PrpCbatch.class, PrpCbatchId);
	}

	@Override
	public Page findPrpCbatch(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取批量保单列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCbatch> findPrpCbatch(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出批量保单
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCbatch findPrpCbatch(String certiNo) throws Exception{
		PrpCbatch PrpCbatch = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCbatch> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCbatch = resultList.get(0);
		}
		return PrpCbatch;
	}

}
