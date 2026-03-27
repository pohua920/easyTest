<%@ include file="/common/taglibs.jsp"%>
<tr>
	<td class="title">
		<s:text name="db.prpLregist.receiverName" />:
	</td>
	<td class="input" colspan="3">
		<input type=hidden name="prpLregistReceiverCode" class="codecode" style="width: 70px" title="接案人" value="${prpLregist.receiverCode}" ondblclick="code_CodeSelect(this, 'handerCode','0,1','Y');" onchange="code_CodeSelect(this, 'handerCode','0,1','Y');" onkeyup="code_CodeSelect(this, 'handerCode','0,1','Y');">
		<input type=text name="prpLregistReceiverName" class="codecode" style="width: 180px" title="接案人" value="${prpLregist.receiverName}" ondblclick="code_CodeSelect(this, 'handerCode','-1,0','Y','N');" onchange="code_CodeSelect(this, 'handerCode','-1,0','Y','N');" onkeyup="code_CodeSelect(this, 'handerCode','-1,0','Y','N');">
		<img src="${ctx }/images/bgDoubleClick1.gif" width="13" height="13" align="absmiddle"> <img src="${ctx }/images/bgMarkMustInput.jpg">
	</td>
	<td class="title" style="display: none">
		<s:text name="commonAcci.regist.whetherSubmit" />:
	</td>
	<%--是否呈报--%>
	<td class="input" style="display: none">
		<input type="radio" name="prplregistReportFlag" onclick="changePrplregistReportFlag()" value="1" <c:if test="${prpLregist.reportFlag=='1'}">checked</c:if> />
		<s:text name="certainLoss.thirdCarLoss.yes" />
		<%--是--%>
		<input type="radio" name="prplregistReportFlag" onclick="changePrplregistReportFlag()" value="0" <c:if test="${prpLregist.reportFlag=='0'}">checked</c:if> />
		<s:text name="certainLoss.thirdCarLoss.no" />
		<%--否--%>
	</td>
</tr>
<tr>
	<td class="title">
		<s:text name="db.prpLregist.handler1Code" />:
	</td>
	<td class="input">
		<input type=hidden name="prpLregistHandler1Code" value="${prpLregist.handler1Code}">
		<input type=text name="prpLregistHandler1Name" title="歸屬業務員" class="readonly" readonly="true" value="${prpLregist.handler1Name}">
	</td>
	<td class="title">
		<s:text name="db.prpLregist.comCode" />:
	</td>
	<td class="input">
		<input type=hidden name="prpLregistComCode" value="${prpLregist.comCode}">
		<input type=text name="prpLregistComName" title="業務歸屬機構" class="readonly" readonly="true" value="${prpLregist.comName}">
	</td>
</tr>
<tr>
	<td class="title">
		<s:text name="db.prpLregist.operatorCode" />:
	</td>
	<td class="input">
		<input type=text name="prpLregistOperatorCode" title="操作員" class="readonly" style="width: 80px" readonly="true" value="${prpLregist.operatorCode}">
		<input type=text name="prpLregistOperatorName" title="操作員名稱" class="readonly" style="width: 80px" readonly="true" value="${prpLregist.operatorName}">
	</td>
	<td class="title">
		<s:text name="commonAcci.claim.claimRegistDepart" />:
	</td>
	<%--理赔登记部门--%>
	<td class="input">
		<input type=text name="prpLregistMakeCom" title="理賠登記部門" class="readonly" style="width: 80px" readonly="true" value="${prpLregist.makeCom}">
		<input type=text name="prpLregistMakeComName" title="理賠登記部門" class="readonly" style="width: 200px" readonly="true" value="${prpLregist.makeComName}">
	</td>
</tr>
<tr style='display: none'>
	<td class="title">
		<s:text name="db.prpLregist.acceptFlag" />:
	</td>
	<td class="input">
		<input type="radio" name="acceptFlag" value="Y" <c:if test="${prpLregist.acceptFlag=='Y'}">checked="checked"</c:if> />
		<s:text name="certainLoss.thirdCarLoss.yes" />
		<%--是--%>
		<input type="radio" name="acceptFlag" value="N" <c:if test="${prpLregist.acceptFlag=='N'}">checked="checked"</c:if> />
		<s:text name="certainLoss.thirdCarLoss.no" />
		<%--否--%>
		<img src="${ctx }/images/bgMarkMustInput.jpg">
	</td>
	<td class="title">
		<s:text name="db.prpLregist.repeatInsureFlag" />:
	</td>
	<td class="input">
		<input type="radio" name="repeatInsureFlag" value="Y" <c:if test="${prpLregist.repeatInsureFlag=='Y'}">checked="checked"</c:if> />
		<s:text name="certainLoss.thirdCarLoss.yes" />
		<%--是--%>
		<input type="radio" name="repeatInsureFlag" value="N" <c:if test="${prpLregist.repeatInsureFlag=='N'}">checked="checked"</c:if> />
		<s:text name="certainLoss.thirdCarLoss.no" />
		<%--否--%>
		<img src="${ctx }/images/bgMarkMustInput.jpg">
	</td>
</tr>
<tr>
	<td class="title">
		<s:text name="db.prpDcompany.remark" />:
	</td>
	<%--备忘录--%>
	<td class="input" colspan=3>
		<textarea style="width: 650px; overflow-x: visible;" name='prpLregistRemark' rows=4 cols=40 title="備註">${prpLregist.remark}</textarea>
	</td>
</tr>
</table>