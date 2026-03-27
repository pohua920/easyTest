package com.sinosoft.undwrt.common.service.facade;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;

import java.util.Collection;
import java.util.List;

import com.sinosoft.undwrt.common.model.PrpDclass;
import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.undwrt.common.model.PrpDuser;

// TODO: Auto-generated Javadoc
/**
 * 險種接口類.
 */
public interface PrpDriskService {
	
	/**
	 * 根據條件查詢險種.
	 *
	 * @param conditions 查詢條件
	 * @return 滿足條件的集合
	 * @throws Exception 異常
	 */
	public Collection findByConditions(String conditions) throws Exception;

	/**
	 * 根據條件查詢險種.
	 *
	 * @param queryRule 查詢規則
	 * @param pageNo 頁碼
	 * @param pageSize 每頁顯示的記錄條數
	 * @return Page對象
	 */
	public Page queryRulePrpDriskPage(QueryRule queryRule, int pageNo, int pageSize) ;
	
	/**
	 * 根據條件查詢險種.
	 *
	 * @param queryRule 查詢規則
	 * @return 滿足條件的集合
	 */
	public List<PrpDrisk> findByQureyRuleList(QueryRule queryRule);
	
	/**
	 * 根據條件查詢險種.
	 *
	 * @param hql 條件
	 * @param pageNo 頁碼
	 * @param pageSize 每頁顯示的記錄條數
	 * @return Page對象
	 */
	public Page findByHql(String hql,int pageNo,int pageSize) ;
	
	/**
	 * 根據條件查詢險種.
	 *
	 * @param hql 條件
	 * @param str1 條件的值
	 * @return 符合條件的集合
	 */
	public List<PrpDrisk> findByHqlList(String hql,String str1) ;
	
	/**
	 * 根據主鍵查詢險種.
	 *
	 * @param queryRule 查詢規則
	 * @return 符合條件的險種記錄
	 */
	public PrpDrisk findByPrimaryKey(QueryRule queryRule);
	
}
