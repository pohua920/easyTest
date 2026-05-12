package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * POJO类PrpLcarLoss
 */
@Entity
@Table(name = "PRPLCARLOSS")
public class PrpLcarLoss implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcarLossId id;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险种 */
	private String riskCode;

	/** 属性车牌号码 */
	private String lossItemName;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性币别 */
	private String currency;

	/** 属性剔除金额/残值/损余 */
	private Double sumRest = 0.00;

	/** 属性管理费 */
	private Double sumManager = 0.00;

	/** 属性总定损金额 */
	private Double sumCertainLoss = 0.00;

	/** 属性剔除金额/残值/损余（核损） */
	private Double sumVeriRest = 0.00;

	/** 属性管理费（核损） */
	private Double sumVeriManager = 0.00;

	/** 属性总核损金额 */
	private Double sumVerifyLoss = 0.00;

	/** 属性损失部位及程度概述 */
	private String lossDesc;

	/** 属性INDEMNITYDUTY */
	private String indemnityDuty;

	/** 属性责任比例 */
	private double indemnityDutyRate = 0;

	/** 属性责任比例(核损) */
	private double veriIndeDutyRate = 0;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性事故类型 */
	private String caseFlag;

	/** 属性标志 */
	private String flag;

	/** 属性VIN号码 */
	private String VINNo;

	/** 属性管理费率 */
	private double sumManageFeeRate = 0;

	/** 属性回勘意见 */
	private String backCheckRemark;

	/** 属性是否需要回勘 */
	private String backCheckFlag;

	/** 属性可操作/处理的级别 */
	private String handlerRange;

	/** 属性SUMTRANSFEE */
	private double sumTransFee = 0.00;

	/** 属性SUMTAX */
	private double sumTax = 0.00;

	/** 属性增加浮動比例 */
	private Double sumFloatRate = 0.00;

	/** 属性车牌底色代码 */
	private String licenseColorCode = "";
	/** 属性号牌种类代码 */
	private String carKindCode = "";
	/** 属性号牌种类名称 */
	private String carKindName = "";
	/** 车型代码 */
	private String modelCode = "";
	/** 属性厂牌型号 */
	private String brandName = "";
	/** 属性发动机号 */
	private String engineNo = "";
	/** 属性车架号 */
	private String frameNo = "";
	/** 属性是否为本保单车辆 */
	private String insureCarFlag = "";
	/** 属性是否为本保单车辆 */
	private String insureCarFlagName = "";
	/** 属性承保公司代码 */
	private String insureComCode = "";
	/** 属性承保公司名称 */
	private String insureComName = "";

	/**
	 * 类PrpLcarLoss的默认构造方法
	 */
	public PrpLcarLoss() {
		this.id = new PrpLcarLossId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "lossItemCode", column = @Column(name = "LOSSITEMCODE")) })
	public PrpLcarLossId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcarLossId id) {
		this.id = id;
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
	 * 属性车牌号码的getter方法
	 */

	@Column(name = "LOSSITEMNAME")
	public String getLossItemName() {
		return this.lossItemName;
	}

	/**
	 * 属性车牌号码的setter方法
	 */
	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
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
	 * 属性剔除金额/残值/损余的getter方法
	 */

	@Column(name = "SUMREST")
	public double getSumRest() {
		return this.sumRest;
	}

	/**
	 * 属性剔除金额/残值/损余的setter方法
	 */
	public void setSumRest(double sumRest) {
		this.sumRest = sumRest;
	}

	/**
	 * 属性管理费的getter方法
	 */

	@Column(name = "SUMMANAGER")
	public double getSumManager() {
		return this.sumManager;
	}

	/**
	 * 属性管理费的setter方法
	 */
	public void setSumManager(double sumManager) {
		this.sumManager = sumManager;
	}

	/**
	 * 属性总定损金额的getter方法
	 */

	@Column(name = "SUMCERTAINLOSS")
	public double getSumCertainLoss() {
		return this.sumCertainLoss;
	}

	/**
	 * 属性总定损金额的setter方法
	 */
	public void setSumCertainLoss(double sumCertainLoss) {
		this.sumCertainLoss = sumCertainLoss;
	}

	/**
	 * 属性剔除金额/残值/损余（核损）的getter方法
	 */

	@Column(name = "SUMVERIREST")
	public double getSumVeriRest() {
		return this.sumVeriRest;
	}

	/**
	 * 属性剔除金额/残值/损余（核损）的setter方法
	 */
	public void setSumVeriRest(double sumVeriRest) {
		this.sumVeriRest = sumVeriRest;
	}

	/**
	 * 属性管理费（核损）的getter方法
	 */

	@Column(name = "SUMVERIMANAGER")
	public double getSumVeriManager() {
		return this.sumVeriManager;
	}

	/**
	 * 属性管理费（核损）的setter方法
	 */
	public void setSumVeriManager(double sumVeriManager) {
		this.sumVeriManager = sumVeriManager;
	}

	/**
	 * 属性总核损金额的getter方法
	 */

	@Column(name = "SUMVERIFYLOSS")
	public double getSumVerifyLoss() {
		return this.sumVerifyLoss;
	}

	/**
	 * 属性总核损金额的setter方法
	 */
	public void setSumVerifyLoss(double sumVerifyLoss) {
		this.sumVerifyLoss = sumVerifyLoss;
	}

	/**
	 * 属性损失部位及程度概述的getter方法
	 */

	@Column(name = "LOSSDESC")
	public String getLossDesc() {
		return this.lossDesc;
	}

	/**
	 * 属性损失部位及程度概述的setter方法
	 */
	public void setLossDesc(String lossDesc) {
		this.lossDesc = lossDesc;
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
	 * 属性责任比例的getter方法
	 */

	@Column(name = "INDEMNITYDUTYRATE")
	public double getIndemnityDutyRate() {
		return this.indemnityDutyRate;
	}

	/**
	 * 属性责任比例的setter方法
	 */
	public void setIndemnityDutyRate(double indemnityDutyRate) {
		this.indemnityDutyRate = indemnityDutyRate;
	}

	/**
	 * 属性责任比例(核损)的getter方法
	 */

	@Column(name = "VERIINDEDUTYRATE")
	public double getVeriIndeDutyRate() {
		return this.veriIndeDutyRate;
	}

	/**
	 * 属性责任比例(核损)的setter方法
	 */
	public void setVeriIndeDutyRate(double veriIndeDutyRate) {
		this.veriIndeDutyRate = veriIndeDutyRate;
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
	 * 属性签发人的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性签发人的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
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
	 * 属性事故类型的getter方法
	 */

	@Column(name = "CASEFLAG")
	public String getCaseFlag() {
		return this.caseFlag;
	}

	/**
	 * 属性事故类型的setter方法
	 */
	public void setCaseFlag(String caseFlag) {
		this.caseFlag = caseFlag;
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
	 * 属性VIN号码的getter方法
	 */

	@Column(name = "VINNO")
	public String getVINNo() {
		return this.VINNo;
	}

	/**
	 * 属性VIN号码的setter方法
	 */
	public void setVINNo(String vINNo) {
		this.VINNo = vINNo;
	}

	/**
	 * 属性管理费率的getter方法
	 */

	@Column(name = "SUMMANAGEFEERATE")
	public double getSumManageFeeRate() {
		return this.sumManageFeeRate;
	}

	/**
	 * 属性管理费率的setter方法
	 */
	public void setSumManageFeeRate(double sumManageFeeRate) {
		this.sumManageFeeRate = sumManageFeeRate;
	}

	/**
	 * 属性回勘意见的getter方法
	 */

	@Column(name = "BACKCHECKREMARK")
	public String getBackCheckRemark() {
		return this.backCheckRemark;
	}

	/**
	 * 属性回勘意见的setter方法
	 */
	public void setBackCheckRemark(String backCheckRemark) {
		this.backCheckRemark = backCheckRemark;
	}

	/**
	 * 属性是否需要回勘的getter方法
	 */

	@Column(name = "BACKCHECKFLAG")
	public String getBackCheckFlag() {
		return this.backCheckFlag;
	}

	/**
	 * 属性是否需要回勘的setter方法
	 */
	public void setBackCheckFlag(String backCheckFlag) {
		this.backCheckFlag = backCheckFlag;
	}

	/**
	 * 属性可操作/处理的级别的getter方法
	 */

	@Column(name = "HANDLERRANGE")
	public String getHandlerRange() {
		return this.handlerRange;
	}

	/**
	 * 属性可操作/处理的级别的setter方法
	 */
	public void setHandlerRange(String handlerRange) {
		this.handlerRange = handlerRange;
	}

	/**
	 * 属性SUMTRANSFEE的getter方法
	 */

	@Column(name = "SUMTRANSFEE")
	public double getSumTransFee() {
		return this.sumTransFee;
	}

	/**
	 * 属性SUMTRANSFEE的setter方法
	 */
	public void setSumTransFee(double sumTransFee) {
		this.sumTransFee = sumTransFee;
	}

	/**
	 * 属性SUMTAX的getter方法
	 */

	@Column(name = "SUMTAX")
	public double getSumTax() {
		return this.sumTax;
	}

	/**
	 * 属性SUMTAX的setter方法
	 */
	public void setSumTax(double sumTax) {
		this.sumTax = sumTax;
	}

	/**
	 * 属性增加浮動比例的getter方法
	 */

	@Column(name = "SUMFLOATRATE")
	public Double getSumFloatRate() {
		return this.sumFloatRate;
	}

	/**
	 * 属性增加浮動比例的setter方法
	 */
	public void setSumFloatRate(Double sumFloatRate) {
		this.sumFloatRate = sumFloatRate;
	}

	@Transient
	public String getLicenseColorCode() {
		return licenseColorCode;
	}

	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	@Transient
	public String getCarKindCode() {
		return carKindCode;
	}

	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}

	@Transient
	public String getCarKindName() {
		return carKindName;
	}

	public void setCarKindName(String carKindName) {
		this.carKindName = carKindName;
	}

	@Transient
	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	@Transient
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Transient
	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	@Transient
	public String getFrameNo() {
		return frameNo;
	}

	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	@Transient
	public String getInsureCarFlag() {
		return insureCarFlag;
	}

	public void setInsureCarFlag(String insureCarFlag) {
		this.insureCarFlag = insureCarFlag;
	}

	@Transient
	public String getInsureCarFlagName() {
		return insureCarFlagName;
	}

	public void setInsureCarFlagName(String insureCarFlagName) {
		this.insureCarFlagName = insureCarFlagName;
	}

	@Transient
	public String getInsureComCode() {
		return insureComCode;
	}

	public void setInsureComCode(String insureComCode) {
		this.insureComCode = insureComCode;
	}

	@Transient
	public String getInsureComName() {
		return insureComName;
	}

	public void setInsureComName(String insureComName) {
		this.insureComName = insureComName;
	}
}
