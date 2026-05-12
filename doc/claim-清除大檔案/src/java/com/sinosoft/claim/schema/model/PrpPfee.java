package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * POJO类PrpPfee
 */
@Entity
@Table(name = "PRPPFEE")
public class PrpPfee implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpPfeeId id;

	/** 属性批改信息表 */
	private PrpPhead prpPhead;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单批改前原币总保额 */
	private Double amount;

	/** 属性保单批改前原币总保费 */
	private Double premium;

	/** 属性批改前支付币别 */
	private String currency1;

	/** 属性原币和支付币别兑换率 */
	private Double exchangeRate1;

	/** 属性批改前折合支付币别总保额 */
	private Double amount1;

	/** 属性批改前折合支付币别总保费 */
	private Double premium1;

	/** 属性批改前打印币别 */
	private String currency2;

	/** 属性原币和打印币别兑换率 */
	private Double exchangeRate2;

	/** 属性批改前折合打印币别总保额 */
	private Double amount2;

	/** 属性改前折合打印币别总保费 */
	private Double premium2;

	/** 属性保额变化量 */
	private Double chgAmount;

	/** 属性保费变化量 */
	private Double chgPremium;

	/** 属性折合支付币别总保额变化量 */
	private Double chgAmount1;

	/** 属性折合支付币别总保费变化量 */
	private Double chgPremium1;

	/** 属性折合打印币别总保额变化量 */
	private Double chgAmount2;

	/** 属性折合打印币别总保费变化量 */
	private Double chgPremium2;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpPfee的默认构造方法
	 */
	public PrpPfee() {
		id = new PrpPfeeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "endorseNo", column = @Column(name = "ENDORSENO")), @AttributeOverride(name = "currency", column = @Column(name = "CURRENCY")) })
	public PrpPfeeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpPfeeId id) {
		this.id = id;
	}

	/**
	 * 属性批改信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENDORSENO", nullable = false, insertable = false, updatable = false)
	public PrpPhead getPrpPhead() {
		return this.prpPhead;
	}

	/**
	 * 属性批改信息表的setter方法
	 */
	public void setPrpPhead(PrpPhead prpPhead) {
		this.prpPhead = prpPhead;
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
	 * 属性保单批改前原币总保额的getter方法
	 */

	@Column(name = "AMOUNT")
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * 属性保单批改前原币总保额的setter方法
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 属性保单批改前原币总保费的getter方法
	 */

	@Column(name = "PREMIUM")
	public Double getPremium() {
		return this.premium;
	}

	/**
	 * 属性保单批改前原币总保费的setter方法
	 */
	public void setPremium(Double premium) {
		this.premium = premium;
	}

	/**
	 * 属性批改前支付币别的getter方法
	 */

	@Column(name = "CURRENCY1")
	public String getCurrency1() {
		return this.currency1;
	}

	/**
	 * 属性批改前支付币别的setter方法
	 */
	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	/**
	 * 属性原币和支付币别兑换率的getter方法
	 */

	@Column(name = "EXCHANGERATE1")
	public Double getExchangeRate1() {
		return this.exchangeRate1;
	}

	/**
	 * 属性原币和支付币别兑换率的setter方法
	 */
	public void setExchangeRate1(Double exchangeRate1) {
		this.exchangeRate1 = exchangeRate1;
	}

	/**
	 * 属性批改前折合支付币别总保额的getter方法
	 */

	@Column(name = "AMOUNT1")
	public Double getAmount1() {
		return this.amount1;
	}

	/**
	 * 属性批改前折合支付币别总保额的setter方法
	 */
	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}

	/**
	 * 属性批改前折合支付币别总保费的getter方法
	 */

	@Column(name = "PREMIUM1")
	public Double getPremium1() {
		return this.premium1;
	}

	/**
	 * 属性批改前折合支付币别总保费的setter方法
	 */
	public void setPremium1(Double premium1) {
		this.premium1 = premium1;
	}

	/**
	 * 属性批改前打印币别的getter方法
	 */

	@Column(name = "CURRENCY2")
	public String getCurrency2() {
		return this.currency2;
	}

	/**
	 * 属性批改前打印币别的setter方法
	 */
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}

	/**
	 * 属性原币和打印币别兑换率的getter方法
	 */

	@Column(name = "EXCHANGERATE2")
	public Double getExchangeRate2() {
		return this.exchangeRate2;
	}

	/**
	 * 属性原币和打印币别兑换率的setter方法
	 */
	public void setExchangeRate2(Double exchangeRate2) {
		this.exchangeRate2 = exchangeRate2;
	}

	/**
	 * 属性批改前折合打印币别总保额的getter方法
	 */

	@Column(name = "AMOUNT2")
	public Double getAmount2() {
		return this.amount2;
	}

	/**
	 * 属性批改前折合打印币别总保额的setter方法
	 */
	public void setAmount2(Double amount2) {
		this.amount2 = amount2;
	}

	/**
	 * 属性改前折合打印币别总保费的getter方法
	 */

	@Column(name = "PREMIUM2")
	public Double getPremium2() {
		return this.premium2;
	}

	/**
	 * 属性改前折合打印币别总保费的setter方法
	 */
	public void setPremium2(Double premium2) {
		this.premium2 = premium2;
	}

	/**
	 * 属性保额变化量的getter方法
	 */

	@Column(name = "CHGAMOUNT")
	public Double getChgAmount() {
		return this.chgAmount;
	}

	/**
	 * 属性保额变化量的setter方法
	 */
	public void setChgAmount(Double chgAmount) {
		this.chgAmount = chgAmount;
	}

	/**
	 * 属性保费变化量的getter方法
	 */

	@Column(name = "CHGPREMIUM")
	public Double getChgPremium() {
		return this.chgPremium;
	}

	/**
	 * 属性保费变化量的setter方法
	 */
	public void setChgPremium(Double chgPremium) {
		this.chgPremium = chgPremium;
	}

	/**
	 * 属性折合支付币别总保额变化量的getter方法
	 */

	@Column(name = "CHGAMOUNT1")
	public Double getChgAmount1() {
		return this.chgAmount1;
	}

	/**
	 * 属性折合支付币别总保额变化量的setter方法
	 */
	public void setChgAmount1(Double chgAmount1) {
		this.chgAmount1 = chgAmount1;
	}

	/**
	 * 属性折合支付币别总保费变化量的getter方法
	 */

	@Column(name = "CHGPREMIUM1")
	public Double getChgPremium1() {
		return this.chgPremium1;
	}

	/**
	 * 属性折合支付币别总保费变化量的setter方法
	 */
	public void setChgPremium1(Double chgPremium1) {
		this.chgPremium1 = chgPremium1;
	}

	/**
	 * 属性折合打印币别总保额变化量的getter方法
	 */

	@Column(name = "CHGAMOUNT2")
	public Double getChgAmount2() {
		return this.chgAmount2;
	}

	/**
	 * 属性折合打印币别总保额变化量的setter方法
	 */
	public void setChgAmount2(Double chgAmount2) {
		this.chgAmount2 = chgAmount2;
	}

	/**
	 * 属性折合打印币别总保费变化量的getter方法
	 */

	@Column(name = "CHGPREMIUM2")
	public Double getChgPremium2() {
		return this.chgPremium2;
	}

	/**
	 * 属性折合打印币别总保费变化量的setter方法
	 */
	public void setChgPremium2(Double chgPremium2) {
		this.chgPremium2 = chgPremium2;
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
