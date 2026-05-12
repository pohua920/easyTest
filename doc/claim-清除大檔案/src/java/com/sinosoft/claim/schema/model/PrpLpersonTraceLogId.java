package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLpersonTraceLogId
 */
@Embeddable
public class PrpLpersonTraceLogId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性轨迹ID */
	private String logID;

	/** 属性报案号码 */
	private String registNo;

	/** 属性人员序号 */
	private BigDecimal personNo;

	/**
	 * 类PrpLpersonTraceLogId的默认构造方法
	 */
	public PrpLpersonTraceLogId() {
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

	/**
	 * 属性人员序号的getter方法
	 */

	@Column(name = "PERSONNO")
	public BigDecimal getPersonNo() {
		return this.personNo;
	}

	/**
	 * 属性人员序号的setter方法
	 */
	public void setPersonNo(BigDecimal personNo) {
		this.personNo = personNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLpersonTraceLogId)) {
			return false;
		}
		PrpLpersonTraceLogId castOther = (PrpLpersonTraceLogId) other;

		return ((this.getLogID() == castOther.getLogID()) || (this.getLogID() != null && castOther.getLogID() != null && this.getLogID().equals(castOther.getLogID())))
				&& ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getPersonNo() == castOther.getPersonNo()) || (this.getPersonNo() != null && castOther.getPersonNo() != null && this.getPersonNo().equals(castOther.getPersonNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLogID() == null ? 0 : this.getLogID().hashCode());
		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getPersonNo() == null ? 0 : this.getPersonNo().hashCode());
		return result;
	}

}
