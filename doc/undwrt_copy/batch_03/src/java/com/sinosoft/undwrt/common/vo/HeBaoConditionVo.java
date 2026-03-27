package com.sinosoft.undwrt.common.vo;

import java.io.Serializable;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.common.util.StringUtils;

/**
 * 这是heBaoCondition的数据传输对象基类<br>
 * 创建于 JToolpad(1.4.0) Vendor:zhouxianli1978@msn.com
 * modify by zhangruifeng 2008-2-20 reason:新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制
 * added by LanNing 20080225 投资金产品调整
 * modify by zhangruifeng 20080304  reason:增加对责任保险(涉外)的附加自留保额的单独控制
 * add by gengxiaobo 20080326 组合险特殊险种起重机械综合保险高级条件因子
 * added by LanNing 20080421 1505每次事故赔偿限额
 * added by gengxiaobo 20080604 增加最大车累记赔偿限额,调整每次事故赔偿限额取值。
 *added by yanglibo  20081112   增加01险类中的国民经济行业保险核保的控制，增加SumAmountS字段
 * @added by xuning 20080814 圆丰产品的质押核批权放在省公司
 * @added by hanxiao 20090226 0125工程机械设备保险增加附加险保额核保因子
 * @added by liuwei 20090303 0911国内货运险（08版）增加附加险核保因子
 * added by xiongguojun 20090327 1506核乏料运输：累计责任限额
 * added by xiongguojun 20090814 货运险启运日期
 */
public class HeBaoConditionVo implements Serializable{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = HeBaoConditionVo.class.getName().hashCode();
    /*---公共部分的高级条件因子--begin---*/
    /** 属性币种. */
    private String Currency = "";
    
    /** 属性净费比例. */
    private double OutLayRate = -1D;
    
    /** 属性直接倒签单天数. */
    private double DirectDay = -1D;
    
    /** 属性代理倒签单天数. */
    private double AgentDay = -1D;
    
    /** 属性中间成本. */
    private double MidCost = -1D;
    
    /** 属性手续费比例. */
    private double WorkFeeProportion = -1D;
    
    /** 属性允许保单注销天数. */
    private double WriteOffDays = -1D;
    
    /** 属性交费宽限期天数. */
    private double GracePeriodDays = -1D;
    
    /** 属性折扣率. */
    private double RabateRate = -1D;
    
    /** 属性总保额. */
    private double SumAmount = -1D;    
    
    /** 属性总保额. */
    private double SumAmountS = -1D; //yanglibo 国民经济行业核保控制
    
    /** 属性总保额CNY. */
    private double SumAmountC = -1D;  
    
    /** 属性总保额USD. */
    private double SumAmountU = -1D; 
    
    /** 属性联共保. */
    private String UnitInsure = "";
    /*---公共部分的高级条件因子---end--*/
    
    /*--车险部分的高级条件因子---begin--*/
    /** 属性是否允许招标. */
    private String PermitBidding = "Y";
    
    /** 属性招标系数下浮比例. */
    private double BiddingDownProportion = -1D; 
    
    /** 属性客户类型. */
    private String ClientProperty = "";
    
    /** 属性使用性质. */
    private String UsingProperty = "999";
    
    /** 属性车型. */
    private String CarType = "";    
    
    /** 属性车辆使用年限. */
    private double UsingYearLimit =  -1D; 
    
    /** 属性团车数量. */
    private double GroupCarSum = -1D; 
    
    /** 属性车损险保额. */
    private double AmountA = -1D; 
    
    /** 属性三者险保额. */
    private double AmountB = -1D; 
    
    /** 属性盗抢保额. */
    private double AmountG = -1D; 
    
    /** 属性车上人员保额. */
    private double AmountD1 = -1D; 
    
    /** 属性车上货物保额. */
    private double AmountD2 = -1D;		//车上货物责任险保额
    
    /** 屬性The sinosoft amount g1. */
    private double amountG1 = -1D;		//特种车盗抢险保额
    
    /** 屬性The sinosoft amount a g1. */
    private double amountAG1 = -1D;		//特种车车损险保额
    
    /** 屬性The sinosoft amount b g1. */
    private double amountBG1 = -1D;		//特种车三者险保额
    
    /** 屬性The sinosoft Amount d11 mj. */
    private double AmountD11MJ = -1D;	//摩托车、拖拉机车上人员责任险保额(每座)
    
    /** 屬性The sinosoft amount gmj. */
    private double amountGMJ = -1D;		//摩托车、拖拉机盗抢险保额
    
    /** 屬性The sinosoft amount amj. */
    private double amountAMJ = -1D;		//摩托车、拖拉机车损险保额
    
    /** 屬性The sinosoft amount bmj. */
    private double amountBMJ = -1D;		//摩托车、拖拉机三者险保额
    
    /** 屬性The sinosoft Suttle amount a. */
    private double SuttleAmountA = -1D;	//增加车损险净自留额
    
    /** 屬性The sinosoft Suttle amount b. */
    private double SuttleAmountB = -1D;	//增加三者险净自留额
    
    /** 屬性The sinosoft Amount new. */
    private double AmountNew = -1D;	   	//新增设备
    
    /** 屬性The sinosoft Amount man sum. */
    private double AmountManSum = -1D;  //车上人员责任险总保额
    
    /** 屬性The sinosoft Amount w. */
    private double AmountW = -1D; 		//随行物品损失保险金额
    
    /** 屬性The sinosoft Endorse power. */
    private String EndorsePower = ""; 	//批单权限
    /*---车险部分的高级条件因子---end--*/
    
    /** *******非车部分***********. */
    private double AmountPer03010001 = -1D; 
    
    /** 屬性The sinosoft Amount per03010002. */
    private double AmountPer03010002 = -1D;
    
    /** 屬性The sinosoft Unit amount03. */
    private double UnitAmount03 = -1D;  	   //add by hanxiao 20091027 家财险每人保额
    
    /** 屬性The sinosoft Amount per9000452. */
    private double AmountPer9000452 = -1D;     //0301家庭财产保险附加险 附加盗抢保险条款保额
    
    /** 屬性The sinosoft Amount per9000453. */
    private double AmountPer9000453 = -1D;     //0301家庭财产保险附加险 附加家用电器用电安全保险条款保额
    
    /** 屬性The sinosoft Amount per9000454. */
    private double AmountPer9000454 = -1D;     //0301家庭财产保险附加险 附加管道破裂及水渍保险条款保额
    
    /** 屬性The sinosoft Amount per9000449. */
    private double AmountPer9000449 = -1D;     //0301家庭财产保险附加险 附加居家责任保险条款保额
    
    /** 屬性The sinosoft Amount per9000450. */
    private double AmountPer9000450 = -1D;     //0301家庭财产保险附加险 附加家庭伤害保险条款保额
    
    /** 屬性The sinosoft Amount per9000451. */
    private double AmountPer9000451 = -1D;     //0301家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
    
    /** 屬性The sinosoft Trial amount. */
    private double TrialAmount = -1D; 
    
    /** 屬性The sinosoft Trial premium. */
    private double TrialPremium = -1D; 
    
    /** 屬性The sinosoft Allow split. */
    private String AllowSplit = "Y";
    
    /** 屬性The sinosoft Plus rate. */
    private double PlusRate = 0D;
    
    /** 屬性The sinosoft Prepay protocol. */
    private String PrepayProtocol = "Y";
    
    /** 屬性The sinosoft Third limit sum07. */
    private double ThirdLimitSum07 = -1D; 
    
    /** 屬性The sinosoft Third limit acc07. */
    private double ThirdLimitAcc07 = -1D; 
    
    /** 屬性The sinosoft Limit man acc01. */
    private double LimitManAcc01 = -1D; 	//每人事故赔偿限额
    
    /** 屬性The sinosoft Limit acc12. */
    private double LimitAcc12 = -1D; 		//每次事故赔偿限额
    
    /** 屬性The sinosoft Limit acc03. */
    private double LimitAcc03 = -1D; 		//每次事故财产损失赔偿限额
    
    /** 屬性The sinosoft limit man acc05. */
    private double limitManAcc05 = -1D; 	//每次事故每人人身伤亡赔偿限额
    
    /** 屬性The sinosoft Ship age. */
    private double ShipAge = -1D; 			//船龄
    
    
    /** 屬性The sinosoft Limit amount02. */
    private double LimitAmount02 = -1D; 		  //每次事故赔偿限额
    
    /** 屬性The sinosoft Limit amount03. */
    private double LimitAmount03 = -1D; 		  //累计赔偿限额
    
    /** 屬性The sinosoft Limit amount46. */
    private double LimitAmount46 = -1D; 	  	  //累计赔偿限额(旅行社责任险)
    
    /** 屬性The sinosoft Limit amount51. */
    private double LimitAmount51 = -1D; 		  //每人赔偿限额(旅行社责任险)
    
    /** 屬性The sinosoft Limit amount66. */
    private double LimitAmount66 = -1D;			  //每人赔偿限额
    
    /** 屬性The sinosoft Manage fee proportion. */
    private double ManageFeeProportion = -1D;	  //经纪费比例
    
    /** 屬性The sinosoft Limit cargo acc. */
    private double LimitCargoAcc = -1;            //1506货物责任：每次事故责任限额
    
    /** 屬性The sinosoft Limit third acc. */
    private double LimitThirdAcc = -1;            //1506第三者责任：每次事故责任限额
    
    /** 屬性The sinosoft Limit third acc2. */
    private double LimitThirdAcc2 = -1;           //1506除污费用：每次事故赔偿限额
    
    /** 屬性The sinosoft Limit third acc4. */
    private double LimitThirdAcc4 = -1;           //1506核乏料运输：累计责任限额
    
    /** 屬性The sinosoft Limit third acc b. */
    private double LimitThirdAccB = -1;           //附加第三者责任 每次事故责任限额
    
    /** 屬性The sinosoft Limit acc1. */
    private double LimitAcc1 = -1;                //1515.1516每次事故赔偿限额(国内)
    
    /** 屬性The sinosoft Limit acc2. */
    private double LimitAcc2 = -1;                //1515.1516每次事故赔偿限额(世界范围除美加)
    
    /** 屬性The sinosoft Limit acc3. */
    private double LimitAcc3 = -1;                //1515.1516每次事故赔偿限额(世界范围含美加)
    
    /** 屬性The sinosoft Sum amount1. */
    private double SumAmount1 = -1;               //1515.1516累计赔偿限额(国内)
    
    /** 屬性The sinosoft Sum amount2. */
    private double SumAmount2 = -1;               //1515.1516累计赔偿限额(世界范围除美加)
    
    /** 屬性The sinosoft Sum amount3. */
    private double SumAmount3 = -1;               //1515.1516累计赔偿限额(世界范围含美加)
    
    /** 屬性The sinosoft Limit man acc11. */
    private double LimitManAcc11 = -1;            //每人人身伤亡赔偿限额
    
    /** 屬性The sinosoft Limit man acc12. */
    private double LimitManAcc12 = -1;            //每人人身伤亡赔偿限额
    
    /** 屬性The sinosoft Limit man acc13. */
    private double LimitManAcc13 = -1;            //每人意外上海医疗费用
    
    /** 屬性The sinosoft Limit man acc14. */
    private double LimitManAcc14 = -1;            //每人意外上海医疗费用
    
    /** 屬性The sinosoft Amount. */
    private double Amount = -1;                   //0108硬件损失保额
    
    /** 屬性The sinosoft Limit man acc. */
    private double LimitManAcc = -1 ;             //0108数据复制费用每次赔偿限额
    
    /** 屬性The sinosoft Sum amount08. */
    private double SumAmount08 = -1;              //0108累计赔偿限额
    
    /** 屬性The sinosoft Tui bao premium. */
    private double TuiBaoPremium = -1 ;           //退保的保额
    
    /** 屬性The sinosoft Chg premium. */
    private String ChgPremium = "" ;
    
    /** 屬性The sinosoft Endor dis rate. */
    private String EndorDisRate = "" ;        	//手续费批改由哪级核保
    
    /** 屬性The sinosoft Allow check. */
    private String AllowCheck = "" ;
    
    /** 屬性The sinosoft Sum amount0145100. */
    private double SumAmount0145100 = -1;        //0125工程机械设备保险附加险 附加自燃损失保险条款保额
    
    /** 屬性The sinosoft Sum amount0145200. */
    private double SumAmount0145200 = -1;        //0125工程机械设备保险附加险 附加第三者责任保险条款保额
    
    /** 屬性The sinosoft Sum amount0145300. */
    private double SumAmount0145300 = -1;        //0125工程机械设备保险附加 附加全车盗抢保险条款保额
    
    /** 屬性The sinosoft Sum amount0145400. */
    private double SumAmount0145400 = -1;        //0125工程机械设备保险附加险 附加工程机械设备操作人员责任保险条款保额
    
    /** 屬性The sinosoft Sum amount0145500. */
    private double SumAmount0145500 = -1;        //0125工程机械设备保险附加险 附加碰撞、倾覆保险保额
    
    /*---意健险部分的高级条件因子---begin--*/
	/** 屬性The sinosoft Unit proportion. */
    private double UnitProportion = -1D;//团单最低人数比例
    
    /** 屬性The sinosoft Sum amount per. */
    private double SumAmountPer = -1D; 	//每人保险金额
    
    /** 屬性The sinosoft Sum amount per1. */
    private double SumAmountPer1 = -1D; //每人保险金额(一类职业)
    
    /** 屬性The sinosoft Sum amount per2. */
    private double SumAmountPer2 = -1D; //每人保险金额(二类职业)
    
    /** 屬性The sinosoft Sum amount per3. */
    private double SumAmountPer3 = -1D; //每人保险金额(三类职业)
    
    /** 屬性The sinosoft Sum amount per4. */
    private double SumAmountPer4 = -1D; //每人保险金额(四类职业)
    
    /** 屬性The sinosoft Sum amount per5. */
    private double SumAmountPer5 = -1D; //每人保险金额(五类职业)
    
    /** 屬性The sinosoft Sum amount per6. */
    private double SumAmountPer6 = -1D; //每人保险金额(六类职业)
   
    /** 屬性The sinosoft Sub amount per01. */
    private double SubAmountPer01 = -1D;//意外伤害医疗费用(每人保险金额)
    
