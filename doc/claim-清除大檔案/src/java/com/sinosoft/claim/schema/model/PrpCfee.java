package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpCfee
 */
@Entity
@Table(name = "PRPCFEE")
public class PrpCfee implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCfeeId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性总保额 */
	private Double amount;

	/** 属性总保费 */
	private Double premium;

	/** 属性标志字段 */
	private String flag;

	/** 属性CURRENCY1 */
	private String currency1;

	/** 属性EXCHANGERATE1 */
	private Double exchangerate1;

	/** 属性AMOUNT1 */
	private Double amount1;

	/** 属性PREMIUM1 */
	private Double premium1;

	/** 属性CURRENCY2 */
	private String currency2;

	/** 属性EXCHANGERATE2 */
	private Double exchangerate2;

	/** 属性AMOUNT2 */
	private Double amount2;

	/** 属性PREMIUM2 */
	private Double premium2;

	/**
	 * 类PrpCfee的默认构造方法
	 */
	public PrpCfee() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "currency", column = @Column(name = "CURRENCY")) })
	public PrpCfeeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCfeeId id) {
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
	 * 属性总保额的getter方法
	 */

	@Column(name = "AMOUNT")
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * 属性总保额的setter方法
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 属性总保费的getter方法
	 */

	@Column(name = "PREMIUM")
	public Double getPremium() {
		return this.premium;
	}

	/**
	 * 属性总保费的setter方法
	 */
	public void setPremium(Double premium) {
		this.premium = premium;
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
	 * 属性CURRENCY1的getter方法
	 */

	@Column(name = "CURRENCY1")
	public String getCurrency1() {
		return this.currency1;
	}

	/**
	 * 属性CURRENCY1的setter方法
	 */
	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	/**
	 * 属性EXCHANGERATE1的getter方法
	 */

	@Column(name = "EXCHANGERATE1")
	public Double getExchangerate1() {
		return this.exchangerate1;
	}

	/**
	 * 属性EXCHANGERATE1的setter方法
	 */
	public void setExchangerate1(Double exchangerate1) {
		this.exchangerate1 = exchangerate1;
	}

	/**
	 * 属性AMOUNT1的getter方法
	 */

	@Column(name = "AMOUNT1")
	public Double getAmount1() {
		return this.amount1;
	}

	/**
	 * 属性AMOUNT1的setter方法
	 */
	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}

	/**
	 * 属性PREMIUM1的getter方法
	 */

	@Column(name = "PREMIUM1")
	public Double getPremium1() {
		return this.premium1;
	}

	/**
	 * 属性PREMIUM1的setter方法
	 */
	public void setPremium1(Double premium1) {
		this.premium1 = premium1;
	}

	/**
	 * 属性CURRENCY2的getter方法
	 */

	@Column(name = "CURRENCY2")
	public String getCurrency2() {
		return this.currency2;
	}

	/**
	 * 属性CURRENCY2的setter方法
	 */
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}

	/**
	 * 属性EXCHANGERATE2的getter方法
	 */

	@Column(name = "EXCHANGERATE2")
	public Double getExchangerate2() {
		return this.exchangerate2;
	}

	/**
	 * 属性EXCHANGERATE2的setter方法
	 */
	public void setExchangerate2(Double exchangerate2) {
		this.exchangerate2 = exchangerate2;
	}

	/**
	 * 属性AMOUNT2的getter方法
	 */

	@Column(name = "AMOUNT2")
	public Double getAmount2() {
		return this.amount2;
	}

	/**
	 * 属性AMOUNT2的setter方法
	 */
	public void setAmount2(Double amount2) {
		this.amount2 = amount2;
	}

	/**
	 * 属性PREMIUM2的getter方法
	 */

	@Column(name = "PREMIUM2")
	public Double getPremium2() {
		return this.premium2;
	}

	/**
	 * 属性PREMIUM2的setter方法
	 */
	public void setPremium2(Double premium2) {
		this.premium2 = premium2;
	}

}
