package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLclaimFee估损金额表
 */
@Entity
@Table(name = "PRPLCLAIMFEE")
public class PrpLclaimFee implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLclaimFeeId id;

	/** 属性立案基本信息表 */
	private PrpLclaim prpLclaim;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保险损失金额 */
	private Double sumClaim;

	/** 属性状态字段 */
	private String flag;

	private Collection<PrpLclaimFee> claimFeeList;

	private String currencyName = "";

	/**
	 * 类PrpLclaimFee的默认构造方法
	 */
	public PrpLclaimFee() {
		id = new PrpLclaimFeeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "currency", column = @Column(name = "CURRENCY")) })
	public PrpLclaimFeeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLclaimFeeId id) {
		this.id = id;
	}

	/**
	 * 属性立案基本信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMNO", nullable = false, insertable = false, updatable = false)
	public PrpLclaim getPrpLclaim() {
		return this.prpLclaim;
	}

	/**
	 * 属性立案基本信息表的setter方法
	 */
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
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
	 * 属性保险损失金额的getter方法
	 */

	@Column(name = "SUMCLAIM")
	public Double getSumClaim() {
		return this.sumClaim;
	}

	/**
	 * 属性保险损失金额的setter方法
	 */
	public void setSumClaim(Double sumClaim) {
		this.sumClaim = sumClaim;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public Collection<PrpLclaimFee> getClaimFeeList() {
		return claimFeeList;
	}

	/**
	 * 设置列表
	 * @param driverList 待设置的列表
	 */
	public void setClaimFeeList(Collection<PrpLclaimFee> claimFeeList) {
		this.claimFeeList = claimFeeList;
	}

	/**
	 * 设置属性币别名称
	 * @param currencyName 待设置的属性币别名称的值
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = StringUtils.rightTrim(currencyName);
	}

	/**
	 * 获取属性币别名称
	 * @return 属性币别名称的值
	 */
	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

}
