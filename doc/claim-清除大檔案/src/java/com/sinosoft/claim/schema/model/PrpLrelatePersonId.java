package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLrelatePersonId
 */
@Embeddable
public class PrpLrelatePersonId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号 */
	private String registNo;

	/** 属性人员类型 */
	private String personType;

	/** 属性SerialNo */
	private BigDecimal serialNo;

	/**
	 * 类PrpLrelatePersonId的默认构造方法
	 */
	public PrpLrelatePersonId() {
	}

	/**
	 * 属性报案号的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性人员类型的getter方法
	 */

	@Column(name = "PERSONTYPE")
	public String getPersonType() {
		return this.personType;
	}

	/**
	 * 属性人员类型的setter方法
	 */
	public void setPersonType(String personType) {
		this.personType = personType;
	}

	/**
	 * 属性SerialNo的getter方法
	 */

	@Column(name = "SERIALNO")
	public BigDecimal getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性SerialNo的setter方法
	 */
	public void setSerialNo(BigDecimal serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLrelatePersonId)) {
			return false;
		}
		PrpLrelatePersonId castOther = (PrpLrelatePersonId) other;

		return ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getPersonType() == castOther.getPersonType()) || (this.getPersonType() != null && castOther.getPersonType() != null && this.getPersonType().equals(castOther.getPersonType())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getPersonType() == null ? 0 : this.getPersonType().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
