package com.sinosoft.undwrt.common.service.facade;

import java.util.List;

import ins.framework.common.QueryRule;

import com.sinosoft.undwrt.common.model.PrpDcompany;

// TODO: Auto-generated Javadoc
/**
 * 機構接口類.
 */
public interface PrpDcompanyService {
	
	/**
	 * 根據機構代碼查詢機構.
	 *
	 * @param comCode 機構代碼
	 * @return 符合條件的機構
	 */
	public PrpDcompany findByPrimaryKey(String comCode);
	
	/**
	 * 根據條件查詢機構.
	 *
	 * @param queryRule 查詢規則
	 * @return 符合條件的機構
	 */
	public PrpDcompany findByPrimaryKey(QueryRule queryRule);
	
	public List<PrpDcompany> findByConditions(QueryRule queryRule);
}
