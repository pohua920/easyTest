package com.sinosoft.undwrt.common.util;

/**
 * <p>Title: 业务数据</p>
 * <p>Description:通过保单号得到业务数据 </p>
 * <p>Copyright: Copyright (c) 2005/7</p>
 * <p>Company: Sinosoft</p>
 * @author qinyongli
 * @version 1.0
 */
import java.util.Date;
import java.sql.*;

import com.sinosoft.sysframework.common.datatype.DateTime;
import com.sinosoft.sysframework.reference.DBManager;

/**
 * The Class BusinessPolicyData.
 */
public class BusinessPolicyData extends  BusinessData{
	
	/** 屬性日期 now. */
	private DateTime dateNow = new DateTime().current();     //当前时间
    //车险核保业务数据

    /**
	 * Instantiates a new business policy data.
	 * 
	 * @param iBusinessNo
	 *            the i business no
	 * @param dbManager
	 *            the db manager
	 * @throws Exception
	 *             the exception
	 */
    public  BusinessPolicyData(String iBusinessNo,DBManager dbManager) throws Exception{
		double sumquantity = 1;    //家财险分户数
		double BWBexchangeRate = 1d;   //签单币别和本位币的兑换率
		String strOthFlag = "";
        try{
        	/**************公共部分*******************/
            String strSQL = "select * from prpCmain where policyno = '"+iBusinessNo+"'";
            String strWhere = "select * from PrpCmainCovernote where policyno = '"+iBusinessNo+"'";
            ResultSet rsCmain = dbManager.executeQuery(strSQL);
            if(rsCmain.next())
            {
                this.riskCode   = rsCmain.getString("riskcode");                      //险种
                this.classCode  = rsCmain.getString("classCode");                     //险别
                this.currency   = rsCmain.getString("currency");                      //币别信息
				BWBexchangeRate = rsCmain.getDouble("exchangeRate");
				if(BWBexchangeRate == 0){
					BWBexchangeRate = 1;
				}
                this.sumAmount  = rsCmain.getDouble("sumAmount") * BWBexchangeRate;                     //总保额
                this.discount   = rsCmain.getInt("discount");                         //折扣率
                this.disRate1   = rsCmain.getDouble("disrate1");                      //中间成本
                this.disRate    = rsCmain.getInt("disrate");                          //经济费和手续费比例
                this.contractno = rsCmain.getString("contractno");                    //合同号
                this.SumPremium = rsCmain.getDouble("SumPremium");                    //总保费
                this.businessNature = rsCmain.getString("BusinessNature");            //业务性质
                //Date UnderwriteEndDate = new Date();
                Date operateDate = rsCmain.getDate("OperateDate");
                Date signDate = rsCmain.getDate("SignDate");
                Date StartDate = rsCmain.getDate("StartDate");
                //直接或者代理倒签单天数(车险，非车有所不同)
                if(riskCode.equals("A01")||riskCode.equals("0510"))
                {
                  if(operateDate!=null&&StartDate!=null){//倒签单天数－车险
                    //directandAgentDay = (operateDate.getTime()-StartDate.getTime())/(24*60*60*1000);
   				    //modify by zhulei 20051228 车险倒签单以当前日期与起保日期比较
                	  DirectDay = (dateNow.getTime()-StartDate.getTime())/(24*60*60*1000);
                  }
                }
                else
                {
                  if(signDate!=null&&StartDate!=null){//直接和代理倒签单天数－非车险
                	  DirectDay = (signDate.getTime()-StartDate.getTime())/(24*60*60*1000);
                  }
                }
                if(riskCode.equals("YAB0")){
                    this.prepayProtocol = "Y"; //预约协议
                }
                strOthFlag = rsCmain.getString("OthFlag");
                if(strOthFlag.length()>=11){
                	if(strOthFlag.substring(10,11).equals("1")){
                		this.coverNoteFlag = "Y";
                	}else{
                		this.coverNoteFlag = "N";
                	}
                }
            }
            rsCmain.close();
            System.out.println("***strWhere=="+strWhere);
            ResultSet rsCmainCovernote = dbManager.executeQuery(strWhere);
                if(rsCmainCovernote.next()){
            	this.riskCode   = rsCmainCovernote.getString("riskcode");                      //险种
                this.classCode  = rsCmainCovernote.getString("classCode");                     //险别
                this.currency   = rsCmainCovernote.getString("currency");                      //币别信息
                this.sumAmount  = rsCmainCovernote.getDouble("sumAmount");                     //总保额
                this.discount   = rsCmainCovernote.getInt("discount");                         //折扣率
                this.disRate1   = rsCmainCovernote.getDouble("disrate1");                      //中间成本
                this.disRate    = rsCmainCovernote.getInt("disrate");                          //经济费和手续费比例
                this.contractno = rsCmainCovernote.getString("contractno");                    //合同号
                this.SumPremium = rsCmainCovernote.getDouble("SumPremium");                    //总保费
                this.businessNature = rsCmainCovernote.getString("BusinessNature");            //业务性质
                //Date UnderwriteEndDate = new Date();
                Date operateDate = rsCmainCovernote.getDate("OperateDate");
                Date signDate = rsCmainCovernote.getDate("SignDate");
                Date StartDate = rsCmainCovernote.getDate("StartDate");
                if(signDate!=null&&StartDate!=null){//直接和代理倒签单天数－非车险
              	  DirectDay = (signDate.getTime()-StartDate.getTime())/(24*60*60*1000);
                }
                strOthFlag = rsCmainCovernote.getString("OthFlag");
                if(strOthFlag.length()>=11){
                	if(strOthFlag.substring(10,11).equals("1")){
                		this.coverNoteFlag = "Y";
                	}else{
                		this.coverNoteFlag = "N";
                	}
                }
            }
            else
            {
                return ;
            }
                rsCmainCovernote.close(); 
            //返回险别
            String strKindcode= "select * from prpCitemkind where policyno  ='" + iBusinessNo+"'";
            ResultSet rsKindcode = dbManager.executeQuery(strKindcode);
            if(rsKindcode.next()){
                riskKind = rsKindcode.getString("kindcode");
            }
            //add by zhulei begin 20060224 特殊因子、手续费，如果有预约协议，需要特殊处理
            strSQL = "select * from prpCmainSub where policyno = '"+iBusinessNo+"'";
            ResultSet rsCmainSub = dbManager.executeQuery(strSQL);
            if(rsCmainSub.next()){
            	//有预约协议大保单
            	String strPolicyNoMain = rsCmainSub.getString("MainPolicyNo");
                strSQL = "select * from prpCmain where policyno = '"+strPolicyNoMain+"'";
                ResultSet rsCmainMain = dbManager.executeQuery(strSQL);
                //小保单特殊因子、手续费比例小于等于预约协议特殊因子时，不再校验，业务取数值给 0
                if(rsCmainMain.next()){
                	if(disRate1<=rsCmainMain.getDouble("disRate1")){  //特殊因子
                		disRate1 = 0;
                	}
                	if(disRate<=rsCmainMain.getDouble("disRate")){    //手续费
                		disRate = 0;
                	}
                }
                rsCmainMain.close();
                strSQL = "select * from prpCmainCargo where policyno = '"+strPolicyNoMain+"'";
                ResultSet rsCmainMainCargo = dbManager.executeQuery(strSQL);
                //小保单特殊因子、手续费比例、保额小于等于预约协议特殊因子时，不再校验，业务取数值给 0
                if(rsCmainMainCargo.next()){
                	if(DirectDay<=rsCmainMainCargo.getInt("OriginalCount")){
                		DirectDay = 0;
                	}
                	if(sumAmount<=rsCmainMainCargo.getDouble("InvoiceAmount")){
                		sumAmount = 0;
                	}
                }
                rsCmainMainCargo.close();
            }
            rsCmainSub.close();
            //add by zhulei end 20060224 特殊因子、手续费，如果有预约协议，需要特殊处理
            //add by zhulei 20060417 begin YAB0 保额，倒签单天数 特殊处理
            if(riskCode.equals("YAB0")){
            	strSQL = "select * from prpCmainCargo where PolicyNo = '"+ iBusinessNo +"'";
            	ResultSet rsTmainMainCargo = dbManager.executeQuery(strSQL);
            	if(rsTmainMainCargo.next()){
            		sumAmount = rsTmainMainCargo.getDouble("InvoiceAmount");
            		if(DirectDay<rsTmainMainCargo.getInt("OriginalCount")){
            			DirectDay = rsTmainMainCargo.getInt("OriginalCount");
            		}
            	}else{
            		sumAmount = 0;
            	}
            	rsTmainMainCargo.close();
            }
            //联(共)保标志
            String strCoinsFlag= "select * from prpCmain where CoinsFlag in ('1','2')  and  policyno  ='" + iBusinessNo+"'";
            ResultSet rsCoinsFlag = dbManager.executeQuery(strCoinsFlag);
            if(rsCoinsFlag.next()){
                unitPolicy= "Y";
            }
            //暂保单
            String strSort= "select * from prpCmain where PolicySort = 3  and  policyno  ='" + iBusinessNo+"'";
            ResultSet rsSort = dbManager.executeQuery(strSort);
            if(rsSort.next()){
                tempPolicy= "Y";
            }
            //兑换率 prpdexch表
            String strExchRate = "SELECT ExchRate FROM PrpDexch WHERE exchcurrency='CNY' AND ValidStatus='1' AND BaseCurrency='" + this.currency + "'";
            ResultSet rsExchRate = dbManager.executeQuery(strExchRate);
            if(rsExchRate.next())
            {
                this.exchRate = rsExchRate.getDouble("ExchRate");
            }
            else             //如果未取得结果，exchRate取默认1
            {
                this.exchRate = 1;
            }
            rsExchRate.close();
            
            //判断是否是预约协议  子保单判断
            String strCmainsub= "select * from PrpCmainsub where policyno  ='" + iBusinessNo+"'";
            ResultSet rsCmainsub = dbManager.executeQuery(strCmainsub);
            if(rsCmainsub.next()){
                this.prepayProtocol = "Y"; //预约协议
            }

            /****************车险部分**************************/
            if(classCode.equals("A") || classCode.equals("B")){
            String strItem = "select * from prpCitemCar where policyno ='" + iBusinessNo+"'";
            ResultSet rsItem = dbManager.executeQuery(strItem);
            if(rsItem.next())
            {
                this.insuredTypeCode = rsItem.getString("InsuredTypeCode");//客户性质
                this.useNatureCode = rsItem.getString("UseNatureCode");    //使用性质
                this.modelCode = rsItem.getString("ModelCode");            //车型信息
                this.useYears = rsItem.getInt("UseYears");                 //使用年限
            }
            //基准保费
            String strPriminum = "select Sum(BenchMarkPremium) as trueSumPremium 　from prpCitemkind where policyno ='" + iBusinessNo+"'";
            ResultSet rsPrimum = dbManager.executeQuery(strPriminum);
            if(rsPrimum.next())
            {
                this.trueSumPremium = rsPrimum.getDouble("trueSumPremium");//基准保费
                if(true){
				    //modify by zhulei 20051109 净费率需要除以短期费率系数
                    outLayRate = SumPremium/trueSumPremium/shortRate*100;//净费率
                }
                if(true){
                    outLayRate =100;
                }
            }
            //返回是否允许招标信息返回团购车的数量
            String strMinus= "select * from PrpMotorcade where contractno ='" + contractno+"'";
            ResultSet rsMinus = dbManager.executeQuery(strMinus);
            if(rsMinus.next()){
            	permitBidding = rsMinus.getString("minusflag");//返回是否允许招标信息
                groupCarSum = rsMinus.getInt("carcount");//返回团购车的数量
            }
            //返回车辆损失险保额
            String strAmountA= "Select * from prpCitemkind where riskcode='"+this.riskCode+"' and kindcode = 'A' and policyno  ='" + iBusinessNo+"'";
            ResultSet rsAmount = dbManager.executeQuery(strAmountA);
            if(rsAmount.next()){
                amountA= rsAmount.getDouble("amount") * BWBexchangeRate;
            }
            //返回全车盗抢险
            String strAmountG= "Select * from prpCitemkind where riskcode='"+this.riskCode+"' and kindcode = 'G' and policyno  ='" + iBusinessNo+"'";
            ResultSet rsAmountG = dbManager.executeQuery(strAmountG);
            if(rsAmountG.next()){
            	amountB= rsAmountG.getDouble("amount");
                unitAmount = rsAmount.getDouble("unitAmount") * BWBexchangeRate;
            }
            //返回自燃损失险
            String strAmountZ= "Select * from prpCitemkind where riskcode='"+this.riskCode+"' and kindcode = 'Z' and policyno  ='" + iBusinessNo+"'";
            ResultSet rsAmountZ = dbManager.executeQuery(strAmountZ);
            if(rsAmountZ.next()){
            	amountZ= rsAmountZ.getDouble("amount") * BWBexchangeRate;
            }

            //返回第三者综合险
            String strAmountB= "Select * from prpCitemkind where riskcode='"+this.riskCode+"' and kindcode = 'B' and policyno  ='" + iBusinessNo+"'";
            ResultSet rsAmountB = dbManager.executeQuery(strAmountB);
            if(rsAmountB.next()){
            	amountB= rsAmountB.getDouble("amount") * BWBexchangeRate;
            }
        }
            /****************非车非意健部分**************/
			if(classCode.equals("03")){
				strSQL = "select sumquantity from prpcmain where policyno='" + iBusinessNo + "'";
				ResultSet rsDisprptmain = dbManager.executeQuery(strSQL);
				if(rsDisprptmain.next())
				{
					sumquantity = rsDisprptmain.getDouble("sumquantity");
				}
				rsDisprptmain.close();
				if(sumquantity!=0){
				    this.sumAmount = this.sumAmount / sumquantity;
				}
			}
            //add by dengwenchun 20060414 reason:家财险每户限额控制 end

//            //兑换后的总保额
//            this.sumAmount = this.sumAmount * this.exchRate;
      //获取折扣信息
            String strWhereDisCount = "SELECT * FROM PrpCitemkind WHERE PolicyNo ='" + iBusinessNo + "'";
            ResultSet rsDisCount = dbManager.executeQuery(strWhereDisCount);
            if(rsDisCount.next())
            {
                this.discount = rsDisCount.getInt("DisCount");
				this.shortRate = rsDisCount.getDouble("ShortRate");
            }
            else
            {
                this.discount = 100;
				this.shortRate = 100;
            }


            //招标系数下浮比例
            String strProfitRate= "select ProfitRate  from prpCprofitdetail where  profitcode='C14'  and policyno  ='" + iBusinessNo+"'";
            ResultSet rsProfitRate = dbManager.executeQuery(strProfitRate);
            if(rsProfitRate.next()){
                double biddingDownProportion = rsProfitRate.getDouble("ProfitRate");
            }
            //允许保单注销天数
            String strLogOut= "select * from prpCplan  where  policyno  ='" + iBusinessNo+"'";
            ResultSet rsLogOut = dbManager.executeQuery(strLogOut);
            if(rsLogOut.next()){
                Date plandate = rsLogOut.getDate("plandate");
                Date planstartdate = rsLogOut.getDate("planstartdate");
                if(plandate!=null&&planstartdate!=null){
                    writeOffDays = (plandate.getTime()-planstartdate.getTime())/(24*60*60*1000);
                }
            }


            //每人事故赔偿限额-乘过汇率
            String strLimit01= "select * from prpCLimit where limittype = '04' and  policyno = '"+iBusinessNo+"'";
            ResultSet rsLimit01 = dbManager.executeQuery(strLimit01);
            if(rsLimit01.next()){
               this.limitManAcc01 =  rsLimit01.getDouble("limitfee") * this.exchRate;
            }
            //每次事故赔偿限额-乘过汇率
            String strLimit12= "select * from prpCLimit where limittype = '12' and policyno = '"+iBusinessNo+"'";
            ResultSet rsLimit12 = dbManager.executeQuery(strLimit12);
            if(rsLimit12.next()){
               this.limitAcc12 = rsLimit12.getDouble("limitfee") * this.exchRate;
            }
            //获取年龄范围
            String strAge= "select age from prpCinsurednature where policyno in (select policyno from prptinsured where insuredtype in ('1','2')) and policyno = '"+iBusinessNo+"'";
            ResultSet rsAge = dbManager.executeQuery(strAge);
            while(rsAge!=null&&rsAge.next()){
                    ageScope.add(rsAge.getString("age"));
            }
            /**************再保部分***************/
            //是否允许划分危险单位
            String strReinsUnit = "SELECT * FROM PrpdRiskConfig WHERE ConfigCode='RISK_UNIT_FLAG' AND ConfigValue='1' AND RiskCode='" + this.riskCode + "'";
            ResultSet rsReinsUnit = dbManager.executeQuery(strReinsUnit);
            if(rsReinsUnit.next())
            {
                this.reinsUnit = "Y";
            }
            //附加自留保费（元）/保额（元）
            String strTrialPremium = "SELECT * FROM PrpCReinsTrial WHERE ReinsMode='182' AND PolicyNo='" + iBusinessNo + "'";
            ResultSet rsTrialPremium = dbManager.executeQuery(strTrialPremium);
            if(rsTrialPremium.next())
            {
				//modify by zhulei 20060426 家财险类03附加自留要考虑分户问题
            	if(classCode.equals("03") && sumquantity>0){
                    this.trialAmount = rsTrialPremium.getDouble("Amount")/sumquantity;    //附加自留保额（元）
                    this.trialPremium = rsTrialPremium.getDouble("Premium")/sumquantity;  //附加自留保费（元）
            	}else{
                    this.trialAmount = rsTrialPremium.getDouble("Amount");    //附加自留保额（元）
                    this.trialPremium = rsTrialPremium.getDouble("Premium");  //附加自留保费（元）
            	}
            }
            //临分（含特约）
            String strReinsMode = "SELECT * FROM PrpCReinsTrial WHERE PolicyNo='" + iBusinessNo + "' AND ReinsMode like '3%'";
            ResultSet rsReinsMode = dbManager.executeQuery(strReinsMode);
            if(rsReinsMode.next())
            {
                this.allowSplit = "Y";          //是否允许临分（含特约）
            }
            /**********费用部分**********/
            //add by zhulei 20060426 begin 费用比例
            strSQL = "SELECT * FROM PrpCexpense WHERE PolicyNo='" + iBusinessNo + "'";
            ResultSet rsExpense = dbManager.executeQuery(strSQL);
            if(rsExpense.next()){
            	this.expenseFeeRate = rsExpense.getDouble("ManageFeeRate") + this.disRate;
            }
            rsExpense.close();
            //add by zhulei 20060426 end 费用比例
            /*************意健险部分****************/
            if(this.classCode.equals("27"))
            {
                //意外险每人最高保额 lijibin add 2005-08-14
                String strUnitAmount = "SELECT MAX(UnitAmount) AS UnitAmount FROM PrpCitemKind WHERE PolicyNo='"+iBusinessNo+"'";
                ResultSet rsUnitAmount = dbManager.executeQuery(strUnitAmount);
                if(rsUnitAmount.next())
                {
                  this.unitAmount = rsUnitAmount.getDouble("UnitAmount") * BWBexchangeRate;
                }
                String strUnit= "select * from prpCmaincasualty where  policyno = '"+iBusinessNo+"'";
                ResultSet rsUnit = dbManager.executeQuery(strUnit);
                if(rsUnit!=null&&rsUnit.next()){
                    double unitCount = rsUnit.getDouble("unitcount");
                    double mainUnitCount = rsUnit.getDouble("MAININSUREDCOUNT");
                    if(unitCount!=0){
                        this.unitProportion = mainUnitCount/unitCount;
                    }
                }
                //add by zhulei begin 20051218 意健险PML
                String strPML = "SELECT * FROM PrpCdangerUnit WHERE PolicyNo='" + iBusinessNo + "'";
                ResultSet rsPML = dbManager.executeQuery(strPML);
                if(rsPML.next())
                {
                   this.dbPML = rsPML.getDouble("Spevalue");
                }
                //add by zhulei end 20051218 意健险PML
            }
			//add by zhaoning20091125 begin Reason:获取危险单位条数
            strSQL = "SELECT count(*) as DangerUnitCount FROM prpcdangerunit where policyno='" +iBusinessNo+"'";
			ResultSet rsDangerUnit = dbManager.executeQuery(strSQL);
			if (rsDangerUnit.next()) {
				this.dangerUnitCount = rsDangerUnit.getInt("DangerUnitCount");
			}
			rsDangerUnit.close(); 
			//add by zhaoning20091125 end
        }catch(Exception e)
        {
            throw e;
        }//try
        
    }
}//class
