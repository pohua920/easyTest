package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLcompensate赔款计算书表
 */
@Entity
@Table(name = "PRPLCOMPENSATE")
public class PrpLcompensate implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private double sumCheckFee = 0d;
	/** 属性赔款计算书号码 */
	private String compensateNo;
	/** 属性车牌底色代码 */
	private String licenseColorCode = "";
	/** 属性厂牌型号 */
	private String brandName = "";
	/** 属性车辆种类代码 */
	private String carKindCode = "";
	/** 属性发动机号 */
	private String engineNo = "";
	/** 属性车架号 */
	private String frameNo = "";
	/** 属性理赔类型 */
	private String lflag;
	/** 属性赔案号 */
	private String caseNo;
	/** 属性调查次数 */
	private int times;
	/** 属性险类代码 */
	private String classCode;
	/** 属性险种代码 */
	private String riskCode;

	/** 属性险种名称 */
	private String riskCodeName;

	/** 属性立案号 */
	private String claimNo;

	/** 属性保单号码 */
	private String policyNo;

	/** 属性免赔条件字段 */
	private String deductCond;

	/** 属性终到日期 */
	private Date preserveDate;

	/** 属性理赔代理人代码 */
	private String checkAgentCode;

	/** 属性理赔代理人名称 */
	private String checkAgentName;

	/** 属性检验人名称 */
	private String surveyorName;

	/** 属性索赔人名称 */
	private String counterClaimerName;

	/** 属性航方责任 */
	private String dutyDescription;

	/** 属性调查费用币别 */
	private String currency;

	/** 属性标的损失金额(同保单币别) */
	private double sumLoss = 0d;

	/** 属性剔除金额/残值/损余 */
	private double sumRest = 0d;

	/** 属性责任赔款合计(同保单币别) */
	private double sumDutyPaid = 0d;

	/** 属性不计入赔款的费用金额(同保单币别) */
	private double sumNoDutyFee = 0d;

	/** 属性总赔付金额(同保单币别) */
	private double sumPaid = 0d;

	/** 属性已预付赔款(同保单币别) */
	private double sumPrePaid = 0d;

	/** 属性本次赔付金额（同保单币别） */
	private double sumThisPaid = 0d;

	/** 属性领赔款单位/代理人/索赔人 */
	private String receiverName;

	/** 属性开户银行 */
	private String bank;

	/** 属性银行帐号 */
	private String account;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性业务归属机构代码 */
	private String comCode;

	/** 属性经办人代码 追偿时：提供占位操作的标志，0为当前立案已做登录但没有正在处理的追偿任务，追偿处理时占位，追偿审核通过时重新置0， */
	private String handlerCode = "0";

	/** 属性归属业务员代码 */
	private String handler1Code;

	/** 属性审核人代码 */
	private String approverCode;

	/** 属性最终核赔人代码 */
	private String underWriteCode;

	/** 属性最终核赔人名称 */
	private String underWriteName;

	/** 属性统计年月 */
	private Date statisticsYM;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性核赔完成日期 追偿计算书随underWriteFlag的状态变化存取 0录入时间，9送审时间，1核赔通过时间，2驳回修改时间 */
	private Date underWriteEndDate;

	/** 属性核赔标志 0表示暫存，1表示審核通過，2不通過，3無需核賠，9提交。 */
	private String underWriteFlag;

	/** 属性备注 */
	private String remark;

	/** 属性标志字段 */
	private String flag;

	/** 属性CASETYPE */
	private String caseType;

	/** 属性责任比例 */
	private double indemnityDutyRate = 0d;

	/** 属性赔偿责任代码 */
	private String indemnityDuty;

	/** 属性最终计算书标志 */
	private String finallyFlag = "1";

	/** 属性理赔结论 */
	private String result;

	/** 属性本位币赔付金额 */
	private double paidCNY = 0d;

	/** 属性赔付币别和本位币的兑换率 */
	private double exchangeRate = 0d;

	/** 属性是否是团单免导标志 */
	private String termFlag;

	/** 属性是否是代付赔款 */
	private String isPayForOther;

	/** 属性银行帳号 */
	private String accountCode;

	/** 属性总行代码 */
	private String bankCode;

	/** 属性开户行 */
	private String bankName;

	/** 属性CUSTOMBANKCODE */
	private String customBankCode;

	/** 属性CUSTOMBANKNAME */
	private String customBankName;

	/** 属性帳户归属人证件代码 */
	private String certifiCateCode;

	/** 属性帳户归属人名称 */
	private String ownerName;

	/** 属性帳户归属人电话 */
	private String ownerPhoneNo;

	/** 属性帳户类型 */
	private String accountType;

	/** 属性帳户币别 */
	private String accountCurrency;

	/** 属性业务与帳户关系 */
	private String ownership;

	/** 属性被保险人联系电话 */
	private String insuredPhoneNumber;

	/** 属性例外事项 */
	private String exceptions;

	/** 属性车牌号 */
	private String licenseNo = "";

	/** 属性reason */
	private String reason;

	/** 属性总保险金额 */
	private double sumAmount = 0d;

	/** 属性被保险人名称 */
	private String insuredName = "";

	/** 属性出险原因说明 */
	private String damageName = "";

	/** 属性出险原因代码 */
	private String damageCode = "";

	/** 属性报案号 2005-08-07 */
	private String registNo = "";
	/** 集合 **/
	List<PrpLcompensate> compensateList;
	/** 编辑类型 */
	private String editType = "";
	/** 此立案的操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = "";
	/** clauseTypeCode */
	private String clauseTypeCode = "";
	/** DeductibleTerm */
	private String deductibleTerm = "";
	/** EscapeFlag */
	private String escapeFlag = "";
	/** PurchasePrice */
	private String purchasePrice = "";
	/** 业务来源 */
	private String businessNature = "";
	/** 属性条款名称 */
	private String clauseName = "";
	/** 共保信息 */
	private String coinsFlag = "";

	/*
	 * 属性共保案件时我方赔款分摊金额
	 */
	private double sumCoinUs = 0d;
	/*
	 * 属性共保案件时我方费用分摊金额
	 */
	private double sumCoinUsFee = 0d;
	/*
	 * 属性共保案件我方代他方赔款金额
	 */
	private double sumCoinForOther = 0d;
	/*
	 * 属性共保案件我方代他方费用金额
	 */
	private double sumCoinForOtherFee = 0d;
	/** 属性历次赔付金额汇总信息 */
	double sumPaidAll = 0;
	/** 属性经办人名称 */
	private String handlerName = "";
	/** 属性被保险人代码 */
	private String insuredCode = "";
	/** 属性起保日期 */
	private Date startDate = new Date();
	/** 属性起保小时 */
	private int startHour = 0;
	/** 属性终保日期 */
	private Date endDate = new Date();
	/** 属性终保小时 */
	private int endHour = 0;
	/** 属性出险地点分类代码 */
	private String damageAddressType = "";
	/** 属性出险地点 */
	private String damageAddress = "";
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
	/** 属性出险Code */
	private String addressCode = "";
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 E
	/** 属性条款类别 */
	private String clauseType = "";
	/** 属性出险开始小时 */
	private String damageStartHour = "";
	/** 属性出险开始分钟 */
	private String damageStartMinute = "";
	/** 属性案件类型 */
	private String claimType = "";

	/** 属性案件类型名 */
	private String claimTypeName = "";
	/** 属性总保费 */
	private double sumPremium = 0d;
	/** 属性赔案类型名称 */
	private String caseTypeName = "";
	/** 属性总保费 */
	/** 属性保险损失金额 */
	private double sumClaim = 0d;
	/** 属性投保人名称 */
	private String appliName = "";
	/** 属性币别中文名称 */
	String currencyName = "";
	/** 属性已付保费 */
	private int payFee = 0;
	/** 属性是否可能有追偿 */
	private String replevyFlag = "";
	private String replevyRemark;
	/** 属性交费标志 -1为未缴费，0为未缴全，1为缴全 */
	private int palyFlag = 1;
	/** 属性核定座位数量 */
	private String seatCount = "";
	/** 属性查勘/代查勘人1 */
	private String checker1 = "";
	/** 属性车辆种类 */
	private String carKind = "";
	/** 属性车牌底色 */
	private String licenseColor = "";
	/** 属性部门名称 */
	private String comName = "";
	/** 属性赔偿责任代码 */
	private String indemnityDutyName = "";

	/** 出险日期起 */
	private Date damageStartDate = new Date();
	/** 出险日期止 */
	private Date damageEndDate = new Date();

	/** 属性出险终止小时 */
	private String damageEndHour = "";

	/** 免赔条件信息 */
	private List<PrpLdeductCond> prpLdeductCondList;

	/** 属性归属业务员名称 */
	private String handler1Name = "";
	/** 属性赔款合计 */
	private double sumDutyPaid1 = 0d;
	private String operatorName = "";
	/** 属性互碰自赔标志 */
	private String payselfFlag = "";
	/** 賠付代號 1一次赔付结案\2免赔结案\3部分赔付\4最後一次赔付\5代位求偿/残余物处理摊回\6已付赔款调整 */
	private String payCode = "3";
	/** 全損/分損代號 1全损\2分损 */
	private String lossType = "2";
	/** 肇事類型 肇事类型：1:有肇责，计次\2:无肇责，不计次\3:有肇责，不计次 */
	private String accidentType = "";
	/** 對方車肇事責任百分比 */
	private Double oppositeIndemnityDuty = 0d;
	/** 其他肇事責任百分比 */
	private Double otherIndemnityDuty = 0d;
	/** 獨立處理費用 */
	private Double independentCosts = 0d;
	/** 是否有残余物 0：否，1：是，未处理，9：已处理 */
	private String remnants = "0";
	/** 健保局追償狀況 1本赔案无健保追偿情形\2本赔案尚待健保追偿\3健保全数付清\4本次健保追偿为分次追偿 */
	private String chasingLossesStatus = "";
	/**
	 * 給付追償情況：1賠款已全數賠付給所有受害人結案、2本次賠款為分次賠付給受害人、3追償金已追償完畢結案、4本次追償為分次追償、5免賠結案、6放棄追償
	 */
	private String paySituation = "2";
	/** 理賠確認日 (对应prplremnant中的remnantDate) */
	private Date remnantDate;
	/** 零結賠案不計次 ,'0':'否','1':'是' */
	private String noPaidClaim = "";
	/** 理算文件備齊日 */
	private String fileReadyDate = "";
	/** 追償，強制險賠付日期 */
	private Date payDate;
	/** 追偿，强制险赔付类别 */
	private String compelPayType;
	/** 賠付代號/追償代號 */
	private String payCodeType;
	/** 追償 對方賠案號碼 */
	private String oppositeClaimNo;
	/** 追償 對方理賠員 */
	private String oppositeClaimOfficer;
	/** 追償總期數 */
	private int totalTimes = 0;
	/** 已追償期數 */
	private int replevyTimes = 0;
	/** 赔款速度 */
	// mantis： CLM0106 ，處理人員：BK007  蘇哲，需求單編號：CLM0106.新核心案件賠付速別預設值更改為速件
	private String speedFlag = "Y";
	/** 代位情形 */
	private String subrogation;
	/** 互冲计算书号 */
	private String mutualCompensateNo = "";
	/** 核赔机构 */
	private String underWriteDeptCode = "";
	/** 对方保单号码 */
	private String otherPolicyNo = "";
	/** 对方赔案号码 */
	private String otherClaimNo = "";
	/** 立案日期 */
	private Date claimDate;
	/** 收件日期 */
	private String receiptDate;
	/** 水险 - 船名 */
	private String shipCName = "";
	/** 水险 - 船 机型 */
	private String shipModel = "";
	/** 水险 - 航空保險保發 - 國籍編號 */
	private String nationalityCode = "";
	/** 理賠代理 */
	private String claimAgent = "";
	/** 水险 - 理算说明 */
	private String contextNo = "";
	/** 航程 始發国家 */
	private String startSiteCountry = "";
	/** 航程 始發港口 */
	private String startSitePort = "";
	/** 航程 終達国家 */
	private String endSiteCountry = "";
	/** 航程 終達港口 */
	private String endSitePort = "";
	/** 通知再保标记 */
	private String informReinsFlag = "";
	/** 航行範圍 */
	private String sailScope = "";
	/** 身份證字號 */
	private String idNumber = "";
	/** 聯絡電話 */
	private String contactTelephone = "";
	/** 聯絡地址 */
	private String contactAddress = "";
	/** 保单年度：承保端保單年度 */
	private String policyYear = "";
	/** 開航日期 */
	private Date sailStartDate;
	/** 貨物類別代號 */
	private String cargoNo = "";
	/** 地區別代號 */
	private String areaCode = "";
	/** 同险代号 */
	private String sameAddressNo;
	/***  add by chenjie 20150601 需求變更-095 begin ***/
	/** 財損肇事類型 肇事类型：1:有肇责，计次\2:无肇责，不计次\3:有肇责，不计次 */
	private String propAccidentType = "";
	/***  add by chenjie 20150601 需求變更-095 end ***/
	
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
	private Integer hospitalizedDays;//本次住院天數
	private Integer sumHospitalizedDay;//NoDB 本次事故累計住院天數(不含本次)
	private double paf4SumLoss;
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
	
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	//ISCOMPULSORYBCHAINCLAIM
	/** 是否為強制險區塊鏈攤賠案件**/
	private String isCompulsoryBchainClaim="";
	
	//SHARINGFLAG
	/** 共摊标志 */
