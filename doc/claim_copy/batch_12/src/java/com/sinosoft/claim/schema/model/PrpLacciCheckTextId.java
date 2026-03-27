package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLacciCheckTextId
 */
@Embeddable
public class PrpLacciCheckTextId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性调查号 */
	private String checkNo;

	/** 属性文字说明类型 */
	private String textType;

	/** 属性行号 */
	private Long lineNo;

	/**
	 * 类PrpLacciCheckTextId的默认构造方法
	 */
	public PrpLacciCheckTextId() {
	}

	/**
	 * 属性调查号的getter方法
	 */

	@Column(name = "CHECKNO")
	public String getCheckNo() {
		return this.checkNo;
	}

	/**
	 * 属性调查号的setter方法
	 */
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
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
	public Long getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行号的setter方法
	 */
	public void setLineNo(Long lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLacciCheckTextId)) {
			return false;
		}
		PrpLacciCheckTextId castOther = (PrpLacciCheckTextId) other;

		return ((this.getCheckNo() == castOther.getCheckNo()) || (this.getCheckNo() != null && castOther.getCheckNo() != null && this.getCheckNo().equals(castOther.getCheckNo())))
				&& ((this.getTextType() == castOther.getTextType()) || (this.getTextType() != null && castOther.getTextType() != null && this.getTextType().equals(castOther.getTextType())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCheckNo() == null ? 0 : this.getCheckNo().hashCode());
		result = 37 * result + (getTextType() == null ? 0 : this.getTextType().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
