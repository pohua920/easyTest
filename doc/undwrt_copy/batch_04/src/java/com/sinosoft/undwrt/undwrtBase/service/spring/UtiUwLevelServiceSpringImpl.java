package com.sinosoft.undwrt.undwrtBase.service.spring;

import ins.framework.common.QueryRule;
import ins.framework.dao.GenericDaoHibernate;

import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.UtiUwLevel;
import com.sinosoft.undwrt.undwrtBase.model.UtiUwLevelId;
import com.sinosoft.undwrt.undwrtBase.service.facade.UtiUwLevelService;

/**
 * 核保級別設定實現類.
 */
public class UtiUwLevelServiceSpringImpl extends GenericDaoHibernate<UtiUwLevel, UtiUwLevelId> implements UtiUwLevelService {

	/**
	 * 查詢核保級別設置列表.
	 * 
	 * @param queryRule
	 *            查詢條件
	 * @return 核保級別設置列表
	 * @see com.sinosoft.undwrt.undwrtBase.service.facade.UtiUwLevelService#getUtiUwLevelList(ins.framework.common.QueryRule)
	 */
	@Override
	public List<UtiUwLevel> getUtiUwLevelList(QueryRule queryRule) {
		// TODO Auto-generated method stub
		return this.find(queryRule);
	}

}
