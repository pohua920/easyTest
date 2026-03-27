package com.sinosoft.undwrt.common.util;

/**
 * <p>
 * Title: 业务数据
 * </p>
 * <p>
 * Description:通过批单号得到业务数据
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005/7
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 *
 * @author qinyongli
 * @ modify by zhangruifeng 2008-2-20 reason:新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制
 * LanNing    20080301    投资金产品调整
 * modify by zhangruifeng 20080304  reason:针对2008年的双核权限对程序进行调整
 * add by gengxiaobo 20080326 起重机械综合保险高级核保条件
 * added by LanNing 20080421 1505每次事故赔偿限额
 * @version 1.0
 * add by zhangruifeng 20080422 reason:共保业务时按照我方份额进行控制
 * added by gengxiaobo 20080604 增加最大车累计限额,调整每次事故赔偿限额取值。
 * modified by gengxiaobo  20080610 起保小时、终保小时从业务表中获取。
 * added by gengxiaobo 20080618 增加保单注销时批改类型的赋值。
 * @added by xuning 20080814 圆丰产品的质押核批权放在省公司
 * added by xiongguojun 20090716 1506核乏料运输：累计责任限额 
 * added by xiongguojun 20091105 增加产品延长保修服务合同责任保险15980001批改权限
 * added by xiongguojun 20090818 货运险起运日期批改 
 * added by ruanzhongxi 20110726 增加保单停效批改校验
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.sql.*;

import com.sinosoft.payment.bl.facade.BLPrpjunionpayfeeFacade;
import com.sinosoft.platform.dto.domain.SwfNodeDto;
import com.sinosoft.platform.resource.dtofactory.domain.DBSwfNode;
import com.sinosoft.prpall.blsvr.pg.BLPrpPmain;
import com.sinosoft.reins.interf.web.ReinsUndrtInterfAction;
import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.DBManager;

/**
 * The Class BusinessEndorseData.
 */
public class BusinessEndorseData extends BusinessData {
	
