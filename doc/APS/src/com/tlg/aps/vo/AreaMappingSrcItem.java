package com.tlg.aps.vo;

public class AreaMappingSrcItem {
	private String zipCode;	//郵遞區號
	private String district; //xx市(縣)xx區	
	private String areaCode; //地區別
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
