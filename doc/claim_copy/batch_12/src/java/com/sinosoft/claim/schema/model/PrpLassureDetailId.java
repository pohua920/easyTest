package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLassureDetailId
 */
@Embeddable
public class PrpLassureDetailId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性担保号 */
	private String assureNo;

	/** 属性序号 */
	private BigDecimal serialNo;

	/**
	 * 类PrpLassureDetailId的默认构造方法
	 */
	public PrpLassureDetailId() {
	}

	/**
	 * 属性担保号的getter方法
	 */

	@Column(name = "ASSURENO")
	public String getAssureNo() {
		return this.assureNo;
	}

	/**
	 * 属性担保号的setter方法
	 */
	public void setAssureNo(String assureNo) {
		this.assureNo = assureNo;
	}

	/**
	 * 属性序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public BigDecimal getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setSerialNo(BigDecimal serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLassureDetailId)) {
			return false;
		}
		PrpLassureDetailId castOther = (PrpLassureDetailId) other;

		return ((this.getAssureNo() == castOther.getAssureNo()) || (this.getAssureNo() != null && castOther.getAssureNo() != null && this.getAssureNo().equals(castOther.getAssureNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getAssureNo() == null ? 0 : this.getAssureNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
