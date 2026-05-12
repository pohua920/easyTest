<%--
****************************************************************************
* DESC       ：显示实赔文字页面
* AUTHOR     ：理赔组
* CREATEDATE ：2004-05-19
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<table class="common" align="center" width="100%">
	<tr class="common">
		<td style="text-align: left;">
			<img style="cursor: hand;" src="${ctx }/images/butCollapseBlue.gif" name="RegistTextImg" onclick="showPage(this,RegistText)">
			<s:text name="commonAcci.compensate.description" />&nbsp;&nbsp;
			<%--理算說明--%>
			<select name="prpLcompensateContextNo" class="input" onchange="getContext(this);" <c:if test="${empty requestScope.CompeContext}">style="width: 250px;"</c:if>>
				<option value=""></option>
				<c:forEach items="${requestScope.CompeContext}" var="temp">
					<option value="${temp.id.contextNo}" style="color: red" <c:if test="${prpLcompensate.contextNo==temp.id.contextNo}">selected</c:if>><c:out value="${temp.title}" /></option>
				</c:forEach>
			</select>
			<br>
			<table class="common" align="center" id="RegistText" style="display: none">
				<tbody>
					<tr>
						<td class="input" style="text-align: center;" colspan="0">
							<textarea style="wrap: hard" rows="15" cols="80" name="prpLctextContextInnerHTML">${prpLctext.context}</textarea>
							<br>
							<%--<input type="button" name="buttonGenerateCtext" class='bigbutton' value="<s:text name='button.auditApproval.value' />" onclick="generateCtext('0');">
							<font color='red'><s:text name="prompt.compensate.modifyAmountAttention" />
								<%--修改金额後注意重新生成审核批文!!!</font>--%>
							<%--生成审核批文--%>
						</td>
					</tr>
				</tbody>
			</table>
		</td>
	</tr>
</table>
