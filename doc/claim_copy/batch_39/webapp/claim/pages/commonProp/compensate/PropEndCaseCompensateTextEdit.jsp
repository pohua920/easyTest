<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：理赔组
* CREATEDATE ：2013-07-10
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%@ include file="/common/taglibs.jsp"%>
<table class="common" cellpadding="5" cellspacing="1" id="Lltext" style="display: none">
	<tr>
		<td class="common" style="text-align: left;">
			<img style="cursor: hand;" src="/claim/images/butCollapseBlue.gif" name="LlTextImg" onclick="showPage(this,LlText2)">
			<input class=readonly readonly name="tdLltextTitle" value="<s:text name='compensate.adjustmentInformation'/>" style="width: 10%;">
			<%--理算信息--%>
			<select name="prpLcompensateContextNo" class="input" onchange="getContext(this);" <c:if test="${empty requestScope.CompeContext}">style="width: 250px;"</c:if>>
				<option value=""></option>
				<c:forEach items="${requestScope.CompeContext}" var="temp">
					<option value="${temp.id.contextNo}" style="color: red" <c:if test="${prpLcompensate.contextNo==temp.id.contextNo}">selected</c:if>><c:out value="${temp.title}" /></option>
				</c:forEach>
			</select>
			<textarea name='backLltextContent' style="display: none"></textarea>
			<br>
			<table class="common" align="center" id="LlText2" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLltextContextInnerHTML"><c:out value="${requestScope.prpLltext.context}" /></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
<c:choose>
	<c:when test="${requestScope.recaseFlag=='0'||requestScope.editType=='SHOW'}">
		<script language="javascript">
			changePrpLcompensateFinallyFlag();
		</script>
	</c:when>
	<c:otherwise>
		<script language="javascript">
			changePrpLcompensateFinallyFlag1();
		</script>
	</c:otherwise>
</c:choose>
