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
 * POJO类PrpLcallCenter呼叫中心信息表
 */
@Entity
@Table(name = "PRPLCALLCENTER")
public class PrpLcallCenter implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcallCenterId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性类型 */
	private String type;

	/** 属性计算机输单小时 */
	private String inputHour;

	/** 属性坐席ID */
	private String surId;

	/** 属性坐席名称 */
	private String surName;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性服务单号 */
	private String serviceNo;

	/** 属性备注参数 */
	private String remarkC;

	/** 属性状态字段 */
	private String flag;

	/**
	 * 类PrpLcallCenter的默认构造方法
	 */
	public PrpLcallCenter() {
		id = new PrpLcallCenterId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLcallCenterId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcallCenterId id) {
		this.id = id;
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
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性类型的getter方法
	 */

	@Column(name = "TYPE")
	public String getType() {
		return this.type;
	}

	/**
	 * 属性类型的setter方法
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 属性计算机输单小时的getter方法
	 */

	@Column(name = "INPUTHOUR")
	public String getInputHour() {
		return this.inputHour;
	}

	/**
	 * 属性计算机输单小时的setter方法
	 */
	public void setInputHour(String inputHour) {
		this.inputHour = inputHour;
	}

	/**
	 * 属性坐席ID的getter方法
	 */

	@Column(name = "SURID")
	public String getSurId() {
		return this.surId;
	}

	/**
	 * 属性坐席ID的setter方法
	 */
	public void setSurId(String surId) {
		this.surId = surId;
	}

	/**
	 * 属性坐席名称的getter方法
	 */

	@Column(name = "SURNAME")
	public String getSurName() {
		return this.surName;
	}

	/**
	 * 属性坐席名称的setter方法
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}

	/**
	 * 属性业务归属机构代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性业务归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性服务单号的getter方法
	 */

	@Column(name = "SERVICENO")
	public String getServiceNo() {
		return this.serviceNo;
	}

	/**
	 * 属性服务单号的setter方法
	 */
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	/**
	 * 属性备注参数的getter方法
	 */

	@Column(name = "REMARKC")
	public String getRemarkC() {
		return this.remarkC;
	}

	/**
	 * 属性备注参数的setter方法
	 */
	public void setRemarkC(String remarkC) {
		this.remarkC = remarkC;
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

}
