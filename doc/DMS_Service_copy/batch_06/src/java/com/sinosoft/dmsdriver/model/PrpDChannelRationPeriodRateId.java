package com.sinosoft.dmsdriver.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDChannelRationPeriodRateId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/**  �������� */
	private String rationCode;
	/** ������Ϣ��� */
	private String channelInfoNo;
	/** �����������α�� */
	private String channelRationClauseKindNo;
	/**  ��� */
	private String serialNo;

	/**  �������� */
	@Column(name = "RATIONCODE")
	public String getRationCode() {
		return rationCode;
	}
	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}
	
	/**  ��� */
	@Column(name="SERIALNO")
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	/**  ������Ϣ���*/
	@Column(name="CHANNELINFONO")
	public String getChannelInfoNo() {
		return channelInfoNo;
	}
	public void setChannelInfoNo(String channelInfoNo) {
		this.channelInfoNo = channelInfoNo;
	}
	/**  �����������α��*/
	@Column(name="CHANNELRATIONCLAUSEKINDNO")
	public String getChannelRationClauseKindNo() {
		return channelRationClauseKindNo;
	}
	public void setChannelRationClauseKindNo(String channelRationClauseKindNo) {
		this.channelRationClauseKindNo = channelRationClauseKindNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDRationEngageId))
			return false;
		PrpDChannelRationPeriodRateId castOther = (PrpDChannelRationPeriodRateId) other;

		return ((this.getRationCode() == castOther.getRationCode()) || (this
				.getRationCode() != null
				&& castOther.getRationCode() != null && this.getRationCode()
				.equals(castOther.getRationCode())))
				&& ((this.getChannelInfoNo() == castOther.getChannelInfoNo()) || (this
						.getChannelInfoNo() != null
						&& castOther.getChannelInfoNo() != null && this
						.getChannelInfoNo().equals(castOther.getChannelInfoNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this
						.getSerialNo().equals(castOther.getSerialNo()))
				&& ((this.getChannelRationClauseKindNo() == castOther.getChannelRationClauseKindNo()) || (this
						.getChannelRationClauseKindNo() != null
						&& castOther.getChannelRationClauseKindNo() != null && this
						.getChannelRationClauseKindNo().equals(castOther.getChannelRationClauseKindNo()))));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getRationCode() == null ? 0 : this.getRationCode()
						.hashCode());
		result = 37 * result
				+ (getChannelInfoNo() == null ? 0 : this.getChannelInfoNo().hashCode());
		result = 37
				* result
				+ (getSerialNo() == null ? 0 : this.getSerialNo()
						.hashCode());
		result = 37
		* result
		+ (getChannelRationClauseKindNo() == null ? 0 : this.getChannelRationClauseKindNo()
				.hashCode());
		return result;
	}
}
