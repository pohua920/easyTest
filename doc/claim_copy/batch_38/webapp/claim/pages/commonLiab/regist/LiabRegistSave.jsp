<%@ include file="/common/taglibs.jsp"%>
<table id="buttonArea" cellpadding="0" cellspacing="0" width="90%" style="display:">
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
			<input type="hidden" name="buttonSaveType" value="1">
		</td>
	</tr>
	<tr>
		<td class="button" align="center">
			<c:choose>
				<c:when test="${editType == 'SHOW'}">
					<c:choose>
						<c:when test="${ifclose=='true'}">
							<input type="button" name="buttonClose" class="button" value="<s:text name='button.close.value' />" onclick="return window.close();">
						</c:when>
						<c:otherwise>
							<input type="button" name="buttonBack" class="button" value="<s:text name='button.return.value' />" onclick="return history.back();">
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${prpLregist.status=='4'}">
							<c:if test="${editType == 'PERFECT'}">
								<input type="button" name="buttonSaveFinishSubmit" class="button" value="<s:text name='button.submit.value' />" onclick="return saveForm(this,'4');">
							</c:if>
							<c:choose>
								<c:when test="${ifclose=='true'}">
									<input type="button" name="buttonClose" class="button" value="<s:text name='button.close.value' />" onclick="return window.close();">
								</c:when>
								<c:otherwise>
									<input type="button" name="buttonBack" class="button" value="<s:text name='button.return.value' />" onclick="return history.back();">
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<input type="button" name="buttonSave" class="button" value="<s:text name='button.save.value'/>" onclick="return saveForm(this,'2');">
							<input type="button" name="buttonSaveFinishSubmit" class="button" value="<s:text name='button.submit.value'/>" onclick="return saveForm(this,'4');">
							<input type="button" name="buttonCancel" class="button" value="<s:text name='button.cancel.value'/>" onclick="return resetForm();">
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>