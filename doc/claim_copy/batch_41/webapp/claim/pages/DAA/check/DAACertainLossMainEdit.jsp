<%--
****************************************************************************
* DESC       ：添加主信息子塊界面頁面
* AUTHOR     ： 中科软
* CREATEDATE ： 2004-07-13   
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title" style="width: 100%">
	<tr>
		<td class="title" colspan="4" style="width: 100%">
			<s:text name="certainLoss.lossOfRegistration" />
			<%-- 定损登记 --%>
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
			<input type="hidden" name="prpLverifyLossVerifyRemark" value="<bean:write name='prpLverifyLossDto' property='verifyRemark'/>">
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
	<logic:notEqual name="prpLverifyLossDto" property="lossItemCode" value="0">
		<tr>
			<td class="title" style="width: 15%">
				<s:text name="check.feeTime" />:
			</td>
			<%-- 定损时间 --%>
			<td class="common" style="width: 35%">
				<input name="prpLverifyLossDefLossDate" class="input" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='defLossDate'/>">
			</td>
			<td class="title" style="width: 15%" style="valign:bottom">
				<s:text name="certainLoss.prpLscheduleMainWF.lossPerson" />:
			</td>
			<%-- 定损人员 --%>
			<td class="common" style="width: 35%">
				<input name="prpLverifyLossHandlerCode" class="codecode" style="width: 40%" value="<bean:write name='prpLverifyLossDto' property='handlerCode'/>">
				<input name="prpLverifyLossHandlerName" class='codename' maxlength=60 style="width: 45%" value="<bean:write name='prpLverifyLossDto' property='handlerName'/>">
			</td>
		</tr>
		<tr>
	</logic:notEqual>
	<tr>
		<input type="hidden" name="prpLverifyLossFirstDefLoss" value="<bean:write name='prpLverifyLossDto' property='firstDefLoss'  format='##0.00'/>">
		<input type="hidden" name="prpLverifyLossWarpDefLoss" value="<bean:write name='prpLverifyLossDto' property='warpDefLoss' format='##0.00'/>">
</table>
<input type="hidden" name="PolicyNo" value="<bean:write name='prpLcheckDtoTemp' property='policyNo'/>">
<input type="hidden" name="RegistNo" value="<bean:write name='prpLcheckDtoTemp' property='registNo'/>">
