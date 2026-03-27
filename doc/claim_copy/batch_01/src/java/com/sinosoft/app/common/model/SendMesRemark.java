package com.sinosoft.app.common.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类SendMesRemark
 */
@Entity
@Table(name = "SENDMESREMARK")
public class SendMesRemark implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private String id;

	/** 属性任务号 */
	private String businessNo;

	/** 属性任务名称 */
	private String taskName;

	/** 属性工作要求 */
	private String jobdeMand;

	/** 属性归属责任人代码 */
	private String executorCode;

	/** 属性归属责任人名称 */
	private String executorName;

	/** 属性开始日期 */
	private Date startDate;

	/** 属性结束日期 */
	private Date endDate;

	/** 属性备用 */
	private String remark;

	/** 属性备用1 */
	private String flag;

	/**
	 * 类SendMesRemark的默认构造方法
	 */
	public SendMesRemark() {
	}

	/**
	 * 属性id的getter方法
	 */
	@Id
	@Column(name = "ID")
	public String getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 属性任务号的getter方法
	 */

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性任务号的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 属性任务名称的getter方法
	 */

	@Column(name = "TASKNAME")
	public String getTaskName() {
		return this.taskName;
	}

	/**
	 * 属性任务名称的setter方法
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * 属性工作要求的getter方法
	 */

	@Column(name = "JOBDEMAND")
	public String getJobdeMand() {
		return this.jobdeMand;
	}

	/**
	 * 属性工作要求的setter方法
	 */
	public void setJobdeMand(String jobdeMand) {
		this.jobdeMand = jobdeMand;
	}

	/**
	 * 属性归属责任人代码的getter方法
	 */

	@Column(name = "EXECUTORCODE")
	public String getExecutorCode() {
		return this.executorCode;
	}

	/**
	 * 属性归属责任人代码的setter方法
	 */
	public void setExecutorCode(String executorCode) {
		this.executorCode = executorCode;
	}

	/**
	 * 属性归属责任人名称的getter方法
	 */

	@Column(name = "EXECUTORNAME")
	public String getExecutorName() {
		return this.executorName;
	}

	/**
	 * 属性归属责任人名称的setter方法
	 */
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}

	/**
	 * 属性开始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性开始日期的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 属性结束日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性结束日期的setter方法
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 属性备用的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备用的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性备用1的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性备用1的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
