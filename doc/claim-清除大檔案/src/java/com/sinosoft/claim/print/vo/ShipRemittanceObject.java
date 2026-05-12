package com.sinosoft.claim.print.vo;

/**
 * 匯款同意書數據對象
 * @author Sinosoft
 */

public class ShipRemittanceObject {
	/** 立案号码 */
	private String claimNo;
	/** 保单号码 */
	private String policyNo;
	/** 被保險人名稱 */
	private String insuredName;
	/** 被保險人地址 */
	private String insuredAddress;
	/** 被保險人统一编号 */
	private String insuredIdentifyNumber;
	
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
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
}
