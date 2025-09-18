package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BankInfoRequestVo {
	
	private String sourceType;
	private String bankCode;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

}
