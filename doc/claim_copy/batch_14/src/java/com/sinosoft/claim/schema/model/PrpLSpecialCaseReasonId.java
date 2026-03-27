package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLSpecialCaseReasonId
 */
@Embeddable
public class PrpLSpecialCaseReasonId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号 */
	private String claimNo;

	/** 属性工作流序号 */
	private Long logNo;

	/**
	 * 类PrpLSpecialCaseReasonId的默认构造方法
	 */
	public PrpLSpecialCaseReasonId() {
	}

	public PrpLSpecialCaseReasonId(String claimNo, Long logNo) {
		super();
		this.claimNo = claimNo;
		this.logNo = logNo;
	}

	/**
	 * 属性立案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性工作流序号的getter方法
	 */

	@Column(name = "LOGNO")
	public Long getLogNo() {
		return this.logNo;
	}

	/**
	 * 属性工作流序号的setter方法
	 */
	public void setLogNo(Long logNo) {
		this.logNo = logNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLSpecialCaseReasonId)) {
			return false;
		}
		PrpLSpecialCaseReasonId castOther = (PrpLSpecialCaseReasonId) other;

		return ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getLogNo() == castOther.getLogNo()) || (this.getLogNo() != null && castOther.getLogNo() != null && this.getLogNo().equals(castOther.getLogNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getLogNo() == null ? 0 : this.getLogNo().hashCode());
		return result;
	}

}
