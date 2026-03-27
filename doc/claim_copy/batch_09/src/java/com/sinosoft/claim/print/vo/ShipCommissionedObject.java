package com.sinosoft.claim.print.vo;

/**
 * 委託公證申請單數據對象
 * @author Sinosoft
 */

public class ShipCommissionedObject {
	/** 委托单位 */
	private String comName;
	/** 属性报案号 */
	private String registNo;
	/** 保单号码 */
	private String policyNo;
	/** 出险时间 */
	private String damageTime;
	/** 被保險人名稱 */
	private String insuredName;
	/** 联系人名稱 */
	private String linkerName;
	/** 联系人电话 */
	private String phoneNumber;
	/** 属性出险地点 */
	private String damageAddress;
	/** 属性出险原因 */
	private String damageName;
	/** 属性受损标的 */
	private String lossName;
	/** 属性估损金额 */
	private String estimateLoss;
	
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getDamageTime() {
		return damageTime;
	}
	public void setDamageTime(String damageTime) {
		this.damageTime = damageTime;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getLinkerName() {
		return linkerName;
	}
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDamageAddress() {
		return damageAddress;
	}
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	public String getDamageName() {
		return damageName;
	}
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}
	public String getLossName() {
		return lossName;
	}
	public void setLossName(String lossName) {
		this.lossName = lossName;
	}
	public String getEstimateLoss() {
		return estimateLoss;
	}
	public void setEstimateLoss(String estimateLoss) {
		this.estimateLoss = estimateLoss;
	}
}
