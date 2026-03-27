package com.sinosoft.claim.print.vo;

/**
 * 責任險旅行業責任保險理賠照會單 數據對象
 * @author 中科軟
 */
public class LiabSingleNoteObject {
	/** 立案任務操作人員 */
	private String operatorName;
	/** 賠案號碼 */
	private String claimNo;
	/** 被保險人 */
	private String insuredName;
	/** 保單號碼 */
	private String policyNo;
	/** 聯繫人 */
	private String linkerName;
	/** 系統時間 */
	private String systemDate;

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

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

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getLinkerName() {
		return linkerName;
	}

	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	public String getSystemDate() {
		return systemDate;
	}

	public void setSystemDate(String systemDate) {
		this.systemDate = systemDate;
	}
}