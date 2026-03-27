package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类prpCplaneId
 */
@Embeddable
public class PrpCplaneId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性投保單號碼 */
	private String policyNo;

	/** 属性序列號 */
	private Integer serialNo;

	/**
	 * 类prpCplaneId的默认构造方法
	 */
	public PrpCplaneId() {
	}

	public PrpCplaneId(String policyNo, Integer serialNo) {
		this.policyNo = policyNo;
		this.serialNo = serialNo;
	}

	/**
	 * 属性投保單號碼的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性投保單號碼的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性序列號的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序列號的setter方法
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
		if (!(other instanceof PrpCplaneId)) {
			return false;
		}
		PrpCplaneId castOther = (PrpCplaneId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
