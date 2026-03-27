package com.tlg.commons.util.api.soap.aml.entity;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/*
 mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
 新AML串接
 */

@XmlRootElement
public class Insrueds {

	private List<AmlInsured> amlInsuredList = new ArrayList<>(); // 掃描對象資料

	private String appCode;// 呼叫程式的系統代號 中科軟核心系統(承保) - NEWIMS_PRPINS，中科軟核心系統(核保) - NEWIMS_UNDWRT

	private String businessNo;// 報價單號、要保號、批單號、賠案號…

	private String amlUniKey;// 若為空值時，將由中介系統產生unikey。若有傳入則依據傳入值提供給AML

	private String extraBusinessNo;// 額外業務號 主要是以『強 + 任』時可放強制險相關業務號

	private String channelType;// 業務來源 10 - 業務員，20 - 保險經紀人，30 - 保險代理人，40 - 直接業務(網路投保或臨櫃)，理賠業務時可不填寫

	private String classCode;// A - 任意險類，B - 強制險類，C - 責任險，C1 - 傷害暨健康險險類，E - 工程險，F - 火險險類，M - 水險，CLM – 理賠

	private String riskCode;// 險種代碼 A01 任意險 B01 強制險 ***A01B01 強制車險 + 任意車險(特例)***

	private String comCode;// ex : 00-總公司

	private String comLevel;// 商品風險等級 H - 高風險，M - 中風險，L - 低風險

	private String prem;// 保費 理賠業務時可不填寫

	private String type;// 作業類型 Q - 報價，T - 要保，E - 批改，C - 理賠

	private String amlType;// 洗錢系統 1- 萊斯，2- 捷智(請輸入2)

	private String resend;// 重送 1 : 資料變更須重新傳送 0 : 查詢(若無相同Screen_UniKey則會進行送掃)

	public List<AmlInsured> getAmlInsuredList() {
		return amlInsuredList;
	}

	public void setAmlInsuredList(List<AmlInsured> amlInsuredList) {
		this.amlInsuredList = amlInsuredList;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getAmlUniKey() {
		return amlUniKey;
	}

	public void setAmlUniKey(String amlUniKey) {
		this.amlUniKey = amlUniKey;
	}

	public String getExtraBusinessNo() {
		return extraBusinessNo;
	}

	public void setExtraBusinessNo(String extraBusinessNo) {
		this.extraBusinessNo = extraBusinessNo;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
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

	public String getResend() {
		return resend;
	}

	public void setResend(String resend) {
		this.resend = resend;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Insrueds [amlInsuredList=");
		builder.append(amlInsuredList);
		builder.append(", appCode=");
		builder.append(appCode);
		builder.append(", businessNo=");
		builder.append(businessNo);
		builder.append(", amlUniKey=");
		builder.append(amlUniKey);
		builder.append(", extraBusinessNo=");
		builder.append(extraBusinessNo);
		builder.append(", channelType=");
		builder.append(channelType);
		builder.append(", classCode=");
		builder.append(classCode);
		builder.append(", riskCode=");
		builder.append(riskCode);
		builder.append(", comCode=");
		builder.append(comCode);
		builder.append(", comLevel=");
		builder.append(comLevel);
		builder.append(", prem=");
		builder.append(prem);
		builder.append(", type=");
		builder.append(type);
		builder.append(", amlType=");
		builder.append(amlType);
		builder.append(", resend=");
		builder.append(resend);
		builder.append("]");
		return builder.toString();
	}

}
