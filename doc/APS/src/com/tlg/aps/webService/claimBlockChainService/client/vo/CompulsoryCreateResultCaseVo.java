package com.tlg.aps.webService.claimBlockChainService.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "owner_msp_id", "id" })
public class CompulsoryCreateResultCaseVo {

	@JsonProperty("id")
	private String id;

	@JsonProperty("owner_msp_id")
	private String ownerMspId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwnerMspId() {
		return ownerMspId;
	}

	public void setOwnerMspId(String ownerMspId) {
		this.ownerMspId = ownerMspId;
	}

}
