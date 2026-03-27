package com.sinosoft.undwrt.common.service.spring;

import ins.framework.common.Page;
import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.undwrt.common.model.PrpDuser;
import com.sinosoft.undwrt.common.service.facade.PrpDuserService;

/**
 * 用戶實現類.
 */
public class PrpDuserServiceSpringImpl extends
		GenericDaoHibernate<PrpDuser, String> implements PrpDuserService {

	/**
	 * 根據員工工號得到員工信息.
	 * 
	 * @param userCode
	 *            人員工號
	 * @return 滿足條件的員工記錄
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDuserService#getUser(java.lang.String)
	 */
	public PrpDuser getUser(String userCode) {
		return super.get(userCode);

	}

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
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDuserService#findByQureyRuleList(ins.framework.common.QueryRule,
	 *      int, int)
	 */
	@Override
	public List<PrpDuser> findByQureyRuleList(QueryRule queryRule, int pageNo,
			int pageSize) {
		List<PrpDuser> list = null;
		Page page = super.find(queryRule, pageNo, pageSize);
		list = page.getResult();

		return list;
	}

	/**
	 * 根據主鍵得到員工記錄.
	 * 
	 * @param operatorCode
	 *            the operator code
	 * @return 滿足條件的員工記錄
	 * @see com.sinosoft.undwrt.common.service.facade.PrpDuserService#findByPrimaryKey(java.lang.String)
	 */
	@Override
	public PrpDuser findByPrimaryKey(String operatorCode) {
		// TODO Auto-generated method stub
		return super.get(operatorCode);
	}

}