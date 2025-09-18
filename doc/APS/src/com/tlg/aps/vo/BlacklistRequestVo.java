package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BlacklistRequestVo {
	
	private String riskCodeNotNull;
	private String identifyNumber;
	
	public String getRiskCodeNotNull() {
		return riskCodeNotNull;
	}
	public void setRiskCodeNotNull(String riskCodeNotNull) {
		this.riskCodeNotNull = riskCodeNotNull;
	}
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

}
