package com.sinosoft.claim.common.vo;

import java.io.Serializable;
import java.util.List;

import com.sinosoft.claim.schema.model.PrpLregist;

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
public class RegistPrintDto implements Serializable {
	/** */
	private static final long serialVersionUID = -2455394769509919586L;
	/** 报案主信息 */
	private PrpLregist prpLregist;
	/** 交强险保单号*/
	private String strCompelNo = "";
	/** 商业险保单号*/
	private String strPolicyNo = "";
	/** 交强险承保公司*/
	private String strCompelComName = "";
	/** 报案方式*/
	private String strReportType = "";
	/**被保险人 */
	private String strInsuredName = "";
	/** 报案人与被保险人的关系*/
	private String strRelationType = "";
	/** 驾驶员姓名*/
	private String strDriverName = "";
	/** 准驾车型*/
	private String strDrivingCarType = "";
	/**驾驶证号码 */
	private String strDrivingLicenseNo = "";
	/** 出险区域*/
	private String strDamageArea = "";
	/** 出险原因*/
	private String strDamageName = "";
	/** 出险地点分类*/
	private String strDamageAddressType = "";
	/** 是否是第一现场报案*/
	private String strFirstSiteName = "";
	/** 事故处理部门*/
	private String strHandleUnit = "";
	/** 伤亡人员*/
	private String strPersonInjure = "";
	/** 出险信息*/
	private String strClaimText = "";
	/** */
	private String strPolicy = "";
	/**号牌号码 */
	private String strLicenseNo;
	/** 厂牌型号*/
	private String strBrandName = "";
	/** 发动机号*/
	private String strEngineNo = "";
	/** 车架号（VIN）*/
	private String strFrameNo = "";
	/**  新车购置价*/
	private String strPurchasePrice = "";
	/** 车辆行驶区域*/
	private String strRunAreaName = "";
	/** 使用性质*/
	private String strUseNatureName = "";
	/** 使用年限*/
	private String strUseYears = "";
	/**使用年限 */
	private String strSeatCount = "";
	/** 是否足额交费*/
	private String strDelinquentFee = "";
	/** 应收保费*/
	private String strSumPremium = "";
	/**交费日期 */
	private String strPlanDate = "";
	/** 保险期间*/
	private String strInsuredTerm = "";
	/** 业务归属部门*/
	private String strComName = "";
	/** 出单员*/
	private String strHanderName1 = "";
	/** 经办人*/
	private String strHandlerName = "";
	/**核保人 */
	private String strUnderwriteName = "";
	/** 抄单人*/
	private String strUserName = "";
	/** 抄单日期*/
	private String strInputDate = "";
	/**特别约定内容 */
	private String strClauses = "";
	/** 批改信息*/
	private String strPheadText = "";
	/** 保险车辆出险信息 */
	private String strRegistInfo = "";
	/** 查勘信息回复*/
	private String strcheckInfo = "";
	/**  赔款总计*/
	private String strSumPaid = "";
	/** 赔款次数*/
	private String strClaimCount = "";
	/** 本单批改次数*/
	private String strPheadCount = "";
	/** 车辆出险次数*/
	private String strCompensateCount = "";
	/** 被保险人住址*/
	private String strInsuredAddress = "";
	/**邮政编码 */
	private String strPostCode = "";
	/** 移动电话*/
	private String strMobile = "";
	/** 打印时间*/
	private String strReportDate = "";
	/** 出险时间*/
	private String strDamageStartDate = "";
	/**子报表数据源 */
	private List<SubReportPrintDto> strItemKindList;

	public RegistPrintDto() {
	}

	public PrpLregist getPrpLregist() {
		return prpLregist;
	}

	public void setPrpLregist(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
	}

	public String getStrLicenseNo() {
		return strLicenseNo;
	}

	public void setStrLicenseNo(String strLicenseNo) {
		this.strLicenseNo = strLicenseNo;
	}

	public String getStrBrandName() {
		return strBrandName;
	}

	public void setStrBrandName(String strBrandName) {
		this.strBrandName = strBrandName;
	}

	public String getStrEngineNo() {
		return strEngineNo;
	}

