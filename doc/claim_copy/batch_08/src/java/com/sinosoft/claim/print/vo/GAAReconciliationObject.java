/**
 * 2014-6-12
 */
package com.sinosoft.claim.print.vo;

/**
 * 工程险  和解書  数据对象 
 * @author 中科軟
 */
public class GAAReconciliationObject {
	private String insuredName;
	private String damageStartDate;
	private String identifyNumber;
	private String roomAddress;
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}
	public String getRoomAddress() {
		return roomAddress;
	}
	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}

}
