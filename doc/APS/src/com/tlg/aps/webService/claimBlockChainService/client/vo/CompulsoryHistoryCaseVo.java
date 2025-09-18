package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "same_hour", "case_type", "extension", "phone",
		"person_in_charge", "apportion_length", "id_number", "case_number",
		"status", "msp_id", "hit_time", "last_apportion_id", "case_id" })

public class CompulsoryHistoryCaseVo {

	@JsonProperty("same_hour")
	private Boolean sameHour;
	@JsonProperty("case_type")
	private String caseType;
	@JsonProperty("extension")
	private String extension;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("person_in_charge")
	private String personInCharge;
	@JsonProperty("apportion_length")
	private BigDecimal apportionLength;
	@JsonProperty("id_number")
	private String idNumber;
	@JsonProperty("case_number")
	private String caseNumber;
	@JsonProperty("status")
	private String status;
	@JsonProperty("msp_id")
	private String mspId;
	@JsonProperty("hit_time")
	private String hitTime;
	@JsonProperty("last_apportion_id")
	private BigDecimal lastApportionId;
	@JsonProperty("case_id")
	private BigDecimal caseId;

	public Boolean getSameHour() {
		return sameHour;
	}

	public void setSameHour(Boolean sameHour) {
		this.sameHour = sameHour;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public BigDecimal getApportionLength() {
		return apportionLength;
	}

	public void setApportionLength(BigDecimal apportionLength) {
		this.apportionLength = apportionLength;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMspId() {
		return mspId;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

	public String getHitTime() {
		return hitTime;
	}

	public void setHitTime(String hitTime) {
		this.hitTime = hitTime;
	}

	public BigDecimal getLastApportionId() {
		return lastApportionId;
	}

	public void setLastApportionId(BigDecimal lastApportionId) {
		this.lastApportionId = lastApportionId;
	}

	public BigDecimal getCaseId() {
		return caseId;
	}

	public void setCaseId(BigDecimal caseId) {
		this.caseId = caseId;
	}

}
