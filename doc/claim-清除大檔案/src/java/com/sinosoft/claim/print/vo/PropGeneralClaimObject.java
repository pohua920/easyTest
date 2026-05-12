package com.sinosoft.claim.print.vo;

/**
 *	非水代查勘委託書 數據對象
 * @author 中科軟
 */
public class PropGeneralClaimObject {
	/**賠案號*/
	private String claimNo;
	/** 收函人*/
	private String handleUnitName;
	/** 險種名稱*/
	private String riskName;
	/** 被保险人名称*/
	private String insuredName;
	/**保单号码*/
	private String policyNo;
	/**出险地址*/
	private String damageAddress;
	/**受损标的*/
	private String itemKind;
	/**出险时间*/
	private String damageDate;
	/**联 系 人*/
	private String contactName;
	/**保险期限*/
	private String insurancePeriod;
	/**联络电话*/
	private String contact;
	/**保险金额*/
	private String sumAmount;
	/**保险公司联系人*/
	private String insuranceLinkMan;
	/**电话*/
	private String phoneNum;
	/**传  真*/
	private String faxNumber;
	/** 列印时间*/
	private String printTime;
	
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getHandleUnitName() {
		return handleUnitName;
	}
	public void setHandleUnitName(String handleUnitName) {
		this.handleUnitName = handleUnitName;
	}
	public String getRiskName() {
		return riskName;
	}
	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getDamageAddress() {
		return damageAddress;
	}
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	public String getItemKind() {
		return itemKind;
	}
	public void setItemKind(String itemKind) {
		this.itemKind = itemKind;
	}
	public String getDamageDate() {
		return damageDate;
	}
	public void setDamageDate(String damageDate) {
		this.damageDate = damageDate;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getInsurancePeriod() {
		return insurancePeriod;
	}
	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getSumAmount() {
		return sumAmount;
	}
	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
	}
	public String getInsuranceLinkMan() {
		return insuranceLinkMan;
	}
	public void setInsuranceLinkMan(String insuranceLinkMan) {
		this.insuranceLinkMan = insuranceLinkMan;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	
}
