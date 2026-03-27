package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *  * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@XmlRootElement
public class ClaimPrpLthirdPartyVo {
	/** 属性id */
	private String id;

	/** 属性报案信息表 */
//	private PrpLregist prpLregist;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性立案号码 */
	private String claimNo;

	/** 属性条款类别 */
	private String clauseType;

	/** 属性车牌号 */
	private String licenseNo;

	/** 属性车牌底色代码 */
	private String licenseColorCode;

	/** 属性车辆种类代码 */
	private String carKindCode;

	/** 属性是否为本保单车辆(1.是2.否) */
	private String insureCarFlag;

	/** 属性车主 */
	private String carOwner;

	/** 属性发动机号 */
	private String engineNo;

	/** 属性车架号 */
	private String frameNo;

	/** 属性车型代码 */
	private String modelCode;

	/** 属性厂牌型号 */
	private String brandName;

	/** 属性车辆已行驶公里数 */
	private Double runDistance;

	/** 属性车辆实际使用年限 */
	private Integer useYears;

	/** 属性本车责任比例 */
	private Double dutyPercent;

	/** 属性是否保险（0-否1-是2-未知） */
	private String insuredFlag;

	/** 属性承保公司代码 */
	private String insureComCode;

	/** 属性承保公司名称 */
	private String insureComName;

	/** 属性状态字段 */
	private String flag;

	/** 属性LOSSFLAG */
	private String lossFlag;

	/** 属性驾驶员信息 */
//	private PrpLdriverDto prpLdriverDto;

	/** 属性调度处理标志 */
	private String scheduleType = "";

	/** 属性是否选择发送 */
	private String selectSend = "";
	/** 属性是哪个节点的调用 */
	private String nodeType = "";
	/** 属性是险别 */
	private String prpLthirdPartyKindCode = "";
	/** 属性驾驶员名称 */
	private String driverName = "";
	/** 属性驾驶员电话 */
	private String driverSeaRoute = "";
	/** 属性驾驶员证件号码 */
	private String drivingLicenseNo = "";
	/** 属性驾驶员证件类型 */
	private String drivingCarType = "";
	/** 属性驾驶员属地代码 */
	private String apanageCode = "";
	/** 属性驾驶员属地名称 */
	private String apanage = "";
	/** 属性性别 */
	private String driverSex = "";
	/** 修車廠負責人姓名 */
	private String garageHeadName = "";
	/** 本車駕駛人與被保險人關係 */
	private String relationship = "";
	/** 保車駕駛地址，財車駕駛地址 */
	private String drivingAddress = "";
	/** 乘載單位 */
	private String carryingUnit = "";
	/** 強制保險證編號 */
	private String insuranceNo = "";
	/** 是否有保強制險 1-有強制保險，0-沒有強制保險 */
	private String isInsurance = "";
	/** 承載數量 */
	private Long carryingNumber;
	/** 財車車主 */
	private String carsOwners = "";
	/** 被保險人身分 */
	private String insuredIdentity = "";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getClauseType() {
		return clauseType;
	}
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getLicenseColorCode() {
		return licenseColorCode;
	}
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}
	public String getCarKindCode() {
		return carKindCode;
	}
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}
	public String getInsureCarFlag() {
		return insureCarFlag;
	}
	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
	}
	public String getCarOwner() {
		return carOwner;
	}
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getFrameNo() {
		return frameNo;
	}
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Double getRunDistance() {
		return runDistance;
	}
	public void setRunDistance(Double runDistance) {
		this.runDistance = runDistance;
	}
	public Integer getUseYears() {
		return useYears;
	}
	public void setUseYears(Integer useYears) {
		this.useYears = useYears;
	}
	public Double getDutyPercent() {
		return dutyPercent;
	}
	public void setDutyPercent(Double dutyPercent) {
		this.dutyPercent = dutyPercent;
	}
	public String getInsuredFlag() {
		return insuredFlag;
	}
	public void setInsuredFlag(String insuredFlag) {
		this.insuredFlag = insuredFlag;
	}
	public String getInsureComCode() {
		return insureComCode;
	}
	public void setInsureComCode(String insureComCode) {
		this.insureComCode = insureComCode;
	}
	public String getInsureComName() {
		return insureComName;
	}
	public void setInsureComName(String insureComName) {
		this.insureComName = insureComName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLossFlag() {
		return lossFlag;
	}
	public void setLossFlag(String lossFlag) {
		this.lossFlag = lossFlag;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public String getSelectSend() {
		return selectSend;
	}
	public void setSelectSend(String selectSend) {
		this.selectSend = selectSend;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getPrpLthirdPartyKindCode() {
		return prpLthirdPartyKindCode;
	}
	public void setPrpLthirdPartyKindCode(String prpLthirdPartyKindCode) {
		this.prpLthirdPartyKindCode = prpLthirdPartyKindCode;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverSeaRoute() {
		return driverSeaRoute;
	}
	public void setDriverSeaRoute(String driverSeaRoute) {
		this.driverSeaRoute = driverSeaRoute;
	}
	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}
	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}
	public String getDrivingCarType() {
		return drivingCarType;
	}
	public void setDrivingCarType(String drivingCarType) {
		this.drivingCarType = drivingCarType;
	}
	public String getApanageCode() {
		return apanageCode;
	}
	public void setApanageCode(String apanageCode) {
		this.apanageCode = apanageCode;
	}
	public String getApanage() {
		return apanage;
	}
	public void setApanage(String apanage) {
		this.apanage = apanage;
	}
	public String getDriverSex() {
		return driverSex;
	}
	public void setDriverSex(String driverSex) {
		this.driverSex = driverSex;
	}
	public String getGarageHeadName() {
		return garageHeadName;
	}
	public void setGarageHeadName(String garageHeadName) {
		this.garageHeadName = garageHeadName;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getDrivingAddress() {
		return drivingAddress;
	}
	public void setDrivingAddress(String drivingAddress) {
		this.drivingAddress = drivingAddress;
	}
	public String getCarryingUnit() {
		return carryingUnit;
	}
	public void setCarryingUnit(String carryingUnit) {
		this.carryingUnit = carryingUnit;
	}
	public String getInsuranceNo() {
		return insuranceNo;
	}
	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}
	public String getIsInsurance() {
		return isInsurance;
	}
	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}
	public Long getCarryingNumber() {
		return carryingNumber;
	}
	public void setCarryingNumber(Long carryingNumber) {
		this.carryingNumber = carryingNumber;
	}
	public String getCarsOwners() {
		return carsOwners;
	}
	public void setCarsOwners(String carsOwners) {
		this.carsOwners = carsOwners;
	}
	public String getInsuredIdentity() {
		return insuredIdentity;
	}
	public void setInsuredIdentity(String insuredIdentity) {
		this.insuredIdentity = insuredIdentity;
	}
	
}
	