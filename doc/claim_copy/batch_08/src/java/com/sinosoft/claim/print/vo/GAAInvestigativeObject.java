/**
 * 2014-6-12
 */
package com.sinosoft.claim.print.vo;

/**
 * 工程险  查案單  数据对象 
 * @author 中科軟
 */
public class GAAInvestigativeObject {

	private String claimNo;
	private String insuredName;
	private String damageStartDate;
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	
}
