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
 * POJO类PrpCmainLiab
 */
@Entity
@Table(name = "PRPCMAINLIAB")
public class PrpCmainLiab implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性风险类别 */
	private String riskKind;

	/** 属性证号 */
	private String certificateNo;

	/** 属性发证日期 */
	private Date certificateDate;

	/** 属性发证机构 */
	private String certificateDepart;

	/** 属性开业日期 */
	private Date practiceDate;

	/** 属性BUSINESSCODE */
	private String businessCode;

	/** 属性营业性质/主要业务范围 */
	private String businessDetail;

	/** 属性营业处所 */
	private String businessSite;

	/** 属性承保区域范围 */
	private String insureArea;

	/** 属性销售区域范围 */
	private String saleArea;

	/** 属性律师职业责任险：事务所类型 */
	private String officeType;

	/** 属性OFFICEGRADE */
	private String officeGrade;

	/** 属性追溯起始日期 */
	private Date bkWardStartDate;

	/** 属性追溯终止日期 */
	private Date bkWardEndDate;

	/** 属性员工人数 */
	private Integer staffCount;

	/** 属性上年度营业额 */
	private Double preTurnOver;

	/** 属性本年度营业额/销售额 */
	private Double nowTurnOver;

	/** 属性供电量 */
	private Double electricPower;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性BUSINESSSOURCE */
	private String businessSource;

	/** 属性LIMITEDSTAFFCOUNT */
	private Integer limitedStaffCount;

	/** 属性LISTEDCOMPANYTYPE */
	private String listedComPanyType;

	/** 属性ENTERPRISETYPE */
	private String enterPriseType;

	/** 属性DANGEROUSGOODSTYPE */
	private String dangerousgoodsType;

	/** 属性EXTENDREGIST */
	private Date extendregist;
	/** 定作人 */
	private String hirer;
	/** 定作人統一編號 */
	private String othPolicyNo;
	/** 定作人住所 */
	private String returnFlight;

	/**
	 * 类PrpCmainLiab的默认构造方法
	 */
	public PrpCmainLiab() {
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
	 * 属性证号的getter方法
	 */

	@Column(name = "CERTIFICATENO")
	public String getCertificateNo() {
		return this.certificateNo;
	}

	/**
	 * 属性证号的setter方法
	 */
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	/**
	 * 属性发证日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CERTIFICATEDATE")
	public Date getCertificateDate() {
		return this.certificateDate;
	}

	/**
	 * 属性发证日期的setter方法
	 */
	public void setCertificateDate(Date certificateDate) {
		this.certificateDate = certificateDate;
	}

	/**
	 * 属性发证机构的getter方法
	 */

	@Column(name = "CERTIFICATEDEPART")
	public String getCertificateDepart() {
		return this.certificateDepart;
	}

	/**
	 * 属性发证机构的setter方法
	 */
	public void setCertificateDepart(String certificateDepart) {
		this.certificateDepart = certificateDepart;
	}

	/**
	 * 属性开业日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRACTICEDATE")
	public Date getPracticeDate() {
		return this.practiceDate;
	}

	/**
	 * 属性开业日期的setter方法
	 */
	public void setPracticeDate(Date practiceDate) {
		this.practiceDate = practiceDate;
	}

	/**
	 * 属性BUSINESSCODE的getter方法
	 */

	@Column(name = "BUSINESSCODE")
	public String getBusinessCode() {
		return this.businessCode;
	}

	/**
	 * 属性BUSINESSCODE的setter方法
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	/**
	 * 属性营业性质/主要业务范围的getter方法
	 */

	@Column(name = "BUSINESSDETAIL")
	public String getBusinessDetail() {
		return this.businessDetail;
	}

	/**
	 * 属性营业性质/主要业务范围的setter方法
	 */
	public void setBusinessDetail(String businessDetail) {
		this.businessDetail = businessDetail;
	}

	/**
	 * 属性营业处所的getter方法
	 */

	@Column(name = "BUSINESSSITE")
	public String getBusinessSite() {
		return this.businessSite;
	}

	/**
	 * 属性营业处所的setter方法
	 */
	public void setBusinessSite(String businessSite) {
		this.businessSite = businessSite;
	}

	/**
	 * 属性承保区域范围的getter方法
	 */

	@Column(name = "INSUREAREA")
	public String getInsureArea() {
		return this.insureArea;
	}

	/**
	 * 属性承保区域范围的setter方法
	 */
	public void setInsureArea(String insureArea) {
		this.insureArea = insureArea;
	}

	/**
	 * 属性销售区域范围的getter方法
	 */

	@Column(name = "SALEAREA")
	public String getSaleArea() {
		return this.saleArea;
	}

	/**
	 * 属性销售区域范围的setter方法
	 */
	public void setSaleArea(String saleArea) {
		this.saleArea = saleArea;
	}

	/**
	 * 属性律师职业责任险：事务所类型的getter方法
	 */

	@Column(name = "OFFICETYPE")
	public String getOfficeType() {
		return this.officeType;
	}

	/**
	 * 属性律师职业责任险：事务所类型的setter方法
	 */
	public void setOfficeType(String officeType) {
		this.officeType = officeType;
	}

	/**
	 * 属性OFFICEGRADE的getter方法
	 */

	@Column(name = "OFFICEGRADE")
	public String getOfficeGrade() {
		return this.officeGrade;
	}

	/**
	 * 属性OFFICEGRADE的setter方法
	 */
	public void setOfficeGrade(String officeGrade) {
		this.officeGrade = officeGrade;
	}

	/**
	 * 属性追溯起始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BKWARDSTARTDATE")
	public Date getBkWardStartDate() {
		return this.bkWardStartDate;
	}

	/**
	 * 属性追溯起始日期的setter方法
	 */
	public void setBkWardStartDate(Date bkWardStartDate) {
		this.bkWardStartDate = bkWardStartDate;
	}

	/**
	 * 属性追溯终止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BKWARDENDDATE")
	public Date getBkWardEndDate() {
		return this.bkWardEndDate;
	}

	/**
	 * 属性追溯终止日期的setter方法
	 */
	public void setBkWardEndDate(Date bkWardEndDate) {
		this.bkWardEndDate = bkWardEndDate;
	}

	/**
	 * 属性员工人数的getter方法
	 */

	@Column(name = "STAFFCOUNT")
	public Integer getStaffCount() {
		return this.staffCount;
	}

	/**
	 * 属性员工人数的setter方法
	 */
	public void setStaffCount(Integer staffCount) {
		this.staffCount = staffCount;
	}

	/**
	 * 属性上年度营业额的getter方法
	 */

	@Column(name = "PRETURNOVER")
	public Double getPreTurnOver() {
		return this.preTurnOver;
	}

	/**
	 * 属性上年度营业额的setter方法
	 */
	public void setPreTurnOver(Double preTurnOver) {
		this.preTurnOver = preTurnOver;
	}

	/**
	 * 属性本年度营业额/销售额的getter方法
	 */

	@Column(name = "NOWTURNOVER")
	public Double getNowTurnOver() {
		return this.nowTurnOver;
	}

	/**
	 * 属性本年度营业额/销售额的setter方法
	 */
	public void setNowTurnOver(Double nowTurnOver) {
		this.nowTurnOver = nowTurnOver;
	}

	/**
	 * 属性供电量的getter方法
	 */

	@Column(name = "ELECTRICPOWER")
	public Double getElectricPower() {
		return this.electricPower;
	}

	/**
	 * 属性供电量的setter方法
	 */
	public void setElectricPower(Double electricPower) {
		this.electricPower = electricPower;
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
	 * 属性BUSINESSSOURCE的getter方法
	 */

	@Column(name = "BUSINESSSOURCE")
	public String getBusinessSource() {
		return this.businessSource;
	}

	/**
	 * 属性BUSINESSSOURCE的setter方法
	 */
	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	/**
	 * 属性LIMITEDSTAFFCOUNT的getter方法
	 */

	@Column(name = "LIMITEDSTAFFCOUNT")
	public Integer getLimitedStaffCount() {
		return this.limitedStaffCount;
	}

	/**
	 * 属性LIMITEDSTAFFCOUNT的setter方法
	 */
	public void setLimitedStaffCount(Integer limitedStaffCount) {
		this.limitedStaffCount = limitedStaffCount;
	}

	/**
	 * 属性LISTEDCOMPANYTYPE的getter方法
	 */

	@Column(name = "LISTEDCOMPANYTYPE")
	public String getListedComPanyType() {
		return this.listedComPanyType;
	}

	/**
	 * 属性LISTEDCOMPANYTYPE的setter方法
	 */
	public void setListedComPanyType(String listedComPanyType) {
		this.listedComPanyType = listedComPanyType;
	}

	/**
	 * 属性ENTERPRISETYPE的getter方法
	 */

	@Column(name = "ENTERPRISETYPE")
	public String getEnterPriseType() {
		return this.enterPriseType;
	}

	/**
	 * 属性ENTERPRISETYPE的setter方法
	 */
	public void setEnterPriseType(String enterPriseType) {
		this.enterPriseType = enterPriseType;
	}

	/**
	 * 属性DANGEROUSGOODSTYPE的getter方法
	 */

	@Column(name = "DANGEROUSGOODSTYPE")
	public String getDangerousgoodsType() {
		return this.dangerousgoodsType;
	}

	/**
	 * 属性DANGEROUSGOODSTYPE的setter方法
	 */
	public void setDangerousgoodsType(String dangerousgoodsType) {
		this.dangerousgoodsType = dangerousgoodsType;
	}

	/**
	 * 属性EXTENDREGIST的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "EXTENDREGIST")
	public Date getExtendregist() {
		return this.extendregist;
	}

	/**
	 * 属性EXTENDREGIST的setter方法
	 */
	public void setExtendregist(Date extendregist) {
		this.extendregist = extendregist;
	}

	@Column(name = "hirer")
	public String getHirer() {
		return hirer;
	}

	public void setHirer(String hirer) {
		this.hirer = hirer;
	}

	@Column(name = "othPolicyNo")
	public String getOthPolicyNo() {
		return othPolicyNo;
	}

	public void setOthPolicyNo(String othPolicyNo) {
		this.othPolicyNo = othPolicyNo;
	}

	@Column(name = "returnFlight")
	public String getReturnFlight() {
		return returnFlight;
	}

	public void setReturnFlight(String returnFlight) {
		this.returnFlight = returnFlight;
	}

}
