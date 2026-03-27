<!--***************************************************************************
* Description: 批单拆分危险单位页面
* Author     : LongYin
* CreateDate : 2005-5-23 20:30
* UpdateLog  ：Name       Date            Reason/Contents
****************************************************************************-->
<%@page import="com.sinosoft.sysframework.reference.DBManager"%>
<%@page import="java.util.Collection"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPrpDriskConfigAction"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDriskConfigDto"%>
<%@page import="com.sinosoft.prpall.dto.domain.PrpPheadCovernoteDto"%>
<%@page import="com.sinosoft.undwrt.common.vo.CommonDangerItemInfoVo"%>
<%@page import="com.sinosoft.prpall.dto.domain.*"%>
<%@page import="com.sinosoft.sysframework.common.Constants"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDuserDto"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPowerAction"%>
<%@page import="com.sinosoft.prpall.dto.domain.*"%>
<%@ page import="java.text.*"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/i18njs.jsp"%>
<%@page import="com.sinosoft.prpall.dbsvr.pg.*"%>
<%@page import="com.sinosoft.prpall.blsvr.pg.*"%>
<%@page import="com.sinosoft.prpall.schema.*"%>
<%@page import="com.sinosoft.utility.error.*"%>
<%@page import="com.sinosoft.platform.bl.facade.BLPrpDpreauditConfigFacade"%>

<%
  //add by zhulei 20060430 金额四舍五入显示
  java.text.DecimalFormat decimalFormat= new java.text.DecimalFormat("0.00");
  String riskUnitFlag = ""; //是否拆分危险单位标识
  String requiredReins  = ""; //是否强制分保试算
  String reinsOfflineFlag  = ""; //是否离线计算
  String businessNo = request.getParameter("iBusinessNo");
  String iPrpallIp     = (String)request.getAttribute("iPrpallIp");//查看业务详细信息ip
  String businessType = request.getParameter("iBusinessType");
  boolean blnIsPreaudit = false;
  boolean blnSplitDangerUnit = false;
  String comCode =   (String) session.getAttribute("myComCode");
  UIPrpDriskConfigAction uiPrpDriskConfigAction  = new UIPrpDriskConfigAction();
  PrpCmainCovernoteDto prpCmainCovernoteDto = (PrpCmainCovernoteDto)request.getAttribute("PrpCmainCovernoteDto");;
  PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
  //add by zhaoning20091109 begin Reason:除了预处理岗，其他双核级别都具有拆分危险单位的操作权限
  BLPrpDpreauditConfigFacade blPrpDpreauditConfigFacade = new BLPrpDpreauditConfigFacade();
  blnIsPreaudit = blPrpDpreauditConfigFacade.getIsPreaudit(businessNo,businessType,request.getParameter("iModelNo"),request.getParameter("iNodeNo"));
  blnSplitDangerUnit = blPrpDpreauditConfigFacade.getAllowSplitDangerUnit(businessNo,businessType);
  //add by zhaoning20091109 end
  prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"RISK_UNIT_FLAG");
  if( prpDriskConfigDto!=null
        && prpDriskConfigDto.getConfigValue().equals("1") && comCode.substring(0,2).equals("00"))
    {
       //System.out.println("系统要求拆分危险单位");
       riskUnitFlag = "1";
    }
   else
   {   //System.out.println("系统没有要求拆分危险单位");
       riskUnitFlag = "0";
   }
   
   //add by zhaoning20091109 begin Reason:除了预处理岗，其他双核级别都具有拆分危险单位的操作权限
   if(prpDriskConfigDto!=null && !blnIsPreaudit && blnSplitDangerUnit)
   {
     riskUnitFlag = "1";
   }
   //add by zhaoning20091109 end
   
   prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"REQUIRED_REINS");
	if(	prpDriskConfigDto!=null
	  		&& prpDriskConfigDto.getConfigValue().equals("1"))
	  {
	     requiredReins = "1";
	  }	
	 else
	 {   //System.out.println("系统没有要求强制分保试算");
	     requiredReins = "0";
	 } 
	 
	    //add begin by zhaijq 0060414 2799纯意外险不允许输入PML值
   String  includeAccident = "Y";
   if (riskCode.equals("2799")){
      includeAccident = "Y";
      Collection prpItemKindList = (Collection)request.getAttribute("ItemKind"); 
      if (prpItemKindList != null && prpItemKindList.size()>0){
          Iterator iterator = prpItemKindList.iterator();
          while(iterator.hasNext()){
             CommonDangerItemInfoVo commonDangerItemInfoDto = (CommonDangerItemInfoVo)iterator.next();
             if (!commonDangerItemInfoDto.getKindCode().substring(0,1).equals("2")){
                 includeAccident = "Y";
                 break;
             } 
          }
      }
   }  
   //add end by zhaijq 20060414	    
%>

