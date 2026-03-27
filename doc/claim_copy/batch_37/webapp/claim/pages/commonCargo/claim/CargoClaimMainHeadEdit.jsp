<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]（非车险）
* AUTHOR     ： 理赔组
* CREATEDATE ： 2014-04-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:if test="#request.prpLclaim.startHour == 0">
	<s:set var="startHour" value="%{getText('claim.prpLclaim.startHour.zero')}" scope="page"></s:set><%-- 零时 --%>
</s:if>
<s:elseif test="#request.prpLclaim.startHour == 12">
	<s:set var="startHour" value="%{getText('claim.prpLclaim.startHour.twelve')}"></s:set><%-- 十二时 --%>
</s:elseif>
<s:elseif test="#request.prpLclaim.startHour == 24">
	<s:set var="startHour" value="%{getText('claim.prpLclaim.startHour.twentyFour')}"></s:set><%-- 二十四时 --%>
</s:elseif>

<s:if test="#request.prpLclaim.endHour == 0">
	<s:set var="endHour" value="%{getText('regist.until')}" scope="page"></s:set><%-- 零时止 --%>
</s:if>
<s:elseif test="#request.prpLclaim.endHour == 12">
	<s:set var="endHour" value="%{getText('regist.end')}" scope="page"></s:set><%-- 十二时止 --%>
</s:elseif>
<s:elseif test="#request.prpLclaim.endHour == 24">
	<s:set var="endHour" value="%{getText('modifySumClaim.hourEnd')}" scope="page"></s:set><%-- 二十四时止 --%>
</s:elseif>

