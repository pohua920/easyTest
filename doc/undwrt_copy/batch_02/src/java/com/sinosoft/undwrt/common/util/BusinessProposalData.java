package com.sinosoft.undwrt.common.util;

/**
 * <p>Title: 业务数据</p>
 * <p>Description:根据投保单号得到业务数据 </p>
 * <p>Copyright: Copyright (c) 2005/7</p>
 * <p>Company: Sinosoft</p>
 * @author qinyongli
 * @version 1.0
 * modify by zhangruifeng 2008-2-20 reason:新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制
 * LanNing    20080301    投资金产品调整
 * modify by zhangruifeng 20080304  reason:针对2008年的双核权限对程序进行调整
 * add by gengxiaobo 20080326 起重机械综合保险高级核保条件
 * added by LanNing 20080421 1505每次事故赔偿限额
 * add by zhangruifeng 20080422 reason:共保业务时按照我方份额进行控制
 * added by gengxiaobo 20080604 增加最大车累计限额,调整每次事故赔偿限额取值。
 * added by gengxiaobo 20080620 投保单业务数据对象添加投保单号
 * added by liuwei 20090303 0911国内货运险（08版）增加附加险核保因子
 * added by xiongguojun 20090327 1506核乏料运输：累计责任限额
 * added by xiongguojun 20090908 增加1598每次事故责任限额、每人人身伤亡责任限额双核条件
 * added by ruanzhongxi 20110526 增加满足条件险种公用变量，便于维护
 */
import java.sql.ResultSet;
import java.util.Date;

import com.sinosoft.product.blsvr.tb.BLPrpTproduct;
import com.sinosoft.product.schema.PrpTproductSchema;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmain;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.prpall.pubfun.PubTools;

/**
 * The Class BusinessProposalData.
 */
public class BusinessProposalData extends BusinessData {
	
	/** 屬性日期 now. */
	private DateTime dateNow = new DateTime().current(); //当前时间

