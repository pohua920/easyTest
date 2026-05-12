package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@XmlRootElement
public class PersonTrace {
	
	private String personName;
	private String personSex;
	private String identifyNumber;
	private String idNumberType;
	private String rideSituation;
	private String licenseNo;
	private String applicantBirthday;
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPersonSex() {
		return personSex;
	}
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}
	public String getIdNumberType() {
		return idNumberType;
	}
	public void setIdNumberType(String idNumberType) {
		this.idNumberType = idNumberType;
	}
	public String getRideSituation() {
		return rideSituation;
	}
	public void setRideSituation(String rideSituation) {
		this.rideSituation = rideSituation;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getApplicantBirthday() {
		return applicantBirthday;
	}
	public void setApplicantBirthday(String applicantBirthday) {
		this.applicantBirthday = applicantBirthday;
	}
	
	
}