    /** 屬性The sinosoft Sub amount per02. */
    private double SubAmountPer02 = -1D;//意外伤害生活津贴(每日津贴金额)
    
    /** 屬性The sinosoft Sub amount per03. */
    private double SubAmountPer03 = -1D;//学生幼儿意外伤害医疗(每人保险金额)
    
    /** 屬性The sinosoft Sum amount per01. */
    private double SumAmountPer01 = -1D;//每人保险金额(高管) 
    
    /** 屬性The sinosoft Sum amount per02. */
    private double SumAmountPer02 = -1D;//每人保险金额(文职) 
    
    /** 屬性The sinosoft Sum amount per03. */
    private double SumAmountPer03 = -1D;//每人保险金额(其他) 
    
    /** 屬性The sinosoft Sum amount per04. */
    private double SumAmountPer04 = -1D;//每人保险金额(国内) 
    
    /** 屬性The sinosoft Sum amount per05. */
    private double SumAmountPer05 = -1D;//每人保险金额(出境) 
    
    /*---组合险特殊险种公路综合险高级条件因子---begin--*/
    /** 屬性The sinosoft sum amount2300200. */
    private double sumAmount2300200 = 0; 	// 公路财产保险保险金额
    
    /** 屬性The sinosoft sum amount2300400. */
    private double sumAmount2300400 = 0; 	// 公众责任保险保险金额	
    
    /** 屬性The sinosoft sum amount2300500. */
    private double sumAmount2300500 = 0; 	// 雇主责任保险保险金额
    
    /** 屬性The sinosoft sum amount2300600. */
    private double sumAmount2300600 = 0; 	// 现金保险金额    
    
    /** 屬性The sinosoft limit2300500. */
    private double limit2300500 = 0;		// 雇主责任保险每人每次事故赔偿限额	

    /*---add by gengxiaobo 20080326 组合险特殊险种起重机械综合保险高级条件因子---*/       
    /** 屬性The sinosoft sum amount2301500. */
    private double sumAmount2301500 = 0; 	// 财产损失保险保险金额
    
    /** 屬性The sinosoft sum amount2301600. */
    private double sumAmount2301600 = 0; 	// 第三者责任保险保险金额	
    
    /** 屬性The sinosoft sum amount2301800. */
    private double sumAmount2301800 = 0; 	// 雇主责任保险保险金额 
    
    //add by hanxiao 组合险2310
    /** 屬性The sinosoft sum amount0300100. */
    private double sumAmount0300100 = -1D;
    //2351 2352
    /** 屬性The sinosoft sum amount yl. */
    private double sumAmountYL = -1D; 		//23意外伤害医疗
    
    /** 屬性The sinosoft Amount per. */
    private double AmountPer = -1D;			//室内装潢/家用电器/衣物床上用品/家具及其他保险金额/每户
    
    /*车险可配置开始自动核保*/
    /** 屬性The sinosoft Amount count. */
    private double AmountCount = -1D; 		// 保险金额、责任限额
    
    /** 屬性The sinosoft Kind code. */
    private String KindCode = "";     		// 条款类别
    
    /** 屬性The sinosoft Use nature code. */
    private String UseNatureCode = "";		// 使用性质
    
    /** 屬性The sinosoft Car kind code. */
    private String CarKindCode = "";  		// 车辆类型
    
    /** 屬性The sinosoft Use years. */
    private double UseYears = -1D;   		// 车辆使用年限
    
    /** 屬性The sinosoft Only ab years. */
    private double OnlyABYears = -1D; 		//只投保三者险，车损险的营业用车
    
    /** 屬性The sinosoft Choose g1 years. */
    private double ChooseG1Years = -1;  	//投保盗抢险且选择不计免赔的车辆使用年限
    
    /** 屬性The sinosoft Choose l1 years. */
    private double ChooseL1Years = -1;  	//投保划痕险且选择不计免赔的车辆使用年限
    
    /** 屬性The sinosoft Choose lp a4 years. */
    private double ChooseLPA4Years = -1; 	//投保专修厂维修特约险或零配件更换险的车辆使用年限
    
    /** 屬性The sinosoft Choose n a0 years. */
    private double ChooseNA0Years = -1;		//非营业客车使用年限
    
    /** 屬性The sinosoft Choose n h0 years. */
    private double ChooseNH0Years = -1;		//非营业货车使用年限
    
    /** 屬性The sinosoft Choose y years. */
    private double ChooseYYears = -1;		//营业用车使用年限
    
    /** 屬性The sinosoft Choose j years. */
    private double ChooseJYears = -1;		//家庭自用汽车使用年限
    
    /** 屬性The sinosoft Choose e years. */
    private double ChooseEYears = -1;		//投保自燃险车龄年限
    
    /** 屬性The sinosoft Choose ej years. */
    private double ChooseEJYears = -1;		//投保自燃险的家用车车龄年限
    
    /** 屬性The sinosoft Choose a price. */
    private double ChooseAPrice = -1;		//投保车损险的新车购置价
    
    /** 屬性The sinosoft Use years l. */
    private double UseYearsL = -1D;		// 车身划痕车辆使用年限
    
    /** 屬性The sinosoft Car kind code l. */
    private String CarKindCodeL = "";	// 车身划痕车辆车辆类型
    
    /** 屬性The sinosoft Choose zf. */
    private String ChooseZF = "";  		// 投保指定附加险
    
    /** 屬性The sinosoft Choose td. */
    private String ChooseTD = "";		//投保特定条款
    
    /** 屬性The sinosoft Not choose b. */
    private String NotChooseB = "";		//未投保第三者责任险的投保单
    
    /** 屬性The sinosoft Seat count. */
    private double SeatCount= -1D;		// 核定座位
    
    /** 屬性The sinosoft Ton count. */
    private double TonCount = -1D;    	// 核定质量
    
    /** 屬性The sinosoft Ton count h0. */
    private double TonCountH0 = -1D;	//货车核定载质量
    
    /** 屬性The sinosoft amount l. */
    private double amountL = -1;     	//车身划痕保险金额
    
    /** 屬性The sinosoft amount r. */
    private double amountR = -1;		//交通事故精神损害赔偿责任险保额
    
    /** 屬性The sinosoft amount r per. */
    private double amountRPer = -1;		//交通事故精神损害赔偿责任险每人每次限额
    
    /** 屬性The sinosoft Amount h0 upp. */
    private double AmountH0Upp = -1; 	//n吨以上货车三者险保险金额
    
    /** 屬性The sinosoft Amount h0 low. */
    private double AmountH0Low = -1;	//n吨以下货车三者险保险金额
    
    /** 屬性The sinosoft Amount h0 upp a. */
    private double AmountH0UppA = -1; 	//n吨以上货车车损险保险金额
    
    /** 屬性The sinosoft Amount h0 low a. */
    private double AmountH0LowA = -1; 	//n吨以下货车车损险保险金额
    
    /** 屬性The sinosoft Amount nx. */
    private double AmountNX = -1;     	//新增设备保险金额
	
	/** 屬性The sinosoft Amount ny. */
	private double AmountNY = -1;     	//新增设备保险金额
	
	/** 屬性The sinosoft Kind code out of auto. */
	private String KindCodeOutOfAuto =  ""; 	//允许自动核保的险别
	
	/** 屬性The sinosoft Kind code out of auto one. */
	private String KindCodeOutOfAutoOne = "";	//单独投保不允许自动核保的险别
	
	/** 屬性The sinosoft License no in clude. */
	private String LicenseNoInClude = "";  		//车牌号
	
	/** 屬性The sinosoft Kind code out of special. */
	private String KindCodeOutOfSpecial = ""; 	//黑牌或白牌车辆投保的险种不允许自动核保
	
	/** 屬性The sinosoft Appli linker name. */
	private String AppliLinkerName ="";  		//被保险人姓名包含的字符
	
	/** 屬性The sinosoft CB year limit. */
	private int  CBYearLimit = -1;   			//新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制
	
	/** 屬性The sinosoft CB month limit. */
	private int CBMonthLimit = -1;   			//0501新增保险期限
    //特险核保部分
	
	/** 屬性The sinosoft Special power. */
    private String SpecialPower = "" ; 				//特险核保权限设置、目前只允许总公司核保
	
	/** 屬性The sinosoft undo contract date. */
	private double undoContractDate = -1 ;			//解除合同权限天数

    //added by LanNing begin 20080225 投资金产品调整
	/** 屬性The sinosoft Investment. */
    private double Investment = -1D;
    //added by LanNing end 20080225 投资金产品调整
	/** 屬性The sinosoft Mortgage. */
    private String Mortgage = "N";
	
	//added by LanNing begin 20080421 1505每次事故赔偿限额
	/** 屬性The sinosoft Limit02 fee1505. */
	private double Limit02Fee1505 = -1D;
	//added by LanNing end 20080421 1505每次事故赔偿限额
    
	//added by gengxiaobo begin 20080604 增加最大车累记赔偿限额,调整每次事故赔偿限额取值。
	/** 屬性The sinosoft dou staff count1505. */
	double douStaffCount1505 =  -1D;
	
	/** 屬性The sinosoft dou pre turn over1505. */
	double douPreTurnOver1505 =  -1D;
	
	/** 屬性The sinosoft Limit03 fee1505. */
	double Limit03Fee1505 =  -1D;
	
	/** 屬性The sinosoft max capacity1505. */
	double maxCapacity1505 =  -1D;
	//added by gengxiaobo begin 20080604 增加最大车累记赔偿限额,调整每次事故赔偿限额取值。
	//added by liuwei begin 20090303 0911国内货运险（08版）增加附加险核保因子
	/** 屬性The sinosoft Allow0911700. */
	private String Allow0911700 = "Y";
	//added by liuwei end
    /** 屬性The sinosoft Sum amount pn. */
	private double SumAmountPN = -1D; //yanglibo 20090429 占用性质：0014、0015;的保额因子
	   
     //added by liuwei begin 20090512 1598每次事故每车位责任限额
    /** 屬性The sinosoft Limit fee one car. */
     private  double LimitFeeOneCar = -1D;
	 //added by liuwei end
    //added by zhouhui begin 20090616 2710批改保险期限时，短期费率标志为3时，只能1c级以上才能核过
    /** 屬性The sinosoft Short rate flag. */
 	private  String ShortRateFlag = "N";
	 //added by zhouhui end
    //added by xiongguojun 20090814 货运险启运日期
    /** 屬性The sinosoft freight start date. */
 	private double freightStartDate = 0D;	//货运险启运日期
    
    /** 屬性The sinosoft Policy type0902. */
    private String PolicyType0902 = "Y";	//0902补录保单核保因子
    //如需增加请在此处分类整理，不要乱搞！

    //add by zhaoning20091125 begin Reason:增加总公司核保标志
    /** 屬性The sinosoft parent company check. */
    private String parentCompanyCheck = "N";	//总公司核保标志位
    //add by zhaoning20091125 end
    
    //add by zhaoning20100128 begin Reason:2010年非车险核保权限
    /** 屬性The sinosoft allow split danger unit. */
    private String allowSplitDangerUnit = "Y";	//是否允许划分风险单位
    
    /** 屬性The sinosoft sum amount0400600. */
    double sumAmount0400600 = -1D; 				// 财产损失保险保险金额
	
	/** 屬性The sinosoft sum amount0400700. */
	double sumAmount0400700 = -1D; 				// 还贷保证保险保险金额
	
	/** 屬性The sinosoft Limit man heal01. */
	double LimitManHeal01 = -1D;				//每人医疗费用赔偿限额
	
	/** 屬性The sinosoft cover note flag. */
	String coverNoteFlag = "Y";					//暂保单标志(是否有权限,N无权限 Y有权限)
    //add by zhaoning20100128 end
	
	//3001险种新增险别因子 begin
	/** 屬性The sinosoft Sum amount300101. */
    double SumAmount300101 = -1D;
	
	/** 屬性The sinosoft Sum amount300102. */
	double SumAmount300102 = -1D;
	
	/** 屬性The sinosoft Sum amount300103. */
	double SumAmount300103 = -1D;
	
	/** 屬性The sinosoft Sum amount300104. */
	double SumAmount300104 = -1D;
	
	/** 屬性The sinosoft Sum amount300105. */
	double SumAmount300105 = -1D;
	
	/** 屬性The sinosoft Sum amount300106. */
	double SumAmount300106 = -1D;
	//	3001险种新增险别因子 end
    
	/**
	 * 獲取屬性the sinosoft freight start date.
	 * 
	 * @return 屬性the sinosoft freight start date的值
	 */
	public double getFreightStartDate() {
		return freightStartDate;
	}
	
	/**
	 * 設置屬性the sinosoft freight start date.
	 * 
	 * @param freightStartDate
	 *            待設置的the sinosoft freight start date的值
	 */
	public void setFreightStartDate(double freightStartDate) {
		this.freightStartDate = freightStartDate;
	}
	
	/**
	 * 獲取屬性the sinosoft limit2300500.
	 * 
	 * @return the limit2300500
	 */
	public double getLimit2300500() {
		return limit2300500;
	}
	
	/**
	 * 設置屬性the sinosoft limit2300500.
	 * 
	 * @param limit2300500
	 *            the limit2300500 to set
	 */
	public void setLimit2300500(double limit2300500) {
		this.limit2300500 = limit2300500;
	}
	
	/**
	 * 獲取屬性the sinosoft ship age.
	 * 
	 * @return 屬性the sinosoft ship age的值
	 */
	public double getShipAge() {
		return ShipAge;
	}
	
	/**
	 * 設置屬性the sinosoft ship age.
	 * 
	 * @param shipAge
	 *            待設置的the sinosoft ship age的值
	 */
	public void setShipAge(double shipAge) {
		ShipAge = shipAge;
	}
	
	/**
	 * 獲取屬性the sinosoft limit man acc05.
	 * 
	 * @return 屬性the sinosoft limit man acc05的值
	 */
	public double getLimitManAcc05() {
		return limitManAcc05;
	}

	/**
	 * 獲取屬性the sinosoft sum amount2300200.
	 * 
	 * @return the sumAmount2300200
	 */
	public double getSumAmount2300200() {
		return sumAmount2300200;
	}
	
