package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类PrpDcarModelaLias
 */
@Entity
@Table(name = "PRPDCARMODELALIAS")
public class PrpDcarModelaLias implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性别名代码 */
	private String aliasCode;

	/** 属性车型代码 */
	private String modelCode;

	/** 属性别名查询码 */
	private String spellab;

	/** 属性别名名称 */
	private String aliasName;

	/** 属性标准型号编码 */
	private String modelId;

	/** 属性状态标志位（1有效、0无效） */
	private String validStatus;

	/** 属性FLAG */
	private String flag;

	/**
	 * 类PrpDcarModelaLias的默认构造方法
	 */
	public PrpDcarModelaLias() {
	}

	/**
	 * 属性别名代码的getter方法
	 */
	@Id
	@Column(name = "ALIASCODE")
	public String getAliasCode() {
		return this.aliasCode;
	}

	/**
	 * 属性别名代码的setter方法
	 */
	public void setAliasCode(String aliasCode) {
		this.aliasCode = aliasCode;
	}

	/**
	 * 属性车型代码的getter方法
	 */

	@Column(name = "MODELCODE")
	public String getModelCode() {
		return this.modelCode;
	}

	/**
	 * 属性车型代码的setter方法
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * 属性别名查询码的getter方法
	 */

	@Column(name = "SPELLAB")
	public String getSpellab() {
		return this.spellab;
	}

	/**
	 * 属性别名查询码的setter方法
	 */
	public void setSpellab(String spellab) {
		this.spellab = spellab;
	}

	/**
	 * 属性别名名称的getter方法
	 */

	@Column(name = "ALIASNAME")
	public String getAliasName() {
		return this.aliasName;
	}

	/**
	 * 属性别名名称的setter方法
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	/**
	 * 属性标准型号编码的getter方法
	 */

	@Column(name = "MODELID")
	public String getModelId() {
		return this.modelId;
	}

	/**
	 * 属性标准型号编码的setter方法
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/**
	 * 属性状态标志位（1有效、0无效）的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性状态标志位（1有效、0无效）的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
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
