package com.sinosoft.undwrt.undwrtRule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.one.rule.service.facade.DroolsRuleService;
import com.sinosoft.undwrt.pub.InternationalizationUtil;

/**
 * 核保審核規則引擎處理接口類.
 */
@Component
public class UndwrtRuleService {

	/** 屬性規則引擎接口類. */
	@Autowired
	private DroolsRuleService droolsRuleService;

	/**
	 * 校驗核保規則.
	 * 
	 * @param level
	 *            核保級別
	 * @param condition
	 *            规则引擎的条件对象
	 * @return 成功返回true,失敗返回false
	 * @throws Exception
	 *             異常
	 */
	public boolean undwrt(String level, UndwrtRuleCondition condition) throws Exception{
		InternationalizationUtil internal = new InternationalizationUtil();
		droolsRuleService.executeRules("undwrtRuleFlow", "undwrtChangeSetCar.xml",
				level, condition);
		if(!condition.isRule()){
        	throw new Exception(internal.getText("undwrt.service.task.contractAdmin"));
        }
		return condition.getResult();
	}

	/**
	 * 獲取屬性規則引擎接口類.
	 * 
	 * @return 屬性規則引擎接口類的值
	 */
	public DroolsRuleService getDroolsRuleService() {
		return droolsRuleService;
	}

	/**
	 * 設置屬性規則引擎接口類.
	 * 
	 * @param droolsRuleService
	 *            待設置的規則引擎接口類的值
	 */
	public void setDroolsRuleService(DroolsRuleService droolsRuleService) {
		this.droolsRuleService = droolsRuleService;
	}
}
