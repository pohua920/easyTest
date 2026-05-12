package com.sinosoft.claim.common.vo;

import java.io.Serializable;
import java.util.List;

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
public class ClaimStatementPrintDto implements Serializable {
	/**序号 */
	private static final long serialVersionUID = -2455394769509919586L;
	/** 赔案号码*/
	private String strClaimNo = "";
	/** 保单号码*/
	private String strPolicyno = "";
	/** 賠付追償次數*/
	private String strReplevytimes = "";
	/** 是否追偿*/
	private String strIsReplevy = "";
	/**受理时间 */
	private String strClaimDate = "";
	/** 结案日期*/
	private String strCloseCaseDate = "";
	/** 开票单位*/
	private String strBillingUnit = "";// 
	/** 出单单位*/
	private String strComName = "";
	/** 被保险人*/
	private String strInsuredName = "";
	/** 保险期间*/
	private String strInsuredTerm = "";
	/** 肇事驾驶人*/
	private String strDriverName = "";
	/** 与被保险人关系*/
	private String strRelationship = "";
	/**驾照号码 */
	private String strDrivingLicenseNo = "";
	/** 驾驶员性别*/
	private String strSex = "";
	/** 驾驶员生日*/
	private String strBirthday = "";
	/** 驾驶员婚姻*/
	private String strMarriage = "";
	/** 出险日期*/
	private String strDamageStartDate = "";
	/**出险原因 */
	private String strDamageReason = "";
	/** 出险地址*/
	private String strDamageAddress = "";
	/** 属性出险地点分类代码 */
	private String damageAddressType = "";
	/** 属性出险原因代码 */
	private String damageCode = "";
	/**牌照号码 */
	private String strLicenseNo = "";
	/** 原始发照年月*/
	private String strUseDriverLicense = "";
	/** 标的编号*/
	private String strSubjectNo = "";
	/**制造年份 */
	private String strMakeDate = "";
	/**厂牌车型 */
	private String strBrandName = "";
	/** 引擎/车身/号码*/
	private String strFrameNo = "";
	/** 排气量*/
	private String strExhaustScale = "";
	/** 车辆种类*/
	private String strCarKindName = "";
	/** 载客限制*/
	private String strPassengerRestrictions = "";
	/** 收费情形 -1为未缴费，0为未缴全，1为缴全*/
	private String palyName = "";
	/**收费日期 */
	private String strPayDate = "";
	/** 票据到期日*/
	private String strNotesMaturityDate = "";
	/**金额 */
	private String strMoney = "";
	/** 本车损失 1全损\2分损*/
	private String strLlossType = "";
	/** 人员伤亡*/
	private String strCasualties = "";
	/** 对造身份代号*/
	private String strManipulateCode = "";
	/**对造车种代号 */
	private String strRepairerKindCode = "";
	/** 乘载数量*/
	private String strSeatCount = "";
	/**对造车承保公司 */
	private String strInsureComName = "";
	/** 对造车牌号码*/
	private String strThirdLicenseNo = "";
	/**对造车保险正号 */
	private String strInsuranceNo = "";
	/** 属性责任比例 */
	private String indemnityDutyRate = "";
	/** 对造车肇责*/
	private String strOppositeIndemnityDuty = "";
	/**其他肇责 */
	private String strOtherIndemnityDuty = "";
	/**  赔款总计*/
	private String strCSumPaidOut = "";
	/**判断是否显示人员信息 */
	private int compelFlag;
	/**總公司經辦人code*/
	private String handleCode = "";
	/**總公司經辦人name*/
	private String handleName = "";
	/**分公司經辦人code*/
	private String handleCode1 = "";
	/**分公司經辦人name*/
	private String handleName1 = "";
	/** 业务来源 */
	private String strBusinessNature = "";
	/** 自負額發票號 */
	private String strDeductibleInvoice = "";
	/** 車體險肇責 */
	private String strAccidentType = "";
	/** 責任險肇責 */
	private String strPropAccidentType = "";
	/** 通用报表打印Dto对象*/
	private List<SubReportPrintDto> compensateInfoList;
	/** 属性受害人信息 */
	private List<SubReportPrintDto> victimsInfoList;
	/** 属性赔付对象信息 */
	private List<SubReportPrintDto> sumFeeInfoList;
	/* mantis： CLM0044 ，處理人員：BK007 蘇哲，需求單編號：CLM0044理賠計算書將開票單位異動成服務人員  --start */
	/** 服務人員 */
	private String handler1Name = "";
	/* mantis： CLM0044 ，處理人員：BK007 蘇哲，需求單編號：CLM0044理賠計算書將開票單位異動成服務人員  --end */
	
	//mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明 START
	/** 追償內容 **/
	private String context;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	//mantis：CLM0028 ，處理人員：BK007 蘇哲，需求單編號：CLM0028 追償處理賠付對象管控、追償計算書增加追償說明 START
	//mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日 - start
	/** 理算文件備齊日 */
	private String fileReadyDate = "";
	public String getFileReadyDate() {
		return fileReadyDate;
	}

