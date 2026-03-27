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
 * POJO类PrpLcomponent换件项目清单
 */
@Entity
@Table(name = "PRPLCOMPONENT")
public class PrpLcomponent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLcomponentId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性立案号 */
	private String claimNo;

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

	/** 属性制造年份 */
	private String makeYear;

	/** 属性变速箱型式 */
	private String gearboxType;

	/** 属性报价公司等级 */
	private String quoteCompanyGrade;

	/** 属性管理费比例 */
	private Double manageFeeRate;

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

	/** 属性部位名称 */
	private String partDesc;

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

	/** 属性数量 */
	private Integer quantity;

	/** 属性工时费 */
	private Double manHourFee;

	/** 属性材料费（上报价格） */
	private Double materialFee;

	/** 属性询价价格 */
	private Double queryPrice;

	/** 属性报价价格 */
	private Double quotedPrice;

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

	/** 属性工时 */
	private Double manHour;

	/** 属性工时单价 */
	private Double manHourUnitPrice;

	/** 属性材料数量 */
	private Double materialQuantity;

	/** 属性材料单价费 */
	private Double materialUnitPrice;

	/** 属性SUMCHECKLOSS */
	private Double sumCheckLoss;

	/** 属性数量(核损) */
	private Integer veriQuantity;

	/** 属性工时费(核损) */
	private Double veriManHourFee;

	/** 属性材料费(核损) */
	private Double veriMaterFee;

	/** 属性赔偿比例(核损) */
	private Double veriLossRate;

	/** 属性核定损金额(核损) */
	private Double sumVeriLoss;

	/** 属性备注（核损意见） */
	private String veriRemark;

	/** 属性残值(核损) */
	private Double veriRestFee;

	/** 属性残值 */
	private Double sumReject;

	/** 属性剔除原因 */
	private String rejectReason;

	/** 属性残值1 */
	private Double restFee;

	/** 属性回勘意见 */
	private String backCheckRemark;

	/** 属性损失部件代码 */
	private String partCode;

	/** 属性损失部件名称 */
	private String partName;

	/** 属性零配件原厂编码 */
	private String originalId;

	/** 属性系统专修价格 */
	private Double sys4SPrice;

	/** 属性系统市场价格 */
	private Double sysMarketPrice;

	/** 属性系统配套价格 */
	private Double sysMatchPrice;

	/** 属性本地专修价格 */
	private Double native4SPrice;

	/** 属性本地市场价格 */
	private Double nativeMarketPrice;

	/** 属性本地配套价格 */
	private Double nativeMatchPrice;

	/** 属性零配件价格（核价） */
	private Double verpCompPrice;

	/** 属性配件序号 */
	private String indId;

	/** 属性FIRSTMATERIALFEE */
	private Double firstMaterialFee;

	/** 属性VERPOFLAG */
	private String verpoFlag;

	/** 属性原有换件标记 */
	private String compensateBackFlag;

	/** 属性价格类型 */
	private String priceType;

	/** 属性修理厂价格 */
	private Double repairFactoryFee;

	/** 属性是否回收 */
	private String ifRemain;

	/** 属性险别名称 */
	private String kindName = "";
	/** 属性经办人名称 */
	private String handlerName = "";
	/** 属性部件代码 */
	private String prpLcomponentPartCode = "";
	/** 属性部件名称 */
	private String prpLcomponentPartName = "";

	private List<PrpLcomponent> componentList = new ArrayList<PrpLcomponent>();

	//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 START
	/** 完工日期 */
	private Date completeDate;//COMPLETEDATE  COURTESYCARUSEDATES
	/** 代步車使用天數*/
	private Integer courtesyCarUseDates;//COURTESYCARUSEDATES
	//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 END
	
	/**
	 * 类PrpLcomponent的默认构造方法
	 */
	public PrpLcomponent() {
		this.id = new PrpLcomponentId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
			@AttributeOverride(name = "lossItemCode", column = @Column(name = "LOSSITEMCODE")) })
	public PrpLcomponentId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLcomponentId id) {
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
	 * 属性制造年份的getter方法
	 */

	@Column(name = "MAKEYEAR")
	public String getMakeYear() {
		return this.makeYear;
	}

	/**
	 * 属性制造年份的setter方法
	 */
	public void setMakeYear(String makeYear) {
		this.makeYear = makeYear;
	}

	/**
	 * 属性变速箱型式的getter方法
	 */

	@Column(name = "GEARBOXTYPE")
	public String getGearboxType() {
		return this.gearboxType;
	}

	/**
	 * 属性变速箱型式的setter方法
	 */
	public void setGearboxType(String gearboxType) {
		this.gearboxType = gearboxType;
	}

	/**
	 * 属性报价公司等级的getter方法
	 */

	@Column(name = "QUOTECOMPANYGRADE")
	public String getQuoteCompanyGrade() {
		return this.quoteCompanyGrade;
	}

	/**
	 * 属性报价公司等级的setter方法
	 */
	public void setQuoteCompanyGrade(String quoteCompanyGrade) {
		this.quoteCompanyGrade = quoteCompanyGrade;
	}

	/**
	 * 属性管理费比例的getter方法
	 */

	@Column(name = "MANAGEFEERATE")
	public Double getManageFeeRate() {
		return this.manageFeeRate;
	}

	/**
	 * 属性管理费比例的setter方法
	 */
	public void setManageFeeRate(Double manageFeeRate) {
		this.manageFeeRate = manageFeeRate;
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
	 * 属性部位名称的getter方法
	 */

	@Column(name = "PARTDESC")
	public String getPartDesc() {
		return this.partDesc;
	}

	/**
	 * 属性部位名称的setter方法
	 */
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
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
	 * 属性数量的getter方法
	 */

	@Column(name = "QUANTITY")
	public Integer getQuantity() {
		return this.quantity;
	}

	/**
	 * 属性数量的setter方法
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	 * 属性询价价格的getter方法
	 */

	@Column(name = "QUERYPRICE")
	public Double getQueryPrice() {
		return this.queryPrice;
	}

	/**
	 * 属性询价价格的setter方法
	 */
	public void setQueryPrice(Double queryPrice) {
		this.queryPrice = queryPrice;
	}

	/**
	 * 属性报价价格的getter方法
	 */

	@Column(name = "QUOTEDPRICE")
	public Double getQuotedPrice() {
		return this.quotedPrice;
	}

	/**
	 * 属性报价价格的setter方法
	 */
	public void setQuotedPrice(Double quotedPrice) {
		this.quotedPrice = quotedPrice;
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
	 * 属性数量(核损)的getter方法
	 */

	@Column(name = "VERIQUANTITY")
	public Integer getVeriQuantity() {
		return this.veriQuantity;
	}

	/**
	 * 属性数量(核损)的setter方法
	 */
	public void setVeriQuantity(Integer veriQuantity) {
		this.veriQuantity = veriQuantity;
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
	 * 属性材料费(核损)的getter方法
	 */

	@Column(name = "VERIMATERFEE")
	public Double getVeriMaterFee() {
		return this.veriMaterFee;
	}

	/**
	 * 属性材料费(核损)的setter方法
	 */
	public void setVeriMaterFee(Double veriMaterFee) {
		this.veriMaterFee = veriMaterFee;
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

	@Column(name = "SUMVERILOSS")
	public Double getSumVeriLoss() {
		return this.sumVeriLoss;
	}

	/**
	 * 属性核定损金额(核损)的setter方法
	 */
	public void setSumVeriLoss(Double sumVeriLoss) {
		this.sumVeriLoss = sumVeriLoss;
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
	 * 属性残值(核损)的getter方法
	 */

	@Column(name = "VERIRESTFEE")
	public Double getVeriRestFee() {
		return this.veriRestFee;
	}

	/**
	 * 属性残值(核损)的setter方法
	 */
	public void setVeriRestFee(Double veriRestFee) {
		this.veriRestFee = veriRestFee;
	}

	/**
	 * 属性残值的getter方法
	 */

	@Column(name = "SUMREJECT")
	public Double getSumReject() {
		return this.sumReject;
	}

	/**
	 * 属性残值的setter方法
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
	 * 属性残值1的getter方法
	 */

	@Column(name = "RESTFEE")
	public Double getRestFee() {
		return this.restFee;
	}

	/**
	 * 属性残值1的setter方法
	 */
	public void setRestFee(Double restFee) {
		this.restFee = restFee;
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
	 * 属性零配件原厂编码的getter方法
	 */

	@Column(name = "ORIGINALID")
	public String getOriginalId() {
		return this.originalId;
	}

	/**
	 * 属性零配件原厂编码的setter方法
	 */
	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	/**
	 * 属性系统专修价格的getter方法
	 */

	@Column(name = "SYS4SPRICE")
	public Double getSys4SPrice() {
		return this.sys4SPrice;
	}

	/**
	 * 属性系统专修价格的setter方法
	 */
	public void setSys4SPrice(Double sys4SPrice) {
		this.sys4SPrice = sys4SPrice;
	}

	/**
	 * 属性系统市场价格的getter方法
	 */

	@Column(name = "SYSMARKETPRICE")
	public Double getSysMarketPrice() {
		return this.sysMarketPrice;
	}

	/**
	 * 属性系统市场价格的setter方法
	 */
	public void setSysMarketPrice(Double sysMarketPrice) {
		this.sysMarketPrice = sysMarketPrice;
	}

	/**
	 * 属性系统配套价格的getter方法
	 */

	@Column(name = "SYSMATCHPRICE")
	public Double getSysMatchPrice() {
		return this.sysMatchPrice;
	}

	/**
	 * 属性系统配套价格的setter方法
	 */
	public void setSysMatchPrice(Double sysMatchPrice) {
		this.sysMatchPrice = sysMatchPrice;
	}

	/**
	 * 属性本地专修价格的getter方法
	 */

	@Column(name = "NATIVE4SPRICE")
	public Double getNative4SPrice() {
		return this.native4SPrice;
	}

	/**
	 * 属性本地专修价格的setter方法
	 */
	public void setNative4SPrice(Double native4SPrice) {
		this.native4SPrice = native4SPrice;
	}

	/**
	 * 属性本地市场价格的getter方法
	 */

	@Column(name = "NATIVEMARKETPRICE")
	public Double getNativeMarketPrice() {
		return this.nativeMarketPrice;
	}

	/**
	 * 属性本地市场价格的setter方法
	 */
	public void setNativeMarketPrice(Double nativeMarketPrice) {
		this.nativeMarketPrice = nativeMarketPrice;
	}

	/**
	 * 属性本地配套价格的getter方法
	 */

	@Column(name = "NATIVEMATCHPRICE")
	public Double getNativeMatchPrice() {
		return this.nativeMatchPrice;
	}

	/**
	 * 属性本地配套价格的setter方法
	 */
	public void setNativeMatchPrice(Double nativeMatchPrice) {
		this.nativeMatchPrice = nativeMatchPrice;
	}

	/**
	 * 属性零配件价格（核价）的getter方法
	 */

	@Column(name = "VERPCOMPPRICE")
	public Double getVerpCompPrice() {
		return this.verpCompPrice;
	}

	/**
	 * 属性零配件价格（核价）的setter方法
	 */
	public void setVerpCompPrice(Double verpCompPrice) {
		this.verpCompPrice = verpCompPrice;
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

	/**
	 * 属性FIRSTMATERIALFEE的getter方法
	 */

	@Column(name = "FIRSTMATERIALFEE")
	public Double getFirstMaterialFee() {
		return this.firstMaterialFee;
	}

	/**
	 * 属性FIRSTMATERIALFEE的setter方法
	 */
	public void setFirstMaterialFee(Double firstMaterialFee) {
		this.firstMaterialFee = firstMaterialFee;
	}

	/**
	 * 属性VERPOFLAG的getter方法
	 */

	@Column(name = "VERPOFLAG")
	public String getVerpoFlag() {
		return this.verpoFlag;
	}

	/**
	 * 属性VERPOFLAG的setter方法
	 */
	public void setVerpoFlag(String verpoFlag) {
		this.verpoFlag = verpoFlag;
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
	 * 属性价格类型的getter方法
	 */

	@Column(name = "PRICETYPE")
	public String getPriceType() {
		return this.priceType;
	}

	/**
	 * 属性价格类型的setter方法
	 */
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	/**
	 * 属性修理厂价格的getter方法
	 */

	@Column(name = "REPAIRFACTORYFEE")
	public Double getRepairFactoryFee() {
		return this.repairFactoryFee;
	}

	/**
	 * 属性修理厂价格的setter方法
	 */
	public void setRepairFactoryFee(Double repairFactoryFee) {
		this.repairFactoryFee = repairFactoryFee;
	}

	/**
	 * 属性是否回收的getter方法
	 */

	@Column(name = "IFREMAIN")
	public String getIfRemain() {
		return this.ifRemain;
	}

	/**
	 * 属性是否回收的setter方法
	 */
	public void setIfRemain(String ifRemain) {
		this.ifRemain = ifRemain;
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
	public String getPrpLcomponentPartCode() {
		return prpLcomponentPartCode;
	}

	public void setPrpLcomponentPartCode(String prpLcomponentPartCode) {
		this.prpLcomponentPartCode = prpLcomponentPartCode;
	}

	@Transient
	public String getPrpLcomponentPartName() {
		return prpLcomponentPartName;
	}

	public void setPrpLcomponentPartName(String prpLcomponentPartName) {
		this.prpLcomponentPartName = prpLcomponentPartName;
	}

	@Transient
	public List<PrpLcomponent> getComponentList() {
		return componentList;
	}

	public void setComponentList(List<PrpLcomponent> componentList) {
		this.componentList = componentList;
	}

	//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 START
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
	//mantis：CLM0213，處理人員：DP0713，需求單編號：新核心-車體險維修時間重疊檢核新增險種 END
}
