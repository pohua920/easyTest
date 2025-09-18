package com.tlg.aps.vo;

public class VerifyIdVo {
	/**
	 * @param code : S0000 – 成功;	E0001 – 基本檢核錯誤;	E0002 – SystemException;	E0003 – Exception;	E0010 – 證號不符規則
	 * */
	private String code;//S0000 – 成功;	E0001 – 基本檢核錯誤;	E0002 – SystemException;	E0003 – Exception;	E0010 – 證號不符規則
	private String msg;
	private String id;
	private String insuredType;//1 - 自然人; 	2 - 法人
	private String gender;//1 - 男;	2 - 女;	3 - 法人
	private String identifyType;//01 - 身分證;	05 - 居留證;	60 - 統一編號

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInsuredType() {
		return insuredType;
	}
	public void setInsuredType(String insuredType) {
		this.insuredType = insuredType;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdentifyType() {
		return identifyType;
	}
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}
}
