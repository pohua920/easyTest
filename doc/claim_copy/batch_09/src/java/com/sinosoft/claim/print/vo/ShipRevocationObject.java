package com.sinosoft.claim.print.vo;

/**
 * 撤銷申請理賠同意書數據對象
 * @author Sinosoft
 */

public class ShipRevocationObject {
	/** 保单号码 */
	private String policyNo;
	/** 立案号码 */
	private String claimNo;
	/** 出险日期 */
	private String damageTime;
	
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getDamageTime() {
		return damageTime;
	}
	public void setDamageTime(String damageTime) {
		this.damageTime = damageTime;
	}
}
