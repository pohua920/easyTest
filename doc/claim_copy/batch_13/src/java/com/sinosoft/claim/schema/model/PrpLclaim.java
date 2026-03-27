package com.sinosoft.claim.schema.model;

// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * POJO类PrpLclaim立案基本信息表对象
 */
@Entity
@Table(name = "PRPLCLAIM")
public class PrpLclaim implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** 属性立案号码 */
	private String claimNo = "";

	/** 属性理赔类型 */
	private String lflag = "";

	/** 属性赔案号 */
	private String caseNo = "";

	/** 属性险类代码 */
	private String classCode = "";

	/** 属性险种代码 */
	private String riskCode = "";

	/** 属性险种名称 */
	private String riskCodeName = "";

	/** 属性报案号 */
	private String registNo = "";

	/** 属性保单号 */
	private String policyNo = "";

	/** 属性业务来源（直接/代理） */
	private String businessNature = "";

	/** 属性语种标志 */
	private String language = "";

	/** 属性保单类型 */
	private String policyType = "";

	/** 属性被保险人代码 */
	private String insuredCode = "";

	/** 属性被保险人名称 */
	private String insuredName = "";

	/** 属性起保日期（启运日期） */
	private Date startDate;

	/** 属性起保小时 */
	private Integer startHour;

	/** 属性终保日期 */
	private Date endDate;

	/** 属性终保小时 */
	private Integer endHour;

	/** 属性币别代码 */
	private String currency = "";

	/** 属性总保险金额 */
	private Double sumAmount;

	/** 属性原总保险费 */
	private Double sumPremium;

	/** 属性被保险总数量 */
	private Long sumQuantity;

	/** 属性出险日期起 */
	private Date damageStartDate;

	/** 属性出险开始小时 */
	private String damageStartHour = "";

	/** 属性出险日期止 */
	private Date damageEndDate;

	/** 属性出险终止小时 */
	private String damageEndHour = "";

	/** 属性出险原因代码 */
	private String damageCode = "";

	/** 属性出险原因名称 */
	private String damageName = "";

	/** 属性事故类型代码 */
	private String damageTypeCode = "";

	/** 属性事故类型说明 */
	private String damageTypeName = "";

	/** 属性出险区域代码 */
	private String damageAreaCode = "";

	/** 属性出险区域名称 */
	private String damageAreaName = "";

	/** 属性出险地点分类代码 */
	private String damageAddressType = "";

	/** 属性出险地代码 */
	private String addressCode = "";

	/** 属性出险地名称 */
	private String addressName = "";

	/** 属性出险地点 */
	private String damageAddress = "";

	/** 属性受损标的 */
	private String lossName = "";

	/** 属性受损标的数量/出险分户数 */
	private Long lossQuantity;

	/** 属性出险险别 */
	private String damageKind = "";

	/** 属性立案日期 */
	private Date claimDate;

	/** 属性赔偿责任代码 */
	private String indemnityDuty = "";

	/** 属性责任比例 */
	private Double indemnityDutyRate;

	/** 属性免赔率 */
	private Double deductibleRate;

	/** 属性保险损失金额 */
	private Double sumClaim;

	/** 属性总核定损金额 */
	private Double sumDefLoss;

	/** 属性总赔付金额 */
	private Double sumPaid;

	/** 属性总追偿金额 */
	private Double sumReplevy;

	/** 属性备注 */
	private String remark = "";

	/** 属性案件性质 */
	private String caseType = "";

	/** 属性出单机构 */
	private String makeCom = "";

	/** 属性业务归属机构代码 */
	private String comCode = "";

	/** 属性代理人代码 */
	private String agentCode = "";

	/** 属性经办人代码 */
	private String handlerCode = "";

	/** 属性归属业务员代码 */
	private String handler1Code = "";

	/** 属性保单统计年月 */
	private Date statisticsYM;

	/** 属性操作员代码 */
	private String operatorCode = "";

	/** 属性操作员名称 */
	private String operatorName = "";

	/** 属性计算机输单日期 */
	private Date inputDate;

	/** 属性结案日期 */
	private Date endCaseDate;

	/** 属性结案员代码 */
	private String endCaserCode = "";

	/** 属性注销/拒赔日期 */
	private Date cancelDate;

	/** 属性注销/拒赔原因 */
	private String cancelReason = "";

	/** 属性注销/拒赔人代码 */
	private String dealerCode = "";

	/** 属性是否为逃逸案 */
	private String escapeFlag = "";

	/** 属性状态字段 */
	private String flag = "";

	/** 属性THIRDCOMFLAG */
	private String thirdComFlag = "";

	/** 属性REPLEVYFLAG */
	private String replevyFlag = "";

	/** 属性计算书责任比例 */
	private Double cindemnityDutyRate;

	/** 属性是否自动结案 */
	private String endCaseFlag = "";

	/** 属性巨灾一级代码 */
	private String catastropheCode1 = "";

	/** 属性巨灾一级名称 */
	private String catastropheName1 = "";

	/** 属性巨灾二级代码 */
	private String catastropheCode2 = "";

	/** 属性巨灾二级名称 */
	private String catastropheName2 = "";

	/** 属性出险地点邮政编码 */
	private String damageAreaPostCode = "";

	/** 属性赔案类别 */
	private String claimType = "";

	/** 属性追偿时效 */
	private Date replevyLimitDate;

	/** 属性是否免导团单标志 */
	private String termFlag = "";

	/** 属性接收客户索赔申请时间 */
	private Date startApplyPayDate;

	/** 属性是否涉及诉讼 */
	private String referLawFlag = "";
	/** 属性收費情形 */
	private String chargeType = "";

	/** 属性是否涉及担保 */
	private String guaranteeFlag = "";

	/** 属性出险终止分钟 */
	private String damageEndMinute = "";

	/** 属性条款类别 */
	private String clauseType = "";

	/** 属性条款名称 */
	private String clauseName = "";

	/** 号牌号码 */
	private String licenseNo = "";

	/** 号牌底色代码 */
	private String licenseColorCode = "";

	/** 号牌底色名称 */
	private String licenseColor = "";

	/** 厂牌型号 */
	private String brandName = "";

	/** 车辆种类 */
	private String carKindCode = "";
	private String carKind = "";

	/** 属性出险开始分钟 */
	private String damageStartMinute = "";

	/** 属性部门名称 */
	private String comName = "";

	/** 属性归属业务员名称 */
	private String handler1Name = "";

	/** 属性代理人名称 */
	private String agentName = "";

	/** 属性经办人名称 */
	private String handlerName = "";

	/** 属性出险次数 */
	private int perilCount = 0;

	/** 属性逃逸标志的第二位 */
	private String escapeFlag2 = "";

	/** 此立案的操作状态 1。未处理 2。正在处理 3。已完成 4。已提交 5。 撤消 */
	private String status = "";

	/** 属性AcciCode */
	private String acciCode = "";
	/** 属性AcciName */
	private String acciName = "";
	/** 属性Sex */
	private String sex = "";
	/** 属性Age */
	private Integer age;
	/** 属性IdentifyNumber */
	private String identifyNumber = "";
	/** 被保险人序号 */
	private int familyNo = 0;

	/** 属性币别estiCurrency */
	private String estiCurrency = "";

	/** 属性赔案类别名称 */
	private String claimTypeName = "";

	private String businessNatureName = ""; // 属性案件性质
	private String languageName = ""; // 语种名称
	/** 属性理赔登记机构名称 */
	private String makeComName = "";
	/** 属性拒赔操作人名称 */
	private String dealerName = "";
	/** 编辑状态，如Eidt,Add等等 */
	private String editType = "";
	/** 列表 */
	List<PrpLclaim> claimList;

	/** 属性此查勘案件的操作时间 */
	private Date operateDate = new Date();
	/** 此报案时间 */
	private Date reportDate = new Date();

	/** 被保险人类别 */
	private String customerType = "";

	/** 属性赔偿责任名称 */
	private String indemnityDutyName = "";

	/** 核保日期 */
	private Date underWriteEndDate = null;
	/** 标志位信息 */
	private String othFlag = "";

	/** 属性prpLltexts */
	private List<PrpLltext> prpLltexts = new ArrayList<PrpLltext>(0);

	/** 属性prpLclaimFees */
	private List<PrpLclaimFee> prpLclaimFees = new ArrayList<PrpLclaimFee>(0);

	/** 属性prpLclaimLosses */
	private List<PrpLclaimLoss> prpLclaimLosses = new ArrayList<PrpLclaimLoss>(0);

	/** 属性prpLdocs */
	private List<PrpLdoc> prpLdocs = new ArrayList<PrpLdoc>(0);
	// 页面展示使用
	private String configCode = null;
	private String endCaserName = "";// 结案员姓名
	/** 属性币别 */
	private String currencyName = "";
	/** 收件时间，精确到分钟 */
	private String receiptDate = "";
	/** 理賠確認日 (对应prplremnant中的remnantDate) */
	private Date remnantDate;

	/** 属性处理部门 */
	private String handleDept;
	/** 追償狀態 0:未登錄，1：未處理可修改；2：已處理待審核 */
	private String hasReplevy = "0";
	/** 貨物編號 */
	private String cargoNo = "";
	/** 貨物名稱 */
	private String cargoName = "";
	/** 共同海損 ，0-否 ， 1-是 */
	private String generalAverage = "";
	/** 運輸方式 ,1-海運 2-空運 3-快遞 4-郵包 5-台灣本島內陸運輸 6-小三通 */
	private String transportType = "";
	/** 追溯日 */
	private Date bkWardStartDate;
	/** 行業職業代號 */
	private String businessCareerCode = "";
	/** 行業職業名称 */
	private String businessCareerName = "";
	/** 危險分類總項 */
	private String dangerousClassItem = "";
	/** 危險分類細項 */
	private String dangerousClassSubItem = "";
	/** 危險分類細項名称 */
	private String dangerousClassSubItemName = "";
	/** 專案代號 */
	private String projectCode = "";
	/** 共保信息 */
	private String coinsFlag = "";
	/** 开航日期 */
	private String sailStartDate = "";
	/** 保单年度 */
	private String policyInputDate = "";
	/** 建造年份 */
	private String makeDate = "";
	/** 批单号码 */
	private String endorseNo = "";
	/** 航程 始發国家 */
	private String startSiteCountry = "";
	/** 航程 始發港口 */
	private String startSitePort = "";
	/** 航程 終達国家 */
	private String endSiteCountry = "";
	/** 航程 終達港口 */
	private String endSitePort = "";
		/** 水险 - 船名 */
	private String shipCName = "";
	/** 理賠代理 */
	private String claimAgent = "";
	/** 地區別代號 */
	private String areaCode = "";
	/** 進出口別代號 */
	private String importType = "";
	/** 同险号码 */
	private String sameAddressNo = "";
	/** 簡易賠案標誌 1，為簡易賠案，0，為正常賠案 */
	private String simpleFlag = "0";
	/** 追偿说明 */
	private String replevyRemark;
	/***  add by chenjie 20150601 需求變更-095 begin ***/
	/** 車損肇事類型 肇事类型：1:有肇责，计次\2:无肇责，不计次\3:有肇责，不计次 */
	private String carAccidentType = "";
	/** 財損肇事類型 肇事类型：1:有肇责，计次\2:无肇责，不计次\3:有肇责，不计次 */
	private String propAccidentType = "";
	/***  add by chenjie 20150601 需求變更-095 end ***/
	
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 
	private Integer hospitalizedDays;

	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return coinsFlag;
	}

	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}

	/**
	 * 类PrpLclaim的默认构造方法
	 */
	public PrpLclaim() {
		escapeFlag2 = "N";
	}

	/**
	 * 属性立案号码的getter方法
	 */
	@Id
	@Column(name = "CLAIMNO")
	public String getClaimNo() {
		return this.claimNo;
	}

	/**
	 * 属性立案号码的setter方法
	 */
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
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
	 * 属性报案号的getter方法
	 */

	@Column(name = "REGISTNO")
	public String getRegistNo() {
		return this.registNo;
	}

	/**
	 * 属性报案号的setter方法
	 */
	public void setRegistNo(String registNo) {
		this.registNo = registNo;
	}

	/**
	 * 属性保单号的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性保单号的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性业务来源（直接/代理）的getter方法
	 */

	@Column(name = "BUSINESSNATURE")
	public String getBusinessNature() {
		return this.businessNature;
	}

	/**
	 * 属性业务来源（直接/代理）的setter方法
	 */
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	/**
	 * 属性语种标志的getter方法
	 */

	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return this.language;
	}

	/**
	 * 属性语种标志的setter方法
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * 属性保单类型的getter方法
	 */

	@Column(name = "POLICYTYPE")
	public String getPolicyType() {
		return this.policyType;
	}

	/**
	 * 属性保单类型的setter方法
	 */
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	/**
	 * 属性被保险人代码的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		if(this.insuredCode==null){
			this.insuredCode = "";
		}
		return this.insuredCode;
	}

	/**
	 * 属性被保险人代码的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性被保险人名称的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性被保险人名称的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性起保日期（启运日期）的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	@Column(name = "BUSINESSCAREERNAME")
	public String getBusinessCareerName() {
		return businessCareerName;
	}

	public void setBusinessCareerName(String businessCareerName) {
		this.businessCareerName = businessCareerName;
	}

	@Column(name = "DANGEROUSCLASSSUBITEMNAME")
	public String getDangerousClassSubItemName() {
		return dangerousClassSubItemName;
	}

	public void setDangerousClassSubItemName(String dangerousClassSubItemName) {
		this.dangerousClassSubItemName = dangerousClassSubItemName;
	}

	/**
	 * 属性起保日期（启运日期）的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * 属性起保小时的getter方法
	 */

	@Column(name = "STARTHOUR")
	public Integer getStartHour() {
		return this.startHour;
	}

	/**
	 * 属性起保小时的setter方法
	 */
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	@Column(name = "BUSINESSCAREERCODE")
	public String getBusinessCareerCode() {
		return businessCareerCode;
	}

	public void setBusinessCareerCode(String businessCareerCode) {
		this.businessCareerCode = businessCareerCode;
	}

	@Column(name = "DANGEROUSCLASSITEM")
	public String getDangerousClassItem() {
		return dangerousClassItem;
	}

	public void setDangerousClassItem(String dangerousClassItem) {
		this.dangerousClassItem = dangerousClassItem;
	}

	@Column(name = "DANGEROUSCLASSSUBITEM")
	public String getDangerousClassSubItem() {
		return dangerousClassSubItem;
	}

	public void setDangerousClassSubItem(String dangerousClassSubItem) {
		this.dangerousClassSubItem = dangerousClassSubItem;
	}

	@Column(name = "PROJECTCODE")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * 属性终保日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性终保日期的setter方法
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 属性终保小时的getter方法
	 */

	@Column(name = "ENDHOUR")
	public Integer getEndHour() {
		return this.endHour;
	}

	/**
	 * 属性终保小时的setter方法
	 */
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
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
	 * 属性总保险金额的getter方法
	 */

	@Column(name = "SUMAMOUNT")
	public Double getSumAmount() {
		return this.sumAmount;
	}

	/**
	 * 属性总保险金额的setter方法
	 */
	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 属性原总保险费的getter方法
	 */

	@Column(name = "SUMPREMIUM")
	public Double getSumPremium() {
		return this.sumPremium;
	}

	/**
	 * 属性原总保险费的setter方法
	 */
	public void setSumPremium(Double sumPremium) {
		this.sumPremium = sumPremium;
	}

	/**
	 * 属性被保险总数量的getter方法
	 */

	@Column(name = "SUMQUANTITY")
	public Long getSumQuantity() {
		return this.sumQuantity;
	}

	/**
	 * 属性被保险总数量的setter方法
	 */
	public void setSumQuantity(Long sumQuantity) {
		this.sumQuantity = sumQuantity;
	}

	/**
	 * 属性出险日期起的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DAMAGESTARTDATE")
	public Date getDamageStartDate() {
		return this.damageStartDate;
	}

	/**
	 * 属性出险日期起的setter方法
	 */
	public void setDamageStartDate(Date damageStartDate) {
		this.damageStartDate = damageStartDate;
	}

	/**
	 * 属性出险开始小时的getter方法
	 */

	@Column(name = "DAMAGESTARTHOUR")
	public String getDamageStartHour() {
		return this.damageStartHour;
	}

	/**
	 * 属性出险开始小时的setter方法
	 */
	public void setDamageStartHour(String damageStartHour) {
		this.damageStartHour = damageStartHour;
	}

	/**
	 * 属性出险日期止的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "DAMAGEENDDATE")
	public Date getDamageEndDate() {
		return this.damageEndDate;
	}

	/**
	 * 属性出险日期止的setter方法
	 */
	public void setDamageEndDate(Date damageEndDate) {
		this.damageEndDate = damageEndDate;
	}

	/**
	 * 属性出险终止小时的getter方法
	 */

	@Column(name = "DAMAGEENDHOUR")
	public String getDamageEndHour() {
		return this.damageEndHour;
	}

	/**
	 * 属性出险终止小时的setter方法
	 */
	public void setDamageEndHour(String damageEndHour) {
		this.damageEndHour = damageEndHour;
	}

	/**
	 * 属性出险原因代码的getter方法
	 */

	@Column(name = "DAMAGECODE")
	public String getDamageCode() {
		return this.damageCode;
	}

	/**
	 * 属性出险原因代码的setter方法
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	/**
	 * 属性出险原因名称的getter方法
	 */

	@Column(name = "DAMAGENAME")
	public String getDamageName() {
		return this.damageName;
	}

	/**
	 * 属性出险原因名称的setter方法
	 */
	public void setDamageName(String damageName) {
		this.damageName = damageName;
	}

	/**
	 * 属性事故类型代码的getter方法
	 */

	@Column(name = "DAMAGETYPECODE")
	public String getDamageTypeCode() {
		return this.damageTypeCode;
	}

	/**
	 * 属性事故类型代码的setter方法
	 */
	public void setDamageTypeCode(String damageTypeCode) {
		this.damageTypeCode = damageTypeCode;
	}

	/**
	 * 属性事故类型说明的getter方法
	 */

	@Column(name = "DAMAGETYPENAME")
	public String getDamageTypeName() {
		return this.damageTypeName;
	}

	/**
	 * 属性事故类型说明的setter方法
	 */
	public void setDamageTypeName(String damageTypeName) {
		this.damageTypeName = damageTypeName;
	}

	/**
	 * 属性出险区域代码的getter方法
	 */

	@Column(name = "DAMAGEAREACODE")
	public String getDamageAreaCode() {
		return this.damageAreaCode;
	}

	/**
	 * 属性出险区域代码的setter方法
	 */
	public void setDamageAreaCode(String damageAreaCode) {
		this.damageAreaCode = damageAreaCode;
	}

	/**
	 * 属性出险区域名称的getter方法
	 */

	@Column(name = "DAMAGEAREANAME")
	public String getDamageAreaName() {
		return this.damageAreaName;
	}

	/**
	 * 属性出险区域名称的setter方法
	 */
	public void setDamageAreaName(String damageAreaName) {
		this.damageAreaName = damageAreaName;
	}

	/**
	 * 属性出险地点分类代码的getter方法
	 */

	@Column(name = "DAMAGEADDRESSTYPE")
	public String getDamageAddressType() {
		return this.damageAddressType;
	}

	/**
	 * 属性出险地点分类代码的setter方法
	 */
	public void setDamageAddressType(String damageAddressType) {
		this.damageAddressType = damageAddressType;
	}

	/**
	 * 属性出险地代码的getter方法
	 */

	@Column(name = "ADDRESSCODE")
	public String getAddressCode() {
		return this.addressCode;
	}

	/**
	 * 属性出险地代码的setter方法
	 */
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}

	@Transient
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	/**
	 * 属性出险地点的getter方法
	 */

	@Column(name = "DAMAGEADDRESS")
	public String getDamageAddress() {
		return this.damageAddress;
	}

	/**
	 * 属性出险地点的setter方法
	 */
	public void setDamageAddress(String damageAddress) {
		this.damageAddress = damageAddress;
	}

	/**
	 * 属性受损标的的getter方法
	 */

	@Column(name = "LOSSNAME")
	public String getLossName() {
		return this.lossName;
	}

	/**
	 * 属性受损标的的setter方法
	 */
	public void setLossName(String lossName) {
		this.lossName = lossName;
	}

	/**
	 * 属性受损标的数量/出险分户数的getter方法
	 */

	@Column(name = "LOSSQUANTITY")
	public Long getLossQuantity() {
		return this.lossQuantity;
	}

	/**
	 * 属性受损标的数量/出险分户数的setter方法
	 */
	public void setLossQuantity(Long lossQuantity) {
		this.lossQuantity = lossQuantity;
	}

	/**
	 * 属性出险险别的getter方法
	 */

	@Column(name = "DAMAGEKIND")
	public String getDamageKind() {
		return this.damageKind;
	}

	/**
	 * 属性出险险别的setter方法
	 */
	public void setDamageKind(String damageKind) {
		this.damageKind = damageKind;
	}

	/**
	 * 属性立案日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CLAIMDATE")
	public Date getClaimDate() {
		return this.claimDate;
	}

	/**
	 * 属性立案日期的setter方法
	 */
	public void setClaimDate(Date claimDate) {
		this.claimDate = claimDate;
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
	 * 属性责任比例的getter方法
	 */

	@Column(name = "INDEMNITYDUTYRATE")
	public Double getIndemnityDutyRate() {
		return this.indemnityDutyRate;
	}

	/**
	 * 属性责任比例的setter方法
	 */
	public void setIndemnityDutyRate(Double indemnityDutyRate) {
		this.indemnityDutyRate = indemnityDutyRate;
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
	 * 属性总核定损金额的getter方法
	 */

	@Column(name = "SUMDEFLOSS")
	public Double getSumDefLoss() {
		return this.sumDefLoss;
	}

	/**
	 * 属性总核定损金额的setter方法
	 */
	public void setSumDefLoss(Double sumDefLoss) {
		this.sumDefLoss = sumDefLoss;
	}

	/**
	 * 属性总赔付金额的getter方法
	 */

	@Column(name = "SUMPAID")
	public Double getSumPaid() {
		return this.sumPaid;
	}

	/**
	 * 属性总赔付金额的setter方法
	 */
	public void setSumPaid(Double sumPaid) {
		this.sumPaid = sumPaid;
	}

	/**
	 * 属性总追偿金额的getter方法
	 */

	@Column(name = "SUMREPLEVY")
	public Double getSumReplevy() {
		return this.sumReplevy;
	}

	/**
	 * 属性总追偿金额的setter方法
	 */
	public void setSumReplevy(Double sumReplevy) {
		this.sumReplevy = sumReplevy;
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
	 * 属性案件性质的getter方法
	 */

	@Column(name = "CASETYPE")
	public String getCaseType() {
		return this.caseType;
	}

	/**
	 * 属性案件性质的setter方法
	 */
	public void setCaseType(String caseType) {
		this.caseType = caseType;
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
	 * 属性代理人代码的getter方法
	 */

	@Column(name = "AGENTCODE")
	public String getAgentCode() {
		return this.agentCode;
	}

	/**
	 * 属性代理人代码的setter方法
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
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
	 * 属性保单统计年月的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STATISTICSYM")
	public Date getStatisticsYM() {
		return this.statisticsYM;
	}

	/**
	 * 属性保单统计年月的setter方法
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
	 * 属性操作员名称的getter方法
	 */
	@Transient
	public String getOperatorName() {
		return operatorName;
	}

	/**
	 * 属性操作员名称的setter方法
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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
	 * 属性结案日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDCASEDATE")
	public Date getEndCaseDate() {
		return this.endCaseDate;
	}

	/**
	 * 属性结案日期的setter方法
	 */
	public void setEndCaseDate(Date endCaseDate) {
		this.endCaseDate = endCaseDate;
	}

	/**
	 * 属性结案员代码的getter方法
	 */

	@Column(name = "ENDCASERCODE")
	public String getEndCaserCode() {
		return this.endCaserCode;
	}

	/**
	 * 属性结案员代码的setter方法
	 */
	public void setEndCaserCode(String endCaserCode) {
		this.endCaserCode = endCaserCode;
	}

	/**
	 * 属性注销/拒赔日期的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "CANCELDATE")
	public Date getCancelDate() {
		return this.cancelDate;
	}

	/**
	 * 属性注销/拒赔日期的setter方法
	 */
	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	/**
	 * 属性注销/拒赔原因的getter方法
	 */

	@Column(name = "CANCELREASON")
	public String getCancelReason() {
		return this.cancelReason;
	}

	/**
	 * 属性注销/拒赔原因的setter方法
	 */
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	/**
	 * 属性注销/拒赔人代码的getter方法
	 */

	@Column(name = "DEALERCODE")
	public String getDealerCode() {
		return this.dealerCode;
	}

	/**
	 * 属性注销/拒赔人代码的setter方法
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	/**
	 * 属性是否为逃逸案的getter方法
	 */

	@Column(name = "ESCAPEFLAG")
	public String getEscapeFlag() {
		return this.escapeFlag;
	}

	/**
	 * 属性是否为逃逸案的setter方法
	 */
	public void setEscapeFlag(String escapeFlag) {
		this.escapeFlag = escapeFlag;
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
	 * 属性THIRDCOMFLAG的getter方法
	 */

	@Column(name = "THIRDCOMFLAG")
	public String getThirdComFlag() {
		return this.thirdComFlag;
	}

	/**
	 * 属性THIRDCOMFLAG的setter方法
	 */
	public void setThirdComFlag(String thirdComFlag) {
		this.thirdComFlag = thirdComFlag;
	}

	/**
	 * 属性REPLEVYFLAG的getter方法
	 */

	@Column(name = "REPLEVYFLAG")
	public String getReplevyFlag() {
		return this.replevyFlag;
	}

	/**
	 * 属性REPLEVYFLAG的setter方法
	 */
	public void setReplevyFlag(String replevyFlag) {
		this.replevyFlag = replevyFlag;
	}

	/**
	 * 属性计算书责任比例的getter方法
	 */

	@Column(name = "CINDEMNITYDUTYRATE")
	public Double getCindemnityDutyRate() {
		return this.cindemnityDutyRate;
	}

	/**
	 * 属性计算书责任比例的setter方法
	 */
	public void setCindemnityDutyRate(Double cindemnityDutyRate) {
		this.cindemnityDutyRate = cindemnityDutyRate;
	}

	/**
	 * 属性是否自动结案的getter方法
	 */

	@Column(name = "ENDCASEFLAG")
	public String getEndCaseFlag() {
		return this.endCaseFlag;
	}

	/**
	 * 属性是否自动结案的setter方法
	 */
	public void setEndCaseFlag(String endCaseFlag) {
		this.endCaseFlag = endCaseFlag;
	}

	/**
	 * 属性巨灾一级代码的getter方法
	 */

	@Column(name = "CATASTROPHECODE1")
	public String getCatastropheCode1() {
		return this.catastropheCode1;
	}

	/**
	 * 属性巨灾一级代码的setter方法
	 */
	public void setCatastropheCode1(String catastropheCode1) {
		this.catastropheCode1 = catastropheCode1;
	}

	/**
	 * 属性巨灾一级名称的getter方法
	 */

	@Column(name = "CATASTROPHENAME1")
	public String getCatastropheName1() {
		return this.catastropheName1;
	}

	/**
	 * 属性巨灾一级名称的setter方法
	 */
	public void setCatastropheName1(String catastropheName1) {
		this.catastropheName1 = catastropheName1;
	}

	/**
	 * 属性巨灾二级代码的getter方法
	 */

	@Column(name = "CATASTROPHECODE2")
	public String getCatastropheCode2() {
		return this.catastropheCode2;
	}

	/**
	 * 属性巨灾二级代码的setter方法
	 */
	public void setCatastropheCode2(String catastropheCode2) {
		this.catastropheCode2 = catastropheCode2;
	}

	/**
	 * 属性巨灾二级名称的getter方法
	 */

	@Column(name = "CATASTROPHENAME2")
	public String getCatastropheName2() {
		return this.catastropheName2;
	}

	/**
	 * 属性巨灾二级名称的setter方法
	 */
	public void setCatastropheName2(String catastropheName2) {
		this.catastropheName2 = catastropheName2;
	}

	/**
	 * 属性出险地点邮政编码的getter方法
	 */

	@Column(name = "DAMAGEAREAPOSTCODE")
	public String getDamageAreaPostCode() {
		return this.damageAreaPostCode;
	}

	/**
	 * 属性出险地点邮政编码的setter方法
	 */
	public void setDamageAreaPostCode(String damageAreaPostCode) {
		this.damageAreaPostCode = damageAreaPostCode;
	}

	/**
	 * 属性赔案类别的getter方法
	 */

	@Column(name = "CLAIMTYPE")
	public String getClaimType() {
		return this.claimType;
	}

	/**
	 * 属性赔案类别的setter方法
	 */
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}

	/**
	 * 属性追偿时效的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "REPLEVYLIMITDATE")
	public Date getReplevyLimitDate() {
		return this.replevyLimitDate;
	}

	/**
	 * 属性追偿时效的setter方法
	 */
	public void setReplevyLimitDate(Date replevyLimitDate) {
		this.replevyLimitDate = replevyLimitDate;
	}

	/**
	 * 属性是否免导团单标志的getter方法
	 */

	@Column(name = "TERMFLAG")
	public String getTermFlag() {
		return this.termFlag;
	}

	/**
	 * 属性是否免导团单标志的setter方法
	 */
	public void setTermFlag(String termFlag) {
		this.termFlag = termFlag;
	}

	/**
	 * 属性接收客户索赔申请时间的getter方法
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "STARTAPPLYPAYDATE")
	public Date getStartApplyPayDate() {
		return this.startApplyPayDate;
	}

	/**
	 * 属性接收客户索赔申请时间的setter方法
	 */
	public void setStartApplyPayDate(Date startApplyPayDate) {
		this.startApplyPayDate = startApplyPayDate;
	}

	/**
	 * 属性是否涉及诉讼的getter方法
	 */

	@Column(name = "REFERLAWFLAG")
	public String getReferLawFlag() {
		return this.referLawFlag;
	}

	/**
	 * 属性是否涉及诉讼的setter方法
	 */
	public void setReferLawFlag(String referLawFlag) {
		this.referLawFlag = referLawFlag;
	}

	/**
	 * 属性是否涉及担保的getter方法
	 */

	@Column(name = "GUARANTEEFLAG")
	public String getGuaranteeFlag() {
		return this.guaranteeFlag;
	}

	/**
	 * 属性是否涉及担保的setter方法
	 */
	public void setGuaranteeFlag(String guaranteeFlag) {
		this.guaranteeFlag = guaranteeFlag;
	}

	/**
	 * 属性prpLltexts的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLclaim")
	public List<PrpLltext> getPrpLltexts() {
		return this.prpLltexts;
	}

	/**
	 * 属性prpLltexts的setter方法
	 */
	public void setPrpLltexts(List<PrpLltext> prpLltexts) {
		this.prpLltexts = prpLltexts;
	}

	/**
	 * 属性prpLclaimFees的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLclaim")
	public List<PrpLclaimFee> getPrpLclaimFees() {
		return this.prpLclaimFees;
	}

	/**
	 * 属性prpLclaimFees的setter方法
	 */
	public void setPrpLclaimFees(List<PrpLclaimFee> prpLclaimFees) {
		this.prpLclaimFees = prpLclaimFees;
	}

	/**
	 * 属性prpLclaimLosses的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLclaim")
	public List<PrpLclaimLoss> getPrpLclaimLosses() {
		return this.prpLclaimLosses;
	}

	/**
	 * 属性prpLclaimLosses的setter方法
	 */
	public void setPrpLclaimLosses(List<PrpLclaimLoss> prpLclaimLosses) {
		this.prpLclaimLosses = prpLclaimLosses;
	}

	/**
	 * 属性prpLdocs的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpLclaim")
	public List<PrpLdoc> getPrpLdocs() {
		return this.prpLdocs;
	}

	/**
	 * 属性prpLdocs的setter方法
	 */
	public void setPrpLdocs(List<PrpLdoc> prpLdocs) {
		this.prpLdocs = prpLdocs;
	}

	@Transient
	public String getDamageEndMinute() {
		return damageEndMinute;
	}

	public void setDamageEndMinute(String damageEndMinute) {
		this.damageEndMinute = damageEndMinute;
	}

	/**
	 * 获取属性条款类别
	 * @return 属性条款类别的值
	 */
	@Transient
	public String getClauseType() {
		return clauseType;
	}

	/**
	 * 设置属性条款类别
	 * @param clauseType 待设置的属性条款类别的值
	 */
	public void setClauseType(String clauseType) {
		this.clauseType = StringUtils.rightTrim(clauseType);
	}

	/**
	 * 获取属性条款名称
	 * @return 属性条款名称的值
	 */
	@Transient
	public String getClauseName() {
		return clauseName;
	}

	/**
	 * 设置属性条款名称
	 * @param clauseName 待设置的属性条款名称的值
	 */
	public void setClauseName(String clauseName) {
		this.clauseName = StringUtils.rightTrim(clauseName);
	}

	/**
	 * 获取属性车牌号码
	 * @return 属性车牌号码的值
	 */
	@Transient
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 设置属性车牌号码
	 * @param licenseNo 待设置的属性车牌号码的值
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = StringUtils.rightTrim(licenseNo);
	}

	/**
	 * 获取属性车牌底色代码
	 * @return 属性车牌底色代码的值
	 */
	@Transient
	public String getLicenseColorCode() {
		return licenseColorCode;
	}

	/**
	 * 设置属性车牌底色代码
	 * @param licenseColorCode 待设置的属性车牌底色代码的值
	 */
	public void setLicenseColorCode(String licenseColorCode) {
		this.licenseColorCode = StringUtils.rightTrim(licenseColorCode);
	}

	/**
	 * 获取属性车牌底色代码
	 * @return 属性车牌底色代码的值
	 */
	@Transient
	public String getLicenseColor() {
		return licenseColor;
	}

	/**
	 * 设置属性车牌底色代码
	 * @param licenseColorCode 待设置的属性车牌底色代码的值
	 */
	public void setLicenseColor(String licenseColor) {
		this.licenseColor = StringUtils.rightTrim(licenseColor);
	}

	/**
	 * 设置属性厂牌型号
	 * @param brandName 待设置的属性厂牌型号的值
	 */
	public void setBrandName(String brandName) {
		this.brandName = StringUtils.rightTrim(brandName);
	}

	/**
	 * 获取属性厂牌型号
	 * @return 属性厂牌型号的值
	 */
	@Transient
	public String getBrandName() {
		return brandName;
	}

	/**
	 * 获取属性车辆种类代码
	 * @return 属性车辆种类代码的值
	 */
	@Transient
	public String getCarKindCode() {
		return carKindCode;
	}

	/**
	 * 设置属性车辆种类代码
	 * @param carKindCode 待设置的属性车辆种类代码的值
	 */
	public void setCarKindCode(String carKindCode) {
		this.carKindCode = StringUtils.rightTrim(carKindCode);
	}

	/**
	 * 获取属性车辆种类
	 * @return 属性车辆种类值
	 */
	@Transient
	public String getCarKind() {
		return carKind;
	}

	/**
	 * 设置属性车辆种类
	 * @param carKind待设置的属性车辆种类的值
	 */
	public void setCarKind(String carKind) {
		this.carKind = StringUtils.rightTrim(carKind);
	}

	/**
	 * 设置属性出险开始分钟
	 * @param damageStartMinute 待设置的属性出险开始分钟的值
	 */
	public void setDamageStartMinute(String damageStartMinute) {
		this.damageStartMinute = StringUtils.rightTrim(damageStartMinute);
	}

	/**
	 * 获取属性出险开始分钟
	 * @return 属性出险开始分钟的值
	 */
	@Transient
	public String getDamageStartMinute() {
		return damageStartMinute;
	}

	/**
	 * 设置属性部门
	 * @param comName 待设置的属性部门的值
	 */
	public void setComName(String comName) {
		this.comName = StringUtils.rightTrim(comName);
	}

	/**
	 * 获取属性部门
	 * @return 属性部门的值
	 */
	@Transient
	public String getComName() {
		return comName;
	}

	/**
	 * 获取属性经办人代码
	 * @return 属性经办人代码的值
	 */
	@Transient
	public String getHandler1Name() {
		return handler1Name;
	}

	/**
	 * 设置属性经办人代码
	 * @param handlerCode 待设置的属性经办人代码的值
	 */
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = StringUtils.rightTrim(handler1Name);
	}

	/**
	 * 获取属性代理人名称
	 * @return 属性代理人名称的值
	 */
	@Transient
	public String getAgentName() {
		return agentName;
	}

	/**
	 * 设置属性代理人名称
	 * @param AgentName 待设置的属性代理人名称
	 */
	public void setAgentName(String agentName) {
		this.agentName = StringUtils.rightTrim(agentName);
	}

	/**
	 * 获取属性经办人代码
	 * @return 属性经办人代码的值
	 */
	@Transient
	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = StringUtils.rightTrim(handlerName);
	}

	/**
	 * 设置属性出险次数
	 * @param perilCount 待设置的属性出险次数的值
	 */
	public void setPerilCount(int perilCount) {
		this.perilCount = perilCount;
	}

	/**
	 * 获取属性出险次数
	 * @return 属性出险次数的值
	 */
	@Transient
	public int getPerilCount() {
		return perilCount;
	}

	/**
	 * 设置属性是否为全损
	 * @param escapeFlag 待设置的属性是否为全损的值
	 */
	public void setEscapeFlag2(String escapeFlag2) {
		this.escapeFlag2 = StringUtils.rightTrim(escapeFlag2);
	}

	/**
	 * 获取属性是否为全损
	 * @return 属性是否为全损的值
	 */
	@Transient
	public String getEscapeFlag2() {
		return escapeFlag2;
	}

	/**
	 * 设置属性操作状态
	 * @param status 待设置的属性操作状态
	 */
	public void setStatus(String status) {
		this.status = StringUtils.rightTrim(status);
	}

	/**
	 * 获取属性操作状态
	 * @return 属性操作状态
	 */
	@Transient
	public String getStatus() {
		return status;
	}

	/**
	 * 设置属性AcciCode
	 * @param acciCode 待设置的属性AcciCode的值
	 */
	public void setAcciCode(String acciCode) {
		this.acciCode = StringUtils.rightTrim(acciCode);
	}

	/**
	 * 获取属性AcciCode
	 * @return 属性AcciCode的值
	 */
	@Transient
	public String getAcciCode() {
		return acciCode;
	}

	/**
	 * 设置属性AcciName
	 * @param acciName 待设置的属性AcciName的值
	 */
	public void setAcciName(String acciName) {
		this.acciName = StringUtils.rightTrim(acciName);
	}

	/**
	 * 获取属性AcciName
	 * @return 属性AcciName的值
	 */
	@Transient
	public String getAcciName() {
		return acciName;
	}

	/**
	 * 设置属性Sex
	 * @param sex 待设置的属性Sex的值
	 */
	public void setSex(String sex) {
		this.sex = StringUtils.rightTrim(sex);
	}

	/**
	 * 获取属性Sex
	 * @return 属性Sex的值
	 */
	@Transient
	public String getSex() {
		return sex;
	}

	/**
	 * 设置属性Age
	 * @param age 待设置的属性Age的值
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * 获取属性Age
	 * @return 属性Age的值
	 */
	@Transient
	public Integer getAge() {
		return age;
	}

	/**
	 * 设置属性IdentifyNumber
	 * @param identifyNumber 待设置的属性IdentifyNumber的值
	 */
	public void setIdentifyNumber(String identifyNumber) {
		this.identifyNumber = StringUtils.rightTrim(identifyNumber);
	}

	/**
	 * 获取属性IdentifyNumber
	 * @return 属性IdentifyNumber的值
	 */
	@Transient
	public String getIdentifyNumber() {
		return identifyNumber;
	}

	/**
	 * 设置序号
	 * @param 序号
	 */
	public void setFamilyNo(int familyNo) {
		this.familyNo = familyNo;
	}

	/**
	 * 获得属性序号
	 * @return caseTypeName 序号
	 */
	@Transient
	public int getFamilyNo() {
		return this.familyNo;
	}

	/**
	 * 设置币别信息
	 * @param EstiCurrency 币别
	 */
	public void setEstiCurrency(String estiCurrency) {
		this.estiCurrency = estiCurrency;
	}

	/**
	 * 获得币别信息
	 * @return EstiCurrency 币别信息
	 */
	@Transient
	public String getEstiCurrency() {
		return this.estiCurrency;
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

	/**
	 * 设置属性businessNatureName
	 * @param businessNatureName 待设置的属性businessNatureName的值
	 */
	public void setBusinessNatureName(String businessNatureName) {
		this.businessNatureName = StringUtils.rightTrim(businessNatureName);
	}

	/**
	 * 获取属性businessNatureName
	 * @return 属性businessNatureName的值
	 */
	@Transient
	public String getBusinessNatureName() {
		return businessNatureName;
	}

	/**
	 * 设置属性businessNatureName
	 * @param businessNatureName 待设置的属性businessNatureName的值
	 */
	public void setLanguageName(String languageName) {
		this.languageName = StringUtils.rightTrim(languageName);
	}

	/**
	 * 获取属性businessNatureName
	 * @return 属性businessNatureName的值
	 */
	@Transient
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * 设置属性理赔登记机构名称
	 * @param makeComName 待设置的属性理赔登记机构名称的值
	 */
	public void setMakeComName(String makeComName) {
		this.makeComName = StringUtils.rightTrim(makeComName);
	}

	/**
	 * 获取属性理赔登记机构名称
	 * @return 属性理赔登记机构名称的值
	 */
	@Transient
	public String getMakeComName() {
		return makeComName;
	}

	/**
	 * 设置属性拒赔操作人名称
	 * @param dealerName 待设置的属性拒赔操作人名称的值
	 */
	public void setDealerName(String dealerName) {
		this.dealerName = StringUtils.rightTrim(dealerName);
	}

	/**
	 * 获取属性拒赔操作人名称
	 * @return 属性拒赔操作人名称的值
	 */
	@Transient
	public String getDealerName() {
		return dealerName;
	}

	/**
	 * 设置编辑类型
	 * @param editType 待设置的编辑类型的值
	 */
	public void setEditType(String editType) {
		this.editType = StringUtils.rightTrim(editType);
	}

	/**
	 * 获取编辑类型
	 * @return 属性编辑类型
	 */
	@Transient
	public String getEditType() {
		return editType;
	}

	/**
	 * 设置列表
	 * @param registList 待设置的列表
	 */
	public void setClaimList(List<PrpLclaim> claimList) {
		this.claimList = claimList;
	}

	/**
	 * 获取列表
	 * @return 属性列表
	 */
	@Transient
	public List<PrpLclaim> getClaimList() {
		return claimList;
	}

	/**
	 * 被保险人类别
	 * @param 被保险人类别
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * 被保险人类别
	 * @return 被保险人类别
	 */
	@Transient
	public String getCustomerType() {
		return this.customerType;
	}

	/**
	 * 设置属性核保信息
	 * @param coinsFlag 核保信息
	 */
	public void setOthFlag(String othFlag) {
		this.othFlag = othFlag;
	}

	/**
	 * 获取属性核保信息othFlag
	 * @return 核保信息
	 */
	@Transient
	public String getOthFlag() {
		return othFlag;
	}

	/**
	 * 设置属性核保信息
	 * @param coinsFlag 核保信息
	 */
	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	/**
	 * 获取属性核保信息
	 * @return 核保信息
	 */
	@Transient
	public Date getUnderWriteEndDate() {
		return underWriteEndDate;
	}

	@Transient
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	@Transient
	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 获取属性赔偿责任名称
	 * @return 属性赔偿责任名称的值
	 */
	@Transient
	public String getIndemnityDutyName() {
		return indemnityDutyName;
	}

	/**
	 * 设置属性赔偿责任名称
	 * @param indemnityDutyName 待设置的属性赔偿责任名称的值
	 */
	public void setIndemnityDutyName(String indemnityDutyName) {
		this.indemnityDutyName = StringUtils.rightTrim(indemnityDutyName);
	}

	@Transient
	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	@Transient
	public String getEndCaserName() {
		return endCaserName;
	}

	public void setEndCaserName(String endCaserName) {
		this.endCaserName = endCaserName;
	}

	@Transient
	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	@Column(name = "RECEIPTDATE")
	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
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

	/**
	 * 属性处理部门的getter方法
	 */

	@Column(name = "HANDLEDEPT")
	public String getHandleDept() {
		return this.handleDept;
	}

	/**
	 * 属性处理部门的setter方法
	 */
	public void setHandleDept(String handleDept) {
		this.handleDept = handleDept;
	}

	@Transient
	public String getHasReplevy() {
		return hasReplevy;
	}

	public void setHasReplevy(String hasReplevy) {
		this.hasReplevy = hasReplevy;
	}

	@Column(name = "CARGONO")
	public String getCargoNo() {
		return cargoNo;
	}

	public void setCargoNo(String cargoNo) {
		this.cargoNo = cargoNo;
	}

	@Column(name = "CARGONAME")
	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	@Column(name = "GENERALAVERAGE")
	public String getGeneralAverage() {
		return generalAverage;
	}

	public void setGeneralAverage(String generalAverage) {
		this.generalAverage = generalAverage;
	}

	@Column(name = "TRANSPORTTYPE")
	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	@Column(name = "BKWARDSTARTDATE")
	public Date getBkWardStartDate() {
		return bkWardStartDate;
	}

	public void setBkWardStartDate(Date bkWardStartDate) {
		this.bkWardStartDate = bkWardStartDate;
	}

//	public String getIssuingBank() {
//		return issuingBank;
//	}
//
//	public void setIssuingBank(String issuingBank) {
//		this.issuingBank = issuingBank;
//	}
//
//	public String getCreditCardType() {
//		return creditCardType;
//	}
//
//	public void setCreditCardType(String creditCardType) {
//		this.creditCardType = creditCardType;
//	}
//
//	public String getCreditCardNo() {
//		return creditCardNo;
//	}
//
//	public void setCreditCardNo(String creditCardNo) {
//		this.creditCardNo = creditCardNo;
//	}
//
//	public String getDueDate() {
//		return dueDate;
//	}
//
//	public void setDueDate(String dueDate) {
//		this.dueDate = dueDate;
//	}
//
//	public String getCardholderName() {
//		return cardholderName;
//	}
//
//	public void setCardholderName(String cardholderName) {
//		this.cardholderName = cardholderName;
//	}
//
//	public String getCardholderIDNo() {
//		return cardholderIDNo;
//	}
//
//	public void setCardholderIDNo(String cardholderIDNo) {
//		this.cardholderIDNo = cardholderIDNo;
//	}
//
//	public String getCardholderTel() {
//		return cardholderTel;
//	}
//
//	public void setCardholderTel(String cardholderTel) {
//		this.cardholderTel = cardholderTel;
//	}
//
//	public String getCardholderPhone() {
//		return cardholderPhone;
//	}
//
//	public void setCardholderPhone(String cardholderPhone) {
//		this.cardholderPhone = cardholderPhone;
//	}
//
//	public String getCardholderAddress() {
//		return cardholderAddress;
//	}
//
//	public void setCardholderAddress(String cardholderAddress) {
//		this.cardholderAddress = cardholderAddress;
//	}
//
//	public String getRelationship() {
//		return relationship;
//	}
//
//	public void setRelationship(String relationship) {
//		this.relationship = relationship;
//	}
	
	@Column(name = "ENDORSENO")
	public String getEndorseNo() {
		return endorseNo;
	}

	public void setEndorseNo(String endorseNo) {
		this.endorseNo = endorseNo;
	}

//	@Column(name = "otherCard")
//	public String getOtherCard() {
//		return otherCard;
//	}
//
//	public void setOtherCard(String otherCard) {
//		this.otherCard = otherCard;
//	}
//
//	@Transient
//	public String getDueDateYear() {
//		return dueDateYear;
//	}
//
//	public void setDueDateYear(String dueDateYear) {
//		this.dueDateYear = dueDateYear;
//	}
//
//	@Transient
//	public String getDueDateMonth() {
//		return dueDateMonth;
//	}
//
//	public void setDueDateMonth(String dueDateMonth) {
//		this.dueDateMonth = dueDateMonth;
//	}

	@Column(name = "CHARGETYPE")
	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	@Transient
	public String getSailStartDate() {
		return sailStartDate;
	}

	public void setSailStartDate(String sailStartDate) {
		this.sailStartDate = sailStartDate;
	}

	@Transient
	public String getPolicyInputDate() {
		return policyInputDate;
	}

	public void setPolicyInputDate(String policyInputDate) {
		this.policyInputDate = policyInputDate;
	}

	@Transient
	public String getMakeDate() {
		return makeDate;
	}

	public void setMakeDate(String makeDate) {
		this.makeDate = makeDate;
	}
	
	@Transient
	public String getStartSiteCountry() {
		return startSiteCountry;
	}

	public void setStartSiteCountry(String startSiteCountry) {
		this.startSiteCountry = startSiteCountry;
	}

	@Transient
	public String getStartSitePort() {
		return startSitePort;
	}

	public void setStartSitePort(String startSitePort) {
		this.startSitePort = startSitePort;
	}

	@Transient
	public String getEndSiteCountry() {
		return endSiteCountry;
	}

	public void setEndSiteCountry(String endSiteCountry) {
		this.endSiteCountry = endSiteCountry;
	}

	@Transient
	public String getEndSitePort() {
		return endSitePort;
	}

	public void setEndSitePort(String endSitePort) {
		this.endSitePort = endSitePort;
	}
	@Transient
	public String getSameAddressNo() {
		return sameAddressNo;
	}

	public void setSameAddressNo(String sameAddressNo) {
		this.sameAddressNo = sameAddressNo;
	}

	@Transient
	public String getShipCName() {
		return shipCName;
	}

	public void setShipCName(String shipCName) {
		this.shipCName = shipCName;
	}

	@Transient
	public String getClaimAgent() {
		return claimAgent;
	}

	public void setClaimAgent(String claimAgent) {
		this.claimAgent = claimAgent;
	}

	@Transient
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	@Transient
	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	@Column(name = "SIMPLEFLAG")
	public String getSimpleFlag() {
		return simpleFlag;
	}

	public void setSimpleFlag(String simpleFlag) {
		this.simpleFlag = simpleFlag;
	}
	@Column(name = "replevyRemark")
	public String getReplevyRemark() {
		return replevyRemark;
	}

	public void setReplevyRemark(String replevyRemark) {
		this.replevyRemark = replevyRemark;
	}
	
	/***  add by chenjie 20150601 需求變更-095 begin ***/
	@Column(name = "carAccidentType")
	public String getCarAccidentType() {
		return carAccidentType;
	}

	public void setCarAccidentType(String carAccidentType) {
		this.carAccidentType = carAccidentType;
	}
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
		return hospitalizedDays;
	}

	public void setHospitalizedDays(Integer hospitalizedDays) {
		this.hospitalizedDays = hospitalizedDays;
	}
	//mantis：CLM0231，處理人員：DP0713，需求單編號：新核心-傷害險高保額新商品檢核 END
}
