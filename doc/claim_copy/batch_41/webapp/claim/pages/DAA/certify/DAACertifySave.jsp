<%@ include file="/common/taglibs.jsp"%>
<table cellspacing="0" cellpadding="0">
	<tr>
		<td class="button" >
			<s:if test="#request.nodeType=='check'||#request.nodeType=='certa'||#request.nodeType=='verif'">
				<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value' />" onclick="javascript:window.close();">
				&nbsp;&nbsp;
			</s:if>
			<s:else>
				<input type="hidden" name=buttonSaveType value="1">
				<s:if test="#request.dfFlag=='Y'">
					<!--取消按钮-->
					<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
					&nbsp;&nbsp;
				</s:if>
				<s:else>
					<s:if test="#request.prpLcertifyCollect.status!=4">
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
				</s:if>
					<!--放弃任务(只对待处理状态)-->
					<s:if test="nodeType == 'certi' && #request.prpLcertifyCollect.status!=null&&#request.prpLcertifyCollect.status==0">
						<!--放弃按钮style="width:33%"-->
						<input type=button name=buttonGiveup class='button' value="<s:text name='button.giveUpTask.value' />" onclick="taskGiveup();">
						&nbsp;&nbsp;
				</s:if>
					<!--放弃任务-->
					<s:if test="#request.prpLcertifyCollect.status==4">
						<s:if test="#request.ifclose=='true'">
							<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value' />" onclick="window.close();">
							&nbsp;&nbsp;
					</s:if>
						<s:else>
							<!--取消按钮-->
							<input type=button name=buttonBack class='button' value="<s:text name='button.return.value' />" onclick="return history.back();">
							&nbsp;&nbsp;
					</s:else>
					</s:if>
				</s:else>
			</s:else>
		</td>
	</tr>
</table>