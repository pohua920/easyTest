package com.sinosoft.claim.schema.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.claim.schema.model.UwMaterial;
import com.sinosoft.claim.schema.model.UwMaterialId;
import com.sinosoft.claim.schema.service.facade.UwMaterialService;


public class UwMaterialServiceSpringImpl extends
		GenericDaoHibernate<UwMaterial, UwMaterialId> implements
		UwMaterialService {

	public List<UwMaterial> getUwMaterialList(QueryRule queryRule) {
		return super.find(queryRule);
	}

}
