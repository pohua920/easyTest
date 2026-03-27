<%@ include file="/common/taglibs.jsp"%>
<table class=common cellpadding="5" cellspacing="1">
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.receiverName" />
			:
		</td>
		<td class="input">
			<input type=hidden name="prpLregistReceiverCode" class="codecode"
				style="width: 40px" title="接案人" value="${prpLregist.receiverCode}"
				ondblclick="code_CodeSelect(this, 'HanderCode');"
				onkeyup="code_CodeSelect(this, 'HanderCode');">
			<input type=text name="prpLregistReceiverName" class="codecode"
				style="width: 125px" title="接案人" value="${prpLregist.receiverName}"
				ondblclick="code_CodeSelect(this, 'HanderCode','-1','always','none','post');"
				onkeyup="code_CodeSelect(this, 'HanderCode','-1','always','none','post');">
			<img src="${ctx}/images/bgDoubleClick1.gif" width="13" height="13"
				align="absmiddle">
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
		<td class="left" >共保狀態：</td>
		<td class="right" >
			<input type="text" name="prpLregistCoinsFlag" class="input" style="width: 150px" value="${prpLregist.coinsFlag}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.handler1Code" />
			:
		</td>
		<td class="input">
			<input type=hidden name="prpLregistHandler1Code"
				value="${prpLregist.handler1Code}">
			<input type=text name="prpLregistHandler1Name" title="归属业务员"
				class="readonly" readonly="true" value="${prpLregist.handler1Name}">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.comCode" />
			:
		</td>
		<td class="input">
			<input type=hidden name="prpLregistComCode"
				value="${prpLregist.comCode}">
			<input type=text name="prpLregistComName" title="业务归属机构"
				class="readonly" readonly="true" value="${prpLregist.comName}">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLregist.operatorCode" />
			:
		</td>
		<td class="input">
			<input type=text name="prpLregistOperatorCode" title="操作员"
				class="readonly" style="width: 80px" readonly="true"
				value="${prpLregist.operatorCode}">
			<input type=text name="prpLregistOperatorName" title="操作员名称"
				class="readonly" style="width: 80px" readonly="true"
				value="${prpLregist.operatorName}">
		</td>
		<td class="title">
			<s:text name="commonAcci.claim.claimRegistDepart" />:
		</td> <%--理赔登记部门--%>
		<td class="input">
			<input type=text name="prpLregistMakeCom" title="理赔登记部门"
				class="readonly" style="width: 80px" readonly="true"
				value="${prpLregist.makeCom}">
			<input type=text name="prpLregistMakeComName" title="理赔登记部门"
				class="readonly" style="width: 200px" readonly="true"
				value="${prpLregist.makeComName}">
		</td>
	</tr>
	<tr>
		<td class="left" >理賠代理：</td>
		<td class="right" >
			<input type="text" name="prpLregistClaimAgent" class="input" style="width: 150px" value="${prpLregist.claimAgent}">
		</td>
		<td class="left" >地區別代號：</td>
		<td class="right" >
			<input type="text" name="prpLregistAreaCode" class="input"value="${prpLregist.areaCode}" style="width: 100px">
		</td>
	</tr>
	<tr style='display: none'>
		<td class="title">
			<s:text name="db.prpLregist.acceptFlag" />
			:
		</td>
		<td class="input">
			<input type="radio" name="acceptFlag" <c:if test="${prpLregist.acceptFlag=='Y' }">checked</c:if> value="Y" />
			<s:text name="certainLoss.thirdCarLoss.yes" /><%--是--%>
			<input type="radio" name="acceptFlag" value="N" <c:if test="${prpLregist.acceptFlag=='N' }">checked</c:if>/>
			<s:text name="certainLoss.thirdCarLoss.no" /><%--否--%>
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
		<td class="title">
			<s:text name="db.prpLregist.repeatInsureFlag" />
			:
		</td>
		<td class="input">
			<input type="radio" name="repeatInsureFlag" <c:if test="${prpLregist.repeatInsureFlag=='Y' }">checked</c:if> value="Y" />
			<s:text name="certainLoss.thirdCarLoss.yes" /><%--是--%>
			<input type="radio" name="repeatInsureFlag" <c:if test="${prpLregist.repeatInsureFlag=='N' }">checked</c:if> value="N" />
			<s:text name="certainLoss.thirdCarLoss.no" /><%--否--%>
			<img src="${ctx}/images/bgMarkMustInput.jpg">
		</td>
	</tr>
	<tr>
		<td class="title">
			<s:text name="db.prpLcomponent.remark" /><%--备注--%>:
		</td>
		<td class="input" colspan=3>
			<textarea style="width: 750px; overflow-x: visible;"
				name='prpLregistRemark' rows=4 cols=40 title="备注">${prpLregist.remark}</textarea>
		</td>
	</tr>
</table>