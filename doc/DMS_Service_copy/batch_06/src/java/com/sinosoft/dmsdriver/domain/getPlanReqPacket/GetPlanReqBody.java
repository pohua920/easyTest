package com.sinosoft.dmsdriver.domain.getPlanReqPacket;

public class GetPlanReqBody {

	private static final long serialVersionUID = 1L;

	public void validate() throws Exception {
	}
	
	private String rationCode;
	
	private String riskCode;
	
	private String[] comCodes;//当前机构及其上级机构

	private String rationType;//方案类型

	public String getRationCode() {
		return rationCode;
	}

	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String[] getComCodes() {
		return comCodes;
	}

	public void setComCodes(String[] comCodes) {
		this.comCodes = comCodes;
	}

	public String getRationType() {
		return rationType;
	}

	public void setRationType(String rationType) {
		this.rationType = rationType;
	}

}
