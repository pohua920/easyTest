package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLcheckItemId
 */
@Embeddable
public class PrpLcheckItemId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性调度ID */
	private Integer scheduleID;

	/** 属性报案号码 */
	private String registNo;

	/** 属性标的序号 */
	private Integer itemNo;

	/**
	 * 类PrpLcheckItemId的默认构造方法
	 */
	public PrpLcheckItemId() {
	}

	/**
	 * 属性调度ID的getter方法
	 */

	@Column(name = "SCHEDULEID")
	public Integer getScheduleID() {
		return this.scheduleID;
	}

	/**
	 * 属性调度ID的setter方法
	 */
	public void setScheduleID(Integer scheduleID) {
		this.scheduleID = scheduleID;
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
	 * 属性标的序号的getter方法
	 */

	@Column(name = "ITEMNO")
	public Integer getItemNo() {
		return this.itemNo;
	}

	/**
	 * 属性标的序号的setter方法
	 */
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLcheckItemId)) {
			return false;
		}
		PrpLcheckItemId castOther = (PrpLcheckItemId) other;

		return ((this.getScheduleID() == castOther.getScheduleID()) || (this.getScheduleID() != null && castOther.getScheduleID() != null && this.getScheduleID().equals(castOther.getScheduleID())))
				&& ((this.getRegistNo() == castOther.getRegistNo()) || (this.getRegistNo() != null && castOther.getRegistNo() != null && this.getRegistNo().equals(castOther.getRegistNo())))
				&& ((this.getItemNo() == castOther.getItemNo()) || (this.getItemNo() != null && castOther.getItemNo() != null && this.getItemNo().equals(castOther.getItemNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getScheduleID() == null ? 0 : this.getScheduleID().hashCode());
		result = 37 * result + (getRegistNo() == null ? 0 : this.getRegistNo().hashCode());
		result = 37 * result + (getItemNo() == null ? 0 : this.getItemNo().hashCode());
		return result;
	}

}
