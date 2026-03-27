package com.sinosoft.undwrt.common.util;

/**
 * <p>Title: 数据类的父类</p>
 * <p>Description:核保业务数据类的父类,主要进行业务属性描述，公共方法声明</p>
 * <p>Copyright: Copyright (c) 2005/7</p>
 * <p>Company: Sinosoft</p>
 * @author qinyongli
 * @modify by zhangruifeng 2008-2-20 reason:新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制
 * @modify by zhangruifeng 20080304  reason:增加对责任保险(涉外)的附加自留保额的单独控制
 * @add by gengxiaobo 20080326 起重机械综合保险高级核保条件
 * added by LanNing 20080421 1505每次事故赔偿限额
 * @added by gengxiaobo 20080604 1505增加最大车累计限额
 * @added by xuning 20080814 圆丰产品的质押核批权放在省公司
 * @added by zhangruifeng 20081126 增加手续费批改
 * @added by hanxiao      20090226 0125工程机械设备保险增加附加险保额核保因子
 * @added by liuwei 20090303 0911国内货运险（08版）增加附加险核保因子
 * added by xiongguojun 20090327 1506核乏料运输：累计责任限额
 * added by ruanzhongxi 20110726 新增保单停效批改因子
 * @version 1.0
 */
import java.util.*;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.DBManager;

/**
 * The Class BusinessData.
 */
public class BusinessData {
           
           /** 屬性業務號. */
           String iBusinessNo = "";
           
           /** 屬性The sinosoft temp policy no. */
           String tempPolicyNo = "";            //保单号（批单条件时使用）
           
           /** 屬性The sinosoft temp proposal no. */
           String tempProposalNo = "";          //投保单号（批单条件时使用）
           
           /** 屬性The sinosoft db manager. */
           DBManager dbManager = null;
           
           /** 屬性The sinosoft contractno. */
           String contractno = "";              //合同号   
           
           /** 屬性險類代碼. */
           String classCode = "";               //险类 
           
           /** 屬性險種代碼. */
           String riskCode = "0000";            //险种
           
           /** 屬性The sinosoft risk kind. */
           String riskKind = "";                //险别
           
           /** 屬性The sinosoft currency. */
           String currency = "";                //币别信息
           
           /** 屬性The sinosoft business nature. */
           String businessNature = "";          //业务属性--代理
           
           /** 屬性The sinosoft discount. */
           double  discount = -1;               //折扣率
           
           /** 屬性The sinosoft dis rate1. */
           double disRate1 = -1;                //中间成本***********************多模板可以公用
           
           /** 屬性The sinosoft dis rate. */
           double disRate = -1;                 //手续费比例经纪费比例**************多模板可以公用
		   
   		/** 屬性The sinosoft unit policy. */
   		String unitPolicy = "";              //联(共)保标志
		   
   		/** 屬性The sinosoft temp policy. */
   		String tempPolicy = "";              //暂保单
           
           /** 屬性The sinosoft out lay rate. */
           double outLayRate = -1;              //净费率
           
           /** 屬性The sinosoft sum amount. */
           double sumAmount = -1;               //最大保额/赔偿限额
           
           /** 屬性The sinosoft sum amount p. */
           double sumAmountP = -1;              //最大保额/赔偿限额(批改用)
           
           /** 屬性The sinosoft sum amount c. */
           double sumAmountC = -1;              //最大保额/赔偿限额(CNY)
           
           /** 屬性The sinosoft sum amount cp. */
           double sumAmountCP = -1;             //最大保额/赔偿限额(批改用)(CNY)
           
           /** 屬性The sinosoft sum amount u. */
           double sumAmountU = -1;              //最大保额/赔偿限额(USD)
           
           /** 屬性The sinosoft sum amount up. */
           double sumAmountUP = -1;             //最大保额/赔偿限额(批改用)(USD)
           
           /** 屬性The sinosoft chg amount. */
           double chgAmount = -1;               //总保额变化量
           
           /** 屬性The sinosoft Sum premium. */
           double SumPremium = -1;              //折后保费
           
           /** 屬性The sinosoft true sum premium. */
           double trueSumPremium = -1;          //基准保费
           
           /** 屬性The sinosoft Direct day. */
           double DirectDay = -1;              	//直接倒签单天数
           
           /** 屬性The sinosoft Agent day. */
           double AgentDay = -1;                //代理倒签单天数
	       //int gracePeriodDays = 0;           //交费宽限期天数
           /** 屬性The sinosoft write off days. */
       	double writeOffDays = -1;            //允许保单注销天数
           
           /** 屬性The sinosoft undo contract date. */
           double undoContractDate = -1 ;       //解除合同权限天数
           
		   /** 屬性The sinosoft operate date. */
   		Date operateDate = new DateTime();   //签单日期-车险
		   
   		/** 屬性The sinosoft sign date. */
   		Date signDate = new DateTime();      //签单日期-非车险
		   
   		/** 屬性起始日期. */
   		Date startDate = new DateTime();     //保险起期
		   
   		/** 屬性終止日期. */
   		Date endDate = new DateTime();       //保险止期
		   
   		/** 屬性The sinosoft valid date. */
   		Date validDate = new DateTime();     //最近的提交核保日期
		   
   		/** 屬性The sinosoft flowintime. */
   		Date flowintime = new DateTime();    //最近的提交核保日期
		   //批改用--批单的核保级别要高于或者等于保单的核保级别。
		   /** 屬性The sinosoft Nodeno old. */
   		double NodenoOld = -1;               //原保单的核保级别
		   
   		/** 屬性The sinosoft Node name old. */
   		String NodeNameOld = "";           	//原保单的核保级别的名称
		   
   		/** 屬性The sinosoft Nodeno. */
   		double Nodeno = -1;                	//当前
		   
   		/** 屬性The sinosoft Node name. */
   		String NodeName = "";              	//当前核保级别的名称
		   
   		/** 屬性The sinosoft Chg premium. */
   		double ChgPremium = -1 ;
		   
   		/** 屬性The sinosoft Endor dis rate. */
   		double EndorDisRate = -1 ;           //手续费批改
		   
           
           /** *************************车险特殊************. */
           String permitBidding = "N";          //允许招标
           //double businessNature = -1;        //业务属性
           /** 屬性The sinosoft bidding down proportion. */
           double biddingDownProportion = -1;   //招标系数下浮比例
           
           /** 屬性The sinosoft group car sum. */
           double groupCarSum = -1;             //团购车的数量
           
           /** 屬性The sinosoft insured type code. */
           String insuredTypeCode = "";         //客户性质
           
           /** 屬性The sinosoft use nature code. */
           String useNatureCode = "";           //使用性质
           
           /** 屬性The sinosoft model code. */
           String modelCode = "";               //车型信息
           
           /** 屬性The sinosoft ton count h0. */
           double tonCountH0 = -1;				//货车的核定载质量
           
           /** 屬性The sinosoft Choose a price. */
           double ChooseAPrice = -1;			//投保车损险的新车购置价
           
           /** 屬性The sinosoft use years. */
           double useYears = -1;                //使用年限
           
           /** 屬性The sinosoft Only ab years. */
           double OnlyABYears = -1;             //使用年限
           
           /** 屬性The sinosoft Choose g1 years. */
           double ChooseG1Years = -1;           //投保盗抢险且选择不计免赔的车辆使用年限
           
           /** 屬性The sinosoft Choose l1 years. */
           double ChooseL1Years = -1;           //投保划痕险且选择不计免赔的车辆使用年限
           
           /** 屬性The sinosoft Choose lp a4 years. */
           double ChooseLPA4Years = -1;			//投保专修厂维修特约险或零配件更换险的车辆使用年限
           
           /** 屬性The sinosoft Choose n a0 years. */
           double ChooseNA0Years = -1;			//非营业客车使用年限
           
           /** 屬性The sinosoft Choose n h0 years. */
           double ChooseNH0Years = -1;			//非营业货车使用年限
           
           /** 屬性The sinosoft Choose y years. */
           double ChooseYYears = -1;			//营业用车使用年限
           
           /** 屬性The sinosoft Choose j years. */
           double ChooseJYears = -1;			//家庭自用汽车使用年限
           
           /** 屬性The sinosoft Choose e years. */
           double ChooseEYears = -1;			//投保自燃险车龄年限
           
           /** 屬性The sinosoft Choose ej years. */
           double ChooseEJYears = -1;			//投保自燃险的家用车车龄年限
           
           /** 屬性The sinosoft Choose zf. */
           String ChooseZF = "";				//投保指定附加险
           
           /** 屬性The sinosoft Choose td. */
           String ChooseTD = "";				//投保特定条款
           
           /** 屬性The sinosoft Not choose b. */
           String NotChooseB = "";				//未投保第三者责任险的投保单
           
           /** 屬性The sinosoft amount new. */
           double amountNew = -1;               //新增设备保额
           
           /** 屬性The sinosoft Amount man sum. */
           double AmountManSum = -1;			//车上人员责任险总保额
           
           /** 屬性The sinosoft amount a. */
           double amountA = -1;                 //车辆损失险
           
           /** 屬性The sinosoft amount g. */
           double amountG = -1;            		//全车盗抢险
           
           /** 屬性The sinosoft amount z. */
           double amountZ = -1;            		//自燃损失险
		   
   		/** 屬性The sinosoft amount b. */
   		double amountB = -1;           		//第三者责任险综合
		   
   		/** 屬性The sinosoft amount d11. */
   		double amountD11 = -1;           	//车上人员责任
		   
   		/** 屬性The sinosoft amount d12. */
   		double amountD12 = -1;           	//车上人员责任
		   
   		/** 屬性The sinosoft amount d2. */
   		double amountD2 = -1;           		//车上货物责任险
		   
   		/** 屬性The sinosoft amount w. */
   		double amountW = -1;           		//随行物品损失责任险保额
		   
   		/** 屬性The sinosoft amount g1. */
   		double amountG1 = -1;           		//特种车盗抢险保额
		   
   		/** 屬性The sinosoft amount a g1. */
   		double amountAG1 = -1;           	//特种车车损险保额
		   
   		/** 屬性The sinosoft amount b g1. */
   		double amountBG1 = -1;           	//特种车商三保额
		   
   		/** 屬性The sinosoft Suttle amount a. */
   		double SuttleAmountA = -1D;      	//增加车损险净自留额
		   
   		/** 屬性The sinosoft Suttle amount b. */
   		double SuttleAmountB = -1D;     		//增加三者险净自留额
		   
   		/** 屬性The sinosoft Amount d11 mj. */
   		private double AmountD11MJ = -1D;	//摩托车、拖拉机车上人员责任险保额(每座)
		   
   		/** 屬性The sinosoft amount gmj. */
   		private double amountGMJ = -1D;		//摩托车、拖拉机盗抢险保额
		   
   		/** 屬性The sinosoft amount amj. */
   		private double amountAMJ = -1D;		//摩托车、拖拉机车损险保额
		   
   		/** 屬性The sinosoft amount bmj. */
   		private double amountBMJ = -1D;		//摩托车、拖拉机三者险保额
		   
		   //(批改用:用于存储保单的原值，从c表取值
           /** 屬性The sinosoft amount ap. */
   		double amountAP = -1;            	//车辆损失险(批改用)
           
           /** 屬性The sinosoft amount gp. */
           double amountGP = -1;            	//全车盗抢险(批改用)
           
           /** 屬性The sinosoft amount zp. */
           double amountZP = -1;            	//自燃损失险(批改用)
		   
   		/** 屬性The sinosoft amount bp. */
   		double amountBP = -1;           		//第三者责任险(批改用)
		   
   		/** 屬性The sinosoft amount d1 p. */
   		double amountD1P = -1;           	//车上人员责任(批改用)
		   
   		/** 屬性The sinosoft amount d2 p. */
   		double amountD2P = -1;           	//车上货物责任险(批改用)
		   
   		/** 屬性The sinosoft prepay protocol. */
   		String prepayProtocol = "";          //预约协议
		   
   		/** 屬性The sinosoft amount wp. */
   		double amountWP = -1;           		//随行物品损失责任险保额
		   
   		/** 屬性The sinosoft Amount man sum p. */
   		double AmountManSumP = -1;			//车上人员责任险总保额
		   
   		/** 屬性The sinosoft Endorse power. */
   		String EndorsePower = ""; 			//批单权限
	       
       	/** *****非车非意************************. */
		   double LimitAcc12 = -1;              //每次事故赔偿限额
		   
   		/** 屬性The sinosoft Limit acc03. */
   		double LimitAcc03 = -1;				//每次事故财产损失赔偿限额 
		   
   		/** 屬性The sinosoft limit amount02. */
   		double limitAmount02 = -1 ;         	//每次事故赔偿限额
		   
   		/** 屬性The sinosoft limit amount03. */
   		double limitAmount03 = -1 ;          //累计赔偿限额
		   
   		/** 屬性The sinosoft limit amount46. */
   		double limitAmount46 = -1 ;			//累计赔偿限额(旅行社责任险)
		   
