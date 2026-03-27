<%--
****************************************************************************
* DESC       ：添加主信息子块界面页面Tail[ 实赔 ]
* AUTHOR     :理赔组
* CREATEDATE :2004-05-12
* MODIFYLIST :  Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="commonAcci.compensate.responsibilityPayment" />
					</td>
					<%--责任赔款币种--%>
					<td class="right">
						<input class="readonly" readonly name="MergeCurrency" value="${prpLcompensate.currency}" style="width: 10%;">
						&nbsp;
						<input class="readonly" readonly name="MergeCurrencyName" value="${prpLcompensate.currencyName}" style="width: 70%;">
						<input type="hidden" name="btnCurrencyTotle" value="分币别汇总" onclick="alert('分币别汇总');">
					</td>
					<td class="left"></td>
					<td class="right"></td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<input class='readonly' readonly type='text' value='标的损失赔款' title='标的损失赔款之和，不包括费用！'>
					</td>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateSumDutyPaid" readonly="true" value="<fmt:formatNumber value ='${prpLcompensate.sumDutyPaid}' pattern='#'/>">
					</td>
					<td class="left">
						<input class='readonly' readonly type='text' value='费用金额' title='费用金额之和，不包括赔款金额！'>
					</td>
					<td class="right">
						<input type=text name="prpLcompensateSumNoDutyFee" class="readonly" readonly="true" value="<fmt:formatNumber value ='${prpLcompensate.sumNoDutyFee}' pattern='#'/>">
					</td>
					<td class="left">
						<s:text name="commonAcci.compensate.businessReview" />
					</td>
					<%--业务审核人--%>
					<td class="right">
						<input name="prpLcompensateHandlerCode" class="codecode" style="width: 40%" value="${prpLcompensate.handlerCode}" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');" onchange="code_CodeSelect(this, 'handerCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');">
						<input name="prpLcompensateHandlerName" class="codename" style="width: 50%" title="審核人" value="${prpLcompensate.handlerName}" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');" onchange="code_CodeSelect(this, 'handerCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="compensate.paymentAmount" />
					</td>
					<%--赔款合计--%>
					<td class="right">
						<input class="readonly" type=text name="prpLcompensateSumPaid" readonly="true" style="width: 150px" value="<fmt:formatNumber value ='${prpLcompensate.sumPaid}' pattern='#'/>">
					</td>
					<td class="left">
						<s:text name="db.prpLcompensate.sumPrePaid" />
					</td>
					<%--已预付赔款--%>
					<td class="right">
						<input type=text name="prpLcompensateSumPrePaid" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value='${sosMedicFee }' pattern='#'/>">
					</td>
					<td class="left">
						<s:text name="commonAcci.compensate.paymentAmount" />
					</td>
					<%--本次给付金额--%>
					<td class="right">
						<input name="prpLcompensateSumThisPaid" type=text class="readonly" readonly="true" style="width: 150px" value="<fmt:formatNumber value ='${prpLcompensate.sumThisPaid}' pattern='#'/>">
					</td>
				</tr>
				<c:if test="${coinsFlag!=null&&(coinsFlag=='1'||coinsFlag=='2'||coinsFlag=='3')}">
					<tr style="display: none;">
						<td class="left">
							<input class='readonly' readonly type='text' value='我方赔款金额' title='我方赔款金额'>
						</td>
						<td class="right" colspan="2">
							<input type=text name="prpLcompensateSumCoinUs" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value ='${prpLcompensate.sumCoinUs}' pattern='#'/>">
						</td>
						<td class="left">
							<input class='readonly' readonly type='text' value='我方费用金额' title='我方费用金额'>
						</td>
						<td class="right" colspan="2">
							<input class="readonly" type=text name="prpLcompensateSumCoinUsFee" readonly="true" value="<fmt:formatNumber value ='${prpLcompensate.sumCoinUsFee}' pattern='#'/>">
						</td>
					</tr>
					<tr style="display: none;">
						<td class="left">
							<input class='readonly' readonly type='text' value='代付赔款金额' title='代付赔款金额'>
						</td>
						<td class="right" colspan="2">
							<input type=text name="prpLcompensateSumCoinForOther" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value ='${prpLcompensate.sumCoinForOther}' pattern='#'/>">
							<input type=hidden name="prpLcompensateSumCoinForOtherBak" class="readonly" readonly="true" style="width: 140px" value="<fmt:formatNumber value ='${prpLcompensate.sumCoinForOther}' pattern='#'/>">
						</td>
						<td class="left">
							<input class='readonly' readonly type='text' value='代付费用金额' title='代付费用金额'>
						</td>
						<td class="right" colspan="2">
							<input class="readonly" type=text name="prpLcompensateSumCoinForOtherFee" readonly="true" value="<fmt:formatNumber value ='${prpLcompensate.sumCoinForOtherFee}' pattern='#'/>">
							<input class="readonly" type=hidden name="prpLcompensateSumCoinForOtherFeeBak" readonly="true" value="<fmt:formatNumber value ='${prpLcompensate.sumCoinForOtherFee}' pattern='#'/>">
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="left">
						<s:text name="workflow.countDate" />
						<%--统计日期--%>
					</td>
					<td class="right">
						<rc:rcDate name="prpLcompensateStatisticsYM" value="${prpLcompensate.statisticsYM}"/>
					<td class="left">
						<s:text name="commonAcci.compensate.claimConclusion" />
					</td>
					<%--理赔结论--%>
					<td class="right" colspan="3">
						<s:select name="result" styleClass="three" style="width:20%" list="#request.compensateResultMap" listKey="key" listValue="value"></s:select>
						<input style="width: 78%" class="input" type=text name="prpLcompensateRemark" value="${prpLcompensate.remark}">
					</td>
				</tr>
				<input type="hidden" name="prpLcompensateChecker1" value="${prpLcompensate.checker1}">
			</table>
		</td>
	</tr>
</table>
<br>