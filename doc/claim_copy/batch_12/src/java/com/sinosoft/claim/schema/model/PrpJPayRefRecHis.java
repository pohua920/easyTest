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
 * POJO类PrpJPayRefRecHis
 */
@Entity
@Table(name = "PRPJPAYREFRECHIS")
public class PrpJPayRefRecHis implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpJPayRefRecHisId id;

	/** 属性POLICYNO */
	private String policyNo;

	/** 属性CLAIMNO */
	private String claimNo;

	/** 属性CLASSCODE */
	private String classCode;

	/** 属性RISKCODE */
	private String riskCode;

	/** 属性CONTRACTNO */
	private String contractNo;

	/** 属性APPLICODE */
	private String appliCode;

	/** 属性APPLINAME */
	private String appliName;

	/** 属性INSUREDCODE */
	private String insuredCode;

	/** 属性INSUREDNAME */
	private String insuredName;

	/** 属性STARTDATE */
	private Date startDate;

	/** 属性ENDDATE */
	private Date endDate;

	/** 属性VALIDDATE */
	private Date validDate;

	/** 属性PAYNO */
	private Integer payNo;

	/** 属性CURRENCY1 */
	private String currency1;

	/** 属性PLANFEE */
	private Double planFee;

	/** 属性PLANDATE */
	private Date planDate;

	/** 属性COMCODE */
	private String comCode;

	/** 属性MAKECOM */
	private String makeCom;

	/** 属性AGENTCODE */
	private String agentCode;

	/** 属性HANDLER1CODE */
	private String handler1Code;

	/** 属性HANDLERCODE */
	private String handlerCode;

	/** 属性UNDERWRITEDATE */
	private Date underWriteDate;

	/** 属性COINSFLAG */
	private String coinsFlag;

	/** 属性COINSCODE */
	private String coinsCode;

	/** 属性COINSNAME */
	private String coinsName;

	/** 属性COINSTYPE */
	private String coinsType;

	/** 属性OPERATEDATE */
	private Date operateDate;

	/** 属性OPERATORCODE */
	private String operatorCode;

	/** 属性OPERATEUNIT */
	private String operateUnit;

	/** 属性CURRENCY2 */
	private String currency2;

	/** 属性EXCHANGERATE */
	private Double exchangeRate;

	/** 属性PayRefFee */
	private Double payRefFee;

	/** 属性VISACODE */
	private String visaCode;

	/** 属性VisaName */
	private String visaName;

	/** 属性VISASERIALNO */
	private String visaSerialNo;

	/** 属性PrintDate */
	private Date printDate;

	/** 属性PRINTERCODE */
	private String printerCode;

	/** 属性VISAHANDLER */
	private String visaHandler;

	/** 属性PAYREFNAME */
	private String payRefName;

	/** 属性IDENTIFYTYPE */
	private String identifyType;

	/** 属性IDENTIFYNUMBER */
	private String identifyNumber;

	/** 属性REMARK */
	private String remark;

	/** 属性PAYREFNO */
	private String payRefNo;

	/** 属性PAYREFDATE */
	private Date payRefDate;

	/** 属性FLAG */
	private String flag;

	/** 属性BUSINESSNATURE */
	private String businessNature;

	/** 属性OTHFLAG */
	private String othFlag;

	/** 属性TAXFEE */
	private Double taxFee;

	/** 属性LOCATIONFLAG */
	private String locationFlag;

	/** 属性ACCFLAG */
	private String accFlag;

	/** 属性HANDLER1NAME */
	private String handler1Name;

	/** 属性AGENTNAME */
	private String agentName;

	/** 属性UNDERWRITEFLAG */
	private String underWriteFlag;

	/** 属性JFEEFLAG */
	private String jfeeFlag;

	/** 属性PAYWAY */
	private String payWay;

	/** 属性ENDORTYPE */
	private String endorType;

	/** 属性TOTALPAYNO */
	private Long totalPayNo;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性CENTERCODE */
	private String centerCode;

	/** 属性BRANCHCODE */
	private String branchCode;

	/** 属性ACCBOOKTYPE */
	private String accBookType;

	/** 属性ACCBOOKCODE */
	private String accBookCode;

	/** 属性YEARMONTH */
	private String yearMonth;

	/** 属性EXCHANGERATE1 */
	private Double exchangeRate1;

	/** 属性PLANFEECNY */
	private Double planFeecny;

	/** 属性CARMODEL */
	private String carModel;

	/** 属性CHANNELTYPE */
	private String channelType;

	/** 属性ISCOMBIN */
	private String isCombin;

	/** 属性CASETYPE */
	private String caseType;

	/** 属性INDEMNITYDUTY */
	private String indemnityduty;

	/** 属性ONACCDATE */
	private Date onaccDate;

	/** 属性ONACCFLAG */
	private String onaccFlag;

	/** 属性BANKSERAILNO */
	private String bankSerailNo;

	/** 属性BANKPOSNO */
	private String bankPosNo;

	/** 属性REALPayRefFeeCNY */
	private Double realPayRefFeeCNY;

	/** 属性EXCHANGERATECNY */
	private Double echangeRateCNY;

	/** 属性TAXFLAG */
	private String taxFlag;

	/** 属性PREMIUMREFFLAG */
	private String premiumRefFlag;

	/** 属性REALPAYREFFLAG */
	private String realPayRefFlag;

	/** 属性REVERSEFLAG */
	private String reverseFlag;

	/** 属性ARTICLEENDORFLAG */
	private String articleEndorFlag;

	/** 属性AGRITYPE */
	private String agriType;

	/** 属性WRITEOFFDATE */
	private Date writeOffDate;

	/** 属性PREMIUMREFDATE */
	private Date premiumRefDate;

	/** 属性PACKAGEFLAG */
	private String packageFlag;

	/** 属性WRITEOFFFLAG */
	private String writeOffFlag;

	/** 属性SIGNDATE */
	private Date signDate;

	/** 属性ACCOUNTCODE */
	private String accountCode;

	/** 属性CUSTOMBANKCODE */
	private String customBankCode;

	/** 属性CUSTOMBANKNAME */
	private String customBankName;

	/** 属性CERTIFICATECODE */
	private String certificateCode;

	/** 属性OWNERNAME */
	private String ownerName;

	/** 属性ACCOUNTTYPE */
	private String accountType;

	/** 属性ACCOUNTCURRENCY */
	private String accountCurrency;

	/** 属性OWNERSHIP */
	private String ownership;

	/** 属性EXCEPTIONCODE */
	private String exceptionCode;

	/** 属性EXCEPTIONTEXT */
	private String exceptionText;

	/** 属性CHANNELCODE */
	private String channelCode;

	/**
	 * 类PrpJPayRefRecHis的默认构造方法
	 */
	public PrpJPayRefRecHis() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides( { @AttributeOverride(name = "certitype", column = @Column(name = "CERTITYPE")), @AttributeOverride(name = "certino", column = @Column(name = "CERTINO")),
			@AttributeOverride(name = "serialno", column = @Column(name = "SERIALNO")), @AttributeOverride(name = "payrefreason", column = @Column(name = "PAYREFREASON")),
			@AttributeOverride(name = "payreftimes", column = @Column(name = "PAYREFTIMES")) })
	public PrpJPayRefRecHisId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpJPayRefRecHisId id) {
		this.id = id;
	}

	/**
	 * 属性POLICYNO的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性POLICYNO的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性CLAIMNO的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性CLAIMNO的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性CLASSCODE的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性CLASSCODE的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 属性RISKCODE的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性RISKCODE的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性CONTRACTNO的getter方法
	 */

	@Column(name = "CONTRACTNO")
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * 属性CONTRACTNO的setter方法
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 属性APPLICODE的getter方法
	 */

	@Column(name = "APPLICODE")
	public String getAppliCode() {
		return this.appliCode;
	}

	/**
	 * 属性APPLICODE的setter方法
	 */
	public void setAppliCode(String appliCode) {
		this.appliCode = appliCode;
	}

	/**
	 * 属性APPLINAME的getter方法
	 */

	@Column(name = "APPLINAME")
	public String getAppliName() {
		return this.appliName;
	}

	/**
	 * 属性APPLINAME的setter方法
	 */
	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	/**
	 * 属性INSUREDCODE的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性INSUREDCODE的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性INSUREDNAME的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性INSUREDNAME的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性STARTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性STARTDATE的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 属性ENDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性ENDDATE的setter方法
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 属性VALIDDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDDATE")
	public Date getValidDate() {
		return this.validDate;
	}

	/**
	 * 属性VALIDDATE的setter方法
	 */
	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	/**
	 * 属性PAYNO的getter方法
	 */

	@Column(name = "PAYNO")
	public Integer getPayNo() {
		return this.payNo;
	}

	/**
	 * 属性PAYNO的setter方法
	 */
	public void setPayNo(Integer payNo) {
		this.payNo = payNo;
	}

	/**
	 * 属性CURRENCY1的getter方法
	 */

	@Column(name = "CURRENCY1")
	public String getCurrency1() {
		return this.currency1;
	}

	/**
	 * 属性CURRENCY1的setter方法
	 */
	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	/**
	 * 属性PLANFEE的getter方法
	 */

	@Column(name = "PLANFEE")
	public Double getPlanFee() {
		return this.planFee;
	}

	/**
	 * 属性PLANFEE的setter方法
	 */
	public void setPlanFee(Double planFee) {
		this.planFee = planFee;
	}

	/**
	 * 属性PLANDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PLANDATE")
	public Date getPlanDate() {
		return this.planDate;
	}

	/**
	 * 属性PLANDATE的setter方法
	 */
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	/**
	 * 属性COMCODE的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性COMCODE的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性MAKECOM的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性MAKECOM的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**
	 * 属性AGENTCODE的getter方法
	 */

	@Column(name = "AGENTCODE")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**
	 * 属性AGENTCODE的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 属性HANDLER1CODE的getter方法
	 */

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return this.handler1Code;
	}

	/**
	 * 属性HANDLER1CODE的setter方法
	 */
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}

	/**
	 * 属性HANDLERCODE的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性HANDLERCODE的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性UNDERWRITEDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDERWRITEDATE")
	public Date getUnderWriteDate() {
		return this.underWriteDate;
	}

	/**
	 * 属性UNDERWRITEDATE的setter方法
	 */
	public void setUnderWriteDate(Date underWriteDate) {
		this.underWriteDate = underWriteDate;
	}

	/**
	 * 属性COINSFLAG的getter方法
	 */

	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return this.coinsFlag;
	}

	/**
	 * 属性COINSFLAG的setter方法
	 */
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}

	/**
	 * 属性COINSCODE的getter方法
	 */

	@Column(name = "COINSCODE")
	public String getCoinsCode() {
		return this.coinsCode;
	}

	/**
	 * 属性COINSCODE的setter方法
	 */
	public void setCoinsCode(String coinsCode) {
		this.coinsCode = coinsCode;
	}

	/**
	 * 属性COINSNAME的getter方法
	 */

	@Column(name = "COINSNAME")
	public String getCoinsName() {
		return this.coinsName;
	}

	/**
	 * 属性COINSNAME的setter方法
	 */
	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}

	/**
	 * 属性COINSTYPE的getter方法
	 */

	@Column(name = "COINSTYPE")
	public String getCoinsType() {
		return this.coinsType;
	}

	/**
	 * 属性COINSTYPE的setter方法
	 */
	public void setCoinsType(String coinsType) {
		this.coinsType = coinsType;
	}

	/**
	 * 属性OPERATEDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OPERATEDATE")
	public Date getOperateDate() {
		return this.operateDate;
	}

	/**
	 * 属性OPERATEDATE的setter方法
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性OPERATORCODE的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性OPERATORCODE的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性OPERATEUNIT的getter方法
	 */

	@Column(name = "OPERATEUNIT")
	public String getOperateUnit() {
		return this.operateUnit;
	}

	/**
	 * 属性OPERATEUNIT的setter方法
	 */
	public void setOperateUnit(String operateUnit) {
		this.operateUnit = operateUnit;
	}

	/**
	 * 属性CURRENCY2的getter方法
	 */

	@Column(name = "CURRENCY2")
	public String getCurrency2() {
		return this.currency2;
	}

	/**
	 * 属性CURRENCY2的setter方法
	 */
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
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
	 * 属性PayRefFee的getter方法
	 */

	@Column(name = "PayRefFee")
	public Double getPayRefFee() {
		return this.payRefFee;
	}

	/**
	 * 属性PayRefFee的setter方法
	 */
	public void setPayRefFee(Double payRefFee) {
		this.payRefFee = payRefFee;
	}

	/**
	 * 属性VisaCode的getter方法
	 */

	@Column(name = "VisaCode")
	public String getVisaCode() {
		return this.visaCode;
	}

	/**
	 * 属性VisaCode的setter方法
	 */
	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}

	/**
	 * 属性VisaName的getter方法
	 */

	@Column(name = "VisaName")
	public String getVisaName() {
		return this.visaName;
	}

	/**
	 * 属性VisaName的setter方法
	 */
	public void setVisaName(String visaName) {
		this.visaName = visaName;
	}

	/**
	 * 属性VisaSerialNo的getter方法
	 */

	@Column(name = "VisaSerialNo")
	public String getVisaSerialNo() {
		return this.visaSerialNo;
	}

	/**
	 * 属性VisaSerialNo的setter方法
	 */
	public void setVisaSerialNo(String visaSerialNo) {
		this.visaSerialNo = visaSerialNo;
	}

	/**
	 * 属性PrintDate的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PrintDate")
	public Date getPrintDate() {
		return this.printDate;
	}

	/**
	 * 属性PrintDate的setter方法
	 */
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	/**
	 * 属性PRINTERCODE的getter方法
	 */

	@Column(name = "PRINTERCODE")
	public String getPrinterCode() {
		return this.printerCode;
	}

	/**
	 * 属性PRINTERCODE的setter方法
	 */
	public void setPrinterCode(String printerCode) {
		this.printerCode = printerCode;
	}

	/**
	 * 属性VISAHANDLER的getter方法
	 */

	@Column(name = "VISAHANDLER")
	public String getVisaHandler() {
		return this.visaHandler;
	}

	/**
	 * 属性VISAHANDLER的setter方法
	 */
	public void setVisaHandler(String visaHandler) {
		this.visaHandler = visaHandler;
	}

	/**
	 * 属性PAYREFNAME的getter方法
	 */

	@Column(name = "PAYREFNAME")
	public String getPayRefName() {
		return this.payRefName;
	}

	/**
	 * 属性PAYREFNAME的setter方法
	 */
	public void setPayRefName(String payRefName) {
		this.payRefName = payRefName;
	}

	/**
	 * 属性IDENTIFYTYPE的getter方法
	 */

	@Column(name = "IDENTIFYTYPE")
	public String getIdentifyType() {
		return this.identifyType;
	}

	/**
	 * 属性IDENTIFYTYPE的setter方法
	 */
	public void setIdentifyType(String identifyType) {
		this.identifyType = identifyType;
	}

	/**
	 * 属性IDENTIFYNUMBER的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性IDENTIFYNUMBER的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性REMARK的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性REMARK的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性PAYREFNO的getter方法
	 */

	@Column(name = "PAYREFNO")
	public String getPayRefNo() {
		return this.payRefNo;
	}

	/**
	 * 属性PAYREFNO的setter方法
	 */
	public void setPayRefNo(String payRefNo) {
		this.payRefNo = payRefNo;
	}

	/**
	 * 属性PAYREFDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYREFDATE")
	public Date getPayRefDate() {
		return this.payRefDate;
	}

	/**
	 * 属性PAYREFDATE的setter方法
	 */
	public void setPayRefDate(Date payRefDate) {
		this.payRefDate = payRefDate;
	}

	/**
	 * 属性FLAG的getter方法
	 */

	@Column(name = "FLAG")
	public String getFlag() {
		return this.flag;
	}

	/**
	 * 属性FLAG的setter方法
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 属性BUSINESSNATURE的getter方法
	 */

	@Column(name = "BUSINESSNATURE")
	public String getBusinessNature() {
		return this.businessNature;
	}

	/**
	 * 属性BUSINESSNATURE的setter方法
	 */
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	/**
	 * 属性OTHFLAG的getter方法
	 */

	@Column(name = "OTHFLAG")
	public String getOthFlag() {
		return this.othFlag;
	}

	/**
	 * 属性OTHFLAG的setter方法
	 */
	public void setOthFlag(String othFlag) {
		this.othFlag = othFlag;
	}

	/**
	 * 属性TAXFEE的getter方法
	 */

	@Column(name = "TAXFEE")
	public Double getTaxFee() {
		return this.taxFee;
	}

	/**
	 * 属性TAXFEE的setter方法
	 */
	public void setTaxFee(Double taxFee) {
		this.taxFee = taxFee;
	}

	/**
	 * 属性LOCATIONFLAG的getter方法
	 */

	@Column(name = "LOCATIONFLAG")
	public String getLocationFlag() {
		return this.locationFlag;
	}

	/**
	 * 属性LOCATIONFLAG的setter方法
	 */
	public void setLocationFlag(String locationFlag) {
		this.locationFlag = locationFlag;
	}

	/**
	 * 属性ACCFLAG的getter方法
	 */

	@Column(name = "ACCFLAG")
	public String getAccFlag() {
		return this.accFlag;
	}

	/**
	 * 属性ACCFLAG的setter方法
	 */
	public void setAccFlag(String accFlag) {
		this.accFlag = accFlag;
	}

	/**
	 * 属性HANDLER1NAME的getter方法
	 */

	@Column(name = "HANDLER1NAME")
	public String getHandler1Name() {
		return this.handler1Name;
	}

	/**
	 * 属性HANDLER1NAME的setter方法
	 */
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}

	/**
	 * 属性AGENTNAME的getter方法
	 */

	@Column(name = "AGENTNAME")
	public String getAgentName() {
		return this.agentName;
	}

	/**
	 * 属性AGENTNAME的setter方法
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * 属性UNDERWRITEFLAG的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return this.underWriteFlag;
	}

	/**
	 * 属性UNDERWRITEFLAG的setter方法
	 */
	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
	}

	/**
	 * 属性JFEEFLAG的getter方法
	 */

	@Column(name = "JFEEFLAG")
	public String getJfeeFlag() {
		return this.jfeeFlag;
	}

	/**
	 * 属性JFEEFLAG的setter方法
	 */
	public void setJfeeFlag(String jfeeFlag) {
		this.jfeeFlag = jfeeFlag;
	}

	/**
	 * 属性PAYWAY的getter方法
	 */

	@Column(name = "PAYWAY")
	public String getPayWay() {
		return this.payWay;
	}

	/**
	 * 属性PAYWAY的setter方法
	 */
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	/**
	 * 属性ENDORTYPE的getter方法
	 */

	@Column(name = "ENDORTYPE")
	public String getEndorType() {
		return this.endorType;
	}

	/**
	 * 属性ENDORTYPE的setter方法
	 */
	public void setEndorType(String endorType) {
		this.endorType = endorType;
	}

	/**
	 * 属性TOTALPAYNO的getter方法
	 */

	@Column(name = "TOTALPAYNO")
	public Long getTotalPayNo() {
		return this.totalPayNo;
	}

	/**
	 * 属性TOTALPAYNO的setter方法
	 */
	public void setTotalPayNo(Long totalPayNo) {
		this.totalPayNo = totalPayNo;
	}

	/**
	 * 属性INPUTDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性INPUTDATE的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性CENTERCODE的getter方法
	 */

	@Column(name = "CENTERCODE")
	public String getCenterCode() {
		return this.centerCode;
	}

	/**
	 * 属性CENTERCODE的setter方法
	 */
	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	/**
	 * 属性BRANCHCODE的getter方法
	 */

	@Column(name = "BRANCHCODE")
	public String getBranchCode() {
		return this.branchCode;
	}

	/**
	 * 属性BRANCHCODE的setter方法
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * 属性ACCBOOKTYPE的getter方法
	 */

	@Column(name = "ACCBOOKTYPE")
	public String getAccBookType() {
		return this.accBookType;
	}

	/**
	 * 属性ACCBOOKTYPE的setter方法
	 */
	public void setAccBookType(String accBookType) {
		this.accBookType = accBookType;
	}

	/**
	 * 属性ACCBOOKCODE的getter方法
	 */

	@Column(name = "ACCBOOKCODE")
	public String getAccBookCode() {
		return this.accBookCode;
	}

	/**
	 * 属性ACCBOOKCODE的setter方法
	 */
	public void setAccBookCode(String accBookCode) {
		this.accBookCode = accBookCode;
	}

	/**
	 * 属性YEARMONTH的getter方法
	 */

	@Column(name = "YEARMONTH")
	public String getYearMonth() {
		return this.yearMonth;
	}

	/**
	 * 属性YEARMONTH的setter方法
	 */
	public void setYearMonth(String yearMonth) {
		this.yearMonth = yearMonth;
	}

	/**
	 * 属性EXCHANGERATE1的getter方法
	 */

	@Column(name = "EXCHANGERATE1")
	public Double getExchangeRate1() {
		return this.exchangeRate1;
	}

	/**
	 * 属性EXCHANGERATE1的setter方法
	 */
	public void setExchangeRate1(Double exchangeRate1) {
		this.exchangeRate1 = exchangeRate1;
	}

	/**
	 * 属性PLANFEECNY的getter方法
	 */

	@Column(name = "PLANFEECNY")
	public Double getPlanFeecny() {
		return this.planFeecny;
	}

	/**
	 * 属性PLANFEECNY的setter方法
	 */
	public void setPlanFeecny(Double planFeecny) {
		this.planFeecny = planFeecny;
	}

	/**
	 * 属性CARMODEL的getter方法
	 */

	@Column(name = "CARMODEL")
	public String getCarModel() {
		return this.carModel;
	}

	/**
	 * 属性CARMODEL的setter方法
	 */
	public void setCarModel(String carModel) {
		this.carModel = carModel;
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
	 * 属性ISCOMBIN的getter方法
	 */

	@Column(name = "ISCOMBIN")
	public String getIsCombin() {
		return this.isCombin;
	}

	/**
	 * 属性ISCOMBIN的setter方法
	 */
	public void setIsCombin(String isCombin) {
		this.isCombin = isCombin;
	}

	/**
	 * 属性CASETYPE的getter方法
	 */

	@Column(name = "CASETYPE")
	public String getCaseType() {
		return this.caseType;
	}

	/**
	 * 属性CASETYPE的setter方法
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	/**
	 * 属性INDEMNITYDUTY的getter方法
	 */

	@Column(name = "INDEMNITYDUTY")
	public String getIndemnityduty() {
		return this.indemnityduty;
	}

	/**
	 * 属性INDEMNITYDUTY的setter方法
	 */
	public void setIndemnityduty(String indemnityduty) {
		this.indemnityduty = indemnityduty;
	}

	/**
	 * 属性ONACCDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ONACCDATE")
	public Date getOnaccDate() {
		return this.onaccDate;
	}

	/**
	 * 属性ONACCDATE的setter方法
	 */
	public void setOnaccDate(Date onaccDate) {
		this.onaccDate = onaccDate;
	}

	/**
	 * 属性ONACCFLAG的getter方法
	 */

	@Column(name = "ONACCFLAG")
	public String getOnaccFlag() {
		return this.onaccFlag;
	}

	/**
	 * 属性ONACCFLAG的setter方法
	 */
	public void setOnaccFlag(String onaccFlag) {
		this.onaccFlag = onaccFlag;
	}

	/**
	 * 属性BANKSERAILNO的getter方法
	 */

	@Column(name = "BANKSERAILNO")
	public String getBankSerailNo() {
		return this.bankSerailNo;
	}

	/**
	 * 属性BANKSERAILNO的setter方法
	 */
	public void setBankSerailNo(String bankSerailNo) {
		this.bankSerailNo = bankSerailNo;
	}

	/**
	 * 属性BANKPOSNO的getter方法
	 */

	@Column(name = "BANKPOSNO")
	public String getBankPosNo() {
		return this.bankPosNo;
	}

	/**
	 * 属性BANKPOSNO的setter方法
	 */
	public void setBankPosNo(String bankPosNo) {
		this.bankPosNo = bankPosNo;
	}

	/**
	 * 属性REALPayRefFeeCNY的getter方法
	 */

	@Column(name = "REALPayRefFeeCNY")
	public Double getRealPayRefFeeCNY() {
		return this.realPayRefFeeCNY;
	}

	/**
	 * 属性REALPayRefFeeCNY的setter方法
	 */
	public void setRealPayRefFeeCNY(Double realPayRefFeeCNY) {
		this.realPayRefFeeCNY = realPayRefFeeCNY;
	}

	/**
	 * 属性EXCHANGERATECNY的getter方法
	 */

	@Column(name = "EXCHANGERATECNY")
	public Double getEchangeRateCNY() {
		return this.echangeRateCNY;
	}

	/**
	 * 属性EXCHANGERATECNY的setter方法
	 */
	public void setEchangeRateCNY(Double echangeRateCNY) {
		this.echangeRateCNY = echangeRateCNY;
	}

	/**
	 * 属性TAXFLAG的getter方法
	 */

	@Column(name = "TAXFLAG")
	public String getTaxFlag() {
		return this.taxFlag;
	}

	/**
	 * 属性TAXFLAG的setter方法
	 */
	public void setTaxFlag(String taxFlag) {
		this.taxFlag = taxFlag;
	}

	/**
	 * 属性PREMIUMREFFLAG的getter方法
	 */

	@Column(name = "PREMIUMREFFLAG")
	public String getPremiumRefFlag() {
		return this.premiumRefFlag;
	}

	/**
	 * 属性PREMIUMREFFLAG的setter方法
	 */
	public void setPremiumRefFlag(String premiumRefFlag) {
		this.premiumRefFlag = premiumRefFlag;
	}

	/**
	 * 属性REALPAYREFFLAG的getter方法
	 */

	@Column(name = "REALPAYREFFLAG")
	public String getRealPayRefFlag() {
		return this.realPayRefFlag;
	}

	/**
	 * 属性REALPAYREFFLAG的setter方法
	 */
	public void setRealPayRefFlag(String realPayRefFlag) {
		this.realPayRefFlag = realPayRefFlag;
	}

	/**
	 * 属性REVERSEFLAG的getter方法
	 */

	@Column(name = "REVERSEFLAG")
	public String getReverseFlag() {
		return this.reverseFlag;
	}

	/**
	 * 属性REVERSEFLAG的setter方法
	 */
	public void setReverseFlag(String reverseFlag) {
		this.reverseFlag = reverseFlag;
	}

	/**
	 * 属性ARTICLEENDORFLAG的getter方法
	 */

	@Column(name = "ARTICLEENDORFLAG")
	public String getArticleEndorFlag() {
		return this.articleEndorFlag;
	}

	/**
	 * 属性ARTICLEENDORFLAG的setter方法
	 */
	public void setArticleEndorFlag(String articleEndorFlag) {
		this.articleEndorFlag = articleEndorFlag;
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
	 * 属性WRITEOFFDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "WRITEOFFDATE")
	public Date getWriteOffDate() {
		return this.writeOffDate;
	}

	/**
	 * 属性WRITEOFFDATE的setter方法
	 */
	public void setWriteOffDate(Date writeOffDate) {
		this.writeOffDate = writeOffDate;
	}

	/**
	 * 属性PREMIUMREFDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PREMIUMREFDATE")
	public Date getPremiumRefDate() {
		return this.premiumRefDate;
	}

	/**
	 * 属性PREMIUMREFDATE的setter方法
	 */
	public void setPremiumRefDate(Date premiumRefDate) {
		this.premiumRefDate = premiumRefDate;
	}

	/**
	 * 属性PACKAGEFLAG的getter方法
	 */

	@Column(name = "PACKAGEFLAG")
	public String getPackageFlag() {
		return this.packageFlag;
	}

	/**
	 * 属性PACKAGEFLAG的setter方法
	 */
	public void setPackageFlag(String packageFlag) {
		this.packageFlag = packageFlag;
	}

	/**
	 * 属性WRITEOFFFLAG的getter方法
	 */

	@Column(name = "WRITEOFFFLAG")
	public String getWriteOffFlag() {
		return this.writeOffFlag;
	}

	/**
	 * 属性WRITEOFFFLAG的setter方法
	 */
	public void setWriteOffFlag(String writeOffFlag) {
		this.writeOffFlag = writeOffFlag;
	}

	/**
	 * 属性SIGNDATE的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "SIGNDATE")
	public Date getSignDate() {
		return this.signDate;
	}

	/**
	 * 属性SIGNDATE的setter方法
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * 属性ACCOUNTCODE的getter方法
	 */

	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**
	 * 属性ACCOUNTCODE的setter方法
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * 属性CUSTOMBANKCODE的getter方法
	 */

	@Column(name = "CUSTOMBANKCODE")
	public String getCustomBankCode() {
		return this.customBankCode;
	}

	/**
	 * 属性CUSTOMBANKCODE的setter方法
	 */
	public void setCustomBankCode(String customBankCode) {
		this.customBankCode = customBankCode;
	}

	/**
	 * 属性CUSTOMBANKNAME的getter方法
	 */

	@Column(name = "CUSTOMBANKNAME")
	public String getCustomBankName() {
		return this.customBankName;
	}

	/**
	 * 属性CUSTOMBANKNAME的setter方法
	 */
	public void setCustomBankName(String customBankName) {
		this.customBankName = customBankName;
	}

	/**
	 * 属性CERTIFICATECODE的getter方法
	 */

	@Column(name = "CERTIFICATECODE")
	public String getCertificateCode() {
		return this.certificateCode;
	}

	/**
	 * 属性CERTIFICATECODE的setter方法
	 */
	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}

	/**
	 * 属性OWNERNAME的getter方法
	 */

	@Column(name = "OWNERNAME")
	public String getOwnerName() {
		return this.ownerName;
	}

	/**
	 * 属性OWNERNAME的setter方法
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * 属性ACCOUNTTYPE的getter方法
	 */

	@Column(name = "ACCOUNTTYPE")
	public String getAccountType() {
		return this.accountType;
	}

	/**
	 * 属性ACCOUNTTYPE的setter方法
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 属性ACCOUNTCURRENCY的getter方法
	 */

	@Column(name = "ACCOUNTCURRENCY")
	public String getAccountCurrency() {
		return this.accountCurrency;
	}

	/**
	 * 属性ACCOUNTCURRENCY的setter方法
	 */
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	/**
	 * 属性OWNERSHIP的getter方法
	 */

	@Column(name = "OWNERSHIP")
	public String getOwnership() {
		return this.ownership;
	}

	/**
	 * 属性OWNERSHIP的setter方法
	 */
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	/**
	 * 属性EXCEPTIONCODE的getter方法
	 */

	@Column(name = "EXCEPTIONCODE")
	public String getExceptionCode() {
		return this.exceptionCode;
	}

	/**
	 * 属性EXCEPTIONCODE的setter方法
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	/**
	 * 属性EXCEPTIONTEXT的getter方法
	 */

	@Column(name = "EXCEPTIONTEXT")
	public String getExceptionText() {
		return this.exceptionText;
	}

	/**
	 * 属性EXCEPTIONTEXT的setter方法
	 */
	public void setExceptionText(String exceptionText) {
		this.exceptionText = exceptionText;
	}

	/**
	 * 属性CHANNELCODE的getter方法
	 */

	@Column(name = "CHANNELCODE")
	public String getChannelCode() {
		return this.channelCode;
	}

	/**
	 * 属性CHANNELCODE的setter方法
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

}
