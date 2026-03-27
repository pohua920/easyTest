<%--
****************************************************************************
* DESC       :添加主信息子块界面页面Head[ 实赔 ]
* AUTHOR     :中科软
* CREATEDATE :2012-02-18
* MODIFYLIST :  Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ include file="/common/taglibs.jsp"%>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td colspan="4"></td>
	</tr>
</table>
<input type="hidden" name="editType" value="${param.editType}">
<input type="hidden" name="damageStartDate" value="${prpLClaim.damageStartDate}">
<input type="hidden" name="damageStartHour" value="${prpLClaim.damageStartHour}">
<input type="hidden" name="prpLcompensateRegistNo" value="${prpLClaim.registNo}">
<input type="hidden" name="prpLregistExtRegistNo" value="${prpLClaim.registNo}">
<input type="hidden" name="prpLcompensateRiskCode" value="${prpLClaim.riskCode}">
<input type="hidden" name="riskCode" value="${prpLClaim.riskCode}">
<input type="hidden" name="swfLogFlowID" value="<c:out value='${param.swfLogFlowID}'/>">
<input type="hidden" name="swfLogLogNo" value="<c:out value='${param.swfLogLogNo}'/>">
<table class=common cellpadding="1" cellspacing="1">
	<tr>
		<td class="left"><s:text name='compensate.computeBookNum' />：<%-- 计算书号 --%></td>
		<td class="right">
			<input class="readonly" type="text" name="prpLcompensateCompensateNo" readonly="true" value="${prpLcompensate.compensateNo}">
		</td>
		<td class="left">
			<s:text name='check.claimNum' />：
		</td>
		<%-- 賠案號碼 --%>
		<td class="right">
			<input type="text" name="prpLcompensateClaimNo" value="${prpLClaim.claimNo}" title="<s:text name='check.claimNum'/>"  class="readonly" readonly="true" >
		</td>
		<td class="left"><s:text name='prompt.queRegist.PolicyNo' />：<%-- 保單號碼 --%></td>
		<td class="right">
			<input class="readonly" type="text" name="prpLcompensatePolicyNo" readonly="true" value="${prpLClaim.policyNo}">
		</td>
	</tr>
	<tr>
		<td class="left">
			<s:text name='prplremnant.comName' />：
		</td>
		<%-- 理賠單位 --%>
		<td class="right">
			<input class="readonly" type="hidden" name="prpLcompensateComName" readonly="true" value="${prpLClaim.comName}">
			<input class="readonly" type="text" name="prpLcompensateComName" readonly="true" value="${prpLClaim.comName}">
		</td>
		<td class="left">
			<s:text name='prplremnant.remnantDate' />：
		</td>
		<%-- 理賠確認日 --%>
		<td class="right">
			<rc:rcDate name="prpLcompensateRemnantDate" class="readonly" readonly="true" wdatePicker="false" style="width: 90%" value="${remnantDto.nowDate}" />
		</td>
		<td class="left">
		</td>
		<td class="right">
		</td>
	</tr>
	<tr>
		<td class="left">
			<s:text name='prplremnant.remnantCode' />：
		</td>
		<%-- 標的號碼 --%>
		<td class="right">
			<input type="text" name="prpLcompensateRemnantCode" style="width: 100%" class="readonly" readonly="true" value="${remnantDto.itemNo}">
		</td>
		<td class="left">
			<s:text name="regist.prpLregist.insuranceTime" />：
		</td>
		<td class="right" colspan="3">
			<c:set var="title"><s:text name='db.prpCmain.startDate'/></c:set>
			<rc:rcDate name="prpLcompensateStartDate" title="${title}" class="readonly" readonly="true" wdatePicker="false" style="width: 15%" value="${prpLClaim.startDate}" />
			<c:if test="${prpLClaim.startHour == '12'}">
				<s:text name='regist.from' />
			</c:if>
			<%--十二時至--%>
			<c:if test="${prpLClaim.startHour == '24'}">
				<s:text name='regist.start' />
			</c:if>
			<%--二十四時起--%>
			<c:if test="${prpLClaim.startHour == '0'}">
				<s:text name='modifySumClaim.comeEffect' />
			</c:if>
			<%--零时起至--%>
			<c:set var="title"><s:text name='db.prpCmain.endDate'/></c:set>
			<rc:rcDate name="prpLcompensateEndDate" title="${title}" class="readonly" readonly="true" wdatePicker="false" style="width: 15%" value="${prpLClaim.endDate}" />
			<c:if test="${prpLClaim.endHour == '12'}">
				<s:text name='regist.end' />
			</c:if>
			<%--十二時止--%>
			<c:if test="${prpLClaim.endHour == '24'}">
				<s:text name='modifySumClaim.hourEnd' />
			</c:if>
			<%-- 二十四時止 --%>
			<c:if test="${prpLClaim.endHour == '0'}">
				<s:text name='regist.until' />
			</c:if>
			<%-- 零時止 --%>
		</td>
	</tr>
	<tr>
		<td class="left">
			<s:text name='db.prpCmain.insuredName' />：
		</td>
		<%-- 被保險人 --%>
		<td class="right">
			<input type="text" name="prpLcompensateInsuredName" title="<s:text name='db.prpCmain.insuredName'/>"  class="readonly" readonly="true" value="${prpLClaim.insuredName}">
		</td>
		<td class="left">
			<s:text name='db.prpDration.amount' />：
		</td>
		<%-- 保額 --%>
		<td class="right">
			<input class="readonly" type="text" name="prpLcompensateSumAmount" readonly="true" value="<fmt:formatNumber value='${prpCmain.sumAmount}' pattern='#'/>">
		</td>
		<td class="left">
			<s:text name='prplremnant.sumPaid' />：
		</td>
		<%-- 賠付總額 --%>
		<td class="right">
			<input type="text" name="prpLcompensateSumPaid" style="width: 100%" class="readonly" readonly="true" value="<fmt:formatNumber value='${remnantDto.sumPaid}' pattern='#'/>">
		</td>
	</tr>
	<tr>
		<td class="left">
			<s:text name='db.prpCitem_car.licenseNo' />：
		</td>
		<%-- 牌照號碼 --%>
		<td class="right">
			<input class="readonly" type="text" name="prpLcompensateLicenseNo" readonly="true" value="${prpCitemCar.licenseNo}">
		</td>
		<td class="left">
			<s:text name='certainLoss.thirdCarLoss.carKind' />：
		</td>
		<%-- 車輛種類 --%>
		<td class="right">
			<select name="prpLcompensateCarKindCode" style="width: 90%" disabled="disabled">
				<c:forEach items="${requestScope.carKindCodes}" var="prpDcode">
					<option value="${prpDcode.id.codeCode}" <c:if test="${prpDcode.id.codeCode==prpCitemCar.carKindCode}"> selected="selected"</c:if>>${prpDcode.codeCName}</option>
				</c:forEach>
			</select>
		</td>
		<td class="left">
			<s:text name='db.prpCitem_car.engineNo' />：
		</td>
		<%-- 引擎號碼 --%>
		<td class="right">
			<input type="text" name="prpLcompensateEngineNo" style="width: 100%" class="readonly" readonly="true" value="${prpCitemCar.engineNo}">
		</td>
	</tr>
	<tr>
		<td class="left">
			<s:text name='prpCitemCar.EnrollDate' />：
		</td>
		<%-- 原發照時間（年月） --%>
		<td class="right">
			<rc:rcDate name="prpLcompensateEnrollDate" class="readonly" readonly="true" wdatePicker="false" style="width: 100%" value="${prpCitemCar.enrollDate}" />
		</td>
		<td class="left">
			<s:text name='prpCitemCar.MakeDate' />：
		</td>
		<%-- 製造時間（年月） --%>
		<td class="right">
			<rc:rcDate name="prpLcompensateMakeDate" class="readonly" readonly="true" wdatePicker="false" style="width: 100%" value="${prpCitemCar.makeDate}" />
		</td>
		<td class="left"></td>
		<td class="right"></td>
	</tr>
	<tr>
		<td class="left">
			<s:text name='regist.prpLregist.brandName' />：
		</td>
		<%-- 厂牌车型 --%>
		<td class="right">
			<input class="readonly" type="text" name="prpLcompensateBrandName" readonly="true" value="${prpCitemCar.brandName}">
		</td>
		<td class="left">
			<s:text name='prpCitemCar.exhaustScale' />：
		</td>
		<%-- 排氣量 --%>
		<td class="right">
			<input type="text" name="prpLcompensateExhaustScale" style="width: 100%" class="readonly" readonly="true" value="<fmt:formatNumber value='${prpCitemCar.exhaustScale}' pattern='#'/>">
		</td>
		<td class="left"></td>
		<td class="right"></td>
	</tr>
</table>
<%--  其他币种对的本位币（新台币）当日汇率   --%>
<div style="display: none;" id="divExchToBase">
	<c:forEach items="${exchToBase}" var="prpDexchTemp">
		<span name="spanExchToBase">
			<input name="prpDexchBaseCurrency" value="${prpDexchTemp.id.baseCurrency }">
			<input name="prpDexchExchCurrency" value="${prpDexchTemp.id.exchCurrency }">
			<input name="prpDexchExchRate" value="${prpDexchTemp.exchRate }">
		</span>
	</c:forEach>
</div>