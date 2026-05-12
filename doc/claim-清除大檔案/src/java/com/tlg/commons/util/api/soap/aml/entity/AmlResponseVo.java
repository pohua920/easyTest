package com.tlg.commons.util.api.soap.aml.entity;

import javax.xml.bind.annotation.XmlRootElement;

/*
mantis： OTH0065，處理人員：Sam，需求單編號：OTH0065--- start
新AML串接
*/

@XmlRootElement
public class AmlResponseVo {

	private String screenCode;//狀態碼	0 - 成功，1 - 格式錯誤，2 - 系統錯誤，3 - Unikey重覆，4 - 無此資料(No Data Found)，5 - 該客戶有未結案件
	
	private String bussinessNo;//報價單號、要保號、批單號…
	
	private String listDetection;//名單檢測	01 - 未命中，02 - 未判定，03 - 命中已判定
	
	private String refuseLimiteInsurance;//拒限保
	
	private String riskRating;//風險評級	00 - 高風險未處理，01 - 高風險已處理，02 - 中風險未處理，03 - 中風險已處理，04 - 低風險
	
	//00 - 不執行，01 - 待再查詢，02 - 查詢中，03 - 收到回覆拒保，04 - 收到回覆可承保，05 - 查詢異常，06 - 查詢超時，07 - 人工審核註記，08 - 人工審核完成
	private String workStatus;//作業狀態
	
	private String errMsg;//訊息內容
	
	public String getBussinessNo() {
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo) {
		this.bussinessNo = bussinessNo;
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

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	@Override
	public String toString() {
		return "AmlResponseVo [screenCode=" + screenCode + ", bussinessNo="
				+ bussinessNo + ", listDetection=" + listDetection
				+ ", refuseLimiteInsurance=" + refuseLimiteInsurance
				+ ", riskRating=" + riskRating + ", workStatus=" + workStatus
				+ ", errMsg=" + errMsg + "]";
	}

}
