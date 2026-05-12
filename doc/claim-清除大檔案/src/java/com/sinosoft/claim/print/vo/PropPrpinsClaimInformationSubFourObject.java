package com.sinosoft.claim.print.vo;

/**
 * 火險承保理賠信息 數據對象 子報表數據 （收費情況）
 * @author 中科軟
 *
 */
public class PropPrpinsClaimInformationSubFourObject {
	/**付款期數 期次*/
	private String paidTimes;
	/**批單號碼*/
	private String endorseNo;
	/**應收*/
	private String sumShouldPaid;
	/**實收*/
	private String sumRealpaid;
	/**到帳日期*/
	private String arriveDate;
	public String getPaidTimes() {
		return paidTimes;
	}
	public void setPaidTimes(String paidTimes) {
		this.paidTimes = paidTimes;
	}
	public String getEndorseNo() {
		return endorseNo;
	}
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}
	public String getSumShouldPaid() {
		return sumShouldPaid;
	}
	public void setSumShouldPaid(String sumShouldPaid) {
		this.sumShouldPaid = sumShouldPaid;
	}
	public String getSumRealpaid() {
		return sumRealpaid;
	}
	public void setSumRealpaid(String sumRealpaid) {
		this.sumRealpaid = sumRealpaid;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}

}
