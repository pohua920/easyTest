package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.Collection;
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
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLclaimLoss立案险别估损金额
 */
@Entity
@Table(name = "PRPLCLAIMLOSS")
public class PrpLclaimLoss implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLclaimLossId id;

	/** 属性立案基本信息表 */
	private PrpLclaim prpLclaim;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性标的子险序号 */
	private Integer itemKindNo;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性保单标的项目代码 */
	private String itemCode;

	/** 属性币别代码 */
	private String currency;

	/** 属性保险损失金额 */
	private Double sumClaim = 0.0;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性备注 */
	private String remarkFlag;

	/** 属性状态字段 */
	private String flag;

	/** 属性要能分别出赔款还是直接理赔费用 */
	private String lossFeeType;

	/** 属性险别损失 */
	private Double kindLoss;

	/** 属性险别残值 */
	private Double kindRest;

	/** 属性免赔率 */
	private Double deductibleRate;

	/** 属性绝对免赔额 */
	private Double deductible;

	/** 属性事故责任免赔率 */
	private Double acciDeductibleRate;

	/** 属性费用范围 */
	private String feeCategory;

	/** 属性标的项目明细名称 */
	private String itemDetailName;

	/** 属性REPORTSUMCLAIM */
	private Double reportSumClaim;

	/** 属性危险单位序号 */
	private Integer dangerNo;

	/** 属性事故责任免赔额 */
	private Double acciDeductiblePay;

	/** 属性车险不计免赔额特约(M)对应的险别 */
	private String kindCodeSub;

	private Collection<PrpLclaimLoss> claimLossList;

	/** 属性currencyName */
	private String currencyName = "";
	/** 属性kindName */
	private String kindName = "";
	/** 属性责任名称 add by qinyongli 2005-9-2 */
	private String itemKindName = "";
	/** 属性车险不计免赔额特约(M)对应的险别名称 */
	private String KindNameSub = "";
	/** 保额 */
	private Double amount = 0D;
	/** 报案号码 */
	private String registNo = "";
	/** 属性经办人代码 */
	private String handlerCode = "";
	/** 属性经办人代码 */
	private String handlerName = "";
	//delete by chenjie 20150601 需求變更-095 begin
