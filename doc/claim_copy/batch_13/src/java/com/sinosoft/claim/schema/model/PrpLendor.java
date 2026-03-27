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
 * POJO类PrpLendor
 */
@Entity
@Table(name = "PRPLENDOR")
public class PrpLendor implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLendorId id;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性标的项目类别代码 */
	private String itemCode;

	/** 属性标的项目名称 */
	private String itemName;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性险别名称 */
	private String kindName;

	/** 属性币别 */
	private String currency;

	/** 属性冲减保额/赔款金额 */
	private BigDecimal endorAmount;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLendor的默认构造方法
	 */
	public PrpLendor() {
		id = new PrpLendorId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")),
			@AttributeOverride(name = "itemKindNo", column = @Column(name = "ITEMKINDNO")) })
	public PrpLendorId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLendorId id) {
		this.id = id;
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	 * 属性冲减保额/赔款金额的getter方法
	 */

	@Column(name = "ENDORAMOUNT")
	public BigDecimal getEndorAmount() {
		return this.endorAmount;
	}

	/**
	 * 属性冲减保额/赔款金额的setter方法
	 */
	public void setEndorAmount(BigDecimal endorAmount) {
		this.endorAmount = endorAmount;
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
