package com.sinosoft.claim.print.vo;

/**
 * 火險承保理賠信息 數據對象 子報表數據 （批改情況）
 * @author 中科軟
 *
 */
public class PropPrpinsClaimInformationSubThreeObject {
	/**批單號碼 */
	private String endorseNo;
	/**批改原因*/
	private String endorReason;
	/**批改時間*/
	private String endorDate;
	/**核保人*/
	private String underWriteName;
	public String getEndorseNo() {
		return endorseNo;
	}
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}
	public String getEndorReason() {
		return endorReason;
	}
	public void setEndorReason(String endorReason) {
		this.endorReason = endorReason;
	}
	public String getEndorDate() {
		return endorDate;
	}
	public void setEndorDate(String endorDate) {
		this.endorDate = endorDate;
	}
	public String getUnderWriteName() {
		return underWriteName;
	}
	public void setUnderWriteName(String underWriteName) {
		this.underWriteName = underWriteName;
	}
	
}
