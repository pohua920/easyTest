package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.claim.common.ConstantCodes;

/**
 * POJO类PrpLcharge
 */
@Entity
@Table(name = "PRPLCHARGE")
public class PrpLcharge implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLchargeId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性险别 */
	private String kindCode;
	
	/** 险别序号 */
	private Integer itemKindNo = 0;

	/** 属性费用类别代码 */
	private String chargeCode;

	/** 属性费用名称 */
	private String chargeName;

	/** 属性币别 */
	private String currency = ConstantCodes.LOCAL_CURRENCY;

	/** 属性费用金额 */
	private double chargeAmount;

	/** 属性计入赔款金额 */
	private double sumRealPay;

	/** 属性标志 */
	private String flag;

	/** 属性INPUTDATE */
	private Date inputDate;

	/** 属性上报费用字段 */
	private double chargeReport;

	/** 属性支付对象类型 */
	private String payObjectType;

	/** 属性支付对象代码 */
	private String payObjectCode;

	/** 属性支付对象名称 */
	private String payObjectName;

	/** 属性费用信息标志 */
	private String chargeFlag;

	/** 属性不计免赔率 */
	private double exceptDeductibleRate;

	/** 属性不计免赔率赔偿金额 */
	private double exceptDeductiblePay;

	/** 属性危险单位序号 */
	private Integer dangerNo;

	/** 属性车牌号码 */
	private String licenseNo;

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
	private String certifiCateCode;

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
	
	/** 属性显示列表 */
	private List<PrpLcharge> prpLchargeList;
	
	/** 属性险别名称 */
	private String kindName;
	
	/** 货币名称 */
	private String currencyName;
	
	/** 支付帳号信息 */
	private PrpLpayObjectInfo prpLpayObjectInfo;
	
	/** 本位币的金额 */
	private Double currencyAmount;
	
	/** 汇率 （赔付币别对本位币的汇率） */
	private Double exchRate = 1d;
	
    /** 关联的序号,表示是那条的代扣税  */
	private Integer feeSerialNo;

	/** 洗錢旗標 Y:已命中,N:未命中,M:手動*/
	private String amlFlag ;
	
	/** 洗錢檢測日期*/
	private Date amlDate ;
	
	/**
	 * 类PrpLcharge的默认构造方法
	 */
	public PrpLcharge() {
		this.id = new PrpLchargeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLchargeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLchargeId id) {
		this.id = id;
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
	 * 属性险别的getter方法
	 */

	@Column(name = "KINDCODE")
	public String getKindCode() {
		return this.kindCode;
	}

	/**
	 * 属性险别的setter方法
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
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
	 * 属性费用名称的getter方法
	 */

	@Column(name = "CHARGENAME")
	public String getChargeName() {
		return this.chargeName;
	}

	/**
	 * 属性费用名称的setter方法
	 */
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
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
	 * 属性费用金额的getter方法
	 */

	@Column(name = "CHARGEAMOUNT")
	public double getChargeAmount() {
		return this.chargeAmount;
	}

	/**
	 * 属性费用金额的setter方法
	 */
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * 属性计入赔款金额的getter方法
	 */

	@Column(name = "SUMREALPAY")
	public double getSumRealPay() {
		return this.sumRealPay;
	}

	/**
	 * 属性计入赔款金额的setter方法
	 */
	public void setSumRealPay(double sumRealPay) {
		this.sumRealPay = sumRealPay;
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
	 * 属性上报费用字段的getter方法
	 */

	@Column(name = "CHARGEREPORT")
	public double getChargeReport() {
		return this.chargeReport;
	}

	/**
	 * 属性上报费用字段的setter方法
	 */
	public void setChargeReport(double chargeReport) {
		this.chargeReport = chargeReport;
	}

	/**
	 * 属性支付对象类型的getter方法
	 */

	@Column(name = "PAYOBJECTTYPE")
	public String getPayObjectType() {
		return this.payObjectType;
	}

	/**
	 * 属性支付对象类型的setter方法
	 */
	public void setPayObjectType(String payObjectType) {
		this.payObjectType = payObjectType;
	}

	/**
	 * 属性支付对象代码的getter方法
	 */

	@Column(name = "PAYOBJECTCODE")
	public String getPayObjectCode() {
		return this.payObjectCode;
	}

	/**
	 * 属性支付对象代码的setter方法
	 */
	public void setPayObjectCode(String payObjectCode) {
		this.payObjectCode = payObjectCode;
	}

	/**
	 * 属性支付对象名称的getter方法
	 */

	@Column(name = "PAYOBJECTNAME")
	public String getPayObjectName() {
		return this.payObjectName;
	}

	/**
	 * 属性支付对象名称的setter方法
	 */
	public void setPayObjectName(String payObjectName) {
		this.payObjectName = payObjectName;
	}

	/**
	 * 属性费用信息标志的getter方法
	 */

	@Column(name = "CHARGEFLAG")
	public String getChargeFlag() {
		return this.chargeFlag;
	}

	/**
	 * 属性费用信息标志的setter方法
	 */
	public void setChargeFlag(String chargeFlag) {
		this.chargeFlag = chargeFlag;
	}

	/**
	 * 属性不计免赔率的getter方法
	 */

	@Column(name = "EXCEPTDEDUCTIBLERATE")
	public double getExceptDeductibleRate() {
		return this.exceptDeductibleRate;
	}

	/**
	 * 属性不计免赔率的setter方法
	 */
	public void setExceptDeductibleRate(double exceptDeductibleRate) {
		this.exceptDeductibleRate = exceptDeductibleRate;
	}

	/**
	 * 属性不计免赔率赔偿金额的getter方法
	 */

	@Column(name = "EXCEPTDEDUCTIBLEPAY")
	public double getExceptDeductiblePay() {
		return this.exceptDeductiblePay;
	}

	/**
	 * 属性不计免赔率赔偿金额的setter方法
	 */
	public void setExceptDeductiblePay(double exceptDeductiblePay) {
		this.exceptDeductiblePay = exceptDeductiblePay;
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
	 * 属性车牌号码的getter方法
	 */

	@Column(name = "LICENSENO")
	public String getLicenseNo() {
		return this.licenseNo;
	}

	/**
	 * 属性车牌号码的setter方法
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
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
	public String getCertifiCateCode() {
		return this.certifiCateCode;
	}

	/**
	 * 属性CERTIFICATECODE的setter方法
	 */
	public void setCertifiCateCode(String certifiCateCode) {
		this.certifiCateCode = certifiCateCode;
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

    @Transient
	public Double getCurrencyAmount() {
		return currencyAmount;
	}

	public void setCurrencyAmount(Double currencyAmount) {
		this.currencyAmount = currencyAmount;
	}

	/**
	 * 设置属性显示列表
	 * @param prpLctextList 属性显示列表
	 */
	public void setPrpLchargeList(List<PrpLcharge> prpLchargeList) {
		this.prpLchargeList = prpLchargeList;
	}

	/**
	 * 设置属性险别名称
	 * @param kindName 属性险别名称
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * 得到属性显示列表
	 * @return 属性显示列表
	 */
	@Transient
	public List<PrpLcharge> getPrpLchargeList() {
		return prpLchargeList;
	}

	/**
	 * 得到属性险别名称
	 * @return 属性险别名称
	 */
	@Transient
	public String getKindName() {
		return kindName;
	}

	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	@Transient
	public PrpLpayObjectInfo getPrpLpayObjectInfo() {
		return prpLpayObjectInfo;
	}

	public void setPrpLpayObjectInfo(PrpLpayObjectInfo prpLpayObjectInfo) {
		this.prpLpayObjectInfo = prpLpayObjectInfo;
	}

	@Column(name = "EXCHRATE")
	public Double getExchRate() {
		if (ConstantCodes.LOCAL_CURRENCY.equals(this.currency)||this.exchRate==null) {
			return 1d;
		}
		return exchRate;
	}

	public void setExchRate(Double exchRate) {
		this.exchRate = exchRate;
	}

	@Column(name = "FEESERIALNO")
	public Integer getFeeSerialNo() {
		return feeSerialNo;
	}

	public void setFeeSerialNo(Integer feeSerialNo) {
		this.feeSerialNo = feeSerialNo;
	}
	@Column(name = "itemKindNo")
	public Integer getItemKindNo() {
		if(this.itemKindNo == null){
			return 0;
		}
		return itemKindNo;
	}

	public void setItemKindNo(Integer itemKindNo) {
		this.itemKindNo = itemKindNo;
	}
	
	@Column(name = "AMLFLAG")
	public String getAmlFlag() {
		return amlFlag;
	}

	public void setAmlFlag(String amlFlag) {
		this.amlFlag = amlFlag;
	}

	@Column(name = "AMLDATE")
	public Date getAmlDate() {
		return amlDate;
	}

	public void setAmlDate(Date amlDate) {
		this.amlDate = amlDate;
	}
	
}
