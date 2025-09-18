package com.tlg.aps.vo.sms;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SmsQueryDrRes")
public class SmsQueryDrResVo {

	
	private String ResultCode;
	private String ResultText;
	private ArrayList<SmsReceiptVo> receiptList;

	@XmlElement(name="ResultCode")
	public String getResultCode() {
		return ResultCode;
	}

	public void setResultCode(String resultCode) {
		ResultCode = resultCode;
	}

	@XmlElement(name="ResultText")
	public String getResultText() {
		return ResultText;
	}

	public void setResultText(String resultText) {
		ResultText = resultText;
	}
	@XmlElement(name="Receipt")
	public ArrayList<SmsReceiptVo> getReceiptList() {
		return receiptList;
	}

	public void setReceiptList(ArrayList<SmsReceiptVo> receiptList) {
		this.receiptList = receiptList;
	}

}
