package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpGroupId
 */
@Embeddable
public class PrpGroupId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性合编组 */
	private String groupNo;

	/** 属性分编组 */
	private String subGroupNo;

	/**
	 * 类PrpGroupId的默认构造方法
	 */
	public PrpGroupId() {
	}

	/**
	 * 属性合编组的getter方法
	 */

	@Column(name = "GROUPNO")
	public String getGroupNo() {
		return this.groupNo;
	}

	/**
	 * 属性合编组的setter方法
	 */
	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	/**
	 * 属性分编组的getter方法
	 */

	@Column(name = "SUBGROUPNO")
	public String getSubGroupNo() {
		return this.subGroupNo;
	}

	/**
	 * 属性分编组的setter方法
	 */
	public void setSubGroupNo(String subGroupNo) {
		this.subGroupNo = subGroupNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpGroupId)) {
			return false;
		}
		PrpGroupId castOther = (PrpGroupId) other;

		return ((this.getGroupNo() == castOther.getGroupNo()) || (this.getGroupNo() != null && castOther.getGroupNo() != null && this.getGroupNo().equals(castOther.getGroupNo())))
				&& ((this.getSubGroupNo() == castOther.getSubGroupNo()) || (this.getSubGroupNo() != null && castOther.getSubGroupNo() != null && this.getSubGroupNo().equals(castOther.getSubGroupNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getGroupNo() == null ? 0 : this.getGroupNo().hashCode());
		result = 37 * result + (getSubGroupNo() == null ? 0 : this.getSubGroupNo().hashCode());
		return result;
	}

}
