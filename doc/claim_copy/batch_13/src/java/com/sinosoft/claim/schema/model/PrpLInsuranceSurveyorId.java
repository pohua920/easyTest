package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLInsuranceSurveyorId
 */
@Embeddable
public class PrpLInsuranceSurveyorId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性公估师代码 */
	private String comCode;

	/** 属性公估机构代码 */
	private String newcomcode;

	/**
	 * 类PrpLInsuranceSurveyorId的默认构造方法
	 */
	public PrpLInsuranceSurveyorId() {
	}

	/**
	 * 属性公估师代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性公估师代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性公估机构代码的getter方法
	 */

	@Column(name = "NEWCOMCODE")
	public String getNewcomcode() {
		return this.newcomcode;
	}

	/**
	 * 属性公估机构代码的setter方法
	 */
	public void setNewcomcode(String newcomcode) {
		this.newcomcode = newcomcode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLInsuranceSurveyorId)) {
			return false;
		}
		PrpLInsuranceSurveyorId castOther = (PrpLInsuranceSurveyorId) other;

		return ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(castOther.getComCode())))
				&& ((this.getNewcomcode() == castOther.getNewcomcode()) || (this.getNewcomcode() != null && castOther.getNewcomcode() != null && this.getNewcomcode().equals(castOther.getNewcomcode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (getNewcomcode() == null ? 0 : this.getNewcomcode().hashCode());
		return result;
	}

}
