package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@XmlRootElement
public class Driver {
	
	private String driverName;
	private String driverSex;
	private String driverIsMarried;
	private String driverBirthday;
	private String driverIdentityNumber;
	private String driverPhone;
	private String driverMobilePhone;
	private String driverIdentity;
	private String driverDistrict;
	
	//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
	private String driverLicenseNo; 
	
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverSex() {
		return driverSex;
	}
	public void setDriverSex(String driverSex) {
		this.driverSex = driverSex;
	}
	public String getDriverIsMarried() {
		return driverIsMarried;
	}
	public void setDriverIsMarried(String driverIsMarried) {
		this.driverIsMarried = driverIsMarried;
	}
	public String getDriverBirthday() {
		return driverBirthday;
	}
	public void setDriverBirthday(String driverBirthday) {
		this.driverBirthday = driverBirthday;
	}
	public String getDriverIdentityNumber() {
		return driverIdentityNumber;
	}
	public void setDriverIdentityNumber(String driverIdentityNumber) {
		this.driverIdentityNumber = driverIdentityNumber;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getDriverMobilePhone() {
		return driverMobilePhone;
	}
	public void setDriverMobilePhone(String driverMobilePhone) {
		this.driverMobilePhone = driverMobilePhone;
	}
	public String getDriverIdentity() {
		return driverIdentity;
	}
	public void setDriverIdentity(String driverIdentity) {
		this.driverIdentity = driverIdentity;
	}
	public String getDriverDistrict() {
		return driverDistrict;
	}
	public void setDriverDistrict(String driverDistrict) {
		this.driverDistrict = driverDistrict;
	}
	//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種	START
	public String getDriverLicenseNo() {
		return driverLicenseNo;
	}
	public void setDriverLicenseNo(String driverLicenseNo) {
		this.driverLicenseNo = driverLicenseNo;
	}
	//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種	END
	
}
