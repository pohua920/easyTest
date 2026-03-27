package com.sinosoft.undwrt.undwrtRule.vo;

/**
 * 業務數據的父類
 */

public class BusinessData {

	/** 屬性險類代碼. */
	String classCode = "";

	/** 屬性險種代碼. */
	String riskCode = "0000";

	/** 屬性險別代碼. */
	String riskKind = "";
	
	/** 屬性歸屬機構代碼. */
	String comCode = "";

	/**
	 * 獲取屬性歸屬機構代碼.
	 * 
	 * @return the 屬性歸屬機構代碼
	 */
	public String getComCode() {
		return comCode;
	}

	/**
	 * 設置屬性歸屬機構代碼.
	 * 
	 * @param comCode
	 *            the new 屬性歸屬機構代碼
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 獲取屬性險類代碼.
	 * 
	 * @return 屬性險類代碼的值
	 */
	public String getClassCode() {
		return classCode;
	}

	/**
	 * 設置屬性險類代碼.
	 * 
	 * @param classCode
	 *            待設置的險類代碼的值
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
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
	 * 獲取屬性險別代碼.
	 * 
	 * @return 屬性險別代碼的值
	 */
	public String getRiskKind() {
		return riskKind;
	}

	/**
	 * 設置屬性險別代碼.
	 * 
	 * @param riskKind
	 *            待設置的險別代碼的值
	 */
	public void setRiskKind(String riskKind) {
		this.riskKind = riskKind;
	}
}