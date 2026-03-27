package com.sinosoft.undwrt.common.service.spring;

import java.util.List;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.undwrt.common.model.PrpDcompany;
import com.sinosoft.undwrt.common.service.facade.PrpDcompanyService;

/**
 * 機構實現類.
 */
public class PrpDcompanyServiceSpringImpl extends
		GenericDaoHibernate<PrpDcompany, String> implements PrpDcompanyService {

	/**
	 * 根據機構代碼查詢機構.
	 * 
	 * @param comCode
	 *            機構代碼
	 * @return 符合條件的機構
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcompanyService#findByPrimaryKey(java.lang.String)
	 */
	public PrpDcompany findByPrimaryKey(String comCode) {
		return super.get(comCode);
	}

	/**
	 * 根據條件查詢機構.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 符合條件的機構
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDcompanyService#findByPrimaryKey(ins.framework.common.QueryRule)
	 */
	public PrpDcompany findByPrimaryKey(QueryRule queryRule) {
		return super.findUnique(queryRule);
	}

	@Override
	public List<PrpDcompany> findByConditions(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}
}
