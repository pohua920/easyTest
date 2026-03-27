package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLclaimStatusId
 */
@Embeddable
public class PrpLclaimStatusId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性业务号 */
	private String businessNo;

	/** 属性节点种类 */
	private String nodeType;

	/** 属性SerialNo */
	private Integer serialNo = 0;

	/**
	 * 类PrpLclaimStatusId的默认构造方法
	 */
	public PrpLclaimStatusId() {
	}

	public PrpLclaimStatusId(String businessNo, String nodeType, Integer serialNo) {
		this.businessNo = businessNo;
		this.nodeType = nodeType;
		this.serialNo = serialNo;
	}

	/**
	 * 属性业务号的getter方法
	 */

	@Column(name = "BUSINESSNO")
	public String getBusinessNo() {
		return this.businessNo;
	}

	/**
	 * 属性业务号的setter方法
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	/**
	 * 属性节点种类的getter方法
	 */

	@Column(name = "NODETYPE")
	public String getNodeType() {
		return this.nodeType;
	}

	/**
	 * 属性节点种类的setter方法
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	/**
	 * 属性SerialNo的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性SerialNo的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLclaimStatusId)) {
			return false;
		}
		PrpLclaimStatusId castOther = (PrpLclaimStatusId) other;

		return ((this.getBusinessNo() == castOther.getBusinessNo()) || (this.getBusinessNo() != null && castOther.getBusinessNo() != null && this.getBusinessNo().equals(castOther.getBusinessNo())))
				&& ((this.getNodeType() == castOther.getNodeType()) || (this.getNodeType() != null && castOther.getNodeType() != null && this.getNodeType().equals(castOther.getNodeType())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBusinessNo() == null ? 0 : this.getBusinessNo().hashCode());
		result = 37 * result + (getNodeType() == null ? 0 : this.getNodeType().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