   		/** 屬性The sinosoft limit amount51. */
   		double limitAmount51 = -1 ;			//每人赔偿限额(旅行社责任险)
		   
   		/** 屬性The sinosoft limit amount66. */
   		double limitAmount66 = -1 ;			//每人赔偿限额
		   
   		/** 屬性The sinosoft limit acc12. */
   		double limitAcc12 = -1;                
		   
   		/** 屬性The sinosoft limit man acc01. */
   		double limitManAcc01 = -1;           //每人伤亡赔偿限额
		   
   		/** 屬性The sinosoft limit man acc05. */
   		double limitManAcc05 = -1;           //每次事故每人人身伤亡赔偿限额
		   
   		/** 屬性The sinosoft third limit sum07. */
   		double thirdLimitSum07 = -1;         //07三者责任累计限额
		   
   		/** 屬性The sinosoft third limit acc07. */
   		double thirdLimitAcc07 = -1;         //07三者责任每次限额
		   
   		/** 屬性The sinosoft amount per03010001. */
   		double amountPer03010001 = -1;       //0301房屋及附属设备保险金额/每户
		   
   		/** 屬性The sinosoft amount per03010002. */
   		double amountPer03010002 = -1;       //0301室内装潢保险金额/每户
		   
   		/** 屬性The sinosoft amount per9000452. */
   		double amountPer9000452 = -1;     	//0301家庭财产保险附加险 附加盗抢保险条款保额
		   
   		/** 屬性The sinosoft amount per9000453. */
   		double amountPer9000453 = -1;     	//0301家庭财产保险附加险 附加家用电器用电安全保险条款保额
		   
   		/** 屬性The sinosoft amount per9000454. */
   		double amountPer9000454 = -1;     	//0301家庭财产保险附加险 附加管道破裂及水渍保险条款保额
		   
   		/** 屬性The sinosoft amount per9000449. */
   		double amountPer9000449 = -1;     	//0301家庭财产保险附加险 附加居家责任保险条款保额
		   
   		/** 屬性The sinosoft amount per9000450. */
   		double amountPer9000450 = -1;    	//0301家庭财产保险附加险 附加家庭伤害保险条款保额
		   
   		/** 屬性The sinosoft amount per9000451. */
   		double amountPer9000451 = -1;     	//0301家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
		   
   		/** 屬性The sinosoft amount per9000455. */
   		double amountPer9000455 = -1;     	//0310附加住宅火灾事故延烧自有车辆保险
		   
   		/** 屬性The sinosoft amount per9000456. */
   		double amountPer9000456 = -1;     	//0310附加租房费用损失保险
		   
   		/** 屬性The sinosoft amount per9000457. */
   		double amountPer9000457 = -1;     	//0310附加家庭火灾火场清理费用损失保险
		   
   		/** 屬性The sinosoft amount per9000458. */
   		double amountPer9000458 = -1;     	//0310附加搬迁费用损失保险
		   
   		/** 屬性The sinosoft amount per9000455 p. */
   		double amountPer9000455P = -1;     	//0310附加住宅火灾事故延烧自有车辆保险
		   
   		/** 屬性The sinosoft amount per9000456 p. */
   		double amountPer9000456P = -1;     	//0310附加租房费用损失保险
		   
   		/** 屬性The sinosoft amount per9000457 p. */
   		double amountPer9000457P = -1;     	//0310附加家庭火灾火场清理费用损失保险
		   
   		/** 屬性The sinosoft amount per9000458 p. */
   		double amountPer9000458P = -1;     	//0310附加搬迁费用损失保险
		   
   		/** 屬性The sinosoft unit amount03. */
   		double unitAmount03 = -1;            //add by hanxiao 20091027 家财险每人保额
		   
   		/** 屬性The sinosoft plus rate. */
   		double plusRate         = -1;        //10险种用--加成比例
		   
   		/** 屬性The sinosoft ship age. */
   		double shipAge = -1;                 //船龄(进出口货运用)
		   
   		/** 屬性The sinosoft ship age1001. */
   		String shipAge1001 = "";				//1001
		   
   		/** 屬性The sinosoft Limit cargo acc. */
   		double LimitCargoAcc = -1;           //1506货物责任：每次事故责任限额
		   
   		/** 屬性The sinosoft Limit third acc. */
   		double LimitThirdAcc = -1;           //1506第三者责任：每次事故责任限额
		   
   		/** 屬性The sinosoft Limit third acc2. */
   		double LimitThirdAcc2 = -1;          //1506除污费用：每次事故赔偿限额
		   
   		/** 屬性The sinosoft Limit third acc4. */
   		double LimitThirdAcc4 = -1;          //1506核乏料运输：累计责任限额
		   
   		/** 屬性The sinosoft Limit third acc b. */
   		double LimitThirdAccB = -1;          //附加第三者责任：每次事故责任限额
		   
   		/** 屬性The sinosoft Limit acc1. */
   		double LimitAcc1 = -1;               //1515.1526每次事故赔偿限额(国内)
		   
   		/** 屬性The sinosoft Limit acc2. */
   		double LimitAcc2 = -1;               //1515.1526每次事故赔偿限额(世界范围除美加)
		   
   		/** 屬性The sinosoft Limit acc3. */
   		double LimitAcc3 = -1;               //1515.1526每次事故赔偿限额(世界范围含美加)
		   
   		/** 屬性The sinosoft Sum amount1. */
   		double SumAmount1 = -1;              //1515.1526累计赔偿限额(国内)
		   
   		/** 屬性The sinosoft Sum amount2. */
   		double SumAmount2 = -1;              //1515.1526累计赔偿限额(世界范围除美加)
		   
   		/** 屬性The sinosoft Sum amount3. */
   		double SumAmount3 = -1;              //1515.1526累计赔偿限额(世界范围含美加)
		   
   		/** 屬性The sinosoft Limit man acc11. */
   		double LimitManAcc11 = -1;           //每人人身伤亡赔偿限额
		   
   		/** 屬性The sinosoft Limit man acc12. */
   		double LimitManAcc12 = -1;           //每人人身伤亡赔偿限额
		   
   		/** 屬性The sinosoft Limit man acc13. */
   		double LimitManAcc13 = -1;           //每人意外上海医疗费用
		   
   		/** 屬性The sinosoft Limit man acc14. */
   		double LimitManAcc14 = -1;           //每人意外上海医疗费用
		   
		   //(批改用:用于存储保单的原值，从c表取值
		   /** 屬性The sinosoft limit acc12 p. */
   		double limitAcc12P = -1 ;            //每次事故赔偿限额
		   
   		/** 屬性The sinosoft Limit acc03 p. */
   		double LimitAcc03P = -1;				//每次事故财产损失赔偿限额
		   
   		/** 屬性The sinosoft limit man acc01 p. */
   		double limitManAcc01P = -1;          //每人伤亡赔偿限额
		   
   		/** 屬性The sinosoft limit man acc05 p. */
   		double limitManAcc05P = -1;          //每次事故每人人身伤亡赔偿限额
		   
   		/** 屬性The sinosoft third limit sum07 p. */
   		double thirdLimitSum07P = -1;        //07三者责任累计限额
		   
   		/** 屬性The sinosoft third limit acc07 p. */
   		double thirdLimitAcc07P = -1;        //07三者责任每次限额
		   
   		/** 屬性The sinosoft amount per03010001 p. */
   		double amountPer03010001P = -1;      //0301房屋及附属设备保险金额/每户
		   
   		/** 屬性The sinosoft amount per03010002 p. */
   		double amountPer03010002P = -1;      //0301室内装潢保险金额/每户
		   
   		/** 屬性The sinosoft amount per9000452 p. */
   		double amountPer9000452P = -1;     	//0301家庭财产保险附加险 附加盗抢保险条款保额
		   
   		/** 屬性The sinosoft amount per9000453 p. */
   		double amountPer9000453P = -1;     	//0301家庭财产保险附加险 附加家用电器用电安全保险条款保额
		   
   		/** 屬性The sinosoft amount per9000454 p. */
   		double amountPer9000454P = -1;     	//0301家庭财产保险附加险 附加管道破裂及水渍保险条款保额
		   
   		/** 屬性The sinosoft amount per9000449 p. */
   		double amountPer9000449P = -1;     	//0301家庭财产保险附加险 附加居家责任保险条款保额
		   
   		/** 屬性The sinosoft amount per9000450 p. */
   		double amountPer9000450P = -1;     	//0301家庭财产保险附加险 附加家庭伤害保险条款保额
		   
   		/** 屬性The sinosoft amount per9000451 p. */
   		double amountPer9000451P = -1;     	//0301家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
		   
   		/** 屬性The sinosoft unit amount03 p. */
   		double unitAmount03P = -1;
		   
   		/** 屬性The sinosoft plus rate p. */
   		double plusRateP         = -1;       //10险种用--加成比例		   
		   
   		/** 屬性The sinosoft ship age p. */
   		double shipAgeP = -1;                //船龄(进出口货运用)
		   
   		/** 屬性The sinosoft Amount. */
   		double Amount = -1;                  //0108硬件损失险保额
		   //double LimitManAcc = -1 ;          //0108数据复制费用每次赔偿限额
		   /** 屬性The sinosoft Limit man acc. */
   		double LimitManAcc = -1 ;            //0125、0127第三者责任每次事故赔偿限额
		   //double SumAmount08 = -1;           //0108累计赔偿限额
		   /** 屬性The sinosoft Sum amount08. */
   		double SumAmount08 = -1;             //0125、0127第三者累计赔偿限额
		   
   		/** 屬性The sinosoft Third limit acc07. */
   		double ThirdLimitAcc07 = -1;         //07第三者责任每次事故赔偿限额
		   
   		/** 屬性The sinosoft Third limit sum07. */
   		double ThirdLimitSum07 =-1;          //第三者累计赔偿限额
		   
   		/** 屬性The sinosoft Sum amount0145100. */
   		double SumAmount0145100 = -1;        //0125工程机械设备保险附加险 附加自燃损失保险条款保额
		   
   		/** 屬性The sinosoft Sum amount0145200. */
   		double SumAmount0145200 = -1;        //0125工程机械设备保险附加险 附加第三者责任保险条款保额
		   
   		/** 屬性The sinosoft Sum amount0145300. */
   		double SumAmount0145300 = -1;        //0125工程机械设备保险附加 附加全车盗抢保险条款保额
		   
   		/** 屬性The sinosoft Sum amount0145400. */
   		double SumAmount0145400 = -1;        //0125工程机械设备保险附加险 附加工程机械设备操作人员责任保险条款保额
		   
   		/** 屬性The sinosoft Sum amount0145500. */
   		double SumAmount0145500 = -1;        //0125工程机械设备保险附加险 附加碰撞、倾覆保险保额
		   
		   /*组合险特殊险种公路综合险*/
		   /** 屬性The sinosoft sum amount2300200. */
   		double sumAmount2300200 = -1D; 		// 公路财产保险保险金额
		   
   		/** 屬性The sinosoft sum amount2300400. */
   		double sumAmount2300400 = -1D; 		// 公众责任保险保险金额	
		   
   		/** 屬性The sinosoft sum amount2300500. */
   		double sumAmount2300500 = -1D; 		// 雇主责任保险保险金额
		   
   		/** 屬性The sinosoft sum amount2300600. */
   		double sumAmount2300600 = -1D; 		// 现金保险保险金额	
		   
   		/** 屬性The sinosoft limit2300500. */
   		double limit2300500 = 0; 			// 雇主责任保险每人每次事故赔偿限额	
		   
		   /*---add by gengxiaobo 20080326 组合险特殊险种起重机械综合保险高级条件因子--*/       
		   /** 屬性The sinosoft sum amount2301500. */
   		double sumAmount2301500 = -1D; 		// 财产损失保险保险金额
		   
   		/** 屬性The sinosoft sum amount2301600. */
   		double sumAmount2301600 = -1D; 		// 第三者责任保险保险金额	
		   
   		/** 屬性The sinosoft sum amount2301800. */
   		double sumAmount2301800 = -1D; 		// 雇主责任保险保险金额 		   
		   
		   //add by hanxiao 组合险2310
		   /** 屬性The sinosoft sum amount0300100. */
   		double sumAmount0300100 = -1D;
		   
   		/** 屬性The sinosoft sum amount0300100 p. */
   		double sumAmount0300100P = -1D;
		   
   		/** 屬性The sinosoft sum amount yl. */
   		double sumAmountYL = -1D; 			//23意外伤害医疗
		   
   		/** 屬性The sinosoft sum amount ylp. */
   		double sumAmountYLP = -1D; 			//23意外伤害医疗(批改用) 暂时不用
		   
   		/** 屬性The sinosoft sumquantity. */
   		double sumquantity = 1; 				//23险种的份数
		   
   		/** 屬性The sinosoft Amount per. */
   		double AmountPer = -1D;				//室内装潢/家用电器/衣物床上用品/家具及其他保险金额/每户
		   
