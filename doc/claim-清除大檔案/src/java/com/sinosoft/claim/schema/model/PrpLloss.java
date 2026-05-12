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
import com.sinosoft.claim.common.util.CommonUtils;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLloss
 */
@Entity
@Table(name = "PRPLLOSS")
public class PrpLloss implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLlossId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性标的编号 */
	private int itemKindNo;

	/** 属性分户序号(仅用於集体家财险) */
	private int familyNo;

	/** 属性分户名称(仅用於集体家财险) */
	private String familyName;

	/** 属性险别 */
	private String kindCode = "";

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性标的代码 */
	private String itemCode;

	/** 属性受损标的名称 */
	private String lossName;

	/** 属性受损标的地址 */
	private String itemAddress;

	/** 属性费用明细类别代码 */
	private String feeTypeCode;

	/** 属性费用明细类别 */
	private String feeTypeName;

	/** 属性受损标的数量 */
	private double lossQuantity;

	/** 属性数量单位 */
	private String unit;

	/** 属性单价 */
	private double unitPrice;

	/** 属性购买日期 */
	private Date buyDate;

	/** 属性总折旧率 */
	private double depreRate = 0d;

	/** 属性币别 */
	private String currency;

	/** 属性保险金额/赔偿限额 */
	private double amount;

	/** 属性应收币种 */
	private String currency1;

	/** 属性标的价值 */
	private double itemValue;

	/** 属性收付币种 */
	private String currency2;

	/** 属性损失金额 */
	private double sumLoss = 0;

	/** 属性剔除金额/残值/损余 */
	private double sumRest;

	/** 属性责任比例 */
	private double indemnityDutyRate;

	/** 属性赔付比例 */
	private double claimRate;

	/** 属性免赔额币别 */
	private String currency3;

	/** 自負額比率 */
	private double deductiblerate = 0d;

	/** 属性免赔额 */
	private double deductible = 0d;

	/** 属性实赔币别 */
	private String currency4;

	/** 属性计入赔款金额 */
	private double sumRealPay = 0;

	/** 属性标志 */
	private String flag;

	/** 属性剔除原因 */
	private String rejectReason;

	/** 属性事故责任免赔率 */
	private double dutyDeductibleRate;

	/** 属性驾驶员免赔率 */
	private double driverDeductibleRate;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性协商比例 */
	private double arrangeRate;

	/** 属性RATIFYPAY */
	private Double ratifypay;

	/** 属性核定赔偿 。追偿时存该险别的赔款金额 */
	private double sumDefPay = 0d;;

	/** 属性不计免赔率 */
	private double exceptDeductibleRate;

	/** 属性不计免赔率赔偿金额 */
	private double exceptDeductiblePay;

	/** 属性危险单位序号 */
	private Integer dangerNo;

	/** 属性交强险赔款 */
	private double compelPay;

	/** 属性实际金额 */
	private double carRealValue;
	/** 属性显示列表 */
	private List<PrpLloss> prpLlossList;

	/** 属性险别名称 */
	private String kindName = "";
	/** 属性货币名称 */
	private String currencyName = "";
	/** 属性货币名称 */
	private String currency1Name = "";
	/** 属性货币名称 */
	private String currency2Name = "";
	/** 属性货币名称 */
	private String currency3Name = "";
	/** 属性货币名称 */
	private String currency4Name = "";

	/** 属性主险的绝对免赔率 */
	private double mainKindDeductibleRate = 0d;
	/** 属性全损、部分损失标示 */
	private String isLossAll = "";

	/** 赔付对象的序号 */
	private String payObjectSerialNo;
	/** 追偿：追償修改前的法務預估   */
	private Double preSumloss = 0d;
	/** 汇率 （赔付币别对本位币的汇率） */
	private Double exchRate = 1d;

	/**
	 * 累计赔付金额
	 */
	private Double hisPaid;
	/**  保留预估  */
	private String reservedEstimate = "N";
	//delete by chenjie 20150601 需求變更-095 begin
