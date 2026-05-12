package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
 */
@XmlRootElement
public class ClaimExternalRiskSourceVo{
	private String multiRecepNo;
	private String channelSource;
	private String policyNo;
	private String claimNo;
	private String mainPolicyNo;
	private String registNo;
	private String reportDate;
	private String reportHour;
	private String reportMinute;
	private String reportType;
	private String reportorName;
	private String reportorPhoneNumber;
	private String reportorMobile;
	private String linkerName;
	private String phoneNumber;
	private String driverMobile;
	private String relationType;
	private String firstSiteFlag;
	private String sendMesFlag;
	private String scheduleType;
	private String paySelfFlag;
	private String personLossFlag;
	private String propLossFlag;
	private String registType;
	private String riskCode;
	private String damageCode;
	private String handlerCode;
	private String handleUnit;
	private String memo;

	private String manageType;
	private String damageAddress;
	private String damageStartDate;
	private String damageStartHour;
	private String damageStartMinute;
	private String remark;
	private String dutyPercent;
	private String unitCode;
	
	private String insuredName;
	
	//車(內部)
	RiskA riskA;
	//車(外部)
	private String relationship;
	List<ThirdParty> thirdPartyList;
	List<PersonTrace> personTraceList;
	List<Driver> driverList;

	
	//----------------
	//非車後加入的
//	private String insuredCode;
//	private String insuredName;
	private String linkerAddress;
	private String conText;
	//火(內部)
	RiskF riskF;
	//工程
	RiskE riskE;
	//火(外部)
	private String countryFlag;	
	private Integer estimateLoss;	
	private String lossName;
	

	//責任(內部)
	RiskC1 riskC1;
	//責任(外部)
	private String damageTypeCode;	
	private String clauseType;

	RiskCC riskCC;
	//水(內部)
	RiskM riskM;
	
