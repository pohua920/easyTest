package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class GetInsCardVo {
	
	private String userId = "INA18099";
	private String passWord = "tlg27938888";
	/**
	 * 檢查碼
	 */
	protected String checkValue;

	/**
	 * 強制保險證號
	 */
	private String insuredCardNo;

	/**
	 * 車牌號碼
	 */
	private String plateNo;

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public String getInsuredCardNo() {
		return insuredCardNo;
	}

	public void setInsuredCardNo(String insuredCardNo) {
		this.insuredCardNo = insuredCardNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
