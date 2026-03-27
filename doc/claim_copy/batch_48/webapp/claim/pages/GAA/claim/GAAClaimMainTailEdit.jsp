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
					</td>
					<td class="left">
						<s:text name="db.prpLcfee.sumPaid" />
						:
					</td>
					<!--赔付金额 -->
					<td class="right">
						<input type=text name="prpLclaimSumPaid" title="<s:text name="db.prpLcfee.sumPaid" />" Class="readonly" readonly value="<fmt:formatNumber value="${prpLclaim.sumPaid}" pattern="#"/>">
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
						<input type=text name="prpLregistOperatorCode" title="<s:text name='prompt.queRegist.Operator'/>" class="readonly" style="width: 20%" readonly="true" value="${prpLclaim.operatorCode}">
						<%--操作员--%>
						<input type=text name="prpLregistOperatorName" title="<s:text name='guarantee.operateName'/>" class="readonly" style="width: 43%" readonly="true" value="${prpLclaim.operatorName}">
						<%--操作员名称--%>
					</td>
				</tr>
				<%--增加 “行業職業代號 ”等字段  begin--%>
				<tr>
					<td class='left'>
						<s:text name="db.prpLclaim.businessCareerCode" />
						:
					</td>
					<td class='right'>
						<input name="prpLclaimBusinessCareerCode" class="codecode"  title="<s:text name='db.prpLclaim.businessCareerCode'/>" style="width: 25%"value="${prpLclaim.businessCareerCode}" ondblclick="code_CodeSelect(this, 'BusinessCareer','0,1','Y','Y');"
							onkeyup="code_CodeSelect(this, 'BusinessCareer','0,1','Y','Y');" onchange="code_CodeSelect(this, 'BusinessCareer','0,1','Y','Y');">
						<input name="prpLclaimBusinessCareerName" class="codename"  title="<s:text name='db.prpLclaim.businessCareerName'/>" style="width: 55%" value="${prpLclaim.businessCareerName}" ondblclick="code_CodeSelect(this, 'BusinessCareer','-1,0','Y','N');"
							onkeyup="code_CodeSelect(this, 'BusinessCareer','-1,0','Y','N');" onchange="code_CodeSelect(this, 'BusinessCareer','-1,0','Y','N');">
						<%-- 行業職業代號--%>
					</td>
					<td class='left'>
						<s:text name="db.prpLclaim.dangerousClassItem" />:
					</td>
					<td class='right'>
						<s:select name="prpLclaimDangerousClassItem" value="#request.prpLclaim.dangerousClassItem" list="#request.dangerousClassItemList" listKey="id.codeCode" listValue="codeCName" onchange="dangerousClassItem(this);" style="width:100px;"/>
						<%-- 危險分類總項--%>
					</td>
					<td class="left">
						<s:text name="db.prpLclaim.dangerousClassSubItem" />:
					</td>
					<td class="right">
						<input type=text name="prpLclaimDangerousClassSubItem" class="codecode" title="<s:text name='db.prpLclaim.dangerousClassSubItem'/>" style="width: 25%"  value="${prpLclaim.dangerousClassSubItem}" ondblclick="code_CodeSelect(this, 'DangerousClassSubItem','0,1','Y','Y',fm.prpLclaimDangerousClassItem.value);"
							onkeyup="code_CodeSelect(this, 'DangerousClassSubItem','0,1','Y','Y',fm.prpLclaimDangerousClassItem.value);" onchange="code_CodeSelect(this, 'DangerousClassSubItem','0,1','Y','Y',fm.prpLclaimDangerousClassItem.value);">
						<input type=text name="prpLclaimDangerousClassSubItemName" class="codename" title="<s:text name='db.prpLclaim.dangerousClassSubItemName'/>" style="width: 55%" value="${prpLclaim.dangerousClassSubItemName}" ondblclick="code_CodeSelect(this, 'DangerousClassSubItem','-1,0','Y','N',fm.prpLclaimDangerousClassItem.value);"
							onkeyup="code_CodeSelect(this, 'DangerousClassSubItem','-1,0','Y','N',fm.prpLclaimDangerousClassItem.value);" onchange="code_CodeSelect(this, 'DangerousClassSubItem','-1,0','Y','N',fm.prpLclaimDangerousClassItem.value);">
						<%-- 危險分類細項--%>
					</td>
				</tr>
				<tr>
					<td class='left'>
						<s:text name="db.prpLclaim.projectCode" />
						:
					</td>
					<td class='right'>
						<input class="input" name="prpLclaimProjectCode"style="width: 90%" title="<s:text name='db.prpLclaim.projectCode'/>" value="${prpLclaim.projectCode}">
						<%-- 專案代號--%>
					</td>
					<td class='left'>
					</td>
					<td class='right'>
					</td>
					<td class="left">
					</td>
					<td class="right">
					</td>
				</tr>
				<%--增加 “行業職業代號 ”等字段  end--%>
			</table>
</table>
</td>
</tr>
</table>
<br>
