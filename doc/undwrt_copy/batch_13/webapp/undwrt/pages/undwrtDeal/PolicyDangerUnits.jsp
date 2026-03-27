<!--***************************************************************************
* Description: 拆分危险单位页面
* Author     : LUYANG
* CreateDate : 2005-5-4 20:30
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->

<%@page import="com.sinosoft.sysframework.reference.DBManager"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPrpDriskConfigAction"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDriskConfigDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLchargeDto"%>
<%@page import="com.sinosoft.prpall.dto.domain.*"%>
<%@page import="com.sinosoft.prpall.dto.domain.PrpCmainCovernoteDto"%>
<%@page import="com.sinosoft.undwrt.dto.custom.*"%>
<%@page import="com.sinosoft.utility.UtiPower"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.sinosoft.utility.SysConfig"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDuserDto"%>
<%@page import="com.sinosoft.sysframework.common.Constants"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPowerAction"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.prpall.blsvr.cb.*" %>

<%
  java.text.DecimalFormat decimalFormat= new java.text.DecimalFormat("0.00");
  String riskUnitFlag   = ""; //是否拆分危险单位标识
  String requiredReins  = ""; //是否强制分保试算
  String reinsOfflineFlag  = ""; //是否离线计算
  boolean allowSpecial   = false; //是否允许录入特殊因子
  boolean allowSpecial_user = false; //操作员是否有录入特殊因子的权限
  String businessNo     = request.getParameter("iBusinessNo");
  String iPrpallIp     = (String)request.getAttribute("iPrpallIp");//查看业务详细信息ip
  System.out.println("------businessNo======="+businessNo);
  System.out.println("-----------editType====="+editType);
  System.out.println("-----------handType====="+handType);
  
  BLPrpCmainCovernote blPrpCmainCovernote = new BLPrpCmainCovernote();
  blPrpCmainCovernote.getData(businessNo);
  if(blPrpCmainCovernote.getSize()>0){
	  riskCode = blPrpCmainCovernote.getArr(0).getRiskCode();
	  System.out.println("riskCode===="+riskCode);
  }
  String businessType   = request.getParameter("iBusinessType");
  System.out.println("businessType===="+businessType);
  String comCode =   (String) session.getAttribute("myComCode");
  String userCode =  (String) session.getValue("myUserCode");
  String strGoalInsuredFlag = "0";
  double profitRate = 0.00;   //优惠
  double disRate = 0.00;      //手续费
  String color = "";          //用于高亮显示超过15%的手续费
  String strLanguage ="";  //取语言种类
  UIPrpDriskConfigAction uiPrpDriskConfigAction  = new UIPrpDriskConfigAction();
  PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
  prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"RISK_UNIT_FLAG");
  riskUnitFlag = "0";
   //System.out.println("riskUnitFlag是否拆分危险单位" + riskUnitFlag);
   prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"REQUIRED_REINS");
  if( prpDriskConfigDto!=null
        && prpDriskConfigDto.getConfigValue().equals("1"))
    {
       requiredReins = "1";
    }
   else
   {
       requiredReins = "0";
   }
   //特殊因子录入开关
   prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"ALLOW_SPECIALPREMIUM_POLICY");
  if( prpDriskConfigDto!=null
        && prpDriskConfigDto.getConfigValue().equals("1"))
    {
       allowSpecial = true;
    }
   else
   {
       allowSpecial = false;
   }
   //承保录入特殊因子权限校验
  //modify by zhulei 20060419 begin 权限改造
  //UtiPower utiPower = new UtiPower();
  //String CheckCode  = SysConfig.getProperty("CHECKCODE_WRITE");
  //add xuning gpic 10061016
  double FACLEVEL  =Double.parseDouble(SysConfig.getProperty("FACLEVEL"));
  //System.out.println("--FACLEVEL="+FACLEVEL);
  //allowSpecial_user = utiPower.checkPower(userCode,riskCode,"cbzjcb",CheckCode);
  PrpDuserDto user = (PrpDuserDto) (session.getAttribute("user"));
  allowSpecial_user = UIPowerAction.checkPowerReturn(user,"prpall.policy.middlecost");
  //modify by zhulei 20060419 end 权限改造

   //是否显示特殊因子项
   String strDisplay = "none";  //默认不显示
   //只有特殊因子开关打开并且该人员具有承保中间成本的写权限时才允许显示特殊因子修改项。
   if ((allowSpecial) && (allowSpecial_user)){
     strDisplay="";
   }
   /* modify by xiaojian 20060304_1 begin reason：非“非联共保”不允许修改特殊因子 */
   String className = "";
   String coinsFlag = "";
   /* modify by xiaojian 20060304_1 end */
   //add begin by zhaijq 0060414 2799纯意外险不允许输入PML值
   String  includeAccident = "Y";
   if (riskCode.equals("2799")){
      includeAccident = "N";
      Collection prpItemKindList = (Collection)request.getAttribute("ItemKind"); 
      if (prpItemKindList != null && prpItemKindList.size()>0){
          Iterator iterator = prpItemKindList.iterator();
          while(iterator.hasNext()){
             CommonDangerItemInfoDto commonDangerItemInfoDto = (CommonDangerItemInfoDto)iterator.next();
             if (!commonDangerItemInfoDto.getKindCode().substring(0,1).equals("2")){
                 includeAccident = "Y";
                 break;
             }
          }
      }
   }  
   //add end by zhaijq 20060414
