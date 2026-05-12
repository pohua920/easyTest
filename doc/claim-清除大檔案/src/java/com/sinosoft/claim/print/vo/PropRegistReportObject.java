package com.sinosoft.claim.print.vo;

/**
 * 火險出險報告 數據對象
 * @author 中科軟
 *
 */
public class PropRegistReportObject {
	/**賠案號碼*/
	private String claimNo;
	/**被保險人*/
	private String insuredName;
	/**保單號碼*/
	private String policyNo;
	/**批單號碼*/
	private String endorseNo;
	/**保險期間*/
	private String insurancePeriod;
	/**出險地點*/
	private String damageAddress;
	/**出險原因*/
	private String damageReason;
	/**出險標的*/
	private String itemKind;
	/**索賠金額*/
	private String claimAmount;
	/**備案時間*/
	private String registDate;
	/**出險時間*/
	private String damageDate;
	/**連絡人*/
	private String contactName;
	/**聯繫方式*/
	private String contact;
	/**本案出險原因、經過以及損失處理情況；估計全案損失並填寫查勘人意見*/
	private String registProcess;
	/**查勘時間*/
	private String checkDate;
	/**查勘地點*/
	private String checkAddress;
	/**查 勘 人*/
	private String checker;
	/**列印時間*/
	private String printTime;
	
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	public String getEndorseNo() {
		return endorseNo;
	}
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}
	public String getInsurancePeriod() {
		return insurancePeriod;
	}
	public void setInsurancePeriod(String insurancePeriod) {
		this.insurancePeriod = insurancePeriod;
	}
	public String getDamageAddress() {
		return damageAddress;
	}
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	public String getDamageReason() {
		return damageReason;
	}
	public void setDamageReason(String damageReason) {
		this.damageReason = damageReason;
	}
	public String getItemKind() {
		return itemKind;
	}
	public void setItemKind(String itemKind) {
		this.itemKind = itemKind;
	}
	public String getClaimAmount() {
		return claimAmount;
	}
	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getRegistProcess() {
		return registProcess;
	}
	public void setRegistProcess(String registProcess) {
		this.registProcess = registProcess;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckAddress() {
		return checkAddress;
	}
	public void setCheckAddress(String checkAddress) {
		this.checkAddress = checkAddress;
	}
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	public String getPrintTime() {
		return printTime;
	}
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	
}
