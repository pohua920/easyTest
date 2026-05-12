package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpLcarInsuranceId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 赔款计算书号 */
	private String compensateNo;

	/** 序号 */
	private Integer serialNo;

	/**
	 * 类PrpLcarInsuranceId的默认构造方法
	 */
	public PrpLcarInsuranceId() {
		
	}

	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return serialNo;
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
		if (!(other instanceof PrpLcarInsuranceId)) {
			return false;
		}
		PrpLcarInsuranceId castOther = (PrpLcarInsuranceId) other;

		return ((this.getCompensateNo() == castOther.getCompensateNo()) || (this.getCompensateNo() != null && castOther.getCompensateNo() != null && this.getCompensateNo().equals(castOther.getCompensateNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (getCompensateNo() == null ? 0 : this.getCompensateNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}
}
