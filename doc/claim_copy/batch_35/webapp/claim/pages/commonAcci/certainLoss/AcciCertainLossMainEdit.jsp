<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-10-13 
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<input type="button" name="message" class="bigbutton" value="<s:text name='button.composeMessage.value' />" onclick="openWinSave()">
		</td>
		<%--撰写留言--%>
		<td>
			<input type="button" name="messageView" class="bigbutton" value="<s:text name='button.viewMessage.value' />" onclick="openWinQuery()">
		</td>
		<%--查看留言--%>
	</tr>
</table>
<table border="0" align="center" cellpadding="5" cellspacing="1" class="common">
	<tr class=listtitle>
		<td colspan="4">
			<s:text name="certainLoss.lossOfRegistration" />
			<%--定损登记--%>
			<input type="hidden" name="prpLverifyLossClaimNo" value="<bean:write name='prpLverifyLossDto' property='claimNo'/>">
			<input type="hidden" name="prpLverifyLossRiskCode" value="<bean:write name='prpLverifyLossDto' property='riskCode'/>">
			<input type="hidden" name="prpLverifyLossLicenseColorcode" value="<bean:write name='prpLverifyLossDto' property='licenseColorcode'/>">
			<input type="hidden" name="prpLverifyLossCarKindCode" value="<bean:write name='prpLverifyLossDto' property='carKindCode'/>">
			<input type="hidden" name="prpLverifyLossSumPreDefLoss" value="<bean:write name='prpLverifyLossDto' property='sumPreDefLoss'/>">
			<input type="hidden" name="prpLverifyLossSumDefLoss" value="<bean:write name='prpLverifyLossDto' property='sumDefLoss'/>">
			<input type="hidden" name="prpLverifyLossMakeCom" value="<bean:write name='prpLverifyLossDto' property='makeCom'/>">
			<input type="hidden" name="prpLverifyLossComCode" value="<bean:write name='prpLverifyLossDto' property='comCode'/>">
			<input type="hidden" name="prpLverifyLossHandlerCode" value="<bean:write name='prpLverifyLossDto' property='handlerCode'/>">
			<input type="hidden" name="prpLverifyLossHandlerName" value="<bean:write name='prpLverifyLossDto' property='handlerName'/>">
			<input type="hidden" name="prpLverifyLossDefLossDate" value="<bean:write name='prpLverifyLossDto' property='defLossDate'/>">
			<input type="hidden" name="prpLverifyLossUnderWriteCode" value="<bean:write name='prpLverifyLossDto' property='underWriteCode'/>">
			<input type="hidden" name="prpLverifyLossUnderWriteName" value="<bean:write name='prpLverifyLossDto' property='underWriteName'/>">
			<input type="hidden" name="prpLverifyLossUnderWriteEndDate" value="<bean:write name='prpLverifyLossDto' property='underWriteEndDate'/>">
			<input type="hidden" name="prpLverifyLossUnderWriteFlag" value="<bean:write name='prpLverifyLossDto' property='underWriteFlag'/>">
			<input type="hidden" name="prpLverifyLossRemark" value="<bean:write name='prpLverifyLossDto' property='remark'/>">
			<input type="hidden" name="prpLverifyLossVerifyRemark" value="<bean:write name='prpLverifyLossDto' property='verifyRemark'/>">
			<input type="hidden" name="prpLverifyLossFlag" value="<bean:write name='prpLverifyLossDto' property='flag'/>">
			<input type="hidden" name="prpLverifyLossRegistNo" value="<bean:write name='prpLverifyLossDto' property='registNo'/>">
			<input type="hidden" name="prpLverifyLossPolicyNo" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='policyNo'/>">
			<input type="hidden" name="prpLverifyLossInsuredName" value="<bean:write name='prpLverifyLossDto' property='insuredName'/>">
			<input type="hidden" name="prpLverifyLossLicenseNo" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='licenseNo'/>">
			<input type="hidden" name="prpLverifyLossLicenseColor" value="<bean:write name='prpLverifyLossDto' property='licenseColor'/>">
			<input type="hidden" name="prpLverifyLossCarKind" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='carKind'/>">
			<input type="hidden" name="prpLverifyLossClauseName" value="<bean:write name='prpLverifyLossDto' property='clauseName'/>">
			<input type="hidden" name="prpLverifyLossCurrencyName" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='currencyName'/>">
			<input type="hidden" name="prpLverifyLossCurrency" value="<bean:write name='prpLverifyLossDto' property='currency'/>">
			<input type="hidden" name="swfLogFlowID" class="common" value="<%=request.getParameter("swfLogFlowID")%>">
			<input type="hidden" name="swfLogLogNo" class="common" value="<%=request.getParameter("swfLogLogNo")%>">
			<input type="hidden" name="prpLverifyLossLossItemCode" value="0">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="prpLregist.registNo " />:
		</td>
		<%--报案号--%>
		<td class="input">
			<input type="text" name="RegistNo" class="readonly" readonly="true" value="<bean:write name='prpLverifyLossDto' property='registNo'/>">
		</td>
		<td class="title" style="valign: bottom">
			<s:text name="db.view_larrearage.policyNo" />：
		</td>
		<%--保单号--%>
		<td class="input">
			<input type="hidden" name="riskcode" value="<bean:write name='prpLverifyLossDto' property='riskCode'/>">
			<input type="hidden" name="policyno" value="<bean:write name='prpLverifyLossDto' property='policyNo'/>">
			<input type=text name="PolicyNo" class="readonly" readonly="true" style="width: 140px" value="<bean:write name='prpLverifyLossDto' property='policyNo'/>">
			<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.PolicyNo.value);return false;">
		</td>
	</tr>
</table>
