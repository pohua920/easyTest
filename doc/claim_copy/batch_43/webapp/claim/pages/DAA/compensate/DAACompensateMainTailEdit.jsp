<%@	page contentType="text/html; charset=GBK" language="java"%>
<%--
****************************************************************************
* DESC       :添加主信息子块界面页面Tail[ 实赔 ]
* AUTHOR     :理赔组
* CREATEDATE :2013-02-20
* MODIFYLIST :  Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td class="left">
			<img style="cursor: hand;" src="${ctx }/images/butExpandBlue.gif" name="ChargeImg" onclick="showPage(this,span);">賠款計算訊息<br>
			<%-- 賠款計算資訊 --%>
			<span id="span">
				<table class=common cellpadding="1" cellspacing="1">
					<tr>
						<td class="left">
							<input class='readonly' readonly type='text' value='<s:text name="compensate.compel.paymentAmount" />' title='賠款金額之和，不包括費用！'>
						</td>
						<%--赔款金额 --%>
						<td class="right">
							<input class="readonly" type=text name="prpLcompensateSumDutyPaid" readonly="true" value="<fmt:formatNumber value="${requestScope.prpLcompensate.sumDutyPaid}" pattern="#" />">
						</td>
						<td class="left">
							<input class='readonly' readonly type='text' value='<s:text name="undwrt.ChargeAmount" />' title='費用金額之和，不包括賠款金額！'>
						</td>
						<%--费用金额 --%>
						<td class="right">
							<input type=text name="prpLcompensateSumNoDutyFee" class="readonly" readonly="true" value="<fmt:formatNumber value="${requestScope.prpLcompensate.sumNoDutyFee}" pattern="#" />">
						</td>
						<td class="left">
							<input class='readonly' readonly type='text' value='<s:text name="undwrt.CaseTotal" />' title='賠款合計與費用之和！'>
						</td>
						<%--本案合计 --%>
						<td class="right">
							<input class="readonly" type=text name="prpLcompensateSumPaid" readonly="true" value="<fmt:formatNumber value="${requestScope.prpLcompensate.sumPaid}" pattern="#" />">
						</td>
					</tr>
					<tr>
						<td class="left">
							<input class='readonly' readonly type='text' value='<s:text name="undwrt.PaymentAmount" />' title='預付賠款金額之和！'>
						</td>
						<%--已预付赔款金额 --%>
						<td class="right">
							<input type=text name="prpLcompensateSumPrePaid" class="readonly" readonly="true" value="<fmt:formatNumber value='${requestScope.sosMedicFee}' pattern='#'/>">
						</td>
						<td class="left">
							<input class='readonly' readonly type='text' value='<s:text name="db.prpLcompensate.sumThisPaid" />' title='賠款合計減去已預付賠款！'>
						</td>
						<%--本次赔付金额 --%>
						<td class="right">
							<input class="readonly" type=text name="prpLcompensateSumThisPaid" id="prpLcompensateSumThisPaid" readonly="true" value="<fmt:formatNumber value="${requestScope.prpLcompensate.sumThisPaid}" pattern="#" />">
						</td>
						<td class="left"></td>
						<td class="right">
							<input class="readonly" type="hidden" name="prpLcompensateSumSelfValue" readonly="true" value="<fmt:formatNumber value="${requestScope.prpLcompensate.bank}" pattern="#" />">
						</td>
					</tr>
				</table>
			</span>
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
						<s:text name="compensate.businessAgent" />
					</td>
					<%-- 业务经办人 --%>
					<td class="right">
						<input name="prpLcompensateHandlerCode" class="codecode" style="width: 27%" value="${requestScope.prpLcompensate.handlerCode}" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');"
							onchange="code_CodeChange(this, 'handerCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');">
						<input name="prpLcompensateHandlerName" class="codename" style="width: 48%" title="經辦人" value="${requestScope.prpLcompensate.handlerName}"
							ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');" onchange="code_CodeChange(this, 'handerCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="db.prpLcompensate.statisticsYM" />
					</td>
					<%-- 统计年月 --%>
					<td class="right">
						<%--<input type="text" class="common" name="prpLcompensateStatisticsYM" value="${requestScope.prpLcompensate.statisticsYM}"> --%>
						<rc:rcDate name="prpLcompensateStatisticsYM" class="readonly" readonly="true" wdatePicker="false" value="${requestScope.prpLcompensate.statisticsYM}" />
					</td>
					<td class="left">
						<s:text name="prpLcheck.checkUser" />
					</td>
					<%-- 查勘人 --%>
					<td class="right">
						<input type=text name="prpLcompensateChecker1" class="readonly" readonly="true" value="${requestScope.prpLcompensate.checker1}">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLcomponent.remark" />
					</td>
					<%-- 备注 --%>
					<td class="right" colspan="5">
						<input class="input" type=text name="prpLcompensateRemark" value="${requestScope.prpLcompensate.remark}">
					</td>
				</tr>
				<c:if test="${requestScope.prpLcompensate.status=='3'}">
					<tr>
						<td class="left">
							<s:text name="compensate.fallbackReason" />
						</td>
						<%-- 回退原因 --%>
						<td class="right" colspan="5">
							<input class="input" readonly="true" type="text" name="backReason" value="${requestScope.swfNotionDto.handleText}">
						</td>
					</tr>
				</c:if>
			</table>
		</td>
	</tr>
</table>
