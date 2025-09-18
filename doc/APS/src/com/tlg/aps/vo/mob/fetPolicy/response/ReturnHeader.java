package com.tlg.aps.vo.mob.fetPolicy.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

/** mantis：MOB0001，處理人員：CC009，需求單編號：MOB0001 遠傳手機保險投保&批改作業 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnHeader {
	
	@SerializedName("returnCode")
	private String returnCode;
	
	@SerializedName("returnMesg")
	private String returnMesg;
	
	@SerializedName("legacyCode")
	private String legacyCode;
	
	@SerializedName("processID")
	private String processID;
	
	@SerializedName("processName")
	private String processName;
	
	@SerializedName("reserved1")
	private String reserved1;
	
	@SerializedName("reserved2")
	private String reserved2;
	
	@SerializedName("reserved3")
	private String reserved3;
	
	@Override
	public String toString() {
		return "ReturnHeader [returnCode=" + returnCode + ", returnMesg=" + returnMesg + ", legacyCode=" + legacyCode
				+ ", processID=" + processID + ", processName=" + processName + ", reserved1=" + reserved1
				+ ", reserved2=" + reserved2 + ", reserved3=" + reserved3 + "]";
	}
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMesg() {
		return returnMesg;
	}
	public void setReturnMesg(String returnMesg) {
		this.returnMesg = returnMesg;
	}
	public String getLegacyCode() {
		return legacyCode;
	}
	public void setLegacyCode(String legacyCode) {
		this.legacyCode = legacyCode;
	}
	public String getProcessID() {
		return processID;
	}
	public void setProcessID(String processID) {
		this.processID = processID;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getReserved1() {
		return reserved1;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public String getReserved3() {
		return reserved3;
	}
	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	
}
