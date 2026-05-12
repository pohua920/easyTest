package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDlimitId
 */
@Embeddable
public class PrpDlimitId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性险种 */
	private String riskCode;

	/** 属性限额/免赔类别代码 */
	private String limitCode;

	/**
	 * 类PrpDlimitId的默认构造方法
	 */
	public PrpDlimitId() {
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性限额/免赔类别代码的getter方法
	 */

	@Column(name = "LIMITCODE")
	public String getLimitCode() {
		return this.limitCode;
	}

	/**
	 * 属性限额/免赔类别代码的setter方法
	 */
	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDlimitId)) {
			return false;
		}
		PrpDlimitId castOther = (PrpDlimitId) other;

		return ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getLimitCode() == castOther.getLimitCode()) || (this.getLimitCode() != null && castOther.getLimitCode() != null && this.getLimitCode().equals(castOther.getLimitCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getLimitCode() == null ? 0 : this.getLimitCode().hashCode());
		return result;
	}

	public PrpDlimitId(String riskCode, String limitCode) {
		this.riskCode = riskCode;
		this.limitCode = limitCode;
	}

}
