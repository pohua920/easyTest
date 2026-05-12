package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLcheckId
 */
@Embeddable
public class PrpLcheckId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号码 */
	private String registNo;

	/** 属性关联理赔车辆序号 */
	private Integer referSerialNo;

	/**
	 * 类PrpLcheckId的默认构造方法
	 */
	public PrpLcheckId() {
	}

	public PrpLcheckId(String registNo, Integer referSerialNo) {
		this.registNo = registNo;
		this.referSerialNo = referSerialNo;
	}

	/**
	 * 属性报案号码的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号码的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性关联理赔车辆序号的getter方法
	 */

	@Column(name = "REFERSERIALNO")
	public Integer getReferSerialNo() {
		return this.referSerialNo;
	}

	/**
	 * 属性关联理赔车辆序号的setter方法
	 */
	public void setReferSerialNo(Integer referSerialNo) {
		this.referSerialNo = referSerialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLcheckId)) {
			return false;
		}
		PrpLcheckId castOther = (PrpLcheckId) other;

		return ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getReferSerialNo() == castOther.getReferSerialNo()) || (this.getReferSerialNo() != null && castOther.getReferSerialNo() != null && this.getReferSerialNo().equals(castOther.getReferSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getReferSerialNo() == null ? 0 : this.getReferSerialNo().hashCode());
		return result;
	}

}
