package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.PrpCopymainCarGoSub;
import com.sinosoft.claim.schema.model.PrpCopymainCarGoSubId;
import com.sinosoft.claim.schema.service.facade.PrpCopymainCarGoSubService;

public class PrpCopymainCarGoSubServiceSpringImpl extends GenericDaoHibernate<PrpCopymainCarGoSub, PrpCopymainCarGoSubId> implements PrpCopymainCarGoSubService {

	@Override
	public PrpCopymainCarGoSub findPrpCopymainCarGoSub(String endorseNo, Integer serialNo) throws Exception {
		return super.get(new PrpCopymainCarGoSubId(endorseNo, serialNo));
	}

	@Override
	public List<PrpCopymainCarGoSub> findPrpCopymainCarGoSub(String conditions) throws Exception { 
		return super.find(QueryRule.getInstance().addSql(conditions));
	}

}
