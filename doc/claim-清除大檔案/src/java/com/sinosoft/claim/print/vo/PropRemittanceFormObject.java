package com.sinosoft.claim.print.vo;

/**
 * 匯款同意書 數據對象
 * @author 中科軟
 *
 */
public class PropRemittanceFormObject {
	/**賠案號碼*/
	private String claimNo;
	/** 保單號碼*/
	private String policyNo;
	/**大寫賠款金額*/
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
