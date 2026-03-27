package com.sinosoft.claim.schema.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 每日汇率主表
 * @author 中科软
 *
 */
@Embeddable
public class PrpDexchId implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date exchDate;//时间
	private String baseCurrency;//本位币
	private String exchCurrency;//

	public PrpDexchId() {
	}

	public PrpDexchId(Date exchDate, String baseCurrency, String exchCurrency) {
		this.exchDate = exchDate;
		this.baseCurrency = baseCurrency;
		this.exchCurrency = exchCurrency;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "EXCHDATE")
	public Date getExchDate() {
		return this.exchDate;
	}

	public void setExchDate(Date exchDate) {
		this.exchDate = exchDate;
	}

	@Column(name = "BASECURRENCY")
	public String getBaseCurrency() {
		return this.baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	@Column(name = "EXCHCURRENCY")
	public String getExchCurrency() {
		return this.exchCurrency;
	}

	public void setExchCurrency(String exchCurrency) {
		this.exchCurrency = exchCurrency;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!(other instanceof PrpDexchId)) {
			return false;
		}
		PrpDexchId castOther = (PrpDexchId) other;

		return ((((getExchDate() == castOther.getExchDate()) || ((getExchDate() != null) && (castOther.getExchDate() != null) && (getExchDate().equals(castOther.getExchDate())))))
				&& (((getBaseCurrency() == castOther.getBaseCurrency()) || ((getBaseCurrency() != null) && (castOther.getBaseCurrency() != null) && (getBaseCurrency().equals(castOther.getBaseCurrency()))))) && (((getExchCurrency() == castOther
				.getExchCurrency()) || ((getExchCurrency() != null) && (castOther.getExchCurrency() != null) && (getExchCurrency().equals(castOther.getExchCurrency()))))));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + ((getExchDate() == null) ? 0 : getExchDate().hashCode());
		result = 37 * result + ((getBaseCurrency() == null) ? 0 : getBaseCurrency().hashCode());
		result = 37 * result + ((getExchCurrency() == null) ? 0 : getExchCurrency().hashCode());
		return result;
	}

}
