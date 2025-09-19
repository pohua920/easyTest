package com.tlg.aps.vo;

import java.util.ArrayList;

public class SmsVoList{

	private ArrayList<SmsVo> smsvoList;

	private int totalCount;

	private int successCount;

	private int failCount;
	
	/**
	 * 各種狀態代碼，例如 S0000 → 成功， E1001 → 資料檢核失敗...<br>
	 * 依各系統自己定義 
	 */
	private String code;
	/**
	 * 對應code的中文訊息
	 */
	private String msg;

	public ArrayList<SmsVo> getSmsvoList() {
		if(this.smsvoList == null){
			this.smsvoList = new ArrayList<SmsVo>();
		}
		return smsvoList;
	}

	public void setSmsvoList(ArrayList<SmsVo> smsvoList) {
		this.smsvoList = smsvoList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
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

}
