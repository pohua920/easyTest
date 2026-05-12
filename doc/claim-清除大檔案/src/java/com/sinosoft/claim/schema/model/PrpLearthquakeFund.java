package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 地震基金代码表
 * POJO类PrpLearthquakeFund
 */
@Entity
@Table(name = "PrpLearthquakeFund")
public class PrpLearthquakeFund implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLearthquakeFundId id;

	/** 险种 */
	private String riskCode;

	/** 地震基金代码 */
	private String earthquakeFundNo;

	/** 出险时间 */
	private Date damageStartDate;
	
	/** 出险小时  */
	private String damageStartHour;
	/** 属性出险开始分钟 */
	private String damageStartMinute = "";

	/** 公司代码 */
	private String companyCode;

	/** 分支机构代码 */
	private String comCode;

	/** 保单号码 */
	private String policyNo;
	
	/** 立案号码  */
	private String claimNo;

	/** 赔付次数 */
	private Integer times;
	/** 地址序号 */
	private String addressNo;
	/** 备注 */
	private String remark;
	/** 属性标志 */
	private String flag;
	private List<PrpLearthquakeFund> prpLearthquakeFundList;

	/**
	 * 类PrpDcode的默认构造方法
	 */
	public PrpLearthquakeFund() {
		id = new PrpLearthquakeFundId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "compensateNo")), @AttributeOverride(name = "serialNo", column = @Column(name = "serialNo")) })
	public PrpLearthquakeFundId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLearthquakeFundId id) {
		this.id = id;
	}
	@Column(name="riskCode")
	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name="earthquakeFundNo")
	public String getEarthquakeFundNo() {
		return earthquakeFundNo;
	}

	public void setEarthquakeFundNo(String earthquakeFundNo) {
		this.earthquakeFundNo = earthquakeFundNo;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="damageStartDate")
	public Date getDamageStartDate() {
		return damageStartDate;
	}

	public void setDamageStartDate(Date damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	@Column(name="damageStartHour")
	public String getDamageStartHour() {
		return damageStartHour;
	}

	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}
	@Column(name="companyCode")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	@Column(name="comCode")
	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	@Column(name="policyNo")
	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	@Column(name="claimNo")
	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	@Column(name="times")
	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	@Column(name="addressNo")
	public String getAddressNo() {
		return addressNo;
	}

	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Column(name="damageStartMinute")
	public String getDamageStartMinute() {
		return damageStartMinute;
	}

	public void setDamageStartMinute(String damageStartMinute) {
		this.damageStartMinute = damageStartMinute;
	}
	@Transient
	public List<PrpLearthquakeFund> getPrpLearthquakeFundList() {
		return prpLearthquakeFundList;
	}

	public void setPrpLearthquakeFundList(List<PrpLearthquakeFund> prpLearthquakeFundList) {
		this.prpLearthquakeFundList = prpLearthquakeFundList;
	}
	
}
