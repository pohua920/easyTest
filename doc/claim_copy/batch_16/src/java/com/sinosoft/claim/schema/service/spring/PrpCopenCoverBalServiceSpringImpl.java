package com.sinosoft.claim.schema.service.spring;
/**
 * 人伤跟踪信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopenCoverBal;
import com.sinosoft.claim.schema.model.PrpCopenCoverBalId;
import com.sinosoft.claim.schema.service.facade.PrpCopenCoverBalService;

public class PrpCopenCoverBalServiceSpringImpl extends
GenericDaoHibernate<PrpCopenCoverBal, PrpCopenCoverBalId> implements PrpCopenCoverBalService{

	@Override
	public void save(PrpCopenCoverBal PrpCopenCoverBal) throws Exception {
		logger.info("保存人伤跟踪信息信息");
		super.save(PrpCopenCoverBal);
		
	}

	@Override
	public void save(List<PrpCopenCoverBal> list) throws Exception {
		logger.info("保存人伤跟踪信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCopenCoverBalId PrpCopenCoverBalId) throws Exception {
		logger.info("删除人伤跟踪信息编号为" + PrpCopenCoverBalId + "的人伤跟踪信息");
		super.deleteByPK(PrpCopenCoverBal.class, PrpCopenCoverBalId);
	}

	@Override
	public PrpCopenCoverBal findPrpCopenCoverBal(PrpCopenCoverBalId PrpCopenCoverBalId) throws Exception {
		logger.info("查询人伤跟踪信息编号为" + PrpCopenCoverBalId + "的人伤跟踪信息");
		return super.get(PrpCopenCoverBal.class, PrpCopenCoverBalId);
	}

	@Override
	public Page findPrpCopenCoverBal(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取人伤跟踪信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCopenCoverBal> findPrpCopenCoverBal(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出人伤跟踪信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCopenCoverBal findPrpCopenCoverBal(String certiNo) throws Exception{
		PrpCopenCoverBal PrpCopenCoverBal = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpCopenCoverBal> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCopenCoverBal = resultList.get(0);
		}
		return PrpCopenCoverBal;
	}

}
