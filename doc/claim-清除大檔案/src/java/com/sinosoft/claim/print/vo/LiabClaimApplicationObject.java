/**
 * 2014-6-13
 */
package com.sinosoft.claim.print.vo;

/**
 * 责任险 理賠申請書 数据对象
 * @author 中科軟
 */
public class LiabClaimApplicationObject {
	/** 大險種 */
	private String className;
	/** 立案號碼 */
	private String claimNo;
	/** 保單號碼 */
	private String policyNo;
	/** 保險起期 */
	private String startDate;
	/** 保險至期 */
	private String endDate;
	/** 第一聯繫人姓名 */
	private String linkman;
	/** 地址 */
	private String address;
	/** 第一聯繫人聯繫電話 */
	private String linkPhone;
	/** 被保險人 */
	private String insuredName;
	/** 出險日期 */
	private String damageStartDate;
	/** 出險地點 */
	private String damageAddress;
	/** 出險摘要 */
	private String damageContent;
	/** 憲警單位 */
	private String policeUnit;
	/** 保险金额 */
	private String amount;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

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

	public String getDamageAddress() {
		return damageAddress;
	}

	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}

	public String getDamageContent() {
		return damageContent;
	}

	public void setDamageContent(String damageContent) {
		this.damageContent = damageContent;
	}

	public String getPoliceUnit() {
		return policeUnit;
	}

	public void setPoliceUnit(String policeUnit) {
		this.policeUnit = policeUnit;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
