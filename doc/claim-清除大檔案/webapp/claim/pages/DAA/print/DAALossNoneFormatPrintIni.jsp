<%--
****************************************************************************
* DESC       ：机动车辆保险定损报告/明细表打印初始化
* AUTHOR     ：zhulianyu
* CREATEDATE ：2005-11-15
* MODIFYLIST ：   Name       Date            Reason/Contents
--------------------------------------------------------------------------
****************************************************************************
--%>

<%-- 引入bean类部分 --%> 
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.claim.ui.control.action.*"%>
<%@page import="com.sinosoft.claim.dto.custom.*"%>
<%@page import="com.sinosoft.claim.dto.domain.*"%>
<%@page import="com.sinosoft.claim.util.*"%>
<%@page import="com.sinosoft.sysframework.common.util.*"%>
<%@page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<%@page import="com.sinosoft.sysframework.exceptionlog.*"%>
<%@page import="com.sinosoft.claim.dto.custom.RepairContentDto"%>
<%@page import="com.sinosoft.prpall.blsvr.lp.BLPrpLrepairFee"%>
<%@page import="com.sinosoft.prpall.schema.PrpLrepairFeeSchema"%>
<%@page import="com.sinosoft.prpall.blsvr.cb.BLPrpCitemKind"%>
<%@page import="com.sinosoft.prpall.schema.PrpCitemKindSchema"%>

<%

	
  //变量声明部分
  String printType         = request.getParameter("printType"); 
  String licenseNo         = request.getParameter("strLicenseNo");
//  //System.out.println("oooooooo00000000000000车牌号ooooooo==="+licenseNo);
  String  engineNo="" ;
  String  frameNo="" ;
  String  brandName="" ;
  Date printDate = new Date();
  
  //String strClaimNo         = request.getParameter("ClaimNo"); //赔案号
  String strPolicyNo        = ""  ;   //保单号
  String strLicenseNo       = ""  ;   //号牌号码
  String strLicenseNo1      = ""  ;   //号牌号码
  String strFrameNo         = ""  ;   //车架号
  String strDamageStartDate = ""  ;   //出险时间
  String strDamageStartHour = ""  ;   //出险小时
  String strInsuredName     = ""  ;   //被保险人
  String strBrandName       = ""  ;   //厂牌型号
  String strEngineNo        = ""  ;   //发动机号
  String strKindCode        = ""  ;   //险别条款
  String strIndemnityDuty   = ""  ;   //事故责任
  String strEnrollDate      = ""  ;   //初登日期
  double douExhaustScale    = 0   ;   //排气量
  String strSafeDevice      = ""  ;   //安全装置
  String strRepairStartDate = ""  ;   //送修时间
  String strRepairEndDate   = ""  ;   //修复竣工时间
  String strInsureCarFlag   = ""  ;   //是否为本保单车辆
  double [] dblRepSumDefLoss      ;   //换件项目总金额
  double [] dblComSumDefLoss      ;   //修理项目总金额
  double [] dblProSumReject       ;   //残值作价金额
  String [] strRepSumDefLoss1     ;   //
  String [] strComSumDefLoss1     ;   //
  String [] strProSumReject1      ;   //
  String [] strRepSumDefLoss2     ;   //
  String [] strComSumDefLoss2     ;   //
  String [] strProSumReject2      ;   //
  String [] strRepContext         ;   //换件项目总金额（中文描述）
  String [] strComContext         ;   //修理项目总金额（中文描述）
  String [] strProContext         ;   //残值作价金额（中文描述）
  int    [] intCount              ;
  String strGearboxType       = "";   //
  String strGearboxName       = "";   //
  String strQuoteCompanyGrade = "";   //
  String strQuoteCompanyName  = "";   //
  String strMessage           = "";
  String strCurrency          = "";   //币别
