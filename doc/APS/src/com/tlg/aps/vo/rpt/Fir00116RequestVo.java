package com.tlg.aps.vo.rpt;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/** mantis：FIR0621，處理人員：BJ085，需求單編號：FIR0621 住火_臺銀續保作業_續件資料處理作業 */
@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fir00116RequestVo implements Serializable {

	@JsonProperty("batchNo")
	private String batchNo;

	@JsonProperty("extracomcode")
	private String extracomcode;
	
	@JsonProperty("isAutoRenew")
	private String isAutoRenew;

	
	@JsonProperty("batchNo")
	public String getBatchNo() {
		return batchNo;
	}

	@JsonProperty("batchNo")
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@JsonProperty("extracomcode")
	public String getExtracomcode() {
		return extracomcode;
	}

	@JsonProperty("extracomcode")
	public void setExtracomcode(String extracomcode) {
		this.extracomcode = extracomcode;
	}

	@JsonProperty("isAutoRenew")
	public String getIsAutoRenew() {
		return isAutoRenew;
	}

	@JsonProperty("isAutoRenew")
	public void setIsAutoRenew(String isAutoRenew) {
		this.isAutoRenew = isAutoRenew;
	}
}
