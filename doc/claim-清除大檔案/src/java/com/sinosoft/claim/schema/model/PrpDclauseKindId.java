package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDclauseKindId
 */
@Embeddable
public class PrpDclauseKindId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性条款类别 */
	private String clauseType;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性关联险别代码 */
	private String relateKindCode;

	/**
	 * 类PrpDclauseKindId的默认构造方法
	 */
	public PrpDclauseKindId() {
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
	 * 属性条款类别的getter方法
	 */

	@Column(name = "CLAUSETYPE")
	public String getClauseType() {
		return this.clauseType;
	}

	/**
	 * 属性条款类别的setter方法
	 */
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}

	/**
	 * 属性险别代码的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别代码的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * 属性关联险别代码的getter方法
	 */

	@Column(name = "RELATEKINDCODE")
	public String getRelateKindCode() {
		return this.relateKindCode;
	}

	/**
	 * 属性关联险别代码的setter方法
	 */
	public void setRelateKindCode(String relateKindCode) {
		this.relateKindCode = relateKindCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDclauseKindId)) {
			return false;
		}
		PrpDclauseKindId castOther = (PrpDclauseKindId) other;

		return ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getClauseType() == castOther.getClauseType()) || (this.getClauseType() != null && castOther.getClauseType() != null && this.getClauseType().equals(castOther.getClauseType())))
				&& ((this.getKindCode() == castOther.getKindCode()) || (this.getKindCode() != null && castOther.getKindCode() != null && this.getKindCode().equals(castOther.getKindCode())))
				&& ((this.getRelateKindCode() == castOther.getRelateKindCode()) || (this.getRelateKindCode() != null && castOther.getRelateKindCode() != null && this.getRelateKindCode().equals(castOther.getRelateKindCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getClauseType() == null ? 0 : this.getClauseType().hashCode());
		result = 37 * result + (getKindCode() == null ? 0 : this.getKindCode().hashCode());
		result = 37 * result + (getRelateKindCode() == null ? 0 : this.getRelateKindCode().hashCode());
		return result;
	}

}
