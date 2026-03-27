package com.sinosoft.dmsdriver.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDChannelRationEngageId  implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private String rationCode;

	//private String riskCode;

	//private String engageCode;
	
	//private String clauseCode;
	private String channelInfoNo;
	private String serialNo;

	public PrpDChannelRationEngageId() {
	}
	
	@Column(name="CHANNELINFONO", nullable = false)
	public String getChannelInfoNo() {
		return channelInfoNo;
	}

	public void setChannelInfoNo(String channelInfoNo) {
		this.channelInfoNo = channelInfoNo;
	}


/*	@Column(name="CLAUSECODE")
	public String getClauseCode() {
		return clauseCode;
	}

	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}
*/
	@Column(name = "RATIONCODE", nullable = false)
	public String getRationCode() {
		return this.rationCode;
	}

	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}

/*	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}*/

/*	@Column(name = "ENGAGECODE")
	public String getEngageCode() {
		return this.engageCode;
	}

	public void setEngageCode(String engageCode) {
		this.engageCode = engageCode;
	}*/

	@Column(name = "SERIALNO", nullable = false)
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
		if (!(other instanceof PrpDChannelRationEngageId))
			return false;
		PrpDChannelRationEngageId castOther = (PrpDChannelRationEngageId) other;

		return ((this.getRationCode() == castOther.getRationCode()) || (this
				.getRationCode() != null
				&& castOther.getRationCode() != null && this.getRationCode()
				.equals(castOther.getRationCode())))
				&&
				(this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this.getSerialNo()
						.equals(castOther.getSerialNo()))
				&& ((this.getChannelInfoNo() == castOther.getChannelInfoNo()) || (this
						.getChannelInfoNo() != null
						&& castOther.getChannelInfoNo() != null && this
						.getChannelInfoNo().equals(castOther.getChannelInfoNo())));
	}


	public int hashCode() {
		int result = 17;

		result = 37* result+ (getRationCode() == null ? 0 : this.getRationCode().hashCode());
		result = 37 * result+ (getChannelInfoNo() == null ? 0 : this.getChannelInfoNo().hashCode());
		result = 37* result+ (getSerialNo() == null ? 0 : this.getSerialNo().hashCode());
		//result = 37* result+ (getClauseCode() == null ? 0 : this.getClauseCode().hashCode());
		return result;
	}
}
