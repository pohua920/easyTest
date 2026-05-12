package com.sinosoft.claim.print.vo;

/**
 * 債權讓與契約暨通知書數據對象
 * @author Sinosoft
 */

public class ShipContractObject {
	/** 保单号码 */
	private String policyNo;
	/** 被保險人名稱 */
	private String insuredName;
	/** 被保險人地址 */
	private String insuredAddress;
	/** 被保險人统一编号 */
	private String insuredIdentifyNumber;
	/** 出险日期 */
	private String damageTime;
	/** 险种名称 */
	private String riskName;
	
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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
	public String getInsuredIdentifyNumber() {
		return insuredIdentifyNumber;
	}
	public void setInsuredIdentifyNumber(String insuredIdentifyNumber) {
		this.insuredIdentifyNumber = insuredIdentifyNumber;
	}
	public String getDamageTime() {
		return damageTime;
	}
	public void setDamageTime(String damageTime) {
		this.damageTime = damageTime;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
}
