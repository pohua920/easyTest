<!--***************************************************************************
* Description: 拆分危险单位页面
* Author     : LUYANG
* CreateDate : 2005-5-4 20:30
* UpdateLog  ：Name       Date            Reason/Contents
*           zhangruifeng 20071217 当是车险联合出单时在审核商业险投保单信息时增加查看交强险信息按钮
*           yanglibo     20080826       增加千分位显示格式化
             yanglibo     20080828        去掉风险评估信息里的千分位
*              zhangfan    20080902       传入批单中的comcode
*               yanglibo  20080918          15险类原始标的显示
*              douzongxing  20081126       增加每次事故赔偿限额             
*              zhouhui      20090722       免导团单查看详细信息时，调用自己的页面                  
****************************************************************************-->

<%@page import="com.sinosoft.sysframework.reference.DBManager"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPrpDriskConfigAction"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDriskConfigDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="com.sinosoft.claim.dto.domain.PrpLchargeDto"%>
<%@page import="com.sinosoft.prpall.dto.domain.*"%>
<%@page import="com.sinosoft.prpall.dto.domain.PrpCmainDto"%>
<%@page import="com.sinosoft.utility.UtiPower"%>
<%@page import="com.sinosoft.utility.SysConfig"%>
<%@page import="com.sinosoft.platform.dto.domain.PrpDuserDto"%>
<%@page import="com.sinosoft.sysframework.common.Constants"%>
<%@page import="com.sinosoft.platform.ui.control.action.UIPowerAction"%>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.utility.string.*"%>
<%@page import="com.sinosoft.utility.string.ChgDate"%>
<%@page import="com.sinosoft.prpall.ui.model.UtiPrintPageFindByConditionsCommand"%>
<%@page import="com.sinosoft.utiall.blsvr.BLPrpDconfigCode"%>
<%@page import="com.sinosoft.reins.in.bl.facade.BLPrpReinsVerifyFacade"%>
<%@page import="com.sinosoft.reins.in.dto.domain.PrpReinsVerifyDto"%>
<%@page import="com.sinosoft.platform.bl.facade.BLPrpDpreauditConfigFacade"%>
<%@page import="com.sinosoft.prpall.blsvr.tb.*" %>
<%@page import="com.sinosoft.undwrt.common.model.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

 <!--  add by qiuxia.lai for fubon-2711 begain -->

<%@page import="com.sinosoft.prpall.resource.dtofactory.domain.DBPrpTmain"%>
<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>

<!--  add by qiuxia.lai for fubon-2711 end-->
<jsp:directive.page import="com.sinosoft.reins.out.dto.domain.PrpTdangerUnitDto"/>
<jsp:directive.page import="com.sinosoft.reins.out.dto.domain.PrpPdangerUnitDto"/>
<%@page import="com.sinosoft.prpall.blsvr.tb.BLPrpTmainSub"%>
<%@page import="com.sinosoft.utility.database.DbPool"%>
<%@page import="com.sinosoft.prpall.blsvr.tb.BLPrpTmain"%>
<%@page import="com.sinosoft.undwrt.common.vo.*" %>
<%
//String riskCode = (String)session.getAttribute("riskCode");
//是否续保业务标记zhutq
boolean isRenewal = false;
String oldPolicyNo = "";
if(isRenewal){
	oldPolicyNo = (String)request.getAttribute("oldPolicyNo");
}

  java.text.DecimalFormat decimalFormat1 = new java.text.DecimalFormat("#,##0.00");
  java.text.DecimalFormat decimalFormat= new java.text.DecimalFormat("0.00");
  java.text.DecimalFormat gradeFormat= new java.text.DecimalFormat("0.000");
  String riskUnitFlag   = ""; //是否拆分危险单位标识
  String requiredReins  = ""; //是否强制分保试算
  String reinsOfflineFlag  = ""; //是否离线计算
  String strsumAmount="";
  String strsumPremium="";
  boolean allowSpecial   = false; //是否允许录入特殊因子
  boolean allowSpecial_user = false; //操作员是否有录入特殊因子的权限
  //0507关联单处理
  String strproposalno = "";
	  
  String businessType   = (String)session.getAttribute("iBusinessType");

  boolean blnIsPreaudit = false;
  boolean blnSplitDangerUnit = false;

  String strGoalInsuredFlag = "0";
  String strComCode = "";
  double profitRate = 0.00;   //优惠
  double disRate = 0.00;      //手续费
  String color = "";          //用于高亮显示超过15%的手续费
  String strLanguage ="";  //取语言种类
  String strRiskCodeCI ="" ; //交强险险种代码
  String strBusinessNoCI = ""; //关联交强险投保单号

  //add by zhaoning20091109 end
  ChgDate nowDate = new ChgDate();
  String strNotifyPath = "";
  
    //add by zhaoning20091109 begin Reason:除了预处理岗，其他双核级别都具有拆分危险单位的操作权限  经fubon项目组确认屏蔽此部分
   //if(prpDriskConfigDto!=null && !blnIsPreaudit && blnSplitDangerUnit)
   //{
    // riskUnitFlag = "1";
   //}
   //add by zhaoning20091109 end

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

   
%>
  <script language='javascript'>
    function Print(Flag)
    {
      var strURL;
      var strTypeFlag;
      var strBizNo = fm.hiBusinessNo.value;
      var vRiskcode= fm.riskCode.value;
      if(strBizNo=="")
      {
        errorMessage("<s:text name='undwrt.pages.undwrtDeal.CommonDangerUnits.alert'/>!");
        return false;
      }
      var prpallIP = fm.PrpallIp.value;
      //保单打印
      if (Flag=="11")  //保单抄件正本打印
      {
      	  strURL = prpallIP+"/prpall/"+vRiskcode+"/tb/UIProposal"+vRiskcode+"NoneFormatPrint.jsp?BizNo="+strBizNo+"&EDITTYPE=MASTER&NotEdit=0";
      } 
      if (Flag=="61"){
        strURL = prpallIP+"/prpall/commonship/pub/UIEditReportPrint.jsp?BizNo=" + strBizNo + "&BizType=0";
      }
      if (Flag=="62"){
        strURL = prpallIP+"/prpall/commonship/pub/UIEditReportPrint.jsp?BizNo=" + strBizNo + "&BizType=0&PrintType=1";
      }    //aded by LanNing end 20070813 意外险团体清单打印
    
    strURL += "&FlagForPrint=" + Flag;
    printWindow(strURL,"<s:text name='undwrt.print'/>");
 	
 	}
 	
 	//显示打印窗口

function printWindow(strURL,strWindowName)

