package com.sinosoft.claim.print.vo;

/**
 * 火險承保理賠信息 數據對象 子報表數據 （賠付記錄）
 * @author 中科軟
 *
 */
public class PropPrpinsClaimInformationSubFiveObject {
	/**賠案號碼*/
	private String claimNo;
	/**出險時間*/
	private String damageDate;
	/**未決金額*/
	private String undecidedAmount;
	/**賠付金額*/
	private String claimPaid;
	/**結案日期*/
	private String closeDate;
	/**理算人*/
	private String handleName;
	/**核賠人*/
	private String underWriteName;
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getDamageDate() {
		return damageDate;
	}
	public void setDamageDate(String damageDate) {
		this.damageDate = damageDate;
	}
	public String getUndecidedAmount() {
		return undecidedAmount;
	}
	public void setUndecidedAmount(String undecidedAmount) {
		this.undecidedAmount = undecidedAmount;
	}
	public String getClaimPaid() {
		return claimPaid;
	}
	public void setClaimPaid(String claimPaid) {
		this.claimPaid = claimPaid;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public String getHandleName() {
		return handleName;
	}
	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}
	public String getUnderWriteName() {
		return underWriteName;
	}
	public void setUnderWriteName(String underWriteName) {
		this.underWriteName = underWriteName;
	}
	
}
