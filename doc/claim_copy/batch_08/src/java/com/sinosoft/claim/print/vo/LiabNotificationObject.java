/**
 * 2014-6-11
 */
package com.sinosoft.claim.print.vo;

/**
 * 责任险  補件通知函  数据对象 
 * @author 中科軟
 */
public class LiabNotificationObject {
	private String claimNo;
	private String damageStartDate;
	private String riskCname;
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	public String getRiskCname() {
		return riskCname;
	}
	public void setRiskCname(String riskCname) {
		this.riskCname = riskCname;
	}

}
