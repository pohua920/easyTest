package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
 */
@XmlRootElement
public class RiskC1{
	private String damageTypeCode;	
	private String clauseType;

	private String countryFlag;	
	private Integer estimateLoss;	
	private String lossName;
	public String getDamageTypeCode() {
		return damageTypeCode;
	}
	public void setDamageTypeCode(String damageTypeCode) {
		this.damageTypeCode = damageTypeCode;
	}
	public String getClauseType() {
		return clauseType;
	}
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}
	public String getCountryFlag() {
		return countryFlag;
	}
	public void setCountryFlag(String countryFlag) {
		this.countryFlag = countryFlag;
	}
	public Integer getEstimateLoss() {
		return estimateLoss;
	}
	public void setEstimateLoss(Integer estimateLoss) {
		this.estimateLoss = estimateLoss;
	}
	public String getLossName() {
		return lossName;
	}
	public void setLossName(String lossName) {
		this.lossName = lossName;
	}
	
}
