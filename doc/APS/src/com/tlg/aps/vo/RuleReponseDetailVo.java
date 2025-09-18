package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RuleReponseDetailVo {
	/* mantis：FIR0225，處理人員：BJ085，需求單編號：FIR0225 稽核議題調整-過往資料檢核 start */
	/**
	 * id
	 */
	private String ruleId;
	
	/**
	 * seq
	 */
	private String ruleSeq;
	
	/**
	 * 檢核方式 0提示 1禁止
	 */
	private String checkType;

	/**
	 * 檢核結果 0提示 1禁止
	 */
	private String ruleResult;
	
	/**
	 * 檢核訊息
	 */
	private String ruleMsg;

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleSeq() {
		return ruleSeq;
	}

	public void setRuleSeq(String ruleSeq) {
		this.ruleSeq = ruleSeq;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getRuleResult() {
		return ruleResult;
	}

	public void setRuleResult(String ruleResult) {
		this.ruleResult = ruleResult;
	}

	public String getRuleMsg() {
		return ruleMsg;
	}

	public void setRuleMsg(String ruleMsg) {
		this.ruleMsg = ruleMsg;
	}
	
}