//  String strMainKindName      = "";   //主险险别
  String strHandlerName       = "";   //查勘定损人
  String strInsureComName     = "" ; //承保公司
  //caopeng11.22
  String [] strCompName			  ;   //更换配件名
  String [] strCompName1		  ;   //修理配件名
  String [] strRepairType		  ;   //修理项目
  double [] dblMaterialQuantity  ;   //数量
  double [] dblMaterialUnitPrice ;   //配件价格
  double [] dblManHourFee		  ;   //工时费
  double [] dblSumManHourFee	  ;   //工时费小计
  double [] dblSumMaterialFee    ;   //材料费小计
  double [] dblSumMQuantity      ;   //数量小计
  double    dblSumMaterialFeeTemp  = 0.0;
  int       inttemp           = 0;
  //caopeng11.22
  
  //liuyanmei  start
   double[] dblManHour ;
   double[]  dblMaterialFee ;
   // strSanctioner  strApproverName
   String[] dblRemark ;
  
  //liuyanmei end 
	
	
  //代码翻译变量
  

  String strCode    = "";
  String strName    = "";
  boolean isChinese = true; //中文标志

  //对象定义部分

  PrpCitemCarDto    prpItemcarDto       = null;   //保单的ItemcarDto对象
  PrpLclaimDto       prpLclaimDto       = null;   //ClaimDto对象
  PrpLthirdPartyDto  prpLthirdPartyDto  = null;   //ThirdPartyDto对象
  PrpLcheckDto       prpLcheckDto       = null;   //checkDto对象
  PrpLpropDto        prpLpropDto        = null;   //propDto对象
  PrpLcomponentDto   prpLcomponentDto   = null;   //ComponentDto对象
  PrpLrepairFeeDto   prpLrepairFeeDto   = null;   //RepairFeeDto对象
  //add by miaowenjun 20060403
  PrpLverifyLossDto prpLverifyLossDto = null;
  //add end by miaowenjun 20060403


  int intClaimCount      = 0;   //ClaimDto对象的记录数
  int intItemCarCount    = 0;   //itemCarDto对象的记录数
  int intThirdPartyCount = 0;   //ThirdPartyDto对象的记录数
  int intCheckCount      = 0;   //CheckDto对象的记录数
  int intPropCount       = 0;   //PropDto对象的记录数
  int intComponentCount  = 0;   //ComponentDto对象的记录数
  int intRepairFeeCount  = 0;   //RepairFeeDto对象的记录数
  int intCarLossCount    = 0;   //add by miaowenjun 20060406
  
  //add by miaowenjun 20060406
  double sumhour1 = 0;
  double sumhour2 = 0;
  double sumhour3 = 0;
  double sumhour4 = 0;
  double sumhour5 = 0;
  double sumhourfee1 = 0;
  double sumhourfee2 = 0;
  double sumhourfee3 = 0;
  double sumhourfee4 = 0;
  double sumhourfee5 = 0;
   String sumRest = "0.0";
  int count1 = 0;
  int count2 = 0;
  int count3 = 0;
  int count4 = 0;
  int count5 = 0;
  PrpLcarLossDto prpLcarLossDto = null;
  double[] sumTransFee;
  double[] sumTax;
  double[] sumManager;
  String strSumAllFee = "";
  //add end by miaowenjun 20060406
  
  double[] sumFloatRate;//add by zhyi 浮動比例
  
  //add by zhyi fubon-2206 start
  int pageCount = 56;
  int intBaseCount = 28;
  //add by zhyi fubon-2206 end
  
  
  //add by miaowenjun 20060328
  double firstsumhour = 0;
  double firstsumhourfee = 0;
  double secondsumhour = 0;
  double secondsumhourfee = 0;
  double sumhour = 0;
  double sumhourfee = 0;
  List repairListInfo = new ArrayList();
  int repaircount = 0;
  int pageNo = 0;;
  int pageTotal = 0;;
  String strApproverName = "";
  Date verpDate = new Date();
  //add end by miaowenjun 20060328
  
  //add by miaowenjun 20060405
  int changecount = 0;
  int changePageNo = 0;
  int changePageTotal = 0;
  List changeListInfo = new ArrayList();
  double firstchangefee = 0;
  double secondchangefee = 0;
  double sumchangefee = 0;
  //add end by miaowenjun 20060405

  int index = 0;

  //得到ClaimDto,RegistDto,CertainLossDto,PolicyDto对象
 
  if( ! printType.equals("ComponentList")  ) {
  ClaimDto claimDto = (ClaimDto)request.getAttribute("claimDto");
  }
  
  PolicyDto policyDto = (PolicyDto)request.getAttribute("policyDto");
  RegistDto registDto = (RegistDto)request.getAttribute("registDto");
  CheckDto checkDto = (CheckDto)request.getAttribute("checkDto");
  CertainLossDto certainLossDto = (CertainLossDto)request.getAttribute("certainLossDto");
  

  UICodeAction uiCodeAction = new UICodeAction();
  //得到保单号
  //strPolicyNo = claimDto.getPrpLclaimDto().getPolicyNo();  
  strPolicyNo = registDto.getPrpLregistDto().getPolicyNo(); 
  ////System.out.println("保单号码是:"+strPolicyNo);
  PrpCitemKindDto prpCitemKindDto = new PrpCitemKindDto();
  
  PrpCmainSubDto prpCmainSubDto = new PrpCmainSubDto(); 
  
  int intPrpCitemKind = 0;
  String[] strKindName = new String[3];
  strKindName[0] = "";
  strKindName[1] = "";
  strKindName[2] = "";
  String[] strMainKindName = new String[4]; //注释：add by lixiang：表示主险名称，因为国寿财是4个主险，所以这里修改成4 
  strMainKindName[0] = "";
  strMainKindName[1] = "";
    int k = 0 ; 
  intPrpCitemKind = policyDto.getPrpCitemKindDtoList().size();
  //得到子险信息
  if(policyDto.getPrpCitemKindDtoList()!=null){
    int temp = 0;
////System.out.println("测试step0============="+intPrpCitemKind);

  for(index=0;index<intPrpCitemKind;index++)
  {
    prpCitemKindDto = (PrpCitemKindDto)policyDto.getPrpCitemKindDtoList().get(index);
    //add by zhyi 20110826 fubon-2206
     prpCmainSubDto = (PrpCmainSubDto)policyDto.getPrpCmainSubDtoList().get(0);
   
    //System.out.println("prpCitemKindDto.getFlag("+index+"):"+prpCitemKindDto.getFlag());
    if(prpCitemKindDto.getFlag().substring(1,2).equals("1"))
    {
       //strMainKindName += "  "+prpCitemKindDto.getKindName();
////System.out.println("开始复制："+k+ prpCitemKindDto.getKindName());
        //strMainKindName[k]= prpCitemKindDto.getKindName();
//        //System.out.println("复制结束");
        ////System.out.println("strMainKindName[k]=="+ k +strMainKindName[k]);
        k++;
    }
    if(prpCitemKindDto.getFlag().substring(1,2).equals("2")&&temp<3)
    {
    strKindName[temp] = prpCitemKindDto.getKindName();
    temp++;
    }
  }
}
////System.out.println("测试step--end=============");

  //得到blPrpCitemCar对象
  if(policyDto.getPrpCitemCarDtoList()!=null){
    intItemCarCount     = policyDto.getPrpCitemCarDtoList().size();
  }
	//add by zhyi 20110826 fubon-2206　取得交強險承保公司
  String strRemark = prpCmainSubDto.getRemark();

  //得到prpLclaimDto对象
  // prpLclaimDto   = claimDto.getPrpLclaimDto();
  //String strRegistNo = prpLclaimDto.getRegistNo();
   PrpLregistDto prpLregistDto =registDto.getPrpLregistDto();
   
  String strRegistNo = prpLregistDto.getRegistNo(); 
  //add by zhyi  start
   BLPrpLrepairFee blPrpLrepairFee = new BLPrpLrepairFee();
   BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
   String kindCode = "";
   String policyNo = "";
   String kindName = "";
   blPrpLrepairFee.query(" registNo='"+strRegistNo+"'");
   if(blPrpLrepairFee.getSize()>0){
   		kindCode = blPrpLrepairFee.getArr(0).getKindCode();
   		policyNo = blPrpLrepairFee.getArr(0).getPolicyNo();
   		blPrpCitemKind.query(" policyNo='"+policyNo+"' and kindcode='"+kindCode+"'");
   		kindName = blPrpCitemKind.getArr(0).getKindName();
   	}
   	//add by zhyi  end
  //得到赔款币别
  //strCurrency = prpLregistDto.getCarKindCode();

  //得到blPrpLthirdParty对象的记录数
  if(registDto.getPrpLthirdPartyDtoList()!=null){
    intThirdPartyCount     = registDto.getPrpLthirdPartyDtoList().size();
  }

  //得到blPrpLcheck对象的记录数
  prpLcheckDto = checkDto.getPrpLcheckDto();

  //得到blPrpLprop对象的记录数
  if(certainLossDto.getPrpLpropDtoList()!=null){
    intPropCount     = certainLossDto.getPrpLpropDtoList().size();
  }

  //得到blPrpLcomponent对象的记录数
  if(certainLossDto.getPrpLcomponentDtoList()!=null){
    intComponentCount     = certainLossDto.getPrpLcomponentDtoList().size();
  }
  
  //add by miaowenjun 20060406
  if(certainLossDto.getPrpLcarLossDtoList() != null){
  	intCarLossCount = certainLossDto.getPrpLcarLossDtoList().size();
  }
  //add end by miaowenjun 20060406

  //得到blPrpLrepairFee对象的记录数
  if(certainLossDto.getPrpLrepairFeeDtoList()!=null){
    intRepairFeeCount     = certainLossDto.getPrpLrepairFeeDtoList().size();
    //System.out.println("-----------------个数 =="+intRepairFeeCount);
  }


