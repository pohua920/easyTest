package com.tlg.aps.vo;

public class PhoneMappingSrcItem {
	private String areaCode;//區號AreaCode
	private String tel;	//洽詢電話PhoneNumber
	private String ext; //分機Extension
	private String fox; //傳真電話FoxPhone
	private String address; //地址Address
	
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreCode(String areCode) {
		this.areaCode = areCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFox() {
		return fox;
	}
	public void setFox(String fox) {
		this.fox = fox;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
