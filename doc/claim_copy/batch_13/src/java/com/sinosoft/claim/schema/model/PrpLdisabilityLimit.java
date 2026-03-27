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
 * POJO类PrpCaddress
 */
@Entity
@Table(name = "PRPLDISABILITYLIMIT")
public class PrpLdisabilityLimit implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLdisabilityLimitId id;

	// 等级名称 第一级
	private String ratingName;
	// 币别
	private String currency;
	// 限额
	private double limitFee;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 是否有效,1有效，0 无效
	private String status;
	// 备用
	private String flag;

	/**
	 * 类PrpCaddress的默认构造方法
	 */
	public PrpLdisabilityLimit() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "ratingCode", column = @Column(name = "ratingCode")), @AttributeOverride(name = "version", column = @Column(name = "version")),
			@AttributeOverride(name = "riskCode", column = @Column(name = "riskCode")), @AttributeOverride(name = "kindCode", column = @Column(name = "kindCode")) })
	public PrpLdisabilityLimitId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLdisabilityLimitId id) {
		this.id = id;
	}

	@Column(name = "ratingName")
	public String getRatingName() {
		return ratingName;
	}

	public void setRatingName(String ratingName) {
		this.ratingName = ratingName;
	}

	@Column(name = "currency")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "limitFee")
	public double getLimitFee() {
		return limitFee;
	}

	public void setLimitFee(double limitFee) {
		this.limitFee = limitFee;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "startTime")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "endTime")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "status")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "flag")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
