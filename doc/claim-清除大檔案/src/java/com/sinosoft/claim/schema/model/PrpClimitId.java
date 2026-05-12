package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpClimitId
 */
@Embeddable
public class PrpClimitId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性LIMITGRADE */
	private String limitGrade;

	/** 属性LIMITNO */
	private Integer limitNo;

	/** 属性LIMITTYPE */
	private String limitType;

	/** 属性币别 */
	private String currency;

	/**
	 * 类PrpClimitId的默认构造方法
	 */
	public PrpClimitId() {
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
	 * 属性LIMITGRADE的getter方法
	 */

	@Column(name = "LIMITGRADE")
	public String getLimitGrade() {
		return this.limitGrade;
	}

	/**
	 * 属性LIMITGRADE的setter方法
	 */
	public void setLimitGrade(String limitGrade) {
		this.limitGrade = limitGrade;
	}

	/**
	 * 属性LIMITNO的getter方法
	 */

	@Column(name = "LIMITNO")
	public Integer getLimitNo() {
		return this.limitNo;
	}

	/**
	 * 属性LIMITNO的setter方法
	 */
	public void setLimitNo(Integer limitNo) {
		this.limitNo = limitNo;
	}

	/**
	 * 属性LIMITTYPE的getter方法
	 */

	@Column(name = "LIMITTYPE")
	public String getLimitType() {
		return this.limitType;
	}

	/**
	 * 属性LIMITTYPE的setter方法
	 */
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	/**
	 * 属性币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpClimitId)) {
			return false;
		}
		PrpClimitId castOther = (PrpClimitId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getLimitGrade() == castOther.getLimitGrade()) || (this.getLimitGrade() != null && castOther.getLimitGrade() != null && this.getLimitGrade().equals(castOther.getLimitGrade())))
				&& ((this.getLimitNo() == castOther.getLimitNo()) || (this.getLimitNo() != null && castOther.getLimitNo() != null && this.getLimitNo().equals(castOther.getLimitNo())))
				&& ((this.getLimitType() == castOther.getLimitType()) || (this.getLimitType() != null && castOther.getLimitType() != null && this.getLimitType().equals(castOther.getLimitType())))
				&& ((this.getCurrency() == castOther.getCurrency()) || (this.getCurrency() != null && castOther.getCurrency() != null && this.getCurrency().equals(castOther.getCurrency())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getLimitGrade() == null ? 0 : this.getLimitGrade().hashCode());
		result = 37 * result + (getLimitNo() == null ? 0 : this.getLimitNo().hashCode());
		result = 37 * result + (getLimitType() == null ? 0 : this.getLimitType().hashCode());
		result = 37 * result + (getCurrency() == null ? 0 : this.getCurrency().hashCode());
		return result;
	}

}
