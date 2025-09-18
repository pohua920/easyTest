package com.tlg.aps.vo;

import java.beans.Transient;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AmlResponseVo{
	
	//業務號(報價單號、要保號、批改要保號)
	private String bussinessNo;
	//作業狀態
	private String workStatus;
	//拒限保
	private String refuseLimiteInsurance;
	//名單檢測
	private String listDetection;
	//風險評級
	private String riskRating;
	//狀態碼
	private String screenCode;
	//錯誤訊息
	private String errMsg;
	
	ArrayList<AmlResponseDetailVo> detailList;

	public String getBussinessNo() {
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo) {
		this.bussinessNo = bussinessNo;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public ArrayList<AmlResponseDetailVo> getDetailList() {
		if(this.detailList == null){
			detailList = new ArrayList<AmlResponseDetailVo>();
		}
		return detailList;
	}

	public void setDetailList(ArrayList<AmlResponseDetailVo> detailList) {
		this.detailList = detailList;
	}

	public String getRefuseLimiteInsurance() {
		return refuseLimiteInsurance;
	}

	public void setRefuseLimiteInsurance(String refuseLimiteInsurance) {
		this.refuseLimiteInsurance = refuseLimiteInsurance;
	}

	public String getListDetection() {
		return listDetection;
	}

	public void setListDetection(String listDetection) {
		this.listDetection = listDetection;
	}

	public String getRiskRating() {
		return riskRating;
	}

	public void setRiskRating(String riskRating) {
		this.riskRating = riskRating;
	}

	public String getScreenCode() {
		return screenCode;
	}

	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}
	@Transient
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
