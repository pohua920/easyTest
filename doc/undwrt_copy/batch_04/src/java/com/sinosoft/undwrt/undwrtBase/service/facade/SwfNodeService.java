package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.sql.SQLException;
import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.SwfNode;

// TODO: Auto-generated Javadoc
/**
 * 工作流節點定義接口類.
 */
public interface SwfNodeService {

	/**
	 * 根據hql查詢結果集.
	 * 
	 * @param hql
	 *            查詢條件
	 * @param str1
	 *            查詢條件
	 * @return 滿足條件的結果集
	 */
	public List<SwfNode> findByHqlList(String hql, String str1);

	/**
	 * 根據條件查詢結果集.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的結果集
	 */
	public List<SwfNode> findByQureyRuleList(QueryRule queryRule);

	/**
	 * 根據主鍵查詢節點.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄
	 */
	public SwfNode findByPrimaryKey(QueryRule queryRule);

	/**
	 * 檢查當前模版節點是否可以被置爲結束節點.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return true 是,false 否
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public boolean checkEndflag(QueryRule queryRule) throws SQLException,
			Exception;

	/**
	 * 得到滿足條件的記錄數.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄數
	 * @throws SQLException
	 *             sql異常
	 * @throws Exception
	 *             異常
	 */
	public int getCount(QueryRule queryRule) throws SQLException, Exception;
}
