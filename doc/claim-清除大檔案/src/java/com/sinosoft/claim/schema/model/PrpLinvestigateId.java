package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLinvestigateId
 */
@Embeddable
public class PrpLinvestigateId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性REGISTNO */
	private String registno;

	/** 属性调查对象类型 */
	private String objectType;

	/** 属性交费计划序号 */
	private BigDecimal serialNo;

	/**
	 * 类PrpLinvestigateId的默认构造方法
	 */
	public PrpLinvestigateId() {
	}

	/**
	 * 属性REGISTNO的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistno() {
		return this.registno;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistno(String registno) {
		this.registno = registno;
	}

	/**
	 * 属性调查对象类型的getter方法
	 */

	@Column(name = "OBJECTTYPE")
	public String getObjectType() {
		return this.objectType;
	}

	/**
	 * 属性调查对象类型的setter方法
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	/**
	 * 属性交费计划序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public BigDecimal getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性交费计划序号的setter方法
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
		if (!(other instanceof PrpLinvestigateId)) {
			return false;
		}
		PrpLinvestigateId castOther = (PrpLinvestigateId) other;

		return ((this.getRegistno() == castOther.getRegistno()) || (this.getRegistno() != null && castOther.getRegistno() != null && this.getRegistno().equals(castOther.getRegistno())))
				&& ((this.getObjectType() == castOther.getObjectType()) || (this.getObjectType() != null && castOther.getObjectType() != null && this.getObjectType().equals(castOther.getObjectType())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistno() == null ? 0 : this.getRegistno().hashCode());
		result = 37 * result + (getObjectType() == null ? 0 : this.getObjectType().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
