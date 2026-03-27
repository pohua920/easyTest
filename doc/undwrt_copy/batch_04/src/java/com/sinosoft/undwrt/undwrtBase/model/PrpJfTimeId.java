package com.sinosoft.undwrt.undwrtBase.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpJfTimeId.
 */
@Embeddable
public class PrpJfTimeId implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性业务号码. */
	private String businessNo;

	/** 属性单证类型. */
	private String certiType;

	/**
	 * 类PrpJfTimeId的默认构造方法.
	 */
	public PrpJfTimeId() {
	}

	/**
	 * 属性业务号码的getter方法.
	 * 
	 * @return the 属性业务号码
	 */

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号码的setter方法.
	 * 
	 * @param businessNo
	 *            the new 属性业务号码
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 属性单证类型的getter方法.
	 * 
	 * @return the 属性单证类型
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性单证类型的setter方法.
	 * 
	 * @param certiType
	 *            the new 属性单证类型
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * Equals.
	 * 
	 * @param other
	 *            the other
	 * @return true, if successful
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpJfTimeId)) {
			return false;
		}
		PrpJfTimeId castOther = (PrpJfTimeId) other;

		return ((this.getBusinessNo() == castOther.getBusinessNo()) || (this
				.getBusinessNo() != null && castOther.getBusinessNo() != null && this
				.getBusinessNo().equals(castOther.getBusinessNo())))
				&& ((this.getCertiType() == castOther.getCertiType()) || (this
						.getCertiType() != null
						&& castOther.getCertiType() != null && this
						.getCertiType().equals(castOther.getCertiType())));
	}

	/**
	 * Hash code.
	 * 
	 * @return the int
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getBusinessNo() == null ? 0 : this.getBusinessNo()
						.hashCode());
		result = 37 * result
				+ (getCertiType() == null ? 0 : this.getCertiType().hashCode());
		return result;
	}

}