<tr class="mline">
  <td class="subformtitle" colspan="4" style="text-align:left">
  	   <input type=hidden name="PrpallIp" value="<s:property value="iPrpallIp"/>">
       <input type="hidden" name="hiBusinessNo"     value="<s:property value="businessNo"/>">
       <input type="hidden" name="hiBusinessType"   value="<s:property value="businessType"/>">
       <input type="hidden" name="riskUnitFlag"     value="<s:property value="riskUnitFlag"/>"><!--是否需要拆分危险单位标志1为允许-->
       <input type="hidden" name="requiredReins"    value="<s:property value="requiredReins"/>"> <!--是否强制分保试算-->
       <input type="hidden" name="hiRiskLevel"      value="">
	   <input type="hidden" name="hiRetCurrency"    value="">
	   <input type="hidden" name="hiRetentionValue" value="">
	   <input type="hidden" name="hiDangerItemKind" value="">
	   <input type="hidden" name="hiDangerFlag"     value="">
	   <input type="hidden" name="hiRiskLevelDesc"  value="">
	   <input type="hidden" name="includeAccident"  value="<s:property value="includeAccident"/>">

      <%      
        //String strClassCode = businessNo.substring(9,11);
        String strClassCode = prpCmainCovernoteDto.getClassCode();
        String businessFlag= prpCmainCovernoteDto.getBusinessFlag();
      %>

    <table class="sub" width="100%">
      <tr>
      <td>
      <table class="common" cellpadding="2" cellspacing="1" align="center">

       <!-- 保单信息 -->
      <s:if  test='PrpCmainCovernoteDto != null'>
          <tr>
            <td colspan="4" class=listtitle align="left"><s:text name="undwrt.EndorseDangerUnits.policySummaryInfo"/></td>
          </tr>
           <tr>
            <td class=title4><s:text name="undwrt.EndorseDangerUnits.risk"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.riskCode"/></td>
            <td  class=title4><s:text name="undwrt.EndorseDangerUnits.belongOrganization"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.comCode"/></td>
          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.EndorseDangerUnits.riskName"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.riskCName"/></td>
            <td class=title4><s:text name="undwrt.EndorseDangerUnits.belongOrganizationName"/>：</td>
            <td class=input4><s:property value="#PrpCmainCovernoteDto.comCName"/></td>
          </tr>
           <tr>
            <td  class=title4><s:text name="undwrt.EndorseDangerUnits.policyNo"/>：</td>
            <td class=input4><s:property value="policyNo"/></td>
            <td class=title4>
            <input type="hidden" name="riskCode"  description="<s:text name='policyManage.riskCode'/>" value="<s:property value="#PrpCmainCovernoteDto.riskCode" />"></td>
            <input type="hidden" name="hiClassCode" description="<s:text name='policyManage.riskCode'/>" value="<s:property value="#PrpCmainCovernoteDto.classCode" />"></td>
            <input type="hidden" name="policyType" value="<s:property value="#PrpCmainCovernoteDto.comCName"/>">
            <input type="hidden" name="coinsFlag" value="<s:property value="#PrpCmainCovernoteDto.comCName"/>">
            <td class=input4>
            <input type="hidden" name="hiPolicyNo" description="<s:text name='policyManage.policyNo'/>" value="<s:property value="#PrpCmainCovernoteDto.policyNo"/>">
            <input type="hidden" name="hiProposalNo" description="<s:text name='policyManage.policyApprovalBillRelateThrowPolicyNo'/>" value="<s:property value="#PrpCmainCovernoteDto.proposalNo"/>">
            </td>
          </tr>
           <tr>
             <%if(strClassCode.equals("26") ||
            		 strClassCode.equals("27"))
             {		 
           %>
             <td class=title4><s:text name="undwrt.EndorseDangerUnits.policyerName"/>：</td>
            <td class=input4><s:property value="appliName"/></td>
            <%}else {%>
            <td class=title4><s:text name="undwrt.EndorseDangerUnits.insuredName"/>：</td>
            <td class=input4><s:property value="insuredName"/></td>
            <%}
              if((request.getAttribute("riskCName")).equals("09") ||
                 (request.getAttribute("riskCName")).equals("10")
                )
              {
            %>
            <td class=title4><s:text name="undwrt.EndorseDangerUnits.startDate"/>：</td>
            <td class=input4><s:property value="startDate"/></td>
            <%
            }
              else
            {
            %>
            <td class=title4><s:text name="undwrt.EndorseDangerUnits.insureDuration"/>：</td>
            <td class=input4><s:property value="startDate"/>&nbsp;<s:text name="undwrt.EndorseDangerUnits.to"/>&nbsp;<s:property value="endDate"/></td>
            <%
            }
            %>
          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.EndorseDangerUnits.totalInsureAmount"/>：</td>
            <td class=input4><s:property value="currency"/>&nbsp;<s:property value="sumAmount"/></td>
            <td class=title4><s:text name="undwrt.EndorseDangerUnits.totalInsureFee"/>：</td>
            <td class=input4><s:property value="currency"/>&nbsp;<s:property value="sumPremium"/></td>
          </tr>
