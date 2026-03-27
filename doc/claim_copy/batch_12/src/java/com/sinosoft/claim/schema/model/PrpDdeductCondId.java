package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 免赔条件表的数据传输对象类主键PrpDdeductCondId
 */
@Embeddable
public class PrpDdeductCondId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性险种 */
	private String riskCode;

	/** 属性条款类别 */
	private String clauseType;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性免赔条件代码 */
	private String deductCondCode;

	/** 属性期数 */
	private String deductPeriod;

	public PrpDdeductCondId(String riskCode, String clauseType, String kindCode, String deductCondCode, String deductPeriod) {
		this.riskCode = riskCode;
		this.clauseType = clauseType;
		this.kindCode = kindCode;
		this.deductCondCode = deductCondCode;
		this.deductPeriod = deductPeriod;
	}

	/**
	 * 类PrpDdeductCondId的默认构造方法
	 */
	public PrpDdeductCondId() {
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
	 * 属性免赔条件代码的getter方法
	 */

	@Column(name = "DEDUCTCONDCODE")
	public String getDeductCondCode() {
		return this.deductCondCode;
	}

	/**
	 * 属性免赔条件代码的setter方法
	 */
	public void setDeductCondCode(String deductCondCode) {
		this.deductCondCode = deductCondCode;
	}

	/**
	 * 属性期数的getter方法
	 */

	@Column(name = "DEDUCTPERIOD")
	public String getDeductPeriod() {
		return this.deductPeriod;
	}

	/**
	 * 属性期数的setter方法
	 */
	public void setDeductPeriod(String deductPeriod) {
		this.deductPeriod = deductPeriod;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDdeductCondId)) {
			return false;
		}
		PrpDdeductCondId castOther = (PrpDdeductCondId) other;

		return ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getClauseType() == castOther.getClauseType()) || (this.getClauseType() != null && castOther.getClauseType() != null && this.getClauseType().equals(castOther.getClauseType())))
				&& ((this.getKindCode() == castOther.getKindCode()) || (this.getKindCode() != null && castOther.getKindCode() != null && this.getKindCode().equals(castOther.getKindCode())))
				&& ((this.getDeductCondCode() == castOther.getDeductCondCode()) || (this.getDeductCondCode() != null && castOther.getDeductCondCode() != null && this.getDeductCondCode().equals(castOther.getDeductCondCode())))
				&& ((this.getDeductPeriod() == castOther.getDeductPeriod()) || (this.getDeductPeriod() != null && castOther.getDeductPeriod() != null && this.getDeductPeriod().equals(castOther.getDeductPeriod())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getClauseType() == null ? 0 : this.getClauseType().hashCode());
		result = 37 * result + (getKindCode() == null ? 0 : this.getKindCode().hashCode());
		result = 37 * result + (getDeductCondCode() == null ? 0 : this.getDeductCondCode().hashCode());
		result = 37 * result + (getDeductPeriod() == null ? 0 : this.getDeductPeriod().hashCode());
		return result;
	}

}
