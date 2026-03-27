package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 * 受害人就诊医院
 * @author 中科软
 *
 */
@Embeddable
public class PrpLfractureId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 骨折程度代码 */
	private String fractureCode;

	/** 骨折程度类型，骨折程度-FractureDegree,骨折部位-FractureSite*/
	private String fractureType;

	/**
	 * 类PrpLpersonLossId的默认构造方法
	 */
	public PrpLfractureId() {
	}
	@Column(name = "fractureCode")
	public String getFractureCode() {
		return fractureCode;
	}

	public void setFractureCode(String fractureCode) {
		this.fractureCode = fractureCode;
	}
	@Column(name = "fractureType")
	public String getFractureType() {
		return fractureType;
	}

	public void setFractureType(String fractureType) {
		this.fractureType = fractureType;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLfractureId)) {
			return false;
		}
		PrpLfractureId castOther = (PrpLfractureId) other;

		return ((this.getFractureCode() == castOther.getFractureCode()) || (this.getFractureCode() != null && castOther.getFractureCode() != null && this.getFractureCode().equals(castOther.getFractureCode())))
				&& ((this.getFractureType() == castOther.getFractureType()) || (this.getFractureType() != null && castOther.getFractureType() != null && this.getFractureType().equals(castOther.getFractureType())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (getFractureCode() == null ? 0 : this.getFractureCode().hashCode());
		result = 37 * result + (getFractureType() == null ? 0 : this.getFractureType().hashCode());
		return result;
	}

}
