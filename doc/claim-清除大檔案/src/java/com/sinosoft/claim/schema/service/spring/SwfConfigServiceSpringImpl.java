package com.sinosoft.claim.schema.service.spring;

import java.util.List;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.schema.model.SwfConfig;
import com.sinosoft.claim.schema.model.SwfConfigId;
import com.sinosoft.claim.schema.service.facade.SwfConfigService;

public class SwfConfigServiceSpringImpl extends GenericDaoHibernate<SwfConfig, SwfConfigId>  implements SwfConfigService {

	@Override
	public SwfConfig getSwfConfig(String processId, String actorId) {
		return super.get(new SwfConfigId(processId,actorId));
	}

	@Override
	public List<SwfConfig> findByCondition(String conditions) {
		return super.find(QueryRule.getInstance().addSql(conditions));
	}

}
