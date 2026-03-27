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

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLrepairFee修理费用清单
 */
@Entity
@Table(name = "PRPLREPAIRFEE")
public class PrpLrepairFee implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLrepairFeeId id;

	/** 属性立案号 */
	private String claimNo;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性标的编号 */
	private Integer itemKindNo;

	/** 属性险别 */
	private String kindCode;

	/** 属性车牌号码 */
	private String licenseNo;

	/** 属性车牌底色代码 */
	private String licenseColorCode;

	/** 属性车辆种类代码 */
	private String carKindCode;

	/** 属性修理厂代码 */
	private String repairFactoryCode;

	/** 属性修理厂名称 */
	private String repairFactoryName;

	/** 属性经办人 */
	private String handlerCode;

	/** 属性拖修日期 */
	private Date repairStartDate;

	/** 属性修回日期 */
	private Date repairEndDate;

	/** 属性核准人 */
	private String sanctioner;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性签发人 */
	private String operatorCode;

	/** 属性修理项目代码 */
	private String compCode;

	/** 属性修理项目名称 */
	private String compName;

	/** 属性工时 */
	private Double manHour;

	/** 属性工时单价 */
	private Double manHourUnitPrice;

	/** 属性工时费 */
	private Double manHourFee;

	/** 属性材料费（上报价格） */
	private Double materialFee;

	/** 属性赔偿比例 */
	private Double lossRate;

	/** 属性币别 */
	private String currency;

	/** 属性核定损金额（中间计算乘以数量） */
	private Double sumDefLoss;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性标志 */
	private String flag;

	/** 属性工时(核损) */
	private Double veriManHour;

	/** 属性工时单价(核损) */
	private Double veriManUnitPrice;

	/** 属性工时费(核损) */
	private Double veriManHourFee;

	/** 属性材料数量(核损) */
	private Double veriMaterQuantity;

	/** 属性材料单价费(核损) */
	private Double veriMaterUnitPrice;

	/** 属性材料费(核损) */
	private Double veriMaterialFee;

	/** 属性赔偿比例(核损) */
	private Double veriLossRate;

	/** 属性核定损金额(核损) */
	private Double veriSumLoss;

	/** 属性备注（核损意见） */
	private String veriRemark;

	/** 属性材料数量 */
	private Double materialQuantity;

	/** 属性材料单价费 */
	private Double materialUnitPrice;

	/** 属性SUMCHECKLOSS */
	private Double sumCheckLoss;

	/** 属性回勘意见 */
	private String backCheckRemark;

	/** 属性损失部件代码 */
	private String partCode;

	/** 属性损失部件名称 */
	private String partName;

	/** 属性修理方式 */
	private String repairType;

	/** 属性初次定损金额 */
	private Double firstSumDefLoss;

	/** 属性原有换件标记 */
	private String compensateBackFlag;

	/** 属性配件序号 */
	private String indId;

	/** 属性险别名称 */
	private String kindName = "";
	/** 属性经办人名称 */
	private String handlerName = "";

	private String prpLrepairFeePartCode = "";

	private String prpLrepairFeePartName = "";
	/** 集合 **/
	List<PrpLrepairFee> repairFeeList = new ArrayList<PrpLrepairFee>();

	private String repairTypeName = "";

	//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 START
	/** 完工日期 */
	private Date completeDate;//COMPLETEDATE  COURTESYCARUSEDATES
	/** 代步車使用天數*/
	private Integer courtesyCarUseDates;//COURTESYCARUSEDATES
	//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 END
	
	/**
	 * 类PrpLrepairFee的默认构造方法
	 */
	public PrpLrepairFee() {
		this.id = new PrpLrepairFeeId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")), @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")),
			@AttributeOverride(name = "lossItemCode", column = @Column(name = "LOSSITEMCODE")) })
	public PrpLrepairFeeId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLrepairFeeId id) {
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
	public Integer getItemKindNo() {
		return this.itemKindNo;
	}

	/**
	 * 属性标的编号的setter方法
	 */
	public void setItemKindNo(Integer itemKindNo) {
		this.itemKindNo = itemKindNo;
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
	 * 属性车牌底色代码的getter方法
	 */

	@Column(name = "LICENSECOLORCODE")
	public String getLicenseColorCode() {
		return this.licenseColorCode;
	}

	/**
	 * 属性车牌底色代码的setter方法
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	/**
	 * 属性车辆种类代码的getter方法
	 */

	@Column(name = "CARKINDCODE")
	public String getCarKindCode() {
		return this.carKindCode;
	}

	/**
	 * 属性车辆种类代码的setter方法
	 */
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}

	/**
	 * 属性修理厂代码的getter方法
	 */

	@Column(name = "REPAIRFACTORYCODE")
	public String getRepairFactoryCode() {
		return this.repairFactoryCode;
	}

	/**
	 * 属性修理厂代码的setter方法
	 */
	public void setRepairFactoryCode(String repairFactoryCode) {
		this.repairFactoryCode = repairFactoryCode;
	}

	/**
	 * 属性修理厂名称的getter方法
	 */

	@Column(name = "REPAIRFACTORYNAME")
	public String getRepairFactoryName() {
		return this.repairFactoryName;
	}

	/**
	 * 属性修理厂名称的setter方法
	 */
	public void setRepairFactoryName(String repairFactoryName) {
		this.repairFactoryName = repairFactoryName;
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
	 * 属性拖修日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REPAIRSTARTDATE")
	public Date getRepairStartDate() {
		return this.repairStartDate;
	}

	/**
	 * 属性拖修日期的setter方法
	 */
	public void setRepairStartDate(Date repairStartDate) {
		this.repairStartDate = repairStartDate;
	}

	/**
	 * 属性修回日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REPAIRENDDATE")
	public Date getRepairEndDate() {
		return this.repairEndDate;
	}

	/**
	 * 属性修回日期的setter方法
	 */
	public void setRepairEndDate(Date repairEndDate) {
		this.repairEndDate = repairEndDate;
	}

	/**
	 * 属性核准人的getter方法
	 */

	@Column(name = "SANCTIONER")
	public String getSanctioner() {
		return this.sanctioner;
	}

	/**
	 * 属性核准人的setter方法
	 */
	public void setSanctioner(String sanctioner) {
		this.sanctioner = sanctioner;
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
	 * 属性修理项目代码的getter方法
	 */

	@Column(name = "COMPCODE")
	public String getCompCode() {
		return this.compCode;
	}

	/**
	 * 属性修理项目代码的setter方法
	 */
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	/**
	 * 属性修理项目名称的getter方法
	 */

	@Column(name = "COMPNAME")
	public String getCompName() {
		return this.compName;
	}

	/**
	 * 属性修理项目名称的setter方法
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * 属性工时的getter方法
	 */

	@Column(name = "MANHOUR")
	public Double getManHour() {
		return this.manHour;
	}

	/**
	 * 属性工时的setter方法
	 */
	public void setManHour(Double manHour) {
		this.manHour = manHour;
	}

	/**
	 * 属性工时单价的getter方法
	 */

	@Column(name = "MANHOURUNITPRICE")
	public Double getManHourUnitPrice() {
		return this.manHourUnitPrice;
	}

	/**
	 * 属性工时单价的setter方法
	 */
	public void setManHourUnitPrice(Double manHourUnitPrice) {
		this.manHourUnitPrice = manHourUnitPrice;
	}

	/**
	 * 属性工时费的getter方法
	 */

	@Column(name = "MANHOURFEE")
	public Double getManHourFee() {
		return this.manHourFee;
	}

	/**
	 * 属性工时费的setter方法
	 */
	public void setManHourFee(Double manHourFee) {
		this.manHourFee = manHourFee;
	}

	/**
	 * 属性材料费（上报价格）的getter方法
	 */

	@Column(name = "MATERIALFEE")
	public Double getMaterialFee() {
		return this.materialFee;
	}

	/**
	 * 属性材料费（上报价格）的setter方法
	 */
	public void setMaterialFee(Double materialFee) {
		this.materialFee = materialFee;
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
	 * 属性核定损金额（中间计算乘以数量）的getter方法
	 */

	@Column(name = "SUMDEFLOSS")
	public Double getSumDefLoss() {
		return this.sumDefLoss;
	}

	/**
	 * 属性核定损金额（中间计算乘以数量）的setter方法
	 */
	public void setSumDefLoss(Double sumDefLoss) {
		this.sumDefLoss = sumDefLoss;
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
	 * 属性工时(核损)的getter方法
	 */

	@Column(name = "VERIMANHOUR")
	public Double getVeriManHour() {
		return this.veriManHour;
	}

	/**
	 * 属性工时(核损)的setter方法
	 */
	public void setVeriManHour(Double veriManHour) {
		this.veriManHour = veriManHour;
	}

	/**
	 * 属性工时单价(核损)的getter方法
	 */

	@Column(name = "VERIMANUNITPRICE")
	public Double getVeriManUnitPrice() {
		return this.veriManUnitPrice;
	}

	/**
	 * 属性工时单价(核损)的setter方法
	 */
	public void setVeriManUnitPrice(Double veriManUnitPrice) {
		this.veriManUnitPrice = veriManUnitPrice;
	}

	/**
	 * 属性工时费(核损)的getter方法
	 */

	@Column(name = "VERIMANHOURFEE")
	public Double getVeriManHourFee() {
		return this.veriManHourFee;
	}

	/**
	 * 属性工时费(核损)的setter方法
	 */
	public void setVeriManHourFee(Double veriManHourFee) {
		this.veriManHourFee = veriManHourFee;
	}

	/**
	 * 属性材料数量(核损)的getter方法
	 */

	@Column(name = "VERIMATERQUANTITY")
	public Double getVeriMaterQuantity() {
		return this.veriMaterQuantity;
	}

	/**
	 * 属性材料数量(核损)的setter方法
	 */
	public void setVeriMaterQuantity(Double veriMaterQuantity) {
		this.veriMaterQuantity = veriMaterQuantity;
	}

	/**
	 * 属性材料单价费(核损)的getter方法
	 */

	@Column(name = "VERIMATERUNITPRICE")
	public Double getVeriMaterUnitPrice() {
		return this.veriMaterUnitPrice;
	}

	/**
	 * 属性材料单价费(核损)的setter方法
	 */
	public void setVeriMaterUnitPrice(Double veriMaterUnitPrice) {
		this.veriMaterUnitPrice = veriMaterUnitPrice;
	}

	/**
	 * 属性材料费(核损)的getter方法
	 */

	@Column(name = "VERIMATERIALFEE")
	public Double getVeriMaterialFee() {
		return this.veriMaterialFee;
	}

	/**
	 * 属性材料费(核损)的setter方法
	 */
	public void setVeriMaterialFee(Double veriMaterialFee) {
		this.veriMaterialFee = veriMaterialFee;
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

	@Column(name = "VERISUMLOSS")
	public Double getVeriSumLoss() {
		return this.veriSumLoss;
	}

	/**
	 * 属性核定损金额(核损)的setter方法
	 */
	public void setVeriSumLoss(Double veriSumLoss) {
		this.veriSumLoss = veriSumLoss;
	}

	/**
	 * 属性备注（核损意见）的getter方法
	 */

	@Column(name = "VERIREMARK")
	public String getVeriRemark() {
		return this.veriRemark;
	}

	/**
	 * 属性备注（核损意见）的setter方法
	 */
	public void setVeriRemark(String veriRemark) {
		this.veriRemark = veriRemark;
	}

	/**
	 * 属性材料数量的getter方法
	 */

	@Column(name = "MATERIALQUANTITY")
	public Double getMaterialQuantity() {
		return this.materialQuantity;
	}

	/**
	 * 属性材料数量的setter方法
	 */
	public void setMaterialQuantity(Double materialQuantity) {
		this.materialQuantity = materialQuantity;
	}

	/**
	 * 属性材料单价费的getter方法
	 */

	@Column(name = "MATERIALUNITPRICE")
	public Double getMaterialUnitPrice() {
		return this.materialUnitPrice;
	}

	/**
	 * 属性材料单价费的setter方法
	 */
	public void setMaterialUnitPrice(Double materialUnitPrice) {
		this.materialUnitPrice = materialUnitPrice;
	}

	/**
	 * 属性SUMCHECKLOSS的getter方法
	 */

	@Column(name = "SUMCHECKLOSS")
	public Double getSumCheckLoss() {
		return this.sumCheckLoss;
	}

	/**
	 * 属性SUMCHECKLOSS的setter方法
	 */
	public void setSumCheckLoss(Double sumCheckLoss) {
		this.sumCheckLoss = sumCheckLoss;
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
	 * 属性损失部件代码的getter方法
	 */

	@Column(name = "PARTCODE")
	public String getPartCode() {
		return this.partCode;
	}

	/**
	 * 属性损失部件代码的setter方法
	 */
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	/**
	 * 属性损失部件名称的getter方法
	 */

	@Column(name = "PARTNAME")
	public String getPartName() {
		return this.partName;
	}

	/**
	 * 属性损失部件名称的setter方法
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}

	/**
	 * 属性修理方式的getter方法
	 */

	@Column(name = "REPAIRTYPE")
	public String getRepairType() {
		return this.repairType;
	}

	/**
	 * 属性修理方式的setter方法
	 */
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	/**
	 * 属性初次定损金额的getter方法
	 */

	@Column(name = "FIRSTSUMDEFLOSS")
	public Double getFirstSumDefLoss() {
		return this.firstSumDefLoss;
	}

	/**
	 * 属性初次定损金额的setter方法
	 */
	public void setFirstSumDefLoss(Double firstSumDefLoss) {
		this.firstSumDefLoss = firstSumDefLoss;
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
	 * 属性配件序号的getter方法
	 */

	@Column(name = "INDID")
	public String getIndId() {
		return this.indId;
	}

	/**
	 * 属性配件序号的setter方法
	 */
	public void setIndId(String indId) {
		this.indId = indId;
	}

	@Transient
	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	@Transient
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	@Transient
	public String getPrpLrepairFeePartCode() {
		return prpLrepairFeePartCode;
	}

	public void setPrpLrepairFeePartCode(String prpLrepairFeePartCode) {
		this.prpLrepairFeePartCode = prpLrepairFeePartCode;
	}

	@Transient
	public String getPrpLrepairFeePartName() {
		return prpLrepairFeePartName;
	}

	public void setPrpLrepairFeePartName(String prpLrepairFeePartName) {
		this.prpLrepairFeePartName = prpLrepairFeePartName;
	}

	@Transient
	public List<PrpLrepairFee> getRepairFeeList() {
		return repairFeeList;
	}

	public void setRepairFeeList(List<PrpLrepairFee> repairFeeList) {
		this.repairFeeList = repairFeeList;
	}

	/**
	 * 获取属性部件代码
	 * @return 属性部件代码
	 */
	@Transient
	public String getRepairTypeName() {
		return repairTypeName;
	}

	/**
	 * 设置属性部件名称
	 * @param repairTypeName 待设置的属性部件名称
	 */
	public void setRepairTypeName(String repairTypeName) {
		this.repairTypeName = StringUtils.rightTrim(repairTypeName);
	}

	//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 START
	//mantis：CLM0221 ，處理人員：DP0713，需求單編號：新核心-車體險車輛資料完工日期欄位調整
	@Temporal(TemporalType.DATE)
	@Column(name = "COMPLETEDATE")
	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	@Column(name = "COURTESYCARUSEDATES")
	public Integer getCourtesyCarUseDates() {
		return courtesyCarUseDates;
	}

	public void setCourtesyCarUseDates(Integer courtesyCarUseDates) {
		this.courtesyCarUseDates = courtesyCarUseDates;
	}
	//mantis：CLM0193 ，處理人員：DP0713，需求單編號：新核心-代步車日期計算及輸入檢核 END
}