<table class=subtable cellpadding="0" cellspacing="1" >
	<tr>
		<td> 
		<table class="common"  cellpadding="1" cellspacing="1">
			<tr> 
				<td>
					<c:if test="${prpLclaim.caseType == '1'}">
						<s:text name="commonAcci.claim.rejectClaim"/> <%-- （已拒赔）  --%>
					</c:if>
					<c:if test="${prpLclaim.caseType == '0'}">
						<s:text name="commonAcci.claim.cancelled"/>  <%-- （已注销） --%>
					</c:if>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<input type="hidden" name="prpLclaimRiskCode" value="${prpLclaim.riskCode}">
						<input type="hidden" name="prpLclaimOperatorCode" value="${prpLclaim.operatorCode}">
						<input type="hidden" name="prpLclaimMakeCom" value="${prpLclaim.makeCom}">
						<input type="hidden" name="prpLclaimEngineNo">
						<input type="hidden" name="prpLclaimFrameNo">
						<input type="hidden" name="prpLclaimRunDistance">
						<%--<input type="hidden" name="prpLclaimLossName" value="<bean:write name='prpLclaimDto' property='lossName' />">--%>
						<input type="hidden" name="prpLclaimSumDefLoss" value="${prpLclaim.sumDefLoss}">
						<input type="hidden" name="prpLclaimTypeForDriver" value="claim">
						<input type="hidden" name="prpLclaimPolicyType" value="${prpLclaim.policyType}">
						<input type="hidden" name="prpLclaimEscapeFlag" value="${prpLclaim.escapeFlag}">
						<input type="hidden" name="prpLclaimClassCode" value="${prpLclaim.classCode}">
						<input type="hidden" name="prpLclaimInputDate" value="${prpLclaim.inputDate}">
						<input type="hidden" name="prpLclaimDamageEndDate" value="${prpLclaim.damageEndDate}">
						<input type="hidden" name="prpLclaimDamageEndHour" value="${prpLclaim.damageEndHour}">
						<input type="hidden" name="prpLclaimDamageEndMinute" value="${prpLclaim.damageEndMinute}">
						<input type="hidden" name="prpLclaimClauseType" readonly="true" style="width: 30px" value="${prpLclaim.clauseType}">
						<input type="hidden" name="prpLclaimClauseName" readonly="true" style="width: 300px" value="${prpLclaim.clauseName}">
						<input type="hidden" name="prpLclaimAddressCode" title="<s:text name="db.prpLregist.addressCode"/>" class="input" style="width: 80px" value="${prpLclaim.addressCode}">
						<!-- 格式不正确导致JS取不到数，从而结果错误 -->
						<!--
							<input type="hidden" name="prpLclaimDamageAddressType" title="出险地" class="codecode" style="width:90px" value="<bean:write name='prpLclaimDto' property='damageAddressType' filter='true' />"
							<input type="hidden" name="prpLclaimDamageTypeCode" class="codecode"  style="width:15%" title="事故原因" value="<bean:write name='prpLclaimDto' property='damageTypeCode' filter='true' />"
							<input type="hidden" name="prpLclaimDamageAreaCode" class="codecode" style="width:15%" title="出险網域" value="<bean:write name='prpLclaimDto' property='damageAreaCode' filter='true' />"
						-->
						<input type="hidden" name='payFee' value="${payFlag}">
						<input type="hidden" name='BaseCurrency1' value="${prpDexch.baseCurrency}">
						<input type="hidden" name='ExchRate1' value="${prpDexch.exchRate}">
						<input type="hidden" name='delinquentfeeCase' value="${delinquentfeeCase}">
						<input type="hidden" name="coreURL" value="${coreURL}">
						<input type="hidden" name="prpLclaimDamageAddressType" title="<s:text name="db.prpLclaim.damageAddress"/>" class="codecode" style="width: 90px" value="${prpLclaim.damageAddressType}">
						<input type="hidden" name="prpLclaimDamageTypeCode" class="codecode" style="width: 15%" title="<s:text name="db.prpLregist.damageTypeCode"/>" value="${prpLclaim.damageTypeCode}">
						<input type="hidden" name="prpLclaimDamageAreaCode" class="codecode" style="width: 15%" title="<s:text name="db.prpLregist.damageAreaCode"/>" value="${prpLclaim.damageAreaCode}">
						<input type="hidden" name="riskcode" value="${prpLclaim.riskCode}">
						<input type="hidden" name="policyno" value="${prpLclaim.policyNo}">
						<input type="hidden" name="registno" value="${prpLclaim.registNo}">
						<input type="hidden" name="prpLclaimLanguage" title="<s:text name="db.prpLclaim.language"/>" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.language}">
						<input type="hidden" name="language" title="<s:text name="db.prpLclaim.language"/>" value="${prpLclaim.language}' filter='true' />">
						<input type="hidden" name="swfLogFlowID" class="common" value="${param.swfLogFlowID}">
						<input type="hidden" name="swfLogLogNo" class="common" value="${param.swfLogLogNo}">
						<input type="hidden" name="prpLclaimOthFlag" value="${prpLclaim.othFlag}">
						<input type="hidden" name="underWriteEndDate" value="${prpLclaim.underWriteEndDate}">
						<input type="hidden" name="prpLclaimReportDate1" value="${registDate}">
						<input type="hidden" name="damageStartDate" value="${prpLclaim.damageStartDate}">
						<input type="hidden" name="damageStartHour" value="${prpLclaim.damageStartHour}">
						<!-- 联共保和股东信息-->
						<c:if test="${not empty coinsFlag}">
							<input type="hidden" name="coinsFlag" value="${coinsFlag}">
						</c:if>
						<c:if test="${empty coinsFlag}">
							<input type="hidden" name="coinsFlag" value="0">
						</c:if>
						<c:if test="${not empty shareHolderFlag}">
							<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
						</c:if>
						<c:if test="${empty shareHolderFlag}">
							<input type="hidden" name="shareHolderFlag" value="0">
						</c:if>
						<c:if test="${not empty tempReinsFlag}">
							<input type="hidden" name="tempReinsFlag" value="${tempReinsFlag}">
						</c:if>
						<c:if test="${empty tempReinsFlag}">
							<input type="hidden" name="tempReinsFlag" value="0">
						</c:if>
						<%-- 
						<input type="hidden" name="prpLclaimDamageEndHour" value="<bean:write name='prpLclaimDto' property='damageEndHour' />">
						<input type="hidden" name="prpLclaimCurClaimDate" value="<bean:write name='prpLclaimDto' property='curClaimDate' />">		
						--%>
						<%--立案天数 add by qinyongli--%>
						<logic:notEmpty name="claim_days">
							<input type="hidden" name='claim_days' value="<bean:write name='claim_days' />">
						</logic:notEmpty>
						<logic:empty name="claim_days">
							<input type="hidden" name='claim_days' value="1">
						</logic:empty>
						<logic:notEmpty name="standardDays">
							<input type="hidden" name='standardDays' value="<bean:write name='standardDays' />">
						</logic:notEmpty>
						<logic:empty name="standardDays">
							<input type="hidden" name='standardDays' value="100">
						</logic:empty>
						<s:text name="query.xianzhongName" />
						<%-- 险种名称 --%>
					</td>
					<td class="right">${requestScope.riskCName}</td>
					<td class="left">
						<s:text name="db.prpLclaim.claimNo" />
					</td>
					<td class="right">
						<input type=text name="prpLclaimClaimNo" title="<s:text name="db.prpLperson.claimNo"/>" maxlength="22" class="readonly" readonly="true" value="${prpLclaim.claimNo}"><%-- 立案号码 --%>
					</td>
					<td class="left">
						<s:text name="db.prpLcompensate.caseNo" />
					</td>
					<%-- 结案号 --%>
					<td class="right">
						<input type=text name="prpLclaimCaseNo" title="<s:text name="db.prpLcompensate.caseNo"/>" class="readonly" readonly="true" maxlength="22" style="width: 140px" value="${prpLclaim.caseNo}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<%--备案号码--%>
						<s:text name="db.prpLclaim.registNo" />
					</td>
					<td class="right" colspan="3">
						<input type=text name="prpLclaimRegistNo" title="<s:text name="db.prpLcomponent.registNo"/>" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.registNo}">
						<input type="hidden" name="damageDate" value="${prpLclaim.damageStartDate}">
						<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value'/>"
							onclick="backWardPolicy(fm.coreURL.value,fm.prpLclaimPolicyNo.value,fm.prpLclaimRiskCode.value,fm.damageDate.value,fm.prpLclaimComCode.value);">
						<%-- 出险时保单信息 --%>
					</td>
					<td class="left">
						<div style="display: none">
							<s:text name="db.prpLclaim.lflag" />
						</div>
					</td>
					<td class="right">
						<div style="display: none">
							<s:select name="lflag" listKey="key" listValue="value" list="#request.claimFlagList" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="left">
						<%-- 保单号 --%>
						<s:text name="db.prpLclaim.policyNo" />
					</td>
					<td class="right">
						<input type=text name="prpLclaimPolicyNo" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.policyNo}">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLclaimPolicyNo.value);return false;">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<%--  已出险次数--%>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/common/regist/ExistRegist.jsp"%>
					</td>
					<td class="left">
						<s:text name="db.prpLCMain.businessNature" />
					</td>
					<%--  业务来源--%>
					<td class="right">
						<input type="hidden" name="prpLclaimBusinessNature" value="${prpLclaim.businessNature}">
						<input type=text name="prpLclaimBusinessNatureName" title="<s:text name="db.prpLCMain.businessNature " />" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.businessNatureName}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.insuredName" />
					</td>
					<td class="right">
						<logic:equal name='prpLclaimDto' property="customerType" value="1">
							<!---<a href='/claim/processPrpDcustomerIdv.do?actionType=prepareUpdate&prpDcustomerIdvCustomerCode=<bean:write name='prpLclaimDto' property='insuredCode' filter='true' />' >--->
						</logic:equal>
						<logic:equal name='prpLclaimDto' property="customerType" value="2">
							<!---<a href='/claim/processPrpDcustomerUnit.do?actionType=prepareUpdate&prpDcustomerUnitCustomerCode=<bean:write name='prpLclaimDto' property='insuredCode' filter='true' />'>--->
						</logic:equal>
						<input type=hidden name="prpLregistInsuredCode" title="<s:text name="db.prpLclaim.insuredCode" />" class="readonly" readonly="true" value="${prpLclaim.insuredCode}">
						<input type=hidden name="prpLclaimInsuredName" title="<s:text name="db.prpLclaim.insuredName" />" class="readonly" readonly="true" value="${prpLclaim.insuredName}">
						${prpLclaim.insuredName}
						<!-- /a-->
					</td>
					<td class="left">
						<s:text name="check.shipName" />
					</td>
					<%-- 货主名称 --%>
					<td class="right">
						<input type=text name="prpLextSalvor" title="<s:text name="check.shipName" />" value="${prpLext.salvor}">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="db.prpCcargoDetail.startDate" />
					</td>
					<%--起运日期  --%>
					<td class="right">
						<rc:rcDate name="prpLclaimStartDate" class="readonly" wdatePicker="false" value="${prpLclaim.startDate}" title="<s:text name='db.prpCcargoDetail.startDate'/>" onchange="isValidateDateField(this,schemaColumn)" />
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.dischargeDate" />
					</td>
					<%-- 卸货日期 --%>
					<td class="right">
						<input type=text name="prpLextUnloadDate" title="<s:text name="check.dischargeDate" />" class="readonly" readonly="true" value="">
						<rc:rcDate name="prpLextUnloadDate" class="readonly" wdatePicker="false" value="${prpLext.unloadDate}" title="<s:text name='check.dischargeDate'/>" />
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.currency" />
					</td>
					<%-- 币别 --%>
					<td class="right">
						<input class="readonly" name="claimCurrency" value="${prpLclaim.estiCurrency}-${strCurrencyName}">
						<input class="readonly" type=hidden name="prpLclaimCurrency" value="${prpLclaim.estiCurrency}">
						<input class="readonly" type=hidden name="prpLclaimPolicyCurrency">
					</td>
					<td class="left">
						<s:text name="db.prpLloss.amount" />
					</td>
					<%-- 保险金额 --%>
					<td class="right">
						<input class="readonly" name="prpLclaimSumAmount" readonly="true" value="<fmt:formatNumber pattern='#' value='${requestScope.prpLclaim.sumAmount}'/>">
						<input type="hidden" name="prpLclaimSumPremium" readonly="true" value="<fmt:formatNumber pattern='#' value='${requestScope.prpLclaim.sumAmount}'/>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaimagent.conveyance" />
					</td>
					<%-- 运输方式 --%>
					<td class="right">
						<bean:write name='prpCmainCargoDto' property='conveyance' />
					</td>
					<td class="left">
						<s:text name="db.prpCmain_cargo.conveyance" />
					</td>
					<%--装载运输工具  --%>
					<td class="right">
						<bean:write name='prpCmainCargoDto' property='BLNo' />
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.damageTime" />
					</td>
					<%-- 出险时间 --%>
					<td class="right">
						<rc:rcDate name="prpLclaimDamageStartDate" wdatePicker="true" value="${prpLclaim.damageStartDate}" title="<s:text name='general.damageStartDate'/>" onchange="isValidateDateField(this,schemaColumn)" />
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageName" />
					</td>
					<td class="right">
						<input type=hidden class="codecode" name="prpLclaimDamageCode" title="<s:text name="prpLcheck.damageCode" />" value="${prpLclaim.damageCode}" ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);">
						<input type=text class="codecode" name="prpLclaimDamageName" title="<s:text name="prpLcheck.damageCode" />" value="${prpLclaim.damageName}" ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="right" colspan="2"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.damageAddress" />
					</td>
					<%-- 出险地点 --%>
					<td class="right" colspan="5">
						<select name="countryFlag" style="width: 100px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
							<option value="0">
								<s:text name="commonAcci.claim.domestic" />
							</option>
							<%-- 国内 --%>
							<option value="1">
								<s:text name="commonAcci.claim.abroad" />
							</option>
							<%-- 国外 --%>
						</select>
						<input type=text class="codecode" name="foreignCountryCode" style="display: none" />
						<input type=text class="codecode" name="foreignCountryName" style="display: none" title="<s:text name="common.select.country" />" style="width:120px"
							ondblclick="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();" onkeyup="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"
							onchange="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();" /><%-- 选择国家 --%>
						<input type=text class="codecode" name="portCode" style="display: none" />
						<input type=text class="codecode" name="portCName" title="<s:text name="common.select.port" />" style="width: 120px" ondblclick="code_CodeSelect(this, 'portCode','-1,0','Y','N');"
							onkeyup="code_CodeSelect(this, 'portCode','-1,0','Y','N');" onchange="code_CodeSelect(this, 'portCode','-1,0','Y','N');" /><%-- 选择港口 --%>
						<input type=text name="prpLclaimDamageAddress" title="<s:text name="db.prpLclaim.damageAddress" />" style="width: 350px" value="${prpLclaim.damageAddress}" onclick="showPort(this);" />
						<img src="/claim/images/bgMarkMustInput.jpg"><%-- 出险地点 --%>
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="claim.otherClaimsInterm" />
					</td>
					<%-- 是否有其他理赔中介机构 --%>
					<td class="right">
						<select name="thirdComFlag">
							<option value="0">
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1">
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
						</select>
					</td>
					<td class="left">
						<s:text name="claim.possibleRec" />
					</td>
					<%--  是否可能有追偿 --%>
					<td class="right">
						<select name="replevyFlag">
							<option value="0">
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1">
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
						</select>
					</td>
					<td class="left">
						<s:text name="claim.recoverAge" />
					</td>
					<%-- 追偿时效 --%>
					<td class="right">
						<rc:rcDate name="ReplevyLimitDate" class="query" wdatePicker="true" value="${prpLclaim.replevyLimitDate}" title="<s:text name='common.check.endDate'/>" onkeypress="return pressFullDate(event);" />
						<s:if test="#request.editType =='ADD' || #request.editType =='ADD'">
							<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif"
								onclick="TogglePopupCalendarWindow('document.fm.ReplevyLimitDate', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() - 15%>', '<%=new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY).getYear() + 2%>')">
						</s:if>
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certify.whetherInsure" />
					</td>
					<%-- 是否涉及担保 --%>
					<td class="right">
						<s:if test="#request.prpLclaim.guaranteeFlag == '' || #request.prpLclaim.guaranteeFlag == '0' || #request.prpLclaim.guaranteeFlag == '1'">
							<select name="guaranteeFlag">
								<option value="0">
									<s:text name="certainLoss.thirdCarLoss.no" />
								</option>
								<%--否--%>
								<option value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
							</select>
						</s:if>
						<s:else>
							<select name="guaranteeFlag" disabled="true">
								<option value="1">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="2">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="3">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="4">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="5">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
								<option value="6">
									<s:text name="certainLoss.thirdCarLoss.yes" />
								</option>
							</select>
						</s:else>
					</td>
					<td class="left">
						<s:text name="commonAcci.claim.involvedLitigat" />
					</td>
					<%--  是否涉及诉讼--%>
					<td class="right">
						<select name="referLawFlag">
							<option value="0">
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%--否--%>
							<option value="1">
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
						</select>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br> 