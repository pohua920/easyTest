package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLacciPersonId
 */
@Embeddable
public class PrpLacciPersonId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性CertiNo */
	private String certiNo;

	/** 属性CertiType */
	private String certiType;

	/** 属性SerialNo */
	private Integer serialNo;

	/**
	 * 类PrpLacciPersonId的默认构造方法
	 */
	public PrpLacciPersonId() {
	}

	/**
	 * 属性CertiNo的getter方法
	 */

	@Column(name = "CERTINO")
	public String getCertiNo() {
		return this.certiNo;
	}

	/**
	 * 属性CertiNo的setter方法
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 属性CertiType的getter方法
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性CertiType的setter方法
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性SerialNo的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性SerialNo的setter方法
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
		if (!(other instanceof PrpLacciPersonId)) {
			return false;
		}
		PrpLacciPersonId castOther = (PrpLacciPersonId) other;

		return ((this.getCertiNo() == castOther.getCertiNo()) || (this.getCertiNo() != null && castOther.getCertiNo() != null && this.getCertiNo().equals(castOther.getCertiNo())))
				&& ((this.getCertiType() == castOther.getCertiType()) || (this.getCertiType() != null && castOther.getCertiType() != null && this.getCertiType().equals(castOther.getCertiType())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCertiNo() == null ? 0 : this.getCertiNo().hashCode());
		result = 37 * result + (getCertiType() == null ? 0 : this.getCertiType().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
