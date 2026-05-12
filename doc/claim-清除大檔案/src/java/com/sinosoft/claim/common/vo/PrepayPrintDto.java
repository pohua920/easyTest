package com.sinosoft.claim.common.vo;

import java.io.Serializable;

/**
 * 自定义保单数据传输对象
 * <p>
 * Title: 车险理赔保单DTO
 * </p>
 * <p>
 * Description: 车险理赔保单样本程序
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author 中科软
 * @version 1.0
 */
public class PrepayPrintDto implements Serializable {
	/**序号*/
	private static final long serialVersionUID = -2455394769509919586L;
	/**赔案号码*/
	private String strClaimNo = "";
	/**保单号码*/
	private String strPolicyNo = "";
	/**被保险人*/
	private String strInsuredName = "";
	/**保险期间*/
	private String strInsuredDate = "";
	/**出险时间*/
	private String strDamageStartDate = "";
	/**出险地点*/
	private String strDamageAddress = "";
	/**出险险种*/
	private String strRiskName = "";
	/**厂牌型号*/
	private String strBrandName = "";
	/**号牌号码*/
	private String strLicenseNo = "";
	/**预付、垫付原因*/
	private String strPrepayReason = "";
	/**事故概况*/
	private String strRegistReason = "";
	/**預付賠款金額*/
	private String strCSumPrePaid = "";
	/**估损金额*/
	private String strEstimateLoss = "";
	/**保险金额*/
	private String strSumAmount = "";

	public String getStrPolicyNo() {
		return strPolicyNo;
	}

	public void setStrPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}

	public String getStrInsuredName() {
		return strInsuredName;
	}

	public void setStrInsuredName(String strInsuredName) {
		this.strInsuredName = strInsuredName;
	}

	public String getStrInsuredDate() {
		return strInsuredDate;
	}

	public void setStrInsuredDate(String strInsuredDate) {
		this.strInsuredDate = strInsuredDate;
	}

	public String getStrDamageStartDate() {
		return strDamageStartDate;
	}

	public void setStrDamageStartDate(String strDamageStartDate) {
		this.strDamageStartDate = strDamageStartDate;
	}

	public String getStrDamageAddress() {
		return strDamageAddress;
	}

	public void setStrDamageAddress(String strDamageAddress) {
		this.strDamageAddress = strDamageAddress;
	}

	public String getStrRiskName() {
		return strRiskName;
	}

	public void setStrRiskName(String strRiskName) {
		this.strRiskName = strRiskName;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public String getStrLicenseNo() {
		return strLicenseNo;
	}

	public void setStrLicenseNo(String strLicenseNo) {
		this.strLicenseNo = strLicenseNo;
	}

	public String getStrPrepayReason() {
		return strPrepayReason;
	}

	public void setStrPrepayReason(String strPrepayReason) {
		this.strPrepayReason = strPrepayReason;
	}

	public String getStrRegistReason() {
		return strRegistReason;
	}

	public void setStrRegistReason(String strRegistReason) {
		this.strRegistReason = strRegistReason;
	}

	public String getStrCSumPrePaid() {
		return strCSumPrePaid;
	}

	public void setStrCSumPrePaid(String strCSumPrePaid) {
		this.strCSumPrePaid = strCSumPrePaid;
	}

	public String getStrEstimateLoss() {
		return strEstimateLoss;
	}

	public void setStrEstimateLoss(String strEstimateLoss) {
		this.strEstimateLoss = strEstimateLoss;
	}

	public String getStrSumAmount() {
		return strSumAmount;
	}

	public void setStrSumAmount(String strSumAmount) {
		this.strSumAmount = strSumAmount;
	}

	public String getStrClaimNo() {
		return strClaimNo;
	}

	public void setStrClaimNo(String strClaimNo) {
		this.strClaimNo = strClaimNo;
	}

}