//	/** 肇事類型 肇事类型：1:有肇责，计次\2:无肇责，不计次\3:有肇责，不计次 */
//	private String accidentType = "1";
	//delete by chenjie 20150601 需求變更-095 end
	
	/** 賠付類型 財損:proploss , 車損:carloss */
	private String lossType = "";
	public static final String LOSSTYPE_PROP = "proploss";
	public static final String LOSSTYPE_CAR = "carloss";
	/**
	 * 类PrpLloss的默认构造方法
	 */
	public PrpLloss() {
		this.id = new PrpLlossId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLlossId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLlossId id) {
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
	 * 属性标的编号的getter方法
	 */

	@Column(name = "ITEMKINDNO")
	public int getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性标的编号的setter方法
	 */
	public void setItemKindNo(int itemKindNo) {
		this.itemKindNo = itemKindNo;
	}

	/**
	 * 属性分户序号(仅用於集体家财险)的getter方法
	 */

	@Column(name = "FAMILYNO")
	public int getFamilyNo() {
		return this.familyNo;
	}

	/**
	 * 属性分户序号(仅用於集体家财险)的setter方法
	 */
	public void setFamilyNo(int familyNo) {
		this.familyNo = familyNo;
	}

	/**
	 * 属性分户名称(仅用於集体家财险)的getter方法
	 */

	@Column(name = "FAMILYNAME")
	public String getFamilyName() {
		return this.familyName;
	}

	/**
	 * 属性分户名称(仅用於集体家财险)的setter方法
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
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
	 * 属性标的代码的getter方法
	 */

	@Column(name = "ITEMCODE")
	public String getItemCode() {
		return this.itemCode;
	}

	/**
	 * 属性标的代码的setter方法
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 属性受损标的名称的getter方法
	 */

	@Column(name = "LOSSNAME")
	public String getLossName() {
		return this.lossName;
	}

	/**
	 * 属性受损标的名称的setter方法
	 */
	public void setLossName(String lossName) {
		this.lossName = lossName;
	}

	/**
	 * 属性受损标的地址的getter方法
	 */

	@Column(name = "ITEMADDRESS")
	public String getItemAddress() {
		return this.itemAddress;
	}

	/**
	 * 属性受损标的地址的setter方法
	 */
	public void setItemAddress(String itemAddress) {
		this.itemAddress = itemAddress;
	}

	/**
	 * 属性费用明细类别代码的getter方法
	 */

	@Column(name = "FEETYPECODE")
	public String getFeeTypeCode() {
		return this.feeTypeCode;
	}

	/**
	 * 属性费用明细类别代码的setter方法
	 */
	public void setFeeTypeCode(String feeTypeCode) {
		this.feeTypeCode = feeTypeCode;
	}

	/**
	 * 属性费用明细类别的getter方法
	 */

	@Column(name = "FEETYPENAME")
	public String getFeeTypeName() {
		return this.feeTypeName;
	}

	/**
	 * 属性费用明细类别的setter方法
	 */
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	/**
	 * 属性受损标的数量的getter方法
	 */

	@Column(name = "LOSSQUANTITY")
	public double getLossQuantity() {
		return this.lossQuantity;
	}

	/**
	 * 属性受损标的数量的setter方法
	 */
	public void setLossQuantity(double lossQuantity) {
		this.lossQuantity = lossQuantity;
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
	 * 属性单价的getter方法
	 */

	@Column(name = "UNITPRICE")
	public double getUnitPrice() {
		return this.unitPrice;
	}

	/**
	 * 属性单价的setter方法
	 */
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
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
	 * 属性总折旧率的getter方法
	 */

	@Column(name = "DEPRERATE")
	public double getDepreRate() {
		return this.depreRate;
	}

	/**
	 * 属性总折旧率的setter方法
	 */
	public void setDepreRate(double depreRate) {
		this.depreRate = depreRate;
	}

	/**
	 * 属性币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		if(CommonUtils.isEmpty(this.currency)) {
			this.currency = ConstantCodes.LOCAL_CURRENCY;
		}
		return this.currency;
	}

	/**
	 * 属性币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性保险金额/赔偿限额的getter方法
	 */

	@Column(name = "AMOUNT")
	public double getAmount() {
		return this.amount;
	}

	/**
	 * 属性保险金额/赔偿限额的setter方法
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * 属性应收币种的getter方法
	 */

	@Column(name = "CURRENCY1")
	public String getCurrency1() {
		if(CommonUtils.isEmpty(this.currency1)) {
			this.currency1 = ConstantCodes.LOCAL_CURRENCY;
		}
		return this.currency1;
	}

	/**
	 * 属性应收币种的setter方法
	 */
	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	/**
	 * 属性标的价值的getter方法
	 */

	@Column(name = "ITEMVALUE")
	public double getItemValue() {
		return this.itemValue;
	}

	/**
	 * 属性标的价值的setter方法
	 */
	public void setItemValue(double itemValue) {
		this.itemValue = itemValue;
	}

	/**
	 * 属性收付币种的getter方法
	 */

	@Column(name = "CURRENCY2")
	public String getCurrency2() {
		if(CommonUtils.isEmpty(this.currency2)) {
			this.currency2 = ConstantCodes.LOCAL_CURRENCY;
		}
		return this.currency2;
	}

	/**
	 * 属性收付币种的setter方法
	 */
	public void setCurrency2(String currency2) {
		this.currency2 = currency2;
	}

	/**
	 * 属性损失金额的getter方法
	 */

	@Column(name = "SUMLOSS")
	public double getSumLoss() {
		return this.sumLoss;
	}

	/**
	 * 属性损失金额的setter方法
	 */
	public void setSumLoss(double sumLoss) {
		this.sumLoss = sumLoss;
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
	 * 属性赔付比例的getter方法
	 */

	@Column(name = "CLAIMRATE")
	public double getClaimRate() {
		return this.claimRate;
	}

	/**
	 * 属性赔付比例的setter方法
	 */
	public void setClaimRate(double claimRate) {
		this.claimRate = claimRate;
	}

	/**
	 * 属性免赔额币别的getter方法
	 */

	@Column(name = "CURRENCY3")
	public String getCurrency3() {
		if(CommonUtils.isEmpty(this.currency3)) {
			this.currency3 = ConstantCodes.LOCAL_CURRENCY;
		}
		return this.currency3;
	}

	/**
	 * 属性免赔额币别的setter方法
	 */
	public void setCurrency3(String currency3) {
		this.currency3 = currency3;
	}

	/**
	 * 属性DEDUCTIBLERATE的getter方法
	 */

	@Column(name = "DEDUCTIBLERATE")
	public double getDeductiblerate() {
		return this.deductiblerate;
	}

	/**
	 * 属性DEDUCTIBLERATE的setter方法
	 */
	public void setDeductiblerate(double deductiblerate) {
		this.deductiblerate = deductiblerate;
	}

	/**
	 * 属性DEDUCTIBLE的getter方法
	 */

	@Column(name = "DEDUCTIBLE")
	public double getDeductible() {
		return this.deductible;
	}

	/**
	 * 属性DEDUCTIBLE的setter方法
	 */
	public void setDeductible(double deductible) {
		this.deductible = deductible;
	}

	/**
	 * 属性实赔币别的getter方法
	 */

	@Column(name = "CURRENCY4")
	public String getCurrency4() {
		if(CommonUtils.isEmpty(this.currency4)) {
			this.currency4 = ConstantCodes.LOCAL_CURRENCY;
		}
		return this.currency4;
	}

	/**
	 * 属性实赔币别的setter方法
	 */
	public void setCurrency4(String currency4) {
		this.currency4 = currency4;
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
	 * 属性剔除原因的getter方法
	 */

	@Column(name = "REJECTREASON")
	public String getRejectReason() {
		return this.rejectReason;
	}

	/**
	 * 属性剔除原因的setter方法
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	/**
	 * 属性事故责任免赔率的getter方法
	 */

	@Column(name = "DUTYDEDUCTIBLERATE")
	public double getDutyDeductibleRate() {
		return this.dutyDeductibleRate;
	}

	/**
	 * 属性事故责任免赔率的setter方法
	 */
	public void setDutyDeductibleRate(double dutyDeductibleRate) {
		this.dutyDeductibleRate = dutyDeductibleRate;
	}

	/**
	 * 属性驾驶员免赔率的getter方法
	 */

	@Column(name = "DRIVERDEDUCTIBLERATE")
	public double getDriverDeductibleRate() {
		return this.driverDeductibleRate;
	}

	/**
	 * 属性驾驶员免赔率的setter方法
	 */
	public void setDriverDeductibleRate(double driverDeductibleRate) {
		this.driverDeductibleRate = driverDeductibleRate;
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
	 * 属性协商比例的getter方法
	 */

	@Column(name = "ARRANGERATE")
	public double getArrangeRate() {
		return this.arrangeRate;
	}

	/**
	 * 属性协商比例的setter方法
	 */
	public void setArrangeRate(double arrangeRate) {
		this.arrangeRate = arrangeRate;
	}

	/**
	 * 属性RATIFYPAY的getter方法
	 */

	@Column(name = "RATIFYPAY")
	public Double getRatifypay() {
		return this.ratifypay;
	}

	/**
	 * 属性RATIFYPAY的setter方法
	 */
	public void setRatifypay(Double ratifypay) {
		this.ratifypay = ratifypay;
	}

	/**
	 * 属性核定赔偿的getter方法
	 */

	@Column(name = "SUMDEFPAY")
	public double getSumDefPay() {
		return this.sumDefPay;
	}

	/**
	 * 属性核定赔偿的setter方法
	 */
	public void setSumDefPay(double sumDefPay) {
		this.sumDefPay = sumDefPay;
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
	 * 属性交强险赔款的getter方法
	 */

	@Column(name = "COMPELPAY")
	public double getCompelPay() {
		return this.compelPay;
	}

	/**
	 * 属性交强险赔款的setter方法
	 */
	public void setCompelPay(double compelPay) {
		this.compelPay = compelPay;
	}

	/**
	 * 属性实际金额的getter方法
	 */

	@Column(name = "CARREALVALUE")
	public double getCarRealValue() {
		return this.carRealValue;
	}

	/**
	 * 属性实际金额的setter方法
	 */
	public void setCarRealValue(double carRealValue) {
		this.carRealValue = carRealValue;
	}

	@Transient
	public String getIsLossAll() {
		return isLossAll;
	}

	public void setIsLossAll(String isLossAll) {
		this.isLossAll = isLossAll;
	}

	/**
	 * 设置属货币别名称
	 * @param currency2Name 待设置的属货币别名称的值
	 */
	public void setCurrency2Name(String currency2Name) {
		this.currency2Name = StringUtils.rightTrim(currency2Name);
	}

	/**
	 * 获取属性货币名称
	 * @return 属性货币名称的值
	 */
	@Transient
	public String getCurrency2Name() {
		return currency2Name;
	}

	/**
	 * 设置属性险别名称
	 * @param kindName 待设置的属性险别名称的值
	 */
	public void setKindName(String kindName) {
		this.kindName = StringUtils.rightTrim(kindName);
	}

	/**
	 * 获取属性险别名称
	 * @return 属性险别名称的值
	 */
	@Transient
	public String getKindName() {
		return kindName;
	}

	/**
	 * 设置属性显示列表
	 * @param prpLctextList 属性显示列表
	 */
	public void setPrpLlossList(List<PrpLloss> prpLlossList) {
		this.prpLlossList = prpLlossList;
	}

	public void setCurrency4Name(String currency4Name) {
		this.currency4Name = currency4Name;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public void setCurrency3Name(String currency3Name) {
		this.currency3Name = currency3Name;
	}

	public void setCurrency1Name(String currency1Name) {
		this.currency1Name = currency1Name;
	}

	/**
	 * 得到属性显示列表
	 * @return 属性显示列表
	 */
	@Transient
	public List<PrpLloss> getPrpLlossList() {
		return prpLlossList;
	}

	@Transient
	public String getCurrency4Name() {
		return currency4Name;
	}

	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	@Transient
	public String getCurrency3Name() {
		return currency3Name;
	}

	@Transient
	public String getCurrency1Name() {
		return currency1Name;
	}

	public void setMainKindDeductibleRate(double mainKindDeductibleRate) {
		this.mainKindDeductibleRate = mainKindDeductibleRate;
	}

	@Transient
	public double getMainKindDeductibleRate() {
		return mainKindDeductibleRate;
	}

	@Column(name = "payObjectSerialNo")
	public String getPayObjectSerialNo() {
		return payObjectSerialNo;
	}

	public void setPayObjectSerialNo(String payObjectSerialNo) {
		this.payObjectSerialNo = payObjectSerialNo;
	}

	@Column(name = "PRESUMLOSS")
	public Double getPreSumloss() {
		return preSumloss;
	}

	public void setPreSumloss(Double preSumloss) {
		if(preSumloss!=null){
			this.preSumloss = preSumloss;
		}
	}
	
	@Column(name = "EXCHRATE")
	public Double getExchRate() {
		if (ConstantCodes.LOCAL_CURRENCY.equals(this.currency)||this.exchRate == null) {
			return 1d;
		}
		return exchRate;
	}

	public void setExchRate(Double exchRate) {
		this.exchRate = exchRate;
	}

	@Transient
	public Double getHisPaid() {
		return hisPaid;
	}

	public void setHisPaid(Double hisPaid) {
		this.hisPaid = hisPaid;
	}
	@Column(name = "reservedEstimate")
	public String getReservedEstimate() {
		return reservedEstimate;
	}

	public void setReservedEstimate(String reservedEstimate) {
		this.reservedEstimate = reservedEstimate;
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
	@Transient
	public String getLossType() {
		return lossType;
	}

	public void setLossType(String lossType) {
		this.lossType = lossType;
	}
	
	
}
