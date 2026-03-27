package com.sinosoft.undwrt.undwrtBase.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpJpayRefRecId
 */
@Embeddable
public class PrpJpayRefRecId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性属性业务类型 */
	private String certiType;

	/** 属性属性保单号码/批单号码 */
	private String certiNo;

	/** 属性属性交费计划序号 */
	private Integer serialNo;

	/** 属性收付原因 */
	private String payRefReason;

	/** 属性属性收付次数 */
	private Integer payRefTimes;

	/**
	 * 类PrpJpayRefRecId的默认构造方法
	 */
	public PrpJpayRefRecId() {
	}

	/**
	 * 属性属性业务类型的getter方法
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性属性业务类型的setter方法
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性属性保单号码/批单号码的getter方法
	 */

	@Column(name = "CERTINO")
	public String getCertiNo() {
		return this.certiNo;
	}

	/**
	 * 属性属性保单号码/批单号码的setter方法
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 属性属性交费计划序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性属性交费计划序号的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性收付原因的getter方法
	 */

	@Column(name = "PAYREFREASON")
	public String getPayRefReason() {
		return this.payRefReason;
	}

	/**
	 * 属性收付原因的setter方法
	 */
	public void setPayRefReason(String payRefReason) {
		this.payRefReason = payRefReason;
	}

	/**
	 * 属性属性收付次数的getter方法
	 */

	@Column(name = "PAYREFTIMES")
	public Integer getPayRefTimes() {
		return this.payRefTimes;
	}

	/**
	 * 属性属性收付次数的setter方法
	 */
	public void setPayRefTimes(Integer payRefTimes) {
		this.payRefTimes = payRefTimes;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpJpayRefRecId)) {
			return false;
		}
		PrpJpayRefRecId castOther = (PrpJpayRefRecId) other;

		return ((this.getCertiType() == castOther.getCertiType()) || (this
				.getCertiType() != null && castOther.getCertiType() != null && this
				.getCertiType().equals(castOther.getCertiType())))
				&& ((this.getCertiNo() == castOther.getCertiNo()) || (this
						.getCertiNo() != null && castOther.getCertiNo() != null && this
						.getCertiNo().equals(castOther.getCertiNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this
						.getSerialNo().equals(castOther.getSerialNo())))
				&& ((this.getPayRefReason() == castOther.getPayRefReason()) || (this
						.getPayRefReason() != null
						&& castOther.getPayRefReason() != null && this
						.getPayRefReason().equals(castOther.getPayRefReason())))
				&& ((this.getPayRefTimes() == castOther.getPayRefTimes()) || (this
						.getPayRefTimes() != null
						&& castOther.getPayRefTimes() != null && this
						.getPayRefTimes().equals(castOther.getPayRefTimes())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCertiType() == null ? 0 : this.getCertiType().hashCode());
		result = 37 * result
				+ (getCertiNo() == null ? 0 : this.getCertiNo().hashCode());
		result = 37 * result
				+ (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		result = 37
				* result
				+ (getPayRefReason() == null ? 0 : this.getPayRefReason()
						.hashCode());
		result = 37
				* result
				+ (getPayRefTimes() == null ? 0 : this.getPayRefTimes()
						.hashCode());
		return result;
	}

}
