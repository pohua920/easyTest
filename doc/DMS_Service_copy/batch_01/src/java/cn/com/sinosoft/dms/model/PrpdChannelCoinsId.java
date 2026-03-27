package cn.com.sinosoft.dms.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpdChannelCoinsId implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String serialNo;
	private String channelInfoNo;
	private String rationCode;

	@Column(name="channelCoinsNo", nullable = false)
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	@Column(name="channelInfoNo", nullable = false)
	public String getChannelInfoNo() {
		return channelInfoNo;
	}
	public void setChannelInfoNo(String channelInfoNo) {
		this.channelInfoNo = channelInfoNo;
	}
	
	@Column(name="rationCode", nullable = false)
	public String getRationCode() {
		return rationCode;
	}
	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PrpdChannelCoinsId))
			return false;
		PrpdChannelCoinsId castOther = (PrpdChannelCoinsId) other;

		return ((this.getSerialNo() == castOther.getSerialNo()) || (this
				.getSerialNo() != null
				&& castOther.getSerialNo() != null && this.getSerialNo()
				.equals(castOther.getSerialNo())))
				&& ((this.getChannelInfoNo() == castOther.getChannelInfoNo()) || (this
						.getChannelInfoNo() != null
						&& castOther.getChannelInfoNo() != null && this
						.getChannelInfoNo().equals(castOther.getChannelInfoNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getSerialNo() == null ? 0 : this.getSerialNo()
						.hashCode());
		result = 37
				* result
				+ (getChannelInfoNo() == null ? 0 : this.getChannelInfoNo()
						.hashCode());
		return result;
	}
}
