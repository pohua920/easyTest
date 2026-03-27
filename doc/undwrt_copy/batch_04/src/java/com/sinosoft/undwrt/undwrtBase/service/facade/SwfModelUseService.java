package com.sinosoft.undwrt.undwrtBase.service.facade;

import ins.framework.common.QueryRule;

import java.util.List;

import com.sinosoft.undwrt.undwrtBase.model.SwfModelUse;

// TODO: Auto-generated Javadoc
/**
 * 模板使用設定接口類.
 */
public interface SwfModelUseService {

	/**
	 * 根據條件查詢模板設定的集合.
	 * 
	 * @param queryRule
	 *            查詢規則
	 * @return 滿足條件的記錄集合
	 */
	public List<SwfModelUse> getSwfModelUseList(QueryRule queryRule);
}