package com.sinosoft.undwrt.undwrtBase.model;
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfNodeId.
 */
@Embeddable
public class SwfNodeId implements java.io.Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 属性模板號. */
	private int modelNo;

	/** 属性節點號. */
	private int nodeNo;

	/**
	 * 类SwfNodeId的默认构造方法.
	 */
	public SwfNodeId() {
	}

	/**
	 * 属性属性模版号的getter方法.
	 * 
	 * @return the 属性属性模版号
	 */

	@Column(name = "MODELNO")
	public int getModelNo() {
		return this.modelNo;
	}

	/**
	 * 属性属性模版号的setter方法.
	 * 
	 * @param modelNo
	 *            the new 属性属性模版号
	 */
	public void setModelNo(int modelNo) {
		this.modelNo = modelNo;
	}

	/**
	 * 属性当前节点号的getter方法.
	 * 
	 * @return the 属性当前节点号
	 */

	@Column(name = "NODENO")
	public int getNodeNo() {
		return this.nodeNo;
	}

	/**
	 * 属性当前节点号的setter方法.
	 * 
	 * @param nodeNo
	 *            the new 属性当前节点号
	 */
	public void setNodeNo(int nodeNo) {
		this.nodeNo = nodeNo;
	}

	/**
	 * Equals.
	 * 
	 * @param other
	 *            the other
	 * @return true, if successful
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

		return ((this.getModelNo() == castOther.getModelNo()) || (this
				.getModelNo() != 0 && castOther.getModelNo() != 0 && this
				.getModelNo() == castOther.getModelNo()))
				&& ((this.getNodeNo() == castOther.getNodeNo()) || (this
						.getNodeNo() != 0 && castOther.getNodeNo() != 0 && this
						.getNodeNo() == castOther.getNodeNo()));
	}

	/**
	 * Hash code.
	 * 
	 * @return the int
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getModelNo() == 0 ? 0 : String.valueOf(this.getModelNo()).hashCode());
		result = 37 * result
				+ (getNodeNo() == 0 ? 0 : String.valueOf(this.getNodeNo()).hashCode());
		return result;
	}

}
