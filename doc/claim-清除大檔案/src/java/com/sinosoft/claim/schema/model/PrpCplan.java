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
 * POJO类PrpCplan
 */
@Entity
@Table(name = "PRPCPLAN")
public class PrpCplan implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCplanId id;

	/** 属性批单号码 */
	private String endorseNo;

	/** 属性交费期次 */
	private Integer payNo;

	/** 属性交费原因 */
	private String payReason;

	/** 属性计划交费截止日期 */
	private Date planDate;

	/** 属性币别代码 */
	private String currency;

	/** 属性应交费金额 */
	private Double planFee;

	/** 属性拖欠金额 */
	private Double delinquentFee;

	/** 属性标志字段 */
	private String flag;

	/** 属性PLANSTARTDATE */
	private Date planStartDate;

	/** 属性CURRENCY2 */
	private String currency2;

	/** 属性EXCHANGERATECNY */
	private Double exchangeratecny;

	/** 属性PLANFEE2 */
	private Double planfee2;

	/**
	 * 类PrpCplan的默认构造方法
	 */
	public PrpCplan() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpCplanId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCplanId id) {
		this.id = id;
	}

	/**
	 * 属性批单号码的getter方法
	 */

	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性批单号码的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	/**
	 * 属性交费期次的getter方法
	 */

	@Column(name = "PAYNO")
	public Integer getPayNo() {
		return this.payNo;
	}

	/**
	 * 属性交费期次的setter方法
	 */
	public void setPayNo(Integer payNo) {
		this.payNo = payNo;
	}

	/**
	 * 属性交费原因的getter方法
	 */

	@Column(name = "PAYREASON")
	public String getPayReason() {
		return this.payReason;
	}

	/**
	 * 属性交费原因的setter方法
	 */
	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}

	/**
	 * 属性计划交费截止日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PLANDATE")
	public Date getPlanDate() {
		return this.planDate;
	}

	/**
	 * 属性计划交费截止日期的setter方法
	 */
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	/**
	 * 属性币别代码的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别代码的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性应交费金额的getter方法
	 */

	@Column(name = "PLANFEE")
	public Double getPlanFee() {
		return this.planFee;
	}

	/**
	 * 属性应交费金额的setter方法
	 */
	public void setPlanFee(Double planFee) {
		this.planFee = planFee;
	}

	/**
	 * 属性拖欠金额的getter方法
	 */

	@Column(name = "DELINQUENTFEE")
	public Double getDelinquentFee() {
		return this.delinquentFee;
	}

	/**
	 * 属性拖欠金额的setter方法
	 */
	public void setDelinquentFee(Double delinquentFee) {
		this.delinquentFee = delinquentFee;
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
	 * 属性PLANSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PLANSTARTDATE")
	public Date getPlanStartDate() {
		return this.planStartDate;
	}

	/**
	 * 属性PLANSTARTDATE的setter方法
	 */
	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
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
	 * 属性EXCHANGERATECNY的getter方法
	 */

	@Column(name = "EXCHANGERATECNY")
	public Double getExchangeratecny() {
		return this.exchangeratecny;
	}

	/**
	 * 属性EXCHANGERATECNY的setter方法
	 */
	public void setExchangeratecny(Double exchangeratecny) {
		this.exchangeratecny = exchangeratecny;
	}

	/**
	 * 属性PLANFEE2的getter方法
	 */

	@Column(name = "PLANFEE2")
	public Double getPlanfee2() {
		return this.planfee2;
	}

	/**
	 * 属性PLANFEE2的setter方法
	 */
	public void setPlanfee2(Double planfee2) {
		this.planfee2 = planfee2;
	}

}
