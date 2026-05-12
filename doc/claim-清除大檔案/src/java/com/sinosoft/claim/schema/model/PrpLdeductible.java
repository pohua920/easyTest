package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLdeductible免赔信息表
 */
@Entity
@Table(name = "PRPLDEDUCTIBLE")
public class PrpLdeductible implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLdeductibleId id;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险别赔偿金额 */
	private BigDecimal sumKindPaid;

	/** 属性险别 */
	private String kindCode;

	/** 属性免赔类型 */
	private String deductibleLevel;

	/** 属性币别 */
	private String currency;

	/** 属性DEDUCTIBLE */
	private String deductible;

	/** 属性DEDUCTIBLERATE */
	private BigDecimal deductiblerate;

	/** 属性标志 */
	private String flag;
	/** 属性计算书号码 */
	private String compensateNo = "";
	/** 属性序号 */
	private String serialNo = "";

	/** 属性免赔率 */
	private String deductibleRate = "";
	/** 属性险别名称 */
	private String kindName = "";

	/**
	 * 类PrpLdeductible的默认构造方法
	 */
	public PrpLdeductible() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLdeductibleId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLdeductibleId id) {
		this.id = id;
	}

	/**
	 * 属性立案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性险别赔偿金额的getter方法
	 */

	@Column(name = "SUMKINDPAID")
	public BigDecimal getSumKindPaid() {
		return this.sumKindPaid;
	}

	/**
	 * 属性险别赔偿金额的setter方法
	 */
	public void setSumKindPaid(BigDecimal sumKindPaid) {
		this.sumKindPaid = sumKindPaid;
	}

	/**
	 * 属性险别的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * 属性免赔类型的getter方法
	 */

	@Column(name = "DEDUCTIBLELEVEL")
	public String getDeductibleLevel() {
		return this.deductibleLevel;
	}

	/**
	 * 属性免赔类型的setter方法
	 */
	public void setDeductibleLevel(String deductibleLevel) {
		this.deductibleLevel = deductibleLevel;
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
	 * 属性DEDUCTIBLE的getter方法
	 */

	@Column(name = "DEDUCTIBLE")
	public String getDeductible() {
		return this.deductible;
	}

	/**
	 * 属性DEDUCTIBLE的setter方法
	 */
	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}

	/**
	 * 属性DEDUCTIBLERATE的getter方法
	 */

	@Column(name = "DEDUCTIBLERATE")
	public BigDecimal getDeductiblerate() {
		return this.deductiblerate;
	}

	/**
	 * 属性DEDUCTIBLERATE的setter方法
	 */
	public void setDeductiblerate(BigDecimal deductiblerate) {
		this.deductiblerate = deductiblerate;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 获取计算书号码
	 * @return 属性计算书号码
	 */
	@Transient
	public String getCompensateNo() {
		return compensateNo;
	}

	/**
	 * 设置计算书号码
	 * @param compensateNo 计算书号码
	 */
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	/**
	 * 获取序号
	 * @return 属性序号
	 */
	@Transient
	public String getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置序号
	 * @param serialNo 序号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性免赔率
	 * @return 属性免赔率
	 */
	@Transient
	public String getDeductibleRate() {
		return deductibleRate;
	}

	/**
	 * 属性免赔率
	 * @param deductibleRate 属性免赔率
	 */
	public void setDeductibleRate(String deductibleRate) {
		this.deductibleRate = deductibleRate;
	}

	/**
	 * 属性险别名称
	 * @return 属性险别名称
	 */
	@Transient
	public String getKindName() {
		return kindName;
	}

	/**
	 * 属性标志位
	 * @param flag 属性标志位
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

}
