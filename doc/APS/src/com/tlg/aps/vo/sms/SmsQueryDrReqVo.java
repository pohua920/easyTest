package com.tlg.aps.vo.sms;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SmsQueryDrReq")
public class SmsQueryDrReqVo {

	private String sysId;
	private String messageId;
	private ArrayList<String> destAddressList;

	@XmlElement(name = "SysId")
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

//	@XmlElement(name = "DestAddress")
//	public String getDestAddress() {
//		return destAddress;
//	}
//
//	public void setDestAddress(String destAddress) {
//		this.destAddress = destAddress;
//	}

	@XmlElement(name = "MessageId")
	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	@XmlElement(name = "DestAddress")
	public ArrayList<String> getDestAddressList() {
		return destAddressList;
	}

	public void setDestAddressList(ArrayList<String> destAddressList) {
		this.destAddressList = destAddressList;
	}

}
