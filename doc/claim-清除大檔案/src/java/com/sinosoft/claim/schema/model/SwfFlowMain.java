package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类SwfFlowMain流程主表
 */
@Entity
@Table(name = "SWFFLOWMAIN")
public class SwfFlowMain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程编号 */
	private String flowID;

	/** 属性流程名称 */
	private String flowName;

	/** 属性该流程的状态转储後是0 */
	private String flowStatus;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性创建该流程的时间 */
	private Date creatDate;

	/** 属性关闭该流程的时间 */
	private Date closeDate;

	/** 属性模板编码 */
	private Integer modelNo = 0;

	/** 属性标志字段 */
	private String flag;

	/** 属性移出标志转储标志：2已转储 */
	private String storeFlag;

	/** 属性简易赔案标记 */
	private String claimTypeFlag;

	/*
	 * 数据库中没有的字段，在页面上展示用
	 */
	private String setStopTime = ""; // 计算案件处理时间

	/**
	 * 类SwfFlowMain的默认构造方法
	 */
	public SwfFlowMain() {
	}

	/**
	 * 属性流程编号的getter方法
	 */
	@Id
	@Column(name = "FlowID")
	public String getFlowID() {
		return this.flowID;
	}

	/**
	 * 属性流程编号的setter方法
	 */
	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	/**
	 * 属性流程名称的getter方法
	 */

	@Column(name = "FLOWNAME")
	public String getFlowName() {
		return this.flowName;
	}

	/**
	 * 属性流程名称的setter方法
	 */
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	/**
	 * 属性该流程的状态转储後是0的getter方法
	 */

	@Column(name = "FLOWSTATUS")
	public String getFlowStatus() {
		return this.flowStatus;
	}

	/**
	 * 属性该流程的状态转储後是0的setter方法
	 */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
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
	 * 属性创建该流程的时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATDATE")
	public Date getCreatDate() {
		return this.creatDate;
	}

	/**
	 * 属性创建该流程的时间的setter方法
	 */
	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	/**
	 * 属性关闭该流程的时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CLOSEDATE")
	public Date getCloseDate() {
		return this.closeDate;
	}

	/**
	 * 属性关闭该流程的时间的setter方法
	 */
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	/**
	 * 属性模板编码的getter方法
	 */

	@Column(name = "MODELNO")
	public Integer getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性模板编码的setter方法
	 */
	public void setModelNo(Integer modelNo) {
		this.modelNo = modelNo;
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

	/**
	 * 属性移出标志转储标志：2已转储的getter方法
	 */

	@Column(name = "STOREFLAG")
	public String getStoreFlag() {
		return this.storeFlag;
	}

	/**
	 * 属性移出标志转储标志：2已转储的setter方法
	 */
	public void setStoreFlag(String storeFlag) {
		this.storeFlag = storeFlag;
	}

	/**
	 * 属性简易赔案标记的getter方法
	 */

	@Column(name = "CLAIMTYPEFLAG")
	public String getClaimTypeFlag() {
		return this.claimTypeFlag;
	}

	/**
	 * 属性简易赔案标记的setter方法
	 */
	public void setClaimTypeFlag(String claimTypeFlag) {
		this.claimTypeFlag = claimTypeFlag;
	}

	@Transient
	public String getSetStopTime() {
		return setStopTime;
	}

	public void setSetStopTime(String setStopTime) {
		this.setStopTime = setStopTime;
	}

}
