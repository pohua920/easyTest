package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCprofitDetailId
 */
@Embeddable
public class PrpCprofitDetailId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号 */
	private String policyNo;

	/** 属性优惠折扣类型 */
	private String profitType;

	/** 属性标的子险序号 */
	private Integer itemKindNo;

	/** 属性优惠折扣代码 */
	private String profitCode;

	/** 属性序号 */
	private Integer serialNo;

	/**
	 * 类PrpCprofitDetailId的默认构造方法
	 */
	public PrpCprofitDetailId() {
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
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
	 * 属性标的子险序号的getter方法
	 */

	@Column(name = "ITEMKINDNO")
	public Integer getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性标的子险序号的setter方法
	 */
	public void setItemKindNo(Integer itemKindNo) {
		this.itemKindNo = itemKindNo;
	}

	/**
	 * 属性优惠折扣代码的getter方法
	 */

	@Column(name = "PROFITCODE")
	public String getProfitCode() {
		return this.profitCode;
	}

	/**
	 * 属性优惠折扣代码的setter方法
	 */
	public void setProfitCode(String profitCode) {
		this.profitCode = profitCode;
	}

	/**
	 * 属性序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序号的setter方法
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
		if (!(other instanceof PrpCprofitDetailId)) {
			return false;
		}
		PrpCprofitDetailId castOther = (PrpCprofitDetailId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getProfitType() == castOther.getProfitType()) || (this.getProfitType() != null && castOther.getProfitType() != null && this.getProfitType().equals(castOther.getProfitType())))
				&& ((this.getItemKindNo() == castOther.getItemKindNo()) || (this.getItemKindNo() != null && castOther.getItemKindNo() != null && this.getItemKindNo().equals(castOther.getItemKindNo())))
				&& ((this.getProfitCode() == castOther.getProfitCode()) || (this.getProfitCode() != null && castOther.getProfitCode() != null && this.getProfitCode().equals(castOther.getProfitCode())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getProfitType() == null ? 0 : this.getProfitType().hashCode());
		result = 37 * result + (getItemKindNo() == null ? 0 : this.getItemKindNo().hashCode());
		result = 37 * result + (getProfitCode() == null ? 0 : this.getProfitCode().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
