package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLDocArchiveLogId
 */
@Embeddable
public class PrpLDocArchiveLogId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性序号 */
	private Integer SerialNo;

	/**
	 * 类PrpLDocArchiveLogId的默认构造方法
	 */
	public PrpLDocArchiveLogId() {
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.SerialNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.SerialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLDocArchiveLogId)) {
			return false;
		}
		PrpLDocArchiveLogId castOther = (PrpLDocArchiveLogId) other;

		return ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
