package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InsCarApplyVo {

	private String userId = "INA18099";
	private String passWord = "tlg27938888";
	/**
	 * 檢查碼
	 */
	protected String checkValue;

	/**
	 * 強制保險證號
	 */
	private String insuredCardNo;

	/**
	 * 被保險人ID
	 */
	private String ownerId;

	/**
	 * 被保險人姓名
	 */
	private String ownerName;

	/**
	 * 被保險人姓名圖檔
	 */
	private byte[] ownerNamePic;

	/**
	 * 保險生效日期 YYYYMMDDHH
	 */
	private String compBeginDate;

	/**
	 * 保險終止日期 YYYYMMDDHH
	 */
	private String compEndDate;

	/**
	 * 車種別
	 */
	private String carriageType;

	/**
	 * 車輛種類
	 */
	private String insuredCarType;

	/**
	 * 原始發照日期 YYYYMMDD
	 */
	private String orgIssueDate;

	/**
	 * 廠牌型式
	 */
	private String brandModel;

	/**
	 * 廠牌型式名稱
	 */
	private String brandModelName;

	/**
	 * 排氣量/馬力數
	 */
	private String cylinder;

	/**
	 * 引擎/車身號碼
	 */
	private String engineNo;

	/**
	 * 車牌號碼
	 */
	private String plateNo;

	/**
	 * 係數等級(本期)
	 */
	private String feeGrade;

	/**
	 * 產險公司代碼
	 */
	private String compNo;

	/**
	 * 簽單日期 YYYYMMDD
	 */
	private String signDate;

	/**
	 * 保費金額
	 */
	private String compPremium;

	/**
	 * 是否須同步至公路監理機構
	 */
	private String transAngency;

	/**
	 * 通路別代號
	 * 
	 * 請參照統計規程通路別代號 10：保險業務員 不含 A1~A9) A1：汽車代檢廠代收保費機構 A2：汽 機車經銷商代收保費機構
	 * A9：其他代收保費機構 20：保險代理人 21：保險代理人 網路投保 30：保險經紀人 31：保險經紀人 網路投保 40：直接業務 41：直接業務
	 * 網路投保 90：其他通路
	 */
	private String tradingKind;
	/**
	 * 通路統編
	 */
	private String tradingUnitedNo;
	/**
	 * 通路名稱
	 */
	private String tradingName;
	/**
	 * 產險證登錄證號
	 */
	private String producerRegNo;

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public String getInsuredCardNo() {
		return insuredCardNo;
	}

	public void setInsuredCardNo(String insuredCardNo) {
		this.insuredCardNo = insuredCardNo;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public byte[] getOwnerNamePic() {
		return ownerNamePic;
	}

	public void setOwnerNamePic(byte[] ownerNamePic) {
		this.ownerNamePic = ownerNamePic;
	}

	public String getCompBeginDate() {
		return compBeginDate;
	}

	public void setCompBeginDate(String compBeginDate) {
		this.compBeginDate = compBeginDate;
	}

	public String getCompEndDate() {
		return compEndDate;
	}

	public void setCompEndDate(String compEndDate) {
		this.compEndDate = compEndDate;
	}

	public String getCarriageType() {
		return carriageType;
	}

	public void setCarriageType(String carriageType) {
		this.carriageType = carriageType;
	}

	public String getInsuredCarType() {
		return insuredCarType;
	}

	public void setInsuredCarType(String insuredCarType) {
		this.insuredCarType = insuredCarType;
	}

	public String getOrgIssueDate() {
		return orgIssueDate;
	}

	public void setOrgIssueDate(String orgIssueDate) {
		this.orgIssueDate = orgIssueDate;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public String getCylinder() {
		return cylinder;
	}

	public void setCylinder(String cylinder) {
		this.cylinder = cylinder;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getFeeGrade() {
		return feeGrade;
	}

	public void setFeeGrade(String feeGrade) {
		this.feeGrade = feeGrade;
	}

	public String getCompNo() {
		return compNo;
	}

	public void setCompNo(String compNo) {
		this.compNo = compNo;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getCompPremium() {
		return compPremium;
	}

	public void setCompPremium(String compPremium) {
		this.compPremium = compPremium;
	}

	public String getTransAngency() {
		return transAngency;
	}

	public void setTransAngency(String transAngency) {
		this.transAngency = transAngency;
	}

	public static void main(String args[]) {
		String carfaccde = "ABC";
		int length = 8;
		String PAD_STRING = "00000000";
		carfaccde += PAD_STRING.substring(carfaccde.length());
		System.out.println(carfaccde);

		String abc = "9012";
		System.out.println(String.valueOf(abc).substring(0, 2));
		System.out.println(Integer
				.parseInt(String.valueOf(abc).substring(0, 2))
				+ 1911
				+ String.valueOf(abc).substring(2, 4) + "01");
		String lincenseYM = "12019";
		System.out.println(Integer.parseInt(String.valueOf(lincenseYM)
				.substring(0, 3))
				+ 1911
				+ String.valueOf(lincenseYM).substring(3, 5) + "01");
	}

	public String getTradingKind() {
		return tradingKind;
	}

	public void setTradingKind(String tradingKind) {
		this.tradingKind = tradingKind;
	}

	public String getTradingUnitedNo() {
		return tradingUnitedNo;
	}

	public void setTradingUnitedNo(String tradingUnitedNo) {
		this.tradingUnitedNo = tradingUnitedNo;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getProducerRegNo() {
		return producerRegNo;
	}

	public void setProducerRegNo(String producerRegNo) {
		this.producerRegNo = producerRegNo;
	}

	public String getBrandModelName() {
		return brandModelName;
	}

	public void setBrandModelName(String brandModelName) {
		this.brandModelName = brandModelName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
}