//	/** 肇事類型 肇事类型：1:有肇责，计次\2:无肇责，不计次\3:有肇责，不计次 */
//	private String accidentType = "1";
	//delete by chenjie 20150601 需求變更-095 end
	/*  估損訊息來源 1:立案,2:調整估損  */
	private String datafrom;
	
	/**
	 * 类PrpLclaimLoss的默认构造方法
	 */
	public PrpLclaimLoss() {
		id = new PrpLclaimLossId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "claimNo", column = @Column(name = "CLAIMNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLclaimLossId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLclaimLossId id) {
		this.id = id;
	}

	/**
	 * 属性立案基本信息表的getter方法
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMNO", nullable = false, insertable = false, updatable = false)
	public PrpLclaim getPrpLclaim() {
		return this.prpLclaim;
	}

	/**
	 * 属性立案基本信息表的setter方法
	 */
	public void setPrpLclaim(PrpLclaim prpLclaim) {
		this.prpLclaim = prpLclaim;
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
	 * 属性标的子险序号的getter方法
	 */

	@Column(name = "ITEMKINDNO")
	public Integer getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性标的子险序号的setter方法
	 */
	public void setItemKindNo(Integer itemKindNo) {
		this.itemKindNo = itemKindNo;
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
	 * 属性保单标的项目代码的getter方法
	 */

	@Column(name = "ITEMCODE")
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 属性保单标的项目代码的setter方法
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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
	 * 属性保险损失金额的getter方法
	 */

	@Column(name = "SUMCLAIM")
	public Double getSumClaim() {
		return this.sumClaim;
	}

	/**
	 * 属性保险损失金额的setter方法
	 */
	public void setSumClaim(Double sumClaim) {
		this.sumClaim = sumClaim;
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
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARKFLAG")
	public String getRemarkFlag() {
		return this.remarkFlag;
	}

	/**
	 * 属性备注的setter方法
	 */
	public void setRemarkFlag(String remarkFlag) {
		this.remarkFlag = remarkFlag;
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
	 * 属性要能分别出赔款还是直接理赔费用的getter方法
	 */

	@Column(name = "LOSSFEETYPE")
	public String getLossFeeType() {
		return this.lossFeeType;
	}

	/**
	 * 属性要能分别出赔款还是直接理赔费用的setter方法
	 */
	public void setLossFeeType(String lossFeeType) {
		this.lossFeeType = lossFeeType;
	}

	/**
	 * 属性险别损失的getter方法
	 */

	@Column(name = "KINDLOSS")
	public Double getKindLoss() {
		return this.kindLoss;
	}

	/**
	 * 属性险别损失的setter方法
	 */
	public void setKindLoss(Double kindLoss) {
		this.kindLoss = kindLoss;
	}

	/**
	 * 属性险别残值的getter方法
	 */

	@Column(name = "KINDREST")
	public Double getKindRest() {
		return this.kindRest;
	}

	/**
	 * 属性险别残值的setter方法
	 */
	public void setKindRest(Double kindRest) {
		this.kindRest = kindRest;
	}

	/**
	 * 属性免赔率的getter方法
	 */

	@Column(name = "DEDUCTIBLERATE")
	public Double getDeductibleRate() {
		return this.deductibleRate;
	}

	/**
	 * 属性免赔率的setter方法
	 */
	public void setDeductibleRate(Double deductibleRate) {
		this.deductibleRate = deductibleRate;
	}

	/**
	 * 属性绝对免赔额的getter方法
	 */

	@Column(name = "DEDUCTIBLE")
	public Double getDeductible() {
		return this.deductible;
	}

	/**
	 * 属性绝对免赔额的setter方法
	 */
	public void setDeductible(Double deductible) {
		this.deductible = deductible;
	}

	/**
	 * 属性事故责任免赔率的getter方法
	 */

	@Column(name = "ACCIDEDUCTIBLERATE")
	public Double getAcciDeductibleRate() {
		return this.acciDeductibleRate;
	}

	/**
	 * 属性事故责任免赔率的setter方法
	 */
	public void setAcciDeductibleRate(Double acciDeductibleRate) {
		this.acciDeductibleRate = acciDeductibleRate;
	}

	/**
	 * 属性费用范围的getter方法
	 */

	@Column(name = "FEECATEGORY")
	public String getFeeCategory() {
		return this.feeCategory;
	}

	/**
	 * 属性费用范围的setter方法
	 */
	public void setFeeCategory(String feeCategory) {
		this.feeCategory = feeCategory;
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
	 * 属性REPORTSUMCLAIM的getter方法
	 */

	@Column(name = "REPORTSUMCLAIM")
	public Double getReportSumClaim() {
		return this.reportSumClaim;
	}

	/**
	 * 属性REPORTSUMCLAIM的setter方法
	 */
	public void setReportSumClaim(Double reportSumClaim) {
		this.reportSumClaim = reportSumClaim;
	}

	/**
	 * 属性危险单位序号的getter方法
	 */

	@Column(name = "DANGERNO")
	public Integer getDangerNo() {
		return this.dangerNo;
	}

	/**
	 * 属性危险单位序号的setter方法
	 */
	public void setDangerNo(Integer dangerNo) {
		this.dangerNo = dangerNo;
	}

	/**
	 * 属性事故责任免赔额的getter方法
	 */

	@Column(name = "ACCIDEDUCTIBLEPAY")
	public Double getAcciDeductiblePay() {
		return this.acciDeductiblePay;
	}

	/**
	 * 属性事故责任免赔额的setter方法
	 */
	public void setAcciDeductiblePay(Double acciDeductiblePay) {
		this.acciDeductiblePay = acciDeductiblePay;
	}

	/**
	 * 属性车险不计免赔额特约(M)对应的险别的getter方法
	 */

	@Column(name = "KINDCODESUB")
	public String getKindCodeSub() {
		return this.kindCodeSub;
	}

	/**
	 * 属性车险不计免赔额特约(M)对应的险别的setter方法
	 */
	public void setKindCodeSub(String kindCodeSub) {
		this.kindCodeSub = kindCodeSub;
	}

	/**
	 * 设置属性币别名称
	 * @param currencyName 待设置的属性币别名称的值
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = StringUtils.rightTrim(currencyName);
	}

	/**
	 * 获取属性币别名称
	 * @return 属性币别名称的值
	 */
	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	/**
	 * 设置属险别别名称
	 * @param currencyName 待设置的属险别别名称的值
	 */
	public void setKindName(String kindName) {
		this.kindName = StringUtils.rightTrim(kindName);
	}

	/**
	 * 获取属险别别名称
	 * @return 属险别别名称的值
	 */
	@Transient
	public String getKindName() {
		return kindName;
	}

	/**
	 * 获取属性责任名称
	 * @return 属性责任名称
	 */
	@Transient
	public String getItemKindName() {
		return itemKindName;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public Collection<PrpLclaimLoss> getClaimLossList() {
		return claimLossList;
	}

	/**
	 * 获取属性车险不计免赔额特约(M)对应的险别名称
	 * @return 属性车险不计免赔额特约(M)对应的险别名称
	 */
	@Transient
	public String getKindNameSub() {
		return KindNameSub;
	}

	/**
	 * 设置属性车险不计免赔额特约(M)对应的险别名称
	 * @param kindNameSub 待设置的属性车险不计免赔额特约(M)对应的险别名称
	 */
	public void setKindNameSub(String kindNameSub) {
		KindNameSub = StringUtils.rightTrim(kindNameSub);
	}

	/**
	 * 设置列表
	 * @param driverList 待设置的列表
	 */
	public void setClaimLossList(Collection<PrpLclaimLoss> claimLossList) {
		this.claimLossList = claimLossList;
	}

	/**
	 * 设置属性责任名称
	 * @param currencyName 待设置的属性责任名称
	 */
	public void setItemKindName(String itemKindName) {
		this.itemKindName = StringUtils.rightTrim(itemKindName);
	}

	/**
	 * 设置属性事故责任免赔额
	 * @param acciDeductiblePay 待设置的属性事故责任免赔额
	 */
	public void setAcciDeductiblePay(double acciDeductiblePay) {
		this.acciDeductiblePay = acciDeductiblePay;
	}
	@Column(name = "amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column(name = "registNo")
	public String getRegistNo() {
		return registNo;
	}

	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}
	@Column(name = "handlerCode")
	public String getHandlerCode() {
		return handlerCode;
	}

	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}
	@Transient
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
	
	@Column(name = "datafrom")
	public String getDatafrom() {
		if (datafrom == null || datafrom.trim().length() == 0) {
			datafrom = "1";
		}
		return datafrom;
	}

	public void setDatafrom(String datafrom) {
		this.datafrom = datafrom;
	}
	
	
	//delete by chenjie 20150601 需求變更-095 begin
//	@Column(name = "ACCIDENTTYPE")
//	public String getAccidentType() {
//		return accidentType;
//	}
//
//	public void setAccidentType(String accidentType) {
//		this.accidentType = accidentType;
//	}
	//delete by chenjie 20150601 需求變更-095 end
}
