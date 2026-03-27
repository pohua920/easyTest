package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDriskConfigId
 */
@Embeddable
public class PrpDriskConfigId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性归属机构 */
	private String comCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性配置代码 */
	private String configCode;

	/**
	 * 类PrpDriskConfigId的默认构造方法
	 */
	public PrpDriskConfigId() {
	}

	/**
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
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

	/**
	 * 属性配置代码的getter方法
	 */

	@Column(name = "CONFIGCODE")
	public String getConfigCode() {
		return this.configCode;
	}

	/**
	 * 属性配置代码的setter方法
	 */
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDriskConfigId)) {
			return false;
		}
		PrpDriskConfigId castOther = (PrpDriskConfigId) other;

		return ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(castOther.getComCode())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getConfigCode() == castOther.getConfigCode()) || (this.getConfigCode() != null && castOther.getConfigCode() != null && this.getConfigCode().equals(castOther.getConfigCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getConfigCode() == null ? 0 : this.getConfigCode().hashCode());
		return result;
	}

}
