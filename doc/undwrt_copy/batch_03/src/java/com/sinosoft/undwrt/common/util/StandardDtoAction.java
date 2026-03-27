package com.sinosoft.undwrt.common.util;

/**
 * <p>Title: 初始化条件DTO</p>
 * <p>Description:设置基础数据,给DTO赋值 </p>
 * <p>Copyright: Copyright (c) 2005/7</p>
 * <p>Company: Sinosoft</p>
 * @author qinyongli
 * @author xuning modify gpic 20061027
 * @author jiabeilei :20081106 增加通用险种的核保权限判断
 * @author yanglibo :20081112 增加01险种下的国民经济行业核保权限控制
 * modified by xiongguojun 20091105 增加产品延长保修服务合同责任保险15980001的核保权限
 * @version 1.1
 */
import ins.framework.common.QueryRule;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.sinosoft.platform.dto.domain.UtiUwComboFactorDto;
import com.sinosoft.platform.dto.domain.UtiUwConditionDto;
import com.sinosoft.platform.dto.domain.UtiUwFactorDto;
import com.sinosoft.platform.resource.dtofactory.domain.DBUtiUwComboFactor;
import com.sinosoft.platform.resource.dtofactory.domain.DBUtiUwCondition;
import com.sinosoft.platform.resource.dtofactory.domain.DBUtiUwFactor;
import com.sinosoft.prpall.blsvr.cb.BLPrpCPmainProp;
import com.sinosoft.prpall.blsvr.tb.BLPrpTdangerUnit;
import com.sinosoft.prpall.blsvr.tb.BLPrpTmainProp;
import com.sinosoft.prpall.dbsvr.cb.DBPrpCPitemCar;
import com.sinosoft.prpall.dto.domain.PrpCmainCovernoteDto;
import com.sinosoft.prpall.dto.domain.PrpCmainDto;
import com.sinosoft.prpall.dto.domain.PrpPmainDto;
import com.sinosoft.prpall.dto.domain.PrpTitemCarDto;
import com.sinosoft.prpall.dto.domain.PrpTmainDto;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmain;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpCmainCovernote;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpPmain;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTitemCar;
import com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTmain;
import com.sinosoft.prpall.schema.PrpCPmainPropSchema;
import com.sinosoft.prpall.schema.PrpTmainPropSchema;
import com.sinosoft.reins.common.model.PrpTDangerRisk;
import com.sinosoft.reins.common.service.facade.PrpTDangerRiskService;
import com.sinosoft.sysframework.reference.DBManager;
import com.sinosoft.undwrt.common.vo.HeBaoConditionVo;
import com.sinosoft.utiall.dbsvr.DBPrpDcompany;
import com.sinosoft.utiall.dbsvr.DBPrpDrisk;
import com.sinosoft.utiall.schema.PrpDriskSchema;
import com.sinosoft.product.dbsvr.tb.*;
import com.sinosoft.product.blsvr.tb.*;
import com.sinosoft.product.dbsvr.cb.*;
import com.sinosoft.product.blsvr.cb.*;
import com.sinosoft.product.schema.*;


/**
 * The Class StandardDtoAction.
 */
public class StandardDtoAction {
    
    /** The Constant HeBaoConditionDto. */
    private static final String HeBaoConditionDto = null;
	
	/** 屬性險類代碼. */
	private String classCode = "";
    
    /** 屬性險種代碼. */
    private String riskCode = "";
    
    /** 屬性機構代碼. */
    private String comCode = "";
    
    /** 屬性The sinosoft policyno. */
    private String policyno = "";
    
    /** 屬性The sinosoft make com. */
    private String makeCom = "";
    
    /** 屬性The sinosoft policy sort. */
    private String policySort = "";
    
    /** 屬性The sinosoft use nature. */
    private String useNature = "";
    
    /** 屬性The sinosoft car kind. */
    private String carKind = "";//车辆种类
    
    /** 屬性The sinosoft risk class. */
    private String riskClass ="";
    
    /** 屬性The sinosoft business source. */
    private String businessSource="";//国民经济行业代码
    
    /** 屬性The sinosoft possess nature. */
    private String possessNature="";//占用性质
    
    /** 屬性模板號. */
    private int modelNo = 0;
    
    /** 屬性節點號. */
    private int nodeNo = 0;
    
    /** 屬性The sinosoft uw type. */
    private String uwType = "";
    
    /** 屬性The sinosoft Max number. */
    private  double MaxNumber = 2147483647;
    
    /** 屬性The sinosoft cl. */
    private Class cl  = null;
    
    /** 屬性The sinosoft method. */
    private Method method = null;

    /** 屬性The sinosoft productcode. */
    private String productcode = "";
    
