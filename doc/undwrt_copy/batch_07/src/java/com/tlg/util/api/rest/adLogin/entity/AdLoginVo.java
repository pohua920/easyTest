package com.tlg.util.api.rest.adLogin.entity;

/**
 * mantis： OTH0145，處理人員：DP0706，需求單編號：OTH0145- prpins、undwrt、sales及pms登入改為AD驗證  
 * 
 * @author DP0706
 */
public class AdLoginVo {

	/**
	 * AD帳號
	 */
	private String userId;
	/**
	 * AD密碼
	 */
	private String pwd;
	/**
	 * 識別證編號
	 */
	private String cardNo;

	/**
	 * 驗證識別證 (若為Y則會檢查傳入的識別證資料)
	 */
	private String checkCardNo;
	/**
	 * ip
	 */
	private String ip;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCheckCardNo() {
		return checkCardNo;
	}

	public void setCheckCardNo(String checkCardNo) {
		this.checkCardNo = checkCardNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
