/**
 * 2014-6-10
 */
package com.sinosoft.claim.print.vo;

/**
 * 工程险 匯款同意書 数据对象 
 * @author 中科軟
 */
public class GAARemittanceObject {

	private String claimNo;
	private String policyNo;
	private String claimAmount;
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
	public String getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}
}
