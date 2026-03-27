package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLcfeecoinsId
 */
@Embeddable
public class PrpLcfeecoinsId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性业务号 */
	private String businessNo;

	/** 属性交费计划序号 */
	private Integer serialNo;

	/**
	 * 类PrpLcfeecoinsId的默认构造方法
	 */
	public PrpLcfeecoinsId() {
	}

	/**
	 * 属性业务号的getter方法
	 */

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
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
		if (!(other instanceof PrpLcfeecoinsId)) {
			return false;
		}
		PrpLcfeecoinsId castOther = (PrpLcfeecoinsId) other;

		return ((this.getBusinessNo() == castOther.getBusinessNo()) || (this.getBusinessNo() != null && castOther.getBusinessNo() != null && this.getBusinessNo().equals(castOther.getBusinessNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBusinessNo() == null ? 0 : this.getBusinessNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
