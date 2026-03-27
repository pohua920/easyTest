package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import com.sinosoft.undwrt.undwrtBase.model.SwfPathNew;

// TODO: Auto-generated Javadoc
/**
 * 路徑接口類.
 */
public interface SwfPathNewService {
	
	/**
	 * 根據主鍵查詢路徑.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return 路徑類
	 */
	public SwfPathNew findByPrimaryKey(QueryRule queryRule);

}
