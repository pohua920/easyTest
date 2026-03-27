package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *  * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@XmlRootElement
public class ClaimPrpLdriverVo {
	/** 属性id */
	private String id;

	/** 属性报案信息表 */
//	private PrpLregist prpLregist;

	/** 属性立案号码 */
	private String claimNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号 */
	private String policyNo;

	/** 属性车牌号 */
	private String licenseNo;

	/** 属性车牌底色代码 */
	private String licenseColorCode;

	/** 属性(当班) 驾驶员驾驶证号码 */
	private String drivingLicenseNo;

	/** 属性(当班)驾驶员姓名 */
	private String driverName;

	/** 属性(当班)驾驶员性别 */
	private String driverSex;

	/** 属性(当班)驾驶员年龄 */
	private BigDecimal driverAge;

	/** 属性(当班) 驾驶员职业 */
	private String driverOccupation;

	/** 属性文化程度 */
	private String education;

	/** 属性单位地址 */
	private String unitAddress;

	/** 属性身份证号码 */
	private String identifyNumber;

	/** 属性(当班) 驾驶员等级 */
	private BigDecimal driverGrade;

	/** 属性(当班) 驾驶员航线 */
	private String driverSeaRoute;

	/** 属性(当班) 驾驶员领证时间 */
	private String receiveLicenseDate;

	/** 属性准驾车型 */
	private String drivingCarType;

	/** 属性颁证机关 */
	private String awardLicenseOrgan;

	/** 属性有无高速船舶驾驶培训合格证书(0/1) */
	private String specialCertificate;

	/** 属性状态字段 */
	private String flag;

	/** 属性驾驶员联系电话 */
	private String driverPhone;

	/** 属性驾驶员属地代码 */
	private String driverApanageCode;

	/** 属性驾驶员属地 */
	private String driverApanage;

	/** 婚姻別 */
	private String isMarried = "";

	/** 手機號碼 */
	private String mobilePhone = "";

	/** 属性(当班) 驾驶员驾驶年限 */
	private Integer drivingYear;

	/** 駕駛人身份 */
	private String driverIdentity = "";

	/** 駕駛人區別 */
	private String driverDistrict = "";

	/** 属性年龄 */
	private String birthday;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getLicenseColorCode() {
		return licenseColorCode;
	}

	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}

	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

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

	public BigDecimal getDriverAge() {
		return driverAge;
	}

	public void setDriverAge(BigDecimal driverAge) {
		this.driverAge = driverAge;
	}

	public String getDriverOccupation() {
		return driverOccupation;
	}

	public void setDriverOccupation(String driverOccupation) {
		this.driverOccupation = driverOccupation;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	public BigDecimal getDriverGrade() {
		return driverGrade;
	}

	public void setDriverGrade(BigDecimal driverGrade) {
		this.driverGrade = driverGrade;
	}

	public String getDriverSeaRoute() {
		return driverSeaRoute;
	}

	public void setDriverSeaRoute(String driverSeaRoute) {
		this.driverSeaRoute = driverSeaRoute;
	}

	public String getDrivingCarType() {
		return drivingCarType;
	}

	public void setDrivingCarType(String drivingCarType) {
		this.drivingCarType = drivingCarType;
	}

	public String getAwardLicenseOrgan() {
		return awardLicenseOrgan;
	}

	public void setAwardLicenseOrgan(String awardLicenseOrgan) {
		this.awardLicenseOrgan = awardLicenseOrgan;
	}

	public String getSpecialCertificate() {
		return specialCertificate;
	}

	public void setSpecialCertificate(String specialCertificate) {
		this.specialCertificate = specialCertificate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	public String getDriverApanageCode() {
		return driverApanageCode;
	}

	public void setDriverApanageCode(String driverApanageCode) {
		this.driverApanageCode = driverApanageCode;
	}

	public String getDriverApanage() {
		return driverApanage;
	}

	public void setDriverApanage(String driverApanage) {
		this.driverApanage = driverApanage;
	}

	public String getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(String isMarried) {
		this.isMarried = isMarried;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getDrivingYear() {
		return drivingYear;
	}

	public void setDrivingYear(Integer drivingYear) {
		this.drivingYear = drivingYear;
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

	public String getReceiveLicenseDate() {
		return receiveLicenseDate;
	}

	public void setReceiveLicenseDate(String receiveLicenseDate) {
		this.receiveLicenseDate = receiveLicenseDate;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	
}
	