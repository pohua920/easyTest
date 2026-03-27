package com.sinosoft.undwrt.undwrtBase.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类prpInvestIntf.
 */
@Entity(name = "PRPINVESTINTF_UNDWRT")
@Table(name = "PRPINVESTINTF")
public class PrpInvestIntf implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性保单号码/批单号码. */
	private String certiNo;

	/** 属性业务类型. */
	private String certiType;

	/** 属性险种. */
	private String riskCode;

	/** 属性保单号码. */
	private String policyNo;

	/** 属性归属机构. */
	private String comCode;

	/** 属性业务来源. */
	private String businessNature;

	/** 属性起保日期/生效日期. */
	private Date startDate;

	/** 属性终保日期. */
	private Date endDate;

	/** 属性投资金. */
	private double investment;

	/** 属性状态. */
	private String status;

	/** 属性属性备用标志. */
	private String flag;

	/** 属性销售渠道. */
	private String channelType;

	/** 属性标志位2. */
	private BigDecimal bonus;

	/**
	 * 类prpInvestIntf的默认构造方法.
	 */
	public PrpInvestIntf() {
	}

	/**
	 * 属性保单号码/批单号码的getter方法.
	 * 
	 * @return the 属性保单号码/批单号码
	 */
	@Id
	@Column(name = "CERTINO")
	public String getCertiNo() {
		return this.certiNo;
	}

	/**
	 * 属性保单号码/批单号码的setter方法.
	 * 
	 * @param certiNo
	 *            the new 属性保单号码/批单号码
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 属性业务类型的getter方法.
	 * 
	 * @return the 属性业务类型
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性业务类型的setter方法.
	 * 
	 * @param certiType
	 *            the new 属性业务类型
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性险种的getter方法.
	 * 
	 * @return the 属性险种
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法.
	 * 
	 * @param riskCode
	 *            the new 属性险种
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性保单号码的getter方法.
	 * 
	 * @return the 属性保单号码
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法.
	 * 
	 * @param policyNo
	 *            the new 属性保单号码
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性归属机构的getter方法.
	 * 
	 * @return the 属性归属机构
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构的setter方法.
	 * 
	 * @param comCode
	 *            the new 属性归属机构
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性业务来源的getter方法.
	 * 
	 * @return the 属性业务来源
	 */

	@Column(name = "BUSINESSNATURE")
	public String getBusinessNature() {
		return this.businessNature;
	}

	/**
	 * 属性业务来源的setter方法.
	 * 
	 * @param businessNature
	 *            the new 属性业务来源
	 */
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	/**
	 * 属性起保日期/生效日期的getter方法.
	 * 
	 * @return the 属性起保日期/生效日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性起保日期/生效日期的setter方法.
	 * 
	 * @param startDate
	 *            the new 属性起保日期/生效日期
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 属性终保日期的getter方法.
	 * 
	 * @return the 属性终保日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性终保日期的setter方法.
	 * 
	 * @param endDate
	 *            the new 属性终保日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 属性投资金的getter方法.
	 * 
	 * @return the 属性投资金
	 */

	@Column(name = "INVESTMENT")
	public double getInvestment() {
		return this.investment;
	}

	/**
	 * 属性投资金的setter方法.
	 * 
	 * @param investment
	 *            the new 属性投资金
	 */
	public void setInvestment(double investment) {
		this.investment = investment;
	}

	/**
	 * 属性状态的getter方法.
	 * 
	 * @return the 属性状态
	 */

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	/**
	 * 属性状态的setter方法.
	 * 
	 * @param status
	 *            the new 属性状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 属性属性备用标志的getter方法.
	 * 
	 * @return the 属性属性备用标志
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性属性备用标志的setter方法.
	 * 
	 * @param flag
	 *            the new 属性属性备用标志
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性销售渠道的getter方法.
	 * 
	 * @return the 属性销售渠道
	 */

	@Column(name = "CHANNELTYPE")
	public String getChannelType() {
		return this.channelType;
	}

	/**
	 * 属性销售渠道的setter方法.
	 * 
	 * @param channelType
	 *            the new 属性销售渠道
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * 属性标志位2的getter方法.
	 * 
	 * @return the 属性标志位2
	 */

	@Column(name = "BONUS")
	public BigDecimal getBonus() {
		return this.bonus;
	}

	/**
	 * 属性标志位2的setter方法.
	 * 
	 * @param bonus
	 *            the new 属性标志位2
	 */
	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}

}
