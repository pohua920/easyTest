// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.claim.schema.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpCitemShip
 */
@Entity
@Table(name = "PRPCITEMSHIP")
public class PrpCitemShip implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpCitemShipId id;

	/** 属性险类代码 */
	private String riskCode;

	/** 属性险类 */
	private String riskKind;

	/** 属性船队编号 */
	private String fleetNo;

	/** 属性船舶代码 */
	private String shipCode;

	/** 属性船名中文 */
	private String shipCName;

	/** 属性船名(英文) */
	private String shipEName;

	/** 属性曾用名 */
	private String oldShipName;

	/** 属性船东 */
	private String shipOwner;

	/** 属性原船东 */
	private String oldShipOwner;

	/** 属性保赔协会 */
	private String associate;

	/** 属性建造年月 */
	private String makeYearMonth;

	/** 属性国家 */
	private String countryCode;

	/** 属性生产工厂 */
	private String makeFactory;

	/** 属性建造船坞名称及地址 */
	private String makeDock;

	/** 属性船台类型 */
	private String shipWayType;

	/** 属性适用规范 */
	private String applyCriterion;

	/** 属性建造合同号 */
	private String makeContractNo;

	/** 属性建造起始日期 */
	private Date makeStartDate;

	/** 属性建造终止日期 */
	private Date makeEndDate;

	/** 属性预建周期 */
	private String preBuildCyc;

	/** 属性船级 */
	private String stepHull;

	/** 属性原船级 */
	private String oldStepHull;

	/** 属性船状态 */
	private String shipFlag;

	/** 属性船舶种类代码 */
	private String shipTypeCode;

	/** 属性用户国家 */
	private String useNatureCode;

	/** 属性船舶用途 */
	private String shipUsage;

	/** 属性船质结构代码 */
	private String shipStruct;

	/** 属性注册地点 */
	private String registrySite;

	/** 属性吨位数 */
	private BigDecimal tonCount;

	/** 属性净吨位 */
	private BigDecimal netTonCount;

	/** 属性功率 */
	private BigDecimal horsePower;

	/** 属性功率单位 */
	private String powerUnit;

	/** 属性座位数 */
	private Long seatCount;

	/** 属性载重吨 */
	private BigDecimal loadTon;

	/** 属性总长 */
	private BigDecimal shipLength;

	/** 属性型宽 */
	private BigDecimal shipWidth;

	/** 属性型深 */
	private BigDecimal shipDepth;

	/** 属性试航期限 */
	private String trySailPeriod;

	/** 属性试航区域 */
	private String trySailArea;

	/** 属性船籍港 */
	private String shipPort;

	/** 属性出航日期 */
	private Date launchDate;

	/** 属性航行区域代码 */
	private String sailAreaCode;

	/** 属性航行区域名称 */
	private String sailAreaName;

	/** 属性航行范围 */
	private String sailScope;

	/** 属性航行方式 */
	private String sailModeCode;

	/** 属性航行距离 */
	private String voyage;

	/** 属性船舶价值 */
	private BigDecimal shipValue;

	/** 属性上升率 */
	private BigDecimal incrementRate;

	/** 属性上升值 */
	private BigDecimal incrementValue;

	/** 属性币别代码 */
	private String currency;

	/** 属性停航开始日期 */
	private Date suspendStartDate;

	/** 属性停航结束日期 */
	private Date suspendEndDate;

	/** 属性抵押权人 */
	private String mortgageName;

	/** 属性备注 */
	private String remark;

	/** 属性短信转存后是否删除标记 */
	private String flag;

	/** 属性老船名 */
	private String oldShipEName;

	/** 属性船操控者 */
	private String shipOperator;

	/** 属性船管理者 */
	private String shipManager;

	/** 属性制造地址 */
	private String makeArea;

	/** 属性改建地点（国家） */
	private String rebuildCountryCode;

	/** 属性改建地点（地区） */
	private String rebuildArea;

	/** 属性改建厂家 */
	private String rebuildFactory;

	/** 属性改建时间 */
	private Date rebuildStartDate;

	/** 属性主机/主发电机（非自航）型号 */
	private String machineModel;

	/** 属性主机/主发电机（非自航）生产日期 */
	private Date productDate;

	/** 属性属性主机/主发电机 */
	private Long machineQuantity;

	/** 属性船号 */
	private String shipNo;

	/** 属性原船号 */
	private String oldShipNo;

	/** 属性OLDSHIPPORT */
	private String oldShipPort;

	/** 属性CERTIFICATEOWNER */
	private String certificateOwner;

	/** 属性MANAGETYPE */
	private String manageType;

	/** 属性MANAGESTARTDATE */
	private Date manageStartDate;

	/** 属性MANAGEENDDATE */
	private Date manageEndDate;

	/** 属性CHECKSTARTDATE */
	private Date checkStartDate;

	/** 属性CHECKENDDATE */
	private Date checkEndDate;

	/** 属性DEBTAMOUNT */
	private BigDecimal debtAmount;

	/** 属性PLEDGE */
	private String pledge;

	/** 属性DOCKLASTREPEARDATE */
	private Date dockLastRepearDate;

	/** 属性MAKEDATEFLAG */
	private String makeDateFlag;

	/** 属性REBUILDFLAG */
	private String rebuildFlag;

	/** 属性FUNCTIONARYSAILOR */
	private String functionarySailor;

	/** 属性UNDERWRITEREQUIP */
	private String underwriterEquip;

	/** 属性TEMPORARYSTAFF */
	private String temporaryStaff;

	/** 属性STARTSITECODE */
	private String startSiteCode;

	/** 属性起运地 */
	private String startSiteName;

	/** 属性VIASITECODE */
	private String viaSiteCode;

	/** 属性中转地 */
	private String viaSiteName;

	/** 属性ENDSITECODE */
	private String endSiteCode;

	/** 属性目的地 */
	private String endSiteName;
	/** 正本 */
	private String original;
	/** 副本 */
	private String transcript;
	/** 每趟航程时间 */
	private String voyageTime;
	/** 每月航行次數 */
	private String voyageNumber;
	/** 航海经验 */
	private String sailingExperience;
	/**
	 * 属性PrpCmain
	 */
	private PrpCmain prpCmain;

	/**
	 * 属性PrpCmain的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICYNO", nullable = false, insertable = false, updatable = false)
	public PrpCmain getPrpCmain() {
		return this.prpCmain;
	}

	/**
	 * 属性PRPCMAIN的setter方法
	 */
	public void setPrpCmain(PrpCmain prpCmain) {
		this.prpCmain = prpCmain;
	}

	/**
	 * 类PrpCitemShip的默认构造方法
	 */
	public PrpCitemShip() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")), @AttributeOverride(name = "itemNo", column = @Column(name = "ITEMNO")) })
	public PrpCitemShipId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpCitemShipId id) {
		this.id = id;
	}

	/**
	 * 属性险类代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险类代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性险类的getter方法
	 */

	@Column(name = "RISKKIND")
	public String getRiskKind() {
		return this.riskKind;
	}

	/**
	 * 属性险类的setter方法
	 */
	public void setRiskKind(String riskKind) {
		this.riskKind = riskKind;
	}

	/**
	 * 属性船队编号的getter方法
	 */

	@Column(name = "FLEETNO")
	public String getFleetNo() {
		return this.fleetNo;
	}

	/**
	 * 属性船队编号的setter方法
	 */
	public void setFleetNo(String fleetNo) {
		this.fleetNo = fleetNo;
	}

	/**
	 * 属性船舶代码的getter方法
	 */

	@Column(name = "SHIPCODE")
	public String getShipCode() {
		return this.shipCode;
	}

	/**
	 * 属性船舶代码的setter方法
	 */
	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	/**
	 * 属性船名中文的getter方法
	 */

	@Column(name = "SHIPCNAME")
	public String getShipCName() {
		return this.shipCName;
	}

	/**
	 * 属性船名中文的setter方法
	 */
	public void setShipCName(String shipCName) {
		this.shipCName = shipCName;
	}

	/**
	 * 属性船名(英文)的getter方法
	 */

	@Column(name = "SHIPENAME")
	public String getShipEName() {
		return this.shipEName;
	}

	/**
	 * 属性船名(英文)的setter方法
	 */
	public void setShipEName(String shipEName) {
		this.shipEName = shipEName;
	}

	/**
	 * 属性曾用名的getter方法
	 */

	@Column(name = "OLDSHIPNAME")
	public String getOldShipName() {
		return this.oldShipName;
	}

	/**
	 * 属性曾用名的setter方法
	 */
	public void setOldShipName(String oldShipName) {
		this.oldShipName = oldShipName;
	}

	/**
	 * 属性船东的getter方法
	 */

	@Column(name = "SHIPOWNER")
	public String getShipOwner() {
		return this.shipOwner;
	}

	/**
	 * 属性船东的setter方法
	 */
	public void setShipOwner(String shipOwner) {
		this.shipOwner = shipOwner;
	}

	/**
	 * 属性原船东的getter方法
	 */

	@Column(name = "OLDSHIPOWNER")
	public String getOldShipOwner() {
		return this.oldShipOwner;
	}

	/**
	 * 属性原船东的setter方法
	 */
	public void setOldShipOwner(String oldShipOwner) {
		this.oldShipOwner = oldShipOwner;
	}

	/**
	 * 属性保赔协会的getter方法
	 */

	@Column(name = "ASSOCIATE")
	public String getAssociate() {
		return this.associate;
	}

	/**
	 * 属性保赔协会的setter方法
	 */
	public void setAssociate(String associate) {
		this.associate = associate;
	}

	/**
	 * 属性建造年月的getter方法
	 */

	@Column(name = "MAKEYEARMONTH")
	public String getMakeYearMonth() {
		return this.makeYearMonth;
	}

	/**
	 * 属性建造年月的setter方法
	 */
	public void setMakeYearMonth(String makeYearMonth) {
		this.makeYearMonth = makeYearMonth;
	}

	/**
	 * 属性国家的getter方法
	 */

	@Column(name = "COUNTRYCODE")
	public String getCountryCode() {
		return this.countryCode;
	}

	/**
	 * 属性国家的setter方法
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * 属性生产工厂的getter方法
	 */

	@Column(name = "MAKEFACTORY")
	public String getMakeFactory() {
		return this.makeFactory;
	}

	/**
	 * 属性生产工厂的setter方法
	 */
	public void setMakeFactory(String makeFactory) {
		this.makeFactory = makeFactory;
	}

	/**
	 * 属性建造船坞名称及地址的getter方法
	 */

	@Column(name = "MAKEDOCK")
	public String getMakeDock() {
		return this.makeDock;
	}

	/**
	 * 属性建造船坞名称及地址的setter方法
	 */
	public void setMakeDock(String makeDock) {
		this.makeDock = makeDock;
	}

	/**
	 * 属性船台类型的getter方法
	 */

	@Column(name = "SHIPWAYTYPE")
	public String getShipWayType() {
		return this.shipWayType;
	}

	/**
	 * 属性船台类型的setter方法
	 */
	public void setShipWayType(String shipWayType) {
		this.shipWayType = shipWayType;
	}

	/**
	 * 属性适用规范的getter方法
	 */

	@Column(name = "APPLYCRITERION")
	public String getApplyCriterion() {
		return this.applyCriterion;
	}

	/**
	 * 属性适用规范的setter方法
	 */
	public void setApplyCriterion(String applyCriterion) {
		this.applyCriterion = applyCriterion;
	}

	/**
	 * 属性建造合同号的getter方法
	 */

	@Column(name = "MAKECONTRACTNO")
	public String getMakeContractNo() {
		return this.makeContractNo;
	}

	/**
	 * 属性建造合同号的setter方法
	 */
	public void setMakeContractNo(String makeContractNo) {
		this.makeContractNo = makeContractNo;
	}

	/**
	 * 属性建造起始日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MAKESTARTDATE")
	public Date getMakeStartDate() {
		return this.makeStartDate;
	}

	/**
	 * 属性建造起始日期的setter方法
	 */
	public void setMakeStartDate(Date makeStartDate) {
		this.makeStartDate = makeStartDate;
	}

	/**
	 * 属性建造终止日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MAKEENDDATE")
	public Date getMakeEndDate() {
		return this.makeEndDate;
	}

	/**
	 * 属性建造终止日期的setter方法
	 */
	public void setMakeEndDate(Date makeEndDate) {
		this.makeEndDate = makeEndDate;
	}

	/**
	 * 属性预建周期的getter方法
	 */

	@Column(name = "PREBUILDCYC")
	public String getPreBuildCyc() {
		return this.preBuildCyc;
	}

	/**
	 * 属性预建周期的setter方法
	 */
	public void setPreBuildCyc(String preBuildCyc) {
		this.preBuildCyc = preBuildCyc;
	}

	/**
	 * 属性船级的getter方法
	 */

	@Column(name = "STEPHULL")
	public String getStepHull() {
		return this.stepHull;
	}

	/**
	 * 属性船级的setter方法
	 */
	public void setStepHull(String stepHull) {
		this.stepHull = stepHull;
	}

	/**
	 * 属性原船级的getter方法
	 */

	@Column(name = "OLDSTEPHULL")
	public String getOldStepHull() {
		return this.oldStepHull;
	}

	/**
	 * 属性原船级的setter方法
	 */
	public void setOldStepHull(String oldStepHull) {
		this.oldStepHull = oldStepHull;
	}

	/**
	 * 属性船状态的getter方法
	 */

	@Column(name = "SHIPFLAG")
	public String getShipFlag() {
		return this.shipFlag;
	}

	/**
	 * 属性船状态的setter方法
	 */
	public void setShipFlag(String shipFlag) {
		this.shipFlag = shipFlag;
	}

	/**
	 * 属性船舶种类代码的getter方法
	 */

	@Column(name = "SHIPTYPECODE")
	public String getShipTypeCode() {
		return this.shipTypeCode;
	}

	/**
	 * 属性船舶种类代码的setter方法
	 */
	public void setShipTypeCode(String shipTypeCode) {
		this.shipTypeCode = shipTypeCode;
	}

	/**
	 * 属性用户国家的getter方法
	 */

	@Column(name = "USENATURECODE")
	public String getUseNatureCode() {
		return this.useNatureCode;
	}

	/**
	 * 属性用户国家的setter方法
	 */
	public void setUseNatureCode(String useNatureCode) {
		this.useNatureCode = useNatureCode;
	}

	/**
	 * 属性船舶用途的getter方法
	 */

	@Column(name = "SHIPUSAGE")
	public String getShipUsage() {
		return this.shipUsage;
	}

	/**
	 * 属性船舶用途的setter方法
	 */
	public void setShipUsage(String shipUsage) {
		this.shipUsage = shipUsage;
	}

	/**
	 * 属性船质结构代码的getter方法
	 */

	@Column(name = "SHIPSTRUCT")
	public String getShipStruct() {
		return this.shipStruct;
	}

	/**
	 * 属性船质结构代码的setter方法
	 */
	public void setShipStruct(String shipStruct) {
		this.shipStruct = shipStruct;
	}

	/**
	 * 属性注册地点的getter方法
	 */

	@Column(name = "REGISTRYSITE")
	public String getRegistrySite() {
		return this.registrySite;
	}

	/**
	 * 属性注册地点的setter方法
	 */
	public void setRegistrySite(String registrySite) {
		this.registrySite = registrySite;
	}

	/**
	 * 属性吨位数的getter方法
	 */

	@Column(name = "TONCOUNT")
	public BigDecimal getTonCount() {
		return this.tonCount;
	}

	/**
	 * 属性吨位数的setter方法
	 */
	public void setTonCount(BigDecimal tonCount) {
		this.tonCount = tonCount;
	}

	/**
	 * 属性净吨位的getter方法
	 */

	@Column(name = "NETTONCOUNT")
	public BigDecimal getNetTonCount() {
		return this.netTonCount;
	}

	/**
	 * 属性净吨位的setter方法
	 */
	public void setNetTonCount(BigDecimal netTonCount) {
		this.netTonCount = netTonCount;
	}

	/**
	 * 属性功率的getter方法
	 */

	@Column(name = "HORSEPOWER")
	public BigDecimal getHorsePower() {
		return this.horsePower;
	}

	/**
	 * 属性功率的setter方法
	 */
	public void setHorsePower(BigDecimal horsePower) {
		this.horsePower = horsePower;
	}

	/**
	 * 属性功率单位的getter方法
	 */

	@Column(name = "POWERUNIT")
	public String getPowerUnit() {
		return this.powerUnit;
	}

	/**
	 * 属性功率单位的setter方法
	 */
	public void setPowerUnit(String powerUnit) {
		this.powerUnit = powerUnit;
	}

	/**
	 * 属性座位数的getter方法
	 */

	@Column(name = "SEATCOUNT")
	public Long getSeatCount() {
		return this.seatCount;
	}

	/**
	 * 属性座位数的setter方法
	 */
	public void setSeatCount(Long seatCount) {
		this.seatCount = seatCount;
	}

	/**
	 * 属性载重吨的getter方法
	 */

	@Column(name = "LOADTON")
	public BigDecimal getLoadTon() {
		return this.loadTon;
	}

	/**
	 * 属性载重吨的setter方法
	 */
	public void setLoadTon(BigDecimal loadTon) {
		this.loadTon = loadTon;
	}

	/**
	 * 属性总长的getter方法
	 */

	@Column(name = "SHIPLENGTH")
	public BigDecimal getShipLength() {
		return this.shipLength;
	}

	/**
	 * 属性总长的setter方法
	 */
	public void setShipLength(BigDecimal shipLength) {
		this.shipLength = shipLength;
	}

	/**
	 * 属性型宽的getter方法
	 */

	@Column(name = "SHIPWIDTH")
	public BigDecimal getShipWidth() {
		return this.shipWidth;
	}

	/**
	 * 属性型宽的setter方法
	 */
	public void setShipWidth(BigDecimal shipWidth) {
		this.shipWidth = shipWidth;
	}

	/**
	 * 属性型深的getter方法
	 */

	@Column(name = "SHIPDEPTH")
	public BigDecimal getShipDepth() {
		return this.shipDepth;
	}

	/**
	 * 属性型深的setter方法
	 */
	public void setShipDepth(BigDecimal shipDepth) {
		this.shipDepth = shipDepth;
	}

	/**
	 * 属性试航期限的getter方法
	 */

	@Column(name = "TRYSAILPERIOD")
	public String getTrySailPeriod() {
		return this.trySailPeriod;
	}

	/**
	 * 属性试航期限的setter方法
	 */
	public void setTrySailPeriod(String trySailPeriod) {
		this.trySailPeriod = trySailPeriod;
	}

	/**
	 * 属性试航区域的getter方法
	 */

	@Column(name = "TRYSAILAREA")
	public String getTrySailArea() {
		return this.trySailArea;
	}

	/**
	 * 属性试航区域的setter方法
	 */
	public void setTrySailArea(String trySailArea) {
		this.trySailArea = trySailArea;
	}

	/**
	 * 属性船籍港的getter方法
	 */

	@Column(name = "SHIPPORT")
	public String getShipPort() {
		return this.shipPort;
	}

	/**
	 * 属性船籍港的setter方法
	 */
	public void setShipPort(String shipPort) {
		this.shipPort = shipPort;
	}

	/**
	 * 属性出航日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAUNCHDATE")
	public Date getLaunchDate() {
		return this.launchDate;
	}

	/**
	 * 属性出航日期的setter方法
	 */
	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	/**
	 * 属性航行区域代码的getter方法
	 */

	@Column(name = "SAILAREACODE")
	public String getSailAreaCode() {
		return this.sailAreaCode;
	}

	/**
	 * 属性航行区域代码的setter方法
	 */
	public void setSailAreaCode(String sailAreaCode) {
		this.sailAreaCode = sailAreaCode;
	}

	/**
	 * 属性航行区域名称的getter方法
	 */

	@Column(name = "SAILAREANAME")
	public String getSailAreaName() {
		return this.sailAreaName;
	}

	/**
	 * 属性航行区域名称的setter方法
	 */
	public void setSailAreaName(String sailAreaName) {
		this.sailAreaName = sailAreaName;
	}

	/**
	 * 属性航行范围的getter方法
	 */

	@Column(name = "SAILSCOPE")
	public String getSailScope() {
		return this.sailScope;
	}

	/**
	 * 属性航行范围的setter方法
	 */
	public void setSailScope(String sailScope) {
		this.sailScope = sailScope;
	}

	/**
	 * 属性航行方式的getter方法
	 */

	@Column(name = "SAILMODECODE")
	public String getSailModeCode() {
		return this.sailModeCode;
	}

	/**
	 * 属性航行方式的setter方法
	 */
	public void setSailModeCode(String sailModeCode) {
		this.sailModeCode = sailModeCode;
	}

	/**
	 * 属性航行距离的getter方法
	 */

	@Column(name = "VOYAGE")
	public String getVoyage() {
		return this.voyage;
	}

	/**
	 * 属性航行距离的setter方法
	 */
	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	/**
	 * 属性船舶价值的getter方法
	 */

	@Column(name = "SHIPVALUE")
	public BigDecimal getShipValue() {
		return this.shipValue;
	}

	/**
	 * 属性船舶价值的setter方法
	 */
	public void setShipValue(BigDecimal shipValue) {
		this.shipValue = shipValue;
	}

	/**
	 * 属性上升率的getter方法
	 */

	@Column(name = "INCREMENTRATE")
	public BigDecimal getIncrementRate() {
		return this.incrementRate;
	}

	/**
	 * 属性上升率的setter方法
	 */
	public void setIncrementRate(BigDecimal incrementRate) {
		this.incrementRate = incrementRate;
	}

	/**
	 * 属性上升值的getter方法
	 */

	@Column(name = "INCREMENTVALUE")
	public BigDecimal getIncrementValue() {
		return this.incrementValue;
	}

	/**
	 * 属性上升值的setter方法
	 */
	public void setIncrementValue(BigDecimal incrementValue) {
		this.incrementValue = incrementValue;
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
	 * 属性停航开始日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUSPENDSTARTDATE")
	public Date getSuspendStartDate() {
		return this.suspendStartDate;
	}

	/**
	 * 属性停航开始日期的setter方法
	 */
	public void setSuspendStartDate(Date suspendStartDate) {
		this.suspendStartDate = suspendStartDate;
	}

	/**
	 * 属性停航结束日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SUSPENDENDDATE")
	public Date getSuspendEndDate() {
		return this.suspendEndDate;
	}

	/**
	 * 属性停航结束日期的setter方法
	 */
	public void setSuspendEndDate(Date suspendEndDate) {
		this.suspendEndDate = suspendEndDate;
	}

	/**
	 * 属性抵押权人的getter方法
	 */

	@Column(name = "MORTGAGENAME")
	public String getMortgageName() {
		return this.mortgageName;
	}

	/**
	 * 属性抵押权人的setter方法
	 */
	public void setMortgageName(String mortgageName) {
		this.mortgageName = mortgageName;
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
	 * 属性短信转存后是否删除标记的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性短信转存后是否删除标记的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性老船名的getter方法
	 */

	@Column(name = "OLDSHIPENAME")
	public String getOldShipEName() {
		return this.oldShipEName;
	}

	/**
	 * 属性老船名的setter方法
	 */
	public void setOldShipEName(String oldShipEName) {
		this.oldShipEName = oldShipEName;
	}

	/**
	 * 属性船操控者的getter方法
	 */

	@Column(name = "SHIPOPERATOR")
	public String getShipOperator() {
		return this.shipOperator;
	}

	/**
	 * 属性船操控者的setter方法
	 */
	public void setShipOperator(String shipOperator) {
		this.shipOperator = shipOperator;
	}

	/**
	 * 属性船管理者的getter方法
	 */

	@Column(name = "SHIPMANAGER")
	public String getShipManager() {
		return this.shipManager;
	}

	/**
	 * 属性船管理者的setter方法
	 */
	public void setShipManager(String shipManager) {
		this.shipManager = shipManager;
	}

	/**
	 * 属性制造地址的getter方法
	 */

	@Column(name = "MAKEAREA")
	public String getMakeArea() {
		return this.makeArea;
	}

	/**
	 * 属性制造地址的setter方法
	 */
	public void setMakeArea(String makeArea) {
		this.makeArea = makeArea;
	}

	/**
	 * 属性改建地点（国家）的getter方法
	 */

	@Column(name = "REBUILDCOUNTRYCODE")
	public String getRebuildCountryCode() {
		return this.rebuildCountryCode;
	}

	/**
	 * 属性改建地点（国家）的setter方法
	 */
	public void setRebuildCountryCode(String rebuildCountryCode) {
		this.rebuildCountryCode = rebuildCountryCode;
	}

	/**
	 * 属性改建地点（地区）的getter方法
	 */

	@Column(name = "REBUILDAREA")
	public String getRebuildArea() {
		return this.rebuildArea;
	}

	/**
	 * 属性改建地点（地区）的setter方法
	 */
	public void setRebuildArea(String rebuildArea) {
		this.rebuildArea = rebuildArea;
	}

	/**
	 * 属性改建厂家的getter方法
	 */

	@Column(name = "REBUILDFACTORY")
	public String getRebuildFactory() {
		return this.rebuildFactory;
	}

	/**
	 * 属性改建厂家的setter方法
	 */
	public void setRebuildFactory(String rebuildFactory) {
		this.rebuildFactory = rebuildFactory;
	}

	/**
	 * 属性改建时间的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REBUILDSTARTDATE")
	public Date getRebuildStartDate() {
		return this.rebuildStartDate;
	}

	/**
	 * 属性改建时间的setter方法
	 */
	public void setRebuildStartDate(Date rebuildStartDate) {
		this.rebuildStartDate = rebuildStartDate;
	}

	/**
	 * 属性主机/主发电机（非自航）型号的getter方法
	 */

	@Column(name = "MACHINEMODEL")
	public String getMachineModel() {
		return this.machineModel;
	}

	/**
	 * 属性主机/主发电机（非自航）型号的setter方法
	 */
	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}

	/**
	 * 属性主机/主发电机（非自航）生产日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRODUCTDATE")
	public Date getProductDate() {
		return this.productDate;
	}

	/**
	 * 属性主机/主发电机（非自航）生产日期的setter方法
	 */
	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	/**
	 * 属性属性主机/主发电机的getter方法
	 */

	@Column(name = "MACHINEQUANTITY")
	public Long getMachineQuantity() {
		return this.machineQuantity;
	}

	/**
	 * 属性属性主机/主发电机的setter方法
	 */
	public void setMachineQuantity(Long machineQuantity) {
		this.machineQuantity = machineQuantity;
	}

	/**
	 * 属性船号的getter方法
	 */

	@Column(name = "SHIPNO")
	public String getShipNo() {
		return this.shipNo;
	}

	/**
	 * 属性船号的setter方法
	 */
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
	}

	/**
	 * 属性原船号的getter方法
	 */

	@Column(name = "OLDSHIPNO")
	public String getOldShipNo() {
		return this.oldShipNo;
	}

	/**
	 * 属性原船号的setter方法
	 */
	public void setOldShipNo(String oldShipNo) {
		this.oldShipNo = oldShipNo;
	}

	/**
	 * 属性OLDSHIPPORT的getter方法
	 */

	@Column(name = "OLDSHIPPORT")
	public String getOldShipPort() {
		return this.oldShipPort;
	}

	/**
	 * 属性OLDSHIPPORT的setter方法
	 */
	public void setOldShipPort(String oldShipPort) {
		this.oldShipPort = oldShipPort;
	}

	/**
	 * 属性CERTIFICATEOWNER的getter方法
	 */

	@Column(name = "CERTIFICATEOWNER")
	public String getCertificateOwner() {
		return this.certificateOwner;
	}

	/**
	 * 属性CERTIFICATEOWNER的setter方法
	 */
	public void setCertificateOwner(String certificateOwner) {
		this.certificateOwner = certificateOwner;
	}

	/**
	 * 属性MANAGETYPE的getter方法
	 */

	@Column(name = "MANAGETYPE")
	public String getManageType() {
		return this.manageType;
	}

	/**
	 * 属性MANAGETYPE的setter方法
	 */
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	/**
	 * 属性MANAGESTARTDATE的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MANAGESTARTDATE")
	public Date getManageStartDate() {
		return this.manageStartDate;
	}

	/**
	 * 属性MANAGESTARTDATE的setter方法
	 */
	public void setManageStartDate(Date manageStartDate) {
		this.manageStartDate = manageStartDate;
	}

	/**
	 * 属性MANAGEENDDATE的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MANAGEENDDATE")
	public Date getManageEndDate() {
		return this.manageEndDate;
	}

	/**
	 * 属性MANAGEENDDATE的setter方法
	 */
	public void setManageEndDate(Date manageEndDate) {
		this.manageEndDate = manageEndDate;
	}

	/**
	 * 属性CHECKSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECKSTARTDATE")
	public Date getCheckStartDate() {
		return this.checkStartDate;
	}

	/**
	 * 属性CHECKSTARTDATE的setter方法
	 */
	public void setCheckStartDate(Date checkStartDate) {
		this.checkStartDate = checkStartDate;
	}

	/**
	 * 属性CHECKENDDATE的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CHECKENDDATE")
	public Date getCheckEndDate() {
		return this.checkEndDate;
	}

	/**
	 * 属性CHECKENDDATE的setter方法
	 */
	public void setCheckEndDate(Date checkEndDate) {
		this.checkEndDate = checkEndDate;
	}

	/**
	 * 属性DEBTAMOUNT的getter方法
	 */

	@Column(name = "DEBTAMOUNT")
	public BigDecimal getDebtAmount() {
		return this.debtAmount;
	}

	/**
	 * 属性DEBTAMOUNT的setter方法
	 */
	public void setDebtAmount(BigDecimal debtAmount) {
		this.debtAmount = debtAmount;
	}

	/**
	 * 属性PLEDGE的getter方法
	 */

	@Column(name = "PLEDGE")
	public String getPledge() {
		return this.pledge;
	}

	/**
	 * 属性PLEDGE的setter方法
	 */
	public void setPledge(String pledge) {
		this.pledge = pledge;
	}

	/**
	 * 属性DOCKLASTREPEARDATE的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DOCKLASTREPEARDATE")
	public Date getDockLastRepearDate() {
		return this.dockLastRepearDate;
	}

	/**
	 * 属性DOCKLASTREPEARDATE的setter方法
	 */
	public void setDockLastRepearDate(Date dockLastRepearDate) {
		this.dockLastRepearDate = dockLastRepearDate;
	}

	/**
	 * 属性MAKEDATEFLAG的getter方法
	 */

	@Column(name = "MAKEDATEFLAG")
	public String getMakeDateFlag() {
		return this.makeDateFlag;
	}

	/**
	 * 属性MAKEDATEFLAG的setter方法
	 */
	public void setMakeDateFlag(String makeDateFlag) {
		this.makeDateFlag = makeDateFlag;
	}

	/**
	 * 属性REBUILDFLAG的getter方法
	 */

	@Column(name = "REBUILDFLAG")
	public String getRebuildFlag() {
		return this.rebuildFlag;
	}

	/**
	 * 属性REBUILDFLAG的setter方法
	 */
	public void setRebuildFlag(String rebuildFlag) {
		this.rebuildFlag = rebuildFlag;
	}

	/**
	 * 属性FUNCTIONARYSAILOR的getter方法
	 */

	@Column(name = "FUNCTIONARYSAILOR")
	public String getFunctionarySailor() {
		return this.functionarySailor;
	}

	/**
	 * 属性FUNCTIONARYSAILOR的setter方法
	 */
	public void setFunctionarySailor(String functionarySailor) {
		this.functionarySailor = functionarySailor;
	}

	/**
	 * 属性UNDERWRITEREQUIP的getter方法
	 */

	@Column(name = "UNDERWRITEREQUIP")
	public String getUnderwriterEquip() {
		return this.underwriterEquip;
	}

	/**
	 * 属性UNDERWRITEREQUIP的setter方法
	 */
	public void setUnderwriterEquip(String underwriterEquip) {
		this.underwriterEquip = underwriterEquip;
	}

	/**
	 * 属性TEMPORARYSTAFF的getter方法
	 */

	@Column(name = "TEMPORARYSTAFF")
	public String getTemporaryStaff() {
		return this.temporaryStaff;
	}

	/**
	 * 属性TEMPORARYSTAFF的setter方法
	 */
	public void setTemporaryStaff(String temporaryStaff) {
		this.temporaryStaff = temporaryStaff;
	}

	/**
	 * 属性STARTSITECODE的getter方法
	 */

	@Column(name = "STARTSITECODE")
	public String getStartSiteCode() {
		return this.startSiteCode;
	}

	/**
	 * 属性STARTSITECODE的setter方法
	 */
	public void setStartSiteCode(String startSiteCode) {
		this.startSiteCode = startSiteCode;
	}

	/**
	 * 属性起运地的getter方法
	 */

	@Column(name = "STARTSITENAME")
	public String getStartSiteName() {
		return this.startSiteName;
	}

	/**
	 * 属性起运地的setter方法
	 */
	public void setStartSiteName(String startSiteName) {
		this.startSiteName = startSiteName;
	}

	/**
	 * 属性VIASITECODE的getter方法
	 */

	@Column(name = "VIASITECODE")
	public String getViaSiteCode() {
		return this.viaSiteCode;
	}

	/**
	 * 属性VIASITECODE的setter方法
	 */
	public void setViaSiteCode(String viaSiteCode) {
		this.viaSiteCode = viaSiteCode;
	}

	/**
	 * 属性中转地的getter方法
	 */

	@Column(name = "VIASITENAME")
	public String getViaSiteName() {
		return this.viaSiteName;
	}

	/**
	 * 属性中转地的setter方法
	 */
	public void setViaSiteName(String viaSiteName) {
		this.viaSiteName = viaSiteName;
	}

	/**
	 * 属性ENDSITECODE的getter方法
	 */

	@Column(name = "ENDSITECODE")
	public String getEndSiteCode() {
		return this.endSiteCode;
	}

	/**
	 * 属性ENDSITECODE的setter方法
	 */
	public void setEndSiteCode(String endSiteCode) {
		this.endSiteCode = endSiteCode;
	}

	/**
	 * 属性目的地的getter方法
	 */

	@Column(name = "ENDSITENAME")
	public String getEndSiteName() {
		return this.endSiteName;
	}

	/**
	 * 属性目的地的setter方法
	 */

	public void setEndSiteName(String endSiteName) {
		this.endSiteName = endSiteName;
	}

	@Column(name = "ORIGINAL")
	public String getOriginal() {
		return original;
	}

	public void setOriginal(String original) {
		this.original = original;
	}

	@Column(name = "TRANSCRIPT")
	public String getTranscript() {
		return transcript;
	}

	public void setTranscript(String transcript) {
		this.transcript = transcript;
	}

	@Column(name = "VOYAGETIME")
	public String getVoyageTime() {
		return voyageTime;
	}

	public void setVoyageTime(String voyageTime) {
		this.voyageTime = voyageTime;
	}

	@Column(name = "VOYAGENUMBER")
	public String getVoyageNumber() {
		return voyageNumber;
	}

	public void setVoyageNumber(String voyageNumber) {
		this.voyageNumber = voyageNumber;
	}

	@Column(name = "SAILINGEXPERIENCE")
	public String getSailingExperience() {
		return sailingExperience;
	}

	public void setSailingExperience(String sailingExperience) {
		this.sailingExperience = sailingExperience;
	}

}
