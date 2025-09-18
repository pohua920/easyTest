package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "status", "result", "history_cases" })
public class CompulsoryQueryResultVo {

	
	@JsonProperty("status")
	private String status;
	@JsonProperty("code")
	private String code;
	@JsonProperty("message")
	private String message;
	@JsonProperty("result")
	private CompulsoryResultVo result;
	@JsonProperty("history_cases")
	private List<CompulsoryHistoryCaseVo> historyCases;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public CompulsoryResultVo getResult() {
		return result;
	}

	public void setResult(CompulsoryResultVo result) {
		this.result = result;
	}

	public List<CompulsoryHistoryCaseVo> getHistoryCases() {
		return historyCases;
	}
	public void setHistoryCases(List<CompulsoryHistoryCaseVo> historyCases) {
		this.historyCases = historyCases;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
