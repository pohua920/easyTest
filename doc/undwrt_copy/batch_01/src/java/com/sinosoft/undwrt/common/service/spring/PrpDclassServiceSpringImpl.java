package com.sinosoft.undwrt.common.service.spring;

import java.util.Collection;
import java.util.List;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.undwrt.common.model.PrpDclass;
import com.sinosoft.undwrt.common.service.facade.PrpDclassService;

/**
 * 險類實現類.
 */
public class PrpDclassServiceSpringImpl extends
		GenericDaoHibernate<PrpDclass, String> implements PrpDclassService {

	/**
	 * 根據條件查詢險類.
	 * 
	 * @param conditions
	 *            查詢條件
	 * @return 符合條件的險類集合
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDclassService#findByConditions(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<PrpDclass> findByConditions(String conditions)
			throws Exception {
		// TODO Auto-generated method stub
		String sql = "select * from prpdclass where " + conditions;
		List<PrpDclass> list = super.getSession().createSQLQuery(sql)
				.addEntity(PrpDclass.class).list();
		return list;
	}

	/**
	 * 根據條件查詢險類.
	 * 
	 * @param queryRule
	 *            查询规则
	 * @return 符合條件的險類集合
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDclassService#findPrpDclassList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<PrpDclass> findPrpDclassList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}

	/**
	 * 根據主鍵查詢險類.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 險類類
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDclassService#findByPrimaryKey(ins.framework.common.QueryRule)
	 */
	@Override
	public PrpDclass findByPrimaryKey(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.findUnique(queryRule);
	}

}
