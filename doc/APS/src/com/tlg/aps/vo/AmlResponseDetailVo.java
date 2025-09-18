package com.tlg.aps.vo;

public class AmlResponseDetailVo {

	private String serialNo;

	private String id;
	
	private String cio;
	
	private String name;
	
	private String ename;

	private String workStatus;
	/**
	 * 拒限保
	 */
	private String refuseLimiteInsurance;
	/**
	 * 工作狀態
	 */
	private String listDetection;
	/**
	 * 風險率
	 */
	private String riskRating;
	/**
	 * 返回碼
	 */
	private String retCode;
	/**
	 * 最後判定結果
	 */
	private String decType;
	/**
	 * 最後命中狀態
	 */
	private String decState;
	/**
	 * 參考風險等級
	 */
	private String level;
	/**
	 * 風險審查狀態
	 */
	private String review;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
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

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getDecType() {
		return decType;
	}

	public void setDecType(String decType) {
		this.decType = decType;
	}

	public String getDecState() {
		return decState;
	}

	public void setDecState(String decState) {
		this.decState = decState;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getCio() {
		return cio;
	}

	public void setCio(String cio) {
		this.cio = cio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

}
