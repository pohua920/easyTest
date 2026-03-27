<!--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 结案 ] 	
* AUTHOR     ： 理赔组
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">
  <tr> 
    <td class="left">
      <input type="hidden" name="prpLendcaseClaimNo1" value="${prpLclaim.claimNo}"> 
      <input type="hidden" name="prpLendcaseCertiNo" value=" "> 
      <input type="hidden" name="prpLendcaseCertiType" value="C"> 
      <input type="hidden" name="prpLendcaseFlag" value="1"> 
      <input type="hidden" name="prpLendcaseCaseNo" value="${prpLcaseNo.id.caseNo}"> 
       <input type="hidden" name="prpLendcaseComCode" value="${prpLclaim.comCode}"> 
       <input type="hidden" name="prpNotBackCount" value="${prpLcaseNo.notBackCount}"> 
      <s:text name="certainLoss.prpLcheck.riskCName"/>  <%--- 险种名称 --%>
    </td>
    <td class="right">${riskCName }</td>
    <td class="left"><s:text name="check.claimNum"/></td>  <%--- 赔案号 --%>
    <td class="right">
    	<input type="text" name="prpLclaimClaimNo" title="赔案号" class="readonly" readonly="true" value="${prpLclaim.claimNo}">
    </td>
    <td class="left"><s:text name="db.prpLcompensate.lflag"/></td>  <%---理赔类型  --%>
    <td class="right">
    	<c:if test="${prpLclaim.lflag=='D'}">
        <input type="text" name="prpLendcaseLFlag" title="理赔类型" class="readonly" readonly="true"  value="<s:text name='compensate.generationClaim'/>"> <%--- 代理赔 --%>
      </c:if>
      <c:if test="${prpLclaim.lflag=='L'}">
        <input type="text" name="prpLendcaseLFlag" title="理赔类型" class="readonly" readonly="true"  value="<s:text name='endcase.claimsProcessing'/>"> <%--- 理赔 --%>
     </c:if>
    </td>
  </tr>
  <!-- 增加保费是否已经实收信息-->
      <c:if test="${not empty premiumFee}">
      <input type="hidden" name="premiumFee" value="${premiumFee }">
      </c:if>
       <c:if test="${empty premiumFee}">
      <input type="hidden" name="premiumFee" value="1">
      </c:if>
  <tr>
  	<td class="left"><s:text name="prpLbpmMain.mainNo"/></td>  <%--- 报案号 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimRegistNo"  class="readonly" readonly="true" value="${prpLclaim.registNo}">
  	</td>
  	<td class="left"></td>
  	<td class="right"></td>
  	<td class="left"></td>
  	<td class="right"></td>
  </tr>
  </table>
  </td>
  </tr>
  </table>
  <br>
  <table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">
  <tr>
  	<td class="left"><s:text name="db.prpLlawsuit.policyNo"/></td> <%--- 保单号 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimPolicyNo" class="readonly" readonly="true"  value="${prpLclaim.policyNo}">
      <input type="image" name="btRelate" src="${ctx}/images/butRelate.gif" onclick="relate(fm.prpLclaimPolicyNo.value, fm.prpLclaimRegistNo.value);return false;">
  	</td>
  	<td class="left"><s:text name="db.prpLclaim.businessNature"/></td>  <%---业务性质  --%>
  	<td class="right">
  		<input type="text" name="prpLclaimBusinessNatureName"  class="readonly" readonly="true" value="${prpLclaim.businessNature}">
  	</td>
  	<td class="left"><s:text name="db.prpLregist.language"/></td>  <%--- 语种 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimLanguageName" class="readonly" readonly="true" value="${prpLclaim.language}">
  	</td>
  </tr>
  <tr>
  	<td class="left"><s:text name="db.prpCmain.insuredName"/></td>  <%--- 被保险人名称 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimInsuredName" title="被保险人" class="readonly" readonly="true" value="${prpLclaim.insuredName}">
  	</td>
  	<td class="left"><s:text name="regist.prpLregist.insuranceTime"/></td>  <%--- 保险期间 --%>
  	<td class="right" colspan="3">
     <rc:rcDate name="prpLendcaseStartDate" class="readonly" wdatePicker="false" style="width:160px" readonly="true"  value="${prpLclaim.startDate} 日 0 时 至"/>
     <rc:rcDate name="endDate" class="readonly" readonly="true" wdatePicker="false" style="width:160px" value="${prpLclaim.endDate} 日 24 时止"/>
  	</td>
  </tr>
  <tr>
  	<td class="left"><s:text name="regist.prpLregist.currency"/></td>  <%---币别  --%>
  	<td class="right">
  		<input type="text" name="prpLclaimCurrency" class="readonly" readonly="true"  value="${prpLclaim.currency}">
  	</td>
  	<td class="left"><s:text name="regist.prpLregist.sumAmount"/></td> <%---保险金额  --%>
  	<td class="right">
  		<input type="text" name="prpLclaimSumAmount" class="readonly" readonly="true"  value="<fmt:formatNumber value='${prpLclaim.sumAmount}' pattern="#"/>">
  	</td>
  	<td class="left"></td>
  	<td class="right"></td>
  </tr>
  </table>
  </td>
  </tr>
  </table>
  <br>
  <table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">
  <tr>
  	<td class="left"><s:text name="regist.prpLregist.damageTime"/></td> <%---出险时间  --%>
  	<td class="right">
     <rc:rcDate name="prpLendcaseDamageStartDate" title="出险时间" readonly="true" class="readonly" wdatePicker="false" value="${prpLclaim.damageStartDate} 日 ${prpLclaim.damageStartHour} 时"/>
  	</td>
  	<td class="left"><s:text name="regist.prpLregist.damageCode"/></td> <%--- 出险原因 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimDamageName" class="readonly" readonly="true" value="${prpLclaim.damageName}">
  	</td>
  	<td class="left"><s:text name="db.prpLclaim.damageAddress"/></td><%--- 出险地点 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimDamageAddress" class="readonly" readonly="true" value="${prpLclaim.damageAddress}">
  	</td>
  </tr>
  <tr>
  	<td class="left"><s:text name="db.prpLclaim.lossName"/></td> <%--- 受损标的 --%>
  	<td class="right" colspan="5">
  		<input type="text" name="prpLclaimLossName" class="readonly" readonly="true" value="${prpLclaim.lossName}">
  	</td>
  </tr>
  </table>
  </td>
  </tr>
  </table>
  <br>
  <table  class=subtable cellpadding="0" cellspacing="1">
  <tr>
	<td>
	  <table  class=common cellpadding="1" cellspacing="1">
  <tr>
  	<td class="left"><s:text name="db.prpLclaim.claimDate"/></td><%---立案日期  --%>
  	<td class="right">
  	<rc:rcDate name="prpLclaimClaimDate" wdatePicker="false" class="readonly" readonly="true" value="${prpLclaim.claimDate}"/>
  	</td>
  	<td class="left">
  		<s:text name="db.prpLregist.estimateLoss"/>：<input type="text" name="prpLclaimCurrency"  class="readonly" readonly="true" value="${prpLclaim.currency}"> <%--- 估损金额 --%>
  	</td>
  	<td class="right">
  		<input type="text" name="prpLclaimSumClaim" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumClaim}' pattern='#'/>">
  	</td>
  	<td class="left">
  		<s:text name="db.prpLCMain.sumClaim"/>：<input type="text" name="prpLclaimCurrency"  class="readonly" readonly="true" value="${prpLclaim.currency}"> <%--- 赔付金额 --%>
  	</td>
  	<td class="right">
  		<input type="text" name="prpLclaimSumPaid" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpLclaim.sumPaid}' pattern='#'/>">
  	</td>
  </tr>
  <tr>
  	<td class="left"><s:text name="endcase.policyBusiness"/></td> <%--- 保单业务归属部门 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimComName" class="readonly" readonly="true" value="${prpLclaim.comName}">
  	</td>
  	<td class="left"><s:text name="db.prpLregist.handler1Code"/></td>  <%--- 归属业务员 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimHandler1Name" class="readonly" readonly="true" value="${prpLclaim.handler1Name}">
  	</td>
  	<td class="left"><s:text name="db.prpLclaim.agentCode"/></td> <%--- 代理人 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimAgentCode" class="readonly" readonly="true" value="${prpLclaim.agentCode}">
  	</td>
  </tr>
  <tr>
  	<td class="left"><s:text name="db.prpLregist.handler1Name"/></td> <%--- 经办人 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimHandlerName" class="readonly" readonly="true" value="${prpLclaim.handlerName}">
  	</td>
  	<td class="left"><s:text name="endcase.claimsDepartment"/></td> <%--- 理赔部门 --%>
  	<td class="right">
  		<input type="text" name="prpLendcaseMakeCom" class="readonly" readonly="true" value="${prpLclaim.makeComName}">
  	</td>
  	<td class="left"><s:text name="db.prpLlawsuit.operatorCode"/></td><%--- 操作员 --%>
  	<td class="right">
  		<input type="text" name="prpLregistOperatorName" class="readonly" readonly="true" value="${prpLclaim.operatorName}">
  	</td>
  </tr>
  <tr>
  	<td class="left"><s:text name="db.prpLlawsuit.inputDate"/></td> <%--- 输单日期 --%>
  	<td class="right">
  	<rc:rcDate name="prpLclaimInputDate" wdatePicker="false" class="readonly" readonly="true" value="${prpLclaim.inputDate}"/>
  	</td>
  	<td class="left"><s:text name="db.prpLreplevy.endCaseDate"/></td> <%--- 结案日期 --%>
  	<td class="right">
  	<rc:rcDate name="prpLclaimEndCaseDate" wdatePicker="false" class="readonly" readonly="true" value="${prpLclaim.endCaseDate}"/>
  	</td>
  	<td class="left"><s:text name="endcase.closed"/></td><%--- 结案员 --%>
  	<td class="right">
  		<input type="hidden" name="prpLclaimEndCaserCode" class="readonly" readonly="true" value="${prpLclaim.endCaserCode}">
  	<input type="text" name="prpLclaimEndCaseName" class="readonly" readonly="true" value="${prpLclaim.endCaserName}"> 
  	</td>
  </tr>
  <tr>
  	<td class="left"><s:text name="db.prpLreplevy.cancelDate"/></td> <%--- 注销日期 --%>
  	<td class="right">
  	<rc:rcDate name="prpLclaimCancelDate" wdatePicker="false" class="readonly" readonly="true" value="${prpLclaim.cancelDate}"/>
  	</td>
  	<td class="left"><s:text name="endcase.logPeople"/></td> <%--- 注销人 --%>
  	<td class="right">
  		<input type="text" name="prpLclaimDealerCode" class="readonly" readonly="true" value="${prpLclaim.dealerCode}">
  	</td>
  	<td class="left"></td>
  	<td class="right"></td>
  </tr>
  <tr>
  	<td class="left"><s:text name="certify.whetherInsure"/></td><%--- 是否涉及担保 --%>
  	<td class="right">
  		<select name="guaranteeFlag" style="width:50%" disabled="true">
            <option value="0" <c:if test="${prpLclaim.guaranteeFlag=='0' }">selected="selected"</c:if>><s:text name="certainLoss.thirdCarLoss.no"/></option><%--- 否 --%>
            <option value="" <c:if test="${prpLclaim.guaranteeFlag=='' }">selected="selected"</c:if>><s:text name="certainLoss.thirdCarLoss.no"/></option><%--- 否 --%>
            <option value="1" <c:if test="${prpLclaim.guaranteeFlag=='1' }">selected="selected"</c:if>><s:text name="certainLoss.thirdCarLoss.yes"/></option><%--- 是 --%>
            <option value="2" <c:if test="${prpLclaim.guaranteeFlag=='2' }">selected="selected"</c:if>><s:text name="certainLoss.thirdCarLoss.yes"/></option><%--- 是 --%>
            <option value="3" <c:if test="${prpLclaim.guaranteeFlag=='3' }">selected="selected"</c:if>><s:text name="certainLoss.thirdCarLoss.yes"/></option><%--- 是 --%>
            <option value="4" <c:if test="${prpLclaim.guaranteeFlag=='4' }">selected="selected"</c:if>><s:text name="certainLoss.thirdCarLoss.yes"/></option><%--- 是 --%>
            <option value="5" <c:if test="${prpLclaim.guaranteeFlag=='5' }">selected="selected"</c:if>><s:text name="certainLoss.thirdCarLoss.yes"/></option><%--- 是 --%>
            <option value="6" <c:if test="${prpLclaim.guaranteeFlag=='6' }">selected="selected"</c:if>><s:text name="certainLoss.thirdCarLoss.yes"/></option><%--- 是 --%>
        </select>
  	</td>
  	<td class="left"></td>
  	<td class="right">
  	</td>
  	<td class="left"></td>
  	<td class="right"></td>
  </tr> 
</table>
</td>
</tr>
</table>
