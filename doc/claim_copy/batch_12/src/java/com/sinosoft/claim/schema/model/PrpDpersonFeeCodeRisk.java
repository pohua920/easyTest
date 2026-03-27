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
import javax.persistence.Transient;

/**
 * POJO类PrpDpersonFeeCodeRisk
 */
@Entity
@Table(name = "PRPDPERSONFEECODERISK")
public class PrpDpersonFeeCodeRisk implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDpersonFeeCodeRiskId id;

	/** 属性所属类别 */
	private String feeCategory;

	/** 属性强制保险优先级 */
	private Integer priority;

	/** 属性有效状态位 */
	private Integer validStatus;

	/** 属性有效日期 */
	private Date validDate;

	/** 属性费用代码 */
	private String feeName;

	/**
	 * 类PrpDpersonFeeCodeRisk的默认构造方法
	 */
	public PrpDpersonFeeCodeRisk() {
		id = new PrpDpersonFeeCodeRiskId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "feeCode", column = @Column(name = "FEECODE")), @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")) })
	public PrpDpersonFeeCodeRiskId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDpersonFeeCodeRiskId id) {
		this.id = id;
	}

	/**
	 * 属性所属类别的getter方法
	 */

	@Column(name = "FEECATEGORY")
	public String getFeeCategory() {
		return this.feeCategory;
	}

	/**
	 * 属性所属类别的setter方法
	 */
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
	}

	/**
	 * 属性强制保险优先级的getter方法
	 */

	@Column(name = "PRIORITY")
	public Integer getPriority() {
		return this.priority;
	}

	/**
	 * 属性强制保险优先级的setter方法
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * 属性有效状态位的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public Integer getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性有效状态位的setter方法
	 */
	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性有效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE")
	public Date getValidDate() {
		return this.validDate;
	}

	/**
	 * 属性有效日期的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	@Transient
	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

}
