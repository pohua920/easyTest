package com.sinosoft.claim.schema.service.spring;
/**
 * 意健险调查费用信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLacciCheckCharge;
import com.sinosoft.claim.schema.model.PrpLacciCheckChargeId;
import com.sinosoft.claim.schema.service.facade.PrpLacciCheckChargeService;

public class PrpLacciCheckChargeServiceSpringImpl extends
GenericDaoHibernate<PrpLacciCheckCharge, PrpLacciCheckChargeId> implements PrpLacciCheckChargeService{

	@Override
	public void save(PrpLacciCheckCharge prpLacciCheckCharge) throws Exception {
		logger.info("保存意健险调查费用信息");
		super.save(prpLacciCheckCharge);
		
	}

	@Override
	public void save(List<PrpLacciCheckCharge> list) throws Exception {
		logger.info("保存意健险调查费用信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLacciCheckChargeId prpLacciCheckChargeId) throws Exception {
		logger.info("删除意健险调查费用信息编号为" + prpLacciCheckChargeId + "的意健险调查费用信息");
		super.deleteByPK(PrpLacciCheckCharge.class, prpLacciCheckChargeId);
	}

	@Override
	public PrpLacciCheckCharge findPrpLacciCheckCharge(PrpLacciCheckChargeId prpLacciCheckChargeId) throws Exception {
		logger.info("查询意健险调查费用信息编号为" + prpLacciCheckChargeId + "的意健险调查费用信息");
		return super.get(PrpLacciCheckCharge.class, prpLacciCheckChargeId);
	}

	@Override
	public Page findPrpLacciCheckCharge(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取意健险调查费用信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLacciCheckCharge> findPrpLacciCheckCharge(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据意健险调查费用编号查询出意健险调查费用信息
	 * @param certiNo ：传入的意健险调查费用编号
	 * @return 返回意健险调查费用
	 */
	public PrpLacciCheckCharge findPrpLacciCheckCharge(String certiNo) throws Exception{
		PrpLacciCheckCharge prpLacciCheckCharge = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.certiNo", certiNo);
		List<PrpLacciCheckCharge> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			prpLacciCheckCharge = resultList.get(0);
		}
		return prpLacciCheckCharge;
	}

	@Override
	public List<PrpLacciCheckCharge> findByConditions(String condition) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(condition);
		return super.find(queryRule);
	}

}
