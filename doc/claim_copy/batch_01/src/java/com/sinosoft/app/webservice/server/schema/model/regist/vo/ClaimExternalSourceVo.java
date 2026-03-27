package com.sinosoft.app.webservice.server.schema.model.regist.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案
 */
@XmlRootElement
public class ClaimExternalSourceVo {
	
	private String multiRecepNo;
	private String channelSource;
	private String policyNo;
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
	private String relationship;
	List<ThirdParty> thirdPartyList;
	List<PersonTrace> personTraceList;
	List<Driver> driverList;
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
	
	
	
}
