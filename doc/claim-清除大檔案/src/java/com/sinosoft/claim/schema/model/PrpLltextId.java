package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLltextId
 */
@Embeddable
public class PrpLltextId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号码 */
	private String claimNo;

	/** 属性文字说明类型 */
	private String textType;

	/** 属性行号 */
	private Integer lineNo;

	/**
	 * 类PrpLltextId的默认构造方法
	 */
	public PrpLltextId() {
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
	 * 属性文字说明类型的getter方法
	 */

	@Column(name = "TEXTTYPE")
	public String getTextType() {
		return this.textType;
	}

	/**
	 * 属性文字说明类型的setter方法
	 */
	public void setTextType(String textType) {
		this.textType = textType;
	}

	/**
	 * 属性行号的getter方法
	 */

	@Column(name = "LINENO")
	public Integer getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行号的setter方法
	 */
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLltextId)) {
			return false;
		}
		PrpLltextId castOther = (PrpLltextId) other;

		return ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getTextType() == castOther.getTextType()) || (this.getTextType() != null && castOther.getTextType() != null && this.getTextType().equals(castOther.getTextType())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getTextType() == null ? 0 : this.getTextType().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
