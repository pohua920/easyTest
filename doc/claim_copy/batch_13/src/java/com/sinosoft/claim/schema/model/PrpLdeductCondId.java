package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLdeductCondId
 */
@Embeddable
public class PrpLdeductCondId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性赔款计算书号 */
	private String compensateNo;

	/** 属性免赔条件代码 */
	private String deductCondCode;

	/**
	 * 类PrpLdeductCondId的默认构造方法
	 */
	public PrpLdeductCondId() {
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
	 * 属性免赔条件代码的getter方法
	 */

	@Column(name = "DEDUCTCONDCODE")
	public String getDeductCondCode() {
		return this.deductCondCode;
	}

	/**
	 * 属性免赔条件代码的setter方法
	 */
	public void setDeductCondCode(String deductCondCode) {
		this.deductCondCode = deductCondCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLdeductCondId)) {
			return false;
		}
		PrpLdeductCondId castOther = (PrpLdeductCondId) other;

		return ((this.getCompensateNo() == castOther.getCompensateNo()) || (this.getCompensateNo() != null && castOther.getCompensateNo() != null && this.getCompensateNo().equals(castOther.getCompensateNo())))
				&& ((this.getDeductCondCode() == castOther.getDeductCondCode()) || (this.getDeductCondCode() != null && castOther.getDeductCondCode() != null && this.getDeductCondCode().equals(castOther.getDeductCondCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCompensateNo() == null ? 0 : this.getCompensateNo().hashCode());
		result = 37 * result + (getDeductCondCode() == null ? 0 : this.getDeductCondCode().hashCode());
		return result;
	}

}
