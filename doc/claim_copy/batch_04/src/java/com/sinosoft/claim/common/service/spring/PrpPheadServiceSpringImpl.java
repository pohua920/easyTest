package com.sinosoft.claim.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpPheadService;
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpPhead;

public class PrpPheadServiceSpringImpl extends GenericDaoHibernate<PrpPhead, String> implements PrpPheadService {

	@Override
	public List<PrpPhead> findByConditions(String conditions, int pageNo, int rowsPerPage) throws Exception {
		logger.info("获取批改信息表列表信息");
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}

	@Override
	public PrpPhead findByPrimaryKey(String endorseNo) throws Exception {
		logger.info("获取批改信息表信息");
		return super.get(PrpPhead.class, endorseNo);
	}

	@Override
	public List<PrpPhead> findByPolicyNo(String policyNo) throws Exception {
		return super.find(QueryRule.getInstance().addEqual("policyNo", policyNo));
	}

	public List<PrpPhead> findByQueryConditions(String conditions) throws Exception {
		String sql = "select * from PrpPhead where " + conditions;
		return (List<PrpPhead>) HibernateUtils.findbySql(super.getSession(), sql, PrpPhead.class);
	}

	/**
	 * 根据sql语句查询有多少条立案信息
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public long getCount(String conditions) throws Exception {
		long count = 0;
		if (!CommonUtils.isEmpty(conditions)) {
			String countSql = "select count(*) from PrpPhead where " + conditions;
			count = HibernateUtils.getCountbyCountSql(getSession(), countSql);
		}
		return count;
	}
}
