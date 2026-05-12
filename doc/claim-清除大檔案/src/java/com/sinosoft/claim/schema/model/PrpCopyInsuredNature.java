// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.claim.schema.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpCopyInsuredNature
 */
@Entity
@Table(name = "PrpCopyinsuredNature")
public class PrpCopyInsuredNature implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCopyInsuredNatureId id;

	/** 属性PRPCMAIN */
	private PrpCopyInsured prpCopyInsured;

	/** 属性投保单号 */
    private String policyNo;
    
	/** 属性关系人角色标志 */
	private String insuredFlag;

	/** 属性SEX */
	private String sex;

	/** 属性AGE */
	private Long age;

	/** 属性生日 */
	private Date birthday;

	/** 属性健康 */
	private String health;

	/** 属性工作 */
	private String jobTitle;

	/** 属性loacalworkyears */
	private Long loacalworkyears;

	/** 属性教育 */
	private String education;

	/** 属性总共工作年限 */
	private Long totalWorkYears;

	/** 属性单位 */
	private String unit;

	/** 属性单位电话 */
	private String unitPhoneNumber;

	/** 属性单位地址 */
	private String unitAddress;

	/** 属性单位邮编 */
	private String unitPostCode;

	/** 属性单位类型 */
	private String unitType;

	/** 属性职位等级 */
	private String dutyLevel;

	/** 属性职位类型 */
	private String dutyType;

	/** 属性个人职业代码 */
	private String occupationCode;

	/** 属性房产状况 */
	private String houseProperty;

	/** 属性户口所在地派出所名称 */
	private String localPoliceStation;

	/** 属性住房地址 */
	private String roomAddress;

	/** 属性住房邮编 */
	private String roomPostCode;

	/** 属性个人月收入 */
	private BigDecimal selfMonthIncome;

	/** 属性家庭月收入 */
	private BigDecimal familyMonthIncome;

	/** 属性收入来源 */
	private String incomeSource;

	/** 属性电话 */
	private String roomPhone;

	/** 属性移动电话 */
	private String mobile;

	/** 属性家庭成员数量 */
	private Long familySumQuantity;

	/** 属性婚姻状况 */
	private String marriage;

	/** 属性配偶名 */
	private String spouseName;

	/** 属性配偶生日 */
	private Date spouseborndate;

	/** 属性配偶身份号 */
	private String spouseId;

	/** 属性配偶单位 */
	private String spouseUnit;

	/** 属性配偶工作 */
	private String spouseJobTitle;

	/** 属性配偶单位电话 */
	private String spouseUnitPhone;

	/** 属性短信转存后是否删除标记 */
	private String flag;

	/** 属性体重 */
	private BigDecimal weight;

	/** 属性身高 */
	private Short stature;

	/**
	 * 类PrpCopyInsuredNature的默认构造方法
	 */
	public PrpCopyInsuredNature() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "endorseNo", column = @Column(name = "endorseNo")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpCopyInsuredNatureId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCopyInsuredNatureId id) {
		this.id = id;
	}

	/**
	 * 属性关系人角色标志的getter方法
	 */

	@Column(name = "INSUREDFLAG")
	public String getInsuredFlag() {
		return this.insuredFlag;
	}

	/**
	 * 属性关系人角色标志的setter方法
	 */
	public void setInsuredFlag(String insuredFlag) {
		this.insuredFlag = insuredFlag;
	}

	/**
	 * 属性SEX的getter方法
	 */

	@Column(name = "SEX")
	public String getSex() {
		return this.sex;
	}

	/**
	 * 属性SEX的setter方法
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 属性AGE的getter方法
	 */

	@Column(name = "AGE")
	public Long getAge() {
		return this.age;
	}

	/**
	 * 属性AGE的setter方法
	 */
	public void setAge(Long age) {
		this.age = age;
	}

	/**
	 * 属性生日的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return this.birthday;
	}

	/**
	 * 属性生日的setter方法
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * 属性健康的getter方法
	 */

	@Column(name = "HEALTH")
	public String getHealth() {
		return this.health;
	}

	/**
	 * 属性健康的setter方法
	 */
	public void setHealth(String health) {
		this.health = health;
	}

	/**
	 * 属性工作的getter方法
	 */

	@Column(name = "JOBTITLE")
	public String getJobTitle() {
		return this.jobTitle;
	}

	/**
	 * 属性工作的setter方法
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * 属性loacalworkyears的getter方法
	 */

	@Column(name = "LOACALWORKYEARS")
	public Long getLoacalworkyears() {
		return this.loacalworkyears;
	}

	/**
	 * 属性loacalworkyears的setter方法
	 */
	public void setLoacalworkyears(Long loacalworkyears) {
		this.loacalworkyears = loacalworkyears;
	}

	/**
	 * 属性教育的getter方法
	 */

	@Column(name = "EDUCATION")
	public String getEducation() {
		return this.education;
	}

	/**
	 * 属性教育的setter方法
	 */
	public void setEducation(String education) {
		this.education = education;
	}

	/**
	 * 属性总共工作年限的getter方法
	 */

	@Column(name = "TOTALWORKYEARS")
	public Long getTotalWorkYears() {
		return this.totalWorkYears;
	}

	/**
	 * 属性总共工作年限的setter方法
	 */
	public void setTotalWorkYears(Long totalWorkYears) {
		this.totalWorkYears = totalWorkYears;
	}

	/**
	 * 属性单位的getter方法
	 */

	@Column(name = "UNIT")
	public String getUnit() {
		return this.unit;
	}

	/**
	 * 属性单位的setter方法
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 属性单位电话的getter方法
	 */

	@Column(name = "UNITPHONENUMBER")
	public String getUnitPhoneNumber() {
		return this.unitPhoneNumber;
	}

	/**
	 * 属性单位电话的setter方法
	 */
	public void setUnitPhoneNumber(String unitPhoneNumber) {
		this.unitPhoneNumber = unitPhoneNumber;
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
	 * 属性单位邮编的getter方法
	 */

	@Column(name = "UNITPOSTCODE")
	public String getUnitPostCode() {
		return this.unitPostCode;
	}

	/**
	 * 属性单位邮编的setter方法
	 */
	public void setUnitPostCode(String unitPostCode) {
		this.unitPostCode = unitPostCode;
	}

	/**
	 * 属性单位类型的getter方法
	 */

	@Column(name = "UNITTYPE")
	public String getUnitType() {
		return this.unitType;
	}

	/**
	 * 属性单位类型的setter方法
	 */
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	/**
	 * 属性职位等级的getter方法
	 */

	@Column(name = "DUTYLEVEL")
	public String getDutyLevel() {
		return this.dutyLevel;
	}

	/**
	 * 属性职位等级的setter方法
	 */
	public void setDutyLevel(String dutyLevel) {
		this.dutyLevel = dutyLevel;
	}

	/**
	 * 属性职位类型的getter方法
	 */

	@Column(name = "DUTYTYPE")
	public String getDutyType() {
		return this.dutyType;
	}

	/**
	 * 属性职位类型的setter方法
	 */
	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}

	/**
	 * 属性个人职业代码的getter方法
	 */

	@Column(name = "OCCUPATIONCODE")
	public String getOccupationCode() {
		return this.occupationCode;
	}

	/**
	 * 属性个人职业代码的setter方法
	 */
	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}

	/**
	 * 属性房产状况的getter方法
	 */

	@Column(name = "HOUSEPROPERTY")
	public String getHouseProperty() {
		return this.houseProperty;
	}

	/**
	 * 属性房产状况的setter方法
	 */
	public void setHouseProperty(String houseProperty) {
		this.houseProperty = houseProperty;
	}

	/**
	 * 属性户口所在地派出所名称的getter方法
	 */

	@Column(name = "LOCALPOLICESTATION")
	public String getLocalPoliceStation() {
		return this.localPoliceStation;
	}

	/**
	 * 属性户口所在地派出所名称的setter方法
	 */
	public void setLocalPoliceStation(String localPoliceStation) {
		this.localPoliceStation = localPoliceStation;
	}

	/**
	 * 属性住房地址的getter方法
	 */

	@Column(name = "ROOMADDRESS")
	public String getRoomAddress() {
		return this.roomAddress;
	}

	/**
	 * 属性住房地址的setter方法
	 */
	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}

	/**
	 * 属性住房邮编的getter方法
	 */

	@Column(name = "ROOMPOSTCODE")
	public String getRoomPostCode() {
		return this.roomPostCode;
	}

	/**
	 * 属性住房邮编的setter方法
	 */
	public void setRoomPostCode(String roomPostCode) {
		this.roomPostCode = roomPostCode;
	}

	/**
	 * 属性个人月收入的getter方法
	 */

	@Column(name = "SELFMONTHINCOME")
	public BigDecimal getSelfMonthIncome() {
		return this.selfMonthIncome;
	}

	/**
	 * 属性个人月收入的setter方法
	 */
	public void setSelfMonthIncome(BigDecimal selfMonthIncome) {
		this.selfMonthIncome = selfMonthIncome;
	}

	/**
	 * 属性家庭月收入的getter方法
	 */

	@Column(name = "FAMILYMONTHINCOME")
	public BigDecimal getFamilyMonthIncome() {
		return this.familyMonthIncome;
	}

	/**
	 * 属性家庭月收入的setter方法
	 */
	public void setFamilyMonthIncome(BigDecimal familyMonthIncome) {
		this.familyMonthIncome = familyMonthIncome;
	}

	/**
	 * 属性收入来源的getter方法
	 */

	@Column(name = "INCOMESOURCE")
	public String getIncomeSource() {
		return this.incomeSource;
	}

	/**
	 * 属性收入来源的setter方法
	 */
	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}

	/**
	 * 属性电话的getter方法
	 */

	@Column(name = "ROOMPHONE")
	public String getRoomPhone() {
		return this.roomPhone;
	}

	/**
	 * 属性电话的setter方法
	 */
	public void setRoomPhone(String roomPhone) {
		this.roomPhone = roomPhone;
	}

	/**
	 * 属性移动电话的getter方法
	 */

	@Column(name = "MOBILE")
	public String getMobile() {
		return this.mobile;
	}

	/**
	 * 属性移动电话的setter方法
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 属性家庭成员数量的getter方法
	 */

	@Column(name = "FAMILYSUMQUANTITY")
	public Long getFamilySumQuantity() {
		return this.familySumQuantity;
	}

	/**
	 * 属性家庭成员数量的setter方法
	 */
	public void setFamilySumQuantity(Long familySumQuantity) {
		this.familySumQuantity = familySumQuantity;
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
	 * 属性配偶名的getter方法
	 */

	@Column(name = "SPOUSENAME")
	public String getSpouseName() {
		return this.spouseName;
	}

	/**
	 * 属性配偶名的setter方法
	 */
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	/**
	 * 属性配偶生日的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SPOUSEBORNDATE")
	public Date getSpouseborndate() {
		return this.spouseborndate;
	}

	/**
	 * 属性配偶生日的setter方法
	 */
	public void setSpouseborndate(Date spouseborndate) {
		this.spouseborndate = spouseborndate;
	}

	/**
	 * 属性配偶身份号的getter方法
	 */

	@Column(name = "SPOUSEID")
	public String getSpouseId() {
		return this.spouseId;
	}

	/**
	 * 属性配偶身份号的setter方法
	 */
	public void setSpouseId(String spouseId) {
		this.spouseId = spouseId;
	}

	/**
	 * 属性配偶单位的getter方法
	 */

	@Column(name = "SPOUSEUNIT")
	public String getSpouseUnit() {
		return this.spouseUnit;
	}

	/**
	 * 属性配偶单位的setter方法
	 */
	public void setSpouseUnit(String spouseUnit) {
		this.spouseUnit = spouseUnit;
	}

	/**
	 * 属性配偶工作的getter方法
	 */

	@Column(name = "SPOUSEJOBTITLE")
	public String getSpouseJobTitle() {
		return this.spouseJobTitle;
	}

	/**
	 * 属性配偶工作的setter方法
	 */
	public void setSpouseJobTitle(String spouseJobTitle) {
		this.spouseJobTitle = spouseJobTitle;
	}

	/**
	 * 属性配偶单位电话的getter方法
	 */

	@Column(name = "SPOUSEUNITPHONE")
	public String getSpouseUnitPhone() {
		return this.spouseUnitPhone;
	}

	/**
	 * 属性配偶单位电话的setter方法
	 */
	public void setSpouseUnitPhone(String spouseUnitPhone) {
		this.spouseUnitPhone = spouseUnitPhone;
	}

	/**
	 * 属性短信转存后是否删除标记的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性短信转存后是否删除标记的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性体重的getter方法
	 */

	@Column(name = "WEIGHT")
	public BigDecimal getWeight() {
		return this.weight;
	}

	/**
	 * 属性体重的setter方法
	 */
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	/**
	 * 属性身高的getter方法
	 */

	@Column(name = "STATURE")
	public Short getStature() {
		return this.stature;
	}

	/**
	 * 属性身高的setter方法
	 */
	public void setStature(Short stature) {
		this.stature = stature;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "endorseNo", referencedColumnName = "endorseNo", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "serialno", referencedColumnName = "serialno", nullable = false, insertable = false, updatable = false) })
	public PrpCopyInsured getPrpCopyInsured() {
		return prpCopyInsured;
	}

	public void setPrpCopyInsured(PrpCopyInsured prpCopyInsured) {
		this.prpCopyInsured = prpCopyInsured;
	}
	
	@Column(name = "policyNo")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

}
