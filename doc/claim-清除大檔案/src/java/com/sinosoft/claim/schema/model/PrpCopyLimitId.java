package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpClimitId
 */
@Embeddable
public class PrpCopyLimitId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性批单号码 */
	private String endorseNo;

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
	public PrpCopyLimitId() {
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
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
		if (!(other instanceof PrpCopyLimitId)) {
			return false;
		}
		PrpCopyLimitId castOther = (PrpCopyLimitId) other;

		return ((this.getEndorseNo() == castOther.getEndorseNo()) || (this.getEndorseNo() != null && castOther.getEndorseNo() != null && this.getEndorseNo().equals(castOther.getEndorseNo())))
				&& ((this.getLimitGrade() == castOther.getLimitGrade()) || (this.getLimitGrade() != null && castOther.getLimitGrade() != null && this.getLimitGrade().equals(castOther.getLimitGrade())))
				&& ((this.getLimitNo() == castOther.getLimitNo()) || (this.getLimitNo() != null && castOther.getLimitNo() != null && this.getLimitNo().equals(castOther.getLimitNo())))
				&& ((this.getLimitType() == castOther.getLimitType()) || (this.getLimitType() != null && castOther.getLimitType() != null && this.getLimitType().equals(castOther.getLimitType())))
				&& ((this.getCurrency() == castOther.getCurrency()) || (this.getCurrency() != null && castOther.getCurrency() != null && this.getCurrency().equals(castOther.getCurrency())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEndorseNo() == null ? 0 : this.getEndorseNo().hashCode());
		result = 37 * result + (getLimitGrade() == null ? 0 : this.getLimitGrade().hashCode());
		result = 37 * result + (getLimitNo() == null ? 0 : this.getLimitNo().hashCode());
		result = 37 * result + (getLimitType() == null ? 0 : this.getLimitType().hashCode());
		result = 37 * result + (getCurrency() == null ? 0 : this.getCurrency().hashCode());
		return result;
	}

}
