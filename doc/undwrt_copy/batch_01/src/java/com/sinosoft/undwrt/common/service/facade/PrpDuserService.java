package com.sinosoft.undwrt.common.service.facade;

import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.undwrt.common.model.PrpDuser;

// TODO: Auto-generated Javadoc
/**
 * 用戶接口類.
 */
public interface PrpDuserService {

	/**
	 * 根據員工工號得到員工信息.
	 * 
	 * @param userCode
	 *            人員工號
	 * @return 滿足條件的員工記錄
	 */
	public PrpDuser getUser(String userCode);

	/**
	 * 根據條件查詢員工記錄.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁顯示的記錄條數
	 * @return 符合查詢條件的集合
	 */
	public List<PrpDuser> findByQureyRuleList(QueryRule queryRule, int pageNo,
			int pageSize);

	/**
	 * 根據主鍵得到員工記錄.
	 * 
	 * @param operatorCode
	 *            the operator code
	 * @return 滿足條件的員工記錄
	 */
	public PrpDuser findByPrimaryKey(String operatorCode);
}
