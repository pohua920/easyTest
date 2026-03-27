package com.sinosoft.claim.schema.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PrpLremnantId implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** º∆À„ È∫≈¬Î */
	private String compensateNo = "";
	/** –Ú∫≈ */
	private Integer serialNo;
	
	public PrpLremnantId() {
	}
	
	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	@Column(name = "SERIALNO")
	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((compensateNo == null) ? 0 : compensateNo.hashCode());
		result = prime * result
				+ ((serialNo == null) ? 0 : serialNo.hashCode());
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
		PrpLremnantId other = (PrpLremnantId) obj;
		if (compensateNo == null) {
			if (other.compensateNo != null)
				return false;
		} else if (!compensateNo.equals(other.compensateNo))
			return false;
		if (serialNo == null) {
			if (other.serialNo != null)
				return false;
		} else if (!serialNo.equals(other.serialNo))
			return false;
		return true;
	}


	
}
