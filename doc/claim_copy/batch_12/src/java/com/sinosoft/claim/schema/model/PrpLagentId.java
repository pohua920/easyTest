package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLagentId
 */
@Embeddable
public class PrpLagentId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性无责方报案号 */
	private String nullReportNo;

	/** 属性全责方报案号 */
	private String fullReportNo;

	/** 属性案件类型 */
	private String claimType;

	/**
	 * 类PrpLagentId的默认构造方法
	 */
	public PrpLagentId() {
	}

	/**
	 * 属性无责方报案号的getter方法
	 */

	@Column(name = "NULLREPORTNO")
	public String getNullReportNo() {
		return this.nullReportNo;
	}

	/**
	 * 属性无责方报案号的setter方法
	 */
	public void setNullReportNo(String nullReportNo) {
		this.nullReportNo = nullReportNo;
	}

	/**
	 * 属性全责方报案号的getter方法
	 */

	@Column(name = "FULLREPORTNO")
	public String getFullReportNo() {
		return this.fullReportNo;
	}

	/**
	 * 属性全责方报案号的setter方法
	 */
	public void setFullReportNo(String fullReportNo) {
		this.fullReportNo = fullReportNo;
	}

	/**
	 * 属性案件类型的getter方法
	 */

	@Column(name = "CLAIMTYPE")
	public String getClaimType() {
		return this.claimType;
	}

	/**
	 * 属性案件类型的setter方法
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLagentId)) {
			return false;
		}
		PrpLagentId castOther = (PrpLagentId) other;

		return ((this.getNullReportNo() == castOther.getNullReportNo()) || (this.getNullReportNo() != null && castOther.getNullReportNo() != null && this.getNullReportNo().equals(castOther.getNullReportNo())))
				&& ((this.getFullReportNo() == castOther.getFullReportNo()) || (this.getFullReportNo() != null && castOther.getFullReportNo() != null && this.getFullReportNo().equals(castOther.getFullReportNo())))
				&& ((this.getClaimType() == castOther.getClaimType()) || (this.getClaimType() != null && castOther.getClaimType() != null && this.getClaimType().equals(castOther.getClaimType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getNullReportNo() == null ? 0 : this.getNullReportNo().hashCode());
		result = 37 * result + (getFullReportNo() == null ? 0 : this.getFullReportNo().hashCode());
		result = 37 * result + (getClaimType() == null ? 0 : this.getClaimType().hashCode());
		return result;
	}

}
