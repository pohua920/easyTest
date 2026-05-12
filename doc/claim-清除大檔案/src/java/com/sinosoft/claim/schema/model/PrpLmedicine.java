package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
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
 * POJO类PrpLmedicine雇员医药费清单表
 */
@Entity
@Table(name = "PRPLMEDICINE")
public class PrpLmedicine implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLmedicineId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性序号 */
	private BigDecimal itemKindNo;

	/** 属性分户序号 */
	private BigDecimal familyNo;

	/** 属性分户名称 */
	private String familyName;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性标的项目类别代码 */
	private String itemCode;

	/** 属性雇员编码 */
	private String employeeCode;

	/** 属性雇员姓名 */
	private String employeeName;

	/** 属性各种费用代码 */
	private String feeTypeCode;

	/** 属性费用名称 */
	private String feeTypeName;

	/** 属性币别 */
	private String currency;

	/** 属性赔偿限额 */
	private BigDecimal indemnityLimit;

	/** 属性收据数 */
	private BigDecimal receiptCount;

	/** 属性收据金额 */
	private BigDecimal receiptAmt;

	/** 属性收据日期 */
	private Date receiptDate;

	/** 属性误工天数 */
	private BigDecimal missWorkDays;

	/** 属性受损金额 */
	private BigDecimal sumLoss;

	/** 属性剔除金额 */
	private BigDecimal sumReject;

	/** 属性剔除原因 */
	private String rejectReason;

	/** 属性赔偿比例 */
	private BigDecimal lossRate;

	/** 属性核定损金额 */
	private BigDecimal sumDefLoss;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLmedicine的默认构造方法
	 */
	public PrpLmedicine() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLmedicineId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLmedicineId id) {
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
	 * 属性序号的getter方法
	 */

	@Column(name = "ITEMKINDNO")
	public BigDecimal getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setItemKindNo(BigDecimal itemKindNo) {
		this.itemKindNo = itemKindNo;
	}

	/**
	 * 属性分户序号的getter方法
	 */

	@Column(name = "FAMILYNO")
	public BigDecimal getFamilyNo() {
		return this.familyNo;
	}

	/**
	 * 属性分户序号的setter方法
	 */
	public void setFamilyNo(BigDecimal familyNo) {
		this.familyNo = familyNo;
	}

	/**
	 * 属性分户名称的getter方法
	 */

	@Column(name = "FAMILYNAME")
	public String getFamilyName() {
		return this.familyName;
	}

	/**
	 * 属性分户名称的setter方法
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * 属性险别代码的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别代码的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * 属性标的项目类别代码的getter方法
	 */

	@Column(name = "ITEMCODE")
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 属性标的项目类别代码的setter方法
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 属性雇员编码的getter方法
	 */

	@Column(name = "EMPLOYEECODE")
	public String getEmployeeCode() {
		return this.employeeCode;
	}

	/**
	 * 属性雇员编码的setter方法
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * 属性雇员姓名的getter方法
	 */

	@Column(name = "EMPLOYEENAME")
	public String getEmployeeName() {
		return this.employeeName;
	}

	/**
	 * 属性雇员姓名的setter方法
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * 属性各种费用代码的getter方法
	 */

	@Column(name = "FEETYPECODE")
	public String getFeeTypeCode() {
		return this.feeTypeCode;
	}

	/**
	 * 属性各种费用代码的setter方法
	 */
	public void setFeeTypeCode(String feeTypeCode) {
		this.feeTypeCode = feeTypeCode;
	}

	/**
	 * 属性费用名称的getter方法
	 */

	@Column(name = "FEETYPENAME")
	public String getFeeTypeName() {
		return this.feeTypeName;
	}

	/**
	 * 属性费用名称的setter方法
	 */
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	/**
	 * 属性币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性赔偿限额的getter方法
	 */

	@Column(name = "INDEMNITYLIMIT")
	public BigDecimal getIndemnityLimit() {
		return this.indemnityLimit;
	}

	/**
	 * 属性赔偿限额的setter方法
	 */
	public void setIndemnityLimit(BigDecimal indemnityLimit) {
		this.indemnityLimit = indemnityLimit;
	}

	/**
	 * 属性收据数的getter方法
	 */

	@Column(name = "RECEIPTCOUNT")
	public BigDecimal getReceiptCount() {
		return this.receiptCount;
	}

	/**
	 * 属性收据数的setter方法
	 */
	public void setReceiptCount(BigDecimal receiptCount) {
		this.receiptCount = receiptCount;
	}

	/**
	 * 属性收据金额的getter方法
	 */

	@Column(name = "RECEIPTAMT")
	public BigDecimal getReceiptAmt() {
		return this.receiptAmt;
	}

	/**
	 * 属性收据金额的setter方法
	 */
	public void setReceiptAmt(BigDecimal receiptAmt) {
		this.receiptAmt = receiptAmt;
	}

	/**
	 * 属性收据日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RECEIPTDATE")
	public Date getReceiptDate() {
		return this.receiptDate;
	}

	/**
	 * 属性收据日期的setter方法
	 */
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	/**
	 * 属性误工天数的getter方法
	 */

	@Column(name = "MISSWORKDAYS")
	public BigDecimal getMissWorkDays() {
		return this.missWorkDays;
	}

	/**
	 * 属性误工天数的setter方法
	 */
	public void setMissWorkDays(BigDecimal missWorkDays) {
		this.missWorkDays = missWorkDays;
	}

	/**
	 * 属性受损金额的getter方法
	 */

	@Column(name = "SUMLOSS")
	public BigDecimal getSumLoss() {
		return this.sumLoss;
	}

	/**
	 * 属性受损金额的setter方法
	 */
	public void setSumLoss(BigDecimal sumLoss) {
		this.sumLoss = sumLoss;
	}

	/**
	 * 属性剔除金额的getter方法
	 */

	@Column(name = "SUMREJECT")
	public BigDecimal getSumReject() {
		return this.sumReject;
	}

	/**
	 * 属性剔除金额的setter方法
	 */
	public void setSumReject(BigDecimal sumReject) {
		this.sumReject = sumReject;
	}

	/**
	 * 属性剔除原因的getter方法
	 */

	@Column(name = "REJECTREASON")
	public String getRejectReason() {
		return this.rejectReason;
	}

	/**
	 * 属性剔除原因的setter方法
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * 属性赔偿比例的getter方法
	 */

	@Column(name = "LOSSRATE")
	public BigDecimal getLossRate() {
		return this.lossRate;
	}

	/**
	 * 属性赔偿比例的setter方法
	 */
	public void setLossRate(BigDecimal lossRate) {
		this.lossRate = lossRate;
	}

	/**
	 * 属性核定损金额的getter方法
	 */

	@Column(name = "SUMDEFLOSS")
	public BigDecimal getSumDefLoss() {
		return this.sumDefLoss;
	}

	/**
	 * 属性核定损金额的setter方法
	 */
	public void setSumDefLoss(BigDecimal sumDefLoss) {
		this.sumDefLoss = sumDefLoss;
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

}
