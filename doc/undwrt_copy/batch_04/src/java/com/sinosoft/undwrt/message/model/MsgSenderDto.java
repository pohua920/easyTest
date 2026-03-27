package com.sinosoft.undwrt.message.model;

import java.io.Serializable;
import java.util.List;

/**
 * 消息发送参数传递DTO.
 * 
 * @author Administrator
 */
public class MsgSenderDto implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 屬性The sinosoft send user code. */
	private String sendUserCode;
	
	/** 屬性The sinosoft send user name. */
	private String sendUserName;
	
	/** 屬性The sinosoft send user pass. */
	private String sendUserPass;
	
	/** 屬性業務類型. */
	private String businessType;
	
	/** 屬性業務號. */
	private String businessNo;
	
	/** 屬性The sinosoft rcver user. */
	private List   rcverUser;
	
	/** 屬性The sinosoft task link. */
	private String taskLink;
	
	/** 屬性The sinosoft up down flag. */
	private String upDownFlag;
	
	/** 屬性險種代碼. */
	private String riskCode;
	
	/**
	 * 獲取屬性險種代碼.
	 * 
	 * @return 屬性險種代碼的值
	 */
	public String getRiskCode() {
		return riskCode;
	}
	
	/**
	 * 設置屬性險種代碼.
	 * 
	 * @param riskCode
	 *            待設置的險種代碼的值
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	
	/**
	 * 獲取屬性the sinosoft up down flag.
	 * 
	 * @return 屬性the sinosoft up down flag的值
	 */
	public String getUpDownFlag() {
		return upDownFlag;
	}
	
	/**
	 * 設置屬性the sinosoft up down flag.
	 * 
	 * @param upDownFlag
	 *            待設置的the sinosoft up down flag的值
	 */
	public void setUpDownFlag(String upDownFlag) {
		this.upDownFlag = upDownFlag;
	}
	
	/**
	 * 獲取屬性the sinosoft send user code.
	 * 
	 * @return 屬性the sinosoft send user code的值
	 */
	public String getSendUserCode() {
		return sendUserCode;
	}
	
	/**
	 * 設置屬性the sinosoft send user code.
	 * 
	 * @param sendUserCode
	 *            待設置的the sinosoft send user code的值
	 */
	public void setSendUserCode(String sendUserCode) {
		this.sendUserCode = sendUserCode;
	}
	
	/**
	 * 獲取屬性the sinosoft send user name.
	 * 
	 * @return 屬性the sinosoft send user name的值
	 */
	public String getSendUserName() {
		return sendUserName;
	}
	
	/**
	 * 設置屬性the sinosoft send user name.
	 * 
	 * @param sendUserName
	 *            待設置的the sinosoft send user name的值
	 */
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	
	/**
	 * 獲取屬性the sinosoft send user pass.
	 * 
	 * @return 屬性the sinosoft send user pass的值
	 */
	public String getSendUserPass() {
		return sendUserPass;
	}
	
	/**
	 * 設置屬性the sinosoft send user pass.
	 * 
	 * @param sendUserPass
	 *            待設置的the sinosoft send user pass的值
	 */
	public void setSendUserPass(String sendUserPass) {
		this.sendUserPass = sendUserPass;
	}
	
	/**
	 * 獲取屬性業務類型.
	 * 
	 * @return 屬性業務類型的值
	 */
	public String getBusinessType() {
		return businessType;
	}
	
	/**
	 * 設置屬性業務類型.
	 * 
	 * @param businessType
	 *            待設置的業務類型的值
	 */
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	/**
	 * 獲取屬性業務號.
	 * 
	 * @return 屬性業務號的值
	 */
	public String getBusinessNo() {
		return businessNo;
	}
	
	/**
	 * 設置屬性業務號.
	 * 
	 * @param businessNo
	 *            待設置的業務號的值
	 */
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	
	/**
	 * 獲取屬性the sinosoft rcver user.
	 * 
	 * @return 屬性the sinosoft rcver user的值
	 */
	public List getRcverUser() {
		return rcverUser;
	}
	
	/**
	 * 設置屬性the sinosoft rcver user.
	 * 
	 * @param rcverUser
	 *            待設置的the sinosoft rcver user的值
	 */
	public void setRcverUser(List rcverUser) {
		this.rcverUser = rcverUser;
	}
	
	/**
	 * 獲取屬性the sinosoft task link.
	 * 
	 * @return 屬性the sinosoft task link的值
	 */
	public String getTaskLink() {
		return taskLink;
	}
	
	/**
	 * 設置屬性the sinosoft task link.
	 * 
	 * @param taskLink
	 *            待設置的the sinosoft task link的值
	 */
	public void setTaskLink(String taskLink) {
		this.taskLink = taskLink;
	}
	
}
