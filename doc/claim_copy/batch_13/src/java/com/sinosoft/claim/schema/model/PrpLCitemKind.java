package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLCitemKind代赔保单投保险别表
 */
@Entity
@Table(name = "PRPLCITEMKIND")
public class PrpLCitemKind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLCitemKindId id;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险种 */
	private String riskCode;

	/** 属性险别 */
	private String kindCode;

	/** 属性险别名称 */
	private String kindName;

	/** 属性标的代码 */
	private String itemCode;

	/** 属性标的项目明细名称 */
	private String itemDetailName;

	/** 属性投保方式/责任分类 */
	private String modeCode;

	/** 属性投保方式名称 */
	private String modeName;

	/** 属性币别 */
	private String currency;

	/** 属性CALCULATEFLAG */
	private String calculateFlag;

	/** 属性单位保险金额（赔偿限额） */
	private BigDecimal unitAmount;

	/** 属性数量 */
	private BigDecimal quantity;

	/** 属性保险价值保险金额/赔偿限额 */
	private BigDecimal value;

	/** 属性保险金额/赔偿限额 */
	private BigDecimal amount;

	/** 属性费率 */
	private BigDecimal rate;

	/** 属性短期费率标志 */
	private String shortRateFlag;

	/** 属性基准保费 */
	private BigDecimal basePremium;

	/** 属性标准保费 */
	private BigDecimal benchMarkPremium;

	/** 属性折扣率(%) */
	private BigDecimal discount;

	/** 属性保费调整比率(%) */
	private BigDecimal adjustRate;

	/** 属性保费/储金 */
	private BigDecimal premium;

	/** 属性DEDUCTIBLERATE */
	private BigDecimal deductiblerate;

	/** 属性DEDUCTIBLE */
	private BigDecimal deductible;

	/** 属性短期费率 */
	private BigDecimal shortRate;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLCitemKind的默认构造方法
	 */
	public PrpLCitemKind() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registno", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")),
			@AttributeOverride(name = "itemKindNo", column = @Column(name = "ITEMKINDNO")) })
	public PrpLCitemKindId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLCitemKindId id) {
		this.id = id;
	}

	/**
	 * 属性立案号的getter方法
	 */

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
	 * 属性险别的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * 属性险别名称的getter方法
	 */

	@Column(name = "KINDNAME")
	public String getKindName() {
		return this.kindName;
	}

	/**
	 * 属性险别名称的setter方法
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	/**
	 * 属性标的代码的getter方法
	 */

	@Column(name = "ITEMCODE")
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 属性标的代码的setter方法
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 属性标的项目明细名称的getter方法
	 */

	@Column(name = "ITEMDETAILNAME")
	public String getItemDetailName() {
		return this.itemDetailName;
	}

	/**
	 * 属性标的项目明细名称的setter方法
	 */
	public void setItemDetailName(String itemDetailName) {
		this.itemDetailName = itemDetailName;
	}

	/**
	 * 属性投保方式/责任分类的getter方法
	 */

	@Column(name = "MODECODE")
	public String getModeCode() {
		return this.modeCode;
	}

	/**
	 * 属性投保方式/责任分类的setter方法
	 */
	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	/**
	 * 属性投保方式名称的getter方法
	 */

	@Column(name = "MODENAME")
	public String getModeName() {
		return this.modeName;
	}

	/**
	 * 属性投保方式名称的setter方法
	 */
	public void setModeName(String modeName) {
		this.modeName = modeName;
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
	 * 属性CALCULATEFLAG的getter方法
	 */

	@Column(name = "CALCULATEFLAG")
	public String getCalculateFlag() {
		return this.calculateFlag;
	}

	/**
	 * 属性CALCULATEFLAG的setter方法
	 */
	public void setCalculateFlag(String calculateFlag) {
		this.calculateFlag = calculateFlag;
	}

	/**
	 * 属性单位保险金额（赔偿限额）的getter方法
	 */

	@Column(name = "UNITAMOUNT")
	public BigDecimal getUnitAmount() {
		return this.unitAmount;
	}

	/**
	 * 属性单位保险金额（赔偿限额）的setter方法
	 */
	public void setUnitAmount(BigDecimal unitAmount) {
		this.unitAmount = unitAmount;
	}

	/**
	 * 属性数量的getter方法
	 */

	@Column(name = "QUANTITY")
	public BigDecimal getQuantity() {
		return this.quantity;
	}

	/**
	 * 属性数量的setter方法
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * 属性保险价值保险金额/赔偿限额的getter方法
	 */

	@Column(name = "VALUE")
	public BigDecimal getValue() {
		return this.value;
	}

	/**
	 * 属性保险价值保险金额/赔偿限额的setter方法
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * 属性保险金额/赔偿限额的getter方法
	 */

	@Column(name = "AMOUNT")
	public BigDecimal getAmount() {
		return this.amount;
	}

	/**
	 * 属性保险金额/赔偿限额的setter方法
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 属性费率的getter方法
	 */

	@Column(name = "RATE")
	public BigDecimal getRate() {
		return this.rate;
	}

	/**
	 * 属性费率的setter方法
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * 属性短期费率标志的getter方法
	 */

	@Column(name = "SHORTRATEFLAG")
	public String getShortRateFlag() {
		return this.shortRateFlag;
	}

	/**
	 * 属性短期费率标志的setter方法
	 */
	public void setShortRateFlag(String shortRateFlag) {
		this.shortRateFlag = shortRateFlag;
	}

	/**
	 * 属性基准保费的getter方法
	 */

	@Column(name = "BASEPREMIUM")
	public BigDecimal getBasePremium() {
		return this.basePremium;
	}

	/**
	 * 属性基准保费的setter方法
	 */
	public void setBasePremium(BigDecimal basePremium) {
		this.basePremium = basePremium;
	}

	/**
	 * 属性标准保费的getter方法
	 */

	@Column(name = "BENCHMARKPREMIUM")
	public BigDecimal getBenchMarkPremium() {
		return this.benchMarkPremium;
	}

	/**
	 * 属性标准保费的setter方法
	 */
	public void setBenchMarkPremium(BigDecimal benchMarkPremium) {
		this.benchMarkPremium = benchMarkPremium;
	}

	/**
	 * 属性折扣率(%)的getter方法
	 */

	@Column(name = "DISCOUNT")
	public BigDecimal getDiscount() {
		return this.discount;
	}

	/**
	 * 属性折扣率(%)的setter方法
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	/**
	 * 属性保费调整比率(%)的getter方法
	 */

	@Column(name = "ADJUSTRATE")
	public BigDecimal getAdjustRate() {
		return this.adjustRate;
	}

	/**
	 * 属性保费调整比率(%)的setter方法
	 */
	public void setAdjustRate(BigDecimal adjustRate) {
		this.adjustRate = adjustRate;
	}

	/**
	 * 属性保费/储金的getter方法
	 */

	@Column(name = "PREMIUM")
	public BigDecimal getPremium() {
		return this.premium;
	}

	/**
	 * 属性保费/储金的setter方法
	 */
	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	/**
	 * 属性DEDUCTIBLERATE的getter方法
	 */

	@Column(name = "DEDUCTIBLERATE")
	public BigDecimal getDeductiblerate() {
		return this.deductiblerate;
	}

	/**
	 * 属性DEDUCTIBLERATE的setter方法
	 */
	public void setDeductiblerate(BigDecimal deductiblerate) {
		this.deductiblerate = deductiblerate;
	}

	/**
	 * 属性DEDUCTIBLE的getter方法
	 */

	@Column(name = "DEDUCTIBLE")
	public BigDecimal getDeductible() {
		return this.deductible;
	}

	/**
	 * 属性DEDUCTIBLE的setter方法
	 */
	public void setDeductible(BigDecimal deductible) {
		this.deductible = deductible;
	}

	/**
	 * 属性短期费率的getter方法
	 */

	@Column(name = "SHORTRATE")
	public BigDecimal getShortRate() {
		return this.shortRate;
	}

	/**
	 * 属性短期费率的setter方法
	 */
	public void setShortRate(BigDecimal shortRate) {
		this.shortRate = shortRate;
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
