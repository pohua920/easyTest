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
 * POJO类PrpLreclaim
 */
@Entity
@Table(name = "PRPLRECLAIM")
public class PrpLreclaim implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLreclaimId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性标的项目类别代码 */
	private String itemCode;

	/** 属性标的项目名称 */
	private String itemName;

	/** 属性抵押物名称 */
	private String guarantyName;

	/** 属性抵押物处理方式 */
	private String dealWay;

	/** 属性币别 */
	private String currency;

	/** 属性数量 */
	private BigDecimal reclaimCount;

	/** 属性损余金额 */
	private BigDecimal lossFee;

	/** 属性交易费用 */
	private BigDecimal bargainFee;

	/** 属性保全财产名称 */
	private String propProtectName;

	/** 属性保全财产费用 */
	private BigDecimal propProtectFee;

	/** 属性回收金额 */
	private BigDecimal reclaimFee;

	/** 属性回收日期 */
	private Date reclaimDate;

	/** 属性摊销方式 */
	private String amortizeWay;

	/** 属性处理人员代码 */
	private String handlerCode;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性处理类型 */
	private String dealType;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLreclaim的默认构造方法
	 */
	public PrpLreclaim() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLreclaimId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLreclaimId id) {
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
	 * 属性标的项目名称的getter方法
	 */

	@Column(name = "ITEMNAME")
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * 属性标的项目名称的setter方法
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 属性抵押物名称的getter方法
	 */

	@Column(name = "GUARANTYNAME")
	public String getGuarantyName() {
		return this.guarantyName;
	}

	/**
	 * 属性抵押物名称的setter方法
	 */
	public void setGuarantyName(String guarantyName) {
		this.guarantyName = guarantyName;
	}

	/**
	 * 属性抵押物处理方式的getter方法
	 */

	@Column(name = "DEALWAY")
	public String getDealWay() {
		return this.dealWay;
	}

	/**
	 * 属性抵押物处理方式的setter方法
	 */
	public void setDealWay(String dealWay) {
		this.dealWay = dealWay;
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
	 * 属性数量的getter方法
	 */

	@Column(name = "RECLAIMCOUNT")
	public BigDecimal getReclaimCount() {
		return this.reclaimCount;
	}

	/**
	 * 属性数量的setter方法
	 */
	public void setReclaimCount(BigDecimal reclaimCount) {
		this.reclaimCount = reclaimCount;
	}

	/**
	 * 属性损余金额的getter方法
	 */

	@Column(name = "LOSSFEE")
	public BigDecimal getLossFee() {
		return this.lossFee;
	}

	/**
	 * 属性损余金额的setter方法
	 */
	public void setLossFee(BigDecimal lossFee) {
		this.lossFee = lossFee;
	}

	/**
	 * 属性交易费用的getter方法
	 */

	@Column(name = "BARGAINFEE")
	public BigDecimal getBargainFee() {
		return this.bargainFee;
	}

	/**
	 * 属性交易费用的setter方法
	 */
	public void setBargainFee(BigDecimal bargainFee) {
		this.bargainFee = bargainFee;
	}

	/**
	 * 属性保全财产名称的getter方法
	 */

	@Column(name = "PROPPROTECTNAME")
	public String getPropProtectName() {
		return this.propProtectName;
	}

	/**
	 * 属性保全财产名称的setter方法
	 */
	public void setPropProtectName(String propProtectName) {
		this.propProtectName = propProtectName;
	}

	/**
	 * 属性保全财产费用的getter方法
	 */

	@Column(name = "PROPPROTECTFEE")
	public BigDecimal getPropProtectFee() {
		return this.propProtectFee;
	}

	/**
	 * 属性保全财产费用的setter方法
	 */
	public void setPropProtectFee(BigDecimal propProtectFee) {
		this.propProtectFee = propProtectFee;
	}

	/**
	 * 属性回收金额的getter方法
	 */

	@Column(name = "RECLAIMFEE")
	public BigDecimal getReclaimFee() {
		return this.reclaimFee;
	}

	/**
	 * 属性回收金额的setter方法
	 */
	public void setReclaimFee(BigDecimal reclaimFee) {
		this.reclaimFee = reclaimFee;
	}

	/**
	 * 属性回收日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RECLAIMDATE")
	public Date getReclaimDate() {
		return this.reclaimDate;
	}

	/**
	 * 属性回收日期的setter方法
	 */
	public void setReclaimDate(Date reclaimDate) {
		this.reclaimDate = reclaimDate;
	}

	/**
	 * 属性摊销方式的getter方法
	 */

	@Column(name = "AMORTIZEWAY")
	public String getAmortizeWay() {
		return this.amortizeWay;
	}

	/**
	 * 属性摊销方式的setter方法
	 */
	public void setAmortizeWay(String amortizeWay) {
		this.amortizeWay = amortizeWay;
	}

	/**
	 * 属性处理人员代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性处理人员代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性处理类型的getter方法
	 */

	@Column(name = "DEALTYPE")
	public String getDealType() {
		return this.dealType;
	}

	/**
	 * 属性处理类型的setter方法
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
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