//engineNo =new String[intThirdPartyCount];
//frameNo =new String[intThirdPartyCount];
//brandName =new String[intThirdPartyCount];


   dblRepSumDefLoss   = new double[intThirdPartyCount];
   dblComSumDefLoss   = new double[intThirdPartyCount];
   dblProSumReject    = new double[intThirdPartyCount];
   strRepSumDefLoss1  = new String[intThirdPartyCount];
   strComSumDefLoss1  = new String[intThirdPartyCount];
   strProSumReject1   = new String[intThirdPartyCount];
   strRepSumDefLoss2  = new String[intThirdPartyCount];
   strComSumDefLoss2  = new String[intThirdPartyCount];
   strProSumReject2   = new String[intThirdPartyCount];
   strRepContext      = new String[intThirdPartyCount];
   strComContext      = new String[intThirdPartyCount];
   strProContext      = new String[intThirdPartyCount];
   intCount           = new int[intThirdPartyCount];
   
   //caopeng11.22
   strCompName        = new String[intComponentCount];
   dblMaterialQuantity  = new double[intComponentCount];
   dblMaterialUnitPrice = new double[intComponentCount];
   strRepairType      = new String[intRepairFeeCount];
   strCompName1       = new String[intRepairFeeCount];
   dblManHourFee      = new double[intRepairFeeCount];
   dblManHour      = new double[intRepairFeeCount];
   dblMaterialFee = new double[intRepairFeeCount];
   dblRemark = new String[intRepairFeeCount];
   dblSumManHourFee   = new double[intThirdPartyCount];
   dblSumMaterialFee  = new double[intThirdPartyCount];
   dblSumMQuantity    = new double[intThirdPartyCount];
   sumTransFee        = new double[intCarLossCount];
   sumTax             = new double[intCarLossCount];
   sumManager         = new double[intCarLossCount];
   
   //caopeng11.22
   
   sumFloatRate = new double[intRepairFeeCount];//add by zhyi 浮動比例
  
   for(index=0;index<intThirdPartyCount;index++)
   {
      dblRepSumDefLoss [index] = 0.0;
      dblComSumDefLoss [index] = 0.0;
      dblProSumReject  [index] = 0.0;
      strRepSumDefLoss1[index] = "";
      strComSumDefLoss1[index] = "";
      strProSumReject1 [index] = "";
      strRepSumDefLoss2[index] = "";
      strComSumDefLoss2[index] = "";
      strProSumReject2 [index] = "";
      strRepContext    [index] = "";
      strComContext    [index] = "";
      strProContext    [index] = "";
      intCount         [index] = 0;
      //caopeng11.22
      dblSumManHourFee [index] = 0.0;
      dblSumMaterialFee[index] = 0.0;
      dblSumMQuantity  [index] = 0.0;
      //caopeng11.22
   }
      //caopeng11.22
   for(index=0;index<intComponentCount;index++){
      strCompName      [index] = "";
      dblMaterialQuantity [index] = 0.0;
      dblMaterialUnitPrice[index] = 0.0;
   }
   for(index=0;index<intRepairFeeCount;index++){
      strRepairType       [index] = "";
      strCompName1        [index] = "";
      dblManHourFee    [index] = 0.0;
      dblManHour  [index] =0.0; 
     
   }
      //caopeng11.22
   

