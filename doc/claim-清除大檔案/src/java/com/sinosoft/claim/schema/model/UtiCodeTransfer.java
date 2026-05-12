package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类UtiCodeTransfer险种险类代码对照表
 */
@Entity
@Table(name = "UTICODETRANSFER")
public class UtiCodeTransfer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性程序中代码 */
	private String configCode;

	/** 属性外部代码 */
	private String outerCode;

	/** 属性内部代码 */
	private String innerCode;

	/** 属性接口代码类型 */
	private String codeType;

	/** 属性效力状态 */
	private String validStatus;

	/** 属性险别大类 */
	private String riskType;

	/**
	 * 类UtiCodeTransfer的默认构造方法
	 */
	public UtiCodeTransfer() {
	}

	/**
	 * 属性程序中代码的getter方法
	 */
	@Id
	@Column(name = "CONFIGCODE")
	public String getConfigCode() {
		return this.configCode;
	}

	/**
	 * 属性程序中代码的setter方法
	 */
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	/**
	 * 属性外部代码的getter方法
	 */

	@Column(name = "OUTERCODE")
	public String getOuterCode() {
		return this.outerCode;
	}

	/**
	 * 属性外部代码的setter方法
	 */
	public void setOuterCode(String outerCode) {
		this.outerCode = outerCode;
	}

	/**
	 * 属性内部代码的getter方法
	 */

	@Column(name = "INNERCODE")
	public String getInnerCode() {
		return this.innerCode;
	}

	/**
	 * 属性内部代码的setter方法
	 */
	public void setInnerCode(String innerCode) {
		this.innerCode = innerCode;
	}

	/**
	 * 属性接口代码类型的getter方法
	 */

	@Column(name = "CODETYPE")
	public String getCodeType() {
		return this.codeType;
	}

	/**
	 * 属性接口代码类型的setter方法
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
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
	 * 属性险别大类的getter方法
	 */

	@Column(name = "RISKTYPE")
	public String getRiskType() {
		return this.riskType;
	}

	/**
	 * 属性险别大类的setter方法
	 */
	public void setRiskType(String riskType) {
		this.riskType = riskType;
	}

}