	public void setStrEngineNo(String strEngineNo) {
		this.strEngineNo = strEngineNo;
	}

	public String getStrFrameNo() {
		return strFrameNo;
	}

	public void setStrFrameNo(String strFrameNo) {
		this.strFrameNo = strFrameNo;
	}

	public String getStrUseNatureName() {
		return strUseNatureName;
	}

	public void setStrUseNatureName(String strUseNatureName) {
		this.strUseNatureName = strUseNatureName;
	}

	public String getStrRunAreaName() {
		return strRunAreaName;
	}

	public void setStrRunAreaName(String strRunAreaName) {
		this.strRunAreaName = strRunAreaName;
	}

	public String getStrPurchasePrice() {
		return strPurchasePrice;
	}

	public void setStrPurchasePrice(String strPurchasePrice) {
		this.strPurchasePrice = strPurchasePrice;
	}

	public String getStrReportType() {
		return strReportType;
	}

	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}

	public String getStrInsuredName() {
		return strInsuredName;
	}

	public void setStrInsuredName(String strInsuredName) {
		this.strInsuredName = strInsuredName;
	}

	public String getStrInsuredAddress() {
		return strInsuredAddress;
	}

	public void setStrInsuredAddress(String strInsuredAddress) {
		this.strInsuredAddress = strInsuredAddress;
	}

	public String getStrPostCode() {
		return strPostCode;
	}

	public void setStrPostCode(String strPostCode) {
		this.strPostCode = strPostCode;
	}

	public String getStrMobile() {
		return strMobile;
	}

	public void setStrMobile(String strMobile) {
		this.strMobile = strMobile;
	}

	public String getStrRelationType() {
		return strRelationType;
	}

	public void setStrRelationType(String strRelationType) {
		this.strRelationType = strRelationType;
	}

	public String getStrDriverName() {
		return strDriverName;
	}

	public void setStrDriverName(String strDriverName) {
		this.strDriverName = strDriverName;
	}

	public String getStrDrivingCarType() {
		return strDrivingCarType;
	}

	public void setStrDrivingCarType(String strDrivingCarType) {
		this.strDrivingCarType = strDrivingCarType;
	}

	public String getStrDrivingLicenseNo() {
		return strDrivingLicenseNo;
	}

	public void setStrDrivingLicenseNo(String strDrivingLicenseNo) {
		this.strDrivingLicenseNo = strDrivingLicenseNo;
	}

	public String getStrDamageArea() {
		return strDamageArea;
	}

	public void setStrDamageArea(String strDamageArea) {
		this.strDamageArea = strDamageArea;
	}

	public String getStrDamageName() {
		return strDamageName;
	}

	public void setStrDamageName(String strDamageName) {
		this.strDamageName = strDamageName;
	}

	public String getStrDamageAddressType() {
		return strDamageAddressType;
	}

	public void setStrDamageAddressType(String strDamageAddressType) {
		this.strDamageAddressType = strDamageAddressType;
	}

	public String getStrFirstSiteName() {
		return strFirstSiteName;
	}

	public void setStrFirstSiteName(String strFirstSiteName) {
		this.strFirstSiteName = strFirstSiteName;
	}

	public String getStrHandleUnit() {
		return strHandleUnit;
	}

	public void setStrHandleUnit(String strHandleUnit) {
		this.strHandleUnit = strHandleUnit;
	}

	public String getStrPersonInjure() {
		return strPersonInjure;
	}

	public void setStrPersonInjure(String strPersonInjure) {
		this.strPersonInjure = strPersonInjure;
	}

	public String getStrClaimText() {
		return strClaimText;
	}

	public void setStrClaimText(String strClaimText) {
		this.strClaimText = strClaimText;
	}

	public String getStrPolicy() {
		return strPolicy;
	}

	public void setStrPolicy(String strPolicy) {
		this.strPolicy = strPolicy;
	}

	public String getStrUseYears() {
		return strUseYears;
	}

	public void setStrUseYears(String strUseYears) {
		this.strUseYears = strUseYears;
	}

	public String getStrSeatCount() {
		return strSeatCount;
	}

	public void setStrSeatCount(String strSeatCount) {
		this.strSeatCount = strSeatCount;
	}

	public String getStrDelinquentFee() {
		return strDelinquentFee;
	}

	public void setStrDelinquentFee(String strDelinquentFee) {
		this.strDelinquentFee = strDelinquentFee;
	}

	public String getStrSumPremium() {
		return strSumPremium;
	}

	public void setStrSumPremium(String strSumPremium) {
		this.strSumPremium = strSumPremium;
	}

	public String getStrPlanDate() {
		return strPlanDate;
	}

	public void setStrPlanDate(String strPlanDate) {
		this.strPlanDate = strPlanDate;
	}

	public String getStrInsuredTerm() {
		return strInsuredTerm;
	}

	public void setStrInsuredTerm(String strInsuredTerm) {
		this.strInsuredTerm = strInsuredTerm;
	}

	public String getStrComName() {
		return strComName;
	}

	public void setStrComName(String strComName) {
		this.strComName = strComName;
	}

	public String getStrHanderName1() {
		return strHanderName1;
	}

	public void setStrHanderName1(String strHanderName1) {
		this.strHanderName1 = strHanderName1;
	}

	public String getStrHandlerName() {
		return strHandlerName;
	}

	public void setStrHandlerName(String strHandlerName) {
		this.strHandlerName = strHandlerName;
	}

	public String getStrUnderwriteName() {
		return strUnderwriteName;
	}

	public void setStrUnderwriteName(String strUnderwriteName) {
		this.strUnderwriteName = strUnderwriteName;
	}

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrInputDate() {
		return strInputDate;
	}

	public void setStrInputDate(String strInputDate) {
		this.strInputDate = strInputDate;
	}

	public String getStrClauses() {
		return strClauses;
	}

	public void setStrClauses(String strClauses) {
		this.strClauses = strClauses;
	}

	public String getStrPheadText() {
		return strPheadText;
	}

	public void setStrPheadText(String strPheadText) {
		this.strPheadText = strPheadText;
	}

	public String getStrRegistInfo() {
		return strRegistInfo;
	}

	public void setStrRegistInfo(String strRegistInfo) {
		this.strRegistInfo = strRegistInfo;
	}

	public String getStrcheckInfo() {
		return strcheckInfo;
	}

	public void setStrcheckInfo(String strcheckInfo) {
		this.strcheckInfo = strcheckInfo;
	}

	public String getStrSumPaid() {
		return strSumPaid;
	}

	public void setStrSumPaid(String strSumPaid) {
		this.strSumPaid = strSumPaid;
	}

	public String getStrClaimCount() {
		return strClaimCount;
	}

	public void setStrClaimCount(String strClaimCount) {
		this.strClaimCount = strClaimCount;
	}

	public String getStrPheadCount() {
		return strPheadCount;
	}

	public void setStrPheadCount(String strPheadCount) {
		this.strPheadCount = strPheadCount;
	}

	public String getStrCompensateCount() {
		return strCompensateCount;
	}

	public void setStrCompensateCount(String strCompensateCount) {
		this.strCompensateCount = strCompensateCount;
	}

	public String getStrCompelNo() {
		return strCompelNo;
	}

	public void setStrCompelNo(String strCompelNo) {
		this.strCompelNo = strCompelNo;
	}

	public String getStrCompelComName() {
		return strCompelComName;
	}

	public void setStrCompelComName(String strCompelComName) {
		this.strCompelComName = strCompelComName;
	}

	public List<SubReportPrintDto> getStrItemKindList() {
		return strItemKindList;
	}

	public void setStrItemKindList(List<SubReportPrintDto> strItemKindList) {
		this.strItemKindList = strItemKindList;
	}

	public String getStrPolicyNo() {
		return strPolicyNo;
	}

	public void setStrPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}

	public String getStrReportDate() {
		return strReportDate;
	}

	public void setStrReportDate(String strReportDate) {
		this.strReportDate = strReportDate;
	}

	public String getStrDamageStartDate() {
		return strDamageStartDate;
	}

	public void setStrDamageStartDate(String strDamageStartDate) {
		this.strDamageStartDate = strDamageStartDate;
	}

}
