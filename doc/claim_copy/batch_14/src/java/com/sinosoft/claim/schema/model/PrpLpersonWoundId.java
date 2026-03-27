package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLpersonWoundId
 */
@Embeddable
public class PrpLpersonWoundId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号码 */
	private String registNo;

	/** 属性序号 */
	private Integer serialNo;

	/** 属性人员序号 */
	private Integer personNo;

	/**
	 * 类PrpLpersonWoundId的默认构造方法
	 */
	public PrpLpersonWoundId() {
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

	/**
	 * 属性人员序号的getter方法
	 */

	@Column(name = "PERSONNO")
	public Integer getPersonNo() {
		return this.personNo;
	}

	/**
	 * 属性人员序号的setter方法
	 */
	public void setPersonNo(Integer personNo) {
		this.personNo = personNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLpersonWoundId)) {
			return false;
		}
		PrpLpersonWoundId castOther = (PrpLpersonWoundId) other;

		return ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())))
				&& ((this.getPersonNo() == castOther.getPersonNo()) || (this.getPersonNo() != null && castOther.getPersonNo() != null && this.getPersonNo().equals(castOther.getPersonNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		result = 37 * result + (getPersonNo() == null ? 0 : this.getPersonNo().hashCode());
		return result;
	}

}
