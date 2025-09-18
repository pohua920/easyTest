package com.tlg.aps.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BankInfoResponseVo {
	
	private String statusCode;
	
	
	private ArrayList<BankInfoVo> bankInfoVoList;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@XmlElement(name="bankInfoVo")
	public ArrayList<BankInfoVo> getBankInfoVoList() {
		return bankInfoVoList;
	}

	public void setBankInfoVoList(ArrayList<BankInfoVo> bankInfoVoList) {
		this.bankInfoVoList = bankInfoVoList;
	}

}
