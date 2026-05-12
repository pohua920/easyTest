<%--
****************************************************************************
* DESC       :添加主信息子块界面页面Head[ 实赔 ]
* AUTHOR     : 理赔组
* CREATEDATE : 2004-05-12
* MODIFYLIST :   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<s:if test="#attr.amountMap!=null">
	<s:iterator var="amountTemp" value="#attr.amountMap">
		<input type="hidden" name="kindCode" value="${amountTemp.key }">
		<input type="hidden" name="kindAmount" value="${amountTemp.value }">
	</s:iterator>
</s:if>
<s:set value="''" name="startHour" scope="page" />
<s:set value="''" name="endHour" scope="page" />
<s:if test="#attr.prpLcompensate.startHour==0">
	<s:set value="'零時起至'" name="startHour" scope="page" />
</s:if>
<s:elseif test="#attr.prpLcompensate.startHour==12">
	<s:set value="'十二時起至'" name="startHour" scope="page" />
</s:elseif>
<s:elseif test="#attr.prpLcompensate.startHour==24">
	<s:set value="'二十四時起'" name="startHour" scope="page" />
</s:elseif>
<s:if test="#attr.prpLcompensate.endHour==0">
	<s:set value="'零時止'" name="endHour" scope="page" />
</s:if>
<s:elseif test="#attr.prpLcompensate.endHour==12">
	<s:set value="'十二時止'" name="endHour" scope="page" />
</s:elseif>
<s:elseif test="#attr.prpLcompensate.endHour==24">
	<s:set value="'二十四時止'" name="endHour" scope="page" />
