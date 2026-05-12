package com.sinosoft.claim.schema.service.spring;
/**
 * 被保险人详细信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCinsuredNature;
import com.sinosoft.claim.schema.model.PrpCinsuredNatureId;
import com.sinosoft.claim.schema.service.facade.PrpCinsuredNatureService;

public class PrpCinsuredNatureServiceSpringImpl extends
GenericDaoHibernate<PrpCinsuredNature, PrpCinsuredNatureId> implements PrpCinsuredNatureService{

	@Override
	public void save(PrpCinsuredNature prpCinsuredNature) throws Exception {
		logger.info("保存被保险人详细信息");
		super.save(prpCinsuredNature);
		
	}

	@Override
	public void save(List<PrpCinsuredNature> list) throws Exception {
		logger.info("保存被保险人详细信息");
		for(int i=0;i<list.size();i++){
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpCinsuredNatureId prpCinsuredNatureId) throws Exception {
		logger.info("删除被保险人详细信息编号为" + prpCinsuredNatureId + "的被保险人详细信息");
		super.deleteByPK(PrpCinsuredNature.class, prpCinsuredNatureId);
	}

	@Override
	public PrpCinsuredNature findPrpCinsuredNature(PrpCinsuredNatureId prpCinsuredNatureId) throws Exception {
		logger.info("查询被保险人详细信息编号为" + prpCinsuredNatureId + "的被保险人详细信息");
		return super.get(PrpCinsuredNature.class, prpCinsuredNatureId);
	}
	/**
	 * 查询自然人信息
	 * @param policyNo 保单号码
	 * @param serialNo 序号
	 * @return
	 * @throws Exception
	 */
	public PrpCinsuredNature findPrpCinsuredNature(String policyNo,int serialNo) throws Exception{
		PrpCinsuredNatureId prpCinsuredNatureId = new PrpCinsuredNatureId();
		prpCinsuredNatureId.setPolicyNo(policyNo);
		prpCinsuredNatureId.setSerialNo(serialNo);
		return this.findPrpCinsuredNature(prpCinsuredNatureId);
	}

	@Override
	public Page findPrpCinsuredNature(QueryRule queryRule, int pageNo, int pageSize)
			throws Exception {
		logger.info("获取被保险人详细信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpCinsuredNature> findPrpCinsuredNature(QueryRule queryRule)
			throws Exception {
		return super.find(queryRule);
	}
	/**
	 * 根据保单号码，查询自然人信息
	 * @param policyNo 保单号码
	 * @return
	 * @throws Exception
	 */
	public List<PrpCinsuredNature> findPrpCinsuredNature(String policyNo) throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.policyNo", policyNo);
		return super.find(queryRule);
	}

}
