package com.tlg.aps.vo.sms;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ShortSmsSubmitReq")
public class ShortSmsSubmitReqVo {

	private String sysId;
	private String srcAddress;
	private ArrayList<String> destAddressList;
	private String smsBody;
	private String expiryMinutes;
	private String flashFlag;
	private String drFlag;
	private String firstFailFlag;

	@XmlElement(name="SysId")
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	@XmlElement(name="SrcAddress")
	public String getSrcAddress() {
		return srcAddress;
	}

	public void setSrcAddress(String srcAddress) {
		this.srcAddress = srcAddress;
	}

	@XmlElement(name="SmsBody")
	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	@XmlElement(name="ExpiryMinutes")
	public String getExpiryMinutes() {
		return expiryMinutes;
	}

	public void setExpiryMinutes(String expiryMinutes) {
		this.expiryMinutes = expiryMinutes;
	}

	@XmlElement(name="FlashFlag")
	public String getFlashFlag() {
		return flashFlag;
	}

	public void setFlashFlag(String flashFlag) {
		this.flashFlag = flashFlag;
	}

	@XmlElement(name="DrFlag")
	public String getDrFlag() {
		return drFlag;
	}

	public void setDrFlag(String drFlag) {
		this.drFlag = drFlag;
	}

	@XmlElement(name="FirstFailFlag")
	public String getFirstFailFlag() {
		return firstFailFlag;
	}

	public void setFirstFailFlag(String firstFailFlag) {
		this.firstFailFlag = firstFailFlag;
	}

	@XmlElement(name="DestAddress")
	public ArrayList<String> getDestAddressList() {
		return destAddressList;
	}

	public void setDestAddressList(ArrayList<String> destAddressList) {
		this.destAddressList = destAddressList;
	}

}
