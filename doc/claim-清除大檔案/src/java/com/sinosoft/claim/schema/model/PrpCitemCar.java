package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

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
 * POJO类PrpCitemCar
 */
@Entity
@Table(name = "PRPCITEMCAR")
public class PrpCitemCar implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCitemCarId id;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保户类别代码 */
	private String insuredTypeCode;

	/** 属性被保险人与车辆关系 */
	private String carInsuredRelation;

	/** 属性车主 */
	private String carOwner;

	/** 属性条款类别 */
	private String clauseType;

	/** 属性是否约定驾驶员标志 */
	private String agreeDriverFlag;

	/** 属性是否投保新增设备标志 */
	private String newDeviceFlag;

	/** 属性车险保单号 */
	private String carPolicyno;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性车牌底色代码 */
	private String licenseColorCode;

	/** 属性车辆种类代码 */
	private String carKindCode;

	/** 属性是否港澳车标志 */
	private String hkFlag;

	/** 属性港澳车牌号码 */
	private String hkLicenseNo;

	/** 属性发动机号 */
	private String engineNo;

	/** 属性VIN号 */
	private String vinNo;

	/** 属性车架号 */
	private String frameNo;

	/** 属性行驶区域代码 */
	private String runAreaCode;

	/** 属性行驶区域名称 */
	private String runAreaName;

	/** 属性行驶里程(公里) */
	private Double runMiles;

	/** 属性初登日期 */
	private Date enrollDate;

	/** 属性使用年限 */
	private Integer useYears;

	/** 属性车型代码 */
	private String modelCode;

	/** 属性厂牌型号名称 */
	private String brandName;

	/** 属性国别性质 */
	private String countryNature;

	/** 属性生产国家代码 */
	private String countryCode;

	/** 属性使用性质代码 */
	private String useNatureCode;

	/** 属性营业性质分类代码 */
	private String businessClassCode;

	/** 属性座位数 */
	private Integer seatCount;

	/** 属性吨位数 */
	private Double tonCount;

	/** 属性排量 */
	private Double exhaustScale;

	/** 属性车身颜色代码 */
	private String colorCode;

	/** 属性安全配置 */
	private String safeDevice;

	/** 属性固定停放地点 */
	private String parkSite;

	/** 属性购车人地址 */
	private String ownerAddress;

	/** 属性其他性质 */
	private String otherNature;

	/** 属性费率号次 */
	private String rateCode;

	/** 属性生产日期 */
	private Date makeDate;

	/** 属性购车用途 */
	private String carUsage;

	/** 属性币别代码 */
	private String currency;

	/** 属性新车重置价格 */
	private Double purchasePrice;

	/** 属性实际价值 */
	private Double actualValue;

	/** 属性购车发票号 */
	private String invoiceNo;

	/** 属性是否在我公司投保信用或保证保险 */
	private String carLoanFlag;

	/** 属性承保公司代码 */
	private String insurerCode;

	/** 属性上期承保公司 */
	private String lastInsurer;

	/** 属性验车情况 */
	private String carCheckStatus;

	/** 属性验车人 */
	private String carChecker;

	/** 属性验车时间 */
	private String carCheckTime;

	/** 属性无赔款优待 */
	private Double specialTreat;

	/** 属性救助区域 */
	private String relievingAreaCode;

	/** 属性附加险数量 */
	private Integer addonCount;

	/** 属性经销商代码 */
	private String carDealerCode;

	/** 属性经销商名称 */
	private String carDealerName;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性CARCHECKREASON */
	private String carCheckReason;

	/** 属性LVIOLATEDTIMES */
	private Integer lvioLatedTimes;

	/** 属性SVIOLATEDTIMES */
	private Integer svioLatedTimes;

	/** 属性LICENSEKINDCODE */
	private String licenseKindCode;

	/** 属性REGISTMODELCODE */
	private String registModelCode;

	/** 属性SECONDHANDCARFLAG */
	private String secondHandCarFlag;

	/** 属性SECONDHANDCARPRICE */
	private Double secondHandCarPrice;

	/** 属性RUNAREADESC */
	private String runareaDesc;

	/** 属性VISACODE */
	private String visaCode;

	/** 属性ORIGINCARPRICE */
	private Double originCarPrice;

	/** 属性VEHICLESTYLE */
	private String vehicleStyle;

	/** 属性WHOLEWEIGHT */
	private Double wholeWeight;

	/** 属性VEHICLEBRAND */
	private String vehiclebRand;

	/** 属性VEHICLESTYLEDESC */
	private String vehicleStyleDesc;

	/** 属性CARMODELID */
	private String carModelid;

	/** 属性VEHICLECODE */
	private String vehicleCode;

	/** 属性CHGOWNERFLAG */
	private String chgownerFlag;

	/** 属性LOANVEHICLEFLAG */
	private String loanvehicleFlag;

	/** 属性TRANSFERDATE */
	private String transferDate;

	/**
	 * 类PrpCitemCar的默认构造方法
	 */
	public PrpCitemCar() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "itemNo", column = @Column(name = "ITEMNO")) })
	public PrpCitemCarId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCitemCarId id) {
		this.id = id;
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
	 * 属性保户类别代码的getter方法
	 */

	@Column(name = "INSUREDTYPECODE")
	public String getInsuredTypeCode() {
		return this.insuredTypeCode;
	}

	/**
	 * 属性保户类别代码的setter方法
	 */
	public void setInsuredTypeCode(String insuredTypeCode) {
		this.insuredTypeCode = insuredTypeCode;
	}

	/**
	 * 属性被保险人与车辆关系的getter方法
	 */

	@Column(name = "CARINSUREDRELATION")
	public String getCarInsuredRelation() {
		return this.carInsuredRelation;
	}

	/**
	 * 属性被保险人与车辆关系的setter方法
	 */
	public void setCarInsuredRelation(String carInsuredRelation) {
		this.carInsuredRelation = carInsuredRelation;
	}

	/**
	 * 属性车主的getter方法
	 */

	@Column(name = "CAROWNER")
	public String getCarOwner() {
		return this.carOwner;
	}

	/**
	 * 属性车主的setter方法
	 */
	public void setCarOwner(String carOwner) {
		this.carOwner = carOwner;
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
	 * 属性是否约定驾驶员标志的getter方法
	 */

	@Column(name = "AGREEDRIVERFLAG")
	public String getAgreeDriverFlag() {
		return this.agreeDriverFlag;
	}

	/**
	 * 属性是否约定驾驶员标志的setter方法
	 */
	public void setAgreeDriverFlag(String agreeDriverFlag) {
		this.agreeDriverFlag = agreeDriverFlag;
	}

	/**
	 * 属性是否投保新增设备标志的getter方法
	 */

	@Column(name = "NEWDEVICEFLAG")
	public String getNewDeviceFlag() {
		return this.newDeviceFlag;
	}

	/**
	 * 属性是否投保新增设备标志的setter方法
	 */
	public void setNewDeviceFlag(String newDeviceFlag) {
		this.newDeviceFlag = newDeviceFlag;
	}

	/**
	 * 属性车险保单号的getter方法
	 */

	@Column(name = "CARPOLICYNO")
	public String getCarPolicyno() {
		return this.carPolicyno;
	}

	/**
	 * 属性车险保单号的setter方法
	 */
	public void setCarPolicyno(String carPolicyno) {
		this.carPolicyno = carPolicyno;
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
	 * 属性是否港澳车标志的getter方法
	 */

	@Column(name = "HKFLAG")
	public String getHkFlag() {
		return this.hkFlag;
	}

	/**
	 * 属性是否港澳车标志的setter方法
	 */
	public void setHkFlag(String hkFlag) {
		this.hkFlag = hkFlag;
	}

	/**
	 * 属性港澳车牌号码的getter方法
	 */

	@Column(name = "HKLICENSENO")
	public String getHkLicenseNo() {
		return this.hkLicenseNo;
	}

	/**
	 * 属性港澳车牌号码的setter方法
	 */
	public void setHkLicenseNo(String hkLicenseNo) {
		this.hkLicenseNo = hkLicenseNo;
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
	 * 属性VIN号的getter方法
	 */

	@Column(name = "VINNO")
	public String getVinNo() {
		return this.vinNo;
	}

	/**
	 * 属性VIN号的setter方法
	 */
	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
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
	 * 属性行驶区域代码的getter方法
	 */

	@Column(name = "RUNAREACODE")
	public String getRunAreaCode() {
		return this.runAreaCode;
	}

	/**
	 * 属性行驶区域代码的setter方法
	 */
	public void setRunAreaCode(String runAreaCode) {
		this.runAreaCode = runAreaCode;
	}

	/**
	 * 属性行驶区域名称的getter方法
	 */

	@Column(name = "RUNAREANAME")
	public String getRunAreaName() {
		return this.runAreaName;
	}

	/**
	 * 属性行驶区域名称的setter方法
	 */
	public void setRunAreaName(String runAreaName) {
		this.runAreaName = runAreaName;
	}

	/**
	 * 属性行驶里程(公里)的getter方法
	 */

	@Column(name = "RUNMILES")
	public Double getRunMiles() {
		return this.runMiles;
	}

	/**
	 * 属性行驶里程(公里)的setter方法
	 */
	public void setRunMiles(Double runMiles) {
		this.runMiles = runMiles;
	}

	/**
	 * 属性初登日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENROLLDATE")
	public Date getEnrollDate() {
		return this.enrollDate;
	}

	/**
	 * 属性初登日期的setter方法
	 */
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	/**
	 * 属性使用年限的getter方法
	 */
	@Column(name = "USEYEARS")
	public Integer getUseYears() {
		if(this.useYears==null){
			return 0;
		}
		return this.useYears;
	}

	/**
	 * 属性使用年限的setter方法
	 */
	public void setUseYears(Integer useYears) {
		this.useYears = useYears;
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
	 * 属性厂牌型号名称的getter方法
	 */

	@Column(name = "BRANDNAME")
	public String getBrandName() {
		return this.brandName;
	}

	/**
	 * 属性厂牌型号名称的setter方法
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 属性国别性质的getter方法
	 */

	@Column(name = "COUNTRYNATURE")
	public String getCountryNature() {
		return this.countryNature;
	}

	/**
	 * 属性国别性质的setter方法
	 */
	public void setCountryNature(String countryNature) {
		this.countryNature = countryNature;
	}

	/**
	 * 属性生产国家代码的getter方法
	 */

	@Column(name = "COUNTRYCODE")
	public String getCountryCode() {
		return this.countryCode;
	}

	/**
	 * 属性生产国家代码的setter方法
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * 属性使用性质代码的getter方法
	 */

	@Column(name = "USENATURECODE")
	public String getUseNatureCode() {
		return this.useNatureCode;
	}

	/**
	 * 属性使用性质代码的setter方法
	 */
	public void setUseNatureCode(String useNatureCode) {
		this.useNatureCode = useNatureCode;
	}

	/**
	 * 属性营业性质分类代码的getter方法
	 */

	@Column(name = "BUSINESSCLASSCODE")
	public String getBusinessClassCode() {
		return this.businessClassCode;
	}

	/**
	 * 属性营业性质分类代码的setter方法
	 */
	public void setBusinessClassCode(String businessClassCode) {
		this.businessClassCode = businessClassCode;
	}

	/**
	 * 属性座位数的getter方法
	 */

	@Column(name = "SEATCOUNT")
	public Integer getSeatCount() {
		if(this.seatCount==null){
			return 0;
		}
		return this.seatCount;
	}

	/**
	 * 属性座位数的setter方法
	 */
	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	/**
	 * 属性吨位数的getter方法
	 */

	@Column(name = "TONCOUNT")
	public Double getTonCount() {
		if(this.tonCount==null){
			return 0D;
		}
		return this.tonCount;
	}

	/**
	 * 属性吨位数的setter方法
	 */
	public void setTonCount(Double tonCount) {
		this.tonCount = tonCount;
	}

	/**
	 * 属性排量的getter方法
	 */

	@Column(name = "EXHAUSTSCALE")
	public Double getExhaustScale() {
		return this.exhaustScale;
	}

	/**
	 * 属性排量的setter方法
	 */
	public void setExhaustScale(Double exhaustScale) {
		this.exhaustScale = exhaustScale;
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
	 * 属性安全配置的getter方法
	 */

	@Column(name = "SAFEDEVICE")
	public String getSafeDevice() {
		return this.safeDevice;
	}

	/**
	 * 属性安全配置的setter方法
	 */
	public void setSafeDevice(String safeDevice) {
		this.safeDevice = safeDevice;
	}

	/**
	 * 属性固定停放地点的getter方法
	 */

	@Column(name = "PARKSITE")
	public String getParkSite() {
		return this.parkSite;
	}

	/**
	 * 属性固定停放地点的setter方法
	 */
	public void setParkSite(String parkSite) {
		this.parkSite = parkSite;
	}

	/**
	 * 属性购车人地址的getter方法
	 */

	@Column(name = "OWNERADDRESS")
	public String getOwnerAddress() {
		return this.ownerAddress;
	}

	/**
	 * 属性购车人地址的setter方法
	 */
	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	/**
	 * 属性其他性质的getter方法
	 */

	@Column(name = "OTHERNATURE")
	public String getOtherNature() {
		return this.otherNature;
	}

	/**
	 * 属性其他性质的setter方法
	 */
	public void setOtherNature(String otherNature) {
		this.otherNature = otherNature;
	}

	/**
	 * 属性费率号次的getter方法
	 */

	@Column(name = "RATECODE")
	public String getRateCode() {
		return this.rateCode;
	}

	/**
	 * 属性费率号次的setter方法
	 */
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	/**
	 * 属性生产日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "MAKEDATE")
	public Date getMakeDate() {
		return this.makeDate;
	}

	/**
	 * 属性生产日期的setter方法
	 */
	public void setMakeDate(Date makeDate) {
		this.makeDate = makeDate;
	}

	/**
	 * 属性购车用途的getter方法
	 */

	@Column(name = "CARUSAGE")
	public String getCarUsage() {
		return this.carUsage;
	}

	/**
	 * 属性购车用途的setter方法
	 */
	public void setCarUsage(String carUsage) {
		this.carUsage = carUsage;
	}

	/**
	 * 属性币别代码的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别代码的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性新车重置价格的getter方法
	 */

	@Column(name = "PURCHASEPRICE")
	public Double getPurchasePrice() {
		if(this.purchasePrice==null){
			this.purchasePrice = 0d;
		}
		return this.purchasePrice;
	}

	/**
	 * 属性新车重置价格的setter方法
	 */
	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * 属性实际价值的getter方法
	 */

	@Column(name = "ACTUALVALUE")
	public Double getActualValue() {
		return this.actualValue;
	}

	/**
	 * 属性实际价值的setter方法
	 */
	public void setActualValue(Double actualValue) {
		this.actualValue = actualValue;
	}

	/**
	 * 属性购车发票号的getter方法
	 */

	@Column(name = "INVOICENO")
	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	/**
	 * 属性购车发票号的setter方法
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * 属性是否在我公司投保信用或保证保险的getter方法
	 */

	@Column(name = "CARLOANFLAG")
	public String getCarLoanFlag() {
		return this.carLoanFlag;
	}

	/**
	 * 属性是否在我公司投保信用或保证保险的setter方法
	 */
	public void setCarLoanFlag(String carLoanFlag) {
		this.carLoanFlag = carLoanFlag;
	}

	/**
	 * 属性承保公司代码的getter方法
	 */

	@Column(name = "INSURERCODE")
	public String getInsurerCode() {
		return this.insurerCode;
	}

	/**
	 * 属性承保公司代码的setter方法
	 */
	public void setInsurerCode(String insurerCode) {
		this.insurerCode = insurerCode;
	}

	/**
	 * 属性上期承保公司的getter方法
	 */

	@Column(name = "LASTINSURER")
	public String getLastInsurer() {
		return this.lastInsurer;
	}

	/**
	 * 属性上期承保公司的setter方法
	 */
	public void setLastInsurer(String lastInsurer) {
		this.lastInsurer = lastInsurer;
	}

	/**
	 * 属性验车情况的getter方法
	 */

	@Column(name = "CARCHECKSTATUS")
	public String getCarCheckStatus() {
		return this.carCheckStatus;
	}

	/**
	 * 属性验车情况的setter方法
	 */
	public void setCarCheckStatus(String carCheckStatus) {
		this.carCheckStatus = carCheckStatus;
	}

	/**
	 * 属性验车人的getter方法
	 */

	@Column(name = "CARCHECKER")
	public String getCarChecker() {
		return this.carChecker;
	}

	/**
	 * 属性验车人的setter方法
	 */
	public void setCarChecker(String carChecker) {
		this.carChecker = carChecker;
	}

	/**
	 * 属性验车时间的getter方法
	 */

	@Column(name = "CARCHECKTIME")
	public String getCarCheckTime() {
		return this.carCheckTime;
	}

	/**
	 * 属性验车时间的setter方法
	 */
	public void setCarCheckTime(String carCheckTime) {
		this.carCheckTime = carCheckTime;
	}

	/**
	 * 属性无赔款优待的getter方法
	 */

	@Column(name = "SPECIALTREAT")
	public Double getSpecialTreat() {
		return this.specialTreat;
	}

	/**
	 * 属性无赔款优待的setter方法
	 */
	public void setSpecialTreat(Double specialTreat) {
		this.specialTreat = specialTreat;
	}

	/**
	 * 属性救助区域的getter方法
	 */

	@Column(name = "RELIEVINGAREACODE")
	public String getRelievingAreaCode() {
		return this.relievingAreaCode;
	}

	/**
	 * 属性救助区域的setter方法
	 */
	public void setRelievingAreaCode(String relievingAreaCode) {
		this.relievingAreaCode = relievingAreaCode;
	}

	/**
	 * 属性附加险数量的getter方法
	 */

	@Column(name = "ADDONCOUNT")
	public Integer getAddonCount() {
		return this.addonCount;
	}

	/**
	 * 属性附加险数量的setter方法
	 */
	public void setAddonCount(Integer addonCount) {
		this.addonCount = addonCount;
	}

	/**
	 * 属性经销商代码的getter方法
	 */

	@Column(name = "CARDEALERCODE")
	public String getCarDealerCode() {
		return this.carDealerCode;
	}

	/**
	 * 属性经销商代码的setter方法
	 */
	public void setCarDealerCode(String carDealerCode) {
		this.carDealerCode = carDealerCode;
	}

	/**
	 * 属性经销商名称的getter方法
	 */

	@Column(name = "CARDEALERNAME")
	public String getCarDealerName() {
		return this.carDealerName;
	}

	/**
	 * 属性经销商名称的setter方法
	 */
	public void setCarDealerName(String carDealerName) {
		this.carDealerName = carDealerName;
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
	 * 属性CARCHECKREASON的getter方法
	 */

	@Column(name = "CARCHECKREASON")
	public String getCarCheckReason() {
		return this.carCheckReason;
	}

	/**
	 * 属性CARCHECKREASON的setter方法
	 */
	public void setCarCheckReason(String carCheckReason) {
		this.carCheckReason = carCheckReason;
	}

	/**
	 * 属性LVIOLATEDTIMES的getter方法
	 */

	@Column(name = "LVIOLATEDTIMES")
	public Integer getLvioLatedTimes() {
		return this.lvioLatedTimes;
	}

	/**
	 * 属性LVIOLATEDTIMES的setter方法
	 */
	public void setLvioLatedTimes(Integer lvioLatedTimes) {
		this.lvioLatedTimes = lvioLatedTimes;
	}

	/**
	 * 属性SVIOLATEDTIMES的getter方法
	 */

	@Column(name = "SVIOLATEDTIMES")
	public Integer getSvioLatedTimes() {
		return this.svioLatedTimes;
	}

	/**
	 * 属性SVIOLATEDTIMES的setter方法
	 */
	public void setSvioLatedTimes(Integer svioLatedTimes) {
		this.svioLatedTimes = svioLatedTimes;
	}

	/**
	 * 属性LICENSEKINDCODE的getter方法
	 */

	@Column(name = "LICENSEKINDCODE")
	public String getLicenseKindCode() {
		return this.licenseKindCode;
	}

	/**
	 * 属性LICENSEKINDCODE的setter方法
	 */
	public void setLicenseKindCode(String licenseKindCode) {
		this.licenseKindCode = licenseKindCode;
	}

	/**
	 * 属性REGISTMODELCODE的getter方法
	 */

	@Column(name = "REGISTMODELCODE")
	public String getRegistModelCode() {
		return this.registModelCode;
	}

	/**
	 * 属性REGISTMODELCODE的setter方法
	 */
	public void setRegistModelCode(String registModelCode) {
		this.registModelCode = registModelCode;
	}

	/**
	 * 属性SECONDHANDCARFLAG的getter方法
	 */

	@Column(name = "SECONDHANDCARFLAG")
	public String getSecondHandCarFlag() {
		return this.secondHandCarFlag;
	}

	/**
	 * 属性SECONDHANDCARFLAG的setter方法
	 */
	public void setSecondHandCarFlag(String secondHandCarFlag) {
		this.secondHandCarFlag = secondHandCarFlag;
	}

	/**
	 * 属性SECONDHANDCARPRICE的getter方法
	 */

	@Column(name = "SECONDHANDCARPRICE")
	public Double getSecondHandCarPrice() {
		return this.secondHandCarPrice;
	}

	/**
	 * 属性SECONDHANDCARPRICE的setter方法
	 */
	public void setSecondHandCarPrice(Double secondHandCarPrice) {
		this.secondHandCarPrice = secondHandCarPrice;
	}

	/**
	 * 属性RUNAREADESC的getter方法
	 */

	@Column(name = "RUNAREADESC")
	public String getRunareaDesc() {
		return this.runareaDesc;
	}

	/**
	 * 属性RUNAREADESC的setter方法
	 */
	public void setRunareaDesc(String runareaDesc) {
		this.runareaDesc = runareaDesc;
	}

	/**
	 * 属性VISACODE的getter方法
	 */

	@Column(name = "VISACODE")
	public String getVisaCode() {
		return this.visaCode;
	}

	/**
	 * 属性VISACODE的setter方法
	 */
	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}

	/**
	 * 属性ORIGINCARPRICE的getter方法
	 */

	@Column(name = "ORIGINCARPRICE")
	public Double getOriginCarPrice() {
		return this.originCarPrice;
	}

	/**
	 * 属性ORIGINCARPRICE的setter方法
	 */
	public void setOriginCarPrice(Double originCarPrice) {
		this.originCarPrice = originCarPrice;
	}

	/**
	 * 属性VEHICLESTYLE的getter方法
	 */

	@Column(name = "VEHICLESTYLE")
	public String getVehicleStyle() {
		return this.vehicleStyle;
	}

	/**
	 * 属性VEHICLESTYLE的setter方法
	 */
	public void setVehicleStyle(String vehicleStyle) {
		this.vehicleStyle = vehicleStyle;
	}

	/**
	 * 属性WHOLEWEIGHT的getter方法
	 */

	@Column(name = "WHOLEWEIGHT")
	public Double getWholeWeight() {
		return this.wholeWeight;
	}

	/**
	 * 属性WHOLEWEIGHT的setter方法
	 */
	public void setWholeWeight(Double wholeWeight) {
		this.wholeWeight = wholeWeight;
	}

	/**
	 * 属性VEHICLEBRAND的getter方法
	 */

	@Column(name = "VEHICLEBRAND")
	public String getVehiclebRand() {
		return this.vehiclebRand;
	}

	/**
	 * 属性VEHICLEBRAND的setter方法
	 */
	public void setVehiclebRand(String vehiclebRand) {
		this.vehiclebRand = vehiclebRand;
	}

	/**
	 * 属性VEHICLESTYLEDESC的getter方法
	 */

	@Column(name = "VEHICLESTYLEDESC")
	public String getVehicleStyleDesc() {
		return this.vehicleStyleDesc;
	}

	/**
	 * 属性VEHICLESTYLEDESC的setter方法
	 */
	public void setVehicleStyleDesc(String vehicleStyleDesc) {
		this.vehicleStyleDesc = vehicleStyleDesc;
	}

	/**
	 * 属性CARMODELID的getter方法
	 */

	@Column(name = "CARMODELID")
	public String getCarModelid() {
		return this.carModelid;
	}

	/**
	 * 属性CARMODELID的setter方法
	 */
	public void setCarModelid(String carModelid) {
		this.carModelid = carModelid;
	}

	/**
	 * 属性VEHICLECODE的getter方法
	 */

	@Column(name = "VEHICLECODE")
	public String getVehicleCode() {
		return this.vehicleCode;
	}

	/**
	 * 属性VEHICLECODE的setter方法
	 */
	public void setVehicleCode(String vehicleCode) {
		this.vehicleCode = vehicleCode;
	}

	/**
	 * 属性CHGOWNERFLAG的getter方法
	 */

	@Column(name = "CHGOWNERFLAG")
	public String getChgownerFlag() {
		return this.chgownerFlag;
	}

	/**
	 * 属性CHGOWNERFLAG的setter方法
	 */
	public void setChgownerFlag(String chgownerFlag) {
		this.chgownerFlag = chgownerFlag;
	}

	/**
	 * 属性LOANVEHICLEFLAG的getter方法
	 */

	@Column(name = "LOANVEHICLEFLAG")
	public String getLoanvehicleFlag() {
		return this.loanvehicleFlag;
	}

	/**
	 * 属性LOANVEHICLEFLAG的setter方法
	 */
	public void setLoanvehicleFlag(String loanvehicleFlag) {
		this.loanvehicleFlag = loanvehicleFlag;
	}

	/**
	 * 属性TRANSFERDATE的getter方法
	 */

	@Column(name = "TRANSFERDATE")
	public String getTransferDate() {
		return this.transferDate;
	}

	/**
	 * 属性TRANSFERDATE的setter方法
	 */
	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

}
