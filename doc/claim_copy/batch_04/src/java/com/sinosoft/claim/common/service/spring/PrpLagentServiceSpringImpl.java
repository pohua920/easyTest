package com.sinosoft.claim.common.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.common.service.facade.PrpLagentService;
import com.sinosoft.claim.common.util.HibernateUtils;
import com.sinosoft.claim.schema.model.PrpLagent;
import com.sinosoft.claim.schema.model.PrpLagentId;

public class PrpLagentServiceSpringImpl  extends GenericDaoHibernate<PrpLagent, PrpLagentId> implements PrpLagentService {

	@Override
	public List<PrpLagent> findByConditions(String conditions) {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addSql(conditions);
		return super.find(queryRule);
	}
	 /**
     * 用於更改核赔标志位
     * @param prpLagentDto prpLagentDto
     * @throws Exception
     */
	public void updateUndwrt(String conditions) throws Exception {
		StringBuffer buffer = new StringBuffer(200);
		buffer.append("UPDATE PrpLagent SET ");
		buffer.append("UnderwriteFlag = '1' ");
		buffer.append("WHERE ");
		buffer.append(conditions);
		HibernateUtils.executeSql(super.getSession(), buffer.toString());
	}
}