<%//add by zhulei 20060425 管理费比例 begin
  PrpDuserDto user = (PrpDuserDto) (session.getAttribute("user"));
  //PrpCmainCovernoteDto prpCmainCovernoteDto = (PrpCmainCovernoteDto)(session.getArrtibute("PrpCmainCovernoteDto"));
  double dblPremium1 = prpCmainCovernoteDto.getSumPremium();
  String strManageFeeDisplay = "none";  //管理费比例默认不显示
  boolean allowManageFee = false;       //条件1：是否允许录入管理费比例
  boolean allowManageFee_user = false;  //条件2：操作员是否有录入管理费比例的权限
  boolean allowManageFee_Flag = false;  //条件3：业务录入时是否允许管理费比例
  //管理费比例录入开关
  prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(comCode,riskCode,"SWITCH_MANAGEFEERATE");
  if( prpDriskConfigDto!=null && prpDriskConfigDto.getConfigValue().equals("1")){
    allowManageFee = true;
  }else{
    allowManageFee = false;
  }
  //人员管理费比例操作权限
  allowManageFee_user = UIPowerAction.checkPowerReturn(user,"prpall.policy.managefeerate");
  double dbManageFeeRate = 0;
  double dbManageFee = 0;
  //zhulei：管理费比例显示，三个条件，prpDriskConfig配置放开，当前登陆人员有权，当前业务录入时允许管理费比例；
  if(allowManageFee && allowManageFee_user && allowManageFee_Flag){
    strManageFeeDisplay = "";
  }
  //管理费比例是否允许修改控制，批改时，仅批改类型中包含“管理费比例批改”（EndorseType＝59）时，允许双核修改管理费
  String strManageFeeAble = "readonly";
  String strManageFeeClass = "readonly";
  PrpPheadCovernoteDto prpPheadCovernoteDto = (PrpPheadCovernoteDto)(request.getAttribute("PrpPheadCovernoteDto"));
  if(prpPheadCovernoteDto!=null && prpPheadCovernoteDto.getEndorType().indexOf("59")>0){
    strManageFeeAble = "";
    strManageFeeClass = "common";
  }
  System.out.println("111111111111111111111"+prpPheadCovernoteDto.getEndorseNo());
  String endorseNo = prpPheadCovernoteDto.getEndorseNo();
  //add by zhulei 20060424 管理费比例 end
%>
          
          <!-- add by zhulei 20060423 管理费比例 begin -->
          <s:if test="PrpCPexpenseDto!=null">
            <tr style="display:${strManageFeeDisplay}">
              <td class="title4"><s:text name="undwrt.EndorseDangerUnits.managerScale"/>：</td>
              <td class="input4">
                <input type="hidden" name="ManageFeeRateOld" value="<s:property value="#PrpCPexpenseDto.manageFeeRate"/>">
                <input type="text" name="ManageFeeRate"  <%=strManageFeeAble %> value="<s:property value="#PrpCPexpenseDto.manageFeeRate"/>"
                  onblur="changeManageFeeRate(this)">
              </td>
              <td class="title4"><s:text name="undwrt.EndorseDangerUnits.managerAmount"/>：</td>
              <td class="input4">
                <input type="hidden" name="Premium2" value="<s:property value="dblPremium1" />">
                <input type="text" name="ManageFee" class="readonly" readonly value="<s:property value="#PrpCPexpenseDto.dbManageFee" />">
              </td>
            </tr>
          </s:if>
          <!-- add by zhulei 20060423 管理费比例 end -->
         </s:if>
          </table>
        </td>
      </tr>
      
     	<%
			  //对象定义部分
			  BLPrpPtextCovernote     blPrpPtextCovernote     = null;   //批文对象
			  PrpPtextCovernoteSchema prpPtextCovernoteSchema = null;   //批单的PrpPtextCovernoteSchema对象
			
			  if((businessNo == null )||(businessNo.trim().length() == 0))
			  {
			    throw new UserException(-98,-1014,"UIPtextShow.jsp");
			  }
			
			  blPrpPtextCovernote = new BLPrpPtextCovernote();
			  blPrpPtextCovernote.query(" EndorseNo = '" + businessNo +"' ORDER BY LineNo",0);
			%>
			
      <tr>
      <td>
      <table border="0" class="sub" align="left" width="100%" >



