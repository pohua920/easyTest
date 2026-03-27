<!--
****************************************************************************
* DESC       :添加主信息子块界面页面[ 立案 ]
* AUTHOR     :理赔组
* CREATEDATE :2004-05-12
* MODIFYLIST :  Name       Date            Reason/Contents
*          ------------------------------------------------------
*             增加代码选择的onchange事件，同时支持名称与代码的相互选择
****************************************************************************
-->
<%@ page import="com.sinosoft.claim.common.ConstantCodes" %>
<%@ include file="/common/taglibs.jsp"%>
<table  class=common cellpadding="5" cellspacing="1">
  <tr class=listtitle>   
    <td  colspan="4" >
      <c:if test="${caseType=='7'}"><s:text name="prepay.salvageFeesRegistration"/></c:if><!--支付抢救费登记-->
      <c:if test="${caseType=='8'}"><s:text name="prepay.rescueFeeRegistration"/></c:if><!--垫付抢救费登记-->
      <c:if test="${!(caseType=='7'&&caseType=='8')}"><s:text name="prepay.paymentRegistration"/></c:if><!--预付赔款登记-->
      <input type="hidden" name="prpLprepayRiskCode" value="${prpLprepay.riskCode}"> 
      <input type="hidden" name="prpLprepayOperatorCode" value="${prpLprepay.operatorCode}"> 
      <input type="hidden" name="prpLprepayMakeCom" value="${prpLprepay.makeCom}">	
      <input type="hidden" name="prpLprepayInputDate" value="${prpLprepay.inputDate}"> 
      <input type="hidden" name="prpLprepayLicenseColorCode" value="${prpLprepay.licenseColorCode}"> 
      <input type="hidden" name="prpLprepayCarKindCode" value="${prpLprepay.carKindCode}">
      <input type="hidden" name="prpLprepaySumPremium" value="${prpLprepay.sumPremium}">  
      <input type="hidden" name="prpLprepayCurrency" value="${prpLprepay.currency}"> 
      <input type="hidden" name="prpLprepayArrearageTimes" value="${prpLprepay.arrearageTimes}"> 
      <input type="hidden" name="prpLprepaySumArrearage" value="${prpLprepay.sumArrearage}"> 
      <input type="hidden" name="prpLprepaySumBeforePrePaid" value="${prpLprepay.sumBeforePrePaid}"> 
      <input type="hidden" name="prpLprepayBlockUpTimes" value="${prpLprepay.blockUpTimes}">       
      <input type="hidden" name="prpLprepaySumTotalPrepaid" value="${prpLprepay.sumTotalPrepaid}">         
      <input type="hidden" name="prpLprepayApproverCode" value="${prpLprepay.approverCode}"> 
      <input type="hidden" name="prpLprepayUnderWriteCode" value="${prpLprepay.underWriteCode}">  
      <input type="hidden" name="prpLprepayUnderWriteName" value="${prpLprepay.underWriteName}"> 
      <input type="hidden" name="prpLprepayUnderWriteEndDate" value="${prpLprepay.underWriteEndDate}">    
      <input type="hidden" name="prpLprepayCaseType" class="common" value="${caseType }">
      <input type="hidden" name="sumClaim"  value="${prpLprepay.sumClaim}"> 
        <input type="hidden" name="prpLprepayCaseType" value="${caseType }"> 
      <input type="hidden" name="percent"  value="${sysconst_PrepayPercent }"> 
      <input type="hidden" name="prpLprepayUnderWriteFlag"  value="${prpLprepay.underWriteFlag}"> 
      <input type="hidden" name="prpLprepayFlag" value="${payFlag }"> 
     	<input type="hidden" name="prpLprepayComCode"  value = "${prpLprepay.comCode}"> 
    	<input type="hidden" name="prpLprepayHandler1Code" value="${prpLprepay.handler1Code}"> 
    	<input type="hidden" name="ClaimNo" value="${prpLprepay.claimNo}">     	
    	<input type="hidden" name="riskCode" value="${prpLprepay.riskCode}">     	
    	<input type="hidden" name="editType" value="ADD"> 
    	<input type="hidden" name="swfLogFlowID" class="common" value="${param.swfLogFlowID }">
      <input type="hidden" name="swfLogLogNo" class="common" value="${param.swfLogLogNo}">
    	 <input type="hidden" name="prpLprepayClassCode" class="common" value="${prpLprepay.classCode}">  
    	<input type="hidden" name="registNo" class="common" value="${registNo }">
    	<input type="hidden" name="limitfeeHaveDuty" class="common" value="${limitfeeHaveDuty }">
    	<input type="hidden" name="limitfeeNoneDuty" class="common" value="${limitfeeNoneDuty }">
    	<input type="hidden" name="sumBeforePrepaidzf" class="common" value="${sumBeforePrepaidzf }">
    	<input type="hidden" name="sumBeforePrepaiddf" class="common" value="${sumBeforePrepaiddf }">
      <!-- 增加保费是否已经实收信息-->
      <c:if test="${prePayFlag!=null&&prePayFlag!=''}">
      <input type="hidden" name="prePayFlag" value="${prePayFlag }">
     </c:if>
      <c:if test="${prePayFlag==null||prePayFlag==''}">
      <input type="hidden" name="prePayFlag" value="0">
      </c:if>
      <input type="hidden" name="prpLprepayInsuredName" value="${insuredName }">
    </td>    
  </tr>
  <table  class=subtable cellpadding="0" cellspacing="1">
  <table  class=common cellpadding="1" cellspacing="1">
  <tr> 
    <td class="left" ><s:text name="prepay.compensationNo"/>：</td><!--赔款号-->
    <td class="right" > <input type=text name="prpLprepayPreCompensateNo" title="預付賠款號" maxlength="22" class="readonly" readonly="true" value="${prpLprepay.preCompensateNo}"> 
    </td>
    
    <td class="left" ><s:text name="db.prpLprepay.claimNo"/>：</td><!--立案号码-->
    <td class="right" > <input type=text name="prpLprepayClaimNo" title="立案號碼" maxlength="22" class="readonly" readonly="true"  value="${prpLprepay.claimNo}"> 
    </td>
 
 	<td class="left" ><s:text name="prompt.queRegist.RegistNo"/>：</td><!--備案號碼-->
    <td class="right" > <input type=text  title="備案號碼" maxlength="22" class="readonly" readonly="true"  value="${registNo}"> 
    </td>   
   </tr>
   <tr>
    <td class="left"><s:text name='db.prpCmain.policyNo'/>：<!-- 保单号 --></td>
    <td class="right" > 
    <input type=text name="prpLprepayPolicyNo" class="readonly" readonly="true"  value="${prpLprepay.policyNo}">
    </td>
    <td class='right'>
    <input type="image" name="btRelate" src="${ctx }/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLprepayPolicyNo.value);return false;"> 
    </td>
    <td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes"/></td>
    <td class="right" colspan='2'>
          <%-- 出险信息画面 --%>
           <%@include file="/pages/DAA/regist/DAAExistRegist.jsp"%>           
    </td>
   </tr>
