package com.sinosoft.undwrt.common.util;

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
import com.sinosoft.product.blsvr.cb.BLPrpCproduct;
import com.sinosoft.product.blsvr.tb.BLPrpTproduct;
import com.sinosoft.product.schema.PrpCproductSchema;
import com.sinosoft.product.schema.PrpTproductSchema;
import com.sinosoft.prpall.blsvr.cb.BLPrpCPmainProp;
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
import com.sinosoft.undwrt.undwrtRule.vo.StandardVo;
import com.sinosoft.utiall.dbsvr.DBPrpDcompany;
import com.sinosoft.utiall.dbsvr.DBPrpDrisk;
import com.sinosoft.utiall.schema.PrpDriskSchema;

/**
 * The Class StandardAction.
 */
public class StandardAction {

    /** 屬性The sinosoft standardvo. */
    private StandardVo standardvo;
    
    /** 屬性要保書危險單位風險評估接口. */
    private PrpTDangerRiskService prpTDangerRiskService;
    
    /**
	 * 獲取屬性the sinosoft standardvo.
	 * 
	 * @return 屬性the sinosoft standardvo的值
	 */
    public StandardVo getStandardvo() {
        return standardvo;
    }

    /**
	 * 設置屬性the sinosoft standardvo.
	 * 
	 * @param standardvo
	 *            待設置的the sinosoft standardvo的值
	 */
    public void setStandardvo(StandardVo standardvo) {
        this.standardvo = standardvo;
    }

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
        standardvo.setModelNo(iModelNo);
        standardvo.setNodeNo(iNodeNo);
        if (iBusinessType.equals("proposal")) {
            DBPrpTmain dbPrpTmain = new DBPrpTmain(dbManager);
            PrpTmainDto prpTmainDto = dbPrpTmain.findByPrimaryKey(iBusinessNo);
            if(prpTmainDto!=null){
                standardvo.setClassCode(iComCode);
                standardvo.setRiskCode(prpTmainDto.getRiskCode());
                standardvo.setComCode(prpTmainDto.getComCode());
                standardvo.setPolicySort(prpTmainDto.getPolicySort());
                if(standardvo.getClassCode().equals("A")|| standardvo.getClassCode().equals("B"))//车险的各险别的保额与车辆的使用性质关联--特殊处理
                {
                    DBPrpTitemCar dbPrpTitemCar = new DBPrpTitemCar(dbManager);
                    PrpTitemCarDto prpTitemCarDto = dbPrpTitemCar.findByPrimaryKey(iBusinessNo,1);
                    standardvo.setUseNature(prpTitemCarDto.getUseNatureCode());
                    standardvo.setCarKind(prpTitemCarDto.getCarKindCode());
                    
                }
                if(standardvo.getClassCode().equals("01")||standardvo.getClassCode().equals("03")||standardvo.getClassCode().equals("07"))//非车险各险别的保额与拆分的危险单位有关
                {
                    PrpTDangerRisk prpTdangerRiskDto = new PrpTDangerRisk ();
                    String riskClass = "";
                    QueryRule queryrule=QueryRule.getInstance();
                    queryrule.addEqual("proposalno", iBusinessNo);
                    Collection wfLogList = new ArrayList();
                    wfLogList=(Collection)prpTDangerRiskService.findByConditions(queryrule);
                    Iterator prpTdangerRisk = wfLogList.iterator();
                    if(prpTdangerRisk.hasNext()){
                        prpTdangerRiskDto = (PrpTDangerRisk) prpTdangerRisk.next();
                        standardvo.setRiskClass(prpTdangerRiskDto.getRiskClass());
                    }
                    
                }
                if(standardvo.getRiskCode().equals("0999")||standardvo.getRiskCode().equals("1598")||standardvo.getRiskCode().equals("1599")
                ||standardvo.getRiskCode().equals("2399")||standardvo.getRiskCode().equals("2798")||standardvo.getRiskCode().equals("2799"))
                {//2799要不要去掉？？？
                    BLPrpTproduct blPrpTproduct = new BLPrpTproduct();
                    blPrpTproduct.getData(iBusinessNo); 
                    if(blPrpTproduct.getSize()>0)
                    {
                      PrpTproductSchema prpTproductSchema = blPrpTproduct.getArr(0);
                      standardvo.setProductcode(prpTproductSchema.getProductCode());
                    }
                }
                //add by yanglibo 20081112 begin reason: 增加以下险种的国民行业核保控制
                //add by yanglibo 20090504 begin reason: 增加以下险种的占有性质核保控制
                if(standardvo.getRiskCode().equals("0101")||standardvo.getRiskCode().equals("0102")||standardvo.getRiskCode().equals("0103")
                        ||standardvo.getRiskCode().equals("0104")||standardvo.getRiskCode().equals("0122")||standardvo.getRiskCode().equals("0123")
                        ||standardvo.getRiskCode().equals("0124")){
                    BLPrpTmainProp blPrpTmainProp = new BLPrpTmainProp();
                    blPrpTmainProp.getData(iBusinessNo);    
                    if(blPrpTmainProp.getSize()>0)
                    {
                        PrpTmainPropSchema prpTmainPropSchema = blPrpTmainProp.getArr(0);
                        standardvo.setBusinessSource(prpTmainPropSchema.getBusinessSource().substring(0, prpTmainPropSchema.getBusinessSource().length()-2));
                        standardvo.setPossessNature(prpTmainPropSchema.getPossessNature());
                    }
                }
                //add by yanglibo 20081112 end
                //add by yanglibo 20090504 end reason: 增加以下险种的占有性质核保控制
                standardvo.setUwType("P");//投保单\保单\批单核保的合并处理
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
                standardvo.setClassCode(prpCmainDto.getClassCode());
                standardvo.setRiskCode(prpCmainDto.getRiskCode());
                standardvo.setComCode(prpCmainDto.getComCode());
                standardvo.setPolicySort(prpCmainDto.getPolicySort());
                standardvo.setUwType("P");
            }else if (prpCmainCovernoteDto!=null){
                standardvo.setClassCode(prpCmainCovernoteDto.getClassCode());
                standardvo.setRiskCode(prpCmainCovernoteDto.getRiskCode());
                standardvo.setComCode(prpCmainCovernoteDto.getComCode());
                standardvo.setPolicySort(prpCmainCovernoteDto.getPolicySort());
                standardvo.setUwType("P");
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
                standardvo.setClassCode(prpPmainDto.getClassCode());
                standardvo.setRiskCode(prpPmainDto.getRiskCode());
                standardvo.setComCode(prpPmainDto.getComCode());
                standardvo.setPolicyno(prpPmainDto.getPolicyNo());
                standardvo.setPolicySort(prpPmainDto.getPolicySort());
                if(standardvo.getClassCode().equals("A") || standardvo.getClassCode().equals("B"))//车险的各险别的保额与车辆的使用性质关联--特殊处理
                {
                    DBPrpCPitemCar dbPrpCPitemCar = new DBPrpCPitemCar();
                    //PrpCPitemCarDto prpCPitemCarDto = DBPrpCPitemCar.findByPrimaryKey(iBusinessNo,1);
                    String strSQL = "select * from PrpCPitemCar where policyno='"+standardvo.getPolicyno()+"'";
                    ResultSet rsPolicyno = dbManager.executeQuery(strSQL);
                    if(rsPolicyno.next()){
                        standardvo.setUseNature(rsPolicyno.getString("useNatureCode"));
                        standardvo.setCarKind(rsPolicyno.getString("carKindCode"));
                    }
                    rsPolicyno.close();
                    
                }
                //add by yanglibo 20081112 begin reason: 增加以下险种的国民行业核保控制
                //add by yanglibo 20090504 begin reason: 增加以下险种的占有性质核保控制
                if(standardvo.getRiskCode().equals("0101")||standardvo.getRiskCode().equals("0102")||standardvo.getRiskCode().equals("0103")
                        ||standardvo.getRiskCode().equals("0104")||standardvo.getRiskCode().equals("0122")||standardvo.getRiskCode().equals("0123")
                        ||standardvo.getRiskCode().equals("0124")){
                    BLPrpCPmainProp blPrpCPmainProp = new BLPrpCPmainProp();
                    blPrpCPmainProp.getData(prpPmainDto.getPolicyNo()); 
                    if(blPrpCPmainProp.getSize()>0)
                    {
                        PrpCPmainPropSchema prpCPmainPropSchema = blPrpCPmainProp.getArr(0);
                      standardvo.setBusinessSource(prpCPmainPropSchema.getBusinessSource().substring(0, prpCPmainPropSchema.getBusinessSource().length()-2));
                      standardvo.setPossessNature(prpCPmainPropSchema.getPossessNature());
                    }
                }
                //add by yanglibo 20081112 end
                //add by yanglibo 20090504 end reason: 增加以下险种的占有性质核保控制
                if(standardvo.getRiskCode().equals("0999")||standardvo.getRiskCode().equals("1598")||standardvo.getRiskCode().equals("1599")
                        ||standardvo.getRiskCode().equals("2399")||standardvo.getRiskCode().equals("2798")||standardvo.getRiskCode().equals("2799"))
                {
                    BLPrpCproduct blPrpCproduct = new BLPrpCproduct();
                    blPrpCproduct.getData(prpPmainDto.getPolicyNo());
                    if(blPrpCproduct.getSize()>0)
                    {
                      PrpCproductSchema prpCproductSchema = blPrpCproduct.getArr(0);
                      standardvo.setProductcode(prpCproductSchema.getProductCode());
                    }
                }
                standardvo.setUwType("P");
            }else{
                return false;
            }

        }
        else
        {
            System.out.println("无此业务类型："+iBusinessType);
            return false;
        }
        if(standardvo.getComCode().equals(""))
        {
            standardvo.setComCode("0000000000");
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
        standardvo.setCl(heBaoConditionDto.getClass());
        Object resultObject = standardvo.getCl().newInstance();
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
        String comCodeTemp = standardvo.getComCode();
        //modify by liuwei begin 20090512 1598停车场责任险产品个性化双核条件配置 
        //根据prpdrisk表riskflag第十四位标志位"T"判断该险种下是否有产品代码
        DBPrpDrisk  dbPrpDrisk = new DBPrpDrisk();
        PrpDriskSchema prpDriskSchema = null;
        Vector vector = new Vector();
        boolean isRemark = false;
        String conditionSql = " Select * From prpdrisk Where riskcode = '"+standardvo.getRiskCode()+"'  ";
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
                    && ("00000054".equals(standardvo.getProductcode()) 
                            || "15980001".equals(standardvo.getProductcode()))
                    && "1598".equals(standardvo.getRiskCode())){
            strConditonSql = " COMCODE ='"+ comCodeTemp
            +"' AND MODELNO = '"+ standardvo.getModelNo()
            +"' AND NODENO = '"+ standardvo.getNodeNo()
            +"' AND RISKCODE = '"+ standardvo.getProductcode()  //如果险种下有产品,则配置产品代码.
            +"' AND CLASSCODE = '"+ standardvo.getClassCode()
            +"' AND UWTYPE = '"+ standardvo.getUwType()
            +"'";
            }
            // modify by liuwei end 20090512  
            else {
                strConditonSql = " COMCODE ='"+ comCodeTemp
                +"' AND MODELNO = '"+ standardvo.getModelNo()
                +"' AND NODENO = '"+ standardvo.getNodeNo()
                +"' AND RISKCODE = '"+ standardvo.getRiskCode()
                +"' AND CLASSCODE = '"+ standardvo.getClassCode()
                +"' AND UWTYPE = '"+ standardvo.getUwType()
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
        strFactorSql = "CLASSCODE = '" + standardvo.getClassCode() + "' AND UWTYPE = '" + standardvo.getUwType() + "' AND validstatus='1"
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
                      else {
                            valuess = values;
                            partypes = new Class[0];         //获得这个被调用的方法的类型
                            partypes[0] = Class.forName(strFactorAttr);
                        }
                    }
                Method math = standardvo.getCl().getMethod("set"+strFactorCode, partypes);//动态生成各个方法
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
                    strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +standardvo.getUseNature()
                                      +"%' AND RISKCODE = '"+standardvo.getRiskCode()+"'";//组合值的查询，现在只能这么查
                }
                if(strCodeType.equals("CarKind")){
                    strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +standardvo.getCarKind()
                                      +"%' AND RISKCODE = '"+standardvo.getRiskCode()+"'";//组合值的查询，现在只能这么查
                }
                if(strCodeType.equals("UsingYearLimit")){
                    strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +standardvo.getUseNature()
                                      +"%' AND RISKCODE = '"+standardvo.getRiskCode()+"'";//组合值的查询，现在只能这么查
                    System.out.println("strConditonLast=="+strConditonLast);
                }
                if(strCodeType.equals("RiskClass")){
                    strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%:" +standardvo.getRiskClass()
                                      +"%' AND RISKCODE = '"+standardvo.getRiskCode()+"'";//组合值的查询，现在只能这么查
                }
                //add by yanglibo 20081112 begin reason:增加国民经济行业的核保控制
                if(strCodeType.equals("BusinessSource")){
                    strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +standardvo.getBusinessSource()
                                      +"%' AND RISKCODE = '"+standardvo.getRiskCode()+"'";//组合值的查询，现在只能这么查
                }
                //add by yanglibo 20081112 end
                //add by yanglibo 20090504 begin reason:增加占有性质的核保控制
                if(strCodeType.equals("PossessNature")){
                    strConditonLast = strConditonLast+ "AND FACTORVALUE LIKE '%" +standardvo.getPossessNature()
                                      +"%' AND RISKCODE = '"+standardvo.getRiskCode()+"'";//组合值的查询，现在只能这么查
                }
                //add by yanglibo 20090504 end
                java.util.List uwConditionList = dbUtiUwCondition.findFactorValueByConditions(strConditonLast);
                if(uwConditionList.size()>0){
                UtiUwConditionDto utiUwConditionDto = (UtiUwConditionDto)uwConditionList.get(0);
                //String[] factorValueAry = utiUwConditionDto.getFactorValue().split(";");
                String temp =utiUwConditionDto.getFactorValue();
                strFactorValue  = temp.substring((temp.lastIndexOf(";")+1),temp.length());//最后的标准
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
                Method math = standardvo.getCl().getMethod("set"+strFactorCode, partypes);//动态生成各个方法
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
