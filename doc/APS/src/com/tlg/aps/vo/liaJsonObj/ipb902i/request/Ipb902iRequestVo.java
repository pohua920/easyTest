package com.tlg.aps.vo.liaJsonObj.ipb902i.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "reqMode", "convenantType", "cprodName", "cinsKindName",
		"celectronicType", "cmptype", "cmpno", "idno", "birdate", "insno", "insclass", "inskind",
		"insitem" })
public class Ipb902iRequestVo implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonProperty("reqMode")
	private String reqMode;
	@JsonProperty("convenantType")
	private String convenantType;
	@JsonProperty("cprodName")
	private String cprodName;
	@JsonProperty("cinsKindName")
	private String cinsKindName;
	@JsonProperty("celectronicType")
	private String celectronicType;
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

	@JsonProperty("reqMode")
	public String getReqMode() {
		return reqMode;
	}

	@JsonProperty("reqMode")
	public void setReqMode(String reqMode) {
		this.reqMode = reqMode;
	}

	@JsonProperty("convenantType")
	public String getConvenantType() {
		return convenantType;
	}

	@JsonProperty("convenantType")
	public void setConvenantType(String convenantType) {
		this.convenantType = convenantType;
	}

	@JsonProperty("cprodName")
	public String getCprodName() {
		return cprodName;
	}

	@JsonProperty("cprodName")
	public void setCprodName(String cprodName) {
		this.cprodName = cprodName;
	}

	@JsonProperty("cinsKindName")
	public String getCinsKindName() {
		return cinsKindName;
	}

	@JsonProperty("cinsKindName")
	public void setCinsKindName(String cinsKindName) {
		this.cinsKindName = cinsKindName;
	}

	@JsonProperty("celectronicType")
	public String getCelectronicType() {
		return celectronicType;
	}

	@JsonProperty("celectronicType")
	public void setCelectronicType(String celectronicType) {
		this.celectronicType = celectronicType;
	}

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

}