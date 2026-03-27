package com.sinosoft.undwrt.undwrtBase.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类UwBlackListId
 */
@Embeddable
public class UwBlackListId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性黑名單類型 */
	private String blackListType;

	/** 属性黑名單代碼 */
	private String blackListCode;

	/**
	 * 类UwBlackListId的默认构造方法
	 */
	public UwBlackListId() {
	}

	/**
	 * 属性黑名單類型的getter方法
	 */

	@Column(name = "BLACKLISTTYPE")
	public String getBlackListType() {
		return this.blackListType;
	}

	/**
	 * 属性黑名單類型的setter方法
	 */
	public void setBlackListType(String blackListType) {
		this.blackListType = blackListType;
	}

	/**
	 * 属性黑名單代碼的getter方法
	 */

	@Column(name = "BLACKLISTCODE")
	public String getBlackListCode() {
		return this.blackListCode;
	}

	/**
	 * 属性黑名單代碼的setter方法
	 */
	public void setBlackListCode(String blackListCode) {
		this.blackListCode = blackListCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof UwBlackListId)) {
			return false;
		}
		UwBlackListId castOther = (UwBlackListId) other;

		return ((this.getBlackListType() == castOther.getBlackListType()) || (this
				.getBlackListType() != null
				&& castOther.getBlackListType() != null && this
				.getBlackListType().equals(castOther.getBlackListType())))
				&& ((this.getBlackListCode() == castOther.getBlackListCode()) || (this
						.getBlackListCode() != null
						&& castOther.getBlackListCode() != null && this
						.getBlackListCode()
						.equals(castOther.getBlackListCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getBlackListType() == null ? 0 : this.getBlackListType()
						.hashCode());
		result = 37
				* result
				+ (getBlackListCode() == null ? 0 : this.getBlackListCode()
						.hashCode());
		return result;
	}

}
