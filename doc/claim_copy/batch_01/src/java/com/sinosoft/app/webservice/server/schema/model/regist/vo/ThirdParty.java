package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@XmlRootElement
public class ThirdParty {
	
	private String carKindCode;
	private String licenseNo;
	private String insureComCode;
	private String carryingUnit;
	private String carringNumber;
	private String isInsurance;
	private String insuranceNo;
	private String insuredIdentity;
	private String dutyPercent;
	public String getCarKindCode() {
		return carKindCode;
	}
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getInsureComCode() {
		return insureComCode;
	}
	public void setInsureComCode(String insureComCode) {
		this.insureComCode = insureComCode;
	}
	public String getCarryingUnit() {
		return carryingUnit;
	}
	public void setCarryingUnit(String carryingUnit) {
		this.carryingUnit = carryingUnit;
	}
	public String getCarringNumber() {
		return carringNumber;
	}
	public void setCarringNumber(String carringNumber) {
		this.carringNumber = carringNumber;
	}
	public String getIsInsurance() {
		return isInsurance;
	}
	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}
	public String getInsuranceNo() {
		return insuranceNo;
	}
	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}
	public String getInsuredIdentity() {
		return insuredIdentity;
	}
	public void setInsuredIdentity(String insuredIdentity) {
		this.insuredIdentity = insuredIdentity;
	}
	public String getDutyPercent() {
		return dutyPercent;
	}
	public void setDutyPercent(String dutyPercent) {
		this.dutyPercent = dutyPercent;
	}
	
	
	
}
