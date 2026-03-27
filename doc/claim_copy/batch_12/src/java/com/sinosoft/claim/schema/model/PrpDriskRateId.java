package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDriskRateId implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	/** œ’÷÷ */
	private String riskCode;
	private String clauseCode;
	/** œ’± */
	private String kindCode;
	private String rateCode;
	/** –Ú∫≈ */
	private Integer serialNo = 1;
	
	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	@Column(name = "clauseCode")
	public String getClauseCode() {
		return clauseCode;
	}
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}
	@Column(name = "kindCode")
	public String getKindCode() {
		return kindCode;
	}
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	@Column(name = "rateCode")
	public String getRateCode() {
		return rateCode;
	}
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}
	@Column(name = "serialNo")
	public Integer getSerialNo() {
		return serialNo;
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
		if (!(other instanceof PrpDriskRateId)) {
			return false;
		}
		PrpDriskRateId castOther = (PrpDriskRateId) other;

		return ((this.getRiskCode() == castOther.getRiskCode()) || (this.getRiskCode() != null && castOther.getRiskCode() != null && this.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getClauseCode() == castOther.getClauseCode()) || (this.getClauseCode() != null && castOther.getClauseCode() != null && this.getClauseCode().equals(castOther.getClauseCode())))
				&& ((this.getKindCode() == castOther.getKindCode()) || (this.getKindCode() != null && castOther.getKindCode() != null && this.getKindCode().equals(castOther.getKindCode())))
				&& ((this.getRateCode() == castOther.getRateCode()) || (this.getRateCode() != null && castOther.getRateCode() != null && this.getRateCode().equals(castOther.getRateCode())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this.getSerialNo() != null && castOther.getSerialNo() != null && this.getSerialNo().equals(castOther.getSerialNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37 * result + (getClauseCode() == null ? 0 : this.getClauseCode().hashCode());
		result = 37 * result + (getKindCode() == null ? 0 : this.getKindCode().hashCode());
		result = 37 * result + (getRateCode() == null ? 0 : this.getRateCode().hashCode());
		result = 37 * result + (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		return result;
	}
}
