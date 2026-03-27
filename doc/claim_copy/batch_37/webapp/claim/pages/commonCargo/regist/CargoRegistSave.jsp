<%@ include file="/common/taglibs.jsp"%>
<table id="buttonArea" cellpadding="0" cellspacing="0" width="90%"
	style="display: ">
	<!--在不同状态下，按钮的数量是不同的，-->
	<%-- (1)立案登记 " 保存" "取消" "已完成","已完成並提交"
                 (2)正在处理
                 (3)回退的
                 (4)已提交   "返回"
                 (5)撤消     "返回"
            --%>
	<tr>
		<%-- 隐藏所按的保存按钮是哪个的标志--%>
		<td>
			<input type="hidden" name=buttonSaveType value="1">
		</td>
	</tr>
	<tr>
		<c:if test="${prpLregist.status!='4'}">
			<td class=button style="width: 40%" align="center">
				<!--保存按钮-->
				<input type="button" name=buttonSave class='button' value="<s:text name='button.save.value'/>"
					onclick="return saveForm(this,'2');">
			</td>
			<td class=button style="width: 20%" align="center">
				<input type="button" name=buttonSaveFinishSubmit class='button'
					value="<s:text name='button.submit.value'/>"
					onclick="return saveForm(this,'4');">
			</td>
			<td class=button style="width: 40%" align="center">
				<!--取消按钮-->
				<input type=button name=buttonCancel class='button'
					value="<s:text name='button.cancel.value'/>"
					onclick="return resetForm();">
			</td>
		</c:if>
		<c:if test="${editType=='PERFECT'}">
		<td class=button style="width: 40%" align="center">
			<!-- 修改按钮 -->
			<input type="button" name=buttonSave class='button' value="<s:text name='button.edit.value'/>"
				onclick="return saveForm(this,'4');">
		</td>
		</c:if>
			<c:if test="${prpLregist.status=='4'}">
			<td class=button style="width: 33%" align="center">
				<c:if test="${ifclose=='true'}">
				<input type="button" name=buttonClose class='button' value="<s:text name='button.close.value'/>" <%-- 关 闭 --%>
					onclick="return window.close();">
					</c:if>
				<c:if test="${ifclose!='true'}">
				<!--返回按钮-->
				<!-- reason 加返回图标 -->
				<input type=button name=buttonBack class='button' value="<s:text name='button.return.value'/>"
					onclick="return history.back();">
				</c:if>
			</td>
		</c:if>
	</tr>
</table>