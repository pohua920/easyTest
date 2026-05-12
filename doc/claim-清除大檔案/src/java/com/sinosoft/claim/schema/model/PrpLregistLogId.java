package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLregistLogId
 */
@Embeddable
public class PrpLregistLogId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性轨迹ID */
	private String logID;

	/** 属性报案号码 */
	private String registNo;

	/**
	 * 类PrpLregistLogId的默认构造方法
	 */
	public PrpLregistLogId() {
	}

	/**
	 * 属性轨迹ID的getter方法
	 */

	@Column(name = "LOGID")
	public String getLogID() {
		return this.logID;
	}

	/**
	 * 属性轨迹ID的setter方法
	 */
	public void setLogID(String logID) {
		this.logID = logID;
	}

	/**
	 * 属性报案号码的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号码的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLregistLogId)) {
			return false;
		}
		PrpLregistLogId castOther = (PrpLregistLogId) other;

		return ((this.getLogID() == castOther.getLogID()) || (this.getLogID() != null && castOther.getLogID() != null && this.getLogID().equals(castOther.getLogID())))
				&& ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLogID() == null ? 0 : this.getLogID().hashCode());
		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		return result;
	}

}
