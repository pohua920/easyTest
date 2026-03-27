<!--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-05-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
  <%@ include file="/common/taglibs.jsp"%>
         <table width="100%" border="0" cellpadding="5" cellspacing="1">
           <tr> 
              
       <td align=center><input type="button" name="message" value="<s:text name='button.disMessage.value'/>" class="bigbutton" onclick="openWinSave()"></td> <%--讨论留言 --%>
       <td align=center><input type="button" name="messageView"  class="bigbutton" value="<s:text name='button.viewMessage.value'/>" onclick="openWinQuery()"></td><%-- 查看留言 --%>
       
    </tr>
    </table>
<table  border="0" align="center" cellpadding="5" cellspacing="1"  class="common">
  <tr> 
    <td class="title" colspan="4" ><s:text name="prepay.paymentRegistration"/><%-- 预付赔款登记 --%>
      <input type="hidden" name="prpLprepayRiskCode" value="${prpLprepay.riskCode}"> 
      <input type="hidden" name="prpLprepayOperatorCode" value="${prpLprepay.operatorCode}"> 
      <input type="hidden" name="prpLprepayMakeCom" value="${prpLprepay.makeCom}">	
      <input type="hidden" name="prpLprepayTypeForDriver" value="claim"> 
      <input type="hidden" name="prpLprepayInputDate" value="${prpLprepay.inputDate}"> 
      <input type="hidden" name="prpLprepayLicenseColorCode" value="${prpLprepay.licenseColorCode}"> 
      <input type="hidden" name="prpLprepayCarKindCode" value="${prpLprepay.carKindCode}">
      <input type="hidden" name="prpLprepaySumPremium" value="${prpLprepay.sumPremium}">  

      <input type="hidden" name="prpLprepayPolicyCurrency"> 
      <input type="hidden" name="prpLprepayArrearageTimes" value="0"> 
      <input type="hidden" name="prpLprepaySumArrearage" value="0"> 
      <input type="hidden" name="prpLprepaySumBeforePrePaid"  value="0"> 
      <input type="hidden" name="prpLprepayBlockUpTimes"  value="0">       
      <input type="hidden" name="prpLprepaySumTotalPrepaid"  value="0">         
      <input type="hidden" name="prpLprepayApproverCode"> 
      <input type="hidden" name="prpLprepayUnderWriteCode"> 
      <input type="hidden" name="prpLprepayUnderWriteName"> 
      <input type="hidden" name="prpLprepayUnderWriteEndDate">    
      <input type="hidden" name="sumClaim"  value="${sumClaim }"> 
      <input type="hidden" name="percent"  value="${sysconst_PrepayPercent }"> 
      <input type="hidden" name="prpLprepayUnderWriteFlag"  value="${prpLprepay.underWriteFlag}"> 
      <input type="hidden" name="prpLprepayFlag" value="${prpLprepay.flag}"> 
     	<input type="hidden" name="prpLprepayComCode"  value = "${prpLprepay.comCode}"> 
    	<input type="hidden" name="prpLprepayHandler1Code" value="${prpLprepay.handler1Code}"> 
    
     
      <input type="hidden" name="prpLprepayClauseName" value="${prpLprepay.clauseName}">	  
     	<input type="hidden" name="prpLprepayStartDate" value="${prpLprepay.startDate} 日 0 时 至 ${prpLprepay.endDate} 日 24 时止">
      <input type="hidden" name="prpLprepayLicenseNo" value="${prpLprepay.licenseNo}"> 
      <input type="hidden" name="prpLprepayLicenseColor" value="${prpLprepay.licenseColor}"> 
      <input type="hidden" name="prpLprepayCarKind" value="${prpLprepay.carKind}">  
      <input type="hidden" name="prpLprepayBrandName" value="${prpLprepay.brandName}"> 
      <input type="hidden" name="prpLprepayEngineNo" value="${prpLprepay.engineNo}"> 
      <input type="hidden" name="prpLprepayFrameNo" value="${prpLprepay.frameNo}"> 
      <input type="hidden" name="prpLprepayDamageStartDate" value="${prpLprepay.damageStartDate} 日 ${prpLprepay.damageStartHour} 时 ${prpLprepay.damageStartMinute} 分">
      <input type="hidden" name="prpLprepayDamageAddress" value="${prpLprepay.damageAddress}"> 
      <input type="hidden" name="prpLprepaySumClaim" value="${prpLprepay.sumClaim}"> 
      <input type="hidden" name="prpLprepaySumAmount" value="${prpLprepay.sumAmount}"> 
    </td>
  </tr>
  <tr> 
    <td class="title"  > <s:text name="prepay.paidAdvance"/>：</td> <%--预付赔款号  --%>
    <td class="input"  > <input type=text name="prpLprepayPreCompensateNo" title="预付赔款号" maxlength="22" class="readonly" readonly="true" value="${prpLprepay.preCompensateNo}"> 
    </td>
    <td class="title"  > <s:text name="check.claimNum"/>：</td>  <%-- 赔案号 --%>
    <td class="input"  > <input type=text name="prpLprepayClaimNo" title="赔案号" maxlength="22" class="readonly" readonly="true"  value="${prpLprepay.claimNo}"> 
    </td>
  </tr>
  <tr> 
    <td class="title"> <s:text name="db.prpLlawsuit.policyNo"/>：</td> <%--  保单号--%>
    <td class="input" colspan="3"> <input type=text name="prpLprepayPolicyNo" class="readonly" readonly="true"   style="width:140px" value="${prpLprepay.policyNo}"><input type="image" name="btRelate" src="${ctx}/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.PolicyNo.value);"> 
    </td>
  </tr>      
  <tr> 
    <td class="title"><s:text name="regist.prpLregist.currency"/>：</td> <%--币别  --%>
    <td class="input"> 
      <input name="prpLprepayCurrency" class="readonly" readonly   value="${prpLprepay.currency}"> 
      <input name="prpLprepayCurrencyName" class="readonly" readonly  value="${prpLprepay.currencyName}">       
    </td>
    <td class="title"> <s:text name="db.prpLprepay.sumPrepaid"/>：</td><%-- 预赔金额 --%>
    <td class="input"> 
    	<input name="prpLprepaySumPrePaid" type="text" class="readonly" readonly  style="width:130" value="${prpLprepay.sumPrePaid}"><img src="${ctx}/images/bgMarkMustInput.jpg"> 
    </td>
  </tr> 
  <tr> 
    <td class="title"> <s:text name="db.prpLclaim.comCode"/> ：</td>  <%-- 业务归属机构 --%>
    <td class="input"> 
      <input type=text   name="prpLprepayComName" class="readonly" readonly="true" value = "${prpLprepay.comName}"> 
    </td>
    <td class="title"><s:text name="db.prpLregist.handler1Code"/>：</td>  <%-- 归属业务员 --%>
    <td class="input"> 
      <input type=text   name="prpLprepayHandler1Name" class="readonly" readonly="true" value="${prpLprepay.handler1Name}"> 
    </td>
  </tr>
  <tr> 
    <td class="title">  <s:text name="db.prpLregist.handler1Name"/>：</td> <%-- 经办人 --%>
    <td class="input"> 
      <input name="prpLprepayHandlerCode" class="readonly" readonly  style="width:90px" value="${prpLprepay.handlerCode}">
      <input name="prpLprepayHandlerName" class="readonly" readonly  style="width:120px" value="${prpLprepay.handlerName}"> 
    </td>
    <td class="title"> <s:text name="db.prpLclaim.statisticsYM"/> ：</td><%-- 统计年月 --%>
    <td class="input"> <input type="text" class="readonly" readonly  style="width:130" name="prpLprepayStatisticsYM" value="${prpLprepay.statisticsYM}">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
    </td> 
  </tr>  
</table> 