<%@page import="com.sinosoft.sysframework.reference.AppConfig"%>
<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-07-13 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>  
<%@ include file="/common/taglibs.jsp"%>
  <table class=common cellpadding="5" cellspacing="1">
    <tr>
      <td class="centertitle" colspan="4" ><s:text name="verifyLoss.editVerifyLoss" /><%--核损登记 --%>
        <input type="hidden" name="prpLverifyLossClaimNo"              value="${requestScope.prpLverifyLoss.claimNo}">             
        <input type="hidden" name="prpLverifyLossRiskCode"             value="${requestScope.prpLverifyLoss.riskCode}">            
        <input type="hidden" name="prpLverifyLossLicenseColorcode"     value="${requestScope.prpLverifyLoss.licenseColorCode}">
        <input type="hidden" name="prpLverifyLossCarKindCode"          value="${requestScope.prpLverifyLoss.carKindCode}">         
        <input type="hidden" name="prpLverifyLossSumPreDefLoss"        value="${requestScope.prpLverifyLoss.sumPreDefLoss}">       
        <input type="hidden" name="prpLverifyLossSumDefLoss"           value="${requestScope.prpLverifyLoss.sumDefLoss}">          
        <input type="hidden" name="prpLverifyLossMakeCom"              value="${requestScope.prpLverifyLoss.makeCom}">             
        <input type="hidden" name="prpLverifyLossComCode"              value="${requestScope.prpLverifyLoss.comCode}">            
        <input type="hidden" name="coreURL" value="<%=AppConfig.get("sysconst.Core_URL")%>">
        <input type="hidden" name="prpLverifyLossUnderWriteCode"       value="${requestScope.prpLverifyLoss.underWriteCode}">      
        <input type="hidden" name="prpLverifyLossUnderWriteName"       value="${requestScope.prpLverifyLoss.underWriteName}">      
        <input type="hidden" name="prpLverifyLossUnderWriteEndDate"    value="${requestScope.prpLverifyLoss.underWriteEndDate}">   
        <input type="hidden" name="prpLverifyLossUnderWriteFlag"       value="${requestScope.prpLverifyLoss.underWriteFlag}">      
        <input type="hidden" name="damageStartDate"                    value="${requestScope.prpLregist.damageStartDate}"/>
        <input type="hidden" name="damageStartHour"                    value="${requestScope.prpLregist.damageStartHour}"/>
        <input type="hidden" name="prpLverifyLossRemark"               value="${requestScope.prpLverifyLoss.remark}">        
        <input type="hidden" name="prpLverifyLossFlag"                 value="${requestScope.prpLverifyLoss.flag}"> 
        <input type="hidden" name="prpLverifyLossLossItemCode"         value="${requestScope.prpLverifyLoss.id.lossItemCode}">   
        <input type="hidden" name="prpLverifyLossNodeType"         value="${requestScope.prpLverifyLoss.id.nodeType}"> 
        <input type="hidden" name="prpLverifyLossLossItemName"         value="${requestScope.prpLverifyLoss.lossItemName}">   
        <input type="hidden" name="prpLverifyLossInsureCarFlag"        value="${requestScope.prpLverifyLoss.insureCarFlag}"> 
        <input type="hidden" name="prpLverifyLossRegistNo" value="${requestScope.prpLverifyLoss.id.registNo}">
        <input type="hidden" name="prpLverifyLossPolicyNo" style="width:140px" value="${requestScope.prpLverifyLoss.policyNo}">          
        <input type="hidden" name="prpLverifyLossInsuredName" value="${requestScope.prpLverifyLoss.insuredName}">
        <input type="hidden" name="prpLverifyLossLicenseNo" style="width:140px" value="${requestScope.prpLverifyLoss.licenseNo}">          
        <input type="hidden" name="prpLverifyLossLicenseColor" value="${requestScope.prpLverifyLoss.licenseColor}">
        <input type="hidden" name="prpLverifyLossCarKind" style="width:140px" value="${requestScope.prpLverifyLoss.carKind}">          
        <input type="hidden" name="prpLverifyLossClauseName" value="${requestScope.prpLverifyLoss.clauseName}">
        <input type="hidden" name="prpLverifyLossCurrencyName" style="width:140px" value="${requestScope.prpLverifyLoss.currencyName}">          
        <input type="hidden" name="prpLverifyLossCurrency" value="${requestScope.prpLverifyLoss.currency}">          
        <input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
        <input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
        <input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
        <input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
        <%--//reason :增加理算退回定损的环节--%>
        <input type="hidden" name="prpLverifyLossCompensateFlag" value="${requestScope.prpLverifyLoss.compensateFlag}">
        <input type="hidden" name="prpLverifyLossCompensateOpinion" value="${requestScope.prpLverifyLoss.compensateOpinion}">          
        <input type="hidden" name="prpLverifyLossCompensateBackDate" value="${requestScope.prpLverifyLoss.compensateBackDate}">          
        <input type="hidden" name="prpLverifyLossCompensateApproverCode" value="${requestScope.prpLverifyLoss.compensateApproverCode}">          
      
      </td>  
    </tr>
    </table>
    <table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">      
    <tr>
      <td class="left"><s:text name="prpLregist.registNo" />：</td><%--报案号--%>
      <td class="right">
        <input type=text name="prplCheckRegistNo" class="readonly" readonly="true" value="${requestScope.prpLcheckTemp.id.registNo}">
        <input type="button" name="btRegistRelate" value="<s:text name='button.reportInfo.value' />" class='button' onclick="relateRegist();return false;"><%--报案信息 --%>
      </td>
      <td class="left"><s:text name="db.view_larrearage.policyNo" />：</td><%--保单号 --%>
      <td class="right">
        <input type=text name="prplCheckPolicyNo" class="readonly" readonly="true" value="${requestScope.prpLcheckTemp.policyNo}">
        <input type="button" name="btPolicyRelate" value="<s:text name='button.InsuranceInformation.value' />" class='button' onclick="relateBeforePolicyNo('${prpLregist.policyNo}','${prpLregist.riskCode}','${prpLregist.damageStartDate}');"><%--保单信息 --%>
      </td>
      <td class="left"><s:text name="check.claimNum" />：</td><%--赔案号 --%>
      <td class="right">
        <input type=text name="prplCheckClaimNo" class="readonly" readonly="true" value="${requestScope.prpLcheckTemp.claimNo}">
        <input type="image" name="btRelate" src="/claim/images/butRelate.gif" onclick="relate(fm.prplCheckPolicyNo.value);return false;">
      </td>
      <td class="right"></td>
      <td class="right"></td>
    </tr>          
    <tr>
      <td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" /></td><%--出险时间 --%>
      <td class="right">
        <%--<input type=text name="prpLregistDamageStartDate1" class="readonly" readonly maxlength="10" style="width:40%" value="${prpLregist.damageStartDate}"><s:text name="regist.prpLregist.date" />--%><%--日 --%>
        <rc:rcDate name="prpLregistDamageStartDate1"  class="readonly" readonly="true" wdatePicker="false"  style="width:90px;" value="${prpLregist.damageStartDate}" /><s:text name="regist.prpLregist.date" /><%--日 --%>
        <input type=text name="prpLregistDamageStartHour1" class="readonly" readonly maxlength="2" style="width:10%" value="${prpLregist.damageStartHour}"><s:text name="regist.prpLregist.hour" /><%--时 --%>
        <input type=text name="prpLregistDamageStartMinute1" class="readonly" readonly maxlength="2" style="width:10%" value="${prpLregist.damageStartMinute}"><s:text name="regist.prpLregist.minute" /><%--分 --%>
      </td>
      <td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" /></td><%--出险地点 --%>
      <td class="right">
        <input type=text name="prpLregistDamageAddress" class="readonly" readonly value="${prpLregist.damageAddress}">
      </td>
      <td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" /></td><%--已出险次数 --%>
      <td class="right">
        <%-- 出险信息画面 --%> 
        <%@include file="/pages/DAA/regist/DAAExistRegist.jsp"%> 
      </td>
    </tr>  
    <tr>
      <td class="left"><s:text name="check.surveyTtime" />：</td><%--查勘时间 --%>
      <td class="right">
      <rc:rcDate name="checkDate" class="readonly" readonly="true" wdatePicker="false" format="yyyy-MM-dd HH:mm:ss" value="${requestScope.prpLcheckTemp.checkDate}" />
      </td> 
      <td class="left"><s:text name="prpLcheck.checkArea" />：</td><%--查勘地点 --%>
      <td class="right"><c:out value="${requestScope.prpLcheckTemp.checkSite}" /></td>
      <td class="left" style="width:15%"><s:text name="certainLoss.firstSite" />：</td><%--是否第一现场查勘 --%>
      <td class="right">
      　　<c:if test="${requestScope.prpLcheckTemp.firstSiteFlag =='0'}"><s:text name="certainLoss.thirdCarLoss.no" /></c:if><%--否 --%>
         <c:if test="${requestScope.prpLcheckTemp.firstSiteFlag =='1'}"><s:text name="certainLoss.thirdCarLoss.yes" /></c:if> <%--是 --%>
      </td>	
    </tr> 
    <tr>
      <td class="left"></td>
      <td class="right">
         <input type="button" class='bigbutton' value="<s:text name='button.viewInformation.value' />" onclick="relateCheck();return false;"><%--查看查勘信息 --%>
      </td>
      <td class="left"></td>
      <td class="right"></td>
      <td class="left"></td>
      <td class="right"></td>
    </tr> 
    <tr>
      <td class="left"><s:text name="certainLoss.person1" />：</td><%--查勘人1 --%>
      <td class="right"><c:out value="${requestScope.prpLcheckTemp.checker1}" /></td>  
      <td class="left"><s:text name="certainLoss.person2" />：</td><%--查勘人2 --%>
      <td class="right"><c:out value="${requestScope.prpLcheckTemp.checker2}" /></td>
      <td class="left"></td>
      <td class="right"></td>
    </tr>  
    <tr>
      <td class="left"><s:text name="certainLoss.lossTime" />：</td><%--定损时间 --%>
      <td class="right">
       <%-- <input name="prpLverifyLossDefLossDate" class="readonly" readonly value="${requestScope.prpLverifyLoss.defLossDate}"> --%>        
      	<rc:rcDate name="prpLverifyLossDefLossDate" class="readonly" readonly="true" wdatePicker="false"  value="${requestScope.prpLverifyLoss.defLossDate}" />
      </td>
      <td class="left"><s:text name="certainLoss.prpLscheduleMainWF.lossPerson" /></td><%--定损人员 --%>
      <td class="right">
        <input name="prpLverifyLossHandlerCode" class="readonly" readonly style="width:40%"  value="${requestScope.prpLverifyLoss.handlerCode}">         
        <input name="prpLverifyLossHandlerName" class="readonly" readonly style="width:45%" value="${requestScope.prpLverifyLoss.handlerName}"> 
      </td>
      <td class="left"></td>
      <td class="right"></td>
    </tr> 
    <tr>
      <td class="left"><s:text name="verifyPrice.initialFeeAmount" />：</td><%--初次定损金额 --%>
      <td class="right">
       <input name="prpLverifyLossFirstDefLoss" class="readonly" readonly value="<fmt:formatNumber value="${requestScope.prpLverifyLoss.firstDefLoss}" pattern="#" />">         
      </td>
      <td class="left"><s:text name="verifyPrice.discrepancyAmount" />：</td><%--偏差定损金额 --%>
      <td class="right">
        <input name="prpLverifyLossWarpDefLoss"  class='input' value="<fmt:formatNumber value="${requestScope.prpLverifyLoss.warpDefLoss}" pattern="#" />"> 
      </td>
      <td class="left"></td>
      <td class="right"></td>
    </tr> 
  </table> 
  </td>
  </tr>
  </table>	  
  <input type="hidden" name="riskcode" value="${requestScope.prpLcheckTemp.riskCode}"> 
  <input type="hidden" name="policyno" value="${requestScope.prpLcheckTemp.policyNo}"> 
  <input type="hidden" name="PolicyNo" value="${requestScope.prpLcheckTemp.policyNo}"> 
  <input type="hidden" name="RegistNo" value="${requestScope.prpLcheckTemp.id.registNo}"> 
 
