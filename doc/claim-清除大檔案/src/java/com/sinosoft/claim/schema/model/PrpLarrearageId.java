package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpLarrearageId
 */
@Embeddable
public class PrpLarrearageId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性本期应还款截止日期 */
	private Date arrearageEndDate;

	/**
	 * 类PrpLarrearageId的默认构造方法
	 */
	public PrpLarrearageId() {
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
	 * 属性本期应还款截止日期的getter方法
	 */

	@Column(name = "ARREARAGEENDDATE")
	public Date getArrearageEndDate() {
		return this.arrearageEndDate;
	}

	/**
	 * 属性本期应还款截止日期的setter方法
	 */
	public void setArrearageEndDate(Date arrearageEndDate) {
		this.arrearageEndDate = arrearageEndDate;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLarrearageId)) {
			return false;
		}
		PrpLarrearageId castOther = (PrpLarrearageId) other;

		return ((this.getPolicyNo() == castOther.getPolicyNo()) || (this.getPolicyNo() != null && castOther.getPolicyNo() != null && this.getPolicyNo().equals(castOther.getPolicyNo())))
				&& ((this.getArrearageEndDate() == castOther.getArrearageEndDate()) || (this.getArrearageEndDate() != null && castOther.getArrearageEndDate() != null && this.getArrearageEndDate().equals(castOther.getArrearageEndDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getPolicyNo() == null ? 0 : this.getPolicyNo().hashCode());
		result = 37 * result + (getArrearageEndDate() == null ? 0 : this.getArrearageEndDate().hashCode());
		return result;
	}

}
