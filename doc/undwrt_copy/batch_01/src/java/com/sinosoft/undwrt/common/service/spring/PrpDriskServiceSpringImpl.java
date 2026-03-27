package com.sinosoft.undwrt.common.service.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ins.framework.cache.CacheManager;
import ins.framework.cache.CacheService;
import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.platform.dto.domain.PrpDclassDto;
import com.sinosoft.platform.dto.domain.PrpDriskDto;

import com.sinosoft.undwrt.common.model.PrpDrisk;
import com.sinosoft.undwrt.common.service.facade.PrpDriskService;

/**
 * 險種實現類.
 */
public class PrpDriskServiceSpringImpl extends
		GenericDaoHibernate<PrpDrisk, String> implements PrpDriskService {

	// private static CacheService cacheManager =
	// CacheManager.getInstance("code");

	/**
	 * 根據條件查詢險種.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return 滿足條件的集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDriskService#findByConditions(java.lang.String)
	 */
	@Override
	public Collection findByConditions(String conditions) throws Exception {
		String sql = "select * from prpdrisk where " + conditions;
		List list = super.getSession().createSQLQuery(sql)
				.addEntity(PrpDrisk.class).list();

		return list;
	}

	/**
	 * 根據條件查詢險種.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁顯示的記錄條數
	 * @return Page對象
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDriskService#queryRulePrpDriskPage(ins.framework.common.QueryRule,
	 *      int, int)
	 */
	@Override
	public Page queryRulePrpDriskPage(QueryRule queryRule, int pageNo,
			int pageSize) {
		Page page = super.find(queryRule, pageNo, pageSize);

		return page;
	}

	/**
	 * 根據條件查詢險種.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的集合
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDriskService#findByQureyRuleList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<PrpDrisk> findByQureyRuleList(QueryRule queryRule) {
		return super.find(queryRule);
	}

	/**
	 * 根據條件查詢險種.
	 * 
	 * @param hql
	 *            條件
	 * @param pageNo
	 *            頁碼
	 * @param pageSize
	 *            每頁顯示的記錄條數
	 * @return Page對象
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDriskService#findByHql(java.lang.String,
	 *      int, int)
	 */
	@Override
	public Page findByHql(String hql, int pageNo, int pageSize) {

		return null;
	}

	/**
	 * 根據條件查詢險種.
	 * 
	 * @param hql
	 *            條件
	 * @param str1
	 *            條件的值
	 * @return 符合條件的集合
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDriskService#findByHqlList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<PrpDrisk> findByHqlList(String hql, String str1) {

		return super.findByHql(hql, str1);
	}

	/**
	 * 根據主鍵查詢險種.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 符合條件的險種記錄
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDriskService#findByPrimaryKey(ins.framework.common.QueryRule)
	 */
	@Override
	public PrpDrisk findByPrimaryKey(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.findUnique(queryRule);
	}

}
