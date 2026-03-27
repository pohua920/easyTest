/**
 * 2014-6-10
 */
package com.sinosoft.claim.print.vo;

/**
 * 工程险  撤銷申請理賠同意書  数据对象 
 * @author 中科軟
 */
public class GAARevocationObject {
	private String claimNo;
	private String policyNo;
	private String damageStartDate;
	
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
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	
}
