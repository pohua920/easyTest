package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLPersonLimitId
 */
@Embeddable
public class PrpLPersonLimitId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性员工代码 */
	private String userCode;

	/** 属性险种 */
	private String riskCode;

	/**
	 * 类PrpLPersonLimitId的默认构造方法
	 */
	public PrpLPersonLimitId() {
	}

	/**
	 * 属性员工代码的getter方法
	 */

	@Column(name = "USERCODE")
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * 属性员工代码的setter方法
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLPersonLimitId)) {
			return false;
		}
		PrpLPersonLimitId castOther = (PrpLPersonLimitId) other;

		return ((this.getUserCode() == castOther.getUserCode()) || (this.getUserCode() != null && castOther.getUserCode() != null && this.getUserCode().equals(castOther.getUserCode())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getUserCode() == null ? 0 : this.getUserCode().hashCode());
		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		return result;
	}

}
