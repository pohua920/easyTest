package com.sinosoft.claim.print.vo;

/**
 * 信用卡附加旅平險理賠申請書 數據對象
 * @author 中科軟
 */
public class LiabCardAppendObject {
	/** 賠案號碼 */
	private String claimNo;
	/** 保單號碼 */
	private String policyNo;
	/** 要保人 */
	private String appliName;
	/** 卡別 */
	private String cardType;
	/** 信用卡號碼 */
	private String cardCode;
	/** 持卡人姓名 */
	private String name;
	/** 持卡人身份證字號 */
	private String id;
	/** 持卡人居住地址 */
	private String adress;
	/** 持卡人電話 */
	private String phone;
	/** 出險地點 */
	private String damageAdress;
	/** 出險時間 */
	private String damageDate;
	/** 出險摘要 */
	private String damageContent;
	/** 就診醫院 */
	private String hospital;

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

	public String getAppliName() {
		return appliName;
	}

	public void setAppliName(String appliName) {
		this.appliName = appliName;
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

	public String getDamageAdress() {
		return damageAdress;
	}

	public void setDamageAdress(String damageAdress) {
		this.damageAdress = damageAdress;
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

	public String getHospital() {
		return hospital;
	}

	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
}