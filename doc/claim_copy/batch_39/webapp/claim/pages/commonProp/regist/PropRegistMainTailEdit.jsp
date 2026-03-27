<tr>
	<td class="title">
		<s:text name="db.prpLregist.receiverName" />
		:
	</td>
	<td class="input" colspan=3>
		<input type=hidden name="prpLregistReceiverCode" class="codecode" style="width: 80px" title="<s:text name="db.prpLregist.receiverName" />" value="${prpLregist.receiverCode}" ondblclick="code_CodeSelect(this, 'HanderCode');"
			onkeyup="code_CodeSelect(this, 'HanderCode');"><%--接案人--%>
			<input type=text readonly=true name="prpLregistReceiverName" class="readonly" style="width: 125px" title="<s:text name="db.prpLregist.receiverName" />" value="${prpLregist.receiverName}"
			ondblclick="code_CodeSelect(this, 'HanderCode','-1','always','none','post');" onkeyup="code_CodeSelect(this, 'HanderCode','-1','always','none','post');">
	</td>
</tr>
<tr>
	<td class="title">
		<s:text name="db.prpLregist.handler1Code" />
		:
	</td>
	<td class="input">
		<input type=hidden name="prpLregistHandler1Code" value="${prpLregist.handler1Code}">
		<input type=text name="prpLregistHandler1Name" title="<s:text name="db.prpLregist.handler1Code"/>" class="readonly" readonly="true" value="${prpLregist.handler1Name}">
	</td>
	<td class="title">
		<s:text name="db.prpLregist.comCode" />
		:
	</td>
	<td class="input">
		<input type=hidden name="prpLregistComCode" value="${prpLregist.comCode}">
		<input type=text name="prpLregistComName" title="<s:text name="db.prpLregist.comCode" />" class="readonly" readonly="true" value="${prpLregist.comName}">
	</td>
</tr>
<tr>
	<td class="title">
		<s:text name="db.prpLlawsuit.operatorCode" />
		:
	</td>
	<td class="input">
		<input type=text name="prpLregistOperatorCode" title="<s:text name="prompt.queRegist.Operator"/>" class="readonly" style="width: 80px" readonly="true" value="${prpLregist.operatorCode}">
		<input type=text name="prpLregistOperatorName" title="<s:text name="guarantee.operateName"/>" class="readonly" style="width: 80px" readonly="true" value="${prpLregist.operatorName}">
	</td>
	<td class="title">
		<s:text name="commonAcci.claim.claimRegistDepart" />
		:
	</td>
	<%--理赔登记部门--%>
	<td class="input">
		<input type=text name="prpLregistMakeCom" title="<s:text name="commonAcci.claim.claimRegistDepart" />" class="readonly" style="width: 60px" readonly="true" value="${prpLregist.makeCom}">
		<input type=text name="prpLregistMakeComName" title="<s:text name="commonAcci.claim.claimRegistDepart" />" class="readonly" style="width: 120px" readonly="true" value="${prpLregist.makeComName}">
	</td>
</tr>
<tr style='display: none'>
	<td class="title">
		<s:text name="db.prpLregist.acceptFlag" />
		:
	</td>
	<td class="input">
		<input type="radio" <c:if test="${prpLregist.acceptFlag=='Y' }">checked="checked" </c:if> name="acceptFlag" value="Y">
		<s:text name="certainLoss.thirdCarLoss.yes" />
		<%--是--%>
		<input type="radio" <c:if test="${prpLregist.acceptFlag=='N' }">checked="checked" </c:if> name="acceptFlag" value="N">
		<s:text name="certainLoss.thirdCarLoss.no" />
		<%--否--%>
		<img src="${ctx }/images/bgMarkMustInput.jpg">
	</td>
	<td class="title">
		<s:text name="db.prpLregist.repeatInsureFlag" />
		:
	</td>
	<td class="input">
		<input type="radio" name="repeatInsureFlag" <c:if test="${prpLregist.repeatInsureFlag=='Y' }">checked="checked" </c:if> value="Y">
		<s:text name="certainLoss.thirdCarLoss.yes" />
		<%--是--%>
		<input type="radio" name="repeatInsureFlag" <c:if test="${prpLregist.repeatInsureFlag=='N' }">checked="checked" </c:if> value="N">
		<s:text name="certainLoss.thirdCarLoss.no" />
		<%--否--%>
		<img src="${ctx }/images/bgMarkMustInput.jpg">
	</td>
</tr>
<tr>
	<td class="title">
		<s:text name="db.prpLcomponent.remark" />
		:
	</td>
	<%--备注--%>
	<td class="input" colspan=3>
		<textarea style="width: 750px; overflow-x: visible;" name='prpLregistRemark' rows=4 cols=40 title="<s:text name="db.prpLcomponent.remark" />">${prpLregist.remark}</textarea>
	</td>
</tr>
</table>