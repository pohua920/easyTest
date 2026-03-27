package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrplregistrpolicyId
 */
@Embeddable
public class PrplregistrpolicyId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号 */
	private String registNo;

	/** 属性保单号 */
	private String policyNo;

	/**
	 * 类PrplregistrpolicyId的默认构造方法
	 */
	public PrplregistrpolicyId() {
	}

	public PrplregistrpolicyId(String registNo, String policyNo) {
		this.registNo = registNo;
		this.policyNo = policyNo;
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
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrplregistrpolicyId)) {
			return false;
		}
		PrplregistrpolicyId castOther = (PrplregistrpolicyId) other;

		return ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		return result;
	}

}
