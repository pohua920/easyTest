package com.sinosoft.claim.schema.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpClimit
 */
@Entity
@Table(name = "PRPCLIMIT")
public class PrpClimit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpClimitId id;

	/** 属性险种 */
	private String riskCode;

	/** 赔偿限额/免赔额 */
	private Double limitFee = 0d;

	/** 是否计算保额标志 */
	private String calculateFlag;

	/** 免赔标志 */
	private String limitFlag;

	/** 属性标志 */
	private String flag;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性LIMITFEERATE */
	private Double limitfeerate;

	/** 属性DEDUCTIBLE */
	private Double deductible;

	/** 属性DEDUCTIBLERATE */
	private Double deductiblerate;

	/** 属性EXTEND1 */
	private String extend1;

	/** 属性EXTEND2 */
	private String extend2;

	/** 属性EXTEND3 */
	private String extend3;

	/** 属性EXTEND4 */
	private String extend4;

	/** 属性EXTEND5 */
	private String extend5;

	/** 属性EXTEND6 */
	private String extend6;
	/** 列表 */
	private List<PrpClimit> prpClimitList = null;
	/** 币别名称 */
	String currencyName = null;
	/** 限制名称 */
	String limitTypeName = null;

	/**
	 * 类PrpClimit的默认构造方法
	 */
	public PrpClimit() {
		this.id = new PrpClimitId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "limitGrade", column = @Column(name = "LIMITGRADE")),
			@AttributeOverride(name = "limitNo", column = @Column(name = "LIMITNO")), @AttributeOverride(name = "limitType", column = @Column(name = "LIMITTYPE")), @AttributeOverride(name = "currency", column = @Column(name = "CURRENCY")) })
	public PrpClimitId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpClimitId id) {
		this.id = id;
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性LIMITFEE的getter方法
	 */

	@Column(name = "LIMITFEE")
	public Double getLimitFee() {
		return this.limitFee == null ? 0d : this.limitFee;
	}

	/**
	 * 属性LIMITFEE的setter方法
	 */
	public void setLimitFee(Double limitFee) {
		this.limitFee = limitFee;
	}

	/**
	 * 属性CALCULATEFLAG的getter方法
	 */

	@Column(name = "CALCULATEFLAG")
	public String getCalculateFlag() {
		return this.calculateFlag;
	}

	/**
	 * 属性CALCULATEFLAG的setter方法
	 */
	public void setCalculateFlag(String calculateFlag) {
		this.calculateFlag = calculateFlag;
	}

	/**
	 * 属性LIMITFLAG的getter方法
	 */

	@Column(name = "LIMITFLAG")
	public String getLimitFlag() {
		return this.limitFlag;
	}

	/**
	 * 属性LIMITFLAG的setter方法
	 */
	public void setLimitFlag(String limitFlag) {
		this.limitFlag = limitFlag;
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
	 * 属性发票/支付单备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性发票/支付单备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性LIMITFEERATE的getter方法
	 */

	@Column(name = "LIMITFEERATE")
	public Double getLimitfeerate() {
		return this.limitfeerate;
	}

	/**
	 * 属性LIMITFEERATE的setter方法
	 */
	public void setLimitfeerate(Double limitfeerate) {
		this.limitfeerate = limitfeerate;
	}

	/**
	 * 属性DEDUCTIBLE的getter方法
	 */

	@Column(name = "DEDUCTIBLE")
	public Double getDeductible() {
		return this.deductible;
	}

	/**
	 * 属性DEDUCTIBLE的setter方法
	 */
	public void setDeductible(Double deductible) {
		this.deductible = deductible;
	}

	/**
	 * 属性DEDUCTIBLERATE的getter方法
	 */

	@Column(name = "DEDUCTIBLERATE")
	@Transient
	public Double getDeductiblerate() {
		return this.deductiblerate;
	}

	/**
	 * 属性DEDUCTIBLERATE的setter方法
	 */
	public void setDeductiblerate(Double deductiblerate) {
		this.deductiblerate = deductiblerate;
	}

	/**
	 * 属性EXTEND1的getter方法
	 */

	@Column(name = "EXTEND1")
	public String getExtend1() {
		return this.extend1;
	}

	/**
	 * 属性EXTEND1的setter方法
	 */
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	/**
	 * 属性EXTEND2的getter方法
	 */

	@Column(name = "EXTEND2")
	public String getExtend2() {
		return this.extend2;
	}

	/**
	 * 属性EXTEND2的setter方法
	 */
	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	/**
	 * 属性EXTEND3的getter方法
	 */

	@Column(name = "EXTEND3")
	public String getExtend3() {
		return this.extend3;
	}

	/**
	 * 属性EXTEND3的setter方法
	 */
	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}

	/**
	 * 属性EXTEND4的getter方法
	 */

	@Column(name = "EXTEND4")
	public String getExtend4() {
		return this.extend4;
	}

	/**
	 * 属性EXTEND4的setter方法
	 */
	public void setExtend4(String extend4) {
		this.extend4 = extend4;
	}

	/**
	 * 属性EXTEND5的getter方法
	 */

	@Column(name = "EXTEND5")
	public String getExtend5() {
		return this.extend5;
	}

	/**
	 * 属性EXTEND5的setter方法
	 */
	public void setExtend5(String extend5) {
		this.extend5 = extend5;
	}

	/**
	 * 属性EXTEND6的getter方法
	 */

	@Column(name = "EXTEND6")
	public String getExtend6() {
		return this.extend6;
	}

	/**
	 * 属性EXTEND6的setter方法
	 */
	public void setExtend6(String extend6) {
		this.extend6 = extend6;
	}

	public void setPrpClimitList(List<PrpClimit> prpClimitList) {
		this.prpClimitList = prpClimitList;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public void setLimitTypeName(String limitTypeName) {
		this.limitTypeName = limitTypeName;
	}

	@Transient
	public List<PrpClimit> getPrpClimitList() {
		return prpClimitList;
	}

	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	@Transient
	public String getLimitTypeName() {
		return limitTypeName;
	}


}
