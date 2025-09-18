package com.tlg.aps.webService.claimBlockChainService.client.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "title", "status", "code", "message", "case", "apportion"})
public class CompulsoryCreateResultVo {

	@JsonProperty("title")
	private String title;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("code")
	private String code;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("case")
	private CompulsoryCreateResultCaseVo _case;
	
	@JsonProperty("apportion")
	private CompulsoryCreateResultApportionVo apportion;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CompulsoryCreateResultCaseVo get_case() {
		return _case;
	}

	public void set_case(CompulsoryCreateResultCaseVo _case) {
		this._case = _case;
	}

	public CompulsoryCreateResultApportionVo getApportion() {
		return apportion;
	}

	public void setApportion(CompulsoryCreateResultApportionVo apportion) {
		this.apportion = apportion;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