</s:elseif>
<script language="javascript">
	function changePrpLcompensateFinallyFlag() {
		var t = document.getElementById("prpLcompensateFinallyFlag");
		var v = t.options[t.selectedIndex].value;
		if ("0" == v) {
			fm.replevyFlag.disabled = false;
			fm.referLawFlag.disabled = false;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='common.certify.followupclaimDesc'/>";<%--后续理算说明--%>
		} else if ("1" == v) {
			fm.replevyFlag.disabled = false;
			fm.referLawFlag.disabled = true;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/> ";<%--理算报告--%>
		} else {
			fm.replevyFlag.disabled = true;
			fm.referLawFlag.disabled = true;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/>";<%--理算报告--%>
		}
	}
	function changePrpLcompensateFinallyFlag1() {
		fm.replevyFlag.disabled = false;
		fm.referLawFlag.disabled = true;
		Lltext.style.display = "";
		fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/>";<%--理算报告--%>
	}
	function changeFinallyFlagAndLltextContent() {
		var isPayForOtherList = document.getElementsByName("isPayForOther");
		//reason:理算报告用来保存"後续理算内容"
		var strtemp = "";
		var t = document.getElementById("prpLcompensateFinallyFlag");
		var v = t.options[t.selectedIndex].value;
		if ("0" == v) {
			alert("<s:text name='prompt.certify.message1'/>\n");<%--案件核赔通过后，不会自动结案，请手工结案！--%>
			fm.replevyFlag.disabled = false;
			fm.referLawFlag.disabled = false;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='common.certify.followupclaimDesc'/>";<%--后续理算说明--%>
			strtemp = fm.backLltextContent.value;
			fm.backLltextContent.value = fm.prpLltextContextInnerHTML.value;//备份现在的数据
			fm.prpLltextContextInnerHTML.value = strtemp;
		} else if ("1" == v) {
			if (isPayForOtherList.length > 0
					&& isPayForOtherList[0].checked == true) {
				alert("<s:text name='prompt.certify.message2'/>");<%--选择代付赔款，不准许选择为结案！--%>
				fm.prpLcompensateFinallyFlag.options[0].selected = true;
				fm.replevyFlag.disabled = false;
				fm.referLawFlag.disabled = false;
				Lltext.style.display = "";
				fm.tdLltextTitle.value = "<s:text name='common.certify.followupclaimDesc'/>";<%--后续理算说明--%>
				strtemp = fm.backLltextContent.value;
				fm.backLltextContent.value = fm.prpLltextContextInnerHTML.value;//备份现在的数据
				fm.prpLltextContextInnerHTML.value = strtemp;
				return;
			}
			alert("<s:text name='prompt.certify.message3'/>\n");<%--案件核赔通过后，将自动结案！--%>
			fm.replevyFlag.disabled = false;
			fm.referLawFlag.disabled = true;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/>";<%--理算报告--%>
			strtemp = fm.backLltextContent.value;
			fm.backLltextContent.value = fm.prpLltextContextInnerHTML.value;//备份现在的数据
			fm.prpLltextContextInnerHTML.value = strtemp;
		} else {
			alert("<s:text name='prompt.certify.message3'/>\n");<%--案件核赔通过后，将自动结案！--%>
			fm.replevyFlag.disabled = true;
			fm.referLawFlag.disabled = true;
			Lltext.style.display = "";
			fm.tdLltextTitle.value = "<s:text name='compensate.adjustReport'/>";<%--理算报告--%>
			strtemp = fm.backLltextContent.value;
			fm.backLltextContent.value = fm.prpLltextContextInnerHTML.value;//备份现在的数据
			fm.prpLltextContextInnerHTML.value = strtemp;
		}
	}
	function changeFinallyFlag(type) {
		var prpLcompensateFinallyFlag = document
				.getElementById("prpLcompensateFinallyFlag");
		var prpLcompensateSumCoinForOther = document
				.getElementsByName("prpLcompensateSumCoinForOther");
		var prpLcompensateSumCoinForOtherFee = document
				.getElementsByName("prpLcompensateSumCoinForOtherFee");
		var prpLcompensateSumCoinForOtherBak = document
				.getElementsByName("prpLcompensateSumCoinForOtherBak");
		var prpLcompensateSumCoinForOtherFeeBak = document
				.getElementsByName("prpLcompensateSumCoinForOtherFeeBak");
		if (type == 1) {
			if (prpLcompensateFinallyFlag.length > 1) {
				fm.prpLcompensateFinallyFlag.options[0].selected = true;
			}
			if (prpLcompensateSumCoinForOther.length > 0
					&& prpLcompensateSumCoinForOtherBak.length > 0) {
				prpLcompensateSumCoinForOther[0].value = prpLcompensateSumCoinForOtherBak[0].value;
			}
			if (prpLcompensateSumCoinForOtherFee.length > 0
					&& prpLcompensateSumCoinForOtherFeeBak.length > 0) {
				prpLcompensateSumCoinForOtherFee[0].value = prpLcompensateSumCoinForOtherFeeBak[0].value;
			}
		} else if (type == 0) {
			if (prpLcompensateSumCoinForOther.length > 0
					&& prpLcompensateSumCoinForOtherBak.length > 0) {
				prpLcompensateSumCoinForOther[0].value = '0.0';
			}
			if (prpLcompensateSumCoinForOtherFee.length > 0
					&& prpLcompensateSumCoinForOtherFeeBak.length > 0) {
				prpLcompensateSumCoinForOtherFee[0].value = '0.0';
			}
		}
	}
