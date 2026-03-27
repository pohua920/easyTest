package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.sysframework.common.datatype.PageRecord;
import com.sinosoft.sysframework.exceptionlog.UserException;
import com.sinosoft.undwrt.undwrtBase.model.SwfCondition;

/**
 * 工作流條件描述類.
 */
public interface SwfConditionService {

	/**
	 * 獲得模板號.
	 * 
	 * @param modelType
	 *            模版類型
	 * @param riskCode
	 *            險種代碼
	 * @param comCode
	 *            機構代碼
	 * @return 模板號
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public int getModelNo(String modelType, String riskCode, String comCode)
			throws SQLException, Exception;

	/**
	 * 插入一條記錄.
	 * 
	 * @param swfCondition
	 *            工作流條件描述類
	 * @throws Exception
	 *             異常
	 */
	public void insert(SwfCondition swfCondition) throws Exception;

	/**
	 * 按主鍵刪除一條數據.
	 * 
	 * @param modelNo
	 *            模板號
	 * @param pathNo
	 *            路徑號
	 * @param conditionNo
	 *            條件編號
	 * @param serialNo
	 *            序號
	 * @throws Exception
	 *             異常
	 */
	public void delete(int modelNo, int pathNo, int conditionNo, int serialNo)
			throws Exception;

	/**
	 * 按條件刪除數據.
	 * 
	 * @param conditions
	 *            刪除條件
	 * @throws Exception
	 *             異常
	 */
	public void deleteByConditions(String conditions) throws Exception;

	/**
	 * 按主鍵更新一條數據(主鍵本身無法變更).
	 * 
	 * @param swfCondition
	 *            工作流條件類
	 * @throws Exception
	 *             異常
	 */
	public void update(SwfCondition swfCondition) throws Exception;

	/**
	 * 按主鍵查找一條數據..
	 * 
	 * @param modelNo
	 *            模板號
	 * @param pathNo
	 *            路徑號
	 * @param conditionNo
	 *            條件編號
	 * @param serialNo
	 *            序號
	 * @return sWfCondition 工作流條件類
	 * @throws Exception
	 *             異常
	 */
	public SwfCondition findByPrimaryKey(int modelNo, int pathNo,
			int conditionNo, int serialNo) throws Exception;

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @param pageNo
	 *            頁碼
	 * @param rowsPerPage
	 *            每頁的行數
	 * @return PageRecord 查詢的一頁的結果
	 * @throws Exception
	 *             異常
	 */
	public PageRecord findByConditions(String conditions, int pageNo,
			int rowsPerPage) throws Exception;

	/**
	 * 按條件查詢多條數據.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return Collection 符合條件的集合
	 * @throws Exception
	 *             異常
	 */
	public List<SwfCondition> findByConditions(QueryRule queryRule)
			throws Exception;

	/**
	 * 查詢滿足模糊查詢條件的記錄數.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return 满足模糊查询条件的记录数
	 * @throws Exception
	 *             異常
	 */
	public int getCount(QueryRule queryRule) throws Exception;

	/**
	 * 執行工作流系統發出的sql語句(針對簡單描述和SQL描述).
	 * 
	 * @param businessNo
	 *            業務號碼
	 * @param comcode
	 *            機構代碼
	 * @param modelno
	 *            模板號
	 * @param nodeNo
	 *            節點號
	 * @param swfCondition
	 *            工作流條件類
	 * @return boolean 成功返回true，失敗返回false
	 * @throws UserException
	 *             用戶自定義異常
	 * @throws Exception
	 *             異常
	 */
	public boolean execute(String businessNo, String comcode, int modelno,
			int nodeNo, SwfCondition swfCondition) throws UserException,
			Exception;

}
