package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLchargeId
 */
@Embeddable
public class PrpLchargeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性赔款计算书号 */
	private String compensateNo;

	/** 属性交费计划序号 */
	private Integer serialNo;

	/**
	 * 类PrpLchargeId的默认构造方法
	 */
	public PrpLchargeId() {
	}

	/**
	 * 属性赔款计算书号的getter方法
	 */

	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return this.compensateNo;
	}

	/**
	 * 属性赔款计算书号的setter方法
	 */
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	/**
	 * 属性交费计划序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性交费计划序号的setter方法
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
		if (!(other instanceof PrpLchargeId)) {
			return false;
		}
		PrpLchargeId castOther = (PrpLchargeId) other;

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
