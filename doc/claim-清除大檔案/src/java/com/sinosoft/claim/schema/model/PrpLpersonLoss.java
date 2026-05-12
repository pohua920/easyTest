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
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLpersonLoss人员赔付信息表
 */
/**
 * @author 中科软
 */
@Entity
@Table(name = "PRPLPERSONLOSS")
public class PrpLpersonLoss implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性id */
	private PrpLpersonLossId id;

	/** 属性险种 */
	private String riskCode;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性人员序号 */
	private int personNo;

	/** 属性人员名称 */
	private String personName;

	/** 属性交/领款人证件号码 */
	private String identifyNumber;

	/** 属性性别 */
	private String sex;

	/** 属性年龄 */
	private int age;

	/** 属性标的编号 */
	private int itemKindNo;

	/** 属性分户序号(仅用於集体家财险) */
	private int familyNo;

	/** 属性分户名称(仅用於集体家财险) */
	private String familyName;

	/** 属性险别 */
	private String kindCode;

	/** 属性责任分类代码 */
	private String liabCode;

	/** 属性责任分类名称 */
	private String liabName;

	/** 属性雇员工种代码 */
	private String jobCode;

	/** 属性雇员工种名称 */
	private String jobName;

	/** 属性责任名细分类代码 */
	private String liabDetailCode;

	/** 属性责任名细分类名称 */
	private String liabDetailName;

	/** 属性受损标的地址 */
	private String itemAddress;

	/** 属性受损标的数量 */
	private int lossQuantity;

	/** 属性数量单位 */
	private String unit;

	/** 属性单位保险金额（赔偿限额） */
	private double unitAmount;

	/** 属性币别 */
	private String currency;

	/** 属性保险金额/赔偿限额 */
	private double amount = 0d;

	/** 属性应收币种 */
	private String currency1;

	/** 属性标的价值 */
	private double itemValue = 0d;

	/** 属性收付币种 */
	private String currency2;

	/** 属性损失金额 */
	private double sumLoss = 0d;

	/** 属性剔除金额/残值/损余 */
	private double sumRest = 0d;

	/** 属性责任比例 */
	private double indemnityDutyRate;

	/** 属性赔付比例 */
	private double claimRate;

	/** 属性免赔额币别 */
	private String currency3;

	/** 属性免赔率 */
	private double deductiblerate = 0D;

	/** 属性免赔额 */
	private double deductible = 0d;

	/** 属性实赔币别 */
	private String currency4;

	/** 属性计入赔款金额 */
	private double sumRealPay = 0d;

	/** 属性标志 */
	private String flag;

	/** 属性事故责任免赔率 */
	private double dutyDeductibleRate;

	/** 属性伤残等级 */
	private String injuryGrade;

	/** 属性损伤程度描述 */
	private String injuryScopeDesc;

	/** 属性入院日期 */
	private Date inHospDate;

	/** 属性出院日期 */
	private Date outHospDate;

	/** 属性就诊医院 */
	private String hospital;

	/** 属性住院天数 */
	private int hospitalDays;

	/** 属性剔除原因 */
	private String rejectReason;

	/** 属性驾驶员免赔率 */
	private double driverDeductibleRate;

	/** 属性最高赔付金额 */
	private double maxpaid;

	/** 属性历史赔付金额 */
	private double hispaid;

	/** 属性发票/支付单备注 */
	private String remark;

	/** 属性协商比例 */
	private double arrangeRate;

	/** 属性RATIFYPAY */
	private Double ratifypay;

	/** 属性费用范围 */
	private String feeCategory;

	/** 属性核定赔偿 */
	private double sumDefPay;

	/** 属性不计免赔率 */
	private double exceptDeductibleRate;

	/** 属性不计免赔率赔偿金额 */
	private double exceptDeductiblePay;

	/** 属性危险单位序号 */
	private Integer dangerNo;

	/** 属性交强险赔款 */
	private double compelPay = 0d;
	/** 属性显示列表 */
	private List<PrpLpersonLoss> prpLpersonLossList;
	/** 属性显示列表 */
	private List<PrpLpersonLoss> personLossList;

	/** 属性险别名称 */
	private String kindName = "";
	/** 属性货币名称 */
	private String currency3Name = "";
	/** 属性货币名称 */
	private String currencyName = "";
	/** 属性货币名称 */
	private String currency2Name = "";
	/** 属性实赔金额 */
	private double sumRealPay1 = 0d;

	/** 是否超过保单中的限额标志域 */
	private String overAmount = "";
	/** 属性实赔金额 */
	private double mainKindDeductibleRate = 0d;

	/** 属性货币名称 */
	private String injuryGradeName = "";

	/** 险别最大赔付信息 */
	private double dblMaxPaid;

	/** 赔偿类型 1:死亡伤残;2医疗费用;3:财产损失;4:其它;5:无责死亡伤残;6:无责医疗费用;7:无责财产损失;8:无责其它 */
	private String claimfeeType = "";
	/**
	 * 受害人身份： 1自然人本國籍 2自然人外國籍 3自然人外國籍無居留證號或居留證號不符檢核邏輯
	 */
	private String identityOfInjuredPerson;
	/**
	 * 出生年份
	 */
	private Date birthday;
	/**
	 * 受害人健保就醫代號： Y曾以健保身分就醫 N未以健保身分就醫
	 */
	private String medicalCode;
	/**
	 * 出事當時乘坐狀況
	 */
	private String rideSituation;
	/**
	 * 個別受害人醫療給付是否結案且待健保追償(返還) 0否 1是
	 */
	private String endCaseAndRecoverFlag = "1";
	/**
	 * 受害人手機
	 */
	private String mobilePhone;
	/**
	 * 地檢署
	 */
	private String prosecutorsOffice;
	/**
	 * 地檢署名称
	 */
	private String prosecutorsOfficeName;
	/**
	 * 檢察官姓名
	 */
	private String prosecutor;
	/**
	 * 法醫師/檢驗員姓名
	 */
	private String courtDoctor;
	/**
	 * 醫療分局別所代號
	 */
	private String hospitalBranchCode;
	/**
	 * 醫療院所代號
	 */
	private String hospitalCode;
	/**
	 * 醫療院所名稱
	 */
	private String hospitalName;
	/**
	 * 醫師姓名
	 */
	private String doctor;
	/**
	 * 傷亡情形 1醫療 2失能 3死亡
	 */
	private String casualties;
	/**
	 * 醫療費用給付
	 */
	private Double sumMedicalPay;
	/**
	 * 失能給付加總
	 */
	private Double sumDisabledPay;
	/**
	 * 死亡給付
	 */
	private Double sumDeathPay;
	/**
	 * 市话号码
	 */
	private String telephoneNo;
	/**
	 * 修車廠負責人姓名
	 */
	private String garageHeadName;

	/** 所属立案 */
	private String claimNo = "";

	/** 赔付对象的信息 */
	private String payObjectSerialNo = "";
	// 伤害险添加的字段
	/** 給付類別 */
	private String paymentType;
	/** 給付類別1 */
	private String paymentType1;
	/** 給付類別2 */
	private String paymentType2;
	/** 給付说明 */
	private String paymentContent;
	/** 给付比例 */
	private Double paymentRate;
	/** 骨折部位 */
	private String fractureSite;
	/** 骨折程度 */
	private String fractureDegree;
	/** 未住院日數 */
	private Integer notHospitalDays = 0;
	/** 死亡日期 */
	private Date deathDate;
	/** 死亡地点 */
	private String deathAddressCode;
	private String deathAddressName;
	/** 死亡場所 */
	private String deathPlace;
	/** 死亡方式 */
	private String deathManner;
	/** 證明書開立日期 */
	private Date deathCertificateDate;
	/** 警員姓名 */
	private String policeName;
	/** 警方單位 */
	private String policeUnits;
	/** 承保范围 */
	private String contractingScope;
	/** 就診醫院,和出入院时间，有多组 */
	private List<PrpLpersonHospital> prpLpersonHospitalList;
	/**
	 * 賠付金額合計
	 */
	private Double personpaid;
	/** 婚姻别 */
	private String isMarried = "1";
	/** 健保局追償狀況 1本赔案无健保追偿情形\2本赔案尚待健保追偿\3健保全数付清\4本次健保追偿为分次追偿 */
	private String chasingLossesStatus = "";
	/** 汇率 （赔付币别对本位币的汇率） */
	private Double exchRate = 1d;
	/** 证件类型  */
	private String certificateCode;
	
	/** 补充保费 */
	private Double addPremium = 0d;
	/** 失能項目  */
	private String injuryCode = "";
	private String injuryName = "";
	/** 失能程度 */
	private String injuryItemCode = "";
	private String injuryItemName = "";
	/**  保留预估  */
	private String reservedEstimate = "N";
	//delete by chenjie 20150601 需求變更-095 begin 
