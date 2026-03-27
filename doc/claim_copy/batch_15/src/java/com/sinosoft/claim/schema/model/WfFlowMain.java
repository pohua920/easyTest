package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类WfFlowMain
 */
@Entity
@Table(name = "WFFLOWMAIN")
public class WfFlowMain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流水号 */
	private String flowId;

	/** 属性流程名称 */
	private String flowName;

	/** 属性该流程的状态 */
	private String flowStatus;

	/** 属性创建该流程的时间 */
	private String creatDate;

	/** 属性关闭该流程的时间 */
	private String closeDate;

	/** 属性模版号 */
	private Long modelNo;

	/** 属性Flag位 */
	private String flag;

	/** 属性转储标志 */
	private String storeFlag;

	/**
	 * 类WfFlowMain的默认构造方法
	 */
	public WfFlowMain() {
	}

	/**
	 * 属性流水号的getter方法
	 */
	@Id
	@Column(name = "FLOWID")
	public String getFlowId() {
		return this.flowId;
	}

	/**
	 * 属性流水号的setter方法
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
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
	 * 属性该流程的状态的getter方法
	 */

	@Column(name = "FLOWSTATUS")
	public String getFlowStatus() {
		return this.flowStatus;
	}

	/**
	 * 属性该流程的状态的setter方法
	 */
	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	/**
	 * 属性创建该流程的时间的getter方法
	 */

	@Column(name = "CREATDATE")
	public String getCreatDate() {
		return this.creatDate;
	}

	/**
	 * 属性创建该流程的时间的setter方法
	 */
	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	/**
	 * 属性关闭该流程的时间的getter方法
	 */

	@Column(name = "CLOSEDATE")
	public String getCloseDate() {
		return this.closeDate;
	}

	/**
	 * 属性关闭该流程的时间的setter方法
	 */
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	/**
	 * 属性模版号的getter方法
	 */

	@Column(name = "MODELNO")
	public Long getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性模版号的setter方法
	 */
	public void setModelNo(Long modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 属性Flag位的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性Flag位的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性转储标志的getter方法
	 */

	@Column(name = "STOREFLAG")
	public String getStoreFlag() {
		return this.storeFlag;
	}

	/**
	 * 属性转储标志的setter方法
	 */
	public void setStoreFlag(String storeFlag) {
		this.storeFlag = storeFlag;
	}

}
