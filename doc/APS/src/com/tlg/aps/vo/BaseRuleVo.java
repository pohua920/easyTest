package com.tlg.aps.vo;

import java.util.HashMap;
import java.util.Map;

import com.tlg.prpins.entity.CmnpBizRule;
import com.tlg.rule.vo.ResultObject;

public class BaseRuleVo {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	
	/**
	 * 檢核結果<br>
	 */
	private ResultObject resultObject;
	/**
	 * 必填。<br>
	 * C:車, M:水, F:火, E:工程, A:責任, B:傷害, H:健康, P:共用<br>
	 */
	private String insType;
	
	/**
	 * 必填。<br>
	 * W:報價試算<br> 
	 * P:承保<br>
	 * E:批改<br>
	 * R:再保<br>
	 * C:理賠<br>
	 * A:共用<br>
	 */
	private String operationType;
	/**
	 * 必填。<br>
	 * 以車險為例CARR0001，所以prefix就是CARR<br>
	 * 以水險為例MARR0001，所以prefix就是MARR<br>
	 */
	private String rulePrefix;

	/**
	 * 必填。<br>
	 * 險別，例如：車險C...<br>
	 */
	private String iinscls;
	/**
	 * 必填。<br>
	 * 公司代號，判斷是否要執行或不執行Rule<br>
	 * 例如，總公司00...
	 */
	private String branch;
//	/**
//	 * 允許執行此rule的單位
//	 */
//	private Map<String, ArrayList<CmnpBizSuspend1>> ruleUnitMap;
//	/**
//	 * 不允許執行此rule的單位
//	 */
//	private Map<String, ArrayList<CmnpBizSuspend>> ruleSuspendUnitMap;
	/**
	 * 存放rule的map 
	 */
	private HashMap<String, CmnpBizRule> ruleMap;
	/**
	 * 自定義的errcode
	 */
	private Map<String, String> errMsgCode= new HashMap<String, String>();
	
	public String getIinscls() {
		return iinscls;
	}

	public void setIinscls(String iinscls) {
		this.iinscls = iinscls;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}


	public String getRulePrefix() {
		return rulePrefix;
	}

	public void setRulePrefix(String rulePrefix) {
		this.rulePrefix = rulePrefix;
	}

	public ResultObject getResultObject() {
		return resultObject;
	}

	public void setResultObject(ResultObject resultObject) {
		this.resultObject = resultObject;
	}

//	public Map<String, ArrayList<CmnpBizSuspend1>> getRuleUnitMap() {
//		return ruleUnitMap;
//	}
//
//	public void setRuleUnitMap(Map<String, ArrayList<CmnpBizSuspend1>> ruleUnitMap) {
//		this.ruleUnitMap = ruleUnitMap;
//	}
//
//	public Map<String, ArrayList<CmnpBizSuspend>> getRuleSuspendUnitMap() {
//		return ruleSuspendUnitMap;
//	}
//
//	public void setRuleSuspendUnitMap(
//			Map<String, ArrayList<CmnpBizSuspend>> ruleSuspendUnitMap) {
//		this.ruleSuspendUnitMap = ruleSuspendUnitMap;
//	}

	public HashMap<String, CmnpBizRule> getRuleMap() {
		return ruleMap;
	}

	public void setRuleMap(HashMap<String, CmnpBizRule> ruleMap) {
		this.ruleMap = ruleMap;
	}

	public Map<String, String> getErrMsgCode() {
		return errMsgCode;
	}

	public void setErrMsgCode(Map<String, String> errMsgCode) {
		this.errMsgCode = errMsgCode;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

}
