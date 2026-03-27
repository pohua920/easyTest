<!--
****************************************************************************
* DESC       :添加主信息子块界面页面[ 立案 ]
* AUTHOR     :理赔组
* CREATEDATE :2004-10-11
* MODIFYLIST :  Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
    <table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
    <tr>
       <td width="30%"> 
         <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr> 
              <td width ="12"><img src="${ctx}/images/bgBarLeft.gif" width="12" height="19"></td>
              <td class="formtitle"><s:text name="prepay.paymentRegistration" /></td><%--预付赔款登记--%>
              <td width ="11"><img src="${ctx}/images/bgBarRight.gif" width="11" height="19"></td>
            </tr>
          </table>
       </td>
      <c:if test="${parame.editTypeOther!='SHOWTASK'}">
       <td><input type="button" name="message" value="<s:text name='button.claimsProcessingRecords.value' />" class="bigbutton" onclick="openWinSave()"></td><%--赔案处理记录--%>
          <td width="70%" align="right"><font color="#666666">　<s:text name="scheduleObject.note1" /><%--注:--%>“<font color="#FF0000">*</font>”<s:text name="scheduleObject.note2" />，<%--为必选项--%>“<img src="${ctx}/images/bgDoubleClick2.gif" width="13" height="13" align="absbottom">”
      <s:text name="scheduleObject.note3" />。<%--为双击选择项--%></font></td>
     </c:if>
     <c:if test="${param.editTypeOther=='SHOWTASK'}">
      <td width="70%"></td>
     </c:if>
    </tr>
    </table>
