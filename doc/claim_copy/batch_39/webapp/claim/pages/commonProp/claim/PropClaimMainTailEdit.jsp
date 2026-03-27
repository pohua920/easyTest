<%@ include file="/common/taglibs.jsp"%>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.claimDate" />
						:
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimReportDate" description="<s:text name='db.prpLregist.reportDate'/>">
						<%--备案日期--%>
						<input type=hidden name="prpLclaimToday" description="<s:text name='common.check.currentDate'/>">
						<%--当前日期--%>
						<rc:rcDate class="readonly" style="width:80%" name="prpLclaimClaimDate" title="<s:text name='db.prpLclaim.claimDate'/>" value="${prpLclaim.claimDate}" format="yyyy-MM-dd HH:mm:ss" wdatePicker="false"/>
						<%--立案日期--%>
					</td>
					<td class="left"><s:text name="certify.dateReceipt"/>:</td><%-- 收件日期 --%>
					<td class="right">
						<rc:rcDate name="prpLclaimReceiptDate" title="收件日期" style="width:187px" value="${prpLclaim.receiptDate}" format="yyyy-MM-dd HH:mm" /><%-- 收件日期 --%>
					</td>
					<td class="left">
						<s:text name="db.prpLregist.estimateLoss" />
						${prpLclaim.estiCurrency}
					</td>
					<%-- 估损金额 --%>
					<td class="right">
						<input type=text name="prpLclaimSumClaim" title="<s:text name="db.prpLregist.estimateLoss" />" class="readonly" readonly value="<fmt:formatNumber value="${prpLclaim.sumClaim}" pattern="#"/>">
						<%--预估金额--%>
						<input type=hidden name="EstiCurrency" value="${prpLclaim.estiCurrency}">
						<input type=hidden name="prpLclaimCurrency" value="${prpLclaim.estiCurrency}">
						<input type=hidden name="prpLclaimSumPaid" title="<s:text name="db.prpLcfee.sumPaid" />" Class="readonly" readonly value="<fmt:formatNumber value="${prpLclaim.sumPaid}" pattern="#"/>">
						<!--赔付金额 -->
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.comCode" />
						:
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimComCode" value="${prpLclaim.comCode}">
						<%--需要转换机构和人的姓名--%>
						<input type=hidden name="prpLclaimComCode" title="<s:text name='db.prpLregist.comCode'/>" class="readonly" readonly="true" value="${prpLclaim.comCode}">
						<input type=text name="prpLclaimComName" title="<s:text name='db.prpLregist.comCode'/>" class="readonly" readonly="true" value="${prpLclaim.comName}">
						<%--业务归属机构--%>
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.handler1Code" />
						:
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimHandler1Code" value="${prpLclaim.handler1Code}">
						<input type=text name="prpLclaimHandler1Name" title="<s:text name='db.prpLregist.handler1Code'/>" class="readonly" readonly="true" value="${prpLclaim.handler1Name}">
						<%--归属业务员--%>
					</td>
					<td class="left">
						<s:text name="commonAcci.claim.claimRegistDepart" />
						:
					</td>
					<%-- 理赔登记部门 --%>
					<td class="right">
						<input type=text name="prpLregistMakeCom" title="<s:text name='commonAcci.claim.claimRegistDepart'/>" class="readonly" style="width: 10%;" readonly="true" value="${prpLclaim.makeCom}">
						<input type=text name="prpLregistMakeComName" title="<s:text name='commonAcci.claim.claimRegistDepart'/>" class="readonly" style="width: 80%;" readonly="true" value="${prpLclaim.makeComName}">
						<%--理赔登记部门--%>
					</td>
				</tr>
				<tr>
					<td class='left'>
						<s:text name="db.prpLclaim.agentCode" />
						:
					</td>
					<td class='right'>
						<input class="readonly" readonly name="prpLclaimAgentCode" title="<s:text name='db.prpLclaim.agentCode'/>" value="${prpLclaim.agentCode}">
						<%--代理人--%>
						<input class="readonly" readonly name="prpLclaimAgentName" title="<s:text name='db.prpLclaim.agentCode'/>" value="${prpLclaim.agentName}">
						<%--代理人--%>
					</td>
					<td class='left'>
						<s:text name="db.prpLlawsuit.handlerCode" />
						:
					</td>
					<td class='right'>
						<input name="prpLclaimHandlerCode" class="readonly" readonly style="width: 30%" value="${prpLclaim.handlerCode}">
						<input name="prpLclaimHandlerName" class="readonly" readonly style="width: 65%" title="<s:text name='common.check.claimPeople'/>" value="${prpLclaim.handlerName}">
						<%--理赔人--%>
					</td>
					<td class="left">
						<s:text name="prompt.queRegist.Operator" />
						:
					</td>
					<td class="right">
						<input type=text name="prpLregistOperatorCode" title="<s:text name='prompt.queRegist.Operator'/>" class="readonly" style="width: 80px" readonly="true" value="${prpLclaim.operatorCode}">
						<%--操作员--%>
						<input type=text name="prpLregistOperatorName" title="<s:text name='guarantee.operateName'/>" class="readonly" style="width: 80px" readonly="true" value="${prpLclaim.operatorName}">
						<%--操作员名称--%>
					</td>
				</tr>
			</table>
</table>
</td>
</tr>
</table>
<br>
