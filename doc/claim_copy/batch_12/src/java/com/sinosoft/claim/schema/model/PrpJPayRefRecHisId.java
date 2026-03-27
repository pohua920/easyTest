package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpJPayRefRecHisId
 */
@Embeddable
public class PrpJPayRefRecHisId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性 业务类型 P 保单 E 批单 S手续费 C赔款 Y 预赔 Z 追偿款 */
	private String certiType;

	/** 属性业务号码 保单：保单号 批单：批单号 手续费：保批单号 赔款：计算书 预赔：计算书 */
	private String certiNo;

	/** 属性交费计划序号(PrpCplan.SerialNo) */
	private Integer serialNo;

	/** 属性收付原因 */
	private String payRefReason;

	/** 属性收付次数 */
	private Integer payRefTimes;

	/**
	 * 类PrpJPayRefRecHisId的默认构造方法
	 */
	public PrpJPayRefRecHisId() {
	}

	/**
	 * 属性CERTITYPE的getter方法
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性CERTITYPE的setter方法
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性CERTINO的getter方法
	 */

	@Column(name = "CERTINO")
	public String getCertiNo() {
		return this.certiNo;
	}

	/**
	 * 属性CERTINO的setter方法
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 属性SERIALNO的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性SERIALNO的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性PAYREFREASON的getter方法
	 */

	@Column(name = "PAYREFREASON")
	public String getPayRefReason() {
		return this.payRefReason;
	}

	/**
	 * 属性PAYREFREASON的setter方法
	 */
	public void setPayRefReason(String payRefReason) {
		this.payRefReason = payRefReason;
	}

	/**
	 * 属性PAYREFTIMES的getter方法
	 */

	@Column(name = "PAYREFTIMES")
	public Integer getPayRefTimes() {
		return this.payRefTimes;
	}

	/**
	 * 属性PAYREFTIMES的setter方法
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
		if (!(other instanceof PrpJPayRefRecHisId)) {
			return false;
		}
		PrpJPayRefRecHisId castOther = (PrpJPayRefRecHisId) other;

		return ((this.getCertiType() == castOther.getCertiType()) || (this.getCertiType() != null && castOther.getCertiType() != null && this.getCertiType().equals(castOther.getCertiType())))
				&& ((this.getCertiNo() == castOther.getCertiNo()) || (this.getCertiNo() != null && castOther.getCertiNo() != null && this.getCertiNo().equals(castOther.getCertiNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())))
				&& ((this.getPayRefReason() == castOther.getPayRefReason()) || (this.getPayRefReason() != null && castOther.getPayRefReason() != null && this.getPayRefReason().equals(castOther.getPayRefReason())))
				&& ((this.getPayRefTimes() == castOther.getPayRefTimes()) || (this.getPayRefTimes() != null && castOther.getPayRefTimes() != null && this.getPayRefTimes().equals(castOther.getPayRefTimes())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCertiType() == null ? 0 : this.getCertiType().hashCode());
		result = 37 * result + (getCertiNo() == null ? 0 : this.getCertiNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		result = 37 * result + (getPayRefReason() == null ? 0 : this.getPayRefReason().hashCode());
		result = 37 * result + (getPayRefTimes() == null ? 0 : this.getPayRefTimes().hashCode());
		return result;
	}

	public PrpJPayRefRecHisId(String certiType, String certiNo, Integer serialNo, String payRefReason, Integer payRefTimes) {
		this.certiType = certiType;
		this.certiNo = certiNo;
		this.serialNo = serialNo;
		this.payRefReason = payRefReason;
		this.payRefTimes = payRefTimes;
	}

}
