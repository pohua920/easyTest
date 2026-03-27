package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.UwMaterial;
import com.sinosoft.undwrt.undwrtBase.model.UwMaterialId;
import com.sinosoft.undwrt.undwrtBase.service.facade.UwMaterialService;

/**
 * The Class UwMaterialServiceSpringImpl.
 */
public class UwMaterialServiceSpringImpl extends
		GenericDaoHibernate<UwMaterial, UwMaterialId> implements
		UwMaterialService {

	/**
	 * 獲取屬性材料集合.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @return 屬性材料集合的值
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UwMaterialService#getUwMaterialList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<UwMaterial> getUwMaterialList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

}
