<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]（非车险）
* AUTHOR     ： lixiang
* CREATEDATE ： 2004-10-14
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime"%>
<c:set var="prpDexch" value="${prpDexch}" />
<c:set var="strBaseCurrency" value="${prpDexch.baseCurrency}" />
<c:set var="strExchRate" value="${prpDexch.exchRate}" />
<c:set var="kind" value="" />
<c:set var="kindAmount" value="" />
<c:if test="${requestScope.amountProp != null}">
	<c:forEach var="amount" items="${requestScope.amountProp}">
		<input type="hidden" name="itemCode" value="${amount.key}">
		<input type="hidden" name="kindAmount" value="${amount.value}">
	</c:forEach>
</c:if>
<c:set var="prpLclaim" value="${requestScope.prpLclaim}" />
<c:set var="intstartHour" value="${prpLclaim.startHour}" />
<c:set var="intendHour" value="${prpLclaim.endHour}" />
<c:set var="startHour" value="0" />
<c:set var="endHour" value="0" />
<c:if test="${intstartHour=='0'}">
	<c:set var="startHour"><s:text name='modifySumClaim.comeEffect'/></c:set> <%--零時起至--%>
</c:if>
<c:if test="${intstartHour=='12'}">
	<c:set var="startHour"><s:text name='regist.from'/></c:set> <%--十二時起至--%>
</c:if>
<c:if test="${intstartHour=='24'}">
	<c:set var="startHour"><s:text name='regist.start'/></c:set><%--二十四時起--%>
</c:if>
<c:if test="${intendHour=='0'}">
	<c:set var="endHour"><s:text name='regist.until'/></c:set><%--零時止--%>
</c:if>
<c:if test="${intendHour=='12'}">
	<c:set var="endHour"><s:text name='regist.end'/></c:set><%--十二時止--%>
</c:if>
<c:if test="${intendHour=='24'}">
	<c:set var="endHour"><s:text name='modifySumClaim.hourEnd'/></c:set><%--二十四時止--%>
