package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLqualityCheckId
 */
@Embeddable
public class PrpLqualityCheckId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号码 */
	private String registNo;

	/** 属性业务类型 */
	private String qualityCheckType;

	/** 属性序号 */
	private Integer serialNo;

	/**
	 * 类PrpLqualityCheckId的默认构造方法
	 */
	public PrpLqualityCheckId() {
	}

	/**
	 * 属性报案号码的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号码的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性业务类型的getter方法
	 */

	@Column(name = "QUALITYCHECKTYPE")
	public String getQualityCheckType() {
		return this.qualityCheckType;
	}

	/**
	 * 属性业务类型的setter方法
	 */
	public void setQualityCheckType(String qualityCheckType) {
		this.qualityCheckType = qualityCheckType;
	}

	/**
	 * 属性序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLqualityCheckId)) {
			return false;
		}
		PrpLqualityCheckId castOther = (PrpLqualityCheckId) other;

		return ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getQualityCheckType() == castOther.getQualityCheckType()) || (this.getQualityCheckType() != null && castOther.getQualityCheckType() != null && this.getQualityCheckType().equals(castOther.getQualityCheckType())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getQualityCheckType() == null ? 0 : this.getQualityCheckType().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
