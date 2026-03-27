package cn.com.sinosoft.dms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDChannelRationClauseKindId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String rationCode;

	private String channelInfoNo;
	private String serialNo;

	public PrpDChannelRationClauseKindId() {
	}

	@Column(name = "RATIONCODE")
	public String getRationCode() {
		return this.rationCode;
	}

	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}

	@Column(name = "SERIALNO")
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "CHANNELINFONO")
	public String getChannelInfoNo() {
		return channelInfoNo;
	}

	public void setChannelInfoNo(String channelInfoNo) {
		this.channelInfoNo = channelInfoNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpDChannelRationClauseKindId))
			return false;
		PrpDChannelRationClauseKindId castOther = (PrpDChannelRationClauseKindId) other;

		return ((this.getRationCode() == castOther.getRationCode()) || (this
				.getRationCode() != null
				&& castOther.getRationCode() != null && this.getRationCode()
				.equals(castOther.getRationCode())))
				&& ((this.getRationCode() == castOther.getRationCode()) || (this
						.getRationCode() != null
						&& castOther.getRationCode() != null && this
						.getRationCode().equals(castOther.getRationCode())))
				&& ((this.getChannelInfoNo() == castOther.getChannelInfoNo()) || (this
						.getChannelInfoNo() != null
						&& castOther.getChannelInfoNo() != null && this
						.getChannelInfoNo().equals(castOther.getChannelInfoNo())))
				&& ((this.getSerialNo() == castOther.getSerialNo()) || (this
						.getSerialNo() != null
						&& castOther.getSerialNo() != null && this
						.getSerialNo().equals(castOther.getSerialNo())));
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
		return result;
	}

}
