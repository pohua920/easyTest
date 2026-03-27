package com.sinosoft.claim.common.service.spring;

import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.claim.common.service.facade.PrpDtypeService;
import com.sinosoft.claim.schema.model.PrpDtype;

public class PrpDtypeServiceSpringImpl extends GenericDaoHibernate<PrpDtype, String> implements PrpDtypeService {

	@Override
	public PrpDtype findByPrimaryKey(String codeType) throws Exception {
		return super.get(PrpDtype.class, codeType);
	}

}
