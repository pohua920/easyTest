package com.sinosoft.claim.print.vo;

/**
 * 貨物運輸險權利轉讓書數據對象
 * @author Sinosoft
 */

public class ShipClaimApplicationObject {
	/** 險種名称 */
	private String riskName;
	/** 备案號碼 */
	private String registNo;
	/** 被保險人名称 */
	private String insuredName;
	/** 被保險人地址 */
	private String insuredAddress;
	/** 保單號碼 */
	private String policyNo;
	/** 保險起期 */
	private String startDate;
	/** 保險至期 */
	private String endDate;
	/** 是否缴费 */
	private String payFlag;
	/** 承保范围 */
	private String coverage;
	/** 出險摘要 */
	private String damageContent;
	/** 属性估损金额 */
	private String estimateLoss;
	
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsuredAddress() {
		return insuredAddress;
	}
	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPayFlag() {
		return payFlag;
	}
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	public String getDamageContent() {
		return damageContent;
	}
	public void setDamageContent(String damageContent) {
		this.damageContent = damageContent;
	}
	public String getEstimateLoss() {
		return estimateLoss;
	}
	public void setEstimateLoss(String estimateLoss) {
		this.estimateLoss = estimateLoss;
	}
}