	public String getMultiRecepNo() {
		return multiRecepNo;
	}
	public void setMultiRecepNo(String multiRecepNo) {
		this.multiRecepNo = multiRecepNo;
	}
	public String getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}
	public String getRegistNo() {
		return registNo;
	}
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getReportHour() {
		return reportHour;
	}
	public void setReportHour(String reportHour) {
		this.reportHour = reportHour;
	}
	public String getReportMinute() {
		return reportMinute;
	}
	public void setReportMinute(String reportMinute) {
		this.reportMinute = reportMinute;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getReportorName() {
		return reportorName;
	}
	public void setReportorName(String reportorName) {
		this.reportorName = reportorName;
	}
	public String getReportorPhoneNumber() {
		return reportorPhoneNumber;
	}
	public void setReportorPhoneNumber(String reportorPhoneNumber) {
		this.reportorPhoneNumber = reportorPhoneNumber;
	}
	public String getReportorMobile() {
		return reportorMobile;
	}
	public void setReportorMobile(String reportorMobile) {
		this.reportorMobile = reportorMobile;
	}
	public String getLinkerName() {
		return linkerName;
	}
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getFirstSiteFlag() {
		return firstSiteFlag;
	}
	public void setFirstSiteFlag(String firstSiteFlag) {
		this.firstSiteFlag = firstSiteFlag;
	}
	public String getSendMesFlag() {
		return sendMesFlag;
	}
	public void setSendMesFlag(String sendMesFlag) {
		this.sendMesFlag = sendMesFlag;
	}
	public String getScheduleType() {
		return scheduleType;
	}
	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}
	public String getPaySelfFlag() {
		return paySelfFlag;
	}
	public void setPaySelfFlag(String paySelfFlag) {
		this.paySelfFlag = paySelfFlag;
	}
	public String getPersonLossFlag() {
		return personLossFlag;
	}
	public void setPersonLossFlag(String personLossFlag) {
		this.personLossFlag = personLossFlag;
	}
	public String getPropLossFlag() {
		return propLossFlag;
	}
	public void setPropLossFlag(String propLossFlag) {
		this.propLossFlag = propLossFlag;
	}
	public String getRegistType() {
		return registType;
	}
	public void setRegistType(String registType) {
		this.registType = registType;
	}
	public String getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}
	public String getDamageCode() {
		return damageCode;
	}
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	public String getHandlerCode() {
		return handlerCode;
	}
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}
	public String getHandleUnit() {
		return handleUnit;
	}
	public void setHandleUnit(String handleUnit) {
		this.handleUnit = handleUnit;
	}
	public String getManageType() {
		return manageType;
	}
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}
	public String getDamageAddress() {
		return damageAddress;
	}
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}
	public String getDamageStartDate() {
		return damageStartDate;
	}
	public void setDamageStartDate(String damageStartDate) {
		this.damageStartDate = damageStartDate;
	}
	public String getDamageStartHour() {
		return damageStartHour;
	}
	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}
	public String getDamageStartMinute() {
		return damageStartMinute;
	}
	public void setDamageStartMinute(String damageStartMinute) {
		this.damageStartMinute = damageStartMinute;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDutyPercent() {
		return dutyPercent;
	}
	public void setDutyPercent(String dutyPercent) {
		this.dutyPercent = dutyPercent;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public List<ThirdParty> getThirdPartyList() {
		return thirdPartyList;
	}
	public void setThirdPartyList(List<ThirdParty> thirdPartyList) {
		this.thirdPartyList = thirdPartyList;
	}
	public List<PersonTrace> getPersonTraceList() {
		return personTraceList;
	}
	public void setPersonTraceList(List<PersonTrace> personTraceList) {
		this.personTraceList = personTraceList;
	}
	public String getMainPolicyNo() {
		return mainPolicyNo;
	}
	public void setMainPolicyNo(String mainPolicyNo) {
		this.mainPolicyNo = mainPolicyNo;
	}
	public List<Driver> getDriverList() {
		return driverList;
	}
	public void setDriverList(List<Driver> driverList) {
		this.driverList = driverList;
	}
	public String getChannelSource() {
		return channelSource;
	}
	public void setChannelSource(String channelSource) {
		this.channelSource = channelSource;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	

	public String getCountryFlag() {
		return countryFlag;
	}
	public void setCountryFlag(String countryFlag) {
		this.countryFlag = countryFlag;
	}
	public Integer getEstimateLoss() {
		return estimateLoss;
	}
	public void setEstimateLoss(Integer estimateLoss) {
		this.estimateLoss = estimateLoss;
	}
	public String getLossName() {
		return lossName;
	}
	public void setLossName(String lossName) {
		this.lossName = lossName;
	}
	

	public String getDamageTypeCode() {
		return damageTypeCode;
	}
	public void setDamageTypeCode(String damageTypeCode) {
		this.damageTypeCode = damageTypeCode;
	}
	public String getClauseType() {
		return clauseType;
	}
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}
	public RiskA getRiskA() {
		return riskA;
	}
	public void setRiskA(RiskA riskA) {
		this.riskA = riskA;
	}
	public RiskF getRiskF() {
		return riskF;
	}
	public void setRiskF(RiskF riskF) {
		this.riskF = riskF;
	}
	public RiskE getRiskE() {
		return riskE;
	}
	public void setRiskE(RiskE riskE) {
		this.riskE = riskE;
	}
	public RiskC1 getRiskC1() {
		return riskC1;
	}
	public void setRiskC1(RiskC1 riskC1) {
		this.riskC1 = riskC1;
	}
//	public String getInsuredCode() {
//		return insuredCode;
//	}
//	public void setInsuredCode(String insuredCode) {
//		this.insuredCode = insuredCode;
//	}
//	public String getInsuredName() {
//		return insuredName;
//	}
//	public void setInsuredName(String insuredName) {
//		this.insuredName = insuredName;
//	}
	public String getLinkerAddress() {
		return linkerAddress;
	}
	public void setLinkerAddress(String linkerAddress) {
		this.linkerAddress = linkerAddress;
	}
	public String getConText() {
		return conText;
	}
	public void setConText(String conText) {
		this.conText = conText;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public RiskCC getRiskCC() {
		return riskCC;
	}
	public void setRiskCC(RiskCC riskCC) {
		this.riskCC = riskCC;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public RiskM getRiskM() {
		return riskM;
	}
	public void setRiskM(RiskM riskM) {
		this.riskM = riskM;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	
}
