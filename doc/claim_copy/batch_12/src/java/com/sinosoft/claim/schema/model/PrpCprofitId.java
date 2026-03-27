package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCprofitId
 */
@Embeddable
public class PrpCprofitId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性优惠折扣类型 */
	private String profitType;

	/** 属性标的险别序号 */
	private Integer itemKindNo;

	
	public PrpCprofitId(String policyNo, String profitType, Integer itemKindNo) {
		this.policyNo = policyNo;
		this.profitType = profitType;
		this.itemKindNo = itemKindNo;
	}

	/**
	 * 类PrpCprofitId的默认构造方法
	 */
	public PrpCprofitId() {
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
	 * 属性优惠折扣类型的getter方法
	 */

	@Column(name = "PROFITTYPE")
	public String getProfitType() {
		return this.profitType;
	}

	/**
	 * 属性优惠折扣类型的setter方法
	 */
	public void setProfitType(String profitType) {
		this.profitType = profitType;
	}

	/**
	 * 属性标的险别序号的getter方法
	 */

	@Column(name = "ITEMKINDNO")
	public Integer getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性标的险别序号的setter方法
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
		if (!(other instanceof PrpCprofitId)) {
			return false;
		}
		PrpCprofitId castOther = (PrpCprofitId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getProfitType() == castOther.getProfitType()) || (this.getProfitType() != null && castOther.getProfitType() != null && this.getProfitType().equals(castOther.getProfitType())))
				&& ((this.getItemKindNo() == castOther.getItemKindNo()) || (this.getItemKindNo() != null && castOther.getItemKindNo() != null && this.getItemKindNo().equals(castOther.getItemKindNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getProfitType() == null ? 0 : this.getProfitType().hashCode());
		result = 37 * result + (getItemKindNo() == null ? 0 : this.getItemKindNo().hashCode());
		return result;
	}

}