	public void setFileReadyDate(String fileReadyDate) {
		this.fileReadyDate = fileReadyDate;
	}
	//mantis： CLM0091 ，處理人員：BK007 蘇哲，需求單編號：CLM0091.新核心-理算文件齊全日 - end

	public String getStrClaimNo() {
		return strClaimNo;
	}

	public void setStrClaimNo(String strClaimNo) {
		this.strClaimNo = strClaimNo;
	}

	public String getStrPolicyno() {
		return strPolicyno;
	}

	public void setStrPolicyno(String strPolicyno) {
		this.strPolicyno = strPolicyno;
	}

	public String getStrInsuredName() {
		return strInsuredName;
	}

	public void setStrInsuredName(String strInsuredName) {
		this.strInsuredName = strInsuredName;
	}

	public String getStrInsuredTerm() {
		return strInsuredTerm;
	}

	public void setStrInsuredTerm(String strInsuredTerm) {
		this.strInsuredTerm = strInsuredTerm;
	}

	public String getStrDriverName() {
		return strDriverName;
	}

	public void setStrDriverName(String strDriverName) {
		this.strDriverName = strDriverName;
	}

	public String getStrRelationship() {
		return strRelationship;
	}

	public void setStrRelationship(String strRelationship) {
		this.strRelationship = strRelationship;
	}

	public String getStrDrivingLicenseNo() {
		return strDrivingLicenseNo;
	}

