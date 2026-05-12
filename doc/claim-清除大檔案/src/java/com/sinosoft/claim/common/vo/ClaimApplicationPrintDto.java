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
public class ClaimApplicationPrintDto implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2455394769509919586L;
	/**报案号 */
	private String strRegistNo = "";
	/** 交强险保单号*/
	private String strCompPolicyNo = "";
	/**商业险保单号 */
	private String strPolicyNo = "";
	/**交强险立案号 */
	private String strCompClaimNo = "";
	/** 商业险立案号*/
	private String strClaimNo = "";
	/**保险期间 */
	private String strInsuredTerm = "";
	/** 被保险人*/
	private String strInsuredName = "";
	/** 厂牌型号*/
	private String strBrandName = "";
	/** 保单中的号牌号码*/
	private String strLicenseNo = "";
	/**被保险人电话 */
	private String strInsuredPhoneNumber = "";
	/**被保险人移动电话 */
	private String strInsuredPhoneNumber1 = "";
	/** 被保险人email*/
	private String strInsuredEmail = "";
	/** 出险日期*/
	private String strDamageStartDate = "";
	/**出险地址 */
	private String strDamageAddress = "";
	/** 驾驶员电话*/
	private String strDriverPhoneNumber = "";
	/**驾驶员移动电话 */
	private String strDriverPhoneNumber1 = "";
	/**驾驶员email */
	private String strEmail = "";
	/** 驾驶员性别*/
	private String strSex = "";
	/**驾驶员生日 */
	private String strBirthday = "";
	/**驾驶员婚姻 */
	private String strMarriage = "";
	/**驾驶员国籍 */
	private String strNationality = "";
	/** 被保险人地址*/
	private String strInsuredAddress = "";
	/** 驾驶人*/
	private String strDriverName = "";
	/**交强险保险期间 */
	private String strCompInsuredTerm = "";
	/** 报案人与被保险人的关系*/
	private String strRelationType = "";
	/**驾照号 */
	private String strDrivingLicenseNo = "";
	/**事故经过 */
	private String strContext = "";

	public String getStrRegistNo() {
		return strRegistNo;
	}

	public void setStrRegistNo(String strRegistNo) {
		this.strRegistNo = strRegistNo;
	}

	public String getStrCompPolicyNo() {
		return strCompPolicyNo;
	}

	public void setStrCompPolicyNo(String strCompPolicyNo) {
		this.strCompPolicyNo = strCompPolicyNo;
	}

	public String getStrPolicyNo() {
		return strPolicyNo;
	}

	public void setStrPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}

	public String getStrCompClaimNo() {
		return strCompClaimNo;
	}

	public void setStrCompClaimNo(String strCompClaimNo) {
		this.strCompClaimNo = strCompClaimNo;
	}

	public String getStrClaimNo() {
		return strClaimNo;
	}

	public void setStrClaimNo(String strClaimNo) {
		this.strClaimNo = strClaimNo;
	}

	public String getStrInsuredTerm() {
		return strInsuredTerm;
	}

	public void setStrInsuredTerm(String strInsuredTerm) {
		this.strInsuredTerm = strInsuredTerm;
	}

	public String getStrInsuredName() {
		return strInsuredName;
	}

	public void setStrInsuredName(String strInsuredName) {
		this.strInsuredName = strInsuredName;
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

	public String getStrInsuredPhoneNumber() {
		return strInsuredPhoneNumber;
	}

	public void setStrInsuredPhoneNumber(String strInsuredPhoneNumber) {
		this.strInsuredPhoneNumber = strInsuredPhoneNumber;
	}

	public String getStrInsuredPhoneNumber1() {
		return strInsuredPhoneNumber1;
	}

	public void setStrInsuredPhoneNumber1(String strInsuredPhoneNumber1) {
		this.strInsuredPhoneNumber1 = strInsuredPhoneNumber1;
	}

	public String getStrInsuredEmail() {
		return strInsuredEmail;
	}

	public void setStrInsuredEmail(String strInsuredEmail) {
		this.strInsuredEmail = strInsuredEmail;
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

	public String getStrDriverPhoneNumber() {
		return strDriverPhoneNumber;
	}

	public void setStrDriverPhoneNumber(String strDriverPhoneNumber) {
		this.strDriverPhoneNumber = strDriverPhoneNumber;
	}

	public String getStrDriverPhoneNumber1() {
		return strDriverPhoneNumber1;
	}

	public void setStrDriverPhoneNumber1(String strDriverPhoneNumber1) {
		this.strDriverPhoneNumber1 = strDriverPhoneNumber1;
	}

	public String getStrEmail() {
		return strEmail;
	}

	public void setStrEmail(String strEmail) {
		this.strEmail = strEmail;
	}

	public String getStrSex() {
		return strSex;
	}

	public void setStrSex(String strSex) {
		this.strSex = strSex;
	}

	public String getStrBirthday() {
		return strBirthday;
	}

	public void setStrBirthday(String strBirthday) {
		this.strBirthday = strBirthday;
	}

	public String getStrMarriage() {
		return strMarriage;
	}

	public void setStrMarriage(String strMarriage) {
		this.strMarriage = strMarriage;
	}

	public String getStrNationality() {
		return strNationality;
	}

	public void setStrNationality(String strNationality) {
		this.strNationality = strNationality;
	}

	public String getStrInsuredAddress() {
		return strInsuredAddress;
	}

	public void setStrInsuredAddress(String strInsuredAddress) {
		this.strInsuredAddress = strInsuredAddress;
	}

	public String getStrDriverName() {
		return strDriverName;
	}

	public void setStrDriverName(String strDriverName) {
		this.strDriverName = strDriverName;
	}

	public String getStrCompInsuredTerm() {
		return strCompInsuredTerm;
	}

	public void setStrCompInsuredTerm(String strCompInsuredTerm) {
		this.strCompInsuredTerm = strCompInsuredTerm;
	}

	public String getStrRelationType() {
		return strRelationType;
	}

	public void setStrRelationType(String strRelationType) {
		this.strRelationType = strRelationType;
	}

	public String getStrDrivingLicenseNo() {
		return strDrivingLicenseNo;
	}

	public void setStrDrivingLicenseNo(String strDrivingLicenseNo) {
		this.strDrivingLicenseNo = strDrivingLicenseNo;
	}

	public String getStrContext() {
		return strContext;
	}

	public void setStrContext(String strContext) {
		this.strContext = strContext;
	}
	
	

}
