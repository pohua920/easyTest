package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpPprofitId
 */
@Embeddable
public class PrpPprofitId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性批单号码 */
	private String endorseNo;

	/** 属性优惠折扣类型 */
	private String profitType;

	/** 属性序号 */
	private Integer itemKindNo;

	/**
	 * 类PrpPprofitId的默认构造方法
	 */
	public PrpPprofitId() {
	}

	/**
	 * 属性批单号码的getter方法
	 */

	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性批单号码的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
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
		if (!(other instanceof PrpPprofitId)) {
			return false;
		}
		PrpPprofitId castOther = (PrpPprofitId) other;

		return ((this.getEndorseNo() == castOther.getEndorseNo()) || (this.getEndorseNo() != null && castOther.getEndorseNo() != null && this.getEndorseNo().equals(castOther.getEndorseNo())))
				&& ((this.getProfitType() == castOther.getProfitType()) || (this.getProfitType() != null && castOther.getProfitType() != null && this.getProfitType().equals(castOther.getProfitType())))
				&& ((this.getItemKindNo() == castOther.getItemKindNo()) || (this.getItemKindNo() != null && castOther.getItemKindNo() != null && this.getItemKindNo().equals(castOther.getItemKindNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEndorseNo() == null ? 0 : this.getEndorseNo().hashCode());
		result = 37 * result + (getProfitType() == null ? 0 : this.getProfitType().hashCode());
		result = 37 * result + (getItemKindNo() == null ? 0 : this.getItemKindNo().hashCode());
		return result;
	}

}