    /** 屬性要保書危險單位風險評估接口. */
    private PrpTDangerRiskService prpTDangerRiskService;
    /*
    public BLStandardDto(){}//构造函数
    public static void main(String[] args) throws Exception
    {
    	BLStandardDto blStandardDto = new BLStandardDto();
    	HeBaoConditionCarDto heBaoConditionCarDto = new HeBaoConditionCarDto();
        blStandardDto.getCarHebaoConditionPrimaryBDto();
    }
    */
    /**
	 * Inits the standard.
	 * 
	 * @param iBusinessNo
	 *            业务号
	 * @param dbManager
	 *            数据库连接池
	 * @param iBusinessType
	 *            业务类型
	 * @param iComCode
	 *            機構代碼
	 * @param iModelNo
	 *            the i model no
	 * @param iNodeNo
	 *            the i node no
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 * @author lijibin 20050910 初始化业务基础数据
	 */
    public boolean initStandard(String iBusinessNo,DBManager dbManager,String iBusinessType,String iComCode,int iModelNo,int iNodeNo) throws Exception{

        //声明:双核高级条件的处理要迁就platform的基础代码表、处理不好之处敬请见谅，非我本意。痛苦啊
    	//高级条件应该是去控制路径上的条件-pathno,而不应该是现在去控制某个nodeno。现在高级条件不能对非审核通过的路径条件
    	//进行控制，只能通过简单sql
    	//投保业务
    	this.modelNo = iModelNo;
    	this.nodeNo = iNodeNo;
        if (iBusinessType.equals("proposal")) {
            DBPrpTmain dbPrpTmain = new DBPrpTmain(dbManager);
            PrpTmainDto prpTmainDto = dbPrpTmain.findByPrimaryKey(iBusinessNo);
            if(prpTmainDto!=null){
                this.classCode = prpTmainDto.getClassCode();
                this.riskCode = prpTmainDto.getRiskCode();
                this.comCode = prpTmainDto.getComCode();
                this.policySort = prpTmainDto.getPolicySort();
                if(this.classCode.equals("A") || this.classCode.equals("B"))//车险的各险别的保额与车辆的使用性质关联--特殊处理
                {
                	DBPrpTitemCar dbPrpTitemCar = new DBPrpTitemCar(dbManager);
                    PrpTitemCarDto prpTitemCarDto = dbPrpTitemCar.findByPrimaryKey(iBusinessNo,1);
                    this.useNature = prpTitemCarDto.getUseNatureCode();
                    this.carKind = prpTitemCarDto.getCarKindCode();
                	
                }
                if(this.classCode.equals("01")||this.classCode.equals("03")||this.classCode.equals("07"))//非车险各险别的保额与拆分的危险单位有关
                {
                	PrpTDangerRisk prpTdangerRiskDto = new PrpTDangerRisk();
                	String riskClass = "";
                    QueryRule queryrule=QueryRule.getInstance();
                    queryrule.addEqual("proposalno", iBusinessNo);
                	Collection wfLogList = new ArrayList();
                	wfLogList=(Collection)prpTDangerRiskService.findByConditions(queryrule);
                	Iterator prpTdangerRisk = wfLogList.iterator();
                	if(prpTdangerRisk.hasNext()){
                		prpTdangerRiskDto = (PrpTDangerRisk) prpTdangerRisk.next();
                	    this.riskClass = prpTdangerRiskDto.getRiskClass();
                	    System.out.println("==========this.riskClass=="+this.riskClass);
                	}
                	
                }
                if(this.riskCode.equals("0999")||this.riskCode.equals("1598")||this.riskCode.equals("1599")
                ||this.riskCode.equals("2399")||this.riskCode.equals("2798")||this.riskCode.equals("2799"))
                {//2799要不要去掉？？？
                	System.out.println("blstandarddto.java中riskCode=="+this.riskCode);
                    BLPrpTproduct blPrpTproduct = new BLPrpTproduct();
                    blPrpTproduct.getData(iBusinessNo);	
                    if(blPrpTproduct.getSize()>0)
                    {
                	  PrpTproductSchema prpTproductSchema = blPrpTproduct.getArr(0);
                      this.productcode = prpTproductSchema.getProductCode();
                      System.out.println("blstandarddto.java中productcode=="+productcode);
                    }
                }
                //add by yanglibo 20081112 begin reason: 增加以下险种的国民行业核保控制
                //add by yanglibo 20090504 begin reason: 增加以下险种的占有性质核保控制
                if(this.riskCode.equals("0101")||this.riskCode.equals("0102")||this.riskCode.equals("0103")
                        ||this.riskCode.equals("0104")||this.riskCode.equals("0122")||this.riskCode.equals("0123")
                        ||this.riskCode.equals("0124")){
                    BLPrpTmainProp blPrpTmainProp = new BLPrpTmainProp();
                    blPrpTmainProp.getData(iBusinessNo);	
                    if(blPrpTmainProp.getSize()>0)
                    {
                    	PrpTmainPropSchema prpTmainPropSchema = blPrpTmainProp.getArr(0);
                      this.businessSource = prpTmainPropSchema.getBusinessSource().substring(0, prpTmainPropSchema.getBusinessSource().length()-2);
                      this.possessNature = prpTmainPropSchema.getPossessNature();
                    }
                }
                //add by yanglibo 20081112 end
                //add by yanglibo 20090504 end reason: 增加以下险种的占有性质核保控制
                this.uwType = "P";//投保单\保单\批单核保的合并处理
            }            
            else{
            	return false;
            }
            
        }
        //保单业务
        else if (iBusinessType.equals("policy")) {
            DBPrpCmain dbPrpCmain = new DBPrpCmain(dbManager);
            DBPrpCmainCovernote dbPrpCmainCovernote = new DBPrpCmainCovernote(dbManager);
            PrpCmainDto prpCmainDto = dbPrpCmain.findByPrimaryKey(iBusinessNo);
            PrpCmainCovernoteDto prpCmainCovernoteDto =dbPrpCmainCovernote.findByPrimaryKey(iBusinessNo);
            if(prpCmainDto!=null){
                this.classCode = prpCmainDto.getClassCode();
                this.riskCode = prpCmainDto.getRiskCode();
                this.comCode = prpCmainDto.getComCode();
                this.policySort = prpCmainDto.getPolicySort();
                this.uwType = "P";
            }else if (prpCmainCovernoteDto!=null){
            	this.classCode = prpCmainCovernoteDto.getClassCode();
            	this.riskCode = prpCmainCovernoteDto.getRiskCode();
                this.comCode = prpCmainCovernoteDto.getComCode();
                this.policySort = prpCmainCovernoteDto.getPolicySort();
                this.uwType = "P";
            }
            else{
            	return false;
            }

        }
        //批单业务
        else if (iBusinessType.equals("endorse")) {
        	
            DBPrpPmain dbPrpPmain = new DBPrpPmain(dbManager);
            PrpPmainDto prpPmainDto = dbPrpPmain.findByPrimaryKey(iBusinessNo);
            if(prpPmainDto!=null){
                this.classCode = prpPmainDto.getClassCode();
                this.riskCode = prpPmainDto.getRiskCode();
                this.comCode = prpPmainDto.getComCode();
                this.policyno = prpPmainDto.getPolicyNo();
                this.policySort = prpPmainDto.getPolicySort();
                if(this.classCode.equals("A") || this.classCode.equals("B"))//车险的各险别的保额与车辆的使用性质关联--特殊处理
                {
                	DBPrpCPitemCar dbPrpCPitemCar = new DBPrpCPitemCar();
                    //PrpCPitemCarDto prpCPitemCarDto = DBPrpCPitemCar.findByPrimaryKey(iBusinessNo,1);
                    //this.useNature = prpCPitemCarDto.getUseNatureCode();
                	String strSQL = "select * from PrpCPitemCar where policyno='"+this.policyno+"'";
                    ResultSet rsPolicyno = dbManager.executeQuery(strSQL);
                    if(rsPolicyno.next()){
                    	this.useNature = rsPolicyno.getString("useNatureCode");
                    	this.carKind = rsPolicyno.getString("carKindCode");
                    }
                    rsPolicyno.close();
                	
                }
                //add by yanglibo 20081112 begin reason: 增加以下险种的国民行业核保控制
                //add by yanglibo 20090504 begin reason: 增加以下险种的占有性质核保控制
                if(this.riskCode.equals("0101")||this.riskCode.equals("0102")||this.riskCode.equals("0103")
                        ||this.riskCode.equals("0104")||this.riskCode.equals("0122")||this.riskCode.equals("0123")
                        ||this.riskCode.equals("0124")){
                    BLPrpCPmainProp blPrpCPmainProp = new BLPrpCPmainProp();
                    blPrpCPmainProp.getData(prpPmainDto.getPolicyNo());	
                    if(blPrpCPmainProp.getSize()>0)
                    {
                    	PrpCPmainPropSchema prpCPmainPropSchema = blPrpCPmainProp.getArr(0);
                      this.businessSource = prpCPmainPropSchema.getBusinessSource().substring(0, prpCPmainPropSchema.getBusinessSource().length()-2);
                      this.possessNature = prpCPmainPropSchema.getPossessNature();
                    }
                }
                //add by yanglibo 20081112 end
                //add by yanglibo 20090504 end reason: 增加以下险种的占有性质核保控制
                if(this.riskCode.equals("0999")||this.riskCode.equals("1598")||this.riskCode.equals("1599")
                        ||this.riskCode.equals("2399")||this.riskCode.equals("2798")||this.riskCode.equals("2799"))
                {
					System.out.println("blstandarddto.java中riskCode=="+this.riskCode);
					BLPrpCproduct blPrpCproduct = new BLPrpCproduct();
					blPrpCproduct.getData(prpPmainDto.getPolicyNo());
					if(blPrpCproduct.getSize()>0)
					{
					  PrpCproductSchema prpCproductSchema = blPrpCproduct.getArr(0);
					  this.productcode = prpCproductSchema.getProductCode();
					  System.out.println("blstandarddto.java中productcode=="+productcode);
					}
                }
                this.uwType = "P";
            }else{
            	return false;
            }

        }
        else
        {
            System.out.println("无此业务类型："+iBusinessType);
            return false;
        }
        if(this.comCode.equals(""))
        {
            this.comCode = "0000000000";
        }
		return true;
    }

