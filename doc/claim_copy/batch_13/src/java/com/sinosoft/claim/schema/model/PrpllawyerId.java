package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * POJO类PrpllawyerId
 */
@Embeddable
public class PrpllawyerId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号 */
	private String claimNo;

	/** 属性标的序号 */
	private BigDecimal itemno;

	/** 属性交费计划序号 */
	private BigDecimal serialNo;

	/**
	 * 类PrpllawyerId的默认构造方法
	 */
	public PrpllawyerId() {
	}

	/**
	 * 属性立案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性标的序号的getter方法
	 */

	@Column(name = "ITEMNO")
	public BigDecimal getItemno() {
		return this.itemno;
	}

	/**
	 * 属性标的序号的setter方法
	 */
	public void setItemno(BigDecimal itemno) {
		this.itemno = itemno;
	}

	/**
	 * 属性交费计划序号的getter方法
	 */

	@Column(name = "SERIALNO")
	public BigDecimal getSerialNo() {
		return this.serialNo;
	}

	/**
	 * 属性交费计划序号的setter方法
	 */
	public void setSerialNo(BigDecimal serialNo) {
		this.serialNo = serialNo;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpllawyerId)) {
			return false;
		}
		PrpllawyerId castOther = (PrpllawyerId) other;

		return ((this.getClaimNo() == castOther.getClaimNo()) || (this.getClaimNo() != null && castOther.getClaimNo() != null && this.getClaimNo().equals(castOther.getClaimNo())))
				&& ((this.getItemno() == castOther.getItemno()) || (this.getItemno() != null && castOther.getItemno() != null && this.getItemno().equals(castOther.getItemno())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getClaimNo() == null ? 0 : this.getClaimNo().hashCode());
		result = 37 * result + (getItemno() == null ? 0 : this.getItemno().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}

}
