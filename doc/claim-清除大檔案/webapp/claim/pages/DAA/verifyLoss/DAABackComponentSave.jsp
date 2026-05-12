<%--
****************************************************************************
* DESC       ：通用按钮画面
* AUTHOR     : 理赔组
* CREATEDATE ：2013-2-27
* MODIFYLIST ：   Name       Date            Reason/Contents
*          ------------------------------------------------------
****************************************************************************
--%>
<%-- 保存通用按钮 --%>
<table cellpadding="0" cellspacing="0" style="display:" id="buttonArea">
	<tr>
		<%-- 隐藏所按的保存按钮是哪个的标志--%>
		<td colspan="5">
			<input type="hidden" name=buttonSaveType value="1">
		</td>
	</tr>
	<tr>
		<logic:notEqual name="prpLverifyLossDto" property="status" value="4">
			<td>
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'2');">
				&nbsp;&nbsp;
			</td>
			<td>
				<!--提交的对象和内容-->
				<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
				&nbsp;&nbsp;
			</td>
			<td>
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="javascript:history.go(-2);">
				&nbsp;&nbsp;
			</td>
		</logic:notEqual>
		<logic:equal name="prpLverifyLossDto" property="status" value="4">
			<td>
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.return.value' />" onclick="javascript:history.go(-2);">
				&nbsp;&nbsp;
			</td>
		</logic:equal>
		<input type=hidden name=verifyOpinion value="">
		<input type=hidden name=nextNodeNo value="0">
	</tr>
</table>