<%--  </table>--%>
<%--  <table  class=common cellpadding="1" cellspacing="1">--%>
   <tr>
    <td class="left"><s:text name="db.prpDdeprecateRate.clauseType"/>：<!-- 条款类别 --></td>
    <td class="right" colspan=2> <input class="readonly" type=text name="prpLprepayClauseName" readonly="true"  value="${prpLprepay.clauseName}">	
    </td>
    <td class="left"><s:text name="regist.prpLregist.insuranceTime"/>：</td><!--保险期间-->
    <td class="right" colspan=2> 
    	 <%--<input type=text name="prpLprepayStartDate" class="readonly" readonly="true"  value="${prpLprepay.startDate}<s:text name='endcase.dayStart'/> ${prpLprepay.endDate} <s:text name='endcase.dayEnd'/>">--%><!--日 0 时 至--> <!--日 24 时止-->
    	<rc:rcDate name="prpLprepayStartDate"  class="readonly" readonly="true" wdatePicker="false"  style="width:80px" value="${prpLprepay.startDate}" /> <s:text name='endcase.dayStart'/><rc:rcDate name="endDate"  class="readonly" readonly="true" wdatePicker="false"  style="width:80px" value="${prpLprepay.endDate}" /> <s:text name='endcase.dayEnd'/><!--日 0 时 至--> <!--日 24 时止-->
      <!--
    	<input type=text name="prpLprepayStartDate" title="起保日期" class="readonly" style="width:80px" readonly="true"  value="<bean:write name='prpLprepayDto' property='startDate' filter='true' />">
       
      <input type=text name="prpLprepayEndDate"   title="終保日期" class="readonly" style="width:80px" readonly="true"   value="<bean:write name='prpLprepayDto' property='endDate' filter='true' />">
      二十四时止 
    -->
    </td>
   </tr>
   <tr>
    <td class="left"><s:text name='regist.prpLregist.damageTime'/>： <!-- 出险时间  --></td>
    <td colspan=2 class="right"> 
    	<%--<input type=text name="prpLprepayDamageStartDate" class="readonly" readonly="true" maxlength="10" style="width:250px" value="${prpLprepay.damageStartDate}<s:text name='regist.prpLregist.date'/> ${prpLprepay.damageStartHour}<s:text name='regist.prpLregist.hour'/> ${prpLprepay.damageStartMinute}<s:text name='regist.prpLregist.minute'/>">--%><!--日--><!--时--><!--分-->
    	 <rc:rcDate name="prpLprepayDamageStartDate" class="readonly" readonly="true" wdatePicker="false"  style="width:80px" value="${prpLprepay.damageStartDate}" /> <s:text name='regist.prpLregist.date'/> ${prpLprepay.damageStartHour}<s:text name='regist.prpLregist.hour'/> ${prpLprepay.damageStartMinute}<s:text name='regist.prpLregist.minute'/><!--日--><!--时--><!--分-->
    	<!--
    	<input type=text name="prpLprepayDamageStartDate" title="出險時間" class="input" maxlength="10" style="width:80px" value="<bean:write name='prpLprepayDto' property='damageStartDate' filter='true' />">
      日 
      <input type=text name="prpLprepayDamageStartHour" title="出險小時" class="input" maxlength="2" style="width:20px" value="<bean:write name='prpLprepayDto' property='damageStartHour' filter='true' />">
      时
      --> 
    </td>
    <td class="left"><s:text name='db.prpLclaim.damageAddress'/>：<!--出险地点 --></td>
    <td colspan=2 class="right"> <input type=text name="prpLprepayDamageAddress" title="<s:text name='db.prpLclaim.damageAddress'/>" style="width:400px" class="readonly" readonly="true" value="${prpLprepay.damageAddress}"> 
    </td>
  </tr>
  </table>
  <table  class=common cellpadding="1" cellspacing="1">
   <tr>
    <td class="left"><s:text name='db.prpLlawsuit.licenseNo'/>：<!--  号牌号码 --></td>
    <td class="right"> <input class="readonly" name="prpLprepayLicenseNo" readonly="true" value="${prpLprepay.licenseNo}"> 
    </td>
    <td class="left"><s:text name='db.prpLlawsuit.licenseColorCode'/>：<!-- 号牌底色  --></td>
    <td class="right"> 
    	<input class="readonly"  name="prpLprepayLicenseColor" readonly="true" value="${prpLprepay.licenseColor}"> 
    </td>
    <td class="left"><s:text name="certainLoss.thirdCarLoss.carKind"/>：</td><!--车辆种类-->
    <td class="right"> 
    	<input name="prpLprepayCarKind"  class="readonly" readonly="true" value="${prpLprepay.carKind}">  
    </td>
   </tr>
   <tr>
    <td class="left"><s:text name='db.prpLlawsuit.brandName'/>：<!--厂牌型号  --></td>
    <td class="right"> <input class="readonly" name="prpLprepayBrandName" readonly="true" value="${prpLprepay.brandName}"> 
    </td>
    <td class="left"><s:text name="db.prpLregist.engineNo"/>：</td><!--引擎号码-->
    <td class="right"> <input type="text" name="prpLprepayEngineNo" class="readonly" readonly="true" maxlength=20 description="发动机号" value="${prpLprepay.engineNo}"> 
    </td>
    <td class="left"><s:text name="db.prpLregist.frameNo"/>：</td><!--车架号-->
    <td class="right"> <input type="text" name="prpLprepayFrameNo" class="readonly" readonly="true" maxlength=20 description="车架号" value="${prpLprepay.frameNo}"> 
    </td>
   </tr>
  </table>
  
  <table  class=common cellpadding="1" cellspacing="1"> 
  <tr>
    <td class="left"><s:text name='db.prpLregist.moneyloss'/>：<!--估损金额 --></td>
    <td class="right"> <input class="readonly" readonly name="prpLprepaySumClaim" description="<s:text name='db.prpLregist.estimateLoss'/>" value="<fmt:formatNumber value="${prpLprepay.sumClaim}" pattern="#"/>"> 
    </td>
    <td class="left"><s:text name='db.prpLpersonloss.amount'/>：<!--保险金额 --></td>
    <td class="right"> 
    	<input name="prpLprepaySumAmount" type="text" class="readonly" readonly="true" value="<fmt:formatNumber value="${prpLprepay.sumAmount}" pattern="#"/>"> 
    </td>
    <td class="left"><s:text name='db.prpLpersonloss.currency'/>：<!-- 币别 --></td>
    <td class="right"> 
    	<input class="readonly"  name="prpLprepayCurrencyName" value="<%=com.sinosoft.claim.common.ConstantCodes.LOCAL_CURRENCYNAME%>"><img src="${ctx }/images/bgMarkMustInput.jpg">
    </td>
  </tr>
  </table>
  <table  class=common cellpadding="1" cellspacing="1"> 
  <tr>
    <td class="left"> 
    <c:if test="${caseType=='7'}"><s:text name="prepay.salvageFeesRegistration"/></c:if><!--支付抢救费登记-->
      <c:if test="${caseType=='8'}"><s:text name="prepay.rescueFeeRegistration"/></c:if><!--垫付抢救费登记-->
      <c:if test="${!(caseType=='7'&&caseType=='8')}"><s:text name="prepay.paymentRegistration"/></c:if><!--预付赔款登记-->
    </td>
    <td class="right"> 
    	<!---<input name="prpLprepaySumPrePaid" type="text" class="input" style="width:130" value="${prpLprepay.sumPrePaid}"   onblur="checkBeyondLimitFee(this);"><img src="/claim/images/bgMarkMustInput.jpg">--->
		<input name="prpLprepaySumPrePaid" type="text" class="input" value="<fmt:formatNumber value="${prpLprepay.sumPrePaid}" pattern="#"/>"><img src="${ctx }/images/bgMarkMustInput.jpg"> 
    </td>
    <td class="left"><s:text name="db.prpLreplevy.comCode"/>：</td><!--业务归属机构-->
    <td class="right"> 
      <input type=text   name="prpLprepayComName" title="業務歸屬機構" class="readonly" readonly="true" value = "${prpLprepay.comName}"> 
    </td>
    <td class="left"><s:text name='db.prpLclaim.handler1Code'/>：<!--归属业务员  --></td>
    <td class="right"> 
      <input type=text   name="prpLprepayHandler1Name" title="<s:text name='db.prpLclaim.handler1Code'/>" class="readonly" readonly="true" value="${prpLprepay.handler1Name}"> 
    </td>
  </tr>
  <tr> 
    <td class="left"><s:text name='db.prpLclaim.handlerCode'/>：<!--  经办人 --></td>
    <td class="right"> 
      <input name="prpLprepayHandlerCode" class="codecode" style="width:35%" value="${prpLprepay.handlerCode}"
          ondblclick="code_CodeSelect(this,'handerCode','0,1','Y');"
          onchange="code_CodeChange(this,'handerCode','0,1','Y');"
          onkeyup= "code_CodeSelect(this,'handerCode','0,1','Y');"> 
      <input name="prpLprepayHandlerName" class="codename" style="width:50%"  title="<s:text name='db.prpLclaim.handlerCode'/>" value="${prpLprepay.handlerName}"
          ondblclick="code_CodeSelect(this,'handerCode','-1,0','Y','N');"
          onkeyup= "code_CodeSelect(this,'handerCode','-1,0','Y','N');"
          onchange="code_CodeChange(this,'handerCode','-1,0','Y','N');"><img src="${ctx }/images/bgMarkMustInput.jpg">    	  
    </td>
    <td class="left"><s:text name='db.prpLcompensate.statisticsYM'/>:<!-- 统计年月  --></td>
    <td class="right"> 
    <%-- <input type="text" class="input" style="width:80" name="prpLprepayStatisticsYM" value="${prpLprepay.statisticsYM}">--%>
		<rc:rcDate name="prpLprepayStatisticsYM" class="readonly" readonly="true" wdatePicker="false"  style="width:80px" value="${prpLprepay.statisticsYM}" /> 
			<img src="${ctx }/images/bgMarkMustInput.jpg">
    </td>
    <td class='left'></td>
    <td class='right'></td>
  </tr>
  </table>
  </table>
</table>
