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
 * POJO类PrpLclaimPolicy
 */
@Entity
@Table(name = "PRPLCLAIMPOLICY")
public class PrpLclaimPolicy implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLclaimPolicyId id;

	/** 属性币别 */
	private String currency;

	/** 属性总保额 */
	private BigDecimal sumAmount;

	/** 属性总保费 */
	private BigDecimal sumPremium;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLclaimPolicy的默认构造方法
	 */
	public PrpLclaimPolicy() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")) })
	public PrpLclaimPolicyId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLclaimPolicyId id) {
		this.id = id;
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
	 * 属性总保额的getter方法
	 */

	@Column(name = "SUMAMOUNT")
	public BigDecimal getSumAmount() {
		return this.sumAmount;
	}

	/**
	 * 属性总保额的setter方法
	 */
	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 属性总保费的getter方法
	 */

	@Column(name = "SUMPREMIUM")
	public BigDecimal getSumPremium() {
		return this.sumPremium;
	}

	/**
	 * 属性总保费的setter方法
	 */
	public void setSumPremium(BigDecimal sumPremium) {
		this.sumPremium = sumPremium;
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
