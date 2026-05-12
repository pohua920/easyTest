package com.sinosoft.claim.schema.service.spring;

/**
 * 赔款费用信息接口实现类
 * @author 中科软
 */
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpLcharge;
import com.sinosoft.claim.schema.model.PrpLchargeId;
import com.sinosoft.claim.schema.service.facade.PrpLchargeService;
import com.sinosoft.sysframework.common.util.StringUtils;

public class PrpLchargeServiceSpringImpl extends GenericDaoHibernate<PrpLcharge, PrpLchargeId> implements PrpLchargeService {

	@Override
	public void save(PrpLcharge prpLcharge) throws Exception {
		logger.info("保存赔款费用信息");
		super.save(prpLcharge);

	}

	@Override
	public void save(List<PrpLcharge> list) throws Exception {
		logger.info("保存赔款费用信息");
		for (int i = 0; i < list.size(); i++) {
			super.save(list.get(i));
		}
	}

	@Override
	public void delete(PrpLchargeId prpLchargeId) throws Exception {
		logger.info("删除赔款费用信息编号为" + prpLchargeId + "的赔款费用信息");
		super.deleteByPK(PrpLcharge.class, prpLchargeId);
	}

	@Override
	public PrpLcharge findPrpLcharge(PrpLchargeId prpLchargeId) throws Exception {
		logger.info("查询赔款费用信息编号为" + prpLchargeId + "的赔款费用信息");
		return super.get(PrpLcharge.class, prpLchargeId);
	}

	@Override
	public Page findPrpLcharge(QueryRule queryRule, int pageNo, int pageSize) throws Exception {
		logger.info("获取赔款费用信息列表信息");
		return super.find(queryRule, pageNo, pageSize);
	}

	@Override
	public List<PrpLcharge> findPrpLcharge(QueryRule queryRule) throws Exception {
		return super.find(queryRule);
	}

	/**
	 * 根据赔款费用编号查询出赔款费用信息
	 * @param certiNo ：传入的赔款费用编号
	 * @return 返回赔款费用
	 */
	public PrpLcharge findPrpLcharge(String compensateNo) throws Exception {
		PrpLcharge prpLcharge = null;
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", compensateNo);
		List<PrpLcharge> resultList = super.find(queryRule);
		if (resultList != null && resultList.size() > 0) {
			prpLcharge = resultList.get(0);
		}
		return prpLcharge;
	}

	@Override
	public void deleteByCompensateNo(String compensateNo) throws Exception {
		String sql = " DELETE FROM prpLcharge Where compensateNo = '" + StringUtils.rightTrim(compensateNo) + "'";
		super.getSession().createSQLQuery(sql).executeUpdate();
	}
	public List<PrpLcharge>findByConditions(String conditions)throws Exception{
		QueryRule queryRule = QueryRule.getInstance().addSql(conditions);
		return super.find(queryRule);
	}
	
	/**
	 * 根据赔款费用编号查询出赔款费用信息
	 * @param certiNo ：传入的赔款费用编号
	 * @return 返回赔款费用
	 */
	public List<PrpLcharge> findPrpLchargeList(String compensateNo) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addEqual("id.compensateNo", compensateNo);
		List<PrpLcharge> resultList = super.find(queryRule);
			return resultList;	
	}

}
