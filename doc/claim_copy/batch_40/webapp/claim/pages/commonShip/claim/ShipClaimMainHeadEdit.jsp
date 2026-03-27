<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]（非车险）
* AUTHOR     ： 中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ page import="com.sinosoft.sysframework.common.datatype.DateTime" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="prpDexch" value="${prpDexch}" />
<c:set var="strBaseCurrency" value="${prpDexch.baseCurrency}" />
<c:set var="strExchRate" value="${prpDexch.exchRate}" />
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
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<c:if test="${prpLclaim.caseType=='1'}">
							<s:text name="commonAcci.claim.rejectClaim" /><%--（已拒赔）--%>
						</c:if>
						<c:if test="${prpLclaim.caseType=='0'}">
							<s:text name="commonAcci.claim.cancelled" /><%--（已注销）--%>
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
			<table  class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
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
						<input type="hidden" name="prpLclaimClauseType" readonly="true"  style="width:30px" value="${prpLclaim.clauseType}">
						<input type="hidden" name="prpLclaimClauseName" readonly="true"  style="width:300px"value="${prpLclaim.clauseName}">
				<%--	<input type="hidden" name="prpLclaimAddressCode"  title="<s:text name='db.prpLregist.addressCode'/>" class="input"  style="width:80px" value="${prpLclaim.addressCode}"> --%> <%-- 出险地代码 --%>
						<input type="hidden" name='payFee' value="${requestScope.payFlag }">
						<input type="hidden" name='BaseCurrency1' value="${strBaseCurrency}">
						<input type="hidden" name='ExchRate1' value="${strExchRate}">
						<input type="hidden" name='delinquentfeeCase' value="${delinquentfeeCase}">
						<input type="hidden" name="coreURL" value="${coreURL}">
						<input type="hidden" name="prpLclaimDamageAddressType" title="<s:text name='db.prpLregist.damageAddress'/>" class="codecode" style="width:90px" value="${prpLclaim.damageAddressType}"><%-- 出险地点 --%>
						<input type="hidden" name="prpLclaimDamageTypeCode" class="codecode"  style="width:15%" title="<s:text name='db.prpLregist.damageTypeCode'/>" value="${prpLclaim.damageTypeCode}"><%-- 事故原因 --%>
						<input type="hidden" name="prpLclaimDamageAreaCode" class="codecode" style="width:15%" title="<s:text name='db.prpLregist.damageAreaCode'/>" value="${prpLclaim.damageAreaCode}"><%-- 出险網域 --%>
						<input type="hidden" name="riskcode" value="${prpLclaim.riskCode}">
						<input type="hidden" name="policyno" value="${prpLclaim.policyNo}">
						<input type="hidden" name="registno" value="${prpLclaim.registNo}">
						<input type="hidden" name="prpLclaimLanguage" title="<s:text name='db.prpCinsured.language'/>" class="readonly" readonly="true" style="width:140px" value="${prpLclaim.language}"><%-- 语种 --%>
						<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
						<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
						<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
						<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
						<input type="hidden" name="prpLclaimOthFlag" value="${prpLclaim.othFlag}">
						<input type="hidden" name="underWriteEndDate" value="${prpLclaim.underWriteEndDate}">
						<input type="hidden" name="prpLclaimReportDate1"   value="${registDate}">
						<input type="hidden" name="damageStartDate" value="${prpLclaim.damageStartDate}">
						<input type="hidden" name="damageStartHour" value="${prpLclaim.damageStartHour}">
						<input type="hidden" name="prpLclaimDamageStartHour" value="${prpLclaim.damageStartHour}">
						<input type="hidden" name="prpLclaimDamageStartMinute" value="${prpLclaim.damageStartMinute}">
						
						<!-- 联共保和股东信息-->
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
						<%--立案天数--%>
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
						<s:text name="query.xianzhongName" /><%--险种名称--%>
					</td>
					<td class="right"><c:out value="${riskCName}" /></td>
					<td class="left"><s:text name="db.prpLclaim.claimNo" /></td><%-- 立案号 --%>
					<td class="right">
						<input type=text name="prpLclaimClaimNo" title="立案号" maxlength="22" class="readonly" readonly="true"  value="${prpLclaim.claimNo}">
					</td>
					<td class="left"><s:text name="db.prpLclaim.caseNo" /></td><%--结案号--%>
					<td class="right">
						<input type=text name="prpLclaimCaseNo" title="结案号" class="readonly" readonly="true"  maxlength="22" style="width:140px" value="${prpLclaim.caseNo}">
					</td>
				</tr>
				<tr>
					<td class="left"><s:text name="db.prpLclaim.registNo" /><%--备案号码--%></td>
					<td class="right">
						<input type=text name="prpLclaimRegistNo" title="<s:text name="db.prpLclaim.registNo" />" class="readonly" readonly="true" style="width:90%;" value="${prpLclaim.registNo}"><%--备案号码--%>
						<input type="hidden" name="damageDate" value="${prpLclaim.damageStartDate}">
						<input type=button class="bigbutton"  name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value'/>" onclick="backWardPolicy(fm.coreURL.value,fm.prpLclaimPolicyNo.value,fm.prpLclaimRiskCode.value,fm.damageDate.value,fm.prpLclaimComCode.value);"><%--出险时保单信息--%>
					</td>
					<td class="left">保單年度</td> <%--保單年度--%>
					<td class="right">
						<rc:rcDate name="prpLclaimPolicyInputDate" title="保單年度" class="readonly" format="yyyy" wdatePicker="false" value="${prpLclaim.policyInputDate}" />
					</td>
					<td class="left" ><div style="display:none"><s:text name="db.prpLclaim.lflag" /></div></td>
					<td class="right" >
						<div style="display:none">
							<s:select name="lflag" value="#request.prpLclaim.lflag" list="#request.claimFlagList" listKey="key" listValue="value" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="left"><s:text name="db.prpLclaim.policyNo" /></td> <%-- 保单号 --%>
					<td class="right">
						<input type=text name="prpLclaimPolicyNo" class="readonly" readonly="true"   style="width:160px" value="${prpLclaim.policyNo}">
						<input type="image" name="btRelate" src="/claim/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.prpLclaimPolicyNo.value);return false;">
					</td>
					<td class="left"><s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" /></td><%--已出险次数--%>
					<td class="right">
					<%-- 出险信息画面 --%>
					<%@include file="/pages/common/regist/ExistRegist.jsp"%>
					</td>
					<td class="left"><s:text name="db.prpLCMain.businessNature" /></td><%--业务来源--%>
					<td class="right">
						<input type="hidden" name="prpLclaimBusinessNature" value="${prpLclaim.businessNature}">
						<input type=text name="prpLclaimBusinessNatureName" title="<s:text name="db.prpLCMain.businessNature" />" class="readonly" readonly="true" style="width:180px" value="${prpLclaim.businessNatureName}">
					</td>
				</tr>
				<tr>
					<td class="left"><s:text name="db.prpLclaim.insuredName" /></td>
					<td class="right">
						<input type=hidden name="prpLregistInsuredCode" title="<s:text name='db.prpLclaim.insuredCode'/>" class="readonly" readonly="true" value="${prpLclaim.insuredCode}"><%-- 被保险人代码 --%>
						<input type=hidden name="prpLclaimInsuredName" title="<s:text name='db.prpLclaim.insuredName'/>" class="readonly" readonly="true" value="${prpLclaim.insuredName}">${prpLclaim.insuredName}<%-- 被保险人 --%>
						<input type=hidden name="prpLextSalvor" title="货主名称" value="无"> 
					</td>
					<td class="left"><s:text name="db.prpLCMain.startDate" /></td><%--起保日期--%>
					<td class="right">
						<rc:rcDate name="prpLclaimStartDate" title="<s:text name='db.prpCmain.startDate'/>" wdatePicker="false" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.startDate}" /><%-- 起保日期 --%>
						<s:text name='claim.toThe'/>
						<rc:rcDate name="prpLclaimEndDate" title="<s:text name='db.prpCmain.endDate'/>" wdatePicker="false" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.endDate}" /><%-- 终保日期 --%>
					</td>
					<s:if test="#request.prpLclaim.riskCode =='MC' || #request.prpLclaim.riskCode =='OP' || #request.prpLclaim.riskCode =='TB'">
						<td class="left"><s:text name="db.prpCmainSub.endorseNo" /></td><%--批单号码--%>
						<td class="right">
							<s:select name="prpLclaimEndorseNo" value="#request.prpLclaim.endorseNo" onchange="refreshEndorseInfo(this)" headerKey="" headerValue="" listKey="endorseNo" listValue="endorseNo" list="#request.endorseList" style="width:140px"/>
						</td>
					</s:if>
					<s:else>
						<td class="left">
							<input type="hidden" name="prpLclaimEndorseNo" value="${prpLclaim.endorseNo }">
						</td>
						<td class="right"></td>
					</s:else>
				</tr>
				<tr>
					<td class="left"><s:text name="db.prpLclaim.setSailDate" /></td> <%--开航日期--%>
					<td class="right">
						<rc:rcDate name="prpLextUnloadDate" title="<s:text name='db.prpLclaim.setSailDate'/>" class="readonly" readonly="true" wdatePicker="false" value="${prpLclaim.sailStartDate}" />
					</td>
					<td class="left"><s:text name="db.prpLperson.currency" /></td>  <%--币别--%>
					<td class="right">
						<input class="readonly" readonly="true" name="claimCurrency" value="${prpLclaim.estiCurrency}-${strCurrencyName}">
						<input class="readonly" type=hidden name="prpLclaimCurrency" value="${prpLclaim.estiCurrency}">
						<input class="readonly" type=hidden name="prpLclaimPolicyCurrency">
					</td>
					<td class="left"><s:text name="regist.prpLregist.sumAmount" /></td><%--保险金额--%>
					<td class="right">
						<input class="readonly" name="prpLclaimSumAmount" readonly="true" value="<fmt:formatNumber value="${prpLclaim.sumAmount}"  pattern="0.00"/>">
						<input type="hidden" name="prpLclaimSumPremium" readonly="true" value="${prpLclaim.sumPremium}">
					</td>
				</tr>
				<tr>
					<td class="left"><s:text name="db.prpLclaim.claimDate" />:</td><%--立案日期--%>
					<td class="right">
						<input type=hidden name="prpLclaimReportDate" description="<s:text name="db.prpLregist.reportDate" />" ><%-- 报案日期 --%>
						<input type=hidden name="prpLclaimToday" description="<s:text name="common.check.currentDate" />" ><%-- 当前日期 --%>
						<rc:rcDate name="prpLclaimClaimDate" class="readonly" readonly="true" wdatePicker="false" title="<s:text name='db.prpLclaim.claimDate'/>" value="${prpLclaim.claimDate}" format="yyyy-MM-dd HH:mm:ss" /><%-- 立案日期 --%>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">建造年份：</td>
					<td class="right">
						<rc:rcDate name="prpLclaimMakeDate" class="readonly" readonly="true" format="yyyy" wdatePicker="false" title="建造年份" value="${prpLclaim.makeDate}" /><%-- 建造年份 --%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left"><s:text name="regist.prpLregist.damageTime" /></td><%--出险时间--%>
					<td class="right">
						<%-- 出险时间 --%>
						<rc:rcDate name="prpLclaimDamageStartDate" title="<s:text name='regist.prpLregist.damageTime'/>" value="${prpLclaim.damageStartDate}" style="width:100px;"/> 
						${prpLclaim.damageStartHour} <s:text name ="regist.prpLregist.hour"/>${prpLclaim.damageStartMinute} <s:text name ="regist.prpLregist.minute"/>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left"><s:text name="db.prpLclaim.damageName" /></td>
					<td class="right" colspan="3">
						<input type=text class="codecode" name="prpLclaimDamageCode"
							style="width:27%" title="<s:text name='db.prpLregist.damageCode'/>"
							value="${prpLclaim.damageCode}"
							ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
							onchange="code_CodeChange(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.riskcode.value);" >
							<input type=text class="codecode" name="prpLclaimDamageName"
							title="<s:text name='db.prpLregist.damageCode'/>" style="width:48%"
							value="${prpLclaim.damageName}"
							ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);"
							onchange="code_CodeChange(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);"
							onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.riskcode.value);">
						&nbsp;
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
				</tr>
				<tr>
 					<td class="left"><s:text name="regist.prpLregist.damageAddress" /></td><%--出险地点--%>
				<td class="right" colspan="5">
					<select name="countryFlag" style="width:50px" onchange="countryFlag_change(this.options[this.selectedIndex].value)">
						<option value="0"><s:text name="commonAcci.claim.domestic" /></option> <%--国内--%>
						<option value="1"><s:text name="commonAcci.claim.abroad" /></option> <%--国外--%>
					</select>
					<input type=text class="codecode" name="foreignCountryCode" style="display: none" />
					<input type=text class="codecode" name="foreignCountryName" style="display: none" title="选择国家名" style="width:120px" ondblclick="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();" onkeyup="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();"
						onchange="code_CodeSelect(this, 'foreignCountryCode','-1,0','Y','N');clearPortCode();" />
					<input type=text class="codecode" name="portCode" style="display: none" />
					<input type=hidden class="codecode" name="portCName" title="选择港口名" style="width: 120px" ondblclick="code_CodeSelect(this, 'portCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'portCode','-1,0','Y','N');" onchange="code_CodeSelect(this, 'portCode','-1,0','Y','N');" />
					
					<input type=text class="codecode" name="prpLclaimAddressCode" style="width: 40px" title="<s:text name='regist.prpLregist.areaCode'/>" value="${prpLclaim.addressCode}"
						ondblclick="code_CodeSelect(this, 'PostCode','0,1','Y','Y');" onkeyup="code_CodeSelect(this, 'PostCode','0,1','Y','Y');"
						onchange="code_CodeSelect(this, 'PostCode','0,1','Y','Y');"><%-- 郵遞區號 --%>
					<input type=text class="codecode" name="prpLclaimAddressName" title="<s:text name='db.prpLclaim.damageAreaName'/>" style="width: 110px" value="${prpLclaim.addressName}"
						ondblclick="code_CodeSelect(this, 'PostCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"
						onchange="code_CodeSelect(this, 'PostCode','-1,0','Y','N');"><%-- 出險地區 --%>
					<input type=text name="prpLclaimDamageAddress" title=<s:text name="regist.prpLregist.damageAddress" /> class="input" value="${prpLclaim.damageAddress}" onclick="showProvinceCity(this,'foreignCountryName','prpLclaimAddressName');" style="width: 300px" selectValue="${prpLclaim.addressName}">
					<img src="/claim/images/bgMarkMustInput.jpg">
				</td>
			</tr>
			<%--理赔三期需求 begin--%>
			<tr>
				<s:if test="#request.prpLclaim.riskCode =='MC' || #request.prpLclaim.riskCode =='OP' || #request.prpLclaim.riskCode =='TB'">
					<td class="left"><s:text name="claim.cargoNo" /></td><%--貨物編號--%>
					<td class="right">
						<input type="text" class="input" readonly="readonly" name="prpLclaimCargoNo" value="${prpLclaim.cargoNo}">
					</td>
					<td class="left"><s:text name="claim.cargoName" /></td><%--貨物名稱--%>
					<td class="right">
						<input type="text" class="input" readonly="readonly" name="prpLclaimCargoName" value="${prpLclaim.cargoName}" style="width: 200px;">
					</td>
				</s:if>
				<s:else>
					<td class="left"><s:text name="claim.cargoName" /></td><%--貨物名稱--%>
					<td class="right">
						<input type="text" class="input" name="prpLclaimCargoName" value="${prpLclaim.cargoName}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</s:else>
					<td class="left">共保狀態</td>
					<td class="right">
						<input type="text" class="input" name="prpLclaimCoinsFlag" value="${prpLclaim.coinsFlag}">
					</td>
			</tr>
			<tr>
				<td class="left"><s:text name="claim.generalAverage" /></td><%--共同海損--%>
				<td class="right">
					<select name="generalAverage">
						<option value="0" <c:if test="${prpLclaim.generalAverage=='0'}"><c:out value="selected"/></c:if>>
							<s:text name="certainLoss.thirdCarLoss.no" />
						</option>
						<%-- 否 --%>
						<option value="1" <c:if test="${prpLclaim.generalAverage=='1'}"><c:out value="selected"/></c:if>>
							<s:text name="certainLoss.thirdCarLoss.yes" />
						</option>
						<%-- 是 --%>
					</select>
				</td>
				<td class="left"><s:text name="claim.transportType" /></td><%--運輸方式--%>
				<td class="right">
					<s:select name="transportType" value="#request.prpLclaim.transportType" list="#request.transportTypeList" listKey="key" listValue="value" />
				</td>
				<td class="left">
					<s:text name="claim.importType" />
				</td>
				<td class="right">
					<s:select name="prpLclaimImportType" value="#request.prpLclaim.importType" list="#request.importTypeList" listKey="key" listValue="value" />
				</td>
			</tr>
			<%--理赔三期需求 end--%>
			<tr>
				<td class="left"><s:text name="claim.otherClaimsInterm" /></td><%--是否有其他理赔中介机构--%>
				<td class="right">
					<select name="thirdComFlag">
						<option value="0" <c:if test="${prpLclaim.thirdComFlag=='0'}" ><c:out value="selected"/></c:if>>
							<s:text name="certainLoss.thirdCarLoss.no" />
						</option>
						<option value="1" <c:if test="${prpLclaim.thirdComFlag=='1'}" ><c:out value="selected"/></c:if>>
							<s:text name="certainLoss.thirdCarLoss.yes" />
						</option>
					</select>
				</td>
				<td class="left"><s:text name="claim.possibleRec" /></td><%--是否可能有追偿--%>
				<td class="right">
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
				<td class="left"><s:text name="claim.recoverAge" /></td><%--追偿时效--%>
				<td class="right">
					<rc:rcDate name="ReplevyLimitDate" class="query" value="${prpLclaim.replevyLimitDate}" title="<s:text name='common.check.endDate'/>" onkeypress="return pressFullDate(event);" />
					<%-- 
					<s:if test="#request.editType =='ADD' || #request.editType =='ADD'">
						<img align="absmiddle" style='cursor: hand' src="/claim/images/bgcalendar.gif" onclick="TogglePopupCalendarWindow('document.fm.ReplevyLimitDate', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()-15 %>', '<%=new DateTime(DateTime.current(),DateTime.YEAR_TO_DAY).getYear()+2 %>')">
					</s:if>
					--%>
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
				<td class="left"><s:text name="certify.whetherInsure" /></td><%--是否涉及担保--%>
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
				<td class="left"><s:text name="commonAcci.claim.involvedLitigat" /></td><%--是否涉及诉讼--%>
				<td class="right">
					<select name="referLawFlag">
						<option value="0" <c:if test="${prpLclaim.referLawFlag=='0'}"><c:out value="selected"/></c:if>>
							<s:text name="certainLoss.thirdCarLoss.no" />
						</option>
						<%-- 否 --%>
						<option value="1" <c:if test="${prpLclaim.referLawFlag=='1'}"><c:out value="selected"/></c:if>>
							<s:text name="certainLoss.thirdCarLoss.yes" />
						</option>
						<%-- 是 --%>
					</select>
				</td>
				<td class="left">收費情形</td>
				<td class="right">
				<select name="chargeType">
					<option value="0" <c:if test="${prpLclaim.chargeType=='0'}"><c:out value="selected"/></c:if>>
						<s:text name="certainLoss.thirdCarLoss.no" />
					</option>
					<%-- 否 --%>
					<option value="1" <c:if test="${prpLclaim.chargeType=='1'}"><c:out value="selected"/></c:if>>
						<s:text name="certainLoss.thirdCarLoss.yes" />
					</option>
					<%-- 是 --%>
				</select>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
<br>