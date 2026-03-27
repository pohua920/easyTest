package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfPathGraphId
 */
@Embeddable
public class SwfPathGraphId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性模板编码 */
	private Integer modelNo = 0;

	/** 属性流程边编码 */
	private Integer pathNo = 0;

	/** 属性序号 */
	private Integer serialNo = 0;

	/**
	 * 类SwfPathGraphId的默认构造方法
	 */
	public SwfPathGraphId() {
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

	/**
	 * 属性序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof SwfPathGraphId)) {
			return false;
		}
		SwfPathGraphId castOther = (SwfPathGraphId) other;

		return ((this.getModelNo() == castOther.getModelNo()) || (this.getModelNo() != null && castOther.getModelNo() != null && this.getModelNo().equals(castOther.getModelNo())))
				&& ((this.getPathNo() == castOther.getPathNo()) || (this.getPathNo() != null && castOther.getPathNo() != null && this.getPathNo().equals(castOther.getPathNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getModelNo() == null ? 0 : this.getModelNo().hashCode());
		result = 37 * result + (getPathNo() == null ? 0 : this.getPathNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
