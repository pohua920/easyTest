package com.tlg.aps.vo;

public class SmsVo {

	private String target;

	private String message;
	
	/**
	 * 各種狀態代碼，例如 S0000 → 成功， E1001 → 資料檢核失敗...<br>
	 * 依各系統自己定義 
	 */
	private String code;
	/**
	 * 對應code的中文訊息
	 */
	private String msg;

	//mantis：CLM0273 ，處理人員：DP0713，需求單編號：將遠傳簡訊轉移至蘋信短碼簡訊平台發送 START
	//mantis： CLM0249，處理人員：DP0713，需求單編號：APS-理賠旅綜結案簡訊WS傳送參數調整 START
	private String subacc;
	 
	private String createdBy;
	 
	private String createdTime;
	//mantis： CLM0249，處理人員：DP0713，需求單編號：APS-理賠旅綜結案簡訊WS傳送參數調整 END
	//mantis：CLM0273 ，處理人員：DP0713，需求單編號：將遠傳簡訊轉移至蘋信短碼簡訊平台發送 END
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

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

	//mantis： CLM0249，處理人員：DP0713，需求單編號：APS-理賠旅綜結案簡訊WS傳送參數調整 START
	public String getSubacc() {
		return subacc;
	}

	public void setSubacc(String subacc) {
		this.subacc = subacc;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	//mantis： CLM0249，處理人員：DP0713，需求單編號：APS-理賠旅綜結案簡訊WS傳送參數調整 END
	
}
