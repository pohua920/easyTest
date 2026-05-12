package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * POJO类PrpdRiskConfig
 */
@Entity
@Table(name = "PRPDRISKCONFIG")
public class PrpDriskConfig implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpDriskConfigId id;

	/** 属性配置名称 */
	private String configName;

	/** 属性配置值 */
	private String configValue;

	/** 属性配置值描述 */
	private String configValueDesc;

	/**
	 * 类PrpDriskConfig的默认构造方法
	 */
	public PrpDriskConfig() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "comCode", column = @Column(name = "COMCODE")), @AttributeOverride(name = "riskCode", column = @Column(name = "RISKCODE")),
			@AttributeOverride(name = "configCode", column = @Column(name = "CONFIGCODE")) })
	public PrpDriskConfigId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpDriskConfigId id) {
		this.id = id;
	}

	/**
	 * 属性配置名称的getter方法
	 */

	@Column(name = "CONFIGNAME")
	public String getConfigName() {
		return this.configName;
	}

	/**
	 * 属性配置名称的setter方法
	 */
	public void setConfigName(String configName) {
		this.configName = configName;
	}

	/**
	 * 属性配置值的getter方法
	 */

	@Column(name = "CONFIGVALUE")
	public String getConfigValue() {
		return this.configValue;
	}

	/**
	 * 属性配置值的setter方法
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	/**
	 * 属性配置值描述的getter方法
	 */

	@Column(name = "CONFIGVALUEDESC")
	public String getConfigValueDesc() {
		return this.configValueDesc;
	}

	/**
	 * 属性配置值描述的setter方法
	 */
	public void setConfigValueDesc(String configValueDesc) {
		this.configValueDesc = configValueDesc;
	}

}