   		/** *******意健险************. */
		   ArrayList ageScope = new ArrayList() ;//年龄范围
		   
   		/** 屬性The sinosoft unit proportion. */
   		double unitProportion = -1 ;         //团单比例
		   
   		/** 屬性The sinosoft unit amount. */
   		double unitAmount = -1;              //意健险团险保额
		   
   		/** 屬性The sinosoft unit amount c. */
   		double unitAmountC = -1;             //意健险团险保额
		   
   		/** 屬性The sinosoft db pml. */
   		double dbPML =-1;      				//PML值
		   
   		/** 屬性The sinosoft real pay flag. */
   		int realPayFlag = -1;     			//保费是否实:-1为未缴费，0为未缴全，1为缴全
		   
   		/** 屬性The sinosoft policy sort. */
   		String policySort = "";  			//增加保单种类（0520使用，区分定额、非定额）
		   
   		/** 屬性The sinosoft exch rate. */
   		double exchRate = -1;                //币种兑换汇率
		   
   		/** 屬性The sinosoft expense fee rate. */
   		double expenseFeeRate = -1;			//费用比例
		   
   		/** 屬性The sinosoft Occupation code. */
   		String OccupationCode = "";  		//职业(工种)代码
		   
   		/** 屬性The sinosoft Occupation flag. */
   		String OccupationFlag = "";  		//职业(工种)代码类别
		   
   		/** 屬性The sinosoft Sum amount. */
   		double SumAmount = -1;    			//累计赔偿限额
		   
   		/** 屬性The sinosoft Sum amount per. */
   		double SumAmountPer = 0D;			//每人保险金额
		   
   		/** 屬性The sinosoft Sum amount per1. */
   		double SumAmountPer1 = 0D;			//每人保险金额(一类职业)
		   
   		/** 屬性The sinosoft Sum amount per2. */
   		double SumAmountPer2 = 0D;			//每人保险金额(一类职业)
		   
   		/** 屬性The sinosoft Sum amount per3. */
   		double SumAmountPer3 = 0D;			//每人保险金额(一类职业)
		   
   		/** 屬性The sinosoft Sum amount per4. */
   		double SumAmountPer4 = 0D;			//每人保险金额(一类职业)
		   
   		/** 屬性The sinosoft Sum amount per5. */
   		double SumAmountPer5 = 0D;			//每人保险金额(一类职业)
		   
   		/** 屬性The sinosoft Sum amount per6. */
   		double SumAmountPer6 = 0D;			//每人保险金额(一类职业)
		    
		   /** 屬性The sinosoft Sub amount per01. */
   		double SubAmountPer01 = 0D;			//意外伤害医疗费用(每人保险金额)
		   
   		/** 屬性The sinosoft Sub amount per02. */
   		double SubAmountPer02 = 0D;			//意外伤害生活津贴(每日津贴金额)
		   
   		/** 屬性The sinosoft Sub amount per03. */
   		double SubAmountPer03 = 0D;			//学生幼儿意外伤害医疗(每人保险金额)
		   
   		/** 屬性The sinosoft Sum amount per01. */
   		double SumAmountPer01 = 0D;			//每人保险金额(高管) 
		   
   		/** 屬性The sinosoft Sum amount per02. */
   		double SumAmountPer02 = 0D;			//每人保险金额(文职) 
		   
   		/** 屬性The sinosoft Sum amount per03. */
   		double SumAmountPer03 = 0D;			//每人保险金额(其他) 
		   
   		/** 屬性The sinosoft Sum amount per04. */
   		double SumAmountPer04 = 0D;			//每人保险金额(国内) 
		   
   		/** 屬性The sinosoft Sum amount per05. */
   		double SumAmountPer05 = 0D;			//每人保险金额(出境) 

		   /** *******圆丰产品************. */
		   /*---add by xuning 20080814 圆丰产品的质押核批权放在省公司--*/       
		   String Mortgage = "N"; 				//初始化为没有审批权。
		   
           /** *******再保部分***************. */
		   String reinsUnit = "N";              //是否允许划分危险单位，N-否，Y-是
		   
   		/** 屬性The sinosoft trial premium. */
   		double trialPremium = -1;			//附加自留保费
		   
   		/** 屬性The sinosoft trial amount. */
   		double trialAmount = -1;             //附加自留保额
		   
   		/** 屬性The sinosoft allow split. */
   		String allowSplit = "N";             //特约临分 N-否，Y-是
		   
   		/** 屬性The sinosoft short rate. */
   		double shortRate = -1;               //短期费率系数
		   /*车险可配置开始自动核保*/
		   /** 屬性The sinosoft Amount count. */
   		double AmountCount = -1D; 			// 保险金额、责任限额
		   
   		/** 屬性The sinosoft Kind code. */
   		String KindCode = "";     			// 条款类别
		   
   		/** 屬性The sinosoft Kind c name. */
   		String KindCName = "";	 			// 条款名称	
		   
   		/** 屬性The sinosoft Car kind code. */
   		String CarKindCode = "";  			// 车辆类型
		   
   		/** 屬性The sinosoft Seat count. */
   		int SeatCount= -1;        			// 核定座位
		   
   		/** 屬性The sinosoft Ton count. */
   		double TonCount =-1D;    			// 核定质量
		   
   		/** 屬性用戶代碼. */
   		String userCode = "";    			//出单员代码
		   
   		/** 屬性機構代碼. */
   		String comCode = "";     			//出单机构
		   
   		/** 屬性The sinosoft amount l. */
   		double amountL = -1;     			//车身划痕保险金额
		   
   		/** 屬性The sinosoft amount r. */
   		double amountR = -1;					//交通事故精神损害赔偿责任险保额
		   
   		/** 屬性The sinosoft amount r per. */
   		double amountRPer = -1;				//交通事故精神损害赔偿责任险每人每次限额
		   
   		/** 屬性The sinosoft Amount h0 upp. */
   		double AmountH0Upp = 0; 				//n吨以上货车保险金额
		   
   		/** 屬性The sinosoft Amount h0 low. */
   		double AmountH0Low = 0; 				//n吨以下货车保险金额
		   
   		/** 屬性The sinosoft Amount h0 upp a. */
   		double AmountH0UppA = 0; 			//n吨以上货车车损险保险金额
		   
   		/** 屬性The sinosoft Amount h0 low a. */
   		double AmountH0LowA = 0; 			//n吨以下货车车损险保险金额
		   
   		/** 屬性The sinosoft Amount nx. */
   		double AmountNX = -1;     			//新增设备保险金额
		   
   		/** 屬性The sinosoft Amount ny. */
   		double AmountNY = -1;     			//新增设备保险金额
		   
   		/** 屬性The sinosoft Quantity. */
   		double Quantity = -1D;    			//车上人员责任险座位数
		   
   		/** 屬性The sinosoft Regist model code. */
   		String RegistModelCode ="" ; 		//行使证车型  用于判断自动核保
		   
   		/** 屬性The sinosoft License no. */
   		String LicenseNo = "";      			//车牌号用于自动核保
		   
   		/** 屬性The sinosoft License no p. */
   		String LicenseNoP = "";      		//批单车牌号
		   
   		/** 屬性The sinosoft Appli linker name. */
   		String AppliLinkerName = ""; 		//被保险人姓名 用于自动核保
		   
   		/** 屬性The sinosoft CB year limit. */
   		int CBYearLimit = -1;   				//新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制
		   
   		/** 屬性The sinosoft CB month limit. */
   		int CBMonthLimit = -1;				//车险（0501）增加保险期限的控制
		   
   		/** 屬性The sinosoft Tui bao premium. */
   		double TuiBaoPremium = -1D; 			//批退时退保的保费   
		   //added by LanNing begin 20080225 投资金产品调整
		   /** 屬性The sinosoft Investment. */
   		double Investment = -1D;
		   //added by LanNing end 20080225 投资金产品调整
		   /** 屬性The sinosoft is new risk evaluate. */
   		boolean isNewRiskEvaluate = false; 	//add by zhangruifeng 判断非车企财险、建工险批改时是否重新进行了风险评估
		   
		   //added by LanNing begin 20080421 1505每次事故赔偿限额
		   /** 屬性The sinosoft Limit02 fee1505. */
   		double Limit02Fee1505 = -1D;		   
		   //added by LanNing end 20080421 1505每次事故赔偿限额
		   //added by gengxiaobo begin 20080604 增加最大车累记赔偿限额。
		   /** 屬性The sinosoft dou staff count1505. */
   		double douStaffCount1505 =  -1D;
		   
   		/** 屬性The sinosoft dou pre turn over1505. */
   		double douPreTurnOver1505 =  -1D;
		   
   		/** 屬性The sinosoft Limit03 fee1505. */
   		double Limit03Fee1505 =  -1D;
		   
   		/** 屬性The sinosoft max capacity1505. */
   		double maxCapacity1505 =  -1D;
		   //added by gengxiaobo begin 20080604 增加最大车累记赔偿限额。
		   //added by gengxiaobo begin 20080618 增加批改类型。
		   /** 屬性The sinosoft str endortype. */
   		String strEndortype = "";
		   //added by gengxiaobo end 20080618 增加批改类型。
		   
		   //added by liuwei begin 20090303 0911国内货运险（08版）增加附加险核保因子
		   /** 屬性The sinosoft Allow0911700. */
   		String Allow0911700 = "N";
		   //added by liuwei end
		   
		   /** 屬性The sinosoft Limit fee one car. */
   		double LimitFeeOneCar = -1D;
		   
   		/** 屬性The sinosoft productcode. */
   		String productcode = "";
		   
   		/** 屬性The sinosoft Policy type0902. */
   		String PolicyType0902 = "N";			//0902补录保单核保因子
		   
		    //added by zhouhui begin 20090616 2710批改保险期限时，短期费率标志为3时，只能1c级以上才能核过
		      /** 屬性The sinosoft Short rate flag. */
    		String ShortRateFlag = "";
			 //added by zhouhui end
		   //add by zhaoning20091125 begin
		   /** 屬性The sinosoft danger unit count. */
 			int dangerUnitCount = 1;//危险单位条数
		   //add by zhaoning20091125 end
		   //add by zhaoning20100129 begin Reason:2010年非车险核保权限
		   /** 屬性The sinosoft sum amount0400600. */
   		double sumAmount0400600 = -1D; 		// 财产损失保险保险金额
		   
   		/** 屬性The sinosoft sum amount0400700. */
   		double sumAmount0400700 = -1D; 		// 还贷保证保险保险金额
		   
   		/** 屬性The sinosoft Limit man heal01. */
   		double LimitManHeal01 = -1D;			//每人医疗费用赔偿限额
		   
   		/** 屬性The sinosoft cover note flag. */
   		String coverNoteFlag = "N";			//暂保单标志(是否是暂保单,N不是Y是)
		   //add by zhaoning20100129 end
		   
		    // 3001险种新增险别因子 begin
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
			
