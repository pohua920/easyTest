package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * POJO类PrpPmain 批改保单信息表
 */
@Entity
@Table(name = "PRPPMAIN")
public class PrpPmain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性批单号码 */
	private String endorseNo;

	/** 属性批改信息表 */
	private PrpPhead prpPhead;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险类代码 */
	private String classCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性投保单号 */
	private String proposalNo;

	/** 属性合同号 */
	private String contractNo;

	/** 属性保单种类 */
	private String policySort;

	/** 属性批单印刷号 */
	private String printNo;

	/** 属性业务来源 */
	private String businessNature;

	/** 属性中/英文 */
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

	/** 属性被保险人姓名 */
	private String insuredName;

	/** 属性被保险人地址 */
	private String insuredAddress;

	/** 属性签单日期 */
	private Date operateDate;

	/** 属性起保日期 */
	private Date startDate;

	/** 属性起保小时 */
	private Integer startHour;

	/** 属性终保日期 */
	private Date endDate;

	/** 属性终保小时 */
	private Integer endHour;

	/** 属性净费率 */
	private Double pureRate;

	/** 属性手续费率/浮动费率 */
	private Double disRate;

	/** 属性折扣率 */
	private Double discount;

	/** 属性币别 */
	private String currency;

	/** 属性总保险价值 */
	private Double sumValue;

	/** 属性总保额 */
	private Double sumAmount;

	/** 属性总折扣金额 */
	private Double sumDiscount;

	/** 属性总保险费 */
	private Double sumPremium;

	/** 属性总附加险保费 */
	private Double sumSubPrem;

	/** 属性压力容器总数 */
	private Integer sumQuantity;

	/** 属性JUDICALCODE */
	private String judicalCode;

	/** 属性司法管辖 */
	private String judicalScope;

	/** 属性是否自动转帳续保标志 */
	private String autoTransRenewFlag;

	/** 属性争议解决方式 */
	private String argueSolution;

	/** 属性仲裁委员会名称 */
	private String arbitBoardName;

	/** 属性约定分期缴费次数 */
	private Integer payTimes;

	/** 属性保单批改次数 */
	private Integer endorseTimes;

	/** 属性理赔次数 */
	private Integer claimTimes;

	/** 属性出单机构代码 */
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

	/** 属性最终核批人代码 */
	private String underWriteCode;

	/** 属性最终核批人名称 */
	private String underWriteName;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性计算机输单小时 */
	private Integer inputHour;

	/** 属性核批完成日期 */
	private Date underWriteEndDate;

	/** 属性批单统计年月 */
	private Date statisticsYM;

	/** 属性代理人代码 */
	private String agentCode;

	/** 属性共保标志 */
	private String coinsFlag;

	/** 属性商业分保标志 */
	private String reinsFlag;

	/** 属性统保标志 */
	private String allinsFlag;

	/** 属性核批标志 */
	private String underWriteFlag;

	/** 属性其它标志字段 */
	private String othFlag;

	/** 属性标志字段 */
	private String flag;

	/** 属性保额变化量 */
	private Double chgAmount;

	/** 属性保费变化量 */
	private Double chgPremium;

	/** 属性变化附加险保费（折算为人民币） */
	private Double chgSubPrem;

	/** 属性数量变化量 */
	private Integer chgQuantity;

	/** 属性超出部分手续费比例 */
	private Double disRate1;

	/** 属性业务类型:0：自营业务，1：分入业务 */
	private String businessFlag;

	/** 属性信息修改人代码 */
	private String updaterCode;

	/** 属性最後一次修改的日期 */
	private Date updateDate;

	/** 属性最後一次修改的小时 */
	private String updateHour;

	/** 属性签单日 */
	private Date signDate;

	/** 属性是否股东业务标识 */
	private String shareHolderFlag;

	/** 属性协议号 */
	private String agreementNo;

	/** 属性询价单号 */
	private String inquiryNo;

	/** 属性缴费方式 */
	private String paymode;

	/** 属性备注 */
	private String remark;

	/** 属性单证代码 */
	private String visaCode;

	/** 属性MANUALTYPE */
	private String manualType;

	/** 属性NATIONFLAG */
	private String nationFlag;

	/** 属性开始分钟 */
	private Byte startMinute;

	/** 属性结束分钟 */
	private Byte endMinute;

	/** 属性见费出单标志位 */
	private String jfeeFlag;

	/** 属性预审核时间 */
	private Date precheckDate;

	/** 属性经办人姓名 */
	private String handlerName;

	/** 属性归属业务员姓名 */
	private String handler1Name;

	/** 属性实收确认人代码 */
	private String payrefCode;

	/** 属性实收确认人姓名 */
	private String payrefName;

	/** 属性实收确认时间 */
	private Date payrefTime;

	/** 属性保单打印时间 */
	private Date printTime;

	/** 属性涉农标志 */
	private String agriType;

	/** 属性SUBBUSINESSNATURE */
	private String subBusinessNature;

	/** 属性银行查询 */
	private String bankCode;

	/** 属性销售渠道 */
	private String channelType;

	/** 属性签单币别与人民币的兑换率 */
	private Double exchangeRate;

	/** 属性大项目标识 */
	private String projectsFlag;

	/** 属性投保单核保通过级别 */
	private String proposalLevel;

	/** 属性停驶次数 */
	private String stopTimes;

	/** 属性交强险即时生效 开始日期 */
	private Date newStartDate;

	/** 属性交强险即时生效 结束日期 */
	private Date newEndDate;

	/** 属性effectiveimmediatelyflag */
	private String effectiveimmediatelyflag;

	/** 属性团队类型 */
	private String groupType;

	/** 属性STARTSTAGES */
	private Integer startstAges;

	/** 属性CONTRIBUTIONLEVEL */
	private String contributionLevel;

	/** 属性DECLAREFLAG */
	private String declareFlag;

	/**
	 * 类PrpPmain的默认构造方法
	 */
	public PrpPmain() {
	}

	/**
	 * 属性批单号码的getter方法
	 */
	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "prpPhead"))
	@Id
	@GeneratedValue(generator = "generator")
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
	 * 属性批改信息表的getter方法
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public PrpPhead getPrpPhead() {
		return this.prpPhead;
	}

	/**
	 * 属性批改信息表的setter方法
	 */
	public void setPrpPhead(PrpPhead prpPhead) {
		this.prpPhead = prpPhead;
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
	 * 属性合同号的getter方法
	 */

	@Column(name = "CONTRACTNO")
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * 属性合同号的setter方法
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
	 * 属性批单印刷号的getter方法
	 */

	@Column(name = "PRINTNO")
	public String getPrintNo() {
		return this.printNo;
	}

	/**
	 * 属性批单印刷号的setter方法
	 */
	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}

	/**
	 * 属性业务来源的getter方法
	 */

	@Column(name = "BUSINESSNATURE")
	public String getBusinessNature() {
		return this.businessNature;
	}

	/**
	 * 属性业务来源的setter方法
	 */
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	/**
	 * 属性中/英文的getter方法
	 */

	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return this.language;
	}

	/**
	 * 属性中/英文的setter方法
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
	 * 属性被保险人姓名的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人姓名的setter方法
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
	 * 属性签单日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATEDATE")
	public Date getOperateDate() {
		return this.operateDate;
	}

	/**
	 * 属性签单日期的setter方法
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性起保日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性起保日期的setter方法
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
	 * 属性手续费率/浮动费率的getter方法
	 */

	@Column(name = "DISRATE")
	public Double getDisRate() {
		return this.disRate;
	}

	/**
	 * 属性手续费率/浮动费率的setter方法
	 */
	public void setDisRate(Double disRate) {
		this.disRate = disRate;
	}

	/**
	 * 属性折扣率的getter方法
	 */

	@Column(name = "DISCOUNT")
	public Double getDiscount() {
		return this.discount;
	}

	/**
	 * 属性折扣率的setter方法
	 */
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	/**
	 * 属性币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性币别的setter方法
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
	 * 属性总保额的getter方法
	 */

	@Column(name = "SUMAMOUNT")
	public Double getSumAmount() {
		return this.sumAmount;
	}

	/**
	 * 属性总保额的setter方法
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
	 * 属性总保险费的getter方法
	 */

	@Column(name = "SUMPREMIUM")
	public Double getSumPremium() {
		return this.sumPremium;
	}

	/**
	 * 属性总保险费的setter方法
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
	 * 属性压力容器总数的getter方法
	 */

	@Column(name = "SUMQUANTITY")
	public Integer getSumQuantity() {
		return this.sumQuantity;
	}

	/**
	 * 属性压力容器总数的setter方法
	 */
	public void setSumQuantity(Integer sumQuantity) {
		this.sumQuantity = sumQuantity;
	}

	/**
	 * 属性JUDICALCODE的getter方法
	 */

	@Column(name = "JUDICALCODE")
	public String getJudicalCode() {
		return this.judicalCode;
	}

	/**
	 * 属性JUDICALCODE的setter方法
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
	 * 属性是否自动转帳续保标志的getter方法
	 */

	@Column(name = "AUTOTRANSRENEWFLAG")
	public String getAutoTransRenewFlag() {
		return this.autoTransRenewFlag;
	}

	/**
	 * 属性是否自动转帳续保标志的setter方法
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
	 * 属性约定分期缴费次数的getter方法
	 */

	@Column(name = "PAYTIMES")
	public Integer getPayTimes() {
		return this.payTimes;
	}

	/**
	 * 属性约定分期缴费次数的setter方法
	 */
	public void setPayTimes(Integer payTimes) {
		this.payTimes = payTimes;
	}

	/**
	 * 属性保单批改次数的getter方法
	 */

	@Column(name = "ENDORSETIMES")
	public Integer getEndorseTimes() {
		return this.endorseTimes;
	}

	/**
	 * 属性保单批改次数的setter方法
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
	 * 属性出单机构代码的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性出单机构代码的setter方法
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
	 * 属性最终核批人代码的getter方法
	 */

	@Column(name = "UNDERWRITECODE")
	public String getUnderWriteCode() {
		return this.underWriteCode;
	}

	/**
	 * 属性最终核批人代码的setter方法
	 */
	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
	}

	/**
	 * 属性最终核批人名称的getter方法
	 */

	@Column(name = "UNDERWRITENAME")
	public String getUnderWriteName() {
		return this.underWriteName;
	}

	/**
	 * 属性最终核批人名称的setter方法
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
	 * 属性核批完成日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDERWRITEENDDATE")
	public Date getUnderWriteEndDate() {
		return this.underWriteEndDate;
	}

	/**
	 * 属性核批完成日期的setter方法
	 */
	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	/**
	 * 属性批单统计年月的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STATISTICSYM")
	public Date getStatisticsYM() {
		return this.statisticsYM;
	}

	/**
	 * 属性批单统计年月的setter方法
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
	 * 属性共保标志的getter方法
	 */

	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return this.coinsFlag;
	}

	/**
	 * 属性共保标志的setter方法
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
	 * 属性核批标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return this.underWriteFlag;
	}

	/**
	 * 属性核批标志的setter方法
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
	 * 属性保额变化量的getter方法
	 */

	@Column(name = "CHGAMOUNT")
	public Double getChgAmount() {
		return this.chgAmount;
	}

	/**
	 * 属性保额变化量的setter方法
	 */
	public void setChgAmount(Double chgAmount) {
		this.chgAmount = chgAmount;
	}

	/**
	 * 属性保费变化量的getter方法
	 */

	@Column(name = "CHGPREMIUM")
	public Double getChgPremium() {
		return this.chgPremium;
	}

	/**
	 * 属性保费变化量的setter方法
	 */
	public void setChgPremium(Double chgPremium) {
		this.chgPremium = chgPremium;
	}

	/**
	 * 属性变化附加险保费（折算为人民币）的getter方法
	 */

	@Column(name = "CHGSUBPREM")
	public Double getChgSubPrem() {
		return this.chgSubPrem;
	}

	/**
	 * 属性变化附加险保费（折算为人民币）的setter方法
	 */
	public void setChgSubPrem(Double chgSubPrem) {
		this.chgSubPrem = chgSubPrem;
	}

	/**
	 * 属性数量变化量的getter方法
	 */

	@Column(name = "CHGQUANTITY")
	public Integer getChgQuantity() {
		return this.chgQuantity;
	}

	/**
	 * 属性数量变化量的setter方法
	 */
	public void setChgQuantity(Integer chgQuantity) {
		this.chgQuantity = chgQuantity;
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
	 * 属性业务类型:0：自营业务，1：分入业务的getter方法
	 */

	@Column(name = "BUSINESSFLAG")
	public String getBusinessFlag() {
		return this.businessFlag;
	}

	/**
	 * 属性业务类型:0：自营业务，1：分入业务的setter方法
	 */
	public void setBusinessFlag(String businessFlag) {
		this.businessFlag = businessFlag;
	}

	/**
	 * 属性信息修改人代码的getter方法
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdaterCode() {
		return this.updaterCode;
	}

	/**
	 * 属性信息修改人代码的setter方法
	 */
	public void setUpdaterCode(String updaterCode) {
		this.updaterCode = updaterCode;
	}

	/**
	 * 属性最後一次修改的日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATEDATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 属性最後一次修改的日期的setter方法
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 属性最後一次修改的小时的getter方法
	 */

	@Column(name = "UPDATEHOUR")
	public String getUpdateHour() {
		return this.updateHour;
	}

	/**
	 * 属性最後一次修改的小时的setter方法
	 */
	public void setUpdateHour(String updateHour) {
		this.updateHour = updateHour;
	}

	/**
	 * 属性签单日的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SIGNDATE")
	public Date getSignDate() {
		return this.signDate;
	}

	/**
	 * 属性签单日的setter方法
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
	public String getPaymode() {
		return this.paymode;
	}

	/**
	 * 属性缴费方式的setter方法
	 */
	public void setPaymode(String paymode) {
		this.paymode = paymode;
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
	 * 属性单证代码的getter方法
	 */

	@Column(name = "VISACODE")
	public String getVisaCode() {
		return this.visaCode;
	}

	/**
	 * 属性单证代码的setter方法
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
	 * 属性开始分钟的getter方法
	 */

	@Column(name = "STARTMINUTE")
	public Byte getStartMinute() {
		return this.startMinute;
	}

	/**
	 * 属性开始分钟的setter方法
	 */
	public void setStartMinute(Byte startMinute) {
		this.startMinute = startMinute;
	}

	/**
	 * 属性结束分钟的getter方法
	 */

	@Column(name = "ENDMINUTE")
	public Byte getEndMinute() {
		return this.endMinute;
	}

	/**
	 * 属性结束分钟的setter方法
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
	public Date getPrecheckDate() {
		return this.precheckDate;
	}

	/**
	 * 属性预审核时间的setter方法
	 */
	public void setPrecheckDate(Date precheckDate) {
		this.precheckDate = precheckDate;
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
	public String getPayrefCode() {
		return this.payrefCode;
	}

	/**
	 * 属性实收确认人代码的setter方法
	 */
	public void setPayrefCode(String payrefCode) {
		this.payrefCode = payrefCode;
	}

	/**
	 * 属性实收确认人姓名的getter方法
	 */

	@Column(name = "PAYREFNAME")
	public String getPayrefName() {
		return this.payrefName;
	}

	/**
	 * 属性实收确认人姓名的setter方法
	 */
	public void setPayrefName(String payrefName) {
		this.payrefName = payrefName;
	}

	/**
	 * 属性实收确认时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYREFTIME")
	public Date getPayrefTime() {
		return this.payrefTime;
	}

	/**
	 * 属性实收确认时间的setter方法
	 */
	public void setPayrefTime(Date payrefTime) {
		this.payrefTime = payrefTime;
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
	 * 属性涉农标志的getter方法
	 */

	@Column(name = "AGRITYPE")
	public String getAgriType() {
		return this.agriType;
	}

	/**
	 * 属性涉农标志的setter方法
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
	 * 属性银行查询的getter方法
	 */

	@Column(name = "BANKCODE")
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * 属性银行查询的setter方法
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 属性销售渠道的getter方法
	 */

	@Column(name = "CHANNELTYPE")
	public String getChannelType() {
		return this.channelType;
	}

	/**
	 * 属性销售渠道的setter方法
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * 属性签单币别与人民币的兑换率的getter方法
	 */

	@Column(name = "EXCHANGERATE")
	public Double getExchangeRate() {
		return this.exchangeRate;
	}

	/**
	 * 属性签单币别与人民币的兑换率的setter方法
	 */
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 属性大项目标识的getter方法
	 */

	@Column(name = "PROJECTSFLAG")
	public String getProjectsFlag() {
		return this.projectsFlag;
	}

	/**
	 * 属性大项目标识的setter方法
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
	 * 属性交强险即时生效 开始日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NEWSTARTDATE")
	public Date getNewStartDate() {
		return this.newStartDate;
	}

	/**
	 * 属性交强险即时生效 开始日期的setter方法
	 */
	public void setNewStartDate(Date newStartDate) {
		this.newStartDate = newStartDate;
	}

	/**
	 * 属性交强险即时生效 结束日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "NEWENDDATE")
	public Date getNewEndDate() {
		return this.newEndDate;
	}

	/**
	 * 属性交强险即时生效 结束日期的setter方法
	 */
	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}

	/**
	 * 属性effectiveimmediatelyflag的getter方法
	 */

	@Column(name = "EFFECTIVEIMMEDIATELYFLAG")
	public String getEffectiveimmediatelyflag() {
		return this.effectiveimmediatelyflag;
	}

	/**
	 * 属性effectiveimmediatelyflag的setter方法
	 */
	public void setEffectiveimmediatelyflag(String effectiveimmediatelyflag) {
		this.effectiveimmediatelyflag = effectiveimmediatelyflag;
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
	 * 属性STARTSTAGES的getter方法
	 */

	@Column(name = "STARTSTAGES")
	public Integer getStartstAges() {
		return this.startstAges;
	}

	/**
	 * 属性STARTSTAGES的setter方法
	 */
	public void setStartstAges(Integer startstAges) {
		this.startstAges = startstAges;
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

}
