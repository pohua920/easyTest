package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：SALES0015，處理人員：DP0706，需求單編號：SALES0015 銷管系統-APS-保經代分支機構地址正規化
 * @author dp0706
 *
 */
@XmlRootElement
public class AddressFormatDataVo {
	
	/** 地址 */
	private String address;
	
	/** 呼叫的地址 */
	private String appCode;
	
	private String code;
	
	private String msg;
	
	private String addressFormatted;
	
	private String alley1;
	
	private String alley2;
	
	private String city;
	
	private String district;
	private String floor;
	private String formattable;
	private String hit;
	private String isMailBox;
	private String lane;
	private String latitude;
	private String longitude;
	private String neighborhood;
	private String no;
	private String other;
	private String road;
	private String suggestAddress;
	private String type;
	private String zip;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAddressFormatted() {
		return addressFormatted;
	}
	public void setAddressFormatted(String addressFormatted) {
		this.addressFormatted = addressFormatted;
	}
	public String getAlley1() {
		return alley1;
	}
	public void setAlley1(String alley1) {
		this.alley1 = alley1;
	}
	public String getAlley2() {
		return alley2;
	}
	public void setAlley2(String alley2) {
		this.alley2 = alley2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getFormattable() {
		return formattable;
	}
	public void setFormattable(String formattable) {
		this.formattable = formattable;
	}
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getIsMailBox() {
		return isMailBox;
	}
	public void setIsMailBox(String isMailBox) {
		this.isMailBox = isMailBox;
	}
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public String getSuggestAddress() {
		return suggestAddress;
	}
	public void setSuggestAddress(String suggestAddress) {
		this.suggestAddress = suggestAddress;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}

}
