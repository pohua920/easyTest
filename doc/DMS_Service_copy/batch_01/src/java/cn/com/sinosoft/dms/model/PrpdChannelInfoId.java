package cn.com.sinosoft.dms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpdChannelInfoId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String rationCode;
	private String serialNo;
	
	@Column(name = "RATIONCODE", nullable = false)
	public String getRationCode() {
		return rationCode;
	}
	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}
	
	@Column(name = "channelInfoNo", nullable = false)
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
		if (!(other instanceof PrpdChannelInfoId))
			return false;
		PrpdChannelInfoId castOther = (PrpdChannelInfoId) other;

		return ((this.getRationCode() == castOther.getRationCode()) || (this
				.getRationCode() != null
				&& castOther.getRationCode() != null && this.getRationCode()
				.equals(castOther.getRationCode())))
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
		result = 37
				* result
				+ (getSerialNo() == null ? 0 : this.getSerialNo()
						.hashCode());
		return result;
	}
	
}
