package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpLassureDetail
 */
@Entity
@Table(name = "PRPLASSUREDETAIL")
public class PrpLassureDetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLassureDetailId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性提单号 */
	private String ladingNo;

	/** 属性货物名称 */
	private String goodsDetailName;

	/** 属性标记 */
	private String marks;

	/** 属性数量 */
	private String count;

	/** 属性币别 */
	private String currency;

	/** 属性保险金额/赔偿限额 */
	private BigDecimal amount;

	/**
	 * 类PrpLassureDetail的默认构造方法
	 */
	public PrpLassureDetail() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "assureNo", column = @Column(name = "ASSURENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLassureDetailId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLassureDetailId id) {
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
	 * 属性提单号的getter方法
	 */

	@Column(name = "LADINGNO")
	public String getLadingNo() {
		return this.ladingNo;
	}

	/**
	 * 属性提单号的setter方法
	 */
	public void setLadingNo(String ladingNo) {
		this.ladingNo = ladingNo;
	}

	/**
	 * 属性货物名称的getter方法
	 */

	@Column(name = "GOODSDETAILNAME")
	public String getGoodsDetailName() {
		return this.goodsDetailName;
	}

	/**
	 * 属性货物名称的setter方法
	 */
	public void setGoodsDetailName(String goodsDetailName) {
		this.goodsDetailName = goodsDetailName;
	}

	/**
	 * 属性标记的getter方法
	 */

	@Column(name = "MARKS")
	public String getMarks() {
		return this.marks;
	}

	/**
	 * 属性标记的setter方法
	 */
	public void setMarks(String marks) {
		this.marks = marks;
	}

	/**
	 * 属性数量的getter方法
	 */

	@Column(name = "COUNT")
	public String getCount() {
		return this.count;
	}

	/**
	 * 属性数量的setter方法
	 */
	public void setCount(String count) {
		this.count = count;
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
	 * 属性保险金额/赔偿限额的getter方法
	 */

	@Column(name = "AMOUNT")
	public BigDecimal getAmount() {
		return this.amount;
	}

	/**
	 * 属性保险金额/赔偿限额的setter方法
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
