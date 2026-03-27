// default package
// 采用工具 Hibernate Tools 3.2.4.GA (sinosoft version) 生成，请勿手工修改。
package com.sinosoft.common.schema.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
 */
@Entity
@Table(name = "PRPQMAIN")
public class PrpQmain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	/** 规则验证结果	 */
	private boolean result=false;

	/** 属性投保单号码 */
	private String proposalNo;

	/** 属性险类代码 */
	private String classCode;

	/** 属性虚拟险种代码 */
	private String riskCode;

	/** 属性合同号 */
	private String contractNo;

	/** 属性保单种类 */
	private String policySort;

	/** 属性单证流水号 */
	private String printNo;

	/** 属性业务性质 */
	private String businessNature;

	/** 属性语言 */
	private String language;

	/** 属性保单类型 */
	private String policyType;

	/** 属性应用代码 */
	private String appliCode;

	/** 属性投保人名称 */
	private String appliName;

	/** 属性投保人地址 */
	private String appliaddress;

	/** 属性关系人代码 */
	private String insuredCode;

	/** 属性关系人名字 */
	private String insuredName;

	/** 属性关系人地址 */
	private String insuredAddress;

	/** 属性操作时间 */
	private Date operateDate;

	/** 属性备用7 */
	private Date startDate;

    /** 原始开航日期*/
    private Date baseStartDate;
	
	/** 属性起保小时 */
	private Integer startHour;

	/** 属性备用8 */
	private Date endDate;

	/** 属性终保小时 */
	private Integer endHour;

	/** 属性净费率 */
	private BigDecimal pureRate;

	/** 属性手续费率/浮动费率 */
	private BigDecimal disRate;

	/** 属性折扣率 */
	private BigDecimal discount;

	/** 属性币别代码 */
	private String currency;

	/** 属性总价款 */
	private BigDecimal sumValue;

	/** 属性总保额 */
	private BigDecimal sumAmount;

	/** 属性总折扣金额 */
	private BigDecimal sumDiscount;

	/** 属性总保费 */
	private BigDecimal sumPremium;

	/** 属性总附加险保费 */
	private BigDecimal sumSubPrem;

	/** 属性被保险总数量/人数/户数 */
	private Long sumQuantity;

	/** 属性司法代码 */
	private String judicalCode;

	/** 属性司法管辖 */
	private String judicalScope;

	/** 属性是否自动转帐续保标志 */
	private String autoTransRenewFlag;

	/** 属性争议解决方式 */
	private String argueSolution;

	/** 属性仲裁委员会名称 */
	private String arbitBoardName;

	/** 属性约定分期交费次数 */
	private Integer payTimes;

	/** 属性批改次数 */
	private Integer endorseTimes;

	/** 属性理赔次数 */
	private Integer claimTimes;

	/** 属性出单机构 */
	private String makeCom;

	/** 属性签单地点 */
	private String operateSite;

	/** 属性机关代码 */
	private String comCode;
	
	 /** 属性登陆机构代码 */
    private String loginCom;
    
	/** 属性经办人代码 */
	private String handlerCode;
    /** 属性经办人身份证号 */
    private String handlerIdentifyNumber;
	/** 属性归属人 */
	private String handler1Code;

	/** 属性复核人代码 */
	private String approverCode;

	/** 属性最终核保人代码 */
	private String underWriteCode;

	/** 属性最终核保人名称 */
	private String underWriteName;

	/** 属性操作员代码 */
	private String operatorCode;

	/** 属性报表数据生成日期 */
	private Date inputDate;

	/** 属性报表数据生成时间 */
	private Integer inputHour;

	/** 属性核保完成日期 */
	private Date underWriteEndDate;

	/** 属性保单统计年月 */
	private Date statisticsYM;

	/** 属性代理人代码 */
	private String agentCode;
    /** 属性代理人名称 */
    private String agentName;
	/** 属性分保类型1-是分出公司 2-不是分出公司 */
	private String coinsFlag;

	/** 属性商业分保标志 */
	private String reinsFlag;

	/** 属性统保标志 */
	private String allinsFlag;

	/** 属性核保标志 --0，初始值；1，核保通過；2，下發修改；3，無需核保；9，待核保；00，暫存單；10，待續保（由續保轉報價單得來）*/
	private String underWriteFlag;

	/** 属性其它标志字段 */
	private String othFlag;

	/** 属性标志字段 */
	private String flag;

	/** 属性DISRATE1 */
	private BigDecimal disRate1;

	/** 属性businessflag */
	private String businessflag;

	/** 属性UPDATERCODE */
	private String updateRCode;

	/** 属性更新日期 */
	private Date updateDate;

	/** 属性更新时间 */
	private String updateHour;

	/** 属性属性签单日期 */
	private Date signDate;

	/** 属性SHAREHOLDERFLAG */
	private String shareHolderFlag;

	/** 属性协议号 */
	private String agreementNo;

	/** 属性INQUIRYNO */
	private String inquiryNo;

	/** 属性保险费支付办法 */
	private String payMode;

	/** 属性REMARK */
	private String remark;

	/** 属性合同号码 */
	private String policyNo;

	/** 属性单证号码 */
	private String visaCode;

	/** 属性MANUALTYPE */
	private String manualType;

	/** 属性NATIONFLAG */
	private String nationFlag;

	/** 属性开始分钟 */
	private Byte startMinute;

	/** 属性结束分钟 */
	private Byte endMinute;

	/** 属性见费出单标志位 */
	private String jfeeFlag;

	/** 属性PRECHECKDATE */
	private Date preCheckDate;

	/** 属性经办人名字 */
	private String handlerName;

	/** 属性HANDLER1NAME */
	private String handler1Name;

	/** 属性PAYREFCODE */
	private String payRefCode;

	/** 属性PAYREFNAME */
	private String payRefName;

	/** 属性PAYREFTIME */
	private Date payRefTime;

	/** 属性PRINTTIME */
	private Date printTime;

	/** 属性AGRITYPE */
	private String agriType;

	/** 属性与现有系统编码统一 */
	private String subBusinessNature;

	/** 属性开户代码 */
	private String bankCode;

	/** 属性CHANNELTYPE */
	private String channelType;

	/** 属性兑换率 */
	private BigDecimal exchangeRate;

	/** 属性projectsflag */
	private String projectsflag;

	/** 属性见费出单类型 */
	private String jfeePayType;

	/** 属性保单级别 */
	private String proposalLevel;

	/** 属性STOPTIMES */
	private String stopTimes;

	/** 属性EFFECTIVEIMMEDIATELYFLAG */
	private String effectiveimmediatelyFlag;

	/** 属性NEWSTARTDATE */
	private Date newStartDate;

	/** 属性NEWENDDATE */
	private Date newEndDate;

	/** 属性GROUPTYPE */
	private String groupType;

	/** 属性STARTSTAGES */
	private Integer startStages;

	/** 属性quoteno */
	private String quoteno;

	/** 属性CONTRIBUTIONLEVEL */
	private String contributionLevel;

	/** 属性caseno */
	private String caseno;

	/** 属性DECLAREFLAG */
	private String declareFlag;

	/** 属性BUSINESSTYPEFLAG */
	 private String businessTypeFlag;

	/** 属性PROGRAMNO */
	// private String programNO;

	/** 属性channelcode */
	private String channelcode;

	/** 属性biznosysflag */
	private String biznosysflag;

	/** 属性businessrecmark */
	private String businessrecmark;

	/** 属性undwrtmark */
	private String undwrtmark;

	/** 属性isundwrtflag */
	private String isundwrtflag;

	/** 属性agentmaxcomission */
	private BigDecimal agentmaxcomission;

	/** 属性bankflag */
	private String bankflag;
	
    /** 属性代办人代码*/
    private String handler2Code;
    
    /** 属性代办人姓名 */
    private String handler2Name;
    
    /** 属性代办人证件 */
    private String handler2IDType;
    
    /** 属性代办人证件号码 */
    private String handler2ID;
    
    /** 属性代办人电话 */
    private String handler2Mobile;
    
    /** 属性代办人邮编 */
    private String handler2Post;    
    
    /** 属性代办人地址 */
    private String handler2Address;
    
    /**属性報關行名稱*/
    private String customsBrokerName;
    
    /** 属性锁定人代码 */
    private String lockerCode;
    
    /** 属性是否AS400续保数据标志 */
    private String editFlag;
    
    /** 属性未续保登记原因 */
    private String rsnNorenewal;
    
    /** 属性未续保登记原因代码 */
    private String notRenewalRegist; 

//	/** 属性更重要类型 --------------add */
//	private String priorType;
    
	/** 属性费率别类型（新、旧) */
    private String ratePeriodType;
    
    /** 属性费率别版本值 */
    private String ratePeriod;
    /** 属性费率别版本旧值 */
    private String ratePeriodOld;
    /** 属性费率别实施日期 */
    private Date rateStartDate;
/** 属性费率别终止日期 */
    private Date rateEndDate;
/** 属性是否政府采购 */
    private String govPurchaseFlag;
/** 属性是否转拨计价 */
    private String fycFlag;
    /** 属性是否直接业务 */
    private String directBusiness;
    /** 属性单位 */
    private String extraComCode;
/** 属性单位名称 */
    private String extraComName;
/** 属性介绍人ID */
    private String introducerID;
/** 属性介绍人姓名 */
    private String introducerName;
/** 属性代收人ID */
    private String agent1Code;
/** 属性代收人姓名 */
    private String agent1Name;

/** 属性关贸查询返回查询序号 */
    private String tradeVanID;
