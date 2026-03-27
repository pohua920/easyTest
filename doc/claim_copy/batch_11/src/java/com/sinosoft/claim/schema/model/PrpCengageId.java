package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCengageId
 */
@Embeddable
public class PrpCengageId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性序号 */
	private Integer serialNo;

	/** 属性行序号 */
	private Integer lineNo;

	/**
	 * 类PrpCengageId的默认构造方法
	 */
	public PrpCengageId() {
	}

	public PrpCengageId(String policyNo, Integer serialNo, Integer lineNo) {
		this.policyNo = policyNo;
		this.serialNo = serialNo;
		this.lineNo = lineNo;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 属性行序号的getter方法
	 */

	@Column(name = "LINENO")
	public Integer getLineNo() {
		return this.lineNo;
	}

	/**
	 * 属性行序号的setter方法
	 */
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpCengageId)) {
			return false;
		}
		PrpCengageId castOther = (PrpCengageId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())))
				&& ((this.getLineNo() == castOther.getLineNo()) || (this.getLineNo() != null && castOther.getLineNo() != null && this.getLineNo().equals(castOther.getLineNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		result = 37 * result + (getLineNo() == null ? 0 : this.getLineNo().hashCode());
		return result;
	}

}
