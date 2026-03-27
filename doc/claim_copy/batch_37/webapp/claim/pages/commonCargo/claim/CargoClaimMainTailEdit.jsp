<table class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table class=common cellpadding="1" cellspacing="1">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.claimDate" />:
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimReportDate" description="<s:text name="db.prpLregist.reportDate" />">
						<input type=hidden name="prpLclaimToday" description="<s:text name="common.check.currentDate" />">
						<rc:rcDate name="prpLclaimClaimDate" class="readonly" readonly="true" wdatePicker="false" title="<s:text name='db.prpLclaim.claimDate'/>" value="${prpLclaim.claimDate}" format="yyyy-MM-dd HH:mm:ss" />
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.comCode" />
						:
					</td>
					<td class="right">
						<%--需要转换机构和人的姓名--%>
						<input type=hidden name="prpLclaimComCode" title="<s:text name="db.prpLclaim.comCode" />" class="readonly" readonly="true" value="${prpLclaim.comCode}">
						<input type=text name="prpLclaimComName" title="<s:text name="db.prpLclaim.comCode" />" class="readonly" readonly="true" value="${prpLclaim.comName}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.handler1Code" />
						:
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimHandler1Code" value="${prpLclaim.handler1Code}"><%-- 归属业务员 --%>
						<input type=text name="prpLclaimHandler1Name" title="<s:text name="db.prpLclaim.handler1Code" />" class="readonly" readonly="true" value="${prpLclaim.handler1Name}"><%-- 归属业代码 --%>
					</td>
				</tr>
				<tr>
					<td class='left'>
						<s:text name="db.prpLclaim.agentCode" />:
					</td>
					<td class='right'>
						<input class="readonly" readonly name="prpLclaimAgentCode" title="<s:text name="regist.prpLregist.agentName" />" value="${prpLclaim.agentCode}"><%-- 代理人代码 --%>
						<input class="readonly" readonly name="prpLclaimAgentName" title="<s:text name="regist.prpLregist.agentName" />" value="${prpLclaim.agentName}"><%-- 代理人名称 --%>
					</td>
					<td class='left'>
						<s:text name="db.prpLclaim.handlerCode" />:
					</td>
					<td class='right'>
						<input name="prpLclaimHandlerCode" class="codecode" style="width: 60%" value="${prpLclaim.handlerCode}" ondblclick="code_CodeSelect(this, 'HanderCode');"
							onkeyup="code_CodeSelect(this, 'HanderCode');"><%-- 经办人代码 --%>
						<input name="prpLclaimHandlerName" class="codename" style="width: 35%" title="<s:text name="db.prpLarrearageNew.handlerCode" />" value="${prpLclaim.handlerName}" ondblclick="code_CodeSelect(this, 'HanderCode','-1','always','none','post');"
							onkeyup="code_CodeSelect(this, 'HanderCode','-1','always','none','post');"><%-- 经办人名称 --%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLregist.operatorCode" />:
					</td>
					<td class="right">
						<input type=text name="prpLregistOperatorCode" title="<s:text name="db.prpLlawsuit.operatorCode" />" class="readonly" style="width: 80px" readonly="true" value="${prpLclaim.operatorCode}"><%-- 操作员 --%>
						<input type=text name="prpLregistOperatorName" title="<s:text name="general.handlerName" />" class="readonly" style="width: 80px" readonly="true" value="${prpLclaim.operatorName}"><%-- 操作员名称 --%>
					</td>
					<td class="left">
						<s:text name="commonAcci.claim.claimRegistDepart" />:
					</td>
					<%-- 理赔登记部门 --%>
					<td class="right">
						<input type=text name="prpLregistMakeCom" title="<s:text name="commonAcci.claim.claimRegistDepart" />" class="readonly" style="width: 34%" readonly="true" value="${prpLclaim.makeCom}">
						<input type=text name="prpLregistMakeComName" title="<s:text name="commonAcci.claim.claimRegistDepart" />" class="readonly" style="width: 46%" readonly="true" value="${prpLclaim.makeComName}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="db.prpLregist.estimateLoss" />:${prpLclaim.estiCurrency}
					</td>
					<%-- 估损金额 --%>
					<td class="right">
						<input type=text name="prpLclaimSumClaim" title="<s:text name="db.prpLregist.estimateLoss" />" Class="readonly" readonly value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumClaim}'/>">
						<input type=hidden name="EstiCurrency" value="${prpLclaim.estiCurrency}" >
					</td>
					<td class="left">
						<s:text name="db.prpLCMain.sumClaim" />:${prpLclaim.estiCurrency}
					</td>
					<%-- 赔付金额 --%>
					<td class="right">
						<input type=text name="prpLclaimSumPaid" title="<s:text name="db.prpLreplevynew.sumpaid" />" Class="readonly" readonly value="${prpLclaim.sumPaid}">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>