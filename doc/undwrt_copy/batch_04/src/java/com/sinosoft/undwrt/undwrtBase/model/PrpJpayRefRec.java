package com.sinosoft.undwrt.undwrtBase.model;

// default package
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
import javax.persistence.Transient;

/**
 * POJO类PrpJpayRefRec
 */
@Entity(name = "PRPJPAYREFREC_UNDWRT")
@Table(name = "PRPJPAYREFREC")
public class PrpJpayRefRec implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpJpayRefRecId id;

	/** 属性属性保单号码 */
	private String policyNo;

	/** 属性属性立案号 */
	private String claimNo;

	/** 属性属性险类 */
	private String classCode;

	/** 属性属性险种 */
	private String riskCode;

	/** 属性属性合同号 */
	private String contractNo;

	/** 属性属性投保人 */
	private String appliCode;

	/** 属性属性投保人名称 */
	private String appliName;

	/** 属性属性被保险人 */
	private String insuredCode;

	/** 属性属性被保人名称 */
	private String insuredName;

	/** 属性属性起保日期 */
	private Date startDate;

	/** 属性属性终保日期 */
	private Date endDate;

	/** 属性属性批单生效日期 */
	private Date validDate;

	/** 属性属性缴费期数 */
	private Integer payNo;

	/** 属性属性应收币种 */
	private String currency1;

	/** 属性属性应收应付金额 */
	private BigDecimal planFee;

	/** 属性属性分期缴费日期 */
	private Date planDate;

	/** 属性属性归属机构 */
	private String comCode;

	/** 属性属性出单机构 */
	private String makeCom;

	/** 属性属性代理人代码 */
	private String agentCode;

	/** 属性属性业务归属人员 */
	private String handler1Code;

	/** 属性属性经办人 */
	private String handlerCode;

	/** 属性属性核保/赔日期 */
	private Date underWriteDate;

	/** 属性属性联共保标志 */
	private String coinsFlag;

	/** 属性属性联共保人代码 */
	private String coinsCode;

	/** 属性属性联共保人名称 */
	private String coinsName;

	/** 属性属性联共保类型 */
	private String coinsType;

	/** 属性属性实际操作日期 */
	private Date operateDate;

	/** 属性属性签发人 */
	private String operatorCode;

	/** 属性属性签发部门 */
	private String operateUnit;

	/** 属性属性收付币种 */
	private String currency2;

	/** 属性属性收付汇率 */
	private BigDecimal exchangeRate;

	/** 属性属性收付金额 */
	private BigDecimal payRefFee;

	/** 属性属性单证类型 */
	private String visaCode;

	/** 属性属性单证名称 */
	private String visaName;

	/** 属性属性发票号 */
	private String visaSerialNo;

	/** 属性性发票打印日期 */
	private Date printDate;

	/** 属性属性发票打印人 */
	private String printerCode;

	/** 属性属性发票经手人 */
	private String visaHandler;

	/** 属性属性交/领款人 */
	private String payRefName;

	/** 属性属性交/领款人证件类型 */
	private String identifyType;

	/** 属性属性交/领款人证件号码 */
	private String identifyNumber;

	/** 属性属性发票/支付单备注 */
	private String remark;

	/** 属性属性打包号/支付单号 */
	private String payRefNo;

	/** 属性属性收付日期 */
	private Date payRefDate;

	/** 属性标志 */
	private String flag;

	/** 属性businessnature */
	private String businessNature;

	/** 属性属性业务标志 */
	private String othFlag;

	/** 属性属性个人代理手续费代扣税金 */
	private BigDecimal taxFee;

	/** 属性属性境内境外标志 */
	private String locationFlag;

	/** 属性属性预收挂账标志 */
	private String accFlag;

	/** 属性属性业务员名称 */
	private String handler1Name;

	/** 属性属性代理人名称 */
	private String agentName;

	/** 属性属性审核通过标志 */
	private String underWriteFlag;

	/** 属性属性见费出单标志 */
	private String jFeeFlag;

	/** 属性属性收付方式 */
	private String payWay;

	/** 属性属性ENDORTYPE */
	private String endorType;

	/** 属性属性TOTALPAYNO */
	private Long totalPayNo;

	/** 属性属性INPUTDATE */
	private Date inputDate;

	/** 属性属性CENTERCODE */
	private String centerCode;

	/** 属性属性BRANCHCODE */
	private String branchCode;

	/** 属性属性ACCBOOKTYPE */
	private String accBookType;

	/** 属性属性ACCBOOKCODE */
	private String accBookCode;

	/** 属性属性YEARMONTH */
	private String yearMonth;

	/** 属性属性EXCHANGERATE1 */
	private BigDecimal exchangeRate1;

	/** 属性属性PLANFEECNY */
	private BigDecimal planFeeCNY;

	/** 属性属性CARMODEL */
	private String carModel;

	/** 属性属性CHANNELTYPE */
	private String channelType;

	/** 属性属性ISCOMBIN */
	private String isCombin;

	/** 属性属性CASETYPE */
	private String caseType;

	/** 属性属性INDEMNITYDUTY */
	private String indemnityDuty;

	/** 属性属性ONACCDATE */
	private Date onAccDate;

	/** 属性属性ONACCFLAG */
	private String onAccFlag;

	/** 属性bankserailno */
	private String bankSerailNo;

	/** 属性属性BANKPOSNO */
	private String bankPosNo;

	/** 属性属性REALPAYREFFEECNY */
	private BigDecimal realPayRefFeeCNY;

	/** 属性属性EXCHANGERATECNY */
	private BigDecimal exchangeRateCNY;

	/** 属性属性TAXFLAG */
	private String taxFlag;

	/** 属性属性PREMIUMREFFLAG */
	private String premiumRefFlag;

	/** 属性属性REALPAYREFFLAG */
	private String realPayRefFlag;

	/** 属性属性REVERSEFLAG */
	private String reverseFlag;

	/** 属性属性ARTICLEENDORFLAG */
	private String articleEndorFlag;

	/** 属性属性AGRITYPE */
	private String agriType;

	/** 属性属性WRITEOFFDATE */
	private Date writeOffDate;

	/** 属性属性PREMIUMREFDATE */
	private Date premiumRefDate;

	/** 属性属性PACKAGEFLAG */
	private String packageFlag;

	/** 属性属性WRITEOFFFLAG */
	private String writeOffFlag;

	/** 属性属性SIGNDATE */
	private Date signDate;

	/** 属性属性ACCOUNTCODE */
	private String accountCode;

	/** 属性属性CUSTOMBANKCODE */
	private String customBankCode;

	/** 属性属性CUSTOMBANKNAME */
	private String customBankName;

	/** 属性属性CERTIFICATECODE */
	private String certificateCode;

	/** 属性属性OWNERNAME */
	private String ownerName;

	/** 属性属性ACCOUNTTYPE */
	private String accountType;

	/** 属性属性ACCOUNTCURRENCY */
	private String accountCurrency;

	/** 属性属性OWNERSHIP */
	private String ownerShip;

	/** 属性属性EXCEPTIONCODE */
	private String exceptionCode;

	/** 属性属性EXCEPTIONTEXT */
	private String exceptionText;

	/** 属性属性CHANNELCODE */
	private String channelCode;

	/** 属性属性虚拟账号*/
    private String virtualNo;

	/** 属性属性保单对应总金额 */
	private BigDecimal sumFee= new BigDecimal(0);

	/** 属性属性SUPERMARKETNAME */
	private String superMarketName;
	
	/** 屬性 是否是續保件  */
	private String isRenewlFlag;
	
    /**
	 * 类PrpJpayRefRec的默认构造方法
	 */
	public PrpJpayRefRec() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "certiType", column = @Column(name = "CERTITYPE")),
			@AttributeOverride(name = "certiNo", column = @Column(name = "CERTINO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "payRefReason", column = @Column(name = "PAYREFREASON")),
			@AttributeOverride(name = "payRefTimes", column = @Column(name = "PAYREFTIMES")) })
	public PrpJpayRefRecId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpJpayRefRecId id) {
		this.id = id;
	}

	/**
	 * 属性属性保单号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性属性保单号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性属性立案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性属性险类的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性属性险类的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 属性属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性属性合同号的getter方法
	 */

	@Column(name = "CONTRACTNO")
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * 属性属性合同号的setter方法
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 属性属性投保人的getter方法
	 */

	@Column(name = "APPLICODE")
	public String getAppliCode() {
		return this.appliCode;
	}

	/**
	 * 属性属性投保人的setter方法
	 */
	public void setAppliCode(String appliCode) {
		this.appliCode = appliCode;
	}

	/**
	 * 属性属性投保人名称的getter方法
	 */

	@Column(name = "APPLINAME")
	public String getAppliName() {
		return this.appliName;
	}

	/**
	 * 属性属性投保人名称的setter方法
	 */
	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	/**
	 * 属性属性被保险人的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性属性被保险人的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性属性被保人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性属性被保人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性属性起保日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性属性起保日期的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 属性属性终保日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性属性终保日期的setter方法
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 属性属性批单生效日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE")
	public Date getValidDate() {
		return this.validDate;
	}

	/**
	 * 属性属性批单生效日期的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * 属性属性缴费期数的getter方法
	 */

	@Column(name = "PAYNO")
	public Integer getPayNo() {
		return this.payNo;
	}

	/**
	 * 属性属性缴费期数的setter方法
	 */
	public void setPayNo(Integer payNo) {
		this.payNo = payNo;
	}

	/**
	 * 属性属性应收币种的getter方法
	 */

	@Column(name = "CURRENCY1")
	public String getCurrency1() {
		return this.currency1;
	}

	/**
	 * 属性属性应收币种的setter方法
	 */
	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	/**
	 * 属性属性应收应付金额的getter方法
	 */

	@Column(name = "PLANFEE")
	public BigDecimal getPlanFee() {
		return this.planFee;
	}

	/**
	 * 属性属性应收应付金额的setter方法
	 */
	public void setPlanFee(BigDecimal planFee) {
		this.planFee = planFee;
	}

	/**
	 * 属性属性分期缴费日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PLANDATE")
	public Date getPlanDate() {
		return this.planDate;
	}

	/**
	 * 属性属性分期缴费日期的setter方法
	 */
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	/**
	 * 属性属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性属性出单机构的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性属性出单机构的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**
	 * 属性属性代理人代码的getter方法
	 */

	@Column(name = "AGENTCODE")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**
	 * 属性属性代理人代码的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 属性属性业务归属人员的getter方法
	 */

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return this.handler1Code;
	}

	/**
	 * 属性属性业务归属人员的setter方法
	 */
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}

	/**
	 * 属性属性经办人的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性属性经办人的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性属性核保/赔日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDERWRITEDATE")
	public Date getUnderWriteDate() {
		return this.underWriteDate;
	}

	/**
	 * 属性属性核保/赔日期的setter方法
	 */
	public void setUnderWriteDate(Date underWriteDate) {
		this.underWriteDate = underWriteDate;
	}

	/**
	 * 属性属性联共保标志的getter方法
	 */

	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return this.coinsFlag;
	}

	/**
	 * 属性属性联共保标志的setter方法
	 */
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}

	/**
	 * 属性属性联共保人代码的getter方法
	 */

	@Column(name = "COINSCODE")
	public String getCoinsCode() {
		return this.coinsCode;
	}

	/**
	 * 属性属性联共保人代码的setter方法
	 */
	public void setCoinsCode(String coinsCode) {
		this.coinsCode = coinsCode;
	}

	/**
	 * 属性属性联共保人名称的getter方法
	 */

	@Column(name = "COINSNAME")
	public String getCoinsName() {
		return this.coinsName;
	}

	/**
	 * 属性属性联共保人名称的setter方法
	 */
	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}

	/**
	 * 属性属性联共保类型的getter方法
	 */

	@Column(name = "COINSTYPE")
	public String getCoinsType() {
		return this.coinsType;
	}

	/**
	 * 属性属性联共保类型的setter方法
	 */
	public void setCoinsType(String coinsType) {
		this.coinsType = coinsType;
	}

	/**
	 * 属性属性实际操作日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATEDATE")
	public Date getOperateDate() {
		return this.operateDate;
	}

	/**
	 * 属性属性实际操作日期的setter方法
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性属性签发人的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性属性签发人的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性属性签发部门的getter方法
	 */

	@Column(name = "OPERATEUNIT")
	public String getOperateUnit() {
		return this.operateUnit;
	}

	/**
	 * 属性属性签发部门的setter方法
	 */
	public void setOperateUnit(String operateUnit) {
		this.operateUnit = operateUnit;
	}

	/**
	 * 属性属性收付币种的getter方法
	 */

	@Column(name = "CURRENCY2")
	public String getCurrency2() {
		return this.currency2;
	}

	/**
	 * 属性属性收付币种的setter方法
	 */
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}

	/**
	 * 属性属性收付汇率的getter方法
	 */

	@Column(name = "EXCHANGERATE")
	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	/**
	 * 属性属性收付汇率的setter方法
	 */
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 属性属性收付金额的getter方法
	 */

	@Column(name = "PAYREFFEE")
	public BigDecimal getPayRefFee() {
		return this.payRefFee;
	}

	/**
	 * 属性属性收付金额的setter方法
	 */
	public void setPayRefFee(BigDecimal payRefFee) {
		this.payRefFee = payRefFee;
	}

	/**
	 * 属性属性单证类型的getter方法
	 */

	@Column(name = "VISACODE")
	public String getVisaCode() {
		return this.visaCode;
	}

	/**
	 * 属性属性单证类型的setter方法
	 */
	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}

	/**
	 * 属性属性单证名称的getter方法
	 */

	@Column(name = "VISANAME")
	public String getVisaName() {
		return this.visaName;
	}

	/**
	 * 属性属性单证名称的setter方法
	 */
	public void setVisaName(String visaName) {
		this.visaName = visaName;
	}

	/**
	 * 属性属性发票号的getter方法
	 */

	@Column(name = "VISASERIALNO")
	public String getVisaSerialNo() {
		return this.visaSerialNo;
	}

	/**
	 * 属性属性发票号的setter方法
	 */
	public void setVisaSerialNo(String visaSerialNo) {
		this.visaSerialNo = visaSerialNo;
	}

	/**
	 * 属性性发票打印日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRINTDATE")
	public Date getPrintDate() {
		return this.printDate;
	}

	/**
	 * 属性性发票打印日期的setter方法
	 */
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	/**
	 * 属性属性发票打印人的getter方法
	 */

	@Column(name = "PRINTERCODE")
	public String getPrinterCode() {
		return this.printerCode;
	}

	/**
	 * 属性属性发票打印人的setter方法
	 */
	public void setPrinterCode(String printerCode) {
		this.printerCode = printerCode;
	}

	/**
	 * 属性属性发票经手人的getter方法
	 */

	@Column(name = "VISAHANDLER")
	public String getVisaHandler() {
		return this.visaHandler;
	}

	/**
	 * 属性属性发票经手人的setter方法
	 */
	public void setVisaHandler(String visaHandler) {
		this.visaHandler = visaHandler;
	}

	/**
	 * 属性属性交/领款人的getter方法
	 */

	@Column(name = "PAYREFNAME")
	public String getPayRefName() {
		return this.payRefName;
	}

	/**
	 * 属性属性交/领款人的setter方法
	 */
	public void setPayRefName(String payRefName) {
		this.payRefName = payRefName;
	}

	/**
	 * 属性属性交/领款人证件类型的getter方法
	 */

	@Column(name = "IDENTIFYTYPE")
	public String getIdentifyType() {
		return this.identifyType;
	}

	/**
	 * 属性属性交/领款人证件类型的setter方法
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * 属性属性交/领款人证件号码的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性属性交/领款人证件号码的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性属性发票/支付单备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性属性发票/支付单备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性属性打包号/支付单号的getter方法
	 */

	@Column(name = "PAYREFNO")
	public String getPayRefNo() {
		return this.payRefNo;
	}

	/**
	 * 属性属性打包号/支付单号的setter方法
	 */
	public void setPayRefNo(String payRefNo) {
		this.payRefNo = payRefNo;
	}

	/**
	 * 属性属性收付日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYREFDATE")
	public Date getPayRefDate() {
		return this.payRefDate;
	}

	/**
	 * 属性属性收付日期的setter方法
	 */
	public void setPayRefDate(Date payRefDate) {
		this.payRefDate = payRefDate;
	}

	/**
	 * 属性标志的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性标志的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性businessnature的getter方法
	 */

	@Column(name = "BUSINESSNATURE")
	public String getBusinessNature() {
		return this.businessNature;
	}

	/**
	 * 属性businessnature的setter方法
	 */
	public void setBusinessNature(String businessnature) {
		this.businessNature = businessnature;
	}

	/**
	 * 属性属性业务标志的getter方法
	 */

	@Column(name = "OTHFLAG")
	public String getOthFlag() {
		return this.othFlag;
	}

	/**
	 * 属性属性业务标志的setter方法
	 */
	public void setOthFlag(String othFlag) {
		this.othFlag = othFlag;
	}

	/**
	 * 属性属性个人代理手续费代扣税金的getter方法
	 */

	@Column(name = "TAXFEE")
	public BigDecimal getTaxFee() {
		return this.taxFee;
	}

	/**
	 * 属性属性个人代理手续费代扣税金的setter方法
	 */
	public void setTaxFee(BigDecimal taxFee) {
		this.taxFee = taxFee;
	}

	/**
	 * 属性属性境内境外标志的getter方法
	 */

	@Column(name = "LOCATIONFLAG")
	public String getLocationFlag() {
		return this.locationFlag;
	}

	/**
	 * 属性属性境内境外标志的setter方法
	 */
	public void setLocationFlag(String locationFlag) {
		this.locationFlag = locationFlag;
	}

	/**
	 * 属性属性预收挂账标志的getter方法
	 */

	@Column(name = "ACCFLAG")
	public String getAccFlag() {
		return this.accFlag;
	}

	/**
	 * 属性属性预收挂账标志的setter方法
	 */
	public void setAccFlag(String accFlag) {
		this.accFlag = accFlag;
	}

	/**
	 * 属性属性业务员名称的getter方法
	 */

	@Column(name = "HANDLER1NAME")
	public String getHandler1Name() {
		return this.handler1Name;
	}

	/**
	 * 属性属性业务员名称的setter方法
	 */
	public void setHandler1Name(String handler1name) {
		this.handler1Name = handler1name;
	}

	/**
	 * 属性属性代理人名称的getter方法
	 */

	@Column(name = "AGENTNAME")
	public String getAgentName() {
		return this.agentName;
	}

	/**
	 * 属性属性代理人名称的setter方法
	 */
	public void setAgentName(String agentname) {
		this.agentName = agentname;
	}

	/**
	 * 属性属性审核通过标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return this.underWriteFlag;
	}

	/**
	 * 属性属性审核通过标志的setter方法
	 */
	public void setUnderWriteFlag(String underwriteflag) {
		this.underWriteFlag = underwriteflag;
	}

	/**
	 * 属性属性见费出单标志的getter方法
	 */

	@Column(name = "JFEEFLAG")
	public String getJFeeFlag() {
		return this.jFeeFlag;
	}

    /**
	 * 属性属性见费出单标志的setter方法
	 */
	public void setJFeeFlag(String jfeeflag) {
		this.jFeeFlag = jfeeflag;
	}

	/**
	 * 属性属性收付方式的getter方法
	 */

	@Column(name = "PAYWAY")
	public String getPayWay() {
		return this.payWay;
	}

	/**
	 * 属性属性收付方式的setter方法
	 */
	public void setPayWay(String payway) {
		this.payWay = payway;
	}

	/**
	 * 属性属性ENDORTYPE的getter方法
	 */

	@Column(name = "ENDORTYPE")
	public String getEndorType() {
		return this.endorType;
	}

	/**
	 * 属性属性ENDORTYPE的setter方法
	 */
	public void setEndorType(String endorType) {
		this.endorType = endorType;
	}

	/**
	 * 属性属性TOTALPAYNO的getter方法
	 */

	@Column(name = "TOTALPAYNO")
	public Long getTotalPayNo() {
		return this.totalPayNo;
	}

	/**
	 * 属性属性TOTALPAYNO的setter方法
	 */
	public void setTotalPayNo(Long totalPayNo) {
		this.totalPayNo = totalPayNo;
	}

	/**
	 * 属性属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性属性CENTERCODE的getter方法
	 */

	@Column(name = "CENTERCODE")
	public String getCenterCode() {
		return this.centerCode;
	}

	/**
	 * 属性属性CENTERCODE的setter方法
	 */
	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	/**
	 * 属性属性BRANCHCODE的getter方法
	 */

	@Column(name = "BRANCHCODE")
	public String getBranchCode() {
		return this.branchCode;
	}

	/**
	 * 属性属性BRANCHCODE的setter方法
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * 属性属性ACCBOOKTYPE的getter方法
	 */

	@Column(name = "ACCBOOKTYPE")
	public String getAccBookType() {
		return this.accBookType;
	}

	/**
	 * 属性属性ACCBOOKTYPE的setter方法
	 */
	public void setAccBookType(String accBookType) {
		this.accBookType = accBookType;
	}

	/**
	 * 属性属性ACCBOOKCODE的getter方法
	 */

	@Column(name = "ACCBOOKCODE")
	public String getAccBookCode() {
		return this.accBookCode;
	}

	/**
	 * 属性属性ACCBOOKCODE的setter方法
	 */
	public void setAccBookCode(String accBookCode) {
		this.accBookCode = accBookCode;
	}

	/**
	 * 属性属性YEARMONTH的getter方法
	 */

	@Column(name = "YEARMONTH")
	public String getYearMonth() {
		return this.yearMonth;
	}

	/**
	 * 属性属性YEARMONTH的setter方法
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * 属性属性EXCHANGERATE1的getter方法
	 */

	@Column(name = "EXCHANGERATE1")
	public BigDecimal getExchangeRate1() {
		return this.exchangeRate1;
	}

	/**
	 * 属性属性EXCHANGERATE1的setter方法
	 */
	public void setExchangeRate1(BigDecimal exChangeRate1) {
		this.exchangeRate1 = exChangeRate1;
	}

	/**
	 * 属性属性PLANFEECNY的getter方法
	 */

	@Column(name = "PLANFEECNY")
	public BigDecimal getPlanFeeCNY() {
		return this.planFeeCNY;
	}

	/**
	 * 属性属性PLANFEECNY的setter方法
	 */
	public void setPlanFeeCNY(BigDecimal planFeeCNY) {
		this.planFeeCNY = planFeeCNY;
	}

	/**
	 * 属性属性CARMODEL的getter方法
	 */

	@Column(name = "CARMODEL")
	public String getCarModel() {
		return this.carModel;
	}

	/**
	 * 属性属性CARMODEL的setter方法
	 */
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	/**
	 * 属性属性CHANNELTYPE的getter方法
	 */

	@Column(name = "CHANNELTYPE")
	public String getChannelType() {
		return this.channelType;
	}

	/**
	 * 属性属性CHANNELTYPE的setter方法
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * 属性属性ISCOMBIN的getter方法
	 */

	@Column(name = "ISCOMBIN")
	public String getIsCombin() {
		return this.isCombin;
	}

	/**
	 * 属性属性ISCOMBIN的setter方法
	 */
	public void setIsCombin(String isCombin) {
		this.isCombin = isCombin;
	}

	/**
	 * 属性属性CASETYPE的getter方法
	 */

	@Column(name = "CASETYPE")
	public String getCaseType() {
		return this.caseType;
	}

	/**
	 * 属性属性CASETYPE的setter方法
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	/**
	 * 属性属性INDEMNITYDUTY的getter方法
	 */

	@Column(name = "INDEMNITYDUTY")
	public String getIndemnityDuty() {
		return this.indemnityDuty;
	}

	/**
	 * 属性属性INDEMNITYDUTY的setter方法
	 */
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}

	/**
	 * 属性属性ONACCDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ONACCDATE")
	public Date getOnAccDate() {
		return this.onAccDate;
	}

	/**
	 * 属性属性ONACCDATE的setter方法
	 */
	public void setOnAccDate(Date onAccDate) {
		this.onAccDate = onAccDate;
	}

	/**
	 * 属性属性ONACCFLAG的getter方法
	 */

	@Column(name = "ONACCFLAG")
	public String getOnAccFlag() {
		return this.onAccFlag;
	}

	/**
	 * 属性属性ONACCFLAG的setter方法
	 */
	public void setOnAccFlag(String onAccFlag) {
		this.onAccFlag = onAccFlag;
	}

	/**
	 * 属性bankserailno的getter方法
	 */

	@Column(name = "BANKSERAILNO")
	public String getBankSerailNo() {
		return this.bankSerailNo;
	}

	/**
	 * 属性bankserailno的setter方法
	 */
	public void setBankSerailNo(String bankserailno) {
		this.bankSerailNo = bankserailno;
	}

	/**
	 * 属性属性BANKPOSNO的getter方法
	 */

	@Column(name = "BANKPOSNO")
	public String getBankPosNo() {
		return this.bankPosNo;
	}

	/**
	 * 属性属性BANKPOSNO的setter方法
	 */
	public void setBankPosNo(String bankPosNo) {
		this.bankPosNo = bankPosNo;
	}

	/**
	 * 属性属性REALPAYREFFEECNY的getter方法
	 */

	@Column(name = "REALPAYREFFEECNY")
	public BigDecimal getRealPayRefFeeCNY() {
		return this.realPayRefFeeCNY;
	}

	/**
	 * 属性属性REALPAYREFFEECNY的setter方法
	 */
	public void setRealPayRefFeeCNY(BigDecimal realPayRefFeeCNY) {
		this.realPayRefFeeCNY = realPayRefFeeCNY;
	}

	/**
	 * 属性属性EXCHANGERATECNY的getter方法
	 */

	@Column(name = "EXCHANGERATECNY")
	public BigDecimal getExchangeRateCNY() {
		return this.exchangeRateCNY;
	}

	/**
	 * 属性属性EXCHANGERATECNY的setter方法
	 */
	public void setExchangeRateCNY(BigDecimal exchangeRateCNY) {
		this.exchangeRateCNY = exchangeRateCNY;
	}

	/**
	 * 属性属性TAXFLAG的getter方法
	 */

	@Column(name = "TAXFLAG")
	public String getTaxFlag() {
		return this.taxFlag;
	}

	/**
	 * 属性属性TAXFLAG的setter方法
	 */
	public void setTaxFlag(String taxFlag) {
		this.taxFlag = taxFlag;
	}

	/**
	 * 属性属性PREMIUMREFFLAG的getter方法
	 */

	@Column(name = "PREMIUMREFFLAG")
	public String getPremiumRefFlag() {
		return this.premiumRefFlag;
	}

	/**
	 * 属性属性PREMIUMREFFLAG的setter方法
	 */
	public void setPremiumRefFlag(String premiumRefFlag) {
		this.premiumRefFlag = premiumRefFlag;
	}

	/**
	 * 属性属性REALPAYREFFLAG的getter方法
	 */

	@Column(name = "REALPAYREFFLAG")
	public String getRealPayRefFlag() {
		return this.realPayRefFlag;
	}

	/**
	 * 属性属性REALPAYREFFLAG的setter方法
	 */
	public void setRealPayRefFlag(String realPayRefFlag) {
		this.realPayRefFlag = realPayRefFlag;
	}

	/**
	 * 属性属性REVERSEFLAG的getter方法
	 */

	@Column(name = "REVERSEFLAG")
	public String getReverseFlag() {
		return this.reverseFlag;
	}

	/**
	 * 属性属性REVERSEFLAG的setter方法
	 */
	public void setReverseFlag(String reverseFlag) {
		this.reverseFlag = reverseFlag;
	}

	/**
	 * 属性属性ARTICLEENDORFLAG的getter方法
	 */

	@Column(name = "ARTICLEENDORFLAG")
	public String getArticleEndorFlag() {
		return this.articleEndorFlag;
	}

	/**
	 * 属性属性ARTICLEENDORFLAG的setter方法
	 */
	public void setArticleEndorFlag(String articLeenDorFlag) {
		this.articleEndorFlag = articLeenDorFlag;
	}

	/**
	 * 属性属性AGRITYPE的getter方法
	 */

	@Column(name = "AGRITYPE")
	public String getAgriType() {
		return this.agriType;
	}

	/**
	 * 属性属性AGRITYPE的setter方法
	 */
	public void setAgriType(String agriType) {
		this.agriType = agriType;
	}

	/**
	 * 属性属性WRITEOFFDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "WRITEOFFDATE")
	public Date getWriteOffDate() {
		return this.writeOffDate;
	}

	/**
	 * 属性属性WRITEOFFDATE的setter方法
	 */
	public void setWriteOffDate(Date writeOffDate) {
		this.writeOffDate = writeOffDate;
	}

	/**
	 * 属性属性PREMIUMREFDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PREMIUMREFDATE")
	public Date getPremiumRefDate() {
		return this.premiumRefDate;
	}

	/**
	 * 属性属性PREMIUMREFDATE的setter方法
	 */
	public void setPremiumRefDate(Date premiumRefDate) {
		this.premiumRefDate = premiumRefDate;
	}

	/**
	 * 属性属性PACKAGEFLAG的getter方法
	 */

	@Column(name = "PACKAGEFLAG")
	public String getPackageFlag() {
		return this.packageFlag;
	}

	/**
	 * 属性属性PACKAGEFLAG的setter方法
	 */
	public void setPackageFlag(String packageFlag) {
		this.packageFlag = packageFlag;
	}

	/**
	 * 属性属性WRITEOFFFLAG的getter方法
	 */

	@Column(name = "WRITEOFFFLAG")
	public String getWriteOffFlag() {
		return this.writeOffFlag;
	}

	/**
	 * 属性属性WRITEOFFFLAG的setter方法
	 */
	public void setWriteOffFlag(String writeOffFlag) {
		this.writeOffFlag = writeOffFlag;
	}

	/**
	 * 属性属性SIGNDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SIGNDATE")
	public Date getSignDate() {
		return this.signDate;
	}

	/**
	 * 属性属性SIGNDATE的setter方法
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * 属性属性ACCOUNTCODE的getter方法
	 */

	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**
	 * 属性属性ACCOUNTCODE的setter方法
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * 属性属性CUSTOMBANKCODE的getter方法
	 */

	@Column(name = "CUSTOMBANKCODE")
	public String getCustomBankCode() {
		return this.customBankCode;
	}

	/**
	 * 属性属性CUSTOMBANKCODE的setter方法
	 */
	public void setCustomBankCode(String customBankCode) {
		this.customBankCode = customBankCode;
	}

	/**
	 * 属性属性CUSTOMBANKNAME的getter方法
	 */

	@Column(name = "CUSTOMBANKNAME")
	public String getCustomBankName() {
		return this.customBankName;
	}

	/**
	 * 属性属性CUSTOMBANKNAME的setter方法
	 */
	public void setCustomBankName(String customBankName) {
		this.customBankName = customBankName;
	}

	/**
	 * 属性属性CERTIFICATECODE的getter方法
	 */

	@Column(name = "CERTIFICATECODE")
	public String getCertificateCode() {
		return this.certificateCode;
	}

	/**
	 * 属性属性CERTIFICATECODE的setter方法
	 */
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	/**
	 * 属性属性OWNERNAME的getter方法
	 */

	@Column(name = "OWNERNAME")
	public String getOwnerName() {
		return this.ownerName;
	}

	/**
	 * 属性属性OWNERNAME的setter方法
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * 属性属性ACCOUNTTYPE的getter方法
	 */

	@Column(name = "ACCOUNTTYPE")
	public String getAccountType() {
		return this.accountType;
	}

	/**
	 * 属性属性ACCOUNTTYPE的setter方法
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 属性属性ACCOUNTCURRENCY的getter方法
	 */

	@Column(name = "ACCOUNTCURRENCY")
	public String getAccountCurrency() {
		return this.accountCurrency;
	}

	/**
	 * 属性属性ACCOUNTCURRENCY的setter方法
	 */
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	/**
	 * 属性属性OWNERSHIP的getter方法
	 */

	@Column(name = "OWNERSHIP")
	public String getOwnerShip() {
		return this.ownerShip;
	}

	/**
	 * 属性属性OWNERSHIP的setter方法
	 */
	public void setOwnerShip(String ownerShip) {
		this.ownerShip = ownerShip;
	}

	/**
	 * 属性属性EXCEPTIONCODE的getter方法
	 */

	@Column(name = "EXCEPTIONCODE")
	public String getExceptionCode() {
		return this.exceptionCode;
	}

	/**
	 * 属性属性EXCEPTIONCODE的setter方法
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * 属性属性EXCEPTIONTEXT的getter方法
	 */

	@Column(name = "EXCEPTIONTEXT")
	public String getExceptionText() {
		return this.exceptionText;
	}

	/**
	 * 属性属性EXCEPTIONTEXT的setter方法
	 */
	public void setExceptionText(String exceptionText) {
		this.exceptionText = exceptionText;
	}

	/**
	 * 属性属性CHANNELCODE的getter方法
	 */

	@Column(name = "CHANNELCODE")
	public String getChannelCode() {
		return this.channelCode;
	}

	/**
	 * 属性属性CHANNELCODE的setter方法
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	/**
	 * 属性属性虚拟账号的getter方法
	 */
	public String getVirtualNo() {
		return virtualNo;
	}
	
	/**
	 * 属性属性虚拟账号的setter方法
	 */
	public void setVirtualNo(String virtualNo) {
		this.virtualNo = virtualNo;
	}
	/**
	 * 属性sumFee的getter方法
	 * @return
	 */
	public BigDecimal getSumFee() {
		return sumFee;
	}
	/**
	 * 属性sumFee的setter方法
	 */
	public void setSumFee(BigDecimal sumFee) {
		this.sumFee = sumFee;
	}

	/**
	 * 属性属性superMarketName的getter方法
	 */
	@Column(name = "SUPERMARKETNAME")
	public String getSuperMarketName() {
		return superMarketName;
	}

	/**
	 * 属性属性superMarketName的setter方法
	 */
	public void setSuperMarketName(String superMarketName) {
		this.superMarketName = superMarketName;
	}
	
	@Column(name = "ISRENEWLFLAG")
	public String getIsRenewlFlag() {
		return isRenewlFlag;
	}

	public void setIsRenewlFlag(String isRenewlFlag) {
		this.isRenewlFlag = isRenewlFlag;
	}

}
