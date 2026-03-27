package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;

/**
 * 險種大類數據VO.
 */
public class RiskCategoryCodeVo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = RiskCategoryCodeVo.class.hashCode();

	/** 屬性險種大類. */
	private String riskCategory = null;

	/** 屬性險種大類名稱. */
	private String riskCategory_name = "";

	/** 屬性險種代碼. */
	private String riskCode = null;

	/** 屬性險種名稱. */
	private String riskName = null;

	/**
	 * 構造方法.
	 */
	public RiskCategoryCodeVo() {
	}

	/**
	 * 獲取屬性險種大類.
	 * 
	 * @return 屬性險種大類的值
	 */
	public String getRiskCategory() {
		return riskCategory;
	}

	/**
	 * 設置屬性險種大類.
	 * 
	 * @param riskCategory
	 *            待設置的險種大類的值
	 */
	public void setRiskCategory(String riskCategory) {
		this.riskCategory = riskCategory;
	}

	/**
	 * 獲取屬性險種大類名稱.
	 * 
	 * @return the 屬性險種大類名稱
	 */
	public String getRiskCategory_name() {
		return riskCategory_name;
	}

	/**
	 * 設置屬性險種大類名稱.
	 * 
	 * @param riskCategory_name
	 *            the new 屬性險種大類名稱
	 */
	public void setRiskCategory_name(String riskCategory_name) {
		this.riskCategory_name = riskCategory_name;
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
	 * 獲取屬性險種名稱.
	 * 
	 * @return 屬性險種名稱的值
	 */
	public String getRiskName() {
		return riskName;
	}

	/**
	 * 設置屬性險種名稱.
	 * 
	 * @param riskName
	 *            待設置的險種名稱的值
	 */
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
}