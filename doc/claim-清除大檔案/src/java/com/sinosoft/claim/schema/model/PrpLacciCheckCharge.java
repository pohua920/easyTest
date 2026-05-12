package com.sinosoft.claim.schema.model;

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
 * POJO类PrpLacciCheckCharge
 */
@Entity
@Table(name = "PRPLACCICHECKCHARGE")
public class PrpLacciCheckCharge implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLacciCheckChargeId id;

	/** 属性意健险调查主表 */
	private PrpLacciCheck prpLacciCheck;

	/** 属性费用类别代码 */
	private String chargeCode;

	/** 属性费用名称 */
	private String chargeName;

	/** 属性调查费用币别 */
	private String currency;

	/** 属性费用金额 */
	private Double chargeAmount;

	/**
	 * 类PrpLacciCheckCharge的默认构造方法
	 */
	public PrpLacciCheckCharge() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "checkNo", column = @Column(name = "CHECKNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLacciCheckChargeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLacciCheckChargeId id) {
		this.id = id;
	}

	/**
	 * 属性意健险调查主表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHECKNO", nullable = false, insertable = false, updatable = false)
	public PrpLacciCheck getPrpLacciCheck() {
		return this.prpLacciCheck;
	}

	/**
	 * 属性意健险调查主表的setter方法
	 */
	public void setPrpLacciCheck(PrpLacciCheck prpLacciCheck) {
		this.prpLacciCheck = prpLacciCheck;
	}

	/**
	 * 属性费用类别代码的getter方法
	 */

	@Column(name = "CHARGECODE")
	public String getChargeCode() {
		return this.chargeCode;
	}

	/**
	 * 属性费用类别代码的setter方法
	 */
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	/**
	 * 属性费用名称的getter方法
	 */

	@Column(name = "CHARGENAME")
	public String getChargeName() {
		return this.chargeName;
	}

	/**
	 * 属性费用名称的setter方法
	 */
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	/**
	 * 属性调查费用币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性调查费用币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性费用金额的getter方法
	 */

	@Column(name = "CHARGEAMOUNT")
	public Double getChargeAmount() {
		return this.chargeAmount;
	}

	/**
	 * 属性费用金额的setter方法
	 */
	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

}
