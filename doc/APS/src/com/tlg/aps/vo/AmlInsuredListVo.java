package com.tlg.aps.vo;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "insrueds")
public class AmlInsuredListVo {

	private ArrayList<AmlInsuredVo> AmlInsuredList;
	
	/**
	 * 業務號(報價單號、保單號、批單號)
	 */
	private String businessNo;
	
	/**
	 * 主要是以『強 + 任』時可放強制險相關業務號
	 */
	private String extraBusinessNo;
	/**
	 * 程式代號  NEWIMS、B2B、B2C
	 */
	private String appCode;
	/**
	 * 登入的id
	 */
	private String userId;
	/**
	 * 登入的姓名
	 */
	private String userName;
	/**
	 * 作業類型 Q 報價、T 要保、E 批改
	 */
	private String type;
	/**
	 * 險類代碼 A、B、C1、M、F、E
	 */
	private String classCode;
	/**
	 * 險種代號
	 * A01、B01...
	 */
	private String riskCode;
	/**
	 * 公司代號 00
	 */
	private String comCode;
	/**
	 * 通路來源
	 * 10、20、30或40...
	 */
	private String channelType;
	/**
	 * 商品風險等級
	 * H、M、L 各商品需找出對應的風險等級。如果是核心取得等級是1、2、3對應就是H、M、L，若沒有值就是""
	 */
	private String comLevel;
	/**
	 * 保費
	 */
	private String prem;
	
	/**
	 * AML方式 - 1.萊斯 2.metaAML
	 */
	private String amlType;
	
	private String amlUniKey;

	private String resend;

	public ArrayList<AmlInsuredVo> getAmlInsuredList() {
		return AmlInsuredList;
	}

	public void setAmlInsuredList(ArrayList<AmlInsuredVo> amlInsuredList) {
		AmlInsuredList = amlInsuredList;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getComLevel() {
		return comLevel;
	}

	public void setComLevel(String comLevel) {
		this.comLevel = comLevel;
	}

	public String getPrem() {
		return prem;
	}

	public void setPrem(String prem) {
		this.prem = prem;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmlType() {
		return amlType;
	}

	public void setAmlType(String amlType) {
		this.amlType = amlType;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getExtraBusinessNo() {
		return extraBusinessNo;
	}

	public void setExtraBusinessNo(String extraBusinessNo) {
		this.extraBusinessNo = extraBusinessNo;
	}

	public String getAmlUniKey() {
		return amlUniKey;
	}

	public void setAmlUniKey(String amlUniKey) {
		this.amlUniKey = amlUniKey;
	}

	public String getResend() {
		return resend;
	}

	public void setResend(String resend) {
		this.resend = resend;
	}

}
