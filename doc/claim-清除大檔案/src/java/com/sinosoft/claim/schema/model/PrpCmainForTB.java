package com.sinosoft.claim.schema.model;

public class PrpCmainForTB {
	/** 保单号 */
	private String policyNo;
	/** 险种 */
	private String riskCode;
	/** 批改日期 */
	private String endorDate;
	/** 批改生效日期 */
	private String validDate;
	/** 批改生效小时 */
	private String validHour;
	/** 经办部门 */
	private String makeCom;
	/** 批改原因 */
	private String reasonCode;
	/** 批改原因备注 */
	private String reasonText;
	/** 新起保日期 */
	private String newStartDate;
	/** 新起保小时 */
	private String newStartHour;
	/** 新终保日期 */
	private String newEndDate;
	/** 新终保小时 */
	private String newEndHour;
	/** 批改类型 */
	private String endorType;
	/** 总保费 */
	private String SumPremium;

	public String getEndorDate() {
		return endorDate;
	}

	public void setEndorDate(String endorDate) {
		this.endorDate = endorDate;
	}

	public String getMakeCom() {
		return makeCom;
	}

	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	public String getNewEndDate() {
		return newEndDate;
	}

	public void setNewEndDate(String newEndDate) {
		this.newEndDate = newEndDate;
	}

	public String getNewEndHour() {
		return newEndHour;
	}

	public void setNewEndHour(String newEndHour) {
		this.newEndHour = newEndHour;
	}

	public String getNewStartDate() {
		return newStartDate;
	}

	public void setNewStartDate(String newStartDate) {
		this.newStartDate = newStartDate;
	}

	public String getNewStartHour() {
		return newStartHour;
	}

	public void setNewStartHour(String newStartHour) {
		this.newStartHour = newStartHour;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getValidHour() {
		return validHour;
	}

	public void setValidHour(String validHour) {
		this.validHour = validHour;
	}

	public String getEndorType() {
		return endorType;
	}

	public void setEndorType(String endorType) {
		this.endorType = endorType;
	}

	public String getSumPremium() {
		return SumPremium;
	}

	public void setSumPremium(String sumPremium) {
		SumPremium = sumPremium;
	}

}
