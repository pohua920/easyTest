/**
 * 2014-6-13
 */
package com.sinosoft.claim.print.vo;

/**
 * 责任险  委託公證申請單  数据对象 
 * @author 中科軟
 */
public class LiabCommissionedObject {
	/**当前时间*/
	private String currentTime;
	/**當前系統登錄人歸屬單位*/
	private String comName;
	/**立案號碼*/
	private String claimNo;
	/**當前系統登錄人名稱*/
	private String name;
	/**出險時間*/
	private String damageStartDate;
	/**顯示第一聯繫人*/
	private String firstLinkMan;
	/**查勘處理界面中欄位“出險地址”*/
	private String damageAddress;
	/**查勘處理界面中欄位“備註” prpLcheckRemark*/
	private String remark;
	/**查勘處理界面中欄位“預估金額”prpLcheckEstimateLoss。*/
	private String estimateLoss;
	/**公證公司名稱*/
	private String escrowCompany;
	/**Excel中聯繫人姓名+“先生”*/
	private String linkMan;
	/**Excel中公司地址*/
	private String companyAdress;
	/**Excel中電話號碼*/
	private String phoneNumber;
	/**Excel中傳真號碼*/
	private String faxNumber;
	
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	public String getFirstLinkMan() {
		return firstLinkMan;
	}
	public void setFirstLinkMan(String firstLinkMan) {
		this.firstLinkMan = firstLinkMan;
	}
	public String getDamageAddress() {
		return damageAddress;
	}
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEstimateLoss() {
		return estimateLoss;
	}
	public void setEstimateLoss(String estimateLoss) {
		this.estimateLoss = estimateLoss;
	}
	public String getEscrowCompany() {
		return escrowCompany;
	}
	public void setEscrowCompany(String escrowCompany) {
		this.escrowCompany = escrowCompany;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getCompanyAdress() {
		return companyAdress;
	}
	public void setCompanyAdress(String companyAdress) {
		this.companyAdress = companyAdress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
}
