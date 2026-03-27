package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfLogStoreId
 */
@Embeddable
public class SwfLogStoreId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性流程编号 */
	private String flowID;

	/** 属性序号 */
	private Integer logNo = 0;

	/**
	 * 类SwfLogStoreId的默认构造方法
	 */
	public SwfLogStoreId() {
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
	 * 属性序号的getter方法
	 */

	@Column(name = "LOGNO")
	public Integer getLogNo() {
		return this.logNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setLogNo(Integer logNo) {
		this.logNo = logNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof SwfLogStoreId)) {
			return false;
		}
		SwfLogStoreId castOther = (SwfLogStoreId) other;

		return ((this.getFlowID() == castOther.getFlowID()) || (this.getFlowID() != null && castOther.getFlowID() != null && this.getFlowID().equals(castOther.getFlowID())))
				&& ((this.getLogNo() == castOther.getLogNo()) || (this.getLogNo() != null && castOther.getLogNo() != null && this.getLogNo().equals(castOther.getLogNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getFlowID() == null ? 0 : this.getFlowID().hashCode());
		result = 37 * result + (getLogNo() == null ? 0 : this.getLogNo().hashCode());
		return result;
	}

	public SwfLogStoreId(String flowID, Integer logNo) {
		this.flowID = flowID;
		this.logNo = logNo;
	}

}
