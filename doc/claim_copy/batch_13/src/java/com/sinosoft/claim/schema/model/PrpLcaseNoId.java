package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLcaseNoId
 */
@Embeddable
public class PrpLcaseNoId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性单证号 */
	private String certiNo;

	/** 属性单证类型 */
	private String certiType;

	/** 属性赔案号 */
	private String caseNo;

	/**
	 * 类PrpLcaseNoId的默认构造方法
	 */
	public PrpLcaseNoId() {
	}

	/**
	 * 属性单证号的getter方法
	 */

	@Column(name = "CERTINO")
	public String getCertiNo() {
		return this.certiNo;
	}

	/**
	 * 属性单证号的setter方法
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 属性单证类型的getter方法
	 */

	@Column(name = "CERTITYPE")
	public String getCertiType() {
		return this.certiType;
	}

	/**
	 * 属性单证类型的setter方法
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CASENO")
	public String getCaseNo() {
		return this.caseNo;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLcaseNoId)) {
			return false;
		}
		PrpLcaseNoId castOther = (PrpLcaseNoId) other;

		return ((this.getCertiNo() == castOther.getCertiNo()) || (this.getCertiNo() != null && castOther.getCertiNo() != null && this.getCertiNo().equals(castOther.getCertiNo())))
				&& ((this.getCertiType() == castOther.getCertiType()) || (this.getCertiType() != null && castOther.getCertiType() != null && this.getCertiType().equals(castOther.getCertiType())))
				&& ((this.getCaseNo() == castOther.getCaseNo()) || (this.getCaseNo() != null && castOther.getCaseNo() != null && this.getCaseNo().equals(castOther.getCaseNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCertiNo() == null ? 0 : this.getCertiNo().hashCode());
		result = 37 * result + (getCertiType() == null ? 0 : this.getCertiType().hashCode());
		result = 37 * result + (getCaseNo() == null ? 0 : this.getCaseNo().hashCode());
		return result;
	}

}
