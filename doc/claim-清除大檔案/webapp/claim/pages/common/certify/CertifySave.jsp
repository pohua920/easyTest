<table cellpadding="0" cellspacing="0" class="common">
	<tr>
		<%-- 隐藏所按的保存按钮是哪个的标志--%>
		<td>
			<input type="hidden" name=buttonSaveType value="1">
			<input type="hidden" name="nodeType" value="certi">
		</td>
	</tr>
	<tr>
		<td class="button" align="center">
			<c:if test="${prpLcertifyCollect.status!='4'}">
				<s:if test="#request.nodeType=='certi'">
					<!--保存按钮-->
					<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value' />" onclick="return saveForm(this,'2');">
					&nbsp;&nbsp;
					<input type="button" name=buttonSaveFinishSubmit class='button' value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
					&nbsp;&nbsp;
				</s:if>
				<s:else>
					<input type="button" name=buttonSave class='button' value="保存" onclick="return saveForm(this,'2');">
					&nbsp;&nbsp;
				</s:else>
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button' value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
				&nbsp;&nbsp;
			</c:if>
			<!--放弃任务(只对待处理状态)-->
			<c:if test="${status!=null&&status=='0'}">
				<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskCertifyGiveup();">
				&nbsp;&nbsp;
				<%--放弃任务--%>
			</c:if>
			<!--放弃任务-->
			<c:if test="${prpLcertifyCollect.status=='4'}">
				<s:if test="#attr.ifclose=='true'">
					<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value' />" onclick="window.close();">
				</s:if>
				<s:else>
					<!--取消按钮-->
					<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
				</s:else>
				&nbsp;&nbsp;
			</c:if>
		</td>
	</tr>
</table>
