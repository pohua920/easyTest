package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * POJO类PrpLplan赔案收费计划表
 */
@Entity
@Table(name = "PRPLPLAN")
public class PrpLplan implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLplanId id;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性费用类别代码 */
	private String chargeCode;

	/** 属性立案号 */
	private String claimNo;

	/** 属性REGISTNO */
	private String registno;

	/** 属性险类 */
	private String classCode;

	/** 属性险种 */
	private String riskCode;

	/** 属性合同号 */
	private String contractNo;

	/** 属性投保人 */
	private String appliCode;

	/** 属性投保人名称 */
	private String appliName;

	/** 属性被保险人 */
	private String insuredCode;

	/** 属性被保人名称 */
	private String insuredName;

	/** 属性起保日期 */
	private Date startDate;

	/** 属性终保日期 */
	private Date endDate;

	/** 属性缴费期数 */
	private Integer payNo;

	/** 属性TOTALPAYNO */
	private Integer totalPayNo;

	/** 属性应收/应付币种(原币) */
	private String planFeeCurrency;

	/** 属性应收应付金额 */
	private Double planFee;

	/** 属性PLANFEECNY */
	private Double planFeeCNY;

	/** 属性收付汇率 */
	private Double exchangeRate;

	/** 属性分期缴费日期 */
	private Date planDate;

	/** 属性归属机构 */
	private String comCode;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性业务来源 */
	private String businessNature;

	/** 属性代理人代码 */
	private String agentCode;

	/** 属性代理人名称 */
	private String agentname;

	/** 属性业务归属人员 */
	private String handler1Code;

	/** 属性业务员名称 */
	private String handler1name;

	/** 属性经办人 */
	private String handlerCode;

	/** 属性核保/赔日期 */
	private Date underWriteDate;

	/** 属性审核通过标志 */
	private String underwriteflag;

	/** 属性联共保标志 */
	private String coinsFlag;

	/** 属性联共保人代码 */
	private String coinsCode;

	/** 属性联共保人名称 */
	private String coinsName;

	/** 属性联共保类型 */
	private String coinsType;

	/** 属性实收/实付日期 */
	private Date realDate;

	/** 属性实收/实付币别 */
	private String realCurrency;

	/** 属性实收/实付兑换率 */
	private Double realExchangeRate;

	/** 属性收收/应付确认金额 */
	private Double realPayRefFee;

	/** 属性REALPAYREFFEECNY */
	private Double realPayRefFeeCNY;

	/** 属性业务标志 */
	private String othFlag;

	/** 属性境内境外标志 */
	private String locationFlag;

	/** 属性CENTERCODE */
	private String centerCode;

	/** 属性BRANCHCODE */
	private String branchCode;

	/** 属性CARMODEL */
	private String carModel;

	/** 属性CHANNELTYPE */
	private String channelType;

	/** 属性ISCOMBIN */
	private String isCombin;

	/** 属性CASETYPE */
	private String caseType;

	/** 属性INDEMNITYDUTY */
	private String indemnityDuty;

	/** 属性AGRITYPE */
	private String agriType;

	/** 属性预留字段1 */
	private String tcol1;

	/** 属性预留字段2 */
	private String tcol2;

	/** 属性预留字段3 */
	private String tcol3;

	/** 属性预留字段4 */
	private String tcol4;

	/** 属性预留字段5 */
	private String tcol5;

	/** 属性处理标志 */
	private String processFlag;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性数据最後一次处理时间 */
	private Date lastProcessDate;

	/** 属性出错信息 */
	private String errorMessage;

	/** 属性标志 */
	private String flag;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性ACCOUNTCODE */
	private String accountCode;

	/** 属性总行代码 */
	private String bankCode;

	/** 属性开户银行 */
	private String bankName;

	/** 属性CUSTOMBANKCODE */
	private String customBankCode;

	/** 属性CUSTOMBANKNAME */
	private String customBankName;

	/** 属性CERTIFICATECODE */
	private String certificateCode;

	/** 属性OWNERNAME */
	private String ownerName;

	/** 属性帳户归属人电话 */
	private String ownerPhoneNo;

	/** 属性ACCOUNTTYPE */
	private String accountType;

	/** 属性ACCOUNTCURRENCY */
	private String accountCurrency;

	/** 属性OWNERSHIP */
	private String ownerShip;

	/** 属性prpLPlanKinds */
	private List<PrpLplanKind> prpLPlanKinds = new ArrayList<PrpLplanKind>(0);
	private double coinsRate = 0;

	/**
	 * 类PrpLplan的默认构造方法
	 */
	public PrpLplan() {
		this.id = new PrpLplanId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "certiType", column = @Column(name = "CERTITYPE")), @AttributeOverride(name = "certiNo", column = @Column(name = "CERTINO")),
			@AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")), @AttributeOverride(name = "payRefReason", column = @Column(name = "PAYREFREASON")) })
	public PrpLplanId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLplanId id) {
		this.id = id;
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
	 * 属性费用类别代码的getter方法
	 */

	@Column(name = "CHARGECODE")
	public String getChargeCode() {
		return this.chargeCode;
	}

	/**
	 * 属性费用类别代码的setter方法
	 */
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	/**
	 * 属性立案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	/**
	 * 属性REGISTNO的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistno() {
		return this.registno;
	}

	/**
	 * 属性REGISTNO的setter方法
	 */
	public void setRegistno(String registno) {
		this.registno = registno;
	}

	/**
	 * 属性险类的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性险类的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 属性险种的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性险种的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
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
	 * 属性投保人的getter方法
	 */

	@Column(name = "APPLICODE")
	public String getAppliCode() {
		return this.appliCode;
	}

	/**
	 * 属性投保人的setter方法
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
	 * 属性被保险人的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性被保险人的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
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
	 * 属性缴费期数的getter方法
	 */

	@Column(name = "PAYNO")
	public Integer getPayNo() {
		return this.payNo;
	}

	/**
	 * 属性缴费期数的setter方法
	 */
	public void setPayNo(Integer payNo) {
		this.payNo = payNo;
	}

	/**
	 * 属性TOTALPAYNO的getter方法
	 */

	@Column(name = "TOTALPAYNO")
	public Integer getTotalPayNo() {
		return this.totalPayNo;
	}

	/**
	 * 属性TOTALPAYNO的setter方法
	 */
	public void setTotalPayNo(Integer totalPayNo) {
		this.totalPayNo = totalPayNo;
	}

	/**
	 * 属性应收/应付币种(原币)的getter方法
	 */

	@Column(name = "PLANFEECURRENCY")
	public String getPlanFeeCurrency() {
		return this.planFeeCurrency;
	}

	/**
	 * 属性应收/应付币种(原币)的setter方法
	 */
	public void setPlanFeeCurrency(String planFeeCurrency) {
		this.planFeeCurrency = planFeeCurrency;
	}

	/**
	 * 属性应收应付金额的getter方法
	 */

	@Column(name = "PLANFEE")
	public Double getPlanFee() {
		return this.planFee;
	}

	/**
	 * 属性应收应付金额的setter方法
	 */
	public void setPlanFee(Double planFee) {
		this.planFee = planFee;
	}

	/**
	 * 属性PLANFEECNY的getter方法
	 */

	@Column(name = "PLANFEECNY")
	public Double getPlanFeeCNY() {
		return this.planFeeCNY;
	}

	/**
	 * 属性PLANFEECNY的setter方法
	 */
	public void setPlanFeeCNY(Double planFeeCNY) {
		this.planFeeCNY = planFeeCNY;
	}

	/**
	 * 属性收付汇率的getter方法
	 */

	@Column(name = "EXCHANGERATE")
	public Double getExchangeRate() {
		return this.exchangeRate;
	}

	/**
	 * 属性收付汇率的setter方法
	 */
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 属性分期缴费日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PLANDATE")
	public Date getPlanDate() {
		return this.planDate;
	}

	/**
	 * 属性分期缴费日期的setter方法
	 */
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	/**
	 * 属性归属机构的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性归属机构的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
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
	 * 属性代理人名称的getter方法
	 */

	@Column(name = "AGENTNAME")
	public String getAgentname() {
		return this.agentname;
	}

	/**
	 * 属性代理人名称的setter方法
	 */
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	/**
	 * 属性业务归属人员的getter方法
	 */

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return this.handler1Code;
	}

	/**
	 * 属性业务归属人员的setter方法
	 */
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}

	/**
	 * 属性业务员名称的getter方法
	 */

	@Column(name = "HANDLER1NAME")
	public String getHandler1name() {
		return this.handler1name;
	}

	/**
	 * 属性业务员名称的setter方法
	 */
	public void setHandler1name(String handler1name) {
		this.handler1name = handler1name;
	}

	/**
	 * 属性经办人的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性经办人的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性核保/赔日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDERWRITEDATE")
	public Date getUnderWriteDate() {
		return this.underWriteDate;
	}

	/**
	 * 属性核保/赔日期的setter方法
	 */
	public void setUnderWriteDate(Date underWriteDate) {
		this.underWriteDate = underWriteDate;
	}

	/**
	 * 属性审核通过标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderwriteflag() {
		return this.underwriteflag;
	}

	/**
	 * 属性审核通过标志的setter方法
	 */
	public void setUnderwriteflag(String underwriteflag) {
		this.underwriteflag = underwriteflag;
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
	 * 属性联共保人代码的getter方法
	 */

	@Column(name = "COINSCODE")
	public String getCoinsCode() {
		return this.coinsCode;
	}

	/**
	 * 属性联共保人代码的setter方法
	 */
	public void setCoinsCode(String coinsCode) {
		this.coinsCode = coinsCode;
	}

	/**
	 * 属性联共保人名称的getter方法
	 */

	@Column(name = "COINSNAME")
	public String getCoinsName() {
		return this.coinsName;
	}

	/**
	 * 属性联共保人名称的setter方法
	 */
	public void setCoinsName(String coinsName) {
		this.coinsName = coinsName;
	}

	/**
	 * 属性联共保类型的getter方法
	 */

	@Column(name = "COINSTYPE")
	public String getCoinsType() {
		return this.coinsType;
	}

	/**
	 * 属性联共保类型的setter方法
	 */
	public void setCoinsType(String coinsType) {
		this.coinsType = coinsType;
	}

	/**
	 * 属性实收/实付日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REALDATE")
	public Date getRealDate() {
		return this.realDate;
	}

	/**
	 * 属性实收/实付日期的setter方法
	 */
	public void setRealDate(Date realDate) {
		this.realDate = realDate;
	}

	/**
	 * 属性实收/实付币别的getter方法
	 */

	@Column(name = "REALCURRENCY")
	public String getRealCurrency() {
		return this.realCurrency;
	}

	/**
	 * 属性实收/实付币别的setter方法
	 */
	public void setRealCurrency(String realCurrency) {
		this.realCurrency = realCurrency;
	}

	/**
	 * 属性实收/实付兑换率的getter方法
	 */

	@Column(name = "REALEXCHANGERATE")
	public Double getRealExchangeRate() {
		return this.realExchangeRate;
	}

	/**
	 * 属性实收/实付兑换率的setter方法
	 */
	public void setRealExchangeRate(Double realExchangeRate) {
		this.realExchangeRate = realExchangeRate;
	}

	/**
	 * 属性收收/应付确认金额的getter方法
	 */

	@Column(name = "REALPAYREFFEE")
	public Double getRealPayRefFee() {
		return this.realPayRefFee;
	}

	/**
	 * 属性收收/应付确认金额的setter方法
	 */
	public void setRealPayRefFee(Double realPayRefFee) {
		this.realPayRefFee = realPayRefFee;
	}

	/**
	 * 属性REALPAYREFFEECNY的getter方法
	 */

	@Column(name = "REALPAYREFFEECNY")
	public Double getRealPayRefFeeCNY() {
		return this.realPayRefFeeCNY;
	}

	/**
	 * 属性REALPAYREFFEECNY的setter方法
	 */
	public void setRealPayRefFeeCNY(Double realPayRefFeeCNY) {
		this.realPayRefFeeCNY = realPayRefFeeCNY;
	}

	/**
	 * 属性业务标志的getter方法
	 */

	@Column(name = "OTHFLAG")
	public String getOthFlag() {
		return this.othFlag;
	}

	/**
	 * 属性业务标志的setter方法
	 */
	public void setOthFlag(String othFlag) {
		this.othFlag = othFlag;
	}

	/**
	 * 属性境内境外标志的getter方法
	 */

	@Column(name = "LOCATIONFLAG")
	public String getLocationFlag() {
		return this.locationFlag;
	}

	/**
	 * 属性境内境外标志的setter方法
	 */
	public void setLocationFlag(String locationFlag) {
		this.locationFlag = locationFlag;
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
	public String getIndemnityDuty() {
		return this.indemnityDuty;
	}

	/**
	 * 属性INDEMNITYDUTY的setter方法
	 */
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
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
	 * 属性预留字段1的getter方法
	 */

	@Column(name = "TCOL1")
	public String getTcol1() {
		return this.tcol1;
	}

	/**
	 * 属性预留字段1的setter方法
	 */
	public void setTcol1(String tcol1) {
		this.tcol1 = tcol1;
	}

	/**
	 * 属性预留字段2的getter方法
	 */

	@Column(name = "TCOL2")
	public String getTcol2() {
		return this.tcol2;
	}

	/**
	 * 属性预留字段2的setter方法
	 */
	public void setTcol2(String tcol2) {
		this.tcol2 = tcol2;
	}

	/**
	 * 属性预留字段3的getter方法
	 */

	@Column(name = "TCOL3")
	public String getTcol3() {
		return this.tcol3;
	}

	/**
	 * 属性预留字段3的setter方法
	 */
	public void setTcol3(String tcol3) {
		this.tcol3 = tcol3;
	}

	/**
	 * 属性预留字段4的getter方法
	 */

	@Column(name = "TCOL4")
	public String getTcol4() {
		return this.tcol4;
	}

	/**
	 * 属性预留字段4的setter方法
	 */
	public void setTcol4(String tcol4) {
		this.tcol4 = tcol4;
	}

	/**
	 * 属性预留字段5的getter方法
	 */

	@Column(name = "TCOL5")
	public String getTcol5() {
		return this.tcol5;
	}

	/**
	 * 属性预留字段5的setter方法
	 */
	public void setTcol5(String tcol5) {
		this.tcol5 = tcol5;
	}

	/**
	 * 属性处理标志的getter方法
	 */

	@Column(name = "PROCESSFLAG")
	public String getProcessFlag() {
		return this.processFlag;
	}

	/**
	 * 属性处理标志的setter方法
	 */
	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
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
	 * 属性数据最後一次处理时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "LASTPROCESSDATE")
	public Date getLastProcessDate() {
		return this.lastProcessDate;
	}

	/**
	 * 属性数据最後一次处理时间的setter方法
	 */
	public void setLastProcessDate(Date lastProcessDate) {
		this.lastProcessDate = lastProcessDate;
	}

	/**
	 * 属性出错信息的getter方法
	 */

	@Column(name = "ERRORMESSAGE")
	public String getErrorMessage() {
		return this.errorMessage;
	}

	/**
	 * 属性出错信息的setter方法
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	 * 属性发票/支付单备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性发票/支付单备注的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * 属性总行代码的getter方法
	 */

	@Column(name = "BANKCODE")
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * 属性总行代码的setter方法
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 属性开户银行的getter方法
	 */

	@Column(name = "BANKNAME")
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * 属性开户银行的setter方法
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	 * 属性帳户归属人电话的getter方法
	 */

	@Column(name = "OWNERPHONENO")
	public String getOwnerPhoneNo() {
		return this.ownerPhoneNo;
	}

	/**
	 * 属性帳户归属人电话的setter方法
	 */
	public void setOwnerPhoneNo(String ownerPhoneNo) {
		this.ownerPhoneNo = ownerPhoneNo;
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
	public String getOwnerShip() {
		return this.ownerShip;
	}

	/**
	 * 属性OWNERSHIP的setter方法
	 */
	public void setOwnerShip(String ownerShip) {
		this.ownerShip = ownerShip;
	}

	/**
	 * 属性prpLPlanKinds的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLplan")
	public List<PrpLplanKind> getPrpLPlanKinds() {
		return this.prpLPlanKinds;
	}

	/**
	 * 属性prpLPlanKinds的setter方法
	 */
	public void setPrpLPlanKinds(List<PrpLplanKind> prpLPlanKinds) {
		this.prpLPlanKinds = prpLPlanKinds;
	}

	/**
	 * @return the coinsRate
	 */
	@Transient
	public double getCoinsRate() {
		return coinsRate;
	}

	/**
	 * @param coinsRate the coinsRate to set
	 */
	public void setCoinsRate(double coinsRate) {
		this.coinsRate = coinsRate;
	}

}
