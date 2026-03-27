package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLscheduleMainId
 */
@Embeddable
public class PrpLscheduleMainId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性调度ID */
	private BigDecimal scheduleID;

	/** 属性REGISTNO */
	private String registno;

	/** 属性交费计划序号 */
	private BigDecimal serialNo;

	/**
	 * 类PrpLscheduleMainId的默认构造方法
	 */
	public PrpLscheduleMainId() {
	}

	/**
	 * 属性调度ID的getter方法
	 */

	@Column(name = "SCHEDULEID")
	public BigDecimal getScheduleID() {
		return this.scheduleID;
	}

	/**
	 * 属性调度ID的setter方法
	 */
	public void setScheduleID(BigDecimal scheduleID) {
		this.scheduleID = scheduleID;
	}

	/**
	 * 属性REGISTNO的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistno() {
		return this.registno;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistno(String registno) {
		this.registno = registno;
	}

	/**
	 * 属性交费计划序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public BigDecimal getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性交费计划序号的setter方法
	 */
	public void setSerialNo(BigDecimal serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLscheduleMainId)) {
			return false;
		}
		PrpLscheduleMainId castOther = (PrpLscheduleMainId) other;

		return ((this.getScheduleID() == castOther.getScheduleID()) || (this.getScheduleID() != null && castOther.getScheduleID() != null && this.getScheduleID().equals(castOther.getScheduleID())))
				&& ((this.getRegistno() == castOther.getRegistno()) || (this.getRegistno() != null && castOther.getRegistno() != null && this.getRegistno().equals(castOther.getRegistno())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getScheduleID() == null ? 0 : this.getScheduleID().hashCode());
		result = 37 * result + (getRegistno() == null ? 0 : this.getRegistno().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
