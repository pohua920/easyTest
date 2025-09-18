package com.tlg.aps.vo.sms;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SmsBatchSubmitReq")
public class SmsBatchSubmitReq {

	private String sysId;
	private String srcAddress;
	private ArrayList<SmsBatchDestVo> smsBatchDestList;
	private String schTime;
	private String expiryMinutes;
	private String longSmsFlag;
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

	@XmlElement(name = "SmsBatchDest")
	public ArrayList<SmsBatchDestVo> getSmsBatchDestList() {
		return smsBatchDestList;
	}

	public void setSmsBatchDestList(ArrayList<SmsBatchDestVo> smsBatchDestList) {
		this.smsBatchDestList = smsBatchDestList;
	}

	@XmlElement(name="SchTime")
	public String getSchTime() {
		return schTime;
	}

	public void setSchTime(String schTime) {
		this.schTime = schTime;
	}

	@XmlElement(name="ExpiryMinutes")
	public String getExpiryMinutes() {
		return expiryMinutes;
	}

	public void setExpiryMinutes(String expiryMinutes) {
		this.expiryMinutes = expiryMinutes;
	}

	@XmlElement(name="LongSmsFlag")
	public String getLongSmsFlag() {
		return longSmsFlag;
	}

	public void setLongSmsFlag(String longSmsFlag) {
		this.longSmsFlag = longSmsFlag;
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

	public String getFirstFailFlag() {
		return firstFailFlag;
	}

	public void setFirstFailFlag(String firstFailFlag) {
		this.firstFailFlag = firstFailFlag;
	}
}
