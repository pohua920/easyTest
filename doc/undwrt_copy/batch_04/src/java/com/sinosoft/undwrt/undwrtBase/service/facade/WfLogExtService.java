package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.WfLogExt;

// TODO: Auto-generated Javadoc
/**
 * 工作流日誌附屬接口類.
 */
public interface WfLogExtService {

	/**
	 * 按條件刪除數據.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @throws Exception
	 *             異常
	 */
	public void deleteByQueryRule(QueryRule queryRule) throws Exception;

	/**
	 * 批量插入多條數據.
	 * 
	 * @param collection
	 *            工作流日誌附屬接口類集合
	 */
	public void insertAll(Collection<WfLogExt> collection);

	/**
	 * 根據條件查詢工作流日誌附屬接口類集合.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的工作流日誌附屬接口類集合
	 */
	public List<WfLogExt> getWfLogExtList(QueryRule queryRule);
}
