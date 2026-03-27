package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpCcarDriver
 */
@Entity
@Table(name = "PRPCCARDRIVER")
public class PrpCopyCarDriver implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCopyCarDriverId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性标的序号 */
	private Integer itemNo;

	/** 属性驾驶证号码 */
	private String drivingLicenseNo;

	/** 属性是否固定驾驶员标志 */
	private String changelessFlag;

	/** 属性驾驶员姓名 */
	private String driverName;

	/** 属性身份证号码 */
	private String identifynumber;

	/** 属性性别 */
	private String sex;

	/** 属性年龄 */
	private Integer age;

	/** 属性婚姻状况 */
	private String marriage;

	/** 属性单位或地址 */
	private String driverAddress;

	/** 属性工作单位性质代码 */
	private String possessnature;

	/** 属性从业类别代码 */
	private String businessSource;

	/** 属性是否有违章扣分 */
	private Integer peccancy;

	/** 属性初次领证日期 */
	private Date acceptLicenseDate;

	/** 属性领驾驶证年数 */
	private Integer receivelicenseyear;

	/** 属性驾龄 */
	private Integer drivingYears;

	/** 属性近两年肇事次数D */
	private Integer causetroubletimes;

	/** 属性颁证机关 */
	private String awardLicenseOrgan;

	/** 属性准驾车型 */
	private String drivingCarType;

	/** 属性APPLIYEARTYPE */
	private String appliYearType;

	/** 属性FLAG */
	private String flag;

	/** 属性LICENSESTATUSCODE */
	private String licensestatuscode;

	/**
	 * 类PrpCcarDriver的默认构造方法
	 */
	public PrpCopyCarDriver() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "endorseNo", column = @Column(name = "ENDORSENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpCopyCarDriverId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCopyCarDriverId id) {
		this.id = id;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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
	 * 属性标的序号的getter方法
	 */

	@Column(name = "ITEMNO")
	public Integer getItemNo() {
		return this.itemNo;
	}

	/**
	 * 属性标的序号的setter方法
	 */
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 属性驾驶证号码的getter方法
	 */

	@Column(name = "DRIVINGLICENSENO")
	public String getDrivingLicenseNo() {
		return this.drivingLicenseNo;
	}

	/**
	 * 属性驾驶证号码的setter方法
	 */
	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	/**
	 * 属性是否固定驾驶员标志的getter方法
	 */

	@Column(name = "CHANGELESSFLAG")
	public String getChangelessFlag() {
		return this.changelessFlag;
	}

	/**
	 * 属性是否固定驾驶员标志的setter方法
	 */
	public void setChangelessFlag(String changelessFlag) {
		this.changelessFlag = changelessFlag;
	}

	/**
	 * 属性驾驶员姓名的getter方法
	 */

	@Column(name = "DRIVERNAME")
	public String getDriverName() {
		return this.driverName;
	}

	/**
	 * 属性驾驶员姓名的setter方法
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 属性身份证号码的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifynumber() {
		return this.identifynumber;
	}

	/**
	 * 属性身份证号码的setter方法
	 */
	public void setIdentifynumber(String identifynumber) {
		this.identifynumber = identifynumber;
	}

	/**
	 * 属性性别的getter方法
	 */

	@Column(name = "SEX")
	public String getSex() {
		return this.sex;
	}

	/**
	 * 属性性别的setter方法
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 属性年龄的getter方法
	 */

	@Column(name = "AGE")
	public Integer getAge() {
		return this.age;
	}

	/**
	 * 属性年龄的setter方法
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * 属性婚姻状况的getter方法
	 */

	@Column(name = "MARRIAGE")
	public String getMarriage() {
		return this.marriage;
	}

	/**
	 * 属性婚姻状况的setter方法
	 */
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	/**
	 * 属性单位或地址的getter方法
	 */

	@Column(name = "DRIVERADDRESS")
	public String getDriverAddress() {
		return this.driverAddress;
	}

	/**
	 * 属性单位或地址的setter方法
	 */
	public void setDriverAddress(String driverAddress) {
		this.driverAddress = driverAddress;
	}

	/**
	 * 属性工作单位性质代码的getter方法
	 */

	@Column(name = "POSSESSNATURE")
	public String getPossessnature() {
		return this.possessnature;
	}

	/**
	 * 属性工作单位性质代码的setter方法
	 */
	public void setPossessnature(String possessnature) {
		this.possessnature = possessnature;
	}

	/**
	 * 属性从业类别代码的getter方法
	 */

	@Column(name = "BUSINESSSOURCE")
	public String getBusinessSource() {
		return this.businessSource;
	}

	/**
	 * 属性从业类别代码的setter方法
	 */
	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	/**
	 * 属性是否有违章扣分的getter方法
	 */

	@Column(name = "PECCANCY")
	public Integer getPeccancy() {
		return this.peccancy;
	}

	/**
	 * 属性是否有违章扣分的setter方法
	 */
	public void setPeccancy(Integer peccancy) {
		this.peccancy = peccancy;
	}

	/**
	 * 属性初次领证日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ACCEPTLICENSEDATE")
	public Date getAcceptLicenseDate() {
		return this.acceptLicenseDate;
	}

	/**
	 * 属性初次领证日期的setter方法
	 */
	public void setAcceptLicenseDate(Date acceptLicenseDate) {
		this.acceptLicenseDate = acceptLicenseDate;
	}

	/**
	 * 属性领驾驶证年数的getter方法
	 */

	@Column(name = "RECEIVELICENSEYEAR")
	public Integer getReceivelicenseyear() {
		return this.receivelicenseyear;
	}

	/**
	 * 属性领驾驶证年数的setter方法
	 */
	public void setReceivelicenseyear(Integer receivelicenseyear) {
		this.receivelicenseyear = receivelicenseyear;
	}

	/**
	 * 属性驾龄的getter方法
	 */

	@Column(name = "DRIVINGYEARS")
	public Integer getDrivingYears() {
		return this.drivingYears;
	}

	/**
	 * 属性驾龄的setter方法
	 */
	public void setDrivingYears(Integer drivingYears) {
		this.drivingYears = drivingYears;
	}

	/**
	 * 属性近两年肇事次数D的getter方法
	 */

	@Column(name = "CAUSETROUBLETIMES")
	public Integer getCausetroubletimes() {
		return this.causetroubletimes;
	}

	/**
	 * 属性近两年肇事次数D的setter方法
	 */
	public void setCausetroubletimes(Integer causetroubletimes) {
		this.causetroubletimes = causetroubletimes;
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
	 * 属性APPLIYEARTYPE的getter方法
	 */

	@Column(name = "APPLIYEARTYPE")
	public String getAppliYearType() {
		return this.appliYearType;
	}

	/**
	 * 属性APPLIYEARTYPE的setter方法
	 */
	public void setAppliYearType(String appliYearType) {
		this.appliYearType = appliYearType;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性LICENSESTATUSCODE的getter方法
	 */

	@Column(name = "LICENSESTATUSCODE")
	public String getLicensestatuscode() {
		return this.licensestatuscode;
	}

	/**
	 * 属性LICENSESTATUSCODE的setter方法
	 */
	public void setLicensestatuscode(String licensestatuscode) {
		this.licensestatuscode = licensestatuscode;
	}

}
