package com.sinosoft.undwrt.common.model;

// Generated 2012-12-28 11:12:02 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 基礎代碼配置類id.
 */
@Embeddable
public class PrpDcodeId implements java.io.Serializable {

	/** 屬性代碼類型. */
	private String codeType;

	/** 屬性代碼. */
	private String codeCode;

	/** 屬性代碼中文名稱. */
	private String codeCName;

	/** 屬性新代碼. */
	private String newCodeCode;

	/** 屬性有效狀態. */
	private String validStatus;

	/**
	 * 構造方法.
	 */
	public PrpDcodeId() {
	}

	/**
	 * 構造方法.
	 * 
	 * @param codeType
	 *            代碼類型
	 * @param codeCode
	 *            代碼
	 * @param newCodeCode
	 *            新代碼
	 * @param validStatus
	 *            有效狀態
	 */
	public PrpDcodeId(String codeType, String codeCode, String newCodeCode, String validStatus) {
		this.codeType = codeType;
		this.codeCode = codeCode;
		this.newCodeCode = newCodeCode;
		this.validStatus = validStatus;
	}

	/**
	 * 構造方法.
	 * 
	 * @param codeType
	 *            代碼類型
	 * @param codeCode
	 *            代碼
	 * @param codeCName
	 *            代碼中文名稱
	 * @param newCodeCode
	 *            新代碼
	 * @param validStatus
	 *            有效狀態
	 */
	public PrpDcodeId(String codeType, String codeCode, String codeCName, String newCodeCode, String validStatus) {
		this.codeType = codeType;
		this.codeCode = codeCode;
		this.codeCName = codeCName;
		this.newCodeCode = newCodeCode;
		this.validStatus = validStatus;
	}

	/**
	 * 獲取屬性代碼類型.
	 * 
	 * @return 屬性代碼類型的值
	 */
	@Column(name = "CODETYPE", nullable = false, length = 20)
	public String getCodeType() {
		return this.codeType;
	}

	/**
	 * 設置屬性代碼類型.
	 * 
	 * @param codeType
	 *            待設置的代碼類型的值
	 */
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	/**
	 * 獲取屬性代碼.
	 * 
	 * @return 屬性代碼的值
	 */
	@Column(name = "CODECODE", nullable = false, length = 40)
	public String getCodeCode() {
		return this.codeCode;
	}

	/**
	 * 設置屬性代碼.
	 * 
	 * @param codeCode
	 *            待設置的代碼的值
	 */
	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}

	/**
	 * 獲取屬性代碼中文名稱.
	 * 
	 * @return 屬性代碼中文名稱的值
	 */
	@Column(name = "CODECNAME", length = 500)
	public String getCodeCName() {
		return this.codeCName;
	}

	/**
	 * 設置屬性代碼中文名稱.
	 * 
	 * @param codeCName
	 *            待設置的代碼中文名稱的值
	 */
	public void setCodeCName(String codeCName) {
		this.codeCName = codeCName;
	}

	/**
	 * 獲取屬性新代碼.
	 * 
	 * @return 屬性新代碼的值
	 */
	@Column(name = "NEWCODECODE", nullable = false, length = 40)
	public String getNewCodeCode() {
		return this.newCodeCode;
	}

	/**
	 * 設置屬性新代碼.
	 * 
	 * @param newCodeCode
	 *            待設置的新代碼的值
	 */
	public void setNewCodeCode(String newCodeCode) {
		this.newCodeCode = newCodeCode;
	}

	/**
	 * 獲取屬性有效狀態.
	 * 
	 * @return 屬性有效狀態的值
	 */
	@Column(name = "VALIDSTATUS", nullable = false, length = 1)
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 設置屬性有效狀態.
	 * 
	 * @param validStatus
	 *            待設置的有效狀態的值
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	/**
	 * Equals.
	 * 
	 * @param other
	 *            the other
	 * @return true, if successful
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDcodeId))
			return false;
		PrpDcodeId castOther = (PrpDcodeId) other;

		return ((this.getCodeType() == castOther.getCodeType()) || (this.getCodeType() != null && castOther.getCodeType() != null && this.getCodeType().equals(
				castOther.getCodeType())))
				&& ((this.getCodeCode() == castOther.getCodeCode()) || (this.getCodeCode() != null && castOther.getCodeCode() != null && this.getCodeCode()
						.equals(castOther.getCodeCode())))
				&& ((this.getCodeCName() == castOther.getCodeCName()) || (this.getCodeCName() != null && castOther.getCodeCName() != null && this
						.getCodeCName().equals(castOther.getCodeCName())))
				&& ((this.getNewCodeCode() == castOther.getNewCodeCode()) || (this.getNewCodeCode() != null && castOther.getNewCodeCode() != null && this
						.getNewCodeCode().equals(castOther.getNewCodeCode())))
				&& ((this.getValidStatus() == castOther.getValidStatus()) || (this.getValidStatus() != null && castOther.getValidStatus() != null && this
						.getValidStatus().equals(castOther.getValidStatus())));
	}

	/**
	 * Hash code.
	 * 
	 * @return the int
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCodeType() == null ? 0 : this.getCodeType().hashCode());
		result = 37 * result + (getCodeCode() == null ? 0 : this.getCodeCode().hashCode());
		result = 37 * result + (getCodeCName() == null ? 0 : this.getCodeCName().hashCode());
		result = 37 * result + (getNewCodeCode() == null ? 0 : this.getNewCodeCode().hashCode());
		result = 37 * result + (getValidStatus() == null ? 0 : this.getValidStatus().hashCode());
		return result;
	}

}
