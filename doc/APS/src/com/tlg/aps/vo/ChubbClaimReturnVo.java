package com.tlg.aps.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/** mantis：MOB0020，處理人員：BI086，需求單編號：MOB0020 理賠盜打詐騙API及XML作業 */

public class ChubbClaimReturnVo {

	@JsonProperty(value = "operationResult")
	private String operationResult;
	@JsonProperty(value = "detailDescription")
	private String detailDescription;
	@JsonProperty(value = "MTN_NO")
	private String mtnNo;
	@JsonProperty(value = "IMEI")
	private String imei;
	@JsonProperty(value = "Contract_ID")
	private String contractId;
	@JsonProperty(value = "IclaimNo")
	private String iclaimNo;

	public String getOperationResult() {
		return operationResult;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}

	public String getDetailDescription() {
		return detailDescription;
	}

	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}

	public String getMtnNo() {
		return mtnNo;
	}

	public void setMtnNo(String mtnNo) {
		this.mtnNo = mtnNo;
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

	public String getIclaimNo() {
		return iclaimNo;
	}

	public void setIclaimNo(String iclaimNo) {
		this.iclaimNo = iclaimNo;
	}

}
