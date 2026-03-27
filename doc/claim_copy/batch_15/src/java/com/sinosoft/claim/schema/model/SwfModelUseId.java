package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfModelUseId
 */
@Embeddable
public class SwfModelUseId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性模板编码 */
	private Integer modelNo = 0;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性部门代码 */
	private String comCode;

	/** 属性模板类型 */
	private String modelType;

	/**
	 * 类SwfModelUseId的默认构造方法
	 */
	public SwfModelUseId() {
	}

	/**
	 * 属性模板编码的getter方法
	 */

	@Column(name = "MODELNO")
	public Integer getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性模板编码的setter方法
	 */
	public void setModelNo(Integer modelNo) {
		this.modelNo = modelNo;
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
	 * 属性部门代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性部门代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性模板类型的getter方法
	 */

	@Column(name = "MODELTYPE")
	public String getModelType() {
		return this.modelType;
	}

	/**
	 * 属性模板类型的setter方法
	 */
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof SwfModelUseId)) {
			return false;
		}
		SwfModelUseId castOther = (SwfModelUseId) other;

		return ((this.getModelNo() == castOther.getModelNo()) || (this.getModelNo() != null && castOther.getModelNo() != null && this.getModelNo().equals(castOther.getModelNo())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(castOther.getComCode())))
				&& ((this.getModelType() == castOther.getModelType()) || (this.getModelType() != null && castOther.getModelType() != null && this.getModelType().equals(castOther.getModelType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getModelNo() == null ? 0 : this.getModelNo().hashCode());
		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (getModelType() == null ? 0 : this.getModelType().hashCode());
		return result;
	}

}