	public void setStrDrivingLicenseNo(String strDrivingLicenseNo) {
		this.strDrivingLicenseNo = strDrivingLicenseNo;
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

	public String getStrLicenseNo() {
		return strLicenseNo;
	}

	public void setStrLicenseNo(String strLicenseNo) {
		this.strLicenseNo = strLicenseNo;
	}

	public String getStrMakeDate() {
		return strMakeDate;
	}

	public void setStrMakeDate(String strMakeDate) {
		this.strMakeDate = strMakeDate;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public String getStrFrameNo() {
		return strFrameNo;
	}

	public void setStrFrameNo(String strFrameNo) {
		this.strFrameNo = strFrameNo;
	}

	public String getStrExhaustScale() {
		return strExhaustScale;
	}

	public void setStrExhaustScale(String strExhaustScale) {
		this.strExhaustScale = strExhaustScale;
	}

	public String getStrCarKindName() {
		return strCarKindName;
	}

	public void setStrCarKindName(String strCarKindName) {
		this.strCarKindName = strCarKindName;
	}

	public String getStrDamageReason() {
		return strDamageReason;
	}

	public void setStrDamageReason(String strDamageReason) {
		this.strDamageReason = strDamageReason;
	}

	public String getStrSeatCount() {
		return strSeatCount;
	}

	public void setStrSeatCount(String strSeatCount) {
		this.strSeatCount = strSeatCount;
	}

	public String getStrReplevytimes() {
		return strReplevytimes;
	}

	public void setStrReplevytimes(String strReplevytimes) {
		this.strReplevytimes = strReplevytimes;
	}

	public String getStrIsReplevy() {
		return strIsReplevy;
	}

	public void setStrIsReplevy(String strIsReplevy) {
		this.strIsReplevy = strIsReplevy;
	}

	public String getStrCloseCaseDate() {
		return strCloseCaseDate;
	}

	public void setStrCloseCaseDate(String strCloseCaseDate) {
		this.strCloseCaseDate = strCloseCaseDate;
	}

	public String getStrClaimDate() {
		return strClaimDate;
	}

	public void setStrClaimDate(String strClaimDate) {
		this.strClaimDate = strClaimDate;
	}

	public String getStrComName() {
		return strComName;
	}

	public void setStrComName(String strComName) {
		this.strComName = strComName;
	}

	public String getDamageAddressType() {
		return damageAddressType;
	}

	public void setDamageAddressType(String damageAddressType) {
		this.damageAddressType = damageAddressType;
	}

	public String getDamageCode() {
		return damageCode;
	}

	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	public String getPalyName() {
		return palyName;
	}

	public void setPalyName(String palyName) {
		this.palyName = palyName;
	}

	public String getStrLlossType() {
		return strLlossType;
	}

	public void setStrLlossType(String strLlossType) {
		this.strLlossType = strLlossType;
	}

	public String getIndemnityDutyRate() {
		return indemnityDutyRate;
	}

	public void setIndemnityDutyRate(String indemnityDutyRate) {
		this.indemnityDutyRate = indemnityDutyRate;
	}

	public String getStrOppositeIndemnityDuty() {
		return strOppositeIndemnityDuty;
	}

	public void setStrOppositeIndemnityDuty(String strOppositeIndemnityDuty) {
		this.strOppositeIndemnityDuty = strOppositeIndemnityDuty;
	}

	public String getStrOtherIndemnityDuty() {
		return strOtherIndemnityDuty;
	}

	public void setStrOtherIndemnityDuty(String strOtherIndemnityDuty) {
		this.strOtherIndemnityDuty = strOtherIndemnityDuty;
	}

	public String getStrBillingUnit() {
		return strBillingUnit;
	}

	public void setStrBillingUnit(String strBillingUnit) {
		this.strBillingUnit = strBillingUnit;
	}

	public String getStrUseDriverLicense() {
		return strUseDriverLicense;
	}

	public void setStrUseDriverLicense(String strUseDriverLicense) {
		this.strUseDriverLicense = strUseDriverLicense;
	}

	public String getStrSubjectNo() {
		return strSubjectNo;
	}

	public void setStrSubjectNo(String strSubjectNo) {
		this.strSubjectNo = strSubjectNo;
	}

	public String getStrPassengerRestrictions() {
		return strPassengerRestrictions;
	}

	public void setStrPassengerRestrictions(String strPassengerRestrictions) {
		this.strPassengerRestrictions = strPassengerRestrictions;
	}

	public String getStrPayDate() {
		return strPayDate;
	}

	public void setStrPayDate(String strPayDate) {
		this.strPayDate = strPayDate;
	}

	public String getStrNotesMaturityDate() {
		return strNotesMaturityDate;
	}

	public void setStrNotesMaturityDate(String strNotesMaturityDate) {
		this.strNotesMaturityDate = strNotesMaturityDate;
	}

	public String getStrMoney() {
		return strMoney;
	}

	public void setStrMoney(String strMoney) {
		this.strMoney = strMoney;
	}

	public String getStrCasualties() {
		return strCasualties;
	}

	public void setStrCasualties(String strCasualties) {
		this.strCasualties = strCasualties;
	}

	public String getStrManipulateCode() {
		return strManipulateCode;
	}

	public void setStrManipulateCode(String strManipulateCode) {
		this.strManipulateCode = strManipulateCode;
	}

	public String getStrRepairerKindCode() {
		return strRepairerKindCode;
	}

	public void setStrRepairerKindCode(String strRepairerKindCode) {
		this.strRepairerKindCode = strRepairerKindCode;
	}

	public String getStrCSumPaidOut() {
		return strCSumPaidOut;
	}

	public void setStrCSumPaidOut(String strCSumPaidOut) {
		this.strCSumPaidOut = strCSumPaidOut;
	}

	public List<SubReportPrintDto> getCompensateInfoList() {
		return compensateInfoList;
	}

	public void setCompensateInfoList(List<SubReportPrintDto> compensateInfoList) {
		this.compensateInfoList = compensateInfoList;
	}

	public List<SubReportPrintDto> getVictimsInfoList() {
		return victimsInfoList;
	}

	public void setVictimsInfoList(List<SubReportPrintDto> victimsInfoList) {
		this.victimsInfoList = victimsInfoList;
	}

	public List<SubReportPrintDto> getSumFeeInfoList() {
		return sumFeeInfoList;
	}

	public void setSumFeeInfoList(List<SubReportPrintDto> sumFeeInfoList) {
		this.sumFeeInfoList = sumFeeInfoList;
	}

	public int getCompelFlag() {
		return compelFlag;
	}

	public void setCompelFlag(int compelFlag) {
		this.compelFlag = compelFlag;
	}

	public String getStrInsureComName() {
		return strInsureComName;
	}

	public void setStrInsureComName(String strInsureComName) {
		this.strInsureComName = strInsureComName;
	}

	public String getStrThirdLicenseNo() {
		return strThirdLicenseNo;
	}

	public void setStrThirdLicenseNo(String strThirdLicenseNo) {
		this.strThirdLicenseNo = strThirdLicenseNo;
	}

	public String getStrInsuranceNo() {
		return strInsuranceNo;
	}

	public void setStrInsuranceNo(String strInsuranceNo) {
		this.strInsuranceNo = strInsuranceNo;
	}

	public String getHandleCode() {
		return handleCode;
	}

	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public String getHandleCode1() {
		return handleCode1;
	}

	public void setHandleCode1(String handleCode1) {
		this.handleCode1 = handleCode1;
	}

	public String getHandleName1() {
		return handleName1;
	}

	public void setHandleName1(String handleName1) {
		this.handleName1 = handleName1;
	}

	public String getStrBusinessNature() {
		return strBusinessNature;
	}

	public void setStrBusinessNature(String strBusinessNature) {
		this.strBusinessNature = strBusinessNature;
	}

	public String getStrDeductibleInvoice() {
		return strDeductibleInvoice;
	}

	public void setStrDeductibleInvoice(String strDeductibleInvoice) {
		this.strDeductibleInvoice = strDeductibleInvoice;
	}

	public String getStrAccidentType() {
		return strAccidentType;
	}

	public void setStrAccidentType(String strAccidentType) {
		this.strAccidentType = strAccidentType;
	}

	public String getStrPropAccidentType() {
		return strPropAccidentType;
	}

	public void setStrPropAccidentType(String strPropAccidentType) {
		this.strPropAccidentType = strPropAccidentType;
	}

	/* mantis： CLM0044 ，處理人員：BK007 蘇哲，需求單編號：CLM0044理賠計算書將開票單位異動成服務人員  --start */
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}

	public String getHandler1Name() {
		return handler1Name;
	}
	/* mantis： CLM0044 ，處理人員：BK007 蘇哲，需求單編號：CLM0044理賠計算書將開票單位異動成服務人員  --end */
}
