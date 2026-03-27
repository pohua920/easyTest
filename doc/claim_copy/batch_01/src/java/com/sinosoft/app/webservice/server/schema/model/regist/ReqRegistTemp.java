package com.sinosoft.app.webservice.server.schema.model.regist;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.sinosoft.app.webservice.server.schema.model.regist.vo.ClaimPrpLregistVo;

/**
 *  mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 *  接外部-無須產出
 */
@XmlRootElement(name="reqRegistQuery")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqRegistTemp {
	/** 保單號(任 & 其他) **/
	private String policyNo;
	/** 保單號(強) **/
	private String mainPolicyNo;
	/** 查詢條件:ADD新增　**/
	private String editType;
	/** **/
	private String insuredCode;
	/** **/
	private String insuredName;
	/** 登入的使用者代碼**/
	private String userCode;
	/** 操作時間 */
	private String operateDate = "";

//	private ClaimPrpLregistVo claimPrpLregistVo;

	private String sendJson;
	private String rtnJson;
	
	/** 備案號 **/
	private String registNo;
	/** 查詢條件:出險日期 */
	private String damageDate;//2002-01-05
	/** 出險小時:出險小時 */
	private String damageHour;
	
	
    public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public String getDamageDate() {
		return damageDate;
	}
	public void setDamageDate(String damageDate) {
		this.damageDate = damageDate;
	}
	public String getDamageHour() {
		return damageHour;
	}
	public void setDamageHour(String damageHour) {
		this.damageHour = damageHour;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public String getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getRtnJson() {
		return rtnJson;
	}
	public void setRtnJson(String rtnJson) {
		this.rtnJson = rtnJson;
	}
	public String getSendJson() {
		return sendJson;
	}
	public void setSendJson(String sendJson) {
		this.sendJson = sendJson;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
//	public ClaimPrpLregistVo getClaimPrpLregistVo() {
//		return claimPrpLregistVo;
//	}
//	public void setClaimPrpLregistVo(ClaimPrpLregistVo claimPrpLregistVo) {
//		this.claimPrpLregistVo = claimPrpLregistVo;
//	}
	public String getMainPolicyNo() {
		return mainPolicyNo;
	}
	public void setMainPolicyNo(String mainPolicyNo) {
		this.mainPolicyNo = mainPolicyNo;
	}
	
	
}
