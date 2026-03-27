package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLPlanKindId
 */
@Embeddable
public class PrpLplanKindId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性业务类型 */
	private String certiType;

	/** 属性保单号码/批单号码 */
	private String certiNo;

	/** 属性交费计划序号 */
	private Integer serialNo;

	/** 属性收付原因 */
	private String payRefReason;

	/** 属性标的编号 */
	private Integer itemKindNo;

	/**
	 * 类PrpLPlanKindId的默认构造方法
	 */
	public PrpLplanKindId() {
	}

	/**
	 * 属性业务类型的getter方法
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性业务类型的setter方法
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性保单号码/批单号码的getter方法
	 */

	@Column(name = "CERTINO")
	public String getCertiNo() {
		return this.certiNo;
	}

	/**
	 * 属性保单号码/批单号码的setter方法
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
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

	/**
	 * 属性收付原因的getter方法
	 */

	@Column(name = "PAYREFREASON")
	public String getPayRefReason() {
		return this.payRefReason;
	}

	/**
	 * 属性收付原因的setter方法
	 */
	public void setPayRefReason(String payRefReason) {
		this.payRefReason = payRefReason;
	}

	/**
	 * 属性标的编号的getter方法
	 */

	@Column(name = "ITEMKINDNO")
	public Integer getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性标的编号的setter方法
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
		if (!(other instanceof PrpLplanKindId)) {
			return false;
		}
		PrpLplanKindId castOther = (PrpLplanKindId) other;

		return ((this.getCertiType() == castOther.getCertiType()) || (this.getCertiType() != null && castOther.getCertiType() != null && this.getCertiType().equals(castOther.getCertiType())))
				&& ((this.getCertiNo() == castOther.getCertiNo()) || (this.getCertiNo() != null && castOther.getCertiNo() != null && this.getCertiNo().equals(castOther.getCertiNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())))
				&& ((this.getPayRefReason() == castOther.getPayRefReason()) || (this.getPayRefReason() != null && castOther.getPayRefReason() != null && this.getPayRefReason().equals(castOther.getPayRefReason())))
				&& ((this.getItemKindNo() == castOther.getItemKindNo()) || (this.getItemKindNo() != null && castOther.getItemKindNo() != null && this.getItemKindNo().equals(castOther.getItemKindNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCertiType() == null ? 0 : this.getCertiType().hashCode());
		result = 37 * result + (getCertiNo() == null ? 0 : this.getCertiNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		result = 37 * result + (getPayRefReason() == null ? 0 : this.getPayRefReason().hashCode());
		result = 37 * result + (getItemKindNo() == null ? 0 : this.getItemKindNo().hashCode());
		return result;
	}

}