	/**
	 * Instantiates a new business proposal data.
	 * 
	 * @param iBusinessNo
	 *            the i business no
	 * @param dbManager
	 *            the db manager
	 * @throws Exception
	 *             the exception
	 */
	public BusinessProposalData(String iBusinessNo, DBManager dbManager)
			throws Exception {
    BLPrpTmain blPrpTmain = new BLPrpTmain();
    com.sinosoft.prpall.pubfun.PubTools pubTools =  new com.sinosoft.prpall.pubfun.PubTools();
		try {
			/****************公共部分**********************/
			double pureRate = 0;
			double sumquantity = 1; 	//家财险分户数
			double coinsRate = 1;
			String coinsflag =""; 		//共保/联保标志位
			int startHour = 0;
			int endHour = 0; 
			double BWBexchangeRate = 1d;	//签单币别和本位币的兑换率
			int kindCountOutOfBD11D12 = 0;	//除三者险车上人员责任险外的其他险别外是否投保了其他险别
			int kindCountG = 0;  			//投保盗抢险且选择不计免赔
			int kindCountL = 0;  			//投保划痕险且选择不计免赔特约险
			int kindCountLPA4 = 0; 			//投保专修厂维修特约险或零配件更换险
			int kindCountAGAll = 0; 		//投保车损或盗抢或车损盗抢相关附加险的
			int kindCountA = 0;	 			//是否投保车损险
			int kindCountE = 0; 			//是否投保自燃险
			int tem = 0;					//临时变量
			double sharerate = 0;
			String permitRisk = null;//增加满足条件险种公用变量，便于维护
			this.tempProposalNo = iBusinessNo;//added by gengxiaobo 20080620 投保单业务数据对象添加投保单号
			String strSQL = "select * from prpTmain where proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rs1 = dbManager.executeQuery(strSQL);
			if (rs1.next()) {
				this.riskCode = rs1.getString("riskcode"); //险种
				this.classCode = rs1.getString("classCode"); //险别
				this.currency = rs1.getString("currency"); //币别信息
				BWBexchangeRate = rs1.getDouble("exchangeRate");
				if(BWBexchangeRate == 0){
					BWBexchangeRate = 1;
				}
				this.sumAmount = rs1.getDouble("sumAmount") * BWBexchangeRate; //总保额
				this.discount = rs1.getInt("discount"); //折扣率
				this.disRate1 = rs1.getDouble("disrate1"); //中间成本
				this.disRate = rs1.getInt("disrate"); //经济费和手续费比例
				this.contractno = rs1.getString("contractno"); //合同号
				this.SumPremium = rs1.getDouble("SumPremium"); //总保费
				this.businessNature = rs1.getString("BusinessNature"); //业务性质
				this.policySort = rs1.getString("policySort"); //保单种类
				pureRate = rs1.getDouble("PureRate"); //法三手续费比例(净费比例用)
				sumquantity = rs1.getDouble("sumquantity");
				this.sumquantity = rs1.getDouble("sumquantity");
				this.operateDate = rs1.getDate("operateDate"); //签单日期-车险
				this.signDate = rs1.getDate("SignDate"); //签单日期-非车险
				this.startDate = rs1.getDate("StartDate"); //起始日期
				this.endDate = rs1.getDate("endDate");     //结束时间
				startHour= rs1.getInt("startHour");        //起始时间
				endHour  = rs1.getInt("endHour");          //结束时间
				coinsflag = rs1.getString("coinsFlag");   //0.独家承保 1.主共保 2.从共保 3.主联保 4.从联保
				if (riskCode.equals("YAB0")) {
					this.prepayProtocol = "Y"; //预约协议
				}
				/*else if(classCode.equals("23")){
					this.sumquantity = rs1.getDouble("sumquantity");
				}*/
			} else {
				return;//说明业务类型不对，没有找到数据；
			}
			rs1.close();
			
			//返回投保单的提交核保日期--取最近一次的提交核保时间。
			strSQL = "select to_date(flowintime,'yyyy-mm-dd hh24:mi:ss') as flowintime from wflog where businessno = '"
					+ iBusinessNo + "' and nodeno = '1' and rownum =1 order by logno desc";
			ResultSet rsWflog = dbManager.executeQuery(strSQL);
			if (rsWflog.next()) {
				this.flowintime = rsWflog.getDate("flowintime"); //最近流入双核系统的时间
			}
			rsWflog.close();
		
			//System.out.println("--FlowInTime--"+new com.sinosoft.utility.string.Date(flowintime.toString()));
			//System.out.println("--startDate--"+new com.sinosoft.utility.string.Date(startDate.toString()));

			
			//需要整理倒签单的定义，是否需要用wflog 表中的提交时间为准
			if (businessNature.equals("05") || businessNature.equals("08")) { // 直接业务
				if (flowintime != null && startDate != null) { // 直接倒签单天数－车险
					DirectDay = pubTools.getDayMinus
					(new com.sinosoft.utility.string.Date(startDate.toString()), 0,
							new com.sinosoft.utility.string.Date(flowintime.toString()), 24);
					//System.out.println("--DirectDay"+DirectDay);

				}
			} else { // 代理业务
				if (flowintime != null && startDate != null) { // 代理倒签单天数－车险
					AgentDay = pubTools.getDayMinus
					(new com.sinosoft.utility.string.Date(startDate.toString()), 0,
							new com.sinosoft.utility.string.Date(flowintime.toString()), 24);
					//System.out.println("--AgentDay"+AgentDay);
				}
			}
			permitRisk = "0999,1501,1504,1505,1515,1516,1518,1526,1532,1598,1599,2354,2399,2798,2799,1531";		 //add by zhyi fubon-1955	
			 if(permitRisk.indexOf(this.riskCode)>0)
			 {//2799要不要去掉？？？
				 System.out.println("BLBusinessProposalData.java中riskCode=="+this.riskCode);
				 BLPrpTproduct blPrpTproduct = new BLPrpTproduct();
				 blPrpTproduct.getData(iBusinessNo);
				 if(blPrpTproduct.getSize()>0)
				 {
					 PrpTproductSchema prpTproductSchema = blPrpTproduct.getArr(0);
					 this.productcode = prpTproductSchema.getProductCode();
					 System.out.println("BLBusinessProposalData.java中productcode=="+productcode);
				 }
				 //added by xiongguojun 20090908 增加1598每次事故责任限额、每人人身伤亡责任限额双核条件 begin
				 strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '02' and  proposalno = '"
					 + iBusinessNo + "'";
				 ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
				 if (rsLimitAcc12.next()) {
					 this.LimitAcc12 = rsLimitAcc12.getDouble("limitfee") * BWBexchangeRate;
				 }
				 rsLimitAcc12.close();
				 
				 strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '05' and  proposalno = '"
					 + iBusinessNo + "'";
				 ResultSet rsLimitManAcc05 = dbManager.executeQuery(strSQL);
				 if (rsLimitManAcc05.next()) {
					 this.limitManAcc05 = rsLimitManAcc05.getDouble("limitfee") * BWBexchangeRate;
				 }
				 rsLimitManAcc05.close();
				 //added by xiongguojun 20090908 增加1598每次事故责任限额、每人人身伤亡责任限额双核条件 end
			 }
			// 返回险别
//			strSQL = "select * from prpTitemkind where proposalno = '"
//					+ iBusinessNo + "'";
//			ResultSet rsKindcode = dbManager.executeQuery(strSQL);
//			if (rsKindcode.next()) {
//				riskKind = rsKindcode.getString("kindcode");
//			}
//			rsKindcode.close();

			//允许保单注销天数
			/*
			 strSQL = "select * from prpTplan  where  proposalno ='"+iBusinessNo+"'";
			 ResultSet rsLogOut = dbManager.executeQuery(strSQL);
			 if(rsLogOut.next()){
			 Date plandate = rsLogOut.getDate("plandate");
			 Date planstartdate = rsLogOut.getDate("planstartdate");
			 if(plandate!=null&&planstartdate!=null){
			 writeOffDays = (plandate.getTime()-planstartdate.getTime())/(24*60*60*1000);
			 }
			 }
			 rsLogOut.close();
			 */
//
//			strSQL = "select * from prpTmainSub where proposalno = '"
//					+ iBusinessNo + "'";
//			ResultSet rsTmainSub = dbManager.executeQuery(strSQL);
//			if (rsTmainSub.next()) {
//				//有预约协议大保单
//				String strPolicyNoMain = rsTmainSub.getString("MainPolicyNo");
//				strSQL = "select * from prpCmain where policyno = '"
//						+ strPolicyNoMain + "'";
//				ResultSet rsCmainMain = dbManager.executeQuery(strSQL);
//				//小保单特殊因子、手续费比例小于等于预约协议特殊因子时，不再校验，业务取数值给 0
//				if (rsCmainMain.next()) {
//					if (disRate1 <= rsCmainMain.getDouble("disRate1")) { //特殊因子
//						disRate1 = 0;
//					}
//					if (disRate <= rsCmainMain.getDouble("disRate")) { //手续费
//						disRate = 0;
//					}
//				}
//				rsCmainMain.close();
//				strSQL = "select * from prpCmainCargo where policyno = '"
//						+ strPolicyNoMain + "'";
//				ResultSet rsCmainMainCargo = dbManager.executeQuery(strSQL);
//				//小保单特殊因子、手续费比例、总保额 小于等于预约协议特殊因子时，不再校验，业务取数值给 0
//				if (rsCmainMainCargo.next()) {
//					if (DirectDay <= rsCmainMainCargo.getInt("OriginalCount")) {
//						DirectDay = 0;
//					}
//					if (SumPremium <= rsCmainMainCargo
//							.getDouble("InvoiceAmount")) {
//						sumAmount = 0;
//					}
//				}
//				rsCmainMainCargo.close();
//			}
//			rsTmainSub.close();

			//预约协议的货运险
			if (riskCode.equals("YAB0")) {
				strSQL = "select * from prpTmainCargo where proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsTmainMainCargo = dbManager.executeQuery(strSQL);
				if (rsTmainMainCargo.next()) {
					sumAmount = rsTmainMainCargo.getDouble("InvoiceAmount");
					if (DirectDay < rsTmainMainCargo.getInt("OriginalCount")) {
						DirectDay = rsTmainMainCargo.getInt("OriginalCount");
					}
				} else {
					sumAmount = 0;
				}
				rsTmainMainCargo.close();
			}
			//兑换率 prpdexch表
			//modify by zhangruifeng 20080307 取最新的兑换率
			strSQL = "SELECT ExchRate FROM PrpDexch WHERE exchcurrency='CNY' AND ValidStatus='1' AND BaseCurrency='"
					+ this.currency + "' order by exchdate desc ";
			ResultSet rsExchRate = dbManager.executeQuery(strSQL);
			if (rsExchRate.next()) {
				this.exchRate = rsExchRate.getDouble("ExchRate");
			} else//如果未取得结果，exchRate取默认1
			{
				this.exchRate = 1;
			}
			rsExchRate.close();

			//获取折扣信息
//			strSQL = "SELECT * FROM PrpTitemkind WHERE ProposalNo ='"
//					+ iBusinessNo + "'";
//			ResultSet rsDisCount = dbManager.executeQuery(strSQL);
//			if (rsDisCount.next()) {
//				this.discount = rsDisCount.getInt("DisCount");
//				this.shortRate = rsDisCount.getDouble("ShortRate");
//			} else {
//				this.discount = 100;
//				this.shortRate = 100;
//			}
//			rsDisCount.close();
			//联(共)保标志
			strSQL = "select * from prpTmain where CoinsFlag in ('1','2')  and  proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsCoinsFlag = dbManager.executeQuery(strSQL);
			if (rsCoinsFlag.next()) {
				unitPolicy = "Y";
			}
			rsCoinsFlag.close();
			//暂保单
			strSQL = "select * from prpTmain where PolicySort = '2'  and  proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsSort = dbManager.executeQuery(strSQL);
			if (rsSort.next()) {
				tempPolicy = "Y";
			}
			rsSort.close();

			/******************车险部分*************************/
			
			if (classCode.equals("A") ||classCode.equals("B")  ) {
				//add by zhangruifeng 20080307 reason:查询此业务除三者险、车上人员责任险外还又没有投保其他险别
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode not in('B','D11','D12')";
				ResultSet rsKind = dbManager.executeQuery(strSQL);
				if (rsKind.next()) {
					kindCountOutOfBD11D12 = rsKind.getInt("kindCount");//除三者险、车上人员责任险外还又没有投保其他险别
				}
				rsKind.close();
				
				//车险新增*****************************************************************************************
				//保险期限（按月）
				this.CBMonthLimit = pubTools.getMonthMinus
					(new com.sinosoft.utility.string.Date(startDate.toString()),startHour,
							new com.sinosoft.utility.string.Date(endDate.toString()),endHour);
				
				/*
				 * 
				this.CBMonthLimit = pubTools.getYearMinus
					(new com.sinosoft.utility.string.Date(startDate.toString()),startHour,
						new com.sinosoft.utility.string.Date(endDate.toString()),endHour);

				this.CBMonthLimit = pubTools.getDayMinus
					(new com.sinosoft.utility.string.Date(startDate.toString()),startHour,
					new com.sinosoft.utility.string.Date(endDate.toString()),endHour);
				*
				*/
				
				//投保盗抢险且选择不计免赔
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode='G' and substr(flag,5,1)='1'";
				ResultSet rsKindG = dbManager.executeQuery(strSQL);
				if(rsKindG.next()){
					kindCountG = rsKindG.getInt("kindCount");
				}
				rsKindG.close();
				strSQL = "select * from prpTitemCar where proposalno ='"
						+ iBusinessNo + "'";
				ResultSet rsChooseG1Years = dbManager.executeQuery(strSQL);
				if(rsChooseG1Years.next()){
					if(kindCountG>0){
						ChooseG1Years = rsChooseG1Years.getInt("UseYears");//投保盗抢险且选择不计免赔的车辆使用年限
					}
				}
				rsChooseG1Years.close();
				/*
				 * //投保划痕险且选择不计免赔特约险
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode='L' and substr(flag,5,1)='1'";
				ResultSet rsKindL = dbManager.executeQuery(strSQL);
				if(rsKindL.next()){
					kindCountL = rsKindL.getInt("kindCount");
				}
				rsKindL.close();
				strSQL = "select * from prpTitemCar where proposalno ='"
						+ iBusinessNo + "'";
				ResultSet rsChooseL1Years = dbManager.executeQuery(strSQL);
				if(rsChooseL1Years.next()){
					if(kindCountL>0){
						ChooseL1Years = rsChooseL1Years.getInt("UseYears");//投保划痕险且选择不计免赔的车辆使用年限
					}
				}
				rsChooseL1Years.close();
				*
				*/
				//投保划痕险
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode='L'";
				ResultSet rsKindL = dbManager.executeQuery(strSQL);
				if(rsKindL.next()){
					kindCountL = rsKindL.getInt("kindCount");
				}
				rsKindL.close();
				strSQL = "select * from prpTitemCar where proposalno ='"
						+ iBusinessNo + "'";
				ResultSet rsChooseL1Years = dbManager.executeQuery(strSQL);
				if(rsChooseL1Years.next()){
					if(kindCountL>0){
						ChooseL1Years = rsChooseL1Years.getInt("UseYears");//投保划痕险的车辆使用年限
					}
				}
				rsChooseL1Years.close();
				
				//投保专修厂维修特约险或零配件更换险
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode in ('LP','A4')";
				ResultSet rsKindLPA4 = dbManager.executeQuery(strSQL);
				if(rsKindLPA4.next()){
					kindCountLPA4 = rsKindLPA4.getInt("kindCount");
				}
				rsKindLPA4.close();
				strSQL = "select * from prpTitemCar where proposalno ='"
						+ iBusinessNo + "'";
				ResultSet rsChooseLPA4Years = dbManager.executeQuery(strSQL);
				if(rsChooseLPA4Years.next()){
					if(kindCountLPA4>0){
						ChooseLPA4Years = rsChooseLPA4Years.getInt("UseYears");//投保专修厂维修特约险或零配件更换险的车辆使用年限
					}
				}
				rsChooseLPA4Years.close();
				
				//投保车损或盗抢或车损盗抢相关附加险的
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode in ('A','G')";
				ResultSet rsKindAGAll = dbManager.executeQuery(strSQL);
				if (rsKindAGAll.next()){
					kindCountAGAll = rsKindAGAll.getInt("kindCount");
				}
				rsKindAGAll.close();
				strSQL = "select * from prpTitemCar where proposalno ='"
					+ iBusinessNo + "'";
				ResultSet rsChooseAGAllears = dbManager.executeQuery(strSQL);
				if(rsChooseAGAllears.next()){
					if(kindCountAGAll>0){
						this.CarKindCode = rsChooseAGAllears.getString("CarKindCode");//车辆类型
						this.useNatureCode = rsChooseAGAllears.getString("UseNatureCode"); //使用性质
						//Choose投保 N非营业 Y营业 J家用 Years使用年限
						if(("8B".equals(this.useNatureCode)||"8C".equals(this.useNatureCode)||"8D".equals(this.useNatureCode))&&"A0".equals(this.CarKindCode)){
							ChooseNA0Years = rsChooseAGAllears.getInt("UseYears");//非营业客车使用年限
						}
						else if(("8B".equals(this.useNatureCode)||"8C".equals(this.useNatureCode)||"8D".equals(this.useNatureCode))&&"H0".equals(this.CarKindCode)){
							ChooseNH0Years = rsChooseAGAllears.getInt("UseYears");//非营业货车使用年限
						}
						else if("9A".equals(this.useNatureCode)||"9B".equals(this.useNatureCode)||"9C".equals(this.useNatureCode)||"9D".equals(this.useNatureCode)){
							ChooseYYears = rsChooseAGAllears.getInt("UseYears");//营业用车使用年限
						}
						else if("8A".equals(this.useNatureCode)){
							ChooseJYears = rsChooseAGAllears.getInt("UseYears");//家庭自用汽车使用年限
						}
					}
				}
				rsChooseAGAllears.close();
				
				//投保自燃险
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode='Z'";
				ResultSet rsKindE = dbManager.executeQuery(strSQL);
				if (rsKindE.next()){
					kindCountE = rsKindE.getInt("kindCount");
				}
				rsKindE.close();
				strSQL = "select * from prpTitemCar where proposalno ='"
					+ iBusinessNo + "'";
				ResultSet rsChooseEYears = dbManager.executeQuery(strSQL);
				if(rsChooseEYears.next()){
					if(kindCountE > 0){
						ChooseEYears = rsChooseEYears.getInt("UseYears");//投保自燃险的车辆使用年限
						if("8A".equals(useNatureCode)){
							ChooseEJYears = rsChooseEYears.getInt("UseYears");//投保自燃险的家用车使用年限
						}
					}
				}
				rsChooseEYears.close();
				
				//查出投保了指定的哪个附加险、条款
				strSQL = "select * from prpdkind where riskcode = '" + this.riskCode + "' and kindcode in (select kindcode from prptitemkind where proposalno ='"
				+ iBusinessNo +
				"'and kindcode in ('E','X1','T1','C','LT','SC','TF','U','NZ','NX','NY','V1','S','C5','TX','SZ','FZ','C6','J','K2','K1','X','D2','R'))";
				//投保了以下列附加险的业务：
				//火灾、爆炸、自燃损失险，发动机特别损失险，机动车停驶损失险，代步机动车服务特约条款，更换轮胎服务特约条款
				//送油、充电服务特约条款，拖车服务特约条款，换件特约条款，随车行李物品损失险，新车特约条款A
				//新车特约条款B，油污污染责任险，机动车出境险，异地出险住宿费特约条款，约定区域通行费用特约条款
				//租车人人车失踪险，车内附属装置单独被盗损失特约，法律费用特约条款，紧急救助特约条款，特种车辆固定设备、仪器损坏扩展条款
				//起重、装卸、挖掘车辆损失扩展条款，新增加设备损失险，车上货物责任险，交通事故精神损害赔偿责任险
				ResultSet rsLook = dbManager.executeQuery(strSQL);
				if (rsLook.next()){
					KindCode = rsLook.getString("kindcode");//条款代码
					KindCName = rsLook.getString("kindcname");//条款名称
				}
				rsLook.close();
				
				//投保指定附加险
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='" + iBusinessNo +
				"'and kindcode in ('E','X1','T1','C','LT','SC','TF','U','NZ','NX','NY','V1','S','C5','TX','SZ','FZ','C6','J','K2','K1')";
				ResultSet rsChooseZF = dbManager.executeQuery(strSQL);
				if (rsChooseZF.next()){
					tem = rsChooseZF.getInt("kindCount");				
				}
				if(tem>0){
					ChooseZF = "Y";
				}else{
					ChooseZF = "N";
				}
				rsChooseZF.close();
				
				tem = 0;//临时变量值归0
				
				//投保特定条款
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='" + iBusinessNo + "'and kindcode in ('X','D2','R')";
				ResultSet rsChooseTD = dbManager.executeQuery(strSQL);
				if (rsChooseTD.next()){
					tem = rsChooseTD.getInt("kindCount");				
				}
				if(tem>0){
					ChooseTD = "Y";
				}else{
					ChooseTD = "N";
				}
				rsChooseTD.close();
				
				tem = 0;//临时变量值归0
				
				//未投保第三者责任险的投保单
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode='B'";
				ResultSet rsNotChooseB = dbManager.executeQuery(strSQL);
				if(rsNotChooseB.next()){
					tem = rsNotChooseB.getInt("kindCount");			
				}
				if(tem>0){
					NotChooseB = "N";
				}else{
					NotChooseB = "Y";
				}
				rsNotChooseB.close();
				
				//******************************************************************************************车险新增
				
				//投保车损险
				strSQL = "select count(*) as kindCount from prptitemkind where proposalno ='"+iBusinessNo+
				"'and kindcode = 'A'";
				ResultSet rsKindA = dbManager.executeQuery(strSQL);
				if (rsKindA.next()) {
					kindCountA = rsKindA.getInt("kindCount");
				}
				rsKindA.close();
				
				//从投保单ItemCar表获取数据
				strSQL = "select * from prpTitemCar where proposalno ='"
						+ iBusinessNo + "'";
				ResultSet rsItem = dbManager.executeQuery(strSQL);
				if (rsItem.next()) {
					this.insuredTypeCode = rsItem.getString("InsuredTypeCode");//客户性质
					this.useNatureCode = rsItem.getString("UseNatureCode"); //使用性质
					this.modelCode = rsItem.getString("ModelCode"); //车型信息
					this.CarKindCode = rsItem.getString("CarKindCode"); //车辆类型
					if(kindCountOutOfBD11D12>0){
						this.useYears = rsItem.getInt("UseYears"); //使用年限
					}else if(!("9A".equals(this.useNatureCode)||"9B".equals(this.useNatureCode)
							||"9C".equals(this.useNatureCode)||"9D".equals(this.useNatureCode))){
						this.useYears = rsItem.getInt("UseYears"); //使用年限
					}else {
						this.OnlyABYears = rsItem.getInt("UseYears"); //仅投保三者险、车上人员责任险使用年限
					}
					if(kindCountA>0){
						this.ChooseAPrice = rsItem.getDouble("purchaseprice");//投保车损险的新车购置价
					}
					if("H0".equals(this.CarKindCode)){
						this.tonCountH0 = rsItem.getDouble("tonCount");//货车的核定载质量
					}
				}
				rsItem.close();
				
				//add by zhulei 20060330 净费比例算法，统一调用BLPrpallFacade.getOutLayRate()
				/*
				 BLPrpallFacade blPrpallFacade = new BLPrpallFacade();
				 outLayRate = blPrpallFacade.getOutLayRate(iBusinessNo,"T");
				 //add by zhulei 20060330 净费比例算法，统一调用BLPrpallFacade.getOutLayRate()
				 */
				
				//返回是否允许招标信息返回团购车的数量
				strSQL = "select * from PrpMotorcade where contractno =  '"
						+ contractno + "'";
				ResultSet rsMinus = dbManager.executeQuery(strSQL);
				if (rsMinus.next()) {
					permitBidding = rsMinus.getString("minusflag");//返回是否允许招标信息
					if (permitBidding != null && permitBidding.equals("3")) {
						permitBidding = "Y";
					} else {
						permitBidding = "N";
					}
					groupCarSum = rsMinus.getInt("carcount");//返回团购车的数量
				}
				rsMinus.close();

				//招标系数下浮比例
				strSQL = "select ProfitRate  from prpTprofitdetail where  profitcode='C14'  and proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsProfitRate = dbManager.executeQuery(strSQL);
				if (rsProfitRate.next()) {
					double biddingDownProportion = rsProfitRate
							.getDouble("ProfitRate");
				}
				rsProfitRate.close();
				
				//新增设备保额
				String strAmountNew = "select sum(actualvalue) as amount from prptcardevice where riskcode='"+this.riskCode+"' and proposalno='"
									+ iBusinessNo + "'";
				ResultSet rsAmountNew = dbManager.executeQuery(strAmountNew);
				if (rsAmountNew.next()){
					amountNew = rsAmountNew.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountNew.close();
				
				//返回车痕险保额
				strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"' and kindcode = 'L' and  proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountL = dbManager.executeQuery(strSQL);
				if (rsAmountL.next()) {
					amountL = rsAmountL.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountL.close();
				
				//返回交通事故精神损害赔偿责任险保额/交通事故精神损害赔偿责任险每人每次限额
				strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"' and kindcode = 'R' and  proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountR = dbManager.executeQuery(strSQL);
				if (rsAmountR.next()) {
					amountR = rsAmountR.getDouble("amount") * BWBexchangeRate;
					amountRPer = rsAmountR.getDouble("unitamount") * BWBexchangeRate;
				}
				rsAmountR.close();

				//返回车损保额
				strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"' and kindcode = 'A' and  proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmount = dbManager.executeQuery(strSQL);
				if (rsAmount.next()) {
					amountA = rsAmount.getDouble("amount") * BWBexchangeRate;
				}
				rsAmount.close();
				
				//返回全车盗抢险
				strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"' and kindcode = 'G' and proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountG = dbManager.executeQuery(strSQL);
				if (rsAmountG.next()) {
					amountG = rsAmountG.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountG.close();
				
				//返回自燃损失险
				strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"'and kindcode = 'Z' and proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountZ = dbManager.executeQuery(strSQL);
				if (rsAmountZ.next()) {
					amountZ = rsAmountZ.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountZ.close();
				
				//返回第三者综合险
				strSQL = "Select * from prpTitemkind where riskcode='"+this.riskCode+"' and kindcode = 'B' and proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountB = dbManager.executeQuery(strSQL);
				if (rsAmountB.next()) {
					amountB = rsAmountB.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountB.close();
				
				//返回车上人员责任险/每座
				String strAmountD11 = "SELECT amount FROM PrpTitemkind WHERE KindCode = 'D11' AND proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountD11 = dbManager.executeQuery(strAmountD11);
				if (rsAmountD11.next()) {
					amountD11 = rsAmountD11.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountD11.close();
				
				//返回车上人员责任险/每座
				String strAmountD12 = "SELECT sum(amount)/sum(quantity) as amount FROM PrpTitemkind WHERE KindCode = 'D12' AND proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountD12 = dbManager.executeQuery(strAmountD12);
				if (rsAmountD12.next()) {
					amountD12 = rsAmountD12.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountD12.close();
				
				//返回车上人员责任险总保额
				String strAmountManSum = "select sum(amount) as amount from prptitemkind where kindcode in ('D11','D12') and proposalno='"
						+ iBusinessNo + "'";
				ResultSet rsAmountManSum = dbManager.executeQuery(strAmountManSum);
				if (rsAmountManSum.next()){
					AmountManSum = rsAmountManSum.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountManSum.close();
				
				//返回车上货物责任险
				String strAmountD2 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'D2' AND proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountD2 = dbManager.executeQuery(strAmountD2);
				if (rsAmountD2.next()) {
					amountD2 = rsAmountD2.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountD2.close();
				
				//随行物品损失责任保额
				String strAmountW = "SELECT * FROM PrpTitemkind WHERE KindCode = 'NZ' AND proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsAmountW = dbManager.executeQuery(strAmountW);
				if (rsAmountW.next()) {
					amountW = rsAmountW.getDouble("amount") * BWBexchangeRate;
				}
				rsAmountW.close();
				
				//第四类特种车盗抢险保额
				if (riskCode.equals("0502")) {
					/*String strAmountG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'G' AND riskcode='0502' and " +
							" proposalno in"+
	                   "(select proposalno from prpTitemcar where carkindcode='G1' and proposalno='"
						    + iBusinessNo + "')";*/
					String strAmountG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'G' AND riskcode='0502' and " +
						" proposalno ='" + iBusinessNo + "'";
				    ResultSet rsAmountG1 = dbManager.executeQuery(strAmountG1);
				    if (rsAmountG1.next()) {
					   amountG1 = rsAmountG1.getDouble("amount") * BWBexchangeRate;				   
				    }
				    rsAmountG1.close();
				    
				    /*String stramountAG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'A' AND riskcode='0502' and " +
					" proposalno in"+
	                "(select proposalno from prpTitemcar where carkindcode='G1' and proposalno='"
						    + iBusinessNo + "')";*/
				    String stramountAG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'A' AND riskcode='0502' and " +
						" proposalno ='"+ iBusinessNo + "'";
				    ResultSet rsAmountAG1 = dbManager.executeQuery(stramountAG1);
				    if (rsAmountAG1.next()) {
				    	amountAG1 = rsAmountAG1.getDouble("amount") * BWBexchangeRate;
				    }
				    rsAmountAG1.close();
				    
				    /*String stramountBG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'B' AND riskcode='0502' and " +
					" proposalno in"+
	                "(select proposalno from prpTitemcar where carkindcode='G1' and proposalno='"
						    + iBusinessNo + "')";*/
				    String stramountBG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'B' AND riskcode='0502' and " +
						" proposalno ='"+ iBusinessNo + "'";
				    ResultSet rsAmountBG1 = dbManager.executeQuery(stramountBG1);
				    if (rsAmountBG1.next()) {
				    	amountBG1 = rsAmountBG1.getDouble("amount") * BWBexchangeRate;
				    }
				    rsAmountBG1.close();
				}
				
                //modify by zhangruifeng 20080307 begin reason:需要控制车损险、三者险的净自留额
				//当reinsmode=181 为自留额；reinsmode=182 为附加自留额
				strSQL =  "Select sum(sharerate) as sharerate  From prptreinsshare Where proposalNo='"
					+iBusinessNo+"' And reinsmode in( '181','182')";
				ResultSet rsSharerate = dbManager.executeQuery(strSQL);
				if (rsSharerate.next()) {
					sharerate = rsSharerate.getDouble("sharerate");//客户性质
					this.SuttleAmountA = amountA*sharerate/100;//车损险的净自留额=车损险的保额*我方份额比例
					this.SuttleAmountB = amountB*sharerate/100;//三者险的净自留额=三者险的保额*我方份额比例
				}
				rsSharerate.close();
			}

			/** ******非车非意健部分******** */
			if (classCode.equals("03")) {
//				strSQL = "select sumquantity from prptmain where proposalno='"
//						+ iBusinessNo + "'";
//				ResultSet rsDisprptmain = dbManager.executeQuery(strSQL);
//				if (rsDisprptmain.next()) {
//					sumquantity = rsDisprptmain.getDouble("sumquantity");
//				}
//				rsDisprptmain.close();
//
//				if (sumquantity != 0) {
//					sumAmount = sumAmount / sumquantity;
//				}
				//modify by zhangruifeng 20080122 begin reason:处理家财险按照每户的总保额进行控制
				strSQL = "SELECT  MAX(SUM(amount)) AS sumAmount FROM prptitemkind  where calculateflag='Y' and proposalno = '"+iBusinessNo+"'" +
						" group by FAMILYNO";
				ResultSet rsPrpTitemKind = dbManager.executeQuery(strSQL);
				if (rsPrpTitemKind.next()) {
					this.sumAmount = rsPrpTitemKind.getDouble("sumAmount") * BWBexchangeRate;
				}
				rsPrpTitemKind.close();
				//modify by zhangruifeng 20080122 end 
				if(sumquantity==1){//当只有一户时承保时能录入相同的两条标的,所以需要求和处理
                //房屋及室内附属设备
					String strSQL1 = "select SUM(amount) as amount from prptitemkind where itemcode ='0001' and  proposalno='"
							+ iBusinessNo + "'";
					ResultSet rsAmoutPer03010001 = dbManager.executeQuery(strSQL1);
					if (rsAmoutPer03010001.next()) {
						this.amountPer03010001 = rsAmoutPer03010001
								.getDouble("amount") * BWBexchangeRate;
					}
					rsAmoutPer03010001.close();
					//室内装潢
					String strSQL2 = "select SUM(amount) as amount from prptitemkind where itemcode ='0002' and  proposalno='"
							+ iBusinessNo + "'";
					ResultSet rsAmoutPer03010002 = dbManager.executeQuery(strSQL2);
					if (rsAmoutPer03010002.next()) {
						this.amountPer03010002 = rsAmoutPer03010002
								.getDouble("amount") * BWBexchangeRate;
					}
					rsAmoutPer03010002.close();
					if (riskCode.equals("0301")){
						//0301家庭财产保险附加险 附加盗抢保险条款保额
						String strSql = "select amount as amount from prptitemkind where kindcode='9000452' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000452 = dbManager.executeQuery(strSql);
						while(rsAmountPer9000452.next()){				
							this.amountPer9000452 = rsAmountPer9000452.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000452.close();
						//0301家庭财产保险附加险 附加家用电器用电安全保险条款保额
						String strSql1 = "select amount as amount from prptitemkind where kindcode='9000453' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000453 = dbManager.executeQuery(strSql1);
						while(rsAmountPer9000453.next()){				
							this.amountPer9000453 = rsAmountPer9000453.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000453.close();
						//0301家庭财产保险附加险 附加管道破裂及水渍保险条款保额
						String strSql2 = "select amount as amount from prptitemkind where kindcode='9000454' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000454 = dbManager.executeQuery(strSql2);
						while(rsAmountPer9000454.next()){				
							this.amountPer9000454 = rsAmountPer9000454.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000454.close();
						//0301家庭财产保险附加险 附加居家责任保险条款保额
						String strSql3 = "select amount as amount from prptitemkind where kindcode='9000449' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000449 = dbManager.executeQuery(strSql3);
						while(rsAmountPer9000449.next()){				
							this.amountPer9000449 = rsAmountPer9000449.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000449.close();
						//0301家庭财产保险附加险 附加家庭伤害保险条款保额
						String strSql4 = "select amount as amount from prptitemkind where kindcode='9000450' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000450 = dbManager.executeQuery(strSql4);
						while(rsAmountPer9000450.next()){				
							this.amountPer9000450 = rsAmountPer9000450.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000450.close();
						//0301家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
						String strSql5 = "select amount as amount from prptitemkind where kindcode='9000451' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000451 = dbManager.executeQuery(strSql5);
						while(rsAmountPer9000451.next()){				
							this.amountPer9000451 = rsAmountPer9000451.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000451.close();
					}else if (riskCode.equals("0309")){
						//0309家庭财产保险附加险 附加盗抢保险条款保额
						String strSql = "select amount as amount from prptitemkind where kindcode='0309200' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000452 = dbManager.executeQuery(strSql);
						while(rsAmountPer9000452.next()){				
							this.amountPer9000452 = rsAmountPer9000452.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000452.close();
						//0309家庭财产保险附加险 附加家用电器用电安全保险条款保额
						String strSql1 = "select amount as amount from prptitemkind where kindcode='0309300' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000453 = dbManager.executeQuery(strSql1);
						while(rsAmountPer9000453.next()){				
							this.amountPer9000453 = rsAmountPer9000453.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000453.close();
						//0309家庭财产保险附加险 附加管道破裂及水渍保险条款保额
						String strSql2 = "select amount as amount from prptitemkind where kindcode='0309400' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000454 = dbManager.executeQuery(strSql2);
						while(rsAmountPer9000454.next()){				
							this.amountPer9000454 = rsAmountPer9000454.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000454.close();
						//0309家庭财产保险附加险 附加居家责任保险条款保额
						String strSql3 = "select amount as amount from prptitemkind where kindcode='0309500' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000449 = dbManager.executeQuery(strSql3);
						while(rsAmountPer9000449.next()){				
							this.amountPer9000449 = rsAmountPer9000449.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000449.close();
						//0309家庭财产保险附加险 附加家庭伤害保险条款保额
						String strSql4 = "select amount as amount from prptitemkind where kindcode='0309700' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000450 = dbManager.executeQuery(strSql4);
						while(rsAmountPer9000450.next()){				
							this.amountPer9000450 = rsAmountPer9000450.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000450.close();
						//0309家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
						String strSql5 = "select amount as amount from prptitemkind where kindcode='0309600' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000451 = dbManager.executeQuery(strSql5);
						while(rsAmountPer9000451.next()){				
							this.amountPer9000451 = rsAmountPer9000451.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000451.close();					
					}else if (riskCode.equals("0310")){
						//0310附加住宅火灾事故延烧自有车辆保险
						String strSql = "select amount as amount from prptitemkind where kindcode='0310200' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000455 = dbManager.executeQuery(strSql);
						while(rsAmountPer9000455.next()){				
							this.amountPer9000455 = rsAmountPer9000455.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000455.close();
						//0310附加租房费用损失保险
						String strSql1 = "select amount as amount from prptitemkind where kindcode='0310300' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000456 = dbManager.executeQuery(strSql1);
						while(rsAmountPer9000456.next()){				
							this.amountPer9000456 = rsAmountPer9000456.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000456.close();
						//0310附加家庭火灾火场清理费用损失保险
						String strSql2 = "select amount as amount from prptitemkind where kindcode='0310400' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000457 = dbManager.executeQuery(strSql2);
						while(rsAmountPer9000457.next()){				
							this.amountPer9000457 = rsAmountPer9000457.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000457.close();
						//0310附加搬迁费用损失保险
						String strSql3 = "select amount as amount from prptitemkind where kindcode='0310500' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000458 = dbManager.executeQuery(strSql3);
						while(rsAmountPer9000458.next()){				
							this.amountPer9000458 = rsAmountPer9000458.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000458.close();			
					}
				}else{//当是团单是按照每户的保险金额来判断
					//房屋及室内附属设备
					String strSQL1 = "select Max(amount) as amount from prptitemkind where itemcode ='0001' and  proposalno='"
							+ iBusinessNo + "'";
					System.out.println(strSQL1);
					ResultSet rsAmoutPer03010001 = dbManager.executeQuery(strSQL1);
					if (rsAmoutPer03010001.next()) {
						this.amountPer03010001 = rsAmoutPer03010001
								.getDouble("amount") * BWBexchangeRate;
					}
					rsAmoutPer03010001.close();
					//室内装潢
					String strSQL2 = "select Max(amount) as amount from prptitemkind where itemcode ='0002' and  proposalno='"
							+ iBusinessNo + "'";
					ResultSet rsAmoutPer03010002 = dbManager.executeQuery(strSQL2);
					if (rsAmoutPer03010002.next()) {
						this.amountPer03010002 = rsAmoutPer03010002
								.getDouble("amount") * BWBexchangeRate;
					}
					rsAmoutPer03010002.close();
					if (riskCode.equals("0301")){
						//0301家庭财产保险附加险 附加盗抢保险条款保额
						String strSql = "select Max(amount) as amount from prptitemkind where kindcode='9000452' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000452 = dbManager.executeQuery(strSql);
						while(rsAmountPer9000452.next()){				
							this.amountPer9000452 = rsAmountPer9000452.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000452.close();
						//0301家庭财产保险附加险 附加家用电器用电安全保险条款保额
						String strSql1 = "select Max(amount) as amount from prptitemkind where kindcode='9000453' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000453 = dbManager.executeQuery(strSql1);
						while(rsAmountPer9000453.next()){				
							this.amountPer9000453 = rsAmountPer9000453.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000453.close();
						//0301家庭财产保险附加险 附加管道破裂及水渍保险条款保额
						String strSql2 = "select Max(amount) as amount from prptitemkind where kindcode='9000454' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000454 = dbManager.executeQuery(strSql2);
						while(rsAmountPer9000454.next()){				
							this.amountPer9000454 = rsAmountPer9000454.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000454.close();
						//0301家庭财产保险附加险 附加居家责任保险条款保额
						String strSql3 = "select Max(amount) as amount from prptitemkind where kindcode='9000449' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000449 = dbManager.executeQuery(strSql3);
						while(rsAmountPer9000449.next()){				
							this.amountPer9000449 = rsAmountPer9000449.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000449.close();
						//0301家庭财产保险附加险 附加家庭伤害保险条款保额
						String strSql4 = "select Max(amount) as amount from prptitemkind where kindcode='9000450' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000450 = dbManager.executeQuery(strSql4);
						while(rsAmountPer9000450.next()){				
							this.amountPer9000450 = rsAmountPer9000450.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000450.close();
						//0301家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
						String strSql5 = "select Max(amount) as amount from prptitemkind where kindcode='9000451' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000451 = dbManager.executeQuery(strSql5);
						while(rsAmountPer9000451.next()){				
							this.amountPer9000451 = rsAmountPer9000451.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000451.close();
					}else if (riskCode.equals("0309")){
						//0309家庭财产保险附加险 附加盗抢保险条款保额
						String strSql = "select Max(amount) as amount from prptitemkind where kindcode='0309200' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000452 = dbManager.executeQuery(strSql);
						while(rsAmountPer9000452.next()){				
							this.amountPer9000452 = rsAmountPer9000452.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000452.close();
						//0309家庭财产保险附加险 附加家用电器用电安全保险条款保额
						String strSql1 = "select Max(amount) as amount from prptitemkind where kindcode='0309300' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000453 = dbManager.executeQuery(strSql1);
						while(rsAmountPer9000453.next()){				
							this.amountPer9000453 = rsAmountPer9000453.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000453.close();
						//0309家庭财产保险附加险 附加管道破裂及水渍保险条款保额
						String strSql2 = "select Max(amount) as amount from prptitemkind where kindcode='0309400' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000454 = dbManager.executeQuery(strSql2);
						while(rsAmountPer9000454.next()){				
							this.amountPer9000454 = rsAmountPer9000454.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000454.close();
						//0309家庭财产保险附加险 附加居家责任保险条款保额
						String strSql3 = "select Max(amount) as amount from prptitemkind where kindcode='0309500' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000449 = dbManager.executeQuery(strSql3);
						while(rsAmountPer9000449.next()){				
							this.amountPer9000449 = rsAmountPer9000449.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000449.close();
						//0309家庭财产保险附加险 附加家庭伤害保险条款保额
						String strSql4 = "select Max(amount) as amount from prptitemkind where kindcode='0309700' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000450 = dbManager.executeQuery(strSql4);
						while(rsAmountPer9000450.next()){				
							this.amountPer9000450 = rsAmountPer9000450.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000450.close();
						//0309家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
						String strSql5 = "select Max(amount) as amount from prptitemkind where kindcode='0309600' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000451 = dbManager.executeQuery(strSql5);
						while(rsAmountPer9000451.next()){				
							this.amountPer9000451 = rsAmountPer9000451.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000451.close();					
					}else if (riskCode.equals("0310")){
						//0310附加住宅火灾事故延烧自有车辆保险
						String strSql = "select amount as amount from prptitemkind where kindcode='0310200' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000455 = dbManager.executeQuery(strSql);
						while(rsAmountPer9000455.next()){				
							this.amountPer9000455 = rsAmountPer9000455.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000455.close();
						//0310附加租房费用损失保险
						String strSql1 = "select amount as amount from prptitemkind where kindcode='0310300' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000456 = dbManager.executeQuery(strSql1);
						while(rsAmountPer9000456.next()){				
							this.amountPer9000456 = rsAmountPer9000456.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000456.close();
						//0310附加家庭火灾火场清理费用损失保险
						String strSql2 = "select amount as amount from prptitemkind where kindcode='0310400' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000457 = dbManager.executeQuery(strSql2);
						while(rsAmountPer9000457.next()){				
							this.amountPer9000457 = rsAmountPer9000457.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000457.close();
						//0310附加搬迁费用损失保险
						String strSql3 = "select amount as amount from prptitemkind where kindcode='0310500' and proposalno='"+iBusinessNo+"'";
						ResultSet rsAmountPer9000458 = dbManager.executeQuery(strSql3);
						while(rsAmountPer9000458.next()){				
							this.amountPer9000458 = rsAmountPer9000458.getDouble("amount") * BWBexchangeRate;
						}
						rsAmountPer9000458.close();			
					}
				}
				
				//add by hanxiao begin 20091027 每人保额
				//每人保额 可以扩展为公共的，默认为不配置则可以通过。
				String strSQLUnitAmout03 = "select Max(unitamount) as unitamount from prptitemkind where proposalno='"+ iBusinessNo + "'";
				ResultSet rsUnitAmout03 = dbManager.executeQuery(strSQLUnitAmout03);
				if (rsUnitAmout03.next()) {
					this.unitAmount03 = rsUnitAmout03
							.getDouble("unitamount") * BWBexchangeRate;
				}
				rsUnitAmout03.close();
				//add by hanxiao end 20091027 每人保额
			}
			
			/** ***********工程保险************* */
			if (classCode.equals("07")) {
				//建工险需要控制第三者累计赔偿限额---add by xuning gpic 20061012
				strSQL = "select * from prpTLimit where limittype = '11' and proposalno = '"
						+ iBusinessNo + "'";
				System.out.println("strSQL07==="+strSQL);
				ResultSet rsThirdLimitSum07 = dbManager.executeQuery(strSQL);
				if (rsThirdLimitSum07.next()) {
					this.thirdLimitSum07 = rsThirdLimitSum07
							.getDouble("limitfee")
							* this.exchRate; //第三者累计赔偿限额
					System.out.println("thirdLimitSum07===="+thirdLimitSum07);

				}
				rsThirdLimitSum07.close();
				strSQL = "select * from prpTLimit where limittype = '10' and proposalno = '"
						+ iBusinessNo + "'";
				ResultSet rsThirdLimitAcc07 = dbManager.executeQuery(strSQL);
				if (rsThirdLimitAcc07.next()) {
					this.thirdLimitAcc07 = rsThirdLimitAcc07
							.getDouble("limitfee")
							* this.exchRate; //第三者累计赔偿限额

				}
				rsThirdLimitAcc07.close();
			}
			
			//货运险
			if (classCode.equals("10")||classCode.equals("09")) {//进出口货物的加成比例-国内货运
				//modify by duhaichao 20100901 加成比例取值 begin				
				strSQL = "select * from prptmaincargo where  proposalno = '"
						+ iBusinessNo + "'";
				//strSQL = "select * from prptitemkind where proposalno = '"+ iBusinessNo + "'";
				System.out.println("strSQL=="+strSQL);
				ResultSet rsplusRate10 = dbManager.executeQuery(strSQL);
				if (rsplusRate10.next()) {
					this.plusRate = rsplusRate10.getDouble("plusRate");
					//this.plusRate = rsplusRate10.getDouble("adjustrate");
					System.out.println("plusRateplusRate==="+plusRate);
					this.plusRate = (this.plusRate - 100) / 100;
					//modify by duhaichao 20100901 加成比例取值 end
				}
				rsplusRate10.close();
				//船龄的控制
				//	strSQL = "select * from prptriskvaluat where  riskvaluatcode = '0001' and  proposalno = '"
				//			+ iBusinessNo + "'";
				strSQL = "select * from prptmaincargosub where proposalno='" + iBusinessNo + "'";
				ResultSet rsshipAge = dbManager.executeQuery(strSQL);
				if (rsshipAge.next()) {
					this.shipAge = rsshipAge.getDouble("shipAge");
				}
				rsshipAge.close();
				if(riskCode.equals("1001")){
					String temm;
					strSQL = "select * from prptriskvaluat where  riskvaluatcode = '0001' and  proposalno = '" + iBusinessNo + "'";
					ResultSet rsshipAge1 = dbManager.executeQuery(strSQL);
					if (rsshipAge1.next()) {
						temm = rsshipAge1.getString("riskvaluatvalue");
						if("0001".equals(temm)||"0002".equals(temm)||"0003".equals(temm)){
							this.shipAge = -1;
						}else if("0004".equals(temm)){
							this.shipAge = 26;
							this.shipAge1001 = "26-30年";
						}else if("0005".equals(temm)){
							this.shipAge = 26;
							this.shipAge1001 = "31年以上";
						}
					}
					rsshipAge1.close();
				}
				//added by liuwei begin 20090303 0911国内货运险（08版）增加附加险核保因子
				if(riskCode.equals("0911")){
					strSQL = "select * from prptitemkind where proposalno='" +iBusinessNo+"' and kindcode='0911700' "
					 +" and riskcode ='"+this.riskCode+"'";
				ResultSet rs0911700 = dbManager.executeQuery(strSQL);
				if(rs0911700.next()) {
					this.Allow0911700 ="Y";  // 附加提货不着扩展条款承保权限的控制,当选择后，对应保单需要提交提交总公司一C才能核保通过
				}
				rs0911700.close();	
				}
				//added by liuwei end 20090303
				strSQL = "SELECT * FROM PrpTmain WHERE ProposalNo='"+ iBusinessNo + "'";
				ResultSet rsPolicyType0902 = dbManager.executeQuery(strSQL);
				if (rsPolicyType0902.next())
					this.PolicyType0902 = rsPolicyType0902.getString("PolicyType");
					System.out.println("******************BLBusinessProposalData  PolicyType  ==="+this.PolicyType0902);
				rsPolicyType0902.close();
			}
			
			/** ***********责任险*************** */
			if (classCode.equals("15")) {// 责任险
				if (riskCode.equals("1501")||riskCode.equals("1522")||riskCode.equals("1508")
						||riskCode.equals("1503")||riskCode.equals("1524")||riskCode.equals("1521")||riskCode.equals("1527")
						||riskCode.equals("1519")||riskCode.equals("1548")||riskCode.equals("1531") || riskCode.equals("1516")) {//add by zhyi 1515 fubon-1955每次事故責任限額
					strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '02' and  proposalno = '"
							+ iBusinessNo + "'";
					ResultSet rsLimitMan01 = dbManager.executeQuery(strSQL);
					if (rsLimitMan01.next()) {
						this.limitAmount02 = rsLimitMan01.getDouble("limitfee")
								* this.exchRate; // 客户现场修改：增加了汇率计算
					}
					rsLimitMan01.close();
				}
				permitRisk = "1501,1504,1515,1518,1526";
				if (permitRisk.indexOf(riskCode)>0){
					strSQL = "select Max(limitfee) limitfee from prptlimit where limittype='37' and  proposalno='"
						+ iBusinessNo + "'";
					ResultSet rsLimitAcc03 = dbManager.executeQuery(strSQL);
					if (rsLimitAcc03.next()){
						this.LimitAcc03 = rsLimitAcc03.getDouble("limitfee") * this.exchRate;
					}
					rsLimitAcc03.close();
				}
				permitRisk = "1501,1502,1503,1508,1509,1519,1520,1522,1523,1524,1527,1531,1532,1548";
				if (permitRisk.indexOf(riskCode)>0)
				{
					strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '03' and  proposalno = '"
							+ iBusinessNo + "'";
					ResultSet rsLimitMan02 = dbManager.executeQuery(strSQL);
					if (rsLimitMan02.next()) {
						this.limitAmount03 = rsLimitMan02.getDouble("limitfee")
								* this.exchRate; // 客户现场修改：增加了汇率计算
					}
					rsLimitMan02.close();
				}
				permitRisk = "1502,1517,1518";
				if (permitRisk.indexOf(riskCode)>0) {
					strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '66' and  proposalno = '"
							+ iBusinessNo + "'";
					ResultSet rsLimitMan03 = dbManager.executeQuery(strSQL);
					if (rsLimitMan03.next()) {
						this.limitAmount66 = rsLimitMan03.getDouble("limitfee")
								* this.exchRate; // 客户现场修改：增加了汇率计算
					}
					rsLimitMan03.close();
				}
				
				if (riskCode.equals("1507")) {
					strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '46' and  proposalno = '"
							+ iBusinessNo + "'";
					ResultSet rsLimitMan03 = dbManager.executeQuery(strSQL);
					if (rsLimitMan03.next()) {
						this.limitAmount46 = rsLimitMan03.getDouble("limitfee")
								* this.exchRate; // 客户现场修改：增加了汇率计算
					}
					rsLimitMan03.close();
					
					strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '51' and  proposalno = '"
							+ iBusinessNo + "'";
					ResultSet rsLimitMan04 = dbManager.executeQuery(strSQL);
					if (rsLimitMan04.next()) {
						this.limitAmount46 = rsLimitMan04.getDouble("limitfee")
								* this.exchRate; // 客户现场修改：增加了汇率计算
					}
					rsLimitMan04.close();
				}
				
				if (riskCode.equals("1515")||riskCode.equals("1526")) {
					
//					strSQL = "select * from prpTLimit where limittype = '01' and  proposalno = '"
//							+ iBusinessNo + "'";
//					ResultSet rsLimitMan = dbManager.executeQuery(strSQL);
//					if (rsLimitMan.next()) {
//						this.sumAmount = rsLimitMan.getDouble("limitfee")
//								* this.exchRate; // 客户现场修改：增加了汇率计算
//					}
//					rsLimitMan.close();
					
	               //1515,1526每次事故赔偿限额 
				   strSQL = "select limitfee as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='02' and limitno='1'";
					ResultSet rsLimitAcc3 = dbManager.executeQuery(strSQL);
					blPrpTmain.getData(iBusinessNo);
					//JudicalScope表示范围1.国内 2.世界范围(除美加) 3.世界范围(含美加) 
					if (rsLimitAcc3.next()) {
						if(blPrpTmain.getSize()>0){
							if("1".equals(blPrpTmain.getArr(0).getJudicalScope())){
								this.LimitAcc1 = rsLimitAcc3.getDouble("limitFee");
							}else if("2".equals(blPrpTmain.getArr(0).getJudicalScope())){
								this.LimitAcc2 = rsLimitAcc3.getDouble("limitFee");
							}else if("3".equals(blPrpTmain.getArr(0).getJudicalScope())){
								this.LimitAcc3 = rsLimitAcc3.getDouble("limitFee");
							}else{								
							}
							//System.out.println("LimitAcc2==="+LimitAcc2);
						}						
					}
					rsLimitAcc3.close();
					
					//1515,1526累计赔偿限额 
				   	strSQL = "select limitfee as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='03' and limitno='1'";
					ResultSet rs1526SumAmount = dbManager.executeQuery(strSQL);
					blPrpTmain.getData(iBusinessNo);
					//JudicalScope表示范围1.国内 2.世界范围(除美加) 3.世界范围(含美加) 
					if (rs1526SumAmount.next()) {
						if(blPrpTmain.getSize()>0){
							if("1".equals(blPrpTmain.getArr(0).getJudicalScope())){
								this.SumAmount1 = rs1526SumAmount.getDouble("limitFee");
							}else if("2".equals(blPrpTmain.getArr(0).getJudicalScope())){
								this.SumAmount2 = rs1526SumAmount.getDouble("limitFee");
							}else if("3".equals(blPrpTmain.getArr(0).getJudicalScope())){
								this.SumAmount3 = rs1526SumAmount.getDouble("limitFee");
							}else{
							}							
						}
					}
					rs1526SumAmount.close();				 
				}
				
				if(riskCode.equals("1547")){
					 strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '02' and  proposalno = '"
						 + iBusinessNo + "'";
					 ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
					 if (rsLimitAcc12.next()) {
						 this.LimitAcc12 = rsLimitAcc12.getDouble("limitfee") * BWBexchangeRate; 
					 }
					 rsLimitAcc12.close();

					 strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '05' and  proposalno = '"
						 + iBusinessNo + "'";
					 ResultSet rsLimitManAcc05 = dbManager.executeQuery(strSQL);
					 if (rsLimitManAcc05.next()) {
						 this.limitManAcc05 = rsLimitManAcc05.getDouble("limitfee") * BWBexchangeRate;
					 }
					 rsLimitManAcc05.close();
				}
				
				//modify by DuHCH  20110726 begin reason:1516险种每次事故责任限额和每次事故每人伤亡责任限额取值和其它责任险不同，屏蔽其它暂时未用因子取值
                if(riskCode.equals("1516")){
                	
                	//每次事故责任限额
                	strSQL = "select max(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode<>'1504500')";
                	ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
                	if(rsLimitAcc12.next()){
                		this.LimitAcc12 = rsLimitAcc12.getDouble("limitFee");
                	}
                	rsLimitAcc12.close();
                	
                	//每次事故每人伤亡责任限额
                	strSQL = "select max(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='05' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode<>'1504500')";
                	ResultSet reLimitManAcc05 = dbManager.executeQuery(strSQL);
                	if(reLimitManAcc05.next()){
                		this.limitManAcc05 = reLimitManAcc05.getDouble("limitFee");
                	}
                	reLimitManAcc05.close();
                	
                	/*
                	 * 
                	 * //每人责任限额
                	strSQL = "select max(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo + "' and limittype='66'";
                	ResultSet rsLimitManAcc01 = dbManager.executeQuery(strSQL);
                	if (rsLimitManAcc01.next()){
                		this.limitManAcc01 = rsLimitManAcc01.getDouble("limitFee");
                		System.out.println("1516每人责任限额SQL=="+strSQL);
                	}
                	rsLimitManAcc01.close();
                	
                	//总累计责任限额
                	strSQL = "select * from prpTmain where proposalno = '"+ iBusinessNo + "'";
                	ResultSet rsSumAmount = dbManager.executeQuery(strSQL);
                	if (rsSumAmount.next()){
                		this.SumAmount = rsSumAmount.getDouble("sumAmount");
                		System.out.println("1516总累计责任限额SQL=="+strSQL);
                	}
                	rsSumAmount.close();
                	
                	//每人赔偿限额(人伤高管)
                	strSQL = "select max(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='66' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='1504500' and itemcode='0001' and modecode='077')";
	                System.out.println("strSQL="+strSQL);
					ResultSet rs1516LimitManAcc11 = dbManager.executeQuery(strSQL);
					if (rs1516LimitManAcc11.next()) {
						this.LimitManAcc11 = rs1516LimitManAcc11.getDouble("limitFee");
					}
					System.out.println("LimitManAcc11=="+LimitManAcc11);
					rs1516LimitManAcc11.close();
					
					//每人赔偿限额(人伤其他)
				 	strSQL = "select max(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='66' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='1504500' and itemcode='0001' and modecode not in('077'))";
				  	System.out.println("strSQL==="+strSQL);
				  	ResultSet rs1516LimitManAcc12 = dbManager.executeQuery(strSQL);
				   	if (rs1516LimitManAcc12.next()) {
						this.LimitManAcc12 = rs1516LimitManAcc12.getDouble("limitFee");
						System.out.println("LimitManAcc12==="+LimitManAcc12);
						//System.out.println("LimitManAcc12="+LimitManAcc12);
						//System.out.println("LimitManAcc12="+strSQL);
				    }
				   	rs1516LimitManAcc12.close();
				   
				   	//每人赔偿限额(意外高管)
				   	strSQL = "select max(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='66' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='1504500' and itemcode='0002' and modecode='077')";
					ResultSet rs1516LimitManAcc13 = dbManager.executeQuery(strSQL);
					if (rs1516LimitManAcc13.next()) {
						this.LimitManAcc13 = rs1516LimitManAcc13.getDouble("limitFee");
					}
					//System.out.println("LimitManAcc13"+LimitManAcc13);
					rs1516LimitManAcc13.close();
					
					//每人赔偿限额(意外其他)
				 	strSQL = "select max(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='66' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='1504500' and itemcode='0002' and modecode not in('077'))";
				 	ResultSet rs1516LimitManAcc14 = dbManager.executeQuery(strSQL);
				   	if (rs1516LimitManAcc14.next()) {
					   this.LimitManAcc14 = rs1516LimitManAcc14.getDouble("limitFee");
				    }
				   	rs1516LimitManAcc14.close();
				   	
				   	//每人医疗费用赔偿限额
				    strSQL = "select max(limitfee) as limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='66' and limitno in (select itemkindno from prptitemkind where proposalno='"
						+ iBusinessNo + "' and kindcode='1504500' and itemcode='0002')";
                    System.out.println("strSQL="+strSQL);
				    ResultSet rs1516LimitManHeal01 = dbManager.executeQuery(strSQL);
					if (rs1516LimitManHeal01.next()) {
						this.LimitManHeal01 = rs1516LimitManHeal01.getDouble("limitFee");
					}
					System.out.println("LimitManHeal01=="+LimitManHeal01);
					rs1516LimitManHeal01.close();*/
                }
                //modify by DuHCH  20110726 end reason:1516险种每次事故责任限额和每次事故每人伤亡责任限额取值和其它责任险不同，屏蔽其它暂时未用因子取值
                
                
                //modify by liuwei 20090512 begin 1598停车场责任险产品个性化双核条件配置
                if (riskCode.equals("1598")&&productcode.equals("00000054")) {
					strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '07' and  proposalno = '"
							+ iBusinessNo + "'";
					ResultSet rslimitFeeOneCar = dbManager.executeQuery(strSQL);
					if (rslimitFeeOneCar.next()) {
						this.LimitFeeOneCar = rslimitFeeOneCar.getDouble("limitfee")
								* this.exchRate; 
					}
					rslimitFeeOneCar.close();
					System.out.println("==============================this.limitFeeOneCar :"+this.LimitFeeOneCar );
					
					strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '03' and  proposalno = '"
							+ iBusinessNo + "'";
					ResultSet rsSumAmount = dbManager.executeQuery(strSQL);
					if (rsSumAmount.next()) {
						this.SumAmount = rsSumAmount.getDouble("limitfee")
								* this.exchRate; 
					}
					   rsSumAmount.close();
				    System.out.println("==============================this.limitFeeOneCar :"+this.LimitFeeOneCar );
				}
	                //modify by liuwei 20090512 end

				if (riskCode.equals("1523")){
					//累计赔偿限额LimitThirdAcc2
						strSQL = "select limitfee as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='03' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='1506400')";
					ResultSet rs1523SumAmount = dbManager.executeQuery(strSQL);
					if (rs1523SumAmount.next()) {
						this.sumAmount = rs1523SumAmount.getDouble("limitFee");
					}
					rs1523SumAmount.close();
					//除污费用每次事故赔偿限额
					strSQL = "select limitfee as limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
						+ iBusinessNo + "' and kindcode='1507000' and itemcode='0001')";
					ResultSet rs1523LimitThirdAcc2 = dbManager.executeQuery(strSQL);
					if (rs1523LimitThirdAcc2.next()) {
						this.LimitThirdAcc2 = rs1523LimitThirdAcc2.getDouble("limitFee");
					}
					rs1523LimitThirdAcc2.close();
				
	               //附加第三者责任：每次事故责任限额
					strSQL = "select limitfee as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='1507000' and itemcode='0002')";
					ResultSet rs1523LimitThirdAcc3 = dbManager.executeQuery(strSQL);
					if (rs1523LimitThirdAcc3.next()) {
						this.LimitThirdAccB = rs1523LimitThirdAcc3.getDouble("limitFee");
					}
					rs1523LimitThirdAcc3.close();				
				}
				
				if (riskCode.equals("1506")) {
					// 先按合计取，需要确认
					// 货运累计（借用原来的整单每次事故限额）
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='01' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='0001')";
					ResultSet rs1506LimitAcc = dbManager.executeQuery(strSQL);
					if (rs1506LimitAcc.next()) {
						this.LimitAcc12 = rs1506LimitAcc.getDouble("limitFee");
					}
					rs1506LimitAcc.close();
					// 货运每人（借用原来的整单每人限额）
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='0001')";
					ResultSet rs1506LimitManAcc = dbManager
							.executeQuery(strSQL);
					if (rs1506LimitManAcc.next()) {
						this.limitManAcc01 = rs1506LimitManAcc
								.getDouble("limitFee");
					}
					rs1506LimitManAcc.close();
					// 第三者累计A（1506专用）
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='01' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='0002')";
					ResultSet rs1506LimitThirdAmount = dbManager
							.executeQuery(strSQL);
					if (rs1506LimitThirdAmount.next()) {
						// this.limit1506SumA =
						// rs1506LimitThirdAmount.getDouble("limitFee");
					}
					rs1506LimitThirdAmount.close();
					// 第三者每人A（1506专用）
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='05' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='002')";
					ResultSet rs1506LimitThirdAcc = dbManager
							.executeQuery(strSQL);
					if (rs1506LimitThirdAcc.next()) {
						// this.limit1506ManA =
						// rs1506LimitThirdAcc.getDouble("limitFee");
					}
					rs1506LimitThirdAcc.close();
					// 第三者每次A（1506专用）
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='002')";
					ResultSet rs1506LimitThirdMain = dbManager
							.executeQuery(strSQL);
					if (rs1506LimitThirdMain.next()) {
						// this.limit1506AccA =
						// rs1506LimitThirdMain.getDouble("limitFee");
					}
					rs1506LimitThirdMain.close();
					// 第三者累计B（1506专用）
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='01' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='003')";
					ResultSet rs1506LimitThirdAmountB = dbManager
							.executeQuery(strSQL);
					if (rs1506LimitThirdAmountB.next()) {
						// this.limit1506SumB =
						// rs1506LimitThirdAmountB.getDouble("limitFee");
					}
					rs1506LimitThirdAmountB.close();
					// 第三者每人B（1506专用）
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='05' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='003')";
					ResultSet rs1506LimitThirdAccB = dbManager
							.executeQuery(strSQL);
					if (rs1506LimitThirdAccB.next()) {
						// this.limit1506ManB =
						// rs1506LimitThirdAccB.getDouble("limitFee");
					}
					rs1506LimitThirdAccB.close();
					// 第三者每次B（1506专用）
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
							+ iBusinessNo
							+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
							+ iBusinessNo + "' and kindcode='003')";
					ResultSet rs1506LimitThirdMainB = dbManager
							.executeQuery(strSQL);
					if (rs1506LimitThirdMainB.next()) {
						//this.limit1506AccB = rs1506LimitThirdMainB.getDouble("limitFee");
					}
					rs1506LimitThirdMainB.close();
					
					//货物责任：每次事故责任限额 
			        strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
						+ iBusinessNo + "' and kindcode='1506000')";
				    ResultSet rs1506LimitCargoAcc = dbManager.executeQuery(strSQL);
				    if (rs1506LimitCargoAcc.next()) {
					this.LimitCargoAcc = rs1506LimitCargoAcc.getDouble("limitFee");
				    }
				    rs1506LimitCargoAcc.close();
				    
                   //1506第三者责任：每次事故责任限额 
			        strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
						+ iBusinessNo + "' and kindcode='1503000' and itemcode='0001')";
				    ResultSet rs1506LimitThirdAccA = dbManager.executeQuery(strSQL);
				    if (rs1506LimitThirdAccA.next()) {
					this.LimitThirdAcc = rs1506LimitThirdAccA.getDouble("limitFee");
				    }
				    rs1506LimitThirdAccA.close();
				    
                   //1506除污费用：每次事故赔偿限额
			        strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where proposalno='"
						+ iBusinessNo + "' and kindcode='1503000' and itemcode='0002')";
				    ResultSet rs1506LimitThirdAcc2 = dbManager.executeQuery(strSQL);
				    if (rs1506LimitThirdAcc2.next()) {
					this.LimitThirdAcc2 = rs1506LimitThirdAcc2.getDouble("limitFee");
				    }
				    rs1506LimitThirdAcc2.close();
				    
	                //added by xiongguojun 20090327 1506核乏料运输：累计责任限额 begin
			        strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='03' and limitno in (select itemkindno from prptitemkind where proposalno='"
						+ iBusinessNo + "' and kindcode='1503000' and itemcode='0004')";
				    ResultSet rs1506LimitThirdAcc4 = dbManager.executeQuery(strSQL);
				    if (rs1506LimitThirdAcc4.next()) {
					this.LimitThirdAcc4 = rs1506LimitThirdAcc4.getDouble("limitFee");
				    }
				    rs1506LimitThirdAcc4.close();
				    //added by xiongguojun 20090327 1506核乏料运输：累计责任限额 end
				}
				
				//modeify by zhangruifeng 20071220 begin reason : 控制1505险种的每人赔偿限额
				if (riskCode.equals("1505")){
					//added by gengxiaobo begin 20080604 增加最大车累计限额,调整每次事故赔偿限额取值。
					strSQL 	= "select PreTurnOver,StaffCount from PrpTmainLiab where proposalno='"
							+ iBusinessNo
							+ "'";
					ResultSet rsPreTurnOver1505 = dbManager.executeQuery(strSQL);
				
					if (rsPreTurnOver1505.next()) {
						this.douPreTurnOver1505 = rsPreTurnOver1505.getDouble("PreTurnOver");
						this.douStaffCount1505 = rsPreTurnOver1505.getDouble("StaffCount");
				    }
					rsPreTurnOver1505.close();
					//每人责任限额
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='04'";
					ResultSet rs1505LimitManAcc01 = dbManager.executeQuery(strSQL);
					if (rs1505LimitManAcc01.next()) {
						this.limitManAcc01 = rs1505LimitManAcc01.getDouble("limitFee");
					    }
					rs1505LimitManAcc01.close();
						
					//added by LanNing begin 20080421 1505每次事故赔偿限额
					strSQL = "select limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='02'";
					ResultSet rsLimit02Fee1505 = dbManager.executeQuery(strSQL);
					if (rsLimit02Fee1505.next()) {
						this.Limit02Fee1505 = rsLimit02Fee1505.getDouble("limitFee");
					}						
					rsLimit02Fee1505.close();
					//added by LanNing end 20080421 1505每次事故赔偿限额					
					if(this.douPreTurnOver1505!=0){
						this.Limit02Fee1505 = this.Limit02Fee1505/this.douPreTurnOver1505;
					}			
					
					strSQL = "select sum(limitfee) as limitFee from prptlimit where proposalno='"
						+ iBusinessNo
						+ "' and limittype='03'";
					ResultSet rsLimit03Fee1505 = dbManager.executeQuery(strSQL);
					if (rsLimit03Fee1505.next()) {
						this.Limit03Fee1505 = rsLimit03Fee1505.getDouble("limitFee");
					}						
					rsLimit03Fee1505.close();
					
					strSQL = "select max(Capacity) as Capacity from prptitemDevice where proposalno='"
						+ iBusinessNo
						+ "'";
					ResultSet rsmaxCapacity1505 = dbManager.executeQuery(strSQL);
					if (rsmaxCapacity1505.next()) {
						this.maxCapacity1505 = rsmaxCapacity1505.getDouble("Capacity");
					}						
					rsmaxCapacity1505.close();
					
					if(this.maxCapacity1505!=0&&this.douStaffCount1505!=0){
						this.Limit03Fee1505 = this.maxCapacity1505*this.Limit03Fee1505/this.douStaffCount1505;
					}
					//added by gengxiaobo end 20080604 增加最大车累计限额,调整每次事故赔偿限额取值。
					
				}
				//modeify by zhangruifeng 20071220 end
            }
			/***************************************责任险-end***********************************************************/	
				
			//获取年龄范围
			strSQL = "select age from prptinsurednature where proposalno in (select proposalno from prptinsured where insuredtype in ('1','2')) and proposalno = '"
					+ iBusinessNo + "'";
			ResultSet rsAge = dbManager.executeQuery(strSQL);
			while (rsAge != null && rsAge.next()) {
				ageScope.add(rsAge.getString("age"));
			}
			rsAge.close();
			
			/** **********保证保险部分************ */
			if (classCode.equals("22")) {
				if("2201".equals(riskCode)){
					//累计赔偿限额
					strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '01' and  proposalno = '"
						 + iBusinessNo + "'";
					 ResultSet rsSumAmount = dbManager.executeQuery(strSQL);
					 if (rsSumAmount.next()) {
						 this.SumAmount = rsSumAmount.getDouble("limitfee") * BWBexchangeRate; 
					 }
					 rsSumAmount.close();
					 //每次事故赔偿限额
					 strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '02' and  proposalno = '"
						 + iBusinessNo + "'";
					 ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
					 if (rsLimitAcc12.next()) {
						 this.LimitAcc12 = rsLimitAcc12.getDouble("limitfee") * BWBexchangeRate; 
					 }
					 rsLimitAcc12.close();
					 //每次事故每人赔偿限额
					 strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '04' and  proposalno = '"
						 + iBusinessNo + "'";
					 ResultSet rsLimitManAcc01 = dbManager.executeQuery(strSQL);
					 if (rsLimitManAcc01.next()) {
						 this.limitManAcc01 = rsLimitManAcc01.getDouble("limitfee") * BWBexchangeRate; 
					 }
					 rsLimitManAcc01.close();
				}
			}
			
			/** *************组合险部分************** */
			if (classCode.equals("23")) {
				if(riskCode.equals("2351")||riskCode.equals("2352") || riskCode.equals("2355") || riskCode.equals("2315")){
					 //每次事故赔偿限额
					 strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '02' and  proposalno = '"
						 + iBusinessNo + "'";
					 ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
					 if (rsLimitAcc12.next()) {
						 this.LimitAcc12 = rsLimitAcc12.getDouble("limitfee") * BWBexchangeRate; 
					 }
					 rsLimitAcc12.close();
					 //每次事故每人人身伤亡赔偿限额
					 strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '36' and  proposalno = '"
						 + iBusinessNo + "'";
					 ResultSet rsLimitManAcc05 = dbManager.executeQuery(strSQL);
					 if (rsLimitManAcc05.next()) {
						 this.limitManAcc05 = rsLimitManAcc05.getDouble("limitfee") * BWBexchangeRate;
					 }
					 rsLimitManAcc05.close();
					 //每次事故财产赔偿限额
					 strSQL = "select Max(limitfee) limitfee from prptlimit where limittype='37' and  proposalno='"
							+ iBusinessNo + "'";
						ResultSet rsLimitAcc03 = dbManager.executeQuery(strSQL);
						if (rsLimitAcc03.next()){
							this.LimitAcc03 = rsLimitAcc03.getDouble("limitfee") * this.exchRate;
						}
					rsLimitAcc03.close();
					//企财综合险保额
					strSQL = "SELECT sum(Amount) AS sumAmount0300100 FROM PrpTitemKind WHERE ProposalNo='"
					    	+ iBusinessNo + "' and Kindcode ='0102700'";
				   ResultSet rsSumAmount0300100 = dbManager.executeQuery(strSQL);
				   if (rsSumAmount0300100.next()) {
				   this.sumAmount0300100 = rsSumAmount0300100.getDouble("sumAmount0300100") * BWBexchangeRate;
				   }
				   rsSumAmount0300100.close();
				}
				if(riskCode.equals("2351")||riskCode.equals("2352")){
					//每人保险金额
					strSQL = "select sum(sumamount) as sumamount from prptmain where proposalno='"+ iBusinessNo + "'";
					ResultSet rsSumAmount = dbManager.executeQuery(strSQL);
					if(rsSumAmount.next()){
						SumAmountPer = (rsSumAmount.getDouble("sumamount")/this.sumquantity) * BWBexchangeRate;
					}
					rsSumAmount.close();
				}
				if(riskCode.equals("2351") || riskCode.equals("2355")){
					//意外伤害医疗
					strSQL = "select sum(amount) as sumAmount9000447 from prptitemkind where kindcode='9000447' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsSumAmount9000447 = dbManager.executeQuery(strSQL);
					if(rsSumAmount9000447.next()){
						this.sumAmountYL = rsSumAmount9000447.getDouble("sumAmount9000447") * BWBexchangeRate;
					}
					rsSumAmount9000447.close();
				}
				if(riskCode.equals("2352")){
					//房屋及附属设备保险金额/每户
					strSQL = "select sum(amount) as sumAmount03002003 from prptitemkind where kindcode='0300200' and itemcode='0003' and proposalno='"
						+ iBusinessNo + "'";
					ResultSet rsSumAmount03002003 = dbManager.executeQuery(strSQL);
					if(rsSumAmount03002003.next()){
						amountPer03010001 = (rsSumAmount03002003.getDouble("sumAmount03002003")/this.sumquantity) * BWBexchangeRate;
					}
					rsSumAmount03002003.close();
					//室内装潢保险金额/每户
					strSQL = "select sum(amount) as sumAmount03002004 from prptitemkind where kindcode='0300200' and itemcode='0004' and proposalno='"
						+ iBusinessNo + "'";
					ResultSet rsSumAmount03002004 = dbManager.executeQuery(strSQL);
					if(rsSumAmount03002004.next()){
						amountPer03010002 = (rsSumAmount03002004.getDouble("sumAmount03002004")/this.sumquantity) * BWBexchangeRate;
					}
					rsSumAmount03002004.close();
					//附加盗抢保险/每户
					strSQL = "select sum(amount) as sumAmount9000452 from prptitemkind where kindcode='9000452' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsSumAmount9000452 = dbManager.executeQuery(strSQL);
					if(rsSumAmount9000452.next()){
						amountPer9000452 = (rsSumAmount9000452.getDouble("sumAmount9000452")/this.sumquantity) * BWBexchangeRate;
					}
					//意外伤害医疗
					strSQL = "select sum(amount) as sumAmount9000448 from prptitemkind where kindcode='9000448' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsSumAmount9000448 = dbManager.executeQuery(strSQL);
					if(rsSumAmount9000448.next()){
						this.sumAmountYL = rsSumAmount9000448.getDouble("sumAmount9000448") * BWBexchangeRate;
					}
					rsSumAmount9000448.close();
				}
				if(riskCode.equals("2353")){
					//室内装潢/家用电器/衣物床上用品/家具及其他保险金额/每户
					strSQL = "select sum(amount) as AmountPer0300200 from prptitemkind where kindcode='0300200' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsAmountPer0300200 = dbManager.executeQuery(strSQL);
					if(rsAmountPer0300200.next()){
						AmountPer = (rsAmountPer0300200.getDouble("AmountPer0300200")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmountPer0300200.close();
					//附加居家责任保险
					strSQL = "select sum(amount) as amountPer9000449 from prptitemkind where kindcode='9000449' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsAmount9000449 = dbManager.executeQuery(strSQL);
					if(rsAmount9000449.next()){
						amountPer9000449 = (rsAmount9000449.getDouble("amountPer9000449")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000449.close();
					//附加家庭意外骨折医疗保险
					strSQL = "select sum(amount) as amountPer9000451 from prptitemkind where kindcode='9000451' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsAmount9000451 = dbManager.executeQuery(strSQL);
					if(rsAmount9000451.next()){
						amountPer9000451 = (rsAmount9000451.getDouble("amountPer9000451")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000451.close();
					//附加盗抢保险
					strSQL = "select sum(amount) as amountPer9000452 from prptitemkind where kindcode='9000452' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsAmount9000452 = dbManager.executeQuery(strSQL);
					if(rsAmount9000452.next()){
						amountPer9000452 = (rsAmount9000452.getDouble("amountPer9000452")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000452.close();
					//附加家用电器用电安全保险
					strSQL = "select sum(amount) as amountPer9000453 from prptitemkind where kindcode='9000453' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsAmount9000453 = dbManager.executeQuery(strSQL);
					if(rsAmount9000453.next()){
						amountPer9000453 = (rsAmount9000453.getDouble("amountPer9000453")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000453.close();
					//附加管道破裂及水渍保险
					strSQL = "select sum(amount) as amountPer9000454 from prptitemkind where kindcode='9000454' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsAmount9000454 = dbManager.executeQuery(strSQL);
					if(rsAmount9000454.next()){
						amountPer9000454 = (rsAmount9000454.getDouble("amountPer9000454")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000454.close();
					//附加家庭成员屋内意外伤害身故、残疾保险金
					strSQL = "select sum(amount) as amountPer9000455 from prptitemkind where kindcode='9000455' and proposalno='"+ iBusinessNo + "'";
					ResultSet rsAmount9000455 = dbManager.executeQuery(strSQL);
					if(rsAmount9000455.next()){
						amountPer9000450 = (rsAmount9000455.getDouble("amountPer9000455")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000455.close();
				}
				if(riskCode.equals("2354")){
					if(!"23540009".equals(productcode) && !"23540010".equals(productcode)){
						//室内装潢/家用电器/衣物床上用品/家具及其他保险金额/每户
						strSQL = "select sum(amount) as AmountPer0300200 from prptitemkind where kindcode='0309100' and proposalno='"+ iBusinessNo + "'";
						ResultSet rsAmountPer0300200 = dbManager.executeQuery(strSQL);
						if(rsAmountPer0300200.next()){
							AmountPer = (rsAmountPer0300200.getDouble("AmountPer0300200")/this.sumquantity) * BWBexchangeRate;
						}
						rsAmountPer0300200.close();
						
						//附加盗抢保险
						strSQL = "select sum(amount) as amountPer9000452 from prptitemkind where kindcode='0309200' and proposalno='"+ iBusinessNo + "'";
						ResultSet rsAmount9000452 = dbManager.executeQuery(strSQL);
						if(rsAmount9000452.next()){
							amountPer9000452 = (rsAmount9000452.getDouble("amountPer9000452")/this.sumquantity) * BWBexchangeRate;
						}
						rsAmount9000452.close();
					}else{
						//室内装潢/家用电器/衣物床上用品/家具及其他保险金额/每户
						strSQL = "select sum(amount) as AmountPer0300200 from prptitemkind where kindcode='0300100' and proposalno='"+ iBusinessNo + "'";
						ResultSet rsAmountPer0300200 = dbManager.executeQuery(strSQL);
						if(rsAmountPer0300200.next()){
							AmountPer = (rsAmountPer0300200.getDouble("AmountPer0300200")/this.sumquantity) * BWBexchangeRate;
						}
						rsAmountPer0300200.close();
						
						//附加盗抢保险
						strSQL = "select sum(amount) as amountPer9000452 from prptitemkind where kindcode='0300200' and proposalno='"+ iBusinessNo + "'";
						ResultSet rsAmount9000452 = dbManager.executeQuery(strSQL);
						if(rsAmount9000452.next()){
							amountPer9000452 = (rsAmount9000452.getDouble("amountPer9000452")/this.sumquantity) * BWBexchangeRate;
						}
						rsAmount9000452.close();
					}
				}
				if(riskCode.equals("2311")||riskCode.equals("2312")){
				   strSQL = "SELECT sum(Amount) AS sumAmount2300200 FROM PrpTitemKind WHERE ProposalNo='"
					    	+ iBusinessNo + "' and Kindcode in ('2300200','2300300','2301100')"; //公路财产损失、中断损失保险
				   ResultSet rsSumAmount2300200 = dbManager.executeQuery(strSQL);
				   if (rsSumAmount2300200.next()) {
				   this.sumAmount2300200 = rsSumAmount2300200.getDouble("sumAmount2300200") * BWBexchangeRate;
				   }
				   rsSumAmount2300200.close();
				   
				   strSQL = "SELECT sum(Amount) AS sumAmount2300400 FROM PrpTitemKind WHERE ProposalNo='"
				    	+ iBusinessNo + "' and Kindcode ='2300400'"; //公众责任保险
			       ResultSet rsSumAmount2300400 = dbManager.executeQuery(strSQL);
			       if (rsSumAmount2300400.next()) {
			       this.sumAmount2300400 = rsSumAmount2300400.getDouble("sumAmount2300400") * BWBexchangeRate;
			       }
			       rsSumAmount2300400.close();
			       
			       strSQL = "SELECT sum(Amount) AS sumAmount2300500 FROM PrpTitemKind WHERE ProposalNo='"
				    	+ iBusinessNo + "' and Kindcode ='2300500'"; //雇主责任保险
			       ResultSet rsSumAmount2300500 = dbManager.executeQuery(strSQL);
			       if (rsSumAmount2300500.next()) {
			       this.sumAmount2300500 = rsSumAmount2300500.getDouble("sumAmount2300500") * BWBexchangeRate;
			       }
			       rsSumAmount2300500.close();
			       
			       strSQL = "SELECT MAX(UnitAmount) AS limit2300500 FROM PrpTitemKind WHERE ProposalNo='"
				    	+ iBusinessNo + "' and Kindcode ='2300500'"; //雇主责任保险每人每次事故赔偿限额
			       ResultSet rsLimit2300500 = dbManager.executeQuery(strSQL);
			       if (rsLimit2300500.next()) {
			       this.limit2300500 = rsLimit2300500.getDouble("limit2300500") * BWBexchangeRate;
			       }
			       rsLimit2300500.close();		       			       
			       
			       
			       strSQL = "SELECT sum(Amount) AS sumAmount2300600 FROM PrpTitemKind WHERE ProposalNo='"
				    	+ iBusinessNo + "' and Kindcode ='2300600'"; //现金保险
			       ResultSet rsSumAmount2300600 = dbManager.executeQuery(strSQL);
			       if (rsSumAmount2300600.next()) {
			       this.sumAmount2300600 = rsSumAmount2300600.getDouble("sumAmount2300600") * BWBexchangeRate;
			       }
			       rsSumAmount2300600.close();
				}
				//add by gengxiaobo 20080326 起重机械综合保险高级核保条件
				if(riskCode.equals("2313")){
					   strSQL = "SELECT Max(Amount) AS sumAmount2301500 FROM PrpTitemKind WHERE ProposalNo='"
						    	+ iBusinessNo + "' and Kindcode ='2301500'"; //财产损失保险
					   ResultSet rsSumAmount2301500 = dbManager.executeQuery(strSQL);
					   if (rsSumAmount2301500.next()) {
					   this.sumAmount2301500 = rsSumAmount2301500.getDouble("sumAmount2301500") * BWBexchangeRate;
					   }
					   rsSumAmount2301500.close();
					   
					   strSQL = "SELECT sum(Amount) AS sumAmount2301600 FROM PrpTitemKind WHERE ProposalNo='"
					    	+ iBusinessNo + "' and Kindcode ='2301600'"; //第三者责任保险
				       ResultSet rsSumAmount2301600 = dbManager.executeQuery(strSQL);
				       if (rsSumAmount2301600.next()) {
				       this.sumAmount2301600 = rsSumAmount2301600.getDouble("sumAmount2301600") * BWBexchangeRate;
				       }
				       rsSumAmount2301600.close();
				       
				       strSQL = "SELECT sum(Amount) AS sumAmount2301800 FROM PrpTitemKind WHERE ProposalNo='"
					    	+ iBusinessNo + "' and Kindcode ='2301800'"; //雇主责任保险
				       ResultSet rsSumAmount2301800 = dbManager.executeQuery(strSQL);
				       if (rsSumAmount2301800.next()) {
				       this.sumAmount2301800 = rsSumAmount2301800.getDouble("sumAmount2301800") * BWBexchangeRate;
				       }
				       rsSumAmount2301800.close();				       
					}
				//add by hanxiao 20091118 2310家财险部分
				if(riskCode.equals("2310")){
					   strSQL = "SELECT sum(Amount) AS sumAmount0300100 FROM PrpTitemKind WHERE ProposalNo='"
						    	+ iBusinessNo + "' and Kindcode ='0300100'"; //财产损失保险
					   ResultSet rsSumAmount0300100 = dbManager.executeQuery(strSQL);
					   if (rsSumAmount0300100.next()) {
					   this.sumAmount0300100 = rsSumAmount0300100.getDouble("sumAmount0300100") * BWBexchangeRate;
					   }
					   rsSumAmount0300100.close();
					   				       
				}
			}
			/***************意健险部分***************/
			if (this.classCode.equals("27")) {
				strSQL = "SELECT MAX(UnitAmount) AS UnitAmount FROM PrpTitemKind WHERE ProposalNo='"
						+ iBusinessNo + "'";
				ResultSet rsUnitAmount = dbManager.executeQuery(strSQL);
				if (rsUnitAmount.next()) {
					this.SumAmountPer = rsUnitAmount.getDouble("UnitAmount") * BWBexchangeRate;
				}
				rsUnitAmount.close();
				
				strSQL = "SELECT MAX(UnitAmount) AS UnitAmount FROM PrpTitemKind WHERE ProposalNo='"
						+ iBusinessNo + "' and Kindcode ='2701300'"; //附加意外伤害医疗保险
				ResultSet rsSubAmount01 = dbManager.executeQuery(strSQL);
				if (rsSubAmount01.next()) {
					this.SubAmountPer01 = rsSubAmount01.getDouble("UnitAmount") * BWBexchangeRate;
				}
				rsSubAmount01.close();
				
				strSQL = "SELECT MAX(UnitAmount) AS UnitAmount FROM PrpTitemKind WHERE ProposalNo='"
						+ iBusinessNo + "' and Kindcode ='2701400'"; // 附加意外伤害生活津贴保险
				ResultSet rsSubAmount02 = dbManager.executeQuery(strSQL);
				if (rsSubAmount02.next()) {
					this.SubAmountPer02 = rsSubAmount02.getDouble("UnitAmount") * BWBexchangeRate;
				}
				rsSubAmount02.close();
				
				strSQL = "SELECT MAX(UnitAmount) AS UnitAmount FROM PrpTitemKind WHERE ProposalNo='"
						+ iBusinessNo + "' and Kindcode ='2700800'"; // 学生幼儿人身意外伤害保险
				ResultSet rsSubAmount03 = dbManager.executeQuery(strSQL);
				if (rsSubAmount03.next()) {
					this.SubAmountPer03 = rsSubAmount03.getDouble("UnitAmount") * BWBexchangeRate;
				}
				rsSubAmount03.close();

				if (this.riskCode.equals("2701")||this.riskCode.equals("2711")||this.riskCode.equals("2705")
						|| this.riskCode.equals("2728") || this.riskCode.equals("2738") || this.riskCode.equals("2741")) {
					strSQL = "select flag from prpdcode  where codetype ='OccupationCode' and codecode in "
							+ "(select OccupationCode from prptinsured WHERE InsuredFlag ='1' and ProposalNo='"
							+ iBusinessNo + "')";
					ResultSet rsOccupationCode = dbManager.executeQuery(strSQL);
					if (rsOccupationCode.next()) {
						this.OccupationFlag = rsOccupationCode
								.getString("flag");
					}
					rsOccupationCode.close();
					if(!(this.OccupationFlag==null||"".equals(this.OccupationFlag))){
						if (this.OccupationFlag.equals("1")) {
							this.SumAmountPer1 = this.SumAmountPer;
						} else if (this.OccupationFlag.equals("2")) {
							this.SumAmountPer2 = this.SumAmountPer;
						} else if (this.OccupationFlag.equals("3")) {
							this.SumAmountPer3 = this.SumAmountPer;
						} else if (this.OccupationFlag.equals("4")) {
							this.SumAmountPer4 = this.SumAmountPer;
						} else if (this.OccupationFlag.equals("5")) {
							this.SumAmountPer5 = this.SumAmountPer;
						} else if (this.OccupationFlag.equals("6")) {
							this.SumAmountPer6 = this.SumAmountPer;
						}
					}
				}
				//最低团单比例
				strSQL = "SELECT * FROM PrpTmain WHERE ProposalNo='"
						+ iBusinessNo + "'";
				ResultSet rsUnit = dbManager.executeQuery(strSQL);
				String strPolicyType = "";
				if (rsUnit.next())
					strPolicyType = rsUnit.getString("PolicyType");
				rsUnit.close();
				if ("02".equals(strPolicyType)) {  //团单
					strSQL = "select * from prptmaincasualty where  proposalno = '"
							+ iBusinessNo + "'";
					rsUnit = dbManager.executeQuery(strSQL);
					if (rsUnit.next()) {
						double unitCount = rsUnit.getDouble("unitcount");
						double mainUnitCount = rsUnit
								.getDouble("MAININSUREDCOUNT");
						if (unitCount != 0) {
							this.unitProportion = mainUnitCount / unitCount;
						}
					}
					rsUnit.close();
				}else{
					this.unitProportion = 1;
				}
				//add by zhulei begin 20051218 意健险PML
//				strSQL = "SELECT * FROM PrpTdangerUnit WHERE ProposalNo='"
//						+ iBusinessNo + "'";
//				ResultSet rsPML = dbManager.executeQuery(strSQL);
//				if (rsPML.next()) {
//					this.dbPML = rsPML.getDouble("SpeValue");
//				}
//				rsPML.close();

			}
			//modify by zhangruifeng begin 20080220
			//新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制
			if (this.classCode.equals("01")||this.classCode.equals("03")||this.classCode.equals("04")||this.classCode.equals("07")) 
			{
				this.CBYearLimit = pubTools.getYearMinus
				(new com.sinosoft.utility.string.Date(startDate.toString()),startHour,
						new com.sinosoft.utility.string.Date(endDate.toString()),endHour);
			}
			//modify by zhangruifeng end 20080220
			
			//add by zhaoning20091125 begin Reason:获取危险单位条数
			strSQL = "SELECT count(*) as DangerUnitCount FROM prptdangerunit where proposalno='" +iBusinessNo+"'";
			ResultSet rsDangerUnit = dbManager.executeQuery(strSQL);
			if (rsDangerUnit.next()) {
				this.dangerUnitCount = rsDangerUnit.getInt("DangerUnitCount");
			}
			rsDangerUnit.close(); 
			//add by zhaoning20091125 end
			
			if (this.classCode.equals("01")||this.classCode.equals("03")||this.classCode.equals("07")) {
				//modify by duhaihchao 20101017 reason: 保险金额在上面从Tmain表取值  begin
				/*if(!"03".equals(this.classCode)){
					strSQL = "SELECT Amount FROM prptdangerunit where proposalno='" +iBusinessNo+"'";
					ResultSet rsSumAmount = dbManager.executeQuery(strSQL);
					if (rsSumAmount.next()) {
						this.sumAmount = rsSumAmount.getDouble("Amount");
					}
					rsSumAmount.close(); 
				}*/
				//modify by duhaihchao 20101017 reason: 保险金额在上面从Tmain表取值 end
				strSQL = "SELECT Amount FROM prpTitemkind where proposalno='" +iBusinessNo+"' and kindcode='0107100'" 
				          +"and riskcode ='"+this.riskCode+"'";
				
				ResultSet rsAmount = dbManager.executeQuery(strSQL);
				if (rsAmount.next()) {
					this.Amount = rsAmount.getDouble("Amount") * BWBexchangeRate;
				}
				rsAmount.close();
				//modify by duhaichao 20101017 reason:第三责任取值调整 begin
				/*strSQL = "SELECT Amount FROM prpTitemkind where proposalno='" +iBusinessNo+"' and kindcode='0108100'"
				         +"and riskcode ='"+this.riskCode+"'";
				ResultSet rsLimitManAcc = dbManager.executeQuery(strSQL);
				if (rsLimitManAcc.next()) {
					this.LimitManAcc = rsLimitManAcc.getDouble("Amount") * BWBexchangeRate;
				}
				rsAmount.close();
				strSQL = "SELECT Amount FROM prpTitemkind where proposalno='" +iBusinessNo+"' and kindcode='0108200'"
				          +"and riskcode ='"+this.riskCode+"'";
				ResultSet rsSumAmount08 = dbManager.executeQuery(strSQL);
				if (rsSumAmount08.next()) {
					this.SumAmount08 = rsSumAmount08.getDouble("Amount") * BWBexchangeRate;
				}
				rsAmount.close();*/
				//在Tlimit表中取值  限额 limitflag = 0 / 免赔额 limitflag=1 / 免赔率 limitflag=2
				strSQL = "SELECT LimitFee FROM prpTlimit where proposalno='" +iBusinessNo+"' and limittype = '10' and limitflag='0'"
		         +"and riskcode ='"+this.riskCode+"'";
				ResultSet rsLimitManAcc = dbManager.executeQuery(strSQL);
				if (rsLimitManAcc.next()) {
					this.LimitManAcc = rsLimitManAcc.getDouble("LimitFee") * BWBexchangeRate;
				}
				rsLimitManAcc.close();
				strSQL = "SELECT LimitFee FROM prpTlimit where proposalno='" +iBusinessNo+"' and limittype = '11' and limitflag='0'"
		         +"and riskcode ='"+this.riskCode+"'";
				ResultSet rsSumAmount08 = dbManager.executeQuery(strSQL);
				if (rsSumAmount08.next()) {
					this.SumAmount08 = rsSumAmount08.getDouble("LimitFee") * BWBexchangeRate;
				}
				rsSumAmount08.close();
				//modify by duhaichao 20101017 reason:第三责任取值调整 begin
				//add by hanxiao 20090226  0125工程机械设备保险 新增附加险保额核保因子
				if(riskCode.equals("0125")){
					strSQL = "SELECT Sum(Amount) as Amount FROM prpTitemkind where proposalno='" +iBusinessNo+"' and calculateflag ='Y' " 
			          +"and riskcode ='"+this.riskCode+"'";
					
					ResultSet rsAmount0125 = dbManager.executeQuery(strSQL);
					
					if (rsAmount0125.next()) {
						this.sumAmount = rsAmount0125.getDouble("Amount") * BWBexchangeRate;
					}
					rsAmount0125.close();
					strSQL = "SELECT sum(Amount) AS sumAmount0145100  FROM prpTitemkind where proposalno='" +iBusinessNo+"' and kindcode='0145100'"
			          +"and riskcode ='"+this.riskCode+"'";
					ResultSet rsSumAmount0145100 = dbManager.executeQuery(strSQL);
					if (rsSumAmount0145100.next()) {
						this.SumAmount0145100 = rsSumAmount0145100.getDouble("sumAmount0145100")* this.exchRate;
					}
					rsSumAmount0145100.close();
					
					strSQL = "SELECT sum(Amount) AS sumAmount0145200 FROM prpTitemkind where proposalno='" +iBusinessNo+"' and kindcode='0145200'"
			          +"and riskcode ='"+this.riskCode+"'";
					ResultSet rsSumAmount0145200 = dbManager.executeQuery(strSQL);
					if (rsSumAmount0145200.next()) {
						this.SumAmount0145200 = rsSumAmount0145200.getDouble("sumAmount0145200")* this.exchRate;
					}
					rsSumAmount0145200.close();
					
					strSQL = "SELECT sum(Amount) AS sumAmount0145300 FROM prpTitemkind where proposalno='" +iBusinessNo+"' and kindcode='0145300'"
			          +"and riskcode ='"+this.riskCode+"'";
					ResultSet rsSumAmount0145300 = dbManager.executeQuery(strSQL);
					if (rsSumAmount0145300.next()) {
						this.SumAmount0145300 = rsSumAmount0145300.getDouble("sumAmount0145300")* this.exchRate;
					}
					rsSumAmount0145300.close();
					
					strSQL = "SELECT sum(Amount) AS sumAmount0145400 FROM prpTitemkind where proposalno='" +iBusinessNo+"' and kindcode='0145400'"
			          +"and riskcode ='"+this.riskCode+"'";
					ResultSet rsSumAmount0145400 = dbManager.executeQuery(strSQL);
					if (rsSumAmount0145400.next()) {
						this.SumAmount0145400 = rsSumAmount0145400.getDouble("sumAmount0145400")* this.exchRate;
					}
					rsSumAmount0145400.close();
					
					strSQL = "SELECT sum(Amount) AS sumAmount0145500 FROM prpTitemkind where proposalno='" +iBusinessNo+"' and kindcode='0145500'"
			          +"and riskcode ='"+this.riskCode+"'";
					ResultSet rsSumAmount0145500 = dbManager.executeQuery(strSQL);
					if (rsSumAmount0145500.next()) {
						this.SumAmount0145500 = rsSumAmount0145500.getDouble("sumAmount0145500")* this.exchRate;
					}
					rsSumAmount0145500.close();
					System.out.println(" @1@="+this.SumAmount0145100+" @2@="+this.SumAmount0145200+" @3@="+this.SumAmount0145300+" @4@="+this.SumAmount0145400+" @5@="+this.SumAmount0145500);
				}//add by hanxiao 20090226  0125工程机械设备保险 新增附加险保额核保因子
			}
			/*******************************************************************************************************/
				                                        /*新增加部分*/
			if ("1503".equals(riskCode)||"1546".equals(riskCode)) {
				strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '02' and  proposalno = '"
				+ iBusinessNo + "'";
				ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
				if (rsLimitAcc12.next()) {
				this.LimitAcc12 = rsLimitAcc12.getDouble("limitfee"); 
				}
				rsLimitAcc12.close();
				strSQL = "select Max(limitfee) limitfee from prpTLimit where limittype = '03' and  proposalno = '"
				+ iBusinessNo + "'";
				ResultSet rsSumAmount = dbManager.executeQuery(strSQL);
				if (rsSumAmount.next()) {
				this.sumAmount = rsSumAmount.getDouble("limitfee"); 
				}
				rsSumAmount.close();
			}
			//add by zhangruifeng 20080422 reason:共保时按照我方份额进行控制总保额
			if(!("A".equals(classCode) || "B".equals(classCode))){//当是非车时
				if("1".equals(coinsflag)||"2".equals(coinsflag)){ //当是主共保或是从共保时
					strSQL = "select coinsRate from prptcoins where coinstype = '1' "+
					"and proposalno ='"+iBusinessNo+"'";
					ResultSet rsSumAmount = dbManager.executeQuery(strSQL);
					if (rsSumAmount.next()) {
						coinsRate = rsSumAmount.getDouble("coinsRate");
						this.sumAmount = sumAmount*coinsRate/100;
					}
					rsSumAmount.close(); 
				}
			}
			//added by LanNing begin 20080225 投资金产品
			if(this.classCode.equals("29")){
				strSQL = "SELECT Investment FROM prptmaininvest where proposalno='" +iBusinessNo+"'";
				ResultSet rsInvest = dbManager.executeQuery(strSQL);
				if (rsInvest.next()) {
					this.Investment = rsInvest.getDouble("Investment");
				}
				rsInvest.close();
			}
			//added by LanNing end 20080225 投资金产品
			
			//**********************************************综合险部分*************************************************************/
			if(this.classCode.equals("30")){
				//取3001险种险别的分项保额
				if("3001".equals(this.riskCode)){
					strSQL = "SELECT Amount AS SumAmount300101 FROM prpTitemkind where proposalno='" +iBusinessNo + "' and kindcode='3001001'"
			          	+ " and itemcode = '0001'";
					ResultSet rsSumAmount300101 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300101.next()) {
						this.SumAmount300101 = (rsSumAmount300101.getDouble("SumAmount300101")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300101.close();
					
					strSQL = "SELECT Amount AS SumAmount300102 FROM prpTitemkind where proposalno='" +iBusinessNo + "' and kindcode='3001001'"
		          	+ " and itemcode = '0002'";
					ResultSet rsSumAmount300102 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300102.next()) {
						this.SumAmount300102 = (rsSumAmount300102.getDouble("SumAmount300102")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300102.close();
				
					strSQL = "SELECT Amount AS SumAmount300103 FROM prpTitemkind where proposalno='" +iBusinessNo + "' and kindcode='3001001'"
		          	+ " and itemcode = '0003'";
					ResultSet rsSumAmount300103 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300103.next()) {
						this.SumAmount300103 = (rsSumAmount300103.getDouble("SumAmount300103")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300103.close();
			
					strSQL = "SELECT Amount AS SumAmount300104 FROM prpTitemkind where proposalno='" +iBusinessNo + "' and kindcode='3001001'"
		          	+ " and itemcode = '0004'";
					ResultSet rsSumAmount300104 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300104.next()) {
						this.SumAmount300104 = (rsSumAmount300104.getDouble("SumAmount300104")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300104.close();
		
					strSQL = "SELECT Amount AS SumAmount300105 FROM prpTitemkind where proposalno='" +iBusinessNo + "' and kindcode='3001001'"
			      	+ " and itemcode = '0005'";
					ResultSet rsSumAmount300105 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300105.next()) {
						this.SumAmount300105 = (rsSumAmount300105.getDouble("SumAmount300105")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300105.close();
	
					strSQL = "SELECT Amount AS SumAmount300106 FROM prpTitemkind where proposalno='" +iBusinessNo + "' and kindcode='3001001'"
				  	+ " and itemcode = '0006'";
					ResultSet rsSumAmount300106 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300106.next()) {
						this.SumAmount300106 = (rsSumAmount300106.getDouble("SumAmount300106")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300106.close();
				}
			}
			
			/***************再保部分************************/
			//是否允许划分危险单位（方案1）
			strSQL = "SELECT * FROM PrpdRiskConfig WHERE ConfigCode='RISK_UNIT_FLAG' AND ConfigValue='1' AND RiskCode='"
					+ this.riskCode + "'";
			ResultSet rsReinsUnit = dbManager.executeQuery(strSQL);
			if (rsReinsUnit.next()) {
				this.reinsUnit = "Y"; //划分危险单位
			}
			rsReinsUnit.close();

			//附加自留保费（元）/保额（元）
			strSQL = "SELECT * FROM PrpTReinsTrial WHERE ReinsMode='182' AND ProposalNo='"
					+ iBusinessNo + "'";
			ResultSet rsTrialPremium = dbManager.executeQuery(strSQL);
			if (rsTrialPremium.next()) {
					this.trialAmount = rsTrialPremium.getDouble("Amount")*this.exchRate; //附加自留保额（元）
					this.trialPremium = rsTrialPremium.getDouble("Premium")*this.exchRate; //附加自留保费（元）
			}
			rsTrialPremium.close();
			//临分（含特约）
			strSQL = "SELECT * FROM PrpTReinsTrial WHERE ProposalNo='"
					+ iBusinessNo + "' AND ReinsMode like '3%'";
			ResultSet rsReinsMode = dbManager.executeQuery(strSQL);
			if (rsReinsMode.next()) {
				this.allowSplit = "Y"; //是否允许临分（含特约）
			}
			rsReinsMode.close();
			//费用比例
			strSQL = "SELECT * FROM PrpTexpense WHERE ProposalNo='"
					+ iBusinessNo + "'";
			ResultSet rsExpense = dbManager.executeQuery(strSQL);
			if (rsExpense.next()) {
				this.expenseFeeRate = rsExpense.getDouble("ManageFeeRate")
						+ this.disRate;
			}
			rsExpense.close();
			//add by zhaoning20100129 begin Reason:2010年非车险核保权限
			if(riskCode.equals("0402")){
				strSQL = "SELECT Max(Amount) AS sumAmount0400600 FROM PrpTitemKind WHERE ProposalNo='" 
					+ iBusinessNo + "' and Kindcode ='0400600'"; //财产损失保险保险金额
				ResultSet rsSumAmount0400600 = dbManager.executeQuery(strSQL);
				if (rsSumAmount0400600.next()) {
					this.sumAmount0400600 = rsSumAmount0400600.getDouble("sumAmount0400600") * BWBexchangeRate;
				}
				rsSumAmount0400600.close();
				
				strSQL = "SELECT Max(Amount) AS sumAmount0400700 FROM PrpTitemKind WHERE ProposalNo='" 
					+ iBusinessNo + "' and Kindcode ='0400700'"; //还贷保证保险保险金额
				ResultSet rsSumAmount0400700 = dbManager.executeQuery(strSQL);
				if (rsSumAmount0400700.next()) {
					this.sumAmount0400700 = rsSumAmount0400700.getDouble("sumAmount0400700") * BWBexchangeRate;
				}
				rsSumAmount0400700.close();
			}
			//add by zhaoning20100129 end
		} catch (Exception e) {
			throw e;
		}//try
	}//method
}//class

