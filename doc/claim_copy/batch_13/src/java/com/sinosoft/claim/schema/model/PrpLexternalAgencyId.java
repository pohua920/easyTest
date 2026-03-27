package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLexternalAgencyId
 */
@Embeddable
public class PrpLexternalAgencyId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性机构类型 */
	private String comtype;

	/**
	 * 类PrpLexternalAgencyId的默认构造方法
	 */
	public PrpLexternalAgencyId() {
	}

	/**
	 * 属性业务归属机构代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性业务归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性机构类型的getter方法
	 */

	@Column(name = "COMTYPE")
	public String getComtype() {
		return this.comtype;
	}

	/**
	 * 属性机构类型的setter方法
	 */
	public void setComtype(String comtype) {
		this.comtype = comtype;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLexternalAgencyId)) {
			return false;
		}
		PrpLexternalAgencyId castOther = (PrpLexternalAgencyId) other;

		return ((this.getComCode() == castOther.getComCode()) || (this.getComCode() != null && castOther.getComCode() != null && this.getComCode().equals(castOther.getComCode())))
				&& ((this.getComtype() == castOther.getComtype()) || (this.getComtype() != null && castOther.getComtype() != null && this.getComtype().equals(castOther.getComtype())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getComCode() == null ? 0 : this.getComCode().hashCode());
		result = 37 * result + (getComtype() == null ? 0 : this.getComtype().hashCode());
		return result;
	}

}
