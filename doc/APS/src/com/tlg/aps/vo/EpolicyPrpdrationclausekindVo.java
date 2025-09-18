package com.tlg.aps.vo;

/** mantis：，處理人員：BJ016，需求單編號：行動裝置險電子保單發送 */
public class EpolicyPrpdrationclausekindVo {
	private String kindcode;
	private String maxIndemnify;
	private String deductible;
	private String unitPremium;
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 電子保單 START*/
	private String basePremium;
	
	public String getBasePremium() {
		return basePremium;
	}
	public void setBasePremium(String basePremium) {
		this.basePremium = basePremium;
	}
	/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 電子保單 EN*/
	public String getKindcode() {
		return kindcode;
	}
	public void setKindcode(String kindcode) {
		this.kindcode = kindcode;
	}
	public String getMaxIndemnify() {
		return maxIndemnify;
	}
	public void setMaxIndemnify(String maxIndemnify) {
		this.maxIndemnify = maxIndemnify;
	}
	public String getDeductible() {
		return deductible;
	}
	public void setDeductible(String deductible) {
		this.deductible = deductible;
	}
	public String getUnitPremium() {
		return unitPremium;
	}
	public void setUnitPremium(String unitPremium) {
		this.unitPremium = unitPremium;
	}


	
}
