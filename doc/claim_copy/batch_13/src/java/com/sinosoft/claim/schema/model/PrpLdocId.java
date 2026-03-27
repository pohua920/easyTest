package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLdocId
 */
@Embeddable
public class PrpLdocId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号码 */
	private String claimNo;

	/** 属性单证代码 */
	private String docCode;

	/**
	 * 类PrpLdocId的默认构造方法
	 */
	public PrpLdocId() {
	}

	/**
	 * 属性立案号码的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号码的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性单证代码的getter方法
	 */

	@Column(name = "DOCCODE")
	public String getDocCode() {
		return this.docCode;
	}

	/**
	 * 属性单证代码的setter方法
	 */
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLdocId)) {
			return false;
		}
		PrpLdocId castOther = (PrpLdocId) other;

		return ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getDocCode() == castOther.getDocCode()) || (this.getDocCode() != null && castOther.getDocCode() != null && this.getDocCode().equals(castOther.getDocCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getDocCode() == null ? 0 : this.getDocCode().hashCode());
		return result;
	}

}
