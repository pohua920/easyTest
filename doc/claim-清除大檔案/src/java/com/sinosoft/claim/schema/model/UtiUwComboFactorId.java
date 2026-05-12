package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类UtiUwComboFactorId
 */
@Embeddable
public class UtiUwComboFactorId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性审核类型 */
	private String uwType;

	/** 属性险类代码 */
	private String classCode;

	/** 属性因子代码 */
	private String factorCode;

	/** 属性代码类型 */
	private String codeType;

	/**
	 * 类UtiUwComboFactorId的默认构造方法
	 */
	public UtiUwComboFactorId() {
	}

	/**
	 * 属性审核类型的getter方法
	 */

	@Column(name = "UWTYPE")
	public String getUwType() {
		return this.uwType;
	}

	/**
	 * 属性审核类型的setter方法
	 */
	public void setUwType(String uwType) {
		this.uwType = uwType;
	}

	/**
	 * 属性险类代码的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性险类代码的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 属性因子代码的getter方法
	 */

	@Column(name = "FACTORCODE")
	public String getFactorCode() {
		return this.factorCode;
	}

	/**
	 * 属性因子代码的setter方法
	 */
	public void setFactorCode(String factorCode) {
		this.factorCode = factorCode;
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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof UtiUwComboFactorId)) {
			return false;
		}
		UtiUwComboFactorId castOther = (UtiUwComboFactorId) other;

		return ((this.getUwType() == castOther.getUwType()) || (this.getUwType() != null && castOther.getUwType() != null && this.getUwType().equals(castOther.getUwType())))
				&& ((this.getClassCode() == castOther.getClassCode()) || (this.getClassCode() != null && castOther.getClassCode() != null && this.getClassCode().equals(castOther.getClassCode())))
				&& ((this.getFactorCode() == castOther.getFactorCode()) || (this.getFactorCode() != null && castOther.getFactorCode() != null && this.getFactorCode().equals(castOther.getFactorCode())))
				&& ((this.getCodeType() == castOther.getCodeType()) || (this.getCodeType() != null && castOther.getCodeType() != null && this.getCodeType().equals(castOther.getCodeType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUwType() == null ? 0 : this.getUwType().hashCode());
		result = 37 * result + (getClassCode() == null ? 0 : this.getClassCode().hashCode());
		result = 37 * result + (getFactorCode() == null ? 0 : this.getFactorCode().hashCode());
		result = 37 * result + (getCodeType() == null ? 0 : this.getCodeType().hashCode());
		return result;
	}

}
