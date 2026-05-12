package com.sinosoft.claim.schema.service.spring;
/**
 * 投保车辆扩展信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCitemCarExt;
import com.sinosoft.claim.schema.model.PrpCitemCarExtId;
import com.sinosoft.claim.schema.service.facade.PrpCitemCarExtService;

public class PrpCitemCarExtServiceSpringImpl extends
GenericDaoHibernate<PrpCitemCarExt, PrpCitemCarExtId> implements PrpCitemCarExtService{

	@Override
	public void save(PrpCitemCarExt PrpCitemCarExt) throws Exception {
		logger.info("保存投保车辆扩展信息信息");
		super.save(PrpCitemCarExt);
		
	}

	@Override
	public void save(List<PrpCitemCarExt> list) throws Exception {
		logger.info("保存投保车辆扩展信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCitemCarExtId PrpCitemCarExtId) throws Exception {
		logger.info("删除投保车辆扩展信息编号为" + PrpCitemCarExtId + "的投保车辆扩展信息");
		super.deleteByPK(PrpCitemCarExt.class, PrpCitemCarExtId);
	}

	@Override
	public PrpCitemCarExt findPrpCitemCarExt(PrpCitemCarExtId PrpCitemCarExtId) throws Exception {
		logger.info("查询投保车辆扩展信息编号为" + PrpCitemCarExtId + "的投保车辆扩展信息");
		return super.get(PrpCitemCarExt.class, PrpCitemCarExtId);
	}

	@Override
	public Page findPrpCitemCarExt(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取投保车辆扩展信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCitemCarExt> findPrpCitemCarExt(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	
	/**
	 * 根据人伤跟踪编号查询出投保车辆扩展信息
	 * @param certiNo ：传入的人伤跟踪编号
	 * @return 返回人伤跟踪
	 */
	public PrpCitemCarExt findPrpCitemCarExt(String policyNo) throws Exception{
		PrpCitemCarExt PrpCitemCarExt = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		List<PrpCitemCarExt> resultList = super.find(queryRule);
		if(resultList!=null&&resultList.size()>0) {
			PrpCitemCarExt = resultList.get(0);
		}
		return PrpCitemCarExt;
	}
	public List<PrpCitemCarExt>findByPolicyNo(String policyNo)throws Exception{
		QueryRule queryRule = QueryRule.getInstance().addEqual("id.policyNo", policyNo);
		return super.find(queryRule);
	}

}
