<table  class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table  class=common cellpadding="1" cellspacing="1">
				<tr>  
					<td class="title"><s:text name="db.prpLclaim.claimDate" />:</td>
					<td class="input" colspan="3">
						<input type=hidden name="prpLclaimReportDate" description="<s:text name="db.prpLregist.reportDate" />" >
						<input type=hidden name="prpLclaimToday" description="<s:text name="common.check.currentDate" />" >
						<rc:rcDate name="prpLclaimClaimDate"  class="readonly"   wdatePicker="false" value="${prpLclaim.claimDate}" title="<s:text name='db.prpLclaim.claimDate'/>" format="yyyy-MM-dd HH:mm:ss"/>
					</td> 
				</tr>
				<tr>		
					<td class="title"><%--估损金额--%>
						<s:text name="db.prpLregist.estimateLoss" />:<c:out value="${prpLclaim.estiCurrency}"/>
					</td>
					<td class="input">
						<input type="text" name="prpLclaimSumClaim" title="<s:text name="db.prpLregist.estimateLoss" />" Class="readonly" readonly   value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumClaim}'/>"/>
						<input type="hidden" name="EstiCurrency" Class="readonly" readonly value="${prpLclaim.estiCurrency}"/>
					</td>
					<td class="title">
						<s:text name="db.prpLCMain.sumClaim" />:<c:out value="${prpLclaim.estiCurrency}"/>
					</td><%--赔付金额--%>
					<td class="input">
						<input type=text name="prpLclaimSumPaid" title="<s:text name="db.prpLreplevynew.sumpaid" />" Class="readonly" readonly value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumPaid}'/>">
					</td>	  
				</tr> 
				<tr>
					<td class="title"><s:text name="db.prpLclaim.comCode" />:</td>
					<td class="input">
						<input type=hidden name="prpLclaimComCode" value = "${prpLclaim.comCode}">
						<%--需要转换机构和人的姓名--%>
						<input type=hidden name="prpLclaimComCode" title="<s:text name="db.prpLclaim.comCode" />" class="readonly" readonly="true" value = "${prpLclaim.comCode}">
						<input type=text   name="prpLclaimComName" title="<s:text name="db.prpLclaim.comCode" />" class="readonly" readonly="true" value = "${prpLclaim.comName}">
					</td>
					<td class="title"><s:text name="db.prpLclaim.handler1Code" />:</td>
					<td class="input">
						<input type=hidden name="prpLclaimHandler1Code" value="${prpLclaim.handler1Code}">
						<input type=text   name="prpLclaimHandler1Name" title="<s:text name="db.prpLclaim.handler1Code" />" class="readonly" readonly="true" value="${prpLclaim.handler1Name}">
					</td>		
				</tr>
				<tr>
					<td class='title'><s:text name="db.prpLclaim.agentCode" />:</td>
					<td class='input'>
						<input class="readonly" readonly name="prpLclaimAgentCode" style="width:34%" title="<s:text name="db.prpDagent.agentCode" />" value="${prpLclaim.agentCode}">
						<input class="readonly" readonly name="prpLclaimAgentName" style="width:56%" title="<s:text name="db.prpDagent.agentName" />" value="${prpLclaim.agentName}">
					</td>
					<td class='title'><s:text name="db.prpLclaim.handlerCode" />:</td>
					<td class='input' >
						<input name="prpLclaimHandlerCode" class="readonly" readonly style="width:34%" value="${prpLclaim.handlerCode}">
						<input name="prpLclaimHandlerName" class="readonly" readonly style="width:56%"  title="<s:text name="db.prpLarrearageNew.handlerCode" />" value="${prpLclaim.handlerName}">
					</td>
				</tr>
				<tr>
					<td class="title"><s:text name="db.prpLregist.operatorCode" />:</td>
					<td class="input">
						<input type=text name="prpLregistOperatorCode" title="<s:text name="db.prpLlawsuit.operatorCode" />" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.operatorCode}">
						<input type=text name="prpLregistOperatorName" title="<s:text name="general.handlerName" />" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.operatorName}">
					</td>
					<td class="title"><s:text name="commonAcci.claim.claimRegistDepart" /></td><%--理赔登记部门--%>
					<td class="input">
						<input type=text name="prpLregistMakeCom" title="<s:text name="commonAcci.claim.claimRegistDepart" />" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.makeCom}">
						<input type=text name="prpLregistMakeComName" title="<s:text name="commonAcci.claim.claimRegistDepart" />" class="readonly" style="width:200px" readonly="true" value="${prpLclaim.makeComName}">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>