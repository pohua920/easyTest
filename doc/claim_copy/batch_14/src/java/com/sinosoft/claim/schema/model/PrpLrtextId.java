package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLrtextId
 */
@Embeddable
public class PrpLrtextId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性文字说明类型 */
	private String textType;

	/** 属性序号 */
	private BigDecimal serialNo;

	/** 属性行序号 */
	private BigDecimal lineNo;

	/**
	 * 类PrpLrtextId的默认构造方法
	 */
	public PrpLrtextId() {
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
	 * 属性序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public BigDecimal getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setSerialNo(BigDecimal serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性行序号的getter方法
	 */

	@Column(name = "LINENO")
	public BigDecimal getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行序号的setter方法
	 */
	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLrtextId)) {
			return false;
		}
		PrpLrtextId castOther = (PrpLrtextId) other;

		return ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getTextType() == castOther.getTextType()) || (this.getTextType() != null && castOther.getTextType() != null && this.getTextType().equals(castOther.getTextType())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getTextType() == null ? 0 : this.getTextType().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
