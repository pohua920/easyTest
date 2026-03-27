<!--
****************************************************************************
* DESC       ：添加主信息子块界面页面[ 立案 ]
* AUTHOR     ： 理赔组
* CREATEDATE ： 2004-05-12
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
-->
<%@ include file="/common/taglibs.jsp"%>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="common">
	<tr>
		<td width="30%">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="12">
						<img src="${ctx }/images/bgBarLeft.gif" width="12" height="19">
					</td>
					<td class="formtitle">
						<s:text name="prepay.paymentRegistration" />
					</td>
					<%-- 预付赔款登记 --%>
					<td width="11">
						<img src="${ctx }/images/bgBarRight.gif" width="11" height="19">
					</td>
				</tr>
			</table>
		</td>
		<td>
			<input type="button" name="message" value="<s:text name='button.disMessage.value'/>" class="bigbutton" onclick="openWinSave(}">
		</td>
		<td>
			<input type="button" name="messageView" value="<s:text name='button.viewMessage.value'/>" class="bigbutton" onclick="openWinQuery(}">
		</td>
		<td width="70%" align="right">
			<font color="#666666"> <s:text name="scheduleObject.note1" />“<font color="#FF0000">*</font>”<s:text name="scheduleObject.note2" />，“<img src="${ctx }/images/bgDoubleClick2.gif" width="13"
				height="13" align="absbottom">”<%--讨论留言 --%> <%-- 查看留言 --%>
				<%-- 注: --%>
				<%-- 为必选项 --%> <s:text name="scheduleObject.note3" />。
			</font>
		</td>
		<%--为双击选择项  --%>
	</tr>
