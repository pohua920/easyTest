<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面Tail[ 实赔 ]
* AUTHOR     : 中科软
* MODIFYLIST : Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="compensate.paymentCrrency" />
					</td>
					<%-- 赔款币种 --%>
					<td class="right">
						<input class="readonly" readonly name="MergeCurrency"
							style="width: 34%" value="${prpLcompensate.currency}">
						<input class="readonly" readonly name="MergeCurrencyName"
							style="width: 56%" value="${prpLcompensate.currencyName}">
					</td>
					<td class="left">
						<input type="hidden" name="btnCurrencyTotle" class="bigbutton"
							value="<s:text name='common.modifySumClaim.summary'/>" onclick="alert('<s:text name='common.modifySumClaim.summary'/>');"><%--分币别汇总--%>
					</td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<input class='readonly' readonly type='text' value='<s:text name="undwrt.LossCompensation"/>' title='<s:text name='prompt.compensate.addmessage2'/>'><%--标的损失赔款之和，不包括费用！ --%>
					</td>
					<%--标的损失赔款  --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateSumDutyPaid"
							readonly="true"
							value="<fmt:formatNumber value='${prpLcompensate.sumDutyPaid}' pattern='#'/>">
					</td>
					<td class="left">
						<input class='readonly' readonly type='text'
							value='<s:text name="certainLoss.prpLacciCheck.prpLacciCheckCheckCostCount"/>'
							title='<s:text name='prompt.compensate.addmessage3'/>'><%--费用金额之和，不包括赔款金额！ --%>
					</td>
					<%-- 费用金额 --%>
					<td class="right">
						<input type=text name="prpLcompensateSumNoDutyFee"
							class="readonly" readonly="true" style="width: 140px"
							value="<fmt:formatNumber value='${prpLcompensate.sumNoDutyFee}' pattern='#'/>">
					</td>
					<td class="left">
						<input class='readonly' readonly type='text'
							value='<s:text name="undwrt.CaseTotal"/>' title='<s:text name='prompt.compensate.addmessage4'/>'><%--赔款合计与费用之和！--%>
					</td>
					<%--  本案合计--%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateSumPaid"
							readonly="true"
							value="<fmt:formatNumber value='${prpLcompensate.sumPaid}' pattern='#'/>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<input class='readonly' readonly type='text'
							value='<s:text name="undwrt.PaymentAmount"/>' title='<s:text name='prompt.compensate.addmessage5'/>'><%--预付赔款金额之和！--%>
					</td>
					<%-- 已预付赔款金额 --%>
					<td class="right">
						<input type=text name="prpLcompensateSumPrePaid" class="readonly"
							readonly="true" style="width: 140px"
							value="<fmt:formatNumber value='${requestScope.sosMedicFee}' pattern='#'/>">
					</td>
					<td class="left">
						<input class='readonly' readonly style="width: 100%" type='text' value='<s:text name='undwrt.LossCompensation'/>' title='<s:text name='prompt.compensate.addmessage6'/>' /><%--标的损失赔款减去已预付赔款！--%>
					</td>
					<%--本次标的损失赔款  --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateSumThisPaid"
							readonly="true"
							value="<fmt:formatNumber value='${prpLcompensate.sumThisPaid}' pattern='#'/>">
					</td>
					<td class="left">
						<s:text name="claim.salvage" />
					</td>
					<%-- 残值 --%>
					<td class="right">
						<input class="readonly" type=text readonly="true"
							name="prpLcompensateSumRest"
							value="<fmt:formatNumber value='${prpLcompensate.sumRest}' pattern='#'/>">
					</td>
				</tr>
				<c:if test="${not empty requestScope.coinsFlag}">
					<c:if
						test="${requestScope.coinsFlag == '1' || requestScope.coinsFlag == '2' || requestScope.coinsFlag == '3'}">
						<tr style="display: none;">
							<td class="left">
								<input class='readonly' readonly type='text'
									value='<s:text name="compensate.ourAmount"/>' title='<s:text name='compensate.ourAmount'/>'><%--我方赔款金额--%>
							</td>
							<%-- 我方赔款金额 --%>
							<td class="right" colspan="2">
								<input type=text name="prpLcompensateSumCoinUs" class="readonly"
									readonly="true" style="width: 140px"
									value="<fmt:formatNumber value='${prpLcompensate.sumCoinUs}' pattern='#'/>">
							</td>
							<td class="left">
								<input class='readonly' readonly type='text'
									value='<s:text name="compensate.weCharge"/>' title='<s:text name='compensate.weCharge'/>'><%--我方费用金额--%>
							</td>
							<%-- 我方费用金额 --%>
							<td class="right" colspan="2">
								<input class="readonly" type=text
									name="prpLcompensateSumCoinUsFee" readonly="true"
									value="<fmt:formatNumber value='${prpLcompensate.sumCoinUsFee}' pattern='#'/>">
							</td>
						</tr>
						<tr style="display: none;">
							<td class="left">
								<input class='readonly' readonly type='text'
									value='<s:text name="compensate.amountPaid"/>' title=''<s:text name='compensate.amountPaid'/>'><%--代付赔款金额--%>
							</td>
							<%-- 代付赔款金额 --%>
							<td class="right" colspan="2">
								<input type=text name="prpLcompensateSumCoinForOther"
									class="readonly" readonly="true" style="width: 140px"
									value="<fmt:formatNumber value='${prpLcompensate.sumCoinForOther}' pattern='#'/>">
								<input type=hidden name="prpLcompensateSumCoinForOtherBak"
									class="readonly" readonly="true" style="width: 140px"
									value="<fmt:formatNumber value='${prpLcompensate.sumCoinForOther}' pattern='#'/>">
							</td>
							<td class="left">
								<input class='readonly' readonly type='text'
									value='<s:text name="compensate.payFee"/>' title='<s:text name='compensate.payFee'/>'><%--代付费用金额--%>
							</td>
							<%-- 代付费用金额 --%>
							<td class="right" colspan="2">
								<input class="readonly" type=text
									name="prpLcompensateSumCoinForOtherFee" readonly="true"
									value="<fmt:formatNumber value='${prpLcompensate.sumCoinForOtherFee}' pattern='#'/>">
								<input class="readonly" type=hidden
									name="prpLcompensateSumCoinForOtherFeeBak" readonly="true"
									value="<fmt:formatNumber value='${prpLcompensate.sumCoinForOtherFee}' pattern='#'/>">
							</td>
						</tr>
					</c:if>
				</c:if>
				<tr>
					<td class="left">
						<s:text name="compensate.businessAgent" />
					</td>
					<%-- 业务经办人 --%>
					<td class="right">
						<input name="prpLcompensateHandlerCode" class="codecode"
							style="width: 40%" value="${prpLcompensate.handlerCode}"
							ondblclick="code_CodeSelect(this, 'HanderCode');"
							onkeyup="code_CodeSelect(this, 'HanderCode');">
						<input name="prpLcompensateHandlerName" class="codename"
							style="width: 50%" title="<s:text name='db.prpLarrearageNew.handlerCode'/>"
							value="${prpLcompensate.handlerName}"
							ondblclick="code_CodeSelect(this, 'HanderCode','-1','always','none','post');"
							onkeyup="code_CodeSelect(this, 'HanderCode','-1','always','none','post');"><%--经办人--%>
					</td>
					<td class="left">
						<s:text name="workflow.countDate" />
					</td>
					<%-- 统计年月 --%>
					<td class="right">
						<rc:rcDate name="prpLcompensateStatisticsYM" title="<s:text name='db.prpLclaim.statisticsYM'/>" readonly="true" style="width:90px" value="${prpLcompensate.statisticsYM}" />
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLcomponent.remark" />
					</td>
					<%--备注  --%>
					<td class="right" colspan="5">
						<textarea style="width: 600px; overflow-x: visible;"
							name='prpLcompensateRemark' rows=4 cols=40 title="<s:text name='db.prpDshipclass.remark'/>">${prpLcompensate.remark}</textarea>
					</td>
				</tr>
				<input type="hidden" name="prpLcompensateChecker1"
					value="${prpLcompensate.checker1}">
			</table>
		</td>
	</tr>
</table>
