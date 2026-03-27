package com.tlg.util.api.rest.adLogin.entity;

/**
 * mantis： OTH0145，處理人員：DP0706，需求單編號：OTH0145- prpins、undwrt、sales及pms登入改為AD驗證  
 * @author DP0706
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
