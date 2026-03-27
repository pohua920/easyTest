package cn.com.sinosoft.common.model;

import java.io.Serializable;

public class ExportBean implements Serializable{
	// 服务器端得错误代码
	private String errorCode;
	// 服务器端的错误信息
	private String errorMsg;
	// 请求类型
	private String requestType;
	// 反馈响应代码
	private boolean responseCode;
//	// 接口服务器端的用户表
//	private UtiIUser utiIUser;
//	// 同步目的地的个人用用户表
//	private UtiIUserIdv utiIUserIdv;
//	// 同步目的地的企业用户表
//	private UtiIUserUnit utiIUserUnit;
//	// 们步目的地的用户类型表
//	private UtiIUserType utiIUserType;
//	// 同步目的地的账户表
//	private UtiIAccount utiIAccount;
//	private List<UtiIAccount> utiIAccounts;
//	// 同步目的地的账户信息表
//	private UtiIAccAtrr utiIAccAtrr;
//	private List<UtiIAccAtrr> utiIAccAtrrs;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

//	public UtiIUser getUtiIUser() {
//		return utiIUser;
//	}
//
//	public void setUtiIUser(UtiIUser utiIUser) {
//		this.utiIUser = utiIUser;
//	}
//
//	public UtiIUserIdv getUtiIUserIdv() {
//		return utiIUserIdv;
//	}
//
//	public void setUtiIUserIdv(UtiIUserIdv utiIUserIdv) {
//		this.utiIUserIdv = utiIUserIdv;
//	}
//
//	public UtiIUserUnit getUtiIUserUnit() {
//		return utiIUserUnit;
//	}
//
//	public void setUtiIUserUnit(UtiIUserUnit utiIUserUnit) {
//		this.utiIUserUnit = utiIUserUnit;
//	}
//
//	public UtiIUserType getUtiIUserType() {
//		return utiIUserType;
//	}
//
//	public void setUtiIUserType(UtiIUserType utiIUserType) {
//		this.utiIUserType = utiIUserType;
//	}
//
//	public UtiIAccount getUtiIAccount() {
//		return utiIAccount;
//	}
//
//	public void setUtiIAccount(UtiIAccount utiIAccount) {
//		this.utiIAccount = utiIAccount;
//	}
//
//	public UtiIAccAtrr getUtiIAccAtrr() {
//		return utiIAccAtrr;
//	}
//
//	public void setUtiIAccAtrr(UtiIAccAtrr utiIAccAtrr) {
//		this.utiIAccAtrr = utiIAccAtrr;
//	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public boolean isResponseCode() {
		return responseCode;
	}

	public void setResponseCode(boolean responseCode) {
		this.responseCode = responseCode;
	}

//	public List<UtiIAccount> getUtiIAccounts() {
//		return utiIAccounts;
//	}
//
//	public void setUtiIAccounts(List<UtiIAccount> utiIAccounts) {
//		this.utiIAccounts = utiIAccounts;
//	}
//
//	public List<UtiIAccAtrr> getUtiIAccAtrrs() {
//		return utiIAccAtrrs;
//	}
//
//	public void setUtiIAccAtrrs(List<UtiIAccAtrr> utiIAccAtrrs) {
//		this.utiIAccAtrrs = utiIAccAtrrs;
//	}

}
