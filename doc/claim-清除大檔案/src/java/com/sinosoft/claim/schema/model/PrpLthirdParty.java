package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.sinosoft.claim.dto.domain.PrpLdriverDto;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLthirdParty理赔车辆信息
 */
@Entity
@Table(name = "PRPLTHIRDPARTY")
public class PrpLthirdParty implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLthirdPartyId id;

	/** 属性报案信息表 */
	private PrpLregist prpLregist;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性立案号码 */
	private String claimNo;

	/** 属性条款类别 */
	private String clauseType;

	/** 属性车牌号 */
	private String licenseNo;

	/** 属性车牌底色代码 */
	private String licenseColorCode;

	/** 属性车辆种类代码 */
	private String carKindCode;

	/** 属性是否为本保单车辆(1.是2.否) */
	private String insureCarFlag;

	/** 属性车主 */
	private String carOwner;

	/** 属性发动机号 */
	private String engineNo;

	/** 属性车架号 */
	private String frameNo;

	/** 属性车型代码 */
	private String modelCode;

	/** 属性厂牌型号 */
	private String brandName;

	/** 属性车辆已行驶公里数 */
	private Double runDistance;

	/** 属性车辆实际使用年限 */
	private Integer useYears;

	/** 属性本车责任比例 */
	private Double dutyPercent;

	/** 属性是否保险（0-否1-是2-未知） */
	private String insuredFlag;

	/** 属性承保公司代码 */
	private String insureComCode;

	/** 属性承保公司名称 */
	private String insureComName;

	/** 属性状态字段 */
	private String flag;

	/** 属性VIN号码 */
	private String VINNo;

	/** 属性LOSSFLAG */
	private String lossFlag;

	/** 属性显示列表 */
	private List<PrpLthirdParty> thirdPartyList;

	/** 属性驾驶员信息 */
	private PrpLdriverDto prpLdriverDto;

	/** 属性调度处理标志 */
	private String scheduleType = "";

	/** 属性是否选择发送 */
	private String selectSend = "";
	/** 属性是哪个节点的调用 */
	private String nodeType = "";
	/** 属性是险别 */
	private String prpLthirdPartyKindCode = "";
	/** 属性驾驶员名称 */
	private String driverName = "";
	/** 属性驾驶员电话 */
	private String driverSeaRoute = "";
	/** 属性驾驶员证件号码 */
	private String drivingLicenseNo = "";
	/** 属性驾驶员证件类型 */
	private String drivingCarType = "";
	/** 属性驾驶员属地代码 */
	private String apanageCode = "";
	/** 属性驾驶员属地名称 */
	private String apanage = "";
	/** 属性性别 */
	private String driverSex = "";
	/** 修車廠負責人姓名 */
	private String garageHeadName = "";
	/** 本車駕駛人與被保險人關係 */
	private String relationship = "";
	/** 保車駕駛地址，財車駕駛地址 */
	private String drivingAddress = "";
	/** 乘載單位 */
	private String carryingUnit = "";
	/** 強制保險證編號 */
	private String insuranceNo = "";
	/** 是否有保強制險 1-有強制保險，0-沒有強制保險 */
	private String isInsurance = "";
	/** 承載數量 */
	private Long carryingNumber;
	/** 財車車主 */
	private String carsOwners = "";
	/** 被保險人身分 */
	private String insuredIdentity = "";

	/**
	 * 类PrpLthirdParty的默认构造方法
	 */
	public PrpLthirdParty() {
		id = new PrpLthirdPartyId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLthirdPartyId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLthirdPartyId id) {
		this.id = id;
	}

	/**
	 * 属性报案信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTNO", nullable = false, insertable = false, updatable = false)
	public PrpLregist getPrpLregist() {
		return this.prpLregist;
	}

	/**
	 * 属性报案信息表的setter方法
	 */
	public void setPrpLregist(PrpLregist prpLregist) {
		this.prpLregist = prpLregist;
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
	 * 属性立案号码的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号码的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	 * 属性车牌号的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号的setter方法
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
	 * 属性是否为本保单车辆(1.是2.否)的getter方法
	 */

	@Column(name = "INSURECARFLAG")
	public String getInsureCarFlag() {
		return this.insureCarFlag;
	}

	/**
	 * 属性是否为本保单车辆(1.是2.否)的setter方法
	 */
	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
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
	 * 属性车辆已行驶公里数的getter方法
	 */

	@Column(name = "RUNDISTANCE")
	public Double getRunDistance() {
		return this.runDistance;
	}

	/**
	 * 属性车辆已行驶公里数的setter方法
	 */
	public void setRunDistance(Double runDistance) {
		this.runDistance = runDistance;
	}

	/**
	 * 属性车辆实际使用年限的getter方法
	 */

	@Column(name = "USEYEARS")
	public Integer getUseYears() {
		return this.useYears;
	}

	/**
	 * 属性车辆实际使用年限的setter方法
	 */
	public void setUseYears(Integer useYears) {
		this.useYears = useYears;
	}

	/**
	 * 属性本车责任比例的getter方法
	 */

	@Column(name = "DUTYPERCENT")
	public Double getDutyPercent() {
		return this.dutyPercent;
	}

	/**
	 * 属性本车责任比例的setter方法
	 */
	public void setDutyPercent(Double dutyPercent) {
		this.dutyPercent = dutyPercent;
	}

	/**
	 * 属性是否保险（0-否1-是2-未知）的getter方法
	 */

	@Column(name = "INSUREDFLAG")
	public String getInsuredFlag() {
		return this.insuredFlag;
	}

	/**
	 * 属性是否保险（0-否1-是2-未知）的setter方法
	 */
	public void setInsuredFlag(String insuredFlag) {
		this.insuredFlag = insuredFlag;
	}

	/**
	 * 属性承保公司代码的getter方法
	 */

	@Column(name = "INSURECOMCODE")
	public String getInsureComCode() {
		return this.insureComCode;
	}

	/**
	 * 属性承保公司代码的setter方法
	 */
	public void setInsureComCode(String insureComCode) {
		this.insureComCode = insureComCode;
	}

	/**
	 * 属性承保公司名称的getter方法
	 */

	@Column(name = "INSURECOMNAME")
	public String getInsureComName() {
		return this.insureComName;
	}

	/**
	 * 属性承保公司名称的setter方法
	 */
	public void setInsureComName(String insureComName) {
		this.insureComName = insureComName;
	}

	/**
	 * 属性状态字段的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性状态字段的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性VIN号码的getter方法
	 */

	@Column(name = "VINNO")
	public String getVINNo() {
		return this.VINNo;
	}

	/**
	 * 属性VIN号码的setter方法
	 */
	public void setVINNo(String VINNO) {
		this.VINNo = VINNO;
	}

	/**
	 * 属性LOSSFLAG的getter方法
	 */

	@Column(name = "LOSSFLAG")
	public String getLossFlag() {
		return this.lossFlag;
	}

	/**
	 * 属性LOSSFLAG的setter方法
	 */
	public void setLossFlag(String lossFlag) {
		this.lossFlag = lossFlag;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public List<PrpLthirdParty> getThirdPartyList() {
		return thirdPartyList;
	}

	/**
	 * 设置列表
	 * @param prpLthirdPartyRegistNo 待设置的列表
	 */
	public void setThirdPartyList(List<PrpLthirdParty> thirdPartyList) {
		this.thirdPartyList = thirdPartyList;
	}

	/**
	 * 设置属性节点类型
	 * @param nodeType 待设置的属性节点类型的值
	 */
	public void setNodeType(String nodeType) {
		this.nodeType = StringUtils.rightTrim(nodeType);
	}

	/**
	 * 获取属性节点类型
	 * @return 属性节点类型的值
	 */
	@Transient
	public String getNodeType() {
		return nodeType;
	}

	@Transient
	public String getDriverSex() {
		return driverSex;
	}

	public void setDriverSex(String driverSex) {
		this.driverSex = driverSex;
	}

	@Transient
	public String getDrivingCarType() {
		return drivingCarType;
	}

	public void setDrivingCarType(String drivingCarType) {
		this.drivingCarType = drivingCarType;
	}

	/**
	 * 设置属性调度处理标志
	 * @param scheduleType 待设置的属性调度处理标志的值
	 */
	public void setScheduleType(String scheduleType) {
		this.scheduleType = StringUtils.rightTrim(scheduleType);
	}

	/**
	 * 获取属性调度处理标志
	 * @return 属性调度处理标志的值
	 */
	@Transient
	public String getScheduleType() {
		return scheduleType;
	}

	/**
	 * 设置属性是否选择发送
	 * @param selectSend 待设置的属性是否选择发送的值
	 */
	public void setSelectSend(String selectSend) {
		this.selectSend = StringUtils.rightTrim(selectSend);
	}

	/**
	 * 获取属性是否选择发送
	 * @return 属性是否选择发送的值
	 */
	@Transient
	public String getSelectSend() {
		return selectSend;
	}

	/**
	 * 设置属性险别类型
	 * @param prpLthirdPartyKindCode 待设置的属性险别类型的值
	 */
	public void setPrpLthirdPartyKindCode(String prpLthirdPartyKindCode) {
		this.prpLthirdPartyKindCode = StringUtils.rightTrim(prpLthirdPartyKindCode);
	}

	/**
	 * 获取属性险别类型
	 * @return 属性险别类型的值
	 */
	@Transient
	public String getPrpLthirdPartyKindCode() {
		return prpLthirdPartyKindCode;
	}

	@Transient
	public PrpLdriverDto getPrpLdriverDto() {
		return prpLdriverDto;
	}

	public void setPrpLdriverDto(PrpLdriverDto prpLdriverDto) {
		this.prpLdriverDto = prpLdriverDto;
	}

	@Transient
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	@Transient
	public String getDriverSeaRoute() {
		return driverSeaRoute;
	}

	public void setDriverSeaRoute(String driverSeaRoute) {
		this.driverSeaRoute = driverSeaRoute;
	}

	@Transient
	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}

	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}

	@Transient
	public String getApanage() {
		return apanage;
	}

	public void setApanage(String apanage) {
		this.apanage = apanage;
	}

	@Transient
	public String getApanageCode() {
		return apanageCode;
	}

	public void setApanageCode(String apanageCode) {
		this.apanageCode = apanageCode;
	}

	@Column(name = "GARAGEHEADNAME")
	public String getGarageHeadName() {
		return garageHeadName;
	}

	public void setGarageHeadName(String garageHeadName) {
		this.garageHeadName = garageHeadName;
	}

	@Column(name = "RELATIONSHIP")
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		/*
		   mantis： CLM0001，處理人員：David，需求單編號：CLM0001 --- start
		        處理過程：因新核心資訊系統報送格式變更，所以舊資料的本車駕駛人與被保險人關係需替換為新代碼
		 */
		if("2".equals(relationship)){
			this.relationship = "B";
		}else if("3".equals(relationship)){
			this.relationship = "G";
		}else if("6".equals(relationship)){
			this.relationship = "5";
		}else{
			this.relationship = relationship;
		}
		/* mantis： CLM0001，處理人員：David，需求單編號：CLM0001 --- end */
	}

	@Column(name = "DRIVINGADDRESS")
	public String getDrivingAddress() {
		return drivingAddress;
	}

	public void setDrivingAddress(String drivingAddress) {
		this.drivingAddress = drivingAddress;
	}

	@Column(name = "CARRYINGUNIT")
	public String getCarryingUnit() {
		return carryingUnit;
	}

	public void setCarryingUnit(String carryingUnit) {
		this.carryingUnit = carryingUnit;
	}

	@Column(name = "INSURANCENO")
	public String getInsuranceNo() {
		return insuranceNo;
	}

	public void setInsuranceNo(String insuranceNo) {
		this.insuranceNo = insuranceNo;
	}

	@Column(name = "ISINSURANCE")
	public String getIsInsurance() {
		return isInsurance;
	}

	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}

	@Column(name = "CARRYINGNUMBER")
	public Long getCarryingNumber() {
		return carryingNumber;
	}

	public void setCarryingNumber(Long carryingNumber) {
		this.carryingNumber = carryingNumber;
	}

	@Column(name = "CARSOWNERS")
	public String getCarsOwners() {
		return carsOwners;
	}

	public void setCarsOwners(String carsOwners) {
		this.carsOwners = carsOwners;
	}

	@Column(name = "INSUREDIDENTITY")
	public String getInsuredIdentity() {
		return insuredIdentity;
	}

	public void setInsuredIdentity(String insuredIdentity) {
		this.insuredIdentity = insuredIdentity;
	}

}
