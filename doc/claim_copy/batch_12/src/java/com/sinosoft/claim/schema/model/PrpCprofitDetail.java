package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpCprofitDetail
 */
@Entity
@Table(name = "PRPCPROFITDETAIL")
public class PrpCprofitDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCprofitDetailId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性期次 */
	private Integer profitPeriod;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性险别名称 */
	private String kindName;

	/** 属性优惠折扣名称 */
	private String profitName;

	/** 属性条件描述 */
	private String condition;

	/** 属性条件具体值 */
	private Double fieldValue;

	/** 属性优惠折扣比率(%) */
	private Double profitRate;

	/** 属性该条件是否选中标志 */
	private String chooseFlag;

	/** 属性标志字段 */
	private String flag;

	/** 属性CONDITIONCODE */
	private String conditionCode;

	/**
	 * 类PrpCprofitDetail的默认构造方法
	 */
	public PrpCprofitDetail() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "profitType", column = @Column(name = "PROFITTYPE")),
			@AttributeOverride(name = "itemKindNo", column = @Column(name = "ITEMKINDNO")), @AttributeOverride(name = "profitCode", column = @Column(name = "PROFITCODE")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpCprofitDetailId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCprofitDetailId id) {
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
	 * 属性期次的getter方法
	 */

	@Column(name = "PROFITPERIOD")
	public Integer getProfitPeriod() {
		return this.profitPeriod;
	}

	/**
	 * 属性期次的setter方法
	 */
	public void setProfitPeriod(Integer profitPeriod) {
		this.profitPeriod = profitPeriod;
	}

	/**
	 * 属性险别代码的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别代码的setter方法
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
	 * 属性优惠折扣名称的getter方法
	 */

	@Column(name = "PROFITNAME")
	public String getProfitName() {
		return this.profitName;
	}

	/**
	 * 属性优惠折扣名称的setter方法
	 */
	public void setProfitName(String profitName) {
		this.profitName = profitName;
	}

	/**
	 * 属性条件描述的getter方法
	 */

	@Column(name = "CONDITION")
	public String getCondition() {
		return this.condition;
	}

	/**
	 * 属性条件描述的setter方法
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 属性条件具体值的getter方法
	 */

	@Column(name = "FIELDVALUE")
	public Double getFieldValue() {
		return this.fieldValue;
	}

	/**
	 * 属性条件具体值的setter方法
	 */
	public void setFieldValue(Double fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * 属性优惠折扣比率(%)的getter方法
	 */

	@Column(name = "PROFITRATE")
	public Double getProfitRate() {
		return this.profitRate;
	}

	/**
	 * 属性优惠折扣比率(%)的setter方法
	 */
	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
	}

	/**
	 * 属性该条件是否选中标志的getter方法
	 */

	@Column(name = "CHOOSEFLAG")
	public String getChooseFlag() {
		return this.chooseFlag;
	}

	/**
	 * 属性该条件是否选中标志的setter方法
	 */
	public void setChooseFlag(String chooseFlag) {
		this.chooseFlag = chooseFlag;
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
	 * 属性CONDITIONCODE的getter方法
	 */

	@Column(name = "CONDITIONCODE")
	public String getConditionCode() {
		return this.conditionCode;
	}

	/**
	 * 属性CONDITIONCODE的setter方法
	 */
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}

}
