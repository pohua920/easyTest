<%@ include file="/common/taglibs.jsp"%>
<table class=subtable cellpadding="0" cellspacing="1" width="100%">
	<tr>
		<td>
			<table class=common cellpadding="0" cellspacing="1" width="100%">
				<tr>
					<td class="left">
						<s:text name="db.prpLclaim.claimDate" />
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimReportDate" description="报案日期">
						<input type=hidden name="prpLclaimToday" description="当前日期">
						<rc:rcDate class="common" style="width:70%" name="prpLclaimClaimDate" title="立案日期" value="${prpLclaim.claimDate}" format="yyyy-MM-dd HH:mm:ss"/>
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="commonAcci.claim.expectCompensate" />
						${prpLclaim.estiCurrency}
					</td>
					<%--预计赔偿金额--%>
					<td class="right">
						<input type=text name="prpLclaimSumClaim" title="預計賠償金額" Class="readonly" readonly style="width: 90;" value="<fmt:formatNumber value="${prpLclaim.sumClaim}" pattern="#"/>">
						<input type=hidden name="EstiCurrency" value="${prpLclaim.estiCurrency}">
					</td>
					<td class="left">
						<s:text name="db.prpLCMain.sumClaim" />
						${prpLclaim.estiCurrency}
					</td>
					<%--赔付金额--%>
					<td class="right">
						<input type=text name="prpLclaimSumPaid" title="賠付金額" Class="readonly" readonly value="<fmt:formatNumber value="${prpLclaim.sumPaid}" pattern="#"/>">
					</td>
				</tr>
				<tr>
					<td class="left">
						<s:text name="check.claimType" />
					</td>
					<%--案件类型--%>
					<td class="right">
						<!--原因：添加案件性质 -->
						<input name="prpLclaimClaimType" class="codecode" style="width: 30%" maxlength=1 value="${prpLclaim.claimType}" ondblclick="code_CodeSelect(this, 'CaseCode','0,1','Y','Y',fm.riskcode.value);" onchange="code_CodeChange(this, 'CaseCode','0,1','Y','Y',fm.riskcode.value);" onkeyup="code_CodeSelect(this, 'CaseCode','0,1','Y','Y',fm.riskcode.value);">
						<input name="prpLclaimClaimTypeName" class="codename" style="width: 50%" title="案件類型" value="${prpLclaim.claimTypeName}" ondblclick="code_CodeSelect(this, 'CaseCode','-1,0','Y','N',fm.riskcode.value);" onchange="code_CodeChange(this, 'CaseCode','-1,0','Y','N',fm.riskcode.value);" onkeyup="code_CodeSelect(this, 'CaseCode','-1,0','Y','N',fm.riskcode.value);">
						<img src="/claim/images/bgMarkMustInput.jpg">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.comCode" />
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimComCode" value="${prpLclaim.comCode}">
						<%--需要转换机构和人的姓名--%>
						<input type=hidden name="prpLclaimComCode" title="業務歸屬機構" class="readonly" readonly="true" value="${prpLclaim.comCode}">
						<input type=text name="prpLclaimComName" title="業務歸屬機構" class="readonly" readonly="true" value="${prpLclaim.comName}">
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.handler1Code" />
					</td>
					<td class="right">
						<input type=hidden name="prpLclaimHandler1Code" value="${prpLclaim.handler1Code}">
						<input type=text name="prpLclaimHandler1Name" title="歸屬業務員" class="readonly" readonly="true" value="${prpLclaim.handler1Name}">
					</td>
				</tr>
				<tr>
					<td class='left'>
						<s:text name="db.prpLclaim.agentCode" />
					</td>
					<td class='right'>
						<input class="readonly" readonly name="prpLclaimAgentCode" title="代理人" value="${prpLclaim.agentCode}">
						<input class="readonly" readonly name="prpLclaimAgentName" title="代理人" value="${prpLclaim.agentName}">
					</td>
					<td class='left'>
						<s:text name="db.prpLclaim.handlerCode" />
					</td>
					<td class='right'>
						<input name="prpLclaimHandlerCode" class="readonly" readonly style="width: 60%" value="${prpLclaim.handlerCode}">
						<!-- 经办人暂不可输入-->
						<input name="prpLclaimHandlerName" class="readonly" readonly style="width: 35%" title="立案人" value="${prpLclaim.handlerName}">
					</td>
					<td class="left"></td>
					<td class="right">
				</tr>
				<tr>
					<td class="left">
						<s:text name="prompt.queRegist.Operator" />
					</td>
					<td class="right">
						<input type=text name="prpLregistOperatorCode" title="操作員" class="readonly" style="width: 80px" readonly="true" value="${prpLclaim.operatorCode}">
						<input type=text name="prpLregistOperatorName" title="操作員名稱" class="readonly" style="width: 80px" readonly="true" value="${prpLclaim.operatorName}">
					</td>
					<td class="left">
						<s:text name="commonAcci.claim.claimRegistDepart" />
					</td>
					<%--理赔登记部门--%>
					<td class="right">
						<input type=text name="prpLregistMakeCom" title="理賠登記部門" class="readonly" style="width: 15%;" readonly="true" value="${prpLclaim.makeCom}">
						<input type=text name="prpLregistMakeComName" title="理賠登記部門" class="readonly" style="width: 80%;" readonly="true" value="${prpLclaim.makeComName}">
					</td>
					<td class="left"></td>
					<td class="right">
				</tr>
			</table>
</table>
</td>
</tr>
</table>
<br>