</c:if>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
	<tr>
		<td width="30%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<c:if test="${prpLclaim.caseType=='1'}">
							<s:text name="commonAcci.claim.rejectClaim" />
							<%-- （已拒赔） --%>
						</c:if>
						<c:if test="${prpLclaim.caseType=='0'}">
							<s:text name="commonAcci.claim.cancelled" />
							<%-- （已注销） --%>
						</c:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<input type="hidden" name="prpLclaimRiskCode" value="${prpLclaim.riskCode}">
					<input type="hidden" name="prpLclaimOperatorCode" value="${prpLclaim.operatorCode}">
					<input type="hidden" name="prpLclaimMakeCom" value="${prpLclaim.makeCom}">
					<input type="hidden" name="prpLclaimEngineNo">
					<input type="hidden" name="prpLclaimFrameNo">
					<input type="hidden" name="prpLclaimRunDistance">
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
					<%-- <input type="hidden" name="prpLclaimAddressCode" title="<s:text name='db.prpLregist.addressCode'/>" class="input" style="width: 80px" value="${prpLclaim.addressCode}">出险地代码 --%>
					<input type="hidden" name="prpLclaimDamageAddressType" title="<s:text name='db.prpLregist.damageAddress'/>" class="codecode" style="width: 90px" value="${prpLclaim.damageAddressType}"><%-- 出险地点 --%>
					<input type="hidden" name="prpLclaimDamageTypeCode" class="codecode" style="width: 15%" title="<s:text name='db.prpLregist.damageTypeCode'/>" value="${prpLclaim.damageTypeCode}"><%-- 事故原因 --%>
					<input type="hidden" name="prpLclaimDamageAreaCode" class="codecode" style="width: 15%" title="<s:text name='db.prpLregist.damageAreaCode'/>" value="${prpLclaim.damageAreaCode}"><%-- 出险区域 --%>
					<input type="hidden" name="riskcode" value="${prpLclaim.riskCode}">
					<input type="hidden" name="policyno" value="${prpLclaim.policyNo}">
					<input type="hidden" name="registno" value="${prpLclaim.registNo}">
					<input type="hidden" name='payFee' value="${requestScope.payFlag }">
					<input type="hidden" name='BaseCurrency1' value="${strBaseCurrency}">
					<input type="hidden" name='ExchRate1' value="${strExchRate}">
					<input type="hidden" name='delinquentfeeCase' value="${delinquentfeeCase}">
					<input type="hidden" name="prpLclaimLanguage" title="<s:text name='db.prpCinsured.language'/>" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.language}"><%-- 语种 --%>
					<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
					<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
					<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
					<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
					<input type="hidden" name="prpLclaimOthFlag" value="${prpLclaim.othFlag}">
					<input type="hidden" name="underWriteEndDate" value="${prpLclaim.underWriteEndDate}">
					<input type="hidden" name="damageStartDate" value="${prpLclaim.damageStartDate}">
					<input type="hidden" name="damageStartHour" value="${prpLclaim.damageStartHour}">
					<input type="hidden" name="coreURL" value="<%=AppConfig.get("sysconst.Core_URL")%>">
					<input type="hidden" name="reportTimeMessage" value="${reportTimeMessage }">
					<c:choose>
						<c:when test="${not empty requestScope.coinsFlag}">
							<input type="hidden" name="coinsFlag" value="${coinsFlag}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="coinsFlag" value="0">
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${not empty requestScope.shareHolderFlag}">
							<input type="hidden" name="shareHolderFlag" value="${shareHolderFlag}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="shareHolderFlag" value="0">
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${not empty requestScope.tempReinsFlag}">
							<input type="hidden" name="tempReinsFlag" value="${tempReinsFlag}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="tempReinsFlag" value="0">
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${not empty requestScope.registDate}">
							<input type="hidden" name="prpLclaimReportDate1" value="${registDate}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="prpLclaimReportDate1" value="0">
						</c:otherwise>
					</c:choose>
					<%--立案天数 add by qinyongli--%>
					<c:choose>
						<c:when test="${not empty requestScope.claim_days}">
							<input type="hidden" name="claim_days" value="${claim_days}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="claim_days" value="0">
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${not empty requestScope.standardDays}">
							<input type="hidden" name="standardDays" value="${standardDays}">
						</c:when>
						<c:otherwise>
							<input type="hidden" name="standardDays" value="0">
						</c:otherwise>
					</c:choose>
					</td>
					<td class="left">
						<s:text name="query.xianzhongName" />
						:
					</td>
					<%-- 险种名称 --%>
					<td class="right">
						<c:out value="${riskCName}" />
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.claimNo" />
						:
					</td>
					<td class="right">
						<input type=text name="prpLclaimClaimNo" title="<s:text name='db.prpLcomponent.claimNo'/>" maxlength="22" class="readonly" readonly="true" value="${prpLclaim.claimNo}"><%-- 立案号码 --%>
					</td>
					<td class="left">
						<s:text name="db.prpLcompensate.caseNo" />
						:
					</td>
					<%--结案号  --%>
					<td class="right">
						<input type=text name="prpLclaimCaseNo" title="<s:text name='db.prpLcompensate.caseNo'/>" class="readonly" readonly="true" maxlength="22" value="${prpLclaim.caseNo}"><%-- 结案号码 --%>
					</td>
				</tr>
				<tr>
					<td class="left" style='display: none'>
						<s:text name="db.prpLclaim.lflag" />
					</td>
					<td class="right" style='display: none'>
						<s:select name="lflag" value="#request.prpLclaim.lflag" list="#request.claimFlagList" listKey="key" listValue="value" />
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.registNo" />
						:
					</td>
					<%--备案号码--%>
					<td class="right" colspan=3>
						<input type=text name="prpLclaimRegistNo" title="<s:text name="db.prpLclaim.registNo" />" class="readonly" readonly="true" style="width: 170px" value="${prpLclaim.registNo}"><%--备案号码--%>
						<input type="hidden" name="damageDate" value="${prpLclaim.damageStartDate}">
						<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value'/>"
							onclick="backWardPolicy(fm.coreURL.value,fm.prpLclaimPolicyNo.value,fm.prpLclaimRiskCode.value,fm.damageDate.value,fm.prpLclaimComCode.value);">
						<%-- 出险时保单信息 --%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.policyNo" />
						:
					</td>
					<td class="right">
						<input type=text name="prpLclaimPolicyNo" class="readonly" readonly="true" style="width: 170px" value="${prpLclaim.policyNo}">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLclaimPolicyNo.value,fm.registno.value);return false;">
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<%-- 已出险次数 --%>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/GAA/regist/GAAExistRegist.jsp"%>
					</td>
					<td class="left">
						<s:text name="db.prpLCMain.businessNature" />:
					</td>
					<%--业务来源  --%>
					<td class="right">
						<input type="hidden" name="prpLclaimBusinessNature" value="${prpLclaim.businessNature}">
						<input type=text name="prpLclaimBusinessNatureName" title="<s:text name="db.prpLCMain.businessNature" />" class="readonly" readonly="true" style="width: 140px" value="${prpLclaim.businessNatureName}"><%--业务来源  --%>
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.insuredName" />:
					</td>
					<td class="right">
						<input type=hidden name="prpLregistInsuredCode" title="<s:text name='db.prpLclaim.insuredCode'/>" class="readonly" readonly="true" value="${prpLclaim.insuredCode}"><%-- 被保险人代码 --%>
						<input type=hidden name="prpLclaimInsuredName" title="<s:text name='db.prpLclaim.insuredName'/>" class="readonly" readonly="true" value="${prpLclaim.insuredName}"><%-- 被保险人 --%>
						${prpLclaim.insuredName} </a>
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.insuranceTime" />:
					</td>
					<%--保险期间  --%>
					<td class="right" colspan='3'>
						<rc:rcDate name="prpLclaimStartDate" title="<s:text name='db.prpCmain.startDate'/>" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.startDate}" /><%-- 起保日期 --%>
						<c:out value="${startHour}" />
						<rc:rcDate name="prpLclaimEndDate" title="<s:text name='db.prpCmain.endDate'/>" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.endDate}" /><%-- 终保日期 --%>
						<c:out value="${endHour}" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.currency" />:
					</td>
					<%--币别  --%>
					<td class="right">
						<input class="readonly" readonly name="claimCurrency" value="${prpLclaim.estiCurrency}-${strCurrencyName}">
						<input class="readonly" type=hidden readonly name="claimCurrency" value="${prpLclaim.estiCurrency}">
						<input class="readonly" type=hidden name="prpLclaimPolicyCurrency">
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.sumAmount" />:
					</td>
					<%--保险金额  --%>
					<td class="right">
						<input class="readonly" name="prpLclaimSumAmount" readonly="true" value="<fmt:formatNumber value="${prpLclaim.sumAmount}"  pattern="#"/>">
						<input type="hidden" name="prpLclaimSumPremium" readonly="true" value="${prpLclaim.sumPremium}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageStartDate" />
					</td>
					<%--出险时间  --%>
					<td class="right">
						<rc:rcDate name="prpLclaimDamageStartDate" title="<s:text name='regist.prpLregist.damageTime'/>" style="width:40%" value="${prpLclaim.damageStartDate}" />
						<%-- 出险时间 --%>
						<input name="prpLclaimDamageStartHour" title="<s:text name='db.prpLregist.damageHour'/>" class="readonly" readonly="true" class="input" maxlength="2" style="width: 10%" value="${prpLclaim.damageStartHour}">
						<%-- 出险小时 --%>
						<s:text name="regist.prpLregist.hour" />
						<%--时  --%>
						<input name="prpLclaimDamageStartMinute" title="<s:text name='db.prpLregist.damageMinute'/>" class="readonly" readonly="true" maxlength="2" style="width: 10%" value="${prpLclaim.damageStartMinute}">
						<%-- 出险分钟  --%>
						<s:text name="regist.prpLregist.minute" />
						<%--分  --%>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left"><s:text name="certify.dateReceipt"/>:</td><%-- 收件日期 --%>
					<td class="right">
						<rc:rcDate name="prpLclaimReceiptDate" title="收件日期" style="width:187px" value="${prpLclaim.receiptDate}" format="yyyy-MM-dd HH:mm" /><%-- 收件日期 --%>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageName" />
						:
					</td>
					<td class="right">
						<input type=text class="codecode" name="prpLclaimDamageCode" style="width: 15%" title="<s:text name='db.prpLregist.damageCode'/>" value="${prpLclaim.damageCode}" ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);">
						<%--出险原因 --%>
						<input type=text class="codecode" name="prpLclaimDamageName" title="<s:text name='db.prpLregist.damageCode'/>" style="width: 43%" value="${prpLclaim.damageName}" ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);">
						<%--出险原因 --%>
						<img src="/claim/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageAddress" />
					</td>
					<%-- 出险地点 --%>
					<td class="right" colspan="3">
						<select name="countryFlag" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
							<option value="0">
								<s:text name="check.domestic" />
							</option>
							<%-- 国内 --%>
							<option value="1">
								<s:text name="check.abroad" />
							</option>
							<%-- 国外 --%>
						</select>
						<input type=text class="codecode" name="countryCode" style="display: none" />
						<input type=text class="codecode" name="countryCName" style="display: none" title="<s:text name='common.select.country'/>" style="width:120px" ondblclick="code_CodeSelect(this, 'CountryCode','-1,0','Y','N');"
							onkeyup="code_CodeSelect(this, 'CountryCode','-1,0','Y','N');" onchange="code_CodeSelect(this, 'CountryCode','-1,0','Y','N');" />
						<%--选择国家名 --%>
						<%-- <input type=hidden class="codecode" name="provinceCode"  value="710000"/>
						<input type=hidden class="codecode" name="provinceName" title="<s:text name='common.select.province'/>" ondblclick="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');"
							onkeyup="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" onchange="code_CodeSelect(this, 'utiAdminProvice','-1,0','Y','N');" value=""/> --%>
						<%--选择省 --%>
						<input type=text class="codecode" name="prpLclaimAddressCode" style="width: 40px;" title="<s:text name='regist.prpLregist.areaCode'/>" value="${prpLclaim.addressCode}"
							ondblclick="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'PostCode','0,1','Y','Y');"
							onchange="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" > 
						<input type=text class="codecode" name="prpLclaimAddressName" title="<s:text name='db.prpLclaim.damageAreaName'/>" style="width: 110px" value="${prpLclaim.addressName}"
							ondblclick="code_CodeSelect(this, 'PostCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"
							onchange="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"> 
						 	
						<%--<input type=text class="codecode" name="cityCode" style="display: none" />
						<input type=text class="codecode" name="cityName" title="<s:text name='common.select.city'/>" style="width: 120px" ondblclick="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);"
							onkeyup="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" onchange="code_CodeSelect(this, 'utiAdminCity','-1,0','Y','N',fm.provinceCode.value);" /> --%>  
						<%--选择市 --%>
						<input type=text name="prpLclaimDamageAddress" title="<s:text name='db.prpLregist.damageAddress'/>" style="width: 300px" value="${prpLclaim.damageAddress}" onclick="showProvinceCity(this,'countryCName','prpLclaimAddressName');" selectValue="${prpLclaim.addressName}" class="input">
						<%--出险地点 --%>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class='title'>
						<s:text name='claim.otherClaimsInterm' />
						:
					</td>
					<%--是否有其他理赔中介机构--%>
					<%-- 是否有其他理赔中介机构 --%>
					<td class='title'>
						<select name="thirdComFlag">
							<option value="0" <c:if test="${prpLclaim.thirdComFlag=='0'}" ><c:out value="selected"/></c:if>>
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<option value="1" <c:if test="${prpLclaim.thirdComFlag=='1'}" ><c:out value="selected"/></c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
						</select>
					</td>
					<td class='left'>
						<s:text name="claim.possibleRec" />
						:
					</td>
					<%-- 是否可能有追偿 --%>
					<td class='right'>
						<select name="replevyFlag">
							<option value="0" <c:if test="${prpLclaim.replevyFlag=='0'}"><c:out value="selected"/></c:if>>
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%-- 否 --%>
							<option value="1" <c:if test="${prpLclaim.replevyFlag=='1'}"><c:out value="selected"/></c:if>>
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%-- 是 --%>
						</select>
					</td>
					<td class="left">
						<s:text name="claim.recoverAge" />
						:
					</td>
					<%-- 追偿时效 --%>
					<td class="right">
						<rc:rcDate name="ReplevyLimitDate" class="query" value="${prpLclaim.replevyLimitDate}" title="<s:text name='common.check.endDate'/>" onkeypress="return pressFullDate(event);" />
						<%--结束日期--%>
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
						<s:text name="commonAcci.claim.involvedLitigat" />
						:
					</td>
					<%-- 是否涉及诉讼 --%>
					<td class="right">
						<select name="referLawFlag">
							<option value="0">
								<s:text name="certainLoss.thirdCarLoss.no" />
							</option>
							<%-- 否 --%>
							<option value="1">
								<s:text name="certainLoss.thirdCarLoss.yes" />
							</option>
							<%-- 是 --%>
						</select>
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
