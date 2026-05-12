package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.claim.compensate.vo.CompensateFeeDto;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLregist报案信息表
 */
@Entity
@Table(name = "PRPLREGIST")
public class PrpLregist implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性报案号 */
	private String registNo = "";

	/** 属性理赔类型 */
	private String lflag = "";

	/** 属性险类代码 */
	private String classCode = "";

	/** 属性险种代码 */
	private String riskCode = "";

	/** 属性保单号 */
	private String policyNo = "";

	/** 属性语种标志 */
	private String language = "";

	/** 属性被保险人代码 */
	private String insuredCode = "";

	/** 属性被保险人名称 */
	private String insuredName = "";

	/** 属性被保险人地址 */
	private String insuredAddress = "";

	/** 属性条款类别 */
	private String clauseType = "";

	/** 属性车牌号 */
	private String licenseNo = "";

	/** 属性车牌底色代码 */
	private String licenseColorCode = "";

	/** 属性车辆种类代码 */
	private String carKindCode = "";

	/** 属性车型代码 */
	private String modelCode = "";

	/** 属性厂牌型号 */
	private String brandName = "";

	/** 属性发动机号 */
	private String engineNo = "";

	/** 属性车架号 */
	private String frameNo = "";

	/** 属性车辆已行驶公里数 */
	private Double runDistance;

	/** 属性车辆实际使用年限 */
	private int useYears;

	/** 属性报案日期 */
	private Date reportDate;

	/** 属性报案小时 */
	private String reportHour = "";

	/** 属性报案地点 */
	private String reportAddress = "";

	/** 属性报案人 */
	private String reportorName = "";

	/** 属性报案形式 */
	private String reportType = "";

	/** 属性报案人联系电话 */
	private String phoneNumber = "";

	/** 属性联系人 */
	private String linkerName = "";

	/** 属性出险日期起 */
	private Date damageStartDate;

	/** 属性出险开始小时 */
	private String damageStartHour = "";

	/** 属性出险日期止 */
	private Date damageEndDate;

	/** 属性出险终止小时 */
	private String damageEndHour = "";

	/** 属性任意险出险原因代码 */
	private String damageCode = "";

	/** 属性任意险出险原因名称 */
	private String damageName = "";

	/** 属性事故类型代码 */
	private String damageTypeCode = "";

	/** 属性事故类型说明 */
	private String damageTypeName = "";

	/** 属性是否第一现场 */
	private String firstSiteFlag = "";

	/** 属性出险区域代码 */
	private String damageAreaCode = "";

	/** 属性出险区域名称 */
	private String damageAreaName = "";

	/** 属性出险地点分类代码 */
	private String damageAddressType = "";

	/** 属性出险地代码 */
	private String addressCode = "";

	/** 属性出险地点 */
	private String damageAddress = "";

	/** 属性出险地点邮政编码 */
	private String damageAreaPostCode = "";

	/** 属性事故处理部门 */
	private String handleUnit = "";

	/** 属性受损标的 */
	private String lossName = "";

	/** 属性受损标的数量/出险分户数 */
	private Double lossQuantity;

	/** 属性数量单位 */
	private String unit = "";

	/** 属性估损币别 */
	private String estiCurrency = "";

	/** 属性估损金额 */
	private Double estimateLoss;

	/** 属性接案员姓名 */
	private String receiverName = "";

	/** 属性经办人代码 */
	private String handlerCode = "";

	/** 属性归属业务员代码 */
	private String handler1Code = "";

	/** 属性业务归属机构代码 */
	private String comCode = "";

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性受理标志(Y/N) */
	private String acceptFlag = "";

	/** 属性是否向别的保险公司投保 */
	private String repeatInsureFlag = "";

	/** 属性赔案类别 */
	private String claimType = "";

	/** 属性注销/拒赔日期 */
	private Date cancelDate;

	/** 属性注销/拒赔人代码 */
	private String dealerCode = "";

	/** 属性备注 */
	private String remark = "";

	/** 属性操作员代码 */
	private String operatorCode = "";

	/** 属性出单机构 */
	private String makeCom = "";

	/** 属性状态字段 */
	private String flag = "";

	/** 属性报案人电话 */
	private String reportorPhoneNumber = "";

	/** 属性联系人邮编 */
	private String linkerPostCode = "";

	/** 属性联系人通讯地址 */
	private String linkerAddress = "";

	/** 属性未决赔款准备金 */
	private Double estimateFee;

	/** 属性巨灾一级代码 */
	private String catastropheCode1 = "";

	/** 属性巨灾一级名称 */
	private String catastropheName1 = "";

	/** 属性巨灾二级代码 */
	private String catastropheCode2 = "";

	/** 属性巨灾二级名称 */
	private String catastropheName2 = "";

	/** 属性报案标志 */
	private String reportFlag = "";

	/** 属性赔偿责任代码 */
	private String indemnityDuty = "";

	/** 属性简易赔案标记 */
	private String claimTypeFlag = "";

	/** 属性事故处理类型代码 */
	private String manageType = "";

	/** 属性事故处理类型名称 */
	private String manageTypeName = "";

	/** 属性天气代码 */
	private String weather = "";

	/** 属性天气名称 */
	private String weatherName = "";

	/** 属性事故管制代码 */
	private String section = "";

	/** 属性事故管制名称 */
	private String sectionName = "";

	/** 属性报案人与被保险人关系代码 */
	private String relationType = "";

	/** 属性垫付赔案类型 */
	private String advanceType = "";

	/** 属性是否免导团单标志 */
	private String termFlag = "";

	/** 属性最新报案修改人名称 */
	private String alterName = "";

	/** 属性最新报案修改人联系电话 */
	private String alterPhoneNumber = "";

	/** 属性最新报案修改人与被保险人关系 */
	private String alterRelationType = "";

	/** 属性最新报案修改时间 */
	private Date alterTime;

	/** 属性报案修改轨迹 */
	private String alterLocus = "";

	/** 属性报案修改方式 */
	private String alterType = "";

	/** 互碰自赔标志 0:非互碰自赔;1:是互碰自赔 */
	private String payselfFlag = "1";

	/** 属性三者车牌号 */
	private String thirdLicenseNo = "";

	/** 属性是否人伤 1:是 0：否 */
	private String personLossFlag = "0";

	/** 属性是否物损 1:是 0：否 */
	private String propLossFlag = "0";

	/** 属性报案类型 0：商业险单独报案 ，1：交强险单独报案，2：商业、交强险关联报案 */
	private String registType = "";

	/** 属性被保险人电话 */
	private String insuredPhoneNumber = "";

	/** 属性是否發簡訊 */
	private String sendMesFlag = "";

	/** 属性prpLthirdParties */
	private List<PrpLthirdParty> prpLthirdParties = new ArrayList<PrpLthirdParty>(0);

	/** 属性prpLdrivers */
	private List<PrpLdriver> prpLdrivers = new ArrayList<PrpLdriver>(0);

	/** 属性经办人名称 */
	private String handlerName = "";
	/** 属性经办人名称 */
	private String handler1Name = "";
	/** 属性部门名称 */
	private String comName = "";
	/** 属性起保日期 */
	private String startDate = "";
	/** 属性操作员名称 */
	private String operatorName = "";
	/** 属性理赔登记机构 */
	private String makeComName = "";
	/** 属性终保日期 */
	private String endDate = "";
	/** 属性条款名称 */
	private String clauseName = "";
	/** 属性出险开始分钟 */
	private String damageStartMinute = "";
	/** 属性处理单位名称 */
	private String handleUnitName = "";
	/** 属性接案人代码 */
	private String receiverCode = "";
	/** 属性事故所涉及险种 */
	private String referKind = "";
	/** 编辑类型 */
	private String editType = "";
	/** 属性报案分钟 */
	private String reportMinute = "";
	/** 交费情况 */
	private String payFlag = "";
	/** 单号 */
	private String certiNo = "";
	/** 单号类型 */
	private String certiType = "";
	/** 第三者亡人数 */
	private long personDeathB = 0;
	/** 第三者伤人数 */
	private long personInjureB = 0;
	/** 车上人员亡人数 */
	private long personDeathD1 = 0;
	/** 车上人员伤人数 */
	private long personInjureD1 = 0;
	/** 车上人员伤人数 */
	private String lextValue1 = "";
	/** 车上人员伤人数 */
	private String lextValue2 = "";

	/** 属性此报案的操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = "";
	/** 币别的名称 */
	private String estiCurrencyName = "";

	/** 被保险人类别 */
	private String customerType = "";

	/** 属性此报案的操作时间 */
	private Date operateDate = new Date();// Modify By Sunhao,2004-08-24

	/** 属性出险次数 */
	private int perilCount = 0;
	/** 属性最近N天出险次数 */
	private int recentCount = 0;
	/** 属性流程编号 */
	private String flowID = "";

	/** 排列记录的编号 */
	private int serialNo = 0;

	/** 调度标的的详细内容 */
	private String scheduleItemNote = "";

	/** 出险原因代码 */
	private String prpLregistDamageCode = "";

	/** 事故原因代码 */
	private String prpLregistDamageTypeCode = "";

	/** 属性标的序号 */
	private String lossItemCode = "";
	/** 列表 */
	private List<?> registList;
	/** 保险金额 */
	private Double sumAmount = 0D;

	/** 共保信息 */
	private String coinsFlag = "";

	/** 属性代理人代码 */
	private String agentCode = "";

	/** 属性代理人名称 */
	private String agentName = "";
	/** 流入系统时间 */
	private Date flowInTime = new Date();
	private Date signDate = new Date();
	private Date underWriteEndDate = new Date();
	private String othFlag = "";
	/** 属性被保险人显示名称 */
	private String insuredNameShow = "";
	private int startHour = 0;
	private int endHour = 0;
	private CompensateFeeDto compensateFeeDto;
	// 相应的工作流ID
	private String workFlowId;
	// 增加字段关联保单
	private Collection<String> relatepolicyNo = null;
	// 增加95519报案服务单号
	private String serviceNo = "";
	// 增加该报案是否允许修改标志，第1位 案件狀態，第2位可修改狀態
	// 00-已註銷，不可修改；01-未立案，可修改；10：已立案，不可修改；11-已立案，可修改；20-已結案，不可修改；21-已結案，可修改
	private String modifyFlag = "";
	// 保单里的被保险人电话
	private String policyInsuredPhoneNumber = "";

	/** 属性报案人手机 */
	private String reportorMobile = "";

	/** 出险车辆驾驶人手机 */
	private String driverMobile = "";

	/** 属性强制险出险原因代码 */
	private String damageCodeBZ = "";

	/** 属性强制险出险原因名称 */
	private String damageNameBZ = "";

	/** 被保险人手机 */
	private String policyInsuredMobile = "";

	/** 被保险人驾照 */
	private String policyInsuredLicenseNumber = "";

	/** 共摊标志 */
	private String sharingFlag = "0";

	/** 出险地点邮编名称 */
	private String addressName = "";
	/** 憲警單位 */
	private String authorityUnit = "";
	/** 船名 */
	private String shipCName = "";
	/** 機型 */
	private String shipModel = "";
	/** 理賠代號 */
	private String claimAgent = "";
	/** 地區別代號 */
	private String areaCode = "";
	/** 同险号码 */
	private String sameAddressNo = "";
	
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
	/** 是否為強制險區塊鏈攤賠案件**/
	private String isCompulsoryBchainClaim="";
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END
	
	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 START
	private String multiRecepNo="";//多元收件備案號碼
	private String channelSource="";//管道來源 001-RTC視訊/其他空
	private String memo="";//多元平台註記
	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 END

	
	@Column(name = "SHIPCNAME")
	public String getShipCName() {
		return shipCName;
	}

	public void setShipCName(String shipCName) {
		this.shipCName = shipCName;
	}

	@Column(name = "SHIPMODEL")
	public String getShipModel() {
		return shipModel;
	}

	public void setShipModel(String shipModel) {
		this.shipModel = shipModel;
	}

	@Column(name = "CLAIMAGENT")
	public String getClaimAgent() {
		return claimAgent;
	}

	public void setClaimAgent(String claimAgent) {
		this.claimAgent = claimAgent;
	}

	@Column(name = "AREACODE")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAuthorityUnit() {
		return authorityUnit;
	}

	public void setAuthorityUnit(String authorityUnit) {
		this.authorityUnit = authorityUnit;
	}

	/**
	 * 0：否；1：是/ /** 类PrpLregist的默认构造方法
	 */
	public PrpLregist() {
	}

	/**
	 * 属性报案号的getter方法
	 */
	@Id
	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
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
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性语种标志的getter方法
	 */

	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return this.language;
	}

	/**
	 * 属性语种标志的setter方法
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
	 * 属性被保险人地址的getter方法
	 */

	@Column(name = "INSUREDADDRESS")
	public String getInsuredAddress() {
		return this.insuredAddress;
	}

	/**
	 * 属性被保险人地址的setter方法
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
	public int getUseYears() {
		return this.useYears;
	}

	/**
	 * 属性车辆实际使用年限的setter方法
	 */
	public void setUseYears(int useYears) {
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
	 * 属性出险原因名称的getter方法
	 */

	@Column(name = "DAMAGENAME")
	public String getDamageName() {
		return this.damageName;
	}

	/**
	 * 属性出险原因名称的setter方法
	 */
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}

	/**
	 * 属性事故类型代码的getter方法
	 */

	@Column(name = "DAMAGETYPECODE")
	public String getDamageTypeCode() {
		return this.damageTypeCode;
	}

	/**
	 * 属性事故类型代码的setter方法
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
	 * 属性出险地点分类代码的getter方法
	 */

	@Column(name = "DAMAGEADDRESSTYPE")
	public String getDamageAddressType() {
		return this.damageAddressType;
	}

	/**
	 * 属性出险地点分类代码的setter方法
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
	 * 属性受损标的数量/出险分户数的getter方法
	 */

	@Column(name = "LOSSQUANTITY")
	public Double getLossQuantity() {
		return this.lossQuantity;
	}

	/**
	 * 属性受损标的数量/出险分户数的setter方法
	 */
	public void setLossQuantity(Double lossQuantity) {
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
	public Double getEstimateLoss() {
		return this.estimateLoss;
	}

	/**
	 * 属性估损金额的setter方法
	 */
	public void setEstimateLoss(Double estimateLoss) {
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
	 * 属性是否向别的保险公司投保的getter方法
	 */

	@Column(name = "REPEATINSUREFLAG")
	public String getRepeatInsureFlag() {
		return this.repeatInsureFlag;
	}

	/**
	 * 属性是否向别的保险公司投保的setter方法
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
	 * 属性出单机构的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性出单机构的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
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
	public Double getEstimateFee() {
		return this.estimateFee;
	}

	/**
	 * 属性未决赔款准备金的setter方法
	 */
	public void setEstimateFee(Double estimateFee) {
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
	 * 属性赔偿责任代码的getter方法
	 */

	@Column(name = "INDEMNITYDUTY")
	public String getIndemnityDuty() {
		return this.indemnityDuty;
	}

	/**
	 * 属性赔偿责任代码的setter方法
	 */
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}

	/**
	 * 属性简易赔案标记的getter方法
	 */

	@Column(name = "CLAIMTYPEFLAG")
	public String getClaimTypeFlag() {
		return this.claimTypeFlag;
	}

	/**
	 * 属性简易赔案标记的setter方法
	 */
	public void setClaimTypeFlag(String claimTypeFlag) {
		this.claimTypeFlag = claimTypeFlag;
	}

	/**
	 * 属性事故处理类型代码的getter方法
	 */

	@Column(name = "MANAGETYPE")
	public String getManageType() {
		return this.manageType;
	}

	/**
	 * 属性事故处理类型代码的setter方法
	 */
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	/**
	 * 属性事故处理类型名称的getter方法
	 */

	@Column(name = "MANAGETYPENAME")
	public String getManageTypeName() {
		return this.manageTypeName;
	}

	/**
	 * 属性事故处理类型名称的setter方法
	 */
	public void setManageTypeName(String manageTypeName) {
		this.manageTypeName = manageTypeName;
	}

	/**
	 * 属性天气代码的getter方法
	 */

	@Column(name = "WEATHER")
	public String getWeather() {
		return this.weather;
	}

	/**
	 * 属性天气代码的setter方法
	 */
	public void setWeather(String weather) {
		this.weather = weather;
	}

	/**
	 * 属性天气名称的getter方法
	 */

	@Column(name = "WEATHERNAME")
	public String getWeatherName() {
		return this.weatherName;
	}

	/**
	 * 属性天气名称的setter方法
	 */
	public void setWeatherName(String weatherName) {
		this.weatherName = weatherName;
	}

	/**
	 * 属性事故管制代码的getter方法
	 */

	@Column(name = "SECTION")
	public String getSection() {
		return this.section;
	}

	/**
	 * 属性事故管制代码的setter方法
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * 属性事故管制名称的getter方法
	 */

	@Column(name = "SECTIONNAME")
	public String getSectionName() {
		return this.sectionName;
	}

	/**
	 * 属性事故管制名称的setter方法
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * 属性报案人与被保险人关系代码的getter方法
	 */

	@Column(name = "RELATIONTYPE")
	public String getRelationType() {
		return this.relationType;
	}

	/**
	 * 属性报案人与被保险人关系代码的setter方法
	 */
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	/**
	 * 属性垫付赔案类型的getter方法
	 */

	@Column(name = "ADVANCETYPE")
	public String getAdvanceType() {
		return this.advanceType;
	}

	/**
	 * 属性垫付赔案类型的setter方法
	 */
	public void setAdvanceType(String advanceType) {
		this.advanceType = advanceType;
	}

	/**
	 * 属性是否免导团单标志的getter方法
	 */

	@Column(name = "TERMFLAG")
	public String getTermFlag() {
		return this.termFlag;
	}

	/**
	 * 属性是否免导团单标志的setter方法
	 */
	public void setTermFlag(String termFlag) {
		this.termFlag = termFlag;
	}

	/**
	 * 属性最新报案修改人名称的getter方法
	 */

	@Column(name = "ALTERNAME")
	public String getAlterName() {
		return this.alterName;
	}

	/**
	 * 属性最新报案修改人名称的setter方法
	 */
	public void setAlterName(String alterName) {
		this.alterName = alterName;
	}

	/**
	 * 属性最新报案修改人联系电话的getter方法
	 */

	@Column(name = "ALTERPHONENUMBER")
	public String getAlterPhoneNumber() {
		return this.alterPhoneNumber;
	}

	/**
	 * 属性最新报案修改人联系电话的setter方法
	 */
	public void setAlterPhoneNumber(String alterPhoneNumber) {
		this.alterPhoneNumber = alterPhoneNumber;
	}

	/**
	 * 属性最新报案修改人与被保险人关系的getter方法
	 */

	@Column(name = "ALTERRELATIONTYPE")
	public String getAlterRelationType() {
		return this.alterRelationType;
	}

	/**
	 * 属性最新报案修改人与被保险人关系的setter方法
	 */
	public void setAlterRelationType(String alterRelationType) {
		this.alterRelationType = alterRelationType;
	}

	/**
	 * 属性最新报案修改时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ALTERTIME")
	public Date getAlterTime() {
		return this.alterTime;
	}

	/**
	 * 属性最新报案修改时间的setter方法
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

	/**
	 * 属性三者车牌号的getter方法
	 */

	@Column(name = "THIRDLICENSENO")
	public String getThirdLicenseNo() {
		return this.thirdLicenseNo;
	}

	/**
	 * 属性三者车牌号的setter方法
	 */
	public void setThirdLicenseNo(String thirdLicenseNo) {
		this.thirdLicenseNo = thirdLicenseNo;
	}

	/**
	 * 属性是否人伤 1:是 0：否的getter方法
	 */

	@Column(name = "PERSONLOSSFLAG")
	public String getPersonLossFlag() {
		return this.personLossFlag;
	}

	/**
	 * 属性是否人伤 1:是 0：否的setter方法
	 */
	public void setPersonLossFlag(String personLossFlag) {
		this.personLossFlag = personLossFlag;
	}

	/**
	 * 属性被保险人电话的getter方法
	 */

	@Column(name = "INSUREDPHONENUMBER")
	public String getInsuredPhoneNumber() {
		return this.insuredPhoneNumber;
	}

	/**
	 * 属性被保险人电话的setter方法
	 */
	public void setInsuredPhoneNumber(String insuredPhoneNumber) {
		this.insuredPhoneNumber = insuredPhoneNumber;
	}

	/**
	 * 属性SENDMESFLAG的getter方法
	 */

	@Column(name = "SENDMESFLAG")
	public String getSendMesFlag() {
		return this.sendMesFlag;
	}

	/**
	 * 属性SENDMESFLAG的setter方法
	 */
	public void setSendMesFlag(String sendMesFlag) {
		this.sendMesFlag = sendMesFlag;
	}

	/**
	 * 属性prpLthirdParties的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLregist")
	public List<PrpLthirdParty> getPrpLthirdParties() {
		return this.prpLthirdParties;
	}

	/**
	 * 属性prpLthirdParties的setter方法
	 */
	public void setPrpLthirdParties(List<PrpLthirdParty> prpLthirdParties) {
		this.prpLthirdParties = prpLthirdParties;
	}

	/**
	 * 属性prpLdrivers的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLregist")
	public List<PrpLdriver> getPrpLdrivers() {
		return this.prpLdrivers;
	}

	/**
	 * 属性prpLdrivers的setter方法
	 */
	public void setPrpLdrivers(List<PrpLdriver> prpLdrivers) {
		this.prpLdrivers = prpLdrivers;
	}

	/**
	 * @return the serviceNo
	 */
	@Transient
	public String getServiceNo() {
		return serviceNo;
	}

	/**
	 * @param serviceNo the serviceNo to set
	 */
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}

	/**
	 * 设置属性被保险人名称
	 * @param insuredName 待设置的属性被保险人名称的值
	 */
	public void setInsuredNameShow(String insuredNameShow) {
		this.insuredNameShow = StringUtils.rightTrim(insuredNameShow);
	}

	/**
	 * 获取属性被保险人名称
	 * @return 属性被保险人名称的值
	 */
	@Transient
	public String getInsuredNameShow() {
		return insuredNameShow;
	}

	// 终保时间
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	@Transient
	public int getEndHour() {
		return endHour;
	}

	// 起保时间
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	@Transient
	public int getStartHour() {
		return startHour;
	}

	// modify by liuyanmei add 20051114 end
	/**
	 * @return Returns the agentCode.
	 */
	@Transient
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * @param agentCode The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * @return Returns the agentName.
	 */
	@Transient
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName The agentName to set.
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * @return Returns the flowInTime.
	 */
	@Transient
	public Date getFlowInTime() {
		return flowInTime;
	}

	/**
	 * @param flowInTime The flowInTime to set.
	 */
	public void setFlowInTime(Date flowInTime) {
		this.flowInTime = flowInTime;
	}

	/**
	 * 设置属性操作员名称
	 * @param makeComName 待设置的属性操作员名称的值
	 */
	public void setMakeComName(String makeComName) {
		this.makeComName = StringUtils.rightTrim(makeComName);
	}

	/**
	 * 获取属性理赔登记机构
	 * @return 属性理赔登记机构的值
	 */
	@Transient
	public String getMakeComName() {
		return makeComName;
	}

	/**
	 * 属性理赔登记机构
	 * @param operatorName 待设置的属性理赔登记机构名称的值
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = StringUtils.rightTrim(operatorName);
	}

	/**
	 * 获取属性操作员名称
	 * @return 属性操作员名称的值
	 */
	@Transient
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 设置属性经办人代码
	 * @param handlerCode 待设置的属性经办人代码的值
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = StringUtils.rightTrim(handlerName);
	}

	/**
	 * 获取属性经办人代码
	 * @return 属性经办人代码的值
	 */
	@Transient
	public String getHandler1Name() {
		return handler1Name;
	}

	/**
	 * 设置属性经办人代码
	 * @param handlerCode 待设置的属性经办人代码的值
	 */
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = StringUtils.rightTrim(handler1Name);
	}

	/**
	 * 获取属性经办人代码
	 * @return 属性经办人代码的值
	 */
	@Transient
	public String getHandlerName() {
		return handlerName;
	}

	/**
	 * 设置属性起保日期
	 * @param startDate 待设置的属性起保日期的值
	 */
	public void setStartDate(String startDate) {
		this.startDate = StringUtils.rightTrim(startDate);
	}

	/**
	 * 获取属性起保日期
	 * @return 属性起保日期的值
	 */
	@Transient
	public String getStartDate() {
		return startDate;
	}

	/**
	 * 设置属性终保日期
	 * @param endDate 待设置的属性终保日期的值
	 */
	public void setEndDate(String endDate) {
		this.endDate = StringUtils.rightTrim(endDate);
	}

	/**
	 * 获取属性终保日期
	 * @return 属性终保日期的值
	 */
	@Transient
	public String getEndDate() {
		return endDate;
	}

	/**
	 * 设置属性条款名称
	 * @param clauseName 待设置的属性条款名称的值
	 */
	public void setClauseName(String clauseName) {
		this.clauseName = StringUtils.rightTrim(clauseName);
	}

	/**
	 * 获取属性条款名称
	 * @return 属性条款名称的值
	 */
	@Transient
	public String getClauseName() {
		return clauseName;
	}

	/**
	 * 设置属性出险开始分钟
	 * @param damageStartMinute 待设置的属性出险开始分钟的值
	 */
	public void setDamageStartMinute(String damageStartMinute) {
		this.damageStartMinute = StringUtils.rightTrim(damageStartMinute);
	}

	/**
	 * 获取属性出险开始分钟
	 * @return 属性出险开始分钟的值
	 */
	@Transient
	public String getDamageStartMinute() {
		return damageStartMinute;
	}

	/**
	 * 设置属性接案人代码
	 * @param receiverCode 待设置的属性接案人代码的值
	 */
	public void setReceiverCode(String receiverCode) {
		this.receiverCode = StringUtils.rightTrim(receiverCode);
	}

	/**
	 * 获取属性接案人代码
	 * @return 属性接案人代码的值
	 */
	@Transient
	public String getReceiverCode() {
		return receiverCode;
	}

	/**
	 * 设置属性处理单位名称
	 * @param handleUnitName 待设置的属性处理单位名称的值
	 */
	public void setHandleUnitName(String handleUnitName) {
		this.handleUnitName = StringUtils.rightTrim(handleUnitName);
	}

	/**
	 * 获取属性处理单位名称
	 * @return 属性处理单位名称
	 */
	@Transient
	public String getHandleUnitName() {
		return handleUnitName;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public List<?> getRegistList() {
		return registList;
	}

	/**
	 * 设置列表
	 * @param registList 待设置的列表
	 */
	public void setRegistList(List<?> registList) {
		this.registList = registList;
	}

	/**
	 * 设置属性操作状态
	 * @param status 待设置的属性操作状态
	 */
	public void setStatus(String status) {
		this.status = StringUtils.rightTrim(status);
	}

	/**
	 * 获取属性操作状态
	 * @return 属性操作状态
	 */
	@Transient
	public String getStatus() {
		return status;
	}

	/**
	 * 设置属性操作时间
	 * @param operateDate 待设置的属性操作时间 Modify By Sunhao,2004-08-24
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public void setReportMinute(String reportMinute) {
		this.reportMinute = reportMinute;
	}

	/**
	 * 获取属性操作时间名称
	 * @param status 待设置的属性操作时间 Modify By Sunhao,2004-08-24
	 */
	@Transient
	public Date getOperateDate() {
		return operateDate;
	}

	@Transient
	public String getReportMinute() {
		return reportMinute;
	}

	/**
	 * 设置属性币别名称
	 * @param estiCurrencyName 待设置的属性币别名称的值
	 */
	public void setEstiCurrencyName(String estiCurrencyName) {
		this.estiCurrencyName = StringUtils.rightTrim(estiCurrencyName);
	}

	/**
	 * 获取属性币别名称
	 * @return 属性币别名称
	 */
	@Transient
	public String getEstiCurrencyName() {
		return estiCurrencyName;
	}

	/**
	 * 设置属性被保险人类别
	 * @param customerType 待设置的属性被保险人类别的值
	 */
	public void setCustomerType(String customerType) {
		this.customerType = StringUtils.rightTrim(customerType);
	}

	/**
	 * 获取属性被保险人类别
	 * @return 属性被保险人类别
	 */
	@Transient
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * 设置属性出险次数
	 * @param perilCount 待设置的属性出险次数的值
	 */
	public void setPerilCount(int perilCount) {
		this.perilCount = perilCount;
	}

	/**
	 * 获取属性出险次数
	 * @return 属性出险次数的值
	 */
	@Transient
	public int getPerilCount() {
		return perilCount;
	}

	/**
	 * 设置属性事故所涉及险种
	 * @param referKind 待设置的属性事故所涉及险种的值
	 */
	public void setReferKind(String referKind) {
		this.referKind = StringUtils.rightTrim(referKind);
	}

	/**
	 * 获取属性事故所涉及险种
	 * @return 属性事故所涉及险种的值
	 */
	@Transient
	public String getReferKind() {
		return referKind;
	}

	public void setFlowID(String flowID) {
		this.flowID = flowID;
	}

	@Transient
	public String getPayFlag() {
		return payFlag;
	}

	@Transient
	public String getFlowID() {
		return flowID;
	}

	/**
	 * 设置属性排列记录的编号
	 * @param serialNo 待设置的属性排列记录的编号的值
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * 获取属性排列记录的编号
	 * @return 属性排列记录的编号
	 */
	@Transient
	public int getSerialNo() {
		return serialNo;
	}

	/**
	 * 设置属性调度标的的详细内容
	 * @param scheduleItemNote 待设置的属性调度标的的详细内容的值
	 */
	public void setScheduleItemNote(String scheduleItemNote) {
		this.scheduleItemNote = StringUtils.rightTrim(scheduleItemNote);
	}

	/**
	 * 获取属性调度标的的详细内容
	 * @return 属性调度标的的详细内容
	 */
	@Transient
	public String getScheduleItemNote() {
		return scheduleItemNote;
	}

	/**
	 * 设置属性出险原因代码的详细内容
	 * @param prpLregistDamageCode 待设置的属性出险原因代码的详细内容的值
	 */
	public void setPrpLregistDamageCode(String prpLregistDamageCode) {
		this.prpLregistDamageCode = StringUtils.rightTrim(prpLregistDamageCode);
	}

	/**
	 * 获取属性出险原因代码的详细内容
	 * @return 属性出险原因代码的详细内容
	 */
	@Transient
	public String getPrpLregistDamageCode() {
		return prpLregistDamageCode;
	}

	/**
	 * 设置属性事故原因代码的详细内容
	 * @param prpLregistDamageTypeCode 待设置的属性事故原因代码的详细内容的值
	 */
	public void setPrpLregistDamageTypeCode(String prpLregistDamageTypeCode) {
		this.prpLregistDamageTypeCode = StringUtils.rightTrim(prpLregistDamageTypeCode);
	}

	/**
	 * 获取属性事故原因代码的详细内容
	 * @return 属性事故原因代码的详细内容
	 */
	@Transient
	public String getPrpLregistDamageTypeCode() {
		return prpLregistDamageTypeCode;
	}

	/**
	 * 设置属性标的序号
	 * @param lossItemCode 待设置的属性标的序号的值
	 */
	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = StringUtils.rightTrim(lossItemCode);
	}

	/**
	 * 获取属性标的序号
	 * @return 属性标的序号的值
	 */
	@Transient
	public String getLossItemCode() {
		return lossItemCode;
	}

	/**
	 * @return Returns the recentCount.
	 */
	@Transient
	public int getRecentCount() {
		return recentCount;
	}

	/**
	 * @param recentCount The recentCount to set.
	 */
	public void setRecentCount(int recentCount) {
		this.recentCount = recentCount;
	}

	// 原因：意键险的报案画面需要显示保单中的保险金额
	/**
	 * 设置属性的保险金额
	 * @param sumAmount 待设置属性的保险金额
	 */
	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 获得属性的保险金额
	 * @return 属性的保险金额
	 */
	@Transient
	public Double getSumAmount() {
		return this.sumAmount;
	}

	/**
	 * 设置单号
	 * @param certiNo 单号
	 */
	public void setCertiNo(String certiNo) {
		this.certiNo = certiNo;
	}

	/**
	 * 获取单号
	 * @return 单号
	 */
	@Transient
	public String getCertiNo() {
		return certiNo;
	}

	/**
	 * 设置单号类型
	 * @param certiType 单号类型
	 */
	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	/**
	 * 获取属性单号类型
	 * @return 属性单号类型
	 */
	@Transient
	public String getCertiType() {
		return certiType;
	}

	/**
	 * 设置属性第三者亡人数
	 * @param personDeathB 第三者亡人数
	 */
	public void setPersonDeathB(long personDeathB) {
		this.personDeathB = personDeathB;
	}

	/**
	 * 获取属性第三者亡人数
	 * @return 属性第三者亡人数
	 */
	@Transient
	public long getPersonDeathB() {
		return personDeathB;
	}

	/**
	 * 设置属性第三者伤人数
	 * @param personInjureB 第三者伤人数
	 */
	public void setPersonInjureB(long personInjureB) {
		this.personInjureB = personInjureB;
	}

	/**
	 * 获取属性第三者伤人数
	 * @return 属性第三者伤人数
	 */
	@Transient
	public long getPersonInjureB() {
		return personInjureB;
	}

	/**
	 * 设置属性车上人员亡人数
	 * @param personDeathD1 车上人员亡人数
	 */
	public void setPersonDeathD1(long personDeathD1) {
		this.personDeathD1 = personDeathD1;
	}

	/**
	 * 获取属性车上人员亡人数
	 * @return 属性车上人员亡人数
	 */
	@Transient
	public long getPersonDeathD1() {
		return personDeathD1;
	}

	/**
	 * 设置属性车上人员伤人数
	 * @param personInjureD1 车上人员伤人数
	 */
	public void setPersonInjureD1(long personInjureD1) {
		this.personInjureD1 = personInjureD1;
	}

	/**
	 * 获取属性车上人员伤人数
	 * @return 属性车上人员伤人数
	 */
	@Transient
	public long getPersonInjureD1() {
		return personInjureD1;
	}

	@Transient
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	@Transient
	public Date getUnderWriteEndDate() {
		return underWriteEndDate;
	}

	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	@Transient
	public String getOthFlag() {
		return othFlag;
	}

	public void setOthFlag(String othFlag) {
		this.othFlag = othFlag;
	}

	// 共保标志
	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return coinsFlag;
	}

	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}

	@Transient
	public CompensateFeeDto getCompensateFeeDto() {
		return compensateFeeDto;
	}

	public void setCompensateFeeDto(CompensateFeeDto compensateFeeDto) {
		this.compensateFeeDto = compensateFeeDto;
	}

	@Transient
	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	// add by zhouliu start at 2006-6-9
	// reason:强三查询
	@Transient
	public Collection<String> getRelatepolicyNo() {
		return relatepolicyNo;
	}

	public void setRelatepolicyNo(Collection<String> relatepolicyNo) {
		this.relatepolicyNo = relatepolicyNo;
	}

	@Transient
	public String getLextValue1() {
		return lextValue1;
	}

	public void setLextValue1(String lextValue1) {
		this.lextValue1 = lextValue1;
	}

	@Transient
	public String getLextValue2() {
		return lextValue2;
	}

	public void setLextValue2(String lextValue2) {
		this.lextValue2 = lextValue2;
	}

	/**
	 * @return the modifyFlag
	 */
	@Transient
	public String getModifyFlag() {
		return modifyFlag;
	}

	/**
	 * @param modifyFlag the modifyFlag to set
	 */
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	@Transient
	public String getPolicyInsuredPhoneNumber() {
		return policyInsuredPhoneNumber;
	}

	public void setPolicyInsuredPhoneNumber(String policyInsuredPhoneNumber) {
		this.policyInsuredPhoneNumber = policyInsuredPhoneNumber;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	/**
	 * 设置属性部门
	 * @param comName 待设置的属性部门的值
	 */
	public void setComName(String comName) {
		this.comName = StringUtils.rightTrim(comName);
	}

	/**
	 * 获取属性部门
	 * @return 属性部门的值
	 */
	@Transient
	public String getComName() {
		return comName;
	}

	/**
	 * 设置编辑类型
	 * @param editType 待设置的编辑类型的值
	 */
	public void setEditType(String editType) {
		this.editType = StringUtils.rightTrim(editType);
	}

	/**
	 * 获取编辑类型
	 * @return 属性编辑类型
	 */
	@Transient
	public String getEditType() {
		return editType;
	}

	@Column(name = "PROPLOSSFLAG")
	public String getPropLossFlag() {
		return propLossFlag;
	}

	public void setPropLossFlag(String propLossFlag) {
		this.propLossFlag = propLossFlag;
	}

	@Column(name = "PAYSELFFLAG")
	public String getPayselfFlag() {
		return payselfFlag;
	}

	public void setPayselfFlag(String payselfFlag) {
		this.payselfFlag = payselfFlag;
	}

	@Column(name = "REGISTTYPE")
	public String getRegistType() {
		return registType;
	}

	public void setRegistType(String registType) {
		this.registType = registType;
	}

	@Column(name = "REPORTORMOBILE")
	public String getReportorMobile() {
		return reportorMobile;
	}

	public void setReportorMobile(String reportorMobile) {
		this.reportorMobile = reportorMobile;
	}

	/**
	 * 强制险出险原因代码
	 */
	@Column(name = "DAMAGECODEBZ")
	public String getDamageCodeBZ() {
		return damageCodeBZ;
	}

	public void setDamageCodeBZ(String damageCodeBZ) {
		this.damageCodeBZ = damageCodeBZ;
	}

	@Column(name = "DAMAGENAMEBZ")
	public String getDamageNameBZ() {
		return damageNameBZ;
	}

	public void setDamageNameBZ(String damageNameBZ) {
		this.damageNameBZ = damageNameBZ;
	}

	@Column(name = "SHARINGFLAG")
	public String getSharingFlag() {
		return sharingFlag;
	}

	public void setSharingFlag(String sharingFlag) {
		this.sharingFlag = sharingFlag;
	}

	@Transient
	public String getPolicyInsuredMobile() {
		return policyInsuredMobile;
	}

	public void setPolicyInsuredMobile(String policyInsuredMobile) {
		this.policyInsuredMobile = policyInsuredMobile;
	}

	@Transient
	public String getPolicyInsuredLicenseNumber() {
		return policyInsuredLicenseNumber;
	}

	public void setPolicyInsuredLicenseNumber(String policyInsuredLicenseNumber) {
		this.policyInsuredLicenseNumber = policyInsuredLicenseNumber;
	}

	@Column(name = "DRIVERMOBILE")
	public String getDriverMobile() {
		return driverMobile;
	}

	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}

	@Transient
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	@Transient
	public String getSameAddressNo() {
		return sameAddressNo;
	}

	public void setSameAddressNo(String sameAddressNo) {
		this.sameAddressNo = sameAddressNo;
	}

	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START
	@Column(name = "ISCOMPULSORYBCHAINCLAIM")
	public String getIsCompulsoryBchainClaim() {
		return isCompulsoryBchainClaim;
	}

	public void setIsCompulsoryBchainClaim(String isCompulsoryBchainClaim) {
		this.isCompulsoryBchainClaim = isCompulsoryBchainClaim;
	}
	//mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END

	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 START
//	@Transient
	@Column(name = "MULTIRECEPNO")
	public String getMultiRecepNo() {
		return multiRecepNo;
	}

	public void setMultiRecepNo(String multiRecepNo) {
		this.multiRecepNo = multiRecepNo;
	}
//	@Transient
	@Column(name = "CHANNELSOURCE")
	public String getChannelSource() {
		return channelSource;
	}

	public void setChannelSource(String channelSource) {
		this.channelSource = channelSource;
	}
//	@Transient
	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	//mantis：CLM0259、CLM9001，處理人員：DP0713，需求單編號：新核心-多元理賠收件平台建置案 END

	
	
}
