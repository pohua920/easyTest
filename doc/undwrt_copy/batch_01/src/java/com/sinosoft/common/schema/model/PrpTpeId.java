package com.sinosoft.common.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
*/
@Embeddable
public class PrpTpeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性投保单号码 */
	private String proposalNo;

	/** 属性序号 */
	private Integer serialNo;

	/**
	 * 类PrpTinsuredId的默认构造方法
	 */
	public PrpTpeId() {
	}

	@Column(name = "PROPOSALNO")
	public String getProposalNo() {
		return this.proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

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
		if (!(other instanceof PrpTinsuredId)) {
			return false;
		}
		PrpTinsuredId castOther = (PrpTinsuredId) other;
		return ((this.getProposalNo() == castOther.getProposalNo()) || (this
				.getProposalNo() != null && castOther.getProposalNo() != null && this
				.getProposalNo().equals(castOther.getProposalNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this
						.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getProposalNo() == null ? 0 : this.getProposalNo()
						.hashCode());
		result = 37 * result
				+ (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