%>

<script language="javascript">
  //add by liuwei at 2011-02-11 打印附页 start
  function printPageAdd(){
    var url="/claim/ClaimPrint.do?printType=ComponentListPrint&RegistNo=<%=request.getAttribute("registNo")%>&ClaimNo=<%=request.getAttribute("claimNo")%>&lossItemCode=<%=request.getAttribute("lossItemCode")%>&strLicenseNo=<%=request.getAttribute("strLicenseNo")%>";
    var newWindow = window.open(url,"NewWindow","width=600,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
  //add by liuwei at 2011-02-11 打印附页 end
   //add by zhyi at 20110901 修理項目清單的打印　start
  function printPageAdd1(){
    var url="/claim/ClaimPrint.do?printType=RepairListPrint&RegistNo=<%=request.getAttribute("registNo")%>&ClaimNo=<%=request.getAttribute("claimNo")%>&lossItemCode=<%=request.getAttribute("lossItemCode")%>&strLicenseNo=<%=request.getAttribute("strLicenseNo")%>";
    var newWindow = window.open(url,"NewWindow","width=600,height=500,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
  //add by zhyi at 20110901 修理項目清單的打印　end
  function loadForm()
  {
 

<%
    strInsuredName=StringConvert.encode(prpLregistDto.getInsuredName());
    //承保公司
    strCode = "";
    strName = "";
    strCode = StringConvert.encode(prpLregistDto.getComCode());
    strName = uiCodeAction.translateComCode(strCode,isChinese);

    strDamageStartDate = prpLregistDto.getDamageStartDate().toString();
    strDamageStartHour = prpLregistDto.getDamageStartHour().toString();
    strDamageStartDate = strDamageStartDate.substring(0,4)+"年"+strDamageStartDate.substring(5,7)+"月"+strDamageStartDate.substring(8,10)+"日"+strDamageStartHour.substring(0,2)+"时";
    //事故责任
    strCode = "";
    strName = "";
    strCode = StringConvert.encode(prpLregistDto.getIndemnityDuty());
    strName = uiCodeAction.translateCodeCode("IndemnityDuty",strCode,isChinese);
    strIndemnityDuty = strName;


	//add by miaowenjun 20060403	
	prpLverifyLossDto = certainLossDto.getPrpLverifyLossDto();
	
	if(prpLverifyLossDto != null){			
		strApproverName =  uiCodeAction.translateUserCode(prpLverifyLossDto.getVerpApproverCode(),true);
      	verpDate = prpLverifyLossDto.getVerpDate();
	}
 	 //add end by miaowenjun 20060403
	
    //*****理赔车辆信息PrpLthirdParty*****

  if(registDto.getPrpLthirdPartyDtoList()!=null){
    for(index=0;index<intThirdPartyCount;index++)
    { strInsureComName = ((PrpLthirdPartyDto)registDto.getPrpLthirdPartyDtoList().get(0)).getInsureComName();
   
      prpLthirdPartyDto = (PrpLthirdPartyDto)registDto.getPrpLthirdPartyDtoList().get(index);
      strInsureCarFlag     = prpLthirdPartyDto.getInsureCarFlag();

      if( strInsureCarFlag.equals("1") )
      {
        strLicenseNo = prpLthirdPartyDto.getLicenseNo();
        strBrandName = prpLthirdPartyDto.getBrandName();
        strEngineNo  = prpLthirdPartyDto.getEngineNo();
        strFrameNo   = prpLthirdPartyDto.getFrameNo();

        //条款类别
        strCode = "";
        strName = "";
        strCode = StringConvert.encode(prpLthirdPartyDto.getClauseType());
        strName = uiCodeAction.translateCodeCode("ClauseType",strCode,isChinese);
        strKindCode =  strName;
      }
    }
  }

 
  
  if(policyDto.getPrpCitemCarDtoList()!=null){
    //*****机动车险标的信息表PrpCitemCar*****
    for(index=0;index<intItemCarCount;index++)
    {
      prpItemcarDto = (PrpCitemCarDto)policyDto.getPrpCitemCarDtoList().get(index);
      strLicenseNo1 = StringConvert.encode(prpItemcarDto.getLicenseNo());

      if( strLicenseNo1.equals(strLicenseNo) )
      {
        if(prpItemcarDto.getVINNo() != ""){
            strFrameNo = strFrameNo+ prpItemcarDto.getVINNo();
         }
         //取初登日期,暂时取初登日期为出生产年月
         strEnrollDate = prpItemcarDto.getEnrollDate().toString();
         strEnrollDate = strEnrollDate.substring(0,4)+"年"+strEnrollDate.substring(5,7)+"月"+strEnrollDate.substring(8,10)+"日";
         //排气量
         douExhaustScale = prpItemcarDto.getExhaustScale();
         //安全装置(应该做代码翻译)
         strSafeDevice   = prpItemcarDto.getSafeDevice();
         java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(strSafeDevice);
         while(tokenizer.hasMoreTokens())
         {
            strSafeDevice = tokenizer.nextToken(",");
            strSafeDevice  += uiCodeAction.translateCodeCode("SafeDevice",strSafeDevice,true)+" ";
            //System.out.println("安全装置"+strSafeDevice);
         }
      }
    }
  }

  if(certainLossDto.getPrpLrepairFeeDtoList()!=null){
    for(index=0;index<intRepairFeeCount;index++)
    {
      prpLrepairFeeDto = (PrpLrepairFeeDto)certainLossDto.getPrpLrepairFeeDtoList().get(index);
      if(strLicenseNo.equals(prpLrepairFeeDto.getLicenseNo()))
      {
          strRepairStartDate = prpLrepairFeeDto.getRepairStartDate().toString();
          strRepairEndDate   = prpLrepairFeeDto.getRepairEndDate().toString();
      }
    }
  }
  if(certainLossDto.getPrpLcomponentDtoList()!=null){
    for(index=0;index<intComponentCount;index++)
    {
      prpLcomponentDto = (PrpLcomponentDto)certainLossDto.getPrpLcomponentDtoList().get(index);

      if(strLicenseNo.equals(prpLcomponentDto.getLicenseNo()))
       {
      //变速箱型式
      strGearboxType = prpLcomponentDto.getGearboxType();
      if( strGearboxType.equals("1") )
      {
        strGearboxName = "手动档";
      }
      else if( strGearboxType.equals("2") )
      {
        strGearboxName = "自动档";
      }
      else
      {
        strGearboxName = "未知";
      }
      //System.out.println("变速箱型式"+strGearboxName);
      //报价公司
      strQuoteCompanyGrade = prpLcomponentDto.getRepairStartDate().toString();
      if( strQuoteCompanyGrade.equals("1") )
      {
        strQuoteCompanyName = "总公司";
      }
      else if( strQuoteCompanyGrade.equals("2") )
      {
        strQuoteCompanyName = "省公司";
      }
      else if( strQuoteCompanyGrade.equals("3") )
      {
        strQuoteCompanyName = "地市公司";
      }
      else
      {
        strQuoteCompanyName = "其它";
      }
      }
    }
  }

  if(registDto.getPrpLthirdPartyDtoList()!=null){
     for(int i=0;i<intThirdPartyCount;i++)
    {
      prpLthirdPartyDto = (PrpLthirdPartyDto)registDto.getPrpLthirdPartyDtoList().get(i);
      strInsureCarFlag     = prpLthirdPartyDto.getInsureCarFlag();
      

if (prpLthirdPartyDto.getLicenseNo().equals(licenseNo)){
      engineNo =prpLthirdPartyDto.getEngineNo();
      frameNo =prpLthirdPartyDto.getFrameNo();
      brandName =prpLthirdPartyDto.getBrandName();
}

      if( "1".equals(strInsureCarFlag))
      {
        strLicenseNo         = prpLthirdPartyDto.getLicenseNo();
      }

      //*****修理费用清单表PrpLrepairFee*****
      //dblRepSumDefLoss = 0;

    if(certainLossDto.getPrpLrepairFeeDtoList()!=null){
      for(index=0;index<intRepairFeeCount;index++)
      {
        prpLrepairFeeDto = (PrpLrepairFeeDto)certainLossDto.getPrpLrepairFeeDtoList().get(index);

        if(strLicenseNo.equals(prpLrepairFeeDto.getLicenseNo()))
        {
          //modify by liuwei at 2011-02-14 判断是显示核损金额还是定损金额 start
          //dblRepSumDefLoss[i] += prpLrepairFeeDto.getSumDefLoss();
          if(prpLrepairFeeDto.getVeriManHour()==0 && prpLrepairFeeDto.getVeriManUnitPrice() == 0){
              dblRepSumDefLoss[i] += prpLrepairFeeDto.getSumDefLoss();
          }else{
              dblRepSumDefLoss[i] += prpLrepairFeeDto.getVeriSumLoss();
          }
          //modify by liuwei at 2011-02-14 判断是显示核损金额还是定损金额 end
        }
        //caopeng11.22
      	strRepairType        [index] = prpLrepairFeeDto.getRepairType();
        strCompName1         [index] = StringConvert.encode(prpLrepairFeeDto.getCompName());
        //modify by liuwei at 2011-02-14 判断是显示核损金额还是定损金额 start
        if(prpLrepairFeeDto.getVeriManHour()==0 && prpLrepairFeeDto.getVeriManUnitPrice() == 0){
            dblManHour           [index] = prpLrepairFeeDto.getManHour();
            dblManHourFee        [index] = prpLrepairFeeDto.getManHour()*prpLrepairFeeDto.getManHourUnitPrice();
        }else{
            dblManHour           [index] = prpLrepairFeeDto.getVeriManHour();
      	    dblManHourFee        [index] = prpLrepairFeeDto.getVeriManHour()*prpLrepairFeeDto.getVeriManUnitPrice();
        }
        //modify by liuwei at 2011-02-14 判断是显示核损金额还是定损金额 end
      	dblRemark            [index] = prpLrepairFeeDto.getRemark();
        dblSumManHourFee[i] += dblManHourFee[index];
        strCode = prpLrepairFeeDto.getHandlerCode();
        //caopeng11.22
        
      }
        
      
        
      strHandlerName = uiCodeAction.translateUserCode(strCode,isChinese);
    }
 
      //*****换件项目清单表PrpLcomponent**** 
      //dblComSumDefLoss = 0;
 //System.out.println("===========printType= Loss====="+printType);  
    if(certainLossDto.getPrpLcomponentDtoList()!=null){  	
     if(printType.equals("ComponentAddPrint") || printType.equals("ComponentListPrint") || printType.equals("LossPrint")) {
      //System.out.println("===========printType======"+printType);

      for(index=0;index<intComponentCount;index++)
      {
        prpLcomponentDto = (PrpLcomponentDto)certainLossDto.getPrpLcomponentDtoList().get(index);
         if(strLicenseNo.equals(prpLcomponentDto.getLicenseNo()))
        {
          //modify by liuwei at 2011-02-14 判断是显示核损金额还是定损金额 start
          //dblComSumDefLoss[i] += prpLcomponentDto.getSumDefLoss();
          if(prpLcomponentDto.getVeriQuantity() == 0 && prpLcomponentDto.getVeriMaterFee() == 0){
              dblComSumDefLoss[i] += prpLcomponentDto.getSumDefLoss();
          }else{
              dblComSumDefLoss[i] += prpLcomponentDto.getSumVeriLoss();
          }
          //modify by liuwei at 2011-02-14 判断是显示核损金额还是定损金额 end
          intCount[i]++;
        }
        //modify by caopeng start 2005-12-19 
        strCompName       [index] = StringConvert.encode(prpLcomponentDto.getCompName());
        //modify by liuwei at 2011-02-14 判断是显示核损金额还是定损金额 start
      	//dblMaterialQuantity  [index] = prpLcomponentDto.getQuantity();
      	//dblMaterialUnitPrice [index] = prpLcomponentDto.getMaterialFee();
      	if(prpLcomponentDto.getVeriQuantity() == 0 && prpLcomponentDto.getVeriMaterFee() == 0){
      	    dblMaterialQuantity  [index] = prpLcomponentDto.getQuantity();
      	    dblMaterialUnitPrice [index] = prpLcomponentDto.getMaterialFee();
      	}else{
      	    dblMaterialQuantity  [index] = prpLcomponentDto.getVeriQuantity();
      	    dblMaterialUnitPrice [index] = prpLcomponentDto.getVeriMaterFee();
      	}
      	//modify by liuwei at 2011-02-14 判断是显示核损金额还是定损金额 end
      	dblSumMQuantity [i]  += dblMaterialQuantity  [index];
      	dblSumMaterialFee[i] += dblMaterialQuantity[index]*dblMaterialUnitPrice [index];
      	
      	strCode = prpLcomponentDto.getHandlerCode();
     
      	if(index<=20){
      	  dblSumMaterialFeeTemp = dblSumMaterialFee[i];
      
      
      	 %>
     <!-- 	tdSumMaterialFee1.innerHTML = '<div align="center">' + '<%=new DecimalFormat("#,##0.00").format(dblSumMaterialFeeTemp)%>' + '</div>';-->
      	<%
      	}
      	if(index<48){
      	%>
      	<%--
     	  tdIndex<%=index%>.innerHTML = '<div align="center">' + '<%=(index+1)%>' + '</div>'; 
      	tdComName<%=index%>.innerHTML = '<div align="center">' + '<%=strCompName[index]%>' + '</div>'; 
      	tdQuantity<%=index%>.innerHTML = '<div align="center">' + '<%=new DecimalFormat("#").format(dblMaterialQuantity[index])%>' + '</div>';
      	tdPrice<%=index%>.innerHTML = '<div align="center">' + '<%=new DecimalFormat("#,##0.00").format(dblMaterialUnitPrice[index])%>' + '</div>';
      	--%>
      	<%}
      	//modify by caopeng end 2005-12-19
      } //forddd
      	strHandlerName = uiCodeAction.translateUserCode(strCode,isChinese);
      %>
      <!--tdSumMaterialFee2.innerHTML = '<div align="center">' + '<%=new DecimalFormat("#,##0.00").format((dblSumMaterialFee[i]-dblSumMaterialFeeTemp))%>' + '</div>';-->
            <%   }  //fi print
      //-----------------------
      
         
            
            
      //------------------------      
      }//finull
     
    //add by miaowenjun 20060406
    if(certainLossDto.getPrpLcarLossDtoList() != null){
    	for(index=0;index<intCarLossCount;index++){
    		prpLcarLossDto = (PrpLcarLossDto)certainLossDto.getPrpLcarLossDtoList().get(index);
    		sumManager[index] =  prpLcarLossDto.getSumManager();
    		sumFloatRate[index] = prpLcarLossDto.getSumFloatRate();//add by zhyi fubon增加浮動比例
    		sumTax[index] = prpLcarLossDto.getSumTax();
    		sumTransFee[index] = prpLcarLossDto.getSumTransFee();
    		strCurrency = prpLcarLossDto.getCurrency();
    		 sumRest = prpLcarLossDto.getSumRest()+"";
    		 System.out.println("xjsdfjsdkfj"+sumRest);
    		
    	}
    }	
    //add end by miaowenjun 20060406      
   
    
    if(certainLossDto.getPrpLpropDtoList()!=null){
      //*****财产核定损明细清单表PrpLprop*****
      for(index=0;index<intPropCount;index++)
      {
        prpLpropDto = (PrpLpropDto)certainLossDto.getPrpLpropDtoList().get(index);

         if(strLicenseNo.equals(prpLpropDto.getFamilyName()))
        {
           dblProSumReject[i] += prpLpropDto.getSumReject();
        }
      }
    }

      if( dblComSumDefLoss[i] != 0 )
      {
        strComSumDefLoss1[i] = MoneyUtils.toChinese(dblComSumDefLoss[i],strCurrency);
      }
      if( dblRepSumDefLoss[i] != 0 )
      {
        strRepSumDefLoss1[i] = MoneyUtils.toChinese(dblRepSumDefLoss[i],strCurrency);
      }
      if( dblProSumReject[i] != 0 )
      {
        strProSumReject1[i]  = MoneyUtils.toChinese(dblProSumReject[i],strCurrency);
      }

      strRepSumDefLoss2[i] = "（￥：" + DataUtils.zeroToEmpty(dblRepSumDefLoss[i]);
      strComSumDefLoss2[i] = "（￥：" + DataUtils.zeroToEmpty(dblComSumDefLoss[i]);
      strProSumReject2 [i] = "（￥：" + DataUtils.zeroToEmpty(dblProSumReject[i]);

      strRepContext[i] = "修理费总计金额：（人民币大写） " + strRepSumDefLoss1[i];
      strComContext[i] = "车牌号码：" + strLicenseNo + "，换件项目共计 " + DataUtils.zeroToEmpty(intCount[i]) + " 项，总计金额：（人民币大写） " + strComSumDefLoss1[i];
      strProContext[i] = "残值作价金额：（人民币大写） " + strProSumReject1[i];

    }
    
    //add by miaowenjun 20060406
     for(index=0;index<intRepairFeeCount;index++){
        if(strRepairType[index].equals("01") || strRepairType[index] == ""){   	
        	sumhour1 = sumhour1 + dblManHour[index];
        	sumhourfee1 = sumhourfee1 + dblManHourFee[index];
        	count1++;
        }
        if(strRepairType[index].equals("02")){
        	sumhour2 = sumhour2 + dblManHour[index];
        	sumhourfee2 = sumhourfee2 + dblManHourFee[index];
        	count2++;
        }
        if(strRepairType[index].equals("03")){
        	sumhour3 = sumhour3 + dblManHour[index];
        	sumhourfee3 = sumhourfee3 + dblManHourFee[index];
        	count3++;
        }
        if(strRepairType[index].equals("04")){
        	sumhour4 = sumhour4 + dblManHour[index];
        	sumhourfee4 = sumhourfee4 + dblManHourFee[index];
        	count4++;
        }
        if(strRepairType[index].equals("05")){
        	sumhour5 = sumhour5 + dblManHour[index];
        	sumhourfee5 = sumhourfee5 + dblManHourFee[index];
        	count5++;
        }
    }
        //add end by miaowenjun 20060406 
//         //System.out.println("测试step2=============");

    //add by miaowenjun 20060328 
      for(int z=0;z<strRepairType.length;z++){
        if(strRepairType[z].equals("01") || strRepairType[z] == ""){
          strRepairType[z] = "事故钣金";
        }else if(strRepairType[z].equals("02")){
          strRepairType[z] = "事故喷漆";
        }else if(strRepairType[z].equals("03")){
          strRepairType[z] = "事故机修";
        }else if(strRepairType[z].equals("04")){
          strRepairType[z] = "事故电工";
        }else if(strRepairType[z].equals("05")){
          strRepairType[z] = "事故拆装";
        }else if(strRepairType[z].equals("99")){
          strRepairType[z] = "事故其它";
        }
      }
      Vector vec = new Vector();
      for(int n=0;n<strRepairType.length;n++){
      	if((!vec.contains(strRepairType[n]))&&(strRepairType[n] != null)){
      		vec.add(strRepairType[n]);
      	}
      }   
      repaircount = vec.size() + strCompName1.length;
      RepairContentDto repairContentDto; 
	  for(int l=0;l<vec.size();l++){
	    int no = 1;
	    repairContentDto = new RepairContentDto();
	    repairContentDto.setRepairType(vec.get(l).toString());
			repairContentDto.setIsType(true);
		  repairListInfo.add(repairContentDto);
		  for(int j=0;j<strCompName1.length;j++){
		    if(strRepairType[j].equals(vec.get(l).toString())){
		      repairContentDto = new RepairContentDto();
		      repairContentDto.setNo(no);
	      	repairContentDto.setRepairName(strCompName1[j]);
	   			no++;
	 				repairContentDto.setRepairHour(dblManHour[j]);
	      	repairContentDto.setRepairHourFee(dblManHourFee[j]); 
	      	repairListInfo.add(repairContentDto);	
		    }
		  }
	  }
	  for(int i=0;i<repairListInfo.size();i++){
	  	sumhour += ((RepairContentDto)repairListInfo.get(i)).getRepairHour();
		sumhourfee += ((RepairContentDto)repairListInfo.get(i)).getRepairHourFee();
	  }
	  if(repaircount<=46){
	  	pageTotal = 1;
	  }else{
	  	if((repaircount-46)%54 == 0)
	  		pageTotal = (repaircount-46)/54 + 1;
	  	else
	  		pageTotal = (repaircount-46)/54 + 2;
	  }
    //add end by miaowenjun 20060328
    
    //add by miaowenjun 20060405
    changecount = strCompName.length;
    RepairContentDto changeRepairContentDto;
    for(int m=0;m<changecount;m++){
    	changeRepairContentDto = new RepairContentDto();
    	changeRepairContentDto.setNo(m+1);
    	changeRepairContentDto.setChangeName(strCompName[m]);
    	changeRepairContentDto.setChangeCount(dblMaterialQuantity[m]);
    	changeRepairContentDto.setChangeFee(dblMaterialUnitPrice[m]);
    	changeListInfo.add(changeRepairContentDto);
    }
    for(int i=0;i<changeListInfo.size();i++){
	  	sumchangefee += ((RepairContentDto)changeListInfo.get(i)).getChangeFee();
	  }
 	if(changecount<=46){
	  	changePageTotal = 1;
	  }else{
	  	if((changecount-46)%54 == 0)
	  		changePageTotal = (changecount-46)/54 + 1;
	  	else
	  		changePageTotal = (changecount-46)/54 + 2;
	  }
    //add end by miaowenjun 20060405
  }
  //strSumAllFee = MoneyUtils.toChinese(dblSumManHourFee[0]+dblSumMaterialFee[0]-Double.parseDouble(sumRest),strCurrency);
  
 // //System.out.println(changeListInfo.size()+"------------------------" +((RepairContentDto)changeListInfo.get(12)).getChangeName()) ;
  
%>
  }
</script>
