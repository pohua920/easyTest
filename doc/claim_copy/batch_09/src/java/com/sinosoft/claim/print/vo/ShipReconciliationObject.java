package com.sinosoft.claim.print.vo;

/**
 * 和解書數據對象
 * @author Sinosoft
 */

public class ShipReconciliationObject {
	/** 被保險人名稱 */
	private String insuredName;
	/** 被保險人地址 */
	private String insuredAddress;
	/** 被保險人统一编号 */
	private String insuredIdentifyNumber;
	/** 出险日期 */
	private String damageTime;
	/** 出险小时 */
	private String damageHour;
	/** 出险分钟 */
	private String damageMinute;
	
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getInsuredAddress() {
		return insuredAddress;
	}
	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}
	public String getInsuredIdentifyNumber() {
		return insuredIdentifyNumber;
	}
	public void setInsuredIdentifyNumber(String insuredIdentifyNumber) {
		this.insuredIdentifyNumber = insuredIdentifyNumber;
	}
	public String getDamageTime() {
		return damageTime;
	}
	public void setDamageTime(String damageTime) {
		this.damageTime = damageTime;
	}
	public String getDamageHour() {
		return damageHour;
	}
	public void setDamageHour(String damageHour) {
		this.damageHour = damageHour;
	}
	public String getDamageMinute() {
		return damageMinute;
	}
	public void setDamageMinute(String damageMinute) {
		this.damageMinute = damageMinute;
	}
}