<%
	//意健险特殊处理 add by luyang 2005-8-31 19:53
	if(strClassCode.startsWith("26") || strClassCode.startsWith("27")){
%>
           <tr class=listtitle>
            <td colspan="10" ><s:text name="undwrt.EndorseDangerUnits.originalObjInfo"/></td>
           </tr>
           <tr class=common>
            <td><s:text name="undwrt.EndorseDangerUnits.itemName"/></td>
            <td><s:text name="undwrt.EndorseDangerUnits.riskDuty"/></td>
            <td><s:text name="undwrt.EndorseDangerUnits.rebate"/></td>

<s:if test='riskCode=="2703"||riskCode=="2708"'>   
            <td><s:text name="undwrt.EndorseDangerUnits.copies"/></td>
</s:if>                  
            <td><s:text name="undwrt.EndorseDangerUnits.peopleCount"/></td>
            <td><s:text name="undwrt.EndorseDangerUnits.policyAmount"/></td>
            <td><s:text name="undwrt.EndorseDangerUnits.policyFee"/></td>
						<td><s:text name="undwrt.EndorseDangerUnits.peopleCountChange"/></td>
						<td><s:text name="undwrt.EndorseDangerUnits.policyAmountChange"/></td>
						<td><s:text name="undwrt.EndorseDangerUnits.policyFeeChange"/></td>
					 </tr>
			<s:if test="ItemKind!=null">>
           <s:iterator id="ItemKind" status="statu" value="ItemKind">
           <tr class=common>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.kindName" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.itemDetailName" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.discount" />"></td>

      <s:if test='riskCode=="2703"||riskCode=="2708"'> 
      
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.value" />"></td>
      </s:if>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.quantity" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.amount" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.premium" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.chgQuantity" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.chgAmount" />"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#ItemKind.chgPremium" />"></td>
			</tr>
	        </s:iterator>
	        </s:if>

<%
	}
%>      
      <s:else>
          <tr class=listtitle>
            <td colspan="11" ><s:text name="undwrt.EndorseDangerUnits.correctObjInfo"/></td>
          </tr>
          <tr class=common>
            <td ><s:text name="undwrt.EndorseDangerUnits.serialNo"/></td>
            <td ><s:text name="undwrt.EndorseDangerUnits.kind"/></td>
            <td ><s:text name="undwrt.EndorseDangerUnits.objProject"/></td>
            <s:if test='strClassCode=="09"||strClassCode=="10"'> 
            </s:if>
            <s:else>
            <td><s:text name="undwrt.EndorseDangerUnits.objName"/></td>
            </s:else>
            <td ><s:text name="undwrt.EndorseDangerUnits.postcode"/></td>
            <td ><s:text name="undwrt.EndorseDangerUnits.objAddress"/></td>
            <td ><s:text name="undwrt.EndorseDangerUnits.currency"/></td>
            <td ><s:text name="undwrt.EndorseDangerUnits.originalProtectAmount"/></td>
            <td><s:text name="undwrt.EndorseDangerUnits.changeProtectAmount"/></td>
            <td><s:text name="undwrt.EndorseDangerUnits.originalProtectFee"/></td>
            <td><s:text name="undwrt.EndorseDangerUnits.changeProtectFee"/></td>
          </tr>
          <s:if test="ItemKind!=null">
          <s:iterator id="ItemKind" status="statu" value="ItemKind">
          <tr class=common>
            <td ><input class="formtitle1"  name="itemKindNo" value="<s:property value="#ItemKind.itemKindNo" />"></td>
            <td >
              <input class="formtitle1"  value="<s:property value="ItemKind.kindName" />" >
              <input type=hidden value="<s:property value="ItemKind.kindCode" />" >
            </td>
            <td ><input class="formtitle1" name=""  value="<s:property value="#ItemKind.itemCode" />" ></td>
            <td ><input class="formtitle1" name=""  value="<s:property value="#ItemKind.itemDetailName" />" ></td>
            <td><input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.addressCode" />" ></td>
            <td><input class="formtitle1" name=""  readonly value="<s:property value="#ItemKind.addressName" />" ></td>
            <td ><input class="formtitle1" name="iCurrency" value="<s:property value="#ItemKind.currency" />" ></td>
            <td ><input class="formtitle1" name="iAmount" value="<s:property value="#ItemKind.amount" />" ></td>
            <td ><input class="formtitle1" name="ichgAmount" value="<s:property value="#ItemKind.chgAmount" />" ></td>
            <td ><input class="formtitle1" name="iPremium" value="<s:property value="#ItemKind.premium" />" >
            <td ><input class="formtitle1" name="ichgPremium" value="<s:property value="#ItemKind.chgPremium" />" ></td>
              <input type="hidden" name="calculateFlag" value="<s:property value="#ItemKind.calculateFlag" />" >
             </td>
          </tr>
        </s:iterator>
       </s:if>
