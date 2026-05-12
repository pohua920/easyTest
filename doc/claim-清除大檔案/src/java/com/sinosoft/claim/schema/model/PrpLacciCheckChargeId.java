package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLacciCheckChargeId
 */
@Embeddable
public class PrpLacciCheckChargeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性调查号 */
	private String checkNo;

	/** 属性序号 */
	private Integer serialNo;

	/**
	 * 类PrpLacciCheckChargeId的默认构造方法
	 */
	public PrpLacciCheckChargeId() {
	}

	/**
	 * 属性调查号的getter方法
	 */

	@Column(name = "CHECKNO")
	public String getCheckNo() {
		return this.checkNo;
	}

	/**
	 * 属性调查号的setter方法
	 */
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
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
		if (!(other instanceof PrpLacciCheckChargeId)) {
			return false;
		}
		PrpLacciCheckChargeId castOther = (PrpLacciCheckChargeId) other;

		return ((this.getCheckNo() == castOther.getCheckNo()) || (this.getCheckNo() != null && castOther.getCheckNo() != null && this.getCheckNo().equals(castOther.getCheckNo()))) && (this.getSerialNo() == castOther.getSerialNo());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCheckNo() == null ? 0 : this.getCheckNo().hashCode());
		result = 37 * result + this.getSerialNo();
		return result;
	}

}
