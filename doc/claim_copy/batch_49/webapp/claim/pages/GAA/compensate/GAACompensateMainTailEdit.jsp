<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面Tail[ 实赔 ]
* AUTHOR     :理赔组
* CREATEDATE :2013-07-11
* MODIFYLIST :  Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="commonLiab.compensate.paymentCurrency" />
					</td>
					<%--  赔款币种--%>
					<td class="right">
						<input class="readonly" readonly name="MergeCurrency" value="${prpLcompensate.currency}" style="width: 60px">
						&nbsp;
						<input class="readonly" readonly name="MergeCurrencyName" value="${prpLcompensate.currencyName}" style="width: 120px">
					</td>
					<input type="hidden" class="bigbutton" name="btnCurrencyTotle" value="<s:text name='common.modifySumClaim.summary'/>" onclick="alert('分币别汇总');"><%--分币别汇总--%>
					<td class="left">
						<input class='readonly' readonly type='text' value='<s:text name="undwrt.LossCompensation"/>' title='<s:text name='prompt.compensate.addmessage2'/>'><%--标的损失赔款之和，不包括费用！ --%>
					</td>
					<%--标的损失赔款  --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateSumDutyPaid" readonly="true" style="width: 150px" value="<fmt:formatNumber value="${prpLcompensate.sumDutyPaid}" pattern="#" />">
					</td>
					<td class="left">
						<input class='readonly' readonly type='text' value='<s:text name="db.prpLafterward.chargeAmount"/>' title='<s:text name='prompt.compensate.addmessage3'/>'><%--费用金额之和，不包括赔款金额！ --%>
					</td>
					<%-- 费用金额 --%>
					<td class="right">
						<input type=text name="prpLcompensateSumNoDutyFee" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value="${prpLcompensate.sumNoDutyFee}" pattern="#" />">
					</td>
				</tr>
				<tr>
					<td class="left">
						<input class='readonly' readonly type='text' value='<s:text name="undwrt.CaseTotal"/>' title='<s:text name='prompt.compensate.addmessage4'/>'><%--赔款合计与费用之和！--%>
					</td>
					<%--本案合计  --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateSumPaid" readonly="true" style="width: 150px" value="<fmt:formatNumber value="${prpLcompensate.sumPaid}" pattern="#" />">
					</td>
					<td class="left">
						<input class='readonly' readonly type='text' value='<s:text name="undwrt.PaymentAmount"/>' title='<s:text name='prompt.compensate.addmessage5'/>'><%--预付赔款金额之和！--%>
					</td>
					<%-- 已预付赔款金额 --%>
					<td class="right">
						<input type=text name="prpLcompensateSumPrePaid" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value="${requestScope.sosMedicFee}" pattern="#" />">
					</td>
					<td class="left" style="width: 13%">
						<input class='readonly' readonly style="width: 100%" type='text' value='<s:text name='undwrt.LossCompensation'/>' title='<s:text name='prompt.compensate.addmessage6'/>' /><%--标的损失赔款减去已预付赔款！--%>
					</td>
					<%--本次标的损失赔款  --%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateSumThisPaid" readonly="true" style="width: 150px" value="<fmt:formatNumber value="${prpLcompensate.sumThisPaid}" pattern="#" />">
					</td>
					<td class="left" style="display: none">
						<s:text name="claim.salvage" />
					</td>
					<%--残值  --%>
					<td class="right" style="display: none">
						<input class="readonly" type=text readonly="true" style="width: 150px" name="prpLcompensateSumRest" value="<fmt:formatNumber value="${prpLcompensate.sumRest}" pattern="#" />">
					</td>
				</tr>
				<c:if test="${not empty requestScope.coinsFlag}">
					<c:if test="${requestScope.coinsFlag=='1'||requestScope.coinsFlag=='3'||requestScope.coinsFlag=='2'}">
						<tr style="display: none;">
							<td class="left">
								<input class='readonly' readonly type='text' value='<s:text name="compensate.ourAmount"/>' title='<s:text name='compensate.ourAmount'/>'><%--我方赔款金额--%>
							</td>
							<%-- 我方赔款金额 --%>
							<td class="right" colspan="2">
								<input type=text name="prpLcompensateSumCoinUs" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value="${prpLcompensate.sumCoinUs}" pattern="#" />">
							</td>
							<td class="left">
								<input class='readonly' readonly type='text' value='我方費用金額' title='<s:text name='compensate.weCharge'/>'><%--我方费用金额--%>
							</td>
							<%-- 我方费用金额 --%>
							<td class="right" colspan="2">
								<input class="readonly" type=text name="prpLcompensateSumCoinUsFee" readonly="true" value="<fmt:formatNumber value="${prpLcompensate.sumCoinUsFee}" pattern="#" />">
							</td>
						</tr>
						<tr style="display: none;">
							<td class="left">
								<input class='readonly' readonly type='text' value='<s:text name="compensate.amountPaid"/>' title='<s:text name='compensate.amountPaid'/>'><%--代付赔款金额--%>
							</td>
							<%-- 代付赔款金额 --%>
							<td class="right" colspan="2">
								<input type=text name="prpLcompensateSumCoinForOther" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value="${prpLcompensate.sumCoinForOther}" pattern="#" />">
								<input type=hidden name="prpLcompensateSumCoinForOtherBak" class="readonly" readonly="true" style="width: 140px"
									value="<fmt:formatNumber value="${prpLcompensate.sumCoinForOther}" pattern="#" />">
							</td>
							<td class="left">
								<input class='readonly' readonly type='text' value='<s:text name="compensate.payFee"/>' title='<s:text name='compensate.payFee'/>'><%--代付费用金额--%>
							</td>
							<%--代付费用金额  --%>
							<td class="right" colspan="2">
								<input class="readonly" type=text name="prpLcompensateSumCoinForOtherFee" readonly="true" value="<fmt:formatNumber value="${prpLcompensate.sumCoinForOtherFee}" pattern="#" />">
								<input class="readonly" type=hidden name="prpLcompensateSumCoinForOtherFeeBak" readonly="true" value="<fmt:formatNumber value="${prpLcompensate.sumCoinForOtherFee}" pattern="#" />">
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
						<input name="prpLcompensateHandlerCode" class="readonly" readonly=true style="width: 40%" value="${prpLcompensate.handlerCode}" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y','Y');"
							onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y','Y');">
						<input name="prpLcompensateHandlerName" class="readonly" readonly=true style="width: 50%" title="<s:text name='db.prpLarrearageNew.handlerCode'/>" value="${prpLcompensate.handlerName}"
							ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');"><%--经办人--%>
					</td>
					<td class="left">
						<s:text name="compensate.actionDate" />
					</td>
					<%--  操作日期--%>
					<td class="right">
						<rc:rcDate class="readonly" readonly="true"  name="prpLcompensateStatisticsYM" value="${prpLcompensate.statisticsYM}"/>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLcompensate.remark" />
					</td>
					<%--备注  --%>
					<td class="right" colspan="5">
						<textarea style="width: 600px; overflow-x: visible;" name='prpLcompensateRemark' rows=4 cols=40 title="<s:text name='db.prpDshipclass.remark'/>">${prpLcompensate.remark}</textarea><%--备注--%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<input type="hidden" name="prpLcompensateChecker1" value="${prpLcompensate.checker1}">
			</table>
		</td>
	</tr>
</table>
<br>
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