</s:else>          
        </table></td>
      </tr>
	  <%--added by LanNing begin 20070416 改变按钮位置--%>
      <tr>
        <td>
        <s:if test='handType=="22"'>
		  <input type="hidden" name="ClaimNo"  value='<s:property value="ClaimNo" />'>
		  <input type="hidden" name="RegistNo" value='<s:property value="RegistNo" />'>
		  <input type="hidden" name="PolicyNo" value='<s:property value="#PrpCmainCovernoteDto.policyNo" />'>
		  <Input type="button" class="button" name="claimInfo" value="<s:text name='undwrt.pages.undwrtDeal.payMessages'/>" onclick="viewClaimInfo();">
</s:if>
		      <Input name="butDetail" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" onclick="showBusinessInfo()">          	


<s:if test='historyProposal=="true"'>
              
          <input type="button" class=longbutton  value="<s:text name='undwrt.pages.undwrtDeal.historyUnderwriteMessages'/>" name="BusinessTotalInfo" onclick="showBusinessTotalInfo('<s:property value="iBusinessNo" />');">
</s:if>
<s:if test='historyLoss=="true"'>
                
        	<input type="button" value="<s:text name='undwrt.pages.undwrtDeal.historyPayMessages'/>" class=longbutton name="HistoryLossInfo" onclick="showHistoryLossInfo('<s:property value="iBusinessNo" />');">
</s:if>   		
          <Input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='undwrt.prompt.uploadResource'/>" 	
				 onclick="showMaterialInfo('<s:property value="iBusinessNo" />');">
<s:if test='handType=="22"'>
		  <Input type="button" name="PolicyNoInfo" class="longbutton" value="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyMessages'/>"
		         onclick="showPolicyInfo();">
</s:if>
<s:if test='handType=="11"'>
		  <Input name="buttonMessage1" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.remarkRecord'/>" onclick="openWinQuery();">
<s:elseif test='handType=="22"'>
		  <Input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.payConditionRecord'/>" onclick="openWinQuery();">
</s:elseif>
</s:if>
        </td>
      </tr>
      <%--added by LanNing end 20070416 改变按钮位置--%>
		<%--add by chengkai;20061114;核批时自动带处批文内容;begin--%>
		<tr>
			<td>
			<IMG name="butDanger" class="button" type="button" alt="<s:text name='undwrt.showApprovalInfo'/>" src="/undwrt/common/images/butCollapse.gif" onclick="showPage(this,PtextInfo)">
       <s:text name="undwrt.EndorseDangerUnits.approvalInfo"/>
			<span id="PtextInfo" style="display:none">
    		<table>      
     			<tr>
     		 		<td class=input>
        			<pre>${endorseText}</pre>
      			</td>
     			</tr>
     		</table>
			</span>
			</td>
			</tr>
		<%--add by chengkai end;--%>

    <!-- 划分风险评估信息Start-->

