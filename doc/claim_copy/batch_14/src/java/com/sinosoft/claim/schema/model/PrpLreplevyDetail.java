package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
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
 * POJO类PrpLreplevyDetail权益转让及追偿信息
 */
@Entity
@Table(name = "PRPLREPLEVYDETAIL")
public class PrpLreplevyDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLreplevyDetailId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性用户输入的出险次数或单证个数 */
	private BigDecimal times;

	/** 属性追偿类型代码 */
	private String replevyTypeCode;

	/** 属性权益转让日期 */
	private Date rightTransferDate;

	/** 属性被追偿人名称 */
	private String repleviedName;

	/** 属性追偿原因 */
	private String replevyReason;

	/** 属性标的项目类别代码 */
	private String itemCode;

	/** 属性标的项目名称 */
	private String itemName;

	/** 属性币别 */
	private String currency;

	/** 属性追偿金额 */
	private BigDecimal replevyFee;

	/** 属性已追回金额 */
	private BigDecimal validFee;

	/** 属性REPLEVYCHARGE */
	private BigDecimal replevyCharge;

	/** 属性回收日期 */
	private Date reclaimDate;

	/** 属性处理人员代码 */
	private String handlerCode;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性备注 */
	private String note;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLreplevyDetail的默认构造方法
	 */
	public PrpLreplevyDetail() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLreplevyDetailId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLreplevyDetailId id) {
		this.id = id;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性用户输入的出险次数或单证个数的getter方法
	 */

	@Column(name = "TIMES")
	public BigDecimal getTimes() {
		return this.times;
	}

	/**
	 * 属性用户输入的出险次数或单证个数的setter方法
	 */
	public void setTimes(BigDecimal times) {
		this.times = times;
	}

	/**
	 * 属性追偿类型代码的getter方法
	 */

	@Column(name = "REPLEVYTYPECODE")
	public String getReplevyTypeCode() {
		return this.replevyTypeCode;
	}

	/**
	 * 属性追偿类型代码的setter方法
	 */
	public void setReplevyTypeCode(String replevyTypeCode) {
		this.replevyTypeCode = replevyTypeCode;
	}

	/**
	 * 属性权益转让日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RIGHTTRANSFERDATE")
	public Date getRightTransferDate() {
		return this.rightTransferDate;
	}

	/**
	 * 属性权益转让日期的setter方法
	 */
	public void setRightTransferDate(Date rightTransferDate) {
		this.rightTransferDate = rightTransferDate;
	}

	/**
	 * 属性被追偿人名称的getter方法
	 */

	@Column(name = "REPLEVIEDNAME")
	public String getRepleviedName() {
		return this.repleviedName;
	}

	/**
	 * 属性被追偿人名称的setter方法
	 */
	public void setRepleviedName(String repleviedName) {
		this.repleviedName = repleviedName;
	}

	/**
	 * 属性追偿原因的getter方法
	 */

	@Column(name = "REPLEVYREASON")
	public String getReplevyReason() {
		return this.replevyReason;
	}

	/**
	 * 属性追偿原因的setter方法
	 */
	public void setReplevyReason(String replevyReason) {
		this.replevyReason = replevyReason;
	}

	/**
	 * 属性标的项目类别代码的getter方法
	 */

	@Column(name = "ITEMCODE")
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 属性标的项目类别代码的setter方法
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 属性标的项目名称的getter方法
	 */

	@Column(name = "ITEMNAME")
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * 属性标的项目名称的setter方法
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	 * 属性追偿金额的getter方法
	 */

	@Column(name = "REPLEVYFEE")
	public BigDecimal getReplevyFee() {
		return this.replevyFee;
	}

	/**
	 * 属性追偿金额的setter方法
	 */
	public void setReplevyFee(BigDecimal replevyFee) {
		this.replevyFee = replevyFee;
	}

	/**
	 * 属性已追回金额的getter方法
	 */

	@Column(name = "VALIDFEE")
	public BigDecimal getValidFee() {
		return this.validFee;
	}

	/**
	 * 属性已追回金额的setter方法
	 */
	public void setValidFee(BigDecimal validFee) {
		this.validFee = validFee;
	}

	/**
	 * 属性REPLEVYCHARGE的getter方法
	 */

	@Column(name = "REPLEVYCHARGE")
	public BigDecimal getReplevyCharge() {
		return this.replevyCharge;
	}

	/**
	 * 属性REPLEVYCHARGE的setter方法
	 */
	public void setReplevyCharge(BigDecimal replevyCharge) {
		this.replevyCharge = replevyCharge;
	}

	/**
	 * 属性回收日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RECLAIMDATE")
	public Date getReclaimDate() {
		return this.reclaimDate;
	}

	/**
	 * 属性回收日期的setter方法
	 */
	public void setReclaimDate(Date reclaimDate) {
		this.reclaimDate = reclaimDate;
	}

	/**
	 * 属性处理人员代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性处理人员代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
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
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "NOTE")
	public String getNote() {
		return this.note;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setNote(String note) {
		this.note = note;
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
