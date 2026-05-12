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
 * POJO类PrpCmainLoan
 */
@Entity
@Table(name = "PRPCMAINLOAN")
public class PrpCmainLoan implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCmainLoanId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性风险类别 */
	private String riskKind;

	/** 属性担保方式（抵押/质押/保证） */
	private String guaranteeType;

	/** 属性抵押/质押物品名称 */
	private String guaranteeName;

	/** 属性抵押合同/确认书号或房屋他项权证 */
	private String mortgageNo;

	/** 属性担保人代码 */
	private String warrantorCode;

	/** 属性担保人名称 */
	private String warrantorName;

	/** 属性贷款相关号1：贷款申请表号 */
	private String loanNo1;

	/** 属性贷款相关号2：贷款审批表号 */
	private String loanNo2;

	/** 属性期房标志(Y/N) */
	private String installmentFlag;

	/** 属性期房交付日期 */
	private Date deliverDate;

	/** 属性贷款合同号 */
	private String loanContractNo;

	/** 属性保额确定方式编码 */
	private String loanWay;

	/** 属性贷款性质代码 */
	private String loanNature;

	/** 属性贷款银行代码 */
	private String loanBankCode;

	/** 属性贷款银行名称 */
	private String loanBankName;

	/** 属性贷款用途 */
	private String loanUsage;

	/** 属性贷款期限开始日期 */
	private Date loanStartDate;

	/** 属性贷款期限终止日期 */
	private Date loanEndDate;

	/** 属性贷款年限 */
	private Integer loanYear;

	/** 属性初装金额 */
	private Double planAmount;

	/** 属性首付比例（%） */
	private Double firstRate;

	/** 属性首期付款金额 */
	private Double firstPaid;

	/** 属性贷款币别 */
	private String currency;

	/** 属性贷款金额 */
	private Double loanAmount;

	/** 属性贷款利率/本息 */
	private Double loanRate;

	/** 属性还款方式:按月、按季 */
	private String repaidType;

	/** 属性约定付款期数 */
	private Integer paidTimes;

	/** 属性每期还款金额 */
	private Double perRepaidAmount;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性MORTGAGEREGORGAN */
	private String mortgageregorgan;

	/** 属性MORTGAGERATE */
	private Double mortgagerate;

	/** 属性ASSOCIATEPOLICYNO */
	private String associatePolicyNo;

	/**
	 * 类PrpCmainLoan的默认构造方法
	 */
	public PrpCmainLoan() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpCmainLoanId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCmainLoanId id) {
		this.id = id;
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
	 * 属性担保方式（抵押/质押/保证）的getter方法
	 */

	@Column(name = "GUARANTEETYPE")
	public String getGuaranteeType() {
		return this.guaranteeType;
	}

	/**
	 * 属性担保方式（抵押/质押/保证）的setter方法
	 */
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	/**
	 * 属性抵押/质押物品名称的getter方法
	 */

	@Column(name = "GUARANTEENAME")
	public String getGuaranteeName() {
		return this.guaranteeName;
	}

	/**
	 * 属性抵押/质押物品名称的setter方法
	 */
	public void setGuaranteeName(String guaranteeName) {
		this.guaranteeName = guaranteeName;
	}

	/**
	 * 属性抵押合同/确认书号或房屋他项权证的getter方法
	 */

	@Column(name = "MORTGAGENO")
	public String getMortgageNo() {
		return this.mortgageNo;
	}

	/**
	 * 属性抵押合同/确认书号或房屋他项权证的setter方法
	 */
	public void setMortgageNo(String mortgageNo) {
		this.mortgageNo = mortgageNo;
	}

	/**
	 * 属性担保人代码的getter方法
	 */

	@Column(name = "WARRANTORCODE")
	public String getWarrantorCode() {
		return this.warrantorCode;
	}

	/**
	 * 属性担保人代码的setter方法
	 */
	public void setWarrantorCode(String warrantorCode) {
		this.warrantorCode = warrantorCode;
	}

	/**
	 * 属性担保人名称的getter方法
	 */

	@Column(name = "WARRANTORNAME")
	public String getWarrantorName() {
		return this.warrantorName;
	}

	/**
	 * 属性担保人名称的setter方法
	 */
	public void setWarrantorName(String warrantorName) {
		this.warrantorName = warrantorName;
	}

	/**
	 * 属性贷款相关号1：贷款申请表号的getter方法
	 */

	@Column(name = "LOANNO1")
	public String getLoanNo1() {
		return this.loanNo1;
	}

	/**
	 * 属性贷款相关号1：贷款申请表号的setter方法
	 */
	public void setLoanNo1(String loanNo1) {
		this.loanNo1 = loanNo1;
	}

	/**
	 * 属性贷款相关号2：贷款审批表号的getter方法
	 */

	@Column(name = "LOANNO2")
	public String getLoanNo2() {
		return this.loanNo2;
	}

	/**
	 * 属性贷款相关号2：贷款审批表号的setter方法
	 */
	public void setLoanNo2(String loanNo2) {
		this.loanNo2 = loanNo2;
	}

	/**
	 * 属性期房标志(Y/N)的getter方法
	 */

	@Column(name = "INSTALLMENTFLAG")
	public String getInstallmentFlag() {
		return this.installmentFlag;
	}

	/**
	 * 属性期房标志(Y/N)的setter方法
	 */
	public void setInstallmentFlag(String installmentFlag) {
		this.installmentFlag = installmentFlag;
	}

	/**
	 * 属性期房交付日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DELIVERDATE")
	public Date getDeliverDate() {
		return this.deliverDate;
	}

	/**
	 * 属性期房交付日期的setter方法
	 */
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	/**
	 * 属性贷款合同号的getter方法
	 */

	@Column(name = "LOANCONTRACTNO")
	public String getLoanContractNo() {
		return this.loanContractNo;
	}

	/**
	 * 属性贷款合同号的setter方法
	 */
	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}

	/**
	 * 属性保额确定方式编码的getter方法
	 */

	@Column(name = "LOANWAY")
	public String getLoanWay() {
		return this.loanWay;
	}

	/**
	 * 属性保额确定方式编码的setter方法
	 */
	public void setLoanWay(String loanWay) {
		this.loanWay = loanWay;
	}

	/**
	 * 属性贷款性质代码的getter方法
	 */

	@Column(name = "LOANNATURE")
	public String getLoanNature() {
		return this.loanNature;
	}

	/**
	 * 属性贷款性质代码的setter方法
	 */
	public void setLoanNature(String loanNature) {
		this.loanNature = loanNature;
	}

	/**
	 * 属性贷款银行代码的getter方法
	 */

	@Column(name = "LOANBANKCODE")
	public String getLoanBankCode() {
		return this.loanBankCode;
	}

	/**
	 * 属性贷款银行代码的setter方法
	 */
	public void setLoanBankCode(String loanBankCode) {
		this.loanBankCode = loanBankCode;
	}

	/**
	 * 属性贷款银行名称的getter方法
	 */

	@Column(name = "LOANBANKNAME")
	public String getLoanBankName() {
		return this.loanBankName;
	}

	/**
	 * 属性贷款银行名称的setter方法
	 */
	public void setLoanBankName(String loanBankName) {
		this.loanBankName = loanBankName;
	}

	/**
	 * 属性贷款用途的getter方法
	 */

	@Column(name = "LOANUSAGE")
	public String getLoanUsage() {
		return this.loanUsage;
	}

	/**
	 * 属性贷款用途的setter方法
	 */
	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage;
	}

	/**
	 * 属性贷款期限开始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LOANSTARTDATE")
	public Date getLoanStartDate() {
		return this.loanStartDate;
	}

	/**
	 * 属性贷款期限开始日期的setter方法
	 */
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	/**
	 * 属性贷款期限终止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LOANENDDATE")
	public Date getLoanEndDate() {
		return this.loanEndDate;
	}

	/**
	 * 属性贷款期限终止日期的setter方法
	 */
	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	/**
	 * 属性贷款年限的getter方法
	 */

	@Column(name = "LOANYEAR")
	public Integer getLoanYear() {
		return this.loanYear;
	}

	/**
	 * 属性贷款年限的setter方法
	 */
	public void setLoanYear(Integer loanYear) {
		this.loanYear = loanYear;
	}

	/**
	 * 属性初装金额的getter方法
	 */

	@Column(name = "PLANAMOUNT")
	public Double getPlanAmount() {
		return this.planAmount;
	}

	/**
	 * 属性初装金额的setter方法
	 */
	public void setPlanAmount(Double planAmount) {
		this.planAmount = planAmount;
	}

	/**
	 * 属性首付比例（%）的getter方法
	 */

	@Column(name = "FIRSTRATE")
	public Double getFirstRate() {
		return this.firstRate;
	}

	/**
	 * 属性首付比例（%）的setter方法
	 */
	public void setFirstRate(Double firstRate) {
		this.firstRate = firstRate;
	}

	/**
	 * 属性首期付款金额的getter方法
	 */

	@Column(name = "FIRSTPAID")
	public Double getFirstPaid() {
		return this.firstPaid;
	}

	/**
	 * 属性首期付款金额的setter方法
	 */
	public void setFirstPaid(Double firstPaid) {
		this.firstPaid = firstPaid;
	}

	/**
	 * 属性贷款币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性贷款币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性贷款金额的getter方法
	 */

	@Column(name = "LOANAMOUNT")
	public Double getLoanAmount() {
		return this.loanAmount;
	}

	/**
	 * 属性贷款金额的setter方法
	 */
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	/**
	 * 属性贷款利率/本息的getter方法
	 */

	@Column(name = "LOANRATE")
	public Double getLoanRate() {
		return this.loanRate;
	}

	/**
	 * 属性贷款利率/本息的setter方法
	 */
	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}

	/**
	 * 属性还款方式:按月、按季的getter方法
	 */

	@Column(name = "REPAIDTYPE")
	public String getRepaidType() {
		return this.repaidType;
	}

	/**
	 * 属性还款方式:按月、按季的setter方法
	 */
	public void setRepaidType(String repaidType) {
		this.repaidType = repaidType;
	}

	/**
	 * 属性约定付款期数的getter方法
	 */

	@Column(name = "PAIDTIMES")
	public Integer getPaidTimes() {
		return this.paidTimes;
	}

	/**
	 * 属性约定付款期数的setter方法
	 */
	public void setPaidTimes(Integer paidTimes) {
		this.paidTimes = paidTimes;
	}

	/**
	 * 属性每期还款金额的getter方法
	 */

	@Column(name = "PERREPAIDAMOUNT")
	public Double getPerRepaidAmount() {
		return this.perRepaidAmount;
	}

	/**
	 * 属性每期还款金额的setter方法
	 */
	public void setPerRepaidAmount(Double perRepaidAmount) {
		this.perRepaidAmount = perRepaidAmount;
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
	 * 属性MORTGAGEREGORGAN的getter方法
	 */

	@Column(name = "MORTGAGEREGORGAN")
	public String getMortgageregorgan() {
		return this.mortgageregorgan;
	}

	/**
	 * 属性MORTGAGEREGORGAN的setter方法
	 */
	public void setMortgageregorgan(String mortgageregorgan) {
		this.mortgageregorgan = mortgageregorgan;
	}

	/**
	 * 属性MORTGAGERATE的getter方法
	 */

	@Column(name = "MORTGAGERATE")
	public Double getMortgagerate() {
		return this.mortgagerate;
	}

	/**
	 * 属性MORTGAGERATE的setter方法
	 */
	public void setMortgagerate(Double mortgagerate) {
		this.mortgagerate = mortgagerate;
	}

	/**
	 * 属性ASSOCIATEPOLICYNO的getter方法
	 */

	@Column(name = "ASSOCIATEPOLICYNO")
	public String getAssociatePolicyNo() {
		return this.associatePolicyNo;
	}

	/**
	 * 属性ASSOCIATEPOLICYNO的setter方法
	 */
	public void setAssociatePolicyNo(String associatePolicyNo) {
		this.associatePolicyNo = associatePolicyNo;
	}

}
