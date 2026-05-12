package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 * 受害人就诊医院
 * @author 中科软
 *
 */
@Embeddable
public class PrpLpaymentTypeId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 给付类型*/
	private String type;

	/** 给付类型序号 */
	private Integer serialNo;

	/**
	 * 类PrpLpaymentTypeId的默认构造方法
	 */
	public PrpLpaymentTypeId() {
	}

	@Column(name = "type")
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
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
		if (!(other instanceof PrpLpaymentTypeId)) {
			return false;
		}
		PrpLpaymentTypeId castOther = (PrpLpaymentTypeId) other;

		return ((this.getType() == castOther.getType()) || (this.getType() != null && castOther.getType() != null && this.getType().equals(castOther.getType())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getType() == null ? 0 : this.getType().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
