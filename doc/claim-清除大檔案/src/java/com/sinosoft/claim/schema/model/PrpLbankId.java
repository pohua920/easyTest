package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpLbankId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 银行代号（总行就是总行代号，分行就是分行代号） */
	private String bankCode;
	/** 上级银行代号 */
	private String upperBankCode;
	
	public PrpLbankId(String bankCode, String upperBankCode) {
		this.bankCode = bankCode;
		this.upperBankCode = upperBankCode;
	}

	public PrpLbankId() {
	}

	@Column(name = "bankCode")
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	@Column(name = "upperBankCode")
	public String getUpperBankCode() {
		return upperBankCode;
	}

	public void setUpperBankCode(String upperBankCode) {
		this.upperBankCode = upperBankCode;
	}

	public boolean equals(Object other) {
		if ((this == other)) {
			return true;
		}
		if ((other == null)) {
			return false;
		}
		if (!(other instanceof PrpLbankId)) {
			return false;
		}
		PrpLbankId castOther = (PrpLbankId) other;

		return ((this.getBankCode() == castOther.getBankCode()) || (this.getBankCode() != null && castOther.getBankCode() != null && this.getBankCode().equals(castOther.getBankCode())))
				&& ((this.getUpperBankCode() == castOther.getUpperBankCode()) || (this.getUpperBankCode() != null && castOther.getUpperBankCode() != null && this.getUpperBankCode().equals(castOther.getUpperBankCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getBankCode() == null ? 0 : this.getBankCode().hashCode());
		result = 37 * result + (getUpperBankCode() == null ? 0 : this.getUpperBankCode().hashCode());
		return result;
	}

}
