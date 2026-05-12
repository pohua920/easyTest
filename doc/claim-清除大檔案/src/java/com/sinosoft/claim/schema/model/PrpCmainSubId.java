package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpCmainSubId
 */
@Embeddable
public class PrpCmainSubId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性主保单号码 */
	private String mainPolicyNo;

	/**
	 * 类PrpCmainSubId的默认构造方法
	 */
	public PrpCmainSubId() {
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
	 * 属性主保单号码的getter方法
	 */

	@Column(name = "MAINPOLICYNO")
	public String getMainPolicyNo() {
		return this.mainPolicyNo;
	}

	/**
	 * 属性主保单号码的setter方法
	 */
	public void setMainPolicyNo(String mainPolicyNo) {
		this.mainPolicyNo = mainPolicyNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpCmainSubId)) {
			return false;
		}
		PrpCmainSubId castOther = (PrpCmainSubId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getMainPolicyNo() == castOther.getMainPolicyNo()) || (this.getMainPolicyNo() != null && castOther.getMainPolicyNo() != null && this.getMainPolicyNo().equals(castOther.getMainPolicyNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getMainPolicyNo() == null ? 0 : this.getMainPolicyNo().hashCode());
		return result;
	}

}