%>
<tr class=common>
  <td colspan="4">
   	  <input type=hidden name="PrpallIp" value="<s:property value="iPrpallIp"/>">
            <input type=hidden name="hiBusinessNo" value="<s:property value="businessNo"/>">
      <input type=hidden name="hiBusinessType" value="<s:property value="businessType"/>">
      <input type="hidden" name="riskUnitFlag" value="<s:property value="riskUnitFlag"/>">   <!--是否需要拆分危险单位标志1为允许-->
      <input type="hidden" name="requiredReins" value="<s:property value="requiredReins"/>"> <!--是否强制分保试算-->
        <input type="hidden" name="hiRiskLevel"      value="">
          <input type="hidden" name="hiRetCurrency"    value="">
          <input type="hidden" name="hiRetentionValue" value="">
          <input type="hidden" name="hiDangerItemKind" value="">
          <input type="hidden" name="hiDangerFlag"     value="">
          <input type="hidden" name="hiRiskLevelDesc"  value="">
          <input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>" value="99">
          <input type="hidden" name="includeAccident"  value="<s:property value="includeAccident"/>">
      <%
        String strClassCode = "";
          //modify begin 20060612 by lihua 提交分入确认
        String businessFlag = "";
      //modify end 20060612 by lihua 提交分入确认
        if(request.getAttribute("PrpTmainDto")!=null)
         {
         PrpTmainDto prpMainDto = (PrpTmainDto)request.getAttribute("PrpTmainDto");
          strClassCode=prpMainDto.getClassCode();
          profitRate = prpMainDto.getDiscount()*100;
          disRate = prpMainDto.getDisRate();
          if(disRate>15){
          	color = "red";
          }
           businessFlag= prpMainDto.getBusinessFlag();
           strLanguage =prpMainDto.getLanguage();
         } else if(request.getAttribute("PrpCmainCovernoteDto")!=null)
          {
         PrpCmainCovernoteDto prpMainDto = (PrpCmainCovernoteDto)request.getAttribute("PrpCmainCovernoteDto");
          strClassCode=prpMainDto.getClassCode();
          profitRate = prpMainDto.getDiscount()*100;
          disRate = prpMainDto.getDisRate();
          if(disRate>15){
          	color = "red";
          }
           businessFlag= prpMainDto.getBusinessFlag();
           strLanguage =prpMainDto.getLanguage();
         }
        //String strClassCode = (String)session.getAttribute("riskCode");
        // strClassCode=strClassCode.substring(0,2);
      %>

    <span id="spanInfo" >
    <table width=100%>


      <tr>
      <td width="100%">
       <table cellpadding="5" cellspacing="1" class="common" align="center" style="width:100%">

        <!-- 保单信息 -->
        <s:if test="PrpCmainCovernoteDto!=null">
          <tr class=listtitle>
          <s:if test='handType=="22"&&(businessType=="P"||businessType=="Y")'>
            <td colspan="4" ><s:text name="undwrt.PolicyDangerUnits.policyAndPaySummaryInfo"/></td>
          </s:if>
          <s:else>
            <td colspan="4" ><s:text name="undwrt.PolicyDangerUnits.policySummaryInfo"/></td>
          </s:else>
          </tr>
           <tr>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.risk"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.riskCode"/></td>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.belongOrganization"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.comCode"/></td>
          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.riskName"/>：</td>
            <td class=input4><s:property value="riskCName"/></td>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.belongOrganizationName"/>：</td>
            <td class=input4><s:property value="comCName"/></td>
          </tr>
          <tr>
            <td  class=title4><s:text name="undwrt.PolicyDangerUnits.policyNo"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.policyNo"/></td>
            <td class=title4>
            <input type="hidden" name="riskCode" description="<s:text name='policyManage.riskCode'/>" value="<s:property value="#PrpCmainCovernoteDto.riskCode"/>"></td>
            <input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>" value="99">
            <td class=input4>&nbsp;</td>
          </tr>
           <tr>
           <s:if test='strClassCode=="26"||strClassCode=="27"'></s:if>
             <td class=title4><s:text name="undwrt.PolicyDangerUnits.throwPolicyName"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.appliName"/></td>
            <s:else>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.insuredName"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.insuredName"/></td>
            </s:else>
             <s:if test='strClassCode=="09"||strClassCode=="10"'>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.startDate"/>：</td>
            </s:if>
            <s:else>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.insureDuration"/>：</td>
           </s:else>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.startDate"/>&nbsp;<s:text name="undwrt.PolicyDangerUnits.to"/>&nbsp;<s:property value="#PrpCmainCovernoteDto.endDate"/></td>
          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.totalInsureAmount"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.currency"/>&nbsp;<s:property value="#PrpCmainCovernoteDto.sumAmount"/></td>
            <td class=title4><s:text name="undwrt.PolicyDangerUnits.totalInsureFee"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.currency"/>&nbsp;<s:property value="#PrpCmainCovernoteDto.sumPremium"/></td>
          </tr>
          <%-- add by xiaojian 20051204_2 begin reason：投保（保）单核保时可见、可修改特殊因子 --%>
