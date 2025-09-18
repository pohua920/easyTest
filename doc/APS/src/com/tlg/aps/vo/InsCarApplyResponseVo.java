package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InsCarApplyResponseVo {
	
	/**
	 * 強制保險證號
	 */
	private String insuredCardNo;
	/**
	 * 被保險人ID
	 */
	private String ownerId;
	/**
	 * 車牌號碼
	 */
	private String plateNo;
	/**
	 * 查詢編號
	 */
	private String cardSelNo;
	/**
	 * 查詢日期
	 */
	private String cardSelDate;
	/**
	 * 保費金額
	 */
	private String compPremium;
	/**
	 * 電子保證
	 * 保險證PDF byte[] 轉 base64
	 */
	private String insuredCard;
	/**
	 * 回覆代碼
	 */
	private String resultCode;
	/**
	 * 回覆訊息
	 */
	private String resultMsg;

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

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public String getCardSelNo() {
		return cardSelNo;
	}

	public void setCardSelNo(String cardSelNo) {
		this.cardSelNo = cardSelNo;
	}

	public String getCardSelDate() {
		return cardSelDate;
	}

	public void setCardSelDate(String cardSelDate) {
		this.cardSelDate = cardSelDate;
	}

	public String getCompPremium() {
		return compPremium;
	}

	public void setCompPremium(String compPremium) {
		this.compPremium = compPremium;
	}

	public String getInsuredCard() {
		return insuredCard;
	}

	public void setInsuredCard(String insuredCard) {
		this.insuredCard = insuredCard;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

}
