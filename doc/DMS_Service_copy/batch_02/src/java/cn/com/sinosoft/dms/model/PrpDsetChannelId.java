package cn.com.sinosoft.dms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class PrpDsetChannelId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**���b��a*/
	private String setCode;
	/**ͨ·�e��̖*/
	private Integer setChannelNo;
	
	public PrpDsetChannelId() {
	}
	@Column(name = "setCode")
	public String getSetCode() {
		return setCode;
	}

	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	@Column(name = "setChannelNo")
	public Integer getSetChannelNo() {
		return setChannelNo;
	}

	public void setSetChannelNo(Integer setChannelNo) {
		this.setChannelNo = setChannelNo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((setChannelNo == null) ? 0 : setChannelNo.hashCode());
		result = prime * result + ((setCode == null) ? 0 : setCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrpDsetChannelId other = (PrpDsetChannelId) obj;
		if (setChannelNo == null) {
			if (other.setChannelNo != null)
				return false;
		} else if (!setChannelNo.equals(other.setChannelNo))
			return false;
		if (setCode == null) {
			if (other.setCode != null)
				return false;
		} else if (!setCode.equals(other.setCode))
			return false;
		return true;
	}
}
