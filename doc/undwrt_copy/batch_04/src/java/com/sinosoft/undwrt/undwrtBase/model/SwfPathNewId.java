package com.sinosoft.undwrt.undwrtBase.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The Class SwfPathNewId.
 */
@Embeddable
public class SwfPathNewId implements java.io.Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 屬性險種代碼. */
	private String riskCode;
	
	/** 屬性機構代碼. */
	private String comCode;
	
	/**
	 * Instantiates a new swf path new id.
	 */
	public SwfPathNewId()
	{
	}

	/**
	 * Instantiates a new swf path new id.
	 * 
	 * @param riskCode
	 *            險種代碼
	 * @param comCode
	 *            the com code
	 */
	public SwfPathNewId(String riskCode, String comCode) {
	
		this.riskCode = riskCode;
		this.comCode = comCode;
	}


	/**
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
	@Column(name = "RISKCODE", nullable = false, length = 4)
	public String getRiskCode() {
		return riskCode;
	}

	/**
	 * 設置屬性險種代碼.
	 * 
	 * @param riskCode
	 *            待設置的險種代碼的值
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	
	/**
	 * 獲取屬性機構代碼.
	 * 
	 * @return 屬性機構代碼的值
	 */
	@Column(name = "COMCODE", nullable = false, length = 10)
	public String getComCode() {
		return comCode;
	}

	/**
	 * 設置屬性機構代碼.
	 * 
	 * @param comCode
	 *            待設置的機構代碼的值
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	
	/**
	 * Equals.
	 * 
	 * @param other
	 *            the other
	 * @return true, if successful
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UwMaterialId))
			return false;
		SwfPathNewId castOther = (SwfPathNewId) other;

		return ((this.getRiskCode() == castOther.getRiskCode()) || (this
				.getRiskCode() != null && castOther.getRiskCode() != null && this
				.getRiskCode().equals(castOther.getRiskCode())))
				&& ((this.getComCode() == castOther.getComCode()) || (this
						.getComCode() != null
						&& castOther.getComCode() != null && this
						.getComCode().equals(castOther.getComCode())));
	}

	/**
	 * Hash code.
	 * 
	 * @return the int
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getRiskCode() == null ? 0 : this.getRiskCode()
						.hashCode());
		result = 37
				* result
				+ (getComCode() == null ? 0 : this.getComCode()
						.hashCode());
		return result;
	}


}
