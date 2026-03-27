package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLctextId
 */
@Embeddable
public class PrpLctextId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性赔款计算书号 */
	private String compensateNo;

	/** 属性文字说明类型 */
	private String textType;

	/** 属性行序号 */
	private Integer lineNo;

	/**
	 * 类PrpLctextId的默认构造方法
	 */
	public PrpLctextId() {
	}

	/**
	 * 属性赔款计算书号的getter方法
	 */

	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return this.compensateNo;
	}

	/**
	 * 属性赔款计算书号的setter方法
	 */
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
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
	 * 属性行序号的getter方法
	 */

	@Column(name = "LINENO")
	public Integer getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行序号的setter方法
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
		if (!(other instanceof PrpLctextId)) {
			return false;
		}
		PrpLctextId castOther = (PrpLctextId) other;

		return ((this.getCompensateNo() == castOther.getCompensateNo()) || (this.getCompensateNo() != null && castOther.getCompensateNo() != null && this.getCompensateNo().equals(castOther.getCompensateNo())))
				&& ((this.getTextType() == castOther.getTextType()) || (this.getTextType() != null && castOther.getTextType() != null && this.getTextType().equals(castOther.getTextType())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCompensateNo() == null ? 0 : this.getCompensateNo().hashCode());
		result = 37 * result + (getTextType() == null ? 0 : this.getTextType().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
