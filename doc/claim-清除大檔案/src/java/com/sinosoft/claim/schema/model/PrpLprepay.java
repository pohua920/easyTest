package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLprepay预赔登记表
 */
@Entity
@Table(name = "PRPLPREPAY")
public class PrpLprepay implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性预赔计算书号 */
	private String preCompensateNo;

	/** 属性立案号码 */
	private String claimNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号 */
	private String policyNo;

	/** 属性币别代码 */
	private String currency;

	/** 属性逾期欠款期数 */
	private Double arrearageTimes;

	/** 属性逾期欠款金额 */
	private Double sumArrearage;

	/** 属性已预（垫）付金额 */
	private Double sumBeforePrePaid;

	/** 属性本次垫付逾期欠款期数 */
	private Double blockUpTimes;

	/** 属性预赔金额 */
	private double sumPrePaid;

	/** 属性总预（垫）付金额 */
	private double sumTotalPrepaid;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性经办人代码 */
	private String handlerCode;

	/** 属性归属业务员代码 */
	private String handler1Code;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性最终核保人代码 */
	private String underWriteCode;

	/** 属性最终核保人名称 */
	private String underWriteName;

	/** 属性保单统计年月 */
	private Date statisticsYM;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性核保完成日期 */
	private Date underWriteEndDate;

	/** 属性核保标志 */
	private String underWriteFlag;

	/** 属性状态字段 */
	private String flag;

	/** 属性案件性质 */
	private String caseType;

	/** 属性本位币赔款金额 */
	private Double paidCNY;

	/** 属性EXCHANGERATE */
	private Double exchangeRate;

	/** 属性是否是代付赔款 */
	private String isPayForOther;

	/** 属性银行帳号 */
	private String accountCode;

	/** 属性BANKCODE */
	private String bankCode;

	/** 属性开户银行 */
	private String bankName;

	/** 属性客户银行代码 */
	private String customBankCode;

	/** 属性客户银行名称 */
	private String customBankName;

	/** 属性帳户归属人证件代码 */
	private String certifiCateCode;

	/** 属性帳户归属人名称 */
	private String ownerName;

	/** 属性帳户归属人电话 */
	private String ownerPhoneNo;

	/** 属性帳户类型 */
	private String accountType;

	/** 属性帳户币别 */
	private String accountCurrency;

	/** 属性业务与帳户关系 */
	private String ownership;

	/** 属性代理人代码 */
	private String agentCode = "";
	/** 属性出险开始分钟 */
	private String damageStartMinute = "";
	/** 属性地址代码 */
	private String addressCode = "";
	/** 属性经办人名称 */
	private String handlerName = "";
	/** 属性代理人名称 */
	private String agentName = "";
	/** 属性归属业务员名称 */
	private String handler1Name = "";
	/** 属性部门名称 */
	private String comName = "";
	/** 属性条款类别 */
	private String clauseType = "";
	/** 属性条款名称 */
	private String clauseName = "";
	/** 号牌号码 */
	private String licenseNo = "";
	/** 号牌底色代码 */
	private String licenseColorCode = "";
	/** 属性起保日期 */
	private DateTime startDate = new DateTime();
	/** 属性终保日期 */
	private DateTime endDate = new DateTime();
	/** 属性保险损失金额 */
	private double sumClaim = 0d;
	/** 属性总保额 */
	private double sumAmount = 0d;
	/** 属性总保费 */
	private double sumPremium = 0d;
	/** 属性出险地点分类代码 */
	private String damageAddressType = "";
	/** 属性出险地点 */
	private String damageAddress = "";
	/** 号牌底色名称 */
	private String licenseColor = "";
	/** 发动机号 */
	private String engineNo = "";
	/** 属性出险日期起 */
	private DateTime damageStartDate = new DateTime();
	/** 属性出险开始小时 */
	private String damageStartHour = "";
	/** 车架号 */
	private String frameNo = "";
	/** 厂牌型号 */
	private String brandName = "";
	/** 车辆种类 */
	private String carKindCode = "";
	/** 车辆种类 */
	private String carKind = "";
	/** Dto传递时的编辑状态，如Eidt,Add等等 */
	private String editType = "";
	/** 币别名称 */
	private String currencyName = "";
	/** 属性险类代码 */
	private String classCode = "";
	// 报案号
	private String registNo = "";
	/** 属性出险次数 */
	private int perilCount = 0;
	/** 列表 */
	Collection<PrpLprepay> claimList;
	/** 属性此查勘案件的操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = ""; // Modify By Sunhao,2004-08-24
	/** 属性此查勘案件的操作时间 */
	private DateTime operateDate = new DateTime();// Modify By Sunhao,2004-08-24
	/** 报案列表 */
	Collection<PrpLregist> registList;
	/** 属性例外事项 */
	private String exceptions = "";
	/** 属性原因 */
	private String reason = "";

	/**
	 * 类PrpLprepay的默认构造方法
	 */
	public PrpLprepay() {
	}

	/**
	 * 属性预赔计算书号的getter方法
	 */
	@Id
	@Column(name = "PRECOMPENSATENO")
	public String getPreCompensateNo() {
		return this.preCompensateNo;
	}

	/**
	 * 属性预赔计算书号的setter方法
	 */
	public void setPreCompensateNo(String preCompensateNo) {
		this.preCompensateNo = preCompensateNo;
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
	 * 属性逾期欠款期数的getter方法
	 */
	@Column(name = "ARREARAGETIMES")
	public Double getArrearageTimes() {
		return this.arrearageTimes;
	}

	/**
	 * 属性逾期欠款期数的setter方法
	 */
	public void setArrearageTimes(Double arrearageTimes) {
		this.arrearageTimes = arrearageTimes;
	}

	/**
	 * 属性逾期欠款金额的getter方法
	 */
	@Column(name = "SUMARREARAGE")
	public Double getSumArrearage() {
		return this.sumArrearage;
	}

	/**
	 * 属性逾期欠款金额的setter方法
	 */
	public void setSumArrearage(Double sumArrearage) {
		this.sumArrearage = sumArrearage;
	}

	/**
	 * 属性已预（垫）付金额的getter方法
	 */
	@Column(name = "SUMBEFOREPREPAID")
	public Double getSumBeforePrePaid() {
		return this.sumBeforePrePaid;
	}

	/**
	 * 属性已预（垫）付金额的setter方法
	 */
	public void setSumBeforePrePaid(Double sumBeforePrePaid) {
		this.sumBeforePrePaid = sumBeforePrePaid;
	}

	/**
	 * 属性本次垫付逾期欠款期数的getter方法
	 */
	@Column(name = "BLOCKUPTIMES")
	public Double getBlockUpTimes() {
		return this.blockUpTimes;
	}

	/**
	 * 属性本次垫付逾期欠款期数的setter方法
	 */
	public void setBlockUpTimes(Double blockUpTimes) {
		this.blockUpTimes = blockUpTimes;
	}

	/**
	 * 属性预赔金额的getter方法
	 */
	@Column(name = "SUMPREPAID")
	public double getSumPrePaid() {
		return this.sumPrePaid;
	}

	/**
	 * 属性预赔金额的setter方法
	 */
	public void setSumPrePaid(double sumPrePaid) {
		this.sumPrePaid = sumPrePaid;
	}

	/**
	 * 属性总预（垫）付金额的getter方法
	 */
	@Column(name = "SUMTOTALPREPAID")
	public double getSumTotalPrepaid() {
		return this.sumTotalPrepaid;
	}

	/**
	 * 属性总预（垫）付金额的setter方法
	 */
	public void setSumTotalPrepaid(double sumTotalPrepaid) {
		this.sumTotalPrepaid = sumTotalPrepaid;
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
	public String getUnderWriteCode() {
		return this.underWriteCode;
	}

	/**
	 * 属性最终核保人代码的setter方法
	 */
	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
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
	 * 属性核保完成日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDERWRITEENDDATE")
	public Date getUnderWriteEndDate() {
		return this.underWriteEndDate;
	}

	/**
	 * 属性核保完成日期的setter方法
	 */
	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
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
	 * 属性案件性质的getter方法
	 */

	@Column(name = "CASETYPE")
	public String getCaseType() {
		return this.caseType;
	}

	/**
	 * 属性案件性质的setter方法
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	/**
	 * 属性本位币赔款金额的getter方法
	 */

	@Column(name = "PAIDCNY")
	public Double getPaidCNY() {
		return this.paidCNY;
	}

	/**
	 * 属性本位币赔款金额的setter方法
	 */
	public void setPaidCNY(Double paidCNY) {
		this.paidCNY = paidCNY;
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
	 * 属性是否是代付赔款的getter方法
	 */

	@Column(name = "ISPAYFOROTHER")
	public String getIsPayForOther() {
		return this.isPayForOther;
	}

	/**
	 * 属性是否是代付赔款的setter方法
	 */
	public void setIsPayForOther(String isPayForOther) {
		this.isPayForOther = isPayForOther;
	}

	/**
	 * 属性银行帳号的getter方法
	 */

	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**
	 * 属性银行帳号的setter方法
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
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
	 * 属性客户银行代码的getter方法
	 */

	@Column(name = "CUSTOMBANKCODE")
	public String getCustomBankCode() {
		return this.customBankCode;
	}

	/**
	 * 属性客户银行代码的setter方法
	 */
	public void setCustomBankCode(String customBankCode) {
		this.customBankCode = customBankCode;
	}

	/**
	 * 属性客户银行名称的getter方法
	 */

	@Column(name = "CUSTOMBANKNAME")
	public String getCustomBankName() {
		return this.customBankName;
	}

	/**
	 * 属性客户银行名称的setter方法
	 */
	public void setCustomBankName(String customBankName) {
		this.customBankName = customBankName;
	}

	/**
	 * 属性帳户归属人证件代码的getter方法
	 */

	@Column(name = "CERTIFICATECODE")
	public String getCertifiCateCode() {
		return this.certifiCateCode;
	}

	/**
	 * 属性帳户归属人证件代码的setter方法
	 */
	public void setCertifiCateCode(String certifiCateCode) {
		this.certifiCateCode = certifiCateCode;
	}

	/**
	 * 属性帳户归属人名称的getter方法
	 */

	@Column(name = "OWNERNAME")
	public String getOwnerName() {
		return this.ownerName;
	}

	/**
	 * 属性帳户归属人名称的setter方法
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
	 * 属性帳户类型的getter方法
	 */

	@Column(name = "ACCOUNTTYPE")
	public String getAccountType() {
		return this.accountType;
	}

	/**
	 * 属性帳户类型的setter方法
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 属性帳户币别的getter方法
	 */

	@Column(name = "ACCOUNTCURRENCY")
	public String getAccountCurrency() {
		return this.accountCurrency;
	}

	/**
	 * 属性帳户币别的setter方法
	 */
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	/**
	 * 属性业务与帳户关系的getter方法
	 */

	@Column(name = "OWNERSHIP")
	public String getOwnership() {
		return this.ownership;
	}

	/**
	 * 属性业务与帳户关系的setter方法
	 */
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	/**
	 * 属性例外事项的getter方法
	 */

	@Column(name = "EXCEPTIONS")
	public String getExceptions() {
		return this.exceptions;
	}

	/**
	 * 属性例外事项的setter方法
	 */
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	/**
	 * 属性原因的getter方法
	 */

	@Column(name = "REASON")
	public String getReason() {
		return this.reason;
	}

	/**
	 * 获取属性操作状态
	 * @return 属性操作状态 Modify By Sunhao,2004-08-24
	 */
	@Transient
	public String getStatus() {
		return status;
	}

	/**
	 * 设置属性操作状态
	 * @param status 待设置的属性操作状态 Modify By Sunhao,2004-08-24
	 */
	public void setStatus(String status) {
		this.status = StringUtils.rightTrim(status);
	}

	/**
	 * 设置属性经办人代码
	 * @param handlerName 属性经办人代码
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
	 * 设置属性地址代码
	 * @param addressCode 待设置的属性地址代码的值
	 */
	public void setAddressCode(String addressCode) {
		this.addressCode = StringUtils.rightTrim(addressCode);
	}

	/**
	 * 获取属性地址代码
	 * @return 属性地址代码
	 */
	@Transient
	public String getAddressCode() {
		return addressCode;
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

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public Collection<PrpLprepay> getClaimList() {
		return claimList;
	}

	/**
	 * 设置列表
	 * @param registList 待设置的列表
	 */
	public void setClaimList(Collection<PrpLprepay> claimList) {
		this.claimList = claimList;
	}

	/**
	 * 设置属性条款类别
	 * @param clauseType 待设置的属性条款类别的值
	 */
	public void setClauseType(String clauseType) {
		this.clauseType = StringUtils.rightTrim(clauseType);
	}

	/**
	 * 获取属性条款类别
	 * @return 属性条款类别的值
	 */
	@Transient
	public String getClauseType() {
		return clauseType;
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
	 * 设置属性车牌底色代码
	 * @param licenseColorCode 待设置的属性车牌底色代码的值
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = StringUtils.rightTrim(licenseColorCode);
	}

	/**
	 * 获取属性车牌底色代码
	 * @return 属性车牌底色代码的值
	 */
	@Transient
	public String getLicenseColorCode() {
		return licenseColorCode;
	}

	/**
	 * 设置属性车牌底色代码
	 * @param licenseColorCode 待设置的属性车牌底色代码的值
	 */
	public void setLicenseColor(String licenseColor) {
		this.licenseColor = StringUtils.rightTrim(licenseColor);
	}

	/**
	 * 获取属性车牌底色代码
	 * @return 属性车牌底色代码的值
	 */
	@Transient
	public String getLicenseColor() {
		return licenseColor;
	}

	/**
	 * 设置属性车辆种类代码
	 * @param carKindCode 待设置的属性车辆种类代码的值
	 */
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = StringUtils.rightTrim(carKindCode);
	}

	/**
	 * 获取属性车辆种类代码
	 * @return 属性车辆种类代码的值
	 */
	@Transient
	public String getCarKindCode() {
		return carKindCode;
	}

	/**
	 * 设置属性车辆种类
	 * @param carKind待设置的属性车辆种类的值
	 */
	public void setCarKind(String carKind) {
		this.carKind = StringUtils.rightTrim(carKind);
	}

	/**
	 * 获取属性车辆种类
	 * @return 属性车辆种类值
	 */
	@Transient
	public String getCarKind() {
		return carKind;
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
	 * 设置属性厂牌型号
	 * @param brandName 待设置的属性厂牌型号的值
	 */
	public void setBrandName(String brandName) {
		this.brandName = StringUtils.rightTrim(brandName);
	}

	/**
	 * 获取属性厂牌型号
	 * @return 属性厂牌型号的值
	 */
	@Transient
	public String getBrandName() {
		return brandName;
	}

	/**
	 * 获取属性代理人名称
	 * @return 属性代理人名称的值
	 */
	@Transient
	public String getAgentName() {
		return agentName;
	}

	/**
	 * 获取代理人代码
	 * @return agentCode 代理人代码
	 */
	@Transient
	public String getAgentCode() {
		return agentCode;
	}

	/**
	 * 获取终保日期
	 * @return 终保日期
	 */
	@Transient
	public DateTime getEndDate() {
		return endDate;
	}

	/**
	 * 获取起保日期
	 * @return 起保日期
	 */
	@Transient
	public DateTime getStartDate() {
		return startDate;
	}

	/**
	 * 获取车架号
	 * @return 车架号
	 */
	@Transient
	public String getFrameNo() {
		return frameNo;
	}

	/**
	 * 获取发动机号
	 * @return 发动机号
	 */
	@Transient
	public String getEngineNo() {
		return engineNo;
	}

	/**
	 * 获取出险开始小时
	 * @return 出险开始小时
	 */
	@Transient
	public String getDamageStartHour() {
		return damageStartHour;
	}

	/**
	 * 获取出险日期起
	 * @return 出险日期起
	 */
	@Transient
	public DateTime getDamageStartDate() {
		return damageStartDate;
	}

	/**
	 * 获取出险地点分类代码
	 * @return 出险地点分类代码
	 */
	@Transient
	public String getDamageAddressType() {
		return damageAddressType;
	}

	/**
	 * 获取出险地点
	 * @return 出险地点
	 */
	@Transient
	public String getDamageAddress() {
		return damageAddress;
	}

	/**
	 * 获取总保费
	 * @return 总保费
	 */
	@Transient
	public double getSumPremium() {
		return sumPremium;
	}

	/**
	 * 获取总保额
	 * @return 总保额
	 */
	@Transient
	public double getSumAmount() {
		return sumAmount;
	}

	/**
	 * 获取保险损失金额
	 * @return 保险损失金额
	 */
	@Transient
	public double getSumClaim() {
		return sumClaim;
	}

	/**
	 * 设置属性代理人名称
	 * @param AgentName 待设置的属性代理人名称
	 */
	public void setAgentName(String agentName) {
		this.agentName = StringUtils.rightTrim(agentName);
	}

	/**
	 * 获取业务归属机构代码
	 * @return 业务归属机构代码
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	/**
	 * 获取终保日期
	 * @return endDate 终保日期
	 */
	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	/**
	 * 获取起保日期
	 * @return endDate 起保日期
	 */
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	/**
	 * 获取车架号
	 * @return frameNo 车架号
	 */
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	/**
	 * 获取发动机号
	 * @return engineNo 发动机号
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	/**
	 * 获取出险开始小时
	 * @return damageStartHour 出险开始小时
	 */
	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}

	/**
	 * 获取出险日期起
	 * @return damageStartDate 出险日期起
	 */
	public void setDamageStartDate(DateTime damageStartDate) {
		this.damageStartDate = damageStartDate;
	}

	/**
	 * 获取出险地点分类代码
	 * @return damageAddressType 出险地点分类代码
	 */
	public void setDamageAddressType(String damageAddressType) {
		this.damageAddressType = damageAddressType;
	}

	/**
	 * 获取出险地点分类代码
	 * @return damageAddress 出险地点分类代码
	 */
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}

	/**
	 * 获取总保费
	 * @return sumPremium 总保费
	 */
	public void setSumPremium(double sumPremium) {
		this.sumPremium = sumPremium;
	}

	/**
	 * 获取总保额
	 * @return sumAmount 总保额
	 */
	public void setSumAmount(double sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 获取保险损失金额
	 * @return sumClaim 保险损失金额
	 */
	public void setSumClaim(double sumClaim) {
		this.sumClaim = sumClaim;
	}

	/**
	 * 设置属性操作时间
	 * @param operateDate 待设置的属性操作时间 Modify By Sunhao,2004-08-24
	 */
	public void setOperateDate(DateTime operateDate) {
		this.operateDate = operateDate;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * 获取属性操作时间名称
	 * @param status 待设置的属性操作时间 Modify By Sunhao,2004-08-24
	 */
	@Transient
	public DateTime getOperateDate() {
		return operateDate;
	}

	@Transient
	public String getCurrencyName() {
		return currencyName;
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
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public Collection<PrpLregist> getRegistList() {
		return registList;
	}

	/**
	 * 设置列表
	 * @param registList 待设置的列表
	 */
	public void setRegistList(Collection<PrpLregist> registList) {
		this.registList = registList;
	}

	/**
	 * 设置属性险类代码
	 * @param classCode 待设置的属性险类代码的值
	 */
	public void setClassCode(String classCode) {
		this.classCode = StringUtils.rightTrim(classCode);
	}

	/**
	 * 获取属性险类代码
	 * @return 属性险类代码的值
	 */
	@Transient
	public String getClassCode() {
		return classCode;
	}

	@Transient
	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
