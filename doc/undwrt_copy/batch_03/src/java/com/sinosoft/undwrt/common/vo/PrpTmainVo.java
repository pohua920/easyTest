package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;
import com.sinosoft.utility.string.ChgData;
import com.sinosoft.utility.string.Str;

/**
 * ★★★★★警告：本文件不允许手工修改！！！请使用JToolpad生成！<br>
 * 这是PRPTMAIN-投保单基本信息表的数据传输对象基类<br>
 * 创建于 JToolpad(1.4.0) Vendor:zhouxianli1978@msn.com
 */
/*******************************************************************************
 * Description：定义Policy的结构 Author : 财项目组 
 * ： Name       Date          Reason/Contents
 *   dengpeng   20080222      增加银邮业务的二级查询
 *   LanNing    20080330      增加银行查询
 *   xiongguojun 20080923     增加销售渠道、大项目标志和签单币别与人民币的兑换率
 *   xiongguojun 20091021     增加团队类型
 ******************************************************************************/
public class PrpTmainVo implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = PrpTmainVo.class.getName().hashCode();
    
    /** 属性投保单号码. */
    private String proposalNo = "";
    
    /** 属性保单号码. */
    private String policyNo = "";
    
    /** 属性险类代码. */
    private String classCode = "";
    
    /** 属性险种代码. */
    private String riskCode = "";
    
    /** 属性合同号(供合保单使用). */
    private String contractNo = "";
    
    /** 属性保单种类 ▲ --** (1普通/2定额/3暂保单/4台帐补录). */
    private String policySort = "";
    
    /** 属性投保单印刷号. */
    private String printNo = "";
    
    /** 属性业务来源（直接/代理）. */
    private String businessNature = "";
    
    /** 属性语种标志（C/E/…）. */
    private String language = "";
    
    /** 属性保单类型（个人/集体）. */
    private String policyType = "";
    
    /** 属性投保人代码. */
    private String appliCode = "";
    
    /** 属性投保人名称. */
    private String appliName = "";
    
    /** 属性投保人地址. */
    private String appliAddress = "";
    
    /** 属性被保险人代码. */
    private String insuredCode = "";
    
    /** 属性被保险人名称. */
    private String insuredName = "";
    
    /** 属性被保险人地址. */
    private String insuredAddress = "";
    
    /** 属性签单日期/投保日期. */
    private DateTime operateDate = new DateTime();
    
    /** 属性起保日期（启运日期）. */
    private DateTime startDate = new DateTime();
    
    /** 属性起保小时. */
    private long startHour = 0L;
    
    /** 属性终保日期. */
    private DateTime endDate = new DateTime();
    
    /** 属性终保小时. */
    private long endHour = 0L;
    
    /** 属性净费率. */
    private double pureRate = 0D;
    
    /** 属性手续费比例. */
    private double disRate = 0D;
    
    /** 属性总折扣率. */
    private double discount = 0D;
    
    /** 属性币别代码. */
    private String currency = "";
    
    /** 属性总保险价值. */
    private double sumValue = 0D;
    
    /** 属性总保险金额(折算为人民币总保额). */
    private double sumAmount = 0D;
    
    /** 属性总折扣金额. */
    private double sumDiscount = 0D;
    
    /** 属性总保险费(折算为人民币总保费). */
    private double sumPremium = 0D;
    
    /** 属性总附加险保费. */
    private double sumSubPrem = 0D;
    
    /** 属性被保险总数量/人数/户数压力容器总数. */
    private long sumQuantity = 0L;
    
    /** 属性司法管辖代码. */
    private String judicalCode = "";
    
    /** 属性司法管辖. */
    private String judicalScope = "";
    
    /** 属性交费方式1：现金 2：银行转账 9：其它. */
    private String autoTransRenewFlag = "";
    
    /** 属性争议解决方式--** 1 诉讼；2 仲裁. */
    private String argueSolution = "";
    
    /** 属性仲裁委员会名称. */
    private String arbitBoardName = "";
    
    /** 属性约定分期交费次数. */
    private long payTimes = 0L;
    
    /** 属性批改次数. */
    private long endorseTimes = 0L;
    
    /** 属性理赔次数. */
    private long claimTimes = 0L;
    
    /** 属性出单机构. */
    private String makeCom = "";
    
    /** 属性签单地点. */
    private String operateSite = "";
    
    /** 属性业务归属机构代码. */
    private String comCode = "";
    
    /** 属性经办人代码. */
    private String handlerCode = "";
    
    /** 属性归属业务员代码. */
    private String handler1Code = "";
    
    /** 属性复核人代码. */
    private String approverCode = "";
    
    /** 属性最终核保人代码. */
    private String underWriteCode = "";
    
    /** 属性最终核保人名称. */
    private String underWriteName = "";
    
    /** 属性操作员代码/第一次录入人员代码. */
    private String operatorCode = "";
    
    /** 属性计算机输单日期. */
    private DateTime inputDate = new DateTime();
    
    /** 属性计算机输单小时. */
    private long inputHour = 0L;
    
    /** 属性核保完成日期. */
    private DateTime underWriteEndDate = new DateTime();
    
    /** 属性保单统计年月. */
    private DateTime statisticsYM = new DateTime();
    
    /** 属性代理人代码. */
    private String agentCode = "";
    
    /** 属性共保标志--** (0非共保/1主共保/2共保). */
    private String coinsFlag = "";
    
    /** 属性商业分保标志(0无需分保/1需分保/2已分保). */
    private String reinsFlag = "";
    
    /** 属性统保标志(0/1统保) --**0顺位,1均分,2法定,3其它. */
    private String allinsFlag = "";
    
    /** 属性核保标志 --** (0初始值/1通过/2不通过/3 无需核保 9待核保). */
    private String underWriteFlag = "";
    
    /**
	 * 属性其它标志字段： --** [1] (0/1)新/续保标志 --** [2] (0/1)被续保标志(1:被续保) --** [3] 退保标志
	 * --** 1:全单退保 --** 2:满期退保 --** 3:部分退保 --** [4] 注销(1:已注销) --** [5]
	 * 遗失标志（1：已遗失） --** [6] 有效标志（1：终止合同, 2 中止.
	 */
    private String othFlag = "";
    
    /** 属性状态字段. */
    private String flag = "";
    
    /** 属性超出部分手续费比例. */
    private double disRate1 = 0D;
    
    /** 属性业务类型. */
    private String businessFlag = "";
    
    /** 属性缴费方式. */
    private String payMode = "";
    
    /** 属性最后一次修改人员代码. */
    private String updaterCode = "";
    
    /** 属性最后一次修改日期. */
    private DateTime updateDate = new DateTime();
    
    /** 属性最后一次修改时间. */
    private String updateHour = "";
    
    /** 属性签单日期. */
    private DateTime signDate = new DateTime();
    
    /** 属性是否股东业务标识. */
    private String shareHolderFlag = "";
    
    /** 属性协议号. */
    private String agreementNo = "";
    
    /** 属性询价单号. */
    private String inquiryNo = "";
    
    /** 屬性The sinosoft visa code. */
    private String visaCode = "";
    
    /** 屬性The sinosoft manual type. */
    private String manualType = "";
    
    /** 属性见费出单标志. */
    private String jFeeFlag   = "";
    
    /** 属性核保预确认时间. */
    private DateTime preCheckDate = new DateTime();
    
    /** 属性名称. */
    private String handlerName = "";
    
    /** 属性业务员名称. */
    private String handler1Name = "";
    
    /** 属性实收确认人代码. */
    private String payRefCode  = "";
    
    /** 属性实收确认人名称. */
    private String payRefName  = "";
    
    /** 属性实收确认时间. */
    private DateTime PayRefTime = new DateTime();
    
    /** 属性保单打印时间. */
    private DateTime PrintTime = new DateTime();
    
    /** 属性涉农标志. */
    private String AgriType = "";
    
    /** 属性保单核保通过级别 add by yanglibo20081128. */
    private String proposalLevel = "";

    //add by dengpeng 20080220 增加银邮渠道的二级查询 begin;
    /** 属性业务子来源. */
    private String SubBusinessNature = "";
    //add by dengpeng 20080220 增加银邮渠道的二级查询 end;
    
    //added by xiongguojun 20080923 增加销售渠道、大项目标志和签单币别与人民币的兑换率 begin
    /** 属性销售渠道字段. */
    private String channelType = "";
    
    /** 属性大项目标志. */
    private String projectsFlag = "";
    
    /** 属性签单币别与人民币的兑换率. */
    private double exchangeRate = 0D;
    //added by xiongguojun 20080923 end
    
    //added by LanNing begin 20080330 增加银行查询
    /** 屬性The sinosoft Bank code. */
    private String BankCode = "";
    
    //added by xiongguojun 20091021 增加团队类型
    /** 屬性The sinosoft group type. */
    private String groupType = "";
    
    /** 屬性The sinosoft start stages. */
    private String startStages = "";
    
    /**
	 * 獲取屬性the sinosoft bank code.
	 * 
	 * @return 屬性the sinosoft bank code的值
	 */
    public String getBankCode() {
		return BankCode;
	}

	/**
	 * 設置屬性the sinosoft bank code.
	 * 
	 * @param bankCode
	 *            待設置的the sinosoft bank code的值
	 */
	public void setBankCode(String bankCode) {
		BankCode = bankCode;
	}
	//added by LanNing end 20080330 增加银行查询
	
    //庄元 2009-07-13 交强险即时生效 开始
    /** 屬性The sinosoft new start date. */
	private String newStartDate = "";
    
    /** 屬性The sinosoft new end date. */
    private String newEndDate = "";
    
    /** 屬性The sinosoft effective immediately flag. */
    private String effectiveImmediatelyFlag = "";
    
    /**
	 * 獲取屬性the sinosoft effective immediately flag.
	 * 
	 * @return 屬性the sinosoft effective immediately flag的值
	 */
    public String getEffectiveImmediatelyFlag()
    {
      return effectiveImmediatelyFlag;
    }
    
    /**
	 * 設置屬性the sinosoft effective immediately flag.
	 * 
	 * @param effectiveImmediatelyFlag
	 *            待設置的the sinosoft effective immediately flag的值
	 */
    public void setEffectiveImmediatelyFlag(String effectiveImmediatelyFlag)
    {
      this.effectiveImmediatelyFlag = Str.rightTrim(effectiveImmediatelyFlag);
    }
    
    /**
	 * 獲取屬性the sinosoft new start date.
	 * 
	 * @return 屬性the sinosoft new start date的值
	 */
    public String getNewStartDate()
    {
      return newStartDate;
    }
    
    /**
	 * 設置屬性the sinosoft new start date.
	 * 
	 * @param newStartDate
	 *            待設置的the sinosoft new start date的值
	 */
    public void setNewStartDate(String newStartDate)
    {
      newStartDate = ChgData.nullToString(newStartDate);
      if(newStartDate.indexOf(':')!=newStartDate.lastIndexOf(':'))//用于判断是2008-3-20 11:12:42还是2008-3-20 11:12
      {
        this.newStartDate = newStartDate.substring(0,newStartDate.lastIndexOf(':'));;//生成的时间格式为2008-01-01 01:01:01.0
      }
      else
      {
        this.newStartDate = newStartDate;
      }
    }
    
    /**
	 * 獲取屬性the sinosoft new end date.
	 * 
	 * @return 屬性the sinosoft new end date的值
	 */
    public String getNewEndDate()
    {
      return newEndDate;
    }
    
    /**
	 * 設置屬性the sinosoft new end date.
	 * 
	 * @param newEndDate
	 *            待設置的the sinosoft new end date的值
	 */
    public void setNewEndDate(String newEndDate)
    {
      newEndDate = ChgData.nullToString(newEndDate);
      if(newEndDate.indexOf(':')!=newEndDate.lastIndexOf(':'))
      {
        this.newEndDate = newEndDate.substring(0,newEndDate.lastIndexOf(':'));;//生成的时间格式为2008-01-01 01:01:01.0
      }
      else
      {
        this.newEndDate = newEndDate;
      }
    }
    //庄元 2009-07-13 交强险即时生效 结束
    
    /**
	 * 默认构造方法,构造一个默认的PrpTmainDtoBase对象.
	 */
    public PrpTmainVo(){
    }

    /**
	 * 设置属性投保单号码.
	 * 
	 * @param proposalNo
	 *            待设置的属性投保单号码的值
	 */
    public void setProposalNo(String proposalNo){
        this.proposalNo = StringUtils.rightTrim(proposalNo);
    }

    /**
	 * 获取属性投保单号码.
	 * 
	 * @return 属性投保单号码的值
	 */
    public String getProposalNo(){
        return proposalNo;
    }
    
    /**
	 * 设置属性保单号码.
	 * 
	 * @param policyNo
	 *            待设置的属性保单号码的值
	 */
    public void setPolicyNo(String policyNo){
        this.policyNo = StringUtils.rightTrim(policyNo);
    }

    /**
	 * 获取属性保单号码.
	 * 
	 * @return 属性保单号码的值
	 */
    public String getPolicyNo(){
        return policyNo;
    }

    /**
	 * 设置属性险类代码.
	 * 
	 * @param classCode
	 *            待设置的属性险类代码的值
	 */
    public void setClassCode(String classCode){
        this.classCode = StringUtils.rightTrim(classCode);
    }

    /**
	 * 获取属性险类代码.
	 * 
	 * @return 属性险类代码的值
	 */
    public String getClassCode(){
        return classCode;
    }

    /**
	 * 设置属性险种代码.
	 * 
	 * @param riskCode
	 *            待设置的属性险种代码的值
	 */
    public void setRiskCode(String riskCode){
        this.riskCode = StringUtils.rightTrim(riskCode);
    }

    /**
	 * 获取属性险种代码.
	 * 
	 * @return 属性险种代码的值
	 */
    public String getRiskCode(){
        return riskCode;
    }

    /**
	 * 设置属性合同号(供合保单使用).
	 * 
	 * @param contractNo
	 *            待设置的属性合同号(供合保单使用)的值
	 */
    public void setContractNo(String contractNo){
        this.contractNo = StringUtils.rightTrim(contractNo);
    }

    /**
	 * 获取属性合同号(供合保单使用).
	 * 
	 * @return 属性合同号(供合保单使用)的值
	 */
    public String getContractNo(){
        return contractNo;
    }

    /**
	 * 设置属性保单种类 ▲ --** (1普通/2定额/3暂保单/4台帐补录).
	 * 
	 * @param policySort
	 *            待设置的属性保单种类 ▲ --** (1普通/2定额/3暂保单/4台帐补录)的值
	 */
    public void setPolicySort(String policySort){
        this.policySort = StringUtils.rightTrim(policySort);
    }

    /**
	 * 获取属性保单种类 ▲ --** (1普通/2定额/3暂保单/4台帐补录).
	 * 
	 * @return 属性保单种类 ▲ --** (1普通/2定额/3暂保单/4台帐补录)的值
	 */
    public String getPolicySort(){
        return policySort;
    }

    /**
	 * 设置属性投保单印刷号.
	 * 
	 * @param printNo
	 *            待设置的属性投保单印刷号的值
	 */
    public void setPrintNo(String printNo){
        this.printNo = StringUtils.rightTrim(printNo);
    }

    /**
	 * 获取属性投保单印刷号.
	 * 
	 * @return 属性投保单印刷号的值
	 */
    public String getPrintNo(){
        return printNo;
    }

    /**
	 * 设置属性业务来源（直接/代理）.
	 * 
	 * @param businessNature
	 *            待设置的属性业务来源（直接/代理）的值
	 */
    public void setBusinessNature(String businessNature){
        this.businessNature = StringUtils.rightTrim(businessNature);
    }

    /**
	 * 获取属性业务来源（直接/代理）.
	 * 
	 * @return 属性业务来源（直接/代理）的值
	 */
    public String getBusinessNature(){
        return businessNature;
    }

    /**
	 * 设置属性语种标志（C/E/…）.
	 * 
	 * @param language
	 *            待设置的属性语种标志（C/E/…）的值
	 */
    public void setLanguage(String language){
        this.language = StringUtils.rightTrim(language);
    }

    /**
	 * 获取属性语种标志（C/E/…）.
	 * 
	 * @return 属性语种标志（C/E/…）的值
	 */
    public String getLanguage(){
        return language;
    }

    /**
	 * 设置属性保单类型（个人/集体）.
	 * 
	 * @param policyType
	 *            待设置的属性保单类型（个人/集体）的值
	 */
    public void setPolicyType(String policyType){
        this.policyType = StringUtils.rightTrim(policyType);
    }

    /**
	 * 获取属性保单类型（个人/集体）.
	 * 
	 * @return 属性保单类型（个人/集体）的值
	 */
    public String getPolicyType(){
        return policyType;
    }

    /**
	 * 设置属性投保人代码.
	 * 
	 * @param appliCode
	 *            待设置的属性投保人代码的值
	 */
    public void setAppliCode(String appliCode){
        this.appliCode = StringUtils.rightTrim(appliCode);
    }

    /**
	 * 获取属性投保人代码.
	 * 
	 * @return 属性投保人代码的值
	 */
    public String getAppliCode(){
        return appliCode;
    }

    /**
	 * 设置属性投保人名称.
	 * 
	 * @param appliName
	 *            待设置的属性投保人名称的值
	 */
    public void setAppliName(String appliName){
        this.appliName = StringUtils.rightTrim(appliName);
    }

    /**
	 * 获取属性投保人名称.
	 * 
	 * @return 属性投保人名称的值
	 */
    public String getAppliName(){
        return appliName;
    }

    /**
	 * 设置属性投保人地址.
	 * 
	 * @param appliAddress
	 *            待设置的属性投保人地址的值
	 */
    public void setAppliAddress(String appliAddress){
        this.appliAddress = StringUtils.rightTrim(appliAddress);
    }

    /**
	 * 获取属性投保人地址.
	 * 
	 * @return 属性投保人地址的值
	 */
    public String getAppliAddress(){
        return appliAddress;
    }

    /**
	 * 设置属性被保险人代码.
	 * 
	 * @param insuredCode
	 *            待设置的属性被保险人代码的值
	 */
    public void setInsuredCode(String insuredCode){
        this.insuredCode = StringUtils.rightTrim(insuredCode);
    }

    /**
	 * 获取属性被保险人代码.
	 * 
	 * @return 属性被保险人代码的值
	 */
    public String getInsuredCode(){
        return insuredCode;
    }

    /**
	 * 设置属性被保险人名称.
	 * 
	 * @param insuredName
	 *            待设置的属性被保险人名称的值
	 */
    public void setInsuredName(String insuredName){
        this.insuredName = StringUtils.rightTrim(insuredName);
    }

    /**
	 * 获取属性被保险人名称.
	 * 
	 * @return 属性被保险人名称的值
	 */
    public String getInsuredName(){
        return insuredName;
    }

    /**
	 * 设置属性被保险人地址.
	 * 
	 * @param insuredAddress
	 *            待设置的属性被保险人地址的值
	 */
    public void setInsuredAddress(String insuredAddress){
        this.insuredAddress = StringUtils.rightTrim(insuredAddress);
    }

    /**
	 * 获取属性被保险人地址.
	 * 
	 * @return 属性被保险人地址的值
	 */
    public String getInsuredAddress(){
        return insuredAddress;
    }

    /**
	 * 设置属性签单日期/投保日期.
	 * 
	 * @param operateDate
	 *            待设置的属性签单日期/投保日期的值
	 */
    public void setOperateDate(DateTime operateDate){
        this.operateDate = operateDate;
    }

    /**
	 * 获取属性签单日期/投保日期.
	 * 
	 * @return 属性签单日期/投保日期的值
	 */
    public DateTime getOperateDate(){
        return operateDate;
    }

    /**
	 * 设置属性起保日期（启运日期）.
	 * 
	 * @param startDate
	 *            待设置的属性起保日期（启运日期）的值
	 */
    public void setStartDate(DateTime startDate){
        this.startDate = startDate;
    }

    /**
	 * 获取属性起保日期（启运日期）.
	 * 
	 * @return 属性起保日期（启运日期）的值
	 */
    public DateTime getStartDate(){
        return startDate;
    }

    /**
	 * 设置属性起保小时.
	 * 
	 * @param startHour
	 *            待设置的属性起保小时的值
	 */
    public void setStartHour(long startHour){
        this.startHour = startHour;
    }

    /**
	 * 获取属性起保小时.
	 * 
	 * @return 属性起保小时的值
	 */
    public long getStartHour(){
        return startHour;
    }

    /**
	 * 设置属性终保日期.
	 * 
	 * @param endDate
	 *            待设置的属性终保日期的值
	 */
    public void setEndDate(DateTime endDate){
        this.endDate = endDate;
    }

    /**
	 * 获取属性终保日期.
	 * 
	 * @return 属性终保日期的值
	 */
    public DateTime getEndDate(){
        return endDate;
    }

    /**
	 * 设置属性终保小时.
	 * 
	 * @param endHour
	 *            待设置的属性终保小时的值
	 */
    public void setEndHour(long endHour){
        this.endHour = endHour;
    }

    /**
	 * 获取属性终保小时.
	 * 
	 * @return 属性终保小时的值
	 */
    public long getEndHour(){
        return endHour;
    }

    /**
	 * 设置属性净费率.
	 * 
	 * @param pureRate
	 *            待设置的属性净费率的值
	 */
    public void setPureRate(double pureRate){
        this.pureRate = pureRate;
    }

    /**
	 * 获取属性净费率.
	 * 
	 * @return 属性净费率的值
	 */
    public double getPureRate(){
        return pureRate;
    }

    /**
	 * 设置属性手续费比例.
	 * 
	 * @param disRate
	 *            待设置的属性手续费比例的值
	 */
    public void setDisRate(double disRate){
        this.disRate = disRate;
    }

    /**
	 * 获取属性手续费比例.
	 * 
	 * @return 属性手续费比例的值
	 */
    public double getDisRate(){
        return disRate;
    }

    /**
	 * 设置属性总折扣率.
	 * 
	 * @param discount
	 *            待设置的属性总折扣率的值
	 */
    public void setDiscount(double discount){
        this.discount = discount;
    }

    /**
	 * 获取属性总折扣率.
	 * 
	 * @return 属性总折扣率的值
	 */
    public double getDiscount(){
        return discount;
    }

    /**
	 * 设置属性币别代码.
	 * 
	 * @param currency
	 *            待设置的属性币别代码的值
	 */
    public void setCurrency(String currency){
        this.currency = StringUtils.rightTrim(currency);
    }

    /**
	 * 获取属性币别代码.
	 * 
	 * @return 属性币别代码的值
	 */
    public String getCurrency(){
        return currency;
    }

    /**
	 * 设置属性总保险价值.
	 * 
	 * @param sumValue
	 *            待设置的属性总保险价值的值
	 */
    public void setSumValue(double sumValue){
        this.sumValue = sumValue;
    }

    /**
	 * 获取属性总保险价值.
	 * 
	 * @return 属性总保险价值的值
	 */
    public double getSumValue(){
        return sumValue;
    }

    /**
	 * 设置属性总保险金额(折算为人民币总保额).
	 * 
	 * @param sumAmount
	 *            待设置的属性总保险金额(折算为人民币总保额)的值
	 */
    public void setSumAmount(double sumAmount){
        this.sumAmount = sumAmount;
    }

    /**
	 * 获取属性总保险金额(折算为人民币总保额).
	 * 
	 * @return 属性总保险金额(折算为人民币总保额)的值
	 */
    public double getSumAmount(){
        return sumAmount;
    }

    /**
	 * 设置属性总折扣金额.
	 * 
	 * @param sumDiscount
	 *            待设置的属性总折扣金额的值
	 */
    public void setSumDiscount(double sumDiscount){
        this.sumDiscount = sumDiscount;
    }

    /**
	 * 获取属性总折扣金额.
	 * 
	 * @return 属性总折扣金额的值
	 */
    public double getSumDiscount(){
        return sumDiscount;
    }

    /**
	 * 设置属性总保险费(折算为人民币总保费).
	 * 
	 * @param sumPremium
	 *            待设置的属性总保险费(折算为人民币总保费)的值
	 */
    public void setSumPremium(double sumPremium){
        this.sumPremium = sumPremium;
    }

    /**
	 * 获取属性总保险费(折算为人民币总保费).
	 * 
	 * @return 属性总保险费(折算为人民币总保费)的值
	 */
    public double getSumPremium(){
        return sumPremium;
    }

    /**
	 * 设置属性总附加险保费.
	 * 
	 * @param sumSubPrem
	 *            待设置的属性总附加险保费的值
	 */
    public void setSumSubPrem(double sumSubPrem){
        this.sumSubPrem = sumSubPrem;
    }

    /**
	 * 获取属性总附加险保费.
	 * 
	 * @return 属性总附加险保费的值
	 */
    public double getSumSubPrem(){
        return sumSubPrem;
    }

    /**
	 * 设置属性被保险总数量/人数/户数压力容器总数.
	 * 
	 * @param sumQuantity
	 *            待设置的属性被保险总数量/人数/户数压力容器总数的值
	 */
    public void setSumQuantity(long sumQuantity){
        this.sumQuantity = sumQuantity;
    }

    /**
	 * 获取属性被保险总数量/人数/户数压力容器总数.
	 * 
	 * @return 属性被保险总数量/人数/户数压力容器总数的值
	 */
    public long getSumQuantity(){
        return sumQuantity;
    }

    /**
	 * 设置属性司法管辖代码.
	 * 
	 * @param judicalCode
	 *            待设置的属性司法管辖代码的值
	 */
    public void setJudicalCode(String judicalCode){
        this.judicalCode = StringUtils.rightTrim(judicalCode);
    }

    /**
	 * 获取属性司法管辖代码.
	 * 
	 * @return 属性司法管辖代码的值
	 */
    public String getJudicalCode(){
        return judicalCode;
    }

    /**
	 * 设置属性司法管辖.
	 * 
	 * @param judicalScope
	 *            待设置的属性司法管辖的值
	 */
    public void setJudicalScope(String judicalScope){
        this.judicalScope = StringUtils.rightTrim(judicalScope);
    }

    /**
	 * 获取属性司法管辖.
	 * 
	 * @return 属性司法管辖的值
	 */
    public String getJudicalScope(){
        return judicalScope;
    }

    /**
	 * 设置属性交费方式1：现金 2：银行转账 9：其它.
	 * 
	 * @param autoTransRenewFlag
	 *            待设置的属性交费方式1：现金 2：银行转账 9：其它的值
	 */
    public void setAutoTransRenewFlag(String autoTransRenewFlag){
        this.autoTransRenewFlag = StringUtils.rightTrim(autoTransRenewFlag);
    }

    /**
	 * 获取属性交费方式1：现金 2：银行转账 9：其它.
	 * 
	 * @return 属性交费方式1：现金 2：银行转账 9：其它的值
	 */
    public String getAutoTransRenewFlag(){
        return autoTransRenewFlag;
    }

    /**
	 * 设置属性争议解决方式--** 1 诉讼；2 仲裁.
	 * 
	 * @param argueSolution
	 *            待设置的属性争议解决方式--** 1 诉讼；2 仲裁的值
	 */
    public void setArgueSolution(String argueSolution){
        this.argueSolution = StringUtils.rightTrim(argueSolution);
    }

    /**
	 * 获取属性争议解决方式--** 1 诉讼；2 仲裁.
	 * 
	 * @return 属性争议解决方式--** 1 诉讼；2 仲裁的值
	 */
    public String getArgueSolution(){
        return argueSolution;
    }

    /**
	 * 设置属性仲裁委员会名称.
	 * 
	 * @param arbitBoardName
	 *            待设置的属性仲裁委员会名称的值
	 */
    public void setArbitBoardName(String arbitBoardName){
        this.arbitBoardName = StringUtils.rightTrim(arbitBoardName);
    }

    /**
	 * 获取属性仲裁委员会名称.
	 * 
	 * @return 属性仲裁委员会名称的值
	 */
    public String getArbitBoardName(){
        return arbitBoardName;
    }

    /**
	 * 设置属性约定分期交费次数.
	 * 
	 * @param payTimes
	 *            待设置的属性约定分期交费次数的值
	 */
    public void setPayTimes(long payTimes){
        this.payTimes = payTimes;
    }

    /**
	 * 获取属性约定分期交费次数.
	 * 
	 * @return 属性约定分期交费次数的值
	 */
    public long getPayTimes(){
        return payTimes;
    }

    /**
	 * 设置属性批改次数.
	 * 
	 * @param endorseTimes
	 *            待设置的属性批改次数的值
	 */
    public void setEndorseTimes(long endorseTimes){
        this.endorseTimes = endorseTimes;
    }

    /**
	 * 获取属性批改次数.
	 * 
	 * @return 属性批改次数的值
	 */
    public long getEndorseTimes(){
        return endorseTimes;
    }

    /**
	 * 设置属性理赔次数.
	 * 
	 * @param claimTimes
	 *            待设置的属性理赔次数的值
	 */
    public void setClaimTimes(long claimTimes){
        this.claimTimes = claimTimes;
    }

    /**
	 * 获取属性理赔次数.
	 * 
	 * @return 属性理赔次数的值
	 */
    public long getClaimTimes(){
        return claimTimes;
    }

    /**
	 * 设置属性出单机构.
	 * 
	 * @param makeCom
	 *            待设置的属性出单机构的值
	 */
    public void setMakeCom(String makeCom){
        this.makeCom = StringUtils.rightTrim(makeCom);
    }

    /**
	 * 获取属性出单机构.
	 * 
	 * @return 属性出单机构的值
	 */
    public String getMakeCom(){
        return makeCom;
    }

    /**
	 * 设置属性签单地点.
	 * 
	 * @param operateSite
	 *            待设置的属性签单地点的值
	 */
    public void setOperateSite(String operateSite){
        this.operateSite = StringUtils.rightTrim(operateSite);
    }

    /**
	 * 获取属性签单地点.
	 * 
	 * @return 属性签单地点的值
	 */
    public String getOperateSite(){
        return operateSite;
    }

    /**
	 * 设置属性业务归属机构代码.
	 * 
	 * @param comCode
	 *            待设置的属性业务归属机构代码的值
	 */
    public void setComCode(String comCode){
        this.comCode = StringUtils.rightTrim(comCode);
    }

    /**
	 * 获取属性业务归属机构代码.
	 * 
	 * @return 属性业务归属机构代码的值
	 */
    public String getComCode(){
        return comCode;
    }

    /**
	 * 设置属性经办人代码.
	 * 
	 * @param handlerCode
	 *            待设置的属性经办人代码的值
	 */
    public void setHandlerCode(String handlerCode){
        this.handlerCode = StringUtils.rightTrim(handlerCode);
    }

    /**
	 * 获取属性经办人代码.
	 * 
	 * @return 属性经办人代码的值
	 */
    public String getHandlerCode(){
        return handlerCode;
    }

    /**
	 * 设置属性归属业务员代码.
	 * 
	 * @param handler1Code
	 *            待设置的属性归属业务员代码的值
	 */
    public void setHandler1Code(String handler1Code){
        this.handler1Code = StringUtils.rightTrim(handler1Code);
    }

    /**
	 * 获取属性归属业务员代码.
	 * 
	 * @return 属性归属业务员代码的值
	 */
    public String getHandler1Code(){
        return handler1Code;
    }

    /**
	 * 设置属性复核人代码.
	 * 
	 * @param approverCode
	 *            待设置的属性复核人代码的值
	 */
    public void setApproverCode(String approverCode){
        this.approverCode = StringUtils.rightTrim(approverCode);
    }

    /**
	 * 获取属性复核人代码.
	 * 
	 * @return 属性复核人代码的值
	 */
    public String getApproverCode(){
        return approverCode;
    }

    /**
	 * 设置属性最终核保人代码.
	 * 
	 * @param underWriteCode
	 *            待设置的属性最终核保人代码的值
	 */
    public void setUnderWriteCode(String underWriteCode){
        this.underWriteCode = StringUtils.rightTrim(underWriteCode);
    }

    /**
	 * 获取属性最终核保人代码.
	 * 
	 * @return 属性最终核保人代码的值
	 */
    public String getUnderWriteCode(){
        return underWriteCode;
    }

    /**
	 * 设置属性最终核保人名称.
	 * 
	 * @param underWriteName
	 *            待设置的属性最终核保人名称的值
	 */
    public void setUnderWriteName(String underWriteName){
        this.underWriteName = StringUtils.rightTrim(underWriteName);
    }

    /**
	 * 获取属性最终核保人名称.
	 * 
	 * @return 属性最终核保人名称的值
	 */
    public String getUnderWriteName(){
        return underWriteName;
    }

    /**
	 * 设置属性操作员代码/第一次录入人员代码.
	 * 
	 * @param operatorCode
	 *            待设置的属性操作员代码/第一次录入人员代码的值
	 */
    public void setOperatorCode(String operatorCode){
        this.operatorCode = StringUtils.rightTrim(operatorCode);
    }

    /**
	 * 获取属性操作员代码/第一次录入人员代码.
	 * 
	 * @return 属性操作员代码/第一次录入人员代码的值
	 */
    public String getOperatorCode(){
        return operatorCode;
    }

    /**
	 * 设置属性计算机输单日期.
	 * 
	 * @param inputDate
	 *            待设置的属性计算机输单日期的值
	 */
    public void setInputDate(DateTime inputDate){
        this.inputDate = inputDate;
    }

    /**
	 * 获取属性计算机输单日期.
	 * 
	 * @return 属性计算机输单日期的值
	 */
    public DateTime getInputDate(){
        return inputDate;
    }

    /**
	 * 设置属性计算机输单小时.
	 * 
	 * @param inputHour
	 *            待设置的属性计算机输单小时的值
	 */
    public void setInputHour(long inputHour){
        this.inputHour = inputHour;
    }

    /**
	 * 获取属性计算机输单小时.
	 * 
	 * @return 属性计算机输单小时的值
	 */
    public long getInputHour(){
        return inputHour;
    }

    /**
	 * 设置属性核保完成日期.
	 * 
	 * @param underWriteEndDate
	 *            待设置的属性核保完成日期的值
	 */
    public void setUnderWriteEndDate(DateTime underWriteEndDate){
        this.underWriteEndDate = underWriteEndDate;
    }

    /**
	 * 获取属性核保完成日期.
	 * 
	 * @return 属性核保完成日期的值
	 */
    public DateTime getUnderWriteEndDate(){
        return underWriteEndDate;
    }

    /**
	 * 设置属性保单统计年月.
	 * 
	 * @param statisticsYM
	 *            待设置的属性保单统计年月的值
	 */
    public void setStatisticsYM(DateTime statisticsYM){
        this.statisticsYM = statisticsYM;
    }

    /**
	 * 获取属性保单统计年月.
	 * 
	 * @return 属性保单统计年月的值
	 */
    public DateTime getStatisticsYM(){
        return statisticsYM;
    }

    /**
	 * 设置属性代理人代码.
	 * 
	 * @param agentCode
	 *            待设置的属性代理人代码的值
	 */
    public void setAgentCode(String agentCode){
        this.agentCode = StringUtils.rightTrim(agentCode);
    }

    /**
	 * 获取属性代理人代码.
	 * 
	 * @return 属性代理人代码的值
	 */
    public String getAgentCode(){
        return agentCode;
    }

    /**
	 * 设置属性共保标志--** (0非共保/1主共保/2共保).
	 * 
	 * @param coinsFlag
	 *            待设置的属性共保标志--** (0非共保/1主共保/2共保)的值
	 */
    public void setCoinsFlag(String coinsFlag){
        this.coinsFlag = StringUtils.rightTrim(coinsFlag);
    }

    /**
	 * 获取属性共保标志--** (0非共保/1主共保/2共保).
	 * 
	 * @return 属性共保标志--** (0非共保/1主共保/2共保)的值
	 */
    public String getCoinsFlag(){
        return coinsFlag;
    }

    /**
	 * 设置属性商业分保标志(0无需分保/1需分保/2已分保).
	 * 
	 * @param reinsFlag
	 *            待设置的属性商业分保标志(0无需分保/1需分保/2已分保)的值
	 */
    public void setReinsFlag(String reinsFlag){
        this.reinsFlag = StringUtils.rightTrim(reinsFlag);
    }

    /**
	 * 获取属性商业分保标志(0无需分保/1需分保/2已分保).
	 * 
	 * @return 属性商业分保标志(0无需分保/1需分保/2已分保)的值
	 */
    public String getReinsFlag(){
        return reinsFlag;
    }

    /**
	 * 设置属性统保标志(0/1统保) --**0顺位,1均分,2法定,3其它.
	 * 
	 * @param allinsFlag
	 *            待设置的属性统保标志(0/1统保) --**0顺位,1均分,2法定,3其它的值
	 */
    public void setAllinsFlag(String allinsFlag){
        this.allinsFlag = StringUtils.rightTrim(allinsFlag);
    }

    /**
	 * 获取属性统保标志(0/1统保) --**0顺位,1均分,2法定,3其它.
	 * 
	 * @return 属性统保标志(0/1统保) --**0顺位,1均分,2法定,3其它的值
	 */
    public String getAllinsFlag(){
        return allinsFlag;
    }

    /**
	 * 设置属性核保标志 --** (0初始值/1通过/2不通过/3 无需核保 9待核保).
	 * 
	 * @param underWriteFlag
	 *            待设置的属性核保标志 --** (0初始值/1通过/2不通过/3 无需核保 9待核保)的值
	 */
    public void setUnderWriteFlag(String underWriteFlag){
        this.underWriteFlag = StringUtils.rightTrim(underWriteFlag);
    }

    /**
	 * 获取属性核保标志 --** (0初始值/1通过/2不通过/3 无需核保 9待核保).
	 * 
	 * @return 属性核保标志 --** (0初始值/1通过/2不通过/3 无需核保 9待核保)的值
	 */
    public String getUnderWriteFlag(){
        return underWriteFlag;
    }

    /**
	 * 设置属性其它标志字段： --** [1] (0/1)新/续保标志 --** [2] (0/1)被续保标志(1:被续保) --** [3] 退保标志
	 * --** 1:全单退保 --** 2:满期退保 --** 3:部分退保 --** [4] 注销(1:已注销) --** [5]
	 * 遗失标志（1：已遗失） --** [6] 有效标志（1：终止合同, 2 中止.
	 * 
	 * @param othFlag
	 *            待设置的属性其它标志字段： --** [1] (0/1)新/续保标志 --** [2] (0/1)被续保标志(1:被续保)
	 *            --** [3] 退保标志 --** 1:全单退保 --** 2:满期退保 --** 3:部分退保 --** [4]
	 *            注销(1:已注销) --** [5] 遗失标志（1：已遗失） --** [6] 有效标志（1：终止合同, 2 中止的值
	 */
    public void setOthFlag(String othFlag){
        this.othFlag = StringUtils.rightTrim(othFlag);
    }

    /**
	 * 获取属性其它标志字段： --** [1] (0/1)新/续保标志 --** [2] (0/1)被续保标志(1:被续保) --** [3] 退保标志
	 * --** 1:全单退保 --** 2:满期退保 --** 3:部分退保 --** [4] 注销(1:已注销) --** [5]
	 * 遗失标志（1：已遗失） --** [6] 有效标志（1：终止合同, 2 中止.
	 * 
	 * @return 属性其它标志字段： --** [1] (0/1)新/续保标志 --** [2] (0/1)被续保标志(1:被续保) --**
	 *         [3] 退保标志 --** 1:全单退保 --** 2:满期退保 --** 3:部分退保 --** [4] 注销(1:已注销)
	 *         --** [5] 遗失标志（1：已遗失） --** [6] 有效标志（1：终止合同, 2 中止的值
	 */
    public String getOthFlag(){
        return othFlag;
    }

    /**
	 * 设置属性状态字段.
	 * 
	 * @param flag
	 *            待设置的属性状态字段的值
	 */
    public void setFlag(String flag){
        this.flag = StringUtils.rightTrim(flag);
    }

    /**
	 * 获取属性状态字段.
	 * 
	 * @return 属性状态字段的值
	 */
    public String getFlag(){
        return flag;
    }

    /**
	 * 设置属性超出部分手续费比例.
	 * 
	 * @param disRate1
	 *            待设置的属性超出部分手续费比例的值
	 */
    public void setDisRate1(double disRate1){
        this.disRate1 = disRate1;
    }

    /**
	 * 获取属性超出部分手续费比例.
	 * 
	 * @return 属性超出部分手续费比例的值
	 */
    public double getDisRate1(){
        return disRate1;
    }

    /**
	 * 设置属性业务类型.
	 * 
	 * @param businessFlag
	 *            待设置的属性业务类型的值
	 */
    public void setBusinessFlag(String businessFlag){
        this.businessFlag = StringUtils.rightTrim(businessFlag);
    }

    /**
	 * 获取属性业务类型.
	 * 
	 * @return 属性业务类型的值
	 */
    public String getBusinessFlag(){
        return businessFlag;
    }

    /**
	 * 设置属性缴费方式.
	 * 
	 * @param payMode
	 *            待设置的属性缴费方式的值
	 */
    public void setPayMode(String payMode){
        this.payMode = StringUtils.rightTrim(payMode);
    }

    /**
	 * 获取属性缴费方式.
	 * 
	 * @return 属性缴费方式的值
	 */
    public String getPayMode(){
        return payMode;
    }

    /**
	 * 设置属性最后一次修改人员代码.
	 * 
	 * @param updaterCode
	 *            待设置的属性最后一次修改人员代码的值
	 */
    public void setUpdaterCode(String updaterCode){
        this.updaterCode = StringUtils.rightTrim(updaterCode);
    }

    /**
	 * 获取属性最后一次修改人员代码.
	 * 
	 * @return 属性最后一次修改人员代码的值
	 */
    public String getUpdaterCode(){
        return updaterCode;
    }

    /**
	 * 设置属性最后一次修改日期.
	 * 
	 * @param updateDate
	 *            待设置的属性最后一次修改日期的值
	 */
    public void setUpdateDate(DateTime updateDate){
        this.updateDate = updateDate;
    }

    /**
	 * 获取属性最后一次修改日期.
	 * 
	 * @return 属性最后一次修改日期的值
	 */
    public DateTime getUpdateDate(){
        return updateDate;
    }

    /**
	 * 设置属性最后一次修改时间.
	 * 
	 * @param updateHour
	 *            待设置的属性最后一次修改时间的值
	 */
    public void setUpdateHour(String updateHour){
        this.updateHour = StringUtils.rightTrim(updateHour);
    }

    /**
	 * 获取属性最后一次修改时间.
	 * 
	 * @return 属性最后一次修改时间的值
	 */
    public String getUpdateHour(){
        return updateHour;
    }

    /**
	 * 设置属性签单日期.
	 * 
	 * @param signDate
	 *            待设置的属性签单日期的值
	 */
    public void setSignDate(DateTime signDate){
        this.signDate = signDate;
    }

    /**
	 * 获取属性签单日期.
	 * 
	 * @return 属性签单日期的值
	 */
    public DateTime getSignDate(){
        return signDate;
    }

    /**
	 * 设置属性是否股东业务标识.
	 * 
	 * @param shareHolderFlag
	 *            待设置的属性是否股东业务标识的值
	 */
    public void setShareHolderFlag(String shareHolderFlag){
        this.shareHolderFlag = StringUtils.rightTrim(shareHolderFlag);
    }

    /**
	 * 获取属性是否股东业务标识.
	 * 
	 * @return 属性是否股东业务标识的值
	 */
    public String getShareHolderFlag(){
        return shareHolderFlag;
    }

    /**
	 * 设置属性协议号.
	 * 
	 * @param agreementNo
	 *            待设置的属性协议号的值
	 */
    public void setAgreementNo(String agreementNo){
        this.agreementNo = StringUtils.rightTrim(agreementNo);
    }

    /**
	 * 获取属性协议号.
	 * 
	 * @return 属性协议号的值
	 */
    public String getAgreementNo(){
        return agreementNo;
    }

    /**
	 * 设置属性询价单号.
	 * 
	 * @param inquiryNo
	 *            待设置的属性询价单号的值
	 */
    public void setInquiryNo(String inquiryNo){
        this.inquiryNo = StringUtils.rightTrim(inquiryNo);
    }

    /**
	 * 获取属性询价单号.
	 * 
	 * @return 属性询价单号的值
	 */
    public String getInquiryNo(){
        return inquiryNo;
    }

    /**
	 * 設置屬性the sinosoft visa code.
	 * 
	 * @param visaCode
	 *            待設置的the sinosoft visa code的值
	 */
    public void setVisaCode(String visaCode){
        this.visaCode = StringUtils.rightTrim(visaCode);
    }

    /**
	 * 獲取屬性the sinosoft visa code.
	 * 
	 * @return 屬性the sinosoft visa code的值
	 */
    public String getVisaCode(){
        return visaCode;
    }        
    
    /**
	 * 設置屬性the sinosoft manual type.
	 * 
	 * @param manualType
	 *            待設置的the sinosoft manual type的值
	 */
    public void setManualType(String manualType){
        this.manualType = StringUtils.rightTrim(manualType);
    }

    /**
	 * 獲取屬性the sinosoft manual type.
	 * 
	 * @return 屬性the sinosoft manual type的值
	 */
    public String getManualType(){
        return manualType;
    }

	/**
	 * Gets the 属性业务员名称.
	 * 
	 * @return the 属性业务员名称
	 */
	public String getHandler1Name() {
		return handler1Name;
	}

	/**
	 * Sets the 属性业务员名称.
	 * 
	 * @param handler1Name
	 *            the new 属性业务员名称
	 */
	public void setHandler1Name(String handler1Name) {
		this.handler1Name = handler1Name;
	}

	/**
	 * Gets the 属性名称.
	 * 
	 * @return the 属性名称
	 */
	public String getHandlerName() {
		return handlerName;
	}

	/**
	 * Sets the 属性名称.
	 * 
	 * @param handlerName
	 *            the new 属性名称
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/**
	 * Gets the 属性见费出单标志.
	 * 
	 * @return the 属性见费出单标志
	 */
	public String getJFeeFlag() {
		return jFeeFlag;
	}

	/**
	 * Sets the 属性见费出单标志.
	 * 
	 * @param feeFlag
	 *            the new 属性见费出单标志
	 */
	public void setJFeeFlag(String feeFlag) {
		jFeeFlag = feeFlag;
	}

	/**
	 * Gets the 属性实收确认人代码.
	 * 
	 * @return the 属性实收确认人代码
	 */
	public String getPayRefCode() {
		return payRefCode;
	}

	/**
	 * Sets the 属性实收确认人代码.
	 * 
	 * @param payRefCode
	 *            the new 属性实收确认人代码
	 */
	public void setPayRefCode(String payRefCode) {
		this.payRefCode = payRefCode;
	}

	/**
	 * Gets the 属性实收确认人名称.
	 * 
	 * @return the 属性实收确认人名称
	 */
	public String getPayRefName() {
		return payRefName;
	}

	/**
	 * Sets the 属性实收确认人名称.
	 * 
	 * @param payRefName
	 *            the new 属性实收确认人名称
	 */
	public void setPayRefName(String payRefName) {
		this.payRefName = payRefName;
	}

	/**
	 * Gets the 属性核保预确认时间.
	 * 
	 * @return the 属性核保预确认时间
	 */
	public DateTime getPreCheckDate() {
		return preCheckDate;
	}

	/**
	 * Sets the 属性核保预确认时间.
	 * 
	 * @param preCheckDate
	 *            the new 属性核保预确认时间
	 */
	public void setPreCheckDate(DateTime preCheckDate) {
		this.preCheckDate = preCheckDate;
	}

	/**
	 * 獲取屬性the sinosoft pay ref time.
	 * 
	 * @return 屬性the sinosoft pay ref time的值
	 */
	public DateTime getPayRefTime() {
		return PayRefTime;
	}

	/**
	 * 設置屬性the sinosoft pay ref time.
	 * 
	 * @param payRefTime
	 *            待設置的the sinosoft pay ref time的值
	 */
	public void setPayRefTime(DateTime payRefTime) {
		PayRefTime = payRefTime;
	}

	/**
	 * 獲取屬性the sinosoft prints the time.
	 * 
	 * @return 屬性the sinosoft prints the time的值
	 */
	public DateTime getPrintTime() {
		return PrintTime;
	}

	/**
	 * 設置屬性the sinosoft prints the time.
	 * 
	 * @param printTime
	 *            待設置的the sinosoft prints the time的值
	 */
	public void setPrintTime(DateTime printTime) {
		PrintTime = printTime;
	}

	/**
	 * 獲取屬性the sinosoft agri type.
	 * 
	 * @return 屬性the sinosoft agri type的值
	 */
	public String getAgriType() {
		return AgriType;
	}

	/**
	 * 設置屬性the sinosoft agri type.
	 * 
	 * @param agriType
	 *            待設置的the sinosoft agri type的值
	 */
	public void setAgriType(String agriType) {
		AgriType = agriType;
	}    
    
    //add by dengpeng 20080220 增加银邮渠道的二级查询 begin;
	/**
	 * 獲取屬性the sinosoft sub business nature.
	 * 
	 * @return 屬性the sinosoft sub business nature的值
	 */
    public String getSubBusinessNature() {
		return SubBusinessNature;
	}

	/**
	 * 設置屬性the sinosoft sub business nature.
	 * 
	 * @param subBusinessNature
	 *            待設置的the sinosoft sub business nature的值
	 */
	public void setSubBusinessNature(String subBusinessNature) {
		this.SubBusinessNature = subBusinessNature;
	}    
    //add by dengpeng 20080220 增加银邮渠道的二级查询 end;
	
	//added by xiongguojun 20080923 增加销售渠道、大项目标志和签单币别与人民币的兑换率 begin
	/**
	 * 设置销售渠道字段.
	 * 
	 * @param channelType
	 *            待设置的属性销售渠道字段的值
	 */
    public void setChannelType(String channelType){
        this.channelType = StringUtils.rightTrim(channelType);
    }

    /**
	 * 获取属性销售渠道字段.
	 * 
	 * @return 属性销售渠道字段的值
	 */
    public String getChannelType(){
        return channelType;
    }
    
    /**
	 * 设置大项目标志.
	 * 
	 * @param projectsFlag
	 *            待设置的属性大项目标志的值
	 */
    public void setProjectsFlag(String projectsFlag){
        this.projectsFlag = StringUtils.rightTrim(projectsFlag);
    }

    /**
	 * 获取属性大项目标志.
	 * 
	 * @return 属性大项目标志的值
	 */
    public String getProjectsFlag(){
        return projectsFlag;
    }
    
    /**
	 * 设置签单币别与人民币的兑换率.
	 * 
	 * @param exchangeRate
	 *            the new 属性签单币别与人民币的兑换率
	 */
    public void setExchangeRate(double exchangeRate){
        this.exchangeRate = exchangeRate;
    }

    /**
	 * 获取属性签单币别与人民币的兑换率.
	 * 
	 * @return 属性签单币别与人民币的兑换率的值
	 */
    public double getExchangeRate(){
        return exchangeRate;
    }
    //added by xiongguojun 20080923

	/**
	 * Gets the 属性保单核保通过级别 add by yanglibo20081128.
	 * 
	 * @return the 属性保单核保通过级别 add by yanglibo20081128
	 */
    public String getProposalLevel() {
		return proposalLevel;
	}

	/**
	 * Sets the 属性保单核保通过级别 add by yanglibo20081128.
	 * 
	 * @param proposalLevel
	 *            the new 属性保单核保通过级别 add by yanglibo20081128
	 */
	public void setProposalLevel(String proposalLevel) {
		this.proposalLevel = proposalLevel;
	}

	/**
	 * 獲取屬性the sinosoft group type.
	 * 
	 * @return 屬性the sinosoft group type的值
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * 設置屬性the sinosoft group type.
	 * 
	 * @param groupType
	 *            待設置的the sinosoft group type的值
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * 獲取屬性the sinosoft start stages.
	 * 
	 * @return 屬性the sinosoft start stages的值
	 */
	public String getStartStages() {
		return startStages;
	}

	/**
	 * 設置屬性the sinosoft start stages.
	 * 
	 * @param startStages
	 *            待設置的the sinosoft start stages的值
	 */
	public void setStartStages(String startStages) {
		this.startStages = startStages;
	}
}
