
<%-- 保存通用按钮 --%>
<table id="buttonArea" cellpadding="0" cellspacing="0" class=common style="display:">
	<tr>
		<%-- 隐藏域保存的值为客户点击按钮的标志位--%>
		<td>
			<input type="hidden" name=buttonSaveType value="1">
		</td>
	</tr>
	<tr>
		<logic:notEqual name="prpLbackVisitDto" property="status" value="4">
			<td class=button style="width: 33%">
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'2');">
			</td>
			<%-- 暂存 --%>
			<td class=button style="width: 34%">
				<!--提交按钮-->
				<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value' />" onclick="return saveForm(this,'4');">
			</td>
			<td class=button style="width: 33%">
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
			</td>
		</logic:notEqual>
		<logic:equal name="prpLbackVisitDto" property="status" value="3">
			<td class=button style="width: 25%" align="center">
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'3');">
			</td>
			<td class=button style="width: 25%" align="center">
				<!--回退按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.return.value' />" onclick="return saveForm(this,'2');">
			</td>
			<td class=button style="width: 25%" align="center">
				<!--提交按钮-->
				<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value' />" onclick="return saveForm(this,'4');">
			</td>
			<td class=button style="width: 25%" align="center">
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value' />" onclick="return resetForm();">
			</td>
		</logic:equal>
		<logic:equal name="prpLbackVisitDto" property="status" value="4">
			<td class=button align="center">
				<!--取消按钮-->
				<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
			</td>
		</logic:equal>
	</tr>
</table>
