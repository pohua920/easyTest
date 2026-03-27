package com.sinosoft.dmsdriver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class PrpDsetRenewalId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**���b��a*/
	private String setCode;
	/**�����b��a*/
	private String newSetCode;
	
	public PrpDsetRenewalId() {
	}
	@Column(name = "setCode")
	public String getSetCode() {
		return setCode;
	}
	public void setSetCode(String setCode) {
		this.setCode = setCode;
	}
	@Column(name = "newSetCode")
	public String getNewSetCode() {
		return newSetCode;
	}
	public void setNewSetCode(String newSetCode) {
		this.newSetCode = newSetCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((newSetCode == null) ? 0 : newSetCode.hashCode());
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
		PrpDsetRenewalId other = (PrpDsetRenewalId) obj;
		if (newSetCode == null) {
			if (other.newSetCode != null)
				return false;
		} else if (!newSetCode.equals(other.newSetCode))
			return false;
		if (setCode == null) {
			if (other.setCode != null)
				return false;
		} else if (!setCode.equals(other.setCode))
			return false;
		return true;
	}
	
}
