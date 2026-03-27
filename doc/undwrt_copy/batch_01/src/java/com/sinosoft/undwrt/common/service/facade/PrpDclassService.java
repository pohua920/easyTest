package com.sinosoft.undwrt.common.service.facade;

import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.undwrt.common.model.PrpDclass;

// TODO: Auto-generated Javadoc
/**
 * 險類接口類.
 */
public interface PrpDclassService {

	/**
	 * 根據條件查詢險類.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return 符合條件的險類集合
	 * @throws Exception
	 *             異常
	 */
	public Collection<PrpDclass> findByConditions(String conditions) throws Exception;
	
	/**
	 * 根據條件查詢險類.
	 * 
	 * @param queryRule
	 *            查询规则
	 * @return 符合條件的險類集合
	 */
	public List<PrpDclass> findPrpDclassList(QueryRule queryRule);
	
	/**
	 * 根據主鍵查詢險類.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 險類類
	 */
	public PrpDclass findByPrimaryKey(QueryRule queryRule);
}
