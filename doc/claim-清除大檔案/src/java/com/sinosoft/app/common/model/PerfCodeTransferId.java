package com.sinosoft.app.common.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PerfcodeTransferId
 */
@Embeddable
public class PerfCodeTransferId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性对照关系ID */
	private String transferId;

	/** 属性代码类型 */
	private String codeType;

	/** 属性对照关系代码 */
	private String codeCode;

	/**
	 * 类PerfcodeTransferId的默认构造方法
	 */
	public PerfCodeTransferId() {
	}

	/**
	 * 属性对照关系ID的getter方法
	 */

	@Column(name = "TRANSFERID")
	public String getTransferId() {
		return this.transferId;
	}

	/**
	 * 属性对照关系ID的setter方法
	 */
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}

	/**
	 * 属性代码类型的getter方法
	 */

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
	 * 属性对照关系代码的getter方法
	 */

	@Column(name = "CODECODE")
	public String getCodeCode() {
		return this.codeCode;
	}

	/**
	 * 属性对照关系代码的setter方法
	 */
	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PerfCodeTransferId)) {
			return false;
		}
		PerfCodeTransferId castOther = (PerfCodeTransferId) other;

		return ((this.getTransferId() == castOther.getTransferId()) || (this.getTransferId() != null && castOther.getTransferId() != null && this.getTransferId().equals(castOther.getTransferId())))
				&& ((this.getCodeType() == castOther.getCodeType()) || (this.getCodeType() != null && castOther.getCodeType() != null && this.getCodeType().equals(castOther.getCodeType())))
				&& ((this.getCodeCode() == castOther.getCodeCode()) || (this.getCodeCode() != null && castOther.getCodeCode() != null && this.getCodeCode().equals(castOther.getCodeCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getTransferId() == null ? 0 : this.getTransferId().hashCode());
		result = 37 * result + (getCodeType() == null ? 0 : this.getCodeType().hashCode());
		result = 37 * result + (getCodeCode() == null ? 0 : this.getCodeCode().hashCode());
		return result;
	}

}
