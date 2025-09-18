package com.tlg.aps.vo;

import javax.xml.bind.annotation.XmlRootElement;

/** mantis：MOB0031，處理人員：CE035，需求單編號：MOB0031 行動裝置險estore網投案開發 銷管系統業務員資料查詢 */
@XmlRootElement(name="prpinsAgentQueryResponseVo")
public class PrpinsAgentRespVo {

	/** 代理人/經紀人/壽險單位代號 */
	private String agentCode;
	/** 代理人/經紀人/壽險單位名稱 */
	private String agentName;
	/** 業務來源 */
	private String businessNature;
	/** 通路別 */
	private String channelType;
	/** 業務員歸屬單位 */
	private String comCode;
	/** 單位代號 */
	private String extraComCode;
	/** 單位中文 */
	private String extraComName;
	/** 業務員登錄證字號*/
	private String handlerCode;
	/** 業務員姓名 */
	private String handlerName;
	/** 介紹人ID */
	private String introducerID;
	/** 介紹人姓名 */
	private String introducerName;
	/** 核心服務人員員編 */
	private String userCode;
	/** 核心服務人員姓名 */
	private String userName;
	
	/** 執行結果， Y=成功 N=失敗*/
	private String pResult;
	/** 錯誤訊息， pResult=N時，此欄位才會有值*/
	private String errMsg;
	@Override
	public String toString() {
		return "PrpinsAgentRespVo [agentCode=" + agentCode + ", agentName=" + agentName + ", businessNature="
				+ businessNature + ", channelType=" + channelType + ", comCode=" + comCode + ", extraComCode="
				+ extraComCode + ", extraComName=" + extraComName + ", handlerCode=" + handlerCode + ", handlerName="
				+ handlerName + ", introducerID=" + introducerID + ", introducerName=" + introducerName + ", userCode="
				+ userCode + ", userName=" + userName + ", pResult=" + pResult + ", errMsg=" + errMsg + "]";
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getBusinessNature() {
		return businessNature;
	}
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getComCode() {
		return comCode;
	}
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	public String getExtraComCode() {
		return extraComCode;
	}
	public void setExtraComCode(String extraComCode) {
		this.extraComCode = extraComCode;
	}
	public String getExtraComName() {
		return extraComName;
	}
	public void setExtraComName(String extraComName) {
		this.extraComName = extraComName;
	}
	public String getHandlerCode() {
		return handlerCode;
	}
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}
	public String getHandlerName() {
		return handlerName;
	}
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	public String getIntroducerID() {
		return introducerID;
	}
	public void setIntroducerID(String introducerID) {
		this.introducerID = introducerID;
	}
	public String getIntroducerName() {
		return introducerName;
	}
	public void setIntroducerName(String introducerName) {
		this.introducerName = introducerName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getpResult() {
		return pResult;
	}
	public void setpResult(String pResult) {
		this.pResult = pResult;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