	/**
	 * 設置屬性the sinosoft sum amount2300200.
	 * 
	 * @param sumAmount2300200
	 *            the sumAmount2300200 to set
	 */
	public void setSumAmount2300200(double sumAmount2300200) {
		this.sumAmount2300200 = sumAmount2300200;
	}
	
	/**
	 * 獲取屬性the sinosoft sum amount2300400.
	 * 
	 * @return the sumAmount2300400
	 */
	public double getSumAmount2300400() {
		return sumAmount2300400;
	}
	
	/**
	 * 設置屬性the sinosoft sum amount2300400.
	 * 
	 * @param sumAmount2300400
	 *            the sumAmount2300400 to set
	 */
	public void setSumAmount2300400(double sumAmount2300400) {
		this.sumAmount2300400 = sumAmount2300400;
	}
	
	/**
	 * 獲取屬性the sinosoft sum amount2300500.
	 * 
	 * @return the sumAmount2300500
	 */
	public double getSumAmount2300500() {
		return sumAmount2300500;
	}
	
	/**
	 * 設置屬性the sinosoft sum amount2300500.
	 * 
	 * @param sumAmount2300500
	 *            the sumAmount2300500 to set
	 */
	public void setSumAmount2300500(double sumAmount2300500) {
		this.sumAmount2300500 = sumAmount2300500;
	}
	
	/**
	 * 獲取屬性the sinosoft sum amount2300600.
	 * 
	 * @return the sumAmount2300600
	 */
	public double getSumAmount2300600() {
		return sumAmount2300600;
	}
	
	/**
	 * 設置屬性the sinosoft sum amount2300600.
	 * 
	 * @param sumAmount2300600
	 *            the sumAmount2300600 to set
	 */
	public void setSumAmount2300600(double sumAmount2300600) {
		this.sumAmount2300600 = sumAmount2300600;
	}
	
	/**
	 * 獲取屬性the sinosoft sum amount2301500.
	 * 
	 * @return 屬性the sinosoft sum amount2301500的值
	 */
	public double getSumAmount2301500() {
		return sumAmount2301500;
	}
	
	/**
	 * 設置屬性the sinosoft sum amount2301500.
	 * 
	 * @param sumAmount2301500
	 *            待設置的the sinosoft sum amount2301500的值
	 */
	public void setSumAmount2301500(double sumAmount2301500) {
		this.sumAmount2301500 = sumAmount2301500;
	}
	
	/**
	 * 獲取屬性the sinosoft sum amount2301600.
	 * 
	 * @return 屬性the sinosoft sum amount2301600的值
	 */
	public double getSumAmount2301600() {
		return sumAmount2301600;
	}
	
	/**
	 * 設置屬性the sinosoft sum amount2301600.
	 * 
	 * @param sumAmount2301600
	 *            待設置的the sinosoft sum amount2301600的值
	 */
	public void setSumAmount2301600(double sumAmount2301600) {
		this.sumAmount2301600 = sumAmount2301600;
	}
	
	/**
	 * 獲取屬性the sinosoft sum amount2301800.
	 * 
	 * @return 屬性the sinosoft sum amount2301800的值
	 */
	public double getSumAmount2301800() {
		return sumAmount2301800;
	}
	
	/**
	 * 設置屬性the sinosoft sum amount2301800.
	 * 
	 * @param sumAmount2301800
	 *            待設置的the sinosoft sum amount2301800的值
	 */
	public void setSumAmount2301800(double sumAmount2301800) {
		this.sumAmount2301800 = sumAmount2301800;
	}	
	
	/**
	 * 設置屬性the sinosoft limit man acc05.
	 * 
	 * @param limitManAcc05
	 *            待設置的the sinosoft limit man acc05的值
	 */
	public void setLimitManAcc05(double limitManAcc05) {
		this.limitManAcc05 = limitManAcc05;
	}

	/**
	 * 獲取屬性the sinosoft limit man acc01.
	 * 
	 * @return 屬性the sinosoft limit man acc01的值
	 */
	public double getLimitManAcc01() {
		return LimitManAcc01;
	}
	
	/**
	 * 設置屬性the sinosoft limit man acc01.
	 * 
	 * @param limitManAcc01
	 *            待設置的the sinosoft limit man acc01的值
	 */
	public void setLimitManAcc01(double limitManAcc01) {
		LimitManAcc01 = limitManAcc01;
	}
	
	/**
	 * 獲取屬性the sinosoft prepay protocol.
	 * 
	 * @return 屬性the sinosoft prepay protocol的值
	 */
	public String getPrepayProtocol() {
		return PrepayProtocol;
	}
	
	/**
	 * 設置屬性the sinosoft prepay protocol.
	 * 
	 * @param prepayProtocol
	 *            待設置的the sinosoft prepay protocol的值
	 */
	public void setPrepayProtocol(String prepayProtocol) {
		PrepayProtocol = prepayProtocol;
	}
	
	/**
	 * 獲取屬性the sinosoft plus rate.
	 * 
	 * @return 屬性the sinosoft plus rate的值
	 */
	public double getPlusRate() {
		return PlusRate;
	}
	
	/**
	 * 設置屬性the sinosoft plus rate.
	 * 
	 * @param plusRate
	 *            待設置的the sinosoft plus rate的值
	 */
	public void setPlusRate(double plusRate) {
		PlusRate = plusRate;
	}
	
	/**
	 * 默认构造方法,构造一个默认的HeBaoConditionDtoBase对象.
	 */
    public HeBaoConditionVo(){
    }
    
    /**
	 * ******公共部分双核高级因子的方法*******begin****.
	 * 
	 * @param Currency
	 *            待設置的the sinosoft currency的值
	 */
    /**
     * 设置属性币种
     * @param currency 待设置的属性币种的值
     
    public void setCurrency(String currency){
        this.currency = StringUtils.rightTrim(currency);
    }  
    */  
    /** 设置属性币种
     * @param currency 待设置的属性币种的值
     * */
    public void setCurrency(String Currency){
        this.Currency = Currency;
    }
    
    /**
	 * 获取属性币种.
	 * 
	 * @return 属性币种的值
	 */
    public String getCurrency(){
        return Currency;
    }
    
    /**
	 * 设置属性净费比例.
	 * 
	 * @param OutLayRate
	 *            待設置的the sinosoft out lay rate的值
	 */
    public void setOutLayRate(double OutLayRate){
        this.OutLayRate = OutLayRate;
    }

    /**
	 * 获取属性净费比例.
	 * 
	 * @return 属性净费比例
	 */
    public double getOutLayRate(){
        return OutLayRate;
    }
    
    /**
	 * 设置属性直接倒签单天数.
	 * 
	 * @param DirectDay
	 *            待設置的the sinosoft direct day的值
	 */
    public void setDirectDay(double DirectDay){
        this.DirectDay = DirectDay;
    }

    /**
	 * 获取属性直接倒签单天数.
	 * 
	 * @return 属性直接倒签单天数
	 */
    public double getDirectDay(){
        return DirectDay;
    }
    
    /**
	 * 设置属性代理倒签单天数.
	 * 
	 * @param AgentDay
	 *            待設置的the sinosoft agent day的值
	 */
    public void setAgentDay(double AgentDay){
        this.AgentDay = AgentDay;
    }

    /**
	 * 获取属性代理倒签单天数.
	 * 
	 * @return 属性代理倒签单天数
	 */
    public double getAgentDay(){
        return AgentDay;
    }
    
    /**
	 * 设置属性中间成本.
	 * 
	 * @param MidCost
	 *            待設置的the sinosoft mid cost的值
	 */
    public void setMidCost(double MidCost){
        this.MidCost = MidCost;
    }

    /**
	 * 获取属性中间成本.
	 * 
	 * @return 属性中间成本
	 */
    public double getMidCost(){
        return MidCost;
    }
    
    /**
	 * 设置属性手续费比例.
	 * 
	 * @param WorkFeeProportion
	 *            待設置的the sinosoft work fee proportion的值
	 */
    public void setWorkFeeProportion(double WorkFeeProportion){
        this.WorkFeeProportion = WorkFeeProportion;
    }

    /**
	 * 获取属性手续费比例.
	 * 
	 * @return 属性手续费比例
	 */
    public double getWorkFeeProportion(){
        return WorkFeeProportion;
    }
    
    /**
	 * 设置属性允许保单注销天数.
	 * 
	 * @param WriteOffDays
	 *            待設置的the sinosoft write off days的值
	 */
    public void setWriteOffDays(double WriteOffDays){
        this.WriteOffDays = WriteOffDays;
    }

    /**
	 * 获取属性允许保单注销天数.
	 * 
	 * @return 属性允许保单注销天数
	 */
    public double getWriteOffDays(){
        return WriteOffDays;
    }
    
    /**
	 * 设置属性交费宽限期天数.
	 * 
	 * @param GracePeriodDays
	 *            待設置的the sinosoft grace period days的值
	 */
    public void setGracePeriodDays(double GracePeriodDays){
        this.GracePeriodDays = GracePeriodDays;
    }

    /**
	 * 获取属性交费宽限期天数.
	 * 
	 * @return 属性交费宽限期天数
	 */
    public double getGracePeriodDays(){
        return GracePeriodDays;
    }
    
    /**
	 * 设置属性RabateRate.
	 * 
	 * @param RabateRate
	 *            待設置的the sinosoft rabate rate的值
	 */
    public void setRabateRate(double  RabateRate){
        this.RabateRate = RabateRate;
    }

    /**
	 * 获取属性RabateRate.
	 * 
	 * @return 属性RabateRate
	 */
    public double getRabateRate(){
        return RabateRate;
    }
    
    /**
	 * *****车险的高级核保因子***********************.
	 * 
	 * @param PermitBidding
	 *            待設置的the sinosoft permit bidding的值
	 */
    /**
     * 设置属性是否允许招标
     * @param directDay 待设置的属性是否允许招标的值
     */
    public void setPermitBidding(String PermitBidding){
        this.PermitBidding = PermitBidding;
    }

    /**
	 * 获取属性是否允许招标.
	 * 
	 * @return 属性是否允许招标
	 */
    public String getPermitBidding(){
        return PermitBidding;
    }
    
    /**
	 * 设置属性招标系数下浮比例.
	 * 
	 * @param BiddingDownProportion
	 *            待設置的the sinosoft bidding down proportion的值
	 */
    public void setBiddingDownProportion(double  BiddingDownProportion){
        this.BiddingDownProportion = BiddingDownProportion;
    }

    /**
	 * 获取属性招标系数下浮比例.
	 * 
	 * @return 属性招标系数下浮比例
	 */
    public double getBiddingDownProportion(){
        return BiddingDownProportion;
    }
    
    /**
	 * 设置属性客户类型.
	 * 
	 * @param ClientProperty
	 *            待設置的the sinosoft client property的值
	 */
    public void setClientProperty(String  ClientProperty){
        this.ClientProperty = ClientProperty;
    }

    /**
	 * 获取属性客户类型.
	 * 
	 * @return 属性客户类型
	 */
    public String getClientProperty(){
        return ClientProperty;
    }
    
    /**
	 * 设置属性使用性质.
	 * 
	 * @param UsingProperty
	 *            待設置的the sinosoft using property的值
	 */
    public void setUsingProperty(String  UsingProperty){
        this.UsingProperty = UsingProperty;
    }

    /**
	 * 获取属性使用性质.
	 * 
	 * @return 属性使用性质
	 */
    public String getUsingProperty(){
        return UsingProperty;
    }
    
    /**
	 * 设置属性车型.
	 * 
	 * @param CarType
	 *            待設置的the sinosoft car type的值
	 */
    public void setCarType(String  CarType){
        this.CarType = CarType;
    }

    /**
	 * 获取属性车型.
	 * 
	 * @return 属性车型
	 */
    public String getCarType(){
        return CarType;
    }
    
    /**
	 * 设置属性车辆使用年限.
	 * 
	 * @param UsingYearLimit
	 *            待設置的the sinosoft using year limit的值
	 */
    public void setUsingYearLimit(double  UsingYearLimit){
        this.UsingYearLimit = UsingYearLimit;
    }

    /**
	 * 获取属性车辆使用年限.
	 * 
	 * @return 属性车辆使用年限
	 */
    public double getUsingYearLimit(){
        return UsingYearLimit;
    }
    
    /**
	 * 设置属性团车数量.
	 * 
	 * @param GroupCarSum
	 *            待設置的the sinosoft group car sum的值
	 */
   public void setGroupCarSum(double  GroupCarSum){
       this.GroupCarSum = GroupCarSum;
   }

   /**
	 * 获取属性团车数量.
	 * 
	 * @return 属性团车数量
	 */
   public double getGroupCarSum(){
       return GroupCarSum;
   }
   
   /**
	 * 设置属性车损险保额.
	 * 
	 * @param AmountA
	 *            待設置的the sinosoft amount a的值
	 */
   public void setAmountA(double  AmountA){
       this.AmountA = AmountA;
   }

   /**
	 * 获取属性车损险保额.
	 * 
	 * @return 属性车损险保额
	 */
   public double getAmountA(){
       return AmountA;
   }
   
   /**
	 * 设置属性三者险保额.
	 * 
	 * @param AmountB
	 *            待設置的the sinosoft amount b的值
	 */
   public void setAmountB(double  AmountB){
       this.AmountB = AmountB;
   }

   /**
	 * 获取属性三者险保额.
	 * 
	 * @return 属性三者险保额
	 */
   public double getAmountB(){
       return AmountB;
   }
   
   /**
	 * 设置属性盗抢险保额.
	 * 
	 * @param AmountG
	 *            待設置的the sinosoft amount g的值
	 */
   public void setAmountG(double  AmountG){
       this.AmountG = AmountG;
   }

   /**
	 * 获取属性盗抢险保额.
	 * 
	 * @return 属性盗抢险保额
	 */
   public double getAmountG(){
       return AmountG;
   }
   
   /**
	 * 设置属性车险保额.
	 * 
	 * @param AmountD1
	 *            待設置的the sinosoft amount d1的值
	 */
   public void setAmountD1(double  AmountD1){
       this.AmountD1 = AmountD1;
   }
   
   /**
	 * 获取属性车上险保额.
	 * 
	 * @return 属性车上险保额
	 */
   public double getAmountD1(){
       return AmountD1;
   }
   
