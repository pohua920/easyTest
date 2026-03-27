package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfPathLogId
 */
@Embeddable
public class SwfPathLogId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程编号 */
	private String flowID;

	/** 属性流程边编码 */
	private Integer pathNo = 0;

	/**
	 * 类SwfPathLogId的默认构造方法
	 */
	public SwfPathLogId() {
	}

	/**
	 * 属性流程编号的getter方法
	 */

	@Column(name = "FlowID")
	public String getFlowID() {
		return this.flowID;
	}

	/**
	 * 属性流程编号的setter方法
	 */
	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	/**
	 * 属性流程边编码的getter方法
	 */

	@Column(name = "PATHNO")
	public Integer getPathNo() {
		return this.pathNo;
	}

	/**
	 * 属性流程边编码的setter方法
	 */
	public void setPathNo(Integer pathNo) {
		this.pathNo = pathNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof SwfPathLogId)) {
			return false;
		}
		SwfPathLogId castOther = (SwfPathLogId) other;

		return ((this.getFlowID() == castOther.getFlowID()) || (this.getFlowID() != null && castOther.getFlowID() != null && this.getFlowID().equals(castOther.getFlowID())))
				&& ((this.getPathNo() == castOther.getPathNo()) || (this.getPathNo() != null && castOther.getPathNo() != null && this.getPathNo().equals(castOther.getPathNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getFlowID() == null ? 0 : this.getFlowID().hashCode());
		result = 37 * result + (getPathNo() == null ? 0 : this.getPathNo().hashCode());
		return result;
	}

	public SwfPathLogId(String flowID, Integer pathNo) {
		this.flowID = flowID;
		this.pathNo = pathNo;
	}

}
