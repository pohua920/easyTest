<table  class=subtable cellpadding="0" cellspacing="1">
	<tr>
		<td>
			<table  class=common cellpadding="1" cellspacing="1">
				<tr>  
					<td class="left"><s:text name="certify.dateReceipt"/>:</td><%-- 收件日期 --%>
					<td class="right">
						<rc:rcDate name="prpLclaimReceiptDate" title="收件日期" style="width:187px" value="${prpLclaim.receiptDate}" format="yyyy-MM-dd HH:mm" /><%-- 收件日期 --%>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left"><s:text name="db.prpLclaim.comCode" />:</td>
					<td class="right">
						<input type=hidden name="prpLclaimComCode" value = "${prpLclaim.comCode}">
						<%--需要转换机构和人的姓名--%>
						<input type=hidden name="prpLclaimComCode" title="<s:text name="db.prpLclaim.comCode" />" class="readonly" readonly="true" value = "${prpLclaim.comCode}"><%-- 业务归属机构 --%>
						<input type=text   name="prpLclaimComName" title="<s:text name="db.prpLclaim.comCode" />" class="readonly" readonly="true" value = "${prpLclaim.comName}"><%-- 业务归属机构 --%>
					</td>
					<td class="left"><s:text name="db.prpLclaim.handler1Code" />:</td>
					<td class="right">
						<input type=hidden name="prpLclaimHandler1Code" value="${prpLclaim.handler1Code}">
						<input type=text   name="prpLclaimHandler1Name" title="<s:text name="db.prpLclaim.handler1Code" />" class="readonly" readonly="true" value="${prpLclaim.handler1Name}"><%-- 归属业务员 --%>
					</td>
				</tr>
				<tr>
					<td class='left'><s:text name="db.prpLclaim.agentCode" />:</td>
					<td class='right'>
						<input class="readonly" readonly name="prpLclaimAgentCode"   title="<s:text name="regist.prpLregist.agentName" />" value="${prpLclaim.agentCode}"><%-- 代理人 --%>
						<input class="readonly" readonly name="prpLclaimAgentName"   title="<s:text name="regist.prpLregist.agentName" />" value="${prpLclaim.agentName}"><%-- 代理人 --%>
					</td>
					<td class='left'><s:text name="db.prpLclaim.handlerCode" />:</td>
					<td class='right' >
						<input name="prpLclaimHandlerCode" class="codecode" style="width:60%" value="${prpLclaim.handlerCode}"
						ondblclick="code_CodeSelect(this, 'HanderCode');"
						onkeyup= "code_CodeSelect(this, 'HanderCode');">
						<input name="prpLclaimHandlerName" class="codename" style="width:35%"  title="<s:text name="db.prpLarrearageNew.handlerCode" />" value="${prpLclaim.handlerName}"<%-- 经办人 --%>
						ondblclick="code_CodeSelect(this, 'HanderCode','-1','always','none','post');"
						onkeyup= "code_CodeSelect(this, 'HanderCode','-1','always','none','post');">
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left"><s:text name="db.prpLregist.operatorCode" />:</td>
					<td class="right">
						<input type=text name="prpLregistOperatorCode" title="<s:text name="db.prpLlawsuit.operatorCode" />" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.operatorCode}"><%-- 操作员 --%>
						<input type=text name="prpLregistOperatorName" title="<s:text name="general.handlerName" />" class="readonly" style="width:80px" readonly="true" value="${prpLclaim.operatorName}"><%-- 操作员名称 --%>
					</td>
					<td class="left"><s:text name="commonAcci.claim.claimRegistDepart" /></td><%--理赔登记部门--%>
					<td class="right">
						<input type=text name="prpLregistMakeCom" title="<s:text name="commonAcci.claim.claimRegistDepart" />" class="readonly" style="width:34%" readonly="true" value="${prpLclaim.makeCom}"><%-- 理赔登记部门 --%>
						<input type=text name="prpLregistMakeComName" title="<s:text name="commonAcci.claim.claimRegistDepart" />" class="readonly" style="width:46%" readonly="true" value="${prpLclaim.makeComName}"><%-- 理赔登记部门 --%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr>
				<tr>
					<td class="left"><s:text name="db.prpLregist.estimateLoss" />:${prpLclaim.estiCurrency}</td><%--估损金额--%>
					<td class="right">
						<input type=text name="prpLclaimSumClaim" title="<s:text name="db.prpLregist.estimateLoss" />" Class="readonly" readonly  value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumClaim}'/>"><%--估损金额--%>
						<input type=hidden name="EstiCurrency" value="${prpLclaim.estiCurrency}">
					</td>
					<td class="left"><s:text name="db.prpLCMain.sumClaim" />:${prpLclaim.estiCurrency}</td><%--赔付金额--%>
					<td class="right">
						<input type=text name="prpLclaimSumPaid" title="<s:text name="db.prpLCMain.sumClaim" />" Class="readonly" readonly value="<fmt:formatNumber pattern='#' value='${prpLclaim.sumPaid}'/>"><%--赔付金额--%>
					</td>
					<td class="left"></td>
					<td class="right"></td>
				</tr> 
			</table>
		</td>
	</tr>
</table>
<br>