//	/** 肇事類型 肇事类型：1:有肇责，计次\2:无肇责，不计次\3:有肇责，不计次 */
//	private String accidentType = "1";
	//delete by chenjie 20150601 需求變更-095 end
	/** 健保金額 */
	private Double healthAmount = 0d;
	/** 健保點數 */
	private Double healthPoints = 0d;

	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單
	/** 受害人身分證號類別**/
	private String idNumberType;
	/**
	 * 类的默认构造方法
	 */
	public PrpLpersonLoss() {
		this.id = new PrpLpersonLossId();
	}

	/**
	 * 属性id的getter方法
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "compensateNo", column = @Column(name = "COMPENSATENO")), @AttributeOverride(name = "serialNo", column = @Column(name = "SERIALNO")) })
	public PrpLpersonLossId getId() {
		return this.id;
	}

	/**
	 * 属性id的setter方法
	 */
	public void setId(PrpLpersonLossId id) {
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
	 * 属性人员序号的getter方法
	 */

	@Column(name = "PERSONNO")
	public int getPersonNo() {
		return this.personNo;
	}

	/**
	 * 属性人员序号的setter方法
	 */
	public void setPersonNo(int personNo) {
		this.personNo = personNo;
	}

	/**
	 * 属性人员名称的getter方法
	 */

	@Column(name = "PERSONNAME")
	public String getPersonName() {
		return this.personName;
	}

	/**
	 * 属性人员名称的setter方法
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}

	/**
	 * 属性交/领款人证件号码的getter方法
	 */

	@Column(name = "IDENTIFYNUMBER")
	public String getIdentifyNumber() {
		return this.identifyNumber;
	}

	/**
	 * 属性交/领款人证件号码的setter方法
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = identifyNumber;
	}

	/**
	 * 属性性别的getter方法
	 */

	@Column(name = "SEX")
	public String getSex() {
		return this.sex;
	}

	/**
	 * 属性性别的setter方法
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * 属性年龄的getter方法
	 */

	@Column(name = "AGE")
	public int getAge() {
		return this.age;
	}

	/**
	 * 属性年龄的setter方法
	 */
	public void setAge(int age) {
		this.age = age;
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
	 * 属性责任分类代码的getter方法
	 */

	@Column(name = "LIABCODE")
	public String getLiabCode() {
		return this.liabCode;
	}

	/**
	 * 属性责任分类代码的setter方法
	 */
	public void setLiabCode(String liabCode) {
		this.liabCode = liabCode;
	}

	/**
	 * 属性责任分类名称的getter方法
	 */

	@Column(name = "LIABNAME")
	public String getLiabName() {
		return this.liabName;
	}

	/**
	 * 属性责任分类名称的setter方法
	 */
	public void setLiabName(String liabName) {
		this.liabName = liabName;
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
	 * 属性责任名细分类代码的getter方法
	 */

	@Column(name = "LIABDETAILCODE")
	public String getLiabDetailCode() {
		return this.liabDetailCode;
	}

	/**
	 * 属性责任名细分类代码的setter方法
	 */
	public void setLiabDetailCode(String liabDetailCode) {
		this.liabDetailCode = liabDetailCode;
	}

	/**
	 * 属性责任名细分类名称的getter方法
	 */

	@Column(name = "LIABDETAILNAME")
	public String getLiabDetailName() {
		return this.liabDetailName;
	}

	/**
	 * 属性责任名细分类名称的setter方法
	 */
	public void setLiabDetailName(String liabDetailName) {
		this.liabDetailName = liabDetailName;
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
	 * 属性受损标的数量的getter方法
	 */

	@Column(name = "LOSSQUANTITY")
	public int getLossQuantity() {
		return this.lossQuantity;
	}

	/**
	 * 属性受损标的数量的setter方法
	 */
	public void setLossQuantity(int lossQuantity) {
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
	 * 属性单位保险金额（赔偿限额）的getter方法
	 */

	@Column(name = "UNITAMOUNT")
	public double getUnitAmount() {
		return this.unitAmount;
	}

	/**
	 * 属性单位保险金额（赔偿限额）的setter方法
	 */
	public void setUnitAmount(double unitAmount) {
		this.unitAmount = unitAmount;
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
	 * 属性伤残等级的getter方法
	 */

	@Column(name = "INJURYGRADE")
	public String getInjuryGrade() {
		return this.injuryGrade;
	}

	/**
	 * 属性伤残等级的setter方法
	 */
	public void setInjuryGrade(String injuryGrade) {
		this.injuryGrade = injuryGrade;
	}

	/**
	 * 属性损伤程度描述的getter方法
	 */

	@Column(name = "INJURYSCOPEDESC")
	public String getInjuryScopeDesc() {
		return this.injuryScopeDesc;
	}

	/**
	 * 属性损伤程度描述的setter方法
	 */
	public void setInjuryScopeDesc(String injuryScopeDesc) {
		this.injuryScopeDesc = injuryScopeDesc;
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
	 * 属性住院天数的getter方法
	 */

	@Column(name = "HOSPITALDAYS")
	public int getHospitalDays() {
		return this.hospitalDays;
	}

	/**
	 * 属性住院天数的setter方法
	 */
	public void setHospitalDays(int hospitalDays) {
		this.hospitalDays = hospitalDays;
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
	 * 属性最高赔付金额的getter方法
	 */

	@Column(name = "MAXPAID")
	public double getMaxpaid() {
		return this.maxpaid;
	}

	/**
	 * 属性最高赔付金额的setter方法
	 */
	public void setMaxpaid(double maxpaid) {
		this.maxpaid = maxpaid;
	}

	/**
	 * 属性历史赔付金额的getter方法
	 */

	@Column(name = "HISPAID")
	public double getHispaid() {
		return this.hispaid;
	}

	/**
	 * 属性历史赔付金额的setter方法
	 */
	public void setHispaid(double hispaid) {
		this.hispaid = hispaid;
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
	 * 设置属货币别名称
	 * @param currency3Name 待设置的属货币别名称的值
	 */
	public void setCurrency3Name(String currency3Name) {
		this.currency3Name = StringUtils.rightTrim(currency3Name);
	}

	/**
	 * 获取属性货币名称
	 * @return 属性货币名称的值
	 */
	@Transient
	public String getCurrency3Name() {
		return currency3Name;
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

	public void setPrpLpersonLossList(List<PrpLpersonLoss> prpLpersonLossList) {
		this.prpLpersonLossList = prpLpersonLossList;
	}

	public void setOverAmount(String overAmount) {
		this.overAmount = overAmount;
	}

	public void setCurrency2Name(String currency2Name) {
		this.currency2Name = currency2Name;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public void setSumRealPay1(double sumRealPay1) {
		this.sumRealPay1 = sumRealPay1;
	}

	@Transient
	public List<PrpLpersonLoss> getPrpLpersonLossList() {
		return prpLpersonLossList;
	}

	@Transient
	public List<PrpLpersonLoss> getPersonLossList() {
		return personLossList;
	}

	@Transient
	public String getOverAmount() {
		return overAmount;
	}

	@Transient
	public String getCurrency2Name() {
		return currency2Name;
	}

	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	@Transient
	public double getSumRealPay1() {
		return sumRealPay1;
	}

	public void setMainKindDeductibleRate(double mainKindDeductibleRate) {
		this.mainKindDeductibleRate = mainKindDeductibleRate;
	}

	@Transient
	public double getDblMaxPaid() {
		return dblMaxPaid;
	}

	public void setDblMaxPaid(double dblMaxPaid) {
		this.dblMaxPaid = dblMaxPaid;
	}

	@Transient
	public double getMainKindDeductibleRate() {
		return mainKindDeductibleRate;
	}

	/**
	 * 设置属性险别名称
	 * @param kindName 待设置的属性险别名称的值
	 */
	public void setInjuryGradeName(String injuryGradeName) {
		this.injuryGradeName = StringUtils.rightTrim(injuryGradeName);
	}

	/**
	 * 获取属性险别名称
	 * @return 属性险别名称的值
	 */
	@Transient
	public String getInjuryGradeName() {
		return injuryGradeName;
	}

	@Column(name = "CLAIMFEETYPE")
	public String getClaimfeeType() {
		return claimfeeType;
	}

	public void setClaimfeeType(String claimfeeType) {
		this.claimfeeType = claimfeeType;
	}

	@Column(name = "IDENTITYOFINJUREDPERSON")
	public String getIdentityOfInjuredPerson() {
		return identityOfInjuredPerson;
	}

	public void setIdentityOfInjuredPerson(String identityOfInjuredPerson) {
		this.identityOfInjuredPerson = identityOfInjuredPerson;
	}

	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "MEDICALCODE")
	public String getMedicalCode() {
		return medicalCode;
	}

	public void setMedicalCode(String medicalCode) {
		this.medicalCode = medicalCode;
	}

	@Column(name = "RIDESITUATION")
	public String getRideSituation() {
		return rideSituation;
	}

	public void setRideSituation(String rideSituation) {
		this.rideSituation = rideSituation;
	}

	@Column(name = "ENDCASEANDRECOVERFLAG")
	public String getEndCaseAndRecoverFlag() {
		return endCaseAndRecoverFlag;
	}

	public void setEndCaseAndRecoverFlag(String endCaseAndRecoverFlag) {
		this.endCaseAndRecoverFlag = endCaseAndRecoverFlag;
	}

	@Column(name = "MOBILEPHONE")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "PROSECUTORSOFFICE")
	public String getProsecutorsOffice() {
		return prosecutorsOffice;
	}

	public void setProsecutorsOffice(String prosecutorsOffice) {
		this.prosecutorsOffice = prosecutorsOffice;
	}

	@Column(name = "PROSECUTOR")
	public String getProsecutor() {
		return prosecutor;
	}

	public void setProsecutor(String prosecutor) {
		this.prosecutor = prosecutor;
	}

	@Column(name = "COURTDOCTOR")
	public String getCourtDoctor() {
		return courtDoctor;
	}

	public void setCourtDoctor(String courtDoctor) {
		this.courtDoctor = courtDoctor;
	}

	@Column(name = "HOSPITALBRANCHCODE")
	public String getHospitalBranchCode() {
		return hospitalBranchCode;
	}

	public void setHospitalBranchCode(String hospitalBranchCode) {
		this.hospitalBranchCode = hospitalBranchCode;
	}

	@Column(name = "HOSPITALCODE")
	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	@Column(name = "HOSPITALNAME")
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	@Column(name = "DOCTOR")
	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	@Column(name = "CASUALTIES")
	public String getCasualties() {
		return casualties;
	}

	public void setCasualties(String casualties) {
		this.casualties = casualties;
	}

	@Column(name = "SUMMEDICALPAY")
	public Double getSumMedicalPay() {
		return sumMedicalPay;
	}

	public void setSumMedicalPay(Double sumMedicalPay) {
		this.sumMedicalPay = sumMedicalPay;
	}

	@Column(name = "SUMDISABLEDPAY")
	public Double getSumDisabledPay() {
		return sumDisabledPay;
	}

	public void setSumDisabledPay(Double sumDisabledPay) {
		this.sumDisabledPay = sumDisabledPay;
	}

	@Column(name = "SUMDEATHPAY")
	public Double getSumDeathPay() {
		return sumDeathPay;
	}

	public void setSumDeathPay(Double sumDeathPay) {
		this.sumDeathPay = sumDeathPay;
	}

	public void setPersonLossList(List<PrpLpersonLoss> personLossList) {
		this.personLossList = personLossList;
	}

	@Column(name = "telephoneNo")
	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	@Column(name = "garageHeadName")
	public String getGarageHeadName() {
		return garageHeadName;
	}

	public void setGarageHeadName(String garageHeadName) {
		this.garageHeadName = garageHeadName;
	}

	@Transient
	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	@Column(name = "payObjectSerialNo")
	public String getPayObjectSerialNo() {
		return payObjectSerialNo;
	}

	public void setPayObjectSerialNo(String payObjectSerialNo) {
		this.payObjectSerialNo = payObjectSerialNo;
	}

	@Column(name = "paymentType")
	public String getPaymentType() {
		return paymentType;
	}

	@Column(name = "PERSONPAID")
	public Double getPersonpaid() {
		return personpaid;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "paymentType1")
	public String getPaymentType1() {
		return paymentType1;
	}

	public void setPaymentType1(String paymentType1) {
		this.paymentType1 = paymentType1;
	}

	@Column(name = "paymentType2")
	public String getPaymentType2() {
		return paymentType2;
	}

	public void setPaymentType2(String paymentType2) {
		this.paymentType2 = paymentType2;
	}

	@Column(name = "paymentContent")
	public String getPaymentContent() {
		return paymentContent;
	}

	public void setPaymentContent(String paymentContent) {
		this.paymentContent = paymentContent;
	}

	@Column(name = "paymentRate")
	public Double getPaymentRate() {
		return paymentRate;
	}

	public void setPaymentRate(Double paymentRate) {
		this.paymentRate = paymentRate;
	}

	@Column(name = "fractureSite")
	public String getFractureSite() {
		return fractureSite;
	}

	public void setFractureSite(String fractureSite) {
		this.fractureSite = fractureSite;
	}

	@Column(name = "fractureDegree")
	public String getFractureDegree() {
		return fractureDegree;
	}

	public void setFractureDegree(String fractureDegree) {
		this.fractureDegree = fractureDegree;
	}

	@Column(name = "notHospitalDays")
	public Integer getNotHospitalDays() {
		return notHospitalDays;
	}

	public void setNotHospitalDays(Integer notHospitalDays) {
		this.notHospitalDays = notHospitalDays;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deathDate")
	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	@Column(name = "deathAddressCode")
	public String getDeathAddressCode() {
		return deathAddressCode;
	}

	public void setDeathAddressCode(String deathAddressCode) {
		this.deathAddressCode = deathAddressCode;
	}

	@Column(name = "deathAddressName")
	public String getDeathAddressName() {
		return deathAddressName;
	}

	public void setDeathAddressName(String deathAddressName) {
		this.deathAddressName = deathAddressName;
	}

	@Column(name = "deathPlace")
	public String getDeathPlace() {
		return deathPlace;
	}

	public void setDeathPlace(String deathPlace) {
		this.deathPlace = deathPlace;
	}

	@Column(name = "deathManner")
	public String getDeathManner() {
		return deathManner;
	}

	public void setDeathManner(String deathManner) {
		this.deathManner = deathManner;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "deathCertificateDate")
	public Date getDeathCertificateDate() {
		return deathCertificateDate;
	}

	public void setDeathCertificateDate(Date deathCertificateDate) {
		this.deathCertificateDate = deathCertificateDate;
	}

	@Column(name = "policeName")
	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	@Column(name = "policeUnits")
	public String getPoliceUnits() {
		return policeUnits;
	}

	public void setPoliceUnits(String policeUnits) {
		this.policeUnits = policeUnits;
	}

	@Column(name = "contractingScope")
	public String getContractingScope() {
		return contractingScope;
	}

	public void setContractingScope(String contractingScope) {
		this.contractingScope = contractingScope;
	}

	@Transient
	public List<PrpLpersonHospital> getPrpLpersonHospitalList() {
		return prpLpersonHospitalList;
	}

	public void setPrpLpersonHospitalList(List<PrpLpersonHospital> prpLpersonHospitalList) {
		this.prpLpersonHospitalList = prpLpersonHospitalList;
	}

	public void setPersonpaid(Double personpaid) {
		this.personpaid = personpaid;
	}

	@Column(name = "PROSECUTORSOFFICENAME")
	public String getProsecutorsOfficeName() {
		return prosecutorsOfficeName;
	}

	public void setProsecutorsOfficeName(String prosecutorsOfficeName) {
		this.prosecutorsOfficeName = prosecutorsOfficeName;
	}

	@Column(name = "CHASINGLOSSESSTATUS")
	public String getChasingLossesStatus() {
		return chasingLossesStatus;
	}

	public void setChasingLossesStatus(String chasingLossesStatus) {
		this.chasingLossesStatus = chasingLossesStatus;
	}

	@Column(name = "ISMARRIED")
	public String getIsMarried() {
		return isMarried;
	}

	public void setIsMarried(String isMarried) {
		this.isMarried = isMarried;
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

	@Column(name = "CERTIFICATECODE")
	public String getCertificateCode() {
		return certificateCode;
	}

	public void setCertificateCode(String certificateCode) {
		this.certificateCode = certificateCode;
	}
	@Column(name = "addPremium")
	public Double getAddPremium() {
		if(this.addPremium == null){
			return 0d;
		}
		return addPremium;
	}

	public void setAddPremium(Double addPremium) {
		this.addPremium = addPremium;
	}

	@Column(name = "injuryCode")
	public String getInjuryCode() {
		return injuryCode;
	}

	public void setInjuryCode(String injuryCode) {
		this.injuryCode = injuryCode;
	}

	@Column(name = "injuryName")
	public String getInjuryName() {
		return injuryName;
	}

	public void setInjuryName(String injuryName) {
		this.injuryName = injuryName;
	}

	@Column(name = "injuryItemCode")
	public String getInjuryItemCode() {
		return injuryItemCode;
	}

	public void setInjuryItemCode(String injuryItemCode) {
		this.injuryItemCode = injuryItemCode;
	}

	@Column(name = "injuryItemName")
	public String getInjuryItemName() {
		return injuryItemName;
	}

	public void setInjuryItemName(String injuryItemName) {
		this.injuryItemName = injuryItemName;
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
	@Column(name = "HEALTHAMOUNT")
	public Double getHealthAmount() {
		return healthAmount;
	}

	public void setHealthAmount(Double healthAmount) {
		this.healthAmount = healthAmount;
	}
	@Column(name = "HEALTHPOINTS")
	public Double getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(Double healthPoints) {
		this.healthPoints = healthPoints;
	}

	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	@Column(name="IDNUMBERTYPE")
	public String getIdNumberType() {
		return idNumberType;
	}

	public void setIdNumberType(String idNumberType) {
		this.idNumberType = idNumberType;
	}
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
}