<table  border="0" align="center" cellpadding="4" cellspacing="1"  class="title" width="100%">
  <tr> 
    <td class="title" colspan="4" ><s:text name="prepay.paymentRegistration" /><%--预付赔款登记--%>
      <input type="hidden" name="prpLprepayRiskCode" value="${prpLprepay.riskCode}"> 
      <input type="hidden" name="prpLprepayOperatorCode" value="${prpLprepay.operatorCode}"> 
      <input type="hidden" name="prpLprepayMakeCom" value="${prpLprepay.makeCom}">	
      <input type="hidden" name="prpLprepayInputDate" value="${prpLprepay.inputDate}"> 
      <input type="hidden" name="prpLprepayLicenseColorCode" value="${prpLprepay.licenseColorCode}"> 
      <input type="hidden" name="prpLprepayCarKindCode" value="${prpLprepay.carKindCode}">
      <input type="hidden" name="prpLprepaySumPremium" value="${prpLprepay.sumPremium}">  
      <input type="hidden" name="prpLprepayArrearageTimes" value="${prpLprepay.arrearageTimes}"> 
      <input type="hidden" name="prpLprepaySumArrearage" value="${prpLprepay.sumArrearage}"> 
      <input type="hidden" name="prpLprepaySumBeforePrePaid" value="${prpLprepay.sumBeforePrePaid}"> 
      <input type="hidden" name="prpLprepayBlockUpTimes" value="${prpLprepay.blockUpTimes}">       
      <input type="hidden" name="prpLprepaySumTotalPrepaid" value="${prpLprepay.sumTotalPrepaid}">         
      <input type="hidden" name="prpLprepayApproverCode" value="${prpLprepay.approverCode}"> 
      <input type="hidden" name="prpLprepayUnderWriteCode" value="${prpLprepay.underWriteCode}">  
      <input type="hidden" name="prpLprepayUnderWriteName" value="${prpLprepay.underWriteName}"> 
      <input type="hidden" name="prpLprepayUnderWriteEndDate" value="${prpLprepay.underWriteEndDate}">    
      <input type="hidden" name="sumClaim"  value="${sumClaim }">  
      <input type="hidden" name="percent"  value="${sysconst_PrepayPercent }"> 
      <input type="hidden" name="prpLprepayUnderWriteFlag"  value="${prpLprepay.underWriteFlag}"> 
      <input type="hidden" name="prpLprepayFlag" value="${prpLprepay.flag}"> 
     	<input type="hidden" name="prpLprepayComCode"  value = "${prpLprepay.comCode}"> 
    	<input type="hidden" name="prpLprepayHandler1Code" value="${prpLprepay.handler1Code}"> 
        <input type="hidden" name="prpLprepayCaseType" value="${caseType }"> 
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
      <input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID }">
      <input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
      <input type="hidden" name="prpLprepayClassCode" class="common" value="${prpLprepay.classCode}">
       <!--增加保费是否已经实收信息-->
      <c:if test="${prePayFlag!=null&&prePayFlag!=''}">
      <input type="hidden" name="prePayFlag" value="${prePayFlag}">
      </c:if>
      <c:if test="${prePayFlag==null||prePayFlag==''}">
      <input type="hidden" name="prePayFlag" value="1">
      </c:if>
    </td>
  </tr>
  <tr> 
    <td class="title"  > <s:text name="prepay.paidAdvance" />:</td><%--预付赔款号--%>
    <td class="input"  style="width:36%"> <input type=text name="prpLprepayPreCompensateNo" title="预付赔款号" maxlength="22" class="readonly" readonly="true" value="${prpLprepay.preCompensateNo}"> 
    </td>
    <td class="title"  > <s:text name="certainLoss.claims" />:</td><%--赔案号--%>
    <td class="input"  style="width:34%"> <input type=text name="prpLprepayClaimNo" title="赔案号" maxlength="22" class="readonly" readonly="true"  value="${prpLprepay.claimNo}"> 
    </td>
  </tr>
  <tr> 
    <td class="title"> <s:text name="db.view_larrearage.policyNo" />:</td><%--保单号--%>
    <td class="input"> <input type=text name="prpLprepayPolicyNo" class="readonly" readonly="true"   style="width:140px" value="${prpLprepay.policyNo}">
       <input type="image" name="btRelate" src="${ctx}/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLprepayPolicyNo.value);return false;"> 
    </td>
    
    <td class="title" ><s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />:</td><%--已出险次数--%>
    <td class="input" >
      <!-- 出险信息画面 -->
           <%@include file="/pages/common/regist/ExistRegist.jsp"%>     
    </td>
  </tr>      
  <tr> 
    <td class="title"><s:text name="db.prpLperson.currency" />:</td><%--币别--%>
    <td class="input"> 
      <input name="prpLprepayCurrency" class="readonly"  readonly value="${prpLprepay.currency}"
          ondblclick="code_CodeSelect(this, 'Currency');"
          onkeyup= "code_CodeSelect(this, 'Currency');"> 
      <input name="prpLprepayCurrencyName" class="readonly" readonly  value="${prpLprepay.currencyName}"
         ondblclick="code_CodeSelect(this, 'Currency','-1','always','none','post');"
         onkeyup= "code_CodeSelect(this, 'Currency','-1','always','none','post');">       
    </td>
    <td class="title"> <s:text name="db.prpLprepay.sumPrepaid" />:</td><%--预赔金额--%>
    <td class="input">
    	<input name="prpLprepaySumPrePaid" type="text" class="input" style="width:130" value="<fmt:formatNumber value='${prpLprepay.sumPrePaid}' pattern='#'/>"><img src="${ctx}/images/bgMarkMustInput.jpg"> 
    </td>
  </tr> 
  <tr> 
    <td class="title"> <s:text name="db.prpLreplevy.comCode" />:</td><%--业务归属机构--%>
    <td class="input"> 
      <input type=text   name="prpLprepayComName" title="业务归属机构" class="readonly" readonly="true" value = "${prpLprepay.comName}"> 
    </td>
    <td class="title"><s:text name="db.prpLregist.handler1Code" />:</td><%--归属业务员--%>
    <td class="input"> 
      <input type=text   name="prpLprepayHandler1Name" title="归属业务员" class="readonly" readonly="true" value="${prpLprepay.handler1Name}"> 
    </td>
  </tr>
  <tr> 
    <td class="title"> <s:text name="db.prpLregist.handler1Name" />:</td><%--经办人--%>
    <td class="input"> 
     <input name="prpLprepayHandlerCode" class="codecode" style="width:40%" value="${prpLprepay.handlerCode}"
          ondblclick="code_CodeSelect(this, 'HanderCode');"
          onkeyup= "code_CodeSelect(this, 'HanderCode');"> <input name="prpLprepayHandlerName" class="codename" style="width:50%"  title="经办人" value="${prpLprepay.handlerName}"
         ondblclick="code_CodeSelect(this, 'HanderCode','-1','always','none','post');"
         onkeyup= "code_CodeSelect(this, 'HanderCode','-1','always','none','post');"> 
    </td>
    <td class="title"> <s:text name="cdb.prpLclaim.statisticsYM" />:</td><%--统计年月--%>
    <td class="input"> <input type="text" class="input" style="width:130" name="prpLprepayStatisticsYM" value="${prpLprepay.statisticsYM}">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
    </td> 
  </tr> 
  <tr>
             <td class="title" colspan="4">
              <s:text name="specialCase.ApplicationReason" />：<%--申请原因--%>
             </td>
         </tr>
         <tr>
           <td class="input" colspan="4" align="center">
             <textarea name='Context' wrap="hard" readonly="readonly" rows=8 cols=80 class=common >${reason}</textarea>
           </td>
         </tr>
  <c:if test="${coinsFlag=='1'}">
   <tr>
     <td class="left"><s:text name="commonAcci.compensate.whetherPaidReparat" /></td><%--是否代付赔款--%>
     <td class="right">
    	<input type="radio"  name="isPayForOther" <c:if test="${prpLprepay.isPayForOther=='1' }">checked</c:if> value="1"><s:text name="certainLoss.thirdCarLoss.yes" /><%--是--%>
        <input type="radio"  name="isPayForOther" <c:if test="${prpLprepay.isPayForOther=='0' }">checked</c:if> value="0"><s:text name="certainLoss.thirdCarLoss.no" /><%--否--%>
     </td>
     <td class="left"></td>
     <td class="right">
     </td>
   </tr>
  </c:if>
</table> 
 