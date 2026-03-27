package com.tlg.commons.util.api.rest.adLogin.entity;

/**
 * mantis： OTH0113，處理人員：kelvin，需求單編號：OTH0113，登入時增加NFC讀卡機驗證、AD密碼驗證
 * @author bi086
 *
 */
public class ApplyTokenResponseVo extends BaseResponseVo {

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