			// 保单停效批改因子 add by ruanzhongxi_20110726 begin
			/** 屬性The sinosoft unionpay count. */
			int unionpayCount = 0;
			//保单停效批改因子 add by ruanzhongxi_20110726 end
		   
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
			//返回险种
			/**
			 * 獲取屬性險種代碼.
			 * 
			 * @return 屬性險種代碼的值
			 */
			public String  getRiskCode(){
				  return riskCode;
			}
		    //返回币别信息
			/**
			 * 獲取屬性the sinosoft currency.
			 * 
			 * @return 屬性the sinosoft currency的值
			 */
    		public String  getCurrency(){
				  return currency;
			}
		    //返回最大保额/赔偿限额
			/**
			 * 獲取屬性the sinosoft sum amount.
			 * 
			 * @return 屬性the sinosoft sum amount的值
			 */
    		public double  getSumAmount(){
				  return sumAmount;
			}
            //返回业务类型
			/**
			 * 獲取屬性the sinosoft business nature.
			 * 
			 * @return 屬性the sinosoft business nature的值
			 */
            public String  getBusinessNature(){
				  return businessNature;
			}
			//折扣率
			/**
			 * 獲取屬性the sinosoft discount.
			 * 
			 * @return 屬性the sinosoft discount的值
			 */
			public double  getDiscount(){
				  return discount;
			}
			//中间成本
			/**
			 * 獲取屬性the sinosoft dis rate1.
			 * 
			 * @return 屬性the sinosoft dis rate1的值
			 */
			public double  getDisRate1(){
				  return disRate1;
			}
		    //手续费比例经纪费比例
			/**
			 * 獲取屬性the sinosoft dis rate.
			 * 
			 * @return 屬性the sinosoft dis rate的值
			 */
    		public double  getDisRate(){
				  return disRate;
			}
			//返回是否允许招标信息
			/**
			 * Gets the *************************车险特殊************.
			 * 
			 * @return the *************************车险特殊************
			 */
			public String getPermitBidding() throws Exception{
				return  permitBidding;
			}
			//返回团购车的数量 
			/**
			 * 獲取屬性the sinosoft group car sum.
			 * 
			 * @return 屬性the sinosoft group car sum的值
			 */
			public double getGroupCarSum() throws Exception{
				return  groupCarSum;
			}
            //返回客户性质
			/**
			 * 獲取屬性the sinosoft insured type code.
			 * 
			 * @return 屬性the sinosoft insured type code的值
			 */
            public String getInsuredTypeCode() throws Exception{
				return  insuredTypeCode;
			}
			//返回使用性质
			/**
			 * 獲取屬性the sinosoft use nature code.
			 * 
			 * @return 屬性the sinosoft use nature code的值
			 */
			public String getUseNatureCode() throws Exception{
				return  useNatureCode;
			}
			//返回车型信息
			/**
			 * 獲取屬性the sinosoft model code.
			 * 
			 * @return 屬性the sinosoft model code的值
			 */
			public String getModelCode() throws Exception{
				return  modelCode;
			}
			//返回使用年限
			/**
			 * 獲取屬性the sinosoft use years.
			 * 
			 * @return 屬性the sinosoft use years的值
			 */
			public double getUseYears() throws Exception{
				return  useYears;
			}	
            //返回险别
			/**
			 * 獲取屬性the sinosoft risk kind.
			 * 
			 * @return 屬性the sinosoft risk kind的值
			 */
            public String getRiskKind() throws Exception{	
				return  riskKind;
			}
			//返回净费率
			/**
			 * 獲取屬性the sinosoft out lay rate.
			 * 
			 * @return 屬性the sinosoft out lay rate的值
			 */
			public double getOutLayRate() throws Exception{
				return  outLayRate;
			}
            //直接倒签单天数
			/**
			 * 獲取屬性the sinosoft direct day.
			 * 
			 * @return 屬性the sinosoft direct day的值
			 * @throws Exception
			 *             the exception
			 */
            public double getDirectDay() throws Exception{
				return  DirectDay;
			}	
			//招标系数下浮比例
			/**
			 * 獲取屬性the sinosoft bidding down proportion.
			 * 
			 * @return 屬性the sinosoft bidding down proportion的值
			 */
			public double getBiddingDownProportion() throws Exception{
				return  biddingDownProportion;
			}	
			//允许保单注销天数
			/**
			 * 獲取屬性the sinosoft write off days.
			 * 
			 * @return 屬性the sinosoft write off days的值
			 */
			public double getWriteOffDays() throws Exception{
				return  writeOffDays;
			}	
            //返回车辆损失险保额
			/**
			 * 獲取屬性the sinosoft amount a.
			 * 
			 * @return 屬性the sinosoft amount a的值
			 */
            public double getAmountA() throws Exception{
				return  amountA;
			}	
            //返回全车盗抢险
			/**
			 * 獲取屬性the sinosoft amount g.
			 * 
			 * @return 屬性the sinosoft amount g的值
			 */
            public double getAmountG() throws Exception{
				return  amountG;
			}	
            //返回自燃损失险
			/**
			 * 獲取屬性the sinosoft amount z.
			 * 
			 * @return 屬性the sinosoft amount z的值
			 */
            public double getAmountZ() throws Exception{
				return  amountZ;
			} 
			//返回第三者综合险
			/**
			 * 獲取屬性the sinosoft amount b.
			 * 
			 * @return 屬性the sinosoft amount b的值
			 */
			public double getAmountB() throws Exception{
				return  amountB;
			}	
			//返回车上人员责任险（每座）
			/**
			 * 獲取屬性the sinosoft amount d11.
			 * 
			 * @return 屬性the sinosoft amount d11的值
			 */
			public double getAmountD11() throws Exception{
				return  amountD11;
			}
			
			/**
			 * 獲取屬性the sinosoft amount d12.
			 * 
			 * @return 屬性the sinosoft amount d12的值
			 */
			public double getAmountD12() throws Exception{
				return  amountD12;
			}
			//返回车上货物责任险
			/**
			 * 獲取屬性the sinosoft amount d2.
			 * 
			 * @return 屬性the sinosoft amount d2的值
			 */
			public double getAmountD2() throws Exception{
				return  amountD2;
			}
		    //联(共)保标志
			/**
			 * 獲取屬性the sinosoft unit policy.
			 * 
			 * @return 屬性the sinosoft unit policy的值
			 */
    		public String getUnitPolicy() throws Exception{
				return  unitPolicy;
			}	
		    //暂保单
			/**
			 * 獲取屬性the sinosoft temp policy.
			 * 
			 * @return 屬性the sinosoft temp policy的值
			 */
    		public String getTempPolicy() throws Exception{
				return  tempPolicy;
			}	
			//返回险类
			/**
			 * 獲取屬性險類代碼.
			 * 
			 * @return 屬性險類代碼的值
			 */
			public String getClassCode() throws Exception{
				return  classCode;
			}	
		    //每人事故赔偿限额
			/**
			 * 獲取屬性the sinosoft limit man acc01.
			 * 
			 * @return 屬性the sinosoft limit man acc01的值
			 */
    		public double getLimitManAcc01() throws Exception{
				return  limitManAcc01;
			}
		    //预约协议
			/**
			 * 獲取屬性the sinosoft prepay protocol.
			 * 
			 * @return 屬性the sinosoft prepay protocol的值
			 */
    		public String getPrepayProtocol() throws Exception{
				return  prepayProtocol;
			}
            //年龄范围
			/**
			 * Gets the *******意健险************.
			 * 
			 * @return the *******意健险************
			 */
            public ArrayList getAgeScope() throws Exception{
				return  ageScope;
			}
            //团单比例
			/**
			 * 獲取屬性the sinosoft unit proportion.
			 * 
			 * @return 屬性the sinosoft unit proportion的值
			 */
            public double getUnitProportion() throws Exception{
				return  unitProportion;
			}
            //团单保额
			/**
			 * 獲取屬性the sinosoft unit amount.
			 * 
			 * @return 屬性the sinosoft unit amount的值
			 */
            public double getUnitAmount() throws Exception{
				return  unitAmount;
			}
			//兑换汇率
			/**
			 * 獲取屬性the sinosoft exch rate.
			 * 
			 * @return 屬性the sinosoft exch rate的值
			 */
			public double getExchRate() throws Exception{
			    return exchRate;
			}
			//保单号（批单条件使用）
			/**
			 * 獲取屬性the sinosoft temp policy no.
			 * 
			 * @return 屬性the sinosoft temp policy no的值
			 */
			public String getTempPolicyNo() throws Exception{
				return tempPolicyNo;
			}

			//总折扣金额(保额变化量)（批单条件使用）
			/**
			 * 獲取屬性the sinosoft chg amount.
			 * 
			 * @return 屬性the sinosoft chg amount的值
			 */
			public double getChgAmount() throws Exception{
				return chgAmount;
			}
			//是否允许划分危险单位
			/**
			 * Gets the *******再保部分***************.
			 * 
			 * @return the *******再保部分***************
			 */
			public String getReinsUnit() throws Exception{
				return reinsUnit;
			}
			//附加自留保费
			/**
			 * 獲取屬性the sinosoft trial premium.
			 * 
			 * @return 屬性the sinosoft trial premium的值
			 */
			public double getTrialPremium() throws Exception{
				return trialPremium;
			}
			//附加自留保额
			/**
			 * 獲取屬性the sinosoft trial amount.
			 * 
			 * @return 屬性the sinosoft trial amount的值
			 */
			public double getTrialAmount() throws Exception{
				return trialAmount;
			}
			//特约临分
			/**
			 * 獲取屬性the sinosoft allow split.
			 * 
			 * @return 屬性the sinosoft allow split的值
			 */
			public String getAllowSplit() throws Exception{
				return allowSplit;
			}
			//短期费率系数
			/**
			 * 獲取屬性the sinosoft short rate.
			 * 
			 * @return 屬性the sinosoft short rate的值
			 */
			public double getShortRate() throws Exception{
				return shortRate;
			}

			//PML值
			/**
			 * 獲取屬性the sinosoft pml.
			 * 
			 * @return 屬性the sinosoft pml的值
			 * @throws Exception
			 *             the exception
			 */
			public double getPML() throws Exception{
				return dbPML;
			}
			//保费是否实收
			/**
			 * 獲取屬性the sinosoft real pay flag.
			 * 
			 * @return 屬性the sinosoft real pay flag的值
			 */
			public int getRealPayFlag() throws Exception{
				return realPayFlag;
			}
			
			/**
			 * 獲取屬性the sinosoft third limit sum07.
			 * 
			 * @return 屬性the sinosoft third limit sum07的值
			 */
			public double getThirdLimitSum07() throws Exception{
				return thirdLimitSum07;
			}
			
