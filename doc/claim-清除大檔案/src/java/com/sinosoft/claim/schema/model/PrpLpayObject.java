package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类PrpLpayObject支付对象管理表
 */
@Entity
@Table(name = "PRPLPAYOBJECT")
public class PrpLpayObject implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性支付对象代码 */
	private String payObjectCode;

	/** 属性支付对象名称 */
	private String payObjectName;

	/** 属性支付对象类型 */
	private String payObjectType;

	/** 属性机构代码 */
	private String comCode;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性备注 */
	private String remark;

	/** 属性效力状态(0失效/1有效) */
	private String validStatus;

	/**
	 * 类PrpLpayObject的默认构造方法
	 */
	public PrpLpayObject() {
	}

	/**
	 * 属性支付对象代码的getter方法
	 */
	@Id
	@Column(name = "PAYOBJECTCODE")
	public String getPayObjectCode() {
		return this.payObjectCode;
	}

	/**
	 * 属性支付对象代码的setter方法
	 */
	public void setPayObjectCode(String payObjectCode) {
		this.payObjectCode = payObjectCode;
	}

	/**
	 * 属性支付对象名称的getter方法
	 */

	@Column(name = "PAYOBJECTNAME")
	public String getPayObjectName() {
		return this.payObjectName;
	}

	/**
	 * 属性支付对象名称的setter方法
	 */
	public void setPayObjectName(String payObjectName) {
		this.payObjectName = payObjectName;
	}

	/**
	 * 属性支付对象类型的getter方法
	 */

	@Column(name = "PAYOBJECTTYPE")
	public String getPayObjectType() {
		return this.payObjectType;
	}

	/**
	 * 属性支付对象类型的setter方法
	 */
	public void setPayObjectType(String payObjectType) {
		this.payObjectType = payObjectType;
	}

	/**
	 * 属性机构代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性效力状态(0失效/1有效)的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态(0失效/1有效)的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

}