    //核保模板基础数据初始化
    /**
	 * 獲取屬性the sinosoft hebao dto.
	 * 
	 * @param dbManager
	 *            the db manager
	 * @return 屬性the sinosoft hebao dto的值
	 * @throws Exception
	 *             the exception
	 */
    public HeBaoConditionVo getHebaoDto (DBManager dbManager) throws Exception{
    	
    	HeBaoConditionVo  heBaoConditionDto = new HeBaoConditionVo();
        cl=heBaoConditionDto.getClass();
        Object resultObject = cl.newInstance();
        Class[] partypes = new Class[1];

        Object[] valuess = new Object[1];
        Object[] values = new Object[1];
    	
    	DBUtiUwFactor  dbUtiUwFactor = new DBUtiUwFactor(dbManager);
    	DBUtiUwComboFactor dbUtiUwComboFactor = new DBUtiUwComboFactor(dbManager);
    	DBUtiUwCondition  dbUtiUwCondition = new DBUtiUwCondition(dbManager); 
    	DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
    	String  strConditonSql = "";
    	String  strFactorSql ="";
    	String  strComboFactorSql ="";
    	String  strConditonLast = "";
        String  strFactorCode = "";    //双核因子代码
    	String  strMultiSelectFlag ="";//因子直接取值还是间接
        String  strFactorAttr = "";    //因子在类里的取值类型
        String  strFactorValue = "";  //因子的标准值
        String  strCodeType = "";    //utiuwcombofactor表用的关联因子
        boolean blnIfExist = false;
        int resultNum = 0;
        String comCodeTemp = this.comCode;
        //modify by liuwei begin 20090512 1598停车场责任险产品个性化双核条件配置 
        //根据prpdrisk表riskflag第十四位标志位"T"判断该险种下是否有产品代码
        DBPrpDrisk  dbPrpDrisk = new DBPrpDrisk();
        PrpDriskSchema prpDriskSchema = null;
        Vector vector = new Vector();
        boolean isRemark = false;
        String conditionSql = " Select * From prpdrisk Where riskcode = '"+this.riskCode+"'  ";
        vector = dbPrpDrisk.findByConditions(conditionSql);
        if(!vector.isEmpty()){
	        for (int i=0;i<vector.size();i++){
	        	prpDriskSchema = (PrpDriskSchema)vector.get(i);
	        	System.out.println("---------根据prpdrisk表riskflag第十四位标志位'T'判断该险种下是否有产品代码--------prpDriskSchema.getRiskFlag().length() =="+prpDriskSchema.getRiskFlag().length());
	        	if(prpDriskSchema.getRiskFlag().length()>=14){
	        		if(prpDriskSchema.getRiskFlag().substring(13,14).equals("T")){//不直接使用indexof()是由于此其它位标志不确定
	        		   isRemark = true;
	        		   System.out.println("-----险种是否有产品代码---" + isRemark);
	        		}
	        	}
	        }
	    }
        
        while (!blnIfExist)
        {
        	//modify by zhouhui 200901104 ,暂时只有“机动车辆停车场责任险”的核保权限是按产品走的
        	//modified by xiongguojun 20091105 增加产品延长保修服务合同责任保险15980001的核保权限
        	if(isRemark 
        			&& ("00000054".equals(this.productcode) 
        					|| "15980001".equals(this.productcode))
        			&& "1598".equals(this.riskCode)){
        	strConditonSql = " COMCODE ='"+ comCodeTemp
            +"' AND MODELNO = '"+ this.modelNo
            +"' AND NODENO = '"+ this.nodeNo
            +"' AND RISKCODE = '"+ this.productcode  //如果险种下有产品,则配置产品代码.
            +"' AND CLASSCODE = '"+ this.classCode
            +"' AND UWTYPE = '"+ this.uwType
            +"'";
        	}
        	// modify by liuwei end 20090512  
            else {
            	strConditonSql = " COMCODE ='"+ comCodeTemp
                +"' AND MODELNO = '"+ this.modelNo
                +"' AND NODENO = '"+ this.nodeNo
                +"' AND RISKCODE = '"+ this.riskCode
                +"' AND CLASSCODE = '"+ this.classCode
                +"' AND UWTYPE = '"+ this.uwType
                +"'";
            	}
            resultNum = dbUtiUwCondition.getCount(strConditonSql);
            System.out.println("--查看utiuwcondition核保权限表 strConditonSql=="+strConditonSql);
            dbPrpDcompany.getInfo(comCodeTemp);
          //判断是否没有查找到信息，若是没有超到信息，并且此comcode在prpdcompany中comcode和uppercomcode一致则提示错误
          if(resultNum==0)
          {
            //若是comcode＝uppercomcode则不在循环
            if (dbPrpDcompany.getComCode().equals(dbPrpDcompany.getUpperComCode())){ 
              blnIfExist=true;
            }
            else{
            	comCodeTemp=dbPrpDcompany.getUpperComCode();
            }
          }
          else
          {
            blnIfExist = true;
          }
        }
        //comCodeTemp是有数据的机构。
    	//查询utiuwfactor表
        /* strFactorSql = "CLASSCODE = '"+ this.classCode
                      +"' AND UWTYPE = '"+ this.uwType +"' AND validstatus='1'";*/
        
        //取utiuwcondition表中的因子  减少系统循环次数 
        strFactorSql = "CLASSCODE = '" + this.classCode + "' AND UWTYPE = '" + this.uwType + "' AND validstatus='1"
        				+ "' AND FACTORCODE IN (select factorcode from utiuwcondition where " + strConditonSql + ")" ;
        
    	List uwFactorList = (List) dbUtiUwFactor.findByConditions(strFactorSql);
    	System.out.println("--查看险种因子《utiuwfactor表》strFactorSql====="+strFactorSql);
        for(int i = 0;i<uwFactorList.size();i++){
        	UtiUwFactorDto utiUwFactorDto = (UtiUwFactorDto)uwFactorList.get(i);
        	strFactorCode = utiUwFactorDto.getFactorCode();
        	strMultiSelectFlag=utiUwFactorDto.getMultiSelectFlag();
        	strFactorAttr = utiUwFactorDto.getFactorAttr();
    		strConditonLast = strConditonSql + "AND FACTORCODE = '" + strFactorCode +"'";
        	if(strMultiSelectFlag.equals("S")){  //标准为简单值，可以直接查询得到
            	java.util.List uwConditionList = dbUtiUwCondition.findFactorValueByConditions(strConditonLast);
            	if(uwConditionList.size()>0){
            	UtiUwConditionDto utiUwConditionDto = (UtiUwConditionDto)uwConditionList.get(0);
            	strFactorValue= utiUwConditionDto.getFactorValue();
            	if (!(strFactorAttr==null||strFactorAttr.equals(""))) {
            			if (strFactorAttr.equals("R")) {       //double   --实数
            				valuess[0] = Double.valueOf(strFactorValue);
            				partypes[0] = double.class;
            			}else if (strFactorAttr.equals("I")) {  //int     --整型
            				valuess[0] = Integer.valueOf(strFactorValue);
            				partypes[0] = int.class;
            			}else if (strFactorAttr.equals("S")) {  //string  --字符型
            				valuess[0] = String.valueOf(strFactorValue);
            				partypes[0] = String.class;
            			}else if (strFactorAttr.equals("B")) {  //boolean --布尔型
            				valuess[0] = Boolean.valueOf(strFactorValue);
            				partypes[0] = boolean.class;
            			}
            			/*
            			else if (strFactorAttr.equals("char")) {
            				char[] chara = ("").toCharArray();
            				valuess[0] = new Character(chara[0]);
            				partypes[0] = char.class;
            			} else if (strFactorAttr.equals("byte")) {
            				valuess[0] = Byte.valueOf(strStanderValue);
            				partypes[0] = byte.class;
            			} else if (strFactorAttr.equals("short")) {
            				valuess[0] = Short.valueOf(strStanderValue);
            				partypes[0] = short.class;
            			} else if (strFactorAttr.equals("long")) {
            				valuess[0] = Long.valueOf(strStanderValue);
            				partypes[0] = long.class;
            			} else if (strFactorAttr.equals("float")) {
            				valuess[0] = Float.valueOf(strStanderValue);
            				partypes[0] = float.class;
            			} else if (strFactorAttr.equals("double")) {
            				valuess[0] = Double.valueOf(strStanderValue);
            				partypes[0] = double.class;
            			} 
            			*/else {
            				valuess = values;
            				partypes = new Class[0];         //获得这个被调用的方法的类型
            				partypes[0] = Class.forName(strFactorAttr);
            			}
            		}
                Method math = cl.getMethod("set"+strFactorCode, partypes);//动态生成各个方法
                math.invoke(resultObject, valuess);                          //给生成的各个方法赋值
                heBaoConditionDto =(HeBaoConditionVo)resultObject;
            	}
        	}else if(strMultiSelectFlag.equals("C")){       //标准为非简单值，不能直接查询得到，组合值
        		strComboFactorSql = strFactorSql+" AND FactorCode = '"+strFactorCode+"'";
        		List uwComboFactorList = (List) dbUtiUwComboFactor.findByConditions(strComboFactorSql);
        		if(uwComboFactorList.size()>0){
                	UtiUwComboFactorDto utiUwComboFactorDto = (UtiUwComboFactorDto)uwComboFactorList.get(0);
                	strCodeType =utiUwComboFactorDto.getCodeType();
        		}
        		if(strCodeType.equals("UseNature")){
        			strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +useNature
        			                  +"%' AND RISKCODE = '"+this.riskCode+"'";//组合值的查询，现在只能这么查
        		}
        		if(strCodeType.equals("CarKind")){
        			strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +carKind
	                  				  +"%' AND RISKCODE = '"+this.riskCode+"'";//组合值的查询，现在只能这么查
        		}
        		if(strCodeType.equals("UsingYearLimit")){
        			strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +useNature
        			                  +"%' AND RISKCODE = '"+this.riskCode+"'";//组合值的查询，现在只能这么查
        			System.out.println("strConditonLast=="+strConditonLast);
        		}
        		if(strCodeType.equals("RiskClass")){
        			strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%:" +riskClass
        			                  +"%' AND RISKCODE = '"+this.riskCode+"'";//组合值的查询，现在只能这么查
        		}
        		//add by yanglibo 20081112 begin reason:增加国民经济行业的核保控制
        		if(strCodeType.equals("BusinessSource")){
        			strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +businessSource
        			                  +"%' AND RISKCODE = '"+this.riskCode+"'";//组合值的查询，现在只能这么查
        		}
        		//add by yanglibo 20081112 end
        		//add by yanglibo 20090504 begin reason:增加占有性质的核保控制
        		if(strCodeType.equals("PossessNature")){
        			strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +possessNature
        			                  +"%' AND RISKCODE = '"+this.riskCode+"'";//组合值的查询，现在只能这么查
        		}
        		//add by yanglibo 20090504 end
            	java.util.List uwConditionList = dbUtiUwCondition.findFactorValueByConditions(strConditonLast);
            	if(uwConditionList.size()>0){
            	UtiUwConditionDto utiUwConditionDto = (UtiUwConditionDto)uwConditionList.get(0);
            	//String[] factorValueAry = utiUwConditionDto.getFactorValue().split(";");
                String temp =utiUwConditionDto.getFactorValue();
            	strFactorValue 	= temp.substring((temp.lastIndexOf(";")+1),temp.length());//最后的标准
            	if (!(strFactorAttr==null||strFactorAttr.equals(""))) {
        			if (strFactorAttr.equals("R")) {       //double   --实数
        				valuess[0] = Double.valueOf(strFactorValue);
        				partypes[0] = double.class;
        			}else if (strFactorAttr.equals("I")) {  //int     --整型
        				valuess[0] = Integer.valueOf(strFactorValue);
        				partypes[0] = int.class;
        			}else if (strFactorAttr.equals("S")) {  //string  --字符型
        				valuess[0] = String.valueOf(strFactorValue);
        				partypes[0] = String.class;
        			}else if (strFactorAttr.equals("B")) {  //boolean --布尔型
        				valuess[0] = Boolean.valueOf(strFactorValue);
        				partypes[0] = boolean.class;
        			}
                    else {
        				valuess = values;
        				partypes = new Class[0]; //获得这个被调用的方法的类型
        				partypes[0] = Class.forName(strFactorAttr);
        			}
        		}
                Method math = cl.getMethod("set"+strFactorCode, partypes);//动态生成各个方法
                math.invoke(resultObject, valuess);                          //给生成的各个方法赋值
                heBaoConditionDto =(HeBaoConditionVo)resultObject;	
            	}
        		
        	} 

        }
        return heBaoConditionDto;
    }
    
