package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import com.sinosoft.undwrt.undwrtBase.model.SwfPathNew;
import com.sinosoft.undwrt.undwrtBase.model.SwfPathNewId;
import com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathNewService;

/**
 * 路徑實現類.
 */
public class SwfPathNewServiceImpl extends
		GenericDaoHibernate<SwfPathNew, SwfPathNewId> implements
		SwfPathNewService {

	/**
	 * 根據主鍵查詢路徑.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return 路徑類
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.SwfPathNewService#findByPrimaryKey(ins.framework.common.QueryRule)
	 */
	@Override
	// add by wangjun 20130119
	public SwfPathNew findByPrimaryKey(QueryRule queryRule) {

		SwfPathNew swfpathnew = null;
		swfpathnew = super.findUnique(queryRule);
		return swfpathnew;
	}

}
