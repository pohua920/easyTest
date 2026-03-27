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
 * POJO类PrpLperson人员伤亡明细信息表
 */
@Entity
@Table(name = "PRPLPERSON")
public class PrpLperson implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLpersonId id;

	/** 属性赔案号 */
	private String claimNo = "";

	/** 属性险种代码 */
	private String riskCode = "";

	/** 属性保单号码 */
	private String policyNo = "";

	/** 属性序号 */
	private Integer itemKindNo = 0;

	/** 属性分户序号 */
	private Integer familyNo = 0;

	/** 属性分户名称 */
	private String familyName = "";

	/** 属性险别代码 */
	private String kindCode = "";

	/** 属性标的项目类别代码 */
	private String itemCode = "";

	/** 属性所在地区代码 */
	private String areaCode = "";

	/** 属性固定收入标志 */
	private String fixedIncomeFlag = "";

	/** 属性雇员工种代码 */
	private String jobCode = "";

	/** 属性雇员工种名称 */
	private String jobName = "";

	/** 属性赔付人员类型 */
	private String payPersonType = "";

	/** 属性各种费用代码 */
	private String feeTypeCode = "";

	/** 属性费用名称 */
	private String feeTypeName = "";

	/** 属性人员姓名 */
	private String personName = "";

	/** 属性性别 */
	private String personSex = "";

	/** 属性年龄 */
	private Integer personAge;

	/** 属性币别 */
	private String currency = "";

	/** 属性收款人身份证号 */
	private String identifyNumber = "";

	/** 属性工作单位 */
	private String jobUnit = "";

	/** 属性标准工资 */
	private double monthStdWage = 0.00;

	/** 属性月奖金 */
	private double monthBonus = 0.00;

	/** 属性津贴 */
	private double allowance = 0.00;

	/** 属性月收入 */
	private double monthWage = 0.00;

	/** 属性就诊医院 */
	private String hospital = "";

	/** 属性需要护理人数 */
	private Integer nursePersons = 0;

	/** 属性需要护理天数 */
	private Integer nurseDays = 0;

	/** 属性诊断结果 */
	private String diagnose = "";

	/** 属性伤势程度 */
	private String woundGrade = "";

	/** 属性拟住院天数 */
	private Integer hospitalDays = 0;

	/** 属性拟治疗天数 */
	private Integer cureDays = 0;

	/** 属性是否需要转院治疗 */
	private String changeHospital = "";

	/** 属性关联人员序号 */
	private Integer relatePersonNo = 0;

	/** 属性单位金额 */
	private double unitLoss = 0.00;

	/** 属性数量 */
	private double quantity = 0.00;

	/** 属性数量单位 */
	private String unit = "";

	/** 属性用户输入的出险次数或单证个数 */
	private double times = 0;

	/** 属性受损金额 */
	private double sumLoss = 0.00;

	/** 属性剔除金额 */
	private double sumReject = 0.00;

	/** 属性剔除原因 */
	private String rejectReason = "";

	/** 属性赔偿比例 */
	private double lossRate = 0.00;

	/** 属性核定损金额 */
	private double sumDefLoss = 0.00;

	/** 属性说明 */
	private String remark = "";

	/** 属性单位金额(核损) */
	private double veriUnitLoss = 0.00;

	/** 属性数量(核损) */
	private double veriQuantity = 0.00;

	/** 属性数量单位(核损) */
	private String veriUnit = "";

	/** 属性倍数(核损) */
	private double veriTimes = 0.00;

	/** 属性受损金额(核损) */
	private double veriSumLoss = 0.00;

	/** 属性剔除金额(核损) */
	private double veriSumReject = 0.00;

	/** 属性剔除原因(核损) */
	private String veriRejectReason = "";

	/** 属性赔偿比例(核损) */
	private double veriLossRate = 0.00;

	/** 属性核定损金额(核损) */
	private double veriSumDefLoss = 0.00;

	/** 属性备注1 */
	private String veriRemark = "";

	/** 属性标志字段 */
	private String flag = "";

	/** 属性继医情况说明 */
	private String fllowHospRemark = "";

	/** 属性入院日期 */
	private Date inHospDate;

	/** 属性出院日期 */
	private Date outHospDate;

	/** 属性定残日期 */
	private Date restDate;

	/** 属性原有换件标记 */
	private String compensateBackFlag = "";

	/** 属性区域名称 */
	private String areaName = "";
	/** 属性currencyName */
	private String currencyName = "";
	/** 属性险别名称 */
	private String kindName = "";
	/** 属性险别名称 */
	private String itemName = "";
	/** 属性一级行业代码 */
	private String jobCode1 = "";
	/** 属性一级行业名称 */
	private String jobName1 = "";
	/** 属性二级行业代码 */
	private String jobCode2 = "";
	/** 属性二级行业名称 */
	private String jobName2 = "";

	private List<PrpLperson> personList = new ArrayList<PrpLperson>();

	/** 属性人员类型名称 */
	private String payPersonTypeName = "";
	/** 属性收入情况名称 */
	private String fixedIncomeFlagName = "";
	private List<PrpLcompelMedical> prpLcompelMedicalList = new ArrayList<PrpLcompelMedical>();
	/**
	 * 类PrpLperson的默认构造方法
	 */
	public PrpLperson() {
		this.id = new PrpLpersonId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")),
		@AttributeOverride(name = "registNo", column = @Column(name = "REGISTNO")),
		@AttributeOverride(name = "personNo", column = @Column(name = "personNo"))})
	public PrpLpersonId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLpersonId id) {
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
	 * 属性所在地区代码的getter方法
	 */

	@Column(name = "AREACODE")
	public String getAreaCode() {
		return this.areaCode;
	}

	/**
	 * 属性所在地区代码的setter方法
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * 属性固定收入标志的getter方法
	 */

	@Column(name = "FIXEDINCOMEFLAG")
	public String getFixedIncomeFlag() {
		return this.fixedIncomeFlag;
	}

	/**
	 * 属性固定收入标志的setter方法
	 */
	public void setFixedIncomeFlag(String fixedIncomeFlag) {
		this.fixedIncomeFlag = fixedIncomeFlag;
	}

	/**
	 * 属性雇员工种代码的getter方法
	 */

	@Column(name = "JOBCODE")
	public String getJobCode() {
		return this.jobCode;
	}

	/**
	 * 属性雇员工种代码的setter方法
	 */
	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	/**
	 * 属性雇员工种名称的getter方法
	 */

	@Column(name = "JOBNAME")
	public String getJobName() {
		return this.jobName;
	}

	/**
	 * 属性雇员工种名称的setter方法
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * 属性赔付人员类型的getter方法
	 */

	@Column(name = "PAYPERSONTYPE")
	public String getPayPersonType() {
		return this.payPersonType;
	}

	/**
	 * 属性赔付人员类型的setter方法
	 */
	public void setPayPersonType(String payPersonType) {
		this.payPersonType = payPersonType;
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
	 * 属性人员姓名的getter方法
	 */

	@Column(name = "PERSONNAME")
	public String getPersonName() {
		return this.personName;
	}

	/**
	 * 属性人员姓名的setter方法
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * 属性性别的getter方法
	 */

	@Column(name = "PERSONSEX")
	public String getPersonSex() {
		return this.personSex;
	}

	/**
	 * 属性性别的setter方法
	 */
	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}

	/**
	 * 属性年龄的getter方法
	 */

	@Column(name = "PERSONAGE")
	public Integer getPersonAge() {
		return this.personAge;
	}

	/**
	 * 属性年龄的setter方法
	 */
	public void setPersonAge(Integer personAge) {
		this.personAge = personAge;
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
	 * 属性收款人身份证号的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性收款人身份证号的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性工作单位的getter方法
	 */

	@Column(name = "JOBUNIT")
	public String getJobUnit() {
		return this.jobUnit;
	}

	/**
	 * 属性工作单位的setter方法
	 */
	public void setJobUnit(String jobUnit) {
		this.jobUnit = jobUnit;
	}

	/**
	 * 属性标准工资的getter方法
	 */

	@Column(name = "MONTHSTDWAGE")
	public double getMonthStdWage() {
		return this.monthStdWage;
	}

	/**
	 * 属性标准工资的setter方法
	 */
	public void setMonthStdWage(double monthStdWage) {
		this.monthStdWage = monthStdWage;
	}

	/**
	 * 属性月奖金的getter方法
	 */

	@Column(name = "MONTHBONUS")
	public double getMonthBonus() {
		return this.monthBonus;
	}

	/**
	 * 属性月奖金的setter方法
	 */
	public void setMonthBonus(double monthBonus) {
		this.monthBonus = monthBonus;
	}

	/**
	 * 属性津贴的getter方法
	 */

	@Column(name = "ALLOWANCE")
	public double getAllowance() {
		return this.allowance;
	}

	/**
	 * 属性津贴的setter方法
	 */
	public void setAllowance(double allowance) {
		this.allowance = allowance;
	}

	/**
	 * 属性月收入的getter方法
	 */

	@Column(name = "MONTHWAGE")
	public double getMonthWage() {
		return this.monthWage;
	}

	/**
	 * 属性月收入的setter方法
	 */
	public void setMonthWage(double monthWage) {
		this.monthWage = monthWage;
	}

	/**
	 * 属性就诊医院的getter方法
	 */

	@Column(name = "HOSPITAL")
	public String getHospital() {
		return this.hospital;
	}

	/**
	 * 属性就诊医院的setter方法
	 */
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}

	/**
	 * 属性需要护理人数的getter方法
	 */

	@Column(name = "NURSEPERSONS")
	public Integer getNursePersons() {
		return this.nursePersons;
	}

	/**
	 * 属性需要护理人数的setter方法
	 */
	public void setNursePersons(Integer nursePersons) {
		this.nursePersons = nursePersons;
	}

	/**
	 * 属性需要护理天数的getter方法
	 */

	@Column(name = "NURSEDAYS")
	public Integer getNurseDays() {
		return this.nurseDays;
	}

	/**
	 * 属性需要护理天数的setter方法
	 */
	public void setNurseDays(Integer nurseDays) {
		this.nurseDays = nurseDays;
	}

	/**
	 * 属性诊断结果的getter方法
	 */

	@Column(name = "DIAGNOSE")
	public String getDiagnose() {
		return this.diagnose;
	}

	/**
	 * 属性诊断结果的setter方法
	 */
	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	/**
	 * 属性伤势程度的getter方法
	 */

	@Column(name = "WOUNDGRADE")
	public String getWoundGrade() {
		return this.woundGrade;
	}

	/**
	 * 属性伤势程度的setter方法
	 */
	public void setWoundGrade(String woundGrade) {
		this.woundGrade = woundGrade;
	}

	/**
	 * 属性拟住院天数的getter方法
	 */

	@Column(name = "HOSPITALDAYS")
	public Integer getHospitalDays() {
		return this.hospitalDays;
	}

	/**
	 * 属性拟住院天数的setter方法
	 */
	public void setHospitalDays(Integer hospitalDays) {
		this.hospitalDays = hospitalDays;
	}

	/**
	 * 属性拟治疗天数的getter方法
	 */

	@Column(name = "CUREDAYS")
	public Integer getCureDays() {
		return this.cureDays;
	}

	/**
	 * 属性拟治疗天数的setter方法
	 */
	public void setCureDays(Integer cureDays) {
		this.cureDays = cureDays;
	}

	/**
	 * 属性是否需要转院治疗的getter方法
	 */

	@Column(name = "CHANGEHOSPITAL")
	public String getChangeHospital() {
		return this.changeHospital;
	}

	/**
	 * 属性是否需要转院治疗的setter方法
	 */
	public void setChangeHospital(String changeHospital) {
		this.changeHospital = changeHospital;
	}

	/**
	 * 属性关联人员序号的getter方法
	 */

	@Column(name = "RELATEPERSONNO")
	public Integer getRelatePersonNo() {
		return this.relatePersonNo;
	}

	/**
	 * 属性关联人员序号的setter方法
	 */
	public void setRelatePersonNo(Integer relatePersonNo) {
		this.relatePersonNo = relatePersonNo;
	}

	/**
	 * 属性单位金额的getter方法
	 */

	@Column(name = "UNITLOSS")
	public double getUnitLoss() {
		return this.unitLoss;
	}

	/**
	 * 属性单位金额的setter方法
	 */
	public void setUnitLoss(double unitLoss) {
		this.unitLoss = unitLoss;
	}

	/**
	 * 属性数量的getter方法
	 */

	@Column(name = "QUANTITY")
	public double getQuantity() {
		return this.quantity;
	}

	/**
	 * 属性数量的setter方法
	 */
	public void setQuantity(double quantity) {
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
	 * 属性用户输入的出险次数或单证个数的getter方法
	 */

	@Column(name = "TIMES")
	public double getTimes() {
		return this.times;
	}

	/**
	 * 属性用户输入的出险次数或单证个数的setter方法
	 */
	public void setTimes(double times) {
		this.times = times;
	}

	/**
	 * 属性受损金额的getter方法
	 */

	@Column(name = "SUMLOSS")
	public double getSumLoss() {
		return this.sumLoss;
	}

	/**
	 * 属性受损金额的setter方法
	 */
	public void setSumLoss(double sumLoss) {
		this.sumLoss = sumLoss;
	}

	/**
	 * 属性剔除金额的getter方法
	 */

	@Column(name = "SUMREJECT")
	public double getSumReject() {
		return this.sumReject;
	}

	/**
	 * 属性剔除金额的setter方法
	 */
	public void setSumReject(double sumReject) {
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
	public double getLossRate() {
		return this.lossRate;
	}

	/**
	 * 属性赔偿比例的setter方法
	 */
	public void setLossRate(double lossRate) {
		this.lossRate = lossRate;
	}

	/**
	 * 属性核定损金额的getter方法
	 */

	@Column(name = "SUMDEFLOSS")
	public double getSumDefLoss() {
		return this.sumDefLoss;
	}

	/**
	 * 属性核定损金额的setter方法
	 */
	public void setSumDefLoss(double sumDefLoss) {
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
	 * 属性单位金额(核损)的getter方法
	 */

	@Column(name = "VERIUNITLOSS")
	public double getVeriUnitLoss() {
		return this.veriUnitLoss;
	}

	/**
	 * 属性单位金额(核损)的setter方法
	 */
	public void setVeriUnitLoss(double veriUnitLoss) {
		this.veriUnitLoss = veriUnitLoss;
	}

	/**
	 * 属性数量(核损)的getter方法
	 */

	@Column(name = "VERIQUANTITY")
	public double getVeriQuantity() {
		return this.veriQuantity;
	}

	/**
	 * 属性数量(核损)的setter方法
	 */
	public void setVeriQuantity(double veriQuantity) {
		this.veriQuantity = veriQuantity;
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
	 * 属性倍数(核损)的getter方法
	 */

	@Column(name = "VERITIMES")
	public double getVeriTimes() {
		return this.veriTimes;
	}

	/**
	 * 属性倍数(核损)的setter方法
	 */
	public void setVeriTimes(double veriTimes) {
		this.veriTimes = veriTimes;
	}

	/**
	 * 属性受损金额(核损)的getter方法
	 */

	@Column(name = "VERISUMLOSS")
	public double getVeriSumLoss() {
		return this.veriSumLoss;
	}

	/**
	 * 属性受损金额(核损)的setter方法
	 */
	public void setVeriSumLoss(double veriSumLoss) {
		this.veriSumLoss = veriSumLoss;
	}

	/**
	 * 属性剔除金额(核损)的getter方法
	 */

	@Column(name = "VERISUMREJECT")
	public double getVeriSumReject() {
		return this.veriSumReject;
	}

	/**
	 * 属性剔除金额(核损)的setter方法
	 */
	public void setVeriSumReject(double veriSumReject) {
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
	public double getVeriLossRate() {
		return this.veriLossRate;
	}

	/**
	 * 属性赔偿比例(核损)的setter方法
	 */
	public void setVeriLossRate(double veriLossRate) {
		this.veriLossRate = veriLossRate;
	}

	/**
	 * 属性核定损金额(核损)的getter方法
	 */

	@Column(name = "VERISUMDEFLOSS")
	public double getVeriSumDefLoss() {
		return this.veriSumDefLoss;
	}

	/**
	 * 属性核定损金额(核损)的setter方法
	 */
	public void setVeriSumDefLoss(double veriSumDefLoss) {
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
	 * 属性继医情况说明的getter方法
	 */

	@Column(name = "FLLOWHOSPREMARK")
	public String getFllowHospRemark() {
		return this.fllowHospRemark;
	}

	/**
	 * 属性继医情况说明的setter方法
	 */
	public void setFllowHospRemark(String fllowHospRemark) {
		this.fllowHospRemark = fllowHospRemark;
	}

	/**
	 * 属性入院日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "INHOSPDATE")
	public Date getInHospDate() {
		return this.inHospDate;
	}

	/**
	 * 属性入院日期的setter方法
	 */
	public void setInHospDate(Date inHospDate) {
		this.inHospDate = inHospDate;
	}

	/**
	 * 属性出院日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "OUTHOSPDATE")
	public Date getOutHospDate() {
		return this.outHospDate;
	}

	/**
	 * 属性出院日期的setter方法
	 */
	public void setOutHospDate(Date outHospDate) {
		this.outHospDate = outHospDate;
	}

	/**
	 * 属性定残日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "RESTDATE")
	public Date getRestDate() {
		return this.restDate;
	}

	/**
	 * 属性定残日期的setter方法
	 */
	public void setRestDate(Date restDate) {
		this.restDate = restDate;
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

	@Transient
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Transient
	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Transient
	public String getJobCode1() {
		return jobCode1;
	}

	public void setJobCode1(String jobCode1) {
		this.jobCode1 = jobCode1;
	}

	@Transient
	public String getJobName1() {
		return jobName1;
	}

	public void setJobName1(String jobName1) {
		this.jobName1 = jobName1;
	}

	@Transient
	public String getJobCode2() {
		return jobCode2;
	}

	public void setJobCode2(String jobCode2) {
		this.jobCode2 = jobCode2;
	}

	@Transient
	public String getJobName2() {
		return jobName2;
	}

	public void setJobName2(String jobName2) {
		this.jobName2 = jobName2;
	}

	@Transient
	public List<PrpLperson> getPersonList() {
		return personList;
	}

	public void setPersonList(List<PrpLperson> personList) {
		this.personList = personList;
	}

	public void setFixedIncomeFlagName(String fixedIncomeFlagName) {
		this.fixedIncomeFlagName = fixedIncomeFlagName;
	}

	public void setPayPersonTypeName(String payPersonTypeName) {
		this.payPersonTypeName = payPersonTypeName;
	}

	@Transient
	public String getFixedIncomeFlagName() {
		return fixedIncomeFlagName;
	}

	@Transient
	public String getPayPersonTypeName() {
		return payPersonTypeName;
	}
	@Transient
	public List<PrpLcompelMedical> getPrpLcompelMedicalList() {
		return prpLcompelMedicalList;
	}

	public void setPrpLcompelMedicalList(
			List<PrpLcompelMedical> prpLcompelMedicalList) {
		this.prpLcompelMedicalList = prpLcompelMedicalList;
	}
	

}
