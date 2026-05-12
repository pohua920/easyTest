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
 * POJO类PrpLarrearage
 */
@Entity
@Table(name = "PRPLARREARAGE")
public class PrpLarrearage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLarrearageId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性汽车经销商 */
	private String automobileDealer;

	/** 属性贷款金额 */
	private Double sumLoan;

	/** 属性借款时间 */
	private Date loanStartDate;

	/** 属性借款期限 */
	private String loanTerm;

	/** 属性已还款期数 */
	private Integer sumRepaidTimes;

	/** 属性已还款金额 */
	private Double sumRePaid;

	/** 属性贷款余额 */
	private Double sumNoRePaid;

	/** 属性尚欠金额 */
	private Double arrearageCorpus;

	/** 属性逾期时间 */
	private Date arrearageDate;

	/** 属性最近一次还款日期 */
	private Date lastRepaidDate;

	/** 属性逾期欠款期数 */
	private Integer arrearageTimes;

	/** 属性逾期欠款金额 */
	private Double sumArrearage;

	/** 属性逾期欠款原因 */
	private String arrearageReason;

	/** 属性抵押物/质押物 */
	private String guaranteeName;

	/** 属性保证人 */
	private String cautioner;

	/** 属性保证金 */
	private Double cautionMoney;

	/** 属性催收措施 */
	private String dunStep;

	/** 属性报告单位意见 */
	private String reportUnitProposal;

	/** 属性经办人 */
	private String handlerCode;

	/** 属性经理 */
	private String manager;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLarrearage的默认构造方法
	 */
	public PrpLarrearage() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "arrearageEndDate", column = @Column(name = "ARREARAGEENDDATE")) })
	public PrpLarrearageId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLarrearageId id) {
		this.id = id;
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性汽车经销商的getter方法
	 */

	@Column(name = "AUTOMOBILEDEALER")
	public String getAutomobileDealer() {
		return this.automobileDealer;
	}

	/**
	 * 属性汽车经销商的setter方法
	 */
	public void setAutomobileDealer(String automobileDealer) {
		this.automobileDealer = automobileDealer;
	}

	/**
	 * 属性贷款金额的getter方法
	 */

	@Column(name = "SUMLOAN")
	public Double getSumLoan() {
		return this.sumLoan;
	}

	/**
	 * 属性贷款金额的setter方法
	 */
	public void setSumLoan(Double sumLoan) {
		this.sumLoan = sumLoan;
	}

	/**
	 * 属性借款时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LOANSTARTDATE")
	public Date getLoanStartDate() {
		return this.loanStartDate;
	}

	/**
	 * 属性借款时间的setter方法
	 */
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	/**
	 * 属性借款期限的getter方法
	 */

	@Column(name = "LOANTERM")
	public String getLoanTerm() {
		return this.loanTerm;
	}

	/**
	 * 属性借款期限的setter方法
	 */
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}

	/**
	 * 属性已还款期数的getter方法
	 */

	@Column(name = "SUMREPAIDTIMES")
	public Integer getSumRepaidTimes() {
		return this.sumRepaidTimes;
	}

	/**
	 * 属性已还款期数的setter方法
	 */
	public void setSumRepaidTimes(Integer sumRepaidTimes) {
		this.sumRepaidTimes = sumRepaidTimes;
	}

	/**
	 * 属性已还款金额的getter方法
	 */

	@Column(name = "SUMREPAID")
	public Double getSumRePaid() {
		return this.sumRePaid;
	}

	/**
	 * 属性已还款金额的setter方法
	 */
	public void setSumRePaid(Double sumRePaid) {
		this.sumRePaid = sumRePaid;
	}

	/**
	 * 属性贷款余额的getter方法
	 */

	@Column(name = "SUMNOREPAID")
	public Double getSumNoRePaid() {
		return this.sumNoRePaid;
	}

	/**
	 * 属性贷款余额的setter方法
	 */
	public void setSumNoRePaid(Double sumNoRePaid) {
		this.sumNoRePaid = sumNoRePaid;
	}

	/**
	 * 属性尚欠金额的getter方法
	 */

	@Column(name = "ARREARAGECORPUS")
	public Double getArrearageCorpus() {
		return this.arrearageCorpus;
	}

	/**
	 * 属性尚欠金额的setter方法
	 */
	public void setArrearageCorpus(Double arrearageCorpus) {
		this.arrearageCorpus = arrearageCorpus;
	}

	/**
	 * 属性逾期时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ARREARAGEDATE")
	public Date getArrearageDate() {
		return this.arrearageDate;
	}

	/**
	 * 属性逾期时间的setter方法
	 */
	public void setArrearageDate(Date arrearageDate) {
		this.arrearageDate = arrearageDate;
	}

	/**
	 * 属性最近一次还款日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LASTREPAIDDATE")
	public Date getLastRepaidDate() {
		return this.lastRepaidDate;
	}

	/**
	 * 属性最近一次还款日期的setter方法
	 */
	public void setLastRepaidDate(Date lastRepaidDate) {
		this.lastRepaidDate = lastRepaidDate;
	}

	/**
	 * 属性逾期欠款期数的getter方法
	 */

	@Column(name = "ARREARAGETIMES")
	public Integer getArrearageTimes() {
		return this.arrearageTimes;
	}

	/**
	 * 属性逾期欠款期数的setter方法
	 */
	public void setArrearageTimes(Integer arrearageTimes) {
		this.arrearageTimes = arrearageTimes;
	}

	/**
	 * 属性逾期欠款金额的getter方法
	 */

	@Column(name = "SUMARREARAGE")
	public Double getSumArrearage() {
		return this.sumArrearage;
	}

	/**
	 * 属性逾期欠款金额的setter方法
	 */
	public void setSumArrearage(Double sumArrearage) {
		this.sumArrearage = sumArrearage;
	}

	/**
	 * 属性逾期欠款原因的getter方法
	 */

	@Column(name = "ARREARAGEREASON")
	public String getArrearageReason() {
		return this.arrearageReason;
	}

	/**
	 * 属性逾期欠款原因的setter方法
	 */
	public void setArrearageReason(String arrearageReason) {
		this.arrearageReason = arrearageReason;
	}

	/**
	 * 属性抵押物/质押物的getter方法
	 */

	@Column(name = "GUARANTEENAME")
	public String getGuaranteeName() {
		return this.guaranteeName;
	}

	/**
	 * 属性抵押物/质押物的setter方法
	 */
	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	/**
	 * 属性保证人的getter方法
	 */

	@Column(name = "CAUTIONER")
	public String getCautioner() {
		return this.cautioner;
	}

	/**
	 * 属性保证人的setter方法
	 */
	public void setCautioner(String cautioner) {
		this.cautioner = cautioner;
	}

	/**
	 * 属性保证金的getter方法
	 */

	@Column(name = "CAUTIONMONEY")
	public Double getCautionMoney() {
		return this.cautionMoney;
	}

	/**
	 * 属性保证金的setter方法
	 */
	public void setCautionMoney(Double cautionMoney) {
		this.cautionMoney = cautionMoney;
	}

	/**
	 * 属性催收措施的getter方法
	 */

	@Column(name = "DUNSTEP")
	public String getDunStep() {
		return this.dunStep;
	}

	/**
	 * 属性催收措施的setter方法
	 */
	public void setDunStep(String dunStep) {
		this.dunStep = dunStep;
	}

	/**
	 * 属性报告单位意见的getter方法
	 */

	@Column(name = "REPORTUNITPROPOSAL")
	public String getReportUnitProposal() {
		return this.reportUnitProposal;
	}

	/**
	 * 属性报告单位意见的setter方法
	 */
	public void setReportUnitProposal(String reportUnitProposal) {
		this.reportUnitProposal = reportUnitProposal;
	}

	/**
	 * 属性经办人的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性经办人的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性经理的getter方法
	 */

	@Column(name = "MANAGER")
	public String getManager() {
		return this.manager;
	}

	/**
	 * 属性经理的setter方法
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * 属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
