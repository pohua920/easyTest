package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpLintfProcess(接口交互记录信息)
 */
@Entity
@Table(name = "PRPLINTFPROCESS")
public class PrpLintfProcess implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性业务号 */
	private String businessNo;

	/** 属性保单号码/批单号码 */
	private String certiNo;

	/** 属性业务类型 */
	private String certiType;

	/** 属性请求时间 */
	private Date arriveDate;

	/** 属性数据处理完成时间 */
	private Date lastOperateDate;

	/** 属性资料状态标志 */
	private String status;

	/** 属性出错信息 */
	private String errorMessage;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性备用字段1 */
	private String rsv1;

	/** 属性备用字段2 */
	private String rsv2;

	/** 属性备用字段3 */
	private String rsv3;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpLintfProcess的默认构造方法
	 */
	public PrpLintfProcess() {
	}

	/**
	 * 属性业务号的getter方法
	 */
	@Id
	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 属性保单号码/批单号码的getter方法
	 */

	@Column(name = "CERTINO")
	public String getCertiNo() {
		return this.certiNo;
	}

	/**
	 * 属性保单号码/批单号码的setter方法
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 属性业务类型的getter方法
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性业务类型的setter方法
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性请求时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ARRIVEDATE")
	public Date getArriveDate() {
		return this.arriveDate;
	}

	/**
	 * 属性请求时间的setter方法
	 */
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	/**
	 * 属性数据处理完成时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LASTOPERATEDATE")
	public Date getLastOperateDate() {
		return this.lastOperateDate;
	}

	/**
	 * 属性数据处理完成时间的setter方法
	 */
	public void setLastOperateDate(Date lastOperateDate) {
		this.lastOperateDate = lastOperateDate;
	}

	/**
	 * 属性资料状态标志的getter方法
	 */

	@Column(name = "STATUS")
	public String getStatus() {
		return this.status;
	}

	/**
	 * 属性资料状态标志的setter方法
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 属性出错信息的getter方法
	 */

	@Column(name = "ERRORMESSAGE")
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * 属性出错信息的setter方法
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	 * 属性备用字段1的getter方法
	 */

	@Column(name = "RSV1")
	public String getRsv1() {
		return this.rsv1;
	}

	/**
	 * 属性备用字段1的setter方法
	 */
	public void setRsv1(String rsv1) {
		this.rsv1 = rsv1;
	}

	/**
	 * 属性备用字段2的getter方法
	 */

	@Column(name = "RSV2")
	public String getRsv2() {
		return this.rsv2;
	}

	/**
	 * 属性备用字段2的setter方法
	 */
	public void setRsv2(String rsv2) {
		this.rsv2 = rsv2;
	}

	/**
	 * 属性备用字段3的getter方法
	 */

	@Column(name = "RSV3")
	public String getRsv3() {
		return this.rsv3;
	}

	/**
	 * 属性备用字段3的setter方法
	 */
	public void setRsv3(String rsv3) {
		this.rsv3 = rsv3;
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

}
