package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLcfee
 */
@Entity
@Table(name = "PRPLCFEE")
public class PrpLcfee implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcfeeId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性赔付金额 */
	private double sumPaid;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLcfee的默认构造方法
	 */
	public PrpLcfee() {
		this.id = new PrpLcfeeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")),
			@AttributeOverride(name = "currency", column = @Column(name = "CURRENCY")) })
	public PrpLcfeeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcfeeId id) {
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
	 * 属性赔付金额的getter方法
	 */

	@Column(name = "SUMPAID")
	public double getSumPaid() {
		return this.sumPaid;
	}

	/**
	 * 属性赔付金额的setter方法
	 */
	public void setSumPaid(double sumPaid) {
		this.sumPaid = sumPaid;
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
