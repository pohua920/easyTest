package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfPathLogStoreId
 */
@Embeddable
public class SwfPathLogStoreId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程编号 */
	private String flowId;

	/** 属性流程边编码 */
	private Integer pathNo = 0;

	/**
	 * 类SwfPathLogStoreId的默认构造方法
	 */
	public SwfPathLogStoreId() {
	}

	/**
	 * 属性流程编号的getter方法
	 */

	@Column(name = "FLOWID")
	public String getFlowId() {
		return this.flowId;
	}

	/**
	 * 属性流程编号的setter方法
	 */
	public void setFlowId(String flowId) {
		this.flowId = flowId;
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
		if (!(other instanceof SwfPathLogStoreId)) {
			return false;
		}
		SwfPathLogStoreId castOther = (SwfPathLogStoreId) other;

		return ((this.getFlowId() == castOther.getFlowId()) || (this.getFlowId() != null && castOther.getFlowId() != null && this.getFlowId().equals(castOther.getFlowId())))
				&& ((this.getPathNo() == castOther.getPathNo()) || (this.getPathNo() != null && castOther.getPathNo() != null && this.getPathNo().equals(castOther.getPathNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getFlowId() == null ? 0 : this.getFlowId().hashCode());
		result = 37 * result + (getPathNo() == null ? 0 : this.getPathNo().hashCode());
		return result;
	}

	public SwfPathLogStoreId(String flowId, Integer pathNo) {
		this.flowId = flowId;
		this.pathNo = pathNo;
	}

}
