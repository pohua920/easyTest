package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCaddressId
 */
@Embeddable
public class PrpCaddressId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性地址序号 */
	private Integer addressNo;

	/**
	 * 类PrpCaddressId的默认构造方法
	 */
	public PrpCaddressId() {
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性地址序号的getter方法
	 */

	@Column(name = "ADDRESSNO")
	public Integer getAddressNo() {
		return this.addressNo;
	}

	/**
	 * 属性地址序号的setter方法
	 */
	public void setAddressNo(Integer addressNo) {
		this.addressNo = addressNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpCaddressId)) {
			return false;
		}
		PrpCaddressId castOther = (PrpCaddressId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getAddressNo() == castOther.getAddressNo()) || (this.getAddressNo() != null && castOther.getAddressNo() != null && this.getAddressNo().equals(castOther.getAddressNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getAddressNo() == null ? 0 : this.getAddressNo().hashCode());
		return result;
	}

}
