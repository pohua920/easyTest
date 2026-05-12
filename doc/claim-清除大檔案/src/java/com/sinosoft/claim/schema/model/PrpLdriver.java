package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLdriver车险驾驶员信息表
 */
@Entity
@Table(name = "PRPLDRIVER")
public class PrpLdriver implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLdriverId id;

	/** 属性报案信息表 */
	private PrpLregist prpLregist;

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
	private Date receiveLicenseDate;

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
	private Date birthday;

	/** 属性显示列表 */
	private List<PrpLdriver> driverList = new ArrayList<PrpLdriver>();

	/**
	 * 类PrpLdriver的默认构造方法
	 */
	public PrpLdriver() {
		id = new PrpLdriverId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLdriverId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLdriverId id) {
		this.id = id;
	}

	/**
	 * 属性报案信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTNO", nullable = false, insertable = false, updatable = false)
	public PrpLregist getPrpLregist() {
		return this.prpLregist;
	}

	/**
	 * 属性报案信息表的setter方法
	 */
	public void setPrpLregist(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
	}

	/**
	 * 属性立案号码的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号码的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性车牌号的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性车牌底色代码的getter方法
	 */

	@Column(name = "LICENSECOLORCODE")
	public String getLicenseColorCode() {
		return this.licenseColorCode;
	}

	/**
	 * 属性车牌底色代码的setter方法
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	/**
	 * 属性(当班) 驾驶员驾驶证号码的getter方法
	 */

	@Column(name = "DRIVINGLICENSENO")
	public String getDrivingLicenseNo() {
		return this.drivingLicenseNo;
	}

	/**
	 * 属性(当班) 驾驶员驾驶证号码的setter方法
	 */
	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	/**
	 * 属性(当班)驾驶员姓名的getter方法
	 */

	@Column(name = "DRIVERNAME")
	public String getDriverName() {
		return this.driverName;
	}

	/**
	 * 属性(当班)驾驶员姓名的setter方法
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 属性(当班)驾驶员性别的getter方法
	 */

	@Column(name = "DRIVERSEX")
	public String getDriverSex() {
		return this.driverSex;
	}

	/**
	 * 属性(当班)驾驶员性别的setter方法
	 */
	public void setDriverSex(String driverSex) {
		this.driverSex = driverSex;
	}

	/**
	 * 属性(当班)驾驶员年龄的getter方法
	 */

	@Column(name = "DRIVERAGE")
	public BigDecimal getDriverAge() {
		return this.driverAge;
	}

	/**
	 * 属性(当班)驾驶员年龄的setter方法
	 */
	public void setDriverAge(BigDecimal driverAge) {
		this.driverAge = driverAge;
	}

	/**
	 * 属性(当班) 驾驶员职业的getter方法
	 */

	@Column(name = "DRIVEROCCUPATION")
	public String getDriverOccupation() {
		return this.driverOccupation;
	}

	/**
	 * 属性(当班) 驾驶员职业的setter方法
	 */
	public void setDriverOccupation(String driverOccupation) {
		this.driverOccupation = driverOccupation;
	}

	/**
	 * 属性文化程度的getter方法
	 */

	@Column(name = "EDUCATION")
	public String getEducation() {
		return this.education;
	}

	/**
	 * 属性文化程度的setter方法
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * 属性单位地址的getter方法
	 */

	@Column(name = "UNITADDRESS")
	public String getUnitAddress() {
		return this.unitAddress;
	}

	/**
	 * 属性单位地址的setter方法
	 */
	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
	}

	/**
	 * 属性IdentifyNumber的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性IdentifyNumber的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性(当班) 驾驶员等级的getter方法
	 */

	@Column(name = "DRIVERGRADE")
	public BigDecimal getDriverGrade() {
		return this.driverGrade;
	}

	/**
	 * 属性(当班) 驾驶员等级的setter方法
	 */
	public void setDriverGrade(BigDecimal driverGrade) {
		this.driverGrade = driverGrade;
	}

	/**
	 * 属性(当班) 驾驶员航线的getter方法
	 */

	@Column(name = "DRIVERSEAROUTE")
	public String getDriverSeaRoute() {
		return this.driverSeaRoute;
	}

	/**
	 * 属性(当班) 驾驶员航线的setter方法
	 */
	public void setDriverSeaRoute(String driverSeaRoute) {
		this.driverSeaRoute = driverSeaRoute;
	}

	/**
	 * 属性(当班) 驾驶员领证时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RECEIVELICENSEDATE")
	public Date getReceiveLicenseDate() {
		return this.receiveLicenseDate;
	}

	/**
	 * 属性(当班) 驾驶员领证时间的setter方法
	 */
	public void setReceiveLicenseDate(Date receiveLicenseDate) {
		this.receiveLicenseDate = receiveLicenseDate;
	}

	/**
	 * 属性准驾车型的getter方法
	 */

	@Column(name = "DRIVINGCARTYPE")
	public String getDrivingCarType() {
		return this.drivingCarType;
	}

	/**
	 * 属性准驾车型的setter方法
	 */
	public void setDrivingCarType(String drivingCarType) {
		this.drivingCarType = drivingCarType;
	}

	/**
	 * 属性颁证机关的getter方法
	 */

	@Column(name = "AWARDLICENSEORGAN")
	public String getAwardLicenseOrgan() {
		return this.awardLicenseOrgan;
	}

	/**
	 * 属性颁证机关的setter方法
	 */
	public void setAwardLicenseOrgan(String awardLicenseOrgan) {
		this.awardLicenseOrgan = awardLicenseOrgan;
	}

	/**
	 * 属性有无高速船舶驾驶培训合格证书(0/1)的getter方法
	 */

	@Column(name = "SPECIALCERTIFICATE")
	public String getSpecialCertificate() {
		return this.specialCertificate;
	}

	/**
	 * 属性有无高速船舶驾驶培训合格证书(0/1)的setter方法
	 */
	public void setSpecialCertificate(String specialCertificate) {
		this.specialCertificate = specialCertificate;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性驾驶员联系电话的getter方法
	 */

	@Column(name = "DRIVERPHONE")
	public String getDriverPhone() {
		return this.driverPhone;
	}

	/**
	 * 属性驾驶员联系电话的setter方法
	 */
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}

	/**
	 * 属性驾驶员属地代码的getter方法
	 */

	@Column(name = "DRIVERAPANAGECODE")
	public String getDriverApanageCode() {
		return this.driverApanageCode;
	}

	/**
	 * 属性驾驶员属地代码的setter方法
	 */
	public void setDriverApanageCode(String driverApanageCode) {
		this.driverApanageCode = driverApanageCode;
	}

	/**
	 * 属性驾驶员属地的getter方法
	 */

	@Column(name = "DRIVERAPANAGE")
	public String getDriverApanage() {
		return this.driverApanage;
	}

	/**
	 * 属性驾驶员属地的setter方法
	 */
	public void setDriverApanage(String driverApanage) {
		this.driverApanage = driverApanage;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public List<PrpLdriver> getDriverList() {
		return driverList;
	}

	/**
	 * 设置列表
	 * @param driverList 待设置的列表
	 */
	public void setDriverList(List<PrpLdriver> driverList) {
		this.driverList = driverList;
	}

	@Column(name = "ISMARRIED")
	public String getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(String isMarried) {
		this.isMarried = isMarried;
	}

	@Column(name = "MOBILEPHONE")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "DRIVERIDENTITY")
	public String getDriverIdentity() {
		return driverIdentity;
	}

	public void setDriverIdentity(String driverIdentity) {
		this.driverIdentity = driverIdentity;
	}

	@Column(name = "DRIVERDISTRICT")
	public String getDriverDistrict() {
		return driverDistrict;
	}

	public void setDriverDistrict(String driverDistrict) {
		this.driverDistrict = driverDistrict;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "DRIVINGYEAR")
	public Integer getDrivingYear() {
		return drivingYear;
	}

	public void setDrivingYear(Integer drivingYear) {
		this.drivingYear = drivingYear;
	}

}
