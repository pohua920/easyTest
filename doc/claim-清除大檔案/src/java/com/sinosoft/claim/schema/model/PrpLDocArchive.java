package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLDocArchive
 */
@Entity
@Table(name = "PRPLDOCARCHIVE")
public class PrpLDocArchive implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号 */
	private String claimNo;

	/** 属性REGISTNO */
	private String registno;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性归属机构 */
	private String comCode;

	/** 属性被保险人 */
	private String insuredCode;

	/** 属性被保人名称 */
	private String insuredName;

	/** 属性结案日期 */
	private Date endCaseDate = new Date();

	/** 属性标的赔款金额 */
	private double sumDutyPaid;

	/** 属性资料状态标志 */
	private String status;

	/** 属性调阅申请人代码 */
	private String applicantCode;

	/** 属性调阅申请人名称 */
	private String applicantName;

	/** 属性申请调阅日期 */
	private Date applyDate = new Date();

	/** 属性调阅时间 */
	private Date startReviewDate = new Date();

	/** 属性预计归档周期 */
	private String estimatePeriod;

	/** 属性申请延期次数 */
	private Integer applyDeferno = 0;

	/** 属性延期周期 */
	private String applyDeferPeriod;

	/** 属性预计归档日期 */
	private Date estimateReturnDate = new Date();

	/** 属性退还日期 */
	private Date returnDate = new Date();
	/** 列表 */
	private List<PrpLDocArchive> archiveList;
	/** 一页数据 */

	private String flagColor;

	/**
	 * 类PrpLDocArchive的默认构造方法
	 */
	public PrpLDocArchive() {
	}

	/**
	 * 属性立案号的getter方法
	 */
	@Id
	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性REGISTNO的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistno() {
		return this.registno;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistno(String registno) {
		this.registno = registno;
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
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性被保险人的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性被保险人的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性结案日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDCASEDATE")
	public Date getEndCaseDate() {
		return this.endCaseDate;
	}

	/**
	 * 属性结案日期的setter方法
	 */
	public void setEndCaseDate(Date endCaseDate) {
		this.endCaseDate = endCaseDate;
	}

	/**
	 * 属性标的赔款金额的getter方法
	 */

	@Column(name = "SUMDUTYPAID")
	public double getSumDutyPaid() {
		return this.sumDutyPaid;
	}

	/**
	 * 属性标的赔款金额的setter方法
	 */
	public void setSumDutyPaid(double sumDutyPaid) {
		this.sumDutyPaid = sumDutyPaid;
	}

	/**
	 * 属性资料状态标志的getter方法
	 */

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	/**
	 * 属性资料状态标志的setter方法
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 属性调阅申请人代码的getter方法
	 */

	@Column(name = "APPLICANTCODE")
	public String getApplicantCode() {
		return this.applicantCode;
	}

	/**
	 * 属性调阅申请人代码的setter方法
	 */
	public void setApplicantCode(String applicantCode) {
		this.applicantCode = applicantCode;
	}

	/**
	 * 属性调阅申请人名称的getter方法
	 */

	@Column(name = "APPLICANTNAME")
	public String getApplicantName() {
		return this.applicantName;
	}

	/**
	 * 属性调阅申请人名称的setter方法
	 */
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	/**
	 * 属性申请调阅日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "APPLYDATE")
	public Date getApplyDate() {
		return this.applyDate;
	}

	/**
	 * 属性申请调阅日期的setter方法
	 */
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * 属性调阅时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTREVIEWDATE")
	public Date getStartReviewDate() {
		return this.startReviewDate;
	}

	/**
	 * 属性调阅时间的setter方法
	 */
	public void setStartReviewDate(Date startReviewDate) {
		this.startReviewDate = startReviewDate;
	}

	/**
	 * 属性预计归档周期的getter方法
	 */

	@Column(name = "ESTIMATEPERIOD")
	public String getEstimatePeriod() {
		return this.estimatePeriod;
	}

	/**
	 * 属性预计归档周期的setter方法
	 */
	public void setEstimatePeriod(String estimatePeriod) {
		this.estimatePeriod = estimatePeriod;
	}

	/**
	 * 属性申请延期次数的getter方法
	 */

	@Column(name = "APPLYDEFERNO")
	public Integer getApplyDeferno() {
		return this.applyDeferno;
	}

	/**
	 * 属性申请延期次数的setter方法
	 */
	public void setApplyDeferno(Integer applyDeferno) {
		this.applyDeferno = applyDeferno;
	}

	/**
	 * 属性延期周期的getter方法
	 */

	@Column(name = "APPLYDEFERPERIOD")
	public String getApplyDeferPeriod() {
		return this.applyDeferPeriod;
	}

	/**
	 * 属性延期周期的setter方法
	 */
	public void setApplyDeferPeriod(String applyDeferPeriod) {
		this.applyDeferPeriod = applyDeferPeriod;
	}

	/**
	 * 属性预计归档日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ESTIMATERETURNDATE")
	public Date getEstimateReturnDate() {
		return this.estimateReturnDate;
	}

	/**
	 * 属性预计归档日期的setter方法
	 */
	public void setEstimateReturnDate(Date estimateReturnDate) {
		this.estimateReturnDate = estimateReturnDate;
	}

	/**
	 * 属性退还日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RETURNDATE")
	public Date getReturnDate() {
		return this.returnDate;
	}

	/**
	 * 属性退还日期的setter方法
	 */
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Transient
	public List<PrpLDocArchive> getArchiveList() {
		return archiveList;
	}

	public void setArchiveList(List<PrpLDocArchive> archiveList) {
		this.archiveList = archiveList;
	}

	@Transient
	public String getFlagColor() {
		return flagColor;
	}

	public void setFlagColor(String flagColor) {
		this.flagColor = flagColor;
	}

}
