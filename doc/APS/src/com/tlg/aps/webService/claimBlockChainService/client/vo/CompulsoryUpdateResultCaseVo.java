package com.tlg.aps.webService.claimBlockChainService.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "apportion", "id" })
public class CompulsoryUpdateResultCaseVo  {
	
	/**
	 * 案件流水號
	 */
	@JsonProperty("id")
	private String id;

	/**
	 * 更新案件結果
	 */
	@JsonProperty("apportion")
	private CompulsoryUpdateResultApportionVo apportion;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CompulsoryUpdateResultApportionVo getApportion() {
		return apportion;
	}

	public void setApportion(CompulsoryUpdateResultApportionVo apportion) {
		this.apportion = apportion;
	}

	
}
