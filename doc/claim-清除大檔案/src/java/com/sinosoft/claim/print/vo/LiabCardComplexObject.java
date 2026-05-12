package com.sinosoft.claim.print.vo;

/**
 * 信用卡綜合保險全球購物理賠申請書 數據對象
 * @author 中科軟
 */
public class LiabCardComplexObject {
	/** 賠案號碼 */
	private String claimNo;
	/** 保單號碼 */
	private String policyNo;
	/** 持卡人姓名 */
	private String name;
	/** 持卡人居住地址 */
	private String adress;
	/** 持卡人電話 */
	private String phone;
	/** 信用卡卡號 */
	private String cardCode;
	/** 到期日 */
	private String endDate;
	/** 出險時間 */
	private String damageDate;
	/** 出險摘要 */
	private String damageContent;
	/** 保險金額 */
	private String amount;

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

	public String getDamageDate() {
		return damageDate;
	}

	public void setDamageDate(String damageDate) {
		this.damageDate = damageDate;
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