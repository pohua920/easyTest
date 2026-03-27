package com.sinosoft.dmsdriver.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDRationPeriodRateId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/**  �������� */
	private String rationCode;
	/**  ��� */
	private String serialNo;
	/** ���ִ��� */
	private String riskCode;
	/** �������  */
	private String clauseCode;
	/** ���δ��� */
	private String kindCode;
	
	
	@Column(name="RISKCODE",nullable=false)
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	
	@Column(name="CLAUSECODE",nullable=false)
	public String getClauseCode() {
		return clauseCode;
	}
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}
	
	@Column(name="KINDCODE",nullable=false)
	public String getKindCode() {
		return kindCode;
	}
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	/**  �������� */
	@Column(name = "RATIONCODE", nullable = false)
	public String getRationCode() {
		return rationCode;
	}
	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}
	
	/**  ���*/
	@Column(name="SERIALNO",nullable=false)
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDRationEngageId))
			return false;
		PrpDRationPeriodRateId castOther = (PrpDRationPeriodRateId) other;

		return ((this.getRationCode() == castOther.getRationCode()) || (this
				.getRationCode() != null
				&& castOther.getRationCode() != null && this.getRationCode()
				.equals(castOther.getRationCode())))
				&& ((this.getRiskCode() == castOther.getRiskCode()) || (this
						.getRiskCode() != null
						&& castOther.getRiskCode() != null && this
						.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this
						.getSerialNo().equals(castOther.getSerialNo()))
				&& ((this.getClauseCode() == castOther.getClauseCode()) || (this
						.getClauseCode() != null
						&& castOther.getClauseCode() != null && this
						.getClauseCode().equals(castOther.getClauseCode()))));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getRationCode() == null ? 0 : this.getRationCode()
						.hashCode());
		result = 37 * result
				+ (getRiskCode() == null ? 0 : this.getRiskCode().hashCode());
		result = 37
				* result
				+ (getSerialNo() == null ? 0 : this.getSerialNo()
						.hashCode());
		result = 37
		* result
		+ (getClauseCode() == null ? 0 : this.getClauseCode()
				.hashCode());
		return result;
	}
	
}
