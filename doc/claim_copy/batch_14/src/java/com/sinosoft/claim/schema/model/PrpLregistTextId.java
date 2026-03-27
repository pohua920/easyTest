package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLregistTextId
 */
@Embeddable
public class PrpLregistTextId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号 */
	private String registNo;

	/** 属性文字说明类型 */
	private String textType;

	/** 属性行号 */
	private Integer lineNo;

	/**
	 * 类PrpLregistTextId的默认构造方法
	 */
	public PrpLregistTextId() {
	}

	/**
	 * 属性报案号的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
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
		if (!(other instanceof PrpLregistTextId)) {
			return false;
		}
		PrpLregistTextId castOther = (PrpLregistTextId) other;

		return ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getTextType() == castOther.getTextType()) || (this.getTextType() != null && castOther.getTextType() != null && this.getTextType().equals(castOther.getTextType())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getTextType() == null ? 0 : this.getTextType().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
