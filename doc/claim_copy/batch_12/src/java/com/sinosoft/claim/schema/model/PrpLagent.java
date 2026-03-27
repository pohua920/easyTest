package com.sinosoft.claim.schema.model;

// default package
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
 * POJO类PrpLagent
 */
@Entity
@Table(name = "PRPLAGENT")
public class PrpLagent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLagentId id;

	/** 属性垫付赔案编码 */
	private String advanceNo;

	/** 属性无责方被保险人 */
	private String nullInsured;

	/** 属性无责方公司名称 */
	private String nullComName;

	/** 属性无责方公司号 */
	private String nullCompany;

	/** 属性无责方赔案号 */
	private String nullClaimCode;

	/** 属性无责方保单号 */
	private String nullPolicyCode;

	/** 属性无责方投保确认码 */
	private String nullConfirmSequenceNo;

	/** 属性无责方号牌号码 */
	private String nullCarMark;

	/** 属性无责方号牌种类 */
	private String nullVihecleType;

	/** 属性无责方驾驶员名称 */
	private String nullDriverName;

	/** 属性无责方驾驶员证件号码 */
	private String nullDriverCode;

	/** 属性全责方定损金额 */
	private Double estimatedAmount;

	/** 属性无责方赔偿金额 */
	private Double settleMentAmount;

	/** 属性全责方号牌号码 */
	private String fullCarMark;

	/** 属性全责方号牌种类 */
	private String fullVihecleType;

	/** 属性付款方式 */
	private String payMode;

	/** 属性全责方意见 */
	private String fullComment;

	/** 属性全责方赔案号 */
	private String fullClaimCode;

	/** 属性核赔通过标志 */
	private String underwriteFlag;

	/** 属性全责方保险公司代码 */
	private String fullCompany;

	/** 属性全责方保单号码 */
	private String fullPolicyCode;

	/** 属性全责方投保确认码 */
	private String fullConfirmSequenceNo;

	/** 属性全责方驾驶员名称 */
	private String fullDriverName;

	/** 属性全责方驾驶员证件号码 */
	private String fullDriverCode;

	/** 属性出险时间 */
	private Date accidentTime;

	/** 属性全责方报案时间 */
	private Date fullReportTime;

	/** 属性全责方查勘时间 */
	private Date fullInvestigationTime;

	/** 属性出险地点 */
	private String accidentPlace;

	/** 属性查勘地点 */
	private String investigationPlace;

	/** 属性出险经过 */
	private String accidentDescreption;

	/** 属性出险原因 */
	private String accidentReason;

	/** 属性无责方意见 */
	private String nullComments;

	/** 属性全责方被保险人 */
	private String fullInsured;

	/** 属性案件状态 */
	private String caseStatus;

	/** 属性支付时间 */
	private Date payTime;

	/** 属性清算时间 */
	private Date accountingTime;

	/** 属性结算批次号 */
	private String accountingNumber;

	/** 属性冲销时间 */
	private Date closeTime;

	/**
	 * 类PrpLagent的默认构造方法
	 */
	public PrpLagent() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "nullReportNo", column = @Column(name = "NULLREPORTNO")), @AttributeOverride(name = "fullReportNo", column = @Column(name = "FULLREPORTNO")),
			@AttributeOverride(name = "claimType", column = @Column(name = "CLAIMTYPE")) })
	public PrpLagentId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLagentId id) {
		this.id = id;
	}

	/**
	 * 属性垫付赔案编码的getter方法
	 */

	@Column(name = "ADVANCENO")
	public String getAdvanceNo() {
		return this.advanceNo;
	}

	/**
	 * 属性垫付赔案编码的setter方法
	 */
	public void setAdvanceNo(String advanceNo) {
		this.advanceNo = advanceNo;
	}

	/**
	 * 属性无责方被保险人的getter方法
	 */

	@Column(name = "NULLINSURED")
	public String getNullInsured() {
		return this.nullInsured;
	}

	/**
	 * 属性无责方被保险人的setter方法
	 */
	public void setNullInsured(String nullInsured) {
		this.nullInsured = nullInsured;
	}

	/**
	 * 属性无责方公司名称的getter方法
	 */

	@Column(name = "NULLCOMNAME")
	public String getNullComName() {
		return this.nullComName;
	}

	/**
	 * 属性无责方公司名称的setter方法
	 */
	public void setNullComName(String nullComName) {
		this.nullComName = nullComName;
	}

	/**
	 * 属性无责方公司号的getter方法
	 */

	@Column(name = "NULLCOMPANY")
	public String getNullCompany() {
		return this.nullCompany;
	}

	/**
	 * 属性无责方公司号的setter方法
	 */
	public void setNullCompany(String nullCompany) {
		this.nullCompany = nullCompany;
	}

	/**
	 * 属性无责方赔案号的getter方法
	 */

	@Column(name = "NULLCLAIMCODE")
	public String getNullClaimCode() {
		return this.nullClaimCode;
	}

	/**
	 * 属性无责方赔案号的setter方法
	 */
	public void setNullClaimCode(String nullClaimCode) {
		this.nullClaimCode = nullClaimCode;
	}

	/**
	 * 属性无责方保单号的getter方法
	 */

	@Column(name = "NULLPOLICYCODE")
	public String getNullPolicyCode() {
		return this.nullPolicyCode;
	}

	/**
	 * 属性无责方保单号的setter方法
	 */
	public void setNullPolicyCode(String nullPolicyCode) {
		this.nullPolicyCode = nullPolicyCode;
	}

	/**
	 * 属性无责方投保确认码的getter方法
	 */

	@Column(name = "NULLCONFIRMSEQUENCENO")
	public String getNullConfirmSequenceNo() {
		return this.nullConfirmSequenceNo;
	}

	/**
	 * 属性无责方投保确认码的setter方法
	 */
	public void setNullConfirmSequenceNo(String nullConfirmSequenceNo) {
		this.nullConfirmSequenceNo = nullConfirmSequenceNo;
	}

	/**
	 * 属性无责方号牌号码的getter方法
	 */

	@Column(name = "NULLCARMARK")
	public String getNullCarMark() {
		return this.nullCarMark;
	}

	/**
	 * 属性无责方号牌号码的setter方法
	 */
	public void setNullCarMark(String nullCarMark) {
		this.nullCarMark = nullCarMark;
	}

	/**
	 * 属性无责方号牌种类的getter方法
	 */

	@Column(name = "NULLVIHECLETYPE")
	public String getNullVihecleType() {
		return this.nullVihecleType;
	}

	/**
	 * 属性无责方号牌种类的setter方法
	 */
	public void setNullVihecleType(String nullVihecleType) {
		this.nullVihecleType = nullVihecleType;
	}

	/**
	 * 属性无责方驾驶员名称的getter方法
	 */

	@Column(name = "NULLDRIVERNAME")
	public String getNullDriverName() {
		return this.nullDriverName;
	}

	/**
	 * 属性无责方驾驶员名称的setter方法
	 */
	public void setNullDriverName(String nullDriverName) {
		this.nullDriverName = nullDriverName;
	}

	/**
	 * 属性无责方驾驶员证件号码的getter方法
	 */

	@Column(name = "NULLDRIVERCODE")
	public String getNullDriverCode() {
		return this.nullDriverCode;
	}

	/**
	 * 属性无责方驾驶员证件号码的setter方法
	 */
	public void setNullDriverCode(String nullDriverCode) {
		this.nullDriverCode = nullDriverCode;
	}

	/**
	 * 属性全责方定损金额的getter方法
	 */

	@Column(name = "ESTIMATEDAMOUNT")
	public Double getEstimatedAmount() {
		return this.estimatedAmount;
	}

	/**
	 * 属性全责方定损金额的setter方法
	 */
	public void setEstimatedAmount(Double estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}

	/**
	 * 属性无责方赔偿金额的getter方法
	 */

	@Column(name = "SETTLEMENTAMOUNT")
	public Double getSettleMentAmount() {
		return this.settleMentAmount;
	}

	/**
	 * 属性无责方赔偿金额的setter方法
	 */
	public void setSettleMentAmount(Double settleMentAmount) {
		this.settleMentAmount = settleMentAmount;
	}

	/**
	 * 属性全责方号牌号码的getter方法
	 */

	@Column(name = "FULLCARMARK")
	public String getFullCarMark() {
		return this.fullCarMark;
	}

	/**
	 * 属性全责方号牌号码的setter方法
	 */
	public void setFullCarMark(String fullCarMark) {
		this.fullCarMark = fullCarMark;
	}

	/**
	 * 属性全责方号牌种类的getter方法
	 */

	@Column(name = "FULLVIHECLETYPE")
	public String getFullVihecleType() {
		return this.fullVihecleType;
	}

	/**
	 * 属性全责方号牌种类的setter方法
	 */
	public void setFullVihecleType(String fullVihecleType) {
		this.fullVihecleType = fullVihecleType;
	}

	/**
	 * 属性付款方式的getter方法
	 */

	@Column(name = "PAYMODE")
	public String getPayMode() {
		return this.payMode;
	}

	/**
	 * 属性付款方式的setter方法
	 */
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	/**
	 * 属性全责方意见的getter方法
	 */

	@Column(name = "FULLCOMMENT")
	public String getFullComment() {
		return this.fullComment;
	}

	/**
	 * 属性全责方意见的setter方法
	 */
	public void setFullComment(String fullComment) {
		this.fullComment = fullComment;
	}

	/**
	 * 属性全责方赔案号的getter方法
	 */

	@Column(name = "FULLCLAIMCODE")
	public String getFullClaimCode() {
		return this.fullClaimCode;
	}

	/**
	 * 属性全责方赔案号的setter方法
	 */
	public void setFullClaimCode(String fullClaimCode) {
		this.fullClaimCode = fullClaimCode;
	}

	/**
	 * 属性核赔通过标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderwriteFlag() {
		return this.underwriteFlag;
	}

	/**
	 * 属性核赔通过标志的setter方法
	 */
	public void setUnderwriteFlag(String underwriteFlag) {
		this.underwriteFlag = underwriteFlag;
	}

	/**
	 * 属性全责方保险公司代码的getter方法
	 */

	@Column(name = "FULLCOMPANY")
	public String getFullCompany() {
		return this.fullCompany;
	}

	/**
	 * 属性全责方保险公司代码的setter方法
	 */
	public void setFullCompany(String fullCompany) {
		this.fullCompany = fullCompany;
	}

	/**
	 * 属性全责方保单号码的getter方法
	 */

	@Column(name = "FULLPOLICYCODE")
	public String getFullPolicyCode() {
		return this.fullPolicyCode;
	}

	/**
	 * 属性全责方保单号码的setter方法
	 */
	public void setFullPolicyCode(String fullPolicyCode) {
		this.fullPolicyCode = fullPolicyCode;
	}

	/**
	 * 属性全责方投保确认码的getter方法
	 */

	@Column(name = "FULLCONFIRMSEQUENCENO")
	public String getFullConfirmSequenceNo() {
		return this.fullConfirmSequenceNo;
	}

	/**
	 * 属性全责方投保确认码的setter方法
	 */
	public void setFullConfirmSequenceNo(String fullConfirmSequenceNo) {
		this.fullConfirmSequenceNo = fullConfirmSequenceNo;
	}

	/**
	 * 属性全责方驾驶员名称的getter方法
	 */

	@Column(name = "FULLDRIVERNAME")
	public String getFullDriverName() {
		return this.fullDriverName;
	}

	/**
	 * 属性全责方驾驶员名称的setter方法
	 */
	public void setFullDriverName(String fullDriverName) {
		this.fullDriverName = fullDriverName;
	}

	/**
	 * 属性全责方驾驶员证件号码的getter方法
	 */

	@Column(name = "FULLDRIVERCODE")
	public String getFullDriverCode() {
		return this.fullDriverCode;
	}

	/**
	 * 属性全责方驾驶员证件号码的setter方法
	 */
	public void setFullDriverCode(String fullDriverCode) {
		this.fullDriverCode = fullDriverCode;
	}

	/**
	 * 属性出险时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ACCIDENTTIME")
	public Date getAccidentTime() {
		return this.accidentTime;
	}

	/**
	 * 属性出险时间的setter方法
	 */
	public void setAccidentTime(Date accidentTime) {
		this.accidentTime = accidentTime;
	}

	/**
	 * 属性全责方报案时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "FULLREPORTTIME")
	public Date getFullReportTime() {
		return this.fullReportTime;
	}

	/**
	 * 属性全责方报案时间的setter方法
	 */
	public void setFullReportTime(Date fullReportTime) {
		this.fullReportTime = fullReportTime;
	}

	/**
	 * 属性全责方查勘时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "FULLINVESTIGATIONTIME")
	public Date getFullInvestigationTime() {
		return this.fullInvestigationTime;
	}

	/**
	 * 属性全责方查勘时间的setter方法
	 */
	public void setFullInvestigationTime(Date fullInvestigationTime) {
		this.fullInvestigationTime = fullInvestigationTime;
	}

	/**
	 * 属性出险地点的getter方法
	 */

	@Column(name = "ACCIDENTPLACE")
	public String getAccidentPlace() {
		return this.accidentPlace;
	}

	/**
	 * 属性出险地点的setter方法
	 */
	public void setAccidentPlace(String accidentPlace) {
		this.accidentPlace = accidentPlace;
	}

	/**
	 * 属性查勘地点的getter方法
	 */

	@Column(name = "INVESTIGATIONPLACE")
	public String getInvestigationPlace() {
		return this.investigationPlace;
	}

	/**
	 * 属性查勘地点的setter方法
	 */
	public void setInvestigationPlace(String investigationPlace) {
		this.investigationPlace = investigationPlace;
	}

	/**
	 * 属性出险经过的getter方法
	 */

	@Column(name = "ACCIDENTDESCREPTION")
	public String getAccidentDescreption() {
		return this.accidentDescreption;
	}

	/**
	 * 属性出险经过的setter方法
	 */
	public void setAccidentDescreption(String accidentDescreption) {
		this.accidentDescreption = accidentDescreption;
	}

	/**
	 * 属性出险原因的getter方法
	 */

	@Column(name = "ACCIDENTREASON")
	public String getAccidentReason() {
		return this.accidentReason;
	}

	/**
	 * 属性出险原因的setter方法
	 */
	public void setAccidentReason(String accidentReason) {
		this.accidentReason = accidentReason;
	}

	/**
	 * 属性无责方意见的getter方法
	 */

	@Column(name = "NULLCOMMENTS")
	public String getNullComments() {
		return this.nullComments;
	}

	/**
	 * 属性无责方意见的setter方法
	 */
	public void setNullComments(String nullComments) {
		this.nullComments = nullComments;
	}

	/**
	 * 属性全责方被保险人的getter方法
	 */

	@Column(name = "FULLINSURED")
	public String getFullInsured() {
		return this.fullInsured;
	}

	/**
	 * 属性全责方被保险人的setter方法
	 */
	public void setFullInsured(String fullInsured) {
		this.fullInsured = fullInsured;
	}

	/**
	 * 属性案件状态的getter方法
	 */

	@Column(name = "CASESTATUS")
	public String getCaseStatus() {
		return this.caseStatus;
	}

	/**
	 * 属性案件状态的setter方法
	 */
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}

	/**
	 * 属性支付时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYTIME")
	public Date getPayTime() {
		return this.payTime;
	}

	/**
	 * 属性支付时间的setter方法
	 */
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	/**
	 * 属性清算时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ACCOUNTINGTIME")
	public Date getAccountingTime() {
		return this.accountingTime;
	}

	/**
	 * 属性清算时间的setter方法
	 */
	public void setAccountingTime(Date accountingTime) {
		this.accountingTime = accountingTime;
	}

	/**
	 * 属性结算批次号的getter方法
	 */

	@Column(name = "ACCOUNTINGNUMBER")
	public String getAccountingNumber() {
		return this.accountingNumber;
	}

	/**
	 * 属性结算批次号的setter方法
	 */
	public void setAccountingNumber(String accountingNumber) {
		this.accountingNumber = accountingNumber;
	}

	/**
	 * 属性冲销时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CLOSETIME")
	public Date getCloseTime() {
		return this.closeTime;
	}

	/**
	 * 属性冲销时间的setter方法
	 */
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

}
