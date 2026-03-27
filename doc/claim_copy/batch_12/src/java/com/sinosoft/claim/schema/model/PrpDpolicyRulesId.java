package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則
 * POJO类PrpDpolicyRulesId
 */
@Embeddable
public class PrpDpolicyRulesId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String codeCode;

	private Date startDate;

	/**
	 * 类PrpDpolicyRulesId的默认构造方法
	 */
	public PrpDpolicyRulesId() {
	}
	/**
	 * 类PrpDpolicyRulesId的默认构造方法
	 * @param codeCode
	 * @param startDate
	 */
	public PrpDpolicyRulesId(String codeCode, Date startDate) {
		this.codeCode = codeCode;
		this.startDate = startDate;
	}
	@Column(name = "CODECODE")
	public String getCodeCode() {
		return this.codeCode;
	}

	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}

	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpDpolicyRulesId)) {
			return false;
		}
		PrpDpolicyRulesId castOther = (PrpDpolicyRulesId) other;

		return ((this.getCodeCode() == castOther.getCodeCode()) || (this.getCodeCode() != null && castOther.getCodeCode() != null && this.getCodeCode().equals(castOther.getCodeCode())))
				&& ((this.getStartDate() == castOther.getStartDate()) || (this.getStartDate() != null && castOther.getStartDate() != null && this.getStartDate().equals(castOther.getStartDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCodeCode() == null ? 0 : this.getCodeCode().hashCode());
		result = 37 * result + (getStartDate() == null ? 0 : this.getStartDate().hashCode());
		return result;
	}

}
