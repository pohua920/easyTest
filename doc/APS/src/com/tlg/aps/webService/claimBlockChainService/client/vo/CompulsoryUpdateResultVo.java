package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "title", "status", "code", "message", "messages", "case" })
public class CompulsoryUpdateResultVo {

	@JsonProperty("title")
	private String title;

	@JsonProperty("status")
	private String status;

	@JsonProperty("code")
	private String code;
	
	@JsonProperty("message")
	private String message;

	@JsonProperty("messages")
	private List<String> messages;

	@JsonProperty("case")
	private CompulsoryUpdateResultCaseVo updateCase;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CompulsoryUpdateResultCaseVo getUpdateCase() {
		return updateCase;
	}

	public void setUpdateCase(CompulsoryUpdateResultCaseVo updateCase) {
		this.updateCase = updateCase;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

}
