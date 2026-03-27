package com.tlg.util.api.rest.adLogin.entity;

/**
 * mantis： OTH0145，處理人員：DP0706，需求單編號：OTH0145- prpins、undwrt、sales及pms登入改為AD驗證  
 * @author DP0706
 *
 */
public class BaseResponseVo {
	
	
	/**
	 * 各種狀態代碼，例如 S0000 → 成功， E1001 → 資料檢核失敗...<br>
	 * 依各系統自己定義 
	 */
	private String code;
	/**
	 * 對應code的中文訊息
	 */
	private String msg;

	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
