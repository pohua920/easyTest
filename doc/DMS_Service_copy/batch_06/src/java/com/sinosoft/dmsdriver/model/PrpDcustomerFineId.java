package com.sinosoft.dmsdriver.model;
// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDcustomerFineId
 */
@Embeddable
public class PrpDcustomerFineId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性COMCODE */
	private String comCode;

	/** 属性RISKCODE */
	private String riskCode;

	/** 属性BATCHNO */
	private Long batchNo;

	/** 属性LICENSENO */
	private String licenseNo;

	/**
	 * 类PrpDcustomerFineId的默认构造方法
	 */
	public PrpDcustomerFineId() {
	}

	/**
	 * 属性COMCODE的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性COMCODE的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性RISKCODE的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性RISKCODE的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性BATCHNO的getter方法
	 */

	@Column(name = "BATCHNO")
	public Long getBatchNo() {
		return this.batchNo;
	}

	/**
	 * 属性BATCHNO的setter方法
	 */
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

	/**
	 * 属性LICENSENO的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性LICENSENO的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDcustomerFineId)) {
			return false;
		}
		PrpDcustomerFineId castOther = (PrpDcustomerFineId) other;

		return ((this.getComCode() == castOther.getComCode()) || (this
				.getComCode() != null && castOther.getComCode() != null && this
				.getComCode().equals(castOther.getComCode())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this
						.getRiskCode() != null
						&& castOther.getRiskCode() != null && this
						.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getBatchNo() == castOther.getBatchNo()) || (this
						.getBatchNo() != null && castOther.getBatchNo() != null && this
						.getBatchNo().equals(castOther.getBatchNo())))
				&& ((this.getLicenseNo() == castOther.getLicenseNo()) || (this
						.getLicenseNo() != null
						&& castOther.getLicenseNo() != null && this
						.getLicenseNo().equals(castOther.getLicenseNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result
				+ (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result
				+ (getBatchNo() == null ? 0 : this.getBatchNo().hashCode());
		result = 37 * result
				+ (getLicenseNo() == null ? 0 : this.getLicenseNo().hashCode());
		return result;
	}

}
