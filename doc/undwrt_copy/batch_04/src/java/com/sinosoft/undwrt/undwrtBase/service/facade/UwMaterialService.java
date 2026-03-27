package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.UwMaterial;

/**
 * 材料接口類.
 */
public interface UwMaterialService {

	/**
	 * 獲取屬性材料集合.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 屬性材料集合的值
	 */
	public List<UwMaterial> getUwMaterialList(QueryRule queryRule);
}
