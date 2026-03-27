package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfPathId
 */
@Embeddable
public class SwfPathId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性模板编码 */
	private Integer modelNo = 0;

	/** 属性流程边编码 */
	private Integer pathNo = 0;

	/**
	 * 类SwfPathId的默认构造方法
	 */
	public SwfPathId() {
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
	 * 属性流程边编码的getter方法
	 */

	@Column(name = "PATHNO")
	public Integer getPathNo() {
		return this.pathNo;
	}

	/**
	 * 属性流程边编码的setter方法
	 */
	public void setPathNo(Integer pathNo) {
		this.pathNo = pathNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof SwfPathId)) {
			return false;
		}
		SwfPathId castOther = (SwfPathId) other;

		return ((this.getModelNo() == castOther.getModelNo()) || (this.getModelNo() != null && castOther.getModelNo() != null && this.getModelNo().equals(castOther.getModelNo())))
				&& ((this.getPathNo() == castOther.getPathNo()) || (this.getPathNo() != null && castOther.getPathNo() != null && this.getPathNo().equals(castOther.getPathNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getModelNo() == null ? 0 : this.getModelNo().hashCode());
		result = 37 * result + (getPathNo() == null ? 0 : this.getPathNo().hashCode());
		return result;
	}

}
