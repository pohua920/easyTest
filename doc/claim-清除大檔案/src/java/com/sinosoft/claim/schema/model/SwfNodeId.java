package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfNodeId
 */
@Embeddable
public class SwfNodeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性模板编码 */
	private Integer modelNo = 0;

	/** 属性当前节点号 */
	private Integer nodeNo = 0;

	/**
	 * 类SwfNodeId的默认构造方法
	 */
	public SwfNodeId() {
	}

	public SwfNodeId(Integer modelNo, Integer nodeNo) {
		super();
		this.modelNo = modelNo;
		this.nodeNo = nodeNo;
	}

	/**
	 * 属性模板编码的getter方法
	 */

	@Column(name = "MODELNO")
	public Integer getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性模板编码的setter方法
	 */
	public void setModelNo(Integer modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 属性当前节点号的getter方法
	 */

	@Column(name = "NODENO")
	public Integer getNodeNo() {
		return this.nodeNo;
	}

	/**
	 * 属性当前节点号的setter方法
	 */
	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof SwfNodeId)) {
			return false;
		}
		SwfNodeId castOther = (SwfNodeId) other;

		return ((this.getModelNo() == castOther.getModelNo()) || (this.getModelNo() != null && castOther.getModelNo() != null && this.getModelNo().equals(castOther.getModelNo())))
				&& ((this.getNodeNo() == castOther.getNodeNo()) || (this.getNodeNo() != null && castOther.getNodeNo() != null && this.getNodeNo().equals(castOther.getNodeNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getModelNo() == null ? 0 : this.getModelNo().hashCode());
		result = 37 * result + (getNodeNo() == null ? 0 : this.getNodeNo().hashCode());
		return result;
	}

}
