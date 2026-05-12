<%--
****************************************************************************
* DESC       ：送审按钮公用界面
* AUTHOR     ：中科软
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<script src="${ctx}/pages/common/sendUndwrt/js/SendUndwrt.js"></script>
<%@ include file="/common/taglibs.jsp"%>
<c:if test="${requestScope.needUndwrtFlag=='Y'}">
	<c:choose>
		<c:when test="${requestScope.sendUndwrtFlag=='N'}">
			<td class=button style="width: 33%" align="center">
				<input type=button name="sendUndwrt" class='button' value="<s:text name='button.Sent.value'/>" onclick="SendUndwrt();">
				<%--送审--%>
			</td>
		</c:when>
		<c:when test="${requestScope.sendUndwrtFlag=='Y'}">
			<script>
				SubmitDisplay();
			</script>
			<td class=button style="width: 30%" align="center">
				<input type=button name="undwrt" class='button' value="<s:text name='button.By.value'/>" onclick="Undwrt('Pass');">
				<%--通过--%>
			</td>
			<td class=button style="width: 30%" align="center">
				<input type=button  name="noUndwrt" class='button' value="<s:text name='button.noPass.value'/>" onclick="Undwrt('NoPass');">
				<%--不通过--%>
			</td>
			<c:if test="${requestScope.sendUndwrtFlag!='999999999999'}">
				<td class=button style="width: 30%" align="center">
					<input type=button name="sendUpUndwrt" class='button' value="<s:text name='button.submitUp.value'/>" onclick="Undwrt('SendUp');">
					<%--提交上级--%>
				</td>
			</c:if>
		</c:when>
		<c:otherwise></c:otherwise>
	</c:choose>
</c:if>
<input type=hidden name="undwrtFlag" value="<c:out value='${requestScope.undwrtFlag}' />" />
<input type=hidden name="sendUndwrtFlag" value="<c:out value='${requestScope.sendUndwrtFlag}'/>" />
<input type=hidden name="needUndwrtFlag" value="<c:out value='${requestScope.needUndwrtFlag}' />" />
<input type=hidden name="undwrtSumPaid" value="<c:out value='${requestScope.undwrtSumPaid}'/>" />
<input type=hidden name="nodeType" value="<c:out value='${param.nodeType}' />" />