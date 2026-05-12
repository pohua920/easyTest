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
 * POJO类PrpLsalvation特约救助表
 */
@Entity
@Table(name = "PRPLSALVATION")
public class PrpLsalvation implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLsalvationId id;

	/** 属性特约救助号 */
	private String salvationNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性牌照底色代码 */
	private String licenseColorCode;

	/** 属性发动机号 */
	private String engineNo;

	/** 属性车身颜色代码 */
	private String colorCode;

	/** 属性车型代码 */
	private String modelCode;

	/** 属性厂牌型号 */
	private String brandName;

	/** 属性驾驶员姓名 */
	private String driverName;

	/** 属性联系电话 */
	private String phone;

	/** 属性salvatype */
	private String salvatype;

	/** 属性发生事故时间 */
	private Date damageTime;

	/** 属性付费方式 */
	private String moneyFlag;

	/** 属性救助项目代码 */
	private String salvaItemCode;

	/** 属性救助项目名称 */
	private String salvaItemName;

	/** 属性救助地点 */
	private String salvaSite;

	/** 属性车辆送至地点 */
	private String sendSite;

	/** 属性救助协助单位名称 */
	private String salvaAssistUnit;

	/** 属性救助协助单位经办人名称 */
	private String salvaUnitHandler;

	/** 属性拖运路线 */
	private String salvaRoute;

	/** 属性救助距离 */
	private BigDecimal salvaMile;

	/** 属性承保公司代码 */
	private String insureUnitCode;

	/** 属性承保公司名称 */
	private String insureUnitName;

	/** 属性收费标准 */
	private String feeStandard;

	/** 属性救助费用 */
	private BigDecimal salvaFee;

	/** 属性救助车到达时间 */
	private Date arriveDate;

	/** 属性处理人员代码 */
	private String handlerCode;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性确认日期 */
	private Date verifyDate;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLsalvation的默认构造方法
	 */
	public PrpLsalvation() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLsalvationId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLsalvationId id) {
		this.id = id;
	}

	/**
	 * 属性特约救助号的getter方法
	 */

	@Column(name = "SALVATIONNO")
	public String getSalvationNo() {
		return this.salvationNo;
	}

	/**
	 * 属性特约救助号的setter方法
	 */
	public void setSalvationNo(String salvationNo) {
		this.salvationNo = salvationNo;
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
	 * 属性牌照底色代码的getter方法
	 */

	@Column(name = "LICENSECOLORCODE")
	public String getLicenseColorCode() {
		return this.licenseColorCode;
	}

	/**
	 * 属性牌照底色代码的setter方法
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
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
	 * 属性车身颜色代码的getter方法
	 */

	@Column(name = "COLORCODE")
	public String getColorCode() {
		return this.colorCode;
	}

	/**
	 * 属性车身颜色代码的setter方法
	 */
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	/**
	 * 属性车型代码的getter方法
	 */

	@Column(name = "MODELCODE")
	public String getModelCode() {
		return this.modelCode;
	}

	/**
	 * 属性车型代码的setter方法
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
	 * 属性驾驶员姓名的getter方法
	 */

	@Column(name = "DRIVERNAME")
	public String getDriverName() {
		return this.driverName;
	}

	/**
	 * 属性驾驶员姓名的setter方法
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * 属性联系电话的getter方法
	 */

	@Column(name = "PHONE")
	public String getPhone() {
		return this.phone;
	}

	/**
	 * 属性联系电话的setter方法
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 属性salvatype的getter方法
	 */

	@Column(name = "SALVATYPE")
	public String getSalvatype() {
		return this.salvatype;
	}

	/**
	 * 属性salvatype的setter方法
	 */
	public void setSalvatype(String salvatype) {
		this.salvatype = salvatype;
	}

	/**
	 * 属性发生事故时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DAMAGETIME")
	public Date getDamageTime() {
		return this.damageTime;
	}

	/**
	 * 属性发生事故时间的setter方法
	 */
	public void setDamageTime(Date damageTime) {
		this.damageTime = damageTime;
	}

	/**
	 * 属性付费方式的getter方法
	 */

	@Column(name = "MONEYFLAG")
	public String getMoneyFlag() {
		return this.moneyFlag;
	}

	/**
	 * 属性付费方式的setter方法
	 */
	public void setMoneyFlag(String moneyFlag) {
		this.moneyFlag = moneyFlag;
	}

	/**
	 * 属性救助项目代码的getter方法
	 */

	@Column(name = "SALVAITEMCODE")
	public String getSalvaItemCode() {
		return this.salvaItemCode;
	}

	/**
	 * 属性救助项目代码的setter方法
	 */
	public void setSalvaItemCode(String salvaItemCode) {
		this.salvaItemCode = salvaItemCode;
	}

	/**
	 * 属性救助项目名称的getter方法
	 */

	@Column(name = "SALVAITEMNAME")
	public String getSalvaItemName() {
		return this.salvaItemName;
	}

	/**
	 * 属性救助项目名称的setter方法
	 */
	public void setSalvaItemName(String salvaItemName) {
		this.salvaItemName = salvaItemName;
	}

	/**
	 * 属性救助地点的getter方法
	 */

	@Column(name = "SALVASITE")
	public String getSalvaSite() {
		return this.salvaSite;
	}

	/**
	 * 属性救助地点的setter方法
	 */
	public void setSalvaSite(String salvaSite) {
		this.salvaSite = salvaSite;
	}

	/**
	 * 属性车辆送至地点的getter方法
	 */

	@Column(name = "SENDSITE")
	public String getSendSite() {
		return this.sendSite;
	}

	/**
	 * 属性车辆送至地点的setter方法
	 */
	public void setSendSite(String sendSite) {
		this.sendSite = sendSite;
	}

	/**
	 * 属性救助协助单位名称的getter方法
	 */

	@Column(name = "SALVAASSISTUNIT")
	public String getSalvaAssistUnit() {
		return this.salvaAssistUnit;
	}

	/**
	 * 属性救助协助单位名称的setter方法
	 */
	public void setSalvaAssistUnit(String salvaAssistUnit) {
		this.salvaAssistUnit = salvaAssistUnit;
	}

	/**
	 * 属性救助协助单位经办人名称的getter方法
	 */

	@Column(name = "SALVAUNITHANDLER")
	public String getSalvaUnitHandler() {
		return this.salvaUnitHandler;
	}

	/**
	 * 属性救助协助单位经办人名称的setter方法
	 */
	public void setSalvaUnitHandler(String salvaUnitHandler) {
		this.salvaUnitHandler = salvaUnitHandler;
	}

	/**
	 * 属性拖运路线的getter方法
	 */

	@Column(name = "SALVAROUTE")
	public String getSalvaRoute() {
		return this.salvaRoute;
	}

	/**
	 * 属性拖运路线的setter方法
	 */
	public void setSalvaRoute(String salvaRoute) {
		this.salvaRoute = salvaRoute;
	}

	/**
	 * 属性救助距离的getter方法
	 */

	@Column(name = "SALVAMILE")
	public BigDecimal getSalvaMile() {
		return this.salvaMile;
	}

	/**
	 * 属性救助距离的setter方法
	 */
	public void setSalvaMile(BigDecimal salvaMile) {
		this.salvaMile = salvaMile;
	}

	/**
	 * 属性承保公司代码的getter方法
	 */

	@Column(name = "INSUREUNITCODE")
	public String getInsureUnitCode() {
		return this.insureUnitCode;
	}

	/**
	 * 属性承保公司代码的setter方法
	 */
	public void setInsureUnitCode(String insureUnitCode) {
		this.insureUnitCode = insureUnitCode;
	}

	/**
	 * 属性承保公司名称的getter方法
	 */

	@Column(name = "INSUREUNITNAME")
	public String getInsureUnitName() {
		return this.insureUnitName;
	}

	/**
	 * 属性承保公司名称的setter方法
	 */
	public void setInsureUnitName(String insureUnitName) {
		this.insureUnitName = insureUnitName;
	}

	/**
	 * 属性收费标准的getter方法
	 */

	@Column(name = "FEESTANDARD")
	public String getFeeStandard() {
		return this.feeStandard;
	}

	/**
	 * 属性收费标准的setter方法
	 */
	public void setFeeStandard(String feeStandard) {
		this.feeStandard = feeStandard;
	}

	/**
	 * 属性救助费用的getter方法
	 */

	@Column(name = "SALVAFEE")
	public BigDecimal getSalvaFee() {
		return this.salvaFee;
	}

	/**
	 * 属性救助费用的setter方法
	 */
	public void setSalvaFee(BigDecimal salvaFee) {
		this.salvaFee = salvaFee;
	}

	/**
	 * 属性救助车到达时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ARRIVEDATE")
	public Date getArriveDate() {
		return this.arriveDate;
	}

	/**
	 * 属性救助车到达时间的setter方法
	 */
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}

	/**
	 * 属性处理人员代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性处理人员代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
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
	 * 属性确认日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VERIFYDATE")
	public Date getVerifyDate() {
		return this.verifyDate;
	}

	/**
	 * 属性确认日期的setter方法
	 */
	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
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

}
