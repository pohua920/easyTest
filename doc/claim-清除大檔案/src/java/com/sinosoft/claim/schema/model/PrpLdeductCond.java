package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLdeductCond计算书免赔条件表
 */
@Entity
@Table(name = "PRPLDEDUCTCOND")
public class PrpLdeductCond implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLdeductCondId id;

	/** 属性免赔条件名称 */
	private String deductCondName;

	/** 属性用户输入的出险次数或单证个数 */
	private int times;

	/**
	 * 类PrpLdeductCond的默认构造方法
	 */
	public PrpLdeductCond() {
		this.id = new PrpLdeductCondId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "deductCondCode", column = @Column(name = "DEDUCTCONDCODE")) })
	public PrpLdeductCondId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLdeductCondId id) {
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
	 * 属性用户输入的出险次数或单证个数的getter方法
	 */

	@Column(name = "TIMES")
	public int getTimes() {
		return this.times;
	}

	/**
	 * 属性用户输入的出险次数或单证个数的setter方法
	 */
	public void setTimes(int times) {
		this.times = times;
	}

}