</table>
<table border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#2D8EE1" class="title" width="100%">
	<tr>
		<td class="title" colspan="4" style="width: 100%">
			<s:text name="prepay.paymentRegistration" />
			<%-- 预付赔款登记 --%>
			<input type="hidden" name="prpLprepayRiskCode" value="${prpLprepay.riskCode}">
			<input type="hidden" name="prpLprepayOperatorCode" value="${prpLprepay.operatorCode}">
			<input type="hidden" name="prpLprepayMakeCom" value="${prpLprepay.makeCom}">
			<input type="hidden" name="prpLprepayTypeForDriver" value="claim">
			<input type="hidden" name="prpLprepayInputDate" value="${prpLprepay.inputDate}">
			<input type="hidden" name="prpLprepayLicenseColorCode" value="${prpLprepay.licenseColorCode}">
			<input type="hidden" name="prpLprepayCarKindCode" value="${prpLprepay.carKindCode}">
			<input type="hidden" name="prpLprepaySumPremium" value="${prpLprepay.sumPremium}">
			<input type="hidden" name="prpLprepayPolicyCurrency">
			<input type="hidden" name="prpLprepayArrearageTimes" value="0">
			<input type="hidden" name="prpLprepaySumArrearage" value="0">
			<input type="hidden" name="prpLprepaySumBeforePrePaid" value="0">
			<input type="hidden" name="prpLprepayBlockUpTimes" value="0">
			<input type="hidden" name="prpLprepaySumTotalPrepaid" value="0">
			<input type="hidden" name="prpLprepayApproverCode">
			<input type="hidden" name="prpLprepayUnderWriteCode">
			<input type="hidden" name="prpLprepayUnderWriteName">
			<input type="hidden" name="prpLprepayUnderWriteEndDate">
			<input type="hidden" name="sumClaim" value="${sumClaim }">
			<input type="hidden" name="percent" value="${sysconst_PrepayPercent }">
			<input type="hidden" name="prpLprepayUnderWriteFlag" value="${prpLprepay.underWriteFlag}">
			<input type="hidden" name="prpLprepayFlag" value="${prpLprepay.flag}">
			<input type="hidden" name="prpLprepayComCode" value="${prpLprepay.comCode}">
			<input type="hidden" name="prpLprepayHandler1Code" value="${prpLprepay.handler1Code}">
			<input type="hidden" name="prpLprepayClauseName" value="${prpLprepay.clauseName}">
			<input type="hidden" name="prpLprepayStartDate" value="${prpLprepay.startDate} 日 0 时 至 ${prpLprepay.endDate} 日 24 时止">
			<input type="hidden" name="prpLprepayLicenseNo" value="${prpLprepay.licenseNo}">
			<input type="hidden" name="prpLprepayLicenseColor" value="${prpLprepay.licenseColor}">
			<input type="hidden" name="prpLprepayCarKind" value="${prpLprepay.carKind}">
			<input type="hidden" name="prpLprepayBrandName" value="${prpLprepay.brandName}">
			<input type="hidden" name="prpLprepayEngineNo" value="${prpLprepay.engineNo}">
			<input type="hidden" name="prpLprepayFrameNo" value="${prpLprepay.frameNo}">
			<input type="hidden" name="prpLprepayDamageStartDate" value="${prpLprepay.damageStartDate} 日 ${prpLprepay.damageStartHour} 时 ${prpLprepay.damageStartMinute} 分">
			<input type="hidden" name="prpLprepayDamageAddress" value="${prpLprepay.damageAddress}">
			<input type="hidden" name="prpLprepaySumClaim" value="${prpLprepay.sumClaim}">
			<input type="hidden" name="prpLprepaySumAmount" value="${prpLprepay.sumAmount}">
		</td>
	</tr>
	<tr>
		<td class="title" style="width: 15%">
			<s:text name="prepay.paidAdvance" />
			：
		</td>
		<%--预付赔款号  --%>
		<td class="input" style="width: 35%">
			<input type=text name="prpLprepayPreCompensateNo" title="预付赔款号" maxlength="22" class="readonly" readonly="true" value="${prpLprepay.preCompensateNo}">
		</td>
		<td class="title" style="width: 15%">
			<s:text name="check.claimNum" />
			：
		</td>
		<%-- 赔案号 --%>
		<td class="input" style="width: 35%">
			<input type=text name="prpLprepayClaimNo" title="赔案号" maxlength="22" class="readonly" readonly="true" value="${prpLprepay.claimNo}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLlawsuit.policyNo" />
			：
		</td>
		<%--  保单号--%>
		<td class="input" colspan="3">
			<input type=text name="prpLprepayPolicyNo" class="readonly" readonly="true" style="width: 140px" value="${prpLprepay.policyNo}">
			<input type="image" name="btRelate" src="${ctx}/images/butRelate.gif" align="middle" width="54" height="17" border="0" onclick="relate(fm.PolicyNo.value};">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="regist.prpLregist.currency" />
			：
		</td>
		<%--币别  --%>
		<td class="input">
			<input name="prpLprepayCurrency" class="readonly" readonly style="width: 40px" value="${prpLprepay.currency}">
			<input name="prpLprepayCurrencyName" class="readonly" readonly style="width: 100px" value="${prpLprepay.currencyName}">
		</td>
		<td class="title">
			<s:text name="db.prpLprepay.sumPrepaid" />
			：
		</td>
		<%-- 预赔金额 --%>
		<td class="input">
			<input name="prpLprepaySumPrePaid" type="text" class="readonly" readonly style="width: 130" value="${prpLprepay.sumPrePaid}">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLclaim.comCode" />
			：
		</td>
		<%-- 业务归属机构 --%>
		<td class="input">
			<input type=text name="prpLprepayComName" class="readonly" readonly="true" value="${prpLprepay.comName}">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.handler1Code" />
			：
		</td>
		<%-- 归属业务员 --%>
		<td class="input">
			<input type=text name="prpLprepayHandler1Name" class="readonly" readonly="true" value="${prpLprepay.handler1Name}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.handler1Name" />
			：
		</td>
		<%-- 经办人 --%>
		<td class="input">
			<input name="prpLprepayHandlerCode" class="readonly" readonly style="width: 90px" value="${prpLprepay.handlerCode}">
			<input name="prpLprepayHandlerName" class="readonly" readonly style="width: 120px" value="${prpLprepay.handlerName}">
		</td>
		<td class="title">
			<s:text name="db.prpLclaim.statisticsYM" />
			：
		</td>
		<%-- 统计年月 --%>
		<td class="input">
			<input type="text" class="readonly" readonly style="width: 130" name="prpLprepayStatisticsYM" value="${prpLprepay.statisticsYM}">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
	</tr>
</table>