   /**
	 * 设置属性车上货物责任险保额.
	 * 
	 * @param AmountD2
	 *            待設置的the sinosoft amount d2的值
	 */
   public void setAmountD2(double  AmountD2){
       this.AmountD2 = AmountD2;
   }

   /**
	 * 获取属性车上货物责任险保额.
	 * 
	 * @return 属性车上货物责任险保额
	 */
   public double getAmountD2(){
       return AmountD2;

   }

/**
 * 獲取屬性the sinosoft allow split.
 * 
 * @return 屬性the sinosoft allow split的值
 */
public String getAllowSplit() {
	return AllowSplit;
}

/**
 * 設置屬性the sinosoft allow split.
 * 
 * @param allowSplit
 *            待設置的the sinosoft allow split的值
 */
public void setAllowSplit(String allowSplit) {
	AllowSplit = allowSplit;
}

/**
 * 獲取屬性the sinosoft amount per03010001.
 * 
 * @return 屬性the sinosoft amount per03010001的值
 */
public double getAmountPer03010001() {
	return AmountPer03010001;
}

/**
 * 設置屬性the sinosoft amount per03010001.
 * 
 * @param AmountPer03010001
 *            待設置的the sinosoft amount per03010001的值
 */
public void setAmountPer03010001(double AmountPer03010001) {
	this.AmountPer03010001 = AmountPer03010001;
}

/**
 * 獲取屬性the sinosoft amout per03010002.
 * 
 * @return 屬性the sinosoft amout per03010002的值
 */
public double getAmoutPer03010002() {
	return AmountPer03010002;
}

/**
 * 設置屬性the sinosoft amount per03010002.
 * 
 * @param AmountPer03010002
 *            待設置的the sinosoft amount per03010002的值
 */
public void setAmountPer03010002(double AmountPer03010002) {
	this.AmountPer03010002 = AmountPer03010002;
}

/**
 * 獲取屬性the sinosoft trial amount.
 * 
 * @return 屬性the sinosoft trial amount的值
 */
public double getTrialAmount() {
	return TrialAmount;
}

/**
 * 設置屬性the sinosoft trial amount.
 * 
 * @param trialAmount
 *            待設置的the sinosoft trial amount的值
 */
public void setTrialAmount(double trialAmount) {
	this.TrialAmount = trialAmount;
}

/**
 * 獲取屬性the sinosoft trial premium.
 * 
 * @return 屬性the sinosoft trial premium的值
 */
public double getTrialPremium() {
	return TrialPremium;
}

/**
 * 設置屬性the sinosoft trial premium.
 * 
 * @param trialPremium
 *            待設置的the sinosoft trial premium的值
 */
public void setTrialPremium(double trialPremium) {
	TrialPremium = trialPremium;
}

/**
 * 獲取屬性the sinosoft sum amount.
 * 
 * @return 屬性the sinosoft sum amount的值
 */
public double getSumAmount() {
	return SumAmount;
}

/**
 * 設置屬性the sinosoft sum amount.
 * 
 * @param sumAmount
 *            待設置的the sinosoft sum amount的值
 */
public void setSumAmount(double sumAmount) {
	SumAmount = sumAmount;
}

/**
 * 獲取屬性the sinosoft sum amount s.
 * 
 * @return 屬性the sinosoft sum amount s的值
 */
public double getSumAmountS() {
	return SumAmountS;
}

/**
 * 設置屬性the sinosoft sum amount s.
 * 
 * @param sumAmountS
 *            待設置的the sinosoft sum amount s的值
 */
public void setSumAmountS(double sumAmountS) {
	SumAmountS = sumAmountS;
}

/**
 * 獲取屬性the sinosoft third limit acc07.
 * 
 * @return 屬性the sinosoft third limit acc07的值
 */
public double getThirdLimitAcc07() {
	return ThirdLimitAcc07;
}

/**
 * 設置屬性the sinosoft third limit acc07.
 * 
 * @param thirdLimitAcc07
 *            待設置的the sinosoft third limit acc07的值
 */
public void setThirdLimitAcc07(double thirdLimitAcc07) {
	ThirdLimitAcc07 = thirdLimitAcc07;
}

/**
 * 獲取屬性the sinosoft third limit sum07.
 * 
 * @return 屬性the sinosoft third limit sum07的值
 */
public double getThirdLimitSum07() {
	return ThirdLimitSum07;
}

/**
 * 設置屬性the sinosoft third limit sum07.
 * 
 * @param thirdLimitSum07
 *            待設置的the sinosoft third limit sum07的值
 */
public void setThirdLimitSum07(double thirdLimitSum07) {
	ThirdLimitSum07 = thirdLimitSum07;
}

/**
 * 獲取屬性the sinosoft amount per03010002.
 * 
 * @return 屬性the sinosoft amount per03010002的值
 */
public double getAmountPer03010002() {
	return AmountPer03010002;
}

/**
 * 獲取屬性the sinosoft unit insure.
 * 
 * @return 屬性the sinosoft unit insure的值
 */
public String getUnitInsure() {
	return UnitInsure;
}

/**
 * 設置屬性the sinosoft unit insure.
 * 
 * @param unitInsure
 *            待設置的the sinosoft unit insure的值
 */
public void setUnitInsure(String unitInsure) {
	UnitInsure = unitInsure;
}

/**
 * 獲取屬性the sinosoft sum amount c.
 * 
 * @return 屬性the sinosoft sum amount c的值
 */
public double getSumAmountC() {
	return SumAmountC;
}

/**
 * 設置屬性the sinosoft sum amount c.
 * 
 * @param sumAmountC
 *            待設置的the sinosoft sum amount c的值
 */
public void setSumAmountC(double sumAmountC) {
	SumAmountC = sumAmountC;
}

/**
 * 獲取屬性the sinosoft sum amount u.
 * 
 * @return 屬性the sinosoft sum amount u的值
 */
public double getSumAmountU() {
	return SumAmountU;
}

/**
 * 設置屬性the sinosoft sum amount u.
 * 
 * @param sumAmountU
 *            待設置的the sinosoft sum amount u的值
 */
public void setSumAmountU(double sumAmountU) {
	SumAmountU = sumAmountU;
}

/**
 * 獲取屬性the sinosoft unit proportion.
 * 
 * @return 屬性the sinosoft unit proportion的值
 */
public double getUnitProportion() {
	return UnitProportion;
}

/**
 * 設置屬性the sinosoft unit proportion.
 * 
 * @param unitProportion
 *            待設置的the sinosoft unit proportion的值
 */
public void setUnitProportion(double unitProportion) {
	UnitProportion = unitProportion;
}

/**
 * 獲取屬性the sinosoft sum amount per1.
 * 
 * @return 屬性the sinosoft sum amount per1的值
 */
public double getSumAmountPer1() {
	return SumAmountPer1;
}

/**
 * 設置屬性the sinosoft sum amount per1.
 * 
 * @param sumAmountPer1
 *            待設置的the sinosoft sum amount per1的值
 */
public void setSumAmountPer1(double sumAmountPer1) {
	SumAmountPer1 = sumAmountPer1;
}

/**
 * 獲取屬性the sinosoft sum amount per2.
 * 
 * @return 屬性the sinosoft sum amount per2的值
 */
public double getSumAmountPer2() {
	return SumAmountPer2;
}

/**
 * 設置屬性the sinosoft sum amount per2.
 * 
 * @param sumAmountPer2
 *            待設置的the sinosoft sum amount per2的值
 */
public void setSumAmountPer2(double sumAmountPer2) {
	SumAmountPer2 = sumAmountPer2;
}

/**
 * 獲取屬性the sinosoft sum amount per3.
 * 
 * @return 屬性the sinosoft sum amount per3的值
 */
public double getSumAmountPer3() {
	return SumAmountPer3;
}

/**
 * 設置屬性the sinosoft sum amount per3.
 * 
 * @param sumAmountPer3
 *            待設置的the sinosoft sum amount per3的值
 */
public void setSumAmountPer3(double sumAmountPer3) {
	SumAmountPer3 = sumAmountPer3;
}

/**
 * 獲取屬性the sinosoft sum amount per4.
 * 
 * @return 屬性the sinosoft sum amount per4的值
 */
public double getSumAmountPer4() {
	return SumAmountPer4;
}

/**
 * 設置屬性the sinosoft sum amount per4.
 * 
 * @param sumAmountPer4
 *            待設置的the sinosoft sum amount per4的值
 */
public void setSumAmountPer4(double sumAmountPer4) {
	SumAmountPer4 = sumAmountPer4;
}

/**
 * 獲取屬性the sinosoft sum amount per5.
 * 
 * @return 屬性the sinosoft sum amount per5的值
 */
public double getSumAmountPer5() {
	return SumAmountPer5;
}

/**
 * 設置屬性the sinosoft sum amount per5.
 * 
 * @param sumAmountPer5
 *            待設置的the sinosoft sum amount per5的值
 */
public void setSumAmountPer5(double sumAmountPer5) {
	SumAmountPer5 = sumAmountPer5;
}

/**
 * 獲取屬性the sinosoft sum amount per6.
 * 
 * @return 屬性the sinosoft sum amount per6的值
 */
public double getSumAmountPer6() {
	return SumAmountPer6;
}

/**
 * 設置屬性the sinosoft sum amount per6.
 * 
 * @param sumAmountPer6
 *            待設置的the sinosoft sum amount per6的值
 */
public void setSumAmountPer6(double sumAmountPer6) {
	SumAmountPer6 = sumAmountPer6;
}

/**
 * 獲取屬性the sinosoft sum amount per.
 * 
 * @return 屬性the sinosoft sum amount per的值
 */
public double getSumAmountPer() {
	return SumAmountPer;
}

/**
 * 設置屬性the sinosoft sum amount per.
 * 
 * @param sumAmountPer
 *            待設置的the sinosoft sum amount per的值
 */
public void setSumAmountPer(double sumAmountPer) {
	SumAmountPer = sumAmountPer;
}

/**
 * 獲取屬性the sinosoft serial version uid.
 * 
 * @return 屬性the sinosoft serial version uid的值
 */
public static long getSerialVersionUID() {
	return serialVersionUID;
}

/**
 * 獲取屬性the sinosoft sub amount per01.
 * 
 * @return 屬性the sinosoft sub amount per01的值
 */
public double getSubAmountPer01() {
	return SubAmountPer01;
}

/**
 * 設置屬性the sinosoft sub amount per01.
 * 
 * @param subAmountPer01
 *            待設置的the sinosoft sub amount per01的值
 */
public void setSubAmountPer01(double subAmountPer01) {
	SubAmountPer01 = subAmountPer01;
}

/**
 * 獲取屬性the sinosoft sub amount per02.
 * 
 * @return 屬性the sinosoft sub amount per02的值
 */
public double getSubAmountPer02() {
	return SubAmountPer02;
}

/**
 * 設置屬性the sinosoft sub amount per02.
 * 
 * @param subAmountPer02
 *            待設置的the sinosoft sub amount per02的值
 */
public void setSubAmountPer02(double subAmountPer02) {
	SubAmountPer02 = subAmountPer02;
}

/**
 * 獲取屬性the sinosoft sub amount per03.
 * 
 * @return 屬性the sinosoft sub amount per03的值
 */
public double getSubAmountPer03() {
	return SubAmountPer03;
}

/**
 * 設置屬性the sinosoft sub amount per03.
 * 
 * @param subAmountPer03
 *            待設置的the sinosoft sub amount per03的值
 */
public void setSubAmountPer03(double subAmountPer03) {
	SubAmountPer03 = subAmountPer03;
}

/**
 * 獲取屬性the sinosoft sum amount per01.
 * 
 * @return 屬性the sinosoft sum amount per01的值
 */
public double getSumAmountPer01() {
	return SumAmountPer01;
}

/**
 * 設置屬性the sinosoft sum amount per01.
 * 
 * @param sumAmountPer01
 *            待設置的the sinosoft sum amount per01的值
 */
public void setSumAmountPer01(double sumAmountPer01) {
	SumAmountPer01 = sumAmountPer01;
}

/**
 * 獲取屬性the sinosoft sum amount per02.
 * 
 * @return 屬性the sinosoft sum amount per02的值
 */
public double getSumAmountPer02() {
	return SumAmountPer02;
}

/**
 * 設置屬性the sinosoft sum amount per02.
 * 
 * @param sumAmountPer02
 *            待設置的the sinosoft sum amount per02的值
 */
public void setSumAmountPer02(double sumAmountPer02) {
	SumAmountPer02 = sumAmountPer02;
}

/**
 * 獲取屬性the sinosoft sum amount per03.
 * 
 * @return 屬性the sinosoft sum amount per03的值
 */
public double getSumAmountPer03() {
	return SumAmountPer03;
}

/**
 * 設置屬性the sinosoft sum amount per03.
 * 
 * @param sumAmountPer03
 *            待設置的the sinosoft sum amount per03的值
 */
public void setSumAmountPer03(double sumAmountPer03) {
	SumAmountPer03 = sumAmountPer03;
}

/**
 * 獲取屬性the sinosoft sum amount per04.
 * 
 * @return 屬性the sinosoft sum amount per04的值
 */
public double getSumAmountPer04() {
	return SumAmountPer04;
}

/**
 * 設置屬性the sinosoft sum amount per04.
 * 
 * @param sumAmountPer04
 *            待設置的the sinosoft sum amount per04的值
 */
public void setSumAmountPer04(double sumAmountPer04) {
	SumAmountPer04 = sumAmountPer04;
}

/**
 * 獲取屬性the sinosoft sum amount per05.
 * 
 * @return 屬性the sinosoft sum amount per05的值
 */
public double getSumAmountPer05() {
	return SumAmountPer05;
}

/**
 * 設置屬性the sinosoft sum amount per05.
 * 
 * @param sumAmountPer05
 *            待設置的the sinosoft sum amount per05的值
 */
public void setSumAmountPer05(double sumAmountPer05) {
	SumAmountPer05 = sumAmountPer05;
}

/**
 * 獲取屬性the sinosoft limit amount02.
 * 
 * @return 屬性the sinosoft limit amount02的值
 */
public double getLimitAmount02() {
	return LimitAmount02;
}

/**
 * 設置屬性the sinosoft limit amount02.
 * 
 * @param limitAmount02
 *            待設置的the sinosoft limit amount02的值
 */
public void setLimitAmount02(double limitAmount02) {
	LimitAmount02 = limitAmount02;
}

/**
 * 獲取屬性the sinosoft limit amount03.
 * 
 * @return 屬性the sinosoft limit amount03的值
 */
public double getLimitAmount03() {
	return LimitAmount03;
}

/**
 * 設置屬性the sinosoft limit amount03.
 * 
 * @param limitAmount03
 *            待設置的the sinosoft limit amount03的值
 */
public void setLimitAmount03(double limitAmount03) {
	LimitAmount03 = limitAmount03;
}

/**
 * 獲取屬性the sinosoft limit amount46.
 * 
 * @return 屬性the sinosoft limit amount46的值
 */
public double getLimitAmount46() {
	return LimitAmount46;
}

/**
 * 設置屬性the sinosoft limit amount46.
 * 
 * @param limitAmount46
 *            待設置的the sinosoft limit amount46的值
 */
public void setLimitAmount46(double limitAmount46) {
	LimitAmount46 = limitAmount46;
}

/**
 * 獲取屬性the sinosoft limit amount51.
 * 
 * @return 屬性the sinosoft limit amount51的值
 */
public double getLimitAmount51() {
	return LimitAmount51;
}

/**
 * 設置屬性the sinosoft limit amount51.
 * 
 * @param limitAmount51
 *            待設置的the sinosoft limit amount51的值
 */
public void setLimitAmount51(double limitAmount51) {
	LimitAmount51 = limitAmount51;
}

/**
 * 獲取屬性the sinosoft limit amount66.
 * 
 * @return 屬性the sinosoft limit amount66的值
 */
public double getLimitAmount66() {
	return LimitAmount66;
}

/**
 * 設置屬性the sinosoft limit amount66.
 * 
 * @param limitAmount66
 *            待設置的the sinosoft limit amount66的值
 */
public void setLimitAmount66(double limitAmount66) {
	LimitAmount66 = limitAmount66;
}

/**
 * 獲取屬性the sinosoft manage fee proportion.
 * 
 * @return 屬性the sinosoft manage fee proportion的值
 */
public double getManageFeeProportion() {
	return ManageFeeProportion;
}

/**
 * 設置屬性the sinosoft manage fee proportion.
 * 
 * @param manageFeeProportion
 *            待設置的the sinosoft manage fee proportion的值
 */
public void setManageFeeProportion(double manageFeeProportion) {
	ManageFeeProportion = manageFeeProportion;
}

/**
 * 獲取屬性the sinosoft limit cargo acc.
 * 
 * @return 屬性the sinosoft limit cargo acc的值
 */
public double getLimitCargoAcc() {
	return LimitCargoAcc;
}

/**
 * 設置屬性the sinosoft limit cargo acc.
 * 
 * @param limitCargoAcc
 *            待設置的the sinosoft limit cargo acc的值
 */
public void setLimitCargoAcc(double limitCargoAcc) {
	LimitCargoAcc = limitCargoAcc;
}

/**
 * 獲取屬性the sinosoft limit third acc.
 * 
 * @return 屬性the sinosoft limit third acc的值
 */
public double getLimitThirdAcc() {
	return LimitThirdAcc;
}

/**
 * 設置屬性the sinosoft limit third acc.
 * 
 * @param limitThirdAcc
 *            待設置的the sinosoft limit third acc的值
 */
public void setLimitThirdAcc(double limitThirdAcc) {
	LimitThirdAcc = limitThirdAcc;
}

/**
 * 獲取屬性the sinosoft limit third acc2.
 * 
 * @return 屬性the sinosoft limit third acc2的值
 */
public double getLimitThirdAcc2() {
	return LimitThirdAcc2;
}

/**
 * 設置屬性the sinosoft limit third acc2.
 * 
 * @param limitThirdAcc2
 *            待設置的the sinosoft limit third acc2的值
 */
public void setLimitThirdAcc2(double limitThirdAcc2) {
	LimitThirdAcc2 = limitThirdAcc2;
}

/**
 * 獲取屬性the sinosoft limit third acc b.
 * 
 * @return 屬性the sinosoft limit third acc b的值
 */
public double getLimitThirdAccB() {
	return LimitThirdAccB;
}

/**
 * 設置屬性the sinosoft limit third acc b.
 * 
 * @param limitThirdAccB
 *            待設置的the sinosoft limit third acc b的值
 */
public void setLimitThirdAccB(double limitThirdAccB) {
	LimitThirdAccB = limitThirdAccB;
}

/**
 * 獲取屬性the sinosoft limit acc1.
 * 
 * @return 屬性the sinosoft limit acc1的值
 */
public double getLimitAcc1() {
	return LimitAcc1;
}

/**
 * 設置屬性the sinosoft limit acc1.
 * 
 * @param limitAcc1
 *            待設置的the sinosoft limit acc1的值
 */
public void setLimitAcc1(double limitAcc1) {
	LimitAcc1 = limitAcc1;
}

/**
 * 獲取屬性the sinosoft limit acc2.
 * 
 * @return 屬性the sinosoft limit acc2的值
 */
public double getLimitAcc2() {
	return LimitAcc2;
}

/**
 * 設置屬性the sinosoft limit acc2.
 * 
 * @param limitAcc2
 *            待設置的the sinosoft limit acc2的值
 */
public void setLimitAcc2(double limitAcc2) {
	LimitAcc2 = limitAcc2;
}

/**
 * 獲取屬性the sinosoft limit acc3.
 * 
 * @return 屬性the sinosoft limit acc3的值
 */
public double getLimitAcc3() {
	return LimitAcc3;
}

/**
 * 設置屬性the sinosoft limit acc3.
 * 
 * @param limitAcc3
 *            待設置的the sinosoft limit acc3的值
 */
public void setLimitAcc3(double limitAcc3) {
	LimitAcc3 = limitAcc3;
}

/**
 * 獲取屬性the sinosoft sum amount1.
 * 
 * @return 屬性the sinosoft sum amount1的值
 */
public double getSumAmount1() {
	return SumAmount1;
}

/**
 * 設置屬性the sinosoft sum amount1.
 * 
 * @param sumAmount1
 *            待設置的the sinosoft sum amount1的值
 */
public void setSumAmount1(double sumAmount1) {
	SumAmount1 = sumAmount1;
}

/**
 * 獲取屬性the sinosoft sum amount2.
 * 
 * @return 屬性the sinosoft sum amount2的值
 */
public double getSumAmount2() {
	return SumAmount2;
}

/**
 * 設置屬性the sinosoft sum amount2.
 * 
 * @param sumAmount2
 *            待設置的the sinosoft sum amount2的值
 */
public void setSumAmount2(double sumAmount2) {
	SumAmount2 = sumAmount2;
}

/**
 * 獲取屬性the sinosoft sum amount3.
 * 
 * @return 屬性the sinosoft sum amount3的值
 */
public double getSumAmount3() {
	return SumAmount3;
}

/**
 * 設置屬性the sinosoft sum amount3.
 * 
 * @param sumAmount3
 *            待設置的the sinosoft sum amount3的值
 */
public void setSumAmount3(double sumAmount3) {
	SumAmount3 = sumAmount3;
}

/**
 * 獲取屬性the sinosoft limit man acc11.
 * 
 * @return 屬性the sinosoft limit man acc11的值
 */
public double getLimitManAcc11() {
	return LimitManAcc11;
}

/**
 * 設置屬性the sinosoft limit man acc11.
 * 
 * @param limitManAcc11
 *            待設置的the sinosoft limit man acc11的值
 */
public void setLimitManAcc11(double limitManAcc11) {
	LimitManAcc11 = limitManAcc11;
}

/**
 * 獲取屬性the sinosoft limit man acc12.
 * 
 * @return 屬性the sinosoft limit man acc12的值
 */
public double getLimitManAcc12() {
	return LimitManAcc12;
}

/**
 * 設置屬性the sinosoft limit man acc12.
 * 
 * @param limitManAcc12
 *            待設置的the sinosoft limit man acc12的值
 */
public void setLimitManAcc12(double limitManAcc12) {
	LimitManAcc12 = limitManAcc12;
}

/**
 * 獲取屬性the sinosoft limit man acc13.
 * 
 * @return 屬性the sinosoft limit man acc13的值
 */
public double getLimitManAcc13() {
	return LimitManAcc13;
}

/**
 * 設置屬性the sinosoft limit man acc13.
 * 
 * @param limitManAcc13
 *            待設置的the sinosoft limit man acc13的值
 */
public void setLimitManAcc13(double limitManAcc13) {
	LimitManAcc13 = limitManAcc13;
}

/**
 * 獲取屬性the sinosoft limit man acc14.
 * 
 * @return 屬性the sinosoft limit man acc14的值
 */
public double getLimitManAcc14() {
	return LimitManAcc14;
}

/**
 * 設置屬性the sinosoft limit man acc14.
 * 
 * @param limitManAcc14
 *            待設置的the sinosoft limit man acc14的值
 */
public void setLimitManAcc14(double limitManAcc14) {
	LimitManAcc14 = limitManAcc14;
}

/**
 * 獲取屬性the sinosoft amount.
 * 
 * @return 屬性the sinosoft amount的值
 */
public double getAmount() {
	return Amount;
}

/**
 * 設置屬性the sinosoft amount.
 * 
 * @param amount
 *            待設置的the sinosoft amount的值
 */
public void setAmount(double amount) {
	Amount = amount;
}

/**
 * 獲取屬性the sinosoft limit man acc.
 * 
 * @return 屬性the sinosoft limit man acc的值
 */
public double getLimitManAcc() {
	return LimitManAcc;
}

/**
 * 設置屬性the sinosoft limit man acc.
 * 
 * @param limitManAcc
 *            待設置的the sinosoft limit man acc的值
 */
public void setLimitManAcc(double limitManAcc) {
	LimitManAcc = limitManAcc;
}

/**
 * 獲取屬性the sinosoft sum amount08.
 * 
 * @return 屬性the sinosoft sum amount08的值
 */
public double getSumAmount08() {
	return SumAmount08;
}

/**
 * 設置屬性the sinosoft sum amount08.
 * 
 * @param sumAmount08
 *            待設置的the sinosoft sum amount08的值
 */
public void setSumAmount08(double sumAmount08) {
	SumAmount08 = sumAmount08;
}

/**
 * 獲取屬性the sinosoft chg premium.
 * 
 * @return 屬性the sinosoft chg premium的值
 */
public String getChgPremium() {
	return ChgPremium;
}

/**
 * 設置屬性the sinosoft chg premium.
 * 
 * @param chgPremium
 *            待設置的the sinosoft chg premium的值
 */
public void setChgPremium(String chgPremium) {
	ChgPremium = chgPremium;
}

/**
 * 獲取屬性the sinosoft limit acc12.
 * 
 * @return 屬性the sinosoft limit acc12的值
 */
public double getLimitAcc12() {
	return LimitAcc12;
}

/**
 * 設置屬性the sinosoft limit acc12.
 * 
 * @param limitAcc12
 *            待設置的the sinosoft limit acc12的值
 */
public void setLimitAcc12(double limitAcc12) {
	LimitAcc12 = limitAcc12;
}

/**
 * 獲取屬性the sinosoft amount a g1.
 * 
 * @return 屬性the sinosoft amount a g1的值
 */
public double getAmountAG1() {
	return amountAG1;
}

/**
 * 設置屬性the sinosoft amount a g1.
 * 
 * @param amountAG1
 *            待設置的the sinosoft amount a g1的值
 */
public void setAmountAG1(double amountAG1) {
	this.amountAG1 = amountAG1;
}

/**
 * 獲取屬性the sinosoft amount b g1.
 * 
 * @return 屬性the sinosoft amount b g1的值
 */
public double getAmountBG1() {
	return amountBG1;
}

/**
 * 設置屬性the sinosoft amount b g1.
 * 
 * @param amountBG1
 *            待設置的the sinosoft amount b g1的值
 */
public void setAmountBG1(double amountBG1) {
	this.amountBG1 = amountBG1;
}

/**
 * 獲取屬性the sinosoft amount g1.
 * 
 * @return 屬性the sinosoft amount g1的值
 */
public double getAmountG1() {
	return amountG1;
}

/**
 * 設置屬性the sinosoft amount g1.
 * 
 * @param amountG1
 *            待設置的the sinosoft amount g1的值
 */
public void setAmountG1(double amountG1) {
	this.amountG1 = amountG1;
}

/**
 * 獲取屬性the sinosoft allow check.
 * 
 * @return 屬性the sinosoft allow check的值
 */
public String getAllowCheck() {
	return AllowCheck;
}

/**
 * 設置屬性the sinosoft allow check.
 * 
 * @param allowCheck
 *            待設置的the sinosoft allow check的值
 */
public void setAllowCheck(String allowCheck) {
	AllowCheck = allowCheck;
}

/**
 * 獲取屬性the sinosoft amount count.
 * 
 * @return 屬性the sinosoft amount count的值
 */
public double getAmountCount() {
	return AmountCount;
}

/**
 * 設置屬性the sinosoft amount count.
 * 
 * @param amountCount
 *            待設置的the sinosoft amount count的值
 */
public void setAmountCount(double amountCount) {
	AmountCount = amountCount;
}

/**
 * 獲取屬性the sinosoft car kind code.
 * 
 * @return 屬性the sinosoft car kind code的值
 */
public String getCarKindCode() {
	return CarKindCode;
}

/**
 * 設置屬性the sinosoft car kind code.
 * 
 * @param carKindCode
 *            待設置的the sinosoft car kind code的值
 */
public void setCarKindCode(String carKindCode) {
	CarKindCode = carKindCode;
}

/**
 * 獲取屬性the sinosoft kind code.
 * 
 * @return 屬性the sinosoft kind code的值
 */
public String getKindCode() {
	return KindCode;
}

/**
 * 設置屬性the sinosoft kind code.
 * 
 * @param kindCode
 *            待設置的the sinosoft kind code的值
 */
public void setKindCode(String kindCode) {
	KindCode = kindCode;
}

/**
 * 獲取屬性the sinosoft seat count.
 * 
 * @return 屬性the sinosoft seat count的值
 */
public double getSeatCount() {
	return SeatCount;
}

/**
 * 設置屬性the sinosoft seat count.
 * 
 * @param seatCount
 *            待設置的the sinosoft seat count的值
 */
public void setSeatCount(double seatCount) {
	SeatCount = seatCount;
}

/**
 * 獲取屬性the sinosoft ton count.
 * 
 * @return 屬性the sinosoft ton count的值
 */
public double getTonCount() {
	return TonCount;
}

/**
 * 設置屬性the sinosoft ton count.
 * 
 * @param tonCount
 *            待設置的the sinosoft ton count的值
 */
public void setTonCount(double tonCount) {
	TonCount = tonCount;
}

/**
 * 獲取屬性the sinosoft use nature code.
 * 
 * @return 屬性the sinosoft use nature code的值
 */
public String getUseNatureCode() {
	return UseNatureCode;
}

/**
 * 設置屬性the sinosoft use nature code.
 * 
 * @param useNatureCode
 *            待設置的the sinosoft use nature code的值
 */
public void setUseNatureCode(String useNatureCode) {
	UseNatureCode = useNatureCode;
}

/**
 * 獲取屬性the sinosoft use years.
 * 
 * @return 屬性the sinosoft use years的值
 */
public double getUseYears() {
	return UseYears;
}

/**
 * 設置屬性the sinosoft use years.
 * 
 * @param useYears
 *            待設置的the sinosoft use years的值
 */
public void setUseYears(double useYears) {
	UseYears = useYears;
}

/**
 * 獲取屬性the sinosoft amount l.
 * 
 * @return 屬性the sinosoft amount l的值
 */
public double getAmountL() {
	return amountL;
}

/**
 * 設置屬性the sinosoft amount l.
 * 
 * @param amountL
 *            待設置的the sinosoft amount l的值
 */
public void setAmountL(double amountL) {
	this.amountL = amountL;
}

/**
 * 獲取屬性the sinosoft amount h0 low.
 * 
 * @return 屬性the sinosoft amount h0 low的值
 */
public double getAmountH0Low() {
	return AmountH0Low;
}

/**
 * 設置屬性the sinosoft amount h0 low.
 * 
 * @param amountH0Low
 *            待設置的the sinosoft amount h0 low的值
 */
public void setAmountH0Low(double amountH0Low) {
	AmountH0Low = amountH0Low;
}

/**
 * 獲取屬性the sinosoft amount h0 upp.
 * 
 * @return 屬性the sinosoft amount h0 upp的值
 */
public double getAmountH0Upp() {
	return AmountH0Upp;
}

/**
 * 設置屬性the sinosoft amount h0 upp.
 * 
 * @param amountH0Upp
 *            待設置的the sinosoft amount h0 upp的值
 */
public void setAmountH0Upp(double amountH0Upp) {
	AmountH0Upp = amountH0Upp;
}

/**
 * 獲取屬性the sinosoft amount h0 low a.
 * 
 * @return 屬性the sinosoft amount h0 low a的值
 */
public double getAmountH0LowA() {
	return AmountH0LowA;
}

/**
 * 設置屬性the sinosoft amount h0 low a.
 * 
 * @param amountH0LowA
 *            待設置的the sinosoft amount h0 low a的值
 */
public void setAmountH0LowA(double amountH0LowA) {
	AmountH0LowA = amountH0LowA;
}

/**
 * 獲取屬性the sinosoft amount h0 upp a.
 * 
 * @return 屬性the sinosoft amount h0 upp a的值
 */
public double getAmountH0UppA() {
	return AmountH0UppA;
}

/**
 * 設置屬性the sinosoft amount h0 upp a.
 * 
 * @param amountH0UppA
 *            待設置的the sinosoft amount h0 upp a的值
 */
public void setAmountH0UppA(double amountH0UppA) {
	AmountH0UppA = amountH0UppA;
}

/**
 * 獲取屬性the sinosoft amount nx.
 * 
 * @return 屬性the sinosoft amount nx的值
 */
public double getAmountNX() {
	return AmountNX;
}

/**
 * 設置屬性the sinosoft amount nx.
 * 
 * @param amountNX
 *            待設置的the sinosoft amount nx的值
 */
public void setAmountNX(double amountNX) {
	AmountNX = amountNX;
}

/**
 * 獲取屬性the sinosoft amount ny.
 * 
 * @return 屬性the sinosoft amount ny的值
 */
public double getAmountNY() {
	return AmountNY;
}

/**
 * 設置屬性the sinosoft amount ny.
 * 
 * @param amountNY
 *            待設置的the sinosoft amount ny的值
 */
public void setAmountNY(double amountNY) {
	AmountNY = amountNY;
}

/**
 * 獲取屬性the sinosoft special power.
 * 
 * @return 屬性the sinosoft special power的值
 */
public String getSpecialPower() {
	return SpecialPower;
}

/**
 * 設置屬性the sinosoft special power.
 * 
 * @param specialPower
 *            待設置的the sinosoft special power的值
 */
public void setSpecialPower(String specialPower) {
	SpecialPower = specialPower;
}

/**
 * 獲取屬性the sinosoft car kind code l.
 * 
 * @return 屬性the sinosoft car kind code l的值
 */
public String getCarKindCodeL() {
	return CarKindCodeL;
}

/**
 * 設置屬性the sinosoft car kind code l.
 * 
 * @param carKindCodeL
 *            待設置的the sinosoft car kind code l的值
 */
public void setCarKindCodeL(String carKindCodeL) {
	CarKindCodeL = carKindCodeL;
}

/**
 * 獲取屬性the sinosoft use years l.
 * 
 * @return 屬性the sinosoft use years l的值
 */
public double getUseYearsL() {
	return UseYearsL;
}

/**
 * 設置屬性the sinosoft use years l.
 * 
 * @param useYearsL
 *            待設置的the sinosoft use years l的值
 */
public void setUseYearsL(double useYearsL) {
	UseYearsL = useYearsL;
}

/**
 * 獲取屬性the sinosoft kind code out of auto.
 * 
 * @return 屬性the sinosoft kind code out of auto的值
 */
public String getKindCodeOutOfAuto() {
	return KindCodeOutOfAuto;
}

/**
 * 設置屬性the sinosoft kind code out of auto.
 * 
 * @param kindCodeOutOfAuto
 *            待設置的the sinosoft kind code out of auto的值
 */
public void setKindCodeOutOfAuto(String kindCodeOutOfAuto) {
	KindCodeOutOfAuto = kindCodeOutOfAuto;
}

/**
 * 獲取屬性the sinosoft kind code out of auto one.
 * 
 * @return 屬性the sinosoft kind code out of auto one的值
 */
public String getKindCodeOutOfAutoOne() {
	return KindCodeOutOfAutoOne;
}

/**
 * 設置屬性the sinosoft kind code out of auto one.
 * 
 * @param kindCodeOutOfAutoOne
 *            待設置的the sinosoft kind code out of auto one的值
 */
public void setKindCodeOutOfAutoOne(String kindCodeOutOfAutoOne) {
	KindCodeOutOfAutoOne = kindCodeOutOfAutoOne;
}

/**
 * 獲取屬性the sinosoft kind code out of special.
 * 
 * @return 屬性the sinosoft kind code out of special的值
 */
public String getKindCodeOutOfSpecial() {
	return KindCodeOutOfSpecial;
}

/**
 * 設置屬性the sinosoft kind code out of special.
 * 
 * @param kindCodeOutOfSpecial
 *            待設置的the sinosoft kind code out of special的值
 */
public void setKindCodeOutOfSpecial(String kindCodeOutOfSpecial) {
	KindCodeOutOfSpecial = kindCodeOutOfSpecial;
}

/**
 * 獲取屬性the sinosoft license no in clude.
 * 
 * @return 屬性the sinosoft license no in clude的值
 */
public String getLicenseNoInClude() {
	return LicenseNoInClude;
}

/**
 * 設置屬性the sinosoft license no in clude.
 * 
 * @param licenseNoInClude
 *            待設置的the sinosoft license no in clude的值
 */
public void setLicenseNoInClude(String licenseNoInClude) {
	LicenseNoInClude = licenseNoInClude;
}

/**
 * 獲取屬性the sinosoft appli linker name.
 * 
 * @return 屬性the sinosoft appli linker name的值
 */
public String getAppliLinkerName() {
	return AppliLinkerName;
}

/**
 * 設置屬性the sinosoft appli linker name.
 * 
 * @param appliLinkerName
 *            待設置的the sinosoft appli linker name的值
 */
public void setAppliLinkerName(String appliLinkerName) {
	AppliLinkerName = appliLinkerName;
}

/**
 * 獲取屬性the sinosoft cB year limit.
 * 
 * @return 屬性the sinosoft cB year limit的值
 */
public int getCBYearLimit() {
	return CBYearLimit;
}

/**
 * 設置屬性the sinosoft cB year limit.
 * 
 * @param CBYearLimit
 *            待設置的the sinosoft cB year limit的值
 */
public void setCBYearLimit(int CBYearLimit) {
	this.CBYearLimit = CBYearLimit;
}

/**
 * 獲取屬性the sinosoft investment.
 * 
 * @return 屬性the sinosoft investment的值
 */
public double getInvestment() {
	return Investment;
}

/**
 * 設置屬性the sinosoft investment.
 * 
 * @param investment
 *            待設置的the sinosoft investment的值
 */
public void setInvestment(double investment) {
	Investment = investment;
}

/**
 * 獲取屬性the sinosoft undo contract date.
 * 
 * @return 屬性the sinosoft undo contract date的值
 */
public double getUndoContractDate() {
	return undoContractDate;
}

/**
 * 設置屬性the sinosoft undo contract date.
 * 
 * @param undoContractDate
 *            待設置的the sinosoft undo contract date的值
 */
public void setUndoContractDate(double undoContractDate) {
	this.undoContractDate = undoContractDate;
}

/**
 * 獲取屬性the sinosoft suttle amount a.
 * 
 * @return 屬性the sinosoft suttle amount a的值
 */
public double getSuttleAmountA() {
	return SuttleAmountA;
}

/**
 * 設置屬性the sinosoft suttle amount a.
 * 
 * @param suttleAmountA
 *            待設置的the sinosoft suttle amount a的值
 */
public void setSuttleAmountA(double suttleAmountA) {
	SuttleAmountA = suttleAmountA;
}

/**
 * 獲取屬性the sinosoft suttle amount b.
 * 
 * @return 屬性the sinosoft suttle amount b的值
 */
public double getSuttleAmountB() {
	return SuttleAmountB;
}

/**
 * 設置屬性the sinosoft suttle amount b.
 * 
 * @param suttleAmountB
 *            待設置的the sinosoft suttle amount b的值
 */
public void setSuttleAmountB(double suttleAmountB) {
	SuttleAmountB = suttleAmountB;
}

/**
 * 獲取屬性the sinosoft tui bao premium.
 * 
 * @return 屬性the sinosoft tui bao premium的值
 */
public double getTuiBaoPremium() {
	return TuiBaoPremium;
}

/**
 * 設置屬性the sinosoft tui bao premium.
 * 
 * @param tuiBaoPremium
 *            待設置的the sinosoft tui bao premium的值
 */
public void setTuiBaoPremium(double tuiBaoPremium) {
	TuiBaoPremium = tuiBaoPremium;
}

/**
 * 獲取屬性the sinosoft only ab years.
 * 
 * @return 屬性the sinosoft only ab years的值
 */
public double getOnlyABYears() {
	return OnlyABYears;
}

/**
 * 設置屬性the sinosoft only ab years.
 * 
 * @param onlyABYears
 *            待設置的the sinosoft only ab years的值
 */
public void setOnlyABYears(double onlyABYears) {
	OnlyABYears = onlyABYears;
}

/**
 * 獲取屬性the sinosoft limit02 fee1505.
 * 
 * @return 屬性the sinosoft limit02 fee1505的值
 */
public double getLimit02Fee1505() {
	return Limit02Fee1505;
}

/**
 * 設置屬性the sinosoft limit02 fee1505.
 * 
 * @param limit02Fee1505
 *            待設置的the sinosoft limit02 fee1505的值
 */
public void setLimit02Fee1505(double limit02Fee1505) {
	Limit02Fee1505 = limit02Fee1505;
}

/**
 * 獲取屬性the sinosoft dou pre turn over1505.
 * 
 * @return 屬性the sinosoft dou pre turn over1505的值
 */
public double getDouPreTurnOver1505() {
	return douPreTurnOver1505;
}

/**
 * 設置屬性the sinosoft dou pre turn over1505.
 * 
 * @param douPreTurnOver1505
 *            待設置的the sinosoft dou pre turn over1505的值
 */
public void setDouPreTurnOver1505(double douPreTurnOver1505) {
	this.douPreTurnOver1505 = douPreTurnOver1505;
}

/**
 * 獲取屬性the sinosoft dou staff count1505.
 * 
 * @return 屬性the sinosoft dou staff count1505的值
 */
public double getDouStaffCount1505() {
	return douStaffCount1505;
}

/**
 * 設置屬性the sinosoft dou staff count1505.
 * 
 * @param douStaffCount1505
 *            待設置的the sinosoft dou staff count1505的值
 */
public void setDouStaffCount1505(double douStaffCount1505) {
	this.douStaffCount1505 = douStaffCount1505;
}

/**
 * 獲取屬性the sinosoft limit03 fee1505.
 * 
 * @return 屬性the sinosoft limit03 fee1505的值
 */
public double getLimit03Fee1505() {
	return Limit03Fee1505;
}

/**
 * 設置屬性the sinosoft limit03 fee1505.
 * 
 * @param limit03Fee1505
 *            待設置的the sinosoft limit03 fee1505的值
 */
public void setLimit03Fee1505(double limit03Fee1505) {
	Limit03Fee1505 = limit03Fee1505;
}

/**
 * 獲取屬性the sinosoft max capacity1505.
 * 
 * @return 屬性the sinosoft max capacity1505的值
 */
public double getMaxCapacity1505() {
	return maxCapacity1505;
}

/**
 * 設置屬性the sinosoft max capacity1505.
 * 
 * @param maxCapacity1505
 *            待設置的the sinosoft max capacity1505的值
 */
public void setMaxCapacity1505(double maxCapacity1505) {
	this.maxCapacity1505 = maxCapacity1505;
}

/**
 * 獲取屬性the sinosoft mortgage.
 * 
 * @return 屬性the sinosoft mortgage的值
 */
public String getMortgage() {
	return Mortgage;
}

/**
 * 設置屬性the sinosoft mortgage.
 * 
 * @param mortgage
 *            待設置的the sinosoft mortgage的值
 */
public void setMortgage(String mortgage) {
	Mortgage = mortgage;
}

/**
 * 獲取屬性the sinosoft endor dis rate.
 * 
 * @return 屬性the sinosoft endor dis rate的值
 */
public String getEndorDisRate() {
	return EndorDisRate;
}

/**
 * 設置屬性the sinosoft endor dis rate.
 * 
 * @param endorDisRate
 *            待設置的the sinosoft endor dis rate的值
 */
public void setEndorDisRate(String endorDisRate) {
	this.EndorDisRate = endorDisRate;
}

/**
 * 獲取屬性the sinosoft sum amount0145100.
 * 
 * @return 屬性the sinosoft sum amount0145100的值
 */
public double getSumAmount0145100() {
	return SumAmount0145100;
}

/**
 * 設置屬性the sinosoft sum amount0145100.
 * 
 * @param sumAmount0145100
 *            待設置的the sinosoft sum amount0145100的值
 */
public void setSumAmount0145100(double sumAmount0145100) {
	SumAmount0145100 = sumAmount0145100;
}

/**
 * 獲取屬性the sinosoft sum amount0145200.
 * 
 * @return 屬性the sinosoft sum amount0145200的值
 */
public double getSumAmount0145200() {
	return SumAmount0145200;
}

/**
 * 設置屬性the sinosoft sum amount0145200.
 * 
 * @param sumAmount0145200
 *            待設置的the sinosoft sum amount0145200的值
 */
public void setSumAmount0145200(double sumAmount0145200) {
	SumAmount0145200 = sumAmount0145200;
}

/**
 * 獲取屬性the sinosoft sum amount0145300.
 * 
 * @return 屬性the sinosoft sum amount0145300的值
 */
public double getSumAmount0145300() {
	return SumAmount0145300;
}

/**
 * 設置屬性the sinosoft sum amount0145300.
 * 
 * @param sumAmount0145300
 *            待設置的the sinosoft sum amount0145300的值
 */
public void setSumAmount0145300(double sumAmount0145300) {
	SumAmount0145300 = sumAmount0145300;
}

/**
 * 獲取屬性the sinosoft sum amount0145400.
 * 
 * @return 屬性the sinosoft sum amount0145400的值
 */
public double getSumAmount0145400() {
	return SumAmount0145400;
}

/**
 * 設置屬性the sinosoft sum amount0145400.
 * 
 * @param sumAmount0145400
 *            待設置的the sinosoft sum amount0145400的值
 */
public void setSumAmount0145400(double sumAmount0145400) {
	SumAmount0145400 = sumAmount0145400;
}

/**
 * 獲取屬性the sinosoft sum amount0145500.
 * 
 * @return 屬性the sinosoft sum amount0145500的值
 */
public double getSumAmount0145500() {
	return SumAmount0145500;
}

/**
 * 設置屬性the sinosoft sum amount0145500.
 * 
 * @param sumAmount0145500
 *            待設置的the sinosoft sum amount0145500的值
 */
public void setSumAmount0145500(double sumAmount0145500) {
	SumAmount0145500 = sumAmount0145500;
}

/**
 * 獲取屬性the sinosoft allow0911700.
 * 
 * @return 屬性the sinosoft allow0911700的值
 */
public String getAllow0911700() {
	return Allow0911700;
}

/**
 * 設置屬性the sinosoft allow0911700.
 * 
 * @param allow0911700
 *            待設置的the sinosoft allow0911700的值
 */
public void setAllow0911700(String allow0911700) {
	Allow0911700 = allow0911700;
}

/**
 * 獲取屬性the sinosoft limit third acc4.
 * 
 * @return 屬性the sinosoft limit third acc4的值
 */
public double getLimitThirdAcc4() {
	return LimitThirdAcc4;
}

/**
 * 設置屬性the sinosoft limit third acc4.
 * 
 * @param limitThirdAcc4
 *            待設置的the sinosoft limit third acc4的值
 */
public void setLimitThirdAcc4(double limitThirdAcc4) {
	LimitThirdAcc4 = limitThirdAcc4;
}

/**
 * 獲取屬性the sinosoft sum amount pn.
 * 
 * @return 屬性the sinosoft sum amount pn的值
 */
public double getSumAmountPN() {
	return SumAmountPN;
}

/**
 * 設置屬性the sinosoft sum amount pn.
 * 
 * @param sumAmountPN
 *            待設置的the sinosoft sum amount pn的值
 */
public void setSumAmountPN(double sumAmountPN) {
	SumAmountPN = sumAmountPN;
}

/**
 * 獲取屬性the sinosoft limit fee one car.
 * 
 * @return 屬性the sinosoft limit fee one car的值
 */
public double getLimitFeeOneCar() {
	return LimitFeeOneCar;
}

/**
 * 設置屬性the sinosoft limit fee one car.
 * 
 * @param limitFeeOneCar
 *            待設置的the sinosoft limit fee one car的值
 */
public void setLimitFeeOneCar(double limitFeeOneCar) {
	LimitFeeOneCar = limitFeeOneCar;
}

/**
 * 獲取屬性the sinosoft short rate flag.
 * 
 * @return 屬性the sinosoft short rate flag的值
 */
public String getShortRateFlag() {
	return ShortRateFlag;
}

/**
 * 設置屬性the sinosoft short rate flag.
 * 
 * @param shortRateFlag
 *            待設置的the sinosoft short rate flag的值
 */
public void setShortRateFlag(String shortRateFlag) {
	ShortRateFlag = shortRateFlag;
}

/**
 * 獲取屬性the sinosoft policy type0902.
 * 
 * @return 屬性the sinosoft policy type0902的值
 */
public String getPolicyType0902() {
	return PolicyType0902;
}

/**
 * 設置屬性the sinosoft policy type0902.
 * 
 * @param PolicyType0902
 *            待設置的the sinosoft policy type0902的值
 */
public void setPolicyType0902(String PolicyType0902) {
	this.PolicyType0902 = PolicyType0902;
}

/**
 * 獲取屬性the sinosoft parent company check.
 * 
 * @return 屬性the sinosoft parent company check的值
 */
public String getParentCompanyCheck() {
	return parentCompanyCheck;
}

/**
 * 設置屬性the sinosoft parent company check.
 * 
 * @param parentCompanyCheck
 *            待設置的the sinosoft parent company check的值
 */
public void setParentCompanyCheck(String parentCompanyCheck) {
	this.parentCompanyCheck = parentCompanyCheck;
}

/**
 * 獲取屬性the sinosoft unit amount03.
 * 
 * @return 屬性the sinosoft unit amount03的值
 */
public double getUnitAmount03() {
	return UnitAmount03;
}

/**
 * 設置屬性the sinosoft unit amount03.
 * 
 * @param unitAmount03
 *            待設置的the sinosoft unit amount03的值
 */
public void setUnitAmount03(double unitAmount03) {
	this.UnitAmount03 = unitAmount03;
}

/**
 * 獲取屬性the sinosoft sum amount0300100.
 * 
 * @return 屬性the sinosoft sum amount0300100的值
 */
public double getSumAmount0300100() {
	return sumAmount0300100;
}

/**
 * 設置屬性the sinosoft sum amount0300100.
 * 
 * @param sumAmount0300100
 *            待設置的the sinosoft sum amount0300100的值
 */
public void setSumAmount0300100(double sumAmount0300100) {
	this.sumAmount0300100 = sumAmount0300100;
}

/**
 * 獲取屬性the sinosoft allow split danger unit.
 * 
 * @return 屬性the sinosoft allow split danger unit的值
 */
public String getAllowSplitDangerUnit() {
	return allowSplitDangerUnit;
}

/**
 * 設置屬性the sinosoft allow split danger unit.
 * 
 * @param allowSplitDangerUnit
 *            待設置的the sinosoft allow split danger unit的值
 */
public void setAllowSplitDangerUnit(String allowSplitDangerUnit) {
	this.allowSplitDangerUnit = allowSplitDangerUnit;
}

/**
 * 獲取屬性the sinosoft sum amount0400600.
 * 
 * @return 屬性the sinosoft sum amount0400600的值
 */
public double getSumAmount0400600() {
	return sumAmount0400600;
}

/**
 * 設置屬性the sinosoft sum amount0400600.
 * 
 * @param sumAmount0400600
 *            待設置的the sinosoft sum amount0400600的值
 */
public void setSumAmount0400600(double sumAmount0400600) {
	this.sumAmount0400600 = sumAmount0400600;
}

/**
 * 獲取屬性the sinosoft sum amount0400700.
 * 
 * @return 屬性the sinosoft sum amount0400700的值
 */
public double getSumAmount0400700() {
	return sumAmount0400700;
}

/**
 * 設置屬性the sinosoft sum amount0400700.
 * 
 * @param sumAmount0400700
 *            待設置的the sinosoft sum amount0400700的值
 */
public void setSumAmount0400700(double sumAmount0400700) {
	this.sumAmount0400700 = sumAmount0400700;
}

/**
 * 獲取屬性the sinosoft limit man heal01.
 * 
 * @return 屬性the sinosoft limit man heal01的值
 */
public double getLimitManHeal01() {
	return LimitManHeal01;
}

/**
 * 設置屬性the sinosoft limit man heal01.
 * 
 * @param limitManHeal01
 *            待設置的the sinosoft limit man heal01的值
 */
public void setLimitManHeal01(double limitManHeal01) {
	LimitManHeal01 = limitManHeal01;
}

/**
 * 獲取屬性the sinosoft cover note flag.
 * 
 * @return 屬性the sinosoft cover note flag的值
 */
public String getCoverNoteFlag() {
	return coverNoteFlag;
}

/**
 * 設置屬性the sinosoft cover note flag.
 * 
 * @param coverNoteFlag
 *            待設置的the sinosoft cover note flag的值
 */
public void setCoverNoteFlag(String coverNoteFlag) {
	this.coverNoteFlag = coverNoteFlag;
}

/**
 * 獲取屬性the sinosoft amount per9000452.
 * 
 * @return 屬性the sinosoft amount per9000452的值
 */
public double getAmountPer9000452() {
	return AmountPer9000452;
}

/**
 * 設置屬性the sinosoft amount per9000452.
 * 
 * @param amountPer9000452
 *            待設置的the sinosoft amount per9000452的值
 */
public void setAmountPer9000452(double amountPer9000452) {
	this.AmountPer9000452 = amountPer9000452;
}

/**
 * 獲取屬性the sinosoft amount per9000453.
 * 
 * @return 屬性the sinosoft amount per9000453的值
 */
public double getAmountPer9000453() {
	return AmountPer9000453;
}

/**
 * 設置屬性the sinosoft amount per9000453.
 * 
 * @param amountPer9000453
 *            待設置的the sinosoft amount per9000453的值
 */
public void setAmountPer9000453(double amountPer9000453) {
	this.AmountPer9000453 = amountPer9000453;
}

/**
 * 獲取屬性the sinosoft amount per9000454.
 * 
 * @return 屬性the sinosoft amount per9000454的值
 */
public double getAmountPer9000454() {
	return AmountPer9000454;
}

/**
 * 設置屬性the sinosoft amount per9000454.
 * 
 * @param amountPer9000454
 *            待設置的the sinosoft amount per9000454的值
 */
public void setAmountPer9000454(double amountPer9000454) {
	this.AmountPer9000454 = amountPer9000454;
}

/**
 * 獲取屬性the sinosoft amount per9000449.
 * 
 * @return 屬性the sinosoft amount per9000449的值
 */
public double getAmountPer9000449() {
	return AmountPer9000449;
}

/**
 * 設置屬性the sinosoft amount per9000449.
 * 
 * @param amountPer9000449
 *            待設置的the sinosoft amount per9000449的值
 */
public void setAmountPer9000449(double amountPer9000449) {
	this.AmountPer9000449 = amountPer9000449;
}

/**
 * 獲取屬性the sinosoft amount per9000450.
 * 
 * @return 屬性the sinosoft amount per9000450的值
 */
public double getAmountPer9000450() {
	return AmountPer9000450;
}

/**
 * 設置屬性the sinosoft amount per9000450.
 * 
 * @param amountPer9000450
 *            待設置的the sinosoft amount per9000450的值
 */
public void setAmountPer9000450(double amountPer9000450) {
	this.AmountPer9000450 = amountPer9000450;
}

/**
 * 獲取屬性the sinosoft amount per9000451.
 * 
 * @return 屬性the sinosoft amount per9000451的值
 */
public double getAmountPer9000451() {
	return AmountPer9000451;
}

/**
 * 設置屬性the sinosoft amount per9000451.
 * 
 * @param amountPer9000451
 *            待設置的the sinosoft amount per9000451的值
 */
public void setAmountPer9000451(double amountPer9000451) {
	this.AmountPer9000451 = amountPer9000451;
}

/**
 * 獲取屬性the sinosoft limit acc03.
 * 
 * @return 屬性the sinosoft limit acc03的值
 */
public double getLimitAcc03() {
	return LimitAcc03;
}

/**
 * 設置屬性the sinosoft limit acc03.
 * 
 * @param limitAcc03
 *            待設置的the sinosoft limit acc03的值
 */
public void setLimitAcc03(double limitAcc03) {
	LimitAcc03 = limitAcc03;
}

/**
 * 獲取屬性the sinosoft amount new.
 * 
 * @return 屬性the sinosoft amount new的值
 */
public double getAmountNew() {
	return AmountNew;
}

/**
 * 設置屬性the sinosoft amount new.
 * 
 * @param amountNew
 *            待設置的the sinosoft amount new的值
 */
public void setAmountNew(double amountNew) {
	AmountNew = amountNew;
}

/**
 * 獲取屬性the sinosoft amount man sum.
 * 
 * @return 屬性the sinosoft amount man sum的值
 */
public double getAmountManSum() {
	return AmountManSum;
}

/**
 * 設置屬性the sinosoft amount man sum.
 * 
 * @param amountManSum
 *            待設置的the sinosoft amount man sum的值
 */
public void setAmountManSum(double amountManSum) {
	AmountManSum = amountManSum;
}

/**
 * 獲取屬性the sinosoft sum amount yl.
 * 
 * @return 屬性the sinosoft sum amount yl的值
 */
public double getSumAmountYL() {
	return sumAmountYL;
}

/**
 * 設置屬性the sinosoft sum amount yl.
 * 
 * @param sumAmountYL
 *            待設置的the sinosoft sum amount yl的值
 */
public void setSumAmountYL(double sumAmountYL) {
	this.sumAmountYL = sumAmountYL;
}

/**
 * 獲取屬性the sinosoft amount per.
 * 
 * @return 屬性the sinosoft amount per的值
 */
public double getAmountPer() {
	return AmountPer;
}

/**
 * 設置屬性the sinosoft amount per.
 * 
 * @param amountPer
 *            待設置的the sinosoft amount per的值
 */
public void setAmountPer(double amountPer) {
	AmountPer = amountPer;
}

/**
 * 獲取屬性the sinosoft amount w.
 * 
 * @return 屬性the sinosoft amount w的值
 */
public double getAmountW() {
	return AmountW;
}

/**
 * 設置屬性the sinosoft amount w.
 * 
 * @param amountW
 *            待設置的the sinosoft amount w的值
 */
public void setAmountW(double amountW) {
	AmountW = amountW;
}

/**
 * 獲取屬性the sinosoft choose g1 years.
 * 
 * @return 屬性the sinosoft choose g1 years的值
 */
public double getChooseG1Years() {
	return ChooseG1Years;
}

/**
 * 設置屬性the sinosoft choose g1 years.
 * 
 * @param chooseG1Years
 *            待設置的the sinosoft choose g1 years的值
 */
public void setChooseG1Years(double chooseG1Years) {
	ChooseG1Years = chooseG1Years;
}

/**
 * 獲取屬性the sinosoft choose l1 years.
 * 
 * @return 屬性the sinosoft choose l1 years的值
 */
public double getChooseL1Years() {
	return ChooseL1Years;
}

/**
 * 設置屬性the sinosoft choose l1 years.
 * 
 * @param chooseL1Years
 *            待設置的the sinosoft choose l1 years的值
 */
public void setChooseL1Years(double chooseL1Years) {
	ChooseL1Years = chooseL1Years;
}

/**
 * 獲取屬性the sinosoft choose lp a4 years.
 * 
 * @return 屬性the sinosoft choose lp a4 years的值
 */
public double getChooseLPA4Years() {
	return ChooseLPA4Years;
}

/**
 * 設置屬性the sinosoft choose lp a4 years.
 * 
 * @param chooseLPA4Years
 *            待設置的the sinosoft choose lp a4 years的值
 */
public void setChooseLPA4Years(double chooseLPA4Years) {
	ChooseLPA4Years = chooseLPA4Years;
}

/**
 * 獲取屬性the sinosoft choose j years.
 * 
 * @return 屬性the sinosoft choose j years的值
 */
public double getChooseJYears() {
	return ChooseJYears;
}

/**
 * 設置屬性the sinosoft choose j years.
 * 
 * @param chooseJYears
 *            待設置的the sinosoft choose j years的值
 */
public void setChooseJYears(double chooseJYears) {
	ChooseJYears = chooseJYears;
}

/**
 * 獲取屬性the sinosoft choose n a0 years.
 * 
 * @return 屬性the sinosoft choose n a0 years的值
 */
public double getChooseNA0Years() {
	return ChooseNA0Years;
}

/**
 * 設置屬性the sinosoft choose n a0 years.
 * 
 * @param chooseNA0Years
 *            待設置的the sinosoft choose n a0 years的值
 */
public void setChooseNA0Years(double chooseNA0Years) {
	ChooseNA0Years = chooseNA0Years;
}

/**
 * 獲取屬性the sinosoft choose n h0 years.
 * 
 * @return 屬性the sinosoft choose n h0 years的值
 */
public double getChooseNH0Years() {
	return ChooseNH0Years;
}

/**
 * 設置屬性the sinosoft choose n h0 years.
 * 
 * @param chooseNH0Years
 *            待設置的the sinosoft choose n h0 years的值
 */
public void setChooseNH0Years(double chooseNH0Years) {
	ChooseNH0Years = chooseNH0Years;
}

/**
 * 獲取屬性the sinosoft choose y years.
 * 
 * @return 屬性the sinosoft choose y years的值
 */
public double getChooseYYears() {
	return ChooseYYears;
}

/**
 * 設置屬性the sinosoft choose y years.
 * 
 * @param chooseYYears
 *            待設置的the sinosoft choose y years的值
 */
public void setChooseYYears(double chooseYYears) {
	ChooseYYears = chooseYYears;
}

/**
 * 獲取屬性the sinosoft choose zf.
 * 
 * @return 屬性the sinosoft choose zf的值
 */
public String getChooseZF() {
	return ChooseZF;
}

/**
 * 設置屬性the sinosoft choose zf.
 * 
 * @param chooseZF
 *            待設置的the sinosoft choose zf的值
 */
public void setChooseZF(String chooseZF) {
	ChooseZF = chooseZF;
}

/**
 * 獲取屬性the sinosoft not choose b.
 * 
 * @return 屬性the sinosoft not choose b的值
 */
public String getNotChooseB() {
	return NotChooseB;
}

/**
 * 設置屬性the sinosoft not choose b.
 * 
 * @param notChooseB
 *            待設置的the sinosoft not choose b的值
 */
public void setNotChooseB(String notChooseB) {
	NotChooseB = notChooseB;
}

/**
 * 獲取屬性the sinosoft choose e years.
 * 
 * @return 屬性the sinosoft choose e years的值
 */
public double getChooseEYears() {
	return ChooseEYears;
}

/**
 * 設置屬性the sinosoft choose e years.
 * 
 * @param chooseEYears
 *            待設置的the sinosoft choose e years的值
 */
public void setChooseEYears(double chooseEYears) {
	ChooseEYears = chooseEYears;
}

/**
 * 獲取屬性the sinosoft endorse power.
 * 
 * @return 屬性the sinosoft endorse power的值
 */
public String getEndorsePower() {
	return EndorsePower;
}

/**
 * 設置屬性the sinosoft endorse power.
 * 
 * @param endorsePower
 *            待設置的the sinosoft endorse power的值
 */
public void setEndorsePower(String endorsePower) {
	EndorsePower = endorsePower;
}

/**
 * 獲取屬性the sinosoft cB month limit.
 * 
 * @return 屬性the sinosoft cB month limit的值
 */
public int getCBMonthLimit() {
	return CBMonthLimit;
}

/**
 * 設置屬性the sinosoft cB month limit.
 * 
 * @param monthLimit
 *            待設置的the sinosoft cB month limit的值
 */
public void setCBMonthLimit(int monthLimit) {
	CBMonthLimit = monthLimit;
}

/**
 * 獲取屬性the sinosoft sum amount300101.
 * 
 * @return 屬性the sinosoft sum amount300101的值
 */
public double getSumAmount300101() {
	return SumAmount300101;
}

/**
 * 設置屬性the sinosoft sum amount300101.
 * 
 * @param sumAmount300101
 *            待設置的the sinosoft sum amount300101的值
 */
public void setSumAmount300101(double sumAmount300101) {
	SumAmount300101 = sumAmount300101;
}

/**
 * 獲取屬性the sinosoft sum amount300102.
 * 
 * @return 屬性the sinosoft sum amount300102的值
 */
public double getSumAmount300102() {
	return SumAmount300102;
}

/**
 * 設置屬性the sinosoft sum amount300102.
 * 
 * @param sumAmount300102
 *            待設置的the sinosoft sum amount300102的值
 */
public void setSumAmount300102(double sumAmount300102) {
	SumAmount300102 = sumAmount300102;
}

/**
 * 獲取屬性the sinosoft sum amount300103.
 * 
 * @return 屬性the sinosoft sum amount300103的值
 */
public double getSumAmount300103() {
	return SumAmount300103;
}

/**
 * 設置屬性the sinosoft sum amount300103.
 * 
 * @param sumAmount300103
 *            待設置的the sinosoft sum amount300103的值
 */
public void setSumAmount300103(double sumAmount300103) {
	SumAmount300103 = sumAmount300103;
}

/**
 * 獲取屬性the sinosoft sum amount300104.
 * 
 * @return 屬性the sinosoft sum amount300104的值
 */
public double getSumAmount300104() {
	return SumAmount300104;
}

/**
 * 設置屬性the sinosoft sum amount300104.
 * 
 * @param sumAmount300104
 *            待設置的the sinosoft sum amount300104的值
 */
public void setSumAmount300104(double sumAmount300104) {
	SumAmount300104 = sumAmount300104;
}

/**
 * 獲取屬性the sinosoft sum amount300105.
 * 
 * @return 屬性the sinosoft sum amount300105的值
 */
public double getSumAmount300105() {
	return SumAmount300105;
}

/**
 * 設置屬性the sinosoft sum amount300105.
 * 
 * @param sumAmount300105
 *            待設置的the sinosoft sum amount300105的值
 */
public void setSumAmount300105(double sumAmount300105) {
	SumAmount300105 = sumAmount300105;
}

/**
 * 獲取屬性the sinosoft sum amount300106.
 * 
 * @return 屬性the sinosoft sum amount300106的值
 */
public double getSumAmount300106() {
	return SumAmount300106;
}

/**
 * 設置屬性the sinosoft sum amount300106.
 * 
 * @param sumAmount300106
 *            待設置的the sinosoft sum amount300106的值
 */
public void setSumAmount300106(double sumAmount300106) {
	SumAmount300106 = sumAmount300106;
}

/**
 * 獲取屬性the sinosoft choose a price.
 * 
 * @return 屬性the sinosoft choose a price的值
 */
public double getChooseAPrice() {
	return ChooseAPrice;
}

/**
 * 設置屬性the sinosoft choose a price.
 * 
 * @param chooseAPrice
 *            待設置的the sinosoft choose a price的值
 */
public void setChooseAPrice(double chooseAPrice) {
	ChooseAPrice = chooseAPrice;
}

/**
 * 獲取屬性the sinosoft ton count h0.
 * 
 * @return 屬性the sinosoft ton count h0的值
 */
public double getTonCountH0() {
	return TonCountH0;
}

/**
 * 設置屬性the sinosoft ton count h0.
 * 
 * @param tonCountH0
 *            待設置的the sinosoft ton count h0的值
 */
public void setTonCountH0(double tonCountH0) {
	TonCountH0 = tonCountH0;
}

/**
 * 獲取屬性the sinosoft amount amj.
 * 
 * @return 屬性the sinosoft amount amj的值
 */
public double getAmountAMJ() {
	return amountAMJ;
}

/**
 * 設置屬性the sinosoft amount amj.
 * 
 * @param amountAMJ
 *            待設置的the sinosoft amount amj的值
 */
public void setAmountAMJ(double amountAMJ) {
	this.amountAMJ = amountAMJ;
}

/**
 * 獲取屬性the sinosoft amount bmj.
 * 
 * @return 屬性the sinosoft amount bmj的值
 */
public double getAmountBMJ() {
	return amountBMJ;
}

/**
 * 設置屬性the sinosoft amount bmj.
 * 
 * @param amountBMJ
 *            待設置的the sinosoft amount bmj的值
 */
public void setAmountBMJ(double amountBMJ) {
	this.amountBMJ = amountBMJ;
}

/**
 * 獲取屬性the sinosoft amount d11 mj.
 * 
 * @return 屬性the sinosoft amount d11 mj的值
 */
public double getAmountD11MJ() {
	return AmountD11MJ;
}

/**
 * 設置屬性the sinosoft amount d11 mj.
 * 
 * @param amountD11MJ
 *            待設置的the sinosoft amount d11 mj的值
 */
public void setAmountD11MJ(double amountD11MJ) {
	AmountD11MJ = amountD11MJ;
}

/**
 * 獲取屬性the sinosoft amount gmj.
 * 
 * @return 屬性the sinosoft amount gmj的值
 */
public double getAmountGMJ() {
	return amountGMJ;
}

/**
 * 設置屬性the sinosoft amount gmj.
 * 
 * @param amountGMJ
 *            待設置的the sinosoft amount gmj的值
 */
public void setAmountGMJ(double amountGMJ) {
	this.amountGMJ = amountGMJ;
}

/**
 * 獲取屬性the sinosoft choose td.
 * 
 * @return 屬性the sinosoft choose td的值
 */
public String getChooseTD() {
	return ChooseTD;
}

/**
 * 設置屬性the sinosoft choose td.
 * 
 * @param chooseTD
 *            待設置的the sinosoft choose td的值
 */
public void setChooseTD(String chooseTD) {
	ChooseTD = chooseTD;
}

/**
 * 獲取屬性the sinosoft choose ej years.
 * 
 * @return 屬性the sinosoft choose ej years的值
 */
public double getChooseEJYears() {
	return ChooseEJYears;
}

/**
 * 設置屬性the sinosoft choose ej years.
 * 
 * @param chooseEJYears
 *            待設置的the sinosoft choose ej years的值
 */
public void setChooseEJYears(double chooseEJYears) {
	ChooseEJYears = chooseEJYears;
}

/**
 * 獲取屬性the sinosoft amount r.
 * 
 * @return 屬性the sinosoft amount r的值
 */
public double getAmountR() {
	return amountR;
}

/**
 * 設置屬性the sinosoft amount r.
 * 
 * @param amountR
 *            待設置的the sinosoft amount r的值
 */
public void setAmountR(double amountR) {
	this.amountR = amountR;
}

/**
 * 獲取屬性the sinosoft amount r per.
 * 
 * @return 屬性the sinosoft amount r per的值
 */
public double getAmountRPer() {
	return amountRPer;
}

/**
 * 設置屬性the sinosoft amount r per.
 * 
 * @param amountRPer
 *            待設置的the sinosoft amount r per的值
 */
public void setAmountRPer(double amountRPer) {
	this.amountRPer = amountRPer;
}

}