<s:if test='handType=="11"'>
      <tr>
      <td>
      <IMG name="butDanger" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.riskAssessMessages'/>" 
      src="/undwrt/common/images/butCollapse.gif" onclick="showPage(this,dangerInfo)"><s:text name="undwrt.EndorseDangerUnits.riskAccessInfo0"/>
    <span id="dangerInfo">
     <table width="100%">
        <tr class=common>
        <td>
           <input type="hidden" name="hiRiskLevel"      value="">
           <input type="hidden" name="hiRetCurrency"    value="">
           <input type="hidden" name="hiRetentionValue" value="">
           <input type="hidden" name="hiDangerItemKind" value="">
           <input type="hidden" name="hiDangerFlag"     value="">
         <span style="display:none">
          <table class="common" style="display:none" id="DangerUnit_Data" cellspacing="1" cellpadding="0">
          <tbody>
            <td>
             <table class="common" style="width:100%" cellspacing="1" cellpadding="0">
             				<tr class=common>
             					<td width='5%'><s:text name="undwrt.EndorseDangerUnits.serialNo1"/></td>
			 								<td width='8%'><s:text name="undwrt.EndorseDangerUnits.riskLevel"/></td>
			 								<td width='12%'><s:text name="undwrt.EndorseDangerUnits.riskName"/></td>
			 								<td width='8%'><s:text name="undwrt.EndorseDangerUnits.autoRemainAmount"/></td> 
			 								<s:if test='strClassCode=="27"&&includeAccident=="Y"'>
			 								<td width='5%'  colspan='2'><s:text name="undwrt.EndorseDangerUnits.accidentHealthPMLValue"/></td>
			 								</s:if>
			 								<td width='5%'><s:text name="undwrt.EndorseDangerUnits.currency1"/></td>
			 								<td width='8%'><s:text name="undwrt.EndorseDangerUnits.originalProtectAmount1"/></td>
			 								<td width='8%'><s:text name="undwrt.EndorseDangerUnits.changeProtectAmount1"/></td>
			 								<td width='8%'><s:text name="undwrt.EndorseDangerUnits.originalProtectFee1"/></td>
			 								<td width='8%'><s:text name="undwrt.EndorseDangerUnits.changeProtectFee1"/></td>
			 								<td width='5%'><s:text name="undwrt.EndorseDangerUnits.occupyScale"/>%</td>	
			 								<td width='10%'><s:text name="undwrt.EndorseDangerUnits.sonInfo"/></td>
    						      <td width='5%'>*</td>
									</tr>
									<tr class=common>
										  <td  rowspan ="3">
									  		<input class="readonly" name="dangerNo" readonly description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" ></td>
											<td><input class="free"  name="riskLevel" description="<s:text name='undwrt.CommonDangerUnits.dangerLevel'/>"></td>
									    <td><input class="free" readonly name="riskLevelDesc" description="<s:text name='undwrt.CommonDangerUnits.dangerName'/>" ></td>
									    <td>
    						       <input type='hidden' readonly name="retCurrency" description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>">
									  	<input class="free"  readonly  name="retentionValue" description="s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>">
									  	</td>
									  	<s:if test='strClassCode=="27"&&includeAccident=="Y"'>
									     <td><input class="free"  name="speCurrency" description="<s:text name='undwrt.CommonDangerUnits.PMLCurrencyKind'/>" value=""></td>
									     <td><input class="free"  name="speValue" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>" value="" format="0.00"></td>
									    </s:if>
										<td><input class="free" readonly name="currency" description="<s:text name='undwrt.CommonDangerUnits.currency'/>"></td>					 
										<td><input class="free"  readonly name="amount" description="<s:text name='undwrt.CommonDealContentQta.insureAmout'/>" onblur="checkNumber(this)"></td>
										<td><input class="free" name="chgAmount" description="<s:text name='undwrt.CommonReinsSimulate.changePolicyAmount'/>" onblur="checkNumber(this)"></td>
										<td><input class="free"  readonly name="premium" description="<s:text name='undwrt.EndorseDangerUnits.policyFee'/>" onblur="checkNumber(this)"></td>
									  <td><input class="free" name="chgPremium" description="<s:text name='undwrt.EndorseDangerUnits.changeProtectFee'/>" onblur="checkNumber(this)"></td>
	  						    <td><input class="free"  readonly name="dangerShare" description="<s:text name='undwrt.EndorseDangerUnits.occupyScale'/>" onblur="checkNumber(this)"></td>
	  						    <td  rowspan ="3">
												<input type=button name="buttonShowItem" class=button 
												onclick="showEndorseDangerItem(this,'DangerUnit','0');" value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" style="cursor: hand">
    						        <input type=hidden name="isSavaDangerUnit" value="N"/>
    						    </div>
									  </td>
									  <td  rowspan ="3">
									      <input type=button name="buttonDelete" class=smallbutton 
									      onclick="deletePdangerInfo(this,'DangerUnit');" value="-" style="cursor: hand">
									  </td>
								    </tr>	
    						     <tr class=common>
    						     	 <td><s:text name="undwrt.EndorseDangerUnits.riskName1"/></td>
			                 <td><s:text name="undwrt.EndorseDangerUnits.describe"/></td>
			                 <td colspan="3"><s:text name="undwrt.EndorseDangerUnits.address"/></td>
			                 <td colspan="3"><s:text name="undwrt.EndorseDangerUnits.exDutyApplyBusiness"/></td>
                       <td>进合约<s:text name=""/></td>		
		                 </tr>
    						    <tr class=common>   
    						    	<td>
    						    		<input type="hidden" name="eRiskCode"  description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" >
    						    		<input class="free" name="riskName" description="<s:text name='undwrt.CommonDangerUnits.riskName'/>"></td>           
    						    <td><input class="free" name="dangerDesc" description="<s:text name='undwrt.page.riskUnitDescribe'/>"></td>
									  <td colspan="3"><input class="free" name="dangerAddress" description="<s:text name='undwrt.page.addressDescribe'/>"></td>
	  						    <td colspan="3">
									   <input class="free" type="hidden" name="dangerItemKind" description="<s:text name='undwrt.pages.undwrtDeal.exceptResponsibility'/>" >
									   <input class="free" name="dangerItemKindName" readonly>
	  						        </td>
    						      <td>
    						        <input type="checkbox" align="center" name="dangerItemFlag" description="<s:text name='undwrt.pages.undwrtDeal.intoContract'/>" value="">
    						        <input type="hidden" name="hiDangerItemFlag">
    						      </td>  
								     </tr>	  
		   </table>
		   </td>   
       </tbody>
      </table>
      </span>
  <span  id="spanDangerUnit" style="display:none" cellspacing="1" cellpadding="0">
  <table class="common" cellpadding="5" cellspacing="1" align="center" id="DangerUnit"> 
      <thead>
		  <tr>
			<td  class=listtitle><s:text name="undwrt.EndorseDangerUnits.approvalBillRiskAccessInfo"/></td>
		  </tr>	  
	   </thead>
	  
	   <tfoot>
	   <s:if test="riskUnitFlag==1">
       <tr class=common>
        <td align=left><s:text name="undwrt.EndorseDangerUnits.pressAddMarkEnhanceDangerUnitInfoOrElseSo"/>
        
         <div align="right">
          <input type="button" class=smallbutton value="+" onclick="insertRow('DangerUnit');return showEndorseDangerItem(this,'DangerUnit','NewDangerNo');" name="buttonInsert" style="cursor: hand">
         </div>
       </td>
       </tr>
       </s:if>
    </tfoot>
      <tbody>
      <s:if test='DangerDetail!=null'>
       <s:iterator id="DangerDetail" status="statu" value="DangerDetail">
        <tr>
           <td>
             <table class="common" style="width:100%" cellspacing="1" cellpadding="0">
         <tr class=common>
       <td width='5%'><s:text name="undwrt.EndorseDangerUnits.serialNo2"/></td>
			 <td width='8%'><s:text name="undwrt.EndorseDangerUnits.riskLeve1"/></td>
			 <td width='12%'><s:text name="undwrt.EndorseDangerUnits.riskName1"/></td>
			 <td width='5%'><s:text name="undwrt.EndorseDangerUnits.autoRemainAmount1"/></td>
			 <% if (strClassCode.equals("27") && includeAccident.equals("Y")) {  %>
			 <td width='5%' colspan='2'><s:text name="undwrt.EndorseDangerUnits.accidentHealthPMLValue1"/></td>
			 <% } %>
			 <td width='5%'><s:text name="undwrt.EndorseDangerUnits.currency2"/></td>
			 <td width='8%'><s:text name="undwrt.EndorseDangerUnits.originalProtectAmount2"/></td>
			 <td width='8%'><s:text name="undwrt.EndorseDangerUnits.changeProtectAmount2"/></td>
			 <td width='8%'><s:text name="undwrt.EndorseDangerUnits.originalProtectFee2"/></td>
			 <td width='8%'><s:text name="undwrt.EndorseDangerUnits.changeProtectFee2"/></td>
			 <td width='5%'><s:text name="undwrt.EndorseDangerUnits.occupyScale1"/>%</td>
			 <td width='10%'><s:text name="undwrt.EndorseDangerUnits.sonInfo1"/></td>
       <td width='5%'>*</td>
			</tr>
			<tr  class=common>
				<td rowspan ="3"><input class="readonly"   name="dangerNo" description="<s:text name='undwrt.CommonDangerUnits.serialNo'/>" value="<s:property value="#DangerDetail.dangerNo" />"></td> 
			  <td><input class="free"  name="riskLevel" description="<s:text name='undwrt.CommonDangerUnits.dangerLevel'/>" value="<s:property value="#DangerDetail.riskLevel" />"></td>
			  <td><input class="free"   readonly name="riskLevelDesc" description="<s:text name='undwrt.pages.undwrtDeal.riskGradeDescribe'/>" value="<s:property value="#DangerDetail.riskLevelDesc" />"></td>
			  <td><input class="free" type='hidden'  readonly name="retCurrency" description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>"  value="<s:property value="#DangerDetail.retCurrency" />">
			  	<input class="free" width="80%"readonly  name="retentionValue"  description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" value="<s:property value="#DangerDetail.retentionValue" />"></td>
		
			  <% if (strClassCode.equals("27") && includeAccident.equals("Y")) {  %>
			   <td><input class="free"  name="speCurrency" description="<s:text name='undwrt.CommonDangerUnits.PMLCurrencyKind'/>" value="<s:property value="#DangerDetail.speCurrency" />"></td>
			   <td><input class="free"  name="speValue" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>" value="<s:property value="#DangerDetail.speValue" />"></td>
			  <% } %>
			  <td ><input class="free" readonly name="currency" description="<s:text name='undwrt.CommonDangerUnits.currency4'/>" value="<s:property value="#DangerDetail.currency" />"></td>
			  <td><input class="free" readonly name="amount"  description="<s:text name='undwrt.CommonDealContentQta.insureAmout'/>" value="<s:property value="#DangerDetail.amount" />" onblur="checkNumber(this)"></td>
			  <td><input class="free" readonly name="chgAmount"  description="<s:text name='undwrt.CommonReinsSimulate.changePolicyAmount'/>" value="<s:property value="#DangerDetail.chgAmount" />" ></td>
			  <td><input class="free" readonly name="premium" readonly description="<s:text name='undwrt.EndorseDangerUnits.policyFee'/>" value="<s:property value="#DangerDetail.premium" />" onblur="checkNumber(this)"></td>
		    <td><input class="free" readonly name="chgPremium" readonly description="<s:text name='undwrt.EndorseDangerUnits.changeProtectFee'/>" value="<s:property value="#DangerDetail.chgPremium" />"></td>
        <td><input class="free" readonly name="dangerShare" description="<s:text name='undwrt.EndorseDangerUnits.occupyScale'/>" value="<s:property value="#DangerDetail.dangerShare" />" onblur="checkNumber(this)"></td>
        <td rowspan ="3">
			    <input type=button name="buttonShowItem" class=button onclick="showEndorseDangerItem(this,'DangerUnit');" value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" style="cursor: hand">
          <input type=hidden name="isSavaDangerUnit" value="N"/>
			  </td>
			  <td rowspan ="3">
           <input type=button name="buttonDelete" class=smallbutton onclick="deletePdangerInfo(this,'DangerUnit');" value="-" style="cursor: hand">
			  </td>	
		    </tr>
		    
        <tr class=common>
         <td><s:text name="undwrt.EndorseDangerUnits.riskName2"/></td>
			   <td ><s:text name="undwrt.EndorseDangerUnits.describe1"/></td>
			   <td colspan="3"><s:text name="undwrt.EndorseDangerUnits.address1"/></td>
			   <td colspan="3"><s:text name="undwrt.EndorseDangerUnits.exDutyApplyBusiness1"/></td>
         <td><s:text name="undwrt.EndorseDangerUnits.intoContract1"/></td>		
		     </tr>

		    <tr class=common>
		      <td>
		      	<input type="hidden" name="eRiskCode"  description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" 
         	value="<s:property value="riskCode" />">
         		<input class="free" name="riskName" value="<s:property value="riskName" />"></td>
			    <td><input class="free" name="dangerDesc" description="<s:text name='undwrt.page.riskUnitDescribe'/>" value="<s:property value="dangerDesc" />"></td>
			  	<td colspan="3"><input class="free" name="dangerAddress" description="<s:text name='undwrt.ShowDangerItem.address1'/>" value="<s:property value="addressName" />"></td>
			  	<td colspan="3"><input class="free" type="hidden" name="dangerItemKind" description="<s:text name='undwrt.ShowDangerItem.exDuty'/>"
				 		value="<s:property value="itemKind" />">
					<input type="test" name="dangerItemKindName" readonly class="free" 	
					value="<s:property value="itemKindDesc" />" >
        </td> 
			  <td>
			  <input type="checkbox" name="dangerItemFlag" description="<s:text name='undwrt.CommonDangerUnits.enterConract'/>" 
			  <s:if test="DangerDetail.flag==10">checked</s:if>
			  <s:if test="DangerDetail.flag==11">checked</s:if>
			      value="<s:property value="#DangerDetail.flag" />">
			  <input type="hidden" name="hiDangerItemFlag" value="00">
			  </td>				 
			 </tr>			    
		    </table>
		    </td>
        </s:iterator>
        </s:if>
        
      </tr>
     </tbody>
  </table>
  </span>

    </td>
     </tr>
    </table>
   </span>
  </td>
  </tr>

       
	   
	   <tr width=100%>
	   <td>
	     <table width=100% border="0" style="display:none">
	     <tr>
	     <s:if test='editType=="query"'>
            <td class=button width="33%">
                <input type="button" class="longbutton" name="allEvaluate" description="<s:text name='undwrt.CommonDangerUnits.riskAssessInfo'/>"
                 value="<s:text name='undwrt.CommonDangerUnits.riskAssessInfo'/>" onclick="showEvaluateRiskInfo(this)">
            </td>
          </s:if>
           
            <td class=button width="33%">
            <Input name="ReinsTrial" type="button" class=button value="<s:text name='undwrt.CommonDangerUnits.divideBatchTestAccount'/>" onclick="endorseSimulateReinsByDanger()">
             <s:if test="AmountAndPremiumDto!=null">
			  <input type="hidden" name="tolAmount" value="<s:property value="#AmountAndPremiumDto.amount" />">
              <input type="hidden" name="tolPremium" value="<s:property value="#AmountAndPremiumDto.premium" />">
             </s:if>
        <input type="hidden" name="endorNo"     value="${businessNo}">
        <input type="hidden" name="policyNo" description="<s:text name='undwrt.CommonDangerUnits.PolicyNo'/>" value="<s:property value="#PrpCmainCovernoteDto.policyNo" />">
        <s:if test='businessFlag==1'>
        <Input name="ReinsTrial" type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.submitPointsAffirm'/>" onclick="reinsPolicyVerify(endorNo)">
		</s:if>

           </td>
        
	       <td class=button width="34%">     
            <Input name="butSubmitReins" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.partIntention'/>" value="<s:text name='undwrt.pages.undwrtDeal.partIntention'/>" onclick="submitReins()" >
           </td>
         </tr>
        </table>
	   </td>
      </tr>    
</s:if>
 	
 </table>
 </td>
</tr>
