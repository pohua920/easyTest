package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLcompelMedicalId
 */
@Embeddable
public class PrpLcompelMedicalId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 計算書號碼 */
	private String compensateNo;
	
	/** 受害人身分證號碼 */
	private String identifyNumber;

	/** 收據編號 */
	private Integer serialNo;

	/**
	 * 类PrpLcompelMedicalId的默认构造方法
	 */
	public PrpLcompelMedicalId() {
	}

	public PrpLcompelMedicalId(String compensateNo, String identifyNumber, Integer serialNo) {
		this.compensateNo = compensateNo;
		this.identifyNumber = identifyNumber;
		this.serialNo = serialNo;
	}

	/**
	 * 属性赔款计算书号的getter方法
	 */

	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return this.compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return identifyNumber;
	}

	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}


	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

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
		if (!(other instanceof PrpLcompelMedicalId)) {
			return false;
		}
		PrpLcompelMedicalId castOther = (PrpLcompelMedicalId) other;

		return this.getCompensateNo() != null && castOther.getCompensateNo() != null && this.getCompensateNo().equals(castOther.getCompensateNo()) && this.getIdentifyNumber() != null && castOther.getIdentifyNumber() != null
				&& this.getIdentifyNumber().equals(castOther.getIdentifyNumber()) && this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().intValue() == castOther.getSerialNo().intValue();
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (this.getCompensateNo() == null ? 0 : this.getCompensateNo().hashCode());
		result = 37 * result + (this.getIdentifyNumber() == null ? 0 : this.getIdentifyNumber().hashCode());
		result = 37 * result + (this.getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
