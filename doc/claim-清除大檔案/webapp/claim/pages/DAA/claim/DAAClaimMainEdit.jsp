<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]
* AUTHOR     ： 中科软
* CREATEDATE ： 2013-03-11
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------

****************************************************************************
--%>
<%@ page import="com.sinosoft.claim.common.ConstantCodes"%>
<%@ include file="/common/taglibs.jsp"%>
<c:choose>
	<c:when test="${requestScope.prpLclaim.startHour==0}">
		<c:set var="startHour" value="零時起至" />
	</c:when>
	<c:when test="${requestScope.prpLclaim.startHour==12}">
		<c:set var="startHour" value="十二時起至" />
	</c:when>
	<c:when test="${requestScope.prpLclaim.startHour==24}">
		<c:set var="startHour" value="二十四時起" />
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${requestScope.prpLclaim.endHour==0}">
		<c:set var="endHour" value="十二時止" />
	</c:when>
	<c:when test="${requestScope.prpLclaim.endHour==12}">
		<c:set var="endHour" value="二十四時止" />
	</c:when>
	<c:when test="${requestScope.prpLclaim.endHour==24}">
		<c:set var="endHour" value="零時止" />
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td colspan="4">
			<c:choose>
				<c:when test="${requestScope.prpLclaim.caseType=='1'}">(已拒赔)</c:when>
				<c:when test="${requestScope.prpLclaim.caseType=='0'}">(已註銷)</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
			<input type="hidden" name="damageStartDate" value="<fmt:formatDate pattern='yyyy-MM-dd' value='${requestScope.prpLclaim.damageStartDate}'/>">
			<input type="hidden" name="damageStartHour" value="<c:out value='${requestScope.prpLclaim.damageStartHour}'/>">
			<input type="hidden" name="prpLclaimRiskCode" value="<c:out value='${requestScope.prpLclaim.riskCode}'/>">
			<input type="hidden" name="prpLclaimOperatorCode" value="<c:out value='${requestScope.prpLclaim.operatorCode}'/>">
			<input type="hidden" name="prpLclaimMakeCom" value="<c:out value='${requestScope.prpLclaim.makeCom}'/>">
			<input type="hidden" name="prpLclaimEngineNo">
			<input type="hidden" name="prpLclaimFrameNo">
			<input type="hidden" name="prpLclaimRunDistance">
			<input type="hidden" name="prpLclaimLossName" value="<c:out value='${requestScope.prpLclaim.lossName}'/>">
			<input type="hidden" name="prpLclaimSumDefLoss" value="<c:out value='${requestScope.prpLclaim.sumDefLoss}'/>">
			<input type="hidden" name="prpLclaimTypeForDriver" value="claim">
			<input type="hidden" name="prpLclaimBusinessNature" value="<c:out value='${requestScope.prpLclaim.businessNature}'/>">
			<input type="hidden" name="prpLclaimPolicyType" value="<c:out value='${requestScope.prpLclaim.policyType}'/>">
			<input type="hidden" name="coreURL" value="${core_URL }">
			<input type="hidden" name="prpLclaimIntPayFee" value="<c:out value='${payFlag}'/>">
			<c:choose>
				<c:when test="${not empty requestScope.claim_days}">
					<input type="hidden" name='claim_days' value="<c:out value='${requestScope.claim_days}'/>">
				</c:when>
				<c:otherwise>
					<input type="hidden" name='claim_days' value="1">
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${not empty requestScope.standardDays}">
					<input type="hidden" name='standardDays' value="<c:out value='${requestScope.standardDays}'/>">
				</c:when>
				<c:otherwise>
					<input type="hidden" name='standardDays' value="100">
				</c:otherwise>
			</c:choose>
			<input type="hidden" name="swfLogFlowID" value="<c:out value='${param.swfLogFlowID}'/>">
			<input type="hidden" name="swfLogLogNo" value="<c:out value='${param.swfLogLogNo}'/>">
			<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
			<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
			<input type="hidden" name="prpLclaimClassCode" value="<c:out value='${requestScope.prpLclaim.classCode}'/>">
			<input type="hidden" name="prpLclaimInputDate" value="<fmt:formatDate pattern='yyyy-MM-dd'  value='${requestScope.prpLclaim.inputDate}'/>">
			<input type="hidden" name="prpLclaimDamageEndDate" value="<fmt:formatDate pattern='yyyy-MM-dd'  value='${requestScope.prpLclaim.damageEndDate}'/>">
			<input type="hidden" name="prpLclaimDamageEndHour" value="<c:out value='${requestScope.prpLclaim.damageEndHour}'/>">
			<input type="hidden" name="prpLclaimDamageEndMinute" value="<c:out value='${requestScope.prpLclaim.damageEndMinute}'/>">
			<input type="hidden" name="prpLclaimLanguage" value="<c:out value='${requestScope.prpLclaim.language}'/>">
			<input type="hidden" name="riskcode" value="<c:out value='${requestScope.prpLclaim.riskCode}'/>">
			<input type="hidden" name="policyno" value="<c:out value='${requestScope.prpLclaim.policyNo}'/>">
			<input type="hidden" name="prpLclaimAddressCode" value="<c:out value='${requestScope.prpLclaim.addressCode}'/>">
			<input type="hidden" name="prpLclaimComCode1" value="<c:out value='${requestScope.prpLclaim.comCode}'/>">
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.claimNo" />：
					</td>
					<td class="right">
						<input type=text name="prpLclaimClaimNo" title="立案號碼" maxlength="22" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLclaim.claimNo}'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLcompensate.caseNo" />：
					</td>
					<%-- 结案号 --%>
					<td class="right">
						<input type=text name="prpLclaimCaseNo" title="結案號碼" class="readonly" readonly="true" maxlength="22" value="<c:out value='${requestScope.prpLclaim.caseNo}'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.registNo" />：
					</td>
					<td class="right">
						<input type=text name="prpLclaimRegistNo" title="備案號碼" class="readonly" readonly="true" style="width: 220px" value="<c:out value='${requestScope.prpLclaim.registNo}'/>">
						<input type="hidden" name="damageDate" value="<c:out value='${requestScope.prpLclaim.damageStartDate}'/>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.lflag" />：
					</td>
					<td class="right">
						<s:select name="lflag" value="#request.prpLclaim.lflag" list="#request.claimFlagList" listKey="key" listValue="value" style="width:30%" />
					</td>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right">
						<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value' />"
							onclick="backWardPolicy(fm.coreURL.value,fm.prpLclaimPolicyNo.value,fm.prpLclaimRiskCode.value,fm.damageDate.value,fm.prpLclaimComCode.value);">
					</td>
				</tr>
				<%-- 出险时保单信息 --%>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.policyNo" />：
					</td>
					<td class="right">
						<input type=text name="prpLclaimPolicyNo" class="readonly" readonly="true" style="width: 220px" value="<c:out value='${requestScope.prpLclaim.policyNo}'/>">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLclaimPolicyNo.value);return false;">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.insuredName" />：
					</td>
					<td class="right">
						<input type=hidden name="prpLregistInsuredCode" title="被保險人代碼" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLclaim.insuredCode}'/>">
						<input type=hidden name="prpLclaimInsuredName" title="被保險人" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLclaim.insuredName}'/>">
						<c:out value='${requestScope.prpLclaim.insuredName}' />
					</td>
					<td class="left">
						<nobr>
							<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />:
						</nobr>
						<%-- 已出险次数 --%>
					</td>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/DAA/regist/DAAExistRegist.jsp"%>
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.insuranceTime" />：
						<%-- 保险期间 --%>
					</td>
					<td class="right" colspan="5">
						<%-- <input type=text name="prpLclaimStartDate" title="起保日期" class="readonly" style="width:80px" readonly="true"  value="<fmt:formatDate pattern='yyyy-MM-dd'  value='${requestScope.prpLclaim.startDate}'/>"><c:out value="${startHour}"/> --%>
						<rc:rcDate name="prpLclaimStartDate" title="起保日期" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${requestScope.prpLclaim.startDate}" format="yyyy-MM-dd" />
						<c:out value="${startHour}" />
						<%--  <input type=text name="prpLclaimEndDate"   title="終保日期" class="readonly" style="width:80px" readonly="true"   value="<fmt:formatDate pattern='yyyy-MM-dd'  value='${requestScope.prpLclaim.endDate}'/>"><c:out value="${endHour}"/>--%>
						<rc:rcDate name="prpLclaimEndDate" title="終保日期" class="readonly" readonly="true" wdatePicker="false" style="width:80px" value="${requestScope.prpLclaim.endDate}" format="yyyy-MM-dd" />
						<c:out value="${endHour}" />
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLCItemCar.clauseType" />：
						<%-- 条款类别 --%>
					</td>
					<td class="right">
						<input class="readonly" type=text name="prpLclaimClauseType" readonly="true" style="width: 10%" value="<c:out value='${requestScope.prpLclaim.clauseType}'/>">
						<input class="readonly" type=text name="prpLclaimClauseName" readonly="true" style="width: 70%" value="<c:out value='${requestScope.prpLclaim.clauseName}'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLlawsuit.licenseNo" />：
						<%-- 号牌号码 --%>
					</td>
					<td class="right">
						<input class="readonly" name="prpLclaimLicenseNo" readonly="true" value="<c:out value='${requestScope.prpLclaim.licenseNo}'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLlawsuit.licenseColorCode" />：
						<%-- 号牌底色 --%>
					</td>
					<td class="right">
						<input class="readonly" name="prpLclaimLicenseColor" readonly="true" value="<c:out value='${requestScope.prpLclaim.licenseColor}'/>">
						<input class="readonly" type=hidden name="prpLclaimLicenseColorCode" value="<c:out value='${requestScope.prpLclaim.licenseColorCode}'/>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLlawsuit.brandName" />：
						<%-- 厂牌型号 --%>
					</td>
					<td class="right">
						<input class="readonly" name="prpLclaimBrandName" readonly="true" value="<c:out value='${requestScope.prpLclaim.brandName}'/>">
					</td>
					<td class="left">
						<s:text name="certainLoss.thirdCarLoss.carKind" />：
						<%-- 车辆种类 --%>
					</td>
					<td class="right">
						<input name="prpLclaimCarKind" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLclaim.carKind}'/>">
						<input class="readonly" type=hidden name="prpLclaimCarKindCode" value="<c:out value='${requestScope.prpLclaim.carKindCode}'/>">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.currency" />：
						<%-- 币别 --%>
					</td>
					<td class="right">
						<input class="readonly" readonly name="prpLclaimCurrencyName" value="<%=ConstantCodes.LOCAL_CURRENCYNAME%>">
						<input class="readonly" type=hidden name="prpLclaimCurrency" value="<%=ConstantCodes.LOCAL_CURRENCY%>">
						<input class="readonly" type=hidden name="prpLclaimPolicyCurrency">
					</td>
					<td class="left">
						<s:text name="db.prpLpersonloss.amount" />：
						<%-- 保险金额 --%>
					</td>
					<td class="right">
						<input class="readonly" name="prpLclaimSumAmount" readonly="true" value="<fmt:formatNumber pattern='#' value='${requestScope.prpLclaim.sumAmount}'/>">
						<input type="hidden" name="prpLclaimSumPremium" readonly="true" value="<fmt:formatNumber pattern='#' value='${requestScope.prpLclaim.sumPremium}'/>">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.damageTime" />：
						<%-- 出险时间 --%>
					</td>
					<td class="right">
					<%--mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能 Start --%>
					<c:choose>
						<c:when test="${editType == 'EDIT'}">
							<rc:rcDate name="prpLclaimDamageStartDate" title="出險日期" style="width:100px" value="${requestScope.prpLclaim.damageStartDate}" onkeypress="pressFullDate(fm.prpLregistDamageStartDate)" onblur="checkFullDate(fm.prpLclaimDamageStartDate)" onchange="" />
							<s:text name="regist.prpLregist.date" />
							<input type="text" name="prpLclaimDamageStartHour" title="出險小時" class="input" maxlength="2" style="width: 25px" value="${requestScope.prpLclaim.damageStartHour}" onchange="">
							<s:text name="regist.prpLregist.hour" />
							<input type="text" name="prpLclaimDamageStartMinute" title="出險分鐘" class="input" maxlength="2" style="width: 25px" value="${requestScope.prpLclaim.damageStartMinute}" onchange="">分 
							<img src="${ctx}/images/bgMarkMustInput.jpg">
							<input type=hidden name="prpLregistStartDate" value = "${prpLregistStartDate}" description="保單開始時間">
							<input type=hidden name="prpLregistStartHour" value = "${prpLregistStartHour}" description="保單開始小時">
							<input type=hidden name="prpLregistEndDate" value = "${prpLregistEndDate}" description="保單結束時間">
							<input type=hidden name="prpLregistEndHour" value = "${prpLregistEndHour}" description="保單結束小時">
						</c:when>
						<c:otherwise>
							<%-- <input type=text name="prpLclaimDamageStartDate" title="出險時間"
								class="readonly" readonly maxlength="10" style="width:80px"
								value="<fmt:formatDate pattern='yyyy-MM-dd' value='${requestScope.prpLclaim.damageStartDate}'/>"><s:text name="regist.prpLregist.date" />--%>
							<%-- 日 --%>
							<rc:rcDate name="prpLclaimDamageStartDate" title="出險時間" class="readonly" readonly="" wdatePicker="false" style="width:80px" value="${requestScope.prpLclaim.damageStartDate}"
								format="yyyy-MM-dd" />
							<input type=text name="prpLclaimDamageStartHour" title="出險小時" class="readonly" readonly maxlength="2" style="width: 20px" value="<c:out value='${requestScope.prpLclaim.damageStartHour}'/>">
							<s:text name="regist.prpLregist.hour" />
							<%-- 时 --%>
							<input type=text name="prpLclaimDamageStartMinute" title="出險分鐘" class="readonly" readonly maxlength="2" style="width: 20px" value="<c:out value='${requestScope.prpLclaim.damageStartMinute}'/>">
							<s:text name="regist.prpLregist.minute" />
							<%-- 分 --%>
						</c:otherwise>
						</c:choose>
						<%--mantis： CLM0197，處理人員：CD078，需求單編號：CLM0197 新核心-新增立案修改出險日期及出險地區功能 End --%>
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageName" />：
					</td>
					<c:set var="riskCodeBZ" value="<%=ConstantCodes.RISKCODE_DAZ%>" />
					<c:choose>
						<c:when test="${requestScope.prpLclaim.riskCode==riskCodeBZ}">
							<td class="right">
								<input type=text class="codecode" name="prpLclaimDamageCode" style="width: 27%" title="出險原因" value="<c:out value='${requestScope.prpLclaim.damageCode}'/>"
									ondblclick="code_CodeSelect(this, 'DamageCodeBZ','0,1','Y','Y',fm.riskcode.value);" onchange="code_CodeChange(this, 'DamageCodeBZ','0,1','Y','Y',fm.riskcode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCodeBZ','0,1','Y','Y',fm.riskcode.value);">
								<input type=text class="codecode" name="prpLclaimDamageName" title="出險原因" style="width: 48%" value="<c:out value='${requestScope.prpLclaim.damageName}'/>"
									ondblclick="code_CodeSelect(this, 'DamageCodeBZ','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeChange(this, 'DamageCodeBZ','-1,0','Y','N',fm.riskcode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCodeBZ','-1,0','Y','N',fm.riskcode.value);">
								<img src="/claim/images/bgMarkMustInput.jpg">
							</td>
						</c:when>
						<c:otherwise>
							<td class="right">
								<input type=text class="codecode" name="prpLclaimDamageCode" style="width: 27%" title="出險原因" value="<c:out value='${requestScope.prpLclaim.damageCode}'/>"
									ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);" onchange="code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);">
								<input type=text class="codecode" name="prpLclaimDamageName" title="出險原因" style="width: 48%" value="<c:out value='${requestScope.prpLclaim.damageName}'/>"
									ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeChange(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);"
									onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);">
								<img src="/claim/images/bgMarkMustInput.jpg">
							</td>
						</c:otherwise>
					</c:choose>
					<td class="left">
						<s:text name="db.prpLclaim.damageTypeName" />：
					</td>
					<td class="right">
						<input type=text name="prpLclaimDamageTypeCode" class="codecode" style="width: 27%" title="事故類型" value="<c:out value='${requestScope.prpLclaim.damageTypeCode}'/>"
							ondblclick="code_CodeSelect(this,'DamageTypeCode','0,1','Y','Y',fm.prpLclaimRiskCode.value);" onchange="code_CodeChange(this,'DamageTypeCode','0,1','Y','Y',fm.prpLclaimRiskCode.value);"
							onkeyup="code_CodeSelect(this,'DamageTypeCode','0,1','Y','Y',fm.prpLclaimRiskCode.value);">
						<input type=text name="prpLclaimDamageTypeName" class="codecode" title="事故類型" style="width: 48%" value="<c:out value='${requestScope.prpLclaim.damageTypeName}'/>"
							ondblclick="code_CodeSelect(this,'DamageTypeCode','-1,0','Y','N',fm.prpLclaimRiskCode.value);" onchange="code_CodeChange(this,'DamageTypeCode','-1,0','Y','N',fm.prpLclaimRiskCode.value);"
							onkeyup="code_CodeSelect(this,'DamageTypeCode','-1,0','Y','N',fm.prpLclaimRiskCode.value);">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="收件日期" />：
					</td>
					<td class="right">
						<%-- <input class=Wdate style="width:80%" name='prpLclaimReceiptDate' title="收件日期" onclick="WdatePicker();"/><img src="/claim/images/bgMarkMustInput.jpg">--%>
						<rc:rcDate name="prpLclaimReceiptDate" title="收件日期" style="width:187px" value="${prpLclaim.receiptDate}" format="yyyy-MM-dd HH:mm" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left" colspan="4"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.damageAreaName" />：
					</td>
					<td class="right">
						<c:choose>
							<c:when test="${param.editType=='ADD'}">
								<%
									/**取查勘登記的信息*/
								%>
								<input type=text name="prpLclaimDamageAreaCode" class="codecode" style="width: 27%" title="<s:text name="db.prpLclaim.damageAreaName" />" value="<c:out value='${requestScope.prpLclaim.damageAreaCode}'/>"
									ondblclick="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');" onchange="code_CodeChange(this, 'DamageAreaCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');">
								<input type=text name="prpLclaimDamageAreaName" class="codecode" style="width: 48%" title="<s:text name="db.prpLclaim.damageAreaName" />" value="<c:out value='${requestScope.prpLclaim.damageAreaName}'/>"
									ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'DamageAreaCode','-1,0','Y','N');"
									onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');">
								<img src="/claim/images/bgMarkMustInput.jpg">
							</c:when>
							<c:otherwise>
								<input type=text name="prpLclaimDamageAreaCode" class="codecode" style="width: 27%" title="<s:text name="db.prpLclaim.damageAreaName" />" value="<c:out value='${requestScope.prpLclaim.damageAreaCode}'/>"
									ondblclick="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');" onchange="code_CodeChange(this, 'DamageAreaCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'DamageAreaCode','0,1','Y');">
								<input type=text name="prpLclaimDamageAreaName" class="codecode" style="width: 48%" title="<s:text name="db.prpLclaim.damageAreaName" />" value="<c:out value='${requestScope.prpLclaim.damageAreaName}'/>"
									ondblclick="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'DamageAreaCode','-1,0','Y','N');"
									onkeyup="code_CodeSelect(this, 'DamageAreaCode','-1,0','Y','N');">
								<img src="/claim/images/bgMarkMustInput.jpg">
							</c:otherwise>
						</c:choose>
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageAddress" />：
					</td>
					<td class="right" colspan="3">
						<input type="hidden" name="prpLclaimDamageAddressType" value="<c:out value='${requestScope.prpLclaim.damageAddressType}'/>">
						<input type=text name="prpLclaimDamageAddress" title="出險地" class="input" style="width: 92%" value="<c:out value='${requestScope.prpLclaim.damageAddress}'/>">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.claimDate" />：
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimReportDate" description="报案日期">
						<input type=hidden name="prpLclaimToday" description="当前日期">
						<%-- <input class="readonly" name="prpLclaimClaimDate" description="立案日期" readonly
							value="<fmt:formatDate pattern='yyyy-MM-dd' value='${requestScope.prpLclaim.claimDate}'/>">
						<img src="/claim/images/bgMarkMustInput.jpg">
						--%>
						<rc:rcDate name="prpLclaimClaimDate" title="立案日期" style="width:187px" value="${requestScope.prpLclaim.claimDate}" format="yyyy-MM-dd HH:mm:ss" readonly="true" wdatePicker="false" class="readonly"/>
					</td>
					<td class="left" colspan="2">
						<s:text name="claim.otherClaimsInterm" />：&nbsp;
						<%-- 是否有其他理赔中介机构 --%>
						<s:set name="flagYes" value="%{getText('certainLoss.prpLscheduleMainWF.yes')}"></s:set>
						<s:set name="flagNo" value="%{getText('certainLoss.prpLscheduleMainWF.no')}"></s:set>
						<s:select list='#{"0":#flagNo,"1":#flagYes}' value="#request.prpLclaim.thirdComFlag" name="thirdComFlag" />
						<%--0- 否 --%>
						<%--1-是 --%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="claim.possibleRec" />：
						<%-- 是否可能有追偿 --%>
					</td>
					<td class="right">
						<s:select list='#{"0":#flagNo,"1":#flagYes}' value="#request.prpLclaim.replevyFlag" name="replevyFlag" />
					</td>
					<td class="left">
						<s:text name="claim.recoverAge" />：
						<%-- 追偿时效 --%>
					</td>
					<td class="right">
						<c:choose>
							<c:when test="${param.editType =='ADD' || param.editType == 'EDIT'}">
								<%-- <input type=text name="ReplevyLimitDate" class="Wdate"
							value="<fmt:formatDate pattern='yyyy-MM-dd' value="${requestScope.prpLclaim.replevyLimitDate}" />"
							description="结束日期" onkeypress="return pressFullDate(event);" onClick="WdatePicker()">
							--%>
								<rc:rcDate name="ReplevyLimitDate" title="結束日期" value="${requestScope.prpLclaim.replevyLimitDate}" />
							</c:when>
							<c:otherwise>
								<%--  <input type=text name="ReplevyLimitDate" class="Wdate"
							value="<fmt:formatDate pattern='yyyy-MM-dd' value="${requestScope.prpLclaim.replevyLimitDate}" />"
							description="结束日期" onkeypress="return pressFullDate(event);" onClick="WdatePicker()">
							--%>
								<rc:rcDate name="ReplevyLimitDate" title="結束日期" value="${requestScope.prpLclaim.replevyLimitDate}" />
							</c:otherwise>
						</c:choose>
					</td>
					<td class="left">
						<s:text name="certainLoss.thirdCarLoss.indemnityDuty" />：
						<%-- 事故责任 --%>
					</td>
					<td class="right">
						<s:select name="indemnityDuty" value="#request.prpLclaim.indemnityDuty" list="#request.indemnityDutys" listKey="id.codeCode" listValue="codeCName" onchange="changeIndemnityDuty();sumClaimNew();" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						追償說明：
						<%-- 是否可能有追偿 --%>
					</td>
					<td class="right" colspan="3">
						<input name="prpLclaimReplevyRemark" class="common" value="${prpLclaim.replevyRemark }">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.thirdCarLoss.prpLthirdPartyDutyPercent" />
						<%-- 责任比例 --%>
					</td>
					<td class="right">
						<input class="common" style="width: 50%" name='prpLclaimIndemnityDutyRate' description="责任比例" readonly="true" value="<c:out value='${requestScope.prpLclaim.indemnityDutyRate}'/>"
							onchange="sumClaimNew(this);" onfocus="cacheData(this);">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.sumClaim" />：
						<%-- 保险损失金额 --%>
					</td>
					<td class="right">
						<input type="hidden" name="prpLclaimSumClaim" value="<fmt:formatNumber pattern='#' value='${requestScope.prpLclaim.sumClaim}'/>">
						<input name="prpLclaimSumClaim1" class="readonly" readonly value="0">
					</td>
					<td class="left">
						<s:text name="claim.amountLiability" />：
						<%-- 责任估损金额 --%>
					</td>
					<td class="right">
						<input class="readonly" style="width: 80%" name="prpLclaimDutySumClaim" readonly="true" value="<fmt:formatNumber pattern='#' value='${requestScope.prpLclaim.sumClaim}'/>">
						<input type="hidden" name="prpLclaimLossExchRate">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.escapeFlag" />：
						<%-- 賠案類別 --%>
					</td>
					<td class="right">
						<s:select name="escapeFlag" value="#request.prpLclaim.escapeFlag" list="#request.escapeFlags" listKey="id.codeCode" listValue="codeCName"></s:select>
					</td>
					<td class="left" colspan="2">
						<s:set var="noTotalLoss" value="%{getText('claim.noTotalLoss')}" scope="request" />
						<s:set var="totalLoss" value="%{getText('claim.totalLoss')}" scope="request" />
						<s:set var="glassAloneBro" value="%{getText('claim.glassAloneBro')}" scope="request" />
						<s:radio list="#{'N':#request.noTotalLoss, 'Y':#request.totalLoss}" value="#request.prpLclaim.escapeFlag2" name="escapeFlag2" />
						<%-- 非全损 --%>
						<%-- 全损 --%>
					</td>
					<!-- mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 START-->
					<c:if test="${request.isCompulsoryBchainClaimDisabled}">
					<td class="left"></td>
					<td class="right"></td>
					</c:if>
					<c:if test="${!request.isCompulsoryBchainClaimDisabled}">
					<td class="left">是否為強制險區塊鏈攤賠案件：</td>
					<td class="right"><s:radio list="#{'N':'否','Y':'是'}" name="prpLregistIsCompulsoryBchainClaim" value="#request.prpLregist.isCompulsoryBchainClaim"/></td>
					</c:if>
					<!-- mantis：CLM0257，處理人員：DP0713，需求單編號：新核心-備案任務處理，新增[是否為強制險區塊鏈攤賠案件]選項 END-->
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.comCode" />：
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimComCode" value="<c:out value='${requestScope.prpLclaim.comCode}'/>">
						<input type=hidden name="prpLclaimComCode" title="業務歸屬機構" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLclaim.comCode}'/>">
						<input type=text name="prpLclaimComName" title="業務歸屬機構" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLclaim.comName}'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.handler1Code" />：
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimHandler1Code" value="<c:out value='${requestScope.prpLclaim.handler1Code}'/>">
						<input type=text name="prpLclaimHandler1Name" title="歸屬業務員" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLclaim.handler1Name}'/>">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.agentCode" />：
					</td>
					<td class="right">
						<input class="readonly" readonly name="prpLclaimAgentCode" title="代理人" value="<c:out value='${requestScope.prpLclaim.agentCode}'/>">
						<input class="readonly" readonly name="prpLclaimAgentName" title="代理人" value="<c:out value='${requestScope.prpLclaim.agentName}'/>">
					</td>
					<td class="left">
						<s:text name="undwrt.Handlers" />：
					</td>
					<td class="right">
						<input name="prpLclaimHandlerCode" class="codecode" style="width: 50%" value="<c:out value='${requestScope.prpLclaim.handlerCode}'/>" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y','Y','ALL');"
							onchange="code_CodeChange(this, 'handerCode','0,1','Y','Y','ALL');" onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y','Y','ALL');">
						<input name="prpLclaimHandlerName" class="codename" style="width: 45%" title="經辦人" value="<c:out value='${requestScope.prpLclaim.handlerName}'/>"
							ondblclick="code_CodeSelect(this,'handerCode','-1,0','Y','N','ALL');" onchange="code_CodeChange(this,'handerCode','-1,0','Y','N','ALL');" onkeyup="code_CodeSelect(this,'handerCode','-1,0','Y','N','ALL');">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>