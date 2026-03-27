package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpCmain
 */
@Entity
@Table(name = "PRPCOPYMAIN")
public class PrpCopyMain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性批单号 */
	private String endorseNo;
	
	/** 属性保单号 */
	private String policyNo;

	/** 属性险类代码 */
	private String classCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性投保单号 */
	private String proposalNo;

	/** 属性合同号(供合保单使用) */
	private String contractNo;

	/** 属性保单种类 */
	private String policySort;

	/** 属性保单印刷号 */
	private String printNo;

	/** 属性业务来源（直接/代理） */
	private String businessNature;

	/** 属性语种标志 */
	private String language;

	/** 属性保单类型 */
	private String policyType;

	/** 属性投保人代码 */
	private String appliCode;

	/** 属性投保人名称 */
	private String appliName;

	/** 属性投保人地址 */
	private String appliAddress;

	/** 属性被保险人代码 */
	private String insuredCode;

	/** 属性被保险人名称 */
	private String insuredName;

	/** 属性被保险人地址 */
	private String insuredAddress;

	/** 属性签单日期（制单日期） */
	private Date operateDate;

	/** 属性起保日期（启运日期） */
	private Date startDate;

	/** 属性起保小时 */
	private Integer startHour;

	/** 属性终保日期 */
	private Date endDate;

	/** 属性终保小时 */
	private Integer endHour;

	/** 属性净费率 */
	private Double pureRate;

	/** 属性手续费比例 */
	private Double disRate;

	/** 属性总折扣率 */
	private Double discount;

	/** 属性币别代码 */
	private String currency;

	/** 属性总保险价值 */
	private Double sumValue;

	/** 属性总保险金额 */
	private Double sumAmount;

	/** 属性总折扣金额 */
	private Double sumDiscount;

	/** 属性原总保险费 */
	private Double sumPremium;

	/** 属性总附加险保费 */
	private Double sumSubPrem;

	/** 属性被保险总数量 */
	private Integer sumQuantity;

	/** 属性司法管辖代码 */
	private String judicalCode;

	/** 属性司法管辖 */
	private String judicalScope;

	/** 属性交费方式 */
	private String autoTransRenewFlag;

	/** 属性争议解决方式 */
	private String argueSolution;

	/** 属性仲裁委员会名称 */
	private String arbitBoardName;

	/** 属性约定分期交费次数 */
	private Integer payTimes;

	/** 属性批改次数 */
	private Integer endorseTimes;

	/** 属性理赔次数 */
	private Integer claimTimes;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性签单地点 */
	private String operateSite;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性经办人代码 */
	private String handlerCode;

	/** 属性归属业务员代码 */
	private String handler1Code;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性最终核保人代码 */
	private String underwriteCode;

	/** 属性最终核保人名称 */
	private String underWriteName;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性计算机输单小时 */
	private Integer inputHour;

	/** 属性核保完成日期 */
	private Date underwriteEndDate;

	/** 属性保单统计年月 */
	private Date statisticsYM;

	/** 属性代理人代码 */
	private String agentCode;

	/** 属性联共保标志 */
	private String coinsFlag;

	/** 属性商业分保标志 */
	private String reinsFlag;

	/** 属性统保标志 */
	private String allinsFlag;

	/** 属性核保标志 */
	private String underWriteFlag;

	/** 属性其它标志字段 */
	private String othFlag;

	/** 属性状态字段 */
	private String flag;

	/** 属性超出部分手续费比例 */
	private Double disRate1;

	/** 属性业务类型 */
	private String businessFlag;

	/** 属性最後一次修改人员代码 */
	private String updaterCode;

	/** 属性最後一次修改日期 */
	private Date updateDate;

	/** 属性最後一次修改时间 */
	private String updateHour;

	/** 属性签单日期 */
	private Date signDate;

	/** 属性是否股东业务标识 */
	private String shareHolderFlag;

	/** 属性协议号 */
	private String agreementNo;

	/** 属性询价单号 */
	private String inquiryNo;

	/** 属性缴费方式 */
	private String payMode;

	/** 属性备注 */
	private String remark;

	/** 属性保单流水号 */
	private String visaCode;

	/** 属性MANUALTYPE */
	private String manualType;

	/** 属性NATIONFLAG */
	private String nationFlag;

	/** 属性STARTMINUTE */
	private Byte startMinute;

	/** 属性ENDMINUTE */
	private Byte endMinute;

	/** 属性见费出单标志位 */
	private String jfeeFlag;

	/** 属性预审核时间 */
	private Date preCheckDate;

	/** 属性经办人姓名 */
	private String handlerName;

	/** 属性归属业务员姓名 */
	private String handler1Name;

	/** 属性实收确认人代码 */
	private String payRefCode;

	/** 属性实收确认人姓名 */
	private String payRefName;

	/** 属性实收确认时间 */
	private Date payRefTime;

	/** 属性保单打印时间 */
	private Date printTime;

	/** 属性AGRITYPE */
	private String agriType;

	/** 属性SUBBUSINESSNATURE */
	private String subBusinessNature;

	/** 属性BANKCODE */
	private String bankCode;

	/** 属性CHANNELTYPE */
	private String channelType;

	/** 属性EXCHANGERATE */
	private Double exchangeRate;

	/** 属性产品业务标识 */
	private String projectsFlag;

	/** 属性投保单核保通过级别 */
	private String proposalLevel;

	/** 属性停驶次数 */
	private String stopTimes;

	/** 属性EFFECTIVEIMMEDIATELYFLAG */
	private String effectiveImmediatelyFlag;

	/** 属性NEWSTARTDATE */
	private Date newStartDate;

	/** 属性NEWENDDATE */
	private Date newEndDate;

	/** 属性团队类型 */
	private String groupType;

	/** 属性批量代收代付起始缴费期次 */
	private Integer startStages;

	/** 属性锁定人代码 */
	private String lockerCode;

	/** 属性保单加锁标记 */
	private String editFlag;

	/** 属性未续保登记原因 */
	private String rsnNoRenewal;

	/** 属性未续保登记原因代码 */
	private String notRenewalRegist;

	/** 属性DECLAREFLAG */
	private String declareFlag;

	/** 属性CONTRIBUTIONLEVEL */
	private String contributionLevel;

	/** 号牌号码 */
	private String licenseNo = "";
	/** 属性厂牌型号 */
	private String brandName = "";
	/** 交费情况 */
	private String payFlag = "";
	/** 显示的标志，当前日期超过终保日期，此行字显示为红色，若保费未缴/已缴未缴全也显示红色 */
	private String colorFlag = "";
	private String frameNo = "";
	private String damageDate = "";
	private String damageHour = "";
	private String vINNo = "";
	private String carOwne = "";
	private String licenseColorCode = "";
	private String modelCode = "";

	private String carKindCode = "";
	private String relationPolicyNo = "";
	private String engineNo = "";

	private String icenseColorCode = "";
	private String licenseColorName = "";
	private String relationRiskCode = "";

	/*
	 * 二期begin
	 */
	private String z2_regJplanFee = "";

	private String z2_curJplanFee = "";

	private String z2_handlerCode = "";

	private String z2_handlerName = "";

	/**
	 * 类PrpCmain的默认构造方法
	 */
	public PrpCopyMain() {
	}

	/**
	 * 属性批单号码的getter方法
	 */
	@Id
	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return this.endorseNo;
	}

	/**
	 * 属性批单号码的setter方法
	 */
	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
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
	 * 属性投保单号的getter方法
	 */

	@Column(name = "PROPOSALNO")
	public String getProposalNo() {
		return this.proposalNo;
	}

	/**
	 * 属性投保单号的setter方法
	 */
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	/**
	 * 属性合同号(供合保单使用)的getter方法
	 */

	@Column(name = "CONTRACTNO")
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * 属性合同号(供合保单使用)的setter方法
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 属性保单种类的getter方法
	 */

	@Column(name = "POLICYSORT")
	public String getPolicySort() {
		return this.policySort;
	}

	/**
	 * 属性保单种类的setter方法
	 */
	public void setPolicySort(String policySort) {
		this.policySort = policySort;
	}

	/**
	 * 属性保单印刷号的getter方法
	 */

	@Column(name = "PRINTNO")
	public String getPrintNo() {
		return this.printNo;
	}

	/**
	 * 属性保单印刷号的setter方法
	 */
	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}

	/**
	 * 属性业务来源（直接/代理）的getter方法
	 */

	@Column(name = "BUSINESSNATURE")
	public String getBusinessNature() {
		return this.businessNature;
	}

	/**
	 * 属性业务来源（直接/代理）的setter方法
	 */
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
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
	 * 属性保单类型的getter方法
	 */

	@Column(name = "POLICYTYPE")
	public String getPolicyType() {
		return this.policyType;
	}

	/**
	 * 属性保单类型的setter方法
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	/**
	 * 属性投保人代码的getter方法
	 */

	@Column(name = "APPLICODE")
	public String getAppliCode() {
		return this.appliCode;
	}

	/**
	 * 属性投保人代码的setter方法
	 */
	public void setAppliCode(String appliCode) {
		this.appliCode = appliCode;
	}

	/**
	 * 属性投保人名称的getter方法
	 */

	@Column(name = "APPLINAME")
	public String getAppliName() {
		return this.appliName;
	}

	/**
	 * 属性投保人名称的setter方法
	 */
	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	/**
	 * 属性投保人地址的getter方法
	 */

	@Column(name = "APPLIADDRESS")
	public String getAppliAddress() {
		return this.appliAddress;
	}

	/**
	 * 属性投保人地址的setter方法
	 */
	public void setAppliAddress(String appliAddress) {
		this.appliAddress = appliAddress;
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
	 * 属性签单日期（制单日期）的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATEDATE")
	public Date getOperateDate() {
		return this.operateDate;
	}

	/**
	 * 属性签单日期（制单日期）的setter方法
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性起保日期（启运日期）的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性起保日期（启运日期）的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 属性起保小时的getter方法
	 */

	@Column(name = "STARTHOUR")
	public Integer getStartHour() {
		return this.startHour;
	}

	/**
	 * 属性起保小时的setter方法
	 */
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	/**
	 * 属性终保日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性终保日期的setter方法
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 属性终保小时的getter方法
	 */

	@Column(name = "ENDHOUR")
	public Integer getEndHour() {
		return this.endHour;
	}

	/**
	 * 属性终保小时的setter方法
	 */
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	/**
	 * 属性净费率的getter方法
	 */

	@Column(name = "PURERATE")
	public Double getPureRate() {
		return this.pureRate;
	}

	/**
	 * 属性净费率的setter方法
	 */
	public void setPureRate(Double pureRate) {
		this.pureRate = pureRate;
	}

	/**
	 * 属性手续费比例的getter方法
	 */

	@Column(name = "DISRATE")
	public Double getDisRate() {
		return this.disRate;
	}

	/**
	 * 属性手续费比例的setter方法
	 */
	public void setDisRate(Double disRate) {
		this.disRate = disRate;
	}

	/**
	 * 属性总折扣率的getter方法
	 */

	@Column(name = "DISCOUNT")
	public Double getDiscount() {
		return this.discount;
	}

	/**
	 * 属性总折扣率的setter方法
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
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
	 * 属性总保险价值的getter方法
	 */

	@Column(name = "SUMVALUE")
	public Double getSumValue() {
		return this.sumValue;
	}

	/**
	 * 属性总保险价值的setter方法
	 */
	public void setSumValue(Double sumValue) {
		this.sumValue = sumValue;
	}

	/**
	 * 属性总保险金额的getter方法
	 */

	@Column(name = "SUMAMOUNT")
	public Double getSumAmount() {
		return this.sumAmount;
	}

	/**
	 * 属性总保险金额的setter方法
	 */
	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 属性总折扣金额的getter方法
	 */

	@Column(name = "SUMDISCOUNT")
	public Double getSumDiscount() {
		return this.sumDiscount;
	}

	/**
	 * 属性总折扣金额的setter方法
	 */
	public void setSumDiscount(Double sumDiscount) {
		this.sumDiscount = sumDiscount;
	}

	/**
	 * 属性原总保险费的getter方法
	 */

	@Column(name = "SUMPREMIUM")
	public Double getSumPremium() {
		return this.sumPremium;
	}

	/**
	 * 属性原总保险费的setter方法
	 */
	public void setSumPremium(Double sumPremium) {
		this.sumPremium = sumPremium;
	}

	/**
	 * 属性总附加险保费的getter方法
	 */

	@Column(name = "SUMSUBPREM")
	public Double getSumSubPrem() {
		return this.sumSubPrem;
	}

	/**
	 * 属性总附加险保费的setter方法
	 */
	public void setSumSubPrem(Double sumSubPrem) {
		this.sumSubPrem = sumSubPrem;
	}

	/**
	 * 属性被保险总数量的getter方法
	 */

	@Column(name = "SUMQUANTITY")
	public Integer getSumQuantity() {
		return this.sumQuantity;
	}

	/**
	 * 属性被保险总数量的setter方法
	 */
	public void setSumQuantity(Integer sumQuantity) {
		this.sumQuantity = sumQuantity;
	}

	/**
	 * 属性司法管辖代码的getter方法
	 */

	@Column(name = "JUDICALCODE")
	public String getJudicalCode() {
		return this.judicalCode;
	}

	/**
	 * 属性司法管辖代码的setter方法
	 */
	public void setJudicalCode(String judicalCode) {
		this.judicalCode = judicalCode;
	}

	/**
	 * 属性司法管辖的getter方法
	 */

	@Column(name = "JUDICALSCOPE")
	public String getJudicalScope() {
		return this.judicalScope;
	}

	/**
	 * 属性司法管辖的setter方法
	 */
	public void setJudicalScope(String judicalScope) {
		this.judicalScope = judicalScope;
	}

	/**
	 * 属性交费方式的getter方法
	 */

	@Column(name = "AUTOTRANSRENEWFLAG")
	public String getAutoTransRenewFlag() {
		return this.autoTransRenewFlag;
	}

	/**
	 * 属性交费方式的setter方法
	 */
	public void setAutoTransRenewFlag(String autoTransRenewFlag) {
		this.autoTransRenewFlag = autoTransRenewFlag;
	}

	/**
	 * 属性争议解决方式的getter方法
	 */

	@Column(name = "ARGUESOLUTION")
	public String getArgueSolution() {
		return this.argueSolution;
	}

	/**
	 * 属性争议解决方式的setter方法
	 */
	public void setArgueSolution(String argueSolution) {
		this.argueSolution = argueSolution;
	}

	/**
	 * 属性仲裁委员会名称的getter方法
	 */

	@Column(name = "ARBITBOARDNAME")
	public String getArbitBoardName() {
		return this.arbitBoardName;
	}

	/**
	 * 属性仲裁委员会名称的setter方法
	 */
	public void setArbitBoardName(String arbitBoardName) {
		this.arbitBoardName = arbitBoardName;
	}

	/**
	 * 属性约定分期交费次数的getter方法
	 */

	@Column(name = "PAYTIMES")
	public Integer getPayTimes() {
		return this.payTimes;
	}

	/**
	 * 属性约定分期交费次数的setter方法
	 */
	public void setPayTimes(Integer payTimes) {
		this.payTimes = payTimes;
	}

	/**
	 * 属性批改次数的getter方法
	 */

	@Column(name = "ENDORSETIMES")
	public Integer getEndorseTimes() {
		return this.endorseTimes;
	}

	/**
	 * 属性批改次数的setter方法
	 */
	public void setEndorseTimes(Integer endorseTimes) {
		this.endorseTimes = endorseTimes;
	}

	/**
	 * 属性理赔次数的getter方法
	 */

	@Column(name = "CLAIMTIMES")
	public Integer getClaimTimes() {
		return this.claimTimes;
	}

	/**
	 * 属性理赔次数的setter方法
	 */
	public void setClaimTimes(Integer claimTimes) {
		this.claimTimes = claimTimes;
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
	 * 属性签单地点的getter方法
	 */

	@Column(name = "OPERATESITE")
	public String getOperateSite() {
		return this.operateSite;
	}

	/**
	 * 属性签单地点的setter方法
	 */
	public void setOperateSite(String operateSite) {
		this.operateSite = operateSite;
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
	 * 属性复核人代码的getter方法
	 */

	@Column(name = "APPROVERCODE")
	public String getApproverCode() {
		return this.approverCode;
	}

	/**
	 * 属性复核人代码的setter方法
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	/**
	 * 属性最终核保人代码的getter方法
	 */

	@Column(name = "UNDERWRITECODE")
	public String getUnderwriteCode() {
		return this.underwriteCode;
	}

	/**
	 * 属性最终核保人代码的setter方法
	 */
	public void setUnderwriteCode(String underwriteCode) {
		this.underwriteCode = underwriteCode;
	}

	/**
	 * 属性最终核保人名称的getter方法
	 */

	@Column(name = "UNDERWRITENAME")
	public String getUnderWriteName() {
		return this.underWriteName;
	}

	/**
	 * 属性最终核保人名称的setter方法
	 */
	public void setUnderWriteName(String underWriteName) {
		this.underWriteName = underWriteName;
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
	 * 属性计算机输单小时的getter方法
	 */

	@Column(name = "INPUTHOUR")
	public Integer getInputHour() {
		return this.inputHour;
	}

	/**
	 * 属性计算机输单小时的setter方法
	 */
	public void setInputHour(Integer inputHour) {
		this.inputHour = inputHour;
	}

	/**
	 * 属性核保完成日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDERWRITEENDDATE")
	public Date getUnderwriteEndDate() {
		return this.underwriteEndDate;
	}

	/**
	 * 属性核保完成日期的setter方法
	 */
	public void setUnderwriteEndDate(Date underwriteEndDate) {
		this.underwriteEndDate = underwriteEndDate;
	}

	/**
	 * 属性保单统计年月的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STATISTICSYM")
	public Date getStatisticsYM() {
		return this.statisticsYM;
	}

	/**
	 * 属性保单统计年月的setter方法
	 */
	public void setStatisticsYM(Date statisticsYM) {
		this.statisticsYM = statisticsYM;
	}

	/**
	 * 属性代理人代码的getter方法
	 */

	@Column(name = "AGENTCODE")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**
	 * 属性代理人代码的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 属性联共保标志的getter方法
	 */

	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return this.coinsFlag;
	}

	/**
	 * 属性联共保标志的setter方法
	 */
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}

	/**
	 * 属性商业分保标志的getter方法
	 */

	@Column(name = "REINSFLAG")
	public String getReinsFlag() {
		return this.reinsFlag;
	}

	/**
	 * 属性商业分保标志的setter方法
	 */
	public void setReinsFlag(String reinsFlag) {
		this.reinsFlag = reinsFlag;
	}

	/**
	 * 属性统保标志的getter方法
	 */

	@Column(name = "ALLINSFLAG")
	public String getAllinsFlag() {
		return this.allinsFlag;
	}

	/**
	 * 属性统保标志的setter方法
	 */
	public void setAllinsFlag(String allinsFlag) {
		this.allinsFlag = allinsFlag;
	}

	/**
	 * 属性核保标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return this.underWriteFlag;
	}

	/**
	 * 属性核保标志的setter方法
	 */
	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
	}

	/**
	 * 属性其它标志字段的getter方法
	 */

	@Column(name = "OTHFLAG")
	public String getOthFlag() {
		return this.othFlag;
	}

	/**
	 * 属性其它标志字段的setter方法
	 */
	public void setOthFlag(String othFlag) {
		this.othFlag = othFlag;
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
	 * 属性超出部分手续费比例的getter方法
	 */

	@Column(name = "DISRATE1")
	public Double getDisRate1() {
		return this.disRate1;
	}

	/**
	 * 属性超出部分手续费比例的setter方法
	 */
	public void setDisRate1(Double disRate1) {
		this.disRate1 = disRate1;
	}

	/**
	 * 属性业务类型的getter方法
	 */

	@Column(name = "BUSINESSFLAG")
	public String getBusinessFlag() {
		return this.businessFlag;
	}

	/**
	 * 属性业务类型的setter方法
	 */
	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}

	/**
	 * 属性最後一次修改人员代码的getter方法
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**
	 * 属性最後一次修改人员代码的setter方法
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**
	 * 属性最後一次修改日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 属性最後一次修改日期的setter方法
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 属性最後一次修改时间的getter方法
	 */

	@Column(name = "UPDATEHOUR")
	public String getUpdateHour() {
		return this.updateHour;
	}

	/**
	 * 属性最後一次修改时间的setter方法
	 */
	public void setUpdateHour(String updateHour) {
		this.updateHour = updateHour;
	}

	/**
	 * 属性签单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SIGNDATE")
	public Date getSignDate() {
		return this.signDate;
	}

	/**
	 * 属性签单日期的setter方法
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * 属性是否股东业务标识的getter方法
	 */

	@Column(name = "SHAREHOLDERFLAG")
	public String getShareHolderFlag() {
		return this.shareHolderFlag;
	}

	/**
	 * 属性是否股东业务标识的setter方法
	 */
	public void setShareHolderFlag(String shareHolderFlag) {
		this.shareHolderFlag = shareHolderFlag;
	}

	/**
	 * 属性协议号的getter方法
	 */

	@Column(name = "AGREEMENTNO")
	public String getAgreementNo() {
		return this.agreementNo;
	}

	/**
	 * 属性协议号的setter方法
	 */
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	/**
	 * 属性询价单号的getter方法
	 */

	@Column(name = "INQUIRYNO")
	public String getInquiryNo() {
		return this.inquiryNo;
	}

	/**
	 * 属性询价单号的setter方法
	 */
	public void setInquiryNo(String inquiryNo) {
		this.inquiryNo = inquiryNo;
	}

	/**
	 * 属性缴费方式的getter方法
	 */

	@Column(name = "PAYMODE")
	public String getPayMode() {
		return this.payMode;
	}

	/**
	 * 属性缴费方式的setter方法
	 */
	public void setPayMode(String payMode) {
		this.payMode = payMode;
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
	 * 属性保单流水号的getter方法
	 */

	@Column(name = "VISACODE")
	public String getVisaCode() {
		return this.visaCode;
	}

	/**
	 * 属性保单流水号的setter方法
	 */
	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}

	/**
	 * 属性MANUALTYPE的getter方法
	 */

	@Column(name = "MANUALTYPE")
	public String getManualType() {
		return this.manualType;
	}

	/**
	 * 属性MANUALTYPE的setter方法
	 */
	public void setManualType(String manualType) {
		this.manualType = manualType;
	}

	/**
	 * 属性NATIONFLAG的getter方法
	 */

	@Column(name = "NATIONFLAG")
	public String getNationFlag() {
		return this.nationFlag;
	}

	/**
	 * 属性NATIONFLAG的setter方法
	 */
	public void setNationFlag(String nationFlag) {
		this.nationFlag = nationFlag;
	}

	/**
	 * 属性STARTMINUTE的getter方法
	 */

	@Column(name = "STARTMINUTE")
	public Byte getStartMinute() {
		return this.startMinute;
	}

	/**
	 * 属性STARTMINUTE的setter方法
	 */
	public void setStartMinute(Byte startMinute) {
		this.startMinute = startMinute;
	}

	/**
	 * 属性ENDMINUTE的getter方法
	 */

	@Column(name = "ENDMINUTE")
	public Byte getEndMinute() {
		return this.endMinute;
	}

	/**
	 * 属性ENDMINUTE的setter方法
	 */
	public void setEndMinute(Byte endMinute) {
		this.endMinute = endMinute;
	}

	/**
	 * 属性见费出单标志位的getter方法
	 */

	@Column(name = "JFEEFLAG")
	public String getJfeeFlag() {
		return this.jfeeFlag;
	}

	/**
	 * 属性见费出单标志位的setter方法
	 */
	public void setJfeeFlag(String jfeeFlag) {
		this.jfeeFlag = jfeeFlag;
	}

	/**
	 * 属性预审核时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRECHECKDATE")
	public Date getPreCheckDate() {
		return this.preCheckDate;
	}

	/**
	 * 属性预审核时间的setter方法
	 */
	public void setPreCheckDate(Date preCheckDate) {
		this.preCheckDate = preCheckDate;
	}

	/**
	 * 属性经办人姓名的getter方法
	 */

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return this.handlerName;
	}

	/**
	 * 属性经办人姓名的setter方法
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 属性归属业务员姓名的getter方法
	 */

	@Column(name = "HANDLER1NAME")
	public String getHandler1Name() {
		return this.handler1Name;
	}

	/**
	 * 属性归属业务员姓名的setter方法
	 */
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}

	/**
	 * 属性实收确认人代码的getter方法
	 */

	@Column(name = "PAYREFCODE")
	public String getPayRefCode() {
		return this.payRefCode;
	}

	/**
	 * 属性实收确认人代码的setter方法
	 */
	public void setPayRefCode(String payRefCode) {
		this.payRefCode = payRefCode;
	}

	/**
	 * 属性实收确认人姓名的getter方法
	 */

	@Column(name = "PAYREFNAME")
	public String getPayRefName() {
		return this.payRefName;
	}

	/**
	 * 属性实收确认人姓名的setter方法
	 */
	public void setPayRefName(String payRefName) {
		this.payRefName = payRefName;
	}

	/**
	 * 属性实收确认时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYREFTIME")
	public Date getPayRefTime() {
		return this.payRefTime;
	}

	/**
	 * 属性实收确认时间的setter方法
	 */
	public void setPayRefTime(Date payRefTime) {
		this.payRefTime = payRefTime;
	}

	/**
	 * 属性保单打印时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRINTTIME")
	public Date getPrintTime() {
		return this.printTime;
	}

	/**
	 * 属性保单打印时间的setter方法
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	/**
	 * 属性AGRITYPE的getter方法
	 */

	@Column(name = "AGRITYPE")
	public String getAgriType() {
		return this.agriType;
	}

	/**
	 * 属性AGRITYPE的setter方法
	 */
	public void setAgriType(String agriType) {
		this.agriType = agriType;
	}

	/**
	 * 属性SUBBUSINESSNATURE的getter方法
	 */

	@Column(name = "SUBBUSINESSNATURE")
	public String getSubBusinessNature() {
		return this.subBusinessNature;
	}

	/**
	 * 属性SUBBUSINESSNATURE的setter方法
	 */
	public void setSubBusinessNature(String subBusinessNature) {
		this.subBusinessNature = subBusinessNature;
	}

	/**
	 * 属性BANKCODE的getter方法
	 */

	@Column(name = "BANKCODE")
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * 属性BANKCODE的setter方法
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 属性CHANNELTYPE的getter方法
	 */

	@Column(name = "CHANNELTYPE")
	public String getChannelType() {
		return this.channelType;
	}

	/**
	 * 属性CHANNELTYPE的setter方法
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * 属性EXCHANGERATE的getter方法
	 */

	@Column(name = "EXCHANGERATE")
	public Double getExchangeRate() {
		return this.exchangeRate;
	}

	/**
	 * 属性EXCHANGERATE的setter方法
	 */
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 属性产品业务标识的getter方法
	 */

	@Column(name = "PROJECTSFLAG")
	public String getProjectsFlag() {
		return this.projectsFlag;
	}

	/**
	 * 属性产品业务标识的setter方法
	 */
	public void setProjectsFlag(String projectsFlag) {
		this.projectsFlag = projectsFlag;
	}

	/**
	 * 属性投保单核保通过级别的getter方法
	 */

	@Column(name = "PROPOSALLEVEL")
	public String getProposalLevel() {
		return this.proposalLevel;
	}

	/**
	 * 属性投保单核保通过级别的setter方法
	 */
	public void setProposalLevel(String proposalLevel) {
		this.proposalLevel = proposalLevel;
	}

	/**
	 * 属性停驶次数的getter方法
	 */

	@Column(name = "STOPTIMES")
	public String getStopTimes() {
		return this.stopTimes;
	}

	/**
	 * 属性停驶次数的setter方法
	 */
	public void setStopTimes(String stopTimes) {
		this.stopTimes = stopTimes;
	}

	/**
	 * 属性EFFECTIVEIMMEDIATELYFLAG的getter方法
	 */

	@Column(name = "EFFECTIVEIMMEDIATELYFLAG")
	public String getEffectiveImmediatelyFlag() {
		return this.effectiveImmediatelyFlag;
	}

	/**
	 * 属性EFFECTIVEIMMEDIATELYFLAG的setter方法
	 */
	public void setEffectiveImmediatelyFlag(String effectiveImmediatelyFlag) {
		this.effectiveImmediatelyFlag = effectiveImmediatelyFlag;
	}

	/**
	 * 属性NEWSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NEWSTARTDATE")
	public Date getNewStartDate() {
		return this.newStartDate;
	}

	/**
	 * 属性NEWSTARTDATE的setter方法
	 */
	public void setNewStartDate(Date newStartDate) {
		this.newStartDate = newStartDate;
	}

	/**
	 * 属性NEWENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NEWENDDATE")
	public Date getNewEndDate() {
		return this.newEndDate;
	}

	/**
	 * 属性NEWENDDATE的setter方法
	 */
	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}

	/**
	 * 属性团队类型的getter方法
	 */

	@Column(name = "GROUPTYPE")
	public String getGroupType() {
		return this.groupType;
	}

	/**
	 * 属性团队类型的setter方法
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * 属性批量代收代付起始缴费期次的getter方法
	 */

	@Column(name = "STARTSTAGES")
	public Integer getStartStages() {
		return this.startStages;
	}

	/**
	 * 属性批量代收代付起始缴费期次的setter方法
	 */
	public void setStartStages(Integer startStages) {
		this.startStages = startStages;
	}

	/**
	 * 属性锁定人代码的getter方法
	 */

	@Column(name = "LOCKERCODE")
	public String getLockerCode() {
		return this.lockerCode;
	}

	/**
	 * 属性锁定人代码的setter方法
	 */
	public void setLockerCode(String lockerCode) {
		this.lockerCode = lockerCode;
	}

	/**
	 * 属性保单加锁标记的getter方法
	 */

	@Column(name = "EDITFLAG")
	public String getEditFlag() {
		return this.editFlag;
	}

	/**
	 * 属性保单加锁标记的setter方法
	 */
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

	/**
	 * 属性未续保登记原因的getter方法
	 */

	@Column(name = "RSNNORENEWAL")
	public String getRsnNoRenewal() {
		return this.rsnNoRenewal;
	}

	/**
	 * 属性未续保登记原因的setter方法
	 */
	public void setRsnNoRenewal(String rsnNoRenewal) {
		this.rsnNoRenewal = rsnNoRenewal;
	}

	/**
	 * 属性未续保登记原因代码的getter方法
	 */

	@Column(name = "NOTRENEWALREGIST")
	public String getNotRenewalRegist() {
		return this.notRenewalRegist;
	}

	/**
	 * 属性未续保登记原因代码的setter方法
	 */
	public void setNotRenewalRegist(String notRenewalRegist) {
		this.notRenewalRegist = notRenewalRegist;
	}

	/**
	 * 属性DECLAREFLAG的getter方法
	 */

	@Column(name = "DECLAREFLAG")
	public String getDeclareFlag() {
		return this.declareFlag;
	}

	/**
	 * 属性DECLAREFLAG的setter方法
	 */
	public void setDeclareFlag(String declareFlag) {
		this.declareFlag = declareFlag;
	}

	/**
	 * 属性CONTRIBUTIONLEVEL的getter方法
	 */

	@Column(name = "CONTRIBUTIONLEVEL")
	public String getContributionLevel() {
		return this.contributionLevel;
	}

	/**
	 * 属性CONTRIBUTIONLEVEL的setter方法
	 */
	public void setContributionLevel(String contributionLevel) {
		this.contributionLevel = contributionLevel;
	}

	/**
	 * @return 返回 frameNo。
	 */
	@Transient
	public String getFrameNo() {
		return frameNo;
	}

	/**
	 * @param frameNo 要设置的 frameNo。
	 */
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	/**
	 * @return 返回 vINNo。
	 */
	@Transient
	public String getVINNo() {
		return vINNo;
	}

	/**
	 * @param no 要设置的 vINNo。
	 */
	public void setVINNo(String no) {
		vINNo = no;
	}

	/**
	 * @return 返回 modelCode。
	 */
	@Transient
	public String getModelCode() {
		return modelCode;
	}

	/**
	 * @param modelCode 要设置的 modelCode。
	 */
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	/**
	 * 设置属性车牌号码
	 * @param licenseNo 待设置的属性车牌号码的值
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = StringUtils.rightTrim(licenseNo);
	}

	/**
	 * 获取属性车牌号码
	 * @return 属性车牌号码的值
	 */
	@Transient
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 获取出险时间
	 * @return 属性车牌号码的值
	 */
	@Transient
	public String getDamageDate() {
		return damageDate;
	}

	/**
	 * 设置出险时间
	 * @param damageDate
	 */
	public void setDamageDate(String damageDate) {
		this.damageDate = StringUtils.rightTrim(damageDate);
	}

	/**
	 * 获取出险时间
	 * @return 属性车牌号码的值
	 */
	@Transient
	public String getDamageHour() {
		return damageHour;
	}

	/**
	 * 设置出险时间
	 * @param damageHour
	 */
	public void setDamageHour(String damageHour) {
		this.damageHour = StringUtils.rightTrim(damageHour);
	}

	/**
	 * 设置属性厂牌型号
	 * @param brandName 待设置的属性厂牌型号的值
	 */
	public void setBrandName(String brandName) {
		this.brandName = StringUtils.rightTrim(brandName);
	}

	public void setColorFlag(String colorFlag) {
		this.colorFlag = colorFlag;
	}

	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	/**
	 * 获取属性厂牌型号
	 * @return 属性厂牌型号的值
	 */
	@Transient
	public String getBrandName() {
		return brandName;
	}

	@Transient
	public String getColorFlag() {
		return colorFlag;
	}

	@Transient
	public String getPayFlag() {
		return payFlag;
	}

	/**
	 * @return 返回 carOwne。
	 */
	@Transient
	public String getCarOwne() {
		return carOwne;
	}

	/**
	 * @param carOwne 要设置的 carOwne。
	 */
	public void setCarOwne(String carOwne) {
		this.carOwne = carOwne;
	}

	/**
	 * @return 返回 licenseColorCode。
	 */
	@Transient
	public String getLicenseColorCode() {
		return licenseColorCode;
	}

	/**
	 * @param licenseColorCode 要设置的 licenseColorCode。
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	/**
	 * @return 返回 carKindCode。
	 */
	@Transient
	public String getCarKindCode() {
		return carKindCode;
	}

	/**
	 * @param carKindCode 要设置的 carKindCode。
	 */
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}

	/**
	 * @return 返回 relationPolicyNo。
	 */
	@Transient
	public String getRelationPolicyNo() {
		return relationPolicyNo;
	}

	/**
	 * @param relationPolicyNo 要设置的 relationPolicyNo。
	 */
	public void setRelationPolicyNo(String relationPolicyNo) {
		this.relationPolicyNo = relationPolicyNo;
	}

	/**
	 * @return 返回 engineNo。
	 */
	@Transient
	public String getEngineNo() {
		return engineNo;
	}

	/**
	 * @param engineNo 要设置的 engineNo。
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	@Transient
	public String getIcenseColorCode() {
		return icenseColorCode;
	}

	public void setIcenseColorCode(String icenseColorCode) {
		this.icenseColorCode = icenseColorCode;
	}

	@Transient
	public String getLicenseColorName() {
		return licenseColorName;
	}

	public void setLicenseColorName(String licenseColorName) {
		this.licenseColorName = licenseColorName;
	}

	@Transient
	public String getRelationRiskCode() {
		return relationRiskCode;
	}

	public void setRelationRiskCode(String relationRiskCode) {
		this.relationRiskCode = relationRiskCode;
	}

	/**
	 * @return the z2_curJplanFee
	 */
	@Transient
	public String getZ2_curJplanFee() {
		return z2_curJplanFee;
	}

	/**
	 * @param jplanFee the z2_curJplanFee to set
	 */
	public void setZ2_curJplanFee(String jplanFee) {
		z2_curJplanFee = jplanFee;
	}

	/**
	 * @return the z2_handlerCode
	 */
	@Transient
	public String getZ2_handlerCode() {
		return z2_handlerCode;
	}

	/**
	 * @param code the z2_handlerCode to set
	 */
	public void setZ2_handlerCode(String code) {
		z2_handlerCode = code;
	}

	/**
	 * @return the z2_handlerName
	 */
	@Transient
	public String getZ2_handlerName() {
		return z2_handlerName;
	}

	/**
	 * @param name the z2_handlerName to set
	 */
	public void setZ2_handlerName(String name) {
		z2_handlerName = name;
	}

	/**
	 * @return the z2_regJplanFee
	 */
	@Transient
	public String getZ2_regJplanFee() {
		return z2_regJplanFee;
	}

	/**
	 * @param jplanFee the z2_regJplanFee to set
	 */
	public void setZ2_regJplanFee(String jplanFee) {
		z2_regJplanFee = jplanFee;
	}

}