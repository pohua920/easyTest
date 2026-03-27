package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * POJO类PrpallPolicy保单全貌表
 */
@Entity
@Table(name = "PRPALLPOLICY")
public class PrpallPolicy implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性保单号 */
	private String policyNo;

	/** 属性险类代码 */
	private String classCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性合同号(供合保单使用) */
	private String contractNo;

	/** 属性保单种类 */
	private String policySort;

	/** 属性业务来源（直接/代理） */
	private String businessNature;

	/** 属性投保人代码 */
	private String appliCode;

	/** 属性投保人名称 */
	private String appliName;

	/** 属性被保险人代码 */
	private String insuredCode;

	/** 属性被保险人名称 */
	private String insuredName;

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

	/** 属性手续费比例 */
	private Double disRate;

	/** 属性手续费 */
	private Double commissionAmt;

	/** 属性总折扣率 */
	private Double discount;

	/** 属性币别代码 */
	private String currency;

	/** 属性总保险金额 */
	private Double sumAmount;

	/** 属性原总保险费 */
	private Double sumPremium;

	/** 属性变化总保险费 */
	private Double chgSumPremium;

	/** 属性最新总保险费 */
	private Double lastSumPremium;

	/** 属性realpremium */
	private Double realpremium;

	/** 属性开发票保费 */
	private Double payreffee;

	/** 属性保单期数 */
	private Integer payNum;

	/** 属性总赔款 */
	private Double totalIndemnity;

	/** 属性交费情况 */
	private String statusFlag;

	/** 属性批改次数 */
	private Integer endorseTimes;

	/** 属性理赔次数 */
	private Integer claimTimes;

	/** 属性简易赔案次数 */
	private Integer qucikCaseTimes;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性经办人代码 */
	private String handlerCode;

	/** 属性归属业务员代码 */
	private String handler1Code;

	/** 属性最终核保人代码 */
	private String underwriteCode;

	/** 属性核保完成日期 */
	private Date underwriteEndDate;

	/** 属性代理人代码 */
	private String agentCode;

	/** 属性联共保标志 */
	private String coinsFlag;

	/** 属性保单有效标志 */
	private String validStatus;

	/** 属性其它标志字段 */
	private String othFlag;

	/** 属性状态字段 */
	private String flag;

	/** 属性业务类型 */
	private String businessFlag;

	/**
	 * 类PrpallPolicy的默认构造方法
	 */
	public PrpallPolicy() {
	}

	/**
	 * 属性保单号的getter方法
	 */
	@Id
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
	 * 属性手续费的getter方法
	 */

	@Column(name = "COMMISSIONAMT")
	public Double getCommissionAmt() {
		return this.commissionAmt;
	}

	/**
	 * 属性手续费的setter方法
	 */
	public void setCommissionAmt(Double commissionAmt) {
		this.commissionAmt = commissionAmt;
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
	 * 属性变化总保险费的getter方法
	 */

	@Column(name = "CHGSUMPREMIUM")
	public Double getChgSumPremium() {
		return this.chgSumPremium;
	}

	/**
	 * 属性变化总保险费的setter方法
	 */
	public void setChgSumPremium(Double chgSumPremium) {
		this.chgSumPremium = chgSumPremium;
	}

	/**
	 * 属性最新总保险费的getter方法
	 */

	@Column(name = "LASTSUMPREMIUM")
	public Double getLastSumPremium() {
		return this.lastSumPremium;
	}

	/**
	 * 属性最新总保险费的setter方法
	 */
	public void setLastSumPremium(Double lastSumPremium) {
		this.lastSumPremium = lastSumPremium;
	}

	/**
	 * 属性realpremium的getter方法
	 */

	@Column(name = "REALPREMIUM")
	public Double getRealpremium() {
		return this.realpremium;
	}

	/**
	 * 属性realpremium的setter方法
	 */
	public void setRealpremium(Double realpremium) {
		this.realpremium = realpremium;
	}

	/**
	 * 属性开发票保费的getter方法
	 */

	@Column(name = "PAYREFFEE")
	public Double getPayreffee() {
		return this.payreffee;
	}

	/**
	 * 属性开发票保费的setter方法
	 */
	public void setPayreffee(Double payreffee) {
		this.payreffee = payreffee;
	}

	/**
	 * 属性保单期数的getter方法
	 */

	@Column(name = "PAYNUM")
	public Integer getPayNum() {
		return this.payNum;
	}

	/**
	 * 属性保单期数的setter方法
	 */
	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}

	/**
	 * 属性总赔款的getter方法
	 */

	@Column(name = "TOTALINDEMNITY")
	public Double getTotalIndemnity() {
		return this.totalIndemnity;
	}

	/**
	 * 属性总赔款的setter方法
	 */
	public void setTotalIndemnity(Double totalIndemnity) {
		this.totalIndemnity = totalIndemnity;
	}

	/**
	 * 属性交费情况的getter方法
	 */

	@Column(name = "STATUSFLAG")
	public String getStatusFlag() {
		return this.statusFlag;
	}

	/**
	 * 属性交费情况的setter方法
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
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
	 * 属性简易赔案次数的getter方法
	 */

	@Column(name = "QUCIKCASETIMES")
	public Integer getQucikCaseTimes() {
		return this.qucikCaseTimes;
	}

	/**
	 * 属性简易赔案次数的setter方法
	 */
	public void setQucikCaseTimes(Integer qucikCaseTimes) {
		this.qucikCaseTimes = qucikCaseTimes;
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
	 * 属性保单有效标志的getter方法
	 */

	@Column(name = "VALIDSTATUS")
	public String getValidStatus() {
		return this.validStatus;
	}

	/**
	 * 属性保单有效标志的setter方法
	 */
	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
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

}
