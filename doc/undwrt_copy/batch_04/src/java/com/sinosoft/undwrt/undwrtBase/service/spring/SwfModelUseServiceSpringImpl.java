package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.SwfModelUse;
import com.sinosoft.undwrt.undwrtBase.model.SwfModelUseId;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfModelUseService;

/**
 * 模板使用設定實現類.
 */
public class SwfModelUseServiceSpringImpl extends
		GenericDaoHibernate<SwfModelUse, SwfModelUseId> implements
		SwfModelUseService {

	/**
	 * 根據條件查詢模板設定的集合.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄集合
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfModelUseService#getSwfModelUseList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<SwfModelUse> getSwfModelUseList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return super.find(queryRule);
	}
}