package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpDidentifier
 */
@Entity
@Table(name = "PRPDIDENTIFIER")
public class PrpDidentifier implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDidentifierId id;

	/** 属性国家代码 */
	private String countryCode;

	/** 属性国家中文名称 */
	private String countryCName;

	/** 属性国家英文名称 */
	private String countryEName;

	/** 属性港口名称 */
	private String portName;

	/** 属性优先级别 */
	private Long identifierOrder;

	/** 属性检验人名称 */
	private String identifierName;

	/** 属性检验人地址 */
	private String identifierAddress;

	/** 属性检验人类型 */
	private String identifierType;

	/** 属性效力状态 */
	private String validStatus;

	/** 属性标志 */
	private String flag;

	/**
	 * 类PrpDidentifier的默认构造方法
	 */
	public PrpDidentifier() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "identifierCode", column = @Column(name = "IDENTIFIERCODE")), @AttributeOverride(name = "portCode", column = @Column(name = "PORTCODE")) })
	public PrpDidentifierId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDidentifierId id) {
		this.id = id;
	}

	/**
	 * 属性国家代码的getter方法
	 */

	@Column(name = "COUNTRYCODE")
	public String getCountryCode() {
		return this.countryCode;
	}

	/**
	 * 属性国家代码的setter方法
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * 属性国家中文名称的getter方法
	 */

	@Column(name = "COUNTRYCNAME")
	public String getCountryCName() {
		return this.countryCName;
	}

	/**
	 * 属性国家中文名称的setter方法
	 */
	public void setCountryCName(String countryCName) {
		this.countryCName = countryCName;
	}

	/**
	 * 属性国家英文名称的getter方法
	 */

	@Column(name = "COUNTRYENAME")
	public String getCountryEName() {
		return this.countryEName;
	}

	/**
	 * 属性国家英文名称的setter方法
	 */
	public void setCountryEName(String countryEName) {
		this.countryEName = countryEName;
	}

	/**
	 * 属性港口名称的getter方法
	 */

	@Column(name = "PORTNAME")
	public String getPortName() {
		return this.portName;
	}

	/**
	 * 属性港口名称的setter方法
	 */
	public void setPortName(String portName) {
		this.portName = portName;
	}

	/**
	 * 属性优先级别的getter方法
	 */

	@Column(name = "IDENTIFIERORDER")
	public Long getIdentifierOrder() {
		return this.identifierOrder;
	}

	/**
	 * 属性优先级别的setter方法
	 */
	public void setIdentifierOrder(Long identifierOrder) {
		this.identifierOrder = identifierOrder;
	}

	/**
	 * 属性检验人名称的getter方法
	 */

	@Column(name = "IDENTIFIERNAME")
	public String getIdentifierName() {
		return this.identifierName;
	}

	/**
	 * 属性检验人名称的setter方法
	 */
	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	/**
	 * 属性检验人地址的getter方法
	 */

	@Column(name = "IDENTIFIERADDRESS")
	public String getIdentifierAddress() {
		return this.identifierAddress;
	}

	/**
	 * 属性检验人地址的setter方法
	 */
	public void setIdentifierAddress(String identifierAddress) {
		this.identifierAddress = identifierAddress;
	}

	/**
	 * 属性检验人类型的getter方法
	 */

	@Column(name = "IDENTIFIERTYPE")
	public String getIdentifierType() {
		return this.identifierType;
	}

	/**
	 * 属性检验人类型的setter方法
	 */
	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}

	/**
	 * 属性效力状态的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
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
