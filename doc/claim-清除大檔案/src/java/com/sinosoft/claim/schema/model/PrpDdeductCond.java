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
 * 免赔条件表的数据传输对象类POJO类PrpDdeductcond
 */
@Entity
@Table(name = "PRPDDEDUCTCOND")
public class PrpDdeductCond implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDdeductCondId id;

	/** 属性免赔条件名称 */
	private String deductCondName;

	/** 属性免赔率 */
	private Double deductRate;

	/** 属性免赔额 */
	private Double deductible;

	/** 属性启用日期 */
	private Date validdate;

	/** 属性标志位 */
	private String flag;

	/** 属性开始计算绝对免赔率的出险次数 */
	private Integer basetimes;

	/** 属性封顶免赔率 */
	private Double maxDeductRate;

	/**
	 * 免赔条件表的数据传输对象的默认构造方法
	 */
	public PrpDdeductCond() {
		this.id = new PrpDdeductCondId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")), @AttributeOverride(name = "clausetype", column = @Column(name = "CLAUSETYPE")),
			@AttributeOverride(name = "kindcode", column = @Column(name = "KINDCODE")), @AttributeOverride(name = "deductcondcode", column = @Column(name = "DEDUCTCONDCODE")),
			@AttributeOverride(name = "deductperiod", column = @Column(name = "DEDUCTPERIOD")) })
	public PrpDdeductCondId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDdeductCondId id) {
		this.id = id;
	}

	/**
	 * 属性免赔条件名称的getter方法
	 */

	@Column(name = "DEDUCTCONDNAME")
	public String getDeductCondName() {
		return this.deductCondName;
	}

	/**
	 * 属性免赔条件名称的setter方法
	 */
	public void setDeductCondName(String deductCondName) {
		this.deductCondName = deductCondName;
	}

	/**
	 * 属性免赔率的getter方法
	 */

	@Column(name = "DEDUCTRATE")
	public Double getDeductRate() {
		return this.deductRate;
	}

	/**
	 * 属性免赔率的setter方法
	 */
	public void setDeductRate(Double deductRate) {
		this.deductRate = deductRate;
	}

	/**
	 * 属性免赔额的getter方法
	 */

	@Column(name = "DEDUCTIBLE")
	public Double getDeductible() {
		return this.deductible;
	}

	/**
	 * 属性免赔额的setter方法
	 */
	public void setDeductible(Double deductible) {
		this.deductible = deductible;
	}

	/**
	 * 属性启用日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE")
	public Date getValiddate() {
		return this.validdate;
	}

	/**
	 * 属性启用日期的setter方法
	 */
	public void setValiddate(Date validdate) {
		this.validdate = validdate;
	}

	/**
	 * 属性标志位的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志位的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性开始计算绝对免赔率的出险次数的getter方法
	 */

	@Column(name = "BASETIMES")
	public Integer getBasetimes() {
		return this.basetimes;
	}

	/**
	 * 属性开始计算绝对免赔率的出险次数的setter方法
	 */
	public void setBasetimes(Integer basetimes) {
		this.basetimes = basetimes;
	}

	/**
	 * 属性封顶免赔率的getter方法
	 */

	@Column(name = "MAXDEDUCTRATE")
	public Double getMaxDeductRate() {
		return this.maxDeductRate;
	}

	/**
	 * 属性封顶免赔率的setter方法
	 */
	public void setMaxDeductRate(Double maxDeductRate) {
		this.maxDeductRate = maxDeductRate;
	}

}
