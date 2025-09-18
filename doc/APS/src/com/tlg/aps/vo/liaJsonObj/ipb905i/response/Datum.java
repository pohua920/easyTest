package com.tlg.aps.vo.liaJsonObj.ipb905i.response;

import java.io.Serializable;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cmptype", "cmpno", "idno", "birdate", "insno",
		"insclass", "inskind", "insitem", "mancon"})
@Generated("jsonschema2pojo")
public class Datum implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("cmptype")
	private String cmptype;
	@JsonProperty("cmpno")
	private String cmpno;
	@JsonProperty("idno")
	private String idno;
	@JsonProperty("birdate")
	private String birdate;
	@JsonProperty("insno")
	private String insno;
	@JsonProperty("insclass")
	private String insclass;
	@JsonProperty("inskind")
	private String inskind;
	@JsonProperty("insitem")
	private String insitem;
	@JsonProperty("mancon")
	private String mancon;

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

	@JsonProperty("idno")
	public String getIdno() {
		return idno;
	}

	@JsonProperty("idno")
	public void setIdno(String idno) {
		this.idno = idno;
	}

	@JsonProperty("birdate")
	public String getBirdate() {
		return birdate;
	}

	@JsonProperty("birdate")
	public void setBirdate(String birdate) {
		this.birdate = birdate;
	}

	@JsonProperty("insno")
	public String getInsno() {
		return insno;
	}

	@JsonProperty("insno")
	public void setInsno(String insno) {
		this.insno = insno;
	}

	@JsonProperty("insclass")
	public String getInsclass() {
		return insclass;
	}

	@JsonProperty("insclass")
	public void setInsclass(String insclass) {
		this.insclass = insclass;
	}

	@JsonProperty("inskind")
	public String getInskind() {
		return inskind;
	}

	@JsonProperty("inskind")
	public void setInskind(String inskind) {
		this.inskind = inskind;
	}

	@JsonProperty("insitem")
	public String getInsitem() {
		return insitem;
	}

	@JsonProperty("insitem")
	public void setInsitem(String insitem) {
		this.insitem = insitem;
	}

	@JsonProperty("mancon")
	public String getMancon() {
		return mancon;
	}

	@JsonProperty("mancon")
	public void setMancon(String mancon) {
		this.mancon = mancon;
	}
}
