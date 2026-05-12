<%@ include file="/common/taglibs.jsp"%>
<table cellpadding="0" cellspacing="0" class="common" align="center">
	<tr>
		<s:if test="#attr.dfFlag=='Y'">
			<td width="10%">
				<!--返回按钮-->
				<input type=button name=buttonBack class='button' value="<s:text name='button.return.value'/>" onclick="return history.back();">
			</td>
		</s:if>
		<s:else>
			<td width="10%">
				<!--确 定按钮-->
				<input type=button name=buttonSave class='button' value="<s:text name='button.submit.value' />" onclick="return saveForm();getClaimNo()">
			</td>
			<td width="10%">
				<!--返回按钮-->
				<input type="button" name=buttonCancel class='button' value="<s:text name='button.return.value'/>" onclick="javascript:history.go(-1);">
			</td>
			<!--取消按钮-->
			<td width="10%">
				<input type=hidden name="nodeType" title="節點類型" class="readonly" value="${nodeType }">
				<input type=hidden name="businessNo" title="業務號碼" class="readonly" value="${bussinessNo }">
			</td>
		</s:else>
		<td width="15%"></td>
	</tr>
</table>