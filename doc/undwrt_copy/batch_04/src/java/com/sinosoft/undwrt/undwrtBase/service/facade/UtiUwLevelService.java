package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.UtiUwLevel;

// TODO: Auto-generated Javadoc
/**
 * 核保級別設定接口類.
 */
public interface UtiUwLevelService {

	/**
	 * 查詢某一級別的記錄.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的集合
	 */
	public List<UtiUwLevel> getUtiUwLevelList(QueryRule queryRule);
}