</script>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<input type="hidden" name="prpLcompensateCaseNo" value="<c:out value='${requestScope.prpLcompensate.caseNo}'/>">
				<input type="hidden" name="prpLcompensateClassCode" value="<c:out value='${requestScope.prpLcompensate.classCode}'/>">
				<input type="hidden" name="prpLcompensateRiskCode" value="<c:out value='${requestScope.prpLcompensate.riskCode}'/>">
				<input type="hidden" name="prpLcompensateDeductCond" value="<c:out value='${requestScope.prpLcompensate.deductCond}'/>">
				<input type="hidden" name="prpLcompensatePreserveDate" value="<c:out value='${requestScope.prpLcompensate.preserveDate}'/>">
				<input type="hidden" name="prpLcompensateCheckAgentCode" value="<c:out value='${requestScope.prpLcompensate.checkAgentCode}'/>">
				<input type="hidden" name="prpLcompensateCheckAgentName" value="<c:out value='${requestScope.prpLcompensate.checkAgentName}'/>">
				<input type="hidden" name="prpLcompensateSurveyorName" value="<c:out value='${requestScope.prpLcompensate.surveyorName}'/>">
				<input type="hidden" name="prpLcompensateCounterClaimerName" value="<c:out value='${requestScope.prpLcompensate.counterClaimerName}'/>">
				<input type="hidden" name="prpLcompensateDutyDescription" value="<c:out value='${requestScope.prpLcompensate.dutyDescription}'/>">
				<input type="hidden" name="prpLcompensateCurrency" value="<c:out value='${requestScope.prpLcompensate.currency}'/>">
				<input type="hidden" name="prpLcompensateSumLoss" value="<c:out value='${requestScope.prpLcompensate.sumLoss}'/>">
				<input type="hidden" name='payFee' value="<c:out value='${requestScope.payFlag}'/>">
				<input type="hidden" name='BaseCurrency1' value="<c:out value='${requestScope.prpDexch.baseCurrency}'/>">
				<input type="hidden" name='ExchRate1' value="<c:out value='${requestScope.prpDexch.exchRate}'/>">
				<input type="hidden" name='delinquentfeeCase' value="<c:out value='${requestScope.delinquentfeeCase}'/>">
				<input type="hidden" name="prpLcompensateReceiverName" value="<c:out value='${requestScope.prpLcompensate.receiverName}'/>">
				<input type="hidden" name="prpLcompensateBank" value="<c:out value='${requestScope.prpLcompensate.bank}'/>">
				<input type="hidden" name="prpLcompensateAccount" value="<c:out value='${requestScope.prpLcompensate.account}'/>">
				<input type="hidden" name="prpLcompensateMakeCom" value="<c:out value='${requestScope.prpLcompensate.makeCom}'/>">
				<input type="hidden" name="prpLcompensateComCode" value="<c:out value='${requestScope.prpLcompensate.comCode}'/>">
				<input type="hidden" name="prpLcompensateHandlerCode" value="<c:out value='${requestScope.prpLcompensate.handlerCode}'/>">
				<input type="hidden" name="prpLcompensateHandler1Code" value="<c:out value='${requestScope.prpLcompensate.handler1Code}'/>">
				<input type="hidden" name="prpLcompensateApproverCode" value="<c:out value='${requestScope.prpLcompensate.approverCode}'/>">
				<input type="hidden" name="prpLcompensateUnderWriteCode" value="<c:out value='${requestScope.prpLcompensate.underWriteCode}'/>">
				<input type="hidden" name="prpLcompensateUnderWriteName" value="<c:out value='${requestScope.prpLcompensate.underWriteName}'/>">
				<input type="hidden" name="prpLcompensateOperatorCode" value="<c:out value='${requestScope.prpLcompensate.operatorCode}'/>">
				<input type="hidden" name="prpLcompensateInputDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.inputDate}' pattern='yyyy-MM-dd HH:mm:ss'/>">
				<input type="hidden" name="prpLcompensateUnderWriteEndDate" value="<c:out value='${requestScope.prpLcompensate.underWriteEndDate}'/>">
				<input type="hidden" name="prpLcompensateUnderWriteFlag" value="<c:out value='${requestScope.prpLcompensate.underWriteFlag}'/>">
				<input type="hidden" name="prpLcompensateFlag" value="<c:out value='${requestScope.prpLcompensate.flag}'/>">
				<input type="hidden" name="riskcode" value="<c:out value='${requestScope.prpLcompensate.riskCode}'/>">
				<input type="hidden" name="policyno" value="<c:out value='${requestScope.prpLcompensate.policyNo}'/>">
				<input type="hidden" name="registno" value="<c:out value='${requestScope.prpLcompensate.registNo}' />">
				<input type="hidden" name="swfLogFlowID" value="${param.swfLogFlowID}">
				<input type="hidden" name="swfLogLogNo" value="${param.swfLogLogNo}">
				<input type="hidden" name="swfLogActorId" value="<c:out value='${param.actorId}'/>">
				<input type="hidden" name="swfLogProcessId" value="<c:out value='${param.processId}'/>">
				<input type="hidden" name="prpLcompensateCaseType" value="<c:out value='${requestScope.prpLcompensate.caseType}' />">
				<input type="hidden" name="prpLcompensateStartDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.startDate}' pattern='yyyy-MM-dd'/> <s:text name='endcase.dayStart'/><fmt:formatDate value='${requestScope.prpLcompensate.endDate}' pattern='yyyy-MM-dd'/><s:text name='endcase.dayEnd'/>"><%--日 0 时 至--%><%--日 24 时止--%>
				<input type="hidden" name="coreURL" value="${core_URL }">
				<input type="hidden" name="prpLcompensateClauseName" value="<c:out value='${requestScope.prpLcompensate.clauseName}' />">
				<input type="hidden" name="prpLcompensateLicenseNo" value="<c:out value='${requestScope.prpLcompensate.licenseNo}' />">
				<input type="hidden" name="prpLcompensateCarKind" value="<c:out value='${requestScope.prpLcompensate.carKind}' />">
				<input type="hidden" name="prpLcompensateLicenseColor" value="<c:out value='${requestScope.prpLcompensate.licenseColor}' />">
				<input type="hidden" name="prpLcompensateBrandName" value="<c:out value='${requestScope.prpLcompensate.brandName}' />">
				<input type="hidden" name="prpLcompensateEngineNo" value="<c:out value='${requestScope.prpLcompensate.engineNo}'/>">
				<input type="hidden" name="prpLcompensateFrameNo" value="<c:out value='${requestScope.prpLcompensate.frameNo}'/>">
				<input type="hidden" name="prpLcompensateDamageAddress" value="<c:out value='${requestScope.prpLcompensate.damageAddress}' />">
				<input type="hidden" name="prpLcompensateSumAmount" value="<c:out value='${requestScope.prpLcompensate.sumAmount}' />">
				<input type="hidden" name="prpLcompensateSumClaim" value="<c:out value='${requestScope.prpLcompensate.sumClaim}' />">
				<input type="hidden" name="GenerateCompensateFlag" value="0">
				<input type="hidden" name="damageStartDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.damageStartDate}' pattern='yyyy-MM-dd'/>">
				<input type="hidden" name="damageStartHour" value="<c:out value='${requestScope.prpLcompensate.damageStartHour}' />">
				<input type="hidden" name="sumPaidAll" value="<c:out value='${requestScope.prpLcompensate.sumPaidAll}' />">
				<tr>
					<td class="left">
						<s:text name="query.xianzhongName"/><%--险种名称--%>
					</td>
					<td class="right"　>
						<c:out value='${requestScope.riskCName}' />
					</td>
					<td class="left"　></td>
					<td class="right"　></td>
					<td class="left"　></td>
					<td class="right"　></td>
				</tr>
				</tr>
				<tr>
					<td class="left">
						<s:text name="compensate.computeBookNum" />
					</td>
					<%-- 计算书号 --%>
					<td class="right">
						<input type=text name="prpLcompensateCompensateNo" title="<s:text name='compensate.computeBookNum' />" maxlength="22" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLcompensate.compensateNo}'/>"><%-- 计算书号 --%>
					</td>
					<td class="left" colspan="2">
						<input type=button class="bigbutton" name="flowShow" value="<s:text name='button.flowChart.value'/>" title="<s:text name='button.flowChart.value'/>" onclick="showWorkFlowerByClaimNo('${requestScope.prpLcompensate.claimNo}')"><%--赔案流程图--%>
					</td>
					<%-- 赔案流程图 --%>
					<input type=hidden name="LFlag" title="<s:text name='db.prpLcompensate.lflag'/>" maxlength="22" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLcompensate.lflag}'/>"><%--理赔类型--%>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.claimNum" />
					</td>
					<%-- 赔案号 --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateClaimNo" readonly="true" value="<c:out value='${requestScope.prpLcompensate.claimNo}' />">
						<input type="hidden" name="damageDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.damageStartDate}' pattern='yyyy-MM-dd'/>">
					</td>
					<td class="left" colspan="2">
						<input type=button class="bigbutton" name="policyBackWard" value="<s:text name='button.dangerPolicyInfo.value'/>"
							onclick="backWardPolicy(fm.coreURL.value,fm.prpLcompensatePolicyNo.value,fm.prpLcompensateRiskCode.value,fm.damageDate.value,fm.prpLcompensateComCode.value);">
						<%-- 出险时保单信息 --%>
					</td>
					<td class="left">
						<s:text name="certainLoss.prpLcheck.prpLcheckDamageTimes" />
					</td>
					<%-- 已出险次数 --%>
					<td class="right">
						<%-- 出险信息画面 --%>
						<%@include file="/pages/GAA/regist/GAAExistRegist.jsp"%>
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
				<c:choose>
					<c:when test="${not empty requestScope.coinsFlag}">
						<input type="hidden" name="coinsFlag" value="<c:out value='${requestScope.coinsFlag}'/>">
					</c:when>
					<c:otherwise>
						<input type="hidden" name="coinsFlag" value="0">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${not empty requestScope.shareHolderFlag}">
						<input type="hidden" name="shareHolderFlag" value="<c:out value='${requestScope.shareHolderFlag}'/>">
					</c:when>
					<c:otherwise>
						<input type="hidden" name="shareHolderFlag" value="0">
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${not empty requestScope.tempReinsFlag}">
						<input type="hidden" name="tempReinsFlag" value="<c:out value='${requestScope.tempReinsFlag}'/>">
					</c:when>
					<c:otherwise>
						<input type="hidden" name="tempReinsFlag" value="0">
					</c:otherwise>
				</c:choose>
				<tr>
					<td class="left">
						<s:text name="db.prpCprofitDetail.policyno" />
					</td>
					<%-- 保单号 --%>
					<td class="right">
						<input type=text name="prpLcompensatePolicyNo" style="width: 175px" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLcompensate.policyNo}' />">
						<img name="btRelate" src="/claim/images/butRelate.gif" width="54" height="17" border="0" onclick="relate(fm.prpLcompensatePolicyNo.value,fm.registno.value);return false;">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.insuredName" />
					</td>
					<%-- 被保险人 --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateInsuredName" readonly="true" value="<c:out value='${requestScope.prpLcompensate.insuredName}' />">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLCMain.businessNature" />
					</td>
					<%-- 业务来源 --%>
					<td class="right">
						<c:choose>
							<c:when test="${requestScope.prpLcompensate.businessNature=='0'}">
								<s:text name="compensate.directBusiness" />
							</c:when>
							<%-- 直接业务 --%>
							<c:otherwise>
								<s:text name="compensate.agencyBusiness" />
							</c:otherwise>
							<%-- 代理业务 --%>
						</c:choose>
					</td>
					<td class="left">
						<s:text name="db.prpLlawsuit.currency" />
					</td>
					<%-- 币别 --%>
					<td class="right">
						<c:out value='${requestScope.prpLcompensate.currency}' />
					</td>
					<td class="left">
						<s:text name="regist.prpLregist.sumAmount" />
					</td>
					<%-- 保险金额 --%>
					<td class="right">
						<fmt:formatNumber value="${requestScope.prpLcompensate.sumAmount}" pattern="#" type="number" />
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="regist.prpLregist.insuranceTime" />
					</td>
					<%-- 保险期间 --%>
					<td class="right" colspan="3">
						<rc:rcDate name="prpLcompensateStartDate" value="${requestScope.prpLcompensate.startDate}" class="readonly" wdatePicker="false"  style="width: 80px;" />
						${startHour }
						<rc:rcDate name="prpLcompensateEndDate" value="${requestScope.prpLcompensate.endDate}" class="readonly" wdatePicker="false"  style="width: 80px;" />
						${endHour}
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
						<s:text name="regist.prpLregist.damageTime" />
					</td>
					<%-- 出险时间 --%>
					<td class="right">
						<rc:rcDate name="prpLcompensateDamageStartDate" style="width:100" value='${requestScope.prpLcompensate.damageStartDate}'/>
						<s:text name='regist.prpLregist.date'/> <c:out value='${requestScope.prpLcompensate.damageStartHour}' /> <s:text name="regist.prpLregist.hour"/> <c:out value='${requestScope.prpLcompensate.damageStartMinute}' /> <s:text name="regist.prpLregist.minute"/>
						<%-- 日 --%>
						<%-- 时 --%>
						<%-- 分 --%>
						<input type="hidden" name="DamageStartDate" value="<fmt:formatDate value='${requestScope.prpLcompensate.damageStartDate}' pattern='yyyy-MM-dd'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageAddress" />
					</td>
					<%-- 出险地点 --%>
					<td class="right">
						<c:out value='${requestScope.prpLcompensate.damageAddress}' />
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.damageName" />
					</td>
					<%-- 出险原因 --%>
					<td class="right">
						<input type=text class="codecode" name="prpLcompensateDamageCode" style="width: 20%;" title="<s:text name='db.prpLregist.damageCode'/>" value="${prpLcompensate.damageCode}"
							ondblclick="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"
							onchange="code_CodeSelect(this, 'DamageCode','0,1','Y','Y',fm.prpLcompensateRiskCode.value);"><%-- 出险原因 --%>
						<input type=text class="codecode" name="prpLcompensateDamageName" title="<s:text name='db.prpLregist.damageCode'/>" style="width: 60%;" value="${prpLcompensate.damageName}"
							ondblclick="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);" onkeyup="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"
							onchange="code_CodeSelect(this, 'DamageCode','-1,0','Y','N',fm.prpLcompensateRiskCode.value);"><%-- 出险原因 --%>
						<img src="${ctx }/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle"> <img src="${ctx }/images/bgMarkMustInput.jpg">
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
						<s:text name="db.prpLCMain.claimTimes" />
					</td>
					<%-- 赔付次数 --%>
					<td class="right">
						<input type="text" name="prpLcompensateTimes" class="readonly" readonly value="<c:out value='${requestScope.prpLcompensate.times}'/>">
					</td>
					<c:choose>
					   <c:when test="${not empty requestScope.prpLcompensate.mutualCompensateNo || not empty mutualCompensateNoList}">
						  <td class="left">互沖計算書號碼</td>
						  <td class="right">
							  <c:choose>
							     <c:when test="${param.editType=='ADD'}">
							        <select name="prpLcompensateMutualCompensateNo" onchange="getMutualCompe(this);">
							            <c:if test="${empty param.prpLcompensateMutualCompensateNo}">
							                 <option value="" selected="selected"></option>
							            </c:if>
								        <c:forEach items="${requestScope.mutualCompensateNoList}" var="mutualCompensateNo">
								           <option value="${mutualCompensateNo}" <c:if test="${mutualCompensateNo==param.prpLcompensateMutualCompensateNo}">selected="selected"</c:if>>${mutualCompensateNo}</option>
								        </c:forEach>
							        </select>
							     </c:when>
							     <c:otherwise>
							        <input type="text" name="prpLcompensateMutualCompensateNo" class="readonly" readonly value="<c:out value='${requestScope.prpLcompensate.mutualCompensateNo}'/>">
							     </c:otherwise>
							  </c:choose>
						  </td>
					   </c:when>
					   <c:otherwise>
					      <td class="left"></td>
					      <td class="right"></td>
					   </c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${requestScope.recaseFlag=='0' || param.editType=='SHOW'}">
							<td class="left"><s:text name="compensate.closedType" /><%-- 结案类型 --%></td>
							<td class="right">
								<s:select id="prpLcompensateFinallyFlag" name="prpLcompensateFinallyFlag" list="#request.closedTypeList" value="#request.prpLcompensate.finallyFlag" listKey="key" listValue="value" onchange="return changeFinallyFlagAndLltextContent();" style="width: 110px;"  ></s:select>
							</td>
						</c:when>
						<c:otherwise>
							<td class="left">
								<s:text name="commonLiab.compensate.reopenCaseType" /><%-- 重开赔案结案类型 --%>
							</td>
							<td class="right">
								<input type="hidden" name="prpLcompensateFinallyFlag" value="1"><s:text name="claim.case" /><%-- 结案 --%>
							</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td class="left">
						<c:if test="${requestScope.coinsFlag=='1'}">
							<s:text name="commonAcci.compensate.whetherPaidReparat" />
						</c:if>
						<%-- <s:text name="commonAcci.compensate.receiveCustomerTime" /> 接收客户索赔申请时间  --%>
					</td>
					<td class="right" >
						<c:if test="${requestScope.coinsFlag=='1'}">
							<input type="radio" name="isPayForOther" onclick="changeFinallyFlag(1);" <c:if test="${requestScope.prpLcompensate.isPayForOther=='1'}"><c:out value="checked"/></c:if> value="1">
							<s:text name="regist.prpLregist.yes" />
							<%-- 是 --%>
							<input type="radio" name="isPayForOther" onclick="changeFinallyFlag(0);" <c:if test="${requestScope.prpLcompensate.isPayForOther=='0'}"><c:out value="checked"/></c:if> value="0">
							<s:text name="regist.prpLregist.no" />
							<%--  --%>
						</c:if>
						<rc:rcDate name="startApplyPayDate" style="width:50%;display: none;" value="${requestScope.prpLclaim.startApplyPayDate}" class="query" />
					</td>
					<td class="left">
						<s:text name="commonAcci.claim.involvedLitigat" />
					</td>
					<%-- 是否涉及诉讼 --%>
					<td class="right">
						<c:choose>
							<c:when test="${empty requestScope.prpLclaim.referLawFlag||requestScope.prpLclaim.referLawFlag eq '0' || requestScope.prpLclaim.referLawFlag eq '1'}">
								<select name="referLawFlag" <c:if test="${requestScope.recaseFlag=='0'||param.editType=='SHOW'}"><c:out value="disabled=\"true\""/></c:if>>
									<option value="0" <c:if test="${requestScope.prpLclaim.referLawFlag=='0'}"><c:out value="selected"/></c:if>>
										<s:text name="regist.prpLregist.no" /><%-- 否 --%>
									</option>
									<option value="1" <c:if test="${requestScope.prpLclaim.referLawFlag=='1'}"><c:out value="selected"/></c:if>>
										<s:text name="regist.prpLregist.yes" /><%--  是--%>
									</option>
								</select>
							</c:when>
							<c:otherwise>
								<select name="referLawFlag" disabled="true">
									<option value="${requestScope.prpLclaim.referLawFlag}" selected >
										<s:text name="regist.prpLregist.yes" />
									</option>
								</select>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="left">
						<s:text name="claim.possibleRec" />
					</td>
					<%-- 是否可能有追偿 --%>
					<td class="right">
						<c:choose>
							<c:when test="${empty requestScope.prpLclaim.replevyFlag||requestScope.prpLclaim.replevyFlag=='0'||requestScope.prpLclaim.replevyFlag=='1'}">
								<select name="replevyFlag">
									<option value="0" <c:if test="${requestScope.prpLclaim.replevyFlag=='0'}"><c:out value="selected"/></c:if>>
										<s:text name="regist.prpLregist.no" /><%-- 否 --%>
									</option>
									<option value="1" <c:if test="${requestScope.prpLclaim.replevyFlag=='1'}"><c:out value="selected"/></c:if>>
										<s:text name="regist.prpLregist.yes" /><%--  是--%>
									</option>
								</select>
							</c:when>
							<c:otherwise>
								<select name="replevyFlag" disabled="true">
									<option value="${requestScope.prpLclaim.replevyFlag}" <c:out value="selected"/>>
										<s:text name="regist.prpLregist.yes" /><%--  是--%>
									</option>
								</select>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td class="left">
						追償說明：
					</td>
					<td class="right" colspan="3">
						<input name="prpLcompensateReplevyRemark" class="common" value="${prpLclaim.replevyRemark }">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.claimDate" />：<%-- 立案日期  --%>
					</td>
					<td class="right" >
						<rc:rcDate name="prpLcompensateClaimDate" title="立案日期" style="width:187px" value="${requestScope.prpLcompensate.claimDate}" format="yyyy-MM-dd HH:mm:ss" readonly="true" wdatePicker="false" class="readonly"/>
					</td>
					<td class="left">
						收件日期：
					</td>
					<td class="right">
						<rc:rcDate name="prpLcompensateReceiptDate" title="收件日期" style="width:187px" value="${prpLcompensate.receiptDate}" format="yyyy-MM-dd HH:mm" defaultValue="0"/>
					</td>
					<td class="left">
						<s:text name="title.compensateEdit.speedFlag"/>：<%-- 赔款速度  --%>
					</td>
					<td class="right">
						<s:select name="prpLcompensateSpeedFlag" value="#request.prpLcompensate.speedFlag" list="#request.speedFlagList" listKey="key" listValue="value" ></s:select>
					</td>
				</tr>
				<tr>
					<td class="left">是否有殘餘物：</td>
					<td class="right">
						<s:select name="prpLcompensateRemnants" list="#{'0':'否','1':'是'}" value="#request.prpLcompensate.remnants" listKey="key" listValue="value" />
					</td>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<input type="hidden" name="prpLcompensateIndemnityDuty" style="width: 100px" class="readonly" readonly="true" value="<c:out value='${requestScope.prpLcompensate.indemnityDuty}' />">
				<input type=hidden name="prpLcompensateIndemnityDutyName" style="width: 100px" class="readonly" readonly="true" value="">
				<input type="hidden" name="prpLcompensateIndemnityDutyRate" style="width: 100px" class="readonly" readonly="true" value="0">
			</table>
		</td>
	</tr>
</table>
<br>
<script language="javascript">
/***
 * 互沖計算書切換時，獲取要互沖的計算書的訊息
 * @param field
 */
function getMutualCompe(field){
	if($.trim($(field).val())!=""){
		var url = "${ctx}/compensate/beforeCompeMutualImpulse.do?ClaimNo=${param.ClaimNo}&caseType=${param.caseType}&swfLogFlowID=${param.swfLogFlowID}&swfLogLogNo=${param.swfLogLogNo}&status=0&riskCode=${param.riskCode}&editType=ADD&nodeType=compe&businessNo=${param.businessNo}&keyIn=${param.keyIn}&policyNo=${param.policyNo}&modelNo=${param.modelNo}&nodeNo=${param.nodeNo}&dfFlag=${param.dfFlag}&actorId=${param.actorId}&processId=${param.processId}&compeCount=${param.compeCount}";
		url += "&prpLcompensateMutualCompensateNo="+$(field).val();
		window.location.href = url;
	}
}
</script>
