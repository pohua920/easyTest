package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpDidentifierId
 */
@Embeddable
public class PrpDidentifierId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性检验人代码 */
	private String identifierCode;

	/** 属性港口代码 */
	private String portCode;

	/**
	 * 类PrpDidentifierId的默认构造方法
	 */
	public PrpDidentifierId() {
	}

	/**
	 * 属性检验人代码的getter方法
	 */

	@Column(name = "IDENTIFIERCODE")
	public String getIdentifierCode() {
		return this.identifierCode;
	}

	/**
	 * 属性检验人代码的setter方法
	 */
	public void setIdentifierCode(String identifierCode) {
		this.identifierCode = identifierCode;
	}

	/**
	 * 属性港口代码的getter方法
	 */

	@Column(name = "PORTCODE")
	public String getPortCode() {
		return this.portCode;
	}

	/**
	 * 属性港口代码的setter方法
	 */
	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDidentifierId)) {
			return false;
		}
		PrpDidentifierId castOther = (PrpDidentifierId) other;

		return ((this.getIdentifierCode() == castOther.getIdentifierCode()) || (this.getIdentifierCode() != null && castOther.getIdentifierCode() != null && this.getIdentifierCode().equals(castOther.getIdentifierCode())))
				&& ((this.getPortCode() == castOther.getPortCode()) || (this.getPortCode() != null && castOther.getPortCode() != null && this.getPortCode().equals(castOther.getPortCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIdentifierCode() == null ? 0 : this.getIdentifierCode().hashCode());
		result = 37 * result + (getPortCode() == null ? 0 : this.getPortCode().hashCode());
		return result;
	}

}
