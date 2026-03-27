package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.WfFlowMain;

/**
 * 工作流主表接口類.
 */
public interface WfFlowMainService {

	/**
	 * 插入一條記錄.
	 * 
	 * @param wfFlowMain
	 *            工作流主表類
	 * @throws Exception
	 *             the exception
	 */
	public void insert(WfFlowMain wfFlowMain) throws Exception;

	/**
	 * 按主鍵刪除一條記錄.
	 * 
	 * @param flowID
	 *            工作流號
	 * @throws Exception
	 *             異常
	 */
	public void delete(String flowID) throws Exception;

	/**
	 * 按條件刪除記錄.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @throws Exception
	 *             異常
	 */
	public void deleteByQueryRule(QueryRule queryRule) throws Exception;

	/**
	 * 按主鍵更新一條數據(主鍵本身無法變更).
	 * 
	 * @param wfFlowMain
	 *            工作流主表類
	 * @throws Exception
	 *             異常
	 */
	public void update(WfFlowMain wfFlowMain) throws Exception;

	/**
	 * 按主鍵查找一條數據.
	 * 
	 * @param flowID
	 *            工作流號
	 * @return wfFlowMain 工作流主表類
	 * @throws Exception
	 *             異常
	 */
	public WfFlowMain findByPrimaryKey(String flowID) throws Exception;

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的行數
	 * @return PageRecord 查詢的一頁的結果
	 * @throws Exception
	 *             異常
	 */
	public Page findByQueryRule(QueryRule queryRule, int pageNo, int rowsPerPage)
			throws Exception;

	/**
	 * 按条件查询多条数据.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @return 滿足條件的工作流主表類集合
	 * @throws Exception
	 *             異常
	 */
	public List<WfFlowMain> findByQueryRule(QueryRule queryRule)
			throws Exception;

	/**
	 * 查詢滿足模糊查詢條件的記錄數.
	 * 
	 * @param queryRule
	 *            the query rule
	 * @return 滿足模糊查詢條件的記錄數
	 * @throws Exception
	 *             異常
	 */
	public int getCount(QueryRule queryRule) throws Exception;

}
