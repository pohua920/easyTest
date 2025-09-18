package com.tlg.aps.vo;

/** mantis：MOB0020，處理人員：BI086，需求單編號：MOB0020 理賠盜打詐騙API及XML作業 */

public class ChubbClaimResponseVo {

	private String date;

	private String rptBatchNo;

	private String policyQueryResponse;

	private String claimAuthResponse;

	private String imei;

	private String contractId;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRptBatchNo() {
		return rptBatchNo;
	}

	public void setRptBatchNo(String rptBatchNo) {
		this.rptBatchNo = rptBatchNo;
	}

	public String getPolicyQueryResponse() {
		return policyQueryResponse;
	}

	public void setPolicyQueryResponse(String policyQueryResponse) {
		this.policyQueryResponse = policyQueryResponse;
	}

	public String getClaimAuthResponse() {
		return claimAuthResponse;
	}

	public void setClaimAuthResponse(String claimAuthResponse) {
		this.claimAuthResponse = claimAuthResponse;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
}
