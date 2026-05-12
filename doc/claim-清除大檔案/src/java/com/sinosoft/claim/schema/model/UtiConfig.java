package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类UtiConfig
 */
@Entity
@Table(name = "UTICONFIG")
public class UtiConfig implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性CONFIGCODE */
	private String configCode;

	/** 属性CONFIGCNAME */
	private String configCName;

	/** 属性CONFIGENAME */
	private String configEName;

	/** 属性CONFIGTYPE */
	private String configType;

	/** 属性VALUETYPE */
	private String valueType;

	/** 属性CONFIGVALUE */
	private String configValue;

	/** 属性CONFIGTABLE */
	private String configTable;

	/** 属性CONFIGSELECTCOLOMN1 */
	private String configSelectColomn1;

	/** 属性CONFIGSELECTCOLOMN2 */
	private String configSelectColomn2;

	/** 属性CONFIGSELECTCOLOMN3 */
	private String configSelectColomn3;

	/** 属性CONFIGWHERECOLOMN */
	private String configWhereColomn;

	/** 属性CONFIGWHEREVALUE */
	private String configWhereValue;

	/** 属性MESSAGETYPE */
	private String messageType;

	/** 属性REMARK */
	private String remark;

	/** 属性FLAG */
	private String flag;

	/**
	 * 类UtiConfig的默认构造方法
	 */
	public UtiConfig() {
	}

	/**
	 * 属性CONFIGCODE的getter方法
	 */
	@Id
	@Column(name = "CONFIGCODE")
	public String getConfigCode() {
		return this.configCode;
	}

	/**
	 * 属性CONFIGCODE的setter方法
	 */
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	/**
	 * 属性CONFIGCNAME的getter方法
	 */

	@Column(name = "CONFIGCNAME")
	public String getConfigCName() {
		return this.configCName;
	}

	/**
	 * 属性CONFIGCNAME的setter方法
	 */
	public void setConfigCName(String configCName) {
		this.configCName = configCName;
	}

	/**
	 * 属性CONFIGENAME的getter方法
	 */

	@Column(name = "CONFIGENAME")
	public String getConfigEName() {
		return this.configEName;
	}

	/**
	 * 属性CONFIGENAME的setter方法
	 */
	public void setConfigEName(String configEName) {
		this.configEName = configEName;
	}

	/**
	 * 属性CONFIGTYPE的getter方法
	 */

	@Column(name = "CONFIGTYPE")
	public String getConfigType() {
		return this.configType;
	}

	/**
	 * 属性CONFIGTYPE的setter方法
	 */
	public void setConfigType(String configType) {
		this.configType = configType;
	}

	/**
	 * 属性VALUETYPE的getter方法
	 */

	@Column(name = "VALUETYPE")
	public String getValueType() {
		return this.valueType;
	}

	/**
	 * 属性VALUETYPE的setter方法
	 */
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	/**
	 * 属性CONFIGVALUE的getter方法
	 */

	@Column(name = "CONFIGVALUE")
	public String getConfigValue() {
		return this.configValue;
	}

	/**
	 * 属性CONFIGVALUE的setter方法
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	/**
	 * 属性CONFIGTABLE的getter方法
	 */

	@Column(name = "CONFIGTABLE")
	public String getConfigTable() {
		return this.configTable;
	}

	/**
	 * 属性CONFIGTABLE的setter方法
	 */
	public void setConfigTable(String configTable) {
		this.configTable = configTable;
	}

	/**
	 * 属性CONFIGSELECTCOLOMN1的getter方法
	 */

	@Column(name = "CONFIGSELECTCOLOMN1")
	public String getConfigSelectColomn1() {
		return this.configSelectColomn1;
	}

	/**
	 * 属性CONFIGSELECTCOLOMN1的setter方法
	 */
	public void setConfigSelectColomn1(String configSelectColomn1) {
		this.configSelectColomn1 = configSelectColomn1;
	}

	/**
	 * 属性CONFIGSELECTCOLOMN2的getter方法
	 */

	@Column(name = "CONFIGSELECTCOLOMN2")
	public String getConfigSelectColomn2() {
		return this.configSelectColomn2;
	}

	/**
	 * 属性CONFIGSELECTCOLOMN2的setter方法
	 */
	public void setConfigSelectColomn2(String configSelectColomn2) {
		this.configSelectColomn2 = configSelectColomn2;
	}

	/**
	 * 属性CONFIGSELECTCOLOMN3的getter方法
	 */

	@Column(name = "CONFIGSELECTCOLOMN3")
	public String getConfigSelectColomn3() {
		return this.configSelectColomn3;
	}

	/**
	 * 属性CONFIGSELECTCOLOMN3的setter方法
	 */
	public void setConfigSelectColomn3(String configSelectColomn3) {
		this.configSelectColomn3 = configSelectColomn3;
	}

	/**
	 * 属性CONFIGWHERECOLOMN的getter方法
	 */

	@Column(name = "CONFIGWHERECOLOMN")
	public String getConfigWhereColomn() {
		return this.configWhereColomn;
	}

	/**
	 * 属性CONFIGWHERECOLOMN的setter方法
	 */
	public void setConfigWhereColomn(String configWhereColomn) {
		this.configWhereColomn = configWhereColomn;
	}

	/**
	 * 属性CONFIGWHEREVALUE的getter方法
	 */

	@Column(name = "CONFIGWHEREVALUE")
	public String getConfigWhereValue() {
		return this.configWhereValue;
	}

	/**
	 * 属性CONFIGWHEREVALUE的setter方法
	 */
	public void setConfigWhereValue(String configWhereValue) {
		this.configWhereValue = configWhereValue;
	}

	/**
	 * 属性MESSAGETYPE的getter方法
	 */

	@Column(name = "MESSAGETYPE")
	public String getMessageType() {
		return this.messageType;
	}

	/**
	 * 属性MESSAGETYPE的setter方法
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * 属性REMARK的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性REMARK的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
