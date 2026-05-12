<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ：sunchenggang
* CREATEDATE ：2006-03-07
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td class="centertitle" colspan="4">
			<s:text name="verifyPrice.editVerifyPrice" />
			<%-- 核价登记 --%>
			<input type="hidden" name="prpLverifyLossClaimNo" value="<bean:write name='prpLverifyLossDto' property='claimNo'/>">
			<input type="hidden" name="prpLverifyLossRiskCode" value="<bean:write name='prpLverifyLossDto' property='riskCode'/>">
			<input type="hidden" name="prpLverifyLossLicenseColorcode" value="<bean:write name='prpLverifyLossDto' property='licenseColorcode'/>">
			<input type="hidden" name="prpLverifyLossCarKindCode" value="<bean:write name='prpLverifyLossDto' property='carKindCode'/>">
			<input type="hidden" name="prpLverifyLossSumPreDefLoss" value="<bean:write name='prpLverifyLossDto' property='sumPreDefLoss'/>">
			<input type="hidden" name="prpLverifyLossSumDefLoss" value="<bean:write name='prpLverifyLossDto' property='sumDefLoss'/>">
			<input type="hidden" name="prpLverifyLossMakeCom" value="<bean:write name='prpLverifyLossDto' property='makeCom'/>">
			<input type="hidden" name="prpLverifyLossComCode" value="<bean:write name='prpLverifyLossDto' property='comCode'/>">
			<input type="hidden" name="prpLverifyLossUnderWriteCode" value="<bean:write name='prpLverifyLossDto' property='underWriteCode'/>">
			<input type="hidden" name="prpLverifyLossUnderWriteName" value="<bean:write name='prpLverifyLossDto' property='underWriteName'/>">
			<input type="hidden" name="prpLverifyLossUnderWriteEndDate" value="<bean:write name='prpLverifyLossDto' property='underWriteEndDate'/>">
			<input type="hidden" name="prpLverifyLossUnderWriteFlag" value="<bean:write name='prpLverifyLossDto' property='underWriteFlag'/>">
			<input type="hidden" name="prpLverifyLossRemark" value="<bean:write name='prpLverifyLossDto' property='remark'/>">
			<input type="hidden" name="prpLverifyLossFlag" value="<bean:write name='prpLverifyLossDto' property='flag'/>">
			<input type="hidden" name="prpLverifyLossLossItemCode" value="<bean:write name='prpLverifyLossDto' property='lossItemCode'/>">
			<input type="hidden" name="prpLverifyLossLossItemName" value="<bean:write name='prpLverifyLossDto' property='lossItemName'/>">
			<input type="hidden" name="prpLverifyLossInsureCarFlag" value="<bean:write name='prpLverifyLossDto' property='insureCarFlag'/>">
			<input type="hidden" name="prpLverifyLossRegistNo" value="<bean:write name='prpLverifyLossDto' property='registNo'/>">
			<input type="hidden" name="prpLverifyLossPolicyNo" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='policyNo'/>">
			<input type="hidden" name="prpLverifyLossInsuredName" value="<bean:write name='prpLverifyLossDto' property='insuredName'/>">
			<input type="hidden" name="prpLverifyLossLicenseNo" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='licenseNo'/>">
			<input type="hidden" name="prpLverifyLossLicenseColor" value="<bean:write name='prpLverifyLossDto' property='licenseColor'/>">
			<input type="hidden" name="prpLverifyLossCarKind" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='carKind'/>">
			<input type="hidden" name="prpLverifyLossClauseName" value="<bean:write name='prpLverifyLossDto' property='clauseName'/>">
			<input type="hidden" name="prpLverifyLossCurrencyName" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='currencyName'/>">
			<input type="hidden" name="prpLverifyLossCurrency" value="<bean:write name='prpLverifyLossDto' property='currency'/>">
			<input type="hidden" name="swfLogFlowID" value="<%=request.getParameter("swfLogFlowID")%>">
			<input type="hidden" name="swfLogLogNo" value="<%=request.getParameter("swfLogLogNo")%>">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="db.view_larrearage.policyNo" />:
		</td>
		<%-- 保单号 --%>
		<td class="input" style="width: 35%">
			<input type=text name="prplCheckPolicyNo" class="readonly" readonly="true" style="width: 140px" value="<bean:write name='prpLcheckDtoTemp' property='policyNo'/>">
			<input type="button" name="btPolicyRelate" value="<s:text name='button.InsuranceInformation.value' />" class='bigbutton' onclick="relateBeforePolicyNo('<bean:write name='prpLregistDto' property='policyNo'/>','<bean:write name='prpLregistDto' property='riskCode'/>','<bean:write name='prpLregistDto' property='damageStartDate' filter='true' />');">
		</td>
		<%-- 保单信息 --%>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="check.claimNum" />:
		</td>
		<%-- 赔案号 --%>
		<td class="input" style="width: 35%">
			<input type=text name="prplCheckClaimNo" class="readonly" readonly="true" style="width: 140px" value="<bean:write name='prpLcheckDtoTemp' property='claimNo'/>">
			<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prplCheckPolicyNo.value);return false;">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="prpLregist.registNo" />:
		</td>
		<%-- 报案号 --%>
		<td class="input" style="width: 35%">
			<input type=text name="prplCheckRegistNo" class="readonly" readonly="true" style="width: 140px" value="<bean:write name='prpLcheckDtoTemp' property='registNo'/>">
			<input type="button" name="btRegistRelate" value="<s:text name='button.reportedInformation.value' />" class='bigbutton' onclick="relateRegist();return false;">
		</td>
		<%-- 报案信息 --%>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />:
		</td>
		<%-- 已出险次数: --%>
		<td class="input" style="width: 35%">
			<%-- 出险信息画面 --%>
			<%@include file="/pages/DAA/regist/DAAExistRegist.jsp"%>
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
		</td>
		<%-- 出险时间:--%>
		<td class="common" style="width: 35%">
			<input type=text name="prpLregistDamageStartDate1" class="readonly" readonly maxlength="10" style="width: 80px" value="<bean:write name='prpLregistDto' property='damageStartDate' filter='true' />">
			<s:text name="regist.prpLregist.date" />
			<%-- 日 --%>
			<input type=text name="prpLregistDamageStartHour1" class="readonly" readonly maxlength="2" style="width: 20px" value="<bean:write name='prpLregistDto' property='damageStartHour' filter='true' />">
			<s:text name="regist.prpLregist.hour" />
			<%-- 时 --%>
			<input type=text name="prpLregistDamageStartMinute1" class="readonly" readonly maxlength="2" style="width: 20px"
				value="<bean:write name='prpLregistDto' property='damageStartMinute' filter='true' />">
			<s:text name="regist.prpLregist.minute" />
			<%-- 分 --%>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
		</td>
		<%-- 出险地点: --%>
		<td class="common" style="width: 35%">
			<input type=text name="prpLregistDamageAddress" class="readonly" readonly style="width: 350px" value="<bean:write name='prpLregistDto' property='damageAddress' filter='true' />">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="check.surveyTtime" />:
		</td>
		<%-- 查勘时间 --%>
		<td class="common" style="width: 35%">
			<bean:write name='prpLcheckDtoTemp' property='checkDate' />
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="prpLcheck.checkArea" />:
		</td>
		<%-- 查勘地点 --%>
		<td class="common" style="width: 35%">
			<bean:write name='prpLcheckDtoTemp' property='checkSite' />
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.firstSite" />:
		</td>
		<%-- 是否第一现场查勘 --%>
		<td class="common" style="width: 35%">
			<logic:equal name="prpLcheckDtoTemp" property="firstSiteFlag" value="0">
				<s:text name="certainLoss.thirdCarLoss.no" />
			</logic:equal>
			<%--否 --%>
			<logic:equal name="prpLcheckDtoTemp" property="firstSiteFlag" value="1">
				<s:text name="certainLoss.thirdCarLoss.yes" />
			</logic:equal>
			<%-- 是 --%>
		</td>
		<td class="title" style="width: 15%" style="valign:bottom"></td>
		<td class="common" style="width: 35%">
			<%--resson:按钮太小--%>
			<input type="button" class='bigbutton' value="<s:text name='button.viewInformation.value' />" onclick="relateCheck();return false;">
			<%-- 查看查勘信息 --%>
			<!--<input type="button" class='button' value="查看相关损失">  -->
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.person1" />:
		</td>
		<%-- >查勘人1 --%>
		<td class="common" style="width: 35%">
			<bean:write name='prpLcheckDtoTemp' property='checker1' />
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="certainLoss.person2" />:
		</td>
		<%-- >查勘人2 --%>
		<td class="common" style="width: 35%">
			<bean:write name='prpLcheckDtoTemp' property='checker2' />
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="certainLoss.lossTime" />:
		</td>
		<%-- 定损时间 --%>
		<td class="common" style="width: 35%">
			<input name="prpLverifyLossDefLossDate" class="readonly" readonly style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='defLossDate'/>">
		</td>
		<td class="title" style="width: 15%" style="valign:bottom">
			<s:text name="certainLoss.prpLscheduleMainWF.lossPerson" />
		</td>
		<%-- 定损人员: --%>
		<td class="common" style="width: 35%">
			<input name="prpLverifyLossHandlerCode" class="readonly" readonly style="width: 40%" value="<bean:write name='prpLverifyLossDto' property='handlerCode'/>">
			<input name="prpLverifyLossHandlerName" class="readonly" readonly style="width: 45%" value="<bean:write name='prpLverifyLossDto' property='handlerName'/>">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="verifyPrice.initialFeeAmount" />:
		</td>
		<%-- 初次定损金额 --%>
		<td class="common" style="width: 35%">
			<input name="prpLverifyLossFirstDefLoss" class="readonly" readonly style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='firstDefLoss'  format='##0.00'/>">
		</td>
		<td class="title" style="width: 15%">
			<s:text name="verifyPrice.discrepancyAmount" />:
		</td>
		<%-- 偏差定损金额 --%>
		<td class="common" style="width: 35%">
			<input name="prpLverifyLossWarpDefLoss" class='readonly' readonly style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='warpDefLoss' format='##0.00'/>">
		</td>
	</tr>
</table>
<input type="hidden" name="riskcode" value="<bean:write name='prpLcheckDtoTemp' property='riskCode'/>">
<input type="hidden" name="policyno" value="<bean:write name='prpLcheckDtoTemp' property='policyNo'/>">
<input type="hidden" name="PolicyNo" value="<bean:write name='prpLcheckDtoTemp' property='policyNo'/>">
<input type="hidden" name="RegistNo" value="<bean:write name='prpLcheckDtoTemp' property='registNo'/>">