	/** 屬性日期 now. */
	private DateTime dateNow = new DateTime().current();     //当前时间
	//车险核保业务数据
	/**
	 * Instantiates a new business endorse data.
	 * 
	 * @param iBusinessNo
	 *            the i business no
	 * @param dbManager
	 *            the db manager
	 * @throws Exception
	 *             the exception
	 */
	public BusinessEndorseData(String iBusinessNo, DBManager dbManager)
			throws Exception {
		BLPrpPmain blPrpPmain = new BLPrpPmain();
		double amountTemp = 0;		//保额（临时，计算用）
		double sumquantity = 1;		//家财险分户数
		double sumquantityp = 1;	//家财险分户数(保单)
		String strModelno = "";		//模版号
		int  strEndNodeno = 0;		//审核通过节点编号
		int kindCountG = 0;			//投保盗抢险且选择不计免赔
		int kindCountL = 0;			//投保划痕险且选择不计免赔特约险
		int kindCountLPA4 = 0; 		//投保专修厂维修特约险或零配件更换险
		int kindCountAGAll = 0; 	//投保车损或盗抢或车损盗抢相关附加险的
		int kindCountA = 0;	 		//是否投保车损险
		int kindCountE = 0; 		//是否投保自燃险
		int tem = 0; 				//临时变量
		double sharerate = 0;
		double coinsRate = 1;
		int startHour = 0;
		int endHour = 0; 		
		String strShortRateFlag =""; //短期费率取值方式
		String strPolicyno ="";  //保单号
		Collection conditionList = new ArrayList();
		SwfNodeDto swfNodeDto = null;
		String coinsflag =""; //共保/联保标志位
		double BWBexchangeRate = 1d;   //签单币别和本位币的兑换率   EndorDisRate
		/******************公共部分********************/
		try {
			String strSQL = "select * from prpPmain where endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rs1 = dbManager.executeQuery(strSQL);
			if (rs1.next()) {
				this.tempPolicyNo = rs1.getString("policyno"); //保单号--很重要，理论上说，核保批单应该去最新的数据，即从prpcp表取
				this.tempProposalNo = rs1.getString("proposalno"); //投保单号
				this.riskCode = rs1.getString("riskcode"); //险种  EndorDisRate
				this.classCode = rs1.getString("classCode"); //险别
				this.currency = rs1.getString("currency"); //币别信息
				BWBexchangeRate = rs1.getDouble("exchangeRate");
				if(BWBexchangeRate == 0){
					BWBexchangeRate = 1;
				}
				this.sumAmount = rs1.getDouble("sumAmount") * BWBexchangeRate; //总保额
				this.chgAmount = rs1.getDouble("chgAmount") * BWBexchangeRate; //总变化金额
				this.discount = rs1.getInt("discount"); //折扣率
				this.disRate1 = rs1.getDouble("disrate1"); //中间成本
				this.disRate = rs1.getInt("disrate"); //经济费和手续费比例
				this.contractno = rs1.getString("contractno"); //合同号
				this.SumPremium = rs1.getDouble("SumPremium"); //总保费
				this.ChgPremium = rs1.getDouble("ChgPremium") * BWBexchangeRate;;  //本次批改变化保费
				this.businessNature = rs1.getString("BusinessNature"); //业务性质
				this.policySort = rs1.getString("policySort");                 //保单种类
				//Date operateDate = new Date(); //签单日期-取当前操作日期
				this.operateDate = rs1.getDate("operateDate"); //签单日期-车险/非车险-prpPmain表中OperateDate即签单日
				//Date signDate = rs1.getDate("SignDate"); //签单日期-非车险
				this.startDate = rs1.getDate("StartDate"); //起始日期
				this.endDate = rs1.getDate("endDate"); //起始日期
				startHour= rs1.getInt("startHour");        //起始时间
				endHour  = rs1.getInt("endHour");          //结束时间				
				coinsflag = rs1.getString("coinsFlag");   //0.独家承保 1.主共保 2.从共保 3.主联保 4.从联保
				strPolicyno= rs1.getString("Policyno");
				if (riskCode.equals("yab0")) {
					this.prepayProtocol = "Y"; //预约协议
				}
				else if(classCode.equals("23")){
					this.sumquantity = rs1.getDouble("sumquantity");
				}
			} else {
				return;
			}
			rs1.close();
			//modify by zhangruifeng 20080304 begin reason:查询批改类型
			strSQL = "select * from prpPhead where endorseno = '"
				+ iBusinessNo + "'";
		    ResultSet rsprpPmain = dbManager.executeQuery(strSQL);
		    if (rsprpPmain.next()) {
		    	this.strEndortype = rsprpPmain.getString("endortype"); //批改类型
		    	this.validDate = rsprpPmain.getDate("validDate"); //批单生效日期
			} else {
				return;
			}
		    rsprpPmain.close();
		    com.sinosoft.prpall.pubfun.PubTools pubTools =  new com.sinosoft.prpall.pubfun.PubTools();
            //解除合同权限天数的控制－解除合同指全单退保、保单注销  21全单退保 
		    // modify by yanglibo 20090326 reason 规则调整
		    if ("19".equals(strEndortype)){//19注销保单 解除合同权限天数＝注销生效日期－起保日期
		    	this.undoContractDate=pubTools.getDayMinus
				(new com.sinosoft.utility.string.Date(startDate.toString()), 0,
						new com.sinosoft.utility.string.Date(validDate.toString()), 24);
		    	this.writeOffDays=this.undoContractDate;
		    	this.strEndortype = "19";//added by gengxiaobo 20080618 增加保单注销时批改类型的赋值。
		    }
		    if ("21".equals(strEndortype)){//21全单退保  解除合同权限天数＝退保生效日期－起保日期校验  startDate
		    	strSQL = "select * from prpcpitemkind where Policyno = '"
					+ strPolicyno + "'";
		    	ResultSet rsprpcpitemkind = dbManager.executeQuery(strSQL);
			    if (rsprpcpitemkind.next()) {
			    	strShortRateFlag = rsprpcpitemkind.getString("ShortRateFlag"); //批改类型
				}
			    rsprpcpitemkind.close();
		    	if(!"A".equals(this.classCode) && !"B".equals(this.classCode)){ //非车险当是全单退保且选择按约定的方式时控制天数
				    if("5".equals(strShortRateFlag)){  //当是按约定方式的全单退保时加入天数控制
				    	this.undoContractDate=pubTools.getDayMinus
						(new com.sinosoft.utility.string.Date(startDate.toString()), 0,
								new com.sinosoft.utility.string.Date(validDate.toString()), 24);
				    	if("01".equals(this.classCode)||"07".equals(this.classCode)){//企财险与工程险需要控制退保的保费
					    	this.TuiBaoPremium = -this.ChgPremium;
					    }
				    }else{
				    	this.TuiBaoPremium = -this.ChgPremium;
				    	this.undoContractDate=pubTools.getDayMinus
						(new com.sinosoft.utility.string.Date(startDate.toString()), 0,
								new com.sinosoft.utility.string.Date(validDate.toString()), 24);
				    }
		    	}else if ("A".equals(this.classCode) || "B".equals(this.classCode)){
		    		if("3".equals(strShortRateFlag)){//车险是全单退保批改并且选择全额退保时加入天数的控制
		    			this.undoContractDate=pubTools.getDayMinus
						(new com.sinosoft.utility.string.Date(startDate.toString()), 0,
								new com.sinosoft.utility.string.Date(validDate.toString()), 24);
		    		}
		    	}
		    }
		    if("54".equals(strEndortype)){//增加保单停效批改校验 ruanzhongxi_leave
		    	BLPrpjunionpayfeeFacade blPrpjunionpayfeeFacade = new BLPrpjunionpayfeeFacade();
		    	this.unionpayCount = blPrpjunionpayfeeFacade.getCount(" PayStatus In ('02','05') And PolicyNo = '"+strPolicyno+"'");
		    }
		    if ("75".equals(strEndortype)||"76".equals(strEndortype)){//75 圆丰产品质押批改/76解除质押批改  
		    	this.Mortgage = "Y";//该批改是质押或解除质押批改。
		    }
			//总保额（批单的金额权限取历次批改，包括当前批改中最大者）
			//当前批改结果值
			amountTemp = sumAmount + this.chgAmount;
			
            strSQL = "select * from prpphead where endorseno = '" + iBusinessNo
					+ "'";
			ResultSet rsDate = dbManager.executeQuery(strSQL);
			if (rsDate.next()) {
				this.validDate = rsDate.getDate("ValidDate"); // 生效日期-车险
			} else {
				return;
			}
            rsDate.close();
            
			//返回投保单的提交核保日期--取最近一次的提交核保时间。
			strSQL = "select to_date(flowintime,'yyyy-mm-dd hh24:mi:ss') as flowintime ,modelno from wflog where businessno = '"
					+ iBusinessNo + "' and nodeno = '1' and rownum =1 order by logno desc";
			ResultSet rsWflog = dbManager.executeQuery(strSQL);
			if (rsWflog.next()) {
				this.flowintime = rsWflog.getDate("flowintime");
				strModelno = rsWflog.getString("modelno");
			}
			rsWflog.close();
			
 			//需要整理倒签单的定义，是否需要用wflog 表中的提交时间为准
			if (businessNature.equals("05") || businessNature.equals("08")) { // 直接业务
				if (flowintime != null && validDate != null) { // 直接倒签单天数－车险
					DirectDay = pubTools.getDayMinus
					(new com.sinosoft.utility.string.Date(validDate.toString()), 0,
							new com.sinosoft.utility.string.Date(flowintime.toString()), 24);
				}
			} else {                                           // 代理业务
				if (flowintime != null && validDate != null) { // 代理倒签单天数－车险
					AgentDay = pubTools.getDayMinus
					(new com.sinosoft.utility.string.Date(validDate.toString()), 0,
							new com.sinosoft.utility.string.Date(flowintime.toString()), 24);

				}
			}
            
			//返回保单的提交的核保级别。
			DBSwfNode dbSwfNode = new DBSwfNode(dbManager);
			String swfnode = "modelno = '"+strModelno+ "' and endflag ='1'";
			dbSwfNode.findByConditions(swfnode);
			
            conditionList = dbSwfNode.findByConditions(swfnode);
            Iterator itcondition = conditionList.iterator();
            while (itcondition.hasNext())
            {
            	swfNodeDto = (SwfNodeDto) itcondition.next();
            	strEndNodeno = swfNodeDto.getNodeNo();
            }
			strSQL = "select * from (select * from wflog where businessno = '"+this.tempProposalNo+"" +
					"'and nodeno != '"+strEndNodeno+"'and nodestatus ='0' order by logno desc) where rownum ='1'";
			ResultSet rsWflog1 = dbManager.executeQuery(strSQL);
			if (rsWflog1.next()) {
				NodenoOld = rsWflog1.getDouble("nodeno");
				NodeNameOld = rsWflog1.getString("nodename");
			}
			rsWflog1.close();
			//返回当前的核批级别。
			strSQL = "select * from wflog where businessno = '"+iBusinessNo+"' order by logno desc";
			ResultSet rsWflog2 = dbManager.executeQuery(strSQL);
			if (rsWflog2.next()) {
				Nodeno = rsWflog2.getDouble("nodeno");
				NodeName = rsWflog2.getString("nodename");
			}
			rsWflog2.close();
			//已往批改最大保额值
			String strSumAmount = "SELECT MAX(SumAmount) AS SumAmount FROM PrpPmain WHERE PolicyNo='"
					+ this.tempPolicyNo + "'";
			ResultSet rsSumAmount = dbManager.executeQuery(strSumAmount);
			if (rsSumAmount.next()) {
				this.sumAmountP = rsSumAmount.getDouble("SumAmount") * BWBexchangeRate;
			}
            rsSumAmount.close();
			//当前业务与历次最高比较取大值，即如果当前是批增则加当前变化量
			if (amountTemp > sumAmountP) {
				this.sumAmount = amountTemp;
			}else{
				this.sumAmount = this.sumAmountP;
			}
			amountTemp = 0; //要清空amountTemp，否则会影响下面的计算

			//add by zhulei begin 20060224 特殊因子、手续费，如果有预约协议，需要特殊处理
//            strSQL = "select * from prpCmainSub where policyno = '"+tempPolicyNo+"'";
//            ResultSet rsCmainSub = dbManager.executeQuery(strSQL);
//            if(rsCmainSub.next()){
//            	//有预约协议大保单
//            	String strPolicyNoMain = rsCmainSub.getString("MainPolicyNo");
//                strSQL = "select * from prpCmain where policyno = '"+strPolicyNoMain+"'";
//                ResultSet rsCmainMain = dbManager.executeQuery(strSQL);
//                //小保单特殊因子、手续费比例小于等于预约协议特殊因子时，不再校验，业务取数值给 0
//                if(rsCmainMain.next()){
//                	if(disRate1<=rsCmainMain.getDouble("disRate1")){  //特殊因子
//                		disRate1 = 0;
//                	}
//                	if(disRate<=rsCmainMain.getDouble("disRate")){    //手续费
//                		disRate = 0;
//                	}
//                }
//                rsCmainMain.close();
//                strSQL = "select * from prpCmainCargo where policyno = '"+strPolicyNoMain+"'";
//                ResultSet rsCmainMainCargo = dbManager.executeQuery(strSQL);
//                //小保单特殊因子、手续费比例小于等于预约协议特殊因子时，不再校验，业务取数值给 0
//                if(rsCmainMainCargo.next()){
//                	if(AgentDay<=rsCmainMainCargo.getInt("OriginalCount")){
//                		AgentDay = 0;
//                	}
//                	if(sumAmount<=rsCmainMainCargo.getDouble("InvoiceAmount")){
//                		sumAmount = 0;
//                	}
//                }
//                rsCmainMainCargo.close();
//            }
//            rsCmainSub.close();
            //add by zhulei end 20060224 特殊因子、手续费，如果有预约协议，需要特殊处理
            //add by dengwenchun 20060413 reason:货物运输预约保险每次运输限额控制 begin
			if(riskCode.equals("YAB0")){
				strSQL = "select invoiceamount from prpcpmaincargo where policyno='" + tempPolicyNo + "'";
				ResultSet rsDisprptmaincargo = dbManager.executeQuery(strSQL);
				if(rsDisprptmaincargo.next())
				{
					this.sumAmount = rsDisprptmaincargo.getDouble("InvoiceAmount");
				}
				else
				{
					strSQL = "select invoiceamount from prppmaincargo where endorseno='" + iBusinessNo + "'";
					rsDisprptmaincargo = dbManager.executeQuery(strSQL);
					if(rsDisprptmaincargo.next())
					{
						this.sumAmount = rsDisprptmaincargo.getDouble("invoiceamount");
					}
				}
				rsDisprptmaincargo.close();
			}
			//兑换率 prpdexch表
			String strExchRate = "SELECT ExchRate FROM PrpDexch WHERE exchcurrency='CNY' AND ValidStatus='1' AND BaseCurrency='"
					+ this.currency + "' order by exchdate desc";
			ResultSet rsExchRate = dbManager.executeQuery(strExchRate);
			if (rsExchRate.next()) {
				this.exchRate = rsExchRate.getDouble("ExchRate");
			} else //如果未取得结果，exchRate取默认1
			{
				this.exchRate = 1;
			}
            rsExchRate.close();
			
			/*****************非车非意健部分-begin*****************/
			if(classCode.equals("03")){
				strSQL = "select sumquantity  from prpcpmain where policyno='" + tempPolicyNo + "'";
				ResultSet rsDisprpCpmain = dbManager.executeQuery(strSQL);
				if(rsDisprpCpmain.next())
				{
					sumquantity = rsDisprpCpmain.getDouble("sumquantity");
				}
				rsDisprpCpmain.close();
				

				strSQL = "SELECT  MAX(SUM(amount)) AS sumAmount from prppitemkind where policyno='" + tempPolicyNo + "' " +
						" group by FAMILYNO";
				ResultSet rsDisprppmain = dbManager.executeQuery(strSQL);
				if(rsDisprppmain.next())
				{
					sumAmount = rsDisprppmain.getDouble("sumAmount") * BWBexchangeRate;
				}
				rsDisprppmain.close();
				
				strSQL = "SELECT  MAX(SUM(amount)) AS sumAmount from prpcpitemkind where policyno='" + tempPolicyNo + "'"+
				         " group by FAMILYNO";;
				ResultSet rsDisprpcpmain = dbManager.executeQuery(strSQL);
				if(rsDisprpcpmain.next())
				{
					sumAmountP = rsDisprpcpmain.getDouble("sumAmount") * BWBexchangeRate;
				}
				rsDisprpcpmain.close();
				
				if(sumAmountP>sumAmount){
					sumAmount = sumAmountP;
				}
				

				//房屋及室内附属设备
				String strSQL0001 = "select max(a.amount) as amountPer03010001 from prpcpitemkind a ,prpCpmain  b " +
						"where a.riskcode ='"+this.riskCode+"' and a.policyno = b.policyno and a.itemcode ='0001'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
				ResultSet rsAmoutPer03010001 = dbManager
						.executeQuery(strSQL0001);
				if (rsAmoutPer03010001.next()) {
					this.amountPer03010001 = rsAmoutPer03010001
							.getDouble("amountPer03010001") * BWBexchangeRate;
				}
				rsAmoutPer03010001.close();
				
				strSQL0001 = "select max(a.amount) as amountPer03010001 from prppitemkind a ,prpCpmain  b " +
						"where a.riskcode ='"+this.riskCode+"' and a.policyno = b.policyno and a.itemcode ='0001'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
				ResultSet rsAmoutPer03010001P = dbManager
						.executeQuery(strSQL0001);
				if (rsAmoutPer03010001P.next()) {
					this.amountPer03010001P = rsAmoutPer03010001P
							.getDouble("amountPer03010001") * BWBexchangeRate;
				}
				rsAmoutPer03010001P.close();
				if(amountPer03010001P>amountPer03010001){
					amountPer03010001=amountPer03010001P;
				}
				
				//室内装潢
//				String strSQL0002 = "select * from prpcpitemkind where itemcode ='0002' and  policyno='" + tempPolicyNo + "'";
//				ResultSet rsAmoutPer03010002 = dbManager.executeQuery(strSQL0002);
//				if(rsAmoutPer03010002.next())
//				{
//					this.amoutPer03010002 = rsAmoutPer03010002.getDouble("amount")/sumquantity;
//				}
//				rsAmoutPer03010002.close();
				
				//处理时按照每户的最大保额来控制
				String strSQL0002 = "select max(a.amount) as amountPer03010001 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.itemcode ='0002'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
				ResultSet rsAmoutPer03010002 = dbManager
						.executeQuery(strSQL0002);
				if (rsAmoutPer03010002.next()) {
					this.amountPer03010002 = rsAmoutPer03010002
							.getDouble("amountPer03010001") * BWBexchangeRate;
				}
				rsAmoutPer03010002.close();

				strSQL0001 = "select max(a.amount) as amountPer03010001 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.itemcode ='0002'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
				ResultSet rsAmoutPer03010002P = dbManager
						.executeQuery(strSQL0001);
				if (rsAmoutPer03010002P.next()) {
					this.amountPer03010002P = rsAmoutPer03010002P
							.getDouble("amountPer03010001") * BWBexchangeRate;
				}
				rsAmoutPer03010002P.close();
				if (amountPer03010002P > amountPer03010002) {
					amountPer03010002 = amountPer03010002P;
				}
				
				//处理时按照每户的最大保额来控制
				String strSQLunitAmount03 = "select max(a.unitamount) as unitAmount03 from prpcpitemkind a ,prpCpmain  b " +
						"where a.riskcode ='"+this.riskCode+"' and a.policyno = b.policyno "
						+ " and a.policyno ='" + tempPolicyNo + "'";
				ResultSet rsUnitAmout03 = dbManager.executeQuery(strSQLunitAmount03);
				if (rsUnitAmout03.next()) {
					this.unitAmount03 = rsUnitAmout03.getDouble("unitAmount03") * BWBexchangeRate;
				}
				rsUnitAmout03.close();
				
				strSQLunitAmount03 = "select max(a.unitamount) as unitAmount03 from prppitemkind a ,prpCpmain  b " +
						"where a.riskcode ='"+this.riskCode+"' and a.policyno = b.policyno "
						+ " and a.policyno ='" + tempPolicyNo + "'";
				ResultSet rsUnitAmout03P = dbManager
						.executeQuery(strSQLunitAmount03);
				if (rsUnitAmout03P.next()) {
					this.unitAmount03P = rsUnitAmout03P
							.getDouble("unitamount03") * BWBexchangeRate;
				}
				rsUnitAmout03P.close();
				if(unitAmount03P>unitAmount03){
					unitAmount03=unitAmount03P;
				}
				if (riskCode.equals("0301")){
					//0301家庭财产保险附加险 附加盗抢保险条款保额
					String strSQL9000452 = "select max(a.amount) as amountPer9000452 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000452'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000452 = dbManager
						.executeQuery(strSQL9000452);
					if (rsAmountPer9000452.next()) {
						this.amountPer9000452 = rsAmountPer9000452
							.getDouble("amountPer9000452") * BWBexchangeRate;
					}
					rsAmountPer9000452.close();
					
					strSQL9000452 = "select max(a.amount) as amountPer9000452 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000452'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000452P = dbManager
							.executeQuery(strSQL9000452);
					if (rsAmountPer9000452P.next()) {
						this.amountPer9000452P = rsAmountPer9000452P
								.getDouble("amountPer9000452") * BWBexchangeRate;
					}
					rsAmountPer9000452P.close();
					if (amountPer9000452P > amountPer9000452) {
						amountPer9000452 = amountPer9000452P;
					}
					
					//0301家庭财产保险附加险 附加家用电器用电安全保险条款保额
					String strSQL9000453 = "select max(a.amount) as amountPer9000453 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000453'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000453 = dbManager
						.executeQuery(strSQL9000453);
					if (rsAmountPer9000453.next()) {
						this.amountPer9000453 = rsAmountPer9000453
							.getDouble("amountPer9000453") * BWBexchangeRate;
					}
					rsAmountPer9000453.close();
					
					strSQL9000453 = "select max(a.amount) as amountPer9000453 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000453'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000453P = dbManager
							.executeQuery(strSQL9000453);
					if (rsAmountPer9000453P.next()) {
						this.amountPer9000453P = rsAmountPer9000453P
								.getDouble("amountPer9000453") * BWBexchangeRate;
					}
					rsAmountPer9000453P.close();
					if (amountPer9000453P > amountPer9000453) {
						amountPer9000453 = amountPer9000453P;
					}
					
					//0301家庭财产保险附加险 附加管道破裂及水渍保险条款保额
					String strSQL9000454 = "select max(a.amount) as amountPer9000454 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000454'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000454 = dbManager
						.executeQuery(strSQL9000454);
					if (rsAmountPer9000454.next()) {
						this.amountPer9000454 = rsAmountPer9000454
							.getDouble("amountPer9000454") * BWBexchangeRate;
					}
					rsAmountPer9000454.close();
					
					strSQL9000454 = "select max(a.amount) as amountPer9000454 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000454'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000454P = dbManager
							.executeQuery(strSQL9000454);
					if (rsAmountPer9000454P.next()) {
						this.amountPer9000454P = rsAmountPer9000454P
								.getDouble("amountPer9000454") * BWBexchangeRate;
					}
					rsAmountPer9000454P.close();
					if (amountPer9000454P > amountPer9000454) {
						amountPer9000454 = amountPer9000454P;
					}
					
					//0301家庭财产保险附加险 附加居家责任保险条款保额
					String strSQL9000449 = "select max(a.amount) as amountPer9000449 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000449'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000449 = dbManager
						.executeQuery(strSQL9000449);
					if (rsAmountPer9000449.next()) {
						this.amountPer9000449 = rsAmountPer9000449
							.getDouble("amountPer9000449") * BWBexchangeRate;
					}
					rsAmountPer9000449.close();
					
					strSQL9000449 = "select max(a.amount) as amountPer9000449 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000449'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000449P = dbManager
							.executeQuery(strSQL9000449);
					if (rsAmountPer9000449P.next()) {
						this.amountPer9000449P = rsAmountPer9000449P
								.getDouble("amountPer9000449") * BWBexchangeRate;
					}
					rsAmountPer9000449P.close();
					if (amountPer9000449P > amountPer9000449) {
						amountPer9000449 = amountPer9000449P;
					}
					
					//0301家庭财产保险附加险 附加家庭伤害保险条款保额
					String strSQL9000450 = "select max(a.amount) as amountPer9000450 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000450'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000450 = dbManager
						.executeQuery(strSQL9000450);
					if (rsAmountPer9000450.next()) {
						this.amountPer9000450 = rsAmountPer9000450
							.getDouble("amountPer9000450") * BWBexchangeRate;
					}
					rsAmountPer9000450.close();
					
					strSQL9000450 = "select max(a.amount) as amountPer9000450 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000450'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000450P = dbManager
							.executeQuery(strSQL9000450);
					if (rsAmountPer9000450P.next()) {
						this.amountPer9000450P = rsAmountPer9000450P
								.getDouble("amountPer9000450") * BWBexchangeRate;
					}
					rsAmountPer9000450P.close();
					if (amountPer9000450P > amountPer9000450) {
						amountPer9000450 = amountPer9000450P;
					}
					
					//0301家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
					String strSQL9000451 = "select max(a.amount) as amountPer9000451 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000451'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000451 = dbManager
						.executeQuery(strSQL9000451);
					if (rsAmountPer9000451.next()) {
						this.amountPer9000451 = rsAmountPer9000451
							.getDouble("amountPer9000451") * BWBexchangeRate;
					}
					rsAmountPer9000451.close();
					
					strSQL9000451 = "select max(a.amount) as amountPer9000451 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='9000451'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000451P = dbManager
							.executeQuery(strSQL9000451);
					if (rsAmountPer9000451P.next()) {
						this.amountPer9000451P = rsAmountPer9000451P
								.getDouble("amountPer9000451") * BWBexchangeRate;
					}
					rsAmountPer9000451P.close();
					if (amountPer9000451P > amountPer9000451) {
						amountPer9000451 = amountPer9000451P;
					}
				}else if (riskCode.equals("0309")){
					//0309家庭财产保险附加险 附加盗抢保险条款保额
					String strSQL9000452 = "select max(a.amount) as amountPer9000452 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309200'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000452 = dbManager
						.executeQuery(strSQL9000452);
					if (rsAmountPer9000452.next()) {
						this.amountPer9000452 = rsAmountPer9000452
							.getDouble("amountPer9000452") * BWBexchangeRate;
					}
					rsAmountPer9000452.close();
					
					strSQL9000452 = "select max(a.amount) as amountPer9000452 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309200'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000452P = dbManager
							.executeQuery(strSQL9000452);
					if (rsAmountPer9000452P.next()) {
						this.amountPer9000452P = rsAmountPer9000452P
								.getDouble("amountPer9000452") * BWBexchangeRate;
					}
					rsAmountPer9000452P.close();
					if (amountPer9000452P > amountPer9000452) {
						amountPer9000452 = amountPer9000452P;
					}
					
					//0309家庭财产保险附加险 附加家用电器用电安全保险条款保额
					String strSQL9000453 = "select max(a.amount) as amountPer9000453 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309300'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000453 = dbManager
						.executeQuery(strSQL9000453);
					if (rsAmountPer9000453.next()) {
						this.amountPer9000453 = rsAmountPer9000453
							.getDouble("amountPer9000453") * BWBexchangeRate;
					}
					rsAmountPer9000453.close();
					
					strSQL9000453 = "select max(a.amount) as amountPer9000453 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309300'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000453P = dbManager
							.executeQuery(strSQL9000453);
					if (rsAmountPer9000453P.next()) {
						this.amountPer9000453P = rsAmountPer9000453P
								.getDouble("amountPer9000453") * BWBexchangeRate;
					}
					rsAmountPer9000453P.close();
					if (amountPer9000453P > amountPer9000453) {
						amountPer9000453 = amountPer9000453P;
					}
					
					//0309家庭财产保险附加险 附加管道破裂及水渍保险条款保额
					String strSQL9000454 = "select max(a.amount) as amountPer9000454 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309400'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000454 = dbManager
						.executeQuery(strSQL9000454);
					if (rsAmountPer9000454.next()) {
						this.amountPer9000454 = rsAmountPer9000454
							.getDouble("amountPer9000454") * BWBexchangeRate;
					}
					rsAmountPer9000454.close();
					
					strSQL9000454 = "select max(a.amount) as amountPer9000454 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309400'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000454P = dbManager
							.executeQuery(strSQL9000454);
					if (rsAmountPer9000454P.next()) {
						this.amountPer9000454P = rsAmountPer9000454P
								.getDouble("amountPer9000454") * BWBexchangeRate;
					}
					rsAmountPer9000454P.close();
					if (amountPer9000454P > amountPer9000454) {
						amountPer9000454 = amountPer9000454P;
					}
					
					//0309家庭财产保险附加险 附加居家责任保险条款保额
					String strSQL9000449 = "select max(a.amount) as amountPer9000449 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309500'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000449 = dbManager
						.executeQuery(strSQL9000449);
					if (rsAmountPer9000449.next()) {
						this.amountPer9000449 = rsAmountPer9000449
							.getDouble("amountPer9000449") * BWBexchangeRate;
					}
					rsAmountPer9000449.close();
					
					strSQL9000449 = "select max(a.amount) as amountPer9000449 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309500'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000449P = dbManager
							.executeQuery(strSQL9000449);
					if (rsAmountPer9000449P.next()) {
						this.amountPer9000449P = rsAmountPer9000449P
								.getDouble("amountPer9000449") * BWBexchangeRate;
					}
					rsAmountPer9000449P.close();
					if (amountPer9000449P > amountPer9000449) {
						amountPer9000449 = amountPer9000449P;
					}
					
					//0309家庭财产保险附加险 附加家庭伤害保险条款保额
					String strSQL9000450 = "select max(a.amount) as amountPer9000450 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309700'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000450 = dbManager
						.executeQuery(strSQL9000450);
					if (rsAmountPer9000450.next()) {
						this.amountPer9000450 = rsAmountPer9000450
							.getDouble("amountPer9000450") * BWBexchangeRate;
					}
					rsAmountPer9000450.close();
					
					strSQL9000450 = "select max(a.amount) as amountPer9000450 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309700'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000450P = dbManager
							.executeQuery(strSQL9000450);
					if (rsAmountPer9000450P.next()) {
						this.amountPer9000450P = rsAmountPer9000450P
								.getDouble("amountPer9000450") * BWBexchangeRate;
					}
					rsAmountPer9000450P.close();
					if (amountPer9000450P > amountPer9000450) {
						amountPer9000450 = amountPer9000450P;
					}
					
					//0309家庭财产保险附加险 附加家庭意外骨折医疗保险条款保额
					String strSQL9000451 = "select max(a.amount) as amountPer9000451 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309600'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000451 = dbManager
						.executeQuery(strSQL9000451);
					if (rsAmountPer9000451.next()) {
						this.amountPer9000451 = rsAmountPer9000451
							.getDouble("amountPer9000451") * BWBexchangeRate;
					}
					rsAmountPer9000451.close();
					
					strSQL9000451 = "select max(a.amount) as amountPer9000451 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0309600'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000451P = dbManager
							.executeQuery(strSQL9000451);
					if (rsAmountPer9000451P.next()) {
						this.amountPer9000451P = rsAmountPer9000451P
								.getDouble("amountPer9000451") * BWBexchangeRate;
					}
					rsAmountPer9000451P.close();
					if (amountPer9000451P > amountPer9000451) {
						amountPer9000451 = amountPer9000451P;
					}
				}else if (riskCode.equals("0310")){
					//0310附加住宅火灾事故延烧自有车辆保险
					String strSQL9000455 = "select max(a.amount) as amountPer9000455 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0310200'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000455 = dbManager
						.executeQuery(strSQL9000455);
					if (rsAmountPer9000455.next()) {
						this.amountPer9000455 = rsAmountPer9000455
							.getDouble("amountPer9000455") * BWBexchangeRate;
					}
					rsAmountPer9000455.close();
					
					strSQL9000455 = "select max(a.amount) as amountPer9000455 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0310200'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000455P = dbManager
							.executeQuery(strSQL9000455);
					if (rsAmountPer9000455P.next()) {
						this.amountPer9000455P = rsAmountPer9000455P
								.getDouble("amountPer9000455") * BWBexchangeRate;
					}
					rsAmountPer9000455P.close();
					if (amountPer9000455P > amountPer9000455) {
						amountPer9000455 = amountPer9000455P;
					}
					
					//0310附加租房费用损失保险
					String strSQL9000456 = "select max(a.amount) as amountPer9000456 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0310300'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000456 = dbManager
						.executeQuery(strSQL9000456);
					if (rsAmountPer9000456.next()) {
						this.amountPer9000456 = rsAmountPer9000456
							.getDouble("amountPer9000456") * BWBexchangeRate;
					}
					rsAmountPer9000456.close();
					
					strSQL9000456 = "select max(a.amount) as amountPer9000456 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0310300'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000456P = dbManager
							.executeQuery(strSQL9000456);
					if (rsAmountPer9000456P.next()) {
						this.amountPer9000456P = rsAmountPer9000456P
								.getDouble("amountPer9000456") * BWBexchangeRate;
					}
					rsAmountPer9000456P.close();
					if (amountPer9000456P > amountPer9000456) {
						amountPer9000456 = amountPer9000456P;
					}
					
					//0310附加家庭火灾火场清理费用损失保险
					String strSQL9000457 = "select max(a.amount) as amountPer9000457 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0310400'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000457 = dbManager
						.executeQuery(strSQL9000457);
					if (rsAmountPer9000457.next()) {
						this.amountPer9000457 = rsAmountPer9000457
							.getDouble("amountPer9000457") * BWBexchangeRate;
					}
					rsAmountPer9000457.close();
					
					strSQL9000457 = "select max(a.amount) as amountPer9000457 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0310400'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000457P = dbManager
							.executeQuery(strSQL9000457);
					if (rsAmountPer9000457P.next()) {
						this.amountPer9000457P = rsAmountPer9000457P
								.getDouble("amountPer9000457") * BWBexchangeRate;
					}
					rsAmountPer9000457P.close();
					if (amountPer9000457P > amountPer9000457) {
						amountPer9000457 = amountPer9000457P;
					}					
					
					//0310附加搬迁费用损失保险
					String strSQL9000458 = "select max(a.amount) as amountPer9000458 from prpcpitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0310500'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000458 = dbManager
						.executeQuery(strSQL9000458);
					if (rsAmountPer9000458.next()) {
						this.amountPer9000458 = rsAmountPer9000458
							.getDouble("amountPer9000458") * BWBexchangeRate;
					}
					rsAmountPer9000458.close();
					
					strSQL9000458 = "select max(a.amount) as amountPer9000458 from prppitemkind a ,prpCpmain  b "
						+ "where a.riskcode ='"
						+ this.riskCode
						+ "' and a.policyno = b.policyno and a.kindcode ='0310500'"
						+ " and a.policyno ='" + tempPolicyNo + "'";
					ResultSet rsAmountPer9000458P = dbManager
							.executeQuery(strSQL9000458);
					if (rsAmountPer9000458P.next()) {
						this.amountPer9000458P = rsAmountPer9000458P
								.getDouble("amountPer9000458") * BWBexchangeRate;
					}
					rsAmountPer9000458P.close();
					if (amountPer9000458P > amountPer9000458) {
						amountPer9000458 = amountPer9000458P;
					}					
				}
			}
			/*****************非车非意健部分-end*****************/
			

//			//兑换后的总保额(当前prpPmain表存总保额为已兑换人民币的，更新可能加入币种问题，待确认)
//			this.sumAmount = this.sumAmount * this.exchRate;

			//获取折扣信息
			String strWhereDisCount = "SELECT * FROM PrpPitemkind WHERE EndorseNo ='"
					+ this.iBusinessNo + "'";
			ResultSet rsDisCount = dbManager.executeQuery(strWhereDisCount);
			if (rsDisCount.next()) {
				this.discount = rsDisCount.getInt("DisCount");
				this.shortRate = rsDisCount.getDouble("ShortRate");
			} else {
				if (!this.riskCode.equals("A01") && !this.riskCode.equals("0502") && !this.riskCode.equals("B01") && 
						!this.riskCode.equals("0510")) {
					this.discount = 100;
					this.shortRate = 100;
				}
			}
            rsDisCount.close();
            /*******************车险部分*********************/
			//从投保单ItemCar表获取数据
            if(classCode.equals("A") || classCode.equals("B")){
            	if (riskCode.equals("0502")) {
					/*String strAmountG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'G' AND riskcode='0502' and " +
							" proposalno in"+
	                   "(select proposalno from prpTitemcar where carkindcode='G1' and proposalno='"
						    + iBusinessNo + "')";*/
					String strAmountG1 = "SELECT * FROM PrpCpitemkind WHERE KindCode = 'G' AND riskcode='0502' and " +
						" policyno ='" + this.tempPolicyNo + "'";
				    ResultSet rsAmountG1 = dbManager.executeQuery(strAmountG1);
				    if (rsAmountG1.next()) {
					   amountG1 = rsAmountG1.getDouble("amount") * BWBexchangeRate;				   
				    }
				    rsAmountG1.close();
				    
				    /*String stramountAG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'A' AND riskcode='0502' and " +
					" proposalno in"+
	                "(select proposalno from prpTitemcar where carkindcode='G1' and proposalno='"
						    + iBusinessNo + "')";*/
				    String stramountAG1 = "SELECT * FROM PrpCpitemkind WHERE KindCode = 'A' AND riskcode='0502' and " +
						" policyno ='"+ this.tempPolicyNo + "'";
				    ResultSet rsAmountAG1 = dbManager.executeQuery(stramountAG1);
				    if (rsAmountAG1.next()) {
				    	amountAG1 = rsAmountAG1.getDouble("amount") * BWBexchangeRate;
				    }
				    rsAmountAG1.close();
				    
				    /*String stramountBG1 = "SELECT * FROM PrpTitemkind WHERE KindCode = 'B' AND riskcode='0502' and " +
					" proposalno in"+
	                "(select proposalno from prpTitemcar where carkindcode='G1' and proposalno='"
						    + iBusinessNo + "')";*/
				    String stramountBG1 = "SELECT * FROM PrpCpitemkind WHERE KindCode = 'B' AND riskcode='0502' and " +
						" policyno ='"+ this.tempPolicyNo + "'";
				    ResultSet rsAmountBG1 = dbManager.executeQuery(stramountBG1);
				    if (rsAmountBG1.next()) {
				    	amountBG1 = rsAmountBG1.getDouble("amount") * BWBexchangeRate;
				    }
				    rsAmountBG1.close();
				}
            	
            	//投保车损险
    	        strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='"+this.tempPolicyNo+
    			"'and kindcode= 'A'";
    			ResultSet rsKindA = dbManager.executeQuery(strSQL);
    			if (rsKindA.next()) {
    				kindCountA = rsKindA.getInt("kindCount");//投保车损险
    			}
    			rsKindA.close();
    			
    			//从ItemCar表获取数据
				String strItem = "select * from prpCPitemCar where policyno ='"
						+ this.tempPolicyNo + "'";
				ResultSet rsItem = dbManager.executeQuery(strItem);
				if (rsItem.next()) {
					this.insuredTypeCode = rsItem.getString("InsuredTypeCode");//客户性质
					this.useNatureCode = rsItem.getString("UseNatureCode"); //使用性质
					this.modelCode = rsItem.getString("ModelCode"); //车型信息
					this.useYears = rsItem.getInt("UseYears"); //使用年限
					this.CarKindCode = rsItem.getString("CarKindCode"); //车辆类型
					if(kindCountA>0){
						this.ChooseAPrice = rsItem.getDouble("purchaseprice");//投保车损险的新车购置价
					}
					if("H0".equals(this.CarKindCode)){
						this.tonCountH0 = rsItem.getDouble("tonCount");//货车的核定载质量
					}
				}
	            rsItem.close();
            
            //车险新增*****************************************************************************************
            // 保险期限（按月）
			this.CBMonthLimit = pubTools.getMonthMinus
				(new com.sinosoft.utility.string.Date(startDate.toString()),startHour,
						new com.sinosoft.utility.string.Date(endDate.toString()),endHour);
			
			//投保盗抢险且选择不计免赔
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='"+this.tempPolicyNo+
			"'and kindcode='G' and substr(flag,5,1)='1'";
			ResultSet rsKindG = dbManager.executeQuery(strSQL);
			if(rsKindG.next()){
				kindCountG = rsKindG.getInt("kindCount");
			}
			rsKindG.close();
			strSQL = "select * from prpCPitemCar where policyno ='"
					+ this.tempPolicyNo + "'";
			ResultSet rsChooseG1Years = dbManager.executeQuery(strSQL);
			if(rsChooseG1Years.next()){
				if(kindCountG>0){
					ChooseG1Years = rsChooseG1Years.getInt("UseYears");//投保盗抢险且选择不计免赔的车辆使用年限
				}
			}
			rsChooseG1Years.close();
			
			/*
			 * //投保划痕险且选择不计免赔特约险
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='"+this.tempPolicyNo+
			"'and kindcode='L' and substr(flag,5,1)='1'";
			ResultSet rsKindL = dbManager.executeQuery(strSQL);
			if(rsKindL.next()){
				kindCountL = rsKindL.getInt("kindCount");
			}
			rsKindL.close();
			strSQL = "select * from prpCPitemCar where policyno ='"
					+ this.tempPolicyNo + "'";
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
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='"+this.tempPolicyNo+
			"'and kindcode='L'";
			ResultSet rsKindL = dbManager.executeQuery(strSQL);
			if(rsKindL.next()){
				kindCountL = rsKindL.getInt("kindCount");
			}
			rsKindL.close();
			strSQL = "select * from prpCPitemCar where policyno ='"
					+ this.tempPolicyNo + "'";
			ResultSet rsChooseL1Years = dbManager.executeQuery(strSQL);
			if(rsChooseL1Years.next()){
				if(kindCountL>0){
					ChooseL1Years = rsChooseL1Years.getInt("UseYears");//投保划痕险且选择不计免赔的车辆使用年限
				}
			}
			rsChooseL1Years.close();
			
			//投保专修厂维修特约险或零配件更换险
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='"+this.tempPolicyNo+
			"'and kindcode in ('LP','A4')";
			ResultSet rsKindLPA4 = dbManager.executeQuery(strSQL);
			if(rsKindLPA4.next()){
				kindCountLPA4 = rsKindLPA4.getInt("kindCount");
			}
			rsKindLPA4.close();
			strSQL = "select * from prpCPitemCar where policyno ='"
					+ this.tempPolicyNo + "'";
			ResultSet rsChooseLPA4Years = dbManager.executeQuery(strSQL);
			if(rsChooseLPA4Years.next()){
				if(kindCountLPA4>0){
					ChooseLPA4Years = rsChooseLPA4Years.getInt("UseYears");//投保专修厂维修特约险或零配件更换险的车辆使用年限
				}
			}
			rsChooseLPA4Years.close();
			
			//投保车损或盗抢或车损盗抢相关附加险的
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='"+this.tempPolicyNo+
			"'and kindcode in ('A','G')";
			ResultSet rsKindAGAll = dbManager.executeQuery(strSQL);
			if (rsKindAGAll.next()){
				kindCountAGAll = rsKindAGAll.getInt("kindCount");
			}
			rsKindAGAll.close();
			strSQL = "select * from prpCPitemCar where policyno ='"
					+ this.tempPolicyNo + "'";
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
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='" + this.tempPolicyNo +
			"'and kindcode='Z'";
			ResultSet rsKindE = dbManager.executeQuery(strSQL);
			if (rsKindE.next()){
				kindCountE = rsKindE.getInt("kindCount");
			}
			rsKindE.close();
			strSQL = "select * from prpcpitemCar where policyno ='"
					+ this.tempPolicyNo + "'";
			ResultSet rsChooseEYears = dbManager.executeQuery(strSQL);
			if(rsChooseEYears.next()){
				if(kindCountE > 0){
					ChooseEYears = rsChooseEYears.getInt("UseYears");//投保投保自燃险年限
					if("8A".equals(useNatureCode)){
						ChooseEJYears = rsChooseEYears.getInt("UseYears");//投保自燃险的家用车使用年限
					}
				}
			}
			rsChooseEYears.close();
			
			//查出投保了指定的哪个附加险、条款
			strSQL = "select * from prpdkind where riskcode = '" + this.riskCode + "' and kindcode in (select kindcode from prpcpitemkind where policyno ='"
				+ this.tempPolicyNo +
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
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='" + this.tempPolicyNo +
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
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='" + this.tempPolicyNo + "'and kindcode in ('X','D2','R')";
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
			strSQL = "select count(*) as kindCount from prpcpitemkind where policyno ='"+this.tempPolicyNo+
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
            

			//基准保费
//			String strPriminum = "select Sum(BenchMarkPremium) as trueSumPremium 　from prpCPitemkind where policyno ='"
//					+ this.tempPolicyNo + "'";
//			ResultSet rsPrimum = dbManager.executeQuery(strPriminum);
//			if (rsPrimum.next()) {
//				this.trueSumPremium = rsPrimum.getDouble("trueSumPremium");//基准保费
//				if (SumPremium!=0&& trueSumPremium != 0
//						&& trueSumPremium!=0) {
//					//modify by zhulei 20051109 净费率需要除以短期费率系数
//					outLayRate = (SumPremium + dblChgPremium)
//							/ trueSumPremium/shortRate*100;//净费率
//				}
//                //注销业务不考虑净费比例
//                if(outLayRate <= 0 ) outLayRate = 100;
//				if (trueSumPremium !=0
//						&& trueSumPremium == 0) {
//					outLayRate = 100;
//				}
//			}
//			//modify by zhulei 20060425 批单不控制净费比例
//			
//            rsPrimum.close();
            outLayRate = 100; //净费率

			//返回是否允许招标信息返回团购车的数量
			String strminus = "select * from PrpMotorcade where contractno =  '"
					+ contractno + "'";
			ResultSet rsminus = dbManager.executeQuery(strminus);
			if (rsminus.next()) {
				permitBidding = rsminus.getString("minusflag");//返回是否允许招标信息
				groupCarSum = rsminus.getInt("carcount");//返回团购车的数量
                //lijibin 2005-11-08 modify 先判断招标标志是否为空
                //if(minusFlag.equals("3"))
                if(permitBidding!=null&&permitBidding.equals("3")){
                	permitBidding = "Y";
                }else{
                	permitBidding = "N";
                }
			} else {
				permitBidding = "000";
			}
            rsminus.close();
            
//			//返回险别
//			String strKindcode = "select * from prpPitemkind where endorseno = '"
//					+ iBusinessNo + "'";
//			ResultSet rsKindcode = dbManager.executeQuery(strKindcode);
//			if (rsKindcode.next()) {
//				riskKind = rsKindcode.getString("kindcode");
//			}
//            rsKindcode.close();

			//招标系数下浮比例
			String strProfitRate = "select ProfitRate  from prpPprofitdetail where  profitcode='C14'  and endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsProfitRate = dbManager.executeQuery(strProfitRate);
			if (rsProfitRate.next()) {
				double biddingDownProportion = rsProfitRate
						.getDouble("ProfitRate");
			}
            rsProfitRate.close();

			//允许保单全单退保天数
            
			String strLogOut = "select * from prpPplan  where  endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsLogOut = dbManager.executeQuery(strLogOut);
			if (rsLogOut.next()) {
				Date plandate = rsLogOut.getDate("plandate");
				Date planstartdate = rsLogOut.getDate("planstartdate");
				if (plandate != null && planstartdate != null) {
					//writeOffDays = (plandate.getTime() - planstartdate.getTime())/ (24 * 60 * 60 * 1000);
				}
			}
            rsLogOut.close();

            //新增设备保额
			String strAmountNew = "select sum(actualvalue) as amount from prpcpcardevice where riskcode='"+this.riskCode+"' and PolicyNo = '"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountNew = dbManager.executeQuery(strAmountNew);
			if (rsAmountNew.next()){
				amountNew = rsAmountNew.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountNew.close();
			
			//返回车痕险保额
			strSQL = "Select * from PrpCpitemkind where riskcode='"+this.riskCode+"' and kindcode = 'L' and  PolicyNo = '"
						+ this.tempPolicyNo + "'";
			ResultSet rsAmountL = dbManager.executeQuery(strSQL);
			if (rsAmountL.next()) {
				amountL = rsAmountL.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountL.close();
			
			//返回交通事故精神损害赔偿责任险保额/交通事故精神损害赔偿责任险每人每次限额
			strSQL = "Select * from PrpCpitemkind where riskcode='"+this.riskCode+"' and kindcode = 'R' and  PolicyNo = '"
						+ this.tempPolicyNo + "'";
			ResultSet rsAmountR = dbManager.executeQuery(strSQL);
			if (rsAmountR.next()) {
				amountR = rsAmountR.getDouble("amount") * BWBexchangeRate;
				amountRPer = rsAmountR.getDouble("unitamount") * BWBexchangeRate;
			}
			rsAmountR.close();
			
			//返回车辆损失险保额
			//保单的金额（可能出现未批改，防止仅取“P”表可能得空值）
			String strAmountA = "SELECT * FROM PrpCpitemkind WHERE KindCode = 'A' AND PolicyNo = '"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountAC = dbManager.executeQuery(strAmountA);
			if (rsAmountAC.next()) {
				amountA = rsAmountAC.getDouble("amount") * BWBexchangeRate;
			}
            rsAmountAC.close();

			//以往批改的最大
			strAmountA = "Select MAX(Amount) AS Amount,MAX(UnitAmount) AS UnitAmount from prpPitemkind where kindcode = 'A' and endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmount = dbManager.executeQuery(strAmountA);
			if (rsAmount.next()) {
				if(rsAmount.getDouble("Amount")!=0){  //lijibin 20050804 add 现场修改:如果数据库中没有值也会有一条0值
				    amountAP = rsAmount.getDouble("Amount") * BWBexchangeRate;
				}
				if(rsAmount.getDouble("unitAmount")!=0){
				    unitAmount = rsAmount.getDouble("unitAmount") * BWBexchangeRate;
				}
			}
            rsAmount.close();
            if (amountAP>amountA){
            	amountA = amountAP;
            }
			amountTemp = 0; //要清空amountTemp，否则会影响下面的计算

			//返回全车盗抢险
			//保单的金额（可能出现未批改，防止仅取“P”表可能得空值）
			String strAmountG = "SELECT * FROM prpCPitemkind WHERE KindCode = 'G' AND PolicyNo = '"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountGC = dbManager.executeQuery(strAmountG);
			if (rsAmountGC.next()) {
				this.amountG = rsAmountGC.getDouble("amount") * BWBexchangeRate;
			}
            rsAmountGC.close();

			//以往批改的最大
			strAmountG = "Select MAX(Amount) AS Amount from prpPitemkind where kindcode = 'G' and endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountGP = dbManager.executeQuery(strAmountG);
			if (rsAmountGP.next()) {
				if(rsAmountGP.getDouble("Amount")!=0){
					amountGP = rsAmountGP.getDouble("Amount") * BWBexchangeRate;
				}
			}
            rsAmountGP.close();
			if (amountGP > amountG) {
				amountG = amountGP;
			}
			amountTemp = 0; //要清空amountTemp，否则会影响下面的计算

//			//返回自燃损失险
//			//保单的金额（可能出现未批改，防止仅取“P”表可能得空值）
//			String strAmountZ = "SELECT * FROM PrpCpitemkind WHERE KindCode = 'Z' AND PolicyNo = '"
//					+ this.tempPolicyNo + "'";
//			ResultSet rsAmountZC = dbManager.executeQuery(strAmountZ);
//			if (rsAmountZC.next()) {
//				amountZ = rsAmountZC.getDouble("amount");
//			}
//            rsAmountZC.close();
//
//			//以往批改的最大
//			strAmountZ = "Select MAX(Amount) AS Amount from prpPitemkind where kindcode = 'Z' and endorseno = '"
//					+ iBusinessNo + "'";
//			ResultSet rsAmountZP = dbManager.executeQuery(strAmountZ);
//			if (rsAmountZP.next()) {
//				if(rsAmountZP.getDouble("Amount")!=0){
//					amountZP = rsAmountZP.getDouble("Amount");
//				}
//			}
//            rsAmountZP.close();
//
//			if (amountZP > amountZ) {
//				amountZ = amountZP;
//			}
//			amountTemp = 0; //要清空amountTemp，否则会影响下面的计算



			//返回第三者责任险
			//保单的金额（可能出现未批改，防止仅取“P”表可能得空值）
			String strAmountB = "SELECT * FROM PrpCpitemkind WHERE KindCode = 'B' AND PolicyNo = '"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountBC = dbManager.executeQuery(strAmountB);
			if (rsAmountBC.next()) {
				amountB = rsAmountBC.getDouble("amount") * BWBexchangeRate;
			}
            rsAmountBC.close();

			//以往批改的最大
			strAmountB = "Select MAX(Amount) AS Amount from prpPitemkind where kindcode = 'B' and endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountBP = dbManager.executeQuery(strAmountB);
			if (rsAmountBP.next()) {
				if(rsAmountBP.getDouble("Amount")!=0){
					amountBP = rsAmountBP.getDouble("Amount") * BWBexchangeRate;
				}
			}
            rsAmountBP.close();

			if (amountBP > amountB) {
				amountB = amountBP;
			}
            //modify by zhangruifeng 20080307 begin reason:需要控制车损险、三者险的净自留额
			//当reinsmode=181 为自留额；reinsmode=182 为附加自留额
			strSQL =  "Select sum(sharerate) as sharerate  From prpPreinsshare Where endorseno='"
				+iBusinessNo+"' And reinsmode in( '181','182')";
			ResultSet rsSharerate = dbManager.executeQuery(strSQL);
			if (rsSharerate.next()) {
				sharerate = rsSharerate.getDouble("sharerate");//客户性质
				this.SuttleAmountA = amountA*sharerate/100;//车损险的净自留额=车损险的保额*我方份额比例
				this.SuttleAmountB = amountB*sharerate/100;//三者险的净自留额=三者险的保额*我方份额比例
			}
			rsSharerate.close();
			amountTemp = 0; //要清空amountTemp，否则会影响下面的计算
			
			//返回车上人员责任险/每座
			//保单的金额（可能出现未批改，防止仅取“P”表可能得空值）
			String strAmountD11 = "SELECT amount FROM PrpCpitemkind WHERE KindCode = 'D11' AND PolicyNo = '"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountD11C = dbManager.executeQuery(strAmountD11);
			if (rsAmountD11C.next()) {
				amountD11 = rsAmountD11C.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountD11C.close();

			//以往批改的最大
			strAmountD11 = "Select amount  from prpPitemkind where kindcode = 'D11' and endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountD11P = dbManager.executeQuery(strAmountD11);
			if (rsAmountD11P.next()) {
				if(rsAmountD11P.getDouble("Amount")!=0){
					amountD1P = rsAmountD11P.getDouble("Amount") * BWBexchangeRate;
				}
			}
			rsAmountD11P.close();

			if (amountD1P > amountD11) {
				amountD11 = amountD1P;
			}
			amountTemp = 0; //要清空amountTemp，否则会影响下面的计算
			
			String strAmountD12 = "SELECT sum(amount)/sum(quantity) as amount FROM PrpCpitemkind WHERE KindCode = 'D12' AND PolicyNo = '"
				+ this.tempPolicyNo + "'";
			ResultSet rsAmountD12C = dbManager.executeQuery(strAmountD12);
			if (rsAmountD12C.next()) {
				amountD12 = rsAmountD12C.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountD12C.close();
	
			//以往批改的最大
			strAmountD12 = "Select MAX(sum(amount)/sum(quantity)) AS Amount from prpPitemkind where kindcode = 'D12' and endorseno = '"
					+ iBusinessNo + "' group by policyno";
			ResultSet rsAmountD12P = dbManager.executeQuery(strAmountD12);
			if (rsAmountD12P.next()) {
				if(rsAmountD12P.getDouble("Amount")!=0){
					amountD1P = rsAmountD12P.getDouble("Amount") * BWBexchangeRate;
				}
			}
			rsAmountD12P.close();
	
			if (amountD1P > amountD12) {
				amountD12 = amountD1P;
			}
			
			//返回车上人员责任险总保额
			String strAmountManSum = "select sum(amount) as amount from PrpCpitemkind where kindcode in ('D11','D12') and PolicyNo='"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountManSum = dbManager.executeQuery(strAmountManSum);
			if (rsAmountManSum.next()){
				AmountManSum = rsAmountManSum.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountManSum.close();
			//以往批改的最大
			strAmountManSum = "select sum(amount) as amount from prpPitemkind where kindcode in ('D11','D12') and endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsAmountManSumP = dbManager.executeQuery(strAmountManSum);
			if (rsAmountManSumP.next()){
				AmountManSumP = rsAmountManSumP.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountManSumP.close();
			
			if (AmountManSumP > AmountManSum){
				AmountManSum = AmountManSumP;
			}
			
			amountTemp = 0; //要清空amountTemp，否则会影响下面的计算
			
			//返回车上货物责任险
			//保单的金额（可能出现未批改，防止仅取“P”表可能得空值）
			String strAmountD2 = "SELECT * FROM PrpCpitemkind WHERE KindCode = 'D2' AND PolicyNo = '"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountD2C = dbManager.executeQuery(strAmountD2);
			if (rsAmountD2C.next()) {
				amountD2 = rsAmountD2C.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountD2C.close();

			//以往批改的最大
			strAmountD2 = "Select MAX(Amount) AS Amount from prpPitemkind where kindcode = 'D2' and endorseno = '"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountD2P = dbManager.executeQuery(strAmountD2);
			if (rsAmountD2P.next()) {
				if(rsAmountD2P.getDouble("Amount")!=0){
					amountD2P = rsAmountD2P.getDouble("Amount") * BWBexchangeRate;
				}
			}
			rsAmountD2P.close();

			if (amountD2P > amountD2) {
				amountD2 = amountD2P;
			}
			
			//随行物品损失责任险保额
			String strAmountW = "SELECT * FROM PrpCpitemkind WHERE KindCode = 'NZ' AND PolicyNo = '"
					+ this.tempPolicyNo + "'";
			ResultSet rsAmountW = dbManager.executeQuery(strAmountW);
			if (rsAmountW.next()) {
				amountW = rsAmountW.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountW.close();
			//以往批改的最大
			strAmountW = "SELECT * FROM prpPitemkind WHERE KindCode = 'NZ' AND PolicyNo = '"
				+ this.tempPolicyNo + "'";
			ResultSet rsAmountWP = dbManager.executeQuery(strAmountW);
			if (rsAmountWP.next()) {
				amountWP = rsAmountWP.getDouble("amount") * BWBexchangeRate;
			}
			rsAmountWP.close();
			if(amountWP > amountW){
				amountW = amountWP;
			}
			
            }
			amountTemp = 0; //要清空amountTemp，否则会影响下面的计算
            /****************非车非意健部分*****************/
			//联(共)保标志
			String strCoinsFlag = "select * from prpPmain where CoinsFlag in ('1','2')  and  endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsCoinsFlag = dbManager.executeQuery(strCoinsFlag);
			if (rsCoinsFlag.next()) {
				unitPolicy = "Y";
			}
            rsCoinsFlag.close();

			//暂保单
			String strSort = "select * from prpPmain where PolicySort = '2'  and  endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsSort = dbManager.executeQuery(strSort);
			if (rsSort.next()) {
				tempPolicy = "Y";
			}
            rsSort.close();

			//每人事故赔偿限额-乘过汇率
			String strLimit01 = "select * from prpPLimit where limittype = '04' and  endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsLimit01 = dbManager.executeQuery(strLimit01);
			if (rsLimit01.next()) {
				this.limitManAcc01 = rsLimit01.getDouble("limitfee")
						* this.exchRate;
			}
            rsLimit01.close();

			//每次事故赔偿限额-乘过汇率
			String strLimit12 = "select * from prpPLimit where limittype = '12' and endorseno = '"
					+ iBusinessNo + "'";
			ResultSet rsLimit12 = dbManager.executeQuery(strLimit12);
			if (rsLimit12.next()) {
				this.limitAcc12 = rsLimit12.getDouble("limitfee")
						* this.exchRate;
			}
            rsLimit12.close();
            
            //add by zhouhui begin 20090625 批改保险期限时，短期费率标志为3时，只能1c级以上才能核过
			
			strShortRateFlag = "SELECT ShortRateFlag FROM PrpCPitemkind WHERE PolicyNo = '"
					+ this.tempPolicyNo + "'";
	        ResultSet rsShortRateFlag = dbManager.executeQuery(strShortRateFlag);
			if (rsShortRateFlag.next()) {
					this.ShortRateFlag = rsShortRateFlag.getString("ShortRateFlag");
			}
			rsShortRateFlag.close();
			//add by zhouhui end 20090625 批改保险期限时，短期费率标志为3时，只能1c级以上才能核过
			
			//xuning gpic @@@@@@@整理部分 start
			//07建筑工程险部分 begin add by xuning gpic 20061012
			if(classCode.equals("07")){//建工险需要控制第三者累计赔偿限额---add by xuning gpic 20061012
				String strThirdLimitSum07= "select * from prpcpLimit where limittype = '11' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsThirdLimitSum07 = dbManager.executeQuery(strThirdLimitSum07);
				if(rsThirdLimitSum07.next()){
				   this.thirdLimitSum07 = rsThirdLimitSum07.getDouble("limitfee") * this.exchRate;  //第三者累计赔偿限额
				
				}
				rsThirdLimitSum07.close();
				String strThirdLimitSum07P= "select max(limitfee) as limitfeeP from prppLimit where limittype = '11' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsThirdLimitSum07P = dbManager.executeQuery(strThirdLimitSum07P);
				if(rsThirdLimitSum07P.next()){
				   this.thirdLimitSum07P = rsThirdLimitSum07P.getDouble("limitfeeP") * this.exchRate;  //第三者累计赔偿限额
				
				}
				rsThirdLimitSum07P.close();
				String strThirdLimitAcc07= "select * from prpcpLimit where limittype = '10' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsThirdLimitAcc07 = dbManager.executeQuery(strThirdLimitAcc07);
				if(rsThirdLimitAcc07.next()){
				   this.thirdLimitAcc07 = rsThirdLimitAcc07.getDouble("limitfee") * this.exchRate;  //第三者累计赔偿限额
				
				}
				rsThirdLimitAcc07.close();
				
				String strThirdLimitAcc07P= "select max(limitfee) as limitfeeP  from prppLimit where limittype = '10' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsThirdLimitAcc07P = dbManager.executeQuery(strThirdLimitAcc07P);
				if(rsThirdLimitAcc07P.next()){
				   this.thirdLimitAcc07P = rsThirdLimitAcc07P.getDouble("limitfeeP") * this.exchRate;  //第三者累计赔偿限额
				
				}
				rsThirdLimitAcc07P.close();
				if(thirdLimitSum07P>thirdLimitSum07){
				  thirdLimitSum07 = thirdLimitSum07P;
				}
				if(thirdLimitAcc07P>thirdLimitAcc07){
					thirdLimitAcc07 = thirdLimitAcc07P;
					}
			}
			if(classCode.equals("10")||classCode.equals("09")){//进出口货物的加成比例-国内货运
				String str10= "select * from prpcpmaincargo where  policyno = '"+tempPolicyNo+"'";
				ResultSet rsplusRate10 = dbManager.executeQuery(str10);
				if(rsplusRate10.next()){
				   this.plusRate = rsplusRate10.getDouble("plusRate");  //加成比例
				   this.plusRate = (this.plusRate - 100) / 100;
				
				}
				rsplusRate10.close();
				
				String strplusRate= "select max(plusRate) as plusRateP from prppmaincargo where  policyno = '"+tempPolicyNo+"'";
				ResultSet rsplusRate10P = dbManager.executeQuery(strplusRate);
				if(rsplusRate10P.next()){
				   this.plusRateP = rsplusRate10P.getDouble("plusRateP");  //加成比例
				   this.plusRateP = (this.plusRateP - 100) / 100;
				}
				rsplusRate10P.close();
				if(plusRateP>plusRate){
					plusRate = plusRateP;
					}
				
				String strPolicyType0902= "select * from prppmain where  policyno = '"+tempPolicyNo+"'";
				ResultSet rsPolicyType0902 = dbManager.executeQuery(strPolicyType0902);
				if(rsPolicyType0902.next()){
				   this.PolicyType0902 = rsPolicyType0902.getString("PolicyType"); 
				   System.out.println("******************BLBusinessEndorseData  PolicyType  ==="+this.PolicyType0902);
				}
				rsPolicyType0902.close();
				//船龄的控制
				if(riskCode.equals("0907")){
					strSQL = "select * from prpcmaincargosub where   policyno = '" + this.tempPolicyNo + "'";
					ResultSet rsshipAge = dbManager.executeQuery(strSQL);
					if (rsshipAge.next()) {
						this.shipAge = rsshipAge.getDouble("shipAge");
					}
					rsshipAge.close();
	
					strSQL = "select * from prppmaincargosub where  policyno = '" + this.tempPolicyNo + "'";
					ResultSet rsshipAgeP = dbManager.executeQuery(strSQL);
					if (rsshipAgeP.next()) {
						this.shipAgeP = rsshipAgeP.getDouble("shipAge");
					}
					rsshipAgeP.close();
					if(shipAgeP>shipAge){
						shipAge = shipAgeP;
						}
				}
				else if(riskCode.equals("1001")){
					String temm;
					strSQL = "select * from prpcpriskvaluat where  riskvaluatcode = '0001' and  policyno = '" + iBusinessNo + "'";
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
				//added by xiongguojun 20090818 货运险起运日期批改 begin
				String strQueryEndorType = "select * from prpphead where  endorseno = '"+iBusinessNo+"'";
				ResultSet rsEndorType = dbManager.executeQuery(strQueryEndorType);
				if(rsEndorType.next()){
				   this.strEndortype = rsEndorType.getString("endortype");  //批改类型
				}
				rsEndorType.close();
				//added by xiongguojun 20090818 货运险起运日期批改 end
			}
		
		/** ***************责任险**************** */	
		if (classCode.equals("15")){
			if (riskCode.equals("1516")){
				//每次事故责任限额
            	strSQL = "select max(limitfee) as limitFee from prpcplimit where policyno='"
						+ this.tempPolicyNo
						+ "' and limittype='02' and limitno in (select itemkindno from prptitemkind where policyno='"
						+ this.tempPolicyNo + "' and kindcode<>'1504500')";
            	ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
            	if(rsLimitAcc12.next()){
            		this.LimitAcc12 = rsLimitAcc12.getDouble("limitFee");
            	}
            	rsLimitAcc12.close();
            	
            	//每次事故每人伤亡责任限额
            	strSQL = "select max(limitfee) as limitFee from prpcplimit where policyno='"
						+ this.tempPolicyNo
						+ "' and limittype='05' and limitno in (select itemkindno from prptitemkind where policyno='"
						+ this.tempPolicyNo + "' and kindcode<>'1504500')";
            	ResultSet reLimitManAcc05 = dbManager.executeQuery(strSQL);
            	if(reLimitManAcc05.next()){
            		this.limitManAcc05 = reLimitManAcc05.getDouble("limitFee");
            	}
            	reLimitManAcc05.close();
            	
				/*
				 * 
				 * //每人责任限额
            	strSQL = "select max(limitfee) as limitFee from prpPlimit where policyno='"
						+ tempPolicyNo + "' and limittype='66'";                	
            	ResultSet rsLimitManAcc01 = dbManager.executeQuery(strSQL);
            	if (rsLimitManAcc01.next()){
            		this.limitManAcc01P = rsLimitManAcc01.getDouble("limitFee");
            	}
            	rsLimitManAcc01.close();
            	strSQL = "select max(limitfee) as limitFee from prpcplimit where policyno = '"
            		+ tempPolicyNo + "' and limittype='66'";
            	ResultSet rsLimitManAcc01P = dbManager.executeQuery(strSQL);
            	if (rsLimitManAcc01P.next()){
            		this.limitManAcc01 = rsLimitManAcc01P.getDouble("limitFee");
            	}
            	rsLimitManAcc01P.close();
            	if (limitManAcc01P>limitManAcc01){
            		limitManAcc01 = limitManAcc01P;
            	}*/
			}
			 if (riskCode.equals("1515")||riskCode.equals("1526")) {
//				strSQL = "select * from prpCPLimit where limittype = '01' and  policyno = '"
//						+ tempPolicyNo + "'";
//				ResultSet rsLimitMan = dbManager.executeQuery(strSQL);
//				if (rsLimitMan.next()) {
//					this.sumAmount = rsLimitMan.getDouble("limitfee")
//							* this.exchRate; // 客户现场修改：增加了汇率计算
//				}
//				rsLimitMan.close();
//				strSQL = "select * from prpPLimit where limittype = '01' and  policyno = '"
//						+ tempPolicyNo + "'";
//				ResultSet rsLimitManP = dbManager.executeQuery(strSQL);
//				if (rsLimitManP.next()) {
//					this.sumAmountC = rsLimitManP.getDouble("limitfee")
//							* this.exchRate; // 客户现场修改：增加了汇率计算
//				}
//				rsLimitManP.close();
//				if(sumAmountC>sumAmount){
//					sumAmountC =sumAmount;
//				}
//				1515,1526每次事故赔偿限额 
				   strSQL = "select limitfee as limitFee from prpplimit where policyno='"
							+ this.tempPolicyNo 
							+ "' and limittype='02' and limitno='1'";
					ResultSet rs1526LimitAcc = dbManager.executeQuery(strSQL);
					blPrpPmain.getData(iBusinessNo);
					//JudicalScope表示范围1.国内 2.世界范围(除美加) 3.世界范围(含美加) 
					if (rs1526LimitAcc.next()) {
						if(blPrpPmain.getSize()>0){
							if("1".equals(blPrpPmain.getArr(0).getJudicalScope())){
								this.LimitAcc1 = rs1526LimitAcc.getDouble("limitFee")* this.exchRate;
							}else if("2".equals(blPrpPmain.getArr(0).getJudicalScope())){
								this.LimitAcc2 = rs1526LimitAcc.getDouble("limitFee")* this.exchRate;
							}else if("3".equals(blPrpPmain.getArr(0).getJudicalScope())){
								this.LimitAcc3 = rs1526LimitAcc.getDouble("limitFee")* this.exchRate;
							}else{
								
							}
							
						}
						
					}
					rs1526LimitAcc.close();
	                  //1515,1526累计赔偿限额 
					   strSQL = "select limitfee as limitFee from prpplimit where policyno='"
								+ this.tempPolicyNo 
								+ "' and limittype='02' and limitno='1'";
						ResultSet rs1526SumAmount = dbManager.executeQuery(strSQL);
						blPrpPmain.getData(iBusinessNo);
						//JudicalScope表示范围1.国内 2.世界范围(除美加) 3.世界范围(含美加) 
						if (rs1526SumAmount.next()) {
							if(blPrpPmain.getSize()>0){
								if("1".equals(blPrpPmain.getArr(0).getJudicalScope())){
									this.SumAmount1 = rs1526SumAmount.getDouble("limitFee")* this.exchRate;
								}else if("2".equals(blPrpPmain.getArr(0).getJudicalScope())){
									this.SumAmount2 = rs1526SumAmount.getDouble("limitFee")* this.exchRate;
								}else if("3".equals(blPrpPmain.getArr(0).getJudicalScope())){
									this.SumAmount3 = rs1526SumAmount.getDouble("limitFee")* this.exchRate;
								}else{
									
								}		
							}							
						}
						rs1526SumAmount.close();
			}
			
			//每人事故赔偿限额
			if(riskCode.equals("1509")){
				String strLimitMan= "select * from prpcpLimit where limittype = '08' and  policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitMan = dbManager.executeQuery(strLimitMan);
				if(rsLimitMan.next()){
				   this.limitManAcc01 =  rsLimitMan.getDouble("limitfee") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitMan.close();
				String strLimitManP= "select max(limitfee) as limitfeeP from prppLimit where limittype = '08' and  policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitManP = dbManager.executeQuery(strLimitManP);
				if(rsLimitManP.next()){
				   this.limitManAcc01P =  rsLimitManP.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitManP.close();
				if(limitManAcc01P>limitManAcc01){
					limitManAcc01 = limitManAcc01P;
					}
			}else if(riskCode.equals("1518")){
				String strLimitMan= "select * from prpcpLimit where limittype = '02' and  policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitMan = dbManager.executeQuery(strLimitMan);
				if(rsLimitMan.next()){
				   this.limitManAcc01 =  rsLimitMan.getDouble("limitfee") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitMan.close();
				String strLimitManP= "select MAX(limitfee) AS limitfeeP from prppLimit where limittype = '02' and  policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitManP = dbManager.executeQuery(strLimitManP);
				if(rsLimitManP.next()){
				   this.limitManAcc01P =  rsLimitManP.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitManP.close();
				if(limitManAcc01P>limitManAcc01){
					limitManAcc01 = limitManAcc01P;
					}
			}else if(riskCode.equals("1506")){
				//货物责任：每次事故责任限额 
		        strSQL = "select sum(limitfee) as limitFee from prpcplimit where policyno='"
					+ this.tempPolicyNo
					+ "' and limittype='02' and limitno in (select itemkindno from prpcpitemkind where policyno='"
					+ this.tempPolicyNo + "' and kindcode='1506000')";
			    ResultSet rs1506LimitCargoAcc = dbManager.executeQuery(strSQL);
			    if (rs1506LimitCargoAcc.next()) {
				this.LimitCargoAcc = rs1506LimitCargoAcc.getDouble("limitFee");
			    }
			    rs1506LimitCargoAcc.close();
			    
               //1506第三者责任：每次事故责任限额 
		        strSQL = "select sum(limitfee) as limitFee from prpcplimit where policyno='"
					+ this.tempPolicyNo
					+ "' and limittype='02' and limitno in (select itemkindno from prpcpitemkind where policyno='"
					+ this.tempPolicyNo + "' and kindcode='1503000' and itemcode='0001')";
			    ResultSet rs1506LimitThirdAccA = dbManager.executeQuery(strSQL);
			    if (rs1506LimitThirdAccA.next()) {
				this.LimitThirdAcc = rs1506LimitThirdAccA.getDouble("limitFee");
			    }
			    rs1506LimitThirdAccA.close();
			    
               //1506除污费用：每次事故赔偿限额
		        strSQL = "select sum(limitfee) as limitFee from prpcplimit where policyno='"
					+ this.tempPolicyNo
					+ "' and limittype='02' and limitno in (select itemkindno from prpcpitemkind where policyno='"
					+ this.tempPolicyNo + "' and kindcode='1503000' and itemcode='0002')";
			    ResultSet rs1506LimitThirdAcc2 = dbManager.executeQuery(strSQL);
			    if (rs1506LimitThirdAcc2.next()) {
				this.LimitThirdAcc2 = rs1506LimitThirdAcc2.getDouble("limitFee");
			    }
			    rs1506LimitThirdAcc2.close();
			 //modeify by zhangruifeng 20071220 begin reason : 控制1505险种的每人赔偿限额
			    
                //added by xiongguojun 20090716 1506核乏料运输：累计责任限额 begin
		        strSQL = "select sum(limitfee) as limitFee from prpcplimit where policyno='"
					+ this.tempPolicyNo
					+ "' and limittype='03' and limitno in (select itemkindno from prpcpitemkind where policyno='"
					+ this.tempPolicyNo + "' and kindcode='1503000' and itemcode='0004')";
			    ResultSet rs1506LimitThirdAcc4 = dbManager.executeQuery(strSQL);
			    if (rs1506LimitThirdAcc4.next()) {
				this.LimitThirdAcc4 = rs1506LimitThirdAcc4.getDouble("limitFee");
			    }
			    rs1506LimitThirdAcc4.close();
			    //added by xiongguojun 20090716 1506核乏料运输：累计责任限额 end
			}else if(riskCode.equals("1505")){
				//added by gengxiaobo begin 20080604 增加最大车累计限额,调整每次事故赔偿限额取值。
				//modify by yanglibo begin 20090701 reason: 入参错误
				strSQL 	= "select PreTurnOver,StaffCount from PrpCPmainLiab where policyno='"
					+ this.tempPolicyNo
					+ "'";
				ResultSet rsPreTurnOver1505 = dbManager.executeQuery(strSQL);
		
				if (rsPreTurnOver1505.next()) {
					this.douPreTurnOver1505 = rsPreTurnOver1505.getDouble("PreTurnOver");
					this.douStaffCount1505 = rsPreTurnOver1505.getDouble("StaffCount");
				}
				//modify by yanglibo end 20090701 reason: 入参错误
			
               //每人赔偿限额、责任限额
		        strSQL = "select sum(limitfee) as limitFee from prpcplimit where policyno='"
					+ this.tempPolicyNo
					+ "' and limittype='04'";
			    ResultSet rs1505LimitManAcc01 = dbManager.executeQuery(strSQL);
			    if (rs1505LimitManAcc01.next()) {
				this.limitManAcc01 = rs1505LimitManAcc01.getDouble("limitFee");
			    }
			    rs1505LimitManAcc01.close();
               //modeify by zhangruifeng 20071220 end 
			    
			    //added by LanNing begin 20080421 1505每次事故赔偿限额
			    strSQL = "select limitFee from prpcplimit where policyno='"
					+ this.tempPolicyNo
					+ "' and limittype='02'";
			    ResultSet rsLimit02Fee1505 = dbManager.executeQuery(strSQL);
			    if (rsLimit02Fee1505.next()) {
				    this.Limit02Fee1505 = rsLimit02Fee1505.getDouble("limitFee");
			    }
			    rs1505LimitManAcc01.close();
			    //added by LanNing end 20080421 1505每次事故赔偿限额	    
			    
				
				if(this.douPreTurnOver1505!=0){
					this.Limit02Fee1505 = this.Limit02Fee1505/this.douPreTurnOver1505;
				}			
				
				strSQL = "select sum(limitfee) as limitFee from prpcplimit where policyno='"
					+ iBusinessNo
					+ "' and limittype='03'";
				ResultSet rsLimit03Fee1505 = dbManager.executeQuery(strSQL);
				if (rsLimit03Fee1505.next()) {
					this.Limit03Fee1505 = rsLimit03Fee1505.getDouble("limitFee");
				}						
				rsLimit03Fee1505.close();
				
				strSQL = "select max(Capacity) as Capacity from prpcpitemDevice where policyno='"
					+ iBusinessNo
					+ "'";
				ResultSet rsmaxCapacity1505 = dbManager.executeQuery(strSQL);
				if (rsmaxCapacity1505.next()) {
					this.maxCapacity1505 = rsmaxCapacity1505.getDouble("Capacity");
				}						
				rsmaxCapacity1505.close();
				
				if(this.maxCapacity1505!=0&&this.douStaffCount1505!=0){
					this.Limit03Fee1505 = this.maxCapacity1505*this.Limit03Fee1505/this.douPreTurnOver1505;
				}
				//added by gengxiaobo end 20080604 增加最大车累计限额,调整每次事故赔偿限额取值。			    
			    

			}else{
				String strLimitMan= "select * from prpcpLimit where limittype = '04' and  policyno = '"+this.tempPolicyNo+"'";
				ResultSet rsLimitMan = dbManager.executeQuery(strLimitMan);
				if(rsLimitMan.next()){
				   this.limitManAcc01 =  rsLimitMan.getDouble("limitfee") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitMan.close();
				String strLimitManP= "select MAX(limitfee) AS limitfeeP from prpcpLimit where limittype = '04' and  policyno = '"+this.tempPolicyNo+"'";
				ResultSet rsLimitManP = dbManager.executeQuery(strLimitManP);
				if(rsLimitManP.next()){
				   this.limitManAcc01P =  rsLimitManP.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitManP.close();
				if(limitManAcc01P>limitManAcc01){
					limitManAcc01 = limitManAcc01P;
					}
			}
			//每次事故赔偿限额
			if(riskCode.equals("1509")||riskCode.equals("1507")){
				String strLimitAcc= "select * from prpcpLimit where limittype = '01' and policyno = '"+this.tempPolicyNo+"'";
				ResultSet rsLimitAcc = dbManager.executeQuery(strLimitAcc);
				if(rsLimitAcc.next()){
				   this.limitAcc12 = rsLimitAcc.getDouble("limitfee") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitAcc.close();
				String strLimitAccP= "select MAX(limitfee) AS limitfeeP  from prppLimit where limittype = '01' and policyno = '"+this.tempPolicyNo+"'";
				ResultSet rsLimitAccP = dbManager.executeQuery(strLimitAccP);
				if(rsLimitAccP.next()){
				   this.limitAcc12P = rsLimitAccP.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitAccP.close();
				if(limitAcc12P>limitAcc12){
					limitAcc12 = limitAcc12P;
					}
			}else if ("1501,1504,1505,1515,1516,1526,1547,1531".indexOf(riskCode)>0){
				String strLimitAcc= "select * from prpcpLimit where limittype = '02' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitAcc = dbManager.executeQuery(strLimitAcc);
				if(rsLimitAcc.next()){
				   this.limitAcc12 = rsLimitAcc.getDouble("limitfee") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitAcc.close();
				
				String strLimitAccP= "select MAX(limitfee)  AS limitfeeP from prpcpLimit where limittype = '02' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitAccP = dbManager.executeQuery(strLimitAccP);
				if(rsLimitAccP.next()){
				   this.limitAcc12P = rsLimitAccP.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitAccP.close();
				if(limitAcc12P>limitAcc12){
					limitAcc12 = limitAcc12P;
				}
				String strLimitManAcc05 = "select * from prpcpLimit where limittype = '05' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitManAcc05 = dbManager.executeQuery(strLimitManAcc05);
				if(rsLimitManAcc05.next()){
					this.limitManAcc05 = rsLimitManAcc05.getDouble("limitfee") * this.exchRate;
				}
				rsLimitManAcc05.close();
				String strLimitManAcc05P = "select MAX(limitfee)  AS limitfeeP from prpcpLimit where limittype = '05' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitManAcc05P = dbManager.executeQuery(strLimitManAcc05P);
				if(rsLimitManAcc05P.next()){
					this.limitManAcc05P = rsLimitManAcc05P.getDouble("limitfeeP") * this.exchRate;
				}
				rsLimitManAcc05P.close();
				if(limitManAcc05P>limitManAcc05){
					limitManAcc05 = limitManAcc05P;				
				}
			}else if ("1501,1504,1515,1518,1526".indexOf(riskCode)>0){
				String strLimitAcc03 = "select * from prpcpLimit where limittype = '37' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitAcc03 = dbManager.executeQuery(strLimitAcc03);
				if(rsLimitAcc03.next()){
					this.LimitAcc03 = rsLimitAcc03.getDouble("limitfee") * this.exchRate;
				}
				rsLimitAcc03.close();
				String strLimitAcc03P = "select MAX(limitfee)  AS limitfeeP from prpcpLimit where limittype = '37' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitAcc03P = dbManager.executeQuery(strLimitAcc03P);
				if(rsLimitAcc03P.next()){
					this.LimitAcc03P = rsLimitAcc03P.getDouble("limitfeeP") * this.exchRate;
				}
				rsLimitAcc03P.close();
				if(LimitAcc03P>LimitAcc03){
					LimitAcc03 = LimitAcc03P;
				}
			}else if (riskCode.equals("1523")){
				//累计赔偿限额
				strSQL = "select limitfee as limitFee from prpcplimit where policyno='"
						+ this.tempPolicyNo
						+ "' and limittype='03' and limitno in (select itemkindno from prpcpitemkind where policyno='"
						+ this.tempPolicyNo + "' and kindcode='1506400')";
				ResultSet rs1523SumAmount = dbManager.executeQuery(strSQL);
				if (rs1523SumAmount.next()) {
					this.SumAmount = rs1523SumAmount.getDouble("limitFee");
				}
				rs1523SumAmount.close();
			
	            //除污费用每次事故赔偿限额
				strSQL = "select sum(limitfee) as limitFee from prpcplimit where policyno='"
					+ this.tempPolicyNo
					+ "' and limittype='02' and limitno in (select itemkindno from prpcpitemkind where policyno='"
					+ this.tempPolicyNo + "' and kindcode='1507000' and itemcode='001')";
				ResultSet rs1523LimitThirdAcc2 = dbManager.executeQuery(strSQL);
			    if (rs1523LimitThirdAcc2.next()) {
				this.LimitThirdAcc2 = rs1523LimitThirdAcc2.getDouble("limitFee");
			    }
			    rs1523LimitThirdAcc2.close();
			    
			    //附加第三者责任：每次事故责任限额
				strSQL = "select limitfee as limitFee from prpPlimit where policyno='"
						+ this.tempPolicyNo
						+ "' and limittype='02' and limitno in (select itemkindno from prpPitemkind where policyno='"
						+ this.tempPolicyNo + "' and kindcode='1507000' and itemcode='0002')";
				ResultSet rs1523LimitThirdAcc3 = dbManager.executeQuery(strSQL);
				if (rs1523LimitThirdAcc3.next()) {
					this.LimitThirdAccB = rs1523LimitThirdAcc3.getDouble("limitFee");
				}
				rs1523LimitThirdAcc3.close();				
			}
			//add by zhouhui 1548险种 每次事故责任限额和累计责任限额的权限控制 begin
			else if (riskCode.equals("1548")||riskCode.equals("1531") || riskCode.equals("1516")){ //add by zhyi 1515 fubon-1955每次事故責任限額
				String strlimitAmount02= "select MAX(limitfee)  AS limitfeeP from prpcpLimit where limittype = '02' and policyno = '"+tempPolicyNo+"'";
				ResultSet rslimitAmount02 = dbManager.executeQuery(strlimitAmount02);
				if(rslimitAmount02.next()){
				   this.limitAmount02 = rslimitAmount02.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rslimitAmount02.close();
				String strlimitAmount03= "select MAX(limitfee)  AS limitfeeP from prpcpLimit where limittype = '03' and policyno = '"+tempPolicyNo+"'";
				ResultSet rslimitAmount03 = dbManager.executeQuery(strlimitAmount03);
				if(rslimitAmount03.next()){
				   this.limitAmount03 = rslimitAmount03.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rslimitAmount03.close();
			}
			else if (riskCode.equals("1532")){
				//每次事故赔偿限额
				String strLimitAcc= "select * from prpcpLimit where limittype = '02' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitAcc = dbManager.executeQuery(strLimitAcc);
				if(rsLimitAcc.next()){
				   this.limitAcc12 = rsLimitAcc.getDouble("limitfee") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitAcc.close();
				
				String strLimitAccP= "select MAX(limitfee)  AS limitfeeP from prpcpLimit where limittype = '02' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitAccP = dbManager.executeQuery(strLimitAccP);
				if(rsLimitAccP.next()){
				   this.limitAcc12P = rsLimitAccP.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitAccP.close();
				if(limitAcc12P>limitAcc12){
					limitAcc12 = limitAcc12P;
				}
				//累计赔偿限额
				String strlimitAmount03= "select MAX(limitfee)  AS limitfeeP from prpcpLimit where limittype = '03' and policyno = '"+tempPolicyNo+"'";
				ResultSet rslimitAmount03 = dbManager.executeQuery(strlimitAmount03);
				if(rslimitAmount03.next()){
				   this.limitAmount03 = rslimitAmount03.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rslimitAmount03.close();
			}
			//add by zhouhui 1548险种 每次事故责任限额和累计责任限额的权限控制 begin
			else{
				String strLimitAcc= "select * from prpcpLimit where limittype = '12' and policyno = '"+this.tempPolicyNo+"'";
				ResultSet rsLimitAcc = dbManager.executeQuery(strLimitAcc);
				if(rsLimitAcc.next()){
				   this.limitAcc12 = rsLimitAcc.getDouble("limitfee") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitAcc.close();
				String strLimitAccP= "select MAX(limitfee) AS limitfeeP from prpcpLimit where limittype = '12' and policyno = '"+tempPolicyNo+"'";
				ResultSet rsLimitAccP = dbManager.executeQuery(strLimitAccP);
				if(rsLimitAccP.next()){
				   this.limitAcc12P = rsLimitAccP.getDouble("limitfeeP") * this.exchRate;  //客户现场修改：增加了汇率计算
				}
				rsLimitAccP.close();
				if(limitAcc12P>limitAcc12){
					limitAcc12 = limitAcc12P;
					}
			}
			//added by xiongguojun 20091105 增加产品延长保修服务合同责任保险15980001批改权限 begin
			if ("1598".equals(this.riskCode)) {
				String strLimitAcc= "select * from prpcpLimit where limittype = '02' and policyno = '"+this.tempPolicyNo+"'";
				ResultSet rsLimitAcc = dbManager.executeQuery(strLimitAcc);
				if(rsLimitAcc.next()){
				   this.LimitAcc12 = rsLimitAcc.getDouble("limitfee");
				}
				rsLimitAcc.close();
				System.out.println("15980001LimitAcc12===="+this.LimitAcc12);
			}
			//added by xiongguojun 20091105 增加产品延长保修服务合同责任保险15980001批改权限 end
		}
		/** **********保证保险部分************ */
		if (classCode.equals("22")) {
			if("2201".equals(riskCode)){
				//累计赔偿限额
				strSQL = "select Max(limitfee) limitfee from prpcpLimit where limittype = '01' and policyno = '"+this.tempPolicyNo+"'";
				 ResultSet rsSumAmount1 = dbManager.executeQuery(strSQL);
				 if (rsSumAmount1.next()) {
					 this.SumAmount = rsSumAmount1.getDouble("limitfee") * BWBexchangeRate; 
				 }
				 rsSumAmount1.close();
				 //每次事故赔偿限额
				 strSQL = "select Max(limitfee) limitfee from prpcpLimit where limittype = '02' and policyno = '"+this.tempPolicyNo+"'";
				 ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
				 if (rsLimitAcc12.next()) {
					 this.LimitAcc12 = rsLimitAcc12.getDouble("limitfee") * BWBexchangeRate; 
				 }
				 rsLimitAcc12.close();
				 //每次事故每人赔偿限额
				 strSQL = "select Max(limitfee) limitfee from prpcpLimit where limittype = '04' and policyno = '"+this.tempPolicyNo+"'";
				 ResultSet rsLimitManAcc01 = dbManager.executeQuery(strSQL);
				 if (rsLimitManAcc01.next()) {
					 this.limitManAcc01 = rsLimitManAcc01.getDouble("limitfee") * BWBexchangeRate; 
				 }
				 rsLimitManAcc01.close();
			}
		}
		
            //组合险
			if(classCode.equals("23")){
				if(riskCode.equals("2351")||riskCode.equals("2352") ||riskCode.equals("2355")){
					//每人保险金额
					strSQL = "select sum(sumamount) as sumamount from prpcpmain where policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsSumAmountP = dbManager.executeQuery(strSQL);
					if(rsSumAmountP.next()){
						SumAmountPer = (rsSumAmountP.getDouble("sumamount")/this.sumquantity) * BWBexchangeRate;
					}
					rsSumAmountP.close();
				}
				if(riskCode.equals("2351") ||riskCode.equals("2355")){
					//意外伤害医疗
					strSQL = "select sum(amount) as sumAmount9000447 from prpcpitemkind where kindcode='9000447' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsSumAmount9000447 = dbManager.executeQuery(strSQL);
					if(rsSumAmount9000447.next()){
						this.sumAmountYL = rsSumAmount9000447.getDouble("sumAmount9000447") * BWBexchangeRate;
					}
					rsSumAmount9000447.close();
				}
				if(riskCode.equals("2352")){
					//房屋及附属设备保险金额/每户
					strSQL = "select sum(amount) as sumAmount03002003 from prpcpitemkind where kindcode='0300200' and itemcode='0003' and policyno='"
						+ this.tempPolicyNo + "'";
					ResultSet rsSumAmount03002003 = dbManager.executeQuery(strSQL);
					if(rsSumAmount03002003.next()){
						amountPer03010001 = (rsSumAmount03002003.getDouble("sumAmount03002003")/this.sumquantity) * BWBexchangeRate;
					}
					rsSumAmount03002003.close();
					//室内装潢保险金额/每户
					strSQL = "select sum(amount) as sumAmount03002004 from prpcpitemkind where kindcode='0300200' and itemcode='0004' and policyno='"
						+ this.tempPolicyNo + "'";
					ResultSet rsSumAmount03002004 = dbManager.executeQuery(strSQL);
					if(rsSumAmount03002004.next()){
						amountPer03010002 = (rsSumAmount03002004.getDouble("sumAmount03002004")/this.sumquantity) * BWBexchangeRate;
					}
					rsSumAmount03002004.close();
					//附加盗抢保险/每户
					strSQL = "select sum(amount) as sumAmount9000452 from prpcpitemkind where kindcode='9000452' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsSumAmount9000452 = dbManager.executeQuery(strSQL);
					if(rsSumAmount9000452.next()){
						amountPer9000452 = (rsSumAmount9000452.getDouble("sumAmount9000452")/this.sumquantity) * BWBexchangeRate;
					}
					//意外伤害医疗
					strSQL = "select sum(amount) as sumAmount9000448 from prpcpitemkind where kindcode='9000448' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsSumAmount9000448 = dbManager.executeQuery(strSQL);
					if(rsSumAmount9000448.next()){
						this.sumAmountYL = rsSumAmount9000448.getDouble("sumAmount9000448") * BWBexchangeRate;
					}
					rsSumAmount9000448.close();
				}
				if(riskCode.equals("2353")){
					//室内装潢/家用电器/衣物床上用品/家具及其他保险金额/每户
					strSQL = "select sum(amount) as AmountPer0300200 from prpcpitemkind where kindcode='0300200' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsAmountPer0300200 = dbManager.executeQuery(strSQL);
					if(rsAmountPer0300200.next()){
						AmountPer = (rsAmountPer0300200.getDouble("AmountPer0300200")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmountPer0300200.close();
					//附加居家责任保险
					strSQL = "select sum(amount) as amountPer9000449 from prpcpitemkind where kindcode='9000449' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsAmount9000449 = dbManager.executeQuery(strSQL);
					if(rsAmount9000449.next()){
						amountPer9000449 = (rsAmount9000449.getDouble("amountPer9000449")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000449.close();
					//附加家庭意外骨折医疗保险
					strSQL = "select sum(amount) as amountPer9000451 from prpcpitemkind where kindcode='9000451' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsAmount9000451 = dbManager.executeQuery(strSQL);
					if(rsAmount9000451.next()){
						amountPer9000451 = (rsAmount9000451.getDouble("amountPer9000451")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000451.close();
					//附加盗抢保险
					strSQL = "select sum(amount) as amountPer9000452 from prpcpitemkind where kindcode='9000452' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsAmount9000452 = dbManager.executeQuery(strSQL);
					if(rsAmount9000452.next()){
						amountPer9000452 = (rsAmount9000452.getDouble("amountPer9000452")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000452.close();
					//附加家用电器用电安全保险
					strSQL = "select sum(amount) as amountPer9000453 from prpcpitemkind where kindcode='9000453' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsAmount9000453 = dbManager.executeQuery(strSQL);
					if(rsAmount9000453.next()){
						amountPer9000453 = (rsAmount9000453.getDouble("amountPer9000453")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000453.close();
					//附加管道破裂及水渍保险
					strSQL = "select sum(amount) as amountPer9000454 from prpcpitemkind where kindcode='9000454' and policyno='"+ this.tempPolicyNo + "'";
					ResultSet rsAmount9000454 = dbManager.executeQuery(strSQL);
					if(rsAmount9000454.next()){
						amountPer9000454 = (rsAmount9000454.getDouble("amountPer9000454")/this.sumquantity) * BWBexchangeRate;
					}
					rsAmount9000454.close();
					//附加家庭成员屋内意外伤害身故、残疾保险金
					strSQL = "select sum(amount) as amountPer9000455 from prpcpitemkind where kindcode='9000455' and policyno='"+ this.tempPolicyNo + "'";
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
					   strSQL = "SELECT sum(Amount) AS sumAmount2300200 FROM PrpcpitemKind WHERE policyno = '"
						   + this.tempPolicyNo +"' and Kindcode in ('2300200','2300300','2301100')"; //公路财产损失保险
					   ResultSet rsSumAmount2300200 = dbManager.executeQuery(strSQL);
					   if (rsSumAmount2300200.next()) {
					   this.sumAmount2300200 = rsSumAmount2300200.getDouble("sumAmount2300200") * BWBexchangeRate;
					   }
					   rsSumAmount2300200.close();
					   
					   strSQL = "SELECT sum(Amount) AS sumAmount2300400 FROM PrpcpitemKind WHERE policyno='"
					    	+ this.tempPolicyNo  + "' and Kindcode ='2300400'"; //公众责任保险
				       ResultSet rsSumAmount2300400 = dbManager.executeQuery(strSQL);
				       if (rsSumAmount2300400.next()) {
				       this.sumAmount2300400 = rsSumAmount2300400.getDouble("sumAmount2300400") * BWBexchangeRate;
				       }
				       rsSumAmount2300400.close();
				       
				       strSQL = "SELECT sum(Amount) AS sumAmount2300500 FROM PrpcpitemKind WHERE policyno='"
					    	+ this.tempPolicyNo  + "' and Kindcode ='2300500'"; //雇主责任保险
				       ResultSet rsSumAmount2300500 = dbManager.executeQuery(strSQL);
				       if (rsSumAmount2300500.next()) {
				       this.sumAmount2300500 = rsSumAmount2300500.getDouble("sumAmount2300500") * BWBexchangeRate;
				       }
				       rsSumAmount2300500.close();
				       
				       strSQL = "SELECT MAX(UnitAmount) AS limit2300500 FROM PrpcpitemKind WHERE policyno='"
					    	+ this.tempPolicyNo + "' and Kindcode ='2300500'"; //雇主责任保险每人每次事故赔偿限额
				       ResultSet rsLimit2300500 = dbManager.executeQuery(strSQL);
				       if (rsLimit2300500.next()) {
				       this.limit2300500 = rsLimit2300500.getDouble("limit2300500") * BWBexchangeRate;
				       }
				       rsLimit2300500.close();		
				       
				       strSQL = "SELECT sum(Amount) AS sumAmount2300600 FROM PrpcpitemKind WHERE policyno='"
					    	+ this.tempPolicyNo  + "' and Kindcode ='2300600'"; //现金保险
				       ResultSet rsSumAmount2300600 = dbManager.executeQuery(strSQL);
				       if (rsSumAmount2300600.next()) {
				       this.sumAmount2300600 = rsSumAmount2300600.getDouble("sumAmount2300600") * BWBexchangeRate;
				       }
				       rsSumAmount2300600.close();
					}
				//add by gengxiaobo 20080326 起重机械综合保险高级核保条件
				if(riskCode.equals("2313")){
					   strSQL = "SELECT Max(Amount) AS sumAmount2301500 FROM PrpcpitemKind WHERE policyno = '"
						   + this.tempPolicyNo +"' and Kindcode ='2301500'"; //财产损失保险
					   ResultSet rsSumAmount2301500 = dbManager.executeQuery(strSQL);
					   if (rsSumAmount2301500.next()) {
					   this.sumAmount2301500 = rsSumAmount2301500.getDouble("sumAmount2301500") * BWBexchangeRate;
					   }
					   rsSumAmount2301500.close();
					   
					   strSQL = "SELECT sum(Amount) AS sumAmount2301600 FROM PrpcpitemKind WHERE policyno='"
					    	+ this.tempPolicyNo  + "' and Kindcode ='2301600'"; //第三者责任保险
				       ResultSet rsSumAmount2301600 = dbManager.executeQuery(strSQL);
				       if (rsSumAmount2301600.next()) {
				       this.sumAmount2301600 = rsSumAmount2301600.getDouble("sumAmount2301600") * BWBexchangeRate;
				       }
				       rsSumAmount2301600.close();
				       
				       strSQL = "SELECT sum(Amount) AS sumAmount2301800 FROM PrpcpitemKind WHERE policyno='"
					    	+ this.tempPolicyNo  + "' and Kindcode ='2301800'"; //雇主责任保险
				       ResultSet rsSumAmount2301800 = dbManager.executeQuery(strSQL);
				       if (rsSumAmount2301800.next()) {
				       this.sumAmount2301800 = rsSumAmount2301800.getDouble("sumAmount2301800") * BWBexchangeRate;
				       }
				       rsSumAmount2301800.close();    
					}
				if(riskCode.equals("2310")){
					strSQL = "SELECT sum(Amount) AS sumAmount0300100 FROM PrpcpitemKind WHERE policyno='"
				    	+ this.tempPolicyNo + "' and Kindcode ='0300100'"; //财产损失保险
				   ResultSet rsSumAmount0300100 = dbManager.executeQuery(strSQL);
				   if (rsSumAmount0300100.next()) {
				   this.sumAmount0300100 = rsSumAmount0300100.getDouble("sumAmount0300100") * BWBexchangeRate;
				   }
				   rsSumAmount0300100.close();
					   
				}
				if(riskCode.equals("2315")){
					 //每次事故赔偿限额
					 strSQL = "select Max(limitfee) limitfee from prpcplimit where limittype = '02' and  policyno = '"
						 + this.tempPolicyNo + "'";
					 ResultSet rsLimitAcc12 = dbManager.executeQuery(strSQL);
					 if (rsLimitAcc12.next()) {
						 this.LimitAcc12 = rsLimitAcc12.getDouble("limitfee") * BWBexchangeRate; 
					 }
					 rsLimitAcc12.close();
					 //每次事故每人人身伤亡赔偿限额
					 strSQL = "select Max(limitfee) limitfee from prpcplimit where limittype = '36' and  policyno = '"
						 + this.tempPolicyNo + "'";
					 ResultSet rsLimitManAcc05 = dbManager.executeQuery(strSQL);
					 if (rsLimitManAcc05.next()) {
						 this.limitManAcc05 = rsLimitManAcc05.getDouble("limitfee") * BWBexchangeRate;
					 }
					 rsLimitManAcc05.close();
					 //每次事故财产赔偿限额
					 strSQL = "select Max(limitfee) limitfee from prpcplimit where limittype='37' and  policyno='"
							+ this.tempPolicyNo + "'";
						ResultSet rsLimitAcc03 = dbManager.executeQuery(strSQL);
						if (rsLimitAcc03.next()){
							this.LimitAcc03 = rsLimitAcc03.getDouble("limitfee") * this.exchRate;
						}
					rsLimitAcc03.close();
					//企财综合险保额
					strSQL = "SELECT sum(Amount) AS sumAmount0300100 FROM PrpcpitemKind WHERE policyno='"
					    	+ this.tempPolicyNo + "' and Kindcode ='0102700'";
				   ResultSet rsSumAmount0300100 = dbManager.executeQuery(strSQL);
				   if (rsSumAmount0300100.next()) {
				   this.sumAmount0300100 = rsSumAmount0300100.getDouble("sumAmount0300100") * BWBexchangeRate;
				   }
				   rsSumAmount0300100.close();
				}
			}
			
			//意健险
	        if(this.classCode.equals("27"))
	        {
	        	String strUnitAmount = "SELECT MAX(UnitAmount) AS UnitAmount FROM PrpCPitemkind WHERE PolicyNo = '"
					+ this.tempPolicyNo + "'";
	        	ResultSet rsUnitAmountC = dbManager.executeQuery(strUnitAmount);
				if (rsUnitAmountC.next()) {
					this.SumAmountPer = rsUnitAmountC.getDouble("UnitAmount") * BWBexchangeRate;
				}
                rsUnitAmountC.close();
				if (this.riskCode.equals("2701")) {//人身意外伤害保险
					strSQL = "select flag from prpdcode  where codetype ='OccupationCode' and codecode in "
							+ "(select OccupationCode from prpCPinsured WHERE  InsuredFlag ='1' and PolicyNo='"
							+ this.tempPolicyNo + "')";
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
				strSQL = "SELECT * FROM PrpCPmain WHERE PolicyNo='"
						+ this.tempPolicyNo + "'";
				ResultSet rsUnit = dbManager.executeQuery(strSQL);
				String strPolicyType = "";
				if (rsUnit.next())
					strPolicyType = rsUnit.getString("PolicyType");
				rsUnit.close();
				if ("02".equals(strPolicyType)) {  //团单
					strSQL = "select * from prpCPmaincasualty where  PolicyNo = '"
							+ this.tempPolicyNo + "'";
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
        	
				//获取年龄范围
				//modify by zhulei 20050831 prptinsured应该是prpPinsured！！
//				String strAge = "select age from prpPinsurednature where endorseno in (select endorseno from prpPinsured where insuredtype in ('1','2')) and endorseno = '"
//						+ tempPolicyNo + "'";
//				ResultSet rsAge = dbManager.executeQuery(strAge);
//				while (rsAge != null && rsAge.next()) {
//					ageScope.add(rsAge.getString("age"));
//				}
//	            rsAge.close();
  	        	//保单的金额（可能出现未批改，防止仅取“P”表可能得空值）


  				//以往批改的最大
//	            strUnitAmount = "SELECT MAX(UnitAmount) AS UnitAmount FROM PrpPitemKind WHERE PolicyNo='"+tempPolicyNo+"'";
//	            ResultSet rsUnitAmountP = dbManager.executeQuery(strUnitAmount);
//	            if(rsUnitAmountP.next())
//	            {
//	              this.unitAmount = rsUnitAmountP.getDouble("UnitAmount");
//	            }
//                rsUnitAmountP.close();
//
//				if (unitAmountC > unitAmount) {
//					unitAmount = unitAmountC;
//				}
//				amountTemp = 0; //要清空amountTemp，否则会影响下面的计算

               //add by zhulei begin 20051218 意健险PML
//                String strPML = "SELECT * FROM PrpPdangerUnit WHERE EndorseNo='" + tempPolicyNo + "'";
//                ResultSet rsPML = dbManager.executeQuery(strPML);
//                if(rsPML.next())
//                {
//                   this.dbPML = rsPML.getDouble("Spevalue");
//                }
                //add by zhulei end 20051218 意健险PML
	        }
	        
	        //added by LanNing begin 20080225 投资金产品
			if(this.classCode.equals("29")){
				strSQL = "SELECT Investment FROM prpcpmaininvest where policyno='" + this.tempPolicyNo + "'";
				ResultSet rsInvest = dbManager.executeQuery(strSQL);
				if (rsInvest.next()) {
					this.Investment = rsInvest.getDouble("Investment");
				}
				rsInvest.close();
			}
			//added by LanNing end 20080225 投资金产品
			
			//**********************************************综合险部分*************************************************************
			if(this.classCode.equals("30")){
				//取3001险种的险别
				if("3001".equals(this.riskCode)){
					strSQL = "SELECT Amount AS SumAmount300101 FROM PrpcpitemKind where policyno='" + this.tempPolicyNo + "' and kindcode='3001001'"
			          	+ " and itemcode = '0001'";
					ResultSet rsSumAmount300101 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300101.next()) {
						this.SumAmount300101 = (rsSumAmount300101.getDouble("SumAmount300101")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300101.close();
					
					strSQL = "SELECT Amount AS SumAmount300102 FROM PrpcpitemKind where policyno='" + this.tempPolicyNo + "' and kindcode='3001001'"
		          	+ " and itemcode = '0002'";
					ResultSet rsSumAmount300102 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300102.next()) {
						this.SumAmount300102 = (rsSumAmount300102.getDouble("SumAmount300102")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300102.close();
				
					strSQL = "SELECT Amount AS SumAmount300103 FROM PrpcpitemKind where policyno='" + this.tempPolicyNo + "' and kindcode='3001001'"
		          	+ " and itemcode = '0003'";
					ResultSet rsSumAmount300103 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300103.next()) {
						this.SumAmount300103 = (rsSumAmount300103.getDouble("SumAmount300103")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300103.close();
			
					strSQL = "SELECT Amount AS SumAmount300104 FROM PrpcpitemKind where policyno='" + this.tempPolicyNo + "' and kindcode='3001001'"
		          	+ " and itemcode = '0004'";
					ResultSet rsSumAmount300104 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300104.next()) {
						this.SumAmount300104 = (rsSumAmount300104.getDouble("SumAmount300104")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300104.close();
		
					strSQL = "SELECT Amount AS SumAmount300105 FROM PrpcpitemKind where policyno='" + this.tempPolicyNo + "' and kindcode='3001001'"
			      	+ " and itemcode = '0005'";
					ResultSet rsSumAmount300105 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300105.next()) {
						this.SumAmount300105 = (rsSumAmount300105.getDouble("SumAmount300105")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300105.close();
	
					strSQL = "SELECT Amount AS SumAmount300106 FROM PrpcpitemKind where policyno='" + this.tempPolicyNo + "' and kindcode='3001001'"
				  	+ " and itemcode = '0006'";
					ResultSet rsSumAmount300106 = dbManager.executeQuery(strSQL);
					if (rsSumAmount300106.next()) {
						this.SumAmount300106 = (rsSumAmount300106.getDouble("SumAmount300106")/this.sumquantity)* this.exchRate;
					}
					rsSumAmount300106.close();
				}
			}
	        
            /**************再保部分*******************/
			//是否允许划分危险单位
			String strReinsUnit = "SELECT * FROM PrpdRiskConfig WHERE ConfigCode='RISK_UNIT_FLAG' AND ConfigValue='1' AND RiskCode='"
					+ this.riskCode + "'";
			ResultSet rsReinsUnit = dbManager.executeQuery(strReinsUnit);
			if (rsReinsUnit.next()) {
				this.reinsUnit = "Y";
			}
            rsReinsUnit.close();

			//附加自留保费（元）/保额（元）
			String strTrialPremium = "SELECT * FROM PrpPReinsTrial WHERE ReinsMode='182' AND EndorseNo='"
					+ iBusinessNo + "'";
			ResultSet rsTrialPremium = dbManager.executeQuery(strTrialPremium);
			if (rsTrialPremium.next()) {
				//modify by zhulei 20060426 家财险类03附加自留要考虑分户问题
//				if(classCode.equals("03") && sumquantity>0){
//					this.trialAmount = rsTrialPremium.getDouble("Amount")/sumquantity; //附加自留保额（元）
//					this.trialPremium = rsTrialPremium.getDouble("Premium")/sumquantity; //附加自留保费（元）
//				}else{
					this.trialAmount = rsTrialPremium.getDouble("Amount"); //附加自留保额（元）
					this.trialPremium = rsTrialPremium.getDouble("Premium"); //附加自留保费（元）
//				}
			}
            rsTrialPremium.close();

			//临分（含特约）
			String strReinsMode = "SELECT * FROM PrpPReinsTrial WHERE EndorseNo='"
					+ iBusinessNo + "' AND ReinsMode like '3%'";
			ResultSet rsReinsMode = dbManager.executeQuery(strReinsMode);
			if (rsReinsMode.next()) {
				this.allowSplit = "Y"; //是否允许临分（含特约）
			}
            rsReinsMode.close();
            /*******************财产险部分*******************/
			if (this.classCode.equals("01")||this.classCode.equals("03")||this.classCode.equals("07")) {
				strSQL = "SELECT Amount FROM prppdangerunit where policyno='" +iBusinessNo+"'";
				ResultSet rstSumAmount = dbManager.executeQuery(strSQL);
				if (rstSumAmount.next()) {
					this.SumAmount = rstSumAmount.getDouble("Amount");
				}
				rstSumAmount.close(); 
		
				strSQL = "SELECT Amount FROM prpPitemkind where policyno='" +iBusinessNo+"' and kindcode='0107100'" 
				          +"and riskcode ='"+this.riskCode+"'";
				ResultSet rsAmount = dbManager.executeQuery(strSQL);
				if (rsAmount.next()) {
					this.Amount = rsAmount.getDouble("Amount") * BWBexchangeRate;
				}
				rsAmount.close();
				strSQL = "SELECT Amount FROM prpPitemkind where policyno='" +iBusinessNo+"' and kindcode='0108100'"
				         +"and riskcode ='"+this.riskCode+"'";
				ResultSet rsLimitManAcc = dbManager.executeQuery(strSQL);
				if (rsLimitManAcc.next()) {
					this.LimitManAcc = rsLimitManAcc.getDouble("Amount") * BWBexchangeRate;
				}
				rsAmount.close();
				strSQL = "SELECT Amount FROM prpPitemkind where policyno='" +iBusinessNo+"' and kindcode='0108200'"
				          +"and riskcode ='"+this.riskCode+"'";
				ResultSet rsSumAmount08 = dbManager.executeQuery(strSQL);
				if (rsSumAmount08.next()) {
					this.SumAmount08 = rsSumAmount08.getDouble("Amount") * BWBexchangeRate;
				}
				rsAmount.close();
				
				
				
			}
//			modify by zhangruifeng begin 20080220
			//新增部分－家财险、企财险、房贷险、建工险增加承保年限的控制
			//modified by gengxiaobo begin 20080610 起保小时、终保小时从业务表中获取。
			if (this.classCode.equals("01")||this.classCode.equals("03")||this.classCode.equals("04")||this.classCode.equals("07")) 
			{
				this.CBYearLimit = pubTools.getYearMinus
				(new com.sinosoft.utility.string.Date(startDate.toString()),startHour,
						new com.sinosoft.utility.string.Date(endDate.toString()),endHour);				
			}
			//modified by gengxiaobo end 20080610 起保小时、终保小时从业务表中获取。
			//modify by zhangruifeng end 20080220
            /***********费用部分*****************/
            //add by zhulei 20060426 begin 费用比例
			//费用比例
            strSQL = "SELECT * FROM PrpCPexpense WHERE PolicyNo='" + tempPolicyNo + "'";
            ResultSet rsExpense = dbManager.executeQuery(strSQL);
            if(rsExpense.next()){
            	this.expenseFeeRate = rsExpense.getDouble("ManageFeeRate") + this.disRate;
            }
            rsExpense.close();
			//add by zhangruifeng 20080422 reason:共保时按照我方份额进行控制总保额           
            if(!("05".equals(classCode))){//当是非车时
				if("1".equals(coinsflag)||"2".equals(coinsflag)){ //当是主共保或是从共保时
					strSQL = "select coinsRate from prpcpcoins where coinstype = '1' "+
					"and policyno ='"+tempPolicyNo+"'";
					ResultSet rsSumAmount1 = dbManager.executeQuery(strSQL);
					if (rsSumAmount1.next()) {
						coinsRate = rsSumAmount1.getDouble("coinsRate");
						this.sumAmount = sumAmount*coinsRate/100;
					}
					rsSumAmount1.close(); 
				}
			}
            //add by zhulei 20060426 end 费用比例
            
			//意外险每人最高保额 lijibin add 2005-08-14
			// modify by zhulei 20050831 CP表中没有Endorse字段，需要修改，
			//并需要确认是否从P表取数？为什么原来从CP表取，是不是写错了
            ReinsUndrtInterfAction reinsUndrtInterfAction = new ReinsUndrtInterfAction();
            	isNewRiskEvaluate = reinsUndrtInterfAction.isNewRiskEvaluate(iBusinessNo, strPolicyno);
			//add by zhaoning20100129 begin Reason:2010年非车险核保权限
			if(riskCode.equals("0402")){
				strSQL = "SELECT Max(Amount) AS sumAmount0400600 FROM PrpcpitemKind WHERE policyno = '" + this.tempPolicyNo + 
						"' and Kindcode ='0400600'"; //财产损失保险保险金额
				ResultSet rsSumAmount0400600 = dbManager.executeQuery(strSQL);
				if (rsSumAmount0400600.next()) {
					this.sumAmount0400600 = rsSumAmount0400600.getDouble("sumAmount0400600") * BWBexchangeRate;
				}
				rsSumAmount0400600.close();
				
				strSQL = "SELECT Max(Amount) AS sumAmount0400700 FROM PrpcpitemKind WHERE policyno = '" + this.tempPolicyNo + 
						"' and Kindcode ='0400700'"; //还贷保证保险保险金额
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

