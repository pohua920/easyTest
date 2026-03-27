package com.sinosoft.claim.schema.service.spring;

import java.util.List;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.schema.model.PrpLltextModel;
import com.sinosoft.claim.schema.model.PrpLltextModelId;
import com.sinosoft.claim.schema.service.facade.PrpLltextModelService;

public class PrpLltextModelServiceSpringImpl extends GenericDaoHibernate<PrpLltextModel, PrpLltextModelId> implements PrpLltextModelService {

	@Override
	public List<PrpLltextModel> findByConditions(String conditions) {
		return super.find(QueryRule.getInstance().addSql(conditions));
	}

}
