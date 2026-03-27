package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类SwfPackageId
 */
@Embeddable
public class SwfPackageId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性明细信息包ID */
	private String packageID;

	/** 属性明细项序号 */
	private Integer detailNo = 0;

	/**
	 * 类SwfPackageId的默认构造方法
	 */
	public SwfPackageId() {
	}

	/**
	 * 属性明细信息包ID的getter方法
	 */

	@Column(name = "PACKAGEID")
	public String getPackageID() {
		return this.packageID;
	}

	/**
	 * 属性明细信息包ID的setter方法
	 */
	public void setPackageID(String packageID) {
		this.packageID = packageID;
	}

	/**
	 * 属性明细项序号的getter方法
	 */

	@Column(name = "DETAILNO")
	public Integer getDetailNo() {
		return this.detailNo;
	}

	/**
	 * 属性明细项序号的setter方法
	 */
	public void setDetailNo(Integer detailNo) {
		this.detailNo = detailNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof SwfPackageId)) {
			return false;
		}
		SwfPackageId castOther = (SwfPackageId) other;

		return ((this.getPackageID() == castOther.getPackageID()) || (this.getPackageID() != null && castOther.getPackageID() != null && this.getPackageID().equals(castOther.getPackageID())))
				&& ((this.getDetailNo() == castOther.getDetailNo()) || (this.getDetailNo() != null && castOther.getDetailNo() != null && this.getDetailNo().equals(castOther.getDetailNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPackageID() == null ? 0 : this.getPackageID().hashCode());
		result = 37 * result + (getDetailNo() == null ? 0 : this.getDetailNo().hashCode());
		return result;
	}

	public SwfPackageId(String packageID, Integer detailNo) {
		this.packageID = packageID;
		this.detailNo = detailNo;
	}

}
