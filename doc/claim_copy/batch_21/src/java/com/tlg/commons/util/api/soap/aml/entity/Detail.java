package com.tlg.commons.util.api.soap.aml.entity;

import javax.xml.bind.annotation.XmlRootElement;

/*
mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
新AML串接
*/

@XmlRootElement
public class Detail {

	private String cio;//客户编号	報價單號、要保號、批單號…
	
	private String serialNo;//序號
	
	//NOHIT  =>無命 中資料，SAN => 制裁名單，LPEP => 本國政治人物，OPEP => 外國政治人物，NEGN => 負面消息人物，FINC => 有金融犯罪，MLY => 涉及反洗錢 (本地名單)
	//多個會用逗號分隔, eg: SAN,LPEP	，ps. 理賠系統目前只判斷decState，若為『NOHIT』回傳N，否則回傳Y
	private String decState;//最後命中狀態
	
	//NEW.新命中未做判定,，TRUE.判定為真命中,，FALSE.判定為假命中,，PENDING.正在調查中,，FAILED.失敗（系統異常），及其他使用者自動定狀態(通過FeedbackDecType設定)
	private String decType;//最後判定結果
	
	private String id;//身分證字號/統編
	
	private String level;//參考風險等級	根據風險分值進行計算得到的等級代碼，如L/M/H(如果返回空，則表示無計算結果)

	private String listDetection;//名單檢測
	
	private String refuseLimiteInsurance;//拒限保

	private String retCode;//返回碼	指令包處理狀態返回碼，0為成功，其它為5位元數位的錯誤代碼

	private String review;//參考風險等級	0.無案例，1.案例未處理，2.案例已經處理

	//00 - 高風險未處理，01 - 高風險已處理，02 - 中風險未處理，03 - 中風險已處理，04 - 低風險
	private String riskRating;//風險評級

	//00 - 不執行，01 - 待再查詢，02 - 查詢中，03 - 收到回覆拒保，04 - 收到回覆可承保，05 - 查詢異常，06 - 查詢超時，07 - 人工審核註記，08 - 人工審核完成
	private String workStatus;//作業狀態	

	public String getCio() {
		return cio;
	}

	public void setCio(String cio) {
		this.cio = cio;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getDecState() {
		return decState;
	}

	public void setDecState(String decState) {
		this.decState = decState;
	}

	public String getDecType() {
		return decType;
	}

	public void setDecType(String decType) {
		this.decType = decType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getListDetection() {
		return listDetection;
	}

	public void setListDetection(String listDetection) {
		this.listDetection = listDetection;
	}

	public String getRefuseLimiteInsurance() {
		return refuseLimiteInsurance;
	}

	public void setRefuseLimiteInsurance(String refuseLimiteInsurance) {
		this.refuseLimiteInsurance = refuseLimiteInsurance;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getRiskRating() {
		return riskRating;
	}

	public void setRiskRating(String riskRating) {
		this.riskRating = riskRating;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

}
