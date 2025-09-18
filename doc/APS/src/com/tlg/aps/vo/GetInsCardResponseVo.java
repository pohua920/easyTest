package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class GetInsCardResponseVo {

	/**
	 * 強制保險證號
	 */
	private String insuredCardNo;
	/**
	 * 車牌號碼
	 */
	private String plateNo;
	/**
	 * 電子保證-保險證PDFbyte[]轉 base64
	 */
	private String insuredCard;
	/**
	 * 回覆代碼
	 * S0000成功
	 * 其他請參照「訊息回覆說明表」
	 */
	private String resultCode;
	private String resultMsg;

	public String getInsuredCardNo() {
		return insuredCardNo;
	}

	public void setInsuredCardNo(String insuredCardNo) {
		this.insuredCardNo = insuredCardNo;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
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

	public String getInsuredCard() {
		return insuredCard;
	}

	public void setInsuredCard(String insuredCard) {
		this.insuredCard = insuredCard;
	}

}
