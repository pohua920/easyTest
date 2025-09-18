package com.tlg.aps.webService.claimBlockChainService.client.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * mantis：CLM0168，處理人員：BI086，需求單編號：CLM0168  區塊鏈查詢、新增及更新攤賠案件排程
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompulsoryQueryVo {
	
	/**
	 * 事故時間 ex: 2022-03-30T22:33:00.835Z
	 */
	@JsonProperty(value="hit_time")
	private Date hitTime;
	/**
	 * Applicant ID number 身分證字號
	 */
	@JsonProperty(value="id_number")
	private String idNumber;
	/**
	 * IdNumberType 身分證字號類型
	 * Available values : ID_NUMBER, ARC_NUMBER, PASSPORT_NUMBER
	 */
	@JsonProperty(value="id_number_type")
	private String idNumberType;

	public Date getHitTime() {
		return hitTime;
	}

	public void setHitTime(Date hitTime) {
		this.hitTime = hitTime;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdNumberType() {
		return idNumberType;
	}

	public void setIdNumberType(String idNumberType) {
		this.idNumberType = idNumberType;
	}

}
