package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpPitemCarId
 */
@Embeddable
public class PrpPitemCarId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性批单号码 */
	private String endorseNo;

	/** 属性标的序号 */
	private Integer itemNo;

	/**
	 * 类PrpPitemCarId的默认构造方法
	 */
	public PrpPitemCarId() {
	}

	/**
	 * 属性批单号码的getter方法
	 */

	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性批单号码的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
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
		if (!(other instanceof PrpPitemCarId)) {
			return false;
		}
		PrpPitemCarId castOther = (PrpPitemCarId) other;

		return ((this.getEndorseNo() == castOther.getEndorseNo()) || (this.getEndorseNo() != null && castOther.getEndorseNo() != null && this.getEndorseNo().equals(castOther.getEndorseNo())))
				&& ((this.getItemNo() == castOther.getItemNo()) || (this.getItemNo() != null && castOther.getItemNo() != null && this.getItemNo().equals(castOther.getItemNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEndorseNo() == null ? 0 : this.getEndorseNo().hashCode());
		result = 37 * result + (getItemNo() == null ? 0 : this.getItemNo().hashCode());
		return result;
	}

}