    /**
	 * 由于可配置自动核保重写方法.
	 * 
	 * @param iBusinessNo
	 *            the i business no
	 * @param dbManager
	 *            the db manager
	 * @param iBusinessType
	 *            the i business type
	 * @param iComCode
	 *            機構代碼
	 * @param iModelNo
	 *            the i model no
	 * @param iNodeNo
	 *            the i node no
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
    public boolean initAutoUnderwriteStandard(String iBusinessNo,DBManager dbManager,String iBusinessType,String iComCode,int iModelNo,int iNodeNo) throws Exception{
    	this.modelNo = iModelNo;
    	this.nodeNo = iNodeNo;
            DBPrpTmain dbPrpTmain = new DBPrpTmain(dbManager);
            DBPrpCmain dbPrpCmain = new DBPrpCmain(dbManager);
            DBPrpPmain dbPrpPmain = new DBPrpPmain(dbManager);
            PrpCmainDto prpCmainDto = new PrpCmainDto();
            PrpPmainDto prpPmainDto = null;
            PrpTmainDto prpTmainDto = dbPrpTmain.findByPrimaryKey(iBusinessNo);
            this.makeCom = iComCode;
            if(prpTmainDto==null) {
            prpCmainDto = dbPrpCmain.findByPrimaryKey(iBusinessNo);
            prpPmainDto = dbPrpPmain.findByPrimaryKey(iBusinessNo);
            }
            if(prpTmainDto!=null){
                this.classCode = prpTmainDto.getClassCode();
                this.riskCode = prpTmainDto.getRiskCode();
                this.comCode = prpTmainDto.getComCode();
                this.policySort = prpTmainDto.getPolicySort();
                if(this.classCode.equals("A") ||this.classCode.equals("A"))//车险的各险别的保额与车辆的使用性质关联--特殊处理
                {
                	DBPrpTitemCar dbPrpTitemCar = new DBPrpTitemCar(dbManager);
                    PrpTitemCarDto prpTitemCarDto = dbPrpTitemCar.findByPrimaryKey(iBusinessNo,1);
                    this.useNature = prpTitemCarDto.getUseNatureCode();
  
                	
                }
                this.uwType = "Z";
            }else if(prpCmainDto!=null){
                this.classCode = prpCmainDto.getClassCode();
                this.riskCode = prpCmainDto.getRiskCode();
                this.comCode = prpCmainDto.getComCode();
                this.policySort = prpCmainDto.getPolicySort();
                this.uwType = "Z";
            }else if(prpPmainDto!=null){
                this.classCode = prpPmainDto.getClassCode();
                this.riskCode = prpPmainDto.getRiskCode();
                this.comCode = prpPmainDto.getComCode();
                this.policySort = prpPmainDto.getPolicySort();
                if(this.classCode.equals("A") || this.classCode.equals("A"))//车险的各险别的保额与车辆的使用性质关联--特殊处理
                {
                	DBPrpCPitemCar dbPrpCPitemCar = new DBPrpCPitemCar();	
                }
                this.uwType = "Z";
                System.out.println("this.uwType==="+this.uwType);
            }            
            else{
            	return false;
            }
		return true;
    }
    
    /**
	 * 獲取屬性the sinosoft auto hebao dto.
	 * 
	 * @param dbManager
	 *            the db manager
	 * @param autoUnderWriteFlag
	 *            the auto under write flag
	 * @return 屬性the sinosoft auto hebao dto的值
	 * @throws Exception
	 *             the exception
	 */
    public HeBaoConditionVo getAutoHebaoDto (DBManager dbManager,String autoUnderWriteFlag) throws Exception{
    	HeBaoConditionVo  heBaoConditionDto = new HeBaoConditionVo();
        cl=heBaoConditionDto.getClass();
        Object resultObject = cl.newInstance();
        Class[] partypes = new Class[1];

        Object[] valuess = new Object[1];
        Object[] values = new Object[1];
    	
    	DBUtiUwFactor  dbUtiUwFactor = new DBUtiUwFactor(dbManager);
    	DBUtiUwComboFactor dbUtiUwComboFactor = new DBUtiUwComboFactor(dbManager);
    	DBUtiUwCondition  dbUtiUwCondition = new DBUtiUwCondition(dbManager); 
    	DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
    	String  strConditonSql = "";
    	String  strFactorSql ="";
    	String  strComboFactorSql ="";
    	String  strConditonLast = "";
        String  strFactorCode = "";    //双核因子代码
    	String  strMultiSelectFlag ="";//因子直接取值还是间接
        String  strFactorAttr = "";    //因子在类里的取值类型
        String  strFactorValue = "";  //因子的标准值
        String  strCodeType = "";    //utiuwcombofactor表用的关联因子
        boolean blnIfExist = false;
        int resultNum = 0;
        String comCodeTemp = this.comCode;
        while (!blnIfExist)
        {    	     
            strConditonSql = " COMCODE ='"+ comCodeTemp
            +"' AND MODELNO = '"+ this.modelNo
            +"' AND NODENO = '"+ this.nodeNo
            +"' AND RISKCODE = '"+ this.riskCode
            +"' AND CLASSCODE = '"+ this.classCode
            +"' AND UWTYPE = '"+ this.uwType +"'";
             
            resultNum=dbUtiUwCondition.getCount(strConditonSql);
          //判断是否没有查找到信息，若是没有超到信息，并且此comcode在prpdcompany中comcode和uppercomcode一致则提示错误
          if(resultNum==0)
          {
            dbPrpDcompany.getInfo(comCodeTemp);
            if("2".equals(dbPrpDcompany.getComLevel())){
            	break;
            }
            //若是comcode＝uppercomcode则不在循环
            if (dbPrpDcompany.getComCode().equals(dbPrpDcompany.getUpperComCode())){ 
              blnIfExist=true;
            }
            else{
            	comCodeTemp=dbPrpDcompany.getUpperComCode();
            }
          }
          else
          {
            blnIfExist = true;
          }
        }
        //comCodeTemp是有数据的机构。
    	//查询utiuwfactor表
        strFactorSql = "CLASSCODE = '"+ this.classCode
    	              +"' AND UWTYPE = '"+ this.uwType+"'";   
    	List uwFactorList = (List) dbUtiUwFactor.findByConditions(strFactorSql);
        for(int i = 0;i<uwFactorList.size();i++){    
        	UtiUwFactorDto utiUwFactorDto = (UtiUwFactorDto)uwFactorList.get(i);
        	strFactorCode = utiUwFactorDto.getFactorCode();
        	strMultiSelectFlag=utiUwFactorDto.getMultiSelectFlag();
        	strFactorAttr = utiUwFactorDto.getFactorAttr();
    		strConditonLast = strConditonSql + "AND FACTORCODE = '" + strFactorCode +"'";
        	if(strMultiSelectFlag.equals("S")){             	//标准为简单值，可以直接查询得到
            	java.util.List uwConditionList = dbUtiUwCondition.findFactorValueByConditions(strConditonLast);
            	if(uwConditionList.size()>0){
            	UtiUwConditionDto utiUwConditionDto = (UtiUwConditionDto)uwConditionList.get(0);
            	strFactorValue= utiUwConditionDto.getFactorValue();
            	if (!(strFactorAttr==null||strFactorAttr.equals(""))) {
            			if (strFactorAttr.equals("R")) {       //double   --实数
            				valuess[0] = Double.valueOf(strFactorValue);
            				partypes[0] = double.class;
            			}else if (strFactorAttr.equals("I")) {  //int     --整型
            				valuess[0] = Integer.valueOf(strFactorValue);
            				partypes[0] = int.class;
            			}else if (strFactorAttr.equals("S")) {  //string  --字符型
            				valuess[0] = String.valueOf(strFactorValue);
            				partypes[0] = String.class;
            			}else if (strFactorAttr.equals("B")) {  //boolean --布尔型
            				valuess[0] = Boolean.valueOf(strFactorValue);
            				partypes[0] = boolean.class;
            			}else {
            				valuess = values;
            				partypes = new Class[0];         //获得这个被调用的方法的类型
            				partypes[0] = Class.forName(strFactorAttr);
            			}
            		}
                Method math = cl.getMethod("set"+strFactorCode, partypes);//动态生成各个方法
                math.invoke(resultObject, valuess);                          //给生成的各个方法赋值
                heBaoConditionDto =(HeBaoConditionVo)resultObject;
            	}
        	}
        if(strMultiSelectFlag.equals("C")){       //标准为非简单值，不能直接查询得到，组合值
        		strComboFactorSql = strFactorSql+" AND FactorCode = '"+strFactorCode+"'";
        		List uwComboFactorList = (List) dbUtiUwComboFactor.findByConditions(strComboFactorSql);
        		if(uwComboFactorList.size()>0){
                	UtiUwComboFactorDto utiUwComboFactorDto = (UtiUwComboFactorDto)uwComboFactorList.get(0);
                	strCodeType =utiUwComboFactorDto.getCodeType();
        		}
        		if(strCodeType.equals("UseNature")){
        			strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +useNature
        			                  +"%' AND RISKCODE = '"+this.riskCode+"'";//组合值的查询，现在只能这么查
        		}
        		if(strCodeType.equals("UsingYearLimit")){
        			strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +useNature
        			                  +"%' AND RISKCODE = '"+this.riskCode+"'";//组合值的查询，现在只能这么查
        		}
            	java.util.List uwConditionList = dbUtiUwCondition.findFactorValueByConditions(strConditonLast);
            	if(uwConditionList.size()>0){
            	UtiUwConditionDto utiUwConditionDto = (UtiUwConditionDto)uwConditionList.get(0);
            	//String[] factorValueAry = utiUwConditionDto.getFactorValue().split(";");
                String temp =utiUwConditionDto.getFactorValue();
            	strFactorValue 	= temp.substring((temp.lastIndexOf(";")+1),temp.length());//最后的标准
            	if (!(strFactorAttr==null||strFactorAttr.equals(""))) {
        			if (strFactorAttr.equals("R")) {       //double   --实数
        				valuess[0] = Double.valueOf(strFactorValue);
        				partypes[0] = double.class;
        			}else if (strFactorAttr.equals("I")) {  //int     --整型
        				valuess[0] = Integer.valueOf(strFactorValue);
        				partypes[0] = int.class;
        			}else if (strFactorAttr.equals("S")) {  //string  --字符型
        				valuess[0] = String.valueOf(strFactorValue);
        				partypes[0] = String.class;
        			}else if (strFactorAttr.equals("B")) {  //boolean --布尔型
        				valuess[0] = Boolean.valueOf(strFactorValue);
        				partypes[0] = boolean.class;
        			}
                    else {
        				valuess = values;
        				partypes = new Class[0]; //获得这个被调用的方法的类型
        				partypes[0] = Class.forName(strFactorAttr);
        			}
        		}
                Method math = cl.getMethod("set"+strFactorCode, partypes);//动态生成各个方法
                math.invoke(resultObject, valuess);                          //给生成的各个方法赋值
                heBaoConditionDto =(HeBaoConditionVo)resultObject;	
            	}
        		
        	} 
        }
        return heBaoConditionDto;
    }

	/**
	 * 獲取屬性要保書危險單位風險評估接口.
	 * 
	 * @return 屬性要保書危險單位風險評估接口的值
	 */
	public PrpTDangerRiskService getPrpTDangerRiskService() {
		return prpTDangerRiskService;
	}

	/**
	 * 設置屬性要保書危險單位風險評估接口.
	 * 
	 * @param prpTDangerRiskService
	 *            待設置的要保書危險單位風險評估接口的值
	 */
	public void setPrpTDangerRiskService(PrpTDangerRiskService prpTDangerRiskService) {
		this.prpTDangerRiskService = prpTDangerRiskService;
	}

    
}