/**属性專案代號*/
    private String projectCode; 
	
	/** 属性批次转档批次号 */
    private String batchNO;
    
    /** 属性AS400单号 */
    private String asPolicyNo;
    
    /** 属性占用性质代码 */
    private String possessNature;
    
    /***属性主使用性质代码*/
    private String possessNatureCode;
    
    /** 属性套装商品名称 */
    private String seriesName;
        
	
    /** 属性AS400上期保費 */
    private BigDecimal asSumPremium;
	/** 属性套装商品代号 */
    private String seriesCode;
    /** 属性业绩折算率 */
    private String achievConverRate;
    /**
     * 屬性再保注記
     */
    private String reinsMark;
    /**
     * 屬性建檔人員
     */
    private String creator;
    /**
     * 屬性續保期數
     */
    private String renewalTimes;
	/**
     * 屬性共同業務
     */
    private String commonBusiness;
    /**
     * 屬性特殊件
     */
    private String specialFlag;
    /**
     * 屬性折算率
     */
    private BigDecimal discountRate;
    /**
     * 屬性首次列單日期
     */
    private Date printDate;
    /**
     * 屬性收件日期
     */
    private Date mailDate;
    /**
     * 屬性險別代碼
     */
    private String rationCode;
    /**
     * 屬性收據備註
     */
    private String feeRemark;
    /**
     * 屬性直折件 add by wangyayun 
     */
    private String zhizhe;
    /**
     * 属性保单权限种类 add by　CSY
     */
    private String policyPermissions ;
    /**
     * 属性批准文件號碼
     */
    private String policyFilenumber ;
    
    /** 属性遞送/寄送日期 */
    private Date deliveryDate;
    
    /** 属性保單簽收日期 */
    private Date policyReceptDate;
    
    /**
     * 屬性公家件
     */
    private String publicFlag;
    /*试车起日*/
    private Date testCarStartDate;
    /*试车止日*/
    private Date testCarEndDate;
    /*维护起期*/
    private Date maintainStartDate;
    /*维护止期*/
    private Date maintainEndDate;
    /*维护天數*/
    private String maintainDays;
    /** 電子設備損失總保險 */
    private BigDecimal sumDamageAmount;
    /** 電腦外在資料儲存體損失險總保險*/
    private BigDecimal sumExternalAmount;
    /** 電腦額外費用險總保險 */
    private BigDecimal sumIncreasedAmount;
    /*是否可續保*/
    private String renewInsuranceFlag;
    
    /**保單最低保費金額*/
    private BigDecimal smallAmountBI;
    
    // 水险CF新增字段 by yjm
    /*保險單適用區*/
    private String policyApplyArea;
    /*本保單適用特別約定*/
    private String policyApplyAgreement;
    /*預收保險費（新臺幣元）*/
    private BigDecimal unearnedPremium;
    /*详细备注*/
    private String detailRemark;
    /*說明*/
    private String descriptions;
    /**再保注记的说明*/
    private String reinsMarkDesc;
	/** 属性prpQengages */
	private List<PrpQengage> prpQengages = new ArrayList<PrpQengage>(0);
	
	/** 属性任意保险卡號*/
	private String visaCodeBI;
	/**暂收收据*/
    private String temporarilyReceipt;
    
    
    //add by yjm 20150605 保費浮動相關字段添加 start
    private String flowFlag;//浮動標誌
    private String flowRate;//浮動範圍
    //add by yjm 20150605 保費浮動相關字段添加 end
    
    //add by yjm 20141212 傷害險計算年齡方式增加 
    private String ageKindType;
    //add by cwp 2015-4-3 begin	//新增字段1
    private String remarkCol1;
    private String remarkCol2;
    private String remarkCol3;
    private String remarkCol4;
    private String remarkCol5;
    private String remarkCol6;
    private String remarkCol7;
    private String remarkCol8;
    private String remarkCol9;
    private String remarkCol10;
    //end
    //add by xuhuiling 需求150 20160818 begin
    private String refuseLimiteInsurance;//拒限保
    private String listDetection;//名單檢測
    private String riskRating;//風險評級
    private String workStatus;//作業狀態
    private Date callAmlDate;//調用時間
    private String exceptionNo;//異常代號
    //add by xuhuiling 需求150 20160818 end
	
    //列印次数
    private BigDecimal printTimes;
    
	/** 属性大保單號 */
    private String bigPolicyNo;
    
    /** 核可文件編號 */
    private String approvalNo;
    
    //add by bh054 mantis5945:新增折舊選項
    private String carOptionType;
    //add by dp0703 電子保單欄位 20181019
    private String epolicy = "";
    
    @Column(name="EPOLICY")
    public String getEpolicy() {
		return epolicy;
	}
	public void setEpolicy(String epolicy) {
		this.epolicy = epolicy;
	}
	@Column(name="CAROPTIONTYPE")
    public String getCarOptionType() {
		return carOptionType;
	}
	public void setCarOptionType(String carOptionType) {
		this.carOptionType = carOptionType;
	}
	@Column(name = "APPROVALNO")
	public String getApprovalNo() {
		return approvalNo;
	}
	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}
	
    //add  by mjx  增加是否送入收付   20150313
    private String  sendPaymentFlag;
	/** 属性PRPQMAINCOEFF */
    //add by gaojunfeng 需求變更150 20160808 
    private String commodityRiskGrade;//商品風險等級
  //add by dongfan 需求167 新增超商標誌 20161223
    private String superpay;
    
	private List<PrpQshipDriver> prpQshipDrivers = new ArrayList<PrpQshipDriver>(
			0);
	private List<PrpQriskValuat> prpQriskValuats = new ArrayList<PrpQriskValuat>(
			0);
	private List<PrpQriskProfit> prpQriskProfits = new ArrayList<PrpQriskProfit>(
			0);
	private List<PrpQrenewal> prpQrenewals = new ArrayList<PrpQrenewal>(0);
	private List<PrpQration> prpQrations = new ArrayList<PrpQration>(0);
	private List<PrpQprofitDetail> prpQprofitDetails = new ArrayList<PrpQprofitDetail>(
			0);
	private List<PrpQprofit> prpQprofits = new ArrayList<PrpQprofit>(0);
	private List<PrpQmainCoeff> prpQmainCoeffs = new ArrayList<PrpQmainCoeff>(0);
	private List<PrpQmainCasualty> prpQmainCasualtIies = new ArrayList<PrpQmainCasualty>(
			0);
	private List<PrpQmainCargo> prpQmainCargos = new ArrayList<PrpQmainCargo>(0);
	private List<PrpQitemShip> prpQitemShips = new ArrayList<PrpQitemShip>(0);
	private List<PrpQitemKindRateFactor> prpQitemKindRateFactors = new ArrayList<PrpQitemKindRateFactor>(0);
	private List<PrpQitemHouse> prpQitemHouses = new ArrayList<PrpQitemHouse>(0);
	private List<PrpQitemDevice> prpQitemDevices = new ArrayList<PrpQitemDevice>(
			0);
	private List<PrpQitemCargo> prpQitemCargos = new ArrayList<PrpQitemCargo>(0);
	private List<PrpQitemCar> prpQitemCars = new ArrayList<PrpQitemCar>(0);
	private List<PrpQitemAgri> prpQitemAgris = new ArrayList<PrpQitemAgri>(0);
//	private List<PrpTinsuredExt> prpTinsuredExts = new ArrayList<PrpTinsuredExt>(0);
	private List<PrpQinsuredArtif> prpQinsuredArtifs = new ArrayList<PrpQinsuredArtif>(0);
	private List<PrpQgrade> prpQgrades = new ArrayList<PrpQgrade>(0);
	private List<PrpQexpense> prpQexpenses = new ArrayList<PrpQexpense>(0);
	private List<PrpQdeductible> prpQdeductibles = new ArrayList<PrpQdeductible>(
			0);
	private List<PrpQcoinsDetail> prpQcoinsDetails = new ArrayList<PrpQcoinsDetail>(
			0);
	private List<PrpQcoins> prpQcoinses = new ArrayList<PrpQcoins>(0);
	private List<PrpQcharge> prpQcharges = new ArrayList<PrpQcharge>(0);
	private List<PrpQcarshipTaxPreDetail> prpQcarshipTaxPreDetails = new ArrayList<PrpQcarshipTaxPreDetail>(
			0);
	private List<PrpQcarShipTax> prpQcarShipTaxs = new ArrayList<PrpQcarShipTax>(
			0);
	private List<PrpQcarShipTax3101Bak> prpQcarShipTax3101Baks = new ArrayList<PrpQcarShipTax3101Bak>(0);
	private List<PrpQcarDevice> prpQcarDevices = new ArrayList<PrpQcarDevice>(0);
	private List<PrpQbIPolicy> prpQbIPolicies = new ArrayList<PrpQbIPolicy>(0);
	private List<PrpQbiClaim> prpQbiClaims = new ArrayList<PrpQbiClaim>(0);
	private List<PrpQaviation> prpQaviations = new ArrayList<PrpQaviation>(0);
	private List<PrpQitemProp> prpQitemProps = new ArrayList<PrpQitemProp>(0);
	private List<PrpQguaranty> prpQguaranties = new ArrayList<PrpQguaranty>(0);
	private List<PrpQprintExchange> prpQprintExchanges = new ArrayList<PrpQprintExchange>(
			0);
	private List<PrpQmainCargoSub> prpQmainCarGoSubs = new ArrayList<PrpQmainCargoSub>(
			0);
	private List<PrpQcarDriver> prpQcarDrivers = new ArrayList<PrpQcarDriver>(0);
	private List<PrpQmainSub> prpQmainSubs = new ArrayList<PrpQmainSub>(0);
	private List<PrpQname> prpQnames = new ArrayList<PrpQname>(0);
	private List<PrpQaddress> prpQaddresses = new ArrayList<PrpQaddress>(0);
	private List<PrpQtrafficRecord> prpQtrafficRecords = new ArrayList<PrpQtrafficRecord>(
			0);
	private List<PrpQplan> prpQplans = new ArrayList<PrpQplan>(0);
	private List<PrpQitemPlane> prpQitemPlanes = new ArrayList<PrpQitemPlane>(0);
	private List<PrpQitemCarExt> prpQitemCarExts = new ArrayList<PrpQitemCarExt>(0);
	private List<PrpQinsured> prpQinsureds = new ArrayList<PrpQinsured>(0);
	private List<PrpQcarShipTax> prpQcarShipTaxes = new ArrayList<PrpQcarShipTax>(0);
	private List<PrpQinsuredNature> prpQinsuredNatures = new ArrayList<PrpQinsuredNature>(0);
	private List<PrpQmainProp> prpQmainProps = new ArrayList<PrpQmainProp>(0);
	private List<PrpQproduct> prpQproducts = new ArrayList<PrpQproduct>(0);
	private List<PrpQlimit> prpQlimits = new ArrayList<PrpQlimit>(0);
	private List<PrpQitemKind> prpQitemKinds = new ArrayList<PrpQitemKind>(0);
	private List<PrpQexchange> prpQexchanges = new ArrayList<PrpQexchange>(0);
	private List<PrpQfee> prpQfees = new ArrayList<PrpQfee>(0);
