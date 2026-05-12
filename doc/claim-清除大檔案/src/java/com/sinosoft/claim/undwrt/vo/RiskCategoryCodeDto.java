package com.sinosoft.claim.undwrt.vo;

import java.io.Serializable;

/**
 * 險種基礎對象
 * @author 中科软
 */
public class RiskCategoryCodeDto implements Serializable {
	private static final long serialVersionUID = RiskCategoryCodeDto.class.hashCode();
	/** 險類代碼*/
	private String riskCategory = null;
	/** 險種代碼*/
	private String riskCode = null;
	/** 險種名稱*/
	private String riskName = null;

	public String getRiskCategory() {
		return riskCategory;
	}

	public void setRiskCategory(String riskCategory) {
		this.riskCategory = riskCategory;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
}