{

  var pageWidth=screen.availWidth-10;

  var pageHeight=screen.availHeight-30;

  if (pageWidth<100 )

    pageWidth = 100;



  if (pageHeight<100 )

    pageHeight = 100;



  var newWindow = window.open(strURL,strWindowName,'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');

  newWindow.focus();

  return newWindow;

}
//zhutq 点击查看上张保单赔案信息按钮
function checkLastPolicyClaimInfoList(){
	var strURL = "/undwrt/common/LastPolicyClaimInfoList.jsp?oldPolicyNo=<%=oldPolicyNo%>&pageNo=1";    
  //window.open(strURL,'AddCustomer','Owidth=640,height=420,top=50,left=80,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  window.open(strURL,'<s:text name="undwrt.pages.undwrtDeal.CommonDangerUnits.open"/>','top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}
  </script>
<tr class=common>
  <td colspan="4">
      <input type=hidden name="PrpallIp" value="<s:property value="iPrpallIp"/>">
            <input type=hidden name="hiBusinessNo" value="<s:property value="iBusinessNo"/>">
      <input type=hidden name="hiBusinessType" value="<s:property value="iBusinessType"/>">
      <input type="hidden" name="riskUnitFlag" value="<s:property value="riskUnitFlag"/>">   <!--是否需要拆分危险单位标志1为允许-->
      <input type="hidden" name="requiredReins" value="<s:property value="requiredReins"/>"> <!--是否强制分保试算-->
        <input type="hidden" name="hiRiskLevel"      value="">
          <input type="hidden" name="hiRetCurrency"    value="">
          <input type="hidden" name="hiRetentionValue" value="">
          <input type="hidden" name="hiDangerItemKind" value="">
          <input type="hidden" name="hiDangerFlag"     value="">
          <input type="hidden" name="hiRiskLevelDesc"  value="">
          <input type="hidden" name="includeAccident"  value="<s:property value="includeAccident"/>">

       <s:if test="prpTmainSubSchema!=null">
        <input type="hidden" name="riskCodeCI" value=<s:property value="#prpTmainSubSchema.riskCode"/>><!-- 关联交强险保单号 -->
        <input type="hidden" name="businessNoCI" value=<s:property value="#prpTmainSubSchema.proposalNo"/>>
       </s:if>
       
    <input type="hidden" name="businessFlag" value=<s:property value="#prpTmainVo.businessFlag"/>>
    <!-- add by yangxintao 分入业务临分意向前要先再保分入确认完成  -->
    <input type="hidden" name="verifyFlag" value=<s:property value="verifyFlag"/>>
    <span id="spanInfo" >
    <table width=100%>


      <!--投保单信息-->
      <tr>
      <td width="100%">
       <table cellpadding="5" cellspacing="1" class="common" align="center" style="width:100%">
       <s:if test="#request.PrpTmainDto != null">
          <tr class=listtitle>
          	<td colspan="4" ><s:text name="undwrt.CommonDangerUnits.throwPolicySumInfo"/></td>
          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.risk"/>：</td>
            <td class=input4><s:property value="#request.PrpTmainDto.riskCode" /></td>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.belongOrganization"/>：</td>
            <td class=input4><s:property value="#request.PrpTmainDto.comCode" /></td>
          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.riskName"/>：</td>
            <td class=input4><s:property value="riskCName"/></td>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.belongOrganizationName"/>：</td>
            <td class=input4><s:property value="comCName"/></td>
          </tr>     
          <tr class="mline" >     
          	<td  class=title4><s:text name="undwrt.CommonDangerUnits.throwPolicyNo"/>：</td>
            <td class=input4><s:property value="#request.PrpTmainDto.proposalNo"/></td>
            		<!--modify begin by lihua 提交分入确认-->
            	<input type="hidden" name="proposalNo" description="<s:text name='undwrt.pages.undwrtDeal.insureBillNo'/>" value="<s:property value="#request.PrpTmainDto.proposalNo"/>"/>
            	<!--modify end by lihua 提交分入确认-->
            <td class=title4><s:text name="undwrt.CommonDangerUnits.prePolicyWhetherClaim"/>：            
            <input type="hidden" name="riskCode" description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" value="<s:property value="#request.PrpTmainDto.riskCode"/>">
            <input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>" value="<s:property value="#request.PrpTmainDto.classCode"/>">
            </td>
            <s:if test='isRenewal'>
                <td class=input4> <font color='red'><s:text name="undwrt.CommonDangerUnits.have"/></font>&nbsp; <Input type="button" class="longbutton" name="claimInfo" value="<s:text name='undwrt.CommonDangerUnits.prePolicyPayInfo'/>" style="color:red" onclick="checkLastPolicyClaimInfoList();">  </td>
            </s:if>
            <s:else>
            	<td class=input4> <font color='red'><s:text name="undwrt.CommonDangerUnits.noHave"/></font></td>
            </s:else>
          </tr>
          <tr>

           <s:if test='strClassCode == "26" || strClassCode == "27"'>
             <td class=title4><s:text name="undwrt.CommonDangerUnits.policyerName"/>：</td>
            <td class=input4><s:property value="#request.PrpTmainDto.appliName"/></td>
            </s:if>
            <s:else>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.insuredName"/>：</td>
            <td class=input4><s:property value="#request.PrpTmainDto.insuredName"/></td>
            </s:else>

            <s:if test='strClassCode == "09" || strClassCode == "10"'>
	            <td class=title4><s:text name="undwrt.CommonDangerUnits.startDate"/>：</td>
	            <td class=input4><s:property value="#request.PrpTmainDto.startDate"/></td>
            </s:if>
            <s:else>
	            <td class=title4><s:text name="undwrt.CommonDangerUnits.insureDuration"/>：</td>
	            <td class=input4>${PrpTmainDto.startDate}&nbsp;<s:text name="undwrt.CommonDangerUnits.to"/>&nbsp;${PrpTmainDto.endDate}</td>
            </s:else>

          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.sumInsureAmount"/>：</td>
            <td class=input4>
                   <s:property value="#request.PrpTmainDto.currency"/>&nbsp;
                   <input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${PrpTmainDto.sumAmount}" pattern="#,##0.00"/>">
            </td>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.sumInsureFee"/>：</td>
            <td class=input4><s:property value="#request.PrpTmainDto.currency"/>&nbsp;
            	<input type="hidden" name="TemCurrency" value="<s:property value="#request.PrpTmainDto.currency"/>"/>
            	<input type="text" class=readonly name="sumAmount" value="<fmt:formatNumber value="${PrpTmainDto.sumPremium}" pattern="#,##0.00"/>"></td>
           
            </tr>
          <%-- add by xiaojian 20051204_1 begin reason：投保（保）单核保时可见、可修改特殊因子 --%>

          <tr style="display:<s:property value="strDisplay"/>">
            <td class="title4"><s:text name="undwrt.CommonDangerUnits.specialFactor"/>：</td>
            <td class="input4">
              <input type="hidden" name="DisRate1Old" value="<s:property value="#request.PrpTmainDto.disRate1"/>">
              <input type="text" name="DisRate1" class="<s:property value="className"/>"
              	value="<fmt:formatNumber value="${PrpTmainDto.disRate1}" pattern="0.0000"/>" onblur="changeDisFee1(this)">
              	
            </td>
            <td class="title4"><s:text name="undwrt.CommonDangerUnits.specialFactorAmount"/>：</td>
            <td class="input4">
              <input type="hidden" name="Premium1" value="<s:property value="dblPremium1" />">
              <input type="text" name="DisFee1" class="readonly" readonly value="<s:property value="dblDisFee1" />">
            </td>
          </tr>
          <%-- add by xiaojian 20051204_1 end --%>
          <!-- add by zhulei 20060423 管理费比例 begin -->
          <s:if test="prpTexpenseDto != null">
            <tr style="display:<s:property value="strManageFeeDisplay"/>">
              <td class="title4"><s:text name="undwrt.CommonDangerUnits.managerScale"/>：</td>
              <td class="input4">
                <input type="hidden" name="ManageFeeRateOld" value="<s:property value="prpTexpenseVo.manageFeeRate"/>" >
                <input type="text" name="ManageFeeRate" class=<s:property value="className"/> value="<s:property value="prpTexpenseVo.manageFeeRate"/>"
                  onblur="changeManageFeeRate(this)">
              </td>
              <td class="title4"><s:text name="undwrt.CommonDangerUnits.managerAmount"/>：</td>
              <td class="input4">
                <input type="hidden" name="Premium2" value="<s:property value="dblPremium1" />">
                <input type="text" name="ManageFee" class="readonly" readonly value="<s:property value="dbManageFee" />">
              </td>
            </tr>
          </s:if>
          <!-- add by zhulei 20060423 管理费比例 end -->
          <%-- add by xiaojian 20051112_1 begin reason：增加是否目标客户 --%>
          <tr style="display: none;">
            <td class="title4"><s:text name="undwrt.CommonDangerUnits.whetherTargetCustomer"/>：</td>
            <td class="input4"><s:property value="strGoalInsuredFlag"/></td>
            <td class="title4"></td>
            <td class="input4"><input type="hidden" name="policyType" value="<s:property value="#request.PrpTmainDto.policyType"/>"></td>
          </tr>
          <%-- add by xiaojian 20051112_1 end --%>
          <!--add by zhulei begin 20060213 净费比例（仅车险显示）-->
<s:if test='#request.PrpTmainDto.classCode == "A" || #request.PrpTmainDto.classCode == "B"'>
            <td class="title4"><s:text name="undwrt.CommonDangerUnits.handFeeScale"/>(%)：</td>
            <td class=input4 style="color:'<s:property value="color"/>'"><s:property value="disRate"/></td>
            <%--
            <td class="input4"><%=request.getAttribute("outLayRate")%></td>
            --%>
            <td class="title4"><s:text name="undwrt.CommonDangerUnits.favorableScale"/>(%)：</td>
            <td class=input4><s:property value="profitRate"/></td>
            <%--
            <td class="title4">折扣比例(%)：</td>
            <td class="input4"><%=request.getAttribute("	")%></td>
            --%>
</s:if>
<s:else>
            <td class="title4"></td>
            <td class="input4"></td>
            <td class="title4"></td>
            <td class="input4"></td>
</s:else>
          <!--add by zhulei end 20060213 净费比例（仅车险显示）-->
<s:if test='#request.PrpTmainDto.classCode != "A" && #request.PrpTmainDto.classCode != "B" && #request.PrpTmainDto.riskCode != "9997" && #request.PrpTmainDto.riskCode != "9998" && #request.PrpTmainDto.riskCode != "9999"'>
<%--         <tr style="display:<s:property value="strGradeDisplay"/>"> --%>
        <tr style="display:none">
          <td class=title4><s:text name="undwrt.CommonDangerUnits.rankProvenance"/>：</td>
          <td class=input4>
          	<input type="text" name="GradeSource" class="readonly" readonly value="<s:property value="strGradeSource"/>">
          </td>
          <td class="title4"></td>
          <td class="input4"></td>
        </tr>
        <tr style="display:<s:property value="strDisplayRate"/>">
          <td class=title4><s:text name="undwrt.CommonDangerUnits.outBillRate"/>：</td>
          <td class=input4>
          	<input type="text" name="MakeRate" class="readonly" readonly value="<s:property value="dblMakeRate"/>">%
          </td>
          <td class=title4><s:text name="undwrt.CommonDangerUnits.pureRate"/>：</td>
          <td class=input4><input type="text" name="NetPremiumRate" class="readonly" readonly value="<s:property value="dblNetPremiumRate"/>">%</td>
        </tr>
        <tr style="display:<s:property value="strGradeDisplay"/>">
          <td class=title4><s:text name="undwrt.CommonDangerUnits.mediumhandFeeRate"/>：</td>
          <td class=input4><input type="text" name="DisRate" class="readonly" readonly value="<s:property value="dblDisRate"/>">%</td>
          <td class="title4"></td>
          <td class="input4"></td>
        </tr>
        <tr style="display:<s:property value="strGradeDisplay"/>">
          <td class=title4><s:text name="undwrt.CommonDangerUnits.autoGrading"/>：</td>
          <td class=input4>
          	<input type="text" name="AutoGradeCode" class="readonly" readonly value="<s:property value="strAutoGradeCode"/>">
          	<input type="hidden" name="GradeCode" value="" description="<s:text name='undwrt.pages.undwrtDeal.businessRankCode'/>">
          	<input type="hidden" name="GradeValue" value="" description="<s:text name='undwrt.pages.undwrtDeal.businessRankValue'/>">
          	<input type="hidden" name="RelBusinessFlag" description="<s:text name='undwrt.pages.undwrtDeal.ifRelatedBusiness'/>" value="<s:property value="strRelBusinessFlag"/>">
          	<input type="hidden" name="AutoGradeValue" value="<s:property value="dblAutoGradeValue"/>">
          	<input type="hidden" name="HistoryBusiness" value="<s:property value="strHistoryBusiness"/>" description="<s:text name='undwrt.pages.undwrtDeal.ifHistoryBusiness'/>">
          </td>
          <td class=title4><s:text name="undwrt.CommonDangerUnits.autoGradingMaxUseFeeRate"/>：</td>
          <td class=input4><input type="text" name="AutoMaxUsableRate" class="readonly" readonly value="<s:property value="dblAutoMaxUsableRate"/>">%</td>
        </tr>
        <tr style="display:<s:property value="strGradeDisplay"/>">
          <td class="title4"><s:text name="undwrt.CommonDangerUnits.handGrading"/><s:text name=""/>：</td>
          <td class="input4">
          	<select class=common name="ManualGrade" onchange="checkGrade()">
          		<option value="1,1">----- <s:text name="undwrt.CommonDangerUnits.pleaseChoose"/> -----</option>
                <s:if test="gradeCalculator != null">
                	<s:iterator value="gradeCalculator" status="statu" id="gradeCalculator">
	                   <s:if test="strPreManualGradeCode == gradeCalculator.gradeValues">
	                      <option value='<s:property value="#gradeCalculator.gradeValues"/>' selected><s:property value="#gradeCalculator.gradeCodes"/></option>					
					   </s:if>
					   <s:else>
					   	  <option value='<s:property value="#gradeCalculator.gradeValues"/>' ><s:property value="#gradeCalculator.gradeCodes"/></option>			   
					   </s:else>
				   </s:iterator>
				</s:if>
          
          	</select>
          </td>
          <td class=title4><s:text name="undwrt.CommonDangerUnits.handGradingMaxUseFeeRate"/>：</td>
          <td class=input4><input type="text" name="MaxUsableRate" class="readonly" readonly value="<s:property value="dblMaxUsableRate"/>">%</td>
        </tr>
        <tr style="display:<s:property value="strGradeDisplay"/>">
          <td class=title4><s:text name="undwrt.CommonDangerUnits.preGrading"/>：</td>
          <td class=input4><input type="text" name="PreGradeCode" class="readonly" readonly value="<s:property value="strPreGradeCode"/>"></td>
          <td class="title4"><s:text name="undwrt.CommonDangerUnits.preUnderwriterSuggestion"/>：</td>
          <td class="input4"><input type="text" name="PreHandleText" class="readonly" readonly style="word-wrap:break-word;word-break:break-all" value="<s:property value="strPreHandleText"/>"></td>
        </tr>
        <tr style="display:<s:property value="strGradeDisplay"/>">
          <td class=title4><s:text name="undwrt.CommonDangerUnits.relateBusiness"/>：</td>
          <td class=input4><Input name="RelBusinessBtn" class="button" type="button" value="<s:text name='undwrt.EndorseDangerUnits.relationBusiness'/>" onclick="getRelBusiness();"></td>
          <td class="title4"><s:text name="undwrt.CommonDangerUnits.rankTrackInfo"/>：</td>
          <td class="input4"><Input name="GradeTrackBtn" class="longbutton" type="button" value="<s:text name='undwrt.EndorseDangerUnits.rankTrackInfo'/>" onclick="getGradeTraceInfo();"></td>
        </tr>
</s:if>
<!--     add by zhaoning20090420 end -->


        <!-- 保单信息 -->
      <s:if test="#request.prpCmainDto != null">
          <tr class=listtitle>
<s:if test='handType == "22" && (businessType == "C" ||businessType = "Y")'>
            <td colspan="4" ><s:text name="undwrt.CommonDangerUnits.policyAndcompensateSummaryInfo"/></td>
</s:if>
<s:else>
            <td colspan="4" ><s:text name="undwrt.CommonDangerUnits.policySummaryInfo"/></td>
</s:else>
          </tr>
           <tr>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.risk1"/>：</td>
            <td class=input4><s:property value="#request.prpCmainDto.riskCode"/></td>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.belongOrganization1"/>：</td>
            <td class=input4><s:property value="#request.prpCmainDto.comCode"/></td>
          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.riskName1"/>：</td>
            <td class=input4><%=request.getAttribute("riskCName")%></td>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.belongOrganizationName1"/>：</td>
            <td class=input4><%=request.getAttribute("comCName")%></td>
          </tr>
          <tr>
            <td  class=title4><s:text name="undwrt.CommonDangerUnits.PolicyNo"/><s:text name=""/>：</td>
            <td class=input4><s:property value ="prpCmainDto.policyNo"/></td>
            <td class=title4>
            <input type="hidden" name="riskCode" description="<s:text name='policyManage.riskCode'/>" value="<s:property value="#request.prpCmainDto.riskCode"/>"></td>
            <input type="hidden" name="hiClassCode" description="<s:text name='undwrt.CommonDangerUnits.riskTypeCode'/>" value="<s:property value="#request.prpCmainDto.classCode"/>">
            <td class=input4>&nbsp;</td>
          </tr>
           <tr>
           <s:if test='strClassCode =="26" || strClassCod == "27"'>
             <td class=title4><s:text name="undwrt.CommonDangerUnits.policyerName1"/>：</td>
            <td class=input4><s:property value="#request.prpCmainDto.appliName"/></td>
           </s:if>
           <s:else>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.insuredName1"/>：</td>
            <td class=input4><s:property value="#request.prpCmainDto.insuredName"/></td>
           </s:else>
           <s:if test='strClassCode == "09" || strClassCode == "10" '>

            <td class=title4><s:text name="undwrt.CommonDangerUnits.startDate1"/>：</td>
           </s:if>
           <s:else>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.insureDuration1"/>：</td>
           </s:else>
            <td class=input4><s:property value="#rpCmainVo.startDate"/>&nbsp;<s:text name="undwrt.CommonDangerUnits.to1"/>&nbsp;<s:property value="prpCmainVo.endDate"/></td>
          </tr>
          <tr>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.sumInsureAmount1"/>：</td>
            <td class=input4><s:property value="#prpCmainDto.currency"/>&nbsp;<s:property value="prpCmainVo.sumAmount"/></td>
            <td class=title4><s:text name="undwrt.CommonDangerUnits.sumInsureFee"/>：</td>
            <td class=input4><s:property value="#prpCmainDto.currency"/>&nbsp;<s:property value="prpCmainVo.sumPremium"/></td>
          </tr>
          <%-- add by xiaojian 20051204_2 begin reason：投保（保）单核保时可见、可修改特殊因子 --%>

<!--    modify by xiaojian 20051229 begin reason：核赔也调用此段程序，但是核赔没有放入对象AmountAndPremiumDto  -->

  <s:if test='businessType!="C" && businessType!="Y"'>

          <tr style="display:<s:property value="#strDisplay"/>">
            <td class="title4"><s:text name="undwrt.CommonDangerUnits.specialFactor"/><s:text name=""/>：</td>
            <td class="input4">
              <input type="hidden" name="DisRate1Old" value="<s:property value="#prpCmainDto.disRate1"/>">
              <input type="text" name="DisRate1" class=<s:property value="className"/> value="<s:property value="#prpCmainDto.disRate1"/>"
                onblur="changeDisFee1(this)">
            </td>
            <td class="title4"><s:text name="undwrt.CommonDangerUnits.specialFactorAmount1"/>：</td>
            <td class="input4">
              <input type="hidden" name="Premium1" value="<s:property value="#dblPremium1"/>">
              <input type="text" name="DisFee1" class="readonly" readonly value="<s:property value="#dblDisFee1"/>">
            </td>
          </tr>
          <%-- add by xiaojian 20051204_2 end --%>
          <%-- add by xiaojian 20051112_2 begin reason：增加是否目标客户 --%>
          <tr>
            <td class="title4"><s:text name="undwrt.CommonDangerUnits.whetherTargetCustomer1"/>：</td>

<!-- //     if(prpCmainDto.getOthFlag().length()>=15) -->
<!-- //       strGoalInsuredFlag = prpCmainDto.getOthFlag().substring(14,15); -->
<!-- //     if(strGoalInsuredFlag.equals("1")) -->
<!-- //       strGoalInsuredFlag = "是"; -->
<!-- //     else -->
<!-- //       strGoalInsuredFlag = "否"; -->

            <td class="input4"><s:property value="#strGoalInsuredFlag"/></td>
            <td class="title4"></td>
            <td class="input4"><input type="hidden" name="policyType" value="<s:property value="#prpCmainDto.policyType"/>"></td>
          </tr>
          <%-- add by xiaojian 20051112_2 end --%>
</s:if>
<%-- <%  --%>
//           if(handType.equals("22") && (businessType.equals("C")||businessType.equals("Y"))){
        	  
//                //modify by qinyongli 2005-9-16
//                ArrayList chargeList = (ArrayList)request.getAttribute("prplchargeList");
//                Iterator theCharge = chargeList.iterator();
//                PrpLchargeDto prpLchargeDto = new PrpLchargeDto();
//                int lines= 1 ;
//                while(theCharge.hasNext()){
//                     prpLchargeDto =(PrpLchargeDto) theCharge.next();
//                     if(lines%2!=0){
//                        out.println("<tr>");
//                     }
//                         double sumCharge  = prpLchargeDto.getChargeAmount();
//                         double sumRealPay = prpLchargeDto.getSumRealPay();
//                         double charge  =  sumCharge ;
//                         out.println("<td  class='title4'><b>"+prpLchargeDto.getChargeName()+"</b></td>");
//                         out.println("<td  class='input4'><b>"+charge+"</b></td>");
//                     if(lines%2==0){
//                        out.println("</tr>");
//                     }
//                     lines++;
//                }
//                     if(lines%2==0){
//                        out.println("<td class=title4></td><td class=input4></td>");
//                        out.println("</tr>");
//                     }

<%--          %> --%>
      <%--tr>
      <td class="title4">保品损失：</td>
      <td class="input4"><bean:write name="PolicyAbstractInfoDto" property="sumLoss" format="0.00"/></td>
      <td class="title4">第三者赔付/共同海损（货运）：</td>
      <td class="input4"><bean:write name="PolicyAbstractInfoDto" property="sumSumRealPay11" format="0.00"/></td>
      </tr>
      <tr>
      <td class="title4">整理施救费用：</td>
      <td class="input4"><bean:write name="PolicyAbstractInfoDto" property="sumSumRealPay03" format="0.00"/></td>
      <td class="title4">查勘费：</td>
      <td class="input4"><bean:write name="PolicyAbstractInfoDto" property="sumSumRealPay04" format="0.00"/></td>
      </tr>
      <tr>
      <td class="title4">公估、检验费：</td>
      <td class="input4"><bean:write name="PolicyAbstractInfoDto" property="sumSumRealPay13" format="0.00"/></td>
      <td class="title4">代理费：</td>
      <td class="input4"><bean:write name="PolicyAbstractInfoDto" property="sumSumRealPay09" format="0.00"/></td>
      </tr>
      <tr>
      <td class="title4">其他费用：</td>
      <td class="input4"><bean:write name="PolicyAbstractInfoDto" property="other" format="0.00"/></td>
      <td class="title4">结案合计：</td>
      <td class="input4"><bean:write name="PolicyAbstractInfoDto" property="sumPaid" format="0.00"/></td>
      </tr--%>

       <tr>
      <td class="title4"><b><s:text name="undwrt.CommonDangerUnits.targetLoss"/>：</b></td>
      <td class="input4"><b><s:property value="#policyAbstractInfoDto.sumLoss"/></b></td>
      <td class="title4"><b><s:text name="undwrt.CommonDangerUnits.total"/>：</b></td>
      <td class="input4"><b><s:property value="#policyAbstractInfoDto.sumPaid"/></b></td>
      </tr>
     </s:if>
        </s:if>
        </table>
       </td>
      </tr>
    <%--added by LanNing begin 20070416 修改按钮位置--%>
      <tr>
          <td>
            <s:if test='handType == "22"'>
                <input type="hidden" name="ClaimNo"  value='<s:property value='ClaimNo'/>'>
                <input type="hidden" name="RegistNo" value='<s:property value="RegistNo"/>'>
                <input type="hidden" name="PolicyNo" value='<s:property value="#prpCmainDto.policyNo"/>'>
                <Input type="button" class="button" name="claimInfo" value="<s:text name='undwrt.pages.undwrtDeal.payMessages'/>" onclick="viewClaimInfo();">
            </s:if>
            <%--add by zhangruifeng begin 20071217 当是车险联合出单时在审核商业险投保单信息时增加查看交强险信息按钮--%>
            <s:if test='handType != "22"'>
                
<!--                   blPrpDconfigCode.getFunNameOrFunType(strComCode,riskCode,"Notify",nowDate.getCurrentTime("yyyy-MM-dd")); -->
<!--                   if(blPrpDconfigCode.getSize()>0) -->
<!--                   { -->
<!--                     strNotifyPath = blPrpDconfigCode.getArr(0).getFunName(); -->
                <s:if test='blPrpDconfigCode.getSize()>0'>
                    <input type="hidden" name="NotifyPath" description="<s:text name='undwrt.pages.undwrtDeal.informPath'/>" value="<s:property value="strNotifyPath"/>">                    
                    <Input name="NotifyBtn" class="button" type="button" value="<s:text name='prompt.messages.informInfor'/>" onclick="showNotifyInfo()">
                </s:if>
                <%--added by zhouhui begin 20090722 免导团单调用自己的保单详细信息页面--%>
                <input type="hidden" name="PolicySort" description="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyType'/>" value="<s:property value="strPolicySort"/>">
                <%--added by zhouhui end 20090722 免导团单调用自己的保单详细信息页面--%>
                <Input name="butDetail" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" onclick="showBusinessInfo('<s:property value="comcode"/>')">
               <s:if test ='!("" == prpTmainSubDto.riskCode|| prpTmainSubDto.riskCode==null)'>
                <Input type="button" class="longbutton"  value="<s:text name='undwrt.pages.undwrtDeal.relevanceInsureBillMessages'/>" onclick="showBusinessCIInfo('<s:property value="comcode"/>')">
	           </s:if>
           </s:if>
                
                
            <s:if test='historyProposal=="true"'>
                <input type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.historyUnderwriteMessages'/>" name="BusinessTotalInfo" onclick="showBusinessTotalInfo('<s:property value="iBusinessNo"/>');">                
            </s:if>
            <s:if test='historyLoss=="true"'>
                <input type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.historyPayMessages'/>" name="HistoryLossInfo" onclick="showHistoryLossInfo('<s:property value="iBusinessNo"/>');">     
            </s:if>
            <Input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.influenceMessages'/>" onclick="showMaterialInfo('<s:property value="iBusinessNo"/>');">
            
            <s:if test='handType == "22"'>
                <Input type="button" name="PolicyNoInfo" class="longbutton" value="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyMessages'/>" onclick="showPolicyInfo();">
            </s:if>
            <s:if test="handType == 11 && iNodeStatus != 4 && iNodeStatus != 0">
                <Input name="buttonMessage1" class="button" type="button" value="<s:text name='undwrt.pages.undwrtDeal.remarkRecord'/>" onclick="openWinQuery();">            
            </s:if>
            <s:elseif test='handType == "22"'>
                <Input name="buttonMessage1" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.payConditionRecord'/>" onclick="openWinQuery();">   
            </s:elseif>
            <!--<Input type="button" class="longbutton" name="rateRuleHelp" value="<s:text name='prompt.messages.rateRuleHelp'/>" onclick="viewRateRuleHelp();">-->
             <input type="hidden" name="language" description="<s:text name='undwrt.languageKind'/>" value="<s:property value="##request.PrpTmainDto.language"/>">             
             <!-- added by yanglibo 20090812 begin reason：历年承保理赔信息 -->
<!--                   blPrpDconfigCode.getFunNameOrFunType(strComCode,riskCode,"BISumPaid",nowDate.getCurrentTime("yyyy-MM-dd")); -->
                <s:if test="blPrpDconfigCode.getSize()>0">
                    <Input name="butTPolicyClaimInfo" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.messagesQuery'/>" onclick="viewPolicyClaimInfo()">
                </s:if>            
             <!-- added by yanglibo 20090812 end reason：历年承保理赔信息 -->
        </td>
      </tr>
      	<tr>
				<td>
            <%--added by luojing start 2007-10-17 编辑保单抄件预览按钮--%>
            <%--added by zhouhui start 2009-05-22 编辑保单抄件预览按钮，增加险种0701、0702--%>
            <s:if test='strClassCode=="01"||riskCode=="0701"||riskCode=="0702"'>
<!-- //             //added by zhouhui end 2009-05-22 编辑保单抄件预览按钮，增加险种0701、0702 -->
<!-- //             	String conditions = "BUSINESSNO='" + businessNo + "'"; -->
<!-- //             	UtiPrintPageFindByConditionsCommand commands = new UtiPrintPageFindByConditionsCommand(conditions); -->
<!-- //             	ArrayList printPageList = (ArrayList)commands.executeCommand(); -->
            	<s:if test="printPageList.size()>0">
            
	    			<Input name="buttonPrint" style="color:red" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyOriginal'/>" alt="<s:text name='undwrt.pages.undwrtDeal.insurancePolicyOriginal'/>"  onclick="Print('11');">
	    			<Input name="buttonEditPrint" style="color:red" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.redactInsurancePolicyOriginal'/>" alt="<s:text name='undwrt.pages.undwrtDeal.redactInsurancePolicyOriginal'/>"  onclick="Print('61');">
	    			<Input name="buttonEditPrint" style="color:red" class="longbutton" type="button" value="<s:text name='undwrt.pages.undwrtDeal.redactRemark'/>" alt="<s:text name='undwrt.pages.undwrtDeal.redactRemark'/>"  onclick="Print('62');">
                </s:if>
            </s:if>
            <%--added by luojing end 2007-10-17 编辑保单抄件预览按钮--%>
	</td>
      </tr>
    <%--added by LanNing end 20070416 修改按钮位置--%>

    <!-- 原始标的信息 -->
      <tr>
      <td width="100%">
       <table cellpadding="5" cellspacing="1" class="common" align="center" style="width:100%">
<s:if test='strClassCode=="26" || strClassCode=="27"'>
           <tr class=listtitle>
            <td colspan="7" ><s:text name="undwrt.CommonDangerUnits.originalTargetInfo"/></td>
           </tr>
           <tr class=common>
            <td><s:text name="undwrt.CommonDangerUnits.clauseName"/></td>
            <td><s:text name="undwrt.CommonDangerUnits.riskDuty"/></td>
<s:if test='riskCode == "2727" && "2755,2756,2757,2758".indexOf(riskCode)==-1'>
            <td><s:text name="undwrt.CommonDangerUnits.rebate"/></td>
</s:if>
<s:if test='riskCode == "2703" || riskCode == "2708"'>
            <td><s:text name="undwrt.CommonDangerUnits.copies"/></td>
</s:if>
<s:if test='riskCode != "2727" && "2755,2756,2757,2758".indexOf(riskCode)==-1'>
            <td><s:text name="undwrt.CommonDangerUnits.peopleCount"/></td>
</s:if>
            <td><s:text name="undwrt.CommonDangerUnits.insureAmout"/></td>
            <td><s:text name="undwrt.CommonDangerUnits.insureFee"/></td>
           </tr>
          <s:if test="itemKind != null">
          <s:iterator status="index" id="itemKind" value="itemKind">
           <tr class=common>
            <td><input class="formtitle1"  readonly  value="<s:property value="#itemKind.kindName"/>" ></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#itemKind.itemDetailName"/>"></td>   
<s:if test='"2727" !=riskCode && "2755,2756,2757,2758".indexOf(riskCode)==-1'>         
            <td><input class="formtitle1"  readonly  value="<s:property value="#itemKind.discount"/>"></td>
</s:if>
<s:if test='riskCode =="2703" || riskCode =="2708"'>
            <td><input class="formtitle1"  readonly  value="<s:property value="#itemKind.value"/>"></td>
</s:if>
<s:if test='"2727" != riskCode && "2755,2756,2757,2758".indexOf(riskCode)==-1'>
            <td><input class="formtitle1"  readonly  value="<s:property value="#itemKind.quantity"/>"></td>
</s:if>
            <%-- modify by yanglibo 20080826 begin 改为千分位 --%>
            <td><input class="formtitle1"  readonly  value="<s:property value="#itemKind.amount"/>"></td>
            <td><input class="formtitle1"  readonly  value="<s:property value="#itemKind.premium"/>"></td>
          <%-- modify by yanglibo 20080826 end 改为千分位 --%>
           </tr>
          </s:iterator>
          </s:if>
</s:if>
<s:else>

           <tr class=listtitle>
            <td colspan="10" ><s:text name="undwrt.CommonDangerUnits.originalTargetInfo1"/></td>
           </tr>
           <tr class=common>
            <td><s:text name="undwrt.CommonDangerUnits.serialNo"/></td>
            <td><s:text name="undwrt.CommonDangerUnits.kind"/></td>
<s:if test='!(riskCode == "0101" ||riskCode == "0102" ||riskCode == "0104" || riskCode == "0110" ||riskCode == "0111" ||riskCode =="0112")'>
             <td><s:text name="undwrt.CommonDangerUnits.targetProject"/></td>
</s:if>
<s:if test='strClassCode =="09" || strClassCode == "10"'>
            <td><s:text name="undwrt.CommonDangerUnits.insuredObjectName"/></td>
</s:if>
<s:else>
            <td><s:text name="undwrt.CommonDangerUnits.targetName"/></td>
</s:else>
            <td><s:text name="undwrt.CommonDangerUnits.postcode"/></td>
            <td><s:text name="undwrt.CommonDangerUnits.targetAdress"/></td>
            <td><s:text name="undwrt.CommonDangerUnits.currency"/></td>
             <%--modify by yanglibo begin 20080918 15险类特殊处理--%>
            
            <s:if test='strClassCode == "15"'>
            <td><s:text name="undwrt.CommonDangerUnits.totalDutyLimitAmout"/></td>
            <td><s:text name="undwrt.CommonDangerUnits.perEventDutyLimitAmout"/></td>
            </s:if>
            <s:else>
            <td><s:text name="undwrt.CommonDangerUnits.insureAmout1"/></td>
            </s:else>
             <%--modify by yanglibo end--%>
            <td><s:text name="undwrt.CommonDangerUnits.insureFee1"/></td>
          </tr>
          <s:if test="#request.ItemKind != null">
          <s:iterator value="#request.ItemKind" status="index" id="itemKind">
	          <tr class=common>
	            <td><input class="formtitle1" name="itemKindNo" readonly value="<s:property value="#itemKind.itemKindNo"/>" ></td>
	            <td>
	              <input class="formtitle1"  readonly  value="<s:property value="#itemKind.kindName"/>">
	              <input type=hidden value="<s:property value="#itemKind.kindCode"/>" >
	            </td>

	            <s:if test='!(riskCode=="0101" ||riskCode=="0102" ||riskCode=="0104" || riskCode=="0110" ||riskCode=="0111" ||riskCode=="0112")'>
	            	<td><input class="formtitle1" name=""  readonly value="<s:property value="#itemKind.itemCode"/>" >  </td>        
	            </s:if>
	            <s:else>
	            	<input type="hidden"  class="formtitle1" name=""  readonly value="<s:property value="#itemKind.itemCode"/>" >           
	            </s:else>
	            <td><input class="formtitle1" name=""  readonly value="<s:property value="#itemKind.itemDetailName"/>" ></td>
	            <td><input class="formtitle1" name=""  readonly value="<s:property value="#itemKind.addressCode"/>" ></td>
	            <td><input class="formtitle1" name=""  readonly value="<s:property value="#itemKind.addressName"/>" ></td>
	            <td><input class="formtitle1" name="iCurrency" readonly value="<s:property value="#itemKind.currency"/>" ></td>
	          <%--modify by yanglibo begin 20080918 15险类特殊处理--%>

	           <s:if test='strClassCode.startsWith("15")'>
	             <td><input class="formtitle1" name="iAmount" readonly  value="<s:property value="#itemKind.amount"/></td>
	     
	             <td><input class="formtitle1" name="limitFee" readonly  value="<s:property value="#itemKind.limitFee"/>" ></td>
	
	          </s:if>
	          <s:else>         

	            <td><input class="formtitle1" name="iAmount" readonly  value="<fmt:formatNumber value="${itemKind.amount}" pattern="#,##0.00"/>" /></td>
	           </s:else> 
	               
	            <td>
	                <input class="formtitle1" name="iPremium" readonly value="<fmt:formatNumber value="${itemKind.premium}" pattern="#,##0.00"/>"/>
	                <input type="hidden" name="calculateFlag"  value="<s:property value="#itemKind.calculateFlag"/>" >
	             </td>
	          </tr>
        </s:iterator>
        </s:if>
</s:else>

        </table>
        </td>
      </tr>

    <!-- 划分风险评估信息Start-->
   <s:if test='handType =="11"'>
     <tr>
      <td>
      <img name="butDanger" class="button" type="button" alt="<s:text name='undwrt.pages.undwrtDeal.riskAssessMessages'/>" 
      	src="/undwrt/common/images/butCollapse.gif" onclick="showPage(this,dangerInfo)"/>
      	 <s:text name="undwrt.CommonDangerUnits.riskAssessInfo"/><br>

    <span id="dangerInfo">
    <table width="100%">
      <tr>
      <td>

         <span style="display:none">
         <table class="common" style="display:none" id="DangerUnit_Data" cellspacing="1" cellpadding="0">
          <tbody>
         <td>
        <table class="common" style="width:100%" cellspacing="1" cellpadding="0">
          <tr class=common>
          <td width='4%'><s:text name="undwrt.CommonDangerUnits.serialNo1"/></td>
          <td width='25%' colspan='2'><s:text name="undwrt.CommonDangerUnits.describe"/></td>
          <td width='15%'><s:text name="undwrt.CommonDangerUnits.address"/></td>
          <td width='7%'><s:text name="undwrt.CommonDangerUnits.currency1"/></td>
          <td width='15%'><s:text name="undwrt.CommonDangerUnits.insureAmout2"/></td>
          <td width='13%'><s:text name="undwrt.CommonDangerUnits.insureFee2"/></td>
          <td width='10%'><s:text name="undwrt.CommonDangerUnits.occupyScale"/></td>
          <td width='10%'><s:text name="undwrt.CommonDangerUnits.sonInfo"/></td>
          <td width='1%'>*</td>
          </tr>
          <tr class=common>
          <td rowspan ="3"><input class="free" readonly name="dangerNo"  description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" ></td>
          <td  colspan='2'>
          <input type="hidden" name="dangerCoinsFlag" value="">
          <input type="hidden" name="dangerShareHolderFlag" value="">
          <input type="hidden" name="dangerBusinessFlag" value="">
          <input type="hidden" name="dangerBusinessNature" value="">
          <input type="hidden" name="dangerChannelType" value="">
          <input type="hidden" name="dangerCartypeCode" value="">
          <input type="hidden" name="dangerExchRateCNY" value="">
          <input class="free" readonly name="dangerDesc" description="<s:text name='undwrt.pages.undwrtDeal.describe'/>" ></td>
          <td><input class="free" readonly name="dangerAddress" description="<s:text name='undwrt.pages.undwrtDeal.address'/>" ></td>
          <td><input class="free" readonly name="currency" description="<s:text name='undwrt.pages.undwrtDeal.Currency'/>" value="1x"></td>
         	<td><input class="free" readonly name="amount" description="<s:text name='undwrt.pages.undwrtDeal.coverage'/>"></td>
         	<td><input class="free" readonly name="premium" description="<s:text name='undwrt.pages.undwrtDeal.premium'/>"></td>
          <td align="center"><input class="free" name="dangerShare" readonly description="占比<s:text name=''/>"></td>
          
          <td rowspan ="3">
           <div>
            <!--modify by yanglibo 20090512 begin reason：非车险权限岗位调整，增加核保初审岗-->
            <input type=button class=button name="buttonShowItem" onclick="return showDangerItem(this,'DangerUnit','0');" 
                value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" >
            <!--modify by yanglibo 20090512 end reason：非车险权限岗位调整，增加核保初审岗-->
           </div>
            <input type=hidden name="hiDangerNo">
            <input type=hidden name="isSavaDangerUnit">  <%--单条危险单位保存是否保存标志位--%>
            </td>
           <td  rowspan ="3" style='width:1%'  align="center">
          <div>
           <input type=button name="buttonDelete" class=smallbutton onclick="deleteTdangerInfo(this,'DangerUnit');"
           value="-" style="cursor: hand"  
           <s:if test='editType=="query"'>disabled</s:if>
           <s:else></s:else>>
          </div>
           </td>
       </tr>

      	<tr class=common>
      	<td width = '15%'><s:text name="undwrt.CommonDangerUnits.riskName2"/></td>
        <td width = '10%'><s:text name="undwrt.CommonDangerUnits.dangerLevel"/></td>
        <td width = '15%'><s:text name="undwrt.CommonDangerUnits.dangerName"/></td>
        <s:if test='strClassCode=="27" && includeAccident=="Y"'>
          <td width = '5%'><s:text name="undwrt.CommonDangerUnits.healthInsurePMLValue"/></td>
          <td width = '15%'><s:text name="undwrt.CommonDangerUnits.autoRemainAmout"/></td>
        <td width = '15%'><s:text name="undwrt.CommonDangerUnits.exDutyApplyBusiness"/></td>
        </s:if>
        <s:else>
        <td width = '5%'><s:text name="undwrt.CommonDangerUnits.currency2"/></td>
        <td width = '15%'><s:text name="undwrt.CommonDangerUnits.autoRemainAmout1"/></td>
        <td width = '15%'><s:text name="undwrt.CommonDangerUnits.exDutyApplyBusiness1"/></td>
        </s:else>
        <td width ='10%' ><s:text name="undwrt.CommonDangerUnits.enterConract"/></td>
        </tr>
      	<tr class=common>
      		<td width = '15%'>
      	 <input type="hidden" name="eRiskCode"  description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" >
         <input class="free" readonly name="riskName" description="<s:text name='undwrt.PolicyDangerUnits.riskName'/>"></td>
        
         <td width = '10%'>
         <input class="free" readonly name="riskLevel" description="<s:text name='undwrt.ShowDangerItem.riskLevel'/>"></td>
         <td width = '15%'>
         <input class="free" readonly name="riskLevelDesc" description="<s:text name='undwrt.pages.undwrtDeal.riskGradeDescribe'/>" ></td>
         <s:if test='strClassCode=="27" && includeAccident=="Y"'>
         <td width = '5%'>
          <input type="test"  style="width:50%" name="speCurrency" description="<s:text name='undwrt.PMLCurrencyKind'/>" value="">
           <input class="free"  style="width:50%" name="speValue" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>" value=""></td>
         <td width = '15%'>
          <input class="free" readonly name="retCurrency" style="width:20%" description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>">
          <input class="free"  readonly name="retentionValue" style="width:70%" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>"></td>
         <td width = '15%'>
         <input type="hidden" class="free" name="dangerItemKind" description="<s:text name='undwrt.pages.undwrtDeal.exceptResponsibility'/>">
         <input name="dangerItemKindName" class="free" readonly>
         </td>
         </s:if>
         <s:else>
         <td width = '5%'>
         <input class="free" readonly name="retCurrency" description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>"></td>
         <td width = '15%'>
         	<input class="free"  readonly name="retentionValue"  description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>"></td>
         <td width = '15%'>
         	<input type="hidden" class="free" name="dangerItemKind" description="<s:text name='undwrt.pages.undwrtDeal.exceptResponsibility'/>">
         	<input name="dangerItemKindName" class="free" readonly></span></td>
         </s:else>
          <td width = '10%'>
           <input type="checkbox" align="center" name="dangerFlag" description="<s:text name='undwrt.pages.undwrtDeal.intoContract'/>" >
          </td>
        </tr>
     </table>
     </td>
     </tbody>
      </table>
     </span>

     <span  id="spanDangerUnit" style="display:" cellspacing="1" cellpadding="0">
    <table class="common" cellpadding="5" cellspacing="1" align="center" id="DangerUnit"  >
    <thead>
      <tr class=listtitle>
      <td><s:text name="undwrt.CommonDangerUnits.divideRiskAssessInfo"/></td>
      </tr>
     </thead>
<%-- <% --%>
<!-- // 	double totA	=	0.00d; -->
<!-- // 	double totP	=	0.00d; -->
<%-- // 	Collection dangerDetail	=	(Collection)request.getAttribute("DangerDetail"); --%>
<!-- // 	if(dangerDetail!=null&&dangerDetail.size()>0){ -->
<!-- // 	Iterator itr = dangerDetail.iterator(); -->
<!-- // 		while(itr.hasNext()){ -->
<!-- // 			if(businessType.equals("T")||businessType.equals("X")){//MODIFY BY RENRUIDE 20120614 -->
<!-- // 				PrpTdangerUnitDto prpTdangerUnitDto = (PrpTdangerUnitDto)itr.next(); -->
<!-- // 				totA+=prpTdangerUnitDto.getAmount(); -->
<!-- // 				totP+=prpTdangerUnitDto.getPremium(); -->
<!-- // 			}else if(businessType.equals("E")){ -->
<!-- // 				PrpPdangerUnitDto prpPdangerUnitDto = (PrpPdangerUnitDto)itr.next(); -->
<!-- // 				totA+=prpPdangerUnitDto.getAmount(); -->
<!-- // 				totP+=prpPdangerUnitDto.getPremium(); -->
<!-- // 			} -->
<!-- // 		} -->
<!-- // 	} -->
<%--  %> --%>
<input type="hidden" name="totA" value="<s:property value="totA"/>" description="<s:text name='undwrt.totalPolicyAmount'/>">
<input type="hidden" name="totP" value="<s:property value="totP"/>" description="<s:text name='undwrt.totalPolicyFee'/>">
    <tbody>
       <s:if test="dangerDetail != null">
	       <s:iterator value="dangerDetail" status="index" id="dangerDetail">
			     <tr>
			        <td>
			             <table class="common" style="width:100%" cellspacing="1" cellpadding="0">
			
			          <tr class=common>
			          <td width="4%"><s:text name="undwrt.CommonDangerUnits.serialNo2"/></td>
				       <s:if test='strClassCode == "27" && includeAccident == "Y"'>
				          <td width="25%" colspan="2"><s:text name="undwrt.CommonDangerUnits.describe1"/></td>
				       </s:if>
				       <s:else>
				          	<td width="25%" colspan="2"><s:text name="undwrt.CommonDangerUnits.describe2"/></td>
				       </s:else>
			          <td width="15%"><s:text name="undwrt.CommonDangerUnits.address1"/></td>
			          <td width="7%"><s:text name="undwrt.CommonDangerUnits.currency3"/></td>
			          <td width="15%"><s:text name="undwrt.CommonDangerUnits.insureAmout3"/></td>
			          <td width="13%"><s:text name="undwrt.CommonDangerUnits.insureFee3"/></td>
			          <td width="10%"><s:text name="undwrt.CommonDangerUnits.occupyScale1"/></td>
			          <td width="10%"><s:text name="undwrt.CommonDangerUnits.sonInfo1"/></td>
			          <td width="1%">*</td>
			          </tr>
			          <tr class=common>
			          <td rowspan ="3"><input class="free" readonly name="dangerNo"  description="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" 
			          	value="<s:property value="#dangerDetail.dangerNo"/>"></td>
			          <td colspan="2">
			          <input type="hidden" name="dangerCoinsFlag" value="<s:property value="#dangerDetail.coinsFlag"/>">
			          <input type="hidden" name="dangerShareHolderFlag" 
			          value="<s:property value="#dangerDetail.shareHolderFlag"/>">
			          <input type="hidden" name="dangerBusinessFlag" value="<s:property value="#dangerDetail.businessFlag"/>">
			          <input class="free" readonly name="dangerDesc" description="<s:text name='undwrt.pages.undwrtDeal.describe'/>" value="<s:property value="#dangerDetail.dangerDesc"/>">
			          <input type="hidden" name="dangerBusinessFlag" value="">
			          
			          <input type="hidden" name="dangerBusinessNature" value="<s:property value="#dangerDetail.businessNature"/>">
			          <input type="hidden" name="dangerChannelType" value="<s:property value="#dangerDetail.channelType"/>">
			          <input type="hidden" name="dangerCartypeCode" value="<s:property value="#dangerDetail.cartypeCode"/>">
			          <input type="hidden" name="dangerExchRateCNY" value="<s:property value="#dangerDetail.exchRateCNY"/>">
			          </td>
			          <td><input class="free" readonly name="dangerAddress" description="<s:text name='undwrt.pages.undwrtDeal.address'/>" value="<s:property value="#dangerDetail.addressName"/>"></td>
			          <td><input class="free" readonly name="currency" description="<s:text name='undwrt.PolicyDangerUnits.currency'/>" value="<s:property value="#dangerDetail.currency"/>2x"></td>
			          	<%-- modify by yanglibo 20080826 begin 改为千分位 20080828  去掉千分位--%>
			         	<td><input class="free" readonly name="amount" description="<s:text name='undwrt.PolicyDangerUnits.protectAmount'/>" value="<s:property value="#dangerDetail.amount"/>" onblur="checkNumber(this)" ></td>
			         	<td><input class="free" readonly name="premium" description="<s:text name='undwrt.PolicyDangerUnits.protectFee'/>" value="<s:property value="#dangerDetail.premium"/>" onblur="checkNumber(this)"></td>
			          <%-- modify by yanglibo 20080826 begin 改为千分位 --%>
			          <td align="center"><input class="free" name="dangerShare" readonly description="<s:text name='undwrt.pages.undwrtDeal.dutyCycle'/>" value="<s:property value="#dangerDetail.dangerShare"/>" onblur="checkNumber(this)">
			          </td>
			          <td rowspan ="3">
			           <div>
			           <s:if test='"4" != "iNodeStatus" && "0" !="iNodeStatus"'>
			            <!--modify by yanglibo 20090514 begin reason :非车险核保岗位调整-->
			            <input type=button class=button name="buttonShowItem" onclick="return showDangerItem(this,'DangerUnit','0');" value="<s:text name='undwrt.pages.undwrtDeal.detailedInformation'/>" >
			           <!--modify by yanglibo 20090514 end reason :非车险核保岗位调整-->
			           </s:if>
			           </div>
			            <input type=hidden name="hiDangerNo" value="<s:property value="#dangerDetail.dangerNo"/>">
			            <input type=hidden name="isSavaDangerUnit" value="Y"/>
			            </td>
			           <td  rowspan ="3" style='width:1%'  align="center">
			          <div>
			           <input type=button name="buttonDelete" class=smallbutton onclick="deleteTdangerInfo(this,'DangerUnit');"
			           value="-" style="cursor: hand"  
			           <s:if test='editType=="query"'>disabled</s:if>
                       <s:else></s:else>>
			          </div>
			           </td>
			       </tr>
			
			      <tr class=common>
			      	<td width="15%"><s:text name="undwrt.CommonDangerUnits.riskName3"/></td>
			        <td width="10%" ><s:text name="undwrt.CommonDangerUnits.dangerLevel1"/></td>
			        <td width="15%"><s:text name="undwrt.CommonDangerUnits.dangerName1"/></td>
			        <s:if test='strClassCode=="27" && includeAccident=="Y"'>
			        
			        <td  width="5%"><s:text name="undwrt.CommonDangerUnits.healthInsurePMLValue1"/></td>
			        <td  width="15%"><s:text name="undwrt.CommonDangerUnits.autoRemainAmout2"/></td>
			        <td  width="15%"><s:text name="undwrt.CommonDangerUnits.exDutyApplyBusiness2"/></td>
			        </s:if>
			        <s:else>
			        <td width="5%"><s:text name="undwrt.CommonDangerUnits.currency4"/></td>
			        <td width="15%"><s:text name="undwrt.CommonDangerUnits.autoRemainAmout3"/></td>
			        <td width="15%"><s:text name="undwrt.CommonDangerUnits.exDutyApplyBusiness3"/></td>
			        </s:else>
			        <td  width="10%"><s:text name="undwrt.CommonDangerUnits.enterConract1"/></td>
			       </tr>
			      <tr class=common>
			      	<td>
			         <input type="hidden" name="eRiskCode"  description="<s:text name='undwrt.pages.undwrtDeal.riskCode'/>" 
			         	value="<s:property value="#dangerDetail.riskCode"/>">
			         	<input class="free" readonly name="riskName"  description="<s:text name='undwrt.PolicyDangerUnits.riskName'/>" 
			         	value="<s:property value="#dangerDetail.riskName"/>"></td>
			         <td >
			         	<input class="free" readonly name="riskLevel"  description="<s:text name='undwrt.ShowDangerItem.riskLevel'/>" 
			         	value="<s:property value="#dangerDetail.riskLevel"/>"></td>
			          <td >
			         	<input class="free" readonly name="riskLevelDesc"  description="<s:text name='undwrt.pages.undwrtDeal.riskGradeDescribe'/>" 
			         	value="<s:property value="#dangerDetail.riskLevelDesc"/>"></td>
			         <s:if test='strClassCode=="27" && includeAccident=="Y"'>
			         
			         <td >
			            <input class="free" readonly name="speCurrency" style="width50%"
			             description="<s:text name='undwrt.CommonDangerUnits.PMLCurrencyKind'/>" value="<s:property value="#dangerDetail.speCurrency"/>" >
			           <input class="free"   readonly name="speValue" style="width:50%" description="<s:text name='undwrt.CommonDangerUnits.PMLValue'/>"
			            value="<s:property value="#dangerDetail.speValue"/>">
			         </td>
			         <td>
			         	<input class="free" readonly name="retCurrency" style="width:20%" description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>" 
			         	value="<s:property value="#dangerDetail.retCurrency"/>" >
			         <input class="free" readonly name="retentionValue" style="width:70%" description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" 
			          value="<s:property value="#dangerDetail.retentionValue"/>"></td>
			         <td>
			         	<input class="free" type="hidden" name="dangerItemKind"  description="<s:text name='undwrt.ShowDangerItem.exDuty'/>" 
			         	value="<s:property value="#dangerDetail.itemKind"/>">
				        <input type="text" name="dangerItemKindName"  class="free" 
				        value="<s:property value="#dangerDetail.itemKindDesc"/>"></td>  
			         </s:if>
			         <s:else>         
			           <td>
			         	  <input class="free" readonly name="retCurrency"  description="<s:text name='undwrt.CommonDangerUnits.autoAmountCurrency'/>" 
			         	  value="<s:property value="#dangerDetail.retCurrency"/>" ></rs>
			           <td>
			            <input class="free" readonly name="retentionValue"  
			            description="<s:text name='undwrt.pages.undwrtDeal.retentionMoney'/>" value="<s:property value="#dangerDetail.retentionValue"/>"></td>
			           <td>
			         	  <input class="free" type="hidden" name="dangerItemKind"  description="<s:text name='undwrt.ShowDangerItem.exDuty'/>" 
			         	  value="<s:property value="#dangerDetail.itemKind"/>">
				          <input type="text" name="dangerItemKindName"  class="free" 
				          value="<s:property value="#dangerDetail.itemKindDesc"/>"></td>
			           </td>
			         </s:else>
			         <td  >
			           		<input type="checkbox" align="center" name="dangerFlag" description="<s:text name='undwrt.ShowDangerItem.intoContract'/>"
			             	<s:if test="#dangerDetail.flag ==10">checked</s:if>
			             	<s:if test="#dangerDetail.flag ==11">checked</s:if>
			              value="<s:property value="#dangerDetail.flag"/>"
			              onclick="return false;">
			              
			          </td>
			       </tr>
			     </table>
			     </td>
			     </tr>
	       </s:iterator>
       </s:if>
     </tbody>
     <tfoot>


      <s:if test='riskUnitFlag !="" && riskUnitFlag == "1"'>
    <tr>
       <td>
        <table  width="100%">
         <tr>
       <td>
       <s:text name="undwrt.CommonDangerUnits.pressAddMarkEnhanceDangerUnitInfoOrElseSo"/>,<font color=red>
       <s:text name="undwrt.CommonDangerUnits.pressConfirmSplitDangerUnitFinish"/></font>
             </td>
             <td>
              
             </td>
             <td>
               <div align="center">
          <input type="button" value="+" class=smallbutton onclick="insertRow('DangerUnit'); return showDangerItem

(this,'DangerUnit','NewDangerNo');"
          name="buttonInsert" style="cursor: hand" 
           <s:if test='editType=="query"'>disabled</s:if>
           <s:else></s:else>>
          
         </div>
        </td>
        </tr>
        </table>
      </td>
       </td>

    </tr>
    </s:if>

    </tfoot>
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
       <table width=100% border="0">
       <tr>
          <s:if test='editType =="query"'>
            <td class=button width="33%">
                <input type="button" class="longbutton" name="allEvaluate" description="<s:text name='undwrt.pages.undwrtDeal.assessRiskMessages'/>"
                 value="<s:text name='undwrt.pages.undwrtDeal.assessRiskMessages'/>" onclick="viewDangerRiskInfo2(this)">
            </td>
          </s:if>
            <td class=button width="33%">
          <s:if test='editType !="query"'>
            <input name="ReinsTrial" type="button" class=button value="<s:text name='undwrt.pages.undwrtDeal.reinsuranceTrial'/>" onclick="simulateReinsByDanger()"  >
            
            <!-- 非车询报价添加 委托查勘按钮 -->
            <s:if test='businessNo.length()>2 && "X" == businessNo.substring(0,1) && "4" != iNodeStatus && "0" != iNodeStatus'>
            	<input name="ReinsTrial" type="button" class=button value="<s:text name='undwrt.trustSurvey'/>" onclick="checkSurveyInfo()"  >
            </s:if>
            <s:if test='businessFlag == "1"'>
             	<input name="ReinsTrial" type="button" class=longbutton value="<s:text name='undwrt.pages.undwrtDeal.submitPointsAffirm'/>" onclick="reinsVerify()"  >
            </s:if>
            <s:if test="amountAndPremiumVo != null">
              <input type="hidden" name="tolAmount" value="<s:property value="#amountAndPremiumDto.amount"/>">
              <input type="hidden" name="tolPremium" value="<s:property value="#amountAndPremiumDto.premium" />">
              <s:if test="dangerDetail != null">
              	<input type="hidden" name="tolRetentionValue" value="<s:property value="#dangerDetail.retentionValue" />">
              </s:if>
            </s:if>
           </s:if>
           <s:else>
             <Input name="ReinsTrial" type="button" class=button value="<s:text name='undwrt.pages.undwrtDeal.reinsuranceMessages'/>" onclick="showSimulateReins()"   >
           </s:else>
           </td>
           <!--add by xuning 临时添加的。以后肯定不能这么搞。-->


         <td class=button width="33%">
         <s:if test='"4" != nodeStatus && "0" != nodeStatus'>
            <input name="butSubmitReins" class="button" type="button" alt="<s:text name='prompt.messages.intention2'/>" value="<s:text name='prompt.messages.intention2'/>" onclick="submitReins()"   
           <s:if test='eeditType=="query" || Nownodeno<FACLEVEL || verifyFlag != "0"'>disabled</s:if>
           <s:else></s:else>>

		</s:if>
		<s:if test='"4" == nodeStatus || "0" = nodeStatus'>
		   <input name="butSubmitReins" class="button" type="button" alt="<s:text name='prompt.messages.intention2'/>" value="<s:text name='prompt.messages.intention2'/>" onclick="showReins()" > <!--add by qiuxia.lai for fubon-4920  -->
           <input type="button" class="longbutton" value="<s:text name='undwrt.pages.undwrtDeal.backContinueDispose'/>" onclick="history.back();">            	
        </s:if>
         </tr>
        </table>
     </td>
      </tr>

 </s:if>
<!--   核保时的相关危险单位信息结束 -->


 </table>

	<!--   开始处理核赔时的危险单位 -->
	<!--   该险种要求拆分危险单位和分摊试算add by qinyongli 2005-8-23 -->
	<s:if test='handType =="22"'>
		<s:if test='riskUnitFlag != "" && riskUnitFlag == "1"'>
		 <table class="common" style="width:99%" cellpadding="5" cellspacing="1" align="center" id="HepeiDangerUnit"  >
		      <tr class=listtitle><td colspan="15" ><s:text name="undwrt.CommonDangerUnits.dangerUnitShareInfo"/></td></tr>
		      <tr class=common>
		      <td><s:text name="undwrt.CommonDangerUnits.serialNo3"/></td>
		      <td><s:text name="undwrt.CommonDangerUnits.describe3"/></td>
		      <td><s:text name="undwrt.CommonDangerUnits.address2"/></td>
		      <td><s:text name="undwrt.CommonDangerUnits.currency4"/></td>
		      <td><s:text name="undwrt.CommonDangerUnits.assessLossAmout"/></td>
		      <td><s:text name="undwrt.CommonDangerUnits.alreadyDecidereparation"/></td>
		      <td><s:text name="undwrt.CommonDangerUnits.occupyScale1"/>%</td>
		      </tr>
		
		
		       <s:if test="dangerDetail != null">
		          <s:iterator value="dangerDetail" status="statu" id="dangerDetail">
				     <tr class=common>
				          <td ><input class="formtitle1" readonly name="dangerNo"  title="<s:text name='undwrt.pages.undwrtDeal.serialNo'/>" value="<s:property value="#dangerDetail.dangerno"/>"></td>
					      <td ><input class="formtitle1" readonly name="dangerDesc" title="<s:text name='undwrt.pages.undwrtDeal.describe'/>" value="<s:property value="#dangerDetail.dangerdesc"/>"></td>
					      <td ><input class="formtitle1" readonly name="dangerAddress" title="<s:text name='undwrt.pages.undwrtDeal.address'/>" value="<s:property value="#dangerDetail.addressname"/>"></td>
					      <td ><input class="formtitle1" readonly name="currency" title="<s:text name='undwrt.pages.undwrtDeal.Currency'/>" value="<s:property value="#dangerDetail.dangerno"/>3x"></td>
					      <%--//modify by yanglibo 20080826 begin 增加千分位控制--20080828 去掉千分位%>
					      <td ><input class="formtitle1" readonly name="sumLoss" title="估损金额<s:text name=''/>" value="<s:property value="#dangerDetail.sumLoss" format="0.00"/>" onblur="checkNumber(this)"></td>
					      <td ><input class="formtitle1" readonly name="sumPaid" title="已决赔款<s:text name=''/>" value="<s:property value="#dangerDetail.sumPaid" format="0.00"/>" onblur="checkNumber(this)"></td>
					      <%--//modify by yanglibo 20080826 end 增加千分位控制--%>
				          <td ><input class="formtitle1" readonly name="dangerShare"  title="<s:text name='undwrt.pages.undwrtDeal.dutyCycle'/>" value="<s:property value="#dangerDetail.dangershare"/>" onblur="checkNumber(this)"></td>
				     </tr>
				 </s:iterator>  
		       </s:if>
		       <tr>
		        <td><input class="button" type="button" value="<s:text name='prompt.messages.trial2'/>" onclick="simulateReinsHepei()" ></td>
		       </tr>
		  </table>
		
		</s:if>

       <!-- 不要求拆分危险单位,但是要求分摊试算 -->
        <s:if test='requiredReins != "" && requiredReins =="1" && riskUnitFlag =="0"'>
           <table>
            <tr align="left">
              <td><input class="button" type="button" value="<s:text name='prompt.messages.trial2'/>" onclick="simulateReinsHepei()" ></td>
            </tr>
          </table>
       </s:if>
	</s:if>
	<!-- 核赔危险单位处理完毕 -->

 </span>
 </td>
</tr>


<!-- 添加判断，是否非车询报价添加 -->
<!-- // if("OFFER".equals(strDealOrQueryType)){ -->

<!-- // 	com.sinosoft.prpall.blsvr.tb.BLPrpTmain blPrpTmain = new com.sinosoft.prpall.blsvr.tb.BLPrpTmain(); -->
<!-- // 	String offerFlag = blPrpTmain.isReOffer(businessNo); -->
<s:if test='"OFFER" == strDealOrQueryType'>
<input type='hidden' name='isReOffer' value='<s:property value="offerFlag"/>'/>
</s:if>
<s:else>
<input type='hidden' name='isReOffer' value='true'/>
</s:else>