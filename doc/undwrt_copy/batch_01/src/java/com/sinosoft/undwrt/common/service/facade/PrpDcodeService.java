package com.sinosoft.undwrt.common.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.undwrt.common.model.PrpDcode;
import com.sinosoft.undwrt.common.model.PrpDcodeId;

// TODO: Auto-generated Javadoc
/**
 * 基礎代碼表接口類.
 */
public interface PrpDcodeService {

	/**
	 * 根據條件查詢基礎代碼.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁顯示的記錄條數
	 * @return 符合條件的集合
	 */
	public List<PrpDcode> findByHslList(QueryRule queryRule, int pageNo, int pageSize);
	
	/**
	 * 根據條件查詢基礎代碼.
	 *
	 * @param queryRule 查詢規則
	 * @return 符合條件的集合
	 */
	public List<PrpDcode> findPrpDcodeList(QueryRule queryRule);
	
    /**
     *根據sql查詢基礎代碼.
     *
     * @param sql 查詢條件
     * @return 符合條件的集合
     */
    public List findBySqlList(String sql);
	
	/**
	 * 根據條件查詢基礎代碼.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁顯示的記錄條數
	 * @return page對象
	 */
	public Page findUserTaskList(QueryRule queryRule, int pageNo, int pageSize);
	
	/**
	 * 根據業務代碼得到一條基礎代碼記錄.
	 *
	 * @param codecode 業務代碼
	 * @return 符合條件的記錄
	 */
	public PrpDcode get(String codecode);

	/**
	 * 根據條件查詢基礎代碼.
	 *
	 * @param queryRule 查詢規則
	 * @return 符合條件的記錄集合
	 * @throws Exception 異常
	 */
	public List findByConditions(QueryRule queryRule) throws Exception;
	
	/**
	 * 獲得風險類別.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @return 風險類別
	 * @throws Exception
	 *             異常
	 */
	public Collection getDangerRiskKind(String riskCode)
			throws Exception;
	
}
