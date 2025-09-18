package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;
/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 */
@XmlRootElement(name ="prpinsAgentQueryRequestVo" )
public class PrpinsAgentReqVo {

	/** 查詢類別 ，必填，業務員查詢:1  服務人員查詢：2*/
	private String type;
	/** 查詢身份編號 ，必填，業務員登錄證字號/服務人員員編*/
	private String identifyNumber;
	/** 業務員所屬通路別 ，非必填，服務人員免填*/
	private String channelType;
	/** 險別代碼 ，非必填，車險任A  車險強B  責任險C</br>傷害險C1   工程險E  水險M  服務人員免填*/
	private String classCode;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIdentifyNumber() {
		return identifyNumber;
	}
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
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
	
}
