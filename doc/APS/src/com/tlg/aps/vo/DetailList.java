package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/*mantis：FIR0181， 中信新件流程-排程查詢作業(含產生查詢及回饋查詢)*/
@XmlRootElement
public class DetailList {

	private String checkType;
	private String ruleId;
	private String ruleSeq;
	private String ruleMsg;
	private String ruleResult;
	
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
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
	public String getRuleMsg() {
		return ruleMsg;
	}
	public void setRuleMsg(String ruleMsg) {
		this.ruleMsg = ruleMsg;
	}
	public String getRuleResult() {
		return ruleResult;
	}
	public void setRuleResult(String ruleResult) {
		this.ruleResult = ruleResult;
	}
}
