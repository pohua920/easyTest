package com.sinosoft.dmsdriver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class PrpDrationConditionId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**������a*/
	private String rationCode;
	/**�l����̖*/
	private Integer conditionNo;
	
	public PrpDrationConditionId() {
	}
	@Column(name = "rationCode")
	public String getRationCode() {
		return rationCode;
	}

	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}
	
	@Column(name = "conditionNo")
	public Integer getConditionNo() {
		return conditionNo;
	}

	public void setConditionNo(Integer conditionNo) {
		this.conditionNo = conditionNo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conditionNo == null) ? 0 : conditionNo.hashCode());
		result = prime * result
				+ ((rationCode == null) ? 0 : rationCode.hashCode());
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
		PrpDrationConditionId other = (PrpDrationConditionId) obj;
		if (conditionNo == null) {
			if (other.conditionNo != null)
				return false;
		} else if (!conditionNo.equals(other.conditionNo))
			return false;
		if (rationCode == null) {
			if (other.rationCode != null)
				return false;
		} else if (!rationCode.equals(other.rationCode))
			return false;
		return true;
	}

	
}
