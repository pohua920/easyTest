package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLcertifyImgId
 */
@Embeddable
public class PrpLcertifyImgId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性业务号码 */
	private String businessNo;

	/** 属性序号 */
	private BigDecimal serialNo;

	/** 属性标的代码 */
	private String lossItemCode;

	/**
	 * 类PrpLcertifyImgId的默认构造方法
	 */
	public PrpLcertifyImgId() {
	}

	/**
	 * 属性业务号码的getter方法
	 */

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号码的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
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

	/**
	 * 属性标的代码的getter方法
	 */

	@Column(name = "LOSSITEMCODE")
	public String getLossItemCode() {
		return this.lossItemCode;
	}

	/**
	 * 属性标的代码的setter方法
	 */
	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = lossItemCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLcertifyImgId)) {
			return false;
		}
		PrpLcertifyImgId castOther = (PrpLcertifyImgId) other;

		return ((this.getBusinessNo() == castOther.getBusinessNo()) || (this.getBusinessNo() != null && castOther.getBusinessNo() != null && this.getBusinessNo().equals(castOther.getBusinessNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())))
				&& ((this.getLossItemCode() == castOther.getLossItemCode()) || (this.getLossItemCode() != null && castOther.getLossItemCode() != null && this.getLossItemCode().equals(castOther.getLossItemCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBusinessNo() == null ? 0 : this.getBusinessNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		result = 37 * result + (getLossItemCode() == null ? 0 : this.getLossItemCode().hashCode());
		return result;
	}

}
