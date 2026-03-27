package cn.com.sinosoft.dms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpDsetRationrelationId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**���b��a*/
	private String setCode;
	/**������a*/
	private String rationCode;
	
	public PrpDsetRationrelationId() {
	}
	@Column(name="setCode")
	public String getSetCode() {
		return setCode;
	}
	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	@Column(name="rationCode")
	public String getRationCode() {
		return rationCode;
	}
	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rationCode == null) ? 0 : rationCode.hashCode());
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
		PrpDsetRationrelationId other = (PrpDsetRationrelationId) obj;
		if (rationCode == null) {
			if (other.rationCode != null)
				return false;
		} else if (!rationCode.equals(other.rationCode))
			return false;
		if (setCode == null) {
			if (other.setCode != null)
				return false;
		} else if (!setCode.equals(other.setCode))
			return false;
		return true;
	}
	
}
