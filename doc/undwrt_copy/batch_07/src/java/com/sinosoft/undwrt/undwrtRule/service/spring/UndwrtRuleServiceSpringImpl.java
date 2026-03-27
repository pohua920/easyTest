package com.sinosoft.undwrt.undwrtRule.service.spring;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.sinosoft.one.rule.service.facade.DroolsRuleService;
import com.sinosoft.sysframework.reference.AppConfig;
import com.sinosoft.undwrt.pub.InternationalizationUtil;
import com.sinosoft.undwrt.undwrtRule.service.UndwrtRuleCondition;
import com.sinosoft.undwrt.undwrtRule.service.facade.UndwrtRuleService;
import com.sinosoft.undwrt.undwrtRule.vo.BusinessProposalData;

/**
 * 核保審核規則引擎處理實現類.
 */
@Component
public class UndwrtRuleServiceSpringImpl implements UndwrtRuleService{

	/** 屬性規則引擎接口. */
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
	 * 校驗核保規則.
	 * 
	 * @param level
	 *            核保級別
	 * @param condition
	 *            规则引擎的条件对象
	 * @return 成功返回true,失敗返回false
	 * @throws Exception
	 *             異常
	 * @see com.sinosoft.undwrt.undwrtRule.service.facade.UndwrtRuleService#checkUndwrtRules(java.lang.String,
	 *      com.sinosoft.undwrt.undwrtRule.service.UndwrtRuleCondition)
	 */
	@Override
	public boolean checkUndwrtRules(String level,
			UndwrtRuleCondition condition) throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		droolsRuleService.executeRules("undwrtRuleFlow", "undwrtChangeSetCar.xml",
				level, condition);
		if(!condition.isRule()){
        	throw new Exception(internal.getText("undwrt.service.task.contractAdmin"));
        }
		return condition.getResult();
	}
	
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
	 * @see com.sinosoft.undwrt.undwrtRule.service.facade.UndwrtRuleService#checkUndwrtRules(java.lang.String,
	 *      com.sinosoft.undwrt.undwrtRule.vo.BusinessProposalData)
	 */
	@Override
	public boolean checkUndwrtRules(String level, BusinessProposalData condition)
			throws Exception {
		InternationalizationUtil internal = new InternationalizationUtil();
		String classCode = condition.getClassCode();
		String riskCode = condition.getRiskCode();
		String projectCode = condition.getProjectCode();
		String approvalNo = condition.getApprovalNo();
		Map session = ActionContext.getContext().getSession();  
		String comCode =(String) session.get("myComCode");
		String changeSetXML = "undwrtChangeSet_";
		String expectedRiskCode = AppConfig.get("sysconst.RISKCODE");
		boolean special = false;
		if(expectedRiskCode.indexOf(riskCode)>-1)
		{
			special = true;
		}
		if(classCode.equals("A") || classCode.equals("B"))
		{
			/*
			mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233--- start
			變更31,36,50,51,52,53,54險種核保檢核保額卡控問題
			*/
			if(!"9".equals(level)){
				changeSetXML += "Car-0"+level;
			}else{
				changeSetXML += "Car";
			}
			/* mantis： CAR0233，處理人員：Sam，需求單編號：CAR0233 --- end */
		}
		else if(special)
		{
			if("00".equals(comCode))
			{
				changeSetXML += riskCode + "_" + comCode;
			}
			else
			{
				changeSetXML += riskCode;
			}
		}
		else
		{
			if("00".equals(comCode) && !classCode.equals("C1") && !classCode.equals("C") && !classCode.equals("M") && !classCode.equals("E"))
			{
				changeSetXML += classCode + "_" + comCode;
			}
			else
			{
				if("C".equals(classCode)) {
					
					changeSetXML += classCode;
					changeSetXML += "_ProjectCode"; 
				}else{
					changeSetXML += classCode;
				}
//				if(classCode.equals("C")) {
//					if((null!=projectCode && !"".equals(projectCode))||(null!=approvalNo && !"".equals(approvalNo))) {
//					}
//				}
//				if(classCode.equals("C")) {
//					changeSetXML += classCode;
//					changeSetXML += ".xml";
//					droolsRuleService.executeRules("undwrtRuleFlow", changeSetXML, level, condition);
//					if(condition.getResult()==true){
//						changeSetXML += "_ProjectCode"; 
//						changeSetXML += ".xml";
//						droolsRuleService.executeRules("undwrtRuleFlow", changeSetXML, level, condition);
//					}
//				}else{
//					changeSetXML += classCode;
//				}
			}
			
		}
		
		changeSetXML += ".xml";
		droolsRuleService.executeRules("undwrtRuleFlow", changeSetXML, level, condition);
		if(!condition.isRulesCheckFlag()){
        	throw new Exception(internal.getText("undwrt.service.task.contractAdmin"));
        }
		return condition.getResult();
	}
	
	/**
	 * 獲取屬性規則引擎接口.
	 * 
	 * @return 屬性規則引擎接口的值
	 */
	public DroolsRuleService getDroolsRuleService() {
		return droolsRuleService;
	}

	/**
	 * 設置屬性規則引擎接口.
	 * 
	 * @param droolsRuleService
	 *            待設置的規則引擎接口的值
	 */
	public void setDroolsRuleService(DroolsRuleService droolsRuleService) {
		this.droolsRuleService = droolsRuleService;
	}
}
