package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;
import com.sinosoft.claim.schema.model.PrpCmainCarGoSub;
import com.sinosoft.claim.schema.model.PrpCmainCarGoSubId;
import com.sinosoft.claim.schema.service.facade.PrpCmainCarGoSubService;

public class PrpCmainCarGoSubServiceSpringImpl extends GenericDaoHibernate<PrpCmainCarGoSub, PrpCmainCarGoSubId> implements PrpCmainCarGoSubService {

	@Override
	public PrpCmainCarGoSub findPrpCmainCarGoSub(String policyNo, Integer serialNo) throws Exception {
		return super.get(new PrpCmainCarGoSubId(policyNo, serialNo));
	}

	@Override
	public List<PrpCmainCarGoSub> findPrpCmainCarGoSub(String conditions) throws Exception { 
		return super.find(QueryRule.getInstance().addSql(conditions));
	}

}