<%
  /* modify by xiaojian 20051229 begin reason：核赔也调用此段程序，但是核赔没有放入对象AmountAndPremiumDto */
  if(!businessType.equals("C")&&!businessType.equals("Y"))
  {
    PrpCmainCovernoteDto PrpCmainCovernoteDto = (PrpCmainCovernoteDto)request.getAttribute("PrpCmainCovernoteDto");
    CommonAmountAndPremiumDto commonAmountAndPremiumDto = (CommonAmountAndPremiumDto)request.getAttribute("AmountAndPremiumDto");
    //xiaojian_leave：是否应该“配置项和人员权限都满足”才显示特殊因子
    double dblDisRate1 = 0;
    double dblPremium1 = 0;
    double dblDisFee1 = 0;

    dblDisRate1 = PrpCmainCovernoteDto.getDisRate1();
    dblPremium1 = commonAmountAndPremiumDto.getPremium();
    //xiaojian_leave：四舍五入的问题
    dblDisFee1 = dblPremium1*dblDisRate1/100;

    /* modify by xiaojian 20060304_3 begin reason：非“非联共保”不允许修改特殊因子 */
    coinsFlag = PrpCmainCovernoteDto.getCoinsFlag();
    if(!coinsFlag.equals("0"))
      className = "\"readonly\" readonly";
    else if(editType.equals("query"))
      className = "\"readonly\" readonly";
    else
      className = "\"free\"";
    /* modify by xiaojian 20060304_3 end */
%>
          <tr style="display:${strDisplay}">
            <td class="title4"><s:text name="undwrt.PolicyDangerUnits.specificFactor"/>：</td>
            <td class="input4">
              <input type="hidden" name="DisRate1Old" value="<s:property value="#PrpCmainCovernoteDto.disRate1"/>">
              <input type="text" name="DisRate1" class= ${className} value="<s:property value="#PrpCmainCovernoteDto.disRate1"/>"
                onblur="changeDisFee1(this)">
            </td>
            <td class="title4"><s:text name="undwrt.PolicyDangerUnits.specificFactorAmount"/>：</td>
            <td class="input4">
              <input type="hidden" name="Premium1" value="<%=decimalFormat.format(dblPremium1)%>">
              <input type="text" name="DisFee1" class="readonly" readonly value="<%=decimalFormat.format(dblDisFee1)%>">
            </td>
          </tr>
          <%-- add by xiaojian 20051204_2 end --%>
          <%-- add by xiaojian 20051112_2 begin reason：增加是否目标客户 --%>
          <tr>
            <td class="title4"><s:text name="undwrt.PolicyDangerUnits.WhetherObjUser"/>：</td>
<%
    if(PrpCmainCovernoteDto.getOthFlag().length()>=15)
      strGoalInsuredFlag = PrpCmainCovernoteDto.getOthFlag().substring(14,15);
    if(strGoalInsuredFlag.equals("1"))
      strGoalInsuredFlag = "<s:text name='undwrt.HebaoTaskDealQuery.yes'/>";
    else
      strGoalInsuredFlag = "<s:text name='undwrt.HebaoTaskDealQuery.no'/>";
%>
            <td class="input4">${strGoalInsuredFlag}</td>
            <td class="title4"></td>
            <td class="input4"><input type="hidden" name="policyType" value="<s:property value="#PrpCmainCovernoteDto.policyType"/>"></td>
          </tr>
          <%-- add by xiaojian 20051112_2 end --%>
<%
  }
  /* modify by xiaojian 20051229 end */
          if(handType.equals("22") && (businessType.equals("C")||businessType.equals("Y"))){
               //modify by qinyongli 2005-9-16
               ArrayList chargeList = (ArrayList)request.getAttribute("prplchargeList");
               Iterator theCharge = chargeList.iterator();
               PrpLchargeDto prpLchargeDto = new PrpLchargeDto();
               int lines= 1 ;
               while(theCharge.hasNext()){
                    prpLchargeDto =(PrpLchargeDto) theCharge.next();
                    if(lines%2!=0){
                       out.println("<tr>");
                    }
                        double sumCharge  = prpLchargeDto.getChargeAmount();
                        double sumRealPay = prpLchargeDto.getSumRealPay();
                        double charge  =  sumCharge ;
                        out.println("<td  class='title4'><b>"+prpLchargeDto.getChargeName()+"</b></td>");
                        out.println("<td  class='input4'><b>"+charge+"</b></td>");
                    if(lines%2==0){
                       out.println("</tr>");
                    }
                    lines++;
               }
                    if(lines%2==0){
                       out.println("<td class=title4></td><td class=input4></td>");
                       out.println("</tr>");
                    }

         %>
       <tr>
      <td class="title4"><b><s:text name="undwrt.PolicyDangerUnits.objLoss"/>：</b></td>
      <td class="input4"><b><s:property value="#PolicyAbstractInfoDto.sumLoss"/></b></td>
      <td class="title4"><b><s:text name="undwrt.PolicyDangerUnits.sum"/>：</b></td>
      <td class="input4"><b><s:property value="#PolicyAbstractInfoDto.sumPaid"/></b></td>
      </tr>
     </s:if>
        </table>
       </td>
      </tr>
    <%--added by LanNing begin 20070416 修改按钮位置--%>
      <tr>
          <td>
          <s:if test='handType=="22"'>
            
                <input type="hidden" name="ClaimNo"  value='<s:property value="ClaimNo"/>'>
                <input type="hidden" name="RegistNo" value='<s:property value="RegistNo"/>'>
                <input type="hidden" name="PolicyNo" value='<s:property value="#PrpCmainCovernoteDto.policyNo"/>'>
                <Input type="button" class="button" name="claimInfo" value="<s:text name='undwrt.pages.undwrtDeal.payMessages'/>" onclick="viewClaimInfo();">
           </s:if>
           <s:if test='handType!="22"'>
                <Input name="butDetail" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" onclick="showBusinessInfo()">
            </s:if>
            <s:if test='historyProposal=="true"'>
                <input type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.historyUnderwriteMessages'/>" name="BusinessTotalInfo" onclick="showBusinessTotalInfo('<s:property value="iBusinessNo"/>');">
            </s:if>
            <s:if test='historyLoss=="true"'>
                <input type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.historyPayMessages'/>" name="HistoryLossInfo" onclick="showHistoryLossInfo('<s:property value="iBusinessNo"/>');">
            </s:if>
            <Input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.influenceMessages'/>" onclick="showMaterialInfo('<s:property value="iBusinessNo"/>');">
            <s:if test='handType=="22"'>
                <Input type="button" name="PolicyNoInfo" class="longbutton" value="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyMessages'/>" onclick="showPolicyInfo();">
            </s:if>
            <s:if test='handType=="22"&&<s:property value="iNodeStatus"/>!="4"&&<s:property value="iNodeStatus"/>!="0"'>
                <Input name="buttonMessage1" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.remarkRecord'/>" onclick="openWinQuery();">   
            <s:elseif test='handType=="22"'>
                <Input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.payConditionRecord'/>" onclick="openWinQuery();">
             </s:elseif> 
            </s:if>
             <input type="hidden" name="language" description="<s:text name='undwrt.languageKind'/>" value="${strLanguage}">
        </td>
      </tr>
    <%--added by LanNing end 20070416 修改按钮位置--%>
    <span id="dangerInfo">
    </span>
    <!-- 原始标的信息 -->
      <tr>
      <td width="100%">
       <table cellpadding="5" cellspacing="1" class="common" align="center" style="width:100%">
       
<%
  if(strClassCode.startsWith("26") || strClassCode.startsWith("27")){
%>
           <tr class=listtitle>
            <td colspan="7" ><s:text name="undwrt.PolicyDangerUnits.originalObjInfo"/></td>
           </tr>
           <tr class=common>
            <td><s:text name="undwrt.PolicyDangerUnits.itemName"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.riskDuty"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.rebate"/></td>

<s:if test='riskCode=="2703"||riskCode=="2708"'>
            <td><s:text name="undwrt.PolicyDangerUnits.copies"/></td>
</s:if>
            <td><s:text name="undwrt.PolicyDangerUnits.peopleCount"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.protectAmount"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.protectFee"/></td>
           </tr>
           <s:if test='ItemKind!=null'>
           <s:iterator id="ItemKind" status="statu" value="ItemKind">
           <tr class=common>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.kindName" />" ></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.itemDetailName" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.discount" />"></td>
<%
  if(riskCode.equals("2703") || riskCode.equals("2708") ){
%>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.value" />"></td>
<%
    }
%>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.quantity" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.amount" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.premium" />"></td>
           </tr>
          </s:iterator>
          </s:if>
<%
  }else{
%>
           <tr class=listtitle>
            <td colspan="9" ><s:text name="undwrt.PolicyDangerUnits.originalObjInfo"/></td>
           </tr>
           <tr class=common>
            <td><s:text name="undwrt.PolicyDangerUnits.serialNo"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.kind"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.objProject"/></td>
            <s:if test='strClassCode=="09"||strClassCode=="10"'>
            <td><s:text name="undwrt.PolicyDangerUnits.insueredObjName"/></td>
            </s:if>
            <s:else>
            <td><s:text name="undwrt.PolicyDangerUnits.objName"/></td>
            </s:else>
            <td><s:text name="undwrt.PolicyDangerUnits.postcode"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.objAddress"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.currency"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.protectAmount1"/></td>
            <td><s:text name="undwrt.PolicyDangerUnits.protectFee1"/></td>
          </tr>
          <s:if test='ItemKind!=null'>
          <s:iterator id="ItemKind" status="statu" value="ItemKind">
          <tr class=common>
            <td><input class="formtitle1" name="itemKindNo" readonly value="<s:property value="#ItemKind.itemKindNo" />" ></td>
            <td>
              <input class="formtitle1"  readonly  value="<s:property value="#ItemKind.kindName" />">
              <input type=hidden value="<s:property value="#ItemKind.kindCode" />" >
            </td>
            <td><input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.itemCode" />" ></td>
            <td><input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.itemDetailName" />" ></td>
            <td><input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.addressCode" />" ></td>
            <td><input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.addressName" />" ></td>
            <td><input class="formtitle1" name="iCurrency" readonly value="<s:property value="#ItemKind.currency" />" ></td>
            <td><input class="formtitle1" name="iAmount" readonly  value="<s:property value="#ItemKind.amount" />" ></td>
            <td><input class="formtitle1" name="iPremium" readonly value="<s:property value="#ItemKind.premium" />" >
              <input type="hidden" name="calculateFlag"  value="<s:property value="#ItemKind.calculateFlag" />" >
             </td>
          </tr>
        </s:iterator>
        </s:if>
<%
  }
%>

        </table>
        </td>
      </tr>
 </table>
  <!-- 开始处理核赔时的危险单位
  //该险种要求拆分危险单位和分摊试算add by qinyongli 2005-8-23
   -->
 <s:if test='handType=="22"'>
 
 
  <s:if test='riskUnitFlag!=""&&'riskUnitFlag!="1"'>
 

 <table class="common" style="width:99%" cellpadding="5" cellspacing="1" align="center" id="HepeiDangerUnit"  >
      <tr class=listtitle><td colspan="15" ><s:text name="undwrt.PolicyDangerUnits.riskUnitDivideInfo"/></td></tr>
      <tr class=common>
      <td><s:text name="undwrt.PolicyDangerUnits.serialNo1"/></td>
      <td><s:text name="undwrt.PolicyDangerUnits.describe"/></td>
      <td><s:text name="undwrt.PolicyDangerUnits.address"/></td>
      <td><s:text name="undwrt.PolicyDangerUnits.currency1"/></td>
      <td><s:text name="undwrt.PolicyDangerUnits.accessAmount"/></td>
      <td><s:text name="undwrt.PolicyDangerUnits.alreadyPay"/></td>
      <td><s:text name="undwrt.PolicyDangerUnits.occupyScale"/>%</td>
      </tr>

       <s:if test='DangerDetail!=null'>
       <s:iterator id="DangerDetail" status="statu" value="DangerDetail">
     <tr class=common>
      <td ><input class="formtitle1" readonly name="dangerNo"  title="<s:text name='undwrt.ShowDangerItem.serialNo1'/>" value="<s:property value="#DangerDetail.dangerNo" />"></td>
      <td ><input class="formtitle1" readonly name="dangerDesc" title="<s:text name='undwrt.ShowDangerItem.describe'/>" value="<s:property value="#DangerDetail.dangerDesc" />"></td>
      <td ><input class="formtitle1" readonly name="dangerAddress" title="<s:text name='undwrt.ShowDangerItem.address'/>" value="<s:property value="#DangerDetail.addressName" />"></td>
      <td ><input class="formtitle1" readonly name="currency" title="<s:text name='undwrt.ShowDangerItem.currency'/>" value="<s:property value="#DangerDetail.currency" />"></td>
      <td ><input class="formtitle1" readonly name="sumLoss" title="<s:text name='undwrt.PolicyDangerUnits.accessAmount'/>" value="<s:property value="#DangerDetail.sumLoss" />" onblur="checkNumber(this)"></td>
      <td ><input class="formtitle1" readonly name="sumPaid" title="<s:text name='undwrt.CommonDangerUnits.alreadyDecidereparation'/>" value="<s:property value="#DangerDetail.sumPaid" />" onblur="checkNumber(this)"></td>
      <td ><input class="formtitle1" readonly name="dangerShare"  title="<s:text name='undwrt.CommonDangerUnits.occupyScale'/>" value="<s:property value="#DangerDetail.dangerShare" />" onblur="checkNumber(this)"></td>
      </tr>
       </s:iterator>
       </s:if>
       <tr>
        <td><input class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.reinsTrial'/>" onclick="simulateReinsHepei()" ></td>
       </tr>
  </table>

</s:if>
<!-- 不要求拆分危险单位,但是要求分摊试算 -->
<s:if test='requiredReins!=""&&requiredReins=="1"&&riskUnitFlag=="0"'>
           <table>
            <tr align="left">
              <td><input class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.reinsTrial'/>" onclick="simulateReinsHepei()" ></td>
            </tr>
          </table>
       </s:if>
       </s:if>

 </span>
 </td>
</tr>