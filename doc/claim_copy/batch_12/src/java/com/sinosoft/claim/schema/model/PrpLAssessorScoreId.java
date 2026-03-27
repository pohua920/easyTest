package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLAssessorScoreId
 */
@Embeddable
public class PrpLAssessorScoreId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性公估师代码 */
	private String comCode;

	/** 属性公估机构代码 */
	private String comCode1;

	/** 属性赔案号 */
	private String claimNo;

	/**
	 * 类PrpLAssessorScoreId的默认构造方法
	 */
	public PrpLAssessorScoreId() {
	}

	/**
	 * 属性公估师代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性公估师代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性公估机构代码的getter方法
	 */

	@Column(name = "COMCODE1")
	public String getComCode1() {
		return this.comCode1;
	}

	/**
	 * 属性公估机构代码的setter方法
	 */
	public void setComCode1(String comCode1) {
		this.comCode1 = comCode1;
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

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLAssessorScoreId)) {
			return false;
		}
		PrpLAssessorScoreId castOther = (PrpLAssessorScoreId) other;

		return ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(castOther.getComCode())))
				&& ((this.getComCode1() == castOther.getComCode1()) || (this.getComCode1() != null && castOther.getComCode1() != null && this.getComCode1().equals(castOther.getComCode1())))
				&& ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (getComCode1() == null ? 0 : this.getComCode1().hashCode());
		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		return result;
	}

}
