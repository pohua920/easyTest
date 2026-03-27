package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO类PrpDtype
 */
@Entity
@Table(name = "PRPDTYPE")
public class PrpDtype implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性代码类型 */
	private String codeType;

	/** 属性代码类型描述 */
	private String codeTypeDesc;

	/** 属性新的代码类型 */
	private String newCodeType;

	/** 属性效力状态（0失效/1有效） */
	private String validStatus;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpDtype的默认构造方法
	 */
	public PrpDtype() {
	}

	/**
	 * 属性代码类型的getter方法
	 */
	@Id
	@Column(name = "CODETYPE")
	public String getCodeType() {
		return this.codeType;
	}

	/**
	 * 属性代码类型的setter方法
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * 属性代码类型描述的getter方法
	 */

	@Column(name = "CODETYPEDESC")
	public String getCodeTypeDesc() {
		return this.codeTypeDesc;
	}

	/**
	 * 属性代码类型描述的setter方法
	 */
	public void setCodeTypeDesc(String codeTypeDesc) {
		this.codeTypeDesc = codeTypeDesc;
	}

	/**
	 * 属性新的代码类型的getter方法
	 */

	@Column(name = "NEWCODETYPE")
	public String getNewCodeType() {
		return this.newCodeType;
	}

	/**
	 * 属性新的代码类型的setter方法
	 */
	public void setNewCodeType(String newCodeType) {
		this.newCodeType = newCodeType;
	}

	/**
	 * 属性效力状态（0失效/1有效）的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性效力状态（0失效/1有效）的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

}
