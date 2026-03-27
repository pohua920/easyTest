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
 * POJO类PrpCitemHouse
 */
@Entity
@Table(name = "PRPCITEMHOUSE")
public class PrpCitemHouse implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCitemHouseId id;

	/** 属性RISKCODE */
	private String riskCode;

	/** 属性STRUCTURE */
	private String structure;

	/** 属性BUILDAREA */
	private Double buildArea;

	/** 属性REMARK */
	private String remark;

	/** 属性FLAG */
	private String flag;

	/** 属性UNITVALUE */
	private Double unitValue;

	/** 属性SUMVALUE */
	private Double sumValue;

	/** 属性BUILDTIME */
	private Date buildTime;

	/** 属性USEAGE */
	private String useAge;

	/**
	 * 类PrpCitemHouse的默认构造方法
	 */
	public PrpCitemHouse() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "itemNo", column = @Column(name = "ITEMNO")) })
	public PrpCitemHouseId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCitemHouseId id) {
		this.id = id;
	}

	/**
	 * 属性RISKCODE的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性RISKCODE的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性STRUCTURE的getter方法
	 */

	@Column(name = "STRUCTURE")
	public String getStructure() {
		return this.structure;
	}

	/**
	 * 属性STRUCTURE的setter方法
	 */
	public void setStructure(String structure) {
		this.structure = structure;
	}

	/**
	 * 属性BUILDAREA的getter方法
	 */

	@Column(name = "BUILDAREA")
	public Double getBuildArea() {
		return this.buildArea;
	}

	/**
	 * 属性BUILDAREA的setter方法
	 */
	public void setBuildArea(Double buildArea) {
		this.buildArea = buildArea;
	}

	/**
	 * 属性REMARK的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性REMARK的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性UNITVALUE的getter方法
	 */

	@Column(name = "UNITVALUE")
	public Double getUnitValue() {
		return this.unitValue;
	}

	/**
	 * 属性UNITVALUE的setter方法
	 */
	public void setUnitValue(Double unitValue) {
		this.unitValue = unitValue;
	}

	/**
	 * 属性SUMVALUE的getter方法
	 */

	@Column(name = "SUMVALUE")
	public Double getSumValue() {
		return this.sumValue;
	}

	/**
	 * 属性SUMVALUE的setter方法
	 */
	public void setSumValue(Double sumValue) {
		this.sumValue = sumValue;
	}

	/**
	 * 属性BUILDTIME的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BUILDTIME")
	public Date getBuildTime() {
		return this.buildTime;
	}

	/**
	 * 属性BUILDTIME的setter方法
	 */
	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
	}

	/**
	 * 属性USEAGE的getter方法
	 */

	@Column(name = "USEAGE")
	public String getUseAge() {
		return this.useAge;
	}

	/**
	 * 属性USEAGE的setter方法
	 */
	public void setUseAge(String useAge) {
		this.useAge = useAge;
	}

}
