package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDpersonFeeCodeRiskId
 */
@Embeddable
public class PrpDpersonFeeCodeRiskId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性费用代码 */
	private String feeCode;

	/**
	 * 类PrpDpersonFeeCodeRiskId的默认构造方法
	 */
	public PrpDpersonFeeCodeRiskId() {
	}

	public PrpDpersonFeeCodeRiskId(String riskCode, String feeCode) {
		this.feeCode = feeCode;
		this.riskCode = riskCode;
	}

	/**
	 * 属性费用代码的getter方法
	 */

	@Column(name = "FEECODE")
	public String getFeeCode() {
		return this.feeCode;
	}

	/**
	 * 属性费用代码的setter方法
	 */
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	/**
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDpersonFeeCodeRiskId)) {
			return false;
		}
		PrpDpersonFeeCodeRiskId castOther = (PrpDpersonFeeCodeRiskId) other;

		return ((this.getFeeCode() == castOther.getFeeCode()) || (this.getFeeCode() != null && castOther.getFeeCode() != null && this.getFeeCode().equals(castOther.getFeeCode())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getFeeCode() == null ? 0 : this.getFeeCode().hashCode());
		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		return result;
	}

}
