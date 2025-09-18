package com.tlg.aps.vo.liaJsonObj.ipb905i.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cmptype", "cmpno", "moudle", "pageCount", "pageNumber" })
public class Ipb905iRequestVo implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("cmptype")
	private String cmptype;
	@JsonProperty("cmpno")
	private String cmpno;
	@JsonProperty("moudle")
	private String moudle;
	@JsonProperty("pageCount")
	private Integer pageCount;
	
	@JsonProperty("pageNumber")
	private Integer pageNumber;

	@JsonProperty("cmptype")
	public String getCmptype() {
		return cmptype;
	}

	@JsonProperty("cmptype")
	public void setCmptype(String cmptype) {
		this.cmptype = cmptype;
	}

	@JsonProperty("cmpno")
	public String getCmpno() {
		return cmpno;
	}

	@JsonProperty("cmpno")
	public void setCmpno(String cmpno) {
		this.cmpno = cmpno;
	}

	@JsonProperty("moudle")
	public String getMoudle() {
		return moudle;
	}

	@JsonProperty("moudle")
	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}

	@JsonProperty("pageCount")
	public Integer getPageCount() {
		return pageCount;
	}
	
	@JsonProperty("pageCount")
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	@JsonProperty("pageNumber")
	public Integer getPageNumber() {
		return pageNumber;
	}

	@JsonProperty("pageNumber")
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	

}