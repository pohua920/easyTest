// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCopymainCarGoSubId
 */
@Embeddable
public class PrpCopymainCarGoSubId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性批单号 */
	private String endorseNo;

	/** 属性序列号 */
	private Integer serialNo;

	/**
	 * 类PrpCmainCarGoSubId的默认构造方法
	 */
	public PrpCopymainCarGoSubId() {
	}

	public PrpCopymainCarGoSubId(String endorseNo, Integer serialNo) {
		this.endorseNo = endorseNo;
		this.serialNo = serialNo;
	}

	/**
	 * 属性投保单号的getter方法
	 */

	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性投保单号的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

	/**
	 * 属性序列号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序列号的setter方法
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
		if (!(other instanceof PrpCopymainCarGoSubId)) {
			return false;
		}
		PrpCopymainCarGoSubId castOther = (PrpCopymainCarGoSubId) other;

		return ((this.getEndorseNo() == castOther.getEndorseNo()) || (this.getEndorseNo() != null && castOther.getEndorseNo() != null && this.getEndorseNo().equals(castOther.getEndorseNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getEndorseNo() == null ? 0 : this.getEndorseNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
