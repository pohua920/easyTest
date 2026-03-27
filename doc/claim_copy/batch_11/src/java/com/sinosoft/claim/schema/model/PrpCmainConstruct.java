package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpCmainConstruct
 */
@Entity
@Table(name = "PRPCMAINCONSTRUCT")
public class PrpCmainConstruct implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性工程名称 */
	private String constructName;

	/** 属性工程类别 */
	private String constructType;

	/** 属性工程地点 */
	private String constructAddress;

	/** 属性CONSTRUCTSTRUCT */
	private String constructStruct;

	/** 属性CONSTRUCTAGE */
	private Double constructage;

	/** 属性QUAKEGRADE */
	private String quakeGrade;

	/** 属性风险类别 */
	private String riskKind;

	/** 属性用途 */
	private String purpose;

	/** 属性数量 */
	private Double quantity;

	/** 属性试车起始日期 */
	private Date testStartDate;

	/** 属性试车终止日期 */
	private Date testEndDate;

	/** 属性试车期 */
	private Integer testPeriod;

	/** 属性日期 */
	private Date setDate;

	/** 属性工程开始日期 */
	private Date startFixDate;

	/** 属性工程终止日期 */
	private Date endFixDate;

	/** 属性开始日期 */
	private Date startAddDate;

	/** 属性终止日期 */
	private Date endAddDate;

	/** 属性ADDASSUREMONTH */
	private Integer addAssureMonth;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性TESTTIMEUNIT */
	private String testTimeUnit;

	/** 属性ADDTIMEUNIT */
	private String addTimeUnit;

	/**
	 * 类PrpCmainConstruct的默认构造方法
	 */
	public PrpCmainConstruct() {
	}

	/**
	 * 属性保单号码的getter方法
	 */
	@Id
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
	 * 属性工程名称的getter方法
	 */

	@Column(name = "CONSTRUCTNAME")
	public String getConstructName() {
		return this.constructName;
	}

	/**
	 * 属性工程名称的setter方法
	 */
	public void setConstructName(String constructName) {
		this.constructName = constructName;
	}

	/**
	 * 属性工程类别的getter方法
	 */

	@Column(name = "CONSTRUCTTYPE")
	public String getConstructType() {
		return this.constructType;
	}

	/**
	 * 属性工程类别的setter方法
	 */
	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}

	/**
	 * 属性工程地点的getter方法
	 */

	@Column(name = "CONSTRUCTADDRESS")
	public String getConstructAddress() {
		return this.constructAddress;
	}

	/**
	 * 属性工程地点的setter方法
	 */
	public void setConstructAddress(String constructAddress) {
		this.constructAddress = constructAddress;
	}

	/**
	 * 属性CONSTRUCTSTRUCT的getter方法
	 */

	@Column(name = "CONSTRUCTSTRUCT")
	public String getConstructStruct() {
		return this.constructStruct;
	}

	/**
	 * 属性CONSTRUCTSTRUCT的setter方法
	 */
	public void setConstructStruct(String constructStruct) {
		this.constructStruct = constructStruct;
	}

	/**
	 * 属性CONSTRUCTAGE的getter方法
	 */

	@Column(name = "CONSTRUCTAGE")
	public Double getConstructage() {
		return this.constructage;
	}

	/**
	 * 属性CONSTRUCTAGE的setter方法
	 */
	public void setConstructage(Double constructage) {
		this.constructage = constructage;
	}

	/**
	 * 属性QUAKEGRADE的getter方法
	 */

	@Column(name = "QUAKEGRADE")
	public String getQuakeGrade() {
		return this.quakeGrade;
	}

	/**
	 * 属性QUAKEGRADE的setter方法
	 */
	public void setQuakeGrade(String quakeGrade) {
		this.quakeGrade = quakeGrade;
	}

	/**
	 * 属性风险类别的getter方法
	 */

	@Column(name = "RISKKIND")
	public String getRiskKind() {
		return this.riskKind;
	}

	/**
	 * 属性风险类别的setter方法
	 */
	public void setRiskKind(String riskKind) {
		this.riskKind = riskKind;
	}

	/**
	 * 属性用途的getter方法
	 */

	@Column(name = "PURPOSE")
	public String getPurpose() {
		return this.purpose;
	}

	/**
	 * 属性用途的setter方法
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/**
	 * 属性数量的getter方法
	 */

	@Column(name = "QUANTITY")
	public Double getQuantity() {
		return this.quantity;
	}

	/**
	 * 属性数量的setter方法
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	/**
	 * 属性试车起始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TESTSTARTDATE")
	public Date getTestStartDate() {
		return this.testStartDate;
	}

	/**
	 * 属性试车起始日期的setter方法
	 */
	public void setTestStartDate(Date testStartDate) {
		this.testStartDate = testStartDate;
	}

	/**
	 * 属性试车终止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "TESTENDDATE")
	public Date getTestEndDate() {
		return this.testEndDate;
	}

	/**
	 * 属性试车终止日期的setter方法
	 */
	public void setTestEndDate(Date testEndDate) {
		this.testEndDate = testEndDate;
	}

	/**
	 * 属性试车期的getter方法
	 */

	@Column(name = "TESTPERIOD")
	public Integer getTestPeriod() {
		return this.testPeriod;
	}

	/**
	 * 属性试车期的setter方法
	 */
	public void setTestPeriod(Integer testPeriod) {
		this.testPeriod = testPeriod;
	}

	/**
	 * 属性日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SETDATE")
	public Date getSetDate() {
		return this.setDate;
	}

	/**
	 * 属性日期的setter方法
	 */
	public void setSetDate(Date setDate) {
		this.setDate = setDate;
	}

	/**
	 * 属性工程开始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTFIXDATE")
	public Date getStartFixDate() {
		return this.startFixDate;
	}

	/**
	 * 属性工程开始日期的setter方法
	 */
	public void setStartFixDate(Date startFixDate) {
		this.startFixDate = startFixDate;
	}

	/**
	 * 属性工程终止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDFIXDATE")
	public Date getEndFixDate() {
		return this.endFixDate;
	}

	/**
	 * 属性工程终止日期的setter方法
	 */
	public void setEndFixDate(Date endFixDate) {
		this.endFixDate = endFixDate;
	}

	/**
	 * 属性开始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTADDDATE")
	public Date getStartAddDate() {
		return this.startAddDate;
	}

	/**
	 * 属性开始日期的setter方法
	 */
	public void setStartAddDate(Date startAddDate) {
		this.startAddDate = startAddDate;
	}

	/**
	 * 属性终止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDADDDATE")
	public Date getEndAddDate() {
		return this.endAddDate;
	}

	/**
	 * 属性终止日期的setter方法
	 */
	public void setEndAddDate(Date endAddDate) {
		this.endAddDate = endAddDate;
	}

	/**
	 * 属性ADDASSUREMONTH的getter方法
	 */

	@Column(name = "ADDASSUREMONTH")
	public Integer getAddAssureMonth() {
		return this.addAssureMonth;
	}

	/**
	 * 属性ADDASSUREMONTH的setter方法
	 */
	public void setAddAssureMonth(Integer addAssureMonth) {
		this.addAssureMonth = addAssureMonth;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性TESTTIMEUNIT的getter方法
	 */

	@Column(name = "TESTTIMEUNIT")
	public String getTestTimeUnit() {
		return this.testTimeUnit;
	}

	/**
	 * 属性TESTTIMEUNIT的setter方法
	 */
	public void setTestTimeUnit(String testTimeUnit) {
		this.testTimeUnit = testTimeUnit;
	}

	/**
	 * 属性ADDTIMEUNIT的getter方法
	 */

	@Column(name = "ADDTIMEUNIT")
	public String getAddTimeUnit() {
		return this.addTimeUnit;
	}

	/**
	 * 属性ADDTIMEUNIT的setter方法
	 */
	public void setAddTimeUnit(String addTimeUnit) {
		this.addTimeUnit = addTimeUnit;
	}

}
