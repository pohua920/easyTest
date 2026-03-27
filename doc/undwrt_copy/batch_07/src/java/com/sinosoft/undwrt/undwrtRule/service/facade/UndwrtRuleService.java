package com.sinosoft.undwrt.undwrtRule.service.facade;

import com.sinosoft.undwrt.undwrtRule.service.UndwrtRuleCondition;
import com.sinosoft.undwrt.undwrtRule.vo.BusinessProposalData;

/**
 * 核保審核規則引擎處理接口類.
 */
public interface UndwrtRuleService {

	/**
	 * 校驗核保規則.
	 * @param level 核保級別
	 * @param 规则引擎的条件对象
	 * @return 成功返回true,失敗返回false
	 * @throws Exception 異常
	 */
	public boolean checkUndwrtRules(String level, UndwrtRuleCondition condition) throws Exception;
	
	/**
	 * 校驗核保規則.
	 * @param level 核保級別
	 * @param 规则引擎的条件对象
	 * @return 成功返回true,失敗返回false
	 * @throws Exception 異常
	 */
	public boolean checkUndwrtRules(String level, BusinessProposalData condition) throws Exception;
}
