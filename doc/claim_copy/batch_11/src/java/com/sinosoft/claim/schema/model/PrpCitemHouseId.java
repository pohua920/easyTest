package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCitemHouseId
 */
@Embeddable
public class PrpCitemHouseId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性POLICYNO */
	private String policyNo;

	/** 属性ITEMNO */
	private Integer itemNo;

	/**
	 * 类PrpCitemHouseId的默认构造方法
	 */
	public PrpCitemHouseId() {
	}

	/**
	 * 属性POLICYNO的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性POLICYNO的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性ITEMNO的getter方法
	 */

	@Column(name = "ITEMNO")
	public Integer getItemNo() {
		return this.itemNo;
	}

	/**
	 * 属性ITEMNO的setter方法
	 */
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpCitemHouseId)) {
			return false;
		}
		PrpCitemHouseId castOther = (PrpCitemHouseId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getItemNo() == castOther.getItemNo()) || (this.getItemNo() != null && castOther.getItemNo() != null && this.getItemNo().equals(castOther.getItemNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getItemNo() == null ? 0 : this.getItemNo().hashCode());
		return result;
	}

}
