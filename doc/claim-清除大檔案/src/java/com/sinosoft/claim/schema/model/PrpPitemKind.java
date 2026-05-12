package com.sinosoft.claim.schema.model;

// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

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
 * POJO类PrpPitemKind
 */
@Entity
@Table(name = "PRPPITEMKIND")
public class PrpPitemKind implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpPitemKindId id;

	/** 属性批改信息表 */
	private PrpPhead prpPhead;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性分户序号 */
	private Integer familyNo;

	/** 属性RATIONTYPE */
	private String rationType;

	/** 属性分户名称 */
	private String familyName;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性险别名称 */
	private String kindName;

	/** 属性标的序号 */
	private Integer itemNo;

	/** 属性标的项目类别代码 */
	private String itemCode;

	/** 属性标的项目明细名称 */
	private String itemDetailName;

	/** 属性投保方式代码 */
	private String modeCode;

	/** 属性投保方式名称 */
	private String modeName;

	/** 属性起保日期 */
	private Date startDate;

	/** 属性起保小时 */
	private Integer startHour;

	/** 属性终保日期 */
	private Date endDate;

	/** 属性终保小时 */
	private Integer endHour;

	/** 属性规格型号 */
	private String model;

	/** 属性购买日期 */
	private Date buyDate;

	/** 属性地址序号 */
	private Integer addressNo;

	/** 属性是否计算保额标志 */
	private String calculateFlag;

	/** 属性币别 */
	private String currency;

	/** 属性单位保险金额 */
	private Double unitAmount;

	/** 属性数量 */
	private Double quantity;

	/** 属性数量单位 */
	private String unit;

	/** 属性保险价值 */
	private Double value;

	/** 属性保单批改前原币总保额 */
	private Double amount;

	/** 属性适应费率期数 */
	private Integer ratePeriod;

	/** 属性费率 */
	private Double rate;

	/** 属性短期费率标志 */
	private String shortRateFlag;

	/** 属性短期费率 */
	private Double shortRate;

	/** 属性基准保费 */
	private Double basePremium;

	/** 属性标准保费 */
	private Double benchMarkPremium;

	/** 属性折扣率 */
	private Double discount;

	/** 属性保费调整比率（%） */
	private Double adjustRate;

	/** 属性保单批改前原币总保费 */
	private Double premium;

	/** 属性免赔率（%） */
	private Double deductibleRate;

	/** 属性免赔额 */
	private Double deductible;

	/** 属性标志字段 */
	private String flag;

	/** 属性数量变化量 */
	private Double chgQuantity;

	/** 属性保额变化量 */
	private Double chgAmount;

	/** 属性保费变化量 */
	private Double chgPremium;

	/** 属性PROFITSCALE */
	private Double profitScale;

	/** 属性批改前打印币别 */
	private String currency2;

	/** 属性原币和打印币别兑换率 */
	private Double exchangeRate2;

	/** 属性改前折合打印币别总保费 */
	private Double premium2;

	/** 属性签单币别与人民币的兑换率 */
	private Double exchangeRatecny;

	/** 属性人民币币别下的保费 */
	private Double premiumcny;

	/** 属性折合打印币别总保费变化量 */
	private Double chgPremium2;

	/** 属性人民币别（本位币）保费变化量 */
	private Double chgpremiumcny;

	/** 属性交强险即时生效 开始日期 */
	private Date newStartDate;

	/** 属性交强险即时生效 结束日期 */
	private Date newEndDate;

	/** 属性保险价值项类型 */
	private String insuredValueType;

	/** 属性insuredvaluetypename */
	private String insuredvaluetypename;

	/** 属性STORAGERATE */
	private Double storageRate;
	
	/** 每一個人體傷或死亡 */
	private Double perHumanInjury;
	/** 每一事故體傷或死亡 */
	private Double perAccidentDeaths;
	/** 每一事故財產損失 */
	private Double perAccidentDamage;
	/** 每一事故最高責任 */
	private Double perHumanDeath;
	/** 保險期間內最高責任 */
	private Double periodMaxAmount;

	/**
	 * 类PrpPitemKind的默认构造方法
	 */
	public PrpPitemKind() {
		id = new PrpPitemKindId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "endorseNo", column = @Column(name = "ENDORSENO")), @AttributeOverride(name = "itemKindNo", column = @Column(name = "ITEMKINDNO")) })
	public PrpPitemKindId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpPitemKindId id) {
		this.id = id;
	}

	/**
	 * 属性批改信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENDORSENO", nullable = false, insertable = false, updatable = false)
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
	 * 属性分户序号的getter方法
	 */

	@Column(name = "FAMILYNO")
	public Integer getFamilyNo() {
		return this.familyNo;
	}

	/**
	 * 属性分户序号的setter方法
	 */
	public void setFamilyNo(Integer familyNo) {
		this.familyNo = familyNo;
	}

	/**
	 * 属性RATIONTYPE的getter方法
	 */

	@Column(name = "RATIONTYPE")
	public String getRationType() {
		return this.rationType;
	}

	/**
	 * 属性RATIONTYPE的setter方法
	 */
	public void setRationType(String rationType) {
		this.rationType = rationType;
	}

	/**
	 * 属性分户名称的getter方法
	 */

	@Column(name = "FAMILYNAME")
	public String getFamilyName() {
		return this.familyName;
	}

	/**
	 * 属性分户名称的setter方法
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * 属性险别代码的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别代码的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * 属性险别名称的getter方法
	 */

	@Column(name = "KINDNAME")
	public String getKindName() {
		return this.kindName;
	}

	/**
	 * 属性险别名称的setter方法
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	/**
	 * 属性标的序号的getter方法
	 */

	@Column(name = "ITEMNO")
	public Integer getItemNo() {
		return this.itemNo;
	}

	/**
	 * 属性标的序号的setter方法
	 */
	public void setItemNo(Integer itemNo) {
		this.itemNo = itemNo;
	}

	/**
	 * 属性标的项目类别代码的getter方法
	 */

	@Column(name = "ITEMCODE")
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 属性标的项目类别代码的setter方法
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 属性标的项目明细名称的getter方法
	 */

	@Column(name = "ITEMDETAILNAME")
	public String getItemDetailName() {
		return this.itemDetailName;
	}

	/**
	 * 属性标的项目明细名称的setter方法
	 */
	public void setItemDetailName(String itemDetailName) {
		this.itemDetailName = itemDetailName;
	}

	/**
	 * 属性投保方式代码的getter方法
	 */

	@Column(name = "MODECODE")
	public String getModeCode() {
		return this.modeCode;
	}

	/**
	 * 属性投保方式代码的setter方法
	 */
	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	/**
	 * 属性投保方式名称的getter方法
	 */

	@Column(name = "MODENAME")
	public String getModeName() {
		return this.modeName;
	}

	/**
	 * 属性投保方式名称的setter方法
	 */
	public void setModeName(String modeName) {
		this.modeName = modeName;
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
	 * 属性规格型号的getter方法
	 */

	@Column(name = "MODEL")
	public String getModel() {
		return this.model;
	}

	/**
	 * 属性规格型号的setter方法
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 属性购买日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "BUYDATE")
	public Date getBuyDate() {
		return this.buyDate;
	}

	/**
	 * 属性购买日期的setter方法
	 */
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	/**
	 * 属性地址序号的getter方法
	 */

	@Column(name = "ADDRESSNO")
	public Integer getAddressNo() {
		return this.addressNo;
	}

	/**
	 * 属性地址序号的setter方法
	 */
	public void setAddressNo(Integer addressNo) {
		this.addressNo = addressNo;
	}

	/**
	 * 属性是否计算保额标志的getter方法
	 */

	@Column(name = "CALCULATEFLAG")
	public String getCalculateFlag() {
		return this.calculateFlag;
	}

	/**
	 * 属性是否计算保额标志的setter方法
	 */
	public void setCalculateFlag(String calculateFlag) {
		this.calculateFlag = calculateFlag;
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
	 * 属性单位保险金额的getter方法
	 */

	@Column(name = "UNITAMOUNT")
	public Double getUnitAmount() {
		return this.unitAmount;
	}

	/**
	 * 属性单位保险金额的setter方法
	 */
	public void setUnitAmount(Double unitAmount) {
		this.unitAmount = unitAmount;
	}

	/**
	 * 属性数量的getter方法
	 */

	@Column(name = "QUANTITY")
	public Double getQuantity() {
		return this.quantity;
	}

	/**
	 * 属性数量的setter方法
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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
	 * 属性保险价值的getter方法
	 */

	@Column(name = "VALUE")
	public Double getValue() {
		return this.value;
	}

	/**
	 * 属性保险价值的setter方法
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * 属性保单批改前原币总保额的getter方法
	 */

	@Column(name = "AMOUNT")
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * 属性保单批改前原币总保额的setter方法
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 属性适应费率期数的getter方法
	 */

	@Column(name = "RATEPERIOD")
	public Integer getRatePeriod() {
		return this.ratePeriod;
	}

	/**
	 * 属性适应费率期数的setter方法
	 */
	public void setRatePeriod(Integer ratePeriod) {
		this.ratePeriod = ratePeriod;
	}

	/**
	 * 属性费率的getter方法
	 */

	@Column(name = "RATE")
	public Double getRate() {
		return this.rate;
	}

	/**
	 * 属性费率的setter方法
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * 属性短期费率标志的getter方法
	 */

	@Column(name = "SHORTRATEFLAG")
	public String getShortRateFlag() {
		return this.shortRateFlag;
	}

	/**
	 * 属性短期费率标志的setter方法
	 */
	public void setShortRateFlag(String shortRateFlag) {
		this.shortRateFlag = shortRateFlag;
	}

	/**
	 * 属性短期费率的getter方法
	 */

	@Column(name = "SHORTRATE")
	public Double getShortRate() {
		return this.shortRate;
	}

	/**
	 * 属性短期费率的setter方法
	 */
	public void setShortRate(Double shortRate) {
		this.shortRate = shortRate;
	}

	/**
	 * 属性基准保费的getter方法
	 */

	@Column(name = "BASEPREMIUM")
	public Double getBasePremium() {
		return this.basePremium;
	}

	/**
	 * 属性基准保费的setter方法
	 */
	public void setBasePremium(Double basePremium) {
		this.basePremium = basePremium;
	}

	/**
	 * 属性标准保费的getter方法
	 */

	@Column(name = "BENCHMARKPREMIUM")
	public Double getBenchMarkPremium() {
		return this.benchMarkPremium;
	}

	/**
	 * 属性标准保费的setter方法
	 */
	public void setBenchMarkPremium(Double benchMarkPremium) {
		this.benchMarkPremium = benchMarkPremium;
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
	 * 属性保费调整比率（%）的getter方法
	 */

	@Column(name = "ADJUSTRATE")
	public Double getAdjustRate() {
		return this.adjustRate;
	}

	/**
	 * 属性保费调整比率（%）的setter方法
	 */
	public void setAdjustRate(Double adjustRate) {
		this.adjustRate = adjustRate;
	}

	/**
	 * 属性保单批改前原币总保费的getter方法
	 */

	@Column(name = "PREMIUM")
	public Double getPremium() {
		return this.premium;
	}

	/**
	 * 属性保单批改前原币总保费的setter方法
	 */
	public void setPremium(Double premium) {
		this.premium = premium;
	}

	/**
	 * 属性免赔率（%）的getter方法
	 */

	@Column(name = "DEDUCTIBLERATE")
	public Double getDeductibleRate() {
		return this.deductibleRate;
	}

	/**
	 * 属性免赔率（%）的setter方法
	 */
	public void setDeductibleRate(Double deductibleRate) {
		this.deductibleRate = deductibleRate;
	}

	/**
	 * 属性免赔额的getter方法
	 */

	@Column(name = "DEDUCTIBLE")
	public Double getDeductible() {
		return this.deductible;
	}

	/**
	 * 属性免赔额的setter方法
	 */
	public void setDeductible(Double deductible) {
		this.deductible = deductible;
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
	 * 属性数量变化量的getter方法
	 */

	@Column(name = "CHGQUANTITY")
	public Double getChgQuantity() {
		return this.chgQuantity;
	}

	/**
	 * 属性数量变化量的setter方法
	 */
	public void setChgQuantity(Double chgQuantity) {
		this.chgQuantity = chgQuantity;
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
	 * 属性PROFITSCALE的getter方法
	 */

	@Column(name = "PROFITSCALE")
	public Double getProfitScale() {
		return this.profitScale;
	}

	/**
	 * 属性PROFITSCALE的setter方法
	 */
	public void setProfitScale(Double profitScale) {
		this.profitScale = profitScale;
	}

	/**
	 * 属性批改前打印币别的getter方法
	 */

	@Column(name = "CURRENCY2")
	public String getCurrency2() {
		return this.currency2;
	}

	/**
	 * 属性批改前打印币别的setter方法
	 */
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}

	/**
	 * 属性原币和打印币别兑换率的getter方法
	 */

	@Column(name = "EXCHANGERATE2")
	public Double getExchangeRate2() {
		return this.exchangeRate2;
	}

	/**
	 * 属性原币和打印币别兑换率的setter方法
	 */
	public void setExchangeRate2(Double exchangeRate2) {
		this.exchangeRate2 = exchangeRate2;
	}

	/**
	 * 属性改前折合打印币别总保费的getter方法
	 */

	@Column(name = "PREMIUM2")
	public Double getPremium2() {
		return this.premium2;
	}

	/**
	 * 属性改前折合打印币别总保费的setter方法
	 */
	public void setPremium2(Double premium2) {
		this.premium2 = premium2;
	}

	/**
	 * 属性签单币别与人民币的兑换率的getter方法
	 */

	@Column(name = "EXCHANGERATECNY")
	public Double getExchangeRatecny() {
		return this.exchangeRatecny;
	}

	/**
	 * 属性签单币别与人民币的兑换率的setter方法
	 */
	public void setExchangeRatecny(Double exchangeRatecny) {
		this.exchangeRatecny = exchangeRatecny;
	}

	/**
	 * 属性人民币币别下的保费的getter方法
	 */

	@Column(name = "PREMIUMCNY")
	public Double getPremiumcny() {
		return this.premiumcny;
	}

	/**
	 * 属性人民币币别下的保费的setter方法
	 */
	public void setPremiumcny(Double premiumcny) {
		this.premiumcny = premiumcny;
	}

	/**
	 * 属性折合打印币别总保费变化量的getter方法
	 */

	@Column(name = "CHGPREMIUM2")
	public Double getChgPremium2() {
		return this.chgPremium2;
	}

	/**
	 * 属性折合打印币别总保费变化量的setter方法
	 */
	public void setChgPremium2(Double chgPremium2) {
		this.chgPremium2 = chgPremium2;
	}

	/**
	 * 属性人民币别（本位币）保费变化量的getter方法
	 */

	@Column(name = "CHGPREMIUMCNY")
	public Double getChgpremiumcny() {
		return this.chgpremiumcny;
	}

	/**
	 * 属性人民币别（本位币）保费变化量的setter方法
	 */
	public void setChgpremiumcny(Double chgpremiumcny) {
		this.chgpremiumcny = chgpremiumcny;
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
	 * 属性保险价值项类型的getter方法
	 */

	@Column(name = "INSUREDVALUETYPE")
	public String getInsuredValueType() {
		return this.insuredValueType;
	}

	/**
	 * 属性保险价值项类型的setter方法
	 */
	public void setInsuredValueType(String insuredValueType) {
		this.insuredValueType = insuredValueType;
	}

	/**
	 * 属性insuredvaluetypename的getter方法
	 */

	@Column(name = "INSUREDVALUETYPENAME")
	public String getInsuredvaluetypename() {
		return this.insuredvaluetypename;
	}

	/**
	 * 属性insuredvaluetypename的setter方法
	 */
	public void setInsuredvaluetypename(String insuredvaluetypename) {
		this.insuredvaluetypename = insuredvaluetypename;
	}

	/**
	 * 属性STORAGERATE的getter方法
	 */

	@Column(name = "STORAGERATE")
	public Double getStorageRate() {
		return this.storageRate;
	}

	/**
	 * 属性STORAGERATE的setter方法
	 */
	public void setStorageRate(Double storageRate) {
		this.storageRate = storageRate;
	}
	
	@Column(name = "perHumanInjury")
	public Double getPerHumanInjury() {
		if(perHumanInjury==null) {
			perHumanInjury = 0d;
		}
		return perHumanInjury;
	}

	public void setPerHumanInjury(Double perHumanInjury) {
		this.perHumanInjury = perHumanInjury;
	}
	
	@Column(name = "perAccidentDeaths")
	public Double getPerAccidentDeaths() {
		if(perAccidentDeaths==null) {
			perAccidentDeaths = 0d;
		}
		return perAccidentDeaths;
	}

	public void setPerAccidentDeaths(Double perAccidentDeaths) {
		this.perAccidentDeaths = perAccidentDeaths;
	}
	
	@Column(name = "perAccidentDamage")
	public Double getPerAccidentDamage() {
		if(perAccidentDamage==null) {
			perAccidentDamage = 0d;
		}
		return perAccidentDamage;
	}

	public void setPerAccidentDamage(Double perAccidentDamage) {
		this.perAccidentDamage = perAccidentDamage;
	}
	
	@Column(name = "perHumanDeath")
	public Double getPerHumanDeath() {
		if(perHumanDeath==null) {
			perHumanDeath = 0d;
		}
		return perHumanDeath;
	}

	public void setPerHumanDeath(Double perHumanDeath) {
		this.perHumanDeath = perHumanDeath;
	}

	@Column(name = "periodMaxAmount")
	public Double getPeriodMaxAmount() {
		if(periodMaxAmount==null) {
			periodMaxAmount = 0d;
		}
		return periodMaxAmount;
	}

	public void setPeriodMaxAmount(Double periodMaxAmount) {
		this.periodMaxAmount = periodMaxAmount;
	}

}
