package com.tlg.aps.vo.sms;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SmsBatchDest")
public class SmsBatchDestVo {

	private String destAddress;
	private String smsBody;

	@XmlElement(name="DestAddress")
	public String getDestAddress() {
		return destAddress;
	}

	public void setDestAddress(String destAddress) {
		this.destAddress = destAddress;
	}

	@XmlElement(name="SmsBody")
	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

}
