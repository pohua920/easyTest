package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。

import java.util.ArrayList;
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

/**
 * POJO类PrpLprop:财产核定损明细清单表
 */
@Entity
@Table(name = "PRPLPROP")
public class PrpLprop implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLpropId id;

	/** 属性赔案号 */
	private String claimNo;

	/** 属性险种代码 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性序号 */
	private Integer itemKindNo;

	/** 属性分户序号 */
	private Integer familyNo;

	/** 属性分户名称 */
	private String familyName;

	/** 属性险别代码 */
	private String kindCode;

	/** 属性标的项目类别代码 */
	private String itemCode;

	/** 属性标的代码 */
	private String lossItemCode;

	/** 属性标的名称 */
	private String lossItemName;

	/** 属性各种费用代码 */
	private String feeTypeCode;

	/** 属性费用名称 */
	private String feeTypeName;

	/** 属性币别 */
	private String currency;

	/** 属性单价 */
	private Double unitPrice;

	/** 属性受损标的数量 */
	private Double lossQuantity;

	/** 属性数量单位 */
	private String unit;

	/** 属性购买日期 */
	private Date buyDate;

	/** 属性总折旧率 */
	private Double depreRate;

	/** 属性受损金额 */
	private Double sumLoss;

	/** 属性剔除金额 */
	private Double sumReject;

	/** 属性剔除原因 */
	private String rejectReason;

	/** 属性赔偿比例 */
	private Double lossRate;

	/** 属性核定损金额 */
	private Double sumDefLoss;

	/** 属性说明 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性单价(核损) */
	private Double veriUnitPrice;

	/** 属性受损标的数量(核损) */
	private Double veriLossQuantity;

	/** 属性数量单位(核损) */
	private String veriUnit;

	/** 属性总折旧率(核损) */
	private Double veriDepreRate;

	/** 属性受损金额(核损) */
	private Double veriSumLoss;

	/** 属性剔除金额(核损) */
	private Double veriSumReject;

	/** 属性剔除原因(核损) */
	private String veriRejectReason;

	/** 属性赔偿比例(核损) */
	private Double veriLossRate;

	/** 属性核定损金额(核损) */
	private Double veriSumDefLoss;

	/** 属性备注1 */
	private String veriRemark;

	/** 属性原有换件标记 */
	private String compensateBackFlag;

	/** 属性CARSERIALNO */
	private Integer carserialno;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性标的序号 */
	private Integer carSerailNo;

	/** 属性险别名称 */
	private String kindName = "";
	/** 币别名称 */
	private String currencyName = "";
	/** 集合 **/
	private List<PrpLprop> propList = new ArrayList<PrpLprop>();

	/**
	 * 类PrpLprop的默认构造方法
	 */
	public PrpLprop() {
		this.id = new PrpLpropId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")), @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")) })
	public PrpLpropId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLpropId id) {
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
	 * 属性序号的getter方法
	 */

	@Column(name = "ITEMKINDNO")
	public Integer getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性序号的setter方法
	 */
	public void setItemKindNo(Integer itemKindNo) {
		this.itemKindNo = itemKindNo;
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
	 * 属性标的代码的getter方法
	 */

	@Column(name = "LOSSITEMCODE")
	public String getLossItemCode() {
		return this.lossItemCode;
	}

	/**
	 * 属性标的代码的setter方法
	 */
	public void setLossItemCode(String lossItemCode) {
		this.lossItemCode = lossItemCode;
	}

	/**
	 * 属性标的名称的getter方法
	 */

	@Column(name = "LOSSITEMNAME")
	public String getLossItemName() {
		return this.lossItemName;
	}

	/**
	 * 属性标的名称的setter方法
	 */
	public void setLossItemName(String lossItemName) {
		this.lossItemName = lossItemName;
	}

	/**
	 * 属性各种费用代码的getter方法
	 */

	@Column(name = "FEETYPECODE")
	public String getFeeTypeCode() {
		return this.feeTypeCode;
	}

	/**
	 * 属性各种费用代码的setter方法
	 */
	public void setFeeTypeCode(String feeTypeCode) {
		this.feeTypeCode = feeTypeCode;
	}

	/**
	 * 属性费用名称的getter方法
	 */

	@Column(name = "FEETYPENAME")
	public String getFeeTypeName() {
		return this.feeTypeName;
	}

	/**
	 * 属性费用名称的setter方法
	 */
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
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
	 * 属性单价的getter方法
	 */

	@Column(name = "UNITPRICE")
	public Double getUnitPrice() {
		return this.unitPrice;
	}

	/**
	 * 属性单价的setter方法
	 */
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 属性受损标的数量的getter方法
	 */

	@Column(name = "LOSSQUANTITY")
	public Double getLossQuantity() {
		return this.lossQuantity;
	}

	/**
	 * 属性受损标的数量的setter方法
	 */
	public void setLossQuantity(Double lossQuantity) {
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
	public Double getDepreRate() {
		return this.depreRate;
	}

	/**
	 * 属性总折旧率的setter方法
	 */
	public void setDepreRate(Double depreRate) {
		this.depreRate = depreRate;
	}

	/**
	 * 属性受损金额的getter方法
	 */

	@Column(name = "SUMLOSS")
	public Double getSumLoss() {
		return this.sumLoss;
	}

	/**
	 * 属性受损金额的setter方法
	 */
	public void setSumLoss(Double sumLoss) {
		this.sumLoss = sumLoss;
	}

	/**
	 * 属性剔除金额的getter方法
	 */

	@Column(name = "SUMREJECT")
	public Double getSumReject() {
		return this.sumReject;
	}

	/**
	 * 属性剔除金额的setter方法
	 */
	public void setSumReject(Double sumReject) {
		this.sumReject = sumReject;
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
	 * 属性赔偿比例的getter方法
	 */

	@Column(name = "LOSSRATE")
	public Double getLossRate() {
		return this.lossRate;
	}

	/**
	 * 属性赔偿比例的setter方法
	 */
	public void setLossRate(Double lossRate) {
		this.lossRate = lossRate;
	}

	/**
	 * 属性核定损金额的getter方法
	 */

	@Column(name = "SUMDEFLOSS")
	public Double getSumDefLoss() {
		return this.sumDefLoss;
	}

	/**
	 * 属性核定损金额的setter方法
	 */
	public void setSumDefLoss(Double sumDefLoss) {
		this.sumDefLoss = sumDefLoss;
	}

	/**
	 * 属性说明的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性说明的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * 属性单价(核损)的getter方法
	 */

	@Column(name = "VERIUNITPRICE")
	public Double getVeriUnitPrice() {
		return this.veriUnitPrice;
	}

	/**
	 * 属性单价(核损)的setter方法
	 */
	public void setVeriUnitPrice(Double veriUnitPrice) {
		this.veriUnitPrice = veriUnitPrice;
	}

	/**
	 * 属性受损标的数量(核损)的getter方法
	 */

	@Column(name = "VERILOSSQUANTITY")
	public Double getVeriLossQuantity() {
		return this.veriLossQuantity;
	}

	/**
	 * 属性受损标的数量(核损)的setter方法
	 */
	public void setVeriLossQuantity(Double veriLossQuantity) {
		this.veriLossQuantity = veriLossQuantity;
	}

	/**
	 * 属性数量单位(核损)的getter方法
	 */

	@Column(name = "VERIUNIT")
	public String getVeriUnit() {
		return this.veriUnit;
	}

	/**
	 * 属性数量单位(核损)的setter方法
	 */
	public void setVeriUnit(String veriUnit) {
		this.veriUnit = veriUnit;
	}

	/**
	 * 属性总折旧率(核损)的getter方法
	 */

	@Column(name = "VERIDEPRERATE")
	public Double getVeriDepreRate() {
		return this.veriDepreRate;
	}

	/**
	 * 属性总折旧率(核损)的setter方法
	 */
	public void setVeriDepreRate(Double veriDepreRate) {
		this.veriDepreRate = veriDepreRate;
	}

	/**
	 * 属性受损金额(核损)的getter方法
	 */

	@Column(name = "VERISUMLOSS")
	public Double getVeriSumLoss() {
		return this.veriSumLoss;
	}

	/**
	 * 属性受损金额(核损)的setter方法
	 */
	public void setVeriSumLoss(Double veriSumLoss) {
		this.veriSumLoss = veriSumLoss;
	}

	/**
	 * 属性剔除金额(核损)的getter方法
	 */

	@Column(name = "VERISUMREJECT")
	public Double getVeriSumReject() {
		return this.veriSumReject;
	}

	/**
	 * 属性剔除金额(核损)的setter方法
	 */
	public void setVeriSumReject(Double veriSumReject) {
		this.veriSumReject = veriSumReject;
	}

	/**
	 * 属性剔除原因(核损)的getter方法
	 */

	@Column(name = "VERIREJECTREASON")
	public String getVeriRejectReason() {
		return this.veriRejectReason;
	}

	/**
	 * 属性剔除原因(核损)的setter方法
	 */
	public void setVeriRejectReason(String veriRejectReason) {
		this.veriRejectReason = veriRejectReason;
	}

	/**
	 * 属性赔偿比例(核损)的getter方法
	 */

	@Column(name = "VERILOSSRATE")
	public Double getVeriLossRate() {
		return this.veriLossRate;
	}

	/**
	 * 属性赔偿比例(核损)的setter方法
	 */
	public void setVeriLossRate(Double veriLossRate) {
		this.veriLossRate = veriLossRate;
	}

	/**
	 * 属性核定损金额(核损)的getter方法
	 */

	@Column(name = "VERISUMDEFLOSS")
	public Double getVeriSumDefLoss() {
		return this.veriSumDefLoss;
	}

	/**
	 * 属性核定损金额(核损)的setter方法
	 */
	public void setVeriSumDefLoss(Double veriSumDefLoss) {
		this.veriSumDefLoss = veriSumDefLoss;
	}

	/**
	 * 属性备注1的getter方法
	 */

	@Column(name = "VERIREMARK")
	public String getVeriRemark() {
		return this.veriRemark;
	}

	/**
	 * 属性备注1的setter方法
	 */
	public void setVeriRemark(String veriRemark) {
		this.veriRemark = veriRemark;
	}

	/**
	 * 属性原有换件标记的getter方法
	 */

	@Column(name = "COMPENSATEBACKFLAG")
	public String getCompensateBackFlag() {
		return this.compensateBackFlag;
	}

	/**
	 * 属性原有换件标记的setter方法
	 */
	public void setCompensateBackFlag(String compensateBackFlag) {
		this.compensateBackFlag = compensateBackFlag;
	}

	/**
	 * 属性CARSERIALNO的getter方法
	 */

	@Column(name = "CARSERIALNO")
	public Integer getCarserialno() {
		return this.carserialno;
	}

	/**
	 * 属性CARSERIALNO的setter方法
	 */
	public void setCarserialno(Integer carserialno) {
		this.carserialno = carserialno;
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
	 * 属性标的序号的getter方法
	 */

	@Column(name = "CARSERAILNO")
	public Integer getCarSerailNo() {
		return this.carSerailNo;
	}

	/**
	 * 属性标的序号的setter方法
	 */
	public void setCarSerailNo(Integer carSerailNo) {
		this.carSerailNo = carSerailNo;
	}

	@Transient
	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Transient
	public List<PrpLprop> getPropList() {
		return propList;
	}

	public void setPropList(List<PrpLprop> propList) {
		this.propList = propList;
	}

}
