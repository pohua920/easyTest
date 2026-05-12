package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLFMappingId
 */
@Embeddable
public class PrpLFMappingId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性费用类型代码 */
	private String chargeCode;

	/** 属性收付原因代码 */
	private String payRefReason;

	/**
	 * 类PrpLFMappingId的默认构造方法
	 */
	public PrpLFMappingId() {
	}

	/**
	 * 属性费用类型代码的getter方法
	 */

	@Column(name = "CHARGECODE")
	public String getChargeCode() {
		return this.chargeCode;
	}

	/**
	 * 属性费用类型代码的setter方法
	 */
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	/**
	 * 属性收付原因代码的getter方法
	 */

	@Column(name = "PAYREFREASON")
	public String getPayRefReason() {
		return this.payRefReason;
	}

	/**
	 * 属性收付原因代码的setter方法
	 */
	public void setPayRefReason(String payRefReason) {
		this.payRefReason = payRefReason;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLFMappingId)) {
			return false;
		}
		PrpLFMappingId castOther = (PrpLFMappingId) other;

		return ((this.getChargeCode() == castOther.getChargeCode()) || (this.getChargeCode() != null && castOther.getChargeCode() != null && this.getChargeCode().equals(castOther.getChargeCode())))
				&& ((this.getPayRefReason() == castOther.getPayRefReason()) || (this.getPayRefReason() != null && castOther.getPayRefReason() != null && this.getPayRefReason().equals(castOther.getPayRefReason())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getChargeCode() == null ? 0 : this.getChargeCode().hashCode());
		result = 37 * result + (getPayRefReason() == null ? 0 : this.getPayRefReason().hashCode());
		return result;
	}

}
