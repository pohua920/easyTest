package com.sinosoft.claim.schema.model;

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

/**
 * POJO类PrpLCMain
 */
@Entity
@Table(name = "PRPLCMAIN")
public class PrpLCMain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLCMainId id;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性险类代码 */
	private String classCode;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性业务来源 */
	private String businessNature;

	/** 属性投保人名称 */
	private String appliName;

	/** 属性投保人地址 */
	private String appliAddress;

	/** 属性被保险人名称 */
	private String insuredName;

	/** 属性被保险人地址 */
	private String insuredAddress;

	/** 属性签单日期 */
	private Date operateDate;

	/** 属性起保日期 */
	private Date startDate;

	/** 属性起保小时 */
	private BigDecimal startHour;

	/** 属性终保日期 */
	private Date endDate;

	/** 属性终保小时 */
	private BigDecimal endHour;

	/** 属性币别 */
	private String currency;

	/** 属性总保额 */
	private BigDecimal sumAmount;

	/** 属性总保费 */
	private BigDecimal sumPremium;

	/** 属性争议解决方式 */
	private String argueSolution;

	/** 属性仲裁委员会名称 */
	private String arbitBoardName;

	/** 属性约定分期交费次数 */
	private BigDecimal payTimes;

	/** 属性批改次数 */
	private BigDecimal endorseTimes;

	/** 属性出险次数 */
	private BigDecimal registTimes;

	/** 属性赔付次数 */
	private BigDecimal claimTimes;

	/** 属性赔付金额 */
	private BigDecimal sumClaim;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性公估师代码 */
	private String comCode;

	/** 属性处理人员代码 */
	private String handlerCode;

	/** 属性归属业务员代码 */
	private String handler1Code;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性计算机输单小时 */
	private BigDecimal inputHour;

	/** 属性标志字段 */
	private String flag;

	/**
	 * 类PrpLCMain的默认构造方法
	 */
	public PrpLCMain() {
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "policyNo", column = @Column(name = "POLICYNO")) })
	public PrpLCMainId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLCMainId id) {
		this.id = id;
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	public BigDecimal getStartHour() {
		return this.startHour;
	}

	/**
	 * 属性起保小时的setter方法
	 */
	public void setStartHour(BigDecimal startHour) {
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
	public BigDecimal getEndHour() {
		return this.endHour;
	}

	/**
	 * 属性终保小时的setter方法
	 */
	public void setEndHour(BigDecimal endHour) {
		this.endHour = endHour;
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
	 * 属性总保额的getter方法
	 */

	@Column(name = "SUMAMOUNT")
	public BigDecimal getSumAmount() {
		return this.sumAmount;
	}

	/**
	 * 属性总保额的setter方法
	 */
	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 属性总保费的getter方法
	 */

	@Column(name = "SUMPREMIUM")
	public BigDecimal getSumPremium() {
		return this.sumPremium;
	}

	/**
	 * 属性总保费的setter方法
	 */
	public void setSumPremium(BigDecimal sumPremium) {
		this.sumPremium = sumPremium;
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
	public BigDecimal getPayTimes() {
		return this.payTimes;
	}

	/**
	 * 属性约定分期交费次数的setter方法
	 */
	public void setPayTimes(BigDecimal payTimes) {
		this.payTimes = payTimes;
	}

	/**
	 * 属性批改次数的getter方法
	 */

	@Column(name = "ENDORSETIMES")
	public BigDecimal getEndorseTimes() {
		return this.endorseTimes;
	}

	/**
	 * 属性批改次数的setter方法
	 */
	public void setEndorseTimes(BigDecimal endorseTimes) {
		this.endorseTimes = endorseTimes;
	}

	/**
	 * 属性出险次数的getter方法
	 */

	@Column(name = "REGISTTIMES")
	public BigDecimal getRegistTimes() {
		return this.registTimes;
	}

	/**
	 * 属性出险次数的setter方法
	 */
	public void setRegistTimes(BigDecimal registTimes) {
		this.registTimes = registTimes;
	}

	/**
	 * 属性赔付次数的getter方法
	 */

	@Column(name = "CLAIMTIMES")
	public BigDecimal getClaimTimes() {
		return this.claimTimes;
	}

	/**
	 * 属性赔付次数的setter方法
	 */
	public void setClaimTimes(BigDecimal claimTimes) {
		this.claimTimes = claimTimes;
	}

	/**
	 * 属性赔付金额的getter方法
	 */

	@Column(name = "SUMCLAIM")
	public BigDecimal getSumClaim() {
		return this.sumClaim;
	}

	/**
	 * 属性赔付金额的setter方法
	 */
	public void setSumClaim(BigDecimal sumClaim) {
		this.sumClaim = sumClaim;
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
	 * 属性公估师代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性公估师代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性处理人员代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性处理人员代码的setter方法
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
	public BigDecimal getInputHour() {
		return this.inputHour;
	}

	/**
	 * 属性计算机输单小时的setter方法
	 */
	public void setInputHour(BigDecimal inputHour) {
		this.inputHour = inputHour;
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

}
