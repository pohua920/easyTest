package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpLregistLog报案修改轨迹信息表
 */
@Entity
@Table(name = "PRPLREGISTLOG")
public class PrpLregistLog implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLregistLogId id;

	/** 属性理赔类型 */
	private String lflag;

	/** 属性险类代码 */
	private String classCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性语种 */
	private String language;

	/** 属性被保险人代码 */
	private String insuredCode;

	/** 属性被保险人名称 */
	private String insuredName;

	/** 属性被保险人通讯地址 */
	private String insuredAddress;

	/** 属性条款类别 */
	private String clauseType;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性车牌底色代码 */
	private String licenseColorCode;

	/** 属性车辆种类代码 */
	private String carKindCode;

	/** 属性车型代码(车系+车型) */
	private String modelCode;

	/** 属性厂牌型号 */
	private String brandName;

	/** 属性发动机号 */
	private String engineNo;

	/** 属性车架号 */
	private String frameNo;

	/** 属性车辆已行驶公里数 */
	private BigDecimal runDistance;

	/** 属性车辆实际使用年限 */
	private BigDecimal useYears;

	/** 属性报案日期 */
	private Date reportDate;

	/** 属性报案小时 */
	private String reportHour;

	/** 属性报案地点 */
	private String reportAddress;

	/** 属性报案人 */
	private String reportorName;

	/** 属性报案形式 */
	private String reportType;

	/** 属性报案人联系电话 */
	private String phoneNumber;

	/** 属性联系人 */
	private String linkerName;

	/** 属性出险日期起 */
	private Date damageStartDate;

	/** 属性出险开始小时 */
	private String damageStartHour;

	/** 属性出险日期止 */
	private Date damageEndDate;

	/** 属性出险终止小时 */
	private String damageEndHour;

	/** 属性出险原因代码 */
	private String damageCode;

	/** 属性出险原因说明 */
	private String damageName;

	/** 属性事故类型代码(车险) */
	private String damageTypeCode;

	/** 属性事故类型说明 */
	private String damageTypeName;

	/** 属性是否第一现场 */
	private String firstSiteFlag;

	/** 属性出险区域代码 */
	private String damageAreaCode;

	/** 属性出险区域名称 */
	private String damageAreaName;

	/** 属性出险地点分类 */
	private String damageAddressType;

	/** 属性出险地代码 */
	private String addressCode;

	/** 属性出险地点 */
	private String damageAddress;

	/** 属性出险地点邮政编码 */
	private String damageAreaPostCode;

	/** 属性事故处理部门 */
	private String handleUnit;

	/** 属性受损标的 */
	private String lossName;

	/** 属性受损标的数量 */
	private BigDecimal lossQuantity;

	/** 属性数量单位 */
	private String unit;

	/** 属性估损币别 */
	private String estiCurrency;

	/** 属性估损金额 */
	private BigDecimal estimateLoss;

	/** 属性接案员姓名 */
	private String receiverName;

	/** 属性经办人代码 */
	private String handlerCode;

	/** 属性归属业务员代码 */
	private String handler1Code;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性受理标志(Y/N) */
	private String acceptFlag;

	/** 属性是否向别的保险公司投保(Y/N) */
	private String repeatInsureFlag;

	/** 属性赔案类别 */
	private String claimType;

	/** 属性注销/拒赔日期 */
	private Date cancelDate;

	/** 属性注销/拒赔人代码 */
	private String dealerCode;

	/** 属性备注 */
	private String remark;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性理赔登记机构 */
	private String makeCom;

	/** 属性标志字段 */
	private String flag;

	/** 属性报案人电话 */
	private String reportorPhoneNumber;

	/** 属性联系人邮编 */
	private String linkerPostCode;

	/** 属性联系人通讯地址 */
	private String linkerAddress;

	/** 属性未决赔款准备金 */
	private BigDecimal estimateFee;

	/** 属性巨灾一级代码 */
	private String catastropheCode1;

	/** 属性巨灾一级名称 */
	private String catastropheName1;

	/** 属性巨灾二级代码 */
	private String catastropheCode2;

	/** 属性巨灾二级名称 */
	private String catastropheName2;

	/** 属性报案标志 */
	private String reportFlag;

	/** 属性故责任类型 */
	private String indemnityDuty;

	/** 属性是否是团单免导标志 */
	private String termFlag;

	/** 属性最新修改人姓名 */
	private String altername;

	/** 属性最新修改人电话 */
	private String alterphonenumber;

	/** 属性最新修改人与被保险人关系 */
	private String alterRelationType;

	/** 属性最新修改时间 */
	private Date alterTime;

	/** 属性报案修改轨迹 */
	private String alterLocus;

	/** 属性报案修改方式 */
	private String alterType;

	/**
	 * 类PrpLregistLog的默认构造方法
	 */
	public PrpLregistLog() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "logID", column = @Column(name = "LOGID")), @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")) })
	public PrpLregistLogId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLregistLogId id) {
		this.id = id;
	}

	/**
	 * 属性理赔类型的getter方法
	 */

	@Column(name = "LFLAG")
	public String getLflag() {
		return this.lflag;
	}

	/**
	 * 属性理赔类型的setter方法
	 */
	public void setLflag(String lflag) {
		this.lflag = lflag;
	}

	/**
	 * 属性险类代码的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性险类代码的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 属性险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性语种的getter方法
	 */

	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return this.language;
	}

	/**
	 * 属性语种的setter方法
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * 属性被保险人代码的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性被保险人代码的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保险人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性被保险人通讯地址的getter方法
	 */

	@Column(name = "INSUREDADDRESS")
	public String getInsuredAddress() {
		return this.insuredAddress;
	}

	/**
	 * 属性被保险人通讯地址的setter方法
	 */
	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}

	/**
	 * 属性条款类别的getter方法
	 */

	@Column(name = "CLAUSETYPE")
	public String getClauseType() {
		return this.clauseType;
	}

	/**
	 * 属性条款类别的setter方法
	 */
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}

	/**
	 * 属性车牌号码的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号码的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 属性车牌底色代码的getter方法
	 */

	@Column(name = "LICENSECOLORCODE")
	public String getLicenseColorCode() {
		return this.licenseColorCode;
	}

	/**
	 * 属性车牌底色代码的setter方法
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	/**
	 * 属性车辆种类代码的getter方法
	 */

	@Column(name = "CARKINDCODE")
	public String getCarKindCode() {
		return this.carKindCode;
	}

	/**
	 * 属性车辆种类代码的setter方法
	 */
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}

	/**
	 * 属性车型代码(车系+车型)的getter方法
	 */

	@Column(name = "MODELCODE")
	public String getModelCode() {
		return this.modelCode;
	}

	/**
	 * 属性车型代码(车系+车型)的setter方法
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * 属性厂牌型号的getter方法
	 */

	@Column(name = "BRANDNAME")
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * 属性厂牌型号的setter方法
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 属性发动机号的getter方法
	 */

	@Column(name = "ENGINENO")
	public String getEngineNo() {
		return this.engineNo;
	}

	/**
	 * 属性发动机号的setter方法
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	/**
	 * 属性车架号的getter方法
	 */

	@Column(name = "FRAMENO")
	public String getFrameNo() {
		return this.frameNo;
	}

	/**
	 * 属性车架号的setter方法
	 */
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	/**
	 * 属性车辆已行驶公里数的getter方法
	 */

	@Column(name = "RUNDISTANCE")
	public BigDecimal getRunDistance() {
		return this.runDistance;
	}

	/**
	 * 属性车辆已行驶公里数的setter方法
	 */
	public void setRunDistance(BigDecimal runDistance) {
		this.runDistance = runDistance;
	}

	/**
	 * 属性车辆实际使用年限的getter方法
	 */

	@Column(name = "USEYEARS")
	public BigDecimal getUseYears() {
		return this.useYears;
	}

	/**
	 * 属性车辆实际使用年限的setter方法
	 */
	public void setUseYears(BigDecimal useYears) {
		this.useYears = useYears;
	}

	/**
	 * 属性报案日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REPORTDATE")
	public Date getReportDate() {
		return this.reportDate;
	}

	/**
	 * 属性报案日期的setter方法
	 */
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * 属性报案小时的getter方法
	 */

	@Column(name = "REPORTHOUR")
	public String getReportHour() {
		return this.reportHour;
	}

	/**
	 * 属性报案小时的setter方法
	 */
	public void setReportHour(String reportHour) {
		this.reportHour = reportHour;
	}

	/**
	 * 属性报案地点的getter方法
	 */

	@Column(name = "REPORTADDRESS")
	public String getReportAddress() {
		return this.reportAddress;
	}

	/**
	 * 属性报案地点的setter方法
	 */
	public void setReportAddress(String reportAddress) {
		this.reportAddress = reportAddress;
	}

	/**
	 * 属性报案人的getter方法
	 */

	@Column(name = "REPORTORNAME")
	public String getReportorName() {
		return this.reportorName;
	}

	/**
	 * 属性报案人的setter方法
	 */
	public void setReportorName(String reportorName) {
		this.reportorName = reportorName;
	}

	/**
	 * 属性报案形式的getter方法
	 */

	@Column(name = "REPORTTYPE")
	public String getReportType() {
		return this.reportType;
	}

	/**
	 * 属性报案形式的setter方法
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * 属性报案人联系电话的getter方法
	 */

	@Column(name = "PHONENUMBER")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * 属性报案人联系电话的setter方法
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * 属性联系人的getter方法
	 */

	@Column(name = "LINKERNAME")
	public String getLinkerName() {
		return this.linkerName;
	}

	/**
	 * 属性联系人的setter方法
	 */
	public void setLinkerName(String linkerName) {
		this.linkerName = linkerName;
	}

	/**
	 * 属性出险日期起的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DAMAGESTARTDATE")
	public Date getDamageStartDate() {
		return this.damageStartDate;
	}

	/**
	 * 属性出险日期起的setter方法
	 */
	public void setDamageStartDate(Date damageStartDate) {
		this.damageStartDate = damageStartDate;
	}

	/**
	 * 属性出险开始小时的getter方法
	 */

	@Column(name = "DAMAGESTARTHOUR")
	public String getDamageStartHour() {
		return this.damageStartHour;
	}

	/**
	 * 属性出险开始小时的setter方法
	 */
	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}

	/**
	 * 属性出险日期止的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DAMAGEENDDATE")
	public Date getDamageEndDate() {
		return this.damageEndDate;
	}

	/**
	 * 属性出险日期止的setter方法
	 */
	public void setDamageEndDate(Date damageEndDate) {
		this.damageEndDate = damageEndDate;
	}

	/**
	 * 属性出险终止小时的getter方法
	 */

	@Column(name = "DAMAGEENDHOUR")
	public String getDamageEndHour() {
		return this.damageEndHour;
	}

	/**
	 * 属性出险终止小时的setter方法
	 */
	public void setDamageEndHour(String damageEndHour) {
		this.damageEndHour = damageEndHour;
	}

	/**
	 * 属性出险原因代码的getter方法
	 */

	@Column(name = "DAMAGECODE")
	public String getDamageCode() {
		return this.damageCode;
	}

	/**
	 * 属性出险原因代码的setter方法
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	/**
	 * 属性出险原因说明的getter方法
	 */

	@Column(name = "DAMAGENAME")
	public String getDamageName() {
		return this.damageName;
	}

	/**
	 * 属性出险原因说明的setter方法
	 */
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}

	/**
	 * 属性事故类型代码(车险)的getter方法
	 */

	@Column(name = "DAMAGETYPECODE")
	public String getDamageTypeCode() {
		return this.damageTypeCode;
	}

	/**
	 * 属性事故类型代码(车险)的setter方法
	 */
	public void setDamageTypeCode(String damageTypeCode) {
		this.damageTypeCode = damageTypeCode;
	}

	/**
	 * 属性事故类型说明的getter方法
	 */

	@Column(name = "DAMAGETYPENAME")
	public String getDamageTypeName() {
		return this.damageTypeName;
	}

	/**
	 * 属性事故类型说明的setter方法
	 */
	public void setDamageTypeName(String damageTypeName) {
		this.damageTypeName = damageTypeName;
	}

	/**
	 * 属性是否第一现场的getter方法
	 */

	@Column(name = "FIRSTSITEFLAG")
	public String getFirstSiteFlag() {
		return this.firstSiteFlag;
	}

	/**
	 * 属性是否第一现场的setter方法
	 */
	public void setFirstSiteFlag(String firstSiteFlag) {
		this.firstSiteFlag = firstSiteFlag;
	}

	/**
	 * 属性出险区域代码的getter方法
	 */

	@Column(name = "DAMAGEAREACODE")
	public String getDamageAreaCode() {
		return this.damageAreaCode;
	}

	/**
	 * 属性出险区域代码的setter方法
	 */
	public void setDamageAreaCode(String damageAreaCode) {
		this.damageAreaCode = damageAreaCode;
	}

	/**
	 * 属性出险区域名称的getter方法
	 */

	@Column(name = "DAMAGEAREANAME")
	public String getDamageAreaName() {
		return this.damageAreaName;
	}

	/**
	 * 属性出险区域名称的setter方法
	 */
	public void setDamageAreaName(String damageAreaName) {
		this.damageAreaName = damageAreaName;
	}

	/**
	 * 属性出险地点分类的getter方法
	 */

	@Column(name = "DAMAGEADDRESSTYPE")
	public String getDamageAddressType() {
		return this.damageAddressType;
	}

	/**
	 * 属性出险地点分类的setter方法
	 */
	public void setDamageAddressType(String damageAddressType) {
		this.damageAddressType = damageAddressType;
	}

	/**
	 * 属性出险地代码的getter方法
	 */

	@Column(name = "ADDRESSCODE")
	public String getAddressCode() {
		return this.addressCode;
	}

	/**
	 * 属性出险地代码的setter方法
	 */
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	/**
	 * 属性出险地点的getter方法
	 */

	@Column(name = "DAMAGEADDRESS")
	public String getDamageAddress() {
		return this.damageAddress;
	}

	/**
	 * 属性出险地点的setter方法
	 */
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}

	/**
	 * 属性出险地点邮政编码的getter方法
	 */

	@Column(name = "DAMAGEAREAPOSTCODE")
	public String getDamageAreaPostCode() {
		return this.damageAreaPostCode;
	}

	/**
	 * 属性出险地点邮政编码的setter方法
	 */
	public void setDamageAreaPostCode(String damageAreaPostCode) {
		this.damageAreaPostCode = damageAreaPostCode;
	}

	/**
	 * 属性事故处理部门的getter方法
	 */

	@Column(name = "HANDLEUNIT")
	public String getHandleUnit() {
		return this.handleUnit;
	}

	/**
	 * 属性事故处理部门的setter方法
	 */
	public void setHandleUnit(String handleUnit) {
		this.handleUnit = handleUnit;
	}

	/**
	 * 属性受损标的的getter方法
	 */

	@Column(name = "LOSSNAME")
	public String getLossName() {
		return this.lossName;
	}

	/**
	 * 属性受损标的的setter方法
	 */
	public void setLossName(String lossName) {
		this.lossName = lossName;
	}

	/**
	 * 属性受损标的数量的getter方法
	 */

	@Column(name = "LOSSQUANTITY")
	public BigDecimal getLossQuantity() {
		return this.lossQuantity;
	}

	/**
	 * 属性受损标的数量的setter方法
	 */
	public void setLossQuantity(BigDecimal lossQuantity) {
		this.lossQuantity = lossQuantity;
	}

	/**
	 * 属性数量单位的getter方法
	 */

	@Column(name = "UNIT")
	public String getUnit() {
		return this.unit;
	}

	/**
	 * 属性数量单位的setter方法
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 属性估损币别的getter方法
	 */

	@Column(name = "ESTICURRENCY")
	public String getEstiCurrency() {
		return this.estiCurrency;
	}

	/**
	 * 属性估损币别的setter方法
	 */
	public void setEstiCurrency(String estiCurrency) {
		this.estiCurrency = estiCurrency;
	}

	/**
	 * 属性估损金额的getter方法
	 */

	@Column(name = "ESTIMATELOSS")
	public BigDecimal getEstimateLoss() {
		return this.estimateLoss;
	}

	/**
	 * 属性估损金额的setter方法
	 */
	public void setEstimateLoss(BigDecimal estimateLoss) {
		this.estimateLoss = estimateLoss;
	}

	/**
	 * 属性接案员姓名的getter方法
	 */

	@Column(name = "RECEIVERNAME")
	public String getReceiverName() {
		return this.receiverName;
	}

	/**
	 * 属性接案员姓名的setter方法
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * 属性经办人代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性经办人代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性归属业务员代码的getter方法
	 */

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return this.handler1Code;
	}

	/**
	 * 属性归属业务员代码的setter方法
	 */
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}

	/**
	 * 属性业务归属机构代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性业务归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性计算机输单日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性受理标志(Y/N)的getter方法
	 */

	@Column(name = "ACCEPTFLAG")
	public String getAcceptFlag() {
		return this.acceptFlag;
	}

	/**
	 * 属性受理标志(Y/N)的setter方法
	 */
	public void setAcceptFlag(String acceptFlag) {
		this.acceptFlag = acceptFlag;
	}

	/**
	 * 属性是否向别的保险公司投保(Y/N)的getter方法
	 */

	@Column(name = "REPEATINSUREFLAG")
	public String getRepeatInsureFlag() {
		return this.repeatInsureFlag;
	}

	/**
	 * 属性是否向别的保险公司投保(Y/N)的setter方法
	 */
	public void setRepeatInsureFlag(String repeatInsureFlag) {
		this.repeatInsureFlag = repeatInsureFlag;
	}

	/**
	 * 属性赔案类别的getter方法
	 */

	@Column(name = "CLAIMTYPE")
	public String getClaimType() {
		return this.claimType;
	}

	/**
	 * 属性赔案类别的setter方法
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	/**
	 * 属性注销/拒赔日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CANCELDATE")
	public Date getCancelDate() {
		return this.cancelDate;
	}

	/**
	 * 属性注销/拒赔日期的setter方法
	 */
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	/**
	 * 属性注销/拒赔人代码的getter方法
	 */

	@Column(name = "DEALERCODE")
	public String getDealerCode() {
		return this.dealerCode;
	}

	/**
	 * 属性注销/拒赔人代码的setter方法
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性理赔登记机构的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性理赔登记机构的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**
	 * 属性标志字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性报案人电话的getter方法
	 */

	@Column(name = "REPORTORPHONENUMBER")
	public String getReportorPhoneNumber() {
		return this.reportorPhoneNumber;
	}

	/**
	 * 属性报案人电话的setter方法
	 */
	public void setReportorPhoneNumber(String reportorPhoneNumber) {
		this.reportorPhoneNumber = reportorPhoneNumber;
	}

	/**
	 * 属性联系人邮编的getter方法
	 */

	@Column(name = "LINKERPOSTCODE")
	public String getLinkerPostCode() {
		return this.linkerPostCode;
	}

	/**
	 * 属性联系人邮编的setter方法
	 */
	public void setLinkerPostCode(String linkerPostCode) {
		this.linkerPostCode = linkerPostCode;
	}

	/**
	 * 属性联系人通讯地址的getter方法
	 */

	@Column(name = "LINKERADDRESS")
	public String getLinkerAddress() {
		return this.linkerAddress;
	}

	/**
	 * 属性联系人通讯地址的setter方法
	 */
	public void setLinkerAddress(String linkerAddress) {
		this.linkerAddress = linkerAddress;
	}

	/**
	 * 属性未决赔款准备金的getter方法
	 */

	@Column(name = "ESTIMATEFEE")
	public BigDecimal getEstimateFee() {
		return this.estimateFee;
	}

	/**
	 * 属性未决赔款准备金的setter方法
	 */
	public void setEstimateFee(BigDecimal estimateFee) {
		this.estimateFee = estimateFee;
	}

	/**
	 * 属性巨灾一级代码的getter方法
	 */

	@Column(name = "CATASTROPHECODE1")
	public String getCatastropheCode1() {
		return this.catastropheCode1;
	}

	/**
	 * 属性巨灾一级代码的setter方法
	 */
	public void setCatastropheCode1(String catastropheCode1) {
		this.catastropheCode1 = catastropheCode1;
	}

	/**
	 * 属性巨灾一级名称的getter方法
	 */

	@Column(name = "CATASTROPHENAME1")
	public String getCatastropheName1() {
		return this.catastropheName1;
	}

	/**
	 * 属性巨灾一级名称的setter方法
	 */
	public void setCatastropheName1(String catastropheName1) {
		this.catastropheName1 = catastropheName1;
	}

	/**
	 * 属性巨灾二级代码的getter方法
	 */

	@Column(name = "CATASTROPHECODE2")
	public String getCatastropheCode2() {
		return this.catastropheCode2;
	}

	/**
	 * 属性巨灾二级代码的setter方法
	 */
	public void setCatastropheCode2(String catastropheCode2) {
		this.catastropheCode2 = catastropheCode2;
	}

	/**
	 * 属性巨灾二级名称的getter方法
	 */

	@Column(name = "CATASTROPHENAME2")
	public String getCatastropheName2() {
		return this.catastropheName2;
	}

	/**
	 * 属性巨灾二级名称的setter方法
	 */
	public void setCatastropheName2(String catastropheName2) {
		this.catastropheName2 = catastropheName2;
	}

	/**
	 * 属性报案标志的getter方法
	 */

	@Column(name = "REPORTFLAG")
	public String getReportFlag() {
		return this.reportFlag;
	}

	/**
	 * 属性报案标志的setter方法
	 */
	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}

	/**
	 * 属性故责任类型的getter方法
	 */

	@Column(name = "INDEMNITYDUTY")
	public String getIndemnityDuty() {
		return this.indemnityDuty;
	}

	/**
	 * 属性故责任类型的setter方法
	 */
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}

	/**
	 * 属性是否是团单免导标志的getter方法
	 */

	@Column(name = "TERMFLAG")
	public String getTermFlag() {
		return this.termFlag;
	}

	/**
	 * 属性是否是团单免导标志的setter方法
	 */
	public void setTermFlag(String termFlag) {
		this.termFlag = termFlag;
	}

	/**
	 * 属性最新修改人姓名的getter方法
	 */

	@Column(name = "ALTERNAME")
	public String getAltername() {
		return this.altername;
	}

	/**
	 * 属性最新修改人姓名的setter方法
	 */
	public void setAltername(String altername) {
		this.altername = altername;
	}

	/**
	 * 属性最新修改人电话的getter方法
	 */

	@Column(name = "ALTERPHONENUMBER")
	public String getAlterphonenumber() {
		return this.alterphonenumber;
	}

	/**
	 * 属性最新修改人电话的setter方法
	 */
	public void setAlterphonenumber(String alterphonenumber) {
		this.alterphonenumber = alterphonenumber;
	}

	/**
	 * 属性最新修改人与被保险人关系的getter方法
	 */

	@Column(name = "ALTERRELATIONTYPE")
	public String getAlterRelationType() {
		return this.alterRelationType;
	}

	/**
	 * 属性最新修改人与被保险人关系的setter方法
	 */
	public void setAlterRelationType(String alterRelationType) {
		this.alterRelationType = alterRelationType;
	}

	/**
	 * 属性最新修改时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ALTERTIME")
	public Date getAlterTime() {
		return this.alterTime;
	}

	/**
	 * 属性最新修改时间的setter方法
	 */
	public void setAlterTime(Date alterTime) {
		this.alterTime = alterTime;
	}

	/**
	 * 属性报案修改轨迹的getter方法
	 */

	@Column(name = "ALTERLOCUS")
	public String getAlterLocus() {
		return this.alterLocus;
	}

	/**
	 * 属性报案修改轨迹的setter方法
	 */
	public void setAlterLocus(String alterLocus) {
		this.alterLocus = alterLocus;
	}

	/**
	 * 属性报案修改方式的getter方法
	 */

	@Column(name = "ALTERTYPE")
	public String getAlterType() {
		return this.alterType;
	}

	/**
	 * 属性报案修改方式的setter方法
	 */
	public void setAlterType(String alterType) {
		this.alterType = alterType;
	}

}