			/**
			 * 獲取屬性the sinosoft third limit acc07.
			 * 
			 * @return 屬性the sinosoft third limit acc07的值
			 */
			public double getThirdLimitAcc07() throws Exception{
				return thirdLimitAcc07;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per03010001.
			 * 
			 * @return 屬性the sinosoft amount per03010001的值
			 */
			public double getAmountPer03010001() throws Exception{
				return amountPer03010001;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per03010002.
			 * 
			 * @return 屬性the sinosoft amount per03010002的值
			 */
			public double getAmountPer03010002() throws Exception{
				return amountPer03010002;
			}
			
			/**
			 * 獲取屬性the sinosoft plus rate.
			 * 
			 * @return 屬性the sinosoft plus rate的值
			 */
			public double getPlusRate() throws Exception{
				return plusRate;
			}			

			/**
			 * 獲取屬性the sinosoft expense fee rate.
			 * 
			 * @return 屬性the sinosoft expense fee rate的值
			 */
			public double getExpenseFeeRate() throws Exception{
				return expenseFeeRate;
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
			 * 設置屬性the sinosoft limit man acc05.
			 * 
			 * @param limitManAcc05
			 *            待設置的the sinosoft limit man acc05的值
			 */
			public void setLimitManAcc05(double limitManAcc05) {
				this.limitManAcc05 = limitManAcc05;
			}
			
			/**
			 * 獲取屬性the sinosoft amount ap.
			 * 
			 * @return 屬性the sinosoft amount ap的值
			 */
			public double getAmountAP() {
				return amountAP;
			}
			
			/**
			 * 設置屬性the sinosoft amount ap.
			 * 
			 * @param amountAP
			 *            待設置的the sinosoft amount ap的值
			 */
			public void setAmountAP(double amountAP) {
				this.amountAP = amountAP;
			}
			
			/**
			 * 獲取屬性the sinosoft amount bp.
			 * 
			 * @return 屬性the sinosoft amount bp的值
			 */
			public double getAmountBP() {
				return amountBP;
			}
			
			/**
			 * 設置屬性the sinosoft amount bp.
			 * 
			 * @param amountBP
			 *            待設置的the sinosoft amount bp的值
			 */
			public void setAmountBP(double amountBP) {
				this.amountBP = amountBP;
			}
			
			/**
			 * 獲取屬性the sinosoft amount d1 p.
			 * 
			 * @return 屬性the sinosoft amount d1 p的值
			 */
			public double getAmountD1P() {
				return amountD1P;
			}
			
			/**
			 * 設置屬性the sinosoft amount d1 p.
			 * 
			 * @param amountD1P
			 *            待設置的the sinosoft amount d1 p的值
			 */
			public void setAmountD1P(double amountD1P) {
				this.amountD1P = amountD1P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount d2 p.
			 * 
			 * @return 屬性the sinosoft amount d2 p的值
			 */
			public double getAmountD2P() {
				return amountD2P;
			}
			
			/**
			 * 設置屬性the sinosoft amount d2 p.
			 * 
			 * @param amountD2P
			 *            待設置的the sinosoft amount d2 p的值
			 */
			public void setAmountD2P(double amountD2P) {
				this.amountD2P = amountD2P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount gp.
			 * 
			 * @return 屬性the sinosoft amount gp的值
			 */
			public double getAmountGP() {
				return amountGP;
			}
			
			/**
			 * 設置屬性the sinosoft amount gp.
			 * 
			 * @param amountGP
			 *            待設置的the sinosoft amount gp的值
			 */
			public void setAmountGP(double amountGP) {
				this.amountGP = amountGP;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per03010001 p.
			 * 
			 * @return 屬性the sinosoft amount per03010001 p的值
			 */
			public double getAmountPer03010001P() {
				return amountPer03010001P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per03010001 p.
			 * 
			 * @param amountPer03010001P
			 *            待設置的the sinosoft amount per03010001 p的值
			 */
			public void setAmountPer03010001P(double amountPer03010001P) {
				this.amountPer03010001P = amountPer03010001P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount zp.
			 * 
			 * @return 屬性the sinosoft amount zp的值
			 */
			public double getAmountZP() {
				return amountZP;
			}
			
			/**
			 * 設置屬性the sinosoft amount zp.
			 * 
			 * @param amountZP
			 *            待設置的the sinosoft amount zp的值
			 */
			public void setAmountZP(double amountZP) {
				this.amountZP = amountZP;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per03010002 p.
			 * 
			 * @return 屬性the sinosoft amount per03010002 p的值
			 */
			public double getAmountPer03010002P() {
				return amountPer03010002P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per03010002 p.
			 * 
			 * @param amountPer03010002P
			 *            待設置的the sinosoft amount per03010002 p的值
			 */
			public void setAmountPer03010002P(double amountPer03010002P) {
				this.amountPer03010002P = amountPer03010002P;
			}
			
			/**
			 * 獲取屬性the sinosoft limit acc12 p.
			 * 
			 * @return 屬性the sinosoft limit acc12 p的值
			 */
			public double getLimitAcc12P() {
				return limitAcc12P;
			}
			
			/**
			 * 設置屬性the sinosoft limit acc12 p.
			 * 
			 * @param limitAcc12P
			 *            待設置的the sinosoft limit acc12 p的值
			 */
			public void setLimitAcc12P(double limitAcc12P) {
				this.limitAcc12P = limitAcc12P;
			}
			
			/**
			 * 獲取屬性the sinosoft limit man acc01 p.
			 * 
			 * @return 屬性the sinosoft limit man acc01 p的值
			 */
			public double getLimitManAcc01P() {
				return limitManAcc01P;
			}
			
			/**
			 * 設置屬性the sinosoft limit man acc01 p.
			 * 
			 * @param limitManAcc01P
			 *            待設置的the sinosoft limit man acc01 p的值
			 */
			public void setLimitManAcc01P(double limitManAcc01P) {
				this.limitManAcc01P = limitManAcc01P;
			}
			
			/**
			 * 獲取屬性the sinosoft limit man acc05 p.
			 * 
			 * @return 屬性the sinosoft limit man acc05 p的值
			 */
			public double getLimitManAcc05P() {
				return limitManAcc05P;
			}
			
			/**
			 * 設置屬性the sinosoft limit man acc05 p.
			 * 
			 * @param limitManAcc05P
			 *            待設置的the sinosoft limit man acc05 p的值
			 */
			public void setLimitManAcc05P(double limitManAcc05P) {
				this.limitManAcc05P = limitManAcc05P;
			}
			
			/**
			 * 獲取屬性the sinosoft sum amount p.
			 * 
			 * @return 屬性the sinosoft sum amount p的值
			 */
			public double getSumAmountP() {
				return sumAmountP;
			}
			
			/**
			 * 設置屬性the sinosoft sum amount p.
			 * 
			 * @param sumAmountP
			 *            待設置的the sinosoft sum amount p的值
			 */
			public void setSumAmountP(double sumAmountP) {
				this.sumAmountP = sumAmountP;
			}
			
			/**
			 * 獲取屬性the sinosoft third limit acc07 p.
			 * 
			 * @return 屬性the sinosoft third limit acc07 p的值
			 */
			public double getThirdLimitAcc07P() {
				return thirdLimitAcc07P;
			}
			
			/**
			 * 設置屬性the sinosoft third limit acc07 p.
			 * 
			 * @param thirdLimitAcc07P
			 *            待設置的the sinosoft third limit acc07 p的值
			 */
			public void setThirdLimitAcc07P(double thirdLimitAcc07P) {
				this.thirdLimitAcc07P = thirdLimitAcc07P;
			}
			
			/**
			 * 獲取屬性the sinosoft third limit sum07 p.
			 * 
			 * @return 屬性the sinosoft third limit sum07 p的值
			 */
			public double getThirdLimitSum07P() {
				return thirdLimitSum07P;
			}
			
			/**
			 * 設置屬性the sinosoft third limit sum07 p.
			 * 
			 * @param thirdLimitSum07P
			 *            待設置的the sinosoft third limit sum07 p的值
			 */
			public void setThirdLimitSum07P(double thirdLimitSum07P) {
				this.thirdLimitSum07P = thirdLimitSum07P;
			}
			
			/**
			 * 獲取屬性the sinosoft agent day.
			 * 
			 * @return 屬性the sinosoft agent day的值
			 */
			public double getAgentDay() {
				return AgentDay;
			}
			
			/**
			 * 設置屬性the sinosoft agent day.
			 * 
			 * @param agentDay
			 *            待設置的the sinosoft agent day的值
			 */
			public void setAgentDay(double agentDay) {
				AgentDay = agentDay;
			}
			
			/**
			 * 獲取屬性the sinosoft contractno.
			 * 
			 * @return 屬性the sinosoft contractno的值
			 */
			public String getContractno() {
				return contractno;
			}
			
			/**
			 * 設置屬性the sinosoft contractno.
			 * 
			 * @param contractno
			 *            待設置的the sinosoft contractno的值
			 */
			public void setContractno(String contractno) {
				this.contractno = contractno;
			}
			
			/**
			 * 獲取屬性the sinosoft db manager.
			 * 
			 * @return 屬性the sinosoft db manager的值
			 */
			public DBManager getDbManager() {
				return dbManager;
			}
			
			/**
			 * 設置屬性the sinosoft db manager.
			 * 
			 * @param dbManager
			 *            待設置的the sinosoft db manager的值
			 */
			public void setDbManager(DBManager dbManager) {
				this.dbManager = dbManager;
			}
			
			/**
			 * 獲取屬性the sinosoft db pml.
			 * 
			 * @return 屬性the sinosoft db pml的值
			 */
			public double getDbPML() {
				return dbPML;
			}
			
			/**
			 * 設置屬性the sinosoft db pml.
			 * 
			 * @param dbPML
			 *            待設置的the sinosoft db pml的值
			 */
			public void setDbPML(double dbPML) {
				this.dbPML = dbPML;
			}
			
			/**
			 * 獲取屬性終止日期.
			 * 
			 * @return 屬性終止日期的值
			 */
			public Date getEndDate() {
				return endDate;
			}
			
			/**
			 * 設置屬性終止日期.
			 * 
			 * @param endDate
			 *            待設置的終止日期的值
			 */
			public void setEndDate(Date endDate) {
				this.endDate = endDate;
			}
			
			/**
			 * 獲取屬性the sinosoft flowintime.
			 * 
			 * @return 屬性the sinosoft flowintime的值
			 */
			public Date getFlowintime() {
				return flowintime;
			}
			
			/**
			 * 設置屬性the sinosoft flowintime.
			 * 
			 * @param flowintime
			 *            待設置的the sinosoft flowintime的值
			 */
			public void setFlowintime(Date flowintime) {
				this.flowintime = flowintime;
			}
			
			/**
			 * 獲取屬性業務號.
			 * 
			 * @return 屬性業務號的值
			 */
			public String getIBusinessNo() {
				return iBusinessNo;
			}
			
			/**
			 * 設置屬性業務號.
			 * 
			 * @param businessNo
			 *            待設置的業務號的值
			 */
			public void setIBusinessNo(String businessNo) {
				iBusinessNo = businessNo;
			}
			
			/**
			 * 獲取屬性the sinosoft operate date.
			 * 
			 * @return 屬性the sinosoft operate date的值
			 */
			public Date getOperateDate() {
				return operateDate;
			}
			
			/**
			 * 設置屬性the sinosoft operate date.
			 * 
			 * @param operateDate
			 *            待設置的the sinosoft operate date的值
			 */
			public void setOperateDate(Date operateDate) {
				this.operateDate = operateDate;
			}
			
			/**
			 * 獲取屬性the sinosoft policy sort.
			 * 
			 * @return 屬性the sinosoft policy sort的值
			 */
			public String getPolicySort() {
				return policySort;
			}
			
			/**
			 * 設置屬性the sinosoft policy sort.
			 * 
			 * @param policySort
			 *            待設置的the sinosoft policy sort的值
			 */
			public void setPolicySort(String policySort) {
				this.policySort = policySort;
			}
			
			/**
			 * 獲取屬性the sinosoft sign date.
			 * 
			 * @return 屬性the sinosoft sign date的值
			 */
			public Date getSignDate() {
				return signDate;
			}
			
			/**
			 * 設置屬性the sinosoft sign date.
			 * 
			 * @param signDate
			 *            待設置的the sinosoft sign date的值
			 */
			public void setSignDate(Date signDate) {
				this.signDate = signDate;
			}
			
			/**
			 * 獲取屬性起始日期.
			 * 
			 * @return 屬性起始日期的值
			 */
			public Date getStartDate() {
				return startDate;
			}
			
			/**
			 * 設置屬性起始日期.
			 * 
			 * @param startDate
			 *            待設置的起始日期的值
			 */
			public void setStartDate(Date startDate) {
				this.startDate = startDate;
			}
			
			/**
			 * 獲取屬性the sinosoft sum premium.
			 * 
			 * @return 屬性the sinosoft sum premium的值
			 */
			public double getSumPremium() {
				return SumPremium;
			}
			
			/**
			 * 設置屬性the sinosoft sum premium.
			 * 
			 * @param sumPremium
			 *            待設置的the sinosoft sum premium的值
			 */
			public void setSumPremium(double sumPremium) {
				SumPremium = sumPremium;
			}
			
			/**
			 * 獲取屬性the sinosoft true sum premium.
			 * 
			 * @return 屬性the sinosoft true sum premium的值
			 */
			public double getTrueSumPremium() {
				return trueSumPremium;
			}
			
			/**
			 * 設置屬性the sinosoft true sum premium.
			 * 
			 * @param trueSumPremium
			 *            待設置的the sinosoft true sum premium的值
			 */
			public void setTrueSumPremium(double trueSumPremium) {
				this.trueSumPremium = trueSumPremium;
			}
			
			/**
			 * 獲取屬性the sinosoft valid date.
			 * 
			 * @return 屬性the sinosoft valid date的值
			 */
			public Date getValidDate() {
				return validDate;
			}
			
			/**
			 * 設置屬性the sinosoft valid date.
			 * 
			 * @param validDate
			 *            待設置的the sinosoft valid date的值
			 */
			public void setValidDate(Date validDate) {
				this.validDate = validDate;
			}
			
			/**
			 * Sets the *******意健险************.
			 * 
			 * @param ageScope
			 *            the new *******意健险************
			 */
			public void setAgeScope(ArrayList ageScope) {
				this.ageScope = ageScope;
			}
			
			/**
			 * 設置屬性the sinosoft allow split.
			 * 
			 * @param allowSplit
			 *            待設置的the sinosoft allow split的值
			 */
			public void setAllowSplit(String allowSplit) {
				this.allowSplit = allowSplit;
			}
			
			/**
			 * 設置屬性the sinosoft amount a.
			 * 
			 * @param amountA
			 *            待設置的the sinosoft amount a的值
			 */
			public void setAmountA(double amountA) {
				this.amountA = amountA;
			}
			
			/**
			 * 設置屬性the sinosoft amount b.
			 * 
			 * @param amountB
			 *            待設置的the sinosoft amount b的值
			 */
			public void setAmountB(double amountB) {
				this.amountB = amountB;
			}
			
			/**
			 * 設置屬性the sinosoft amount d11.
			 * 
			 * @param amountD1
			 *            待設置的the sinosoft amount d11的值
			 */
			public void setAmountD11(double amountD1) {
				this.amountD11 = amountD1;
			}
			
			/**
			 * 設置屬性the sinosoft amount d12.
			 * 
			 * @param amountD1
			 *            待設置的the sinosoft amount d12的值
			 */
			public void setAmountD12(double amountD1) {
				this.amountD12 = amountD1;
			}
			
			/**
			 * 設置屬性the sinosoft amount d2.
			 * 
			 * @param amountD2
			 *            待設置的the sinosoft amount d2的值
			 */
			public void setAmountD2(double amountD2) {
				this.amountD2 = amountD2;
			}
			
			/**
			 * 設置屬性the sinosoft amount g.
			 * 
			 * @param amountG
			 *            待設置的the sinosoft amount g的值
			 */
			public void setAmountG(double amountG) {
				this.amountG = amountG;
			}
			
			/**
			 * 設置屬性the sinosoft amount per03010001.
			 * 
			 * @param amountPer03010001
			 *            待設置的the sinosoft amount per03010001的值
			 */
			public void setAmountPer03010001(double amountPer03010001) {
				this.amountPer03010001 = amountPer03010001;
			}
			
			/**
			 * 設置屬性the sinosoft amount z.
			 * 
			 * @param amountZ
			 *            待設置的the sinosoft amount z的值
			 */
			public void setAmountZ(double amountZ) {
				this.amountZ = amountZ;
			}
			
			/**
			 * 設置屬性the sinosoft amount per03010002.
			 * 
			 * @param amountPer03010002
			 *            待設置的the sinosoft amount per03010002的值
			 */
			public void setAmountPer03010002(double amountPer03010002) {
				this.amountPer03010002 = amountPer03010002;
			}
			
			/**
			 * 設置屬性the sinosoft bidding down proportion.
			 * 
			 * @param biddingDownProportion
			 *            待設置的the sinosoft bidding down proportion的值
			 */
			public void setBiddingDownProportion(double biddingDownProportion) {
				this.biddingDownProportion = biddingDownProportion;
			}
			
			/**
			 * 設置屬性the sinosoft business nature.
			 * 
			 * @param businessNature
			 *            待設置的the sinosoft business nature的值
			 */
			public void setBusinessNature(String businessNature) {
				this.businessNature = businessNature;
			}
			
			/**
			 * 設置屬性the sinosoft chg amount.
			 * 
			 * @param chgAmount
			 *            待設置的the sinosoft chg amount的值
			 */
			public void setChgAmount(double chgAmount) {
				this.chgAmount = chgAmount;
			}
			
			/**
			 * 設置屬性險類代碼.
			 * 
			 * @param classCode
			 *            待設置的險類代碼的值
			 */
			public void setClassCode(String classCode) {
				this.classCode = classCode;
			}
			
			/**
			 * 設置屬性the sinosoft currency.
			 * 
			 * @param currency
			 *            待設置的the sinosoft currency的值
			 */
			public void setCurrency(String currency) {
				this.currency = currency;
			}
			
			/**
			 * 設置屬性the sinosoft direct day.
			 * 
			 * @param directDay
			 *            待設置的the sinosoft direct day的值
			 */
			public void setDirectDay(double directDay) {
				DirectDay = directDay;
			}
			
			/**
			 * 設置屬性the sinosoft discount.
			 * 
			 * @param discount
			 *            待設置的the sinosoft discount的值
			 */
			public void setDiscount(double discount) {
				this.discount = discount;
			}
			
			/**
			 * 設置屬性the sinosoft dis rate.
			 * 
			 * @param disRate
			 *            待設置的the sinosoft dis rate的值
			 */
			public void setDisRate(double disRate) {
				this.disRate = disRate;
			}
			
			/**
			 * 設置屬性the sinosoft dis rate1.
			 * 
			 * @param disRate1
			 *            待設置的the sinosoft dis rate1的值
			 */
			public void setDisRate1(double disRate1) {
				this.disRate1 = disRate1;
			}
			
			/**
			 * 設置屬性the sinosoft exch rate.
			 * 
			 * @param exchRate
			 *            待設置的the sinosoft exch rate的值
			 */
			public void setExchRate(double exchRate) {
				this.exchRate = exchRate;
			}
			
			/**
			 * 設置屬性the sinosoft expense fee rate.
			 * 
			 * @param expenseFeeRate
			 *            待設置的the sinosoft expense fee rate的值
			 */
			public void setExpenseFeeRate(double expenseFeeRate) {
				this.expenseFeeRate = expenseFeeRate;
			}
			
			/**
			 * 設置屬性the sinosoft group car sum.
			 * 
			 * @param groupCarSum
			 *            待設置的the sinosoft group car sum的值
			 */
			public void setGroupCarSum(double groupCarSum) {
				this.groupCarSum = groupCarSum;
			}
			
			/**
			 * 設置屬性the sinosoft insured type code.
			 * 
			 * @param insuredTypeCode
			 *            待設置的the sinosoft insured type code的值
			 */
			public void setInsuredTypeCode(String insuredTypeCode) {
				this.insuredTypeCode = insuredTypeCode;
			}
			
			/**
			 * 設置屬性the sinosoft limit man acc01.
			 * 
			 * @param limitManAcc01
			 *            待設置的the sinosoft limit man acc01的值
			 */
			public void setLimitManAcc01(double limitManAcc01) {
				this.limitManAcc01 = limitManAcc01;
			}
			
			/**
			 * 設置屬性the sinosoft model code.
			 * 
			 * @param modelCode
			 *            待設置的the sinosoft model code的值
			 */
			public void setModelCode(String modelCode) {
				this.modelCode = modelCode;
			}
			
			/**
			 * 設置屬性the sinosoft out lay rate.
			 * 
			 * @param outLayRate
			 *            待設置的the sinosoft out lay rate的值
			 */
			public void setOutLayRate(double outLayRate) {
				this.outLayRate = outLayRate;
			}
			
			/**
			 * Sets the *************************车险特殊************.
			 * 
			 * @param permitBidding
			 *            the new *************************车险特殊************
			 */
			public void setPermitBidding(String permitBidding) {
				this.permitBidding = permitBidding;
			}
			
			/**
			 * 設置屬性the sinosoft plus rate.
			 * 
			 * @param plusRate
			 *            待設置的the sinosoft plus rate的值
			 */
			public void setPlusRate(double plusRate) {
				this.plusRate = plusRate;
			}
			
			/**
			 * 設置屬性the sinosoft prepay protocol.
			 * 
			 * @param prepayProtocol
			 *            待設置的the sinosoft prepay protocol的值
			 */
			public void setPrepayProtocol(String prepayProtocol) {
				this.prepayProtocol = prepayProtocol;
			}
			
			/**
			 * 設置屬性the sinosoft real pay flag.
			 * 
			 * @param realPayFlag
			 *            待設置的the sinosoft real pay flag的值
			 */
			public void setRealPayFlag(int realPayFlag) {
				this.realPayFlag = realPayFlag;
			}
			
			/**
			 * Sets the *******再保部分***************.
			 * 
			 * @param reinsUnit
			 *            the new *******再保部分***************
			 */
			public void setReinsUnit(String reinsUnit) {
				this.reinsUnit = reinsUnit;
			}
			
			/**
			 * 設置屬性險種代碼.
			 * 
			 * @param riskCode
			 *            待設置的險種代碼的值
			 */
			public void setRiskCode(String riskCode) {
				this.riskCode = riskCode;
			}
			
			/**
			 * 設置屬性the sinosoft risk kind.
			 * 
			 * @param riskKind
			 *            待設置的the sinosoft risk kind的值
			 */
			public void setRiskKind(String riskKind) {
				this.riskKind = riskKind;
			}
			
			/**
			 * 設置屬性the sinosoft short rate.
			 * 
			 * @param shortRate
			 *            待設置的the sinosoft short rate的值
			 */
			public void setShortRate(double shortRate) {
				this.shortRate = shortRate;
			}
			
			/**
			 * 設置屬性the sinosoft sum amount.
			 * 
			 * @param sumAmount
			 *            待設置的the sinosoft sum amount的值
			 */
			public void setSumAmount(double sumAmount) {
				this.sumAmount = sumAmount;
			}
			
			/**
			 * 設置屬性the sinosoft temp policy.
			 * 
			 * @param tempPolicy
			 *            待設置的the sinosoft temp policy的值
			 */
			public void setTempPolicy(String tempPolicy) {
				this.tempPolicy = tempPolicy;
			}
			
			/**
			 * 設置屬性the sinosoft temp policy no.
			 * 
			 * @param tempPolicyNo
			 *            待設置的the sinosoft temp policy no的值
			 */
			public void setTempPolicyNo(String tempPolicyNo) {
				this.tempPolicyNo = tempPolicyNo;
			}
			
			/**
			 * 設置屬性the sinosoft third limit acc07.
			 * 
			 * @param thirdLimitAcc07
			 *            待設置的the sinosoft third limit acc07的值
			 */
			public void setThirdLimitAcc07(double thirdLimitAcc07) {
				this.thirdLimitAcc07 = thirdLimitAcc07;
			}
			
			/**
			 * 設置屬性the sinosoft third limit sum07.
			 * 
			 * @param thirdLimitSum07
			 *            待設置的the sinosoft third limit sum07的值
			 */
			public void setThirdLimitSum07(double thirdLimitSum07) {
				this.thirdLimitSum07 = thirdLimitSum07;
			}
			
			/**
			 * 設置屬性the sinosoft trial amount.
			 * 
			 * @param trialAmount
			 *            待設置的the sinosoft trial amount的值
			 */
			public void setTrialAmount(double trialAmount) {
				this.trialAmount = trialAmount;
			}
			
			/**
			 * 設置屬性the sinosoft trial premium.
			 * 
			 * @param trialPremium
			 *            待設置的the sinosoft trial premium的值
			 */
			public void setTrialPremium(double trialPremium) {
				this.trialPremium = trialPremium;
			}
			
			/**
			 * 設置屬性the sinosoft unit amount.
			 * 
			 * @param unitAmount
			 *            待設置的the sinosoft unit amount的值
			 */
			public void setUnitAmount(double unitAmount) {
				this.unitAmount = unitAmount;
			}
			
			/**
			 * 設置屬性the sinosoft unit policy.
			 * 
			 * @param unitPolicy
			 *            待設置的the sinosoft unit policy的值
			 */
			public void setUnitPolicy(String unitPolicy) {
				this.unitPolicy = unitPolicy;
			}
			
			/**
			 * 設置屬性the sinosoft unit proportion.
			 * 
			 * @param unitProportion
			 *            待設置的the sinosoft unit proportion的值
			 */
			public void setUnitProportion(double unitProportion) {
				this.unitProportion = unitProportion;
			}
			
			/**
			 * 設置屬性the sinosoft use nature code.
			 * 
			 * @param useNatureCode
			 *            待設置的the sinosoft use nature code的值
			 */
			public void setUseNatureCode(String useNatureCode) {
				this.useNatureCode = useNatureCode;
			}
			
			/**
			 * 設置屬性the sinosoft use years.
			 * 
			 * @param useYears
			 *            待設置的the sinosoft use years的值
			 */
			public void setUseYears(double useYears) {
				this.useYears = useYears;
			}
			
			/**
			 * 設置屬性the sinosoft write off days.
			 * 
			 * @param writeOffDays
			 *            待設置的the sinosoft write off days的值
			 */
			public void setWriteOffDays(double writeOffDays) {
				this.writeOffDays = writeOffDays;
			}
			
			/**
			 * 獲取屬性the sinosoft nodeno.
			 * 
			 * @return 屬性the sinosoft nodeno的值
			 */
			public double getNodeno() {
				return Nodeno;
			}
			
			/**
			 * 設置屬性the sinosoft nodeno.
			 * 
			 * @param nodeno
			 *            待設置的the sinosoft nodeno的值
			 */
			public void setNodeno(double nodeno) {
				Nodeno = nodeno;
			}
			
			/**
			 * 獲取屬性the sinosoft nodeno old.
			 * 
			 * @return 屬性the sinosoft nodeno old的值
			 */
			public double getNodenoOld() {
				return NodenoOld;
			}
			
			/**
			 * 設置屬性the sinosoft nodeno old.
			 * 
			 * @param nodenoOld
			 *            待設置的the sinosoft nodeno old的值
			 */
			public void setNodenoOld(double nodenoOld) {
				NodenoOld = nodenoOld;
			}
			
			/**
			 * 獲取屬性the sinosoft temp proposal no.
			 * 
			 * @return 屬性the sinosoft temp proposal no的值
			 */
			public String getTempProposalNo() {
				return tempProposalNo;
			}
			
			/**
			 * 設置屬性the sinosoft temp proposal no.
			 * 
			 * @param tempProposalNo
			 *            待設置的the sinosoft temp proposal no的值
			 */
			public void setTempProposalNo(String tempProposalNo) {
				this.tempProposalNo = tempProposalNo;
			}
			
			/**
			 * 獲取屬性the sinosoft node name.
			 * 
			 * @return 屬性the sinosoft node name的值
			 */
			public String getNodeName() {
				return NodeName;
			}
			
			/**
			 * 設置屬性the sinosoft node name.
			 * 
			 * @param nodeName
			 *            待設置的the sinosoft node name的值
			 */
			public void setNodeName(String nodeName) {
				NodeName = nodeName;
			}
			
			/**
			 * 獲取屬性the sinosoft node name old.
			 * 
			 * @return 屬性the sinosoft node name old的值
			 */
			public String getNodeNameOld() {
				return NodeNameOld;
			}
			
			/**
			 * 設置屬性the sinosoft node name old.
			 * 
			 * @param nodeNameOld
			 *            待設置的the sinosoft node name old的值
			 */
			public void setNodeNameOld(String nodeNameOld) {
				NodeNameOld = nodeNameOld;
			}
			
			/**
			 * 獲取屬性the sinosoft ship age.
			 * 
			 * @return 屬性the sinosoft ship age的值
			 */
			public double getShipAge() {
				return shipAge;
			}
			
			/**
			 * 設置屬性the sinosoft ship age.
			 * 
			 * @param shipAge
			 *            待設置的the sinosoft ship age的值
			 */
			public void setShipAge(double shipAge) {
				this.shipAge = shipAge;
			}
			
			/**
			 * 獲取屬性the sinosoft ship age p.
			 * 
			 * @return 屬性the sinosoft ship age p的值
			 */
			public double getShipAgeP() {
				return shipAgeP;
			}
			
			/**
			 * 設置屬性the sinosoft ship age p.
			 * 
			 * @param shipAgeP
			 *            待設置的the sinosoft ship age p的值
			 */
			public void setShipAgeP(double shipAgeP) {
				this.shipAgeP = shipAgeP;
			}
			
			/**
			 * 獲取屬性the sinosoft plus rate p.
			 * 
			 * @return 屬性the sinosoft plus rate p的值
			 */
			public double getPlusRateP() {
				return plusRateP;
			}
			
			/**
			 * 設置屬性the sinosoft plus rate p.
			 * 
			 * @param plusRateP
			 *            待設置的the sinosoft plus rate p的值
			 */
			public void setPlusRateP(double plusRateP) {
				this.plusRateP = plusRateP;
			}
			
			/**
			 * 獲取屬性the sinosoft sum amount c.
			 * 
			 * @return 屬性the sinosoft sum amount c的值
			 */
			public double getSumAmountC() {
				return sumAmountC;
			}
			
			/**
			 * 設置屬性the sinosoft sum amount c.
			 * 
			 * @param sumAmountC
			 *            待設置的the sinosoft sum amount c的值
			 */
			public void setSumAmountC(double sumAmountC) {
				this.sumAmountC = sumAmountC;
			}
			
			/**
			 * 獲取屬性the sinosoft sum amount cp.
			 * 
			 * @return 屬性the sinosoft sum amount cp的值
			 */
			public double getSumAmountCP() {
				return sumAmountCP;
			}
			
			/**
			 * 設置屬性the sinosoft sum amount cp.
			 * 
			 * @param sumAmountCP
			 *            待設置的the sinosoft sum amount cp的值
			 */
			public void setSumAmountCP(double sumAmountCP) {
				this.sumAmountCP = sumAmountCP;
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
			 * 獲取屬性the sinosoft sum amount u.
			 * 
			 * @return 屬性the sinosoft sum amount u的值
			 */
			public double getSumAmountU() {
				return sumAmountU;
			}
			
			/**
			 * 設置屬性the sinosoft sum amount u.
			 * 
			 * @param sumAmountU
			 *            待設置的the sinosoft sum amount u的值
			 */
			public void setSumAmountU(double sumAmountU) {
				this.sumAmountU = sumAmountU;
			}
			
			/**
			 * 獲取屬性the sinosoft sum amount up.
			 * 
			 * @return 屬性the sinosoft sum amount up的值
			 */
			public double getSumAmountUP() {
				return sumAmountUP;
			}
			
			/**
			 * 設置屬性the sinosoft sum amount up.
			 * 
			 * @param sumAmountUP
			 *            待設置的the sinosoft sum amount up的值
			 */
			public void setSumAmountUP(double sumAmountUP) {
				this.sumAmountUP = sumAmountUP;
			}
			
			/**
			 * 獲取屬性the sinosoft occupation code.
			 * 
			 * @return 屬性the sinosoft occupation code的值
			 */
			public String getOccupationCode() {
				return OccupationCode;
			}
			
			/**
			 * 設置屬性the sinosoft occupation code.
			 * 
			 * @param occupationCode
			 *            待設置的the sinosoft occupation code的值
			 */
			public void setOccupationCode(String occupationCode) {
				OccupationCode = occupationCode;
			}
			
			/**
			 * 獲取屬性the sinosoft occupation flag.
			 * 
			 * @return 屬性the sinosoft occupation flag的值
			 */
			public String getOccupationFlag() {
				return OccupationFlag;
			}
			
			/**
			 * 設置屬性the sinosoft occupation flag.
			 * 
			 * @param occupationFlag
			 *            待設置的the sinosoft occupation flag的值
			 */
			public void setOccupationFlag(String occupationFlag) {
				OccupationFlag = occupationFlag;
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
			 * 獲取屬性the sinosoft unit amount c.
			 * 
			 * @return 屬性the sinosoft unit amount c的值
			 */
			public double getUnitAmountC() {
				return unitAmountC;
			}
			
			/**
			 * 設置屬性the sinosoft unit amount c.
			 * 
			 * @param unitAmountC
			 *            待設置的the sinosoft unit amount c的值
			 */
			public void setUnitAmountC(double unitAmountC) {
				this.unitAmountC = unitAmountC;
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
				return limitAmount02;
			}
			
			/**
			 * 設置屬性the sinosoft limit amount02.
			 * 
			 * @param limitAmount02
			 *            待設置的the sinosoft limit amount02的值
			 */
			public void setLimitAmount02(double limitAmount02) {
				this.limitAmount02 = limitAmount02;
			}
			
			/**
			 * 獲取屬性the sinosoft limit amount03.
			 * 
			 * @return 屬性the sinosoft limit amount03的值
			 */
			public double getLimitAmount03() {
				return limitAmount03;
			}
			
			/**
			 * 設置屬性the sinosoft limit amount03.
			 * 
			 * @param limitAmount03
			 *            待設置的the sinosoft limit amount03的值
			 */
			public void setLimitAmount03(double limitAmount03) {
				this.limitAmount03 = limitAmount03;
			}
			
			/**
			 * 獲取屬性the sinosoft limit amount46.
			 * 
			 * @return 屬性the sinosoft limit amount46的值
			 */
			public double getLimitAmount46() {
				return limitAmount46;
			}
			
			/**
			 * 設置屬性the sinosoft limit amount46.
			 * 
			 * @param limitAmount46
			 *            待設置的the sinosoft limit amount46的值
			 */
			public void setLimitAmount46(double limitAmount46) {
				this.limitAmount46 = limitAmount46;
			}
			
			/**
			 * 獲取屬性the sinosoft limit amount51.
			 * 
			 * @return 屬性the sinosoft limit amount51的值
			 */
			public double getLimitAmount51() {
				return limitAmount51;
			}
			
			/**
			 * 設置屬性the sinosoft limit amount51.
			 * 
			 * @param limitAmount51
			 *            待設置的the sinosoft limit amount51的值
			 */
			public void setLimitAmount51(double limitAmount51) {
				this.limitAmount51 = limitAmount51;
			}
			
			/**
			 * 獲取屬性the sinosoft limit amount66.
			 * 
			 * @return 屬性the sinosoft limit amount66的值
			 */
			public double getLimitAmount66() {
				return limitAmount66;
			}
			
			/**
			 * 設置屬性the sinosoft limit amount66.
			 * 
			 * @param limitAmount66
			 *            待設置的the sinosoft limit amount66的值
			 */
			public void setLimitAmount66(double limitAmount66) {
				this.limitAmount66 = limitAmount66;
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
				this.LimitCargoAcc = limitCargoAcc;
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
				this.LimitThirdAcc = limitThirdAcc;
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
				this.LimitThirdAcc2 = limitThirdAcc2;
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
			public double getChgPremium() {
				return ChgPremium;
			}
			
			/**
			 * 設置屬性the sinosoft chg premium.
			 * 
			 * @param chgPremium
			 *            待設置的the sinosoft chg premium的值
			 */
			public void setChgPremium(double chgPremium) {
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
			 * 設置屬性the sinosoft seat count.
			 * 
			 * @param seatCount
			 *            待設置的the sinosoft seat count的值
			 */
			public void setSeatCount(int seatCount) {
				SeatCount = seatCount;
			}
			
			/**
			 * 獲取屬性the sinosoft seat count.
			 * 
			 * @return 屬性the sinosoft seat count的值
			 */
			public int getSeatCount() {
				return SeatCount;
			}
			
			/**
			 * 獲取屬性機構代碼.
			 * 
			 * @return 屬性機構代碼的值
			 */
			public String getComCode() {
				return comCode;
			}
			
			/**
			 * 設置屬性機構代碼.
			 * 
			 * @param comCode
			 *            待設置的機構代碼的值
			 */
			public void setComCode(String comCode) {
				this.comCode = comCode;
			}
			
			/**
			 * 獲取屬性用戶代碼.
			 * 
			 * @return 屬性用戶代碼的值
			 */
			public String getUserCode() {
				return userCode;
			}
			
			/**
			 * 設置屬性用戶代碼.
			 * 
			 * @param userCode
			 *            待設置的用戶代碼的值
			 */
			public void setUserCode(String userCode) {
				this.userCode = userCode;
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
			 * 獲取屬性the sinosoft quantity.
			 * 
			 * @return 屬性the sinosoft quantity的值
			 */
			public double getQuantity() {
				return Quantity;
			}
			
			/**
			 * 設置屬性the sinosoft quantity.
			 * 
			 * @param quantity
			 *            待設置的the sinosoft quantity的值
			 */
			public void setQuantity(double quantity) {
				Quantity = quantity;
			}
			
			/**
			 * 獲取屬性the sinosoft regist model code.
			 * 
			 * @return 屬性the sinosoft regist model code的值
			 */
			public String getRegistModelCode() {
				return RegistModelCode;
			}
			
			/**
			 * 設置屬性the sinosoft regist model code.
			 * 
			 * @param registModelCode
			 *            待設置的the sinosoft regist model code的值
			 */
			public void setRegistModelCode(String registModelCode) {
				RegistModelCode = registModelCode;
			}
			
			/**
			 * 獲取屬性the sinosoft license no.
			 * 
			 * @return 屬性the sinosoft license no的值
			 */
			public String getLicenseNo() {
				return LicenseNo;
			}
			
			/**
			 * 設置屬性the sinosoft license no.
			 * 
			 * @param licenseNo
			 *            待設置的the sinosoft license no的值
			 */
			public void setLicenseNo(String licenseNo)  {
				LicenseNo = licenseNo;
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
			 * 獲取屬性the sinosoft sum amount2300200.
			 * 
			 * @return the sumAmount2300200
			 */
			/**
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
			 * @param yearLimit
			 *            待設置的the sinosoft cB year limit的值
			 */
			public void setCBYearLimit(int yearLimit) {
				this.CBYearLimit = yearLimit;
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
				this.SuttleAmountA = suttleAmountA;
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
				this.SuttleAmountB = suttleAmountB;
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
				this.TuiBaoPremium = tuiBaoPremium;
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
				this.OnlyABYears = onlyABYears;
			}
			
			/**
			 * Checks if is new risk evaluate.
			 * 
			 * @return true, if is new risk evaluate
			 */
			public boolean isNewRiskEvaluate() {
				return isNewRiskEvaluate;
			}
			
			/**
			 * 設置屬性the sinosoft new risk evaluate.
			 * 
			 * @param isNewRiskEvaluate
			 *            待設置的the sinosoft new risk evaluate的值
			 */
			public void setNewRiskEvaluate(boolean isNewRiskEvaluate) {
				this.isNewRiskEvaluate = isNewRiskEvaluate;
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
			 * 獲取屬性the sinosoft str endortype.
			 * 
			 * @return 屬性the sinosoft str endortype的值
			 */
			public String getStrEndortype() {
				return strEndortype;
			}
			
			/**
			 * 設置屬性the sinosoft str endortype.
			 * 
			 * @param strEndortype
			 *            待設置的the sinosoft str endortype的值
			 */
			public void setStrEndortype(String strEndortype) {
				this.strEndortype = strEndortype;
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
			public double getEndorDisRate() {
				return EndorDisRate;
			}
			
			/**
			 * 設置屬性the sinosoft endor dis rate.
			 * 
			 * @param endorDisRate
			 *            待設置的the sinosoft endor dis rate的值
			 */
			public void setEndorDisRate(double endorDisRate) {
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
			 * 設置屬性the sinosoft allow allow0911700.
			 * 
			 * @param allow0911700
			 *            待設置的the sinosoft allow allow0911700的值
			 */
			public void setAllowAllow0911700(String allow0911700) {
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
			 * 獲取屬性the sinosoft productcode.
			 * 
			 * @return 屬性the sinosoft productcode的值
			 */
			public String getProductcode() {
				return productcode;
			}
			
			/**
			 * 設置屬性the sinosoft productcode.
			 * 
			 * @param productcode
			 *            待設置的the sinosoft productcode的值
			 */
			public void setProductcode(String productcode) {
				this.productcode = productcode;
			}
			
			/**
			 * 獲取屬性the sinosoft danger unit count.
			 * 
			 * @return 屬性the sinosoft danger unit count的值
			 */
			public int getDangerUnitCount() {
				return dangerUnitCount;
			}
			
			/**
			 * 設置屬性the sinosoft danger unit count.
			 * 
			 * @param dangerUnitCount
			 *            待設置的the sinosoft danger unit count的值
			 */
			public void setDangerUnitCount(int dangerUnitCount) {
				this.dangerUnitCount = dangerUnitCount;
			}
			
			/**
			 * 獲取屬性the sinosoft unit amount03.
			 * 
			 * @return 屬性the sinosoft unit amount03的值
			 */
			public double getUnitAmount03() {
				return unitAmount03;
			}
			
			/**
			 * 設置屬性the sinosoft unit amount03.
			 * 
			 * @param UnitAmount03
			 *            待設置的the sinosoft unit amount03的值
			 */
			public void setUnitAmount03(double UnitAmount03) {
				this.unitAmount03 = UnitAmount03;
			}
			
			/**
			 * 獲取屬性the sinosoft unit amount03 p.
			 * 
			 * @return 屬性the sinosoft unit amount03 p的值
			 */
			public double getUnitAmount03P() {
				return unitAmount03P;
			}
			
			/**
			 * 設置屬性the sinosoft unit amount03 p.
			 * 
			 * @param UnitAmount03P
			 *            待設置的the sinosoft unit amount03 p的值
			 */
			public void setUnitAmount03P(double UnitAmount03P) {
				this.unitAmount03P = UnitAmount03P;
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
			 * 獲取屬性the sinosoft sum amount0300100 p.
			 * 
			 * @return 屬性the sinosoft sum amount0300100 p的值
			 */
			public double getSumAmount0300100P() {
				return sumAmount0300100P;
			}
			
			/**
			 * 設置屬性the sinosoft sum amount0300100 p.
			 * 
			 * @param sumAmount0300100P
			 *            待設置的the sinosoft sum amount0300100 p的值
			 */
			public void setSumAmount0300100P(double sumAmount0300100P) {
				this.sumAmount0300100P = sumAmount0300100P;
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
				return amountPer9000452;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000452.
			 * 
			 * @param amountPer9000452
			 *            待設置的the sinosoft amount per9000452的值
			 */
			public void setAmountPer9000452(double amountPer9000452) {
				this.amountPer9000452 = amountPer9000452;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000453.
			 * 
			 * @return 屬性the sinosoft amount per9000453的值
			 */
			public double getAmountPer9000453() {
				return amountPer9000453;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000453.
			 * 
			 * @param amountPer9000453
			 *            待設置的the sinosoft amount per9000453的值
			 */
			public void setAmountPer9000453(double amountPer9000453) {
				this.amountPer9000453 = amountPer9000453;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000454.
			 * 
			 * @return 屬性the sinosoft amount per9000454的值
			 */
			public double getAmountPer9000454() {
				return amountPer9000454;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000454.
			 * 
			 * @param amountPer9000454
			 *            待設置的the sinosoft amount per9000454的值
			 */
			public void setAmountPer9000454(double amountPer9000454) {
				this.amountPer9000454 = amountPer9000454;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000449.
			 * 
			 * @return 屬性the sinosoft amount per9000449的值
			 */
			public double getAmountPer9000449() {
				return amountPer9000449;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000449.
			 * 
			 * @param amountPer9000449
			 *            待設置的the sinosoft amount per9000449的值
			 */
			public void setAmountPer9000449(double amountPer9000449) {
				this.amountPer9000449 = amountPer9000449;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000450.
			 * 
			 * @return 屬性the sinosoft amount per9000450的值
			 */
			public double getAmountPer9000450() {
				return amountPer9000450;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000450.
			 * 
			 * @param amountPer9000450
			 *            待設置的the sinosoft amount per9000450的值
			 */
			public void setAmountPer9000450(double amountPer9000450) {
				this.amountPer9000450 = amountPer9000450;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000451.
			 * 
			 * @return 屬性the sinosoft amount per9000451的值
			 */
			public double getAmountPer9000451() {
				return amountPer9000451;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000451.
			 * 
			 * @param amountPer9000451
			 *            待設置的the sinosoft amount per9000451的值
			 */
			public void setAmountPer9000451(double amountPer9000451) {
				this.amountPer9000451 = amountPer9000451;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000449 p.
			 * 
			 * @return 屬性the sinosoft amount per9000449 p的值
			 */
			public double getAmountPer9000449P() {
				return amountPer9000449P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000449 p.
			 * 
			 * @param amountPer9000449P
			 *            待設置的the sinosoft amount per9000449 p的值
			 */
			public void setAmountPer9000449P(double amountPer9000449P) {
				this.amountPer9000449P = amountPer9000449P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000450 p.
			 * 
			 * @return 屬性the sinosoft amount per9000450 p的值
			 */
			public double getAmountPer9000450P() {
				return amountPer9000450P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000450 p.
			 * 
			 * @param amountPer9000450P
			 *            待設置的the sinosoft amount per9000450 p的值
			 */
			public void setAmountPer9000450P(double amountPer9000450P) {
				this.amountPer9000450P = amountPer9000450P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000451 p.
			 * 
			 * @return 屬性the sinosoft amount per9000451 p的值
			 */
			public double getAmountPer9000451P() {
				return amountPer9000451P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000451 p.
			 * 
			 * @param amountPer9000451P
			 *            待設置的the sinosoft amount per9000451 p的值
			 */
			public void setAmountPer9000451P(double amountPer9000451P) {
				this.amountPer9000451P = amountPer9000451P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000452 p.
			 * 
			 * @return 屬性the sinosoft amount per9000452 p的值
			 */
			public double getAmountPer9000452P() {
				return amountPer9000452P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000452 p.
			 * 
			 * @param amountPer9000452P
			 *            待設置的the sinosoft amount per9000452 p的值
			 */
			public void setAmountPer9000452P(double amountPer9000452P) {
				this.amountPer9000452P = amountPer9000452P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000453 p.
			 * 
			 * @return 屬性the sinosoft amount per9000453 p的值
			 */
			public double getAmountPer9000453P() {
				return amountPer9000453P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000453 p.
			 * 
			 * @param amountPer9000453P
			 *            待設置的the sinosoft amount per9000453 p的值
			 */
			public void setAmountPer9000453P(double amountPer9000453P) {
				this.amountPer9000453P = amountPer9000453P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000454 p.
			 * 
			 * @return 屬性the sinosoft amount per9000454 p的值
			 */
			public double getAmountPer9000454P() {
				return amountPer9000454P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000454 p.
			 * 
			 * @param amountPer9000454P
			 *            待設置的the sinosoft amount per9000454 p的值
			 */
			public void setAmountPer9000454P(double amountPer9000454P) {
				this.amountPer9000454P = amountPer9000454P;
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
			 * 獲取屬性the sinosoft limit acc03 p.
			 * 
			 * @return 屬性the sinosoft limit acc03 p的值
			 */
			public double getLimitAcc03P() {
				return LimitAcc03P;
			}
			
			/**
			 * 設置屬性the sinosoft limit acc03 p.
			 * 
			 * @param limitAcc03P
			 *            待設置的the sinosoft limit acc03 p的值
			 */
			public void setLimitAcc03P(double limitAcc03P) {
				LimitAcc03P = limitAcc03P;
			}			

			/**
			 * 獲取屬性the sinosoft amount new.
			 * 
			 * @return 屬性the sinosoft amount new的值
			 */
			public double getAmountNew() {
				return amountNew;
			}
			
			/**
			 * 設置屬性the sinosoft amount new.
			 * 
			 * @param amountNew
			 *            待設置的the sinosoft amount new的值
			 */
			public void setAmountNew(double amountNew) {
				this.amountNew = amountNew;
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
			 * 獲取屬性the sinosoft sumquantity.
			 * 
			 * @return 屬性the sinosoft sumquantity的值
			 */
			public double getSumquantity() {
				return sumquantity;
			}
			
			/**
			 * 設置屬性the sinosoft sumquantity.
			 * 
			 * @param sumquantity
			 *            待設置的the sinosoft sumquantity的值
			 */
			public void setSumquantity(double sumquantity) {
				this.sumquantity = sumquantity;
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
				return amountW;
			}
			
			/**
			 * 設置屬性the sinosoft amount w.
			 * 
			 * @param amountW
			 *            待設置的the sinosoft amount w的值
			 */
			public void setAmountW(double amountW) {
				this.amountW = amountW;
			}
			
			/**
			 * 獲取屬性the sinosoft amount wp.
			 * 
			 * @return 屬性the sinosoft amount wp的值
			 */
			public double getAmountWP() {
				return amountWP;
			}
			
			/**
			 * 設置屬性the sinosoft amount wp.
			 * 
			 * @param amountWP
			 *            待設置的the sinosoft amount wp的值
			 */
			public void setAmountWP(double amountWP) {
				this.amountWP = amountWP;
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
			 * 獲取屬性the sinosoft kind c name.
			 * 
			 * @return 屬性the sinosoft kind c name的值
			 */
			public String getKindCName() {
				return KindCName;
			}
			
			/**
			 * 設置屬性the sinosoft kind c name.
			 * 
			 * @param kindCName
			 *            待設置的the sinosoft kind c name的值
			 */
			public void setKindCName(String kindCName) {
				KindCName = kindCName;
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
			 * 獲取屬性the sinosoft ship age1001.
			 * 
			 * @return 屬性the sinosoft ship age1001的值
			 */
			public String getShipAge1001() {
				return shipAge1001;
			}
			
			/**
			 * 設置屬性the sinosoft ship age1001.
			 * 
			 * @param shipAge1001
			 *            待設置的the sinosoft ship age1001的值
			 */
			public void setShipAge1001(String shipAge1001) {
				this.shipAge1001 = shipAge1001;
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
				return tonCountH0;
			}
			
			/**
			 * 設置屬性the sinosoft ton count h0.
			 * 
			 * @param tonCountH0
			 *            待設置的the sinosoft ton count h0的值
			 */
			public void setTonCountH0(double tonCountH0) {
				this.tonCountH0 = tonCountH0;
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
			 * 獲取屬性the sinosoft amount per9000455.
			 * 
			 * @return 屬性the sinosoft amount per9000455的值
			 */
			public double getAmountPer9000455() {
				return amountPer9000455;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000455.
			 * 
			 * @param amountPer9000455
			 *            待設置的the sinosoft amount per9000455的值
			 */
			public void setAmountPer9000455(double amountPer9000455) {
				this.amountPer9000455 = amountPer9000455;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000456.
			 * 
			 * @return 屬性the sinosoft amount per9000456的值
			 */
			public double getAmountPer9000456() {
				return amountPer9000456;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000456.
			 * 
			 * @param amountPer9000456
			 *            待設置的the sinosoft amount per9000456的值
			 */
			public void setAmountPer9000456(double amountPer9000456) {
				this.amountPer9000456 = amountPer9000456;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000457.
			 * 
			 * @return 屬性the sinosoft amount per9000457的值
			 */
			public double getAmountPer9000457() {
				return amountPer9000457;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000457.
			 * 
			 * @param amountPer9000457
			 *            待設置的the sinosoft amount per9000457的值
			 */
			public void setAmountPer9000457(double amountPer9000457) {
				this.amountPer9000457 = amountPer9000457;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000458.
			 * 
			 * @return 屬性the sinosoft amount per9000458的值
			 */
			public double getAmountPer9000458() {
				return amountPer9000458;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000458.
			 * 
			 * @param amountPer9000458
			 *            待設置的the sinosoft amount per9000458的值
			 */
			public void setAmountPer9000458(double amountPer9000458) {
				this.amountPer9000458 = amountPer9000458;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000455 p.
			 * 
			 * @return 屬性the sinosoft amount per9000455 p的值
			 */
			public double getAmountPer9000455P() {
				return amountPer9000455P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000455 p.
			 * 
			 * @param amountPer9000455P
			 *            待設置的the sinosoft amount per9000455 p的值
			 */
			public void setAmountPer9000455P(double amountPer9000455P) {
				this.amountPer9000455P = amountPer9000455P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000456 p.
			 * 
			 * @return 屬性the sinosoft amount per9000456 p的值
			 */
			public double getAmountPer9000456P() {
				return amountPer9000456P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000456 p.
			 * 
			 * @param amountPer9000456P
			 *            待設置的the sinosoft amount per9000456 p的值
			 */
			public void setAmountPer9000456P(double amountPer9000456P) {
				this.amountPer9000456P = amountPer9000456P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000457 p.
			 * 
			 * @return 屬性the sinosoft amount per9000457 p的值
			 */
			public double getAmountPer9000457P() {
				return amountPer9000457P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000457 p.
			 * 
			 * @param amountPer9000457P
			 *            待設置的the sinosoft amount per9000457 p的值
			 */
			public void setAmountPer9000457P(double amountPer9000457P) {
				this.amountPer9000457P = amountPer9000457P;
			}
			
			/**
			 * 獲取屬性the sinosoft amount per9000458 p.
			 * 
			 * @return 屬性the sinosoft amount per9000458 p的值
			 */
			public double getAmountPer9000458P() {
				return amountPer9000458P;
			}
			
			/**
			 * 設置屬性the sinosoft amount per9000458 p.
			 * 
			 * @param amountPer9000458P
			 *            待設置的the sinosoft amount per9000458 p的值
			 */
			public void setAmountPer9000458P(double amountPer9000458P) {
				this.amountPer9000458P = amountPer9000458P;
			}
            
            /**
			 * 獲取屬性the sinosoft unionpay count.
			 * 
			 * @return 屬性the sinosoft unionpay count的值
			 */
            public int getUnionpayCount() {
				return unionpayCount;
			}
			
			/**
			 * 設置屬性the sinosoft unionpay count.
			 * 
			 * @param unionpayCount
			 *            待設置的the sinosoft unionpay count的值
			 */
			public void setUnionpayCount(int unionpayCount) {
				this.unionpayCount = unionpayCount;
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