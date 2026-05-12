package com.sinosoft.claim.print.vo;

/**
 * 信用卡不便險理賠申請書 數據對象
 * @author 中科軟
 */
public class LiabCardObject {
	/** 賠案號碼 */
	private String claimNo;
	/** 保單號碼 */
	private String policyNo;
	/** 持卡人姓名 */
	private String name;
	/** 持卡人身份證字號 */
	private String id;
	/** 持卡人居住地址 */
	private String adress;
	/** 持卡人電話 */
	private String phone;
	/** 持卡人手機 */
	private String mobile;
	/** 發卡銀行 */
	private String bank;
	/** 卡別 */
	private String cardType;
	/** 信用卡號碼 */
	private String cardCode;
	/** 到期日 */
	private String endDate;
	/** 出險摘要 */
	private String damageContent;
	/** 保險金額 */
	private String amount;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDamageContent() {
		return damageContent;
	}

	public void setDamageContent(String damageContent) {
		this.damageContent = damageContent;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}