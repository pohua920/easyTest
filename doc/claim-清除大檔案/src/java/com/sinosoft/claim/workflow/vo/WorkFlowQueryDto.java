/*
 * @(#)WorkFlowQueryDto.java	Mar 16, 2013
 *
 * @Company < Technology Development Company LTD..>
 */
package com.sinosoft.claim.workflow.vo;

/**
 * 定义WorkFlowQueryDto的数据传输对象类
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * @createdate 2006-04-11
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class WorkFlowQueryDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/** 报案号 */
	private String registNo;
	/** 保单号 */
	private String policyNo;
	/** 险种代码 */
	private String riskCode;
	/** 车牌号码 */
	private String licenseNo;
	/** 案件状态 */
	private String caseType;
	/** 被保险人名称 */
	private String insuredName;
	/** 操作时间 */
	private String operateDate;
	/** 是否注销 */
	private String cancelFlag;
	/** 案件状态 */
	private String status;
	/** 赔案号 */
	private String claimNo;
	/** 报案号标志 */
	private String registNoSign;
	/** 保单号标志 */
	private String policyNoSign;
	/** 险种标志 */
	private String riskCodeSign;

	/** 险种名称标志 */
	private String riskCodeNameSign;

	/** 险种名称 */
	private String riskCodeName;

	/** 车牌号标志 */
	private String licenseNoSign;
	/** 操作时间标志 */
	private String operateDateSign;
	/** 被保险人标志 */
	private String insuredNameSign;
	/** 赔案号标志 */
	private String claimNoSign;
	/** 赔款计算书号 */
	private String compensateNo;
	/** 赔款计算书号标志 */
	private String compensateNoSign;
	/** 投保人标志 */
	private String appliNameCodeSign;
	/** 核赔标志 */
	private String underWriteFlag;
	/** 归档号 */
	private String caseNo;
	/** 归档号标志 */
	private String caseNoSign;
	/** 承保机构 */
	private String comCode;
	/** 承保机构标志 */
	private String comCodeSign;
	/** 立案时间 */
	private String claimDate;
	/** 立案时间标志 */
	private String claimDateSign;
	/** 委托类型 */
	private String conSignType;
	/** 节点类型 */
	private String nodeType;
	/** 报案注销起始时间 */
	private String registStartCancelDate;
	/** 报案注销结束时间 */
	private String registEndCancelDate;

	public String getCompensateNo() {
		return compensateNo;
	}

	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	public String getCompensateNoSign() {
		return compensateNoSign;
	}

	public void setCompensateNoSign(String compensateNoSign) {
		this.compensateNoSign = compensateNoSign;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
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

	public String getRiskCode() {
		return riskCode;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getInsuredNameSign() {
		return insuredNameSign;
	}

	public void setInsuredNameSign(String insuredNameSign) {
		this.insuredNameSign = insuredNameSign;
	}

	public String getLicenseNoSign() {
		return licenseNoSign;
	}

	public void setLicenseNoSign(String licenseNoSign) {
		this.licenseNoSign = licenseNoSign;
	}

	public String getOperateDateSign() {
		return operateDateSign;
	}

	public void setOperateDateSign(String operateDateSign) {
		this.operateDateSign = operateDateSign;
	}

	public String getPolicyNoSign() {
		return policyNoSign;
	}

	public void setPolicyNoSign(String policyNoSign) {
		this.policyNoSign = policyNoSign;
	}

	public String getRegistNoSign() {
		return registNoSign;
	}

	public void setRegistNoSign(String registNoSign) {
		this.registNoSign = registNoSign;
	}

	public String getRiskCodeSign() {
		return riskCodeSign;
	}

	public void setRiskCodeSign(String riskCodeSign) {
		this.riskCodeSign = riskCodeSign;
	}

	public String getRiskCodeNameSign() {
		return riskCodeNameSign;
	}

	public void setRiskCodeNameSign(String riskCodeNameSign) {
		this.riskCodeNameSign = riskCodeNameSign;
	}

	public String getRiskCodeName() {
		return riskCodeName;
	}

	public void setRiskCodeName(String riskCodeName) {
		this.riskCodeName = riskCodeName;
	}

	public String getClaimNoSign() {
		return claimNoSign;
	}

	public void setClaimNoSign(String claimNoSign) {
		this.claimNoSign = claimNoSign;
	}

	public String getUnderWriteFlag() {
		return underWriteFlag;
	}

	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	public String getCaseNoSign() {
		return caseNoSign;
	}

	public void setCaseNoSign(String caseNoSign) {
		this.caseNoSign = caseNoSign;
	}

	public String getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}

	public String getClaimDateSign() {
		return claimDateSign;
	}

	public void setClaimDateSign(String claimDateSign) {
		this.claimDateSign = claimDateSign;
	}

	public String getComCode() {
		return comCode;
	}

	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	public String getComCodeSign() {
		return comCodeSign;
	}

	public void setComCodeSign(String comCodeSign) {
		this.comCodeSign = comCodeSign;
	}

	public String getConSignType() {
		return conSignType;
	}

	public void setConSignType(String conSignType) {
		this.conSignType = conSignType;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getRegistEndCancelDate() {
		return registEndCancelDate;
	}

	public void setRegistEndCancelDate(String registEndCancelDate) {
		this.registEndCancelDate = registEndCancelDate;
	}

	public String getRegistStartCancelDate() {
		return registStartCancelDate;
	}

	public void setRegistStartCancelDate(String registStartCancelDate) {
		this.registStartCancelDate = registStartCancelDate;
	}

	public String getAppliNameCodeSign() {
		return appliNameCodeSign;
	}

	public void setAppliNameCodeSign(String appliNameCodeSign) {
		this.appliNameCodeSign = appliNameCodeSign;
	}
}
