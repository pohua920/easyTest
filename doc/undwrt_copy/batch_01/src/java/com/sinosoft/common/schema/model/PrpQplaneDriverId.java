package com.sinosoft.common.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Embeddable
public class PrpQplaneDriverId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性要保單號 */
	private String proposalNo;

	/** 属性序列號 */
	private Integer serialNo;

	/**
	 * 类prpQplaneDriverId的默认构造方法
	 */
	public PrpQplaneDriverId() {
	}

	/**
	 * 属性要保單號的getter方法
	 */

	@Column(name = "PROPOSALNO")
	public String getProposalNo() {
		return this.proposalNo;
	}

	/**
	 * 属性要保單號的setter方法
	 */
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	/**
	 * 属性序列號的getter方法
	 */

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性序列號的setter方法
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
		if (!(other instanceof PrpQplaneDeviceId)) {
			return false;
		}
		PrpQplaneDeviceId castOther = (PrpQplaneDeviceId) other;

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
