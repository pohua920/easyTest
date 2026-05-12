package com.sinosoft.claim.message.vo;
import java.io.Serializable;
import java.util.List;
/**
 * 消息发送参数传递DTO
 * @author Administrator
 *
 */
public class MsgSenderDto implements Serializable{

	
	private static final long serialVersionUID = 1L;
	/** 發送者工號*/
	private String sendUserCode;
	/** 發送者姓名*/
	private String sendUserName;
	/** 發送者密碼*/
	private String sendUserPass;
	/** 單證業務類型*/
	private String businessType;
	/** 單證業務號碼*/
	private String businessNo;
	/** 接收用戶*/
	private List<String>   rcverUser;
	/** 單號好對應的業務處理鏈接地址*/
	private String taskLink;
	/** 提交核保 提交上階 下發修改標誌*/
	private String upDownFlag;
	/** 險種代碼*/
	private String riskCode;
	
	public String getUpDownFlag() {
		return upDownFlag;
	}
	public void setUpDownFlag(String upDownFlag) {
		this.upDownFlag = upDownFlag;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getSendUserCode() {
		return sendUserCode;
	}
	public void setSendUserCode(String sendUserCode) {
		this.sendUserCode = sendUserCode;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public String getSendUserPass() {
		return sendUserPass;
	}
	public void setSendUserPass(String sendUserPass) {
		this.sendUserPass = sendUserPass;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public List<String> getRcverUser() {
		return rcverUser;
	}
	public void setRcverUser(List<String> rcverUser) {
		this.rcverUser = rcverUser;
	}
	public String getTaskLink() {
		return taskLink;
	}
	public void setTaskLink(String taskLink) {
		this.taskLink = taskLink;
	}
	
}
