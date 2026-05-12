package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCitemKindId
 */
@Embeddable
public class PrpCitemKindId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性序号 */
	private Integer itemKindNo;

	public PrpCitemKindId(String policyNo, Integer itemKindNo) {
		this.policyNo = policyNo;
		this.itemKindNo = itemKindNo;
	}

	/**
	 * 类PrpCitemKindId的默认构造方法
	 */
	public PrpCitemKindId() {
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
	public Integer getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setItemKindNo(Integer itemKindNo) {
		this.itemKindNo = itemKindNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpCitemKindId)) {
			return false;
		}
		PrpCitemKindId castOther = (PrpCitemKindId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getItemKindNo() == castOther.getItemKindNo()) || (this.getItemKindNo() != null && castOther.getItemKindNo() != null && this.getItemKindNo().equals(castOther.getItemKindNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getItemKindNo() == null ? 0 : this.getItemKindNo().hashCode());
		return result;
	}

}
