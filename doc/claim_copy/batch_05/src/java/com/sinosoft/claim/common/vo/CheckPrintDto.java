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
public class CheckPrintDto implements Serializable {
	/**序列号*/
	private static final long serialVersionUID = -2455394769509919586L;
	/** 报案号*/
	private String strRegistNo = "";
	/** 交强险保单号*/
	private String strCompPolicyNo = ""; 
	/** 交强险立案号*/
	private String strCompClaimNo = "";
	/** 被保险人*/
	private String strInsuredName = ""; 
	/** 商业险保单号*/
	private String strPolicyNo = "";
	/** 商业险立案号*/
	private String strClaimNo = ""; 
	/** 厂牌型号*/
	private String strBrandName = ""; 
	/** 发动机号*/
	private String strEngineNo = ""; 
	/**车辆已行驶里程 */
	private String strRunDistance = ""; 
	/** 已使用年限*/
	private String strUseYear = "";
	/**号牌号码 */
	private String strLicenseNo = ""; 
	/**车架号（VIN） */
	private String strFrameNo = ""; 
	/** 驾驶员姓名*/
	private String strDriverName = ""; 
	/**性别 */
	private String strSex = "";
	/**年龄 */
	private String strAge = ""; 
	/**驾驶证号码 */
	private String strDriverLicenseNo = ""; 
	/** 查勘地点*/
	private String strCheckSite = "";
	/** 是否第一现场*/
	private String strFirstSite = ""; 
	/** 查勘日期*/
	private String strgetCheckDate = ""; 

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

	public String getStrCompClaimNo() {
		return strCompClaimNo;
	}

	public void setStrCompClaimNo(String strCompClaimNo) {
		this.strCompClaimNo = strCompClaimNo;
	}

	public String getStrInsuredName() {
		return strInsuredName;
	}

	public void setStrInsuredName(String strInsuredName) {
		this.strInsuredName = strInsuredName;
	}

	public String getStrPolicyNo() {
		return strPolicyNo;
	}

	public void setStrPolicyNo(String strPolicyNo) {
		this.strPolicyNo = strPolicyNo;
	}

	public String getStrClaimNo() {
		return strClaimNo;
	}

	public void setStrClaimNo(String strClaimNo) {
		this.strClaimNo = strClaimNo;
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

	public String getStrLicenseNo() {
		return strLicenseNo;
	}

	public void setStrLicenseNo(String strLicenseNo) {
		this.strLicenseNo = strLicenseNo;
	}

	public String getStrFrameNo() {
		return strFrameNo;
	}

	public void setStrFrameNo(String strFrameNo) {
		this.strFrameNo = strFrameNo;
	}

	public String getStrDriverName() {
		return strDriverName;
	}

	public void setStrDriverName(String strDriverName) {
		this.strDriverName = strDriverName;
	}

	public String getStrSex() {
		return strSex;
	}

	public void setStrSex(String strSex) {
		this.strSex = strSex;
	}

	public String getStrAge() {
		return strAge;
	}

	public void setStrAge(String strAge) {
		this.strAge = strAge;
	}

	public String getStrDriverLicenseNo() {
		return strDriverLicenseNo;
	}

	public void setStrDriverLicenseNo(String strDriverLicenseNo) {
		this.strDriverLicenseNo = strDriverLicenseNo;
	}

	public String getStrRunDistance() {
		return strRunDistance;
	}

	public void setStrRunDistance(String strRunDistance) {
		this.strRunDistance = strRunDistance;
	}

	public String getStrUseYear() {
		return strUseYear;
	}

	public void setStrUseYear(String strUseYear) {
		this.strUseYear = strUseYear;
	}

	public String getStrCheckSite() {
		return strCheckSite;
	}

	public void setStrCheckSite(String strCheckSite) {
		this.strCheckSite = strCheckSite;
	}

	public String getStrFirstSite() {
		return strFirstSite;
	}

	public void setStrFirstSite(String strFirstSite) {
		this.strFirstSite = strFirstSite;
	}

	public String getStrgetCheckDate() {
		return strgetCheckDate;
	}

	public void setStrgetCheckDate(String strgetCheckDate) {
		this.strgetCheckDate = strgetCheckDate;
	}

}