//	private String sharingFlag = "0";
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
	/**
	 * @Description: 免赔条件信息
	 * @author 中科软
	 * @return
	 */
	@Transient
	public List<PrpLdeductCond> getPrpLdeductCondList() {
		return prpLdeductCondList;
	}

	public void setPrpLdeductCondList(List<PrpLdeductCond> prpLdeductCondList) {
		this.prpLdeductCondList = prpLdeductCondList;
	}

	/**
	 * 类PrpLcompensate的默认构造方法
	 */
	public PrpLcompensate() {
	}

	/**
	 * 属性赔款计算书号码的getter方法
	 */
	@Id
	@Column(name = "COMPENSATENO")
	public String getCompensateNo() {
		return this.compensateNo;
	}

	/**
	 * 属性赔款计算书号码的setter方法
	 */
	public void setCompensateNo(String compensateNo) {
		this.compensateNo = compensateNo;
	}

	/**
	 * 属性理赔类型的getter方法
	 */

	@Column(name = "LFLAG")
	public String getLflag() {
		return this.lflag;
	}

	/**
	 * 属性理赔类型的setter方法
	 */
	public void setLflag(String lflag) {
		this.lflag = lflag;
	}

	/**
	 * 属性赔案号的getter方法
	 */

	@Column(name = "CASENO")
	public String getCaseNo() {
		return this.caseNo;
	}

	/**
	 * 属性赔案号的setter方法
	 */
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}

	/**
	 * 属性调查次数的getter方法
	 */

	@Column(name = "TIMES")
	public int getTimes() {
		return this.times;
	}

	/**
	 * 属性调查次数的setter方法
	 */
	public void setTimes(int times) {
		this.times = times;
	}

	/**
	 * 属性险类代码的getter方法
	 */

	@Column(name = "CLASSCODE")
	public String getClassCode() {
		return this.classCode;
	}

	/**
	 * 属性险类代码的setter方法
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
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
	 * 属性免赔条件字段的getter方法
	 */

	@Column(name = "DEDUCTCOND")
	public String getDeductCond() {
		return this.deductCond;
	}

	/**
	 * 属性免赔条件字段的setter方法
	 */
	public void setDeductCond(String deductCond) {
		this.deductCond = deductCond;
	}

	/**
	 * 属性终到日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "PRESERVEDATE")
	public Date getPreserveDate() {
		return this.preserveDate;
	}

	/**
	 * 属性终到日期的setter方法
	 */
	public void setPreserveDate(Date preserveDate) {
		this.preserveDate = preserveDate;
	}

	/**
	 * 属性理赔代理人代码的getter方法
	 */

	@Column(name = "CHECKAGENTCODE")
	public String getCheckAgentCode() {
		return this.checkAgentCode;
	}

	/**
	 * 属性理赔代理人代码的setter方法
	 */
	public void setCheckAgentCode(String checkAgentCode) {
		this.checkAgentCode = checkAgentCode;
	}

	/**
	 * 属性理赔代理人名称的getter方法
	 */

	@Column(name = "CHECKAGENTNAME")
	public String getCheckAgentName() {
		return this.checkAgentName;
	}

	/**
	 * 属性理赔代理人名称的setter方法
	 */
	public void setCheckAgentName(String checkAgentName) {
		this.checkAgentName = checkAgentName;
	}

	/**
	 * 属性检验人名称的getter方法
	 */

	@Column(name = "SURVEYORNAME")
	public String getSurveyorName() {
		return this.surveyorName;
	}

	/**
	 * 属性检验人名称的setter方法
	 */
	public void setSurveyorName(String surveyorName) {
		this.surveyorName = surveyorName;
	}

	/**
	 * 属性索赔人名称的getter方法
	 */

	@Column(name = "COUNTERCLAIMERNAME")
	public String getCounterClaimerName() {
		return this.counterClaimerName;
	}

	/**
	 * 属性索赔人名称的setter方法
	 */
	public void setCounterClaimerName(String counterClaimerName) {
		this.counterClaimerName = counterClaimerName;
	}

	/**
	 * 属性航方责任的getter方法
	 */

	@Column(name = "DUTYDESCRIPTION")
	public String getDutyDescription() {
		return this.dutyDescription;
	}

	/**
	 * 属性航方责任的setter方法
	 */
	public void setDutyDescription(String dutyDescription) {
		this.dutyDescription = dutyDescription;
	}

	/**
	 * 属性调查费用币别的getter方法
	 */

	@Column(name = "CURRENCY")
	public String getCurrency() {
		return this.currency;
	}

	/**
	 * 属性调查费用币别的setter方法
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * 属性标的损失金额(同保单币别)的getter方法
	 */

	@Column(name = "SUMLOSS")
	public double getSumLoss() {
		return this.sumLoss;
	}

	/**
	 * 属性标的损失金额(同保单币别)的setter方法
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
	 * 属性责任赔款合计(同保单币别)的getter方法
	 */

	@Column(name = "SUMDUTYPAID")
	public double getSumDutyPaid() {
		return this.sumDutyPaid;
	}

	/**
	 * 属性责任赔款合计(同保单币别)的setter方法
	 */
	public void setSumDutyPaid(double sumDutyPaid) {
		this.sumDutyPaid = sumDutyPaid;
	}

	/**
	 * 属性不计入赔款的费用金额(同保单币别)的getter方法
	 */

	@Column(name = "SUMNODUTYFEE")
	public double getSumNoDutyFee() {
		return this.sumNoDutyFee;
	}

	/**
	 * 属性不计入赔款的费用金额(同保单币别)的setter方法
	 */
	public void setSumNoDutyFee(double sumNoDutyFee) {
		this.sumNoDutyFee = sumNoDutyFee;
	}

	/**
	 * 属性总赔付金额(同保单币别)的getter方法
	 */

	@Column(name = "SUMPAID")
	public double getSumPaid() {
		return this.sumPaid;
	}

	/**
	 * 属性总赔付金额(同保单币别)的setter方法
	 */
	public void setSumPaid(double sumPaid) {
		this.sumPaid = sumPaid;
	}

	/**
	 * 属性已预付赔款(同保单币别)的getter方法
	 */

	@Column(name = "SUMPREPAID")
	public double getSumPrePaid() {
		return this.sumPrePaid;
	}

	/**
	 * 属性已预付赔款(同保单币别)的setter方法
	 */
	public void setSumPrePaid(double sumPrePaid) {
		this.sumPrePaid = sumPrePaid;
	}

	/**
	 * 属性本次赔付金额（同保单币别）的getter方法
	 */

	@Column(name = "SUMTHISPAID")
	public double getSumThisPaid() {
		return this.sumThisPaid;
	}

	/**
	 * 属性本次赔付金额（同保单币别）的setter方法
	 */
	public void setSumThisPaid(double sumThisPaid) {
		this.sumThisPaid = sumThisPaid;
	}

	/**
	 * 属性领赔款单位/代理人/索赔人的getter方法
	 */

	@Column(name = "RECEIVERNAME")
	public String getReceiverName() {
		return this.receiverName;
	}

	/**
	 * 属性领赔款单位/代理人/索赔人的setter方法
	 */
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/**
	 * 属性开户银行的getter方法
	 */

	@Column(name = "BANK")
	public String getBank() {
		return this.bank;
	}

	/**
	 * 属性开户银行的setter方法
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}

	/**
	 * 属性银行帐号的getter方法
	 */

	@Column(name = "ACCOUNT")
	public String getAccount() {
		return this.account;
	}

	/**
	 * 属性银行帐号的setter方法
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 属性出单机构的getter方法
	 */

	@Column(name = "MAKECOM")
	public String getMakeCom() {
		return this.makeCom;
	}

	/**
	 * 属性出单机构的setter方法
	 */
	public void setMakeCom(String makeCom) {
		this.makeCom = makeCom;
	}

	/**
	 * 属性业务归属机构代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性业务归属机构代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}

	/**
	 * 属性经办人代码的getter方法
	 */

	@Column(name = "HANDLERCODE")
	public String getHandlerCode() {
		return this.handlerCode;
	}

	/**
	 * 属性经办人代码的setter方法
	 */
	public void setHandlerCode(String handlerCode) {
		this.handlerCode = handlerCode;
	}

	/**
	 * 属性归属业务员代码的getter方法
	 */

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return this.handler1Code;
	}

	/**
	 * 属性归属业务员代码的setter方法
	 */
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
	}

	/**
	 * 属性审核人代码的getter方法
	 */

	@Column(name = "APPROVERCODE")
	public String getApproverCode() {
		return this.approverCode;
	}

	/**
	 * 属性审核人代码的setter方法
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}

	/**
	 * 属性最终核赔人代码的getter方法
	 */

	@Column(name = "UNDERWRITECODE")
	public String getUnderWriteCode() {
		return this.underWriteCode;
	}

	/**
	 * 属性最终核赔人代码的setter方法
	 */
	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
	}

	/**
	 * 属性最终核赔人名称的getter方法
	 */

	@Column(name = "UNDERWRITENAME")
	public String getUnderWriteName() {
		return this.underWriteName;
	}

	/**
	 * 属性最终核赔人名称的setter方法
	 */
	public void setUnderWriteName(String underWriteName) {
		this.underWriteName = underWriteName;
	}

	/**
	 * 属性统计年月的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STATISTICSYM")
	public Date getStatisticsYM() {
		return this.statisticsYM;
	}

	/**
	 * 属性统计年月的setter方法
	 */
	public void setStatisticsYM(Date statisticsYM) {
		this.statisticsYM = statisticsYM;
	}

	/**
	 * 属性操作员代码的getter方法
	 */

	@Column(name = "OPERATORCODE")
	public String getOperatorCode() {
		return this.operatorCode;
	}

	/**
	 * 属性操作员代码的setter方法
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * 属性计算机输单日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
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
	 * 属性核赔完成日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "UNDERWRITEENDDATE")
	public Date getUnderWriteEndDate() {
		return this.underWriteEndDate;
	}

	/**
	 * 属性核赔完成日期的setter方法
	 */
	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	/**
	 * 属性核赔标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return this.underWriteFlag;
	}

	/**
	 * 属性核赔标志的setter方法
	 */
	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
	}

	/**
	 * 属性备注的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性备注的setter方法
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
	 * 属性CASETYPE的getter方法
	 */

	@Column(name = "CASETYPE")
	public String getCaseType() {
		return this.caseType;
	}

	/**
	 * 属性CASETYPE的setter方法
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
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
	 * 属性赔偿责任代码的getter方法
	 */

	@Column(name = "INDEMNITYDUTY")
	public String getIndemnityDuty() {
		return this.indemnityDuty;
	}

	/**
	 * 属性赔偿责任代码的setter方法
	 */
	public void setIndemnityDuty(String indemnityDuty) {
		this.indemnityDuty = indemnityDuty;
	}

	/**
	 * 属性最终计算书标志的getter方法
	 */

	@Column(name = "FINALLYFLAG")
	public String getFinallyFlag() {
		return this.finallyFlag;
	}

	/**
	 * 属性最终计算书标志的setter方法
	 */
	public void setFinallyFlag(String finallyFlag) {
		this.finallyFlag = finallyFlag;
	}

	/**
	 * 属性理赔结论的getter方法
	 */

	@Column(name = "RESULT")
	public String getResult() {
		return this.result;
	}

	/**
	 * 属性理赔结论的setter方法
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * 属性本位币赔付金额的getter方法
	 */

	@Column(name = "PAIDCNY")
	public double getPaidCNY() {
		return this.paidCNY;
	}

	/**
	 * 属性本位币赔付金额的setter方法
	 */
	public void setPaidCNY(double paidCNY) {
		this.paidCNY = paidCNY;
	}

	/**
	 * 属性赔付币别和本位币的兑换率的getter方法
	 */

	@Column(name = "EXCHANGERATE")
	public double getExchangeRate() {
		return this.exchangeRate;
	}

	/**
	 * 属性赔付币别和本位币的兑换率的setter方法
	 */
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 属性是否是团单免导标志的getter方法
	 */

	@Column(name = "TERMFLAG")
	public String getTermFlag() {
		return this.termFlag;
	}

	/**
	 * 属性是否是团单免导标志的setter方法
	 */
	public void setTermFlag(String termFlag) {
		this.termFlag = termFlag;
	}

	/**
	 * 属性是否是代付赔款的getter方法
	 */

	@Column(name = "ISPAYFOROTHER")
	public String getIsPayForOther() {
		return this.isPayForOther;
	}

	/**
	 * 属性是否是代付赔款的setter方法
	 */
	public void setIsPayForOther(String isPayForOther) {
		this.isPayForOther = isPayForOther;
	}

	/**
	 * 属性银行帳号的getter方法
	 */

	@Column(name = "ACCOUNTCODE")
	public String getAccountCode() {
		return this.accountCode;
	}

	/**
	 * 属性银行帳号的setter方法
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
	 * 属性开户行的getter方法
	 */

	@Column(name = "BANKNAME")
	public String getBankName() {
		return this.bankName;
	}

	/**
	 * 属性开户行的setter方法
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
	 * 属性帳户归属人证件代码的getter方法
	 */

	@Column(name = "CERTIFICATECODE")
	public String getCertifiCateCode() {
		return this.certifiCateCode;
	}

	/**
	 * 属性帳户归属人证件代码的setter方法
	 */
	public void setCertifiCateCode(String certifiCateCode) {
		this.certifiCateCode = certifiCateCode;
	}

	/**
	 * 属性帳户归属人名称的getter方法
	 */

	@Column(name = "OWNERNAME")
	public String getOwnerName() {
		return this.ownerName;
	}

	/**
	 * 属性帳户归属人名称的setter方法
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
	 * 属性帳户类型的getter方法
	 */

	@Column(name = "ACCOUNTTYPE")
	public String getAccountType() {
		return this.accountType;
	}

	/**
	 * 属性帳户类型的setter方法
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 属性帳户币别的getter方法
	 */

	@Column(name = "ACCOUNTCURRENCY")
	public String getAccountCurrency() {
		return this.accountCurrency;
	}

	/**
	 * 属性帳户币别的setter方法
	 */
	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	/**
	 * 属性业务与帳户关系的getter方法
	 */

	@Column(name = "OWNERSHIP")
	public String getOwnership() {
		return this.ownership;
	}

	/**
	 * 属性业务与帳户关系的setter方法
	 */
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	/**
	 * 属性被保险人联系电话的getter方法
	 */

	@Column(name = "INSUREDPHONENUMBER")
	public String getInsuredPhoneNumber() {
		return this.insuredPhoneNumber;
	}

	/**
	 * 属性被保险人联系电话的setter方法
	 */
	public void setInsuredPhoneNumber(String insuredPhoneNumber) {
		this.insuredPhoneNumber = insuredPhoneNumber;
	}

	/**
	 * 属性例外事项的getter方法
	 */

	@Column(name = "EXCEPTIONS")
	public String getExceptions() {
		return this.exceptions;
	}

	/**
	 * 属性例外事项的setter方法
	 */
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}

	/**
	 * 属性reason的getter方法
	 */

	@Column(name = "REASON")
	public String getReason() {
		return this.reason;
	}

	/**
	 * 属性reason的setter方法
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	@Transient
	public Date getDamageStartDate() {
		return damageStartDate;
	}

	public void setDamageStartDate(Date damageStartDate) {
		this.damageStartDate = damageStartDate;
	}

	/**
	 * 得到车牌号
	 * @return 车牌号
	 */
	@Transient
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 设置车牌号
	 * @param licenseNo 车牌号
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 得到总保费
	 * @return 总保费
	 */
	@Transient
	public double getSumAmount() {
		return sumAmount;
	}

	/**
	 * 设置总保费
	 * @param sumAmount 总保费
	 */
	public void setSumAmount(double sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 获取属性出险原因代码
	 * @return 属性出险原因代码的值
	 */
	@Transient
	public String getDamageCode() {
		return damageCode;
	}

	/**
	 * 设置属性出险原因代码
	 * @param damageCode 待设置的属性出险原因代码的值
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = StringUtils.rightTrim(damageCode);
	}

	/**
	 * 设置被保险人名称
	 * @param insuredName 被保险人名称
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 得到被保险人名称
	 * @return 被保险人名称
	 */
	@Transient
	public String getInsuredName() {
		return insuredName;
	}

	/**
	 * 设置属性出险原因说明
	 * @param damageName 待设置的属性出险原因说明的值
	 */
	public void setDamageName(String damageName) {
		this.damageName = StringUtils.rightTrim(damageName);
	}

	/**
	 * 获取属性出险原因说明
	 * @return 属性出险原因说明的值
	 */
	@Transient
	public String getDamageName() {
		return damageName;
	}

	/**
	 * 设置属性报案号
	 * @return 属性报案号
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 获取属性报案号
	 * @return 属性报案号
	 */
	@Transient
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 集合
	 * @param compensateList 集合
	 */
	public void setCompensateList(List<PrpLcompensate> compensateList) {
		this.compensateList = compensateList;
	}

	/**
	 * 设置集合
	 * @param compensateList 集合
	 */
	@Transient
	public List<PrpLcompensate> getCompensateList() {
		return compensateList;
	}

	/**
	 * 设置编辑类型
	 * @param editType 编辑类型
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 设置编辑类型
	 * @param editType 编辑类型
	 */
	@Transient
	public String getEditType() {
		return editType;
	}

	/**
	 * 设置此立案的操作状态
	 * @param prpLclaimDto 此立案的操作状态
	 */
	public void setStatus(String status) {
		//mantis：CLM0211，處理人員：DP0713，需求單編號：新核心-CA工程險理算處理畫面舊有按鈕移除
		System.out.println("CLM0211_prpl status:"+status);
		this.status = status;
	}

	/**
	 * 设置此立案的操作状态
	 * @param status 此立案的操作状态
	 */
	@Transient
	public String getStatus() {
		return status;
	}

	@Transient
	public double getSumCheckFee() {
		return sumCheckFee;
	}

	public void setSumCheckFee(double sumCheckFee) {
		this.sumCheckFee = sumCheckFee;
	}

	@Transient
	public String getEscapeFlag() {
		return escapeFlag;
	}

	public void setEscapeFlag(String escapeFlag) {
		this.escapeFlag = escapeFlag;
	}

	@Transient
	public String getClauseTypeCode() {
		return clauseTypeCode;
	}

	public void setClauseTypeCode(String clauseTypeCode) {
		this.clauseTypeCode = clauseTypeCode;
	}

	@Transient
	public String getDeductibleTerm() {
		return deductibleTerm;
	}

	public void setDeductibleTerm(String deductibleTerm) {
		this.deductibleTerm = deductibleTerm;
	}

	@Transient
	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	/**
	 * 设置属性业务来源
	 * @param businessNature 业务来源
	 */
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	/**
	 * 获取属性业务来源
	 * @return 属性业务来源
	 */
	@Transient
	public String getBusinessNature() {
		return businessNature;
	}

	@Transient
	public double getSumCoinUs() {
		return sumCoinUs;
	}

	public void setSumCoinUs(double sumCoinUs) {
		this.sumCoinUs = sumCoinUs;
	}

	@Transient
	public double getSumCoinUsFee() {
		return sumCoinUsFee;
	}

	public void setSumCoinUsFee(double sumCoinUsFee) {
		this.sumCoinUsFee = sumCoinUsFee;
	}

	@Transient
	public double getSumCoinForOther() {
		return sumCoinForOther;
	}

	public void setSumCoinForOther(double sumCoinForOther) {
		this.sumCoinForOther = sumCoinForOther;
	}

	@Transient
	public double getSumCoinForOtherFee() {
		return sumCoinForOtherFee;
	}

	public void setSumCoinForOtherFee(double sumCoinForOtherFee) {
		this.sumCoinForOtherFee = sumCoinForOtherFee;
	}

	/**
	 * 属性历次赔付金额汇总信息
	 * @return 属性历次赔付金额汇总信息
	 */
	public void setSumPaidAll(double sumPaidAll) {
		this.sumPaidAll = sumPaidAll;
	}

	/**
	 * 属性历次赔付金额汇总信息
	 * @return 属性历次赔付金额汇总信息
	 */
	@Transient
	public double getSumPaidAll() {
		return this.sumPaidAll;
	}

	/**
	 * 设置经办人名称
	 * @param handlerName 经办人名称
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 得到经办人名称
	 * @return 经办人名称
	 */
	@Transient
	public String getHandlerName() {
		return handlerName;
	}

	/**
	 * 设置被保险人代码
	 * @param insuredCode 被保险人代码
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 得到被保险人代码
	 * @return 被保险人代码
	 */
	@Transient
	public String getInsuredCode() {
		return insuredCode;
	}

	/**
	 * 得到起保日期
	 * @return 起保日期
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 设置起保日期
	 * @param startDate 起保日期
	 */
	@Transient
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * 设置出险地点分类代码
	 * @param damageAddressType 出险地点分类代码
	 */
	public void setDamageAddressType(String damageAddressType) {
		this.damageAddressType = damageAddressType;
	}

	/**
	 * 得到出险地点分类代码
	 * @return 出险地点分类代码
	 */
	@Transient
	public String getDamageAddressType() {
		return damageAddressType;
	}

	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 START
	/**
	 * 得到出险地点代碼
	 * @return 出险地点代碼
	 */
	@Transient
	public String getAddressCode() {
		return addressCode;
	}

	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	//mantis：CLM0274 ，處理人員：DP0713，需求單編號：新核心-個人旅行海外突發疾病法傳-增加事故地區保額規則 END

	/**
	 * 得到出险地点
	 * @return 出险地点
	 */
	@Transient
	public String getDamageAddress() {
		return damageAddress;
	}

	/**
	 * 设置出险地点
	 * @param damageAddress 出险地点
	 */
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}

	/**
	 * 设置终保日期
	 * @param endDate 终保日期
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 得到终保日期
	 * @return 终保日期
	 */
	@Transient
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 设置起保小时
	 * @param startHour 起保小时
	 */
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	/**
	 * 得到起保小时
	 * @return 起保小时
	 */
	@Transient
	public int getStartHour() {
		return startHour;
	}

	/**
	 * 设置终保小时
	 * @param endHour 终保小时
	 */
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	/**
	 * 得到终保小时
	 * @return 终保小时
	 */
	@Transient
	public int getEndHour() {
		return endHour;
	}

	/**
	 * 设置条款类别
	 * @param clauseType 条款类别
	 */
	public void setClauseType(String clauseType) {
		this.clauseType = clauseType;
	}

	/**
	 * 设置出险开始小时
	 * @param damageStartHour 出险开始小时
	 */
	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}

	/**
	 * 得到出险开始小时
	 * @return 出险开始小时
	 */
	@Transient
	public String getDamageStartHour() {
		return damageStartHour;
	}

	@Transient
	public String getDamageStartMinute() {
		return damageStartMinute;
	}

	public void setDamageStartMinute(String damageStartMinute) {
		this.damageStartMinute = damageStartMinute;
	}

	/**
	 * 得到条款类别
	 * @return 条款类别
	 */
	@Transient
	public String getClauseType() {
		return clauseType;
	}

	/**
	 * 设置总保费
	 * @param sumPremium 总保费
	 */
	public void setSumPremium(double sumPremium) {
		this.sumPremium = sumPremium;
	}

	/**
	 * 得到总保费
	 * @return 总保费
	 */
	@Transient
	public double getSumPremium() {
		return sumPremium;
	}

	/**
	 * 设置保险损失金额
	 * @param sumClaim 保险损失金额
	 */
	public void setSumClaim(double sumClaim) {
		this.sumClaim = sumClaim;
	}

	/**
	 * 得到保险损失金额
	 * @return 保险损失金额
	 */
	@Transient
	public double getSumClaim() {
		return sumClaim;
	}

	/**
	 * 设置属性赔案类型名称
	 * @param caseTypeName 待设置的属性赔案类型名称的值
	 */
	public void setCaseTypeName(String caseTypeName) {
		this.caseTypeName = StringUtils.rightTrim(caseTypeName);
	}

	/**
	 * 获取属性赔案类型名称
	 * @return 属性赔案类型名称的值
	 */
	@Transient
	public String getCaseTypeName() {
		return caseTypeName;
	}

	/**
	 * 设置属性案件类型
	 * @param claimType 待设置的属性案件类型的值
	 */
	public void setClaimType(String claimType) {
		this.claimType = StringUtils.rightTrim(claimType);
	}

	/**
	 * 获取属性案件类型
	 * @return 属性案件类型的值
	 */
	@Transient
	public String getClaimType() {
		return claimType;
	}

	/**
	 * 设置属性赔案类别名称
	 * @param claimTypeName 待设置的属性赔案类别名称的值
	 */
	public void setClaimTypeName(String claimTypeName) {
		this.claimTypeName = StringUtils.rightTrim(claimTypeName);
	}

	/**
	 * 获取属性赔案类别名称
	 * @return 属性赔案类别名称的值
	 */
	@Transient
	public String getClaimTypeName() {
		return claimTypeName;
	}

	@Transient
	public String getDefaultCompensateResult() {
		// 默认为给付(代码为"1")
		return "1";
	}

	/**
	 * 设置属性投保人名称
	 * @param appliName 待设置的属性投保人名称的值
	 */
	public void setAppliName(String appliName) {
		this.appliName = StringUtils.rightTrim(appliName);
	}

	/**
	 * 获取属性投保人名称
	 * @return 属性投保人名称的值
	 */
	@Transient
	public String getAppliName() {
		return appliName;
	}

	/**
	 * 属性币别中文名称
	 * @return 属性币别中文名称
	 */
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	/**
	 * 属性币别中文名称
	 * @return 属性币别中文名称
	 */
	@Transient
	public String getCurrencyName() {
		return this.currencyName;
	}

	/**
	 * 得到已交保费信息
	 * @return 已交保费
	 */
	@Transient
	public int getPayFee() {
		return payFee;
	}

	/**
	 * 设置已交保费信息
	 * @param payFee 已交保费
	 */
	public void setPayFee(int payFee) {
		this.payFee = payFee;
	}

	/**
	 * 设置车牌底色代码
	 * @param licenseColorCode 车牌底色代码
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = licenseColorCode;
	}

	/**
	 * 得到车牌底色代码
	 * @return 车牌底色代码
	 */
	@Transient
	public String getLicenseColorCode() {
		return licenseColorCode;
	}

	/**
	 * 设置厂牌型号
	 * @param brandName 厂牌型号
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * 得到厂牌型号
	 * @return 厂牌型号
	 */
	@Transient
	public String getBrandName() {
		return brandName;
	}

	/**
	 * 设置车辆种类代码
	 * @param carKindCode 车辆种类代码
	 */
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = carKindCode;
	}

	/**
	 * 得到车辆种类代码
	 * @return 车辆种类代码
	 */
	@Transient
	public String getCarKindCode() {
		return carKindCode;
	}

	/**
	 * 设置发动机号
	 * @param engineNo 发动机号
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	/**
	 * 得到发动机号
	 * @return 发动机号
	 */
	@Transient
	public String getEngineNo() {
		return engineNo;
	}

	/**
	 * 设置车架号
	 * @param frameNo 车架号
	 */
	public void setFrameNo(String frameNo) {
		this.frameNo = frameNo;
	}

	/**
	 * 得到车架号
	 * @return 车架号
	 */
	@Transient
	public String getFrameNo() {
		return frameNo;
	}

	/**
	 * 设置属性是否可能有追偿
	 * @param replevyFlag 待设置的属性是否可能有追偿的值
	 */
	public void setReplevyFlag(String replevyFlag) {
		this.replevyFlag = StringUtils.rightTrim(replevyFlag);
	}

	/**
	 * 获取属性是否可能有追偿
	 * @return 属性是否可能有追偿的值
	 */
	@Transient
	public String getReplevyFlag() {
		return replevyFlag;
	}

	public void setPalyFlag(int palyFlag) {
		// 自动生成方法存根
		this.palyFlag = palyFlag;

	}

	@Transient
	public int getPalyFlag() {
		return this.palyFlag;
	}

	/**
	 * 得到座位数
	 * @return 座位数
	 */
	@Transient
	public String getSeatCount() {
		return seatCount;
	}

	/**
	 * 设置座位数
	 * @param seatCount 座位数
	 */
	public void setSeatCount(String seatCount) {
		this.seatCount = seatCount;
	}

	/**
	 * 设置查勘/代查勘人1
	 * @param checker1 查勘/代查勘人1
	 */
	public void setChecker1(String checker1) {
		this.checker1 = checker1;
	}

	/**
	 * 得到查勘/代查勘人1
	 * @return 查勘/代查勘人1
	 */
	@Transient
	public String getChecker1() {
		return checker1;
	}

	/**
	 * 得到条款名称
	 * @return 条款名称
	 */
	@Transient
	public String getClauseName() {
		return clauseName;
	}

	/**
	 * 设置条款名称
	 * @param clauseName 条款名称
	 */
	public void setClauseName(String clauseName) {
		this.clauseName = clauseName;
	}

	/**
	 * 设置车辆种类
	 * @param carKind 车辆种类
	 */

	public void setCarKind(String carKind) {
		this.carKind = carKind;
	}

	/**
	 * 设置车辆种类
	 * @param prpLclaimDto 车辆种类
	 */
	@Transient
	public String getCarKind() {
		return carKind;
	}

	/**
	 * 设置车牌底色
	 * @param prpLclaimDto 车牌底色
	 */
	@Transient
	public String getLicenseColor() {
		return licenseColor;
	}

	/**
	 * 设置车牌底色
	 * @param licenseColor 车牌底色
	 */
	public void setLicenseColor(String licenseColor) {
		this.licenseColor = licenseColor;
	}

	/**
	 * 设置部门名称
	 * @param prpLclaimDto 部门名称
	 */
	public void setComName(String comName) {
		this.comName = comName;
	}

	/**
	 * 设置部门名称
	 * @param comName 部门名称
	 */
	@Transient
	public String getComName() {
		return comName;
	}

	@Transient
	public String getIndemnityDutyName() {
		return indemnityDutyName;
	}

	public void setIndemnityDutyName(String indemnityDutyName) {
		this.indemnityDutyName = indemnityDutyName;
	}

	@Transient
	public String getHandler1Name() {
		return handler1Name;
	}

	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}

	@Transient
	public Date getDamageEndDate() {
		return damageEndDate;
	}

	public void setDamageEndDate(Date damageEndDate) {
		this.damageEndDate = damageEndDate;
	}

	@Transient
	public String getDamageEndHour() {
		return damageEndHour;
	}

	public void setDamageEndHour(String damageEndHour) {
		this.damageEndHour = damageEndHour;
	}

	@Transient
	public double getSumDutyPaid1() {
		return sumDutyPaid1;
	}

	public void setSumDutyPaid1(double sumDutyPaid1) {
		this.sumDutyPaid1 = sumDutyPaid1;
	}

	@Transient
	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Column(name = "PAYSELFFLAG")
	public String getPayselfFlag() {
		return payselfFlag;
	}

	public void setPayselfFlag(String payselfFlag) {
		this.payselfFlag = payselfFlag;
	}

	@Column(name = "PAYCODE")
	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	@Column(name = "LOSSTYPE")
	public String getLossType() {
		return lossType;
	}

	public void setLossType(String lossType) {
		this.lossType = lossType;
	}

	@Column(name = "ACCIDENTTYPE")
	public String getAccidentType() {
		return accidentType;
	}

	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}

	@Column(name = "OPPOSITEINDEMNITYDUTY")
	public Double getOppositeIndemnityDuty() {
		return oppositeIndemnityDuty;
	}

	public void setOppositeIndemnityDuty(Double oppositeIndemnityDuty) {
		this.oppositeIndemnityDuty = oppositeIndemnityDuty;
	}

	@Column(name = "OTHERINDEMNITYDUTY")
	public Double getOtherIndemnityDuty() {
		return otherIndemnityDuty;
	}

	public void setOtherIndemnityDuty(Double otherIndemnityDuty) {
		this.otherIndemnityDuty = otherIndemnityDuty;
	}

	@Column(name = "INDEPENDENTCOSTS")
	public Double getIndependentCosts() {
		return independentCosts;
	}

	public void setIndependentCosts(Double independentCosts) {
		this.independentCosts = independentCosts;
	}

	@Column(name = "REMNANTS")
	public String getRemnants() {
		return remnants;
	}

	public void setRemnants(String remnants) {
		this.remnants = remnants;
	}

	@Column(name = "CHASINGLOSSESSTATUS")
	public String getChasingLossesStatus() {
		return chasingLossesStatus;
	}

	public void setChasingLossesStatus(String chasingLossesStatus) {
		this.chasingLossesStatus = chasingLossesStatus;
	}

	@Column(name = "paySituation")
	public String getPaySituation() {
		return paySituation;
	}

	public void setPaySituation(String paySituation) {
		this.paySituation = paySituation;
	}

	@Transient
	public String getRiskCodeName() {
		return riskCodeName;
	}

	public void setRiskCodeName(String riskCodeName) {
		this.riskCodeName = riskCodeName;
	}

	@Transient
	public Date getRemnantDate() {
		return remnantDate;
	}

	public void setRemnantDate(Date remnantDate) {
		this.remnantDate = remnantDate;
	}

	@Column(name = "NOPAIDCLAIM")
	public String getNoPaidClaim() {
		return noPaidClaim;
	}

	public void setNoPaidClaim(String noPaidClaim) {
		this.noPaidClaim = noPaidClaim;
	}

	@Column(name = "FILEREADYDATE")
	public String getFileReadyDate() {
		return fileReadyDate;
	}

	public void setFileReadyDate(String fileReadyDate) {
		this.fileReadyDate = fileReadyDate;
	}

	@Transient
	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	@Column(name = "PAYCODETYPE")
	public String getPayCodeType() {
		return payCodeType;
	}

	public void setPayCodeType(String payCodeType) {
		this.payCodeType = payCodeType;
	}

	@Column(name = "OPPOSITECLAIMNO")
	public String getOppositeClaimNo() {
		return oppositeClaimNo;
	}

	public void setOppositeClaimNo(String oppositeClaimNo) {
		this.oppositeClaimNo = oppositeClaimNo;
	}

	@Column(name = "OPPOSITECLAIMOFFICER")
	public String getOppositeClaimOfficer() {
		return oppositeClaimOfficer;
	}

	public void setOppositeClaimOfficer(String oppositeClaimOfficer) {
		this.oppositeClaimOfficer = oppositeClaimOfficer;
	}

	@Column(name = "TOTALTIMES")
	public int getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	@Column(name = "REPLEVYTIMES")
	public int getReplevyTimes() {
		return replevyTimes;
	}

	public void setReplevyTimes(int replevyTimes) {
		this.replevyTimes = replevyTimes;
	}

	@Column(name = "MUTUALCOMPENSATENO")
	public String getMutualCompensateNo() {
		return mutualCompensateNo;
	}

	public void setMutualCompensateNo(String mutualCompensateNo) {
		this.mutualCompensateNo = mutualCompensateNo;
	}

	@Column(name = "COMPELPAYTYPE")
	public String getCompelPayType() {
		return compelPayType;
	}

	public void setCompelPayType(String compelPayType) {
		this.compelPayType = compelPayType;
	}

	@Column(name = "subrogation")
	public String getSubrogation() {
		return subrogation;
	}

	public void setSubrogation(String subrogation) {
		this.subrogation = subrogation;
	}

	@Column(name = "speedFlag")
	public String getSpeedFlag() {
		return speedFlag;
	}

	public void setSpeedFlag(String speedFlag) {
		this.speedFlag = speedFlag;
	}

	@Column(name = "underWriteDeptCode")
	public String getUnderWriteDeptCode() {
		return underWriteDeptCode;
	}

	public void setUnderWriteDeptCode(String underWriteDeptCode) {
		this.underWriteDeptCode = underWriteDeptCode;
	}

	@Column(name = "SHIPCNAME")
	public String getShipCName() {
		return shipCName;
	}

	public void setShipCName(String shipCName) {
		this.shipCName = shipCName;
	}

	@Column(name = "SHIPMODEL")
	public String getShipModel() {
		return shipModel;
	}

	public void setShipModel(String shipModel) {
		this.shipModel = shipModel;
	}

	@Column(name = "NATIONALITYCODE")
	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	@Column(name = "CLAIMAGENT")
	public String getClaimAgent() {
		return claimAgent;
	}

	public void setClaimAgent(String claimAgent) {
		this.claimAgent = claimAgent;
	}

	@Transient
	public Date getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
	}

	@Transient
	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	@Column(name = "CONTEXTNO")
	public String getContextNo() {
		return contextNo;
	}

	public void setContextNo(String contextNo) {
		this.contextNo = contextNo;
	}

	@Column(name = "STARTSITECOUNTRY")
	public String getStartSiteCountry() {
		return startSiteCountry;
	}

	public void setStartSiteCountry(String startSiteCountry) {
		this.startSiteCountry = startSiteCountry;
	}

	@Column(name = "STARTSITEPORT")
	public String getStartSitePort() {
		return startSitePort;
	}

	public void setStartSitePort(String startSitePort) {
		this.startSitePort = startSitePort;
	}

	@Column(name = "ENDSITECOUNTRY")
	public String getEndSiteCountry() {
		return endSiteCountry;
	}

	public void setEndSiteCountry(String endSiteCountry) {
		this.endSiteCountry = endSiteCountry;
	}

	@Column(name = "ENDSITEPORT")
	public String getEndSitePort() {
		return endSitePort;
	}

	public void setEndSitePort(String endSitePort) {
		this.endSitePort = endSitePort;
	}

	@Column(name = "INFORMREINSFLAG")
	public String getInformReinsFlag() {
		return informReinsFlag;
	}

	public void setInformReinsFlag(String informReinsFlag) {
		this.informReinsFlag = informReinsFlag;
	}

	@Column(name = "OTHERPOLICYNO")
	public String getOtherPolicyNo() {
		return otherPolicyNo;
	}

	public void setOtherPolicyNo(String otherPolicyNo) {
		this.otherPolicyNo = otherPolicyNo;
	}

	@Column(name = "OTHERCLAIMNO")
	public String getOtherClaimNo() {
		return otherClaimNo;
	}

	public void setOtherClaimNo(String otherClaimNo) {
		this.otherClaimNo = otherClaimNo;
	}

	@Transient
	public String getSailScope() {
		return sailScope;
	}

	public void setSailScope(String sailScope) {
		this.sailScope = sailScope;
	}

	@Column(name = "IDNUMBER")
	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Column(name = "CONTACTTELEPHONE")
	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	@Column(name = "CONTACTADDRESS")
	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	@Transient
	public String getPolicyYear() {
		return policyYear;
	}

	public void setPolicyYear(String policyYear) {
		this.policyYear = policyYear;
	}

	@Transient
	public Date getSailStartDate() {
		return sailStartDate;
	}

	public void setSailStartDate(Date sailStartDate) {
		this.sailStartDate = sailStartDate;
	}

	@Transient
	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	@Transient
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return coinsFlag;
	}

	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}
	@Transient
	public String getSameAddressNo() {
		return sameAddressNo;
	}

	public void setSameAddressNo(String sameAddressNo) {
		this.sameAddressNo = sameAddressNo;
	}
	@Transient
	public String getReplevyRemark() {
		return replevyRemark;
	}

	public void setReplevyRemark(String replevyRemark) {
		this.replevyRemark = replevyRemark;
	}
	/***  add by chenjie 20150601 需求變更-095 begin ***/
	@Column(name = "propAccidentType")
	public String getPropAccidentType() {
		return propAccidentType;
	}

	public void setPropAccidentType(String propAccidentType) {
		this.propAccidentType = propAccidentType;
	}
	/***  add by chenjie 20150601 需求變更-095 end ***/
	


	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 START
	@Column(name = "HOSPITALIZEDDAYS")
	public Integer getHospitalizedDays() {
		return hospitalizedDays;//本次住院天數
	}

	public void setHospitalizedDays(Integer hospitalizedDays) {
		this.hospitalizedDays = hospitalizedDays;
	}
	
	//@Column(name = "SUMHOSPITALIZEDDAY") 不需要DB 但是需要一個位置放加總  
	@Transient
	public Integer getSumHospitalizedDay() {
		return sumHospitalizedDay;//本次事故累計住院天數(不含本次)
	}

	public void setSumHospitalizedDay(Integer sumHospitalizedDay) {
		this.sumHospitalizedDay = sumHospitalizedDay;
	}

	@Transient
	public double getPaf4SumLoss() {
		return paf4SumLoss;
	}

	public void setPaf4SumLoss(double paf4SumLoss) {
		this.paf4SumLoss = paf4SumLoss;
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 START
	//@Column(name = "ISCOMPULSORYBCHAINCLAIM")
	//mantis：CLM0282、CLM9009 ，處理人員： DP0713 ，需求單編號：多元理賠備案_非車全險種
	@Transient
	public String getIsCompulsoryBchainClaim() {
		return isCompulsoryBchainClaim;
	}

	public void setIsCompulsoryBchainClaim(String isCompulsoryBchainClaim) {
		this.isCompulsoryBchainClaim = isCompulsoryBchainClaim;
	}
//	@Column(name = "SHARINGFLAG")
//	public String getSharingFlag() {
//		return sharingFlag;
//	}
//
//	public void setSharingFlag(String sharingFlag) {
//		this.sharingFlag = sharingFlag;
//	}
	
	//mantis：CLM0277 ，處理人員： DP0713 ，需求單編號：理算任務串聯區塊鏈API3.10同業確認+API3.5建立理賠單 END
}
