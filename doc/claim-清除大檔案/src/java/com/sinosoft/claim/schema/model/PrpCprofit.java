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
 * POJO类PrpCprofit
 */
@Entity
@Table(name = "PRPCPROFIT")
public class PrpCprofit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCprofitId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性币别代码 */
	private String currency;

	/** 属性总优惠(折扣)率(%) */
	private Double discount;

	/** 属性优惠（折扣）金额 */
	private Double totalProfit;

	/** 属性是否冲减保费 */
	private String minusFlag;

	/** 属性经办人代码 */
	private String handlerCode;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性输入日期 */
	private Date inputDate;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpCprofit的默认构造方法
	 */
	public PrpCprofit() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "profitType", column = @Column(name = "PROFITTYPE")),
			@AttributeOverride(name = "itemkindNo", column = @Column(name = "ITEMKINDNO")) })
	public PrpCprofitId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCprofitId id) {
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
	 * 属性总优惠(折扣)率(%)的getter方法
	 */

	@Column(name = "DISCOUNT")
	public Double getDiscount() {
		return this.discount;
	}

	/**
	 * 属性总优惠(折扣)率(%)的setter方法
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * 属性优惠（折扣）金额的getter方法
	 */

	@Column(name = "TOTALPROFIT")
	public Double getTotalProfit() {
		return this.totalProfit;
	}

	/**
	 * 属性优惠（折扣）金额的setter方法
	 */
	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	/**
	 * 属性是否冲减保费的getter方法
	 */

	@Column(name = "MINUSFLAG")
	public String getMinusFlag() {
		return this.minusFlag;
	}

	/**
	 * 属性是否冲减保费的setter方法
	 */
	public void setMinusFlag(String minusFlag) {
		this.minusFlag = minusFlag;
	}

	/**
	 * 属性经办人代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性经办人代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性复核人代码的getter方法
	 */

	@Column(name = "APPROVERCODE")
	public String getApproverCode() {
		return this.approverCode;
	}

	/**
	 * 属性复核人代码的setter方法
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性输入日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性输入日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
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
