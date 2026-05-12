package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLendorId
 */
@Embeddable
public class PrpLendorId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性赔款计算书号 */
	private String compensateNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性序号 */
	private BigDecimal itemKindNo;

	/**
	 * 类PrpLendorId的默认构造方法
	 */
	public PrpLendorId() {
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
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性序号的getter方法
	 */

	@Column(name = "ITEMKINDNO")
	public BigDecimal getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setItemKindNo(BigDecimal itemKindNo) {
		this.itemKindNo = itemKindNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLendorId)) {
			return false;
		}
		PrpLendorId castOther = (PrpLendorId) other;

		return ((this.getCompensateNo() == castOther.getCompensateNo()) || (this.getCompensateNo() != null && castOther.getCompensateNo() != null && this.getCompensateNo().equals(castOther.getCompensateNo())))
				&& ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getItemKindNo() == castOther.getItemKindNo()) || (this.getItemKindNo() != null && castOther.getItemKindNo() != null && this.getItemKindNo().equals(castOther.getItemKindNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCompensateNo() == null ? 0 : this.getCompensateNo().hashCode());
		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getItemKindNo() == null ? 0 : this.getItemKindNo().hashCode());
		return result;
	}

}
