<%-- 保存通用按钮 --%>
</br>
<table cellpadding="0" cellspacing="0" width="80%" class="common">
	<tr>
		<%-- 只显示返回按钮--%>
		<td>
			<input type="hidden" name=buttonSaveType value="1">
		</td>
	</tr>
	<tr>
		<%
			if (!editType.equals("SHOW")) {
		%>
		<td class=button align="center">
			<input type=button name=buttonSave class='button' value="<s:text name="button.update.value" />" onclick="javascript:saveForm();">
			<%--更新 --%>
		</td>
		<%
			}
		%>
		<td class=button align="center">
			<!--取消按钮-->
			<input type=button name=buttonCancel class='button' value="<s:text name="button.return.value " />" onclick="javascript:history.go(-1);">
			<%--返回 --%>
		</td>
	</tr>
</table>