//	private List<PrpQmainCasualty> prpQmainCasualtys = new ArrayList<PrpQmainCasualty>(0);
	private List<PrpQmainLoan> prpQmainLoans = new ArrayList<PrpQmainLoan>(0);
	private List<PrpQbatch> prpQbatchs = new ArrayList<PrpQbatch>(0);
	private List<PrpQmainConstruct> prpQmainConstructs = new ArrayList<PrpQmainConstruct>(
			0);
	private List<PrpQmainHealth> prpQmainHealths = new ArrayList<PrpQmainHealth>(0);
	private List<PrpQmainCredit> prpQmainCredits = new ArrayList<PrpQmainCredit>(
			0);
	private List<PrpQmainLiab> prpQmainLiabs = new ArrayList<PrpQmainLiab>(0);
	private List<PrpQmainInvest> prpQmainInvests = new ArrayList<PrpQmainInvest>(
			0);
	private List<PrpQmainAgri> prpQmainAgris = new ArrayList<PrpQmainAgri>(0);
	//AND BY XULI 20130706
    private List<PrpQcommission> prpQcommissions = new ArrayList<PrpQcommission>(0);
    private List<PrpQcommissionDetail> prpQcommissionDetails = new ArrayList<PrpQcommissionDetail>(0);
    private List<PrpQmortgagee> prpQmortgagees = new ArrayList<PrpQmortgagee>(0);
    private List<PrpQnote> prpQnotes = new ArrayList<PrpQnote>(0);
    private List<PrpQscheme> prpQschemes = new ArrayList<PrpQscheme>(0);
    private List<PrpQreinfo> prpQreinfos = new ArrayList<PrpQreinfo>(0);
    private List<PrpQriskasst> prpQriskassts = new ArrayList<PrpQriskasst>(0);
    private List<PrpQexceptItem> prpQexceptItems = new ArrayList<PrpQexceptItem>(0);
    private List<PrpQCargoItem> prpQCargoItems = new ArrayList<PrpQCargoItem>(0);
    // 水险AV新增类添加  by yjm
	private List<PrpQplaneDevice> prpQplaneDevices = new ArrayList<PrpQplaneDevice>(0);//飞机设备
	private List<PrpQplane> prpQplanes = new ArrayList<PrpQplane>(0);//飞机讯息
	private List<PrpQplaneDriver> prpQplaneDrivers = new ArrayList<PrpQplaneDriver>(0);//飞行员讯息
	private List<PrpQplaneSafe> prpQplaneSafes = new ArrayList<PrpQplaneSafe>(0);//飞安记录讯息
	private List<PrpQshipSafe> prpQshipSafes = new ArrayList<PrpQshipSafe>(0);//船舶出险信息
	
	//责任险TE航线信息 by songxin
	private List<PrpQairline> prpQairlines = new ArrayList<PrpQairline>(0);
	//機票信息
	private List<PrpQticket> prpQtickets = new ArrayList<PrpQticket>(0);
	
    // mantis： FIR0145，處理人員：DP0706，需求單編號：FIR0145.中信新件流程改造-新增受理編號及調整撤單功能
	// 受理編號
    private String orderSeq;
    
 	// mantis： FIR0145，處理人員：DP0706，需求單編號：FIR0145.中信新件流程改造-新增送件類別
    // 送件類別
    private String sendType;
    
    // mantis： FIR0166，處理人員：DP0706，需求單編號：FIR0166.新增條款交付方式
    // 條款交付方式
    private String clauseSendType;
    
    private String normastatus;//核心擴增欄位：地址正規化狀態，mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業
    
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQcommission> getPrpQcommissions() {
		return prpQcommissions;
	}

	public void setPrpQcommissions(List<PrpQcommission> prpQcommissions) {
		this.prpQcommissions = prpQcommissions;
	}
	
	
	
	/**
     * prpQCargoItems.
     *
     * @return  the prpQCargoItems
     * @since   JDK 1.7
     */
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
    public List<PrpQCargoItem> getPrpQCargoItems() {
        return prpQCargoItems;
    }

    /**
     * prpQCargoItems.
     *
     * @param   prpQCargoItems    the prpQCargoItems to set
     * @since   JDK 1.7
     */
    public void setPrpQCargoItems(List<PrpQCargoItem> prpQCargoItems) {
        this.prpQCargoItems = prpQCargoItems;
    }

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQcommissionDetail> getPrpQcommissionDetails() {
		return prpQcommissionDetails;
	}

	public void setPrpQcommissionDetails(
			List<PrpQcommissionDetail> prpQcommissionDetails) {
		this.prpQcommissionDetails = prpQcommissionDetails;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQnote> getPrpQnotes() {
		return prpQnotes;
	}

	public void setPrpQnotes(List<PrpQnote> prpQnotes) {
		this.prpQnotes = prpQnotes;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQscheme> getPrpQschemes() {
		return prpQschemes;
	}

	public void setPrpQschemes(List<PrpQscheme> prpQschemes) {
		this.prpQschemes = prpQschemes;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQreinfo> getPrpQreinfos() {
		return prpQreinfos;
	}

	public void setPrpQreinfos(List<PrpQreinfo> prpQreinfos) {
		this.prpQreinfos = prpQreinfos;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQriskasst> getPrpQriskassts() {
		return prpQriskassts;
	}

	public void setPrpQriskassts(List<PrpQriskasst> prpQriskassts) {
		this.prpQriskassts = prpQriskassts;
	}

	//AND BY XULI 20130706
	/**
	 * 类PrpQmain的默认构造方法
	 */
	public PrpQmain() {
	}
	
	@Transient
	public boolean getResult(){
		return this.result;
	}
	public void setResult(boolean result){
		this.result=result;
	}
	/**
	 * 属性投保单号码的getter方法
	 */
	// @GenericGenerator(name = "generator", strategy = "foreign", parameters =
	// @Parameter(name = "property", value = "prpQmain"))
//	@Id
//	@GeneratedValue(generator = "generator")
//	@Column(name = "PROPOSALNO")
	
	@Id
	@Column(name="PROPOSALNO")
	    
	public String getProposalNo() {
		return this.proposalNo;
	}

	/**
	 * 属性投保单号码的setter方法
	 */
	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}
	/**
	 * 属性保单权限的getter方法
	 */
	public String getPolicyPermissions() {
		return policyPermissions;
	}
	/**
	 * 属性保单权限的setter方法
	 */
	public void setPolicyPermissions(String policyPermissions) {
		this.policyPermissions = policyPermissions;
	}
	/**
	 * 属性批准文件号码的getter方法
	 */
	public String getPolicyFilenumber() {
		return policyFilenumber;
	}
	/**
	 * 属性批准文件号码的setter方法
	 */
	public void setPolicyFilenumber(String policyFilenumber) {
		this.policyFilenumber = policyFilenumber;
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
	 * 属性虚拟险种代码的getter方法
	 */

	@Column(name = "RISKCODE")
	public String getRiskCode() {
		return this.riskCode;
	}

	/**
	 * 属性虚拟险种代码的setter方法
	 */
	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * 属性合同号的getter方法
	 */

	@Column(name = "CONTRACTNO")
	public String getContractNo() {
		return this.contractNo;
	}

	/**
	 * 属性合同号的setter方法
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * 属性保单种类的getter方法
	 */

	@Column(name = "POLICYSORT")
	public String getPolicySort() {
		return this.policySort;
	}

	/**
	 * 属性保单种类的setter方法
	 */
	public void setPolicySort(String policySort) {
		this.policySort = policySort;
	}

	/**
	 * 属性单证流水号的getter方法
	 */

	@Column(name = "PRINTNO")
	public String getPrintNo() {
		return this.printNo;
	}

	/**
	 * 属性单证流水号的setter方法
	 */
	public void setPrintNo(String printNo) {
		this.printNo = printNo;
	}

	/**
	 * 属性业务性质的getter方法
	 */

	@Column(name = "BUSINESSNATURE")
	public String getBusinessNature() {
		return this.businessNature;
	}

	/**
	 * 属性业务性质的setter方法
	 */
	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	/**
	 * 属性语言的getter方法
	 */

	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return this.language;
	}

	/**
	 * 属性语言的setter方法
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
	 * 属性应用代码的getter方法
	 */

	@Column(name = "APPLICODE")
	public String getAppliCode() {
		return this.appliCode;
	}

	/**
	 * 属性应用代码的setter方法
	 */
	public void setAppliCode(String appliCode) {
		this.appliCode = appliCode;
	}

	/**
	 * 属性投保人名称的getter方法
	 */

	@Column(name = "APPLINAME")
	public String getAppliName() {
		return this.appliName;
	}

	/**
	 * 属性投保人名称的setter方法
	 */
	public void setAppliName(String appliName) {
		this.appliName = appliName;
	}

	/**
	 * 属性投保人地址的getter方法
	 */

	@Column(name = "APPLIADDRESS")
	public String getAppliaddress() {
		return this.appliaddress;
	}

	/**
	 * 属性投保人地址的setter方法
	 */
	public void setAppliaddress(String appliaddress) {
		this.appliaddress = appliaddress;
	}

	/**
	 * 属性关系人代码的getter方法
	 */

	@Column(name = "INSUREDCODE")
	public String getInsuredCode() {
		return this.insuredCode;
	}

	/**
	 * 属性关系人代码的setter方法
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}

	/**
	 * 属性关系人名字的getter方法
	 */

	@Column(name = "INSUREDNAME")
	public String getInsuredName() {
		return this.insuredName;
	}

	/**
	 * 属性关系人名字的setter方法
	 */
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	/**
	 * 属性关系人地址的getter方法
	 */

	@Column(name = "INSUREDADDRESS")
	public String getInsuredAddress() {
		return this.insuredAddress;
	}

	/**
	 * 属性关系人地址的setter方法
	 */
	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}

	/**
	 * 属性操作时间的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERATEDATE")
	public Date getOperateDate() {
		return this.operateDate;
	}

	/**
	 * 属性操作时间的setter方法
	 */
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	/**
	 * 属性备用7的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTDATE")
	public Date getStartDate() {
		return this.startDate;
	}

	/**
	 * 属性备用7的setter方法
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BASESTARTDATE")
    public Date getBaseStartDate() {
		return baseStartDate;
	}

	public void setBaseStartDate(Date baseStartDate) {
		this.baseStartDate = baseStartDate;
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

	/**
	 * 属性备用8的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDDATE")
	public Date getEndDate() {
		return this.endDate;
	}

	/**
	 * 属性备用8的setter方法
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
	 * 属性净费率的getter方法
	 */

	@Column(name = "PURERATE")
	public BigDecimal getPureRate() {
		return this.pureRate;
	}

	/**
	 * 属性净费率的setter方法
	 */
	public void setPureRate(BigDecimal pureRate) {
		this.pureRate = pureRate;
	}

	/**
	 * 属性手续费率/浮动费率的getter方法
	 */

	@Column(name = "DISRATE")
	public BigDecimal getDisRate() {
		return this.disRate;
	}

	/**
	 * 属性手续费率/浮动费率的setter方法
	 */
	public void setDisRate(BigDecimal disRate) {
		this.disRate = disRate;
	}

	/**
	 * 属性折扣率的getter方法
	 */

	@Column(name = "DISCOUNT")
	public BigDecimal getDiscount() {
		return this.discount;
	}

	/**
	 * 属性折扣率的setter方法
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
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
	 * 属性总价款的getter方法
	 */

	@Column(name = "SUMVALUE")
	public BigDecimal getSumValue() {
		return this.sumValue;
	}

	/**
	 * 属性总价款的setter方法
	 */
	public void setSumValue(BigDecimal sumValue) {
		this.sumValue = sumValue;
	}

	/**
	 * 属性总保额的getter方法
	 */

	@Column(name = "SUMAMOUNT")
	public BigDecimal getSumAmount() {
		return this.sumAmount;
	}

	/**
	 * 属性总保额的setter方法
	 */
	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	/**
	 * 属性总折扣金额的getter方法
	 */

	@Column(name = "SUMDISCOUNT")
	public BigDecimal getSumDiscount() {
		return this.sumDiscount;
	}

	/**
	 * 属性总折扣金额的setter方法
	 */
	public void setSumDiscount(BigDecimal sumDiscount) {
		this.sumDiscount = sumDiscount;
	}

	/**
	 * 属性总保费的getter方法
	 */

	@Column(name = "SUMPREMIUM")
	public BigDecimal getSumPremium() {
		return this.sumPremium;
	}

	/**
	 * 属性总保费的setter方法
	 */
	public void setSumPremium(BigDecimal sumPremium) {
		this.sumPremium = sumPremium;
	}

	/**
	 * 属性总附加险保费的getter方法
	 */

	@Column(name = "SUMSUBPREM")
	public BigDecimal getSumSubPrem() {
		return this.sumSubPrem;
	}

	/**
	 * 属性总附加险保费的setter方法
	 */
	public void setSumSubPrem(BigDecimal sumSubPrem) {
		this.sumSubPrem = sumSubPrem;
	}

	/**
	 * 属性被保险总数量/人数/户数的getter方法
	 */

	@Column(name = "SUMQUANTITY")
	public Long getSumQuantity() {
		return this.sumQuantity;
	}

	/**
	 * 属性被保险总数量/人数/户数的setter方法
	 */
	public void setSumQuantity(Long sumQuantity) {
		this.sumQuantity = sumQuantity;
	}

	/**
	 * 属性司法代码的getter方法
	 */

	@Column(name = "JUDICALCODE")
	public String getJudicalCode() {
		return this.judicalCode;
	}

	/**
	 * 属性司法代码的setter方法
	 */
	public void setJudicalCode(String judicalCode) {
		this.judicalCode = judicalCode;
	}

	/**
	 * 属性司法管辖的getter方法
	 */

	@Column(name = "JUDICALSCOPE")
	public String getJudicalScope() {
		return this.judicalScope;
	}

	/**
	 * 属性司法管辖的setter方法
	 */
	public void setJudicalScope(String judicalScope) {
		this.judicalScope = judicalScope;
	}

	/**
	 * 属性是否自动转帐续保标志的getter方法
	 */

	@Column(name = "AUTOTRANSRENEWFLAG")
	public String getAutoTransRenewFlag() {
		return this.autoTransRenewFlag;
	}

	/**
	 * 属性是否自动转帐续保标志的setter方法
	 */
	public void setAutoTransRenewFlag(String autoTransRenewFlag) {
		this.autoTransRenewFlag = autoTransRenewFlag;
	}

	/**
	 * 属性争议解决方式的getter方法
	 */

	@Column(name = "ARGUESOLUTION")
	public String getArgueSolution() {
		return this.argueSolution;
	}

	/**
	 * 属性争议解决方式的setter方法
	 */
	public void setArgueSolution(String argueSolution) {
		this.argueSolution = argueSolution;
	}

	/**
	 * 属性仲裁委员会名称的getter方法
	 */

	@Column(name = "ARBITBOARDNAME")
	public String getArbitBoardName() {
		return this.arbitBoardName;
	}

	/**
	 * 属性仲裁委员会名称的setter方法
	 */
	public void setArbitBoardName(String arbitBoardName) {
		this.arbitBoardName = arbitBoardName;
	}

	/**
	 * 属性约定分期交费次数的getter方法
	 */

	@Column(name = "PAYTIMES")
	public Integer getPayTimes() {
		return this.payTimes;
	}

	/**
	 * 属性约定分期交费次数的setter方法
	 */
	public void setPayTimes(Integer payTimes) {
		this.payTimes = payTimes;
	}

	/**
	 * 属性批改次数的getter方法
	 */

	@Column(name = "ENDORSETIMES")
	public Integer getEndorseTimes() {
		return this.endorseTimes;
	}

	/**
	 * 属性批改次数的setter方法
	 */
	public void setEndorseTimes(Integer endorseTimes) {
		this.endorseTimes = endorseTimes;
	}

	/**
	 * 属性理赔次数的getter方法
	 */

	@Column(name = "CLAIMTIMES")
	public Integer getClaimTimes() {
		return this.claimTimes;
	}

	/**
	 * 属性理赔次数的setter方法
	 */
	public void setClaimTimes(Integer claimTimes) {
		this.claimTimes = claimTimes;
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
	 * 属性签单地点的getter方法
	 */

	@Column(name = "OPERATESITE")
	public String getOperateSite() {
		return this.operateSite;
	}

	/**
	 * 属性签单地点的setter方法
	 */
	public void setOperateSite(String operateSite) {
		this.operateSite = operateSite;
	}

	/**
	 * 属性机关代码的getter方法
	 */

	@Column(name = "COMCODE")
	public String getComCode() {
		return this.comCode;
	}

	/**
	 * 属性机关代码的setter方法
	 */
	public void setComCode(String comCode) {
		this.comCode = comCode;
	}
	/**
	 * 變量loginCom的getter方法
	 * @return loginCom
	 */
    @Column(name="LOGINCOM")
	public String getLoginCom() {
		return loginCom;
	}


	/**
	 * 變量loginCom的setter方法
	 * @param loginCom  loginCom
	 */
	public void setLoginCom(String loginCom) {
		this.loginCom = loginCom;
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
	 * 属性归属人的getter方法
	 */

	@Column(name = "HANDLER1CODE")
	public String getHandler1Code() {
		return this.handler1Code;
	}

	/**
	 * 属性归属人的setter方法
	 */
	public void setHandler1Code(String handler1Code) {
		this.handler1Code = handler1Code;
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
	 * 属性最终核保人代码的getter方法
	 */

	@Column(name = "UNDERWRITECODE")
	public String getUnderWriteCode() {
		return this.underWriteCode;
	}

	/**
	 * 属性最终核保人代码的setter方法
	 */
	public void setUnderWriteCode(String underWriteCode) {
		this.underWriteCode = underWriteCode;
	}

	/**
	 * 属性最终核保人名称的getter方法
	 */

	@Column(name = "UNDERWRITENAME")
	public String getUnderWriteName() {
		return this.underWriteName;
	}

	/**
	 * 属性最终核保人名称的setter方法
	 */
	public void setUnderWriteName(String underWriteName) {
		this.underWriteName = underWriteName;
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
	 * 属性报表数据生成日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INPUTDATE")
	public Date getInputDate() {
		return this.inputDate;
	}

	/**
	 * 属性报表数据生成日期的setter方法
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * 属性报表数据生成时间的getter方法
	 */

	@Column(name = "INPUTHOUR")
	public Integer getInputHour() {
		return this.inputHour;
	}

	/**
	 * 属性报表数据生成时间的setter方法
	 */
	public void setInputHour(Integer inputHour) {
		this.inputHour = inputHour;
	}

	/**
	 * 属性核保完成日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UNDERWRITEENDDATE")
	public Date getUnderWriteEndDate() {
		return this.underWriteEndDate;
	}

	/**
	 * 属性核保完成日期的setter方法
	 */
	public void setUnderWriteEndDate(Date underWriteEndDate) {
		this.underWriteEndDate = underWriteEndDate;
	}

	/**
	 * 属性保单统计年月的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
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
	 * 属性分保类型1-是分出公司 2-不是分出公司的getter方法
	 */

	@Column(name = "COINSFLAG")
	public String getCoinsFlag() {
		return this.coinsFlag;
	}

	/**
	 * 属性分保类型1-是分出公司 2-不是分出公司的setter方法
	 */
	public void setCoinsFlag(String coinsFlag) {
		this.coinsFlag = coinsFlag;
	}

	/**
	 * 属性商业分保标志的getter方法
	 */

	@Column(name = "REINSFLAG")
	public String getReinsFlag() {
		return this.reinsFlag;
	}

	/**
	 * 属性商业分保标志的setter方法
	 */
	public void setReinsFlag(String reinsFlag) {
		this.reinsFlag = reinsFlag;
	}

	/**
	 * 属性统保标志的getter方法
	 */

	@Column(name = "ALLINSFLAG")
	public String getAllinsFlag() {
		return this.allinsFlag;
	}

	/**
	 * 属性统保标志的setter方法
	 */
	public void setAllinsFlag(String allinsFlag) {
		this.allinsFlag = allinsFlag;
	}

	/**
	 * 属性核保标志的getter方法
	 */

	@Column(name = "UNDERWRITEFLAG")
	public String getUnderWriteFlag() {
		return this.underWriteFlag;
	}

	/**
	 * 属性核保标志的setter方法
	 */
	public void setUnderWriteFlag(String underWriteFlag) {
		this.underWriteFlag = underWriteFlag;
	}

	/**
	 * 属性其它标志字段的getter方法
	 */

	@Column(name = "OTHFLAG")
	public String getOthFlag() {
		return this.othFlag;
	}

	/**
	 * 属性其它标志字段的setter方法
	 */
	public void setOthFlag(String othFlag) {
		this.othFlag = othFlag;
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
	 * 属性DISRATE1的getter方法
	 */

	@Column(name = "DISRATE1")
	public BigDecimal getDisRate1() {
		return this.disRate1;
	}

	/**
	 * 属性DISRATE1的setter方法
	 */
	public void setDisRate1(BigDecimal disRate1) {
		this.disRate1 = disRate1;
	}

	/**
	 * 属性businessflag的getter方法
	 */

	@Column(name = "BUSINESSFLAG")
	public String getBusinessflag() {
		return this.businessflag;
	}

	/**
	 * 属性businessflag的setter方法
	 */
	public void setBusinessflag(String businessflag) {
		this.businessflag = businessflag;
	}

	/**
	 * 属性UPDATERCODE的getter方法
	 */

	@Column(name = "UPDATERCODE")
	public String getUpdateRCode() {
		return this.updateRCode;
	}

	/**
	 * 属性UPDATERCODE的setter方法
	 */
	public void setUpdateRCode(String updateRCode) {
		this.updateRCode = updateRCode;
	}

	/**
	 * 属性更新日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 属性更新日期的setter方法
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 属性更新时间的getter方法
	 */

	@Column(name = "UPDATEHOUR")
	public String getUpdateHour() {
		return this.updateHour;
	}

	/**
	 * 属性更新时间的setter方法
	 */
	public void setUpdateHour(String updateHour) {
		this.updateHour = updateHour;
	}

	/**
	 * 属性属性签单日期的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SIGNDATE")
	public Date getSignDate() {
		return this.signDate;
	}

	/**
	 * 属性属性签单日期的setter方法
	 */
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	/**
	 * 属性SHAREHOLDERFLAG的getter方法
	 */

	@Column(name = "SHAREHOLDERFLAG")
	public String getShareHolderFlag() {
		return this.shareHolderFlag;
	}

	/**
	 * 属性SHAREHOLDERFLAG的setter方法
	 */
	public void setShareHolderFlag(String shareHolderFlag) {
		this.shareHolderFlag = shareHolderFlag;
	}

	/**
	 * 属性协议号的getter方法
	 */

	@Column(name = "AGREEMENTNO")
	public String getAgreementNo() {
		return this.agreementNo;
	}

	/**
	 * 属性协议号的setter方法
	 */
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	/**
	 * 属性INQUIRYNO的getter方法
	 */

	@Column(name = "INQUIRYNO")
	public String getInquiryNo() {
		return this.inquiryNo;
	}

	/**
	 * 属性INQUIRYNO的setter方法
	 */
	public void setInquiryNo(String inquiryNo) {
		this.inquiryNo = inquiryNo;
	}

	/**
	 * 属性保险费支付办法的getter方法
	 */

	@Column(name = "PAYMODE")
	public String getPayMode() {
		return this.payMode;
	}

	/**
	 * 属性保险费支付办法的setter方法
	 */
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	/**
	 * 属性REMARK的getter方法
	 */

	@Column(name = "REMARK")
	public String getRemark() {
		return this.remark;
	}

	/**
	 * 属性REMARK的setter方法
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 属性合同号码的getter方法
	 */

	@Column(name = "POLICYNO")
	public String getPolicyNo() {
		return this.policyNo;
	}

	/**
	 * 属性合同号码的setter方法
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * 属性单证号码的getter方法
	 */

	@Column(name = "VISACODE")
	public String getVisaCode() {
		return this.visaCode;
	}

	/**
	 * 属性单证号码的setter方法
	 */
	public void setVisaCode(String visaCode) {
		this.visaCode = visaCode;
	}

	/**
	 * 属性MANUALTYPE的getter方法
	 */

	@Column(name = "MANUALTYPE")
	public String getManualType() {
		return this.manualType;
	}

	/**
	 * 属性MANUALTYPE的setter方法
	 */
	public void setManualType(String manualType) {
		this.manualType = manualType;
	}

	/**
	 * 属性NATIONFLAG的getter方法
	 */

	@Column(name = "NATIONFLAG")
	public String getNationFlag() {
		return this.nationFlag;
	}

	/**
	 * 属性NATIONFLAG的setter方法
	 */
	public void setNationFlag(String nationFlag) {
		this.nationFlag = nationFlag;
	}

	/**
	 * 属性开始分钟的getter方法
	 */

	@Column(name = "STARTMINUTE")
	public Byte getStartMinute() {
		return this.startMinute;
	}

	/**
	 * 属性开始分钟的setter方法
	 */
	public void setStartMinute(Byte startMinute) {
		this.startMinute = startMinute;
	}

	/**
	 * 属性结束分钟的getter方法
	 */

	@Column(name = "ENDMINUTE")
	public Byte getEndMinute() {
		return this.endMinute;
	}

	/**
	 * 属性结束分钟的setter方法
	 */
	public void setEndMinute(Byte endMinute) {
		this.endMinute = endMinute;
	}

	/**
	 * 属性见费出单标志位的getter方法
	 */

	@Column(name = "JFEEFLAG")
	public String getJfeeFlag() {
		return this.jfeeFlag;
	}

	/**
	 * 属性见费出单标志位的setter方法
	 */
	public void setJfeeFlag(String jfeeFlag) {
		this.jfeeFlag = jfeeFlag;
	}

	/**
	 * 属性PRECHECKDATE的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRECHECKDATE")
	public Date getPreCheckDate() {
		return this.preCheckDate;
	}

	/**
	 * 属性PRECHECKDATE的setter方法
	 */
	public void setPreCheckDate(Date preCheckDate) {
		this.preCheckDate = preCheckDate;
	}

	/**
     * 属性報關行名稱的getter方法
     */
    @Column(name = "CUSTOMSBROKERNAME")
    public String getCustomsBrokerName() {
	return customsBrokerName;
    }
    
    /**
     * 属性報關行名稱的setter方法
     */
    public void setCustomsBrokerName(String customsBrokerName) {
	this.customsBrokerName = customsBrokerName;
    }
    
	/**
	 * 属性经办人名字的getter方法
	 */

	@Column(name = "HANDLERNAME")
	public String getHandlerName() {
		return this.handlerName;
	}

	/**
	 * 属性经办人名字的setter方法
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * 属性HANDLER1NAME的getter方法
	 */

	@Column(name = "HANDLER1NAME")
	public String getHandler1Name() {
		return this.handler1Name;
	}

	/**
	 * 属性HANDLER1NAME的setter方法
	 */
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}

	/**
	 * 属性PAYREFCODE的getter方法
	 */

	@Column(name = "PAYREFCODE")
	public String getPayRefCode() {
		return this.payRefCode;
	}

	/**
	 * 属性PAYREFCODE的setter方法
	 */
	public void setPayRefCode(String payRefCode) {
		this.payRefCode = payRefCode;
	}

	/**
	 * 属性PAYREFNAME的getter方法
	 */

	@Column(name = "PAYREFNAME")
	public String getPayRefName() {
		return this.payRefName;
	}

	/**
	 * 属性PAYREFNAME的setter方法
	 */
	public void setPayRefName(String payRefName) {
		this.payRefName = payRefName;
	}

	/**
	 * 属性PAYREFTIME的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PAYREFTIME")
	public Date getPayRefTime() {
		return this.payRefTime;
	}

	/**
	 * 属性PAYREFTIME的setter方法
	 */
	public void setPayRefTime(Date payRefTime) {
		this.payRefTime = payRefTime;
	}

	/**
	 * 属性PRINTTIME的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PRINTTIME")
	public Date getPrintTime() {
		return this.printTime;
	}

	/**
	 * 属性PRINTTIME的setter方法
	 */
	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	/**
	 * 属性AGRITYPE的getter方法
	 */

	@Column(name = "AGRITYPE")
	public String getAgriType() {
		return this.agriType;
	}

	/**
	 * 属性AGRITYPE的setter方法
	 */
	public void setAgriType(String agriType) {
		this.agriType = agriType;
	}

	/**
	 * 属性与现有系统编码统一的getter方法
	 */

	@Column(name = "SUBBUSINESSNATURE")
	public String getSubBusinessNature() {
		return this.subBusinessNature;
	}

	/**
	 * 属性与现有系统编码统一的setter方法
	 */
	public void setSubBusinessNature(String subBusinessNature) {
		this.subBusinessNature = subBusinessNature;
	}

	/**
	 * 属性开户代码的getter方法
	 */

	@Column(name = "BANKCODE")
	public String getBankCode() {
		return this.bankCode;
	}

	/**
	 * 属性开户代码的setter方法
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 属性CHANNELTYPE的getter方法
	 */

	@Column(name = "CHANNELTYPE")
	public String getChannelType() {
		return this.channelType;
	}

	/**
	 * 属性CHANNELTYPE的setter方法
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * 属性兑换率的getter方法
	 */

	@Column(name = "EXCHANGERATE")
	public BigDecimal getExchangeRate() {
		return this.exchangeRate;
	}

	/**
	 * 属性兑换率的setter方法
	 */
	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	/**
	 * 属性projectsflag的getter方法
	 */

	@Column(name = "PROJECTSFLAG")
	public String getProjectsflag() {
		return this.projectsflag;
	}

	/**
	 * 属性projectsflag的setter方法
	 */
	public void setProjectsflag(String projectsflag) {
		this.projectsflag = projectsflag;
	}

	/**
	 * 属性见费出单类型的getter方法
	 */

	@Column(name = "JFEEPAYTYPE")
	public String getJfeePayType() {
		return this.jfeePayType;
	}

	/**
	 * 属性见费出单类型的setter方法
	 */
	public void setJfeePayType(String jfeePayType) {
		this.jfeePayType = jfeePayType;
	}

	/**
	 * 属性保单级别的getter方法
	 */

	@Column(name = "PROPOSALLEVEL")
	public String getProposalLevel() {
		return this.proposalLevel;
	}

	/**
	 * 属性保单级别的setter方法
	 */
	public void setProposalLevel(String proposalLevel) {
		this.proposalLevel = proposalLevel;
	}

	/**
	 * 属性STOPTIMES的getter方法
	 */

	@Column(name = "STOPTIMES")
	public String getStopTimes() {
		return this.stopTimes;
	}

	/**
	 * 属性STOPTIMES的setter方法
	 */
	public void setStopTimes(String stopTimes) {
		this.stopTimes = stopTimes;
	}

	/**
	 * 属性EFFECTIVEIMMEDIATELYFLAG的getter方法
	 */

	@Column(name = "EFFECTIVEIMMEDIATELYFLAG")
	public String getEffectiveimmediatelyFlag() {
		return this.effectiveimmediatelyFlag;
	}

	/**
	 * 属性EFFECTIVEIMMEDIATELYFLAG的setter方法
	 */
	public void setEffectiveimmediatelyFlag(String effectiveimmediatelyFlag) {
		this.effectiveimmediatelyFlag = effectiveimmediatelyFlag;
	}

	/**
	 * 属性NEWSTARTDATE的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NEWSTARTDATE")
	public Date getNewStartDate() {
		return this.newStartDate;
	}

	/**
	 * 属性NEWSTARTDATE的setter方法
	 */
	public void setNewStartDate(Date newStartDate) {
		this.newStartDate = newStartDate;
	}

	/**
	 * 属性NEWENDDATE的getter方法
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NEWENDDATE")
	public Date getNewEndDate() {
		return this.newEndDate;
	}

	/**
	 * 属性NEWENDDATE的setter方法
	 */
	public void setNewEndDate(Date newEndDate) {
		this.newEndDate = newEndDate;
	}

	/**
	 * 属性GROUPTYPE的getter方法
	 */

	@Column(name = "GROUPTYPE")
	public String getGroupType() {
		return this.groupType;
	}

	/**
	 * 属性GROUPTYPE的setter方法
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * 属性STARTSTAGES的getter方法
	 */

	@Column(name = "STARTSTAGES")
	public Integer getStartStages() {
		return this.startStages;
	}

	/**
	 * 属性STARTSTAGES的setter方法
	 */
	public void setStartStages(Integer startStages) {
		this.startStages = startStages;
	}

	/**
	 * 属性quoteno的getter方法
	 */

	@Column(name = "QUOTENO")
	public String getQuoteno() {
		return this.quoteno;
	}

	/**
	 * 属性quoteno的setter方法
	 */
	public void setQuoteno(String quoteno) {
		this.quoteno = quoteno;
	}

	/**
	 * 属性CONTRIBUTIONLEVEL的getter方法
	 */

	@Column(name = "CONTRIBUTIONLEVEL")
	public String getContributionLevel() {
		return this.contributionLevel;
	}

	/**
	 * 属性CONTRIBUTIONLEVEL的setter方法
	 */
	public void setContributionLevel(String contributionLevel) {
		this.contributionLevel = contributionLevel;
	}

	/**
	 * 属性caseno的getter方法
	 */

	@Column(name = "CASENO")
	public String getCaseno() {
		return this.caseno;
	}

	/**
	 * 属性caseno的setter方法
	 */
	public void setCaseno(String caseno) {
		this.caseno = caseno;
	}

	/**
	 * 属性DECLAREFLAG的getter方法
	 */

	@Column(name = "DECLAREFLAG")
	public String getDeclareFlag() {
		return this.declareFlag;
	}

	/**
	 * 属性DECLAREFLAG的setter方法
	 */
	public void setDeclareFlag(String declareFlag) {
		this.declareFlag = declareFlag;
	}

	/**
	 * 属性BUSINESSTYPEFLAG的getter方法
	 */

	 @Column(name = "BUSINESSTYPEFLAG")
	 public String getBusinessTypeFlag() {
	 return this.businessTypeFlag;
	 }
	
	 /**
	 * 属性BUSINESSTYPEFLAG的setter方法
	 */
	 public void setBusinessTypeFlag(String businessTypeFlag) {
	 this.businessTypeFlag = businessTypeFlag;
	 }

	 /**
	     * 属性锁定人代码的getter方法
	     */ 
	    
	    @Column(name="LOCKERCODE")

	    public String getLockerCode() {
	        return this.lockerCode;
	    }
	    /**
	     * 属性锁定人代码的setter方法
	     */
	    public void setLockerCode(String lockerCode) {
	        this.lockerCode = lockerCode;
	    }
	/**
	 * 属性PROGRAMNO的getter方法
	 */

	// @Column(name = "PROGRAMNO")
	// public String getProgramNO() {
	// return this.programNO;
	// }
	//
	// /**
	// * 属性PROGRAMNO的setter方法
	// */
	// public void setProgramNO(String programNO) {
	// this.programNO = programNO;
	// }

	/**
	 * 属性channelcode的getter方法
	 */

	@Column(name = "CHANNELCODE")
	public String getChannelcode() {
		return this.channelcode;
	}

	/**
	 * 属性channelcode的setter方法
	 */
	public void setChannelcode(String channelcode) {
		this.channelcode = channelcode;
	}

	/**
	 * 属性biznosysflag的getter方法
	 */

	@Column(name = "BIZNOSYSFLAG")
	public String getBiznosysflag() {
		return this.biznosysflag;
	}

	/**
	 * 属性biznosysflag的setter方法
	 */
	public void setBiznosysflag(String biznosysflag) {
		this.biznosysflag = biznosysflag;
	}

	/**
	 * 属性businessrecmark的getter方法
	 */

	@Column(name = "BUSINESSRECMARK")
	public String getBusinessrecmark() {
		return this.businessrecmark;
	}

	/**
	 * 属性businessrecmark的setter方法
	 */
	public void setBusinessrecmark(String businessrecmark) {
		this.businessrecmark = businessrecmark;
	}

	/**
	 * 属性undwrtmark的getter方法
	 */

	@Column(name = "UNDWRTMARK")
	public String getUndwrtmark() {
		return this.undwrtmark;
	}

	/**
	 * 属性undwrtmark的setter方法
	 */
	public void setUndwrtmark(String undwrtmark) {
		this.undwrtmark = undwrtmark;
	}

	/**
	 * 属性isundwrtflag的getter方法
	 */

	@Column(name = "ISUNDWRTFLAG")
	public String getIsundwrtflag() {
		return this.isundwrtflag;
	}

	/**
	 * 属性isundwrtflag的setter方法
	 */
	public void setIsundwrtflag(String isundwrtflag) {
		this.isundwrtflag = isundwrtflag;
	}

	/**
	 * 属性agentmaxcomission的getter方法
	 */

	@Column(name = "AGENTMAXCOMISSION")
	public BigDecimal getAgentmaxcomission() {
		return this.agentmaxcomission;
	}

	/**
	 * 属性agentmaxcomission的setter方法
	 */
	public void setAgentmaxcomission(BigDecimal agentmaxcomission) {
		this.agentmaxcomission = agentmaxcomission;
	}

	/**
	 * 属性bankflag的getter方法
	 */

	@Column(name = "BANKFLAG")
	public String getBankflag() {
		return this.bankflag;
	}

	/**
	 * 属性bankflag的setter方法
	 */
	public void setBankflag(String bankflag) {
		this.bankflag = bankflag;
	}

	/**
	 * 属性prpQengages的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQengage> getPrpQengages() {
		return this.prpQengages;
	}

	/**
	 * 属性prpQengages的setter方法
	 */
	public void setPrpQengages(List<PrpQengage> prpQengages) {
		this.prpQengages = prpQengages;
	}

	/**
	 * 属性prpQItemProps的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemProp> getPrpQitemProps() {
		return this.prpQitemProps;
	}

	/**
	 * 属性prpQItemProps的setter方法
	 */
	public void setPrpQitemProps(List<PrpQitemProp> prpQitemProps) {
		this.prpQitemProps = prpQitemProps;
	}

	/**
	 * 属性prpQguaranties的getter方法
	 */
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "prpQmain")
	// public List<PrpQguaranty> getPrpQguaranties() {
	// return this.prpQguaranties;
	// }

	/**
	 * 属性prpQguaranties的setter方法
	 */
	// public void setPrpQguaranties(List<PrpQguaranty> prpQguaranties) {
	// this.prpQguaranties = prpQguaranties;
	// }

	/**
	 * 属性prpQprintExchanges的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQprintExchange> getPrpQprintExchanges() {
		return this.prpQprintExchanges;
	}

	/**
	 * 属性prpQprintExchanges的setter方法
	 */
	public void setPrpQprintExchanges(List<PrpQprintExchange> prpQprintExchanges) {
		this.prpQprintExchanges = prpQprintExchanges;
	}

	/**
	 * 属性PRPQMAIN的getter方法
	 */
	/**
	 * 属性prpQmainCarGoSubs的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainCargoSub> getPrpQmainCarGoSubs() {
		return this.prpQmainCarGoSubs;
	}

	/**
	 * 属性prpQmainCargoSubs的setter方法
	 */
	public void setPrpQmainCarGoSubs(List<PrpQmainCargoSub> prpQmainCarGoSubs) {
		this.prpQmainCarGoSubs = prpQmainCarGoSubs;
	}

	/**
	 * 属性prpQcarDrivers的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQcarDriver> getPrpQcarDrivers() {
		return this.prpQcarDrivers;
	}

	/**
	 * 属性prpQcarDrivers的setter方法
	 */
	public void setPrpQcarDrivers(List<PrpQcarDriver> prpQcarDrivers) {
		this.prpQcarDrivers = prpQcarDrivers;
	}

	/**
	 * 属性prpQnames的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQname> getPrpQnames() {
		return this.prpQnames;
	}

	/**
	 * 属性prpQnames的setter方法
	 */
	public void setPrpQnames(List<PrpQname> prpQnames) {
		this.prpQnames = prpQnames;
	}


	/**
	 * 属性prpQaddresses的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQaddress> getPrpQaddresses() {
		return this.prpQaddresses;
	}

	/**
	 * 属性prpQaddresses的setter方法
	 */
	public void setPrpQaddresses(List<PrpQaddress> prpQaddresses) {
		this.prpQaddresses = prpQaddresses;
	}

	/**
	 * 属性prpQtrafficRecords的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQtrafficRecord> getPrpQtrafficRecords() {
		return this.prpQtrafficRecords;
	}

	/**
	 * 属性prpQtrafficRecords的setter方法
	 */
	public void setPrpQtrafficRecords(List<PrpQtrafficRecord> prpQtrafficRecords) {
		this.prpQtrafficRecords = prpQtrafficRecords;
	}

	/**
	 * 属性prpQplans的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQplan> getPrpQplans() {
		return this.prpQplans;
	}

	/**
	 * 属性prpQplans的setter方法
	 */
	public void setPrpQplans(List<PrpQplan> prpQplans) {
		this.prpQplans = prpQplans;
	}

	/**
	 * 属性prpQitemPlanes的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemPlane> getPrpQitemPlanes() {
		return this.prpQitemPlanes;
	}

	/**
	 * 属性prpQitemPlanes的setter方法
	 */
	public void setPrpQitemPlanes(List<PrpQitemPlane> prpQitemPlanes) {
		this.prpQitemPlanes = prpQitemPlanes;
	}

	/**
	 * 属性prpQinsureds的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQinsured> getPrpQinsureds() {
		return this.prpQinsureds;
	}

	/**
	 * 属性prpQinsureds的setter方法
	 */
	public void setPrpQinsureds(List<PrpQinsured> prpQinsureds) {
		this.prpQinsureds = prpQinsureds;
	}

	/**
	 * 属性prpQcarShipTaxes的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQcarShipTax> getPrpQcarShipTaxes() {
		return this.prpQcarShipTaxes;
	}

	/**
	 * 属性prpQcarShipTaxes的setter方法
	 */
	public void setPrpQcarShipTaxes(List<PrpQcarShipTax> prpQcarShipTaxes) {
		this.prpQcarShipTaxes = prpQcarShipTaxes;
	}

	// /**
	// * 属性prpQinsuredNatures的getter方法
	// */
	// @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,
	// mappedBy="prpQmain")
	//
	// public List<PrpQinsuredNature> getPrpQinsuredNatures() {
	// return this.prpQinsuredNatures;
	// }
	// /**
	// * 属性prpQinsuredNatures的setter方法
	// */
	// public void setPrpQinsuredNatures(List<PrpQinsuredNature>
	// prpQinsuredNatures) {
	// this.prpQinsuredNatures = prpQinsuredNatures;
	// }

	/**
	 * 属性prpQproducts的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQproduct> getPrpQproducts() {
		return this.prpQproducts;
	}

	/**
	 * 属性prpQproducts的setter方法
	 */
	public void setPrpQproducts(List<PrpQproduct> prpQproducts) {
		this.prpQproducts = prpQproducts;
	}

	/**
	 * 属性prpQlimits的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQlimit> getPrpQlimits() {
		return this.prpQlimits;
	}

	/**
	 * 属性prpQlimits的setter方法
	 */
	public void setPrpQlimits(List<PrpQlimit> prpQlimits) {
		this.prpQlimits = prpQlimits;
	}

	/**
	 * 属性prpQItemKinds的getter方法
	 */
//	mantis： LIA0058，處理人員：Sam，需求單編號：LIA0058，itemkindno順序亂掉，導致保額無法正確顯示
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	@OrderBy("id.itemKindNo ASC")
	public List<PrpQitemKind> getPrpQitemKinds() {
		return this.prpQitemKinds;
	}

	/**
	 * 属性prpQItemKinds的setter方法
	 */
	public void setPrpQitemKinds(List<PrpQitemKind> prpQItemKinds) {
		this.prpQitemKinds = prpQItemKinds;
	}

	/**
	 * 属性prpQexchanges的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQexchange> getPrpQexchanges() {
		return this.prpQexchanges;
	}

	/**
	 * 属性prpQexchanges的setter方法
	 */
	public void setPrpQexchanges(List<PrpQexchange> prpQexchanges) {
		this.prpQexchanges = prpQexchanges;
	}

	/**
	 * 属性prpQfees的getter方法
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQfee> getPrpQfees() {
		return this.prpQfees;
	}

	/**
	 * 属性prpQfees的setter方法
	 */
	public void setPrpQfees(List<PrpQfee> prpQfees) {
		this.prpQfees = prpQfees;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainSub> getPrpQmainSubs() {
		return this.prpQmainSubs;
	}

	/**
	 * 属性prpQfees的setter方法
	 */
	public void setPrpQmainSubs(List<PrpQmainSub> prpQmainSubs) {
		this.prpQmainSubs = prpQmainSubs;
	}

	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainCasualty> getPrpQmainCasualtys() {
		return this.prpQmainCasualtys;
	}

	*//**
	 * 属性prpQfees的setter方法
	 *//*
	public void setPrpQmainCasualtys(List<PrpQmainCasualty> prpQmainCasualtys) {
		this.prpQmainCasualtys = prpQmainCasualtys;
	}*/

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainLoan> getPrpQmainLoans() {
		return this.prpQmainLoans;
	}

	/**
	 * 属性prpQitempProps的setter方法
	 */
	public void setPrpQmainLoans(List<PrpQmainLoan> prpQmainLoans) {
		this.prpQmainLoans = prpQmainLoans;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQbatch> getPrpQbatchs() {
		return this.prpQbatchs;
	}

	/**
	 * 属性prpCitempProps的setter方法
	 */
	public void setPrpQbatchs(List<PrpQbatch> prpQbatchs) {
		this.prpQbatchs = prpQbatchs;
	}

	// PrpCmainConstruct
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainConstruct> getPrpQmainConstructs() {
		return this.prpQmainConstructs;
	}

	/**
	 * 属性prpCitempProps的setter方法
	 */
	public void setPrpQmainConstructs(List<PrpQmainConstruct> prpQmainConstructs) {
		this.prpQmainConstructs = prpQmainConstructs;
	}
	
	//prpQmainHealth
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainHealth> getPrpQmainHealths() {
		return prpQmainHealths;
	}
	public void setPrpQmainHealths(List<PrpQmainHealth> prpQmainHealths) {
		this.prpQmainHealths = prpQmainHealths;
	}

	// prpCmainProp
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainProp> getPrpQmainProps() {
		return this.prpQmainProps;
	}

	/**
	 * 属性prpCitempProps的setter方法
	 */
	public void setPrpQmainProps(List<PrpQmainProp> prpQmainProps) {
		this.prpQmainProps = prpQmainProps;
	}

	// PrpCmainCredit
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainCredit> getPrpQmainCredits() {
		return this.prpQmainCredits;
	}

	/**
	 * 属性prpCitempProps的setter方法
	 */
	public void setPrpQmainCredits(List<PrpQmainCredit> prpQmainCredits) {
		this.prpQmainCredits = prpQmainCredits;
	}

	// prpCmainLiab
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainLiab> getPrpQmainLiabs() {
		return this.prpQmainLiabs;
	}

	/**
	 * 属性prpCitempProps的setter方法
	 */
	public void setPrpQmainLiabs(List<PrpQmainLiab> prpQmainLiabs) {
		this.prpQmainLiabs = prpQmainLiabs;
	}

	// prpCmainInvest
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainInvest> getPrpQmainInvests() {
		return this.prpQmainInvests;
	}

	/**
	 * 属性prpCitempProps的setter方法
	 */
	public void setPrpQmainInvests(List<PrpQmainInvest> prpQmainInvests) {
		this.prpQmainInvests = prpQmainInvests;
	}

	// prpCmainAgri
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainAgri> getPrpQmainAgris() {
		return this.prpQmainAgris;
	}

	/**
	 * 属性prpQmainAgris的setter方法
	 */
	public void setPrpQmainAgris(List<PrpQmainAgri> prpQmainAgris) {
		this.prpQmainAgris = prpQmainAgris;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQshipDriver> getPrpQshipDrivers() {
		return prpQshipDrivers;
	}

	public void setPrpQshipDrivers(List<PrpQshipDriver> prpQshipDrivers) {
		this.prpQshipDrivers = prpQshipDrivers;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQriskValuat> getPrpQriskValuats() {
		return prpQriskValuats;
	}

	public void setPrpQriskValuats(List<PrpQriskValuat> prpQriskValuats) {
		this.prpQriskValuats = prpQriskValuats;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQriskProfit> getPrpQriskProfits() {
		return prpQriskProfits;
	}

	public void setPrpQriskProfits(List<PrpQriskProfit> prpQriskProfits) {
		this.prpQriskProfits = prpQriskProfits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQrenewal> getPrpQrenewals() {
		return prpQrenewals;
	}

	public void setPrpQrenewals(List<PrpQrenewal> prpQrenewals) {
		this.prpQrenewals = prpQrenewals;
	}

	 @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="prpQmain")
	 public List<PrpQration> getPrpQrations() {
	 return prpQrations;
	 }
	
	 public void setPrpQrations(List<PrpQration> prpQrations) {
	 this.prpQrations = prpQrations;
	 }

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQprofitDetail> getPrpQprofitDetails() {
		return prpQprofitDetails;
	}

	public void setPrpQprofitDetails(List<PrpQprofitDetail> prpQprofitDetails) {
		this.prpQprofitDetails = prpQprofitDetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQprofit> getPrpQprofits() {
		return prpQprofits;
	}

	public void setPrpQprofits(List<PrpQprofit> prpQprofits) {
		this.prpQprofits = prpQprofits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainCoeff> getPrpQmainCoeffs() {
		return prpQmainCoeffs;
	}

	public void setPrpQmainCoeffs(List<PrpQmainCoeff> prpQmainCoeffs) {
		this.prpQmainCoeffs = prpQmainCoeffs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainCasualty> getPrpQmainCasualtIies() {
		return prpQmainCasualtIies;
	}

	public void setPrpQmainCasualtIies(
			List<PrpQmainCasualty> prpQmainCasualtIies) {
		this.prpQmainCasualtIies = prpQmainCasualtIies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmainCargo> getPrpQmainCargos() {
		return prpQmainCargos;
	}

	public void setPrpQmainCargos(List<PrpQmainCargo> prpQmainCargos) {
		this.prpQmainCargos = prpQmainCargos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemShip> getPrpQitemShips() {
		return prpQitemShips;
	}

	public void setPrpQitemShips(List<PrpQitemShip> prpQitemShips) {
		this.prpQitemShips = prpQitemShips;
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "prpQmain")
	// public List<PrpQitemKindRateFactor> getPrpQitemKindRateFactors() {
	// return prpQitemKindRateFactors;
	// }
	//
	// public void setPrpQitemKindRateFactors(
	// List<PrpQitemKindRateFactor> prpQitemKindRateFactors) {
	// this.prpQitemKindRateFactors = prpQitemKindRateFactors;
	// }

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemHouse> getPrpQitemHouses() {
		return prpQitemHouses;
	}

	public void setPrpQitemHouses(List<PrpQitemHouse> prpQitemHouses) {
		this.prpQitemHouses = prpQitemHouses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemDevice> getPrpQitemDevices() {
		return prpQitemDevices;
	}

	public void setPrpQitemDevices(List<PrpQitemDevice> prpQitemDevices) {
		this.prpQitemDevices = prpQitemDevices;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemCargo> getPrpQitemCargos() {
		return prpQitemCargos;
	}

	public void setPrpQitemCargos(List<PrpQitemCargo> prpQitemCargos) {
		this.prpQitemCargos = prpQitemCargos;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemCar> getPrpQitemCars() {
		return prpQitemCars;
	}

	public void setPrpQitemCars(List<PrpQitemCar> prpQitemCars) {
		this.prpQitemCars = prpQitemCars;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemAgri> getPrpQitemAgris() {
		return prpQitemAgris;
	}

	public void setPrpQitemAgris(List<PrpQitemAgri> prpQitemAgris) {
		this.prpQitemAgris = prpQitemAgris;
	}

	// @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,
	// mappedBy="prpTmain")
	// public List<PrpTinsuredExt> getPrpTinsuredExts() {
	// return prpTinsuredExts;
	// }
	//
	//
	// public void setPrpTinsuredExts(List<PrpTinsuredExt> prpTinsuredExts) {
	// this.prpTinsuredExts = prpTinsuredExts;
	// }

	// @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,
	// mappedBy="prpTmain")
	// public List<PrpTinsuredArtif> getPrpTinsuredArtifs() {
	// return prpTinsuredArtifs;
	// }
	//
	//
	// public void setPrpTinsuredArtifs(List<PrpTinsuredArtif>
	// prpTinsuredArtifs) {
	// this.prpTinsuredArtifs = prpTinsuredArtifs;
	// }

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQgrade> getPrpQgrades() {
		return prpQgrades;
	}

	public void setPrpQgrades(List<PrpQgrade> prpQgrades) {
		this.prpQgrades = prpQgrades;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQexpense> getPrpQexpenses() {
		return prpQexpenses;
	}

	public void setPrpQexpenses(List<PrpQexpense> prpQexpenses) {
		this.prpQexpenses = prpQexpenses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQdeductible> getPrpQdeductibles() {
		return prpQdeductibles;
	}

	public void setPrpQdeductibles(List<PrpQdeductible> prpQdeductibles) {
		this.prpQdeductibles = prpQdeductibles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQcoinsDetail> getPrpQcoinsDetails() {
		return prpQcoinsDetails;
	}

	public void setPrpQcoinsDetails(List<PrpQcoinsDetail> prpQcoinsDetails) {
		this.prpQcoinsDetails = prpQcoinsDetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQcoins> getPrpQcoinses() {
		return prpQcoinses;
	}

	public void setPrpQcoinses(List<PrpQcoins> prpQcoinses) {
		this.prpQcoinses = prpQcoinses;
	}


	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "prpQmain")
	// public List<PrpQcharge> getPrpQcharges() {
	// return prpQcharges;
	// }
	//
	// public void setPrpQcharges(List<PrpQcharge> prpQcharges) {
	// this.prpQcharges = prpQcharges;
	// }

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQcarshipTaxPreDetail> getPrpQcarshipTaxPreDetails() {
		return prpQcarshipTaxPreDetails;
	}

	public void setPrpQcarshipTaxPreDetails(
			List<PrpQcarshipTaxPreDetail> prpQcarshipTaxPreDetails) {
		this.prpQcarshipTaxPreDetails = prpQcarshipTaxPreDetails;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQcarShipTax> getPrpQcarShipTaxs() {
		return prpQcarShipTaxs;
	}

	public void setPrpQcarShipTaxs(List<PrpQcarShipTax> prpQcarShipTaxs) {
		this.prpQcarShipTaxs = prpQcarShipTaxs;
	}

	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =
	// "prpQmain")
	// public List<PrpQcarShipTax3101Bak> getPrpQcarShipTax3101Baks() {
	// return prpQcarShipTax3101Baks;
	// }
	//
	// public void setPrpQcarShipTax3101Baks(
	// List<PrpQcarShipTax3101Bak> prpQcarShipTax3101Baks) {
	// this.prpQcarShipTax3101Baks = prpQcarShipTax3101Baks;
	// }

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQcarDevice> getPrpQcarDevices() {
		return prpQcarDevices;
	}

	public void setPrpQcarDevices(List<PrpQcarDevice> prpQcarDevices) {
		this.prpQcarDevices = prpQcarDevices;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQbIPolicy> getPrpQbIPolicies() {
		return prpQbIPolicies;
	}

	public void setPrpQbIPolicies(List<PrpQbIPolicy> prpQbIPolicies) {
		this.prpQbIPolicies = prpQbIPolicies;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQbiClaim> getPrpQbiClaims() {
		return prpQbiClaims;
	}

	public void setPrpQbiClaims(List<PrpQbiClaim> prpQbiClaims) {
		this.prpQbiClaims = prpQbiClaims;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQaviation> getPrpQaviations() {
		return prpQaviations;
	}

	public void setPrpQaviations(List<PrpQaviation> prpQaviations) {
		this.prpQaviations = prpQaviations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQitemCarExt> getPrpQitemCarExts() {
		return prpQitemCarExts;
	}

	public void setPrpQitemCarExts(List<PrpQitemCarExt> prpQitemCarExts) {
		this.prpQitemCarExts = prpQitemCarExts;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQmortgagee> getPrpQmortgagees() {
        return prpQmortgagees;
    }

    public void setPrpQmortgagees(List<PrpQmortgagee> prpQmortgagees) {
        this.prpQmortgagees = prpQmortgagees;
    }

//	/**
//	 * 属性更重要类型的getter方法
//	 */
//	@Column(name = "PRIORTYPE")
//	public String getPriorType() {
//		return priorType;
//	}

//	/**
//	 * 属性更重要类型的setter方法
//	 */
//	public void setPriorType(String priorType) {
//		this.priorType = priorType;
//	}

	
    

    @Column(name = "handler2Code")
    public String getHandler2Code() {
        return handler2Code;
    }

    public void setHandler2Code(String handler2Code) {
        this.handler2Code = handler2Code;
    }

    @Column(name = "handler2Name")
    public String getHandler2Name() {
        return handler2Name;
    }

    public void setHandler2Name(String handler2Name) {
        this.handler2Name = handler2Name;
    }

    @Column(name = "handler2IDType")
    public String getHandler2IDType() {
        return handler2IDType;
    }

    public void setHandler2IDType(String handler2idType) {
        handler2IDType = handler2idType;
    }

    @Column(name = "handler2ID")
    public String getHandler2ID() {
        return handler2ID;
    }

    public void setHandler2ID(String handler2id) {
        handler2ID = handler2id;
    }

    @Column(name = "handler2Mobile")
    public String getHandler2Mobile() {
        return handler2Mobile;
    }

    public void setHandler2Mobile(String handler2Mobile) {
        this.handler2Mobile = handler2Mobile;
    }

    @Column(name = "handler2Post")
    public String getHandler2Post() {
        return handler2Post;
    }

    public void setHandler2Post(String handler2Post) {
        this.handler2Post = handler2Post;
    }

    @Column(name = "handler2Address")
    public String getHandler2Address() {
        return handler2Address;
    }

    public void setHandler2Address(String handler2Address) {
        this.handler2Address = handler2Address;
    }  
    
    @Column(name="RatePeriodType")
    public String getRatePeriodType() {
		return ratePeriodType;
	}


	public void setRatePeriodType(String ratePeriodType) {
		this.ratePeriodType = ratePeriodType;
	}


	@Column(name="RatePeriod")
	public String getRatePeriod() {
		return ratePeriod;
	}


	public void setRatePeriod(String ratePeriod) {
		this.ratePeriod = ratePeriod;
	}
	
	@Column(name="RatePeriodOld")
	public String getRatePeriodOld() {
		return ratePeriodOld;
	}


	public void setRatePeriodOld(String ratePeriodOld) {
		this.ratePeriodOld = ratePeriodOld;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RateStartDate")
	public Date getRateStartDate() {
		return rateStartDate;
	}


	public void setRateStartDate(Date rateStartDate) {
		this.rateStartDate = rateStartDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RateEndDate")
	public Date getRateEndDate() {
		return rateEndDate;
	}


	public void setRateEndDate(Date rateEndDate) {
		this.rateEndDate = rateEndDate;
	}


	@Column(name="GovPurchaseFlag")
	public String getGovPurchaseFlag() {
		return govPurchaseFlag;
	}


	public void setGovPurchaseFlag(String govPurchaseFlag) {
		this.govPurchaseFlag = govPurchaseFlag;
	}


	@Column(name="FycFlag")
	public String getFycFlag() {
		return fycFlag;
	}


	public void setFycFlag(String fycFlag) {
		this.fycFlag = fycFlag;
	}
	
    @Column(name = "DirectBusiness")
    public String getDirectBusiness() {
		return directBusiness;
	}
	public void setDirectBusiness(String directBusiness) {
		this.directBusiness = directBusiness;
	}

	@Column(name="ExtraComCode")
	public String getExtraComCode() {
		return extraComCode;
	}


	public void setExtraComCode(String extraComCode) {
		this.extraComCode = extraComCode;
	}

	@Column(name="ExtraComName")
	public String getExtraComName() {
		return extraComName;
	}


	public void setExtraComName(String extraComName) {
		this.extraComName = extraComName;
	}

	@Column(name="IntroducerID")
	public String getIntroducerID() {
		return introducerID;
	}


	public void setIntroducerID(String introducerID) {
		this.introducerID = introducerID;
	}


	@Column(name="IntroducerName")
	public String getIntroducerName() {
		return introducerName;
	}


	public void setIntroducerName(String introducerName) {
		this.introducerName = introducerName;
	}


	@Column(name="Agent1Code")
	public String getAgent1Code() {
		return agent1Code;
	}


	public void setAgent1Code(String agent1Code) {
		this.agent1Code = agent1Code;
	}


	@Column(name="Agent1Name")
	public String getAgent1Name() {
		return agent1Name;
	}


	public void setAgent1Name(String agent1Name) {
		this.agent1Name = agent1Name;
	}
    /**
     * 属性编辑标志的getter方法
     */ 
    
    @Column(name="EDITFLAG")

    public String getEditFlag() {
        return this.editFlag;
    }
    /**
     * 属性编辑标志的setter方法
     */
    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }
    
    /**
     * 属性未续保登记原因的getter方法
     */ 
    
    @Column(name="RSNNORENEWAL")

    public String getRsnNorenewal() {
        return this.rsnNorenewal;
    }
    /**
     * 属性未续保登记原因的setter方法
     */
    public void setRsnNorenewal(String rsnNorenewal) {
        this.rsnNorenewal = rsnNorenewal;
    }
    
    /**
     * 属性未续保登记原因代码的getter方法
     */ 
    
    @Column(name="NOTRENEWALREGIST")

    public String getNotRenewalRegist() {
        return this.notRenewalRegist;
    }
    /**
     * 属性未续保登记原因代码的setter方法
     */
    public void setNotRenewalRegist(String notRenewalRegist) {
        this.notRenewalRegist = notRenewalRegist;
    }
    
    @Column(name="TradeVanID")
	public String getTradeVanID() {
		return tradeVanID;
	}

	public void setTradeVanID(String tradeVanID) {
		this.tradeVanID = tradeVanID;
	}

	@Column(name = "handlerIdentifyNumber")
	public String getHandlerIdentifyNumber() {
		return handlerIdentifyNumber;
	}

	public void setHandlerIdentifyNumber(String handlerIdentifyNumber) {
		this.handlerIdentifyNumber = handlerIdentifyNumber;
	}

	@Column(name = "agentName")
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	/**
	 * 判断车龄是否满足规则
	 * @param carAge
	 * @return
	 */
	public boolean isCarAgeLessThan(String carAge){
		List cars= this.getPrpQitemCars();
		boolean isLess=false;
		if(cars.size()>0){
			PrpQitemCar car = (PrpQitemCar) cars.get(0);
			Long years=car.getUseYears();
			isLess=years.compareTo(Long.parseLong(carAge))==-1;
		}
		return isLess;
	}
	/**
	 * @功能：任意险险别等于riskKind，且保额小于amountValue。主险为01,05的验证
	 * @param riskKind
	 * @param amountValue
	 * @return
	 */
	public boolean hasRiskKindAndAmountLessThan(String riskKind,String amountValue){
		List<PrpQitemKind> kinds = this.getPrpQitemKinds();
		boolean exist = false;
		if(kinds.size()>0){
			BigDecimal amount = new BigDecimal(amountValue);
			for(PrpQitemKind kind:kinds){
				String kindcode=kind.getKindCode();
				if(kindcode.equals(riskKind)){
					if(kind.getAmount().compareTo(amount)==-1){
						exist = true;
						break;
					}
				}
			}
		}
		return exist;
	}
	
	/**
	 * @功能：规则验证，强制险保额小于amountValue
	 * @param amountValue
	 * @return
	 */
	public boolean isSumAmountLessThan(String amountValue){
		BigDecimal amount = new BigDecimal(amountValue);
		return this.getSumAmount().compareTo(amount)==-1;
		
	}
	/**
	 * @功能：规则验证时，判断险种是否相同
	 * @param riskCode
	 * @return
	 */
	public boolean hasRiskCode(String riskCode){
		return this.riskCode.equals(riskCode);
	}
	/**
	 * @功能，规则验证，将result设为true，标识为自动审核通过
	 */
	public void autoUndwrPassed(){
		result=true;
	}

	@Column(name = "PROJECTCODE")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Column(name = "batchNO")
	public String getBatchNO() {
		return batchNO;
	}

	public void setBatchNO(String batchNO) {
		this.batchNO = batchNO;
	}

	@Column(name = "asPolicyNo")
	public String getAsPolicyNo() {
		return asPolicyNo;
	}

	public void setAsPolicyNo(String asPolicyNo) {
		this.asPolicyNo = asPolicyNo;
	}
	
	@Column(name="ACHIEVCONVERRATE")
	public String getAchievConverRate() {
		return this.achievConverRate;
	}

	public void setAchievConverRate(String achievConverRate) {
		this.achievConverRate = achievConverRate;
	}
	@Column(name="ASSUMPREMIUM")
	public BigDecimal getAsSumPremium() {
		return asSumPremium;
	}
	public void setAsSumPremium(BigDecimal asSumPremium) {
		this.asSumPremium = asSumPremium;
	}
	@Column(name="VISACODEBI")
	public String getVisaCodeBI() {
		return this.visaCodeBI;
	}

	public void setVisaCodeBI(String visaCodeBI) {
		this.visaCodeBI = visaCodeBI;
	}
	
	
	
	@Column(name = "possessNature")
    public String getPossessNature() {
        return possessNature;
    }

    public void setPossessNature(String possessNature) {
        this.possessNature = possessNature;
    }

    @Column(name = "possessNatureCode")
    public String getPossessNatureCode() {
        return possessNatureCode;
    }

    public void setPossessNatureCode(String possessNatureCode) {
        this.possessNatureCode = possessNatureCode;
    }
    
    /** 属性套装商品代号的getter方法 */
	@Column(name="SERIESCODE")
	public String getSeriesCode() {
		return seriesCode;
	}
	/** 属性套装商品代号的setter方法 */
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	
	/** 属性套装商品名称的getter方法 */
	@Column(name="SERIESNAME")
	public String getSeriesName() {
		return seriesName;
	}
	/** 属性套装商品名称的setter方法 */
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	@Column(name="REINSMARK")
	public String getReinsMark() {
		return reinsMark;
	}

	public void setReinsMark(String reinsMark) {
		this.reinsMark = reinsMark;
	}

	@Column(name="CREATOR")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name="RENEWALTIMES")
	public String getRenewalTimes() {
		return renewalTimes;
	}

	public void setRenewalTimes(String renewalTimes) {
		this.renewalTimes = renewalTimes;
	}

	@Column(name="COMMONBUSINESS")
	public String getCommonBusiness() {
		return commonBusiness;
	}

	public void setCommonBusiness(String commonBusiness) {
		this.commonBusiness = commonBusiness;
	}

	@Column(name="SPECIALFLAG")
	public String getSpecialFlag() {
		return specialFlag;
	}

	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}

	@Column(name="DISCOUNTRATE")
	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name="PRINTDATE")
	public Date getPrintDate() {
		return printDate;
	}

	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}

	@Column(name="MAILDATE")
	public Date getMailDate() {
		return mailDate;
	}

	public void setMailDate(Date mailDate) {
		this.mailDate = mailDate;
	}
	@Column(name="RATIONCODE")
	public String getRationCode() {
		return rationCode;
	}

	public void setRationCode(String rationCode) {
		this.rationCode = rationCode;
	}
	@Column(name="FEEREMARK")
	public String getFeeRemark() {
		return feeRemark;
	}

	public void setFeeRemark(String feeRemark) {
		this.feeRemark = feeRemark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQexceptItem> getPrpQexceptItems() {
		return prpQexceptItems;
	}

	public void setPrpQexceptItems(List<PrpQexceptItem> prpQexceptItems) {
		this.prpQexceptItems = prpQexceptItems;
	}
	@Column(name="zhizhe")
	public String getZhizhe() {
		return zhizhe;
	}

	public void setZhizhe(String zhizhe) {
		this.zhizhe = zhizhe;
	}
	@Column(name="DELIVERYDATE")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@Column(name="POLICYRECEPTDATE")
	public Date getPolicyReceptDate() {
		return policyReceptDate;
	}

	public void setPolicyReceptDate(Date policyReceptDate) {
		this.policyReceptDate = policyReceptDate;
	}
	@Column(name="PUBLICFLAG")
	public String getPublicFlag() {
		return publicFlag;
	}

	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}
	@Column(name="TESTCARSTARTDATE")
	public Date getTestCarStartDate() {
		return testCarStartDate;
	}

	public void setTestCarStartDate(Date testCarStartDate) {
		this.testCarStartDate = testCarStartDate;
	}
	@Column(name="TESTCARENDDATE")
	public Date getTestCarEndDate() {
		return testCarEndDate;
	}

	public void setTestCarEndDate(Date testCarEndDate) {
		this.testCarEndDate = testCarEndDate;
	}
	@Column(name="MAINTAINSTARTDATE")
	public Date getMaintainStartDate() {
		return maintainStartDate;
	}

	public void setMaintainStartDate(Date maintainStartDate) {
		this.maintainStartDate = maintainStartDate;
	}
	@Column(name="MAINTAINENDDATE")
	public Date getMaintainEndDate() {
		return maintainEndDate;
	}

	public void setMaintainEndDate(Date maintainEndDate) {
		this.maintainEndDate = maintainEndDate;
	}
	@Column(name="MAINTAINDAYS")
	public String getMaintainDays() {
		return maintainDays;
	}

	public void setMaintainDays(String maintainDays) {
		this.maintainDays = maintainDays;
	}
	
	@Column(name="SUMDAMAGEAMOUNT")
	public BigDecimal getSumDamageAmount() {
		return sumDamageAmount;
	}

	public void setSumDamageAmount(BigDecimal sumDamageAmount) {
		this.sumDamageAmount = sumDamageAmount;
	}
	@Column(name="SUMEXTERNALAMOUNT")
	public BigDecimal getSumExternalAmount() {
		return sumExternalAmount;
	}

	public void setSumExternalAmount(BigDecimal sumExternalAmount) {
		this.sumExternalAmount = sumExternalAmount;
	}
	@Column(name="SUMINCREASEDAMOUNT")
	public BigDecimal getSumIncreasedAmount() {
		return sumIncreasedAmount;
	}

	public void setSumIncreasedAmount(BigDecimal sumIncreasedAmount) {
		this.sumIncreasedAmount = sumIncreasedAmount;
	}
	@Column(name="RENEWINSURANCEFLAG")
	public String getRenewInsuranceFlag() {
		return renewInsuranceFlag;
	}

	public void setRenewInsuranceFlag(String renewInsuranceFlag) {
		this.renewInsuranceFlag = renewInsuranceFlag;
	}
	
	// 水险AV新增类添加  by yjm
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQplaneDevice> getPrpQplaneDevices() {
		return prpQplaneDevices;
	}

	public void setPrpQplaneDevices(List<PrpQplaneDevice> prpQplaneDevices) {
		this.prpQplaneDevices = prpQplaneDevices;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQplane> getPrpQplanes() {
		return prpQplanes;
	}

	public void setPrpQplanes(List<PrpQplane> prpQplanes) {
		this.prpQplanes = prpQplanes;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQplaneDriver> getPrpQplaneDrivers() {
		return prpQplaneDrivers;
	}

	public void setPrpQplaneDrivers(List<PrpQplaneDriver> prpQplaneDrivers) {
		this.prpQplaneDrivers = prpQplaneDrivers;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQplaneSafe> getPrpQplaneSafes() {
		return prpQplaneSafes;
	}

	public void setPrpQplaneSafes(List<PrpQplaneSafe> prpQplaneSafes) {
		this.prpQplaneSafes = prpQplaneSafes;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQshipSafe> getPrpQshipSafes() {
		return prpQshipSafes;
	}

	public void setPrpQshipSafes(List<PrpQshipSafe> prpQshipSafes) {
		this.prpQshipSafes = prpQshipSafes;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQairline> getPrpQairlines() {
		return prpQairlines;
	}
	public void setPrpQairlines(List<PrpQairline> prpQairlines) {
		this.prpQairlines = prpQairlines;
	}
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="prpQmain")
	public List<PrpQticket> getPrpQtickets() {
		return prpQtickets;
	}
	public void setPrpQtickets(List<PrpQticket> prpQtickets) {
		this.prpQtickets = prpQtickets;
	}
	/**
     * smallAmountBI.
     *
     * @return  the smallAmountBI
     * @since   JDK 1.7
     */
    @Column(name="SMALLAMOUNTBI")
    public BigDecimal getSmallAmountBI() {
        return smallAmountBI;
    }

    /**
     * smallAmountBI.
     *
     * @param   smallAmountBI    the smallAmountBI to set
     * @since   JDK 1.7
     */
    public void setSmallAmountBI(BigDecimal smallAmountBI) {
        this.smallAmountBI = smallAmountBI;
    }
    
    // 水险CF新增字段 by yjm
    @Column(name="POLICYAPPLYAREA")
	public String getPolicyApplyArea() {
		return policyApplyArea;
	}

	public void setPolicyApplyArea(String policyApplyArea) {
		this.policyApplyArea = policyApplyArea;
	}

	@Column(name="POLICYAPPLYAGREEMENT")
	public String getPolicyApplyAgreement() {
		return policyApplyAgreement;
	}

	public void setPolicyApplyAgreement(String policyApplyAgreement) {
		this.policyApplyAgreement = policyApplyAgreement;
	}

	@Column(name="UNEARNEDPREMIUM")
	public BigDecimal getUnearnedPremium() {
		return unearnedPremium;
	}

	public void setUnearnedPremium(BigDecimal unearnedPremium) {
		this.unearnedPremium = unearnedPremium;
	}
	
	@Column(name="DETAILREMARK")
	public String getDetailRemark() {
		return detailRemark;
	}

	public void setDetailRemark(String detailRemark) {
		this.detailRemark = detailRemark;
	}

	@Column(name="DESCRIPTIONS")
	public String getDescriptions() {
		return descriptions;
	}
	
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	@Column(name="REINSMARKDESC")
	public String getReinsMarkDesc() {
		return reinsMarkDesc;
	}

	public void setReinsMarkDesc(String reinsMarkDesc) {
		this.reinsMarkDesc = reinsMarkDesc;
	}
	@Column(name="temporarilyReceipt")
	public String getTemporarilyReceipt() {
		return temporarilyReceipt;
	}
	public void setTemporarilyReceipt(String temporarilyReceipt) {
		this.temporarilyReceipt = temporarilyReceipt;
	}
	
	@Column(name = "AGEKINDTYPE")
    public String getAgeKindType() {
		return ageKindType;
	}
	public void setAgeKindType(String ageKindType) {
		this.ageKindType = ageKindType;
	}
	
	@Column(name = "REMARKCOL1")
	public String getRemarkCol1() {
		return remarkCol1;
	}
	public void setRemarkCol1(String remarkCol1) {
		this.remarkCol1 = remarkCol1;
	}
	
	@Column(name = "REMARKCOL2")
	public String getRemarkCol2() {
		return remarkCol2;
	}

	public void setRemarkCol2(String remarkCol2) {
		this.remarkCol2 = remarkCol2;
	}
	@Column(name = "REMARKCOL3")
	public String getRemarkCol3() {
		return remarkCol3;
	}
	
	public void setRemarkCol3(String remarkCol3) {
		this.remarkCol3 = remarkCol3;
	}
	@Column(name = "REMARKCOL4")
	public String getRemarkCol4() {
		return remarkCol4;
	}

	public void setRemarkCol4(String remarkCol4) {
		this.remarkCol4 = remarkCol4;
	}
	@Column(name = "REMARKCOL5")
	public String getRemarkCol5() {
		return remarkCol5;
	}

	public void setRemarkCol5(String remarkCol5) {
		this.remarkCol5 = remarkCol5;
	}
	@Column(name = "REMARKCOL6")
	public String getRemarkCol6() {
		return remarkCol6;
	}

	public void setRemarkCol6(String remarkCol6) {
		this.remarkCol6 = remarkCol6;
	}
	@Column(name = "REMARKCOL7")
	public String getRemarkCol7() {
		return remarkCol7;
	}

	public void setRemarkCol7(String remarkCol7) {
		this.remarkCol7 = remarkCol7;
	}
	@Column(name = "REMARKCOL8")
	public String getRemarkCol8() {
		return remarkCol8;
	}

	public void setRemarkCol8(String remarkCol8) {
		this.remarkCol8 = remarkCol8;
	}
	@Column(name = "REMARKCOL9")
	public String getRemarkCol9() {
		return remarkCol9;
	}

	public void setRemarkCol9(String remarkCol9) {
		this.remarkCol9 = remarkCol9;
	}
	@Column(name = "REMARKCOL10")
	public String getRemarkCol10() {
		return remarkCol10;
	}

	public void setRemarkCol10(String remarkCol10) {
		this.remarkCol10 = remarkCol10;
	}

	@Column(name = "PRINTTIMES")
	public BigDecimal getPrintTimes() {
		return printTimes;
	}
	public void setPrintTimes(BigDecimal printTimes) {
		this.printTimes = printTimes;
	}
	@Column(name = "BIGPOLICYNO")
	public String getBigPolicyNo() {
		return bigPolicyNo;
	}
	public void setBigPolicyNo(String bigPolicyNo) {
		this.bigPolicyNo = bigPolicyNo;
	}
	@Column(name = "SENDPAYMENTFLAG")
	public String getSendPaymentFlag() {
		return sendPaymentFlag;
	}

	public void setSendPaymentFlag(String sendPaymentFlag) {
		this.sendPaymentFlag = sendPaymentFlag;
	}
	
	@Column(name = "COMMODITYRISKGRADE")
	public String getCommodityRiskGrade() {
		return commodityRiskGrade;
	}
	public void setCommodityRiskGrade(String commodityRiskGrade) {
		this.commodityRiskGrade = commodityRiskGrade;
	}

	@Column(name = "SUPERPAY")
	public String getSuperpay() {
		return superpay;
	}
	public void setSuperpay(String superpay) {
		this.superpay = superpay;
	}
	@Column(name = "FLOWFLAG")
	public String getFlowFlag() {
		return flowFlag;
	}
	public void setFlowFlag(String flowFlag) {
		this.flowFlag = flowFlag;
	}
	
	@Column(name = "FLOWRATE")
	public String getFlowRate() {
		return flowRate;
	}
	public void setFlowRate(String flowRate) {
		this.flowRate = flowRate;
	} 
	@Column(name = "REFUSELIMITEINSURANCE")
	public String getRefuseLimiteInsurance() {
		return refuseLimiteInsurance;
	}
	public void setRefuseLimiteInsurance(String refuseLimiteInsurance) {
		this.refuseLimiteInsurance = refuseLimiteInsurance;
	}
	@Column(name = "LISTDETECTION")
	public String getListDetection() {
		return listDetection;
	}
	public void setListDetection(String listDetection) {
		this.listDetection = listDetection;
	}
	@Column(name = "RISKRATING")
	public String getRiskRating() {
		return riskRating;
	}
	public void setRiskRating(String riskRating) {
		this.riskRating = riskRating;
	}
	@Column(name = "WORKSTATUS")
	public String getWorkStatus() {
		return workStatus;
	}
	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}
	@Column(name = "CALLAMLDATE")
	public Date getCallAmlDate() {
		return callAmlDate;
	}
	public void setCallAmlDate(Date callAmlDate) {
		this.callAmlDate = callAmlDate;
	}
	@Column(name = "EXCEPTIONNO")
	public String getExceptionNo() {
		return exceptionNo;
	}
	public void setExceptionNo(String exceptionNo) {
		this.exceptionNo = exceptionNo;
	}
	
	// mantis： FIR0145，處理人員：DP0706，需求單編號：FIR0145.中信新件流程改造-新增受理編號及調整撤單功能 START
	@Column(name = "ORDERSEQ")
	public String getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}
	// mantis： FIR0145，處理人員：DP0706，需求單編號：FIR0145.中信新件流程改造-新增受理編號及調整撤單功能END
	
	
	// mantis： FIR0145，處理人員：DP0706，需求單編號：FIR0145.中信新件流程改造-新增送件類別 START
	@Column(name = "SENDTYPE")
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	// mantis： FIR0145，處理人員：DP0706，需求單編號：FIR0145.中信新件流程改造-新增送件類別END
	
	// mantis： FIR0166，處理人員：DP0706，需求單編號：FIR0166.新增條款交付方式START
	@Column(name = "CLAUSE_SENDTYPE")
	public String getClauseSendType() {
		return clauseSendType;
	}
	public void setClauseSendType(String clauseSendType) {
		this.clauseSendType = clauseSendType;
	}
	// mantis： FIR0166，處理人員：DP0706，需求單編號：FIR0166.新增條款交付方式END
	
	//mantis： LIA0091，處理人員：DP0706，需求單編號：LIA0091.PE普通批改 START
    private List<PrpQpe> PrpQpes = new ArrayList<PrpQpe>(0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "prpQmain")
	public List<PrpQpe> getPrpQpes() {
		return PrpQpes;
	}
	public void setPrpQpes(List<PrpQpe> PrpQpes) {
		this.PrpQpes = PrpQpes;
	}
    //mantis： LIA0091，處理人員：DP0706，需求單編號：LIA0091.PE普通批改 END
	
	/*
	mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266--- start
	保發輔助平台,擴增欄位--核心變更需求
	*/
	private String notifyOrNot;//已確認無法提供行動電話與電子信箱

	@Column(name="NOTIFYORNOT")
	public String getNotifyOrNot() {
		return notifyOrNot;
	}

	public void setNotifyOrNot(String notifyOrNot) {
		this.notifyOrNot = notifyOrNot;
	}
	/* mantis： CAR0266，處理人員：Sam，需求單編號：CAR0266 --- end */
	
	//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 START
	@Column(name="NORMASTATUS")
	public String getNormastatus() {
		return normastatus;
	}

	public void setNormastatus(String normastatus) {
		this.normastatus = normastatus;
	}
	//mantis： CAR0369，處理人員：BI086，需求單編號：CAR0369:核心車險地址正規化